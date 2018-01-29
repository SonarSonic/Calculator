package sonar.calculator.mod.common.block.misc;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculatorScreen;
import sonar.core.common.block.SonarMaterials;

import java.util.Random;

public class CalculatorScreen extends BlockWallSign {

	public CalculatorScreen() {
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityCalculatorScreen();
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Calculator.calculator_screen;
	}
}
