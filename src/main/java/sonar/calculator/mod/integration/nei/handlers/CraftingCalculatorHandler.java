package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;

import net.minecraft.client.gui.inventory.GuiContainer;
import sonar.calculator.mod.client.gui.calculators.GuiCraftingCalculator;
import sonar.core.helpers.FontHelper;

public class CraftingCalculatorHandler extends ShapelessRecipeHandler {
	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(85, 25, 22, 14), "crafting", new Object[0]));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiCraftingCalculator.class;
	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/craftingcalculator.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "craftingcalc";
	}

	@Override
	public String getRecipeName() {
		return FontHelper.translate("item.CraftingCalculator.name");
	}
}
