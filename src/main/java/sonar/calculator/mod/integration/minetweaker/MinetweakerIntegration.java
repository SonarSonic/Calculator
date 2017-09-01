package sonar.calculator.mod.integration.minetweaker;

import java.util.function.BiFunction;

import com.google.common.collect.Lists;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraftforge.fml.common.Loader;
import sonar.calculator.mod.common.recipes.AlgorithmSeparatorRecipes;
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
import sonar.calculator.mod.integration.jei.CalculatorJEI;
import sonar.calculator.mod.integration.jei.CalculatorJEI.Handlers;
import sonar.core.integration.SonarLoader;
import sonar.core.integration.minetweaker.SonarAddRecipeV2;
import sonar.core.integration.minetweaker.SonarRemoveRecipeV2;
import sonar.core.recipes.ISonarRecipe;
import sonar.core.recipes.RecipeHelperV2;
import sonar.core.recipes.RecipeObjectType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

public class MineTweakerIntegration {

	public static void init() {
		MineTweakerAPI.registerClass(CalculatorHandler.class);
		MineTweakerAPI.registerClass(AtomicHandler.class);
		MineTweakerAPI.registerClass(ScientificHandler.class);
		MineTweakerAPI.registerClass(FlawlessHandler.class);
		MineTweakerAPI.registerClass(ConductorMastHandler.class);
		MineTweakerAPI.registerClass(StoneSeparatorHandler.class);
		MineTweakerAPI.registerClass(AlgorithmSeparatorHandler.class);
		MineTweakerAPI.registerClass(ExtractionChamberHandler.class);
		MineTweakerAPI.registerClass(RestorationChamberHandler.class);
		MineTweakerAPI.registerClass(ReassemblyChamberHandler.class);
		MineTweakerAPI.registerClass(PrecisionChamberHandler.class);
		MineTweakerAPI.registerClass(ProcessingChamberHandler.class);
		MineTweakerAPI.registerClass(FabricationChamberHandler.class);
		MineTweakerAPI.registerClass(HealthProcessorHandler.class);
		MineTweakerAPI.registerClass(StarchExtractorHandler.class);
		MineTweakerAPI.registerClass(RedstoneExtractorHandler.class);
		MineTweakerAPI.registerClass(GlowstoneExtractorHandler.class);
	}

	public static final BiFunction<ISonarRecipe, RecipeHelperV2<ISonarRecipe>, Object> createRecipe = ((a, b) -> createJEIRecipe(a, b));

