package sonar.calculator.mod.common.tileentity;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.machines.IPausable;
import sonar.calculator.mod.api.machines.IProcessMachine;
import sonar.calculator.mod.common.item.misc.UpgradeCircuit;
import sonar.core.SonarCore;
import sonar.core.common.tileentity.TileEntitySidedInventoryReceiver;
import sonar.core.inventory.IAdditionalInventory;
import sonar.core.network.sync.SyncBoolean;
import sonar.core.network.sync.SyncInt;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.utils.IUpgradeCircuits;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** electric smelting tile entity */
public abstract class TileEntityProcess extends TileEntitySidedInventoryReceiver implements IUpgradeCircuits, IPausable, IAdditionalInventory, IProcessMachine, IByteBufTile {
	// public int cookTime;
	public int sUpgrade;
	public int eUpgrade;

	public float renderTicks;
	public double energyBuffer;
	// private boolean paused;
	// public boolean invertPaused;
	public SyncBoolean invertPaused = new SyncBoolean(0);
	public SyncBoolean paused = new SyncBoolean(1);
	public SyncInt cookTime = new SyncInt(2);

	public static int lowestSpeed = 4, lowestEnergy = 1000;

	// client
	public int currentSpeed;

	public void updateEntity() {
		super.updateEntity();
		if (!worldObj.isRemote) {
			boolean oldPause = paused.getBoolean();
			if (this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
				this.paused.setBoolean(false);
				return;
			} else {
				this.paused.setBoolean(true);
			}
			if (oldPause != paused.getBoolean()) {
				this.onPause();
			}
		}
		int flag = 0;

		if (!isPaused()) {
			if (this.cookTime.getInt() > 0) {
				this.cookTime.increaseBy(1);
				if (!this.worldObj.isRemote) {
					modifyEnergy();
				}
			}
			if (this.canProcess()) {
				this.renderTicks();
				if (!this.worldObj.isRemote) {
					if (cookTime.getInt() == 0) {
						this.cookTime.increaseBy(1);
						modifyEnergy();
						flag = 1;
					}
					if (this.cookTime.getInt() >= this.getProcessTime()) {
						this.finishProcess();
						if (canProcess()) {
							this.cookTime.increaseBy(1);
						} else {
							flag = 2;
						}
						cookTime.setInt(0);
						this.energyBuffer = 0;
					}
				}
			} else {
				renderTicks = 0;
				if (cookTime.getInt() != 0) {
					cookTime.setInt(0);
					this.energyBuffer = 0;
					flag = 2;
				}

			}
			if (flag != 0) {
				if (flag == 1 || flag == 2 && !canProcess()) {
					SonarCore.sendPacketAround(this, 128, 2);
				}
			}
		}

		this.markDirty();
	}

	public void modifyEnergy() {
		energyBuffer += getEnergyUsage();
		int energyUsage = (int) Math.round(energyBuffer);
		if (energyBuffer - energyUsage < 0) {
			this.energyBuffer = 0;
		} else {
			energyBuffer -= energyUsage;
		}
		this.storage.modifyEnergyStored(-energyUsage);
	}

	public void renderTicks() {
		if (this instanceof TileEntityMachines.PrecisionChamber || this instanceof TileEntityMachines.ExtractionChamber) {
			this.renderTicks += (float) Math.max(1, sUpgrade) / 50;
		} else {
			this.renderTicks += (float) Math.max(1, sUpgrade * 8) / 1000;
		}
		if (this.renderTicks >= 2) {
			this.renderTicks = 0;
		}
	}

	public float getRenderPosition() {
		return renderTicks < 1 ? renderTicks : 1 - (renderTicks - 1);

	}

	public abstract boolean canProcess();

	public abstract void finishProcess();

	private int roundNumber(double i) {
		return (int) (Math.ceil(i / 10) * 10);
	}

	public int requiredEnergy() {
		if (eUpgrade + sUpgrade == 0) {
			return 1000 * 5;
		}
		int i = 16 - (eUpgrade - sUpgrade);
		return roundNumber(((4 + ((i * i) * 2 + i)) * 2) * Math.max(1, (eUpgrade - sUpgrade))) * 5;
	}

