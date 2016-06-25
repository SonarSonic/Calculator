package sonar.calculator.mod.common.block.misc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityMagneticFlux;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.IGuiTile;

public class MagneticFlux extends SonarMachineBlock {

	public MagneticFlux() {
		super(SonarMaterials.machine, false, true);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
	}

	public int getRenderType() {
		return 2;
	}
	
	public boolean hasSpecialRenderer() {
		return true;
	}

	public boolean isFullCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityMagneticFlux();
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null && !world.isRemote) {
			player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		super.canPlaceBlockAt(world, pos);
		for (int Y = Math.max(pos.getY() - 4, 0); Y < Math.min(256, pos.getY() + 4); Y++) {
			for (int X = pos.getX() - 4; X <= pos.getX() + 4; X++) {
				for (int Z = pos.getY() - 4; Z <= pos.getY() + 4; Z++) {
					if (world.getBlockState(new BlockPos(X, Y, Z)).getBlock() == Calculator.magneticFlux) {
						return false;
					}
				}
			}
		}
		return true;
	}

}
