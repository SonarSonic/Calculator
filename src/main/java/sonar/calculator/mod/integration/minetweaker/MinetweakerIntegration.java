package sonar.calculator.mod.integration.minetweaker;

import minetweaker.MineTweakerAPI;

public class MinetweakerIntegration {

	public static void integrate() {
		MineTweakerAPI.registerClass(CalculatorHandler.class);
		MineTweakerAPI.registerClass(AtomicHandler.class);
		MineTweakerAPI.registerClass(ScientificHandler.class);
		MineTweakerAPI.registerClass(ConductorMastHandler.class);
		MineTweakerAPI.registerClass(StoneSeparatorHandler.class);
		MineTweakerAPI.registerClass(AlgorithmSeparatorHandler.class);
		MineTweakerAPI.registerClass(FlawlessHandler.class);

		MineTweakerAPI.registerClass(ExtractionChamberHandler.class);
		MineTweakerAPI.registerClass(RestorationChamberHandler.class);
		MineTweakerAPI.registerClass(ReassemblyChamberHandler.class);
		MineTweakerAPI.registerClass(PrecisionChamberHandler.class);
		MineTweakerAPI.registerClass(ProcessingChamberHandler.class);
	}
}
