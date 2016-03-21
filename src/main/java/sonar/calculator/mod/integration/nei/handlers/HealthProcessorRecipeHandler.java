package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.client.gui.machines.GuiHealthProcessor;
import sonar.calculator.mod.common.recipes.machines.HealthProcessorRecipes;
import sonar.core.utils.helpers.FontHelper;

public class HealthProcessorRecipeHandler extends TemplateRecipeHandler {
	public class ChancePair extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		public int value;

		public ChancePair(Object result, int chance) {
			super();
			this.input = new PositionedStack(result, 80 - 5, 34 - 11);
			this.value = chance;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(HealthProcessorRecipeHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] { this.input }));
		}

		@Override
		public PositionedStack getResult() {
			return null;
		}
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(84 - 11, 32 - 5, 16, 8), "healthvalue", new Object[0]));

	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiHealthProcessor.class;
	}

	@Override
	public String getRecipeName() {
		return FontHelper.translate("tile.HealthProcessor.name");
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("healthvalue")) && (getClass() == HealthProcessorRecipeHandler.class)) {
			Map<ItemStack, Integer> recipes = HealthProcessorRecipes.instance().getRecipes();
			for (Map.Entry<ItemStack, Integer> recipe : recipes.entrySet())
				this.arecipes.add(new ChancePair(recipe.getKey(), recipe.getValue()));
			this.transferRects.clear();
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		int healthValue = (Integer) HealthProcessorRecipes.instance().getOutput(ingredient);
		if (healthValue != 0) {
			this.arecipes.add(new ChancePair(ingredient, healthValue));
		}
	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/guicalculatorplug.png";
	}

	@Override
	public void drawExtras(int recipe) {
		ChancePair pair = (ChancePair) this.arecipes.get(recipe);
		int value = pair.value;
		FontHelper.textCentre("Health Points = " + value, 176, 8, 0);

	}

	@Override
	public String getOverlayIdentifier() {
		return "circuitextraction";
	}

}
