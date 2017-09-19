package sonar.calculator.mod.client.gui.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sonar.calculator.mod.common.containers.ContainerCO2Generator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;

public class GuiCO2Generator extends GuiSonarTile {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/guiCO2Generator.png");

	public TileEntityCO2Generator entity;

	public GuiCO2Generator(InventoryPlayer inventoryPlayer, TileEntityCO2Generator entity) {
		super(new ContainerCO2Generator(inventoryPlayer, entity), entity);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 6, 0);
		if (entity.burnTime > 0 && this.entity.maxBurnTime != 0 && this.entity.gasAdd == 0) {
			String burn = FontHelper.translate("co2.control");
			FontHelper.textCentre(burn, xSize, 50, 0);
        } else if (entity.burnTime > 0 && this.entity.maxBurnTime != 0) {
			String burn = FontHelper.translate("co2.burnt") + ": " + this.entity.burnTime * 100 / this.entity.maxBurnTime;
			FontHelper.textCentre(burn, xSize, 50, 0);
		} else {
			String burn = FontHelper.translate("co2.burning");
			FontHelper.textCentre(burn, xSize, 50, 0);
		}
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 64, 2);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		super.drawGuiContainerBackgroundLayer(var1, var2, var3);
		int k = this.entity.storage.getEnergyStored() * 78 / 1000000;
		int j = 78 - k;
		drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 63, 176, 0, k, 10);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
