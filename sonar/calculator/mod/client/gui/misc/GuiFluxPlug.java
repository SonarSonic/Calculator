package sonar.calculator.mod.client.gui.misc;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons.SonarButton;
import sonar.calculator.mod.client.gui.utils.GuiButtons.CircuitButton;
import sonar.calculator.mod.common.containers.ContainerFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPlug;
import sonar.calculator.mod.network.packets.PacketFluxPoint;
import sonar.calculator.mod.network.packets.PacketMachineButton;
import sonar.core.utils.helpers.FontHelper;

public class GuiFluxPlug extends GuiContainer {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/fluxPlug.png");

	public TileEntityFluxPlug entity;

	private GuiTextField id;

	public GuiFluxPlug(InventoryPlayer inventoryPlayer, TileEntityFluxPlug entity) {
		super(new ContainerFlux(inventoryPlayer, entity));
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		id.drawTextBox();
		FontHelper.text(StatCollector.translateToLocal(entity.blockType.getLocalizedName()), 20, 8, 0);
		FontHelper.text("ID:", 89, 8, 0);
		FontHelper.text("Sending", 10, 40, 0);
		FontHelper.text("Recieving", xSize / 2 + 8, 40, 0);
		FontHelper.text(FontHelper.formatOutput(entity.currentInput), 10, 50, 2);
		FontHelper.text(FontHelper.formatOutput(entity.currentOutput), xSize / 2 + 8, 50, 2);
		FontHelper.text("Plugs: " + entity.plugCount, 10, 60, 2);
		FontHelper.text("Points: " + entity.pointCount, xSize / 2 + 8, 60, 2);
		FontHelper.textCentre("Transfer: " + FontHelper.formatOutput(entity.transfer), xSize, 26, 0);
		FontHelper.textCentre((this.entity.networkState==1?"Protected: " :"Open: ") + entity.masterName, xSize, 73, (entity.playerState==0 ||this.entity.networkState==0 ? 0 : Color.RED.getRGB()));

	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		id = new GuiTextField(this.fontRendererObj, 105, 6, 50, 12);
		id.setMaxStringLength(7);
		id.setText(String.valueOf(entity.freq));
		this.buttonList.add(new ConfirmButton(guiLeft + 157, guiTop + 6));

	}

	@SideOnly(Side.CLIENT)
	public class ConfirmButton extends CalculatorButtons.ImageButton {

		public ConfirmButton(int x, int y) {
			super(1, x, y, new ResourceLocation("Calculator:textures/gui/buttons/buttons.png"), 68, 0, 16, 16);
		}

		public void func_146111_b(int x, int y) {
			drawCreativeTabHoveringText(StatCollector.translateToLocal("buttons.circuits"), x, y);
		}

		@Override
		public void onClicked() {
		}
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		id.mouseClicked(i - guiLeft, j - guiTop, k);
		// max.mouseClicked(i - guiLeft, j - guiTop, k);
	}

	protected void actionPerformed(GuiButton button) {
		if (entity.getWorldObj().isRemote) {
			if (button != null && button instanceof CalculatorButtons.SonarButton) {
				final String text = id.getText();
				if (text.isEmpty() || text == "" || text == null) {
					Calculator.network.sendToServer(new PacketFluxPoint(0, entity.xCoord, entity.yCoord, entity.zCoord,0));
				} else {
					Calculator.network.sendToServer(new PacketFluxPoint(Integer.valueOf(text), entity.xCoord, entity.yCoord, entity.zCoord,0));
				}
			}
		}
	}

	@Override
	protected void keyTyped(char c, int i) {

		if (id.isFocused()) {
			if (c == 13 || c == 27) {
				id.setFocused(false);
			} else {
				typeKey(c, i);
				final String text = id.getText();
				if (text.isEmpty() || text == "" || text == null) {
					entity.freq = 0;
				} else {
					entity.freq = Integer.valueOf(text);
				}
			}
		} else {
			super.keyTyped(c, i);
		}

	}

	public boolean typeKey(char c, int i) {
		switch (c) {
		case '\001':
			return id.textboxKeyTyped(c, i);
		case '\003':
			return id.textboxKeyTyped(c, i);
		case '\026':
			return false;
		case '\030':
			return id.textboxKeyTyped(c, i);
		}
		switch (i) {
		case 14:
			return id.textboxKeyTyped(c, i);
		case 199:
			return id.textboxKeyTyped(c, i);
		case 203:
			return id.textboxKeyTyped(c, i);
		case 205:
			return id.textboxKeyTyped(c, i);
		case 207:
			return id.textboxKeyTyped(c, i);
		case 211:
			return id.textboxKeyTyped(c, i);
		}
		if (Character.isDigit(c)) {
			return id.textboxKeyTyped(c, i);
		}
		return false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

	}
}
