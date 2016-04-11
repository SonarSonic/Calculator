package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerStorageChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.core.helpers.FontHelper;

public class GuiStorageChamber extends GuiContainer {

	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/storage_chamber.png");

	public TileEntityStorageChamber entity;

	public GuiStorageChamber(EntityPlayer player, TileEntityStorageChamber entity) {
		super(new ContainerStorageChamber(player, entity));
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 183;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		String string = FontHelper.translate("circuit.type") + ": ";
		switch (entity.circuitType) {
		case Analysed:
			string = string + FontHelper.translate("circuit.analysed");
			break;
		case Stable:
			string = string + FontHelper.translate("circuit.stable");
			break;
		case Damaged:
			string = string + FontHelper.translate("circuit.damaged");
			break;
		case Dirty:
			string = string + FontHelper.translate("circuit.dirty");
			break;
		case None:
			string = string + FontHelper.translate("locator.none");
			break;
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
