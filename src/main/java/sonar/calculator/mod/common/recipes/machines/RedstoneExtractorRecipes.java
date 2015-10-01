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
import sonar.core.utils.helpers.ValueHelper;

public class RedstoneExtractorRecipes extends ValueHelper {

	private static final RedstoneExtractorRecipes recipes = new RedstoneExtractorRecipes();

	public static final ValueHelper instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		addRecipe("dustRedstone", 500);
		addRecipe("blockRedstone", 4500);
		addRecipe("ingotRedstone", 1000);
		addRecipe("oreRedstone", 1500);
	}

}
