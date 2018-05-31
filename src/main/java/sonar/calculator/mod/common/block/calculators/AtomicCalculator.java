package sonar.calculator.mod.common.block.calculators;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.network.FlexibleGuiHandler;

import javax.annotation.Nonnull;

public class AtomicCalculator extends SonarMachineBlock {

	public AtomicCalculator() {
		super(SonarMaterials.machine, true, true);
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World world, int i) {
		return new TileEntityCalculator.Atomic();
	}

	@Override
	public boolean dropStandard(IBlockAccess world, BlockPos pos) {
		return true;
	}
}
