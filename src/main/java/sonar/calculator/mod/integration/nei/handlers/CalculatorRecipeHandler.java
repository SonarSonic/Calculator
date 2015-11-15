package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.IResearchStore;
import sonar.calculator.mod.client.gui.calculators.GuiCalculator;
import sonar.calculator.mod.common.containers.ContainerDynamicCalculator;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.integration.nei.handlers.ScientificRecipeHandler.SmeltingPair;
import sonar.core.utils.helpers.FontHelper;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import sonar.core.utils.helpers.RecipeHelper;

public class CalculatorRecipeHandler extends TemplateRecipeHandler {
	
	public class SmeltingPair extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack input2;
		PositionedStack output;

		public SmeltingPair(Object input, Object input2, Object output) {
			super();
			if (input instanceof RecipeHelper.OreStack)
				input = ((RecipeHelper.OreStack) input).getStacks();
			if (input2 instanceof RecipeHelper.OreStack)
				input2 = ((RecipeHelper.OreStack) input2).getStacks();
			this.input = new PositionedStack(input, 20, 24);
			this.input2 = new PositionedStack(input2, 74, 24);
			this.output = new PositionedStack(output, 129, 24);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(CalculatorRecipeHandler.this.cycleticks / 16, Arrays.asList(new PositionedStack[] { this.input, this.input2 }));
		}

		@Override
		public PositionedStack getResult() {
			return this.output;
		}
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(100, 25, 22, 14), "calculator", new Object[0]));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiCalculator.class;
	}

	@Override
	public String getRecipeName() {
		return FontHelper.translate("item.Calculator.name");
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("calculator")) && (getClass() == CalculatorRecipeHandler.class)) {
			Map<Object[], Object[]> recipes = RecipeRegistry.CalculatorRecipes.instance().getRecipes();
			for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet())
				if (CalculatorConfig.isEnabled((ItemStack) recipe.getValue()[0])) {
					this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getValue()[0]));
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
		Map<Object[], Object[]> recipes = RecipeRegistry.CalculatorRecipes.instance().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType((ItemStack) recipe.getValue()[0], result)) {
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getValue()[0]));

			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("calculator")) && (getClass() == CalculatorRecipeHandler.class)) {
			loadCraftingRecipes("calculator", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object[], Object[]> recipes = RecipeRegistry.CalculatorRecipes.instance().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			if (RecipeRegistry.CalculatorRecipes.instance().containsStack(ingredient, recipe.getKey(), false) != -1)
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getValue()[0]));

		}

	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/calculator.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "calculator";
	}
}
