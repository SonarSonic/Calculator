package sonar.calculator.mod.common.recipes.machines;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.utils.helpers.RecipeHelper;

public class StoneSeparatorRecipes extends RecipeHelper {

	private static final StoneSeparatorRecipes recipes = new StoneSeparatorRecipes();

	public StoneSeparatorRecipes() {
		super(1, 2, false);
	}

	public static final RecipeHelper instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		addRecipe("oreGold", new ItemStack(Calculator.enrichedgold_ingot, 4), new ItemStack(Calculator.small_stone, 2));
		addRecipe("oreIron", new ItemStack(Calculator.reinforcediron_ingot, 4), new ItemStack(Calculator.small_stone, 2));
		addRecipe("blockLapis", Calculator.large_amethyst, Calculator.shard_amethyst);
		addRecipe("gemLapis", Calculator.small_amethyst, Calculator.shard_amethyst);
		addRecipe(new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.planks, 4, 0), new ItemStack(Items.stick, 2, 0));
		addRecipe(new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.planks, 4, 1), new ItemStack(Items.stick, 2, 0));
		addRecipe(new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.planks, 4, 2), new ItemStack(Items.stick, 2, 0));
		addRecipe(new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.planks, 4, 3), new ItemStack(Items.stick, 2, 0));
		addRecipe(new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.planks, 4, 4), new ItemStack(Items.stick, 2, 0));
		addRecipe(new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.planks, 4, 5), new ItemStack(Items.stick, 2, 0));
		addRecipe(Calculator.amethystLeaf, new ItemStack(Blocks.leaves, 1, 0), new ItemStack(Calculator.shard_amethyst, 2));
		addRecipe(Calculator.amethystLog, new ItemStack(Blocks.log, 1, 0), new ItemStack(Calculator.small_amethyst, 1));

	}

	@Override
	public String getRecipeID() {
		return "StoneSeparator";
	}

}
