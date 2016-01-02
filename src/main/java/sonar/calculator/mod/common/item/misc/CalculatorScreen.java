package sonar.calculator.mod.common.item.misc;

import net.minecraft.block.Block;
import sonar.calculator.mod.Calculator;
import sonar.core.common.item.SonarItemScreen;

public class CalculatorScreen extends SonarItemScreen {

	@Override
	public Block getScreenBlock() {
		return Calculator.calculatorScreen;
	}
}
