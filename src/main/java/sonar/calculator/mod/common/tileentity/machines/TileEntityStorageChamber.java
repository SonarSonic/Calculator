package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IStability;
import sonar.core.common.tileentity.TileEntitySidedInventory;
import sonar.core.network.utils.ISyncTile;
import sonar.core.utils.helpers.NBTHelper.SyncType;

/** needs clean up */
public class TileEntityStorageChamber extends TileEntitySidedInventory implements ISyncTile, ISidedInventory {

	public static int maxSize = 5000;
	public int[] stored = new int[14];
	public ItemStack savedStack;
	public int renderTicks;

	public void updateEntity() {
		super.updateEntity();
		if (renderTicks != 1300) {
			renderTicks++;
		} else {
			renderTicks = 0;
		}
	}

	public TileEntityStorageChamber() {
		this.stored = new int[14];
		super.slots = new ItemStack[14];
		super.input = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
		super.output = new int[] { 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27 };
	}

	@Override
	public int getSizeInventory() {
		return stored.length * 2;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	public boolean isInputSlot(int slot) {
		return slot < stored.length;
	}

	public boolean isOutputSlot(int slot) {
		return slot >= stored.length;
	}

	/**
	 * has the functionality for any stack size;
	 * 
	 * @param slot
	 * @return
	 */
	public ItemStack getInputStack(int slot) {
		if (this.getSavedStack() == null || this.stored[slot] == 0) {
			return null;
		}
		int stackSize = 0;
		int current = (int) Math.floor((this.stored[slot] / savedStack.getMaxStackSize()));
		if (current == this.maxSize) {
			stackSize = savedStack.getMaxStackSize();
		} else {
			stackSize = this.stored[slot] - (current * savedStack.getMaxStackSize());
		}
		if (stackSize == 0) {
			return null;
		}
		ItemStack outputStack = savedStack.copy();
		outputStack.setItemDamage(slot);
		outputStack.stackSize = stackSize;
		if (stackSize == savedStack.getMaxStackSize()) {
			return null;
		}
		return outputStack;

	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		int slot = var1 - stored.length;
		if (isInputSlot(var1)) {
			return this.getInputStack(var1);
		} else if (this.isOutputSlot(var1)) {
			return this.getSlotStack(slot, 1);
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int var2) {
		if (this.isOutputSlot(slot)) {
			int derc = Math.min(var2, this.stored[slot - stored.length]);
			ItemStack output = this.getSlotStack(slot - stored.length, derc);
			this.stored[slot - stored.length] -= derc;
			if (this.stored[slot - stored.length] == 0) {
				this.slots[slot - stored.length] = null;
				this.resetSavedStack(slot - stored.length);
			}
			return output;
		} else if (this.slots[slot] != null) {

			if (this.slots[slot].stackSize <= var2) {
				ItemStack itemstack = this.slots[slot];
				this.slots[slot] = null;
				return itemstack;
			}
			ItemStack itemstack = this.slots[slot].splitStack(var2);

			if (this.slots[slot].stackSize == 0) {
				this.slots[slot] = null;
			}

			return itemstack;
		}

		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (this.isInputSlot(i)) {
			if (itemstack == null) {
				return;
			}
			this.stored[itemstack.getItemDamage()] += 1;
			if (getSavedStack() == null) {
				setSavedStack(itemstack);
			}
		} else if (this.isOutputSlot(i)) {
			if (stored[i - stored.length] == 1) {
				resetSavedStack(i - stored.length);
			}
			this.stored[i - stored.length] -= 1;

		}

	}

	public void setDisplayContents(int i, ItemStack itemstack) {
		this.slots[i] = itemstack;
		if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit())) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (stack != null) {
			if (stored[stack.getItemDamage()] == this.maxSize) {
				return false;
			}
			if (getSavedStack() != null) {
				if (this.getCircuitType(this.getSavedStack()) == this.getCircuitType(stack)) {
					return true;
				}
			} else {
				if (this.getCircuitType(stack) != null) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {

		return this.isInputSlot(slot) && isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return this.isOutputSlot(slot) && !this.isInputSlot(slot);
	}

	public boolean isItemValid(int slot, ItemStack stack) {
		if (stack != null && this.isInputSlot(slot)) {
			if (stack.getItemDamage() != slot) {
				return false;
			}
			if (getSavedStack() != null) {
				if (this.getCircuitType(this.getSavedStack()) == this.getCircuitType(stack)) {
					return true;
				}
			} else {
				if (this.getCircuitType(stack) != null) {
					return true;
				}
			}
		}
		return false;
	}

	public ItemStack getFullStack(int slot) {
		if (stored[slot] != 0 && getSavedStack() != null) {
			ItemStack fullStack = new ItemStack(getSavedStack().getItem(), stored[slot], slot);
			fullStack.setTagCompound(getSavedStack().getTagCompound());
			return fullStack;
		}
		return null;
	}

	public ItemStack getSlotStack(int slot, int size) {
		if (stored[slot] != 0 && getSavedStack() != null) {
			ItemStack slotStack = new ItemStack(getSavedStack().getItem(), size, slot);
			slotStack.setTagCompound(getSavedStack().getTagCompound());
			return slotStack;
		}
		return null;
	}

	public void resetSavedStack(int removed) {
		boolean found = false;
		for (int i = 0; i < stored.length; i++) {
			if (i != removed) {
				if (stored[i] != 0) {
					found = true;
					return;
				}
			}
		}
		if (!found) {
			this.setSavedStack(null);
		}
	}

	public ItemStack getSavedStack() {
		return savedStack;
	}

	public void setSavedStack(ItemStack stack) {
		if (stack != null)
			stack.stackSize = 1;
		savedStack = stack;
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public static CircuitType getCircuitType(ItemStack stack) {
		if (stack != null) {
			if (stack.getItem() == Calculator.circuitBoard) {
				if (stack.getItem() instanceof IStability) {
					IStability stability = (IStability) stack.getItem();
					if (stability.getStability(stack)) {
						NBTTagCompound tag = new NBTTagCompound();
						tag.setInteger("Stable", 1);
						tag.setInteger("Item1", 0);
						tag.setInteger("Item2", 0);
						tag.setInteger("Item3", 0);
						tag.setInteger("Item4", 0);
						tag.setInteger("Item5", 0);
						tag.setInteger("Item6", 0);
						tag.setInteger("Energy", 0);
						ItemStack stable = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
						stable.setTagCompound(tag);
						if (ItemStack.areItemStackTagsEqual(stable, stack)) {
							return CircuitType.Stable;
						}
					} else if (!stack.hasTagCompound()) {
						return CircuitType.Analysed;
					} else if (stack.hasTagCompound()) {
						NBTTagCompound tag = new NBTTagCompound();
						tag.setInteger("Stable", 0);
						tag.setInteger("Item1", 0);
						tag.setInteger("Item2", 0);
						tag.setInteger("Item3", 0);
						tag.setInteger("Item4", 0);
						tag.setInteger("Item5", 0);
						tag.setInteger("Item6", 0);
						tag.setInteger("Energy", 0);
						ItemStack analysed = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
						analysed.setTagCompound(tag);
						if (ItemStack.areItemStackTagsEqual(analysed, stack)) {
							return CircuitType.Analysed;
						}
					}
				}
			} else if (stack.getItem() == Calculator.circuitDamaged) {
				return CircuitType.Damaged;
			} else if (stack.getItem() == Calculator.circuitDirty) {
				return CircuitType.Dirty;
			}
		}

		return null;
	}

	public static CircuitType getCircuitType(int type) {
		if (type == 2) {
			return CircuitType.Stable;
		}
		if (type == 3) {
			return CircuitType.Damaged;
		}
		if (type == 5) {
			return CircuitType.Dirty;
		}
		return CircuitType.Analysed;
	}

	public static int getCircuitValue(CircuitType type) {
		if (type == CircuitType.Stable) {
			return 2;
		}
		if (type == CircuitType.Damaged) {
			return 3;
		}
		if (type == CircuitType.Dirty) {
			return 4;
		}
		return 1;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		if (type == SyncType.SAVE || type == SyncType.DROP) {
			stored = nbt.getIntArray("Stored");
			if (this.stored.length != 14 || stored == null) {
				stored = new int[14];
			}
			this.savedStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("saved"));
		}

	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		if (type == SyncType.SAVE) {
			nbt.setIntArray("Stored", stored);
			NBTTagCompound stack = new NBTTagCompound();
			if (savedStack != null) {
				savedStack.writeToNBT(stack);
			}
			nbt.setTag("saved", stack);
		} else if (type == SyncType.DROP) {
			nbt.setIntArray("Stored", stored);
			if (getSavedStack() != null) {
				NBTTagCompound stack = new NBTTagCompound();
				this.getSavedStack().writeToNBT(stack);
				nbt.setTag("saved", stack);
				nbt.setInteger("type", TileEntityStorageChamber.getCircuitValue(TileEntityStorageChamber.getCircuitType(this.getSavedStack())));
			}

		}
	}

	private enum CircuitType {
		Analysed, Stable, Damaged, Dirty;
	}

}
