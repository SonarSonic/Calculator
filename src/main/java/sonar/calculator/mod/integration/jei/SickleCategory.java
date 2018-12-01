package sonar.calculator.mod.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.CalculatorConstants;
import sonar.core.integration.jei.JEISonarCategory;
import sonar.core.integration.jei.JEISonarMapper;
import sonar.core.integration.jei.JEISonarProvider;
import sonar.core.recipes.RecipeObjectType;

import javax.annotation.Nonnull;

public class SickleCategory extends JEISonarCategory {

	private final IDrawable background;

	public SickleCategory(IGuiHelper guiHelper, JEISonarProvider handler) {
		super(guiHelper, handler);
		ResourceLocation location = new ResourceLocation("calculator", "textures/gui/" + handler.background + ".png");
		background = guiHelper.createDrawable(location, 52, 23, 64, 36);
	}

    @Nonnull
    @Override
    public String getModName() {
        return CalculatorConstants.NAME;
    }

	@Nonnull
    @Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		JEISonarMapper mapper = new JEISonarMapper();
		mapper.map(RecipeObjectType.INPUT, 0, 0, 0, 10);
		mapper.map(RecipeObjectType.OUTPUT, 0, 1, 46, 0);
		mapper.map(RecipeObjectType.OUTPUT, 1, 2, 46, 18);
		mapper.mapTo(recipeLayout.getItemStacks(), ingredients);
	}
}