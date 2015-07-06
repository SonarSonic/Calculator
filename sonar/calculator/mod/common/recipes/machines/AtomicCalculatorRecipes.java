package sonar.calculator.mod.common.recipes.machines;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.utils.helpers.RecipeHelper;

public class AtomicCalculatorRecipes extends RecipeHelper {


	private static final AtomicCalculatorRecipes recipes = new AtomicCalculatorRecipes();

	public AtomicCalculatorRecipes() {
		super(3, 1, false);
	}
	public static final RecipeHelper instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		addRecipe(Items.iron_ingot, Items.redstone, Items.iron_ingot, new ItemStack(Calculator.itemScientificCalculator,1));
		addRecipe(Items.diamond, Calculator.atomic_binder, Items.diamond, Calculator.flawlessdiamond);
		addRecipe(Items.diamond, Items.blaze_rod, Items.diamond, Calculator.flawlessfirediamond);
		addRecipe(Blocks.end_stone, Calculator.electricdiamond, Blocks.obsidian, Calculator.enddiamond);
		addRecipe(Calculator.AmethystSapling, Blocks.end_stone, Calculator.tanzaniteSapling, Calculator.PearSapling);
		addRecipe(new ItemStack(Calculator.itemScientificCalculator,1), Calculator.atomic_binder, Calculator.redstone_ingot, new ItemStack(Calculator.itemAdvancedTerrainModule,1));
		addRecipe(new ItemStack(Calculator.tanzaniteLog), Calculator.atomic_binder, Calculator.tanzaniteLeaf, Calculator.tanzaniteSapling);
		addRecipe(Calculator.large_tanzanite, Calculator.atomic_binder, "treeSapling", Calculator.tanzaniteSapling);
		addRecipe(Calculator.healthprocessor, new ItemStack(Calculator.itemEnergyModule), Calculator.hungerprocessor, new ItemStack(Calculator.itemNutritionModule,1));
		addRecipe(new ItemStack(Calculator.circuitBoard, 1, 4), Calculator.atomic_binder, Calculator.enrichedgold, Calculator.speedUpgrade);
		addRecipe(new ItemStack(Calculator.circuitBoard, 1, 5), Calculator.atomic_binder, Calculator.enrichedgold, Calculator.energyUpgrade);
		addRecipe(new ItemStack(Calculator.circuitBoard, 1, 6), Calculator.atomic_binder, Calculator.enrichedgold, Calculator.voidUpgrade);
		addRecipe(new ItemStack(Calculator.circuitBoard, 1, 9), Calculator.redstoneextractor, Calculator.large_tanzanite, Calculator.glowstoneextractor);
		addRecipe(Calculator.redstone_ingot, Calculator.starchextractor, Calculator.redstone_ingot, Calculator.redstoneextractor);
		addRecipe(new ItemStack(Calculator.itemEnergyModule,1),Calculator.flawlessfirediamond, new ItemStack(Calculator.itemEnergyModule,1), Calculator.conductorMast);
		addRecipe(Calculator.reinforced_iron_block, Blocks.chest, Calculator.reinforced_iron_block, Calculator.storageChamber);
		addRecipe(Calculator.reassemblyChamber, Calculator.flawlessdiamond, Calculator.restorationChamber, Calculator.processingChamber);
		addRecipe(Calculator.reinforcediron_ingot, Blocks.chest, Calculator.reinforcediron_ingot, new ItemStack(Calculator.itemStorageModule,1));
		addRecipe(Calculator.reinforcediron_ingot, Calculator.electricdiamond, Calculator.reinforcediron_ingot, Calculator.transmitter);
		addRecipe(Calculator.reinforcediron_ingot, Calculator.flawlessfirediamond, Calculator.reinforcediron_ingot, Calculator.weatherStation);
		addRecipe(Calculator.electricdiamond, Calculator.rainSensor, Calculator.electricdiamond, Calculator.weatherController);
		
	}

}
