package sonar.calculator.mod.client.gui.machines;
/*
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerFlawlessFurnace;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessFurnace;
import sonar.core.helpers.FontHelper;

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
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 6, 0);
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
				int k = this.entity.cookTime[slotID].getObject() * 20 / this.entity.speed;
				drawTexturedModalRect(this.guiLeft + 12 + j * 56, this.guiTop + 49 + i * 40, 176, 0, k, 4);
				slotID++;
			}
		}
		 int length = this.entity.storage.getEnergyStored() * 78 / this.entity.storage.getMaxEnergyStored();
		 drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 142, 176, 4, length, 10);
	}
}
*/