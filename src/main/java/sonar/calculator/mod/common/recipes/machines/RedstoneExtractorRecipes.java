package sonar.calculator.mod.common.recipes.machines;

import sonar.core.helpers.ValueHelper;

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

	@Override
	public String getRecipeID() {
		return "Redstone Values";
	}

}
