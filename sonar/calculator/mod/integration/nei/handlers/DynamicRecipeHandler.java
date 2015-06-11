package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.IResearchStore;
import sonar.calculator.mod.client.gui.calculators.GuiDynamicCalculator;
import sonar.calculator.mod.common.containers.ContainerDynamicCalculator;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipe;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipes;
import sonar.calculator.mod.integration.nei.handlers.CalculatorRecipeHandler.SmeltingPair;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class DynamicRecipeHandler extends TemplateRecipeHandler {
	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(100, 0, 22, 14), "dynacalculator", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(100, 25, 22, 14), "scientific", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(100, 50, 22, 14), "atomic", new Object[0]));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiDynamicCalculator.class;
	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/dynamiccalculator.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "portable_dynamic";
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("tile.dynamiccalculatorBlock.name");
	}
}
