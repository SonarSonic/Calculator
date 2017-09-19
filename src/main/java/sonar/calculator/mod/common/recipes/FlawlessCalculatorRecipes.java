package sonar.calculator.mod.common.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.recipes.DefinedRecipeHelper;

public class FlawlessCalculatorRecipes extends DefinedRecipeHelper<CalculatorRecipe> {

		private static final FlawlessCalculatorRecipes recipes = new FlawlessCalculatorRecipes();

    public static FlawlessCalculatorRecipes instance() {
			return recipes;
		}

		public FlawlessCalculatorRecipes() {
			super(4, 1, true);
		}

		@Override
		public void addRecipes() {
			addRecipe(Calculator.pearSapling, Calculator.endDiamond, Calculator.endDiamond, Blocks.END_STONE, Calculator.diamondSapling);
			addRecipe("ingotGold", "ingotGold", "ingotGold", "ingotGold", Items.DIAMOND);
			addRecipe("gemDiamond", "gemDiamond", "gemDiamond", "gemDiamond", Items.EMERALD);
			addRecipe("ingotIron", "ingotIron", "ingotIron", "ingotIron", Items.ENDER_PEARL);
			addRecipe("logWood", "logWood", "logWood", "logWood", Blocks.OBSIDIAN);
			addRecipe(Blocks.OBSIDIAN, Blocks.OBSIDIAN, Blocks.OBSIDIAN, Blocks.OBSIDIAN, Calculator.purifiedObsidian);
			addRecipe(Calculator.broccoli, Calculator.broccoli, Calculator.broccoli, Calculator.broccoli, Calculator.fiddledewFruit);

			addRecipe(Calculator.itemEnergyModule, Calculator.itemCalculator, Calculator.itemCalculator, Calculator.itemEnergyModule, Calculator.itemLocatorModule);
			addRecipe(Calculator.flawlessdiamond, "blockGlass", "blockGlass", Calculator.flawlessdiamond, new ItemStack(Calculator.flawlessGlass, 4));
			addRecipe(Calculator.circuitBoard, Calculator.enriched_coal, Calculator.enriched_coal, Calculator.circuitBoard, new ItemStack(Calculator.controlled_Fuel, 4));
			addRecipe(Calculator.gas_lantern_off, new ItemStack(Calculator.circuitBoard, 1, 8), new ItemStack(Calculator.circuitBoard, 1, 8), Calculator.gas_lantern_off, Calculator.CO2Generator);

			addRecipe(Items.BLAZE_POWDER, Items.BLAZE_POWDER, Items.BLAZE_POWDER, Items.BLAZE_POWDER, Items.BLAZE_ROD);
			addRecipe(Items.BLAZE_ROD, Items.BLAZE_ROD, Items.BLAZE_ROD, Items.BLAZE_ROD, Items.GHAST_TEAR);
		}

		@Override
		public String getRecipeID() {
			return "Flawless";
		}
	}