package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;

import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.StatCollector;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class ReinforcedFurnaceRecipeHandler extends TemplateRecipeHandler {

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(66, 19, 24, 10), "smelting", new Object[0]));

		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(49 - 5, 63 - 11, 78, 10), "calculatordischarge", new Object[0]));

	}

	@Override
	public String getOverlayIdentifier() {
		return "reinforced";
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiSmeltingBlock.ReinforcedFurnace.class;
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("tile.ReinforcedFurnace.name");
	}

	@Override
	public String getGuiTexture() {
		return null;
	}
}
