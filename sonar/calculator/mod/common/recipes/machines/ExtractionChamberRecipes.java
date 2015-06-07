package sonar.calculator.mod.common.recipes.machines;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.utils.helpers.RecipeHelper;

public class ExtractionChamberRecipes extends RecipeHelper {

	private static final ExtractionChamberRecipes recipes = new ExtractionChamberRecipes();

	public ExtractionChamberRecipes(){
		super(1,2);
	}
	public static final RecipeHelper instance() {
		return recipes;
	}
	@Override
	public void addRecipes() {
	    addRecipe(Blocks.dirt, new ItemStack(Calculator.soil, 1), new ItemStack(Calculator.circuitDirty));
	    addRecipe("stone", new ItemStack(Calculator.small_stone, 1), new ItemStack(Calculator.circuitDamaged));
	    addRecipe("cobblestone", new ItemStack(Calculator.small_stone, 1), new ItemStack(Calculator.circuitDamaged));
	}

}
