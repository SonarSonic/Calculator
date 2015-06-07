package sonar.calculator.mod.common.recipes.machines;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.utils.helpers.RecipeHelper;

public class AlgorithmSeperatorRecipes extends RecipeHelper {

	private static final AlgorithmSeperatorRecipes recipes = new AlgorithmSeperatorRecipes();

	public AlgorithmSeperatorRecipes(){
		super(1,2);
	}
	public static final RecipeHelper instance() {
		return recipes;
	}
	@Override
	public void addRecipes() {
		addRecipe("blockLapis", Calculator.large_tanzanite, Calculator.shard_tanzanite);
	    addRecipe("gemLapis", Calculator.small_tanzanite, Calculator.shard_tanzanite);
	    addRecipe("gemDiamond",new ItemStack(Calculator.weakeneddiamond, 4), new ItemStack(Items.dye, 2, 4));
	    addRecipe("oreGold", new ItemStack(Calculator.enrichedgold_ingot, 4), new ItemStack(Calculator.small_stone, 2));
	    addRecipe("oreIron", new ItemStack(Calculator.reinforcediron_ingot, 4), new ItemStack(Calculator.small_stone, 2));
	    addRecipe("dustRedstone", new ItemStack(Calculator.redstone_ingot, 2), new ItemStack(Calculator.small_stone, 2));
	    addRecipe(Calculator.tanzaniteLeaf,new ItemStack(Blocks.leaves,1), new ItemStack(Calculator.shard_tanzanite,2));
	    addRecipe(Calculator.tanzaniteLog, new ItemStack(Blocks.log,1), Calculator.small_tanzanite);
		
	}

}
