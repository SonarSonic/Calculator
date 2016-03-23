package sonar.calculator.mod.common.item.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sonar.calculator.mod.common.recipes.TreeHarvestRecipes;
import sonar.core.common.item.SonarItem;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.InventoryHelper;
import sonar.core.utils.helpers.ItemStackHelper;

public class Sickle extends SonarItem {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitx, float hity, float hitz) {
		if (!player.canPlayerEdit(pos, side, stack)) {
			return false;
		}		
		if (!InventoryHelper.isPlayerInventoryFull(player)) {
			ItemStack[] stacks = TreeHarvestRecipes.harvestLeaves(world, pos, world.getBlockMetadata(pos));
			if (stacks != null) {
				for (ItemStack harvest : stacks) {
					player.inventory.addItemStackToInventory(ItemStackHelper.restoreItemStack(harvest, 1));
				}
			}
			return true;
		} else if (!world.isRemote) {
			FontHelper.sendMessage(FontHelper.translate("inv.full"), world, player);
		}
	
		return false;
	}

}