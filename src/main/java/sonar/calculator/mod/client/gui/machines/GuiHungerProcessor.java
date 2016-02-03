package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerHungerProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHungerProcessor;
import sonar.core.utils.helpers.FontHelper;

public class GuiHungerProcessor extends GuiContainer {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/hungerprocessor.png");

	public TileEntityHungerProcessor entity;

	public GuiHungerProcessor(InventoryPlayer inventoryPlayer, TileEntityHungerProcessor entity) {
		super(new ContainerHungerProcessor(inventoryPlayer, entity));
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		FontHelper.textCentre(FontHelper.translate(entity.getInventoryName()), xSize, 6, 0);
		String points = FontHelper.translate("points.hunger") + ": " + this.entity.storedpoints;
		FontHelper.textCentre(points, xSize, 60, 0);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}
