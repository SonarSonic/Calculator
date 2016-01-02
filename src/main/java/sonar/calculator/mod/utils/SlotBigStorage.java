package sonar.calculator.mod.utils;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;

public class SlotBigStorage extends Slot {

	public TileEntityStorageChamber tile;
	

	public SlotBigStorage(TileEntityStorageChamber tile, int slot, int x, int y) {
		super(tile, slot, x, y);
		this.tile = tile;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return tile.isItemValid(slotNumber, stack);
	}

	public ItemStack getStack() {
		return tile.getFullStack(this.getSlotIndex());
	}

	public void putStack(ItemStack stack) {
		this.tile.setDisplayContents(this.getSlotIndex(), stack);;
		this.onSlotChanged();
	}
}
