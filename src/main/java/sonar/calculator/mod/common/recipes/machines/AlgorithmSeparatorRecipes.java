package sonar.calculator.mod.common.recipes.machines;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.helpers.RecipeHelper;

public class AlgorithmSeparatorRecipes extends RecipeHelper {

	private static final AlgorithmSeparatorRecipes recipes = new AlgorithmSeparatorRecipes();

	public AlgorithmSeparatorRecipes(){
		super(1,2, false);
	}
	public static final RecipeHelper instance() {
		return recipes;
	}
	@Override
	public void addRecipes() {
		addRecipe("blockLapis", Calculator.large_tanzanite, Calculator.shard_tanzanite);
	    addRecipe("gemLapis", Calculator.small_tanzanite, Calculator.shard_tanzanite);
	    addRecipe("gemDiamond",new ItemStack(Calculator.weakeneddiamond, 4), new ItemStack(Items.dye, 2, 4));
	    addRecipe("dustRedstone", new ItemStack(Calculator.redstone_ingot, 2), new ItemStack(Calculator.small_stone, 2));
	    addRecipe(Calculator.tanzaniteLeaves,new ItemStack(Blocks.leaves,1), new ItemStack(Calculator.shard_tanzanite,2));
	    addRecipe(Calculator.tanzaniteLog, new ItemStack(Blocks.log,1), Calculator.small_tanzanite);
		
	}

	@Override
	public String getRecipeID() {
		return "AlgorithmSeparator";
	}

}
