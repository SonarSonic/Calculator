package sonar.calculator.mod.common.recipes.machines;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import sonar.core.utils.helpers.ValueHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class GlowstoneExtractorRecipes extends ValueHelper {

	private static final GlowstoneExtractorRecipes recipes = new GlowstoneExtractorRecipes();

	public static final ValueHelper instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		addRecipe("dustGlowstone", 1000);
		addRecipe("glowstone", 4000);
		addRecipe("ingotGlowstone", 3000);

	}

}
