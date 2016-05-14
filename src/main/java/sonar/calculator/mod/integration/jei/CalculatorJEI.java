package sonar.calculator.mod.integration.jei;

import java.util.ArrayList;
import java.util.Map.Entry;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.calculators.GuiAtomicCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiCraftingCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiDynamicCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiDynamicModule;
import sonar.calculator.mod.client.gui.calculators.GuiFlawlessCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiScientificCalculator;
import sonar.calculator.mod.client.gui.machines.GuiDualOutputSmelting;
import sonar.calculator.mod.client.gui.machines.GuiHealthProcessor;
import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import sonar.calculator.mod.common.containers.ContainerAtomicCalculator;
import sonar.calculator.mod.common.containers.ContainerCalculator;
import sonar.calculator.mod.common.containers.ContainerCraftingCalculator;
import sonar.calculator.mod.common.containers.ContainerDualOutputSmelting;
import sonar.calculator.mod.common.containers.ContainerDynamicCalculator;
import sonar.calculator.mod.common.containers.ContainerFlawlessCalculator;
import sonar.calculator.mod.common.containers.ContainerScientificCalculator;
import sonar.calculator.mod.common.containers.ContainerSmeltingBlock;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.common.recipes.machines.AlgorithmSeparatorRecipes;
import sonar.calculator.mod.common.recipes.machines.ExtractionChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.HealthProcessorRecipes;
import sonar.calculator.mod.common.recipes.machines.PrecisionChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.ProcessingChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.ReassemblyChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.RestorationChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.StoneSeparatorRecipes;
import sonar.core.helpers.IRecipeHelper;
import sonar.core.helpers.RecipeHelper;
import sonar.core.helpers.ValueHelper;
import sonar.core.integration.jei.IJEIHandler;
import sonar.core.integration.jei.JEICategory;
import sonar.core.integration.jei.JEIRecipe;

@JEIPlugin
public class CalculatorJEI extends BlankModPlugin {

	@Override
	public void register(IModRegistry registry) {
		Calculator.logger.error("Starting JEI Integration");
		IItemRegistry itemRegistry = registry.getItemRegistry();
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		jeiHelpers.getNbtIgnoreList().ignoreNbtTagNames(Calculator.circuitBoard, "Energy", "Item1", "Item2", "Item3", "Item4", "Item5", "Item6", "Stable");

		for (IJEIHandler handler : Handlers.values()) {
			registry.addRecipes(handler.getJEIRecipes());
			registry.addRecipeCategories(handler.getCategory(guiHelper));
			registry.addRecipeHandlers(handler.getCategory(guiHelper));
			Calculator.logger.error("Registering Recipe Handler: " + handler.getRecipeHelper().getRecipeID());
		}

		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();

		registry.addRecipeClickArea(GuiSmeltingBlock.ProcessingChamber.class, 77, 19, 24, 14, "ProcessingChamber");
		registry.addRecipeClickArea(GuiSmeltingBlock.RestorationChamber.class, 77, 19, 24, 14, "RestorationChamber");
		registry.addRecipeClickArea(GuiSmeltingBlock.ReassemblyChamber.class, 77, 19, 24, 14, "ReassemblyChamber");
		registry.addRecipeClickArea(GuiSmeltingBlock.ReinforcedFurnace.class, 77, 19, 24, 14, VanillaRecipeCategoryUid.SMELTING);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerSmeltingBlock.class, "ProcessingChamber", 0, 1, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerSmeltingBlock.class, "RestorationChamber", 0, 1, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerSmeltingBlock.class, "ReassemblyChamber", 0, 1, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerSmeltingBlock.class, VanillaRecipeCategoryUid.SMELTING, 0, 1, 3, 36);

