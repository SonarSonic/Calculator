package sonar.calculator.mod.client.gui.machines;

import java.text.DecimalFormat;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerAtomicMultiplier;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.core.client.gui.GuiSonar;
import sonar.core.helpers.FontHelper;

public class GuiAtomicMultiplier extends GuiSonar {

	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/atomicMultiplier.png");
	public TileEntityAtomicMultiplier entity;

	public GuiAtomicMultiplier(InventoryPlayer inventoryPlayer, TileEntityAtomicMultiplier entity) {
		super(new ContainerAtomicMultiplier(inventoryPlayer, entity), entity);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		if (entity.active.getObject() == 1) {
			FontHelper.textCentre(FontHelper.translate("locator.active"), xSize, 3, 0);
		} else {
			FontHelper.textCentre(FontHelper.translate("locator.idle"), xSize, 3, 0);
		}
		FontHelper.textCentre(FontHelper.formatStorage(entity.storage.getEnergyStored()), this.xSize, 66, 2);
		if ((x > guiLeft + 2 && x < guiLeft + 16) && (y > guiTop + 62 && y < guiTop + 76)) {
			ArrayList list = new ArrayList();
			DecimalFormat df = new DecimalFormat("#.##");
			list.add(EnumChatFormatting.BLUE + "" + EnumChatFormatting.UNDERLINE + "Machine Stats");
			list.add("Usage: " + df.format(entity.getEnergyUsage()) + " rf/t");
			list.add("Speed: " + entity.getProcessTime() + " ticks");
			drawSpecialToolTip(list, x, y, fontRendererObj);
		}
		super.drawGuiContainerForegroundLayer(x, y);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		super.drawGuiContainerBackgroundLayer(var1, var2, var3);
		int changedEnergy = this.entity.storage.getEnergyStored() / 100000;
		int newEnergy = changedEnergy * 126 / 15000;
		drawTexturedModalRect(this.guiLeft + 41, this.guiTop + 65, 0, 166, newEnergy, 10);

		int c = this.entity.cookTime.getObject() * 18 / this.entity.furnaceSpeed;
		drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 20, 176, 0, c, 9);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}

}