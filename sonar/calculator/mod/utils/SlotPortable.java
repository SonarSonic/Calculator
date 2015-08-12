package sonar.calculator.mod.utils;

import sonar.core.common.item.InventoryItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPortable extends Slot {
	public boolean isRemote;
	public InventoryItem invItem;

	public SlotPortable(InventoryItem inv, int index, int x, int y, boolean isRemote) {
		super(inv, index, x, y);
		this.isRemote = isRemote;
		this.invItem = inv;
	}

	public void putStack(ItemStack stack) {
		invItem.setInventorySlotContents(this.slotNumber, stack, isRemote);
	}

	public void onSlotChanged() {
		if (!this.isRemote)
			this.inventory.markDirty();
	}
}