		registry.addRecipeClickArea(GuiDualOutputSmelting.ExtractionChamber.class, 63, 26, 24, 12, "ExtractionChamber");
		registry.addRecipeClickArea(GuiDualOutputSmelting.PrecisionChamber.class, 63, 26, 24, 12, "PrecisionChamber");
		registry.addRecipeClickArea(GuiDualOutputSmelting.StoneSeperator.class, 63, 26, 24, 12, "StoneSeparator");
		registry.addRecipeClickArea(GuiDualOutputSmelting.AlgorithmSeperator.class, 63, 26, 24, 12, "AlgorithmSeparator");
		registry.addRecipeClickArea(GuiHealthProcessor.class, 80, 40, 16, 5, "Health Values");
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDualOutputSmelting.class, "ExtractionChamber", 0, 1, 4, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDualOutputSmelting.class, "PrecisionChamber", 0, 1, 4, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDualOutputSmelting.class, "StoneSeparator", 0, 1, 4, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDualOutputSmelting.class, "AlgorithmSeparator", 0, 1, 4, 36);

		registry.addRecipeClickArea(GuiCalculator.class, 108, 40, 14, 6, "Calculator");
		registry.addRecipeClickArea(GuiScientificCalculator.class, 108, 40, 14, 6, "Scientific");
		registry.addRecipeClickArea(GuiAtomicCalculator.class, 109, 40, 10, 6, "Atomic");
		registry.addRecipeClickArea(GuiFlawlessCalculator.class, 132, 40, 10, 6, "Flawless");
		registry.addRecipeClickArea(GuiCraftingCalculator.class, 88, 32, 28, 23, VanillaRecipeCategoryUid.CRAFTING);
		registry.addRecipeClickArea(GuiDynamicCalculator.class, 108, 14, 13, 6, "Calculator");
		registry.addRecipeClickArea(GuiDynamicCalculator.class, 108, 40, 13, 6, "Scientific");
		registry.addRecipeClickArea(GuiDynamicCalculator.class, 108, 66, 13, 6, "Atomic");
		registry.addRecipeClickArea(GuiDynamicModule.class, 108, 14, 13, 6, "Calculator");
		registry.addRecipeClickArea(GuiDynamicModule.class, 108, 40, 13, 6, "Scientific");
		registry.addRecipeClickArea(GuiDynamicModule.class, 108, 66, 13, 6, "Atomic");
		recipeTransferRegistry.addRecipeTransferHandler(ContainerCalculator.class, "Calculator", 0, 2, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerScientificCalculator.class, "Scientific", 0, 2, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerAtomicCalculator.class, "Atomic", 0, 3, 4, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerFlawlessCalculator.class, "Flawless", 0, 4, 5, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerCraftingCalculator.class, VanillaRecipeCategoryUid.CRAFTING, 0, 9, 10, 36);

		recipeTransferRegistry.addRecipeTransferHandler(ContainerDynamicCalculator.class, "Calculator", 1, 2, 10, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDynamicCalculator.class, "Scientific", 4, 2, 10, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDynamicCalculator.class, "Atomic", 7, 3, 10, 36);

		Calculator.logger.error("Finished JEI Integration");
	}

	public enum Handlers implements IJEIHandler {
		PROCESSING(ProcessingChamberRecipes.instance(), "tile.ProcessingChamber.name", "restorationchamber", Recipes.Processing.class),
		/**/
		RESTORATION(RestorationChamberRecipes.instance(), "tile.RestorationChamber.name", "restorationchamber", Recipes.Restoration.class),
		/**/
		REASSEMBLY(ReassemblyChamberRecipes.instance(), "tile.ReassemblyChamber.name", "restorationchamber", Recipes.Reassembly.class),
		/**/
		EXTRACTION(ExtractionChamberRecipes.instance(), "tile.ExtractionChamber.name", "extractionchamber", Recipes.Extraction.class),
		/**/
		PRECISION(PrecisionChamberRecipes.instance(), "tile.PrecisionChamber.name", "extractionchamber", Recipes.Precision.class),
		/**/
		STONE(StoneSeparatorRecipes.instance(), "tile.StoneSeparator.name", "stoneseperator", Recipes.Stone.class),
		/**/
		ALGORITHM(AlgorithmSeparatorRecipes.instance(), "tile.AlgorithmSeparator.name", "stoneseperator", Recipes.Algorithm.class),
		/**/
		CALCULATOR(RecipeRegistry.CalculatorRecipes.instance(), "item.Calculator.name", "calculator", Recipes.Calculator.class),
		/**/
		SCIENTIFIC(RecipeRegistry.ScientificRecipes.instance(), "item.ScientificCalculator.name", "scientificcalculator", Recipes.Scientific.class),
		/**/
		ATOMIC(RecipeRegistry.AtomicRecipes.instance(), "tile.AtomicCalculator.name", "atomiccalculator", Recipes.Atomic.class),
		/**/
		FLAWLESS(RecipeRegistry.FlawlessRecipes.instance(), "item.FlawlessCalculator.name", "flawlesscalculator", Recipes.Flawless.class),
		/**/
		HEALTH(HealthProcessorRecipes.instance(), "tile.HealthProcessor.name", "guicalculatorplug", Recipes.Health.class);
		/**/
		//DISCHARGE(null, "Discharge Values", "guipowercube", Recipes.Discharge.class);
		/**/
		public IRecipeHelper helper;
		public String unlocalizedName;
		public String textureName;
		public Class<? extends JEIRecipe> recipeClass;

		Handlers(IRecipeHelper helper, String unlocalizedName, String textureName, Class<? extends JEIRecipe> recipeClass) {
			this.helper = helper;
			this.unlocalizedName = unlocalizedName;
			this.textureName = textureName;
			this.recipeClass = recipeClass;
		}

		@Override
		public JEICategory getCategory(IGuiHelper guiHelper) {
			switch (this) {
			case PROCESSING:
			case RESTORATION:
			case REASSEMBLY:
				return new SingleProcessCategory(guiHelper, this);
			case EXTRACTION:
			case PRECISION:
			case STONE:
			case ALGORITHM:
				return new DualProcessCategory(guiHelper, this);
			case CALCULATOR:
			case SCIENTIFIC:
				return new CalculatorCategory(guiHelper, this);
			case ATOMIC:
				return new AtomicCalculatorCategory(guiHelper, this);
			case FLAWLESS:
				return new FlawlessCalculatorCategory(guiHelper, this);
			case HEALTH:
				return new ValueCategory(guiHelper, this);
			default:
				return null;
			}
		}

		@Override
		public String getTextureName() {
			return textureName;
		}

		@Override
		public String getTitle() {
			return unlocalizedName;
		}

		@Override
		public Class<? extends JEIRecipe> getRecipeClass() {
			return recipeClass;
		}

		@Override
		public IRecipeHelper getRecipeHelper() {
			return helper;
		}

		@Override
		public ArrayList<JEIRecipe> getJEIRecipes() {
			ArrayList<JEIRecipe> recipes = new ArrayList();
			String id = helper.getRecipeID();
			if (helper instanceof RecipeHelper) {
				RecipeHelper helper = (RecipeHelper) this.helper;
				for (Entry<Object[], Object[]> entry : helper.getRecipes().entrySet()) {
					try {
						recipes.add(recipeClass.newInstance().getInstance(id, helper.convertOutput(entry.getKey()), helper.convertOutput(entry.getValue())));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (helper instanceof ValueHelper) {
				ValueHelper helper = (ValueHelper) this.helper;
				for (Entry<Object, Integer> entry : helper.getRecipes().entrySet()) {
					try {
						recipes.add(recipeClass.newInstance().getInstance(id, new Object[] { entry.getKey() }, new Object[] { null }));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return recipes;
		}
	}
}
