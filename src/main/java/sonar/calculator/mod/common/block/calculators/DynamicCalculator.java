package sonar.calculator.mod.common.block.calculators;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.core.common.block.SonarBlockContainer;
import sonar.core.common.block.SonarMaterials;
import sonar.core.network.FlexibleGuiHandler;

import javax.annotation.Nonnull;

public class DynamicCalculator extends SonarBlockContainer {
	public DynamicCalculator() {
		super(SonarMaterials.machine, true);
	}

    @Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World world, int i) {
		return new TileEntityCalculator.Dynamic();
	}
}
