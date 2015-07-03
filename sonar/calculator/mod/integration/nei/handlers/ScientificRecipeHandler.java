package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.calculators.GuiScientificCalculator;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipe;
import sonar.calculator.mod.common.recipes.crafting.ScientificCalculatorRecipes;
import sonar.core.utils.helpers.FontHelper;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class ScientificRecipeHandler extends TemplateRecipeHandler {
	public class SmeltingPair extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack input2;
		PositionedStack output;

		public SmeltingPair(ItemStack input, ItemStack input2, ItemStack output) {
			super();
			input.stackSize = 1;
			this.input = new PositionedStack(input, 20, 24);
			this.input2 = new PositionedStack(input2, 74, 24);
			this.output = new PositionedStack(output, 129, 24);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(
					ScientificRecipeHandler.this.cycleticks / 48,
					Arrays.asList(new PositionedStack[] { this.input }));
		}

		@Override
		public PositionedStack getResult() {
			return this.output;
		}

		@Override
		public PositionedStack getOtherStack() {
			return this.input2;
		}
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(
				new Rectangle(100, 25, 22, 14), "scientific", new Object[0]));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiScientificCalculator.class;
	}

	@Override
	public String getRecipeName() {
		return FontHelper.translate("item.ScientificCalculator.name");
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("scientific"))
				&& (getClass() == ScientificRecipeHandler.class)) {
			Map<Integer, CalculatorRecipe> recipes = ScientificCalculatorRecipes
					.recipes().getStandardList();
			for (Map.Entry<Integer, CalculatorRecipe> recipe : recipes
					.entrySet()) {

		    	if(CalculatorConfig.isEnabled(((CalculatorRecipe) recipe.getValue()).output)){
				this.arecipes.add(new SmeltingPair(((CalculatorRecipe) recipe
						.getValue()).input, ((CalculatorRecipe) recipe
						.getValue()).input2, ((CalculatorRecipe) recipe
						.getValue()).output));
		    	}
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Integer, CalculatorRecipe> recipes = ScientificCalculatorRecipes.recipes().getStandardList();
		for (Map.Entry<Integer, CalculatorRecipe> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameTypeCrafting(((CalculatorRecipe) recipe.getValue()).output, result)) {
		    	if(CalculatorConfig.isEnabled(((CalculatorRecipe) recipe.getValue()).output)){
				this.arecipes.add(new SmeltingPair(((CalculatorRecipe) recipe
						.getValue()).input, ((CalculatorRecipe) recipe
						.getValue()).input2, ((CalculatorRecipe) recipe
						.getValue()).output));}
			}

		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("scientific"))
				&& (getClass() == ScientificRecipeHandler.class)) {
			loadCraftingRecipes("scientific", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {

		Map<Integer, CalculatorRecipe> recipes = ScientificCalculatorRecipes.recipes()
				.getStandardList();
		for (Map.Entry<Integer, CalculatorRecipe> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameTypeCrafting(
					((CalculatorRecipe) recipe.getValue()).input, ingredient)
					|| NEIServerUtils.areStacksSameTypeCrafting(
							((CalculatorRecipe) recipe.getValue()).input2,
							ingredient)) {
		    	if(CalculatorConfig.isEnabled(((CalculatorRecipe) recipe.getValue()).output)){
				this.arecipes.add(new SmeltingPair(((CalculatorRecipe) recipe
						.getValue()).input, ((CalculatorRecipe) recipe
						.getValue()).input2, ((CalculatorRecipe) recipe
						.getValue()).output));}
			}
		}

	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/scientificcalculator.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "scientific";
	}
}
