package sonar.calculator.mod.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import sonar.core.integration.jei.IJEIHandler;
import sonar.core.integration.jei.JEICategory;

public class CalculatorCategory extends JEICategory {

	private final IDrawable background;

	public CalculatorCategory(IGuiHelper guiHelper, IJEIHandler handler) {
		super(handler);
		ResourceLocation location = new ResourceLocation("calculator", "textures/gui/" + handler.getTextureName() + ".png");
		background = guiHelper.createDrawable(location, 20, 30, 135, 27);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		guiItemStacks.init(0, true, 4, 4);
		guiItemStacks.init(1, true, 58, 4);
		guiItemStacks.init(2, false, 113, 4);
		guiItemStacks.setFromRecipe(0, recipeWrapper.getInputs().get(0));
		guiItemStacks.setFromRecipe(1, recipeWrapper.getInputs().get(1));
		guiItemStacks.setFromRecipe(2, recipeWrapper.getOutputs());
	}
}