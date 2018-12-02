package sonar.calculator.mod.integration.jei;

import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConstants;
import sonar.calculator.mod.client.gui.calculators.*;
import sonar.calculator.mod.client.gui.generators.GuiConductorMast;
import sonar.calculator.mod.client.gui.machines.GuiAnalysingChamber;
import sonar.calculator.mod.client.gui.machines.GuiDualOutputSmelting;
import sonar.calculator.mod.client.gui.machines.GuiHealthProcessor;
import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import sonar.calculator.mod.client.gui.misc.GuiFabricationChamber;
import sonar.calculator.mod.common.containers.*;
import sonar.calculator.mod.common.recipes.*;
import sonar.core.integration.jei.JEISonarPlugin;
import sonar.core.integration.jei.JEISonarProvider;

@JEIPlugin
public class JEICalculator extends JEISonarPlugin {

    private JEISonarProvider //
    //SINGLE PROCESS
    PROCESSING, RESTORATION, REASSEMBLY,
    //DUEL PROCESS
	EXTRACTION, PRECISION, STONE, ALGORITHM,
    //CALCULATORS
	CALCULATOR, SCIENTIFIC, ATOMIC, FLAWLESS,
    //MISC
	HEALTH, CONDUCTOR, FABRICATION, SICKLE, ANALYSING;

	public void registerProviders(){
		//SINGLE PROCESS
		PROCESSING = p(ProcessingChamberRecipes.instance(), Calculator.processingChamber, Recipes.Processing.class, Recipes.Processing::new, SingleProcessCategory::new, "restorationchamber", CalculatorConstants.MODID);
		RESTORATION = p(RestorationChamberRecipes.instance(), Calculator.restorationChamber, Recipes.Restoration.class, Recipes.Restoration::new, SingleProcessCategory::new, "restorationchamber", CalculatorConstants.MODID);
		REASSEMBLY = p(ReassemblyChamberRecipes.instance(), Calculator.reassemblyChamber, Recipes.Reassembly.class, Recipes.Reassembly::new, SingleProcessCategory::new, "restorationchamber", CalculatorConstants.MODID);

		//DUEL PROCESS
		EXTRACTION = p(ExtractionChamberRecipes.instance(), Calculator.extractionChamber, Recipes.Extraction.class, Recipes.Extraction::new, DualProcessCategory::new, "extractionchamber", CalculatorConstants.MODID);
		PRECISION = p(PrecisionChamberRecipes.instance(), Calculator.precisionChamber, Recipes.Precision.class, Recipes.Precision::new, DualProcessCategory::new, "extractionchamber", CalculatorConstants.MODID);
		STONE = p(StoneSeparatorRecipes.instance(), Calculator.stoneSeparator, Recipes.Stone.class, Recipes.Stone::new, DualProcessCategory::new, "stoneseperator", CalculatorConstants.MODID);
		ALGORITHM = p(AlgorithmSeparatorRecipes.instance(), Calculator.algorithmSeparator, Recipes.Algorithm.class, Recipes.Algorithm::new, DualProcessCategory::new, "stoneseperator", CalculatorConstants.MODID);

		//CALCULATORS
		CALCULATOR = p(CalculatorRecipes.instance(), Calculator.itemCalculator, Recipes.Calculator.class, Recipes.Calculator::new, DualCalculatorCategory::new, "calculator", CalculatorConstants.MODID);
		SCIENTIFIC = p(ScientificRecipes.instance(), Calculator.itemScientificCalculator, Recipes.Scientific.class, Recipes.Scientific::new, DualCalculatorCategory::new, "scientificcalculator", CalculatorConstants.MODID);
		ATOMIC = p(AtomicCalculatorRecipes.instance(), Calculator.atomicCalculator, Recipes.Atomic.class, Recipes.Atomic::new, AtomicCategory::new, "atomiccalculator", CalculatorConstants.MODID);
		FLAWLESS = p(FlawlessCalculatorRecipes.instance(), Calculator.itemFlawlessCalculator, Recipes.Flawless.class, Recipes.Flawless::new, FlawlessCategory::new, "flawlesscalculator", CalculatorConstants.MODID);

		//MISC
		HEALTH = p(HealthProcessorRecipes.instance(), Calculator.healthProcessor, Recipes.Health.class, Recipes.Health::new, ValueCategory::new, "guicalculatorplug", CalculatorConstants.MODID);
		CONDUCTOR = p(ConductorMastRecipes.instance(), Calculator.conductorMast, Recipes.Conductor.class, Recipes.Conductor::new, ConductorMastCategory::new, "conductorMast", CalculatorConstants.MODID);
		FABRICATION = p(FabricationChamberRecipes.instance(), Calculator.fabricationChamber, Recipes.Fabrication.class, Recipes.Fabrication::new, FabricationCategory::new, "fabrication_chamber_jei", CalculatorConstants.MODID);
		SICKLE = p(TreeHarvestRecipes.instance(), Calculator.sickle, Recipes.Harvest.class, Recipes.Harvest::new, SickleCategory::new, "sickle_harvesting", CalculatorConstants.MODID);
		ANALYSING = p(AnalysingChamberRecipes.instance(), Calculator.analysingChamber, Recipes.Analysing.class, Recipes.Analysing::new, AnalysingCategory::new, "guicalculatorplug", CalculatorConstants.MODID);
	}

