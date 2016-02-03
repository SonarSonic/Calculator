package sonar.calculator.mod.client.gui.generators;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerCalculatorPlug;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.core.utils.helpers.FontHelper;

public class GuiCalculatorPlug extends GuiContainer {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/guicalculatorplug.png");

	public TileEntityCalculatorPlug entity;

	public GuiCalculatorPlug(InventoryPlayer inventoryPlayer, TileEntityCalculatorPlug entity) {
		super(new ContainerCalculatorPlug(inventoryPlayer, entity));

		this.entity = entity;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		FontHelper.textCentre(FontHelper.translate(entity.getInventoryName()), xSize, 6, 0);
		FontHelper.textCentre(getString(entity.stable), xSize, 60, 0);
	}

	public static String getString(int stable) {
		switch (stable) {
		case 0:
			return FontHelper.translate("circuit.stable") + ": " + FontHelper.translate("circuit.noStability");
		case 1:
			return FontHelper.translate("circuit.stable") + ": " + FontHelper.translate("locator.false");
		case 2:
			return FontHelper.translate("circuit.stable") + ": " + FontHelper.translate("locator.true");
		}
		return FontHelper.translate("circuit.stable") + ": " + FontHelper.translate("locator.unknown");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}
