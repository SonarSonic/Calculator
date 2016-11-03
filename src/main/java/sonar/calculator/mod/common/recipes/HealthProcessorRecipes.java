package sonar.calculator.mod.common.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.core.recipes.ValueHelperV2.SimpleValueHelper;

public class HealthProcessorRecipes extends SimpleValueHelper {

	private static final HealthProcessorRecipes recipes = new HealthProcessorRecipes();

	public static final HealthProcessorRecipes instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		addRecipe(Items.BLAZE_ROD, 50);
		addRecipe(Items.BONE, 1);
		addRecipe(Items.BLAZE_POWDER, 25);
		addRecipe(Items.ENDER_PEARL, 50);
		addRecipe(Items.FEATHER, 1);
		addRecipe(Items.EGG, 1);
		addRecipe(new ItemStack(Items.DYE, 1), 3);
		addRecipe(Items.LEATHER, 2);
		addRecipe(Items.GHAST_TEAR, 150);
		addRecipe(Items.STRING, 1);
		addRecipe(Items.GUNPOWDER, 2);
		addRecipe(Items.NETHER_STAR, 500);
		addRecipe(Items.ROTTEN_FLESH, 1);
		addRecipe(Items.SPIDER_EYE, 2);
		addRecipe(Items.SLIME_BALL, 10);
		addRecipe(Items.MAGMA_CREAM, 25);
		addRecipe(Items.FERMENTED_SPIDER_EYE, 4);
		addRecipe(Blocks.WOOL, 3);
		addRecipe(Items.GOLDEN_APPLE, 150);
	}

	@Override
	public String getRecipeID() {
		return "Health Values";
	}
}
