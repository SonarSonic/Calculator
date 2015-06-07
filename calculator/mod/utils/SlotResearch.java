package sonar.calculator.mod.utils;

import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipes;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.calculator.mod.utils.helpers.ResearchPlayer;
import sonar.core.utils.helpers.FontHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

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
			ResearchPlayer research = ResearchPlayer.get(player);
			int[] unblocked = research.unblocked(player);
			if (research.isBlocked(player, entity.lastResearch) && entity.lastResearch != 0) {
				research.unblock(player, CalculatorRecipes.recipes().getRegisteredStack(entity.lastResearch));
				if (CalculatorRecipes.recipes().discovery(player, entity.lastResearch)) {
					FontHelper.sendMessage(StatCollector.translateToLocal("research.recipeNew"), entity.getWorldObj(), player);
				}
			}
			entity.removeResearch();
			entity.getWorldObj().markBlockForUpdate(entity.xCoord, entity.yCoord, entity.zCoord);
			entity.getWorldObj().addBlockEvent(entity.xCoord, entity.yCoord, entity.zCoord, entity.blockType, 1, 0);

		}
	}

	public void onSlotChanged() {
		super.onSlotChanged();
		if (player != null) {
			ResearchPlayer research = ResearchPlayer.get(player);
			int[] unblocked = research.unblocked(player);
			if (research.isBlocked(player, entity.lastResearch) && entity.lastResearch != 0) {
				research.unblock(player, CalculatorRecipes.recipes().getRegisteredStack(entity.lastResearch));
				if (CalculatorRecipes.recipes().discovery(player, entity.lastResearch)) {
					FontHelper.sendMessage(StatCollector.translateToLocal("research.recipeNew"), entity.getWorldObj(), player);
				}
			}
			entity.removeResearch();
			entity.getWorldObj().markBlockForUpdate(entity.xCoord, entity.yCoord, entity.zCoord);
			entity.getWorldObj().addBlockEvent(entity.xCoord, entity.yCoord, entity.zCoord, entity.blockType, 1, 0);

		}
	}

	public boolean isItemValid(ItemStack stack) {
		return CalculatorRecipes.recipes().getID(stack) != 0;
	}

}
