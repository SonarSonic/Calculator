package sonar.calculator.mod.client.gui.utils;

import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.Calculator;
import sonar.core.network.PacketMachineButton;
import sonar.core.network.SonarPackets;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class GuiSonar extends GuiContainer {

	public int x, y, z;

	public GuiSonar(Container container, TileEntity entity) {
		super(container);
		this.x = entity.xCoord;
		this.y = entity.yCoord;
		this.z = entity.zCoord;
	}

	public abstract ResourceLocation getBackground();

	public void reset() {
		this.buttonList.clear();
		this.initGui();
	}

	public void initGui(boolean pause) {

	}

	protected void drawGuiContainerForegroundLayer(int x, int y) {

		RenderHelper.disableStandardItemLighting();
		Iterator iterator = this.buttonList.iterator();

		while (iterator.hasNext()) {
			GuiButton guibutton = (GuiButton) iterator.next();

			if (guibutton.func_146115_a()) {
				guibutton.func_146111_b(x - this.guiLeft, y - this.guiTop);
				break;
			}
		}
		RenderHelper.enableGUIStandardItemLighting();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.getBackground());
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@SideOnly(Side.CLIENT)
	public class PauseButton extends CalculatorButtons.ImageButton {

		boolean paused;
		public int id;

		public PauseButton(int id, int x, int y, boolean paused) {
			super(id, x, y, new ResourceLocation("Calculator:textures/gui/buttons/buttons.png"), paused ? 51 : 34, 0, 16, 16);
			this.paused = paused;
			this.id = id;
		}

		public void func_146111_b(int x, int y) {
			if (paused) {
				drawCreativeTabHoveringText(FontHelper.translate("buttons.resume"), x, y);
			} else {
				drawCreativeTabHoveringText(FontHelper.translate("buttons.pause"), x, y);
			}
		}

		@Override
		public void onClicked() {
			SonarPackets.network.sendToServer(new PacketMachineButton(id, 0, x, y, z));
			buttonList.clear();
			initGui(!paused);
			updateScreen();
		}
	}

	@SideOnly(Side.CLIENT)
	public class CircuitButton extends CalculatorButtons.ImageButton {
		public int id;

		public CircuitButton(int id, int x, int y) {
			super(id, x, y, new ResourceLocation("Calculator:textures/gui/buttons/buttons.png"), 0, 0, 16, 16);
			this.id = id;
		}

		public void func_146111_b(int x, int y) {
			drawCreativeTabHoveringText(FontHelper.translate("buttons.circuits"), x, y);
		}

		@Override
		public void onClicked() {
			SonarPackets.network.sendToServer(new PacketMachineButton(id, 0, x, y, z));
		}
	}

}
