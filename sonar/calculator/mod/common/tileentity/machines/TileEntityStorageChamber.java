package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.StatCollector;
import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.IBigInventory;
import sonar.calculator.mod.api.IStability;
import sonar.calculator.mod.api.ISyncTile;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.network.packets.PacketSonarSides;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.common.tileentity.TileEntitySidedInventory;
import sonar.core.utils.IDropTile;

public class TileEntityStorageChamber extends TileEntitySidedInventory
		implements IDropTile, ISidedInventory, IBigInventory {

	public static int maxSize = 1000;
	public int[] stored;
	public boolean update;

	public TileEntityStorageChamber() {
		this.stored = new int[14];
		super.slots = new ItemStack[15];
		super.input = new int[] { 200, 201, 202, 203, 204, 205, 206, 207, 208,
				209, 210, 211, 212, 213 };
		super.output = new int[] { 100, 101, 102, 103, 104, 105, 106, 107, 108,
				109, 110, 111, 112, 113 };
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.update = nbt.getBoolean("update");
		stored = nbt.getIntArray("Stored");
		if (stored == null) {
			stored = new int[14];
		}
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("update", update);
		nbt.setIntArray("Stored", stored);
	}

	@Override
	public int getSizeInventory() {
		return this.slots.length;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		if (var1 >= 200) {
			return this.slots[var1 - 200];
		}
		if (var1 >= 100) {
			return this.getSlotStack(var1 - 100);
		}
		return this.slots[var1];
	}

	@Override
	public ItemStack decrStackSize(int slot, int var2) {
		if(slot>=100){
			if(this.stored[slot-100]!=0){
				ItemStack display = this.getSlotStack(slot-100);
			this.stored[slot-100]--;
			if(this.stored[slot-100]==0){
				this.slots[slot-100]=null;
				this.resetSavedStack(slot-100);
			}
			return display;
			}
			return null;
		}		
		if (this.slots[slot] != null) {

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
		if (i >= 200) {
			this.stored[itemstack.getItemDamage()] += 1;
			if (getSavedStack() == null) {
				setSavedStack(itemstack);
			}
		} else if (i >= 100) {
			if (stored[i - 100] == 1) {
				resetSavedStack(i - 100);
			}
			this.stored[i - 100] -= 1;
		} else {
			this.slots[i] = itemstack;

			if ((itemstack != null)
					&& (itemstack.stackSize > getInventoryStackLimit())) {
				itemstack.stackSize = getInventoryStackLimit();
			}
		}
	}

	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (stack != null) {
			if (stored[stack.getItemDamage()] == this.maxSize) {
				return false;
			}
			if (getSavedStack() != null) {
				if (this.getCircuitType(this.getSavedStack()) == this
						.getCircuitType(stack)) {
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
		return isItemValidForSlot(slot, stack);
	}

	public boolean isItemValid(int slot, ItemStack stack) {
		if (stack != null) {
			if (stack.getItemDamage() != slot) {
				return false;
			}
			if (getSavedStack() != null) {
				if (this.getCircuitType(this.getSavedStack()) == this
						.getCircuitType(stack)) {
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
			return new ItemStack(getSavedStack().getItem(), stored[slot], slot);
		}
		return null;
	}

	public ItemStack getSlotStack(int slot) {
		if (stored[slot] != 0 && getSavedStack() != null) {
			return new ItemStack(getSavedStack().getItem(), 1, slot);
		}
		return null;
	}

	public void resetSavedStack(int removed) {
		boolean found = false;
		for (int i = 0; i < slots.length - 1; i++) {
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
		return slots[14];
	}

	public void setSavedStack(ItemStack stack) {
		slots[14] = stack;
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
						ItemStack stable = new ItemStack(stack.getItem(), 1,
								stack.getItemDamage());
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
						ItemStack analysed = new ItemStack(stack.getItem(), 1,
								stack.getItemDamage());
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

	@Override
	public void readInfo(NBTTagCompound tag) {
		this.stored = tag.getIntArray("stored");
		this.setSavedStack(ItemStack.loadItemStackFromNBT(tag
				.getCompoundTag("saved")));
	}

	@Override
	public void writeInfo(NBTTagCompound tag) {
		tag.setIntArray("stored", stored);
		if (getSavedStack() != null) {
			NBTTagCompound stack = new NBTTagCompound();
			this.getSavedStack().writeToNBT(stack);
			tag.setTag("saved", stack);
			tag.setInteger("type", TileEntityStorageChamber
					.getCircuitValue(TileEntityStorageChamber
							.getCircuitType(this.getSavedStack())));
		}

	}

	private enum CircuitType {
		Analysed, Stable, Damaged, Dirty;
	}

	@Override
	public void sendPacket(int dimension, int side, int value) {
		Calculator.network.sendToAllAround(new PacketSonarSides(xCoord, yCoord,
				zCoord, side, value), new TargetPoint(dimension, xCoord,
				yCoord, zCoord, 32));

	}
}
