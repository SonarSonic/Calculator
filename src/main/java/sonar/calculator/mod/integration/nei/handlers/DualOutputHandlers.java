package sonar.calculator.mod.integration.nei.handlers;

import net.minecraft.client.gui.inventory.GuiContainer;
import sonar.calculator.mod.client.gui.machines.GuiDualOutputSmelting;
import sonar.calculator.mod.common.recipes.machines.AlgorithmSeparatorRecipes;
import sonar.calculator.mod.common.recipes.machines.PrecisionChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.StoneSeparatorRecipes;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.RecipeHelper;

public class DualOutputHandlers {

	public static class Algorithm extends AbstractDualOutputHandler {

		@Override
		public RecipeHelper recipeHelper() {
			return AlgorithmSeparatorRecipes.instance();
		}

		@Override
		public Class<? extends GuiContainer> getGuiClass() {
			return GuiDualOutputSmelting.AlgorithmSeperator.class;
		}

		@Override
		public String getRecipeName() {
			return FontHelper.translate("tile.AlgorithmSeperatorIdle.name");
		}

		@Override
		public String getGuiTexture() {
			return "Calculator:textures/gui/nei/seperator.png";
		}

		@Override
		public String getOverlayIdentifier() {
			return "algorithm";
		}

	}

	public static class Stone extends AbstractDualOutputHandler {

		@Override
		public RecipeHelper recipeHelper() {
			return StoneSeparatorRecipes.instance();
		}

		@Override
		public Class<? extends GuiContainer> getGuiClass() {
			return GuiDualOutputSmelting.StoneSeperator.class;
		}

		@Override
		public String getRecipeName() {
			return FontHelper.translate("tile.StoneSeperatorIdle.name");
		}

		@Override
		public String getGuiTexture() {
			return "Calculator:textures/gui/nei/seperator.png";
		}

		@Override
		public String getOverlayIdentifier() {
			return "stone";
		}

	}

	public static class Precision extends AbstractDualOutputHandler {

		@Override
		public RecipeHelper recipeHelper() {
			return PrecisionChamberRecipes.instance();
		}

		@Override
		public Class<? extends GuiContainer> getGuiClass() {
			return GuiDualOutputSmelting.PrecisionChamber.class;
		}

		@Override
		public String getRecipeName() {
			return FontHelper.translate("tile.PrecisionChamber.name");
		}

		@Override
		public String getGuiTexture() {
			return "Calculator:textures/gui/nei/extractionchamber.png";
		}

		@Override
		public String getOverlayIdentifier() {
			return "precision";
		}

	}

}
