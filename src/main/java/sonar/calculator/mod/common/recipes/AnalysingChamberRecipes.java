package sonar.calculator.mod.common.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.SonarCore;
import sonar.core.recipes.DefinedRecipeHelper;

public class AnalysingChamberRecipes extends DefinedRecipeHelper {

	private static final AnalysingChamberRecipes instance = new AnalysingChamberRecipes();

	public AnalysingChamberRecipes() {
		super(2, 1, false);
	}

	public static AnalysingChamberRecipes instance() {
		return instance;
	}

	public void addRecipes() {
		addRecipe(1, 1, new ItemStack(Calculator.itemCalculator, 1));
		addRecipe(1, 2, new ItemStack(SonarCore.reinforcedDirtBlock, 1));
		addRecipe(1, 3, new ItemStack(SonarCore.reinforcedStoneBlock, 1));
		addRecipe(1, 4, new ItemStack(Calculator.small_stone, 1));
		addRecipe(1, 5, new ItemStack(Calculator.soil, 1));
		addRecipe(1, 6, new ItemStack(Calculator.powerCube, 1));
		addRecipe(1, 7, new ItemStack(Calculator.broccoli, 1));
		addRecipe(1, 8, new ItemStack(Calculator.reinforcediron_axe, 1));
		addRecipe(1, 9, new ItemStack(Calculator.reinforcediron_sword, 1));
		addRecipe(1, 10, new ItemStack(Calculator.crankHandle, 1));
		addRecipe(1, 11, new ItemStack(Calculator.handcrankedGenerator, 1));
		addRecipe(1, 12, new ItemStack(Calculator.broccoliSeeds, 1));
		addRecipe(1, 13, new ItemStack(Calculator.grenade, 1));

		addRecipe(1, 14, new ItemStack(Calculator.calculator_assembly, 1));
		addRecipe(1, 15, new ItemStack(Calculator.calculator_screen, 1));
		addRecipe(1, 16, new ItemStack(Calculator.enrichedgold_axe, 1));
		addRecipe(1, 17, new ItemStack(Calculator.enrichedgold_pickaxe, 1));

		addRecipe(2, 1, new ItemStack(Calculator.enrichedGold, 1));
		addRecipe(2, 2, new ItemStack(Calculator.circuitDamaged, 1));
		addRecipe(2, 3, new ItemStack(Calculator.circuitDirty, 1));
		addRecipe(2, 4, new ItemStack(Calculator.itemAdvancedTerrainModule, 1));
		addRecipe(2, 5, new ItemStack(Calculator.itemScientificCalculator, 1));
		addRecipe(2, 6, new ItemStack(Calculator.itemTerrainModule, 1));
		addRecipe(2, 7, new ItemStack(Calculator.itemCraftingCalculator, 1));
		addRecipe(2, 8, new ItemStack(Calculator.healthProcessor, 1));
		addRecipe(2, 9, new ItemStack(Calculator.large_amethyst, 1));
		addRecipe(2, 10, new ItemStack(Calculator.large_tanzanite, 1));
		addRecipe(2, 11, new ItemStack(Calculator.amethystLog, 1));
		addRecipe(2, 12, new ItemStack(Calculator.tanzaniteLog, 1));
		addRecipe(2, 13, new ItemStack(Calculator.flawlessdiamond, 1));
		addRecipe(2, 14, new ItemStack(Calculator.enriched_coal, 1));
		addRecipe(2, 15, new ItemStack(Calculator.starchextractor, 1));

		addRecipe(3, 1, new ItemStack(Calculator.itemEnergyModule, 1));
		addRecipe(3, 2, new ItemStack(Calculator.itemHealthModule, 1));
		addRecipe(3, 3, new ItemStack(Calculator.itemHungerModule, 1));
		addRecipe(3, 4, new ItemStack(Calculator.amethystSapling, 1));
		addRecipe(3, 5, new ItemStack(Calculator.tanzaniteSapling, 1));
		addRecipe(3, 6, new ItemStack(Calculator.hungerProcessor, 1));
		addRecipe(3, 7, new ItemStack(Calculator.healthProcessor, 1));
		addRecipe(3, 8, new ItemStack(Calculator.large_amethyst, 1));
		addRecipe(3, 9, new ItemStack(Calculator.large_tanzanite, 1));
		addRecipe(3, 10, new ItemStack(Calculator.tanzaniteLog, 1));
		addRecipe(4, 11, new ItemStack(Blocks.OBSIDIAN, 1));

		addRecipe(4, 1, new ItemStack(Calculator.advanced_assembly, 1));
		addRecipe(4, 2, new ItemStack(Calculator.algorithmSeparator, 1));
		addRecipe(4, 3, new ItemStack(Calculator.stoneSeparator, 1));
		addRecipe(4, 4, new ItemStack(Calculator.atomic_module, 1));
		addRecipe(4, 5, new ItemStack(Items.EMERALD, 1));
		addRecipe(4, 6, new ItemStack(Items.DIAMOND, 1));
		addRecipe(4, 7, new ItemStack(Calculator.flawlessdiamond_sword, 1));
		addRecipe(4, 8, new ItemStack(Calculator.electric_hoe, 1));
		addRecipe(4, 9, new ItemStack(Calculator.endforged_hoe, 1));
		addRecipe(4, 10, new ItemStack(Items.ENDER_PEARL, 1));
		addRecipe(4, 11, new ItemStack(Calculator.fiddledewFruit, 1));
		addRecipe(4, 12, new ItemStack(Calculator.flawlessGlass, 1));
		addRecipe(4, 13, new ItemStack(SonarCore.stableStone[0], 1));
		addRecipe(4, 14, new ItemStack(Calculator.purifiedObsidian, 1));
		addRecipe(4, 15, new ItemStack(Calculator.obsidianKey, 1));

		addRecipe(5, 1, new ItemStack(Calculator.calculatorlocator, 1));
		addRecipe(5, 2, new ItemStack(Calculator.calculatorplug, 1));
		addRecipe(5, 3, new ItemStack(Calculator.itemFlawlessCalculator, 1));
		addRecipe(5, 4, new ItemStack(Calculator.atomic_assembly, 1));
		addRecipe(5, 5, new ItemStack(Calculator.atomicCalculator, 1));
		addRecipe(5, 6, new ItemStack(Items.ENDER_EYE, 1));
		addRecipe(5, 7, new ItemStack(Items.NETHER_STAR, 1));
		addRecipe(5, 8, new ItemStack(Blocks.BEACON, 1));
		addRecipe(5, 9, new ItemStack(Calculator.endDiamond, 1));
		addRecipe(5, 10, new ItemStack(Calculator.flawlessGreenhouse, 1));

	}

	@Override
	public String getRecipeID() {
		return "Analysing Chamber";
	}

}
