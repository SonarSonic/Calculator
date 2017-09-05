package sonar.calculator.mod.common.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.recipes.DefinedRecipeHelper;

public class ScientificRecipes extends DefinedRecipeHelper<CalculatorRecipe> {

	private static final ScientificRecipes recipes = new ScientificRecipes();

    public static ScientificRecipes instance() {
		return recipes;
	}

	public ScientificRecipes() {
		super(2, 1, true);
	}

	@Override
	public void addRecipes() {
		addRecipe(Calculator.enriched_coal, Calculator.enrichedgold_ingot, Calculator.purified_coal);
		addRecipe("ingotIron", "dustRedstone", Calculator.redstone_ingot);
		addRecipe("gemDiamond", Calculator.reinforcediron_ingot, new ItemStack(Calculator.weakeneddiamond, 4));
		addRecipe(Calculator.baby_grenade, Calculator.baby_grenade, Calculator.grenade);
		addRecipe(Calculator.enriched_coal, Items.LAVA_BUCKET, Calculator.firecoal);
		addRecipe(Calculator.large_amethyst, "treeSapling", Calculator.amethystSapling);
		addRecipe(Calculator.amethystLog, Calculator.amethystLeaves, Calculator.amethystSapling);
		addRecipe(Calculator.amethystLog, Calculator.small_amethyst, Calculator.amethystSapling);
		addRecipe(Calculator.amethystLog, Calculator.amethystLog, Calculator.amethystSapling);
		addRecipe(Calculator.itemCalculator, Calculator.redstone_ingot, Calculator.itemTerrainModule);
		addRecipe(Calculator.itemEnergyModule, Calculator.small_amethyst, Calculator.starchextractor);
		addRecipe(Calculator.powerCube, Calculator.purified_coal, Calculator.itemEnergyModule);
	}

	@Override
	public String getRecipeID() {
		return "Scientific";
	}
}