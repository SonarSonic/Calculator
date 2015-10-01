package sonar.calculator.mod.common.item.misc;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculatorScreen;
import sonar.core.common.item.SonarItem;
import sonar.core.common.item.SonarItemScreen;

public class CalculatorScreen extends SonarItemScreen {

	@Override
	public Block getScreenBlock() {
		return Calculator.calculatorScreen;
	}
}
