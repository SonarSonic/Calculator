package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.calculators.GuiFlawlessCalculator;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.RecipeHelper;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class FlawlessRecipeHandler extends TemplateRecipeHandler {
	public class SmeltingPair extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack input2;
		PositionedStack input3;
		PositionedStack input4;
		PositionedStack output;

		public SmeltingPair(Object input, Object input2, Object input3, Object input4, Object output) {
			super();
			if (input instanceof RecipeHelper.OreStack)
				input =((RecipeHelper.OreStack) input).getStacks();
			if (input2 instanceof RecipeHelper.OreStack)
				input2 = ((RecipeHelper.OreStack) input2).getStacks();
			if (input3 instanceof RecipeHelper.OreStack)
				input3 = ((RecipeHelper.OreStack) input3).getStacks();
			if (input4 instanceof RecipeHelper.OreStack)
				input4 = ((RecipeHelper.OreStack) input4).getStacks();

			this.input = new PositionedStack(input, 12, 24);
			this.input2 = new PositionedStack(input2, 44, 24);
			this.input3 = new PositionedStack(input3, 76, 24);
			this.input4 = new PositionedStack(input4, 108, 24);
			this.output = new PositionedStack(output, 140, 24);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(FlawlessRecipeHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] { this.input, this.input2, this.input3, this.input4}));
		}

		@Override
		public PositionedStack getResult() {
			return this.output;
		}
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(127, 28, 10, 8), "flawless", new Object[0]));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiFlawlessCalculator.class;
	}

	@Override
	public String getRecipeName() {
		return FontHelper.translate("item.FlawlessCalculator.name");
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("flawless")) && (getClass() == FlawlessRecipeHandler.class)) {
			Map<Object[], Object[]> recipes = RecipeRegistry.FlawlessRecipes.instance().getRecipes();
			for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet())
				if (CalculatorConfig.isEnabled((ItemStack) recipe.getValue()[0])) {
					this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getKey()[2], recipe.getKey()[3], recipe.getValue()[0]));
				}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (!CalculatorConfig.isEnabled(result)) {
			return;
		}
		Map<Object[], Object[]> recipes = RecipeRegistry.FlawlessRecipes.instance().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType((ItemStack) recipe.getValue()[0], result)) {
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getKey()[2], recipe.getKey()[3], recipe.getValue()[0]));

			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("flawless")) && (getClass() == FlawlessRecipeHandler.class)) {
			loadCraftingRecipes("flawless", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object[], Object[]> recipes = RecipeRegistry.FlawlessRecipes.instance().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			if (RecipeRegistry.FlawlessRecipes.instance().containsStack(ingredient, recipe.getKey(), false) != -1)
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getKey()[2], recipe.getKey()[3], recipe.getValue()[0]));

		}
	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/flawlesscalculator.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "flawless";
	}
}
