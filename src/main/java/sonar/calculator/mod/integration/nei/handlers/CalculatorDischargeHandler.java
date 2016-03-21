package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.CalculatorConfig;
import sonar.core.energy.DischargeValues;
import sonar.core.utils.helpers.FontHelper;

public class CalculatorDischargeHandler extends TemplateRecipeHandler {

	public static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

	public class DischargePair

	extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		public int discharge;

		public DischargePair(ItemStack input, int discharge) {
			this.discharge = discharge;
			input.stackSize = 1;
			this.input = new PositionedStack(input, 28 - 5, 34 - 11);
		}

		@Override
		public PositionedStack getResult() {
			return input;
		}

	}

	public static ArrayList<DischargePair> ainfo;

	@Override
	public String getRecipeName() {
		return FontHelper.translate("energy.display");
	}

	@Override
	public void loadTransferRects() {

		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(49 - 5, 63 - 11, 78, 10), "calculatordischarge", new Object[0]));

	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<ItemStack, Integer> recipes = DischargeValues.getPowerList();
		for (Map.Entry<ItemStack, Integer> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
				DischargePair arecipe = new DischargePair(ingredient, recipe.getValue());
				arecipe.setIngredientPermutation(Arrays.asList(new PositionedStack[] { arecipe.input }), ingredient);
				this.arecipes.add(arecipe);
			}
		}

	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/guiCalculatorDischarge.png";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("calculatordischarge") && getClass() == CalculatorDischargeHandler.class)) {
			Map<ItemStack, Integer> recipes = DischargeValues.getPowerList();
			for (Map.Entry<ItemStack, Integer> recipe : recipes.entrySet())
				this.arecipes.add(new DischargePair(recipe.getKey(), recipe.getValue()));

		} else {
			super.loadCraftingRecipes(outputId, results);
		}

	}

	@Override
	public void drawExtras(int recipe) {
		ItemStack stack = arecipes.get(recipe).getResult().item;
		int info = DischargeValues.getValueOf(stack);
		int take = info * 78 / CalculatorConfig.getInteger("Standard Machine");
		drawProgressBar(49 - 5, 37 - 11, 176, 0, take, 10, 48, 0);
		CalculatorDischargeHandler.fontRenderer.drawString(FontHelper.formatStorage(info), 176 / 2 - CalculatorDischargeHandler.fontRenderer.getStringWidth(" " + info) / 2 - 5, 38 - 11, -1);
	}

	@Override
	public int recipiesPerPage() {

		return 1;

	}

	@Override
	public String getOverlayIdentifier() {
		return "calculatordischarge";
	}

}
