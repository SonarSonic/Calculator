package sonar.calculator.mod.client.gui.machines;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import sonar.calculator.mod.common.containers.ContainerPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityPowerCube;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GuiPowerCube extends GuiSonarTile {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/guipowercube.png");

	public TileEntityPowerCube entity;

	public GuiPowerCube(InventoryPlayer inventoryPlayer, TileEntityPowerCube entity) {
		super(new ContainerPowerCube(inventoryPlayer, entity), entity);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 6, 0);
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 64, 2);
        if (x > guiLeft + 130 && x < guiLeft + 144 && y > guiTop + 60 && y < guiTop + 74) {
            ArrayList<String> list = new ArrayList<>();
			DecimalFormat df = new DecimalFormat("#.##");
			list.add(TextFormatting.BLUE + "" + TextFormatting.UNDERLINE + "Machine Stats");
			list.add("Stored: " + entity.storage.getEnergyStored() + " RF");
			list.add("Max Input: " + df.format(entity.storage.getMaxReceive()) + " rf/t");
			list.add("Max Output: " + df.format(entity instanceof TileEntityAdvancedPowerCube ? entity.storage.getMaxExtract() : 0) + " rf/t");
           drawSpecialToolTip(list, x, y, fontRenderer);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		super.drawGuiContainerBackgroundLayer(var1, var2, var3);
		int k = this.entity.storage.getEnergyStored() * 78 / this.entity.storage.getMaxEnergyStored();
		int j = 78 - k;
		drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 63, 176, 0, k, 10);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
