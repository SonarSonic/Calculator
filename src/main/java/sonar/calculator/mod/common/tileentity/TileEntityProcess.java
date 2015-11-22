package sonar.calculator.mod.common.tileentity;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.api.IPausable;
import sonar.calculator.mod.common.item.misc.UpgradeCircuit;
import sonar.core.common.tileentity.TileEntitySidedInventoryReceiver;
import sonar.core.utils.IMachineButtons;
import sonar.core.utils.IUpgradeCircuits;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** electric smelting tile entity */
public abstract class TileEntityProcess extends TileEntitySidedInventoryReceiver implements IUpgradeCircuits, IPausable, IMachineButtons {
	public int cookTime;
	public int sUpgrade;
	public int eUpgrade;
	public float renderTicks;
	public double energyBuffer;
	public boolean paused;
	public int currentSpeed;
	public static int lowestSpeed = 4, lowestEnergy = 1000;

	public void updateEntity() {
		super.updateEntity();
		if (this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
			this.paused = true;
			return;
		} else {
			this.paused = false;
		}
		int flag = 0;

		if (!paused) {
			if (this.cookTime > 0) {
				this.cookTime++;
				if (!this.worldObj.isRemote) {
					energyBuffer += energyUsage();
					int energyUsage = (int) Math.round(energyBuffer);
					if (energyBuffer - energyUsage < 0) {
						this.energyBuffer = 0;
					} else {
						energyBuffer -= energyUsage;
					}
					this.storage.modifyEnergyStored(-energyUsage);
				}
			}
			if (this.canProcess()) {
				this.renderTicks();
				if (!this.worldObj.isRemote) {
					if (cookTime == 0) {
						this.cookTime++;
						energyBuffer += energyUsage();
						int energyUsage = (int) Math.round(energyBuffer);
						if (energyBuffer - energyUsage < 0) {
							this.energyBuffer = 0;
						} else {
							energyBuffer -= energyUsage;
						}
						this.storage.modifyEnergyStored(-energyUsage);
						flag = 1;
					}
					if (this.cookTime >= this.currentSpeed()) {
						this.finishProcess();
						if (canProcess()) {
							cookTime++;
						} else {
							flag = 2;
						}
						this.cookTime = 0;
						this.energyBuffer = 0;
					}
				}
			} else {
				renderTicks = 0;
				if (cookTime != 0) {
					this.cookTime = 0;
					this.energyBuffer = 0;
					flag = 2;
				}

			}
			if (flag != 0) {
				if (flag == 1 || flag == 2 && !canProcess()) {
					this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					this.worldObj.addBlockEvent(xCoord, yCoord, zCoord, blockType, 1, 1);
				}
			}
		}

		this.markDirty();
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

	public int currentSpeed() {
		int i = 16 - sUpgrade;
		if (sUpgrade == 0) {
			return 1000;
		}
		return ((8 + ((i * i) * 2 + i)));
	}

	private int roundNumber(double i) {
		return (int) (Math.ceil(i / 10) * 10);
	}

	public double energyUsage() {

		return (double) requiredEnergy() / currentSpeed();
	}

	public int requiredEnergy() {
		if (eUpgrade + sUpgrade == 0) {
			return 1000*5;
		}
		int i = 16 - (eUpgrade - sUpgrade);
		return roundNumber(((4 + ((i * i) * 2 + i)) * 2) * Math.max(1, (eUpgrade - sUpgrade)))*5;
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
			this.cookTime = nbt.getShort("CookTime");
			this.sUpgrade = nbt.getShort("sUpgrade");
			this.eUpgrade = nbt.getShort("eUpgrade");
			this.paused = nbt.getBoolean("pause");
			if (type == SyncType.SYNC) {
				this.currentSpeed = nbt.getInteger("speed");
			}
		}

	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setShort("CookTime", (short) this.cookTime);
			nbt.setShort("sUpgrade", (short) this.sUpgrade);
			nbt.setShort("eUpgrade", (short) this.eUpgrade);
			nbt.setBoolean("pause", this.paused);
			if (type == SyncType.SYNC) {
				nbt.setInteger("speed", this.currentSpeed());
			}
		}
	}

	// IPausable
	@Override
	public boolean isActive() {
		return !isPaused() && cookTime > 0;
	}

	@Override
	public void onPause() {
		paused = !paused;
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		this.worldObj.addBlockEvent(xCoord, yCoord, zCoord, blockType, 1, 1);
	}

	@Override
	public boolean isPaused() {
		return paused;
	}

	// IUpgradeCircuits
	@Override
	public boolean canAddUpgrades() {
		return cookTime == 0;
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
		}
		else if (current.stackSize == current.getMaxStackSize()) {
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

	public void buttonPress(int buttonID, int value) {
		switch (buttonID) {
		case 0:
			for (int i = 0; i < 3; i++) {
				if (getUpgrades(i) != 0 && UpgradeCircuit.getItem(i) != null) {
					ForgeDirection dir = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
					EntityItem upgrade = new EntityItem(worldObj, xCoord + dir.offsetX, yCoord + 0.5, zCoord + dir.offsetZ, new ItemStack(UpgradeCircuit.getItem(i), getUpgrades(i)));
					incrementUpgrades(i, -getUpgrades(i));
					worldObj.spawnEntityInWorld(upgrade);
				}
			}
			break;
		case 1:
			onPause();
		}
	}
}
