package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.client.gui.generators.GuiConductorMast;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.core.utils.helpers.FontHelper;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class ConductorRecipeHandler extends TemplateRecipeHandler {
	public class SmeltingPair extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack result;

		public SmeltingPair(ItemStack input, ItemStack result) {
			super();
			input.stackSize = 1;
			this.input = new PositionedStack(input, 49, 16 - 5);
			this.result = new PositionedStack(result, 103, 16 - 5);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(ConductorRecipeHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] { this.input }));
		}

		@Override
		public PositionedStack getResult() {
			return this.result;
		}
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(66, 19, 24, 10), "conductor", new Object[0]));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiConductorMast.class;
	}

	@Override
	public String getRecipeName() {
		return FontHelper.translate("tile.ConductorMast.name");
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("conductor")) && (getClass() == ConductorRecipeHandler.class)) {
			Map<ItemStack, ItemStack> recipes = RecipeRegistry.ConductorMastItemRecipes.instance().getRecipeStacks();
			for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
				this.arecipes.add(new SmeltingPair(recipe.getKey(), recipe.getValue()));
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<ItemStack, ItemStack> recipes = RecipeRegistry.ConductorMastItemRecipes.instance().getRecipeStacks();

		for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType(recipe.getValue(), result))
				this.arecipes.add(new SmeltingPair(recipe.getKey(), result));
		}

	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<ItemStack, ItemStack> recipes = RecipeRegistry.ConductorMastItemRecipes.instance().getRecipeStacks();
		for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
				SmeltingPair arecipe = new SmeltingPair(recipe.getKey(), recipe.getValue());
				arecipe.setIngredientPermutation(Collections.singletonList(arecipe.input), ingredient);
				this.arecipes.add(arecipe);
			}
		}
	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/nei/conductorMast.png";
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(74, 15, 176, 0, 19, 9, 48, 0);

	}

	@Override
	public String getOverlayIdentifier() {
		return "conductor";
	}
}
