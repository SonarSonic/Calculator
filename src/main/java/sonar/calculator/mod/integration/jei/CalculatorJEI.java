package sonar.calculator.mod.integration.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.calculators.*;
import sonar.calculator.mod.client.gui.generators.GuiConductorMast;
import sonar.calculator.mod.client.gui.machines.GuiAnalysingChamber;
import sonar.calculator.mod.client.gui.machines.GuiDualOutputSmelting;
import sonar.calculator.mod.client.gui.machines.GuiDualOutputSmelting.AlgorithmSeperator;
import sonar.calculator.mod.client.gui.machines.GuiHealthProcessor;
import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import sonar.calculator.mod.client.gui.misc.GuiFabricationChamber;
import sonar.calculator.mod.common.containers.*;
import sonar.calculator.mod.common.recipes.*;
import sonar.core.handlers.inventories.ItemStackHelper;
import sonar.core.integration.jei.*;
import sonar.core.recipes.IRecipeHelperV2;
import sonar.core.recipes.ISonarRecipe;
import sonar.core.recipes.RecipeHelperV2;

import java.util.ArrayList;

@JEIPlugin
public class CalculatorJEI extends BlankModPlugin implements ISonarJEIRecipeBuilder {
	{
		JEIHelper.registerRecipeBuilder(this);
	}