    @Override
    public void register(IModRegistry registry) {
    	super.register(registry);

        //// INGREDIENT BLACKLISTS \\\\
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        blacklist.addIngredientToBlacklist(new ItemStack(Calculator.scarecrowBlock));
        blacklist.addIngredientToBlacklist(new ItemStack(Calculator.conductormastBlock));
        blacklist.addIngredientToBlacklist(new ItemStack(Calculator.weatherStationBlock));
        blacklist.addIngredientToBlacklist(new ItemStack(Calculator.transmitterBlock));
        blacklist.addIngredientToBlacklist(new ItemStack(Calculator.calculatorScreen));
        blacklist.addIngredientToBlacklist(new ItemStack(Calculator.gas_lantern_on));
        blacklist.addIngredientToBlacklist(new ItemStack(Calculator.cropBroccoliPlant));
        blacklist.addIngredientToBlacklist(new ItemStack(Calculator.cropPrunaePlant));
        blacklist.addIngredientToBlacklist(new ItemStack(Calculator.cropFiddledewPlant));

        //// ADDITIONAL CATALYSTS \\\\
		registry.addRecipeCatalyst(new ItemStack(Calculator.dynamicCalculator, 1), CALCULATOR.getID(), SCIENTIFIC.getID(), ATOMIC.getID());
		registry.addRecipeCatalyst(new ItemStack(Calculator.itemFlawlessCalculator, 1), VanillaRecipeCategoryUid.CRAFTING, CALCULATOR.getID(), SCIENTIFIC.getID(), ATOMIC.getID());
		registry.addRecipeCatalyst(new ItemStack(Calculator.reinforcedFurnace, 1), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(Calculator.itemCraftingCalculator, 1), VanillaRecipeCategoryUid.CRAFTING);

		//// RECIPE CLICK AREAS \\\\
		registry.addRecipeClickArea(GuiSmeltingBlock.ProcessingChamber.class, 77, 19, 24, 14, PROCESSING.getID());
		registry.addRecipeClickArea(GuiSmeltingBlock.RestorationChamber.class, 77, 19, 24, 14, RESTORATION.getID());
		registry.addRecipeClickArea(GuiSmeltingBlock.ReassemblyChamber.class, 77, 19, 24, 14, REASSEMBLY.getID());
		registry.addRecipeClickArea(GuiSmeltingBlock.ReinforcedFurnace.class, 77, 19, 24, 14, VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeClickArea(GuiDualOutputSmelting.ExtractionChamber.class, 63, 26, 24, 12, EXTRACTION.getID());
		registry.addRecipeClickArea(GuiDualOutputSmelting.PrecisionChamber.class, 63, 26, 24, 12, PRECISION.getID());
		registry.addRecipeClickArea(GuiDualOutputSmelting.StoneSeperator.class, 63, 26, 24, 12, STONE.getID());
		registry.addRecipeClickArea(GuiDualOutputSmelting.AlgorithmSeperator.class, 63, 26, 24, 12, ALGORITHM.getID());
		registry.addRecipeClickArea(GuiHealthProcessor.class, 80, 40, 16, 5, HEALTH.getID());

		registry.addRecipeClickArea(GuiCalculator.class, 108, 40, 14, 6, CALCULATOR.getID());
		registry.addRecipeClickArea(GuiScientificCalculator.class, 108, 40, 14, 6, SCIENTIFIC.getID());
		registry.addRecipeClickArea(GuiAtomicCalculator.class, 109, 40, 10, 6, ATOMIC.getID());
		registry.addRecipeClickArea(GuiFlawlessCalculator.class, 132, 40, 10, 6, FLAWLESS.getID());
		registry.addRecipeClickArea(GuiCraftingCalculator.class, 88, 32, 28, 23, VanillaRecipeCategoryUid.CRAFTING);
		registry.addRecipeClickArea(GuiDynamicCalculator.class, 108, 14, 13, 6, CALCULATOR.getID());
		registry.addRecipeClickArea(GuiDynamicCalculator.class, 108, 40, 13, 6, SCIENTIFIC.getID());
		registry.addRecipeClickArea(GuiDynamicCalculator.class, 108, 66, 13, 6, ATOMIC.getID());
		registry.addRecipeClickArea(GuiDynamicModule.class, 108, 14, 13, 6, CALCULATOR.getID());

		registry.addRecipeClickArea(GuiDynamicModule.class, 108, 40, 13, 6, SCIENTIFIC.getID());
		registry.addRecipeClickArea(GuiDynamicModule.class, 108, 66, 13, 6, ATOMIC.getID());
		registry.addRecipeClickArea(GuiConductorMast.class, 79, 26, 18, 8, CONDUCTOR.getID());
		registry.addRecipeClickArea(GuiFabricationChamber.class, 95, 89, 20, 15, FABRICATION.getID());
		registry.addRecipeClickArea(GuiAnalysingChamber.class, 20, 24, 115, 13, ANALYSING.getID());

        //// RECIPE TRANSFERS \\\\
        IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();

		recipeTransferRegistry.addRecipeTransferHandler(ContainerSmeltingBlock.class, PROCESSING.getID(), 0, 1, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerSmeltingBlock.class, RESTORATION.getID(), 0, 1, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerSmeltingBlock.class, REASSEMBLY.getID(), 0, 1, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerSmeltingBlock.class, VanillaRecipeCategoryUid.SMELTING, 0, 1, 3, 36);

		recipeTransferRegistry.addRecipeTransferHandler(ContainerDualOutputSmelting.class, EXTRACTION.getID(), 0, 1, 4, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDualOutputSmelting.class, PRECISION.getID(), 0, 1, 4, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDualOutputSmelting.class, STONE.getID(), 0, 1, 4, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDualOutputSmelting.class, ALGORITHM.getID(), 0, 1, 4, 36);

		recipeTransferRegistry.addRecipeTransferHandler(ContainerCalculator.class, CALCULATOR.getID(), 0, 2, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerScientificCalculator.class, SCIENTIFIC.getID(), 0, 2, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerAtomicCalculator.class, ATOMIC.getID(), 0, 3, 4, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerFlawlessCalculator.class, FLAWLESS.getID(), 0, 4, 5, 36);

		recipeTransferRegistry.addRecipeTransferHandler(ContainerCraftingCalculator.class, VanillaRecipeCategoryUid.CRAFTING, 0, 9, 10, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDynamicCalculator.class, CALCULATOR.getID(), 1, 2, 10, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDynamicCalculator.class, SCIENTIFIC.getID(), 4, 2, 10, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDynamicCalculator.class, ATOMIC.getID(), 7, 3, 10, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerConductorMast.class, CONDUCTOR.getID(), 0, 1, 2, 36);
    }
}
