package sonar.calculator.mod.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.Calculator;
import sonar.core.integration.jei.IJEIHandler;
import sonar.core.integration.jei.JEICategoryV2;
import sonar.core.integration.jei.JEIHelper.RecipeMapper;
import sonar.core.recipes.RecipeObjectType;

public class FlawlessCategory extends JEICategoryV2 {

	private final IDrawable background;

	public FlawlessCategory(IGuiHelper guiHelper, IJEIHandler handler) {
		super(handler);
		ResourceLocation location = new ResourceLocation("calculator",
				"textures/gui/" + handler.getTextureName() + ".png");
		background = guiHelper.createDrawable(location, 16, 34, 146, 18);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

    @Override
    public String getModName() {
        return Calculator.name;
    }

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		RecipeMapper mapper = new RecipeMapper();
		mapper.map(RecipeObjectType.INPUT, 0, 0, 0, 0);
		mapper.map(RecipeObjectType.INPUT, 1, 1, 32, 0);
		mapper.map(RecipeObjectType.INPUT, 2, 2, 64, 0);
		mapper.map(RecipeObjectType.INPUT, 3, 3, 96, 0);
		mapper.map(RecipeObjectType.OUTPUT, 0, 4, 128, 0);
		mapper.mapTo(recipeLayout.getItemStacks(), ingredients);
		/*
		 * IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		 * stacks.init(0, true, 0, 0); stacks.init(1, true, 32, 0);
		 * stacks.init(2, true, 64, 0); stacks.init(3, true, 96, 0);
		 * stacks.init(4, false, 128, 0); stacks.setFromRecipe(0,
		 * recipeWrapper.getInputs().get(0)); stacks.setFromRecipe(1,
		 * recipeWrapper.getInputs().get(1)); stacks.setFromRecipe(2,
		 * recipeWrapper.getInputs().get(2)); stacks.setFromRecipe(3,
		 * recipeWrapper.getInputs().get(3)); stacks.setFromRecipe(4,
		 * recipeWrapper.getOutputs());
		 */
	}
}