package sonar.calculator.mod.client.gui.misc;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerLantern;
import sonar.calculator.mod.common.tileentity.misc.TileEntityGasLantern;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;

public class GuiGasLantern extends GuiSonarTile {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/guicalculatorplug.png");

	public TileEntityGasLantern entity;

	public GuiGasLantern(InventoryPlayer inventoryPlayer, TileEntityGasLantern entity) {
		super(new ContainerLantern(inventoryPlayer, entity), entity);
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 6, 0);
		if (entity.burnTime.getObject() > 0 && entity.maxBurnTime.getObject() != 0) {
			String burn = FontHelper.translate("co2.burnt") + ": " + entity.burnTime.getObject() * 100 / entity.maxBurnTime.getObject();
			FontHelper.textCentre(burn, xSize, 60, 0);
		} else {
			String burn = FontHelper.translate("co2.burning");
			FontHelper.textCentre(burn, xSize, 60, 0);
		}
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
