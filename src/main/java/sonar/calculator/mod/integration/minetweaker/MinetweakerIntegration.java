package sonar.calculator.mod.integration.minetweaker;

import com.google.common.collect.Lists;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import sonar.calculator.mod.common.recipes.*;
import sonar.core.integration.minetweaker.SonarAddRecipeV2;
import sonar.core.integration.minetweaker.SonarRemoveRecipeV2;
import sonar.core.recipes.RecipeObjectType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;

public class MineTweakerIntegration {
    /*
    If there are issues with recipes not showing up implement the bifunction in
    https://github.com/SonarSonic/Calculator/commit/2ef61a30e84ccef0de0e400c660d58eddf3b69db
    and https://github.com/SonarSonic/Sonar-Core/commit/893e718347d55aaa645f973fbcb199fc88c13533
     */

	public static void init() {
        CraftTweakerAPI.registerClass(CalculatorHandler.class);
        CraftTweakerAPI.registerClass(AtomicHandler.class);
        CraftTweakerAPI.registerClass(ScientificHandler.class);
        CraftTweakerAPI.registerClass(FlawlessHandler.class);
        CraftTweakerAPI.registerClass(ConductorMastHandler.class);
        CraftTweakerAPI.registerClass(StoneSeparatorHandler.class);
        CraftTweakerAPI.registerClass(AlgorithmSeparatorHandler.class);
        CraftTweakerAPI.registerClass(ExtractionChamberHandler.class);
        CraftTweakerAPI.registerClass(RestorationChamberHandler.class);
        CraftTweakerAPI.registerClass(ReassemblyChamberHandler.class);
        CraftTweakerAPI.registerClass(PrecisionChamberHandler.class);
        CraftTweakerAPI.registerClass(ProcessingChamberHandler.class);
        CraftTweakerAPI.registerClass(FabricationChamberHandler.class);
        CraftTweakerAPI.registerClass(HealthProcessorHandler.class);
        CraftTweakerAPI.registerClass(StarchExtractorHandler.class);
        CraftTweakerAPI.registerClass(RedstoneExtractorHandler.class);
        CraftTweakerAPI.registerClass(GlowstoneExtractorHandler.class);
	}

	@ZenClass("mods.calculator.basic")
    @ZenRegister
	public static class CalculatorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input1, IIngredient input2, IItemStack output) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2(CalculatorRecipes.instance(), Lists.newArrayList(input1, input2), Lists.newArrayList(CraftTweakerMC.getItemStack(output))));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(CalculatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.scientific")
    @ZenRegister
	public static class ScientificHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input1, IIngredient input2, IItemStack output) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2(ScientificRecipes.instance(), Lists.newArrayList(input1, input2), Lists.newArrayList(CraftTweakerMC.getItemStack(output))));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(ScientificRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.atomic")
    @ZenRegister
	public static class AtomicHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input1, IIngredient input2, IIngredient input3, IItemStack output) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2(AtomicCalculatorRecipes.instance(), Lists.newArrayList(input1, input2, input3), Lists.newArrayList(CraftTweakerMC.getItemStack(output))));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(AtomicCalculatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.flawless")
    @ZenRegister
	public static class FlawlessHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input1, IIngredient input2, IIngredient input3, IIngredient input4, IItemStack output) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2(FlawlessCalculatorRecipes.instance(), Lists.newArrayList(input1, input2, input3, input4), Lists.newArrayList(CraftTweakerMC.getItemStack(output))));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(FlawlessCalculatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.conductorMast")
    @ZenRegister
	public static class ConductorMastHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int powercost, IItemStack output) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2.Value(ConductorMastRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(CraftTweakerMC.getItemStack(output)), powercost));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(ConductorMastRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.stoneSeparator")
    @ZenRegister
	public static class StoneSeparatorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1, IItemStack output2) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2(StoneSeparatorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(CraftTweakerMC.getItemStack(output1), CraftTweakerMC.getItemStack(output2))));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(StoneSeparatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.algorithmSeparator")
    @ZenRegister
	public static class AlgorithmSeparatorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1, IItemStack output2) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2(AlgorithmSeparatorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(CraftTweakerMC.getItemStack(output1), CraftTweakerMC.getItemStack(output2))));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output1, IIngredient output2) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(AlgorithmSeparatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output1, output2)));
		}
	}

	@ZenClass("mods.calculator.extractionChamber")
    @ZenRegister
	public static class ExtractionChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1, IItemStack output2) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2(ExtractionChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(CraftTweakerMC.getItemStack(output1), CraftTweakerMC.getItemStack(output2))));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(ExtractionChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.restorationChamber")
    @ZenRegister
	public static class RestorationChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2(RestorationChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(CraftTweakerMC.getItemStack(output1))));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(RestorationChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.reassemblyChamber")
    @ZenRegister
	public static class ReassemblyChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2(ReassemblyChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(CraftTweakerMC.getItemStack(output1))));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(ReassemblyChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.precisionChamber")
    @ZenRegister
	public static class PrecisionChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1, IItemStack output2) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2(PrecisionChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(CraftTweakerMC.getItemStack(output1), CraftTweakerMC.getItemStack(output2))));
		}

		@ZenMethod
		public static void removeRecipe(IItemStack output1, IItemStack output2) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(PrecisionChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output1, output2)));
		}
	}

	@ZenClass("mods.calculator.processingChamber")
    @ZenRegister
	public static class ProcessingChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2(ProcessingChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(CraftTweakerMC.getItemStack(output1))));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(ProcessingChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output)));
		}
	}

	@ZenClass("mods.calculator.fabricationChamber")
    @ZenRegister
	public static class FabricationChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2(FabricationChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(CraftTweakerMC.getItemStack(output1))));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(FabricationChamberRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input)));
		}
	}

	@ZenClass("mods.calculator.health")
    @ZenRegister
	public static class HealthProcessorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int value) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2.Value(HealthProcessorRecipes.instance(), Lists.newArrayList(input), new ArrayList<>(), value));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(HealthProcessorRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input)));
		}
	}

	@ZenClass("mods.calculator.starch")
    @ZenRegister
	public static class StarchExtractorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int value) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2.Value(StarchExtractorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(), value));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(StarchExtractorRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input)));
		}
	}

	@ZenClass("mods.calculator.redstone")
    @ZenRegister
	public static class RedstoneExtractorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int value) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2.Value(RedstoneExtractorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(), value));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(RedstoneExtractorRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input)));
		}
	}

	@ZenClass("mods.calculator.glowstone")
    @ZenRegister
	public static class GlowstoneExtractorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int value) {
            CraftTweakerAPI.apply(new SonarAddRecipeV2.Value(GlowstoneExtractorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(), value));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
            CraftTweakerAPI.apply(new SonarRemoveRecipeV2(GlowstoneExtractorRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input)));
		}
	}
}