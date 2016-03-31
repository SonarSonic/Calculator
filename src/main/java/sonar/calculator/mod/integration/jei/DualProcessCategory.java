package sonar.calculator.mod.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import sonar.core.helpers.ISonarRecipeHelper;
import sonar.core.integration.jei.JEICategory;

public class DualProcessCategory extends JEICategory {

	private final IDrawable background;
	protected final IDrawableAnimated arrow;

	public DualProcessCategory(IGuiHelper guiHelper, ISonarRecipeHelper helper, String name, String tex) {
		super(helper, name);
		ResourceLocation location = new ResourceLocation("calculator", "textures/gui/" + tex + ".png");
		background = guiHelper.createDrawable(location, 34, 19, 108, 27);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, 177, 10, 22, 15);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 100, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawAnimations(Minecraft minecraft) {
		arrow.draw(minecraft, 29, 5);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		guiItemStacks.init(0, true, 4, 4);
		guiItemStacks.init(3, false, 58, 4);
		guiItemStacks.setFromRecipe(0, recipeWrapper.getInputs());
		guiItemStacks.setFromRecipe(2, recipeWrapper.getOutputs().get(0));
		guiItemStacks.setFromRecipe(3, recipeWrapper.getOutputs().get(1));
	}
}