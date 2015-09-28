package sonar.calculator.mod.integration.nei.handlers;

import net.minecraft.client.gui.inventory.GuiContainer;
import sonar.calculator.mod.client.gui.generators.GuiExtractor;
import sonar.calculator.mod.common.recipes.machines.GlowstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.machines.RedstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.machines.StarchExtractorRecipes;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.ValueHelper;

public class ExtractorHandlers {

	public static class Starch extends AbstractGeneratorHandler {

		@Override
		public ValueHelper valuehelper() {
			return StarchExtractorRecipes.instance();
		}

		@Override
		public String getOverlayIdentifier() {
			return "starch";
		}

		@Override
		public String getRecipeName() {
			return FontHelper.translate("tile.starchextractor.name");
		}

		@Override
		public String getGuiTexture() {
			return "Calculator:textures/gui/starchgenerator.png";
		}

		@Override
		public Class<? extends GuiContainer> getGuiClass() {
			return GuiExtractor.Starch.class;
		}

	}

	public static class Redstone extends AbstractGeneratorHandler {

		@Override
		public ValueHelper valuehelper() {
			return RedstoneExtractorRecipes.instance();
		}

		@Override
		public String getOverlayIdentifier() {
			return "redstone";
		}

		@Override
		public String getRecipeName() {
			return FontHelper.translate("tile.redstoneextractor.name");
		}

		@Override
		public String getGuiTexture() {
			return "Calculator:textures/gui/redstonegenerator.png";
		}

		@Override
		public Class<? extends GuiContainer> getGuiClass() {
			return GuiExtractor.Redstone.class;
		}
	}

	public static class Glowstone extends AbstractGeneratorHandler {

		@Override
		public ValueHelper valuehelper() {
			return GlowstoneExtractorRecipes.instance();
		}

		@Override
		public String getOverlayIdentifier() {
			return "glowstone";
		}

		@Override
		public String getRecipeName() {
			return FontHelper.translate("tile.glowstoneextractor.name");
		}

		@Override
		public String getGuiTexture() {
			return "Calculator:textures/gui/glowstonegenerator.png";
		}

		@Override
		public Class<? extends GuiContainer> getGuiClass() {
			return GuiExtractor.Glowstone.class;
		}
	}
}
