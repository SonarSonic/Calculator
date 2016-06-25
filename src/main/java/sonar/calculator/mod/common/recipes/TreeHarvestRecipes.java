package sonar.calculator.mod.common.recipes;

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

	public static ItemStack[] harvestLeaves(World world, BlockPos pos, boolean override) {
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
					return new ItemStack[] { outputs[0] };
				case 4:
					return new ItemStack[] { outputs[0], outputs[1] };
				case 5:
					return new ItemStack[] { outputs[1] };

				default:
					return null;

				}
			}
		}
		return null;
	}
}
