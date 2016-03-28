package sonar.calculator.mod.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;

public class SlotSync extends Slot {
	private EntityPlayer player;
	private TileEntityResearchChamber entity;

	public SlotSync(TileEntityResearchChamber entity, int index, int x, int y, EntityPlayer player) {
		super(entity, index, x, y);
		this.player = player;
		this.entity = entity;
	}

	public void onSlotChange(ItemStack stack1, ItemStack stack2) {
		super.onSlotChange(stack1, stack2);
		entity.syncResearch();
	}

	public void onSlotChanged() {
		super.onSlotChanged();
		entity.syncResearch();
	}

	public boolean isItemValid(ItemStack stack) {
		return stack!=null && stack.getItem() instanceof IResearchStore;
	}

}
