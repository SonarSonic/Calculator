package sonar.calculator.mod.common.item.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import sonar.calculator.mod.common.recipes.TreeHarvestRecipes;
import sonar.core.common.item.SonarItem;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class Sickle extends SonarItem {

	@Nonnull
    @Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ArrayList<ItemStack> stacks = TreeHarvestRecipes.harvestLeaves(world, pos, false);
		stacks.forEach(s -> ItemHandlerHelper.giveItemToPlayer(player,s));
		return EnumActionResult.PASS;
	}
}