package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;

import net.minecraft.client.gui.inventory.GuiContainer;
import sonar.calculator.mod.client.gui.calculators.GuiPortableDynamic;
import sonar.core.helpers.FontHelper;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class PortableDynamicRecipeHandler extends TemplateRecipeHandler {
	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(100, 0, 22, 14), "calculator", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(100, 25, 22, 14), "scientific", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(100, 50, 22, 14), "atomic", new Object[0]));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiPortableDynamic.class;
	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/dynamiccalculator.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "dynamic";
	}

	@Override
	public String getRecipeName() {
		return FontHelper.translate("tile.dynamiccalculatorBlock.name");
	}
}
