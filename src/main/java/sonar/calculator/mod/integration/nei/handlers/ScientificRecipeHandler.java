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
import sonar.calculator.mod.client.gui.calculators.GuiScientificCalculator;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.integration.nei.handlers.CalculatorRecipeHandler.SmeltingPair;
import sonar.core.utils.helpers.FontHelper;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import sonar.core.utils.helpers.RecipeHelper;

public class ScientificRecipeHandler extends TemplateRecipeHandler {
	public class SmeltingPair extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack input2;
		PositionedStack output;

		public SmeltingPair(Object input, Object input2, Object output) {
			super();
			if (input instanceof RecipeHelper.OreStack)
				input = OreDictionary.getOres(((RecipeHelper.OreStack) input).oreString);
			if (input2 instanceof RecipeHelper.OreStack)
				input2 = OreDictionary.getOres(((RecipeHelper.OreStack) input2).oreString);

			this.input = new PositionedStack(input, 20, 24);
			this.input2 = new PositionedStack(input2, 74, 24);
			this.output = new PositionedStack(output, 129, 24);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(ScientificRecipeHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] { this.input, this.input2 }));
		}

		@Override
		public PositionedStack getResult() {
			return this.output;
		}

	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(100, 25, 22, 14), "scientific", new Object[0]));
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
		if ((outputId.equals("scientific")) && (getClass() == ScientificRecipeHandler.class)) {
			Map<Object[], Object[]> recipes = RecipeRegistry.ScientificRecipes.instance().getRecipes();
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
		Map<Object[], Object[]> recipes = RecipeRegistry.ScientificRecipes.instance().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType((ItemStack) recipe.getValue()[0], result)) {
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getValue()[0]));

			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("scientific")) && (getClass() == ScientificRecipeHandler.class)) {
			loadCraftingRecipes("scientific", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object[], Object[]> recipes = RecipeRegistry.ScientificRecipes.instance().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			if (RecipeRegistry.ScientificRecipes.instance().containsStack(ingredient, recipe.getKey(), false) != -1)
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getValue()[0]));

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
