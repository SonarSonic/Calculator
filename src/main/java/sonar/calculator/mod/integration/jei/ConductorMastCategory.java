package sonar.calculator.mod.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.Calculator;
import sonar.core.integration.jei.IJEIHandler;
import sonar.core.integration.jei.JEICategoryV2;

import javax.annotation.Nonnull;

public class ConductorMastCategory extends JEICategoryV2 {

	private final IDrawable background;
	protected final IDrawableAnimated arrow;

	public ConductorMastCategory(IGuiHelper guiHelper, IJEIHandler handler) {
		super(handler);
		ResourceLocation location = new ResourceLocation("calculator", "textures/gui/" + handler.getTextureName() + ".png");
		background = guiHelper.createDrawable(location, 49, 17, 80, 26);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, 176, 0, 18, 8);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 100, IDrawableAnimated.StartDirection.LEFT, false);
	}

    @Nonnull
    @Override
    public String getModName() {
        return Calculator.name;
    }

	@Nonnull
    @Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		arrow.draw(minecraft, 30, 9);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 4, 4);
		stacks.init(2, false, 58, 4);
		stacks.set(0, ingredients.getInputs(ItemStack.class).get(0));
		stacks.set(2, ingredients.getOutputs(ItemStack.class).get(0));
	}
}