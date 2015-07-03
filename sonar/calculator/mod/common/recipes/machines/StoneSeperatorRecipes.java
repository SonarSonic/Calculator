package sonar.calculator.mod.common.recipes.machines;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.item.misc.ItemCircuit;
import sonar.core.utils.helpers.RecipeHelper;

public class StoneSeperatorRecipes extends RecipeHelper {

	private static final StoneSeperatorRecipes recipes = new StoneSeperatorRecipes();

	public StoneSeperatorRecipes(){
		super(1,2,false);
	}
	public static final RecipeHelper instance() {
		return recipes;
	}
	@Override
	public void addRecipes() {
		
		addRecipe("blockLapis", Calculator.large_amethyst, Calculator.shard_amethyst);
		addRecipe("gemLapis", Calculator.small_amethyst, Calculator.shard_amethyst);
		addRecipe(new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.planks,4,0), new ItemStack(Items.stick,2,0));
		addRecipe(new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.planks,4,1), new ItemStack(Items.stick,2,0));
		addRecipe(new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.planks,4,2), new ItemStack(Items.stick,2,0));
		addRecipe(new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.planks,4,3), new ItemStack(Items.stick,2,0));
		addRecipe(new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.planks,4,4), new ItemStack(Items.stick,2,0));
		addRecipe(new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.planks,4,5), new ItemStack(Items.stick,2,0));
		addRecipe(Calculator.amethystLeaf, new ItemStack(Blocks.leaves,1,0), new ItemStack(Calculator.shard_amethyst,2));
		addRecipe(Calculator.amethystLog, new ItemStack(Blocks.log,1,0), new ItemStack(Calculator.small_amethyst,1));
	}

}