	public boolean receiveClientEvent(int action, int param) {
		if (action == 1) {
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		return true;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			paused.readFromNBT(nbt, type);
			invertPaused.readFromNBT(nbt, type);
			cookTime.readFromNBT(nbt, type);
			this.sUpgrade = nbt.getShort("sUpgrade");
			this.eUpgrade = nbt.getShort("eUpgrade");
			if (type == SyncType.SYNC) {
				this.currentSpeed = nbt.getInteger("speed");
			}
		}

	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			paused.writeToNBT(nbt, type);
			invertPaused.writeToNBT(nbt, type);
			cookTime.writeToNBT(nbt, type);
			nbt.setShort("sUpgrade", (short) this.sUpgrade);
			nbt.setShort("eUpgrade", (short) this.eUpgrade);
			if (type == SyncType.SYNC) {
				nbt.setInteger("speed", this.getProcessTime());
			}
		}
	}

	// IPausable
	@Override
	public boolean isActive() {
		return !isPaused() && cookTime.getInt() > 0;
	}

	@Override
	public void onPause() {
		// paused.invert();
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		this.worldObj.addBlockEvent(xCoord, yCoord, zCoord, blockType, 1, 1);
	}

	@Override
	public boolean isPaused() {
		return invertPaused.getBoolean() ? paused.getBoolean() : !paused.getBoolean();
	}

	@Override
	public boolean canAddUpgrades() {
		return cookTime.getInt() == 0;
	}

	@Override
	public boolean canAddUpgrades(int type) {
		if (type == 0) {
			return true;
		} else if (type == 1) {
			return true;
		}
		return false;
	}

	@Override
	public int getUpgrades(int type) {
		if (type == 0) {
			return sUpgrade;

		} else if (type == 1) {
			return eUpgrade;
		}
		return 0;
	}

	@Override
	public void incrementUpgrades(int type, int increment) {
		if (type == 0) {
			sUpgrade += increment;
		} else if (type == 1) {
			eUpgrade += increment;
		}

	}

	@Override
	public int getMaxUpgrades(int type) {
		switch (type) {
		case 0:
			return 16;
		case 1:
			return 16;
		}
		return 1;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int slots) {
		return true;
	}

	public boolean canStack(ItemStack current, ItemStack stack) {
		if (current == null) {
			return true;
		} else if (current.stackSize == current.getMaxStackSize()) {
			return false;
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		if (sUpgrade != 0) {
			String speed = FontHelper.translate("circuit.speed") + ": " + sUpgrade;

			currenttip.add(speed);
		}
		if (eUpgrade != 0) {
			String energy = FontHelper.translate("circuit.energy") + ": " + eUpgrade;
			currenttip.add(energy);
		}
		return currenttip;
	}

	@Override
	public ItemStack[] getAdditionalStacks() {
		ItemStack[] circuits = new ItemStack[2];
		if (this.getUpgrades(0) != 0) {
			circuits[0] = new ItemStack(Calculator.speedUpgrade, this.getUpgrades(0));
		}
		if (this.getUpgrades(1) != 0) {
			circuits[1] = new ItemStack(Calculator.energyUpgrade, this.getUpgrades(1));
		}
		return circuits;
	}

	@Override
	public int getCurrentProcessTime() {
		return cookTime.getInt();
	}

	@Override
	public int getProcessTime() {
		int i = 16 - sUpgrade;
		if (sUpgrade == 0) {
			return 1000;
		}
		return ((8 + ((i * i) * 2 + i)));
	}

	@Override
	public double getEnergyUsage() {
		return requiredEnergy() / getProcessTime();
	}

	@Override
	public void writePacket(ByteBuf buf, int id) {
		if (id == 1) {
			invertPaused.invert();
			invertPaused.writeToBuf(buf);
		}
		if (id == 2) {
			invertPaused.writeToBuf(buf);
			paused.writeToBuf(buf);
		}
	}

	@Override
	public void readPacket(ByteBuf buf, int id) {
		if (id == 0) {
			for (int i = 0; i < 3; i++) {
				if (getUpgrades(i) != 0 && UpgradeCircuit.getItem(i) != null) {
					ForgeDirection dir = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
					EntityItem upgrade = new EntityItem(worldObj, xCoord + dir.offsetX, yCoord + 0.5, zCoord + dir.offsetZ, new ItemStack(UpgradeCircuit.getItem(i), getUpgrades(i)));
					incrementUpgrades(i, -getUpgrades(i));
					worldObj.spawnEntityInWorld(upgrade);
				}
			}
		}
		if (id == 1) {
			invertPaused.readFromBuf(buf);
			onPause();
		}
		if (id == 2) {
			invertPaused.readFromBuf(buf);
			paused.readFromBuf(buf);
		}
	}
}
