package sonar.calculator.mod.common.recipes;

import sonar.core.recipes.ValueHelperV2.SimpleValueHelper;

public class GlowstoneExtractorRecipes extends SimpleValueHelper {

	private static final GlowstoneExtractorRecipes recipes = new GlowstoneExtractorRecipes();

	public static final GlowstoneExtractorRecipes instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		addRecipe("dustGlowstone", 1000);
		addRecipe("glowstone", 4000);
		addRecipe("ingotGlowstone", 3000);
	}

	@Override
	public String getRecipeID() {
		return "Glowstone Values";
	}

}
