package sonar.calculator.mod.common.recipes.machines;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import sonar.calculator.mod.Calculator;
import sonar.core.utils.helpers.RecipeHelper;
import sonar.core.utils.helpers.ValueHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class StarchExtractorRecipes extends ValueHelper {
	
	private static final StarchExtractorRecipes recipes = new StarchExtractorRecipes();

	public static final ValueHelper instance() {
		return recipes;
	}	
	@Override
	public void addRecipes() {

		addRecipe(Items.apple, 2000);
		addRecipe("cropPotato", 1000);
		addRecipe("cropCarrot", 1000);
		addRecipe("cropWheat", 800);
		addRecipe(Calculator.broccoli, 1000);
		addRecipe(Calculator.broccoliSeeds, 800);
		addRecipe(Calculator.fiddledewFruit, 5000);
		addRecipe(Calculator.prunaeSeeds, 1500);
		addRecipe(Items.wheat_seeds, 600);
		addRecipe(Items.melon, 1000);
		addRecipe(Items.melon_seeds, 800);
		addRecipe(Items.reeds, 1000);
		addRecipe("treeSapling", 1000);
		addRecipe("calculatorLeaves", 2500);
		addRecipe("treeLeaves", 200);
		
	}

}
