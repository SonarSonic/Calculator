package sonar.calculator.mod.client.gui.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sonar.calculator.mod.common.containers.ContainerWeatherController;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherController;
import sonar.core.SonarCore;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;
import sonar.core.network.PacketByteBuf;

public class GuiWeatherController extends GuiSonarTile {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/guiWeatherController.png");

	public TileEntityWeatherController entity;

    @Override
	public void initGui() {
		super.initGui();
        this.buttonList.add(new GuiButton(1, guiLeft + xSize / 2 - 60 / 2 - 25, guiTop + 18, 60, 20, getTypeIdentifier(entity.type)));
        this.buttonList.add(new GuiButton(2, guiLeft + xSize / 2 - 40 / 2 + 35, guiTop + 18, 40, 20, entity.type == 0 ? entity.data == 0 ? FontHelper.translate("weather.day") : FontHelper.translate("weather.night") : entity.data == 0 ? FontHelper.translate("state.off") : FontHelper.translate("state.on")));
	}

	public static String getTypeIdentifier(int type) {
		switch (type) {
		case 0:
			return FontHelper.translate("weather.time");
		case 1:
			return FontHelper.translate("weather.rain");
		case 2:
			return FontHelper.translate("weather.thunder");
		}
		return null;
	}

    @Override
	protected void actionPerformed(GuiButton button) {
		if (button == null) {
			return;
		}
		switch (button.id) {
		case 1:
			int type = 0;
			if (entity.type < 2) {
				type = entity.type + 1;
			}
			entity.type = type;

			SonarCore.network.sendToServer(new PacketByteBuf(entity, entity.getPos(), 1 + type));
			break;
		case 2:
			if (entity.data == 1) {
				entity.data = 0;
			} else {
				entity.data = 1;
			}
			SonarCore.network.sendToServer(new PacketByteBuf(entity, entity.getPos(), 0));
			break;
		}
		this.buttonList.clear();
		this.initGui();
	}

	public GuiWeatherController(InventoryPlayer inventoryPlayer, TileEntityWeatherController entity) {
		super(new ContainerWeatherController(inventoryPlayer, entity), entity);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 6, 0);
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 64, 2);
        FontHelper.textCentre("Buffer: " + this.entity.buffer * 100 / 100 + '%', this.xSize, 45, 0);
		FontHelper.text(": ", 97, 24, 0);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float floatTicks, int x, int y) {
		super.drawGuiContainerBackgroundLayer(floatTicks, x, y);

		int k = this.entity.storage.getEnergyStored() * 78 / this.entity.storage.getMaxEnergyStored();
		drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 63, 176, 0, k, 10);

		int b = this.entity.buffer * 120 / 100;
		drawTexturedModalRect(this.guiLeft + 28, this.guiTop + 44, 0, 166, b, 10);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
