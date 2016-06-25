package sonar.calculator.mod.common.item.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.common.recipes.TreeHarvestRecipes;
import sonar.core.api.SonarAPI;
import sonar.core.common.item.SonarItem;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.ItemStackHelper;

public class Sickle extends SonarItem {

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
	if (!player.canPlayerEdit(pos, side, stack)) {
			return EnumActionResult.PASS;
		}		
		if (!SonarAPI.getItemHelper().isPlayerInventoryFull(player)) {
			ItemStack[] stacks = TreeHarvestRecipes.harvestLeaves(world, pos, false);
			if (stacks != null) {
				for (ItemStack harvest : stacks) {
					player.inventory.addItemStackToInventory(ItemStackHelper.restoreItemStack(harvest, 1));
				}
			}
			return EnumActionResult.SUCCESS;
		} else if (!world.isRemote) {
			FontHelper.sendMessage(FontHelper.translate("inv.full"), world, player);
		}
	
		return EnumActionResult.PASS;
	}

}