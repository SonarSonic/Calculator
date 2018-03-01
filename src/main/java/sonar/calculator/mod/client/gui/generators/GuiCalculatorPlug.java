package sonar.calculator.mod.client.gui.generators;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerCalculatorPlug;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;

public class GuiCalculatorPlug extends GuiSonarTile {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/guicalculatorplug.png");

	public TileEntityCalculatorPlug entity;

	public GuiCalculatorPlug(InventoryPlayer inventoryPlayer, TileEntityCalculatorPlug entity) {
		super(new ContainerCalculatorPlug(inventoryPlayer, entity), entity);
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate("tile.CalculatorPlug.name"), xSize, 6, 0);
		FontHelper.textCentre(getString(entity.stable.getObject()), xSize, 60, 0);
	}

	public static String getString(int stable) {
		switch (stable) {
		case 0:
			return FontHelper.translate("circuit.stable") + ": " + FontHelper.translate("circuit.noStability");
		case 1:
			return FontHelper.translate("circuit.stable") + ": " + FontHelper.translate("locator.false");
		case 2:
			return FontHelper.translate("circuit.stable") + ": " + FontHelper.translate("locator.true");
		}
		return FontHelper.translate("circuit.stable") + ": " + FontHelper.translate("locator.unknown");
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
