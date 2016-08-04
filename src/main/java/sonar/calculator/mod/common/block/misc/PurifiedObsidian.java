package sonar.calculator.mod.common.block.misc;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.api.blocks.IObsidianDrop;
import sonar.core.common.block.ConnectedBlock;

public class PurifiedObsidian extends ConnectedBlock implements IObsidianDrop {

	public PurifiedObsidian(Material material, int target) {
		super(material, target);
	}

	@Override
	public boolean canKeyDrop(World world, BlockPos pos) {
		return true;
	}

	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		return false;
	}

}
