package sonar.calculator.mod.common.recipes.machines;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.helpers.RecipeHelper;

public class ExtractionChamberRecipes extends RecipeHelper {

	private static final ExtractionChamberRecipes recipes = new ExtractionChamberRecipes();

	public ExtractionChamberRecipes(){
		super(1,2, false);
	}
	public static final RecipeHelper instance() {
		return recipes;
	}
	@Override
	public void addRecipes() {
	    addRecipe(Blocks.dirt, new ItemStack(Calculator.soil, 1), new ItemStack(Calculator.circuitDirty));
	    addRecipe("cobblestone", new ItemStack(Calculator.small_stone, 1), new ItemStack(Calculator.circuitDamaged));
	}

	@Override
	public String getRecipeID() {
		return "ExtractionChamber";
	}
}
