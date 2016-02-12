package sonar.calculator.mod.common.tileentity.machines;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.machines.IPausable;
import sonar.calculator.mod.common.item.misc.ItemCircuit;
import sonar.calculator.mod.common.recipes.machines.AlgorithmSeparatorRecipes;
import sonar.core.common.tileentity.TileEntitySidedInventoryReceiver;
import sonar.core.network.sync.SyncInt;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.RecipeHelper;
import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityFlawlessFurnace extends TileEntitySidedInventoryReceiver implements IPausable {
	public SyncInt[] cookTime = new SyncInt[9];
	public float renderTicks;
	public double energyBuffer;
	public boolean paused;
	public final int speed = 100;
	public int size = 9;
	public int maxProcess;

	public TileEntityFlawlessFurnace() {
		this.slots = new ItemStack[28];
		this.storage = new EnergyStorage(10000000);
	}

	public void updateEntity() {
		super.updateEntity();
		if (this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
			this.paused = true;
			return;
		} else {
			this.paused = false;
		}
		this.discharge(27);
		if (!paused) {
			for (int i = 0; i < 9; i++) {
				if (this.cookTime[i].getInt() > 0) {
					this.cookTime[i].increaseBy(1);
					if (!this.worldObj.isRemote) {
						energyBuffer += (energyUsage() / speed) * 8;
						int energyUsage = (int) Math.round(energyBuffer);
						if (energyBuffer - energyUsage < 0) {
							this.energyBuffer = 0;
						} else {
							energyBuffer -= energyUsage;
						}
						this.storage.modifyEnergyStored(-energyUsage);
					}
				}
				if (this.canProcess(i)) {
					if (!this.worldObj.isRemote) {
						if (this.cookTime[i].getInt() == 0) {
							this.cookTime[i].increaseBy(1);
							energyBuffer += energyUsage() / speed;
							int energyUsage = (int) Math.round(energyBuffer);
							if (energyBuffer - energyUsage < 0) {
								this.energyBuffer = 0;
							} else {
								energyBuffer -= energyUsage;
							}
							this.storage.modifyEnergyStored(-energyUsage);
						}
						if (this.cookTime[i].getInt() >= this.currentSpeed()) {
							for (int process = 0; process < 8; process++) {
								if (canProcess(i)) {
									this.finishProcess(i);
								}
							}
							if (canProcess(i)) {
								this.cookTime[i].increaseBy(1);
							} else {
							}
							this.cookTime[i].setInt(0);
							this.energyBuffer = 0;
						}
					}
				} else {
					renderTicks = 0;
					if (cookTime[i].getInt() != 0) {
						this.cookTime[i].setInt(0);
						this.energyBuffer = 0;
					}
				}
			}
		}

		this.markDirty();
	}

	public boolean canProcess(int slot) {
		if (slots[slot] == null) {
			return false;
		}

		if (cookTime[slot].getInt() == 0) {
			// if (this.storage.getEnergyStored() < energyUsage()) {
			// return false;
			// }
		}
		ItemStack[] output = getOutput(true, slots[slot]);
		if (output == null || output.length == 0) {
			return false;
		}
		for (int o = 0; o < output.length; o++) {
			if (output[o] == null) {
				return false;
			} else {
				if (slots[slot + ((o + 1) * 9)] != null) {
					if (!slots[slot + ((o + 1) * 9)].isItemEqual(output[o])) {

						return false;
					} else if (slots[slot + ((o + 1) * 9)].stackSize + output[o].stackSize > slots[slot + ((o + 1) * 9)].getMaxStackSize()) {

						return false;
					}
				}
			}
		}
		return true;
	}

	public void finishProcess(int slot) {
		ItemStack[] output = getOutput(false, slots[slot]);
		for (int o = 0; o < output.length; o++) {
			if (output[o] != null) {
				if (this.slots[slot + ((o + 1) * 9)] == null) {
					ItemStack outputStack = output[o].copy();
					if (output[o].getItem() == Calculator.circuitBoard) {
						ItemCircuit.setData(outputStack);
					}
					this.slots[slot + ((o + 1) * 9)] = outputStack;
				} else if (this.slots[slot + ((o + 1) * 9)].isItemEqual(output[o])) {
					this.slots[slot + ((o + 1) * 9)].stackSize += output[o].stackSize;

				}
			}
		}
		if (recipeHelper() != null) {
			this.slots[slot].stackSize -= recipeHelper().getInputSize(0, output);
		} else {
			this.slots[slot].stackSize -= 1;
		}
		if (this.slots[slot].stackSize <= 0) {
			this.slots[slot] = null;
		}

	}

	public RecipeHelper recipeHelper() {
		return AlgorithmSeparatorRecipes.instance();
	}

	public ItemStack[] getOutput(boolean simulate, ItemStack... stacks) {
		// return new ItemStack[] {
		// FurnaceRecipes.smelting().getSmeltingResult(stacks[0]) };
		return recipeHelper().getOutput(stacks);
	}

	public int currentSpeed() {
		return speed;
	}

	private int roundNumber(double i) {
		return (int) (Math.ceil(i / 10) * 10);
	}

	public double energyUsage() {

		return 5000;
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
			for (int i = 0; i < cookTime.length; i++){
				cookTime[i].readFromNBT(nbt, type);
			}
			this.paused = nbt.getBoolean("pause");
		}

	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			for (int i = 0; i < cookTime.length; i++){
				cookTime[i].writeToNBT(nbt, type);
			}
			nbt.setBoolean("pause", this.paused);
		}
	}

	// IPausable
	@Override
	public boolean isActive() {
		return !isPaused();
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

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		return currenttip;
	}

}
