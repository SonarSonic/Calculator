package sonar.calculator.mod.common.item.misc;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sonar.calculator.mod.common.entities.EntitySmallStone;
import sonar.core.common.item.SonarItem;

public class SmallStone extends SonarItem {
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			itemstack.stackSize -= 1;
		}
		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote) {
			world.spawnEntityInWorld(new EntitySmallStone(world, player));
		}
		return itemstack;

	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitx, float hity, float hitz) {
		if (player.isSneaking()) {
			if (!player.canPlayerEdit(pos, side, stack)) {
				return false;
			}
			Block block = world.getBlockState(pos).getBlock();

			if (block == Blocks.dirt) {
				world.setBlockState(pos, Blocks.gravel.getDefaultState());
				stack.stackSize -= 1;
			}
			if (block == Blocks.grass) {
				world.setBlockState(pos, Blocks.gravel.getDefaultState());
				stack.stackSize -= 1;
			} else {
				return false;
			}
		}
		return true;
	}
}
