package sonar.calculator.mod;

import java.util.HashMap;
import java.util.Map.Entry;

import sonar.calculator.mod.common.recipes.AlgorithmSeparatorRecipes;
import sonar.calculator.mod.common.recipes.AnalysingChamberRecipes;
import sonar.calculator.mod.common.recipes.AtomicCalculatorRecipes;
import sonar.calculator.mod.common.recipes.CalculatorRecipes;
import sonar.calculator.mod.common.recipes.ConductorMastRecipes;
import sonar.calculator.mod.common.recipes.ExtractionChamberRecipes;
import sonar.calculator.mod.common.recipes.FabricationChamberRecipes;
import sonar.calculator.mod.common.recipes.FlawlessCalculatorRecipes;
import sonar.calculator.mod.common.recipes.GlowstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.HealthProcessorRecipes;
import sonar.calculator.mod.common.recipes.PrecisionChamberRecipes;
import sonar.calculator.mod.common.recipes.ProcessingChamberRecipes;
import sonar.calculator.mod.common.recipes.ReassemblyChamberRecipes;
import sonar.calculator.mod.common.recipes.RedstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.RestorationChamberRecipes;
import sonar.calculator.mod.common.recipes.ScientificRecipes;
import sonar.calculator.mod.common.recipes.StarchExtractorRecipes;
import sonar.calculator.mod.common.recipes.StoneSeparatorRecipes;
import sonar.calculator.mod.common.recipes.TreeHarvestRecipes;
import sonar.core.recipes.RecipeHelperV2;

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
