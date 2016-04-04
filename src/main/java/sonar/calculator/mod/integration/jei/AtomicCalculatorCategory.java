package sonar.calculator.mod.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import sonar.core.integration.jei.IJEIHandler;
import sonar.core.integration.jei.JEICategory;

public class AtomicCalculatorCategory extends JEICategory {

	private final IDrawable background;

	public AtomicCalculatorCategory(IGuiHelper guiHelper, IJEIHandler handler) {
		super(handler);
		ResourceLocation location = new ResourceLocation("calculator", "textures/gui/" + handler.getTextureName() + ".png");
		background = guiHelper.createDrawable(location, 19, 30, 136, 27);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 0, 4);
		stacks.init(1, true, 32, 4);
		stacks.init(2, true, 64, 4);
		stacks.init(3, false, 114, 4);
		stacks.setFromRecipe(0, recipeWrapper.getInputs().get(0));
		stacks.setFromRecipe(1, recipeWrapper.getInputs().get(1));
		stacks.setFromRecipe(2, recipeWrapper.getInputs().get(2));
		stacks.setFromRecipe(3, recipeWrapper.getOutputs());
	}
}