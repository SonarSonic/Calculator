package sonar.calculator.mod;

import sonar.calculator.mod.integration.core.StorageChamberInventoryProvider;
import sonar.core.SonarCore;
import sonar.core.api.SonarAPI;

public class CalculatorIntegration {

	public static void addSonarCore(){
		SonarCore.machineUpgrades.registerMap(CalculatorItems.UpgradeTypes.ENERGY.name(), Calculator.energyUpgrade);
		SonarCore.machineUpgrades.registerMap(CalculatorItems.UpgradeTypes.SPEED.name(), Calculator.speedUpgrade);
		SonarCore.machineUpgrades.registerMap(CalculatorItems.UpgradeTypes.VOID.name(), Calculator.voidUpgrade);
		SonarCore.machineUpgrades.registerMap(CalculatorItems.UpgradeTypes.TRANSFER.name(), Calculator.transferUpgrade);
		Calculator.logger.info("Registered Upgrades");	
		
		SonarAPI.getRegistry().registerInventoryHandler(new StorageChamberInventoryProvider());
		Calculator.logger.info("Registered Storage Handlers");		
		
	}
}
