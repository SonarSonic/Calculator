package sonar.calculator.mod.utils;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SlotPortable extends Slot {
	public IInventory invItem;
	private Item type;

	public SlotPortable(IInventory inv, int index, int x, int y, Item type) {
		super(inv, index, x, y);
		this.invItem = inv;
		this.type = type;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if (type == null) {
			return super.isItemValid(stack);
		}
		return !stack.isEmpty() && stack.getItem() != type;
	}

	@Override
	public void putStack(@Nonnull ItemStack stack) {
		invItem.setInventorySlotContents(getSlotIndex(), stack);
	}

	@Override
	public void onSlotChanged() {
		this.inventory.markDirty();
	}
}