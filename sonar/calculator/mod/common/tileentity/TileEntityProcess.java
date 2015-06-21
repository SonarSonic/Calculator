package sonar.calculator.mod.common.tileentity;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IPausable;
import sonar.calculator.mod.api.IUpgradeCircuits;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.common.recipes.machines.RestorationChamberRecipes;
import sonar.calculator.mod.network.packets.PacketSonarSides;
import sonar.calculator.mod.utils.helpers.RecipeHelper;
import sonar.core.common.tileentity.TileEntitySidedInventoryReceiver;

/** electric smelting tile entity */
public abstract class TileEntityProcess extends TileEntitySidedInventoryReceiver implements IUpgradeCircuits, IPausable {
	public int cookTime;
	public int currentSpeed;
	public int currentEnergy;
	public int sUpgrade;
	public int eUpgrade;
	public boolean paused;

	public void updateEntity() {
		super.updateEntity();
		int flag = 0;
		if (currentSpeed == 0) {
			this.currentSpeed = this.getFurnaceSpeed();
		}
		if (currentEnergy == 0) {
			this.currentEnergy = this.getRequiredEnergy();
		}

		if (!paused) {
			if (this.cookTime > 0) {
				this.cookTime++;
				int energy = currentEnergy / currentSpeed;
				this.storage.modifyEnergyStored(-energy);

			}

			if (this.canProcess()) {
				if (!this.worldObj.isRemote) {
					if (cookTime == 0) {
						this.cookTime++;
						int energy = currentEnergy / currentSpeed;
						this.storage.modifyEnergyStored(-energy);
						flag = 1;
					}

					if (this.cookTime == currentSpeed) {
						this.cookTime = 0;
						this.finishProcess();
						if (canProcess()) {
							cookTime++;
							int energy = currentEnergy / currentSpeed;
						} else {
							flag = 2;
						}
					}
				}
			} else {
				if (cookTime != 0) {
					this.cookTime = 0;
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

	public abstract boolean canProcess();

	public abstract void finishProcess();

	public boolean receiveClientEvent(int action, int param) {
		if (action == 1) {
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.cookTime = nbt.getShort("CookTime");
		this.sUpgrade = nbt.getShort("sUpgrade");
		this.eUpgrade = nbt.getShort("eUpgrade");
		this.currentSpeed = nbt.getShort("currentSpeed");
		this.currentEnergy = nbt.getShort("currentEnergy");
		this.paused = nbt.getBoolean("pause");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("CookTime", (short) this.cookTime);
		nbt.setShort("sUpgrade", (short) this.sUpgrade);
		nbt.setShort("eUpgrade", (short) this.eUpgrade);
		nbt.setShort("currentSpeed", (short) this.currentSpeed);
		nbt.setShort("currentEnergy", (short) this.currentEnergy);
		nbt.setBoolean("pause", this.paused);

	}

	@Override
	public void onSync(Object data, int id) {
		super.onSync(data, id);
		switch (id) {
		case SyncType.COOK:
			this.cookTime = (Integer)data;
			break;
		case SyncType.CURRENTSPEED:
			this.currentSpeed = (Integer)data;
			break;
		case SyncType.PAUSE:
			this.paused = (boolean)data;
			break;
		}
	}

	@Override
	public SyncData getSyncData(int id) {
		switch (id) {
		case SyncType.COOK:
			return new SyncData(true, cookTime);
		case SyncType.CURRENTSPEED:
			return new SyncData(true, currentSpeed);
		case SyncType.PAUSE:
			return new SyncData(true, paused);
		}
		return super.getSyncData(id);
	}

	public int getFurnaceSpeed() {
		switch (sUpgrade) {
		case 1:
			return 800;
		case 2:
			return 600;
		case 3:
			return 400;
		case 4:
			return 200;
		case 5:
			return 100;
		case 6:
			return 50;
		case 7:
			return 40;
		case 8:
			return 20;
		case 9:
			return 10;
		case 10:
			return 8;

		}

		return 1000;
	}

	public int getRequiredEnergy() {
		switch (eUpgrade) {
		case 1:
			return 4000;
		case 2:
			return 3000;
		case 3:
			return 2000;
		case 4:
			return 1000;
		}
		return 5000;
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
			this.currentSpeed = getFurnaceSpeed();
		} else if (type == 1) {
			eUpgrade += increment;
			this.currentEnergy = getRequiredEnergy();
		}

	}

	@Override
	public int getMaxUpgrades(int type) {
		switch (type) {
		case 0:
			return 10;
		case 1:
			return 4;
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
		if (current.stackSize == current.getMaxStackSize()) {
			return false;
		}
		return true;
	}

	@Override
	public void sendPacket(int dimension, int side, int value) {
		Calculator.network.sendToAllAround(new PacketSonarSides(xCoord, yCoord, zCoord, side, value), new TargetPoint(dimension, xCoord, yCoord, zCoord, 32));

	}
}
