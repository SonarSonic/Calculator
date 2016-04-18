package sonar.calculator.mod.common.recipes.machines;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.core.helpers.ValueHelper;

public class HealthProcessorRecipes extends ValueHelper {

	private static final HealthProcessorRecipes recipes = new HealthProcessorRecipes();

	public static final ValueHelper instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		addRecipe(Items.blaze_rod, 50);
		addRecipe(Items.bone, 1);
		addRecipe(Items.blaze_powder, 25);
		addRecipe(Items.ender_pearl, 50);
		addRecipe(Items.feather, 1);
		addRecipe(Items.egg, 1);
		addRecipe(new ItemStack(Items.dye, 1), 3);
		addRecipe(Items.leather, 2);
		addRecipe(Items.ghast_tear, 150);
		addRecipe(Items.string, 1);
		addRecipe(Items.gunpowder, 2);
		addRecipe(Items.nether_star, 500);
		addRecipe(Items.rotten_flesh, 1);
		addRecipe(Items.spider_eye, 2);
		addRecipe(Items.slime_ball, 10);
		addRecipe(Items.magma_cream, 25);
		addRecipe(Items.fermented_spider_eye, 4);
		addRecipe(Blocks.wool, 3);
		addRecipe(Items.golden_apple, 150);
	}

	@Override
	public String getRecipeID() {
		return "Health Values";
	}
}
