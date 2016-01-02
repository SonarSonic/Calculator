package sonar.calculator.mod.integration.minetweaker;

import minetweaker.MineTweakerAPI;

/**
 * Created by AEnterprise
 */
public class MinetweakerIntegration {

	public static void integrate() {
		MineTweakerAPI.registerClass(CalculatorHandler.class);
		MineTweakerAPI.registerClass(AtomicHandler.class);
		MineTweakerAPI.registerClass(ScientificHandler.class);
		MineTweakerAPI.registerClass(ConductorMastHandler.class);
		MineTweakerAPI.registerClass(StoneSeparatorHandler.class);
		MineTweakerAPI.registerClass(AlgorithmSeparatorHandler.class);
		MineTweakerAPI.registerClass(FlawlessHandler.class);
	}
}
