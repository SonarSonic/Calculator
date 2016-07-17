package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerHealthProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHealthProcessor;
import sonar.core.client.gui.GuiSonar;
import sonar.core.helpers.FontHelper;

public class GuiHealthProcessor extends GuiSonar {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/hungerprocessor.png");

	public TileEntityHealthProcessor entity;

	public GuiHealthProcessor(InventoryPlayer inventoryPlayer, TileEntityHealthProcessor entity) {
		super(new ContainerHealthProcessor(inventoryPlayer, entity), entity);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 6, 0);
		String points = FontHelper.translate("points.health") + ": " + this.entity.storedpoints;
		FontHelper.textCentre(points, xSize, 60, 0);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
