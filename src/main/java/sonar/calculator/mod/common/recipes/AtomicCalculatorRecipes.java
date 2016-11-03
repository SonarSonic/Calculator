package sonar.calculator.mod.common.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.block.MaterialBlock.Variants;
import sonar.core.recipes.DefinedRecipeHelper;

public class AtomicCalculatorRecipes extends DefinedRecipeHelper {

	private static final AtomicCalculatorRecipes recipes = new AtomicCalculatorRecipes();

	public static final AtomicCalculatorRecipes instance() {
		return recipes;
	}

	public AtomicCalculatorRecipes() {
		super(3, 1, true);
	}

	@Override
	public void addRecipes() {
		addRecipe("ingotIron", "dustRedstone", "ingotIron", Calculator.itemScientificCalculator);
		addRecipe("gemDiamond", Calculator.atomic_binder, "gemDiamond", Calculator.flawlessdiamond);
		addRecipe("gemDiamond", Items.BLAZE_ROD, "gemDiamond", Calculator.firediamond);
		addRecipe(Items.BLAZE_ROD, Calculator.flawlessdiamond, Items.BLAZE_ROD, Calculator.firediamond);
		addRecipe(Blocks.END_STONE, Calculator.electricDiamond, Blocks.OBSIDIAN, Calculator.endDiamond);
		addRecipe(Calculator.amethystSapling, Blocks.END_STONE, Calculator.tanzaniteSapling, Calculator.pearSapling);
		addRecipe(Calculator.itemScientificCalculator, Calculator.atomic_binder, Calculator.redstone_ingot, Calculator.itemAdvancedTerrainModule);
		addRecipe(Calculator.tanzaniteLog, Calculator.atomic_binder, Calculator.tanzaniteLeaves, Calculator.tanzaniteSapling);
		addRecipe(Calculator.large_tanzanite, Calculator.atomic_binder, "treeSapling", Calculator.tanzaniteSapling);
		addRecipe(Calculator.healthProcessor, Calculator.itemEnergyModule, Calculator.hungerProcessor, Calculator.itemNutritionModule);
		addRecipe(new ItemStack(Calculator.circuitBoard, 1, 4), Calculator.atomic_binder, "dustEnrichedGold", new ItemStack(Calculator.speedUpgrade, 4));
		addRecipe(new ItemStack(Calculator.circuitBoard, 1, 5), Calculator.atomic_binder, "dustEnrichedGold", new ItemStack(Calculator.energyUpgrade, 4));
		addRecipe(new ItemStack(Calculator.circuitBoard, 1, 6), Calculator.atomic_binder, "dustEnrichedGold", Calculator.voidUpgrade);
		addRecipe(new ItemStack(Calculator.circuitBoard, 1, 7), Calculator.atomic_binder, "dustEnrichedGold", Calculator.transferUpgrade);
		addRecipe(new ItemStack(Calculator.circuitBoard, 1, 9), Calculator.redstoneextractor, Calculator.large_tanzanite, Calculator.glowstoneextractor);
		addRecipe(Calculator.redstone_ingot, Calculator.starchextractor, Calculator.redstone_ingot, Calculator.redstoneextractor);
		addRecipe(Calculator.itemEnergyModule, Calculator.firediamond, Calculator.itemEnergyModule, Calculator.conductorMast);
		addRecipe(new ItemStack(Calculator.material_block, 1, Variants.REINFORCED_IRON.getMeta()), Calculator.reinforcedChest, new ItemStack(Calculator.material_block, 1, Variants.REINFORCED_IRON.getMeta()), Calculator.storageChamber);
		addRecipe(Calculator.reassemblyChamber, Calculator.flawlessdiamond, Calculator.restorationChamber, Calculator.processingChamber);
		addRecipe(Calculator.reinforcediron_ingot, Blocks.CHEST, Calculator.reinforcediron_ingot, Calculator.itemStorageModule);
		addRecipe(Calculator.reinforcediron_ingot, Calculator.electricDiamond, Calculator.reinforcediron_ingot, Calculator.transmitter);
		addRecipe(Calculator.reinforcediron_ingot, Calculator.firediamond, Calculator.reinforcediron_ingot, new ItemStack(Calculator.weatherStation, 4));
	}

	@Override
	public String getRecipeID() {
		return "Atomic";
	}
}