package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorOreDict;
import sonar.calculator.mod.client.gui.machines.GuiDualOutputSmelting;
import sonar.calculator.mod.common.recipes.machines.ExtractionChamberRecipes;
import sonar.core.utils.helpers.FontHelper;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import sonar.core.utils.helpers.RecipeHelper;

public class ExtractionRecipeHandler extends TemplateRecipeHandler {
	public class SmeltingPair extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack result;
		PositionedStack result2;

		public SmeltingPair(Object input, Object result, Object result2) {
			super();
			if (input instanceof RecipeHelper.OreStack)
				input = OreDictionary.getOres(((RecipeHelper.OreStack) input).oreString);

			this.input = new PositionedStack(input, 34, 13);
			this.result = new PositionedStack(result, 88, 13);
			this.result2 = new PositionedStack(result2, 117, 13);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(ExtractionRecipeHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] { this.input }));
		}

		@Override
		public PositionedStack getResult() {
			return this.result;
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			return getCycledIngredients(ExtractionRecipeHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] { this.result2 }));

		}
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(52, 19, 24, 10), "extraction", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(49 - 5, 63 - 11, 78, 10), "calculatordischarge", new Object[0]));

	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiDualOutputSmelting.ExtractionChamber.class;
	}

	@Override
	public String getRecipeName() {
		return FontHelper.translate("tile.ExtractionChamber.name");
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("extraction")) && (getClass() == ExtractionRecipeHandler.class)) {
			Map<Object[], Object[]> recipes = ExtractionChamberRecipes.instance().getRecipes();
			for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getValue()[0], ((ItemStack) recipe.getValue()[1]).getItem() == Calculator.circuitDamaged ? CalculatorOreDict
						.circuitList(1) : CalculatorOreDict.circuitList(2)));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (result == null) {
			return;
		}
		Map<Object[], Object[]> recipes = ExtractionChamberRecipes.instance().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			int pos = ExtractionChamberRecipes.instance().containsStack(result, recipe.getValue(), false);

			if (pos == 0) {
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getValue()[0], ((ItemStack) recipe.getValue()[1]).getItem() == Calculator.circuitDamaged ? CalculatorOreDict
						.circuitList(1) : CalculatorOreDict.circuitList(2)));
				
				
			} else if (result.getItem() == Calculator.circuitDamaged || result.getItem() == Calculator.circuitDirty) {
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getValue()[0], ((ItemStack) recipe.getValue()[1]).getItem() == Calculator.circuitDamaged ? CalculatorOreDict
						.circuitList(1) : CalculatorOreDict.circuitList(2)));

			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("extraction")) && (getClass() == ExtractionRecipeHandler.class)) {
			loadCraftingRecipes("extraction", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object[], Object[]> recipes = ExtractionChamberRecipes.instance().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			int pos = ExtractionChamberRecipes.instance().containsStack(ingredient, recipe.getKey(), false);
			if (pos != -1) {
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getValue()[0], recipe.getValue()[1]));
			}
		}
	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/nei/extractionchamber.png";
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(57, 13, 176, 10, 24, 16, 48, 0);
	}

	@Override
	public String getOverlayIdentifier() {
		return "extraction";
	}
}
