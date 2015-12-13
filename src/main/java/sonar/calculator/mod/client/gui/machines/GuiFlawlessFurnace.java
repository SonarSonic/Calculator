package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.containers.ContainerFlawlessFurnace;
import sonar.calculator.mod.common.containers.ContainerPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessFurnace;
import sonar.calculator.mod.common.tileentity.machines.TileEntityPowerCube;
import sonar.core.utils.helpers.FontHelper;

public class GuiFlawlessFurnace extends GuiContainer {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/flawlessFurnace.png");

	public TileEntityFlawlessFurnace entity;

	public GuiFlawlessFurnace(InventoryPlayer inventoryPlayer, TileEntityFlawlessFurnace entity) {
		super(new ContainerFlawlessFurnace(inventoryPlayer, entity));

		this.entity = entity;

		this.xSize = 176;
		this.ySize = 242;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		FontHelper.textCentre(FontHelper.translate(entity.getInventoryName()), xSize, 6, 0);
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 143, 2);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int slotID = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int k = this.entity.cookTime[slotID] * 20 / this.entity.speed;
				drawTexturedModalRect(this.guiLeft + 12 + j * 56, this.guiTop + 49 + i * 40, 176, 0, k, 4);
				// addSlotToContainer(new Slot(entity, slotID, 14 + j * 56, 19 + i * 40));
				// addSlotToContainer(new Slot(entity, slotID+9, 38 + j * 56, 11 + i * 40));
				// addSlotToContainer(new Slot(entity, slotID+18, 38 + j * 56, 31 + i * 40));
				slotID++;
			}
		}
		 int length = this.entity.storage.getEnergyStored() * 78 / this.entity.storage.getMaxEnergyStored();
		 drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 142, 176, 4, length, 10);
	}
}
