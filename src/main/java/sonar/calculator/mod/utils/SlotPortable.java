package sonar.calculator.mod.utils;

import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.core.common.item.InventoryItem;

public class SlotPortable extends Slot {
	public boolean isRemote;
	public InventoryItem invItem;
	private Item type;

	public SlotPortable(InventoryItem inv, int index, int x, int y, boolean isRemote, Item type) {
		super(inv, index, x, y);
		this.isRemote = isRemote;
		this.invItem = inv;
	}

	public boolean isItemValid(ItemStack stack) {
		if (type == null || stack == null) {
			return super.isItemValid(stack);
		}
		return stack.getItem() != type;
	}

	public void putStack(ItemStack stack) {
		invItem.setInventorySlotContents(this.slotNumber, stack, isRemote);
	}

	public void onSlotChanged() {
		if (!this.isRemote)
			this.inventory.markDirty();
	}
}
