package sonar.calculator.mod.common.recipes;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.block.CalculatorLeaves;
import sonar.calculator.mod.common.block.CalculatorLeaves.LeafGrowth;
import sonar.core.recipes.DefinedRecipeHelper;
import sonar.core.recipes.ISonarRecipe;

import java.util.ArrayList;

public class TreeHarvestRecipes extends DefinedRecipeHelper<CalculatorRecipe> {

	private static final TreeHarvestRecipes recipes = new TreeHarvestRecipes();

	public TreeHarvestRecipes() {
		super(1, 2, false);
	}

	@Override
	public void addRecipes() {
		addRecipe(Calculator.pearLeaves, new ItemStack(Calculator.pear, 1), new ItemStack(Calculator.rotten_pear, 1));
		addRecipe(Calculator.diamondLeaves, new ItemStack(Calculator.weakeneddiamond, 1), new ItemStack(Calculator.flawlessdiamond, 1));
	}

	@Override
	public String getRecipeID() {
		return "Tree Harvest Recipes";
	}

	public static TreeHarvestRecipes instance() {
		return recipes;
	}

	public static ArrayList<ItemStack> harvestLeaves(World world, BlockPos pos, boolean override) {
        ArrayList<ItemStack> stacks = new ArrayList<>();
		Block target = world.getBlockState(pos).getBlock();
		if (target != null) {
			ISonarRecipe recipe = recipes.getRecipeFromInputs(null, new Object[] { new ItemStack(target) });
			if (recipe != null) {
				IBlockState state = world.getBlockState(pos);
				LeafGrowth growth = state.getValue(CalculatorLeaves.GROWTH);

				int meta = growth.getMeta()+1;
				world.setBlockState(pos, state.withProperty(CalculatorLeaves.GROWTH, LeafGrowth.FRESH));
				if (override) {
					meta = 4;
				}
				switch (meta) {
				case 3:
					stacks.add((ItemStack) recipe.outputs().get(0).getValue());
					break;
				case 4:
					stacks.add((ItemStack) recipe.outputs().get(0).getValue());
					stacks.add((ItemStack) recipe.outputs().get(1).getValue());
					break;
				case 5:
					stacks.add((ItemStack) recipe.outputs().get(1).getValue());
					break;
				default:
					break;
				}
			}
		}
		return stacks;
	}
}
