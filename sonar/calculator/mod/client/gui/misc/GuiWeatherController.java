package sonar.calculator.mod.client.gui.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.containers.ContainerWeatherController;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherController;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.network.packets.PacketFluxPoint;
import sonar.calculator.mod.network.packets.PacketMachineButton;
import sonar.core.utils.helpers.FontHelper;

public class GuiWeatherController extends GuiContainer {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/guiWeatherController.png");

	public TileEntityWeatherController entity;

	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButton(1, guiLeft + (xSize/2-(60/2))-25, guiTop + 18, 60, 20, getTypeIdentifier(entity.type)));
		this.buttonList.add(new GuiButton(2, guiLeft + (xSize/2-(40/2))+35, guiTop + 18, 40, 20, entity.type==0 ?(entity.data==0?"Day":"Night") :(entity.data==0?"Off":"On") ));
	}

	public String getTypeIdentifier(int type) {
		switch (type) {
		case 0:
			return "Time";
		case 1:
			return "Rain";
		case 2:
			return "Thunder";
		}
		return null;

	}

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
			Calculator.network.sendToServer(new PacketMachineButton(1 + type, entity.xCoord, entity.yCoord, entity.zCoord));
			break;
		case 2:
			if (entity.data == 1) {
				entity.data = 0;
			} else {
				entity.data = 1;
			}
			Calculator.network.sendToServer(new PacketMachineButton(0, entity.xCoord, entity.yCoord, entity.zCoord));
			break;
		}
		this.buttonList.clear();
		this.initGui();

	}

	public GuiWeatherController(InventoryPlayer inventoryPlayer, TileEntityWeatherController entity) {
		super(new ContainerWeatherController(inventoryPlayer, entity));

		this.entity = entity;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		FontHelper.textCentre(FontHelper.translate(entity.getInventoryName()), xSize, 6, 0);
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 64, 2);
		FontHelper.textCentre("Buffer: " + this.entity.buffer * 100 / 100 + "%", this.xSize, 45, 0);
		FontHelper.text(": ", 97, 24, 0);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int k = this.entity.storage.getEnergyStored() * 78 / this.entity.storage.getMaxEnergyStored();
		drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 63, 176, 0, k, 10);

		int b = this.entity.buffer * 120 / 100;
		drawTexturedModalRect(this.guiLeft + 28, this.guiTop + 44, 0, 166, b, 10);
	}
}
