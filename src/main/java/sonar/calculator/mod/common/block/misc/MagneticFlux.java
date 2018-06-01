package sonar.calculator.mod.common.block.misc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityMagneticFlux;
import sonar.core.common.block.SonarBlockContainer;
import sonar.core.common.block.SonarMaterials;
import sonar.core.network.FlexibleGuiHandler;

import javax.annotation.Nonnull;

public class MagneticFlux extends SonarBlockContainer {

	public MagneticFlux() {
		super(SonarMaterials.machine, false);
		this.hasSpecialRenderer = true;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World world, int i) {
		return new TileEntityMagneticFlux();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player != null && !world.isRemote) {
			FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
		}
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(World world, @Nonnull BlockPos pos) {
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