	public static Object createJEIRecipe(ISonarRecipe recipe, RecipeHelperV2<ISonarRecipe> helper) {
		if (SonarLoader.jeiLoaded()) {
			for (Handlers handler : CalculatorJEI.Handlers.values()) {
				if (handler.helper.getRecipeID().equals(helper.getRecipeID())) {
					try {
						return handler.recipeClass.getConstructor(RecipeHelperV2.class, ISonarRecipe.class).newInstance(handler.helper, recipe);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	@ZenClass("mods.calculator.basic")
	public static class CalculatorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input1, IIngredient input2, IItemStack output) {
			MineTweakerAPI.apply(new SonarAddRecipeV2(CalculatorRecipes.instance(), Lists.newArrayList(input1, input2), Lists.newArrayList(MineTweakerMC.getItemStack(output)), createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(CalculatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output), createRecipe));
		}
	}

	@ZenClass("mods.calculator.scientific")
	public static class ScientificHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input1, IIngredient input2, IItemStack output) {
			MineTweakerAPI.apply(new SonarAddRecipeV2(ScientificRecipes.instance(), Lists.newArrayList(input1, input2), Lists.newArrayList(MineTweakerMC.getItemStack(output)), createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(ScientificRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output), createRecipe));
		}
	}

	@ZenClass("mods.calculator.atomic")
	public static class AtomicHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input1, IIngredient input2, IIngredient input3, IItemStack output) {
			MineTweakerAPI.apply(new SonarAddRecipeV2(AtomicCalculatorRecipes.instance(), Lists.newArrayList(input1, input2, input3), Lists.newArrayList(MineTweakerMC.getItemStack(output)), createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(AtomicCalculatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output), createRecipe));
		}
	}

	@ZenClass("mods.calculator.flawless")
	public static class FlawlessHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input1, IIngredient input2, IIngredient input3, IIngredient input4, IItemStack output) {
			MineTweakerAPI.apply(new SonarAddRecipeV2(FlawlessCalculatorRecipes.instance(), Lists.newArrayList(input1, input2, input3, input4), Lists.newArrayList(MineTweakerMC.getItemStack(output)), createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(FlawlessCalculatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output), createRecipe));
		}
	}

	@ZenClass("mods.calculator.conductorMast")
	public static class ConductorMastHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int powercost, IItemStack output) {
			MineTweakerAPI.apply(new SonarAddRecipeV2.Value(ConductorMastRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(MineTweakerMC.getItemStack(output)), powercost, createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(ConductorMastRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output), createRecipe));
		}
	}

	@ZenClass("mods.calculator.stoneSeparator")
	public static class StoneSeparatorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1, IItemStack output2) {
			MineTweakerAPI.apply(new SonarAddRecipeV2(StoneSeparatorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(MineTweakerMC.getItemStack(output1), MineTweakerMC.getItemStack(output2)), createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(StoneSeparatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output), createRecipe));
		}
	}

	@ZenClass("mods.calculator.algorithmSeparator")
	public static class AlgorithmSeparatorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1, IItemStack output2) {
			MineTweakerAPI.apply(new SonarAddRecipeV2(AlgorithmSeparatorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(MineTweakerMC.getItemStack(output1), MineTweakerMC.getItemStack(output2)), createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output1, IIngredient output2) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(AlgorithmSeparatorRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output1, output2), createRecipe));
		}
	}

	@ZenClass("mods.calculator.extractionChamber")
	public static class ExtractionChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1, IItemStack output2) {
			MineTweakerAPI.apply(new SonarAddRecipeV2(ExtractionChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(MineTweakerMC.getItemStack(output1), MineTweakerMC.getItemStack(output2)), createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(ExtractionChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output), createRecipe));
		}
	}

	@ZenClass("mods.calculator.restorationChamber")
	public static class RestorationChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1) {
			MineTweakerAPI.apply(new SonarAddRecipeV2(RestorationChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(MineTweakerMC.getItemStack(output1)), createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(RestorationChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output), createRecipe));
		}
	}

	@ZenClass("mods.calculator.reassemblyChamber")
	public static class ReassemblyChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1) {
			MineTweakerAPI.apply(new SonarAddRecipeV2(ReassemblyChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(MineTweakerMC.getItemStack(output1)), createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(ReassemblyChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output), createRecipe));
		}
	}

	@ZenClass("mods.calculator.precisionChamber")
	public static class PrecisionChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1, IItemStack output2) {
			MineTweakerAPI.apply(new SonarAddRecipeV2(PrecisionChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(MineTweakerMC.getItemStack(output1), MineTweakerMC.getItemStack(output2)), createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IItemStack output1, IItemStack output2) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(PrecisionChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output1, output2), createRecipe));
		}
	}

	@ZenClass("mods.calculator.processingChamber")
	public static class ProcessingChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1) {
			MineTweakerAPI.apply(new SonarAddRecipeV2(ProcessingChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(MineTweakerMC.getItemStack(output1)), createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient output) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(ProcessingChamberRecipes.instance(), RecipeObjectType.OUTPUT, Lists.newArrayList(output), createRecipe));
		}
	}

	@ZenClass("mods.calculator.fabricationChamber")
	public static class FabricationChamberHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, IItemStack output1) {
			MineTweakerAPI.apply(new SonarAddRecipeV2(FabricationChamberRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(MineTweakerMC.getItemStack(output1)), createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(FabricationChamberRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input), createRecipe));
		}
	}

	@ZenClass("mods.calculator.health")
	public static class HealthProcessorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int value) {
			MineTweakerAPI.apply(new SonarAddRecipeV2.Value(HealthProcessorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(), value, createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(HealthProcessorRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input), createRecipe));
		}
	}

	@ZenClass("mods.calculator.starch")
	public static class StarchExtractorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int value) {
			MineTweakerAPI.apply(new SonarAddRecipeV2.Value(StarchExtractorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(), value, createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(StarchExtractorRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input), createRecipe));
		}
	}

	@ZenClass("mods.calculator.redstone")
	public static class RedstoneExtractorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int value) {
			MineTweakerAPI.apply(new SonarAddRecipeV2.Value(RedstoneExtractorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(), value, createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(RedstoneExtractorRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input), createRecipe));
		}
	}

	@ZenClass("mods.calculator.glowstone")
	public static class GlowstoneExtractorHandler {

		@ZenMethod
		public static void addRecipe(IIngredient input, int value) {
			MineTweakerAPI.apply(new SonarAddRecipeV2.Value(GlowstoneExtractorRecipes.instance(), Lists.newArrayList(input), Lists.newArrayList(), value, createRecipe));
		}

		@ZenMethod
		public static void removeRecipe(IIngredient input) {
			MineTweakerAPI.apply(new SonarRemoveRecipeV2(GlowstoneExtractorRecipes.instance(), RecipeObjectType.INPUT, Lists.newArrayList(input), createRecipe));
		}
	}
}