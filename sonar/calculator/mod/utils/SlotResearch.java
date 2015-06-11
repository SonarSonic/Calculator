package sonar.calculator.mod.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipes;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.core.utils.helpers.FontHelper;

public class SlotResearch extends Slot {
	private EntityPlayer player;
	private TileEntityResearchChamber entity;

	public SlotResearch(TileEntityResearchChamber entity, int index, int x, int y, EntityPlayer player) {
		super(entity, index, x, y);
		this.player = player;
		this.entity = entity;
	}

	public void onSlotChange(ItemStack stack1, ItemStack stack2) {
		super.onSlotChange(stack1, stack2);
		if (player != null) {
			/*
			int[] unblocked = entity.unblockedList();
			if (entity.isBlocked(entity.lastResearch) && entity.lastResearch != 0) {
				if (CalculatorRecipes.recipes().discovery(unblocked, entity.lastResearch)) {
					FontHelper.sendMessage(StatCollector.translateToLocal("research.recipeNew"), entity.getWorldObj(), player);
				}
			}
			*/
			entity.getWorldObj().markBlockForUpdate(entity.xCoord, entity.yCoord, entity.zCoord);
			entity.getWorldObj().addBlockEvent(entity.xCoord, entity.yCoord, entity.zCoord, entity.blockType, 1, 0);

		}
	}

	public void onSlotChanged() {
		super.onSlotChanged();
		if (player != null) {
			/*
			int[] unblocked = entity.unblockedList();
			if (entity.isBlocked(entity.lastResearch) && entity.lastResearch != 0) {
				if (CalculatorRecipes.recipes().discovery(unblocked, entity.lastResearch)) {
					FontHelper.sendMessage(StatCollector.translateToLocal("research.recipeNew"), entity.getWorldObj(), player);
				}
			}
			*/
			entity.getWorldObj().markBlockForUpdate(entity.xCoord, entity.yCoord, entity.zCoord);
			entity.getWorldObj().addBlockEvent(entity.xCoord, entity.yCoord, entity.zCoord, entity.blockType, 1, 0);

		}
	}

	public boolean isItemValid(ItemStack stack) {
		return CalculatorRecipes.recipes().getID(stack) != 0;
	}

}
