package sonar.calculator.mod.integration.minetweaker;

import minetweaker.MineTweakerAPI;

/**
 * Created by AEnterprise
 */
public class MinetweakerIntegration {

	public static void integrate() {
		MineTweakerAPI.registerClass(CalculatorHandler.class);
	}
}
