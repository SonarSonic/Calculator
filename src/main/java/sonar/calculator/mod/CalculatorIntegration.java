package sonar.calculator.mod;

import sonar.core.SonarCore;
import sonar.core.energy.DischargeValues;

public class CalculatorIntegration {

	public static void addSonarCore() {
		SonarCore.machineUpgrades.registerMap(CalculatorItems.UpgradeTypes.ENERGY.name(), Calculator.energyUpgrade);
		SonarCore.machineUpgrades.registerMap(CalculatorItems.UpgradeTypes.SPEED.name(), Calculator.speedUpgrade);
		SonarCore.machineUpgrades.registerMap(CalculatorItems.UpgradeTypes.VOID.name(), Calculator.voidUpgrade);
		SonarCore.machineUpgrades.registerMap(CalculatorItems.UpgradeTypes.TRANSFER.name(), Calculator.transferUpgrade);
		Calculator.logger.info("Registered Upgrades");

		DischargeValues.addValue(Calculator.enriched_coal, 3000);
		DischargeValues.addValue(Calculator.firecoal, 10000);
		DischargeValues.addValue(Calculator.purified_coal, 10000);
		DischargeValues.addValue(Calculator.coal_dust, 250);
		Calculator.logger.info("Added Discharge Values");
	}
}