	@Override
	public void register(IModRegistry registry) {
		Calculator.logger.info("Starting JEI Integration");
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		//registry.getIngredientRegistry().getJeiHelpers().getNbtIgnoreList().ignoreNbtTagNames(Calculator.circuitBoard, "Energy", "Item1", "Item2", "Item3", "Item4", "Item5", "Item6", "Stable");

		for (IJEIHandler handler : Handlers.values()) {
			registry.addRecipes(handler.getJEIRecipes());
			JEICategoryV2 cat = handler.getCategory(guiHelper);
			registry.addRecipeCategories(cat);
			registry.addRecipeHandlers(cat);
			if (handler.getCrafterItemStack() != null)
				registry.addRecipeCatalyst(handler.getCrafterItemStack(), handler.getUUID());

			Calculator.logger.info("Registering Recipe Handler: " + handler.getUUID());
		}
		//item blacklist
		jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(Calculator.calculatorScreen);
		
		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();

		registry.addRecipeCatalyst(new ItemStack(Calculator.dynamicCalculator, 1), Handlers.CALCULATOR.getUUID(), Handlers.SCIENTIFIC.getUUID(), Handlers.ATOMIC.getUUID());
		registry.addRecipeCatalyst(new ItemStack(Calculator.itemFlawlessCalculator, 1), Handlers.FLAWLESS.getUUID(), Handlers.CALCULATOR.getUUID(), Handlers.SCIENTIFIC.getUUID(), Handlers.ATOMIC.getUUID());
		registry.addRecipeCatalyst(new ItemStack(Calculator.reinforcedFurnace, 1), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(Calculator.itemCraftingCalculator, 1), VanillaRecipeCategoryUid.CRAFTING);

		registry.addRecipeClickArea(GuiSmeltingBlock.ProcessingChamber.class, 77, 19, 24, 14, Handlers.PROCESSING.getUUID());
		registry.addRecipeClickArea(GuiSmeltingBlock.RestorationChamber.class, 77, 19, 24, 14, Handlers.RESTORATION.getUUID());
		registry.addRecipeClickArea(GuiSmeltingBlock.ReassemblyChamber.class, 77, 19, 24, 14, Handlers.REASSEMBLY.getUUID());
		registry.addRecipeClickArea(GuiSmeltingBlock.ReinforcedFurnace.class, 77, 19, 24, 14, VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeClickArea(GuiDualOutputSmelting.ExtractionChamber.class, 63, 26, 24, 12, Handlers.EXTRACTION.getUUID());
		registry.addRecipeClickArea(GuiDualOutputSmelting.PrecisionChamber.class, 63, 26, 24, 12, Handlers.PRECISION.getUUID());
		registry.addRecipeClickArea(GuiDualOutputSmelting.StoneSeperator.class, 63, 26, 24, 12, Handlers.STONE.getUUID());
		registry.addRecipeClickArea(AlgorithmSeperator.class, 63, 26, 24, 12, Handlers.ALGORITHM.getUUID());
		registry.addRecipeClickArea(GuiHealthProcessor.class, 80, 40, 16, 5, Handlers.HEALTH.getUUID());

		registry.addRecipeClickArea(GuiCalculator.class, 108, 40, 14, 6, Handlers.CALCULATOR.getUUID());
		registry.addRecipeClickArea(GuiScientificCalculator.class, 108, 40, 14, 6, Handlers.SCIENTIFIC.getUUID());
		registry.addRecipeClickArea(GuiAtomicCalculator.class, 109, 40, 10, 6, Handlers.ATOMIC.getUUID());
		registry.addRecipeClickArea(GuiFlawlessCalculator.class, 132, 40, 10, 6, Handlers.FLAWLESS.getUUID());
		registry.addRecipeClickArea(GuiCraftingCalculator.class, 88, 32, 28, 23, VanillaRecipeCategoryUid.CRAFTING);
		registry.addRecipeClickArea(GuiDynamicCalculator.class, 108, 14, 13, 6, Handlers.CALCULATOR.getUUID());
		registry.addRecipeClickArea(GuiDynamicCalculator.class, 108, 40, 13, 6, Handlers.SCIENTIFIC.getUUID());
		registry.addRecipeClickArea(GuiDynamicCalculator.class, 108, 66, 13, 6, Handlers.ATOMIC.getUUID());
		registry.addRecipeClickArea(GuiDynamicModule.class, 108, 14, 13, 6, Handlers.CALCULATOR.getUUID());

		registry.addRecipeClickArea(GuiDynamicModule.class, 108, 40, 13, 6, Handlers.SCIENTIFIC.getUUID());
		registry.addRecipeClickArea(GuiDynamicModule.class, 108, 66, 13, 6, Handlers.ATOMIC.getUUID());
		registry.addRecipeClickArea(GuiConductorMast.class, 79, 26, 18, 8, Handlers.CONDUCTOR.getUUID());
		registry.addRecipeClickArea(GuiFabricationChamber.class, 95, 89, 20, 15, Handlers.FABRICATION.getUUID());
		registry.addRecipeClickArea(GuiAnalysingChamber.class, 20, 24, 115, 13, Handlers.ANALYSING.getUUID());
		recipeTransferRegistry.addRecipeTransferHandler(ContainerSmeltingBlock.class, Handlers.PROCESSING.getUUID(), 0, 1, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerSmeltingBlock.class, Handlers.RESTORATION.getUUID(), 0, 1, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerSmeltingBlock.class, Handlers.REASSEMBLY.getUUID(), 0, 1, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerSmeltingBlock.class, VanillaRecipeCategoryUid.SMELTING, 0, 1, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDualOutputSmelting.class, Handlers.EXTRACTION.getUUID(), 0, 1, 4, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDualOutputSmelting.class, Handlers.PRECISION.getUUID(), 0, 1, 4, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDualOutputSmelting.class, Handlers.STONE.getUUID(), 0, 1, 4, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDualOutputSmelting.class, Handlers.ALGORITHM.getUUID(), 0, 1, 4, 36);

		recipeTransferRegistry.addRecipeTransferHandler(ContainerCalculator.class, Handlers.CALCULATOR.getUUID(), 0, 2, 3, 36);

		recipeTransferRegistry.addRecipeTransferHandler(ContainerScientificCalculator.class, Handlers.SCIENTIFIC.getUUID(), 0, 2, 3, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerAtomicCalculator.class, Handlers.ATOMIC.getUUID(), 0, 3, 4, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerFlawlessCalculator.class, Handlers.FLAWLESS.getUUID(), 0, 4, 5, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerCraftingCalculator.class, VanillaRecipeCategoryUid.CRAFTING, 0, 9, 10, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDynamicCalculator.class, Handlers.CALCULATOR.getUUID(), 1, 2, 10, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDynamicCalculator.class, Handlers.SCIENTIFIC.getUUID(), 4, 2, 10, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerDynamicCalculator.class, Handlers.ATOMIC.getUUID(), 7, 3, 10, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerConductorMast.class, Handlers.CONDUCTOR.getUUID(), 0, 1, 2, 36);

		Calculator.logger.info("Finished JEI Integration");
	}

    @Override
	public Object buildRecipe(ISonarRecipe recipe, RecipeHelperV2<ISonarRecipe> helper) {
        if (Loader.isModLoaded("jei") || Loader.isModLoaded("JEI")) {
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

	public enum Handlers implements IJEIHandler {
		PROCESSING(ProcessingChamberRecipes.instance(), Calculator.processingChamber, "restorationchamber", Recipes.Processing.class),
		/**/
		RESTORATION(RestorationChamberRecipes.instance(), Calculator.restorationChamber, "restorationchamber", Recipes.Restoration.class),
		/**/
		REASSEMBLY(ReassemblyChamberRecipes.instance(), Calculator.reassemblyChamber, "restorationchamber", Recipes.Reassembly.class),
		/**/
		EXTRACTION(ExtractionChamberRecipes.instance(), Calculator.extractionChamber, "extractionchamber", Recipes.Extraction.class),
		/**/
		PRECISION(PrecisionChamberRecipes.instance(), Calculator.precisionChamber, "extractionchamber", Recipes.Precision.class),
		/**/
		STONE(StoneSeparatorRecipes.instance(), Calculator.stoneSeparator, "stoneseperator", Recipes.Stone.class),
		/**/
		ALGORITHM(AlgorithmSeparatorRecipes.instance(), Calculator.algorithmSeparator, "stoneseperator", Recipes.Algorithm.class),
		/**/
		CALCULATOR(CalculatorRecipes.instance(), Calculator.itemCalculator, "calculator", Recipes.Calculator.class),
		/**/
		SCIENTIFIC(ScientificRecipes.instance(), Calculator.itemScientificCalculator, "scientificcalculator", Recipes.Scientific.class),
		/**/
		ATOMIC(AtomicCalculatorRecipes.instance(), Calculator.atomicCalculator, "atomiccalculator", Recipes.Atomic.class),
		/**/
		FLAWLESS(FlawlessCalculatorRecipes.instance(), Calculator.itemFlawlessCalculator, "flawlesscalculator", Recipes.Flawless.class),
		/**/
		HEALTH(HealthProcessorRecipes.instance(), Calculator.healthProcessor, "guicalculatorplug", Recipes.Health.class),
		/**/
		CONDUCTOR(ConductorMastRecipes.instance(), Calculator.conductorMast, "conductorMast", Recipes.Conductor.class),
		/**/
		FABRICATION(FabricationChamberRecipes.instance(), Calculator.fabricationChamber, "fabrication_chamber_jei", Recipes.Fabrication.class),
		/**/
		HARVEST(TreeHarvestRecipes.instance(), Calculator.sickle, "sickle_harvesting", Recipes.Harvest.class),
		/**/
		ANALYSING(AnalysingChamberRecipes.instance(), Calculator.analysingChamber, "guicalculatorplug", Recipes.Analysing.class);
		// DISCHARGE(null, "Discharge Values", "guipowercube",
		// Recipes.Discharge.class);
		/**/
		public IRecipeHelperV2 helper;
		public String unlocalizedName;
		public String textureName;
		public Class<? extends JEIRecipeV2> recipeClass;
		public ItemStack crafter;

		Handlers(IRecipeHelperV2 helper, Object stack, String textureName, Class<? extends JEIRecipeV2> recipeClass) {
			this.helper = helper;
			this.crafter = ItemStackHelper.getOrCreateStack(stack);
			this.unlocalizedName = crafter.getUnlocalizedName() + ".name";
			this.textureName = textureName;
			this.recipeClass = recipeClass;
		}

		@Override
		public JEICategoryV2 getCategory(IGuiHelper guiHelper) {
			switch (this) {
			case SCIENTIFIC:
			case CALCULATOR:
				return new CalculatorCategory(guiHelper, this);
			case PROCESSING:
			case RESTORATION:
			case REASSEMBLY:
				return new SingleProcessCategory(guiHelper, this);
			case EXTRACTION:
			case PRECISION:
			case STONE:
			case ALGORITHM:
				return new DualProcessCategory(guiHelper, this);
			case ATOMIC:
				return new AtomicCategory(guiHelper, this);
			case FLAWLESS:
				return new FlawlessCategory(guiHelper, this);
			case HEALTH:
				return new ValueCategory(guiHelper, this);
			case CONDUCTOR:
				return new ConductorMastCategory(guiHelper, this);
			case FABRICATION:
				return new FabricationCategory(guiHelper, this);
			case HARVEST:
				return new SickleCategory(guiHelper, this);
			case ANALYSING:
				return new AnalysingCategory(guiHelper, this);
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
		public Class<? extends JEIRecipeV2> getRecipeClass() {
			return recipeClass;
		}

		@Override
		public IRecipeHelperV2 getRecipeHelper() {
			return helper;
		}

        @Override
		public ArrayList<JEIRecipeV2> getJEIRecipes() {
            ArrayList<JEIRecipeV2> recipesV2 = new ArrayList<>();
			if (helper instanceof RecipeHelperV2) {
				RecipeHelperV2 helper = (RecipeHelperV2) this.helper;
				for (ISonarRecipe recipe : (ArrayList<ISonarRecipe>) helper.getRecipes()) {
					try {
						recipesV2.add(recipeClass.getConstructor(RecipeHelperV2.class, ISonarRecipe.class).newInstance(helper, recipe));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return recipesV2;
		}

		/* @Deprecated
		 * 
		 * @Override public ArrayList<JEIRecipe> getJEIRecipes() {
         * ArrayList<JEIRecipe> recipes = new ArrayList<>(); String id =
		 * helper.getRecipeID(); if (helper instanceof RecipeHelper) {
		 * RecipeHelper helper = (RecipeHelper) this.helper; for
		 * (Entry<Object[], Object[]> entry : helper.getRecipes().entrySet()) {
		 * try { recipes.add(recipeClass.newInstance().getInstance(id,
		 * helper.convertOutput(entry.getKey()),
		 * helper.convertOutput(entry.getValue()))); } catch (Exception e) {
		 * e.printStackTrace(); } } } else if (helper instanceof ValueHelper) {
		 * ValueHelper helper = (ValueHelper) this.helper; for (Entry<Object,
		 * Integer> entry : helper.getRecipes().entrySet()) { try {
		 * recipes.add(recipeClass.newInstance().getInstance(id, new Object[] {
		 * entry.getKey() }, new Object[] { null })); } catch (Exception e) {
		 * e.printStackTrace(); } } } else if (helper instanceof
		 * FabricationChamberRecipes) { FabricationChamberRecipes helper =
		 * (FabricationChamberRecipes) this.helper; LinkedHashMap<ItemStack,
		 * CircuitStack[]> chamberRecipes = helper.getRecipes(); for
		 * (Entry<ItemStack, CircuitStack[]> entry : chamberRecipes.entrySet())
         * { ArrayList<ItemStack> stacks = new ArrayList<>(); for (CircuitStack
		 * circuit : entry.getValue()) { stacks.add(circuit.buildItemStack()); }
		 * try { recipes.add(recipeClass.newInstance().getInstance(id,
		 * stacks.toArray(), new Object[] { entry.getKey() })); } catch
		 * (Exception e) { e.printStackTrace(); } } } return recipes; } */
		@Override
		public ItemStack getCrafterItemStack() {
			return crafter;
		}

		@Override
		public String getUUID() {
			return helper.getRecipeID();
		}
	}
}
