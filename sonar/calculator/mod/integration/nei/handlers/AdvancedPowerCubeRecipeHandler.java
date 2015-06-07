package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;

import net.minecraft.client.gui.inventory.GuiContainer;
import sonar.calculator.mod.client.gui.machines.GuiAdvancedPowerCube;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class AdvancedPowerCubeRecipeHandler extends TemplateRecipeHandler {

	@Override
	public void loadTransferRects() {

		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(
				new Rectangle(49 - 5, 63 - 11, 78, 10), "calculatordischarge",
				new Object[0]));

	}

	@Override
	public String getOverlayIdentifier() {
		return "advancedpowercube";
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiAdvancedPowerCube.class;
	}

	@Override
	public String getRecipeName() {
		return null;
	}

	@Override
	public String getGuiTexture() {
		return null;
	}
}
