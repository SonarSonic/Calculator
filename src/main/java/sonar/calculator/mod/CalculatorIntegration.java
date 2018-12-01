package sonar.calculator.mod;

import sonar.core.handlers.energy.DischargeValues;
import sonar.core.upgrades.MachineUpgradeRegistry;

public class CalculatorIntegration {

	public static void addSonarCore() {
		MachineUpgradeRegistry.instance().register(CalculatorItems.UpgradeTypes.ENERGY.name(), Calculator.energyUpgrade);
		MachineUpgradeRegistry.instance().register(CalculatorItems.UpgradeTypes.SPEED.name(), Calculator.speedUpgrade);
		MachineUpgradeRegistry.instance().register(CalculatorItems.UpgradeTypes.VOID.name(), Calculator.voidUpgrade);
		MachineUpgradeRegistry.instance().register(CalculatorItems.UpgradeTypes.TRANSFER.name(), Calculator.transferUpgrade);
		Calculator.logger.info("Registered Upgrades");

		DischargeValues.instance().register(Calculator.enriched_coal, 3000);
		DischargeValues.instance().register(Calculator.firecoal, 10000);
		DischargeValues.instance().register(Calculator.purified_coal, 10000);
		DischargeValues.instance().register(Calculator.coal_dust, 250);
		Calculator.logger.info("Added Discharge Values");
	}
}
