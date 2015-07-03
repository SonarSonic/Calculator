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
import sonar.core.common.tileentity.TileEntitySidedInventoryReceiver;
import sonar.core.utils.helpers.RecipeHelper;

/** electric smelting tile entity */
public abstract class TileEntityProcess extends TileEntitySidedInventoryReceiver implements IUpgradeCircuits, IPausable {
	public int cookTime;
	public int sUpgrade;
	public int eUpgrade;
	public double energyBuffer;
	public boolean paused;
	public int currentSpeed;
	public static int lowestSpeed = 4, lowestEnergy = 1000;

	public void updateEntity() {
		super.updateEntity();
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

	public abstract boolean canProcess();

	public abstract void finishProcess();

	public int currentSpeed() {
		int i = 16 - sUpgrade;
		return ((4 + ((i * i) * 2 + i)));
	}

	private int roundNumber(double i) {
		return (int) (Math.ceil(i / 10) * 10);
	}

	public double energyUsage() {

		return (double) requiredEnergy() / currentSpeed();
	}

	public int requiredEnergy() {
		if (eUpgrade + sUpgrade == 0) {
			return 1000;
		}
		int i = 16 - (eUpgrade - sUpgrade);
		return roundNumber(((4 + ((i * i) * 2 + i)) * 2) * Math.max(1, (eUpgrade - sUpgrade)));
	}

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
		this.paused = nbt.getBoolean("pause");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("CookTime", (short) this.cookTime);
		nbt.setShort("sUpgrade", (short) this.sUpgrade);
		nbt.setShort("eUpgrade", (short) this.eUpgrade);
		nbt.setBoolean("pause", this.paused);

	}

	@Override
	public void onSync(Object data, int id) {
		super.onSync(data, id);
		switch (id) {
		case SyncType.COOK:
			this.cookTime = (Integer) data;
			break;
		case SyncType.PAUSE:
			this.paused = (Boolean) data;
			break;
		case SyncType.SPEEDUPGRADES:
			this.currentSpeed = (Integer) data;
			break;
		}
	}

	@Override
	public SyncData getSyncData(int id) {
		switch (id) {
		case SyncType.COOK:
			return new SyncData(true, cookTime);
		case SyncType.PAUSE:
			return new SyncData(true, paused);
		case SyncType.SPEEDUPGRADES:
			return new SyncData(true, this.currentSpeed());
		}
		return super.getSyncData(id);
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
