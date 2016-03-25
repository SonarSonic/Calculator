package sonar.calculator.mod.integration.planting.vanilla;

import java.util.Random;

import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.integration.planting.IFertiliser;

public class Fertiliser implements IFertiliser {

	@Override
	public boolean isLoadable() {
		return true;
	}

	@Override
	public String getName() {
		return "Bonemeal";
	}

	@Override
	public boolean canFertilise(World world, BlockPos pos, IBlockState state) {
		if (state.getBlock() instanceof IGrowable) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
		return ((IGrowable) state.getBlock()).canGrow(world, pos, state, isClient);
	}

	@Override
	public boolean canUseFertiliser(ItemStack fertiliser, World world, Random rand, BlockPos pos, IBlockState state) {
		return fertiliser.getItem() == Items.dye && fertiliser.getItemDamage() ==EnumDyeColor.WHITE.getDyeDamage() && ((IGrowable) state.getBlock()).canUseBonemeal(world, rand, pos, state);
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		((IGrowable) state.getBlock()).grow(world, rand, pos, state);
	}

}
