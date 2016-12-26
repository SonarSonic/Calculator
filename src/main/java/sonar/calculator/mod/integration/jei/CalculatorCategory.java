package sonar.calculator.mod.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import sonar.core.integration.jei.IJEIHandler;
import sonar.core.integration.jei.JEICategoryV2;
import sonar.core.integration.jei.JEIHelper.RecipeMapper;
import sonar.core.recipes.RecipeObjectType;

public class CalculatorCategory extends JEICategoryV2 {

	private final IDrawable background;

	public CalculatorCategory(IGuiHelper guiHelper, IJEIHandler handler) {
		super(handler);
		ResourceLocation location = new ResourceLocation("calculator",
				"textures/gui/" + handler.getTextureName() + ".png");
		background = guiHelper.createDrawable(location, 20, 30, 135, 27);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		RecipeMapper mapper = new RecipeMapper();
		mapper.map(RecipeObjectType.INPUT, 0, 0, 4, 4);
		mapper.map(RecipeObjectType.INPUT, 1, 1, 58, 4);
		mapper.map(RecipeObjectType.OUTPUT, 0, 2, 113, 4);
		mapper.mapTo(recipeLayout.getItemStacks(), ingredients);
	}
}