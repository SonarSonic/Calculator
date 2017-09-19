package sonar.calculator.mod.client.gui.generators;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerCrankedGenerator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankedGenerator;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;

public class GuiCrankedGenerator extends GuiSonarTile {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/guicrank.png");

	public TileEntityCrankedGenerator entity;

	public GuiCrankedGenerator(InventoryPlayer inventoryPlayer, TileEntityCrankedGenerator entity) {
		super(new ContainerCrankedGenerator(inventoryPlayer, entity), entity);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate("tile.HandCrankedGenerator.name"), this.xSize, 6, 0);
		if (this.entity.cranked()) {
			FontHelper.textCentre(FontHelper.translate(FontHelper.translate("crank.cranked") + ": " + FontHelper.translate("locator.true")), this.xSize, 50, 0);
		}
		if (!this.entity.cranked()) {
			FontHelper.textCentre(FontHelper.translate(FontHelper.translate("crank.cranked") + ": " + FontHelper.translate("locator.false")), this.xSize, 50, 0);
		}
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 64, 2);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int x, int y) {
		super.drawGuiContainerBackgroundLayer(var1, x, y);

		int k = this.entity.storage.getEnergyStored() * 78 / 1000;
		int j = 78 - k;
		drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 63, 176, 0, k, 10);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
