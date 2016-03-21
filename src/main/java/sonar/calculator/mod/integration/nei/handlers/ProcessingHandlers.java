package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;

import net.minecraft.client.gui.inventory.GuiContainer;
import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import sonar.calculator.mod.common.recipes.machines.ProcessingChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.ReassemblyChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.RestorationChamberRecipes;
import sonar.core.integration.nei.AbstractProcessorHandler;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.RecipeHelper;

public class ProcessingHandlers {

	public static class ProcessingChamber extends AbstractProcessorHandler {

		@Override
		public RecipeHelper recipeHelper() {
			return ProcessingChamberRecipes.instance();
		}

		@Override
		public Class<? extends GuiContainer> getGuiClass() {
			return GuiSmeltingBlock.ProcessingChamber.class;
		}

		@Override
		public String getRecipeName() {
			return FontHelper.translate("tile.ProcessingChamber.name");
		}

		@Override
		public String getGuiTexture() {
			return "Calculator:textures/gui/nei/restorationchamber.png";
		}

		@Override
		public String getOverlayIdentifier() {
			return "processing";
		}

	}

	public static class ReassemblyChamber extends AbstractProcessorHandler {

		@Override
		public RecipeHelper recipeHelper() {
			return ReassemblyChamberRecipes.instance();
		}

		@Override
		public Class<? extends GuiContainer> getGuiClass() {
			return GuiSmeltingBlock.ReassemblyChamber.class;
		}

		@Override
		public String getRecipeName() {
			return FontHelper.translate("tile.ReassemblyChamber.name");
		}

		@Override
		public String getGuiTexture() {
			return "Calculator:textures/gui/nei/restorationchamber.png";
		}

		@Override
		public String getOverlayIdentifier() {
			return "reassembly";
		}

	}

	public static class RestorationChamber extends AbstractProcessorHandler {

		@Override
		public RecipeHelper recipeHelper() {
			return RestorationChamberRecipes.instance();
		}

		@Override
		public Class<? extends GuiContainer> getGuiClass() {
			return GuiSmeltingBlock.RestorationChamber.class;
		}

		@Override
		public String getRecipeName() {
			return FontHelper.translate("tile.RestorationChamber.name");
		}

		@Override
		public String getGuiTexture() {
			return "Calculator:textures/gui/nei/restorationchamber.png";
		}

		@Override
		public String getOverlayIdentifier() {
			return "restoration";
		}

	}

	public static class ReinforcedFurnace extends TemplateRecipeHandler {

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
			return FontHelper.translate("tile.ReinforcedFurnace.name");
		}

		@Override
		public String getGuiTexture() {
			return null;
		}
	}

}
