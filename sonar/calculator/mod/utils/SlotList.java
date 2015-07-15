package sonar.calculator.mod.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotList extends Slot {

	public SlotList(IInventory inv, int slot, int x, int y) {
		super(inv, slot, x, y);
	}

    public boolean canTakeStack(EntityPlayer player)
    {
        return false;
    }
    public ItemStack decrStackSize(int size)
    {
		this.inventory.setInventorySlotContents(this.getSlotIndex(), null);
		this.inventory.markDirty();
        return null;
    }

	public void onSlotChanged() {
		putStack(this.getStack());
	}

	public void putStack(ItemStack stack) {
		ItemStack copy = null;
		if (stack != null) {
			copy = stack.copy();
			copy.stackSize = 1;
		}
		this.inventory.setInventorySlotContents(this.getSlotIndex(), copy);
		this.inventory.markDirty();

	}

	public int getSlotStackLimit() {
		return 1;
	}
}
