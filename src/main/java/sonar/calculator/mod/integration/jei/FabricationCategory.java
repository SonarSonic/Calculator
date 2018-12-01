package sonar.calculator.mod.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.CalculatorConstants;
import sonar.core.integration.jei.JEISonarCategory;
import sonar.core.integration.jei.JEISonarProvider;

import javax.annotation.Nonnull;
import java.util.List;

public class FabricationCategory extends JEISonarCategory {

	private final IDrawable background;
	protected final IDrawableAnimated arrow;

	public FabricationCategory(IGuiHelper guiHelper, JEISonarProvider handler) {
		super(guiHelper, handler);
		ResourceLocation location = new ResourceLocation("calculator", "textures/gui/" + handler.background + ".png");
		background = guiHelper.createDrawable(location, 0, 0, 151, 55);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, 0, 56, 20, 16);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 100, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Nonnull
    @Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		arrow.draw(minecraft, 95, 20);
	}

    @Nonnull
    @Override
    public String getModName() {
        return CalculatorConstants.NAME;
    }

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		List<List<ItemStack>> outputs = ingredients.getInputs(ItemStack.class);
		int left = 0;
		int top = 0;
		int cPos = 0;
		for (List<ItemStack> stack : outputs) {
            int cLeft = left + (cPos - cPos / 5 * 5) * 18;
            int cTop = top + cPos / 5 * 18;
			stacks.init(cPos, true, cLeft, cTop);
			stacks.set(cPos, stack);
			cPos++;
		}
		stacks.init(-1, false, 129, 20);
		stacks.set(-1, ingredients.getOutputs(ItemStack.class).get(0));
	}
}