package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerStorageChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.core.helpers.FontHelper;

public class GuiStorageChamber extends GuiContainer {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/storage_chamber.png");

	public TileEntityStorageChamber entity;

	public GuiStorageChamber(InventoryPlayer inventoryPlayer, TileEntityStorageChamber entity) {
		super(new ContainerStorageChamber(inventoryPlayer, entity));
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 183;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		String string = FontHelper.translate("circuit.type") + ": ";

		if (((TileEntityStorageChamber.StorageChamberInventory) entity.inv).getSavedStack() != null) {
			switch (TileEntityStorageChamber.getCircuitValue(TileEntityStorageChamber.getCircuitType(((TileEntityStorageChamber.StorageChamberInventory) entity.inv).getSavedStack()))) {
			case 1:
				string = string + FontHelper.translate("circuit.analysed");
				break;
			case 2:
				string = string + FontHelper.translate("circuit.stable");
				break;

			case 3:
				string = string + FontHelper.translate("circuit.damaged");
				break;

			case 4:
				string = string + FontHelper.translate("circuit.dirty");
				break;
			}
		} else {
			string = string + FontHelper.translate("locator.none");
		}
		FontHelper.textCentre(string, xSize, 8, 0);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}
