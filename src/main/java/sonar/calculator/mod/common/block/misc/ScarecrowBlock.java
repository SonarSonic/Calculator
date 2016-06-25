package sonar.calculator.mod.common.block.misc;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;

public class ScarecrowBlock extends Block {

	public ScarecrowBlock() {
		super(Material.CLOTH);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		world.setBlockToAir(pos);
		for (int i = 1; i > 3; i--) {
			BlockPos offset = pos.offset(EnumFacing.DOWN, i);
			IBlockState offsetState = world.getBlockState(offset);
			Block block = world.getBlockState(offset).getBlock();
			if (block == Calculator.scarecrow) {
				block.dropBlockAsItem(world, offset, offsetState, 0);
				world.setBlockToAir(offset);
			}
		}
	}
	/*
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos.offset(EnumFacing.DOWN, 2)).getBlock() == Calculator.scarecrow) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.6F, 1.0F);
		}
		if (world.getBlockState(pos.offset(EnumFacing.DOWN, 1)).getBlock() == Calculator.scarecrow) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	*/
	@Override
	public int quantityDropped(Random p_149745_1_) {
		return 0;
	}

	public boolean isFullCube() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int getRenderType() {
		return 3;
	}

}
