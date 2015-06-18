package sonar.calculator.mod.client.gui.utils;

import java.util.Iterator;

import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons.SonarButton;
import sonar.calculator.mod.network.packets.PacketConductorMast;
import sonar.calculator.mod.network.packets.PacketMachineButton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public abstract class GuiButtons extends GuiContainer {

	public static int circuit = 0, confirm = 1, pause = 2;
	public int x, y, z;

	public GuiButtons(Container container, int x, int y, int z) {
		super(container);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public abstract void initGui(boolean pause);

	@SideOnly(Side.CLIENT)
	public class PauseButton extends CalculatorButtons.ImageButton {

		boolean paused;

		public PauseButton(int x, int y, boolean paused) {
			super(pause, x, y, new ResourceLocation("Calculator:textures/gui/buttons/buttons.png"), paused ? 51 : 34, 0, 16, 16);
			this.paused = paused;
		}

		public void func_146111_b(int x, int y) {
			if (paused) {
				drawCreativeTabHoveringText(StatCollector.translateToLocal("buttons.resume"), x, y);
			} else {
				drawCreativeTabHoveringText(StatCollector.translateToLocal("buttons.pause"), x, y);
			}
		}

		@Override
		public void onClicked() {
			Calculator.network.sendToServer(new PacketMachineButton(pause, x, y, z));
			buttonList.clear();
			initGui(!paused);
			updateScreen();
		}
	}

	@SideOnly(Side.CLIENT)
	public class CircuitButton extends CalculatorButtons.ImageButton {

		public CircuitButton(int x, int y) {
			super(circuit, x, y, new ResourceLocation("Calculator:textures/gui/buttons/buttons.png"), 0, 0, 16, 16);
		}

		public void func_146111_b(int x, int y) {
			drawCreativeTabHoveringText(StatCollector.translateToLocal("buttons.circuits"), x, y);
		}

		@Override
		public void onClicked() {
			Calculator.network.sendToServer(new PacketMachineButton(circuit, x, y, z));
		}
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
}
