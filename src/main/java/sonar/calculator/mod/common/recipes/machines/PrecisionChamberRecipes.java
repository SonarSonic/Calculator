package sonar.calculator.mod.common.recipes.machines;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.helpers.RecipeHelper;

public class PrecisionChamberRecipes extends RecipeHelper {

	private static final PrecisionChamberRecipes recipes = new PrecisionChamberRecipes();

	public PrecisionChamberRecipes() {
		super(1, 2, false);
	}

	public static final RecipeHelper instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		addRecipe("cobblestone", new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 0));
		addRecipe(Blocks.dirt, new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDirty, 1, 0));
		addRecipe("stone", new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 0));
		addRecipe("sand", new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 1));
		addRecipe(Blocks.stonebrick, new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 2));
		addRecipe("reinforcedStone", new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 3));
		addRecipe(Blocks.soul_sand, new ItemStack(Calculator.soil, 2), new ItemStack(Calculator.circuitDirty, 1, 4));
		addRecipe(Blocks.obsidian, new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDirty, 1, 5));
		addRecipe(Blocks.gravel, new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 6));
		addRecipe("treeLeaves", new ItemStack(Calculator.soil, 2), new ItemStack(Calculator.circuitDirty, 1, 7));
		addRecipe("gemLapis", new ItemStack(Calculator.soil, 2), new ItemStack(Calculator.circuitDirty, 1, 8));
		addRecipe(Blocks.nether_brick, new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 9));
		addRecipe(Calculator.reinforceddirtBlock, new ItemStack(Calculator.soil, 2), new ItemStack(Calculator.circuitDirty, 1, 10));
		addRecipe("sandstone", new ItemStack(Calculator.soil, 2), new ItemStack(Calculator.circuitDamaged, 1, 11));
		addRecipe(Blocks.clay, new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 12));
		addRecipe("dustRedstone", new ItemStack(Calculator.soil, 2), new ItemStack(Calculator.circuitDirty, 1, 13));

	}

	@Override
	public String getRecipeID() {
		return "PrecisionChamber";
	}

}
