package sonar.calculator.mod.client.gui.machines;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerAnalysingChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;

public class GuiAnalysingChamber extends GuiSonarTile {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/analysischamber.png");
	public TileEntityAnalysingChamber entity;

	public GuiAnalysingChamber(InventoryPlayer inventoryPlayer, TileEntityAnalysingChamber entity) {
		super(new ContainerAnalysingChamber(inventoryPlayer, entity), entity);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 64, 2);

		if (this.entity.stable.getObject() == 1) {
			FontHelper.textCentre(FontHelper.translate("circuit.stable"), xSize, 12, 0);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int x, int y) {
		super.drawGuiContainerBackgroundLayer(var1, x, y);
		int k = this.entity.storage.getEnergyStored() * 78 / this.entity.storage.getMaxEnergyStored();
		int j = 78 - k;
		drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 63, 176, 0, k, 10);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
