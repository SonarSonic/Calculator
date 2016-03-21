package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.calculators.GuiAtomicCalculator;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.core.utils.helpers.RecipeHelper;

public class AtomicRecipeHandler extends TemplateRecipeHandler {
	public class SmeltingPair extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack input2;
		PositionedStack input3;
		PositionedStack output;

		public SmeltingPair(Object input, Object input2, Object input3, Object output) {
			super();
			if (input instanceof RecipeHelper.OreStack)
				input = ((RecipeHelper.OreStack) input).getStacks();
			if (input2 instanceof RecipeHelper.OreStack)
				input2 = ((RecipeHelper.OreStack) input2).getStacks();
			if (input3 instanceof RecipeHelper.OreStack)
				input3 = ((RecipeHelper.OreStack) input3).getStacks();

			this.input = new PositionedStack(input, 15, 24);
			this.input2 = new PositionedStack(input2, 47, 24);
			this.input3 = new PositionedStack(input3, 79, 24);
			this.output = new PositionedStack(output, 129, 24);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(AtomicRecipeHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] { this.input, this.input2, this.input3 }));
		}

		@Override
		public PositionedStack getResult() {
			return this.output;
		}
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(100, 25, 22, 14), "atomic", new Object[0]));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiAtomicCalculator.class;
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("tile.atomiccalculatorBlock.name");
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("atomic")) && (getClass() == AtomicRecipeHandler.class)) {
			Map<Object[], Object[]> recipes = RecipeRegistry.AtomicRecipes.instance().getRecipes();
			for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet())
				if (CalculatorConfig.isEnabled((ItemStack) recipe.getValue()[0])) {
					this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getKey()[2], recipe.getValue()[0]));
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
		Map<Object[], Object[]> recipes = RecipeRegistry.AtomicRecipes.instance().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType((ItemStack) recipe.getValue()[0], result)) {
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getKey()[2], recipe.getValue()[0]));

			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object[], Object[]> recipes = RecipeRegistry.AtomicRecipes.instance().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			if (RecipeRegistry.AtomicRecipes.instance().containsStack(ingredient, recipe.getKey(), false) != -1)
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getKey()[2], recipe.getValue()[0]));

		}
	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/atomiccalculator.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "atomic";
	}
}
