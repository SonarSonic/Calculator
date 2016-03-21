package sonar.calculator.mod.common.tileentity;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.machines.IPausable;
import sonar.calculator.mod.api.machines.IProcessMachine;
import sonar.calculator.mod.common.item.misc.UpgradeCircuit;
import sonar.core.SonarCore;
import sonar.core.common.tileentity.TileEntityEnergySidedInventory;
import sonar.core.common.tileentity.TileEntitySidedInventory;
import sonar.core.inventory.IAdditionalInventory;
import sonar.core.network.sync.ISyncPart;
import sonar.core.network.sync.SyncTagType;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.utils.IUpgradeCircuits;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;

import com.google.common.collect.Lists;

/** electric smelting tile entity */
public abstract class TileEntityProcess extends TileEntityEnergySidedInventory implements IUpgradeCircuits, IPausable, IAdditionalInventory, IProcessMachine, IByteBufTile {

	public float renderTicks;
	public double energyBuffer;

	public SyncTagType.BOOLEAN invertPaused = new SyncTagType.BOOLEAN(0);
	public SyncTagType.BOOLEAN paused = new SyncTagType.BOOLEAN(1);
	public SyncTagType.INT cookTime = new SyncTagType.INT(2);
	public SyncTagType.INT sUpgrade = new SyncTagType.INT(3);
	public SyncTagType.INT eUpgrade = new SyncTagType.INT(4);
	public boolean isActive = false;

	public static int lowestSpeed = 4, lowestEnergy = 1000;

	// client
	public int currentSpeed;

	public void update() {
		super.update();
		if (!worldObj.isRemote) {
			boolean oldPause = paused.getObject();
			if (this.worldObj.isBlockIndirectlyGettingPowered(pos) > 0) {
				this.paused.setObject(false);
				return;
			} else {
				this.paused.setObject(true);
			}
			if (oldPause != paused.getObject()) {
				this.onPause();
			}
		}
		boolean flag = this.isActive();

		if (!isPaused()) {
			if (this.cookTime.getObject() > 0) {
				this.cookTime.increaseBy(1);
				if (!this.worldObj.isRemote) {
					modifyEnergy();
				}
			}
			if (this.canProcess()) {
				this.renderTicks();
				if (!this.worldObj.isRemote) {
					if (cookTime.getObject() == 0) {
						this.cookTime.increaseBy(1);
						modifyEnergy();
					}
					if (this.cookTime.getObject() >= this.getProcessTime()) {
						this.finishProcess();
						if (canProcess()) {
							this.cookTime.increaseBy(1);
						}
						cookTime.setObject(0);
						this.energyBuffer = 0;
					}
				}
			} else {
				renderTicks = 0;
				if (cookTime.getObject() != 0) {
					cookTime.setObject(0);
					this.energyBuffer = 0;
				}

			}
			
		}
		boolean flag2 = this.isActive();
		if (flag != flag2) {
			isActive = flag2;
			SonarCore.sendPacketAround(this, 128, 2);
			worldObj.addBlockEvent(pos, this.getBlockType(), 1, 1);
		}
		this.markDirty();
	}

	public void onLoaded() {
		super.onLoaded();
		if (!worldObj.isRemote) {
			isActive = this.isActive();
			SonarCore.sendPacketAround(this, 128, 2);
			worldObj.addBlockEvent(pos, this.getBlockType(), 1, 1);
		}
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
			this.renderTicks += (float) Math.max(1, sUpgrade.getObject()) / 50;
		} else {
			this.renderTicks += (float) Math.max(1, sUpgrade.getObject() * 8) / 1000;
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
		if (eUpgrade.getObject() + sUpgrade.getObject() == 0) {
			return 1000 * 5;
		}
		int i = 16 - (eUpgrade.getObject() - sUpgrade.getObject());
		return roundNumber(((4 + ((i * i) * 2 + i)) * 2) * Math.max(1, (eUpgrade.getObject() - sUpgrade.getObject()))) * 5;
	}

	public boolean receiveClientEvent(int action, int param) {
		if (action == 1) {
			this.worldObj.markBlockForUpdate(pos);
		}
		return true;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			if (type == SyncType.SYNC) {
				this.currentSpeed = nbt.getInteger("speed");
			}
		}

	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			if (type == SyncType.SYNC) {
				nbt.setInteger("speed", this.getProcessTime());
			}
		}
	}

	public void addSyncParts(List<ISyncPart> parts) {
		super.addSyncParts(parts);
		parts.addAll(Lists.newArrayList(paused, invertPaused, cookTime, sUpgrade, eUpgrade));
	}

	// IPausable
	@Override
	public boolean isActive() {
		if (worldObj.isRemote) {
			return isActive;
		}
		return !isPaused() && cookTime.getObject() > 0;
	}

	@Override
	public void onPause() {
		// paused.invert();
		this.worldObj.markBlockForUpdate(pos);
		this.worldObj.addBlockEvent(pos, blockType, 1, 1);
	}

	@Override
	public boolean isPaused() {
		return invertPaused.getObject() ? paused.getObject() : !paused.getObject();
	}

	@Override
	public boolean canAddUpgrades() {
		return cookTime.getObject() == 0;
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
			return sUpgrade.getObject();

		} else if (type == 1) {
			return eUpgrade.getObject();
		}
		return 0;
	}

	@Override
	public void incrementUpgrades(int type, int increment) {
		if (type == 0) {
			sUpgrade.increaseBy(increment);
		} else if (type == 1) {
			eUpgrade.increaseBy(increment);
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

	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing dir) {
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
		if (sUpgrade.getObject() != 0) {
			String speed = FontHelper.translate("circuit.speed") + ": " + sUpgrade;

			currenttip.add(speed);
		}
		if (eUpgrade.getObject() != 0) {
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
		return cookTime.getObject();
	}

	@Override
	public int getProcessTime() {
		int i = 16 - sUpgrade.getObject();
		if (sUpgrade.getObject() == 0) {
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
			buf.writeBoolean(isActive);
		}
	}

	@Override
	public void readPacket(ByteBuf buf, int id) {
		if (id == 0) {
			for (int i = 0; i < 3; i++) {
				if (getUpgrades(i) != 0 && UpgradeCircuit.getItem(i) != null) {
					/* EnumFacing dir = EnumFacing.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord)); EntityItem upgrade = new EntityItem(worldObj, xCoord + dir.offsetX, yCoord + 0.5, zCoord + dir.offsetZ, new ItemStack(UpgradeCircuit.getItem(i), getUpgrades(i))); incrementUpgrades(i, -getUpgrades(i)); worldObj.spawnEntityInWorld(upgrade); */
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
			isActive = buf.readBoolean();
		}
	}
}
