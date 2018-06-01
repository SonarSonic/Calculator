package sonar.calculator.mod.common.item.misc;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.core.common.item.SonarItemScreen;
import sonar.core.handlers.energy.EnergyTransferHandler;

public class CalculatorScreen extends SonarItemScreen {

	@Override
	public Block getScreenBlock() {
		return Calculator.calculatorScreen;
	}

	@Override
	public boolean canPlaceScreenOn(World world, IBlockState state, BlockPos pos, EnumFacing screenFacing) {
		return EnergyTransferHandler.INSTANCE_SC.canRead(world.getTileEntity(pos), screenFacing);
	}
}
