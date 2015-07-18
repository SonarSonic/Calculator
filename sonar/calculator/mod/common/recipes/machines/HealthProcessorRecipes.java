package sonar.calculator.mod.common.recipes.machines;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.core.utils.helpers.ValueHelper;

public class HealthProcessorRecipes extends ValueHelper {

	private static final RedstoneExtractorRecipes recipes = new RedstoneExtractorRecipes();

	public static final ValueHelper instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		addRecipe(Items.blaze_rod, 100);
		addRecipe(Items.bone, 2);
		addRecipe(Items.blaze_powder, 50);
		addRecipe(Items.ender_pearl, 200);
		addRecipe(Items.feather, 2);
		addRecipe(Items.egg, 2);
		addRecipe(new ItemStack(Items.dye, 1), 6);
		addRecipe(Items.leather, 4);
		addRecipe(Items.ghast_tear, 250);
		addRecipe(Items.string, 2);
		addRecipe(Items.gunpowder, 4);
		addRecipe(Items.nether_star, 5000);
		addRecipe(Items.rotten_flesh, 2);
		addRecipe(Items.spider_eye, 4);
		addRecipe(Items.slime_ball, 20);
		addRecipe(Items.magma_cream, 50);
		addRecipe(Items.fermented_spider_eye, 8);
		addRecipe(Blocks.wool, 6);
		addRecipe(Items.golden_apple, 250);
	}

}
