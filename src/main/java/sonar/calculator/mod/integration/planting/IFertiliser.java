package sonar.calculator.mod.integration.planting;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sonar.core.api.IRegistryObject;

public interface IFertiliser extends IRegistryObject {

	boolean canFertilise(World world, BlockPos pos, IBlockState state);
	
	boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient);

	boolean canUseFertiliser(ItemStack fertiliser, World world, Random rand, BlockPos pos, IBlockState state);

	void grow(World world, Random rand, BlockPos pos, IBlockState state);
}
