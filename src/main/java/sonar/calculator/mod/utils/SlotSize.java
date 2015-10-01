package sonar.calculator.mod.utils;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/** for inventories with different stack size to there expected ones */
public class SlotSize extends Slot {

	public int size;

	public SlotSize(IInventory inv, int index, int x, int y, int size) {
		super(inv, index, x, y);
		this.size = size;
	}

	public int getSlotStackLimit() {
		return size;
	}
}
