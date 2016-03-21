package sonar.calculator.mod.common.item.modules;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.item.calculators.SonarCalculator;

public class EnergyModule extends SonarCalculator {
	
	public EnergyModule() {
		maxStackSize=1;	
		capacity = CalculatorConfig.getInteger("Energy Module");
		maxReceive = CalculatorConfig.getInteger("Energy Module");
		maxExtract = CalculatorConfig.getInteger("Energy Module");
		maxTransfer = CalculatorConfig.getInteger("Energy Module");
	}
}
