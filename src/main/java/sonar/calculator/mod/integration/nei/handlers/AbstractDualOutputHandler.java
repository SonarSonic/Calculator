package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import sonar.core.utils.helpers.RecipeHelper;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public abstract class AbstractDualOutputHandler extends TemplateRecipeHandler {


	public abstract RecipeHelper recipeHelper();

	public abstract Class<? extends GuiContainer> getGuiClass();

	public abstract String getRecipeName();

	public abstract String getGuiTexture();
	
	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(52, 20, 24, 10), this.getOverlayIdentifier(), new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(49 - 5, 63 - 11, 78, 10), "calculatordischarge", new Object[0]));
	}


	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals(this.getOverlayIdentifier())) && (AbstractDualOutputHandler.class.isAssignableFrom(getClass()))) {
			Map<Object[], Object[]> recipes = recipeHelper().getRecipes();
			for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
				this.arecipes.add(new DualOutputRecipe(recipe.getKey()[0], recipe.getValue()[0], recipe.getValue()[1]));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object[], Object[]> recipes = recipeHelper().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			int pos = recipeHelper().containsStack(result, recipe.getValue(), false);
			if (pos != -1) {
				this.arecipes.add(new DualOutputRecipe(recipe.getKey()[0], recipe.getValue()[0], recipe.getValue()[1]));
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals(getOverlayIdentifier())) && (AbstractDualOutputHandler.class.isAssignableFrom(getClass()))) {
			loadCraftingRecipes(getOverlayIdentifier(), new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object[], Object[]> recipes = recipeHelper().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			int pos = recipeHelper().containsStack(ingredient, recipe.getKey(), false);
			if (pos != -1) {
				this.arecipes.add(new DualOutputRecipe(recipe.getKey()[0], recipe.getValue()[0], recipe.getValue()[1]));
			}
		}
	}


	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(57, 13, 176, 10, 24, 16, 48, 0);
	}

	@Override
	public abstract String getOverlayIdentifier();

	public class DualOutputRecipe extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack result;
		PositionedStack result2;

		public DualOutputRecipe(Object input, Object result, Object result2) {
			if (input instanceof RecipeHelper.OreStack) {
				input = ((RecipeHelper.OreStack) input).getStacks();
			}
			this.input = new PositionedStack(input, 34, 13);
			this.result = new PositionedStack(result, 88, 13);
			this.result2 = new PositionedStack(result2, 117, 13);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(AbstractDualOutputHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] { this.input }));
		}

		@Override
		public PositionedStack getOtherStack() {
			return this.result;
		}

		@Override
		public PositionedStack getResult() {
			return this.result2;
		}
	}
}
