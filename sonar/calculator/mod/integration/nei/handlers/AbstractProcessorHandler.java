package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import sonar.core.utils.helpers.RecipeHelper;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public abstract class AbstractProcessorHandler extends TemplateRecipeHandler {
	
	public abstract RecipeHelper recipeHelper();

	public abstract Class<? extends GuiContainer> getGuiClass();

	public abstract String getRecipeName();

	public abstract String getGuiTexture();
	
	public abstract String getOverlayIdentifier();
	
	public class ProcessingRecipe extends TemplateRecipeHandler.CachedRecipe {		
		PositionedStack input;
		PositionedStack result;

		public ProcessingRecipe(Object input, Object result) {
			this.input = new PositionedStack(input, 48, 13);
			this.result = new PositionedStack(result, 102, 13);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(AbstractProcessorHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] { this.input }));
		}

		@Override
		public PositionedStack getResult() {
			return this.result;
		}
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(66, 19, 24, 10), this.getOverlayIdentifier(), new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(49 - 5, 63 - 11, 78, 10), "calculatordischarge", new Object[0]));

	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals(this.getOverlayIdentifier())) && (AbstractProcessorHandler.class.isAssignableFrom(getClass()))) {
			Map<Object[], Object[]> recipes = recipeHelper().getRecipes();
			for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
				this.arecipes.add(new ProcessingRecipe(recipe.getKey()[0], recipe.getValue()[0]));
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
				this.arecipes.add(new ProcessingRecipe(recipe.getKey()[0], recipe.getValue()[0]));
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals(this.getOverlayIdentifier())) && (AbstractProcessorHandler.class.isAssignableFrom(getClass()))) {
			loadCraftingRecipes(this.getOverlayIdentifier(), new Object[0]);
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
				this.arecipes.add(new ProcessingRecipe(recipe.getKey()[0], recipe.getValue()[0]));
			}
		}
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(71, 13, 176, 10, 24, 16, 48, 0);
	}

}
