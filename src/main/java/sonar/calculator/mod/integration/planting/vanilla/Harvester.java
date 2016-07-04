package sonar.calculator.mod.integration.planting.vanilla;

import java.util.List;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import sonar.calculator.mod.integration.planting.IHarvester;

public class Harvester implements IHarvester {

	@Override
	public boolean isLoadable() {
		return true;
	}

	@Override
	public String getName() {
		return "Harvester";
	}

	@Override
	public boolean canHarvest(World world, BlockPos pos, IBlockState state) {
		if (state.getBlock() instanceof BlockCrops) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isReady(World world, BlockPos pos, IBlockState state) {
		return ((BlockCrops)state.getBlock()).isMaxAge(state);
	}
	
	@Override
	public List<ItemStack> getDrops(World world, BlockPos pos, IBlockState state, int fortune) {
		return world.getBlockState(pos).getBlock().getDrops(world, pos, state, fortune);
	}

}
