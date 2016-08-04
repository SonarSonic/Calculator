package sonar.calculator.mod.common.recipes;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.block.CalculatorLeaves;
import sonar.calculator.mod.common.block.CalculatorLeaves.LeafGrowth;
import sonar.core.helpers.RecipeHelper;

public class TreeHarvestRecipes extends RecipeHelper {

	private static final TreeHarvestRecipes recipes = new TreeHarvestRecipes();

	public TreeHarvestRecipes() {
		super(1, 2, false);
	}

	@Override
	public void addRecipes() {
		this.addRecipe(Calculator.pearLeaves, new ItemStack(Calculator.pear, 1), new ItemStack(Calculator.rotten_pear, 1));
		this.addRecipe(Calculator.diamondLeaves, new ItemStack(Calculator.weakeneddiamond, 1), new ItemStack(Calculator.flawlessdiamond, 1));
	}

	@Override
	public String getRecipeID() {
		return "Tree Harvest Recipes";
	}

	public static ArrayList<ItemStack> harvestLeaves(World world, BlockPos pos, boolean override) {
		ArrayList<ItemStack> stacks = new ArrayList();
		Block target = world.getBlockState(pos).getBlock();
		if (target != null) {
			ItemStack[] outputs = recipes.getOutput(new ItemStack(target));
			if (outputs != null && outputs.length == 2) {
				IBlockState state = world.getBlockState(pos);
				LeafGrowth growth = state.getValue(CalculatorLeaves.GROWTH);

				int meta = growth.getMeta();
				world.setBlockState(pos, state.withProperty(CalculatorLeaves.GROWTH, LeafGrowth.FRESH));
				if (override) {
					meta = 4;
				}
				switch (meta) {
				case 3:
					stacks.add(outputs[0]);
					break;
				case 4:
					stacks.add(outputs[0]);
					stacks.add(outputs[1]);
					break;
				case 5:
					stacks.add(outputs[1]);
					break;
				default:
					break;
				}
			}
		}
		return stacks;
	}
}
