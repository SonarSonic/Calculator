package sonar.calculator.mod.common.tileentity;

import net.minecraft.item.ItemStack;
import cofh.api.energy.EnergyStorage;
import sonar.core.common.tileentity.TileEntityInventory;

public class TileEntityBigInventory extends TileEntityInventory {

	/**full number stored*/
	public int[] stored;
	
	/**stack with maximum allowed size*/
	public ItemStack[] savedSlots;
	
	public TileEntityBigInventory(int size) {		
		super.slots = new ItemStack[size];	
		this.stored = new int[size];			
	}
	
	@Override
	public ItemStack getStackInSlot(int var1) {
		int stackSize = Math.min(this.slots[var1].stackSize, stored[var1]);
		ItemStack stack = this.slots[var1].copy();
		stack.stackSize=stackSize;
		return stack;
	}
	@Override
	public ItemStack decrStackSize(int slot, int var2) {
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
	public void updateStored(int slot){
		int maxSize = savedSlots[slot].getMaxStackSize();
		int size = Math.min(maxSize, stored[slot]);
		
		//this.savedSlots[slot]=new ItemStack(savedSlots);
	}
	
	
	
}
