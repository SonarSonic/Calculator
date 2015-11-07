package sonar.calculator.mod.integration.minetweaker;

import minetweaker.MineTweakerAPI;
import sonar.calculator.mod.common.recipes.machines.AlgorithmSeparatorRecipes;

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
