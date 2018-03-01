package sonar.calculator.mod.common.item.misc;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.core.api.SonarAPI;
import sonar.core.api.energy.ISonarEnergyHandler;
import sonar.core.common.item.SonarItemScreen;

public class CalculatorScreen extends SonarItemScreen {

	@Override
	public Block getScreenBlock() {
		return Calculator.calculatorScreen;
	}

	@Override
	public boolean canPlaceScreenOn(World world, IBlockState state, BlockPos pos, EnumFacing screenFacing) {
		TileEntity tile = world.getTileEntity(pos);
		ISonarEnergyHandler handler = tile == null ? null : SonarAPI.getEnergyHelper().canTransferEnergy(tile, screenFacing);
		return handler != null;
	}
}
