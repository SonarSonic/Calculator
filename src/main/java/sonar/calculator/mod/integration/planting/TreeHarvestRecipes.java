package sonar.calculator.mod.integration.planting;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.core.utils.helpers.RecipeHelper;

public class TreeHarvestRecipes extends RecipeHelper {

	private static final TreeHarvestRecipes recipes = new TreeHarvestRecipes();

	public TreeHarvestRecipes() {
		super(1, 2, false);
	}

	@Override
	public void addRecipes() {
		this.addRecipe(Calculator.pearLeaf, new ItemStack(Calculator.pear, 1), new ItemStack(Calculator.rotten_pear, 1));
		this.addRecipe(Calculator.diamondLeaf, new ItemStack(Calculator.weakeneddiamond, 1), new ItemStack(Calculator.flawlessdiamond, 1));
	}

	@Override
	public String getRecipeID() {
		return "Tree Harvest Recipes";
	}

	public static ItemStack[] harvestLeaves(World world, int x, int y, int z, int random) {
		Block target = world.getBlock(x, y, z);
		if (target != null) {
			ItemStack[] outputs = recipes.getOutput(new ItemStack(target));
			System.out.print(outputs.length);
			if (outputs != null && outputs.length==2) {
				world.setBlockMetadataWithNotify(x, y, z, 0, 2);
				switch (random) {
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
