package sonar.calculator.mod.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import sonar.core.integration.jei.IJEIHandler;
import sonar.core.integration.jei.JEICategory;

public class ValueCategory extends JEICategory {

	private final IDrawable background;

	public ValueCategory(IGuiHelper guiHelper, IJEIHandler handler) {
		super(handler);
		ResourceLocation location = new ResourceLocation("calculator", "textures/gui/" + handler.getTextureName() + ".png");
		background = guiHelper.createDrawable(location, 75, 29, 26, 36);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 4, 4);
		stacks.setFromRecipe(0, recipeWrapper.getInputs());
	}
}