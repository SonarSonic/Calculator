package sonar.calculator.mod.common.item.misc;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.common.entities.EntitySmallStone;
import sonar.core.common.item.SonarItem;

public class SmallStone extends SonarItem {
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if (!player.capabilities.isCreativeMode) {
			stack.stackSize -= 1;
		}
		world.playSound(player, player.getPosition(), SoundEvent.REGISTRY.getObject(new ResourceLocation("random.bow")), SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote) {
			world.spawnEntityInWorld(new EntitySmallStone(world, player));
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);		

	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
			if (player.isSneaking()) {
			if (!player.canPlayerEdit(pos, side, stack)) {
				return EnumActionResult.PASS;
			}
			Block block = world.getBlockState(pos).getBlock();

			if (block == Blocks.DIRT) {
				world.setBlockState(pos, Blocks.GRAVEL.getDefaultState());
				stack.stackSize -= 1;
			}
			if (block == Blocks.GRASS) {
				world.setBlockState(pos, Blocks.GRAVEL.getDefaultState());
				stack.stackSize -= 1;
			} else {
				return EnumActionResult.PASS;
			}
		}
		return EnumActionResult.SUCCESS;
	}
}
