package sonar.calculator.mod;

import sonar.calculator.mod.common.recipes.*;
import sonar.core.recipes.RecipeHelperV2;

import java.util.HashMap;
import java.util.Map.Entry;

public class Recipes {

    public static HashMap<Types, RecipeHelperV2> recipes = new HashMap<>();

	public enum Types {
        ALGORITHM, ANALYSING, ATOMIC, CALCULATOR, CONDUCTOR_MAST, EXTRACTION, FABRICATION, FLAWLESS, GLOWSTONE, HEALTH, PRECISION, PROCESSING, REASSEMBLY, REDSTONE, RESTORATION, SCIENTIFIC, STARCH, STONE, HARVEST
	}
	
	public static void registerRecipes() {
		recipes.put(Types.ALGORITHM, AlgorithmSeparatorRecipes.instance());
		recipes.put(Types.ANALYSING, AnalysingChamberRecipes.instance());
		recipes.put(Types.ATOMIC, AtomicCalculatorRecipes.instance());
		recipes.put(Types.CALCULATOR, CalculatorRecipes.instance());
		recipes.put(Types.CONDUCTOR_MAST, ConductorMastRecipes.instance());
		recipes.put(Types.EXTRACTION, ExtractionChamberRecipes.instance());
		recipes.put(Types.FABRICATION, FabricationChamberRecipes.instance());
		recipes.put(Types.FLAWLESS, FlawlessCalculatorRecipes.instance());
		recipes.put(Types.GLOWSTONE, GlowstoneExtractorRecipes.instance());
		recipes.put(Types.HEALTH, HealthProcessorRecipes.instance());
		recipes.put(Types.PRECISION, PrecisionChamberRecipes.instance());
		recipes.put(Types.PROCESSING, ProcessingChamberRecipes.instance());
		recipes.put(Types.REASSEMBLY, ReassemblyChamberRecipes.instance());
		recipes.put(Types.REDSTONE, RedstoneExtractorRecipes.instance());
		recipes.put(Types.RESTORATION, RestorationChamberRecipes.instance());
		recipes.put(Types.SCIENTIFIC, ScientificRecipes.instance());
		recipes.put(Types.STARCH, StarchExtractorRecipes.instance());
		recipes.put(Types.STONE, StoneSeparatorRecipes.instance());
		recipes.put(Types.HARVEST, TreeHarvestRecipes.instance());
	}

	public static void printRecipeInfo() {
		for (Entry<Types, RecipeHelperV2> entry : recipes.entrySet()) {
			int loaded = entry.getValue().getRecipes().size();
			Calculator.logger.info(entry.getValue().getRecipeID() + ": Loaded " + loaded + " Recipes");
		}
	}

	public static RecipeHelperV2 getRecipeHelper(Types type) {
		return recipes.get(type);
	}
}
