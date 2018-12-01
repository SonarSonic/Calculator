package sonar.calculator.mod.integration.crafttweaker;

import com.google.common.collect.Lists;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import sonar.calculator.mod.common.recipes.*;
import sonar.core.integration.crafttweaker.SonarAddRecipe;
import sonar.core.integration.crafttweaker.SonarRemoveRecipe;
import sonar.core.recipes.RecipeObjectType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;

public class CraftTweakerIntegration {

	@ZenClass("mods.calculator.basic")
    @ZenRegister
	public static class CalculatorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input1, IIngredient input2, IIngredient output) {
            CraftTweakerAPI.apply(new SonarAddRecipe(CalculatorRecipes.instance(), Lists.newArrayList(input1, input2), Lists.newArrayList(output)));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(CalculatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.scientific")
    @ZenRegister
	public static class ScientificHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input1, IIngredient input2, IIngredient output) {
            CraftTweakerAPI.apply(new SonarAddRecipe(ScientificRecipes.instance(), Lists.newArrayList(input1, input2), Lists.newArrayList(output)));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(ScientificRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.atomic")
    @ZenRegister
	public static class AtomicHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input1, IIngredient input2, IIngredient input3, IIngredient output) {
            CraftTweakerAPI.apply(new SonarAddRecipe(AtomicCalculatorRecipes.instance(), Lists.newArrayList(input1, input2, input3), Lists.newArrayList(output)));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(AtomicCalculatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.flawless")
    @ZenRegister
	public static class FlawlessHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input1, IIngredient input2, IIngredient input3, IIngredient input4, IIngredient output) {
            CraftTweakerAPI.apply(new SonarAddRecipe(FlawlessCalculatorRecipes.instance(), Lists.newArrayList(input1, input2, input3, input4), Lists.newArrayList(output)));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(FlawlessCalculatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.conductorMast")
    @ZenRegister
	public static class ConductorMastHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int powercost, IIngredient output) {
            CraftTweakerAPI.apply(new SonarAddRecipe.Value(ConductorMastRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(output), powercost));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(ConductorMastRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.stoneSeparator")
    @ZenRegister
	public static class StoneSeparatorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IIngredient output1, IIngredient output2) {
            CraftTweakerAPI.apply(new SonarAddRecipe(StoneSeparatorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(output1, output2)));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output, IIngredient output2) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(StoneSeparatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output, output2)));
		}
	}

	@ZenClass("mods.calculator.algorithmSeparator")
    @ZenRegister
	public static class AlgorithmSeparatorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IIngredient output1, IIngredient output2) {
            CraftTweakerAPI.apply(new SonarAddRecipe(AlgorithmSeparatorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(output1, output2)));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output1, IIngredient output2) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(AlgorithmSeparatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output1, output2)));
		}
	}

	@ZenClass("mods.calculator.extractionChamber")
    @ZenRegister
	public static class ExtractionChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IIngredient output1, IIngredient output2) {
            CraftTweakerAPI.apply(new SonarAddRecipe(ExtractionChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(output1, output2)));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output, IIngredient output2) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(ExtractionChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output, output2)));
		}
	}

	@ZenClass("mods.calculator.restorationChamber")
    @ZenRegister
	public static class RestorationChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IIngredient output1) {
            CraftTweakerAPI.apply(new SonarAddRecipe(RestorationChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(RestorationChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.reassemblyChamber")
    @ZenRegister
	public static class ReassemblyChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IIngredient output1) {
            CraftTweakerAPI.apply(new SonarAddRecipe(ReassemblyChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(ReassemblyChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.precisionChamber")
    @ZenRegister
	public static class PrecisionChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IIngredient output1, IIngredient output2) {
            CraftTweakerAPI.apply(new SonarAddRecipe(PrecisionChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(output1, output2)));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output1, IIngredient output2) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(PrecisionChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output1, output2)));
		}
	}

	@ZenClass("mods.calculator.processingChamber")
    @ZenRegister
	public static class ProcessingChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IIngredient output1) {
            CraftTweakerAPI.apply(new SonarAddRecipe(ProcessingChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(ProcessingChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.fabricationChamber")
    @ZenRegister
	public static class FabricationChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IIngredient output1) {
            CraftTweakerAPI.apply(new SonarAddRecipe(FabricationChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(FabricationChamberRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input)));
		}
	}

	@ZenClass("mods.calculator.health")
    @ZenRegister
	public static class HealthProcessorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int value) {
            CraftTweakerAPI.apply(new SonarAddRecipe.Value(HealthProcessorRecipes.instance(), Lists.newArrayList(input), new ArrayList<>(), value));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(HealthProcessorRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input)));
		}
	}

	@ZenClass("mods.calculator.starch")
    @ZenRegister
	public static class StarchExtractorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int value) {
            CraftTweakerAPI.apply(new SonarAddRecipe.Value(StarchExtractorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(), value));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(StarchExtractorRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input)));
		}
	}

	@ZenClass("mods.calculator.redstone")
    @ZenRegister
	public static class RedstoneExtractorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int value) {
            CraftTweakerAPI.apply(new SonarAddRecipe.Value(RedstoneExtractorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(), value));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(RedstoneExtractorRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input)));
		}
	}

	@ZenClass("mods.calculator.glowstone")
    @ZenRegister
	public static class GlowstoneExtractorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int value) {
            CraftTweakerAPI.apply(new SonarAddRecipe.Value(GlowstoneExtractorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(), value));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
            CraftTweakerAPI.apply(new SonarRemoveRecipe(GlowstoneExtractorRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input)));
		}
	}
}