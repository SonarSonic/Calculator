package sonar.calculator.mod.client.gui.misc;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import scala.swing.event.Key;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.misc.GuiFluxPlug.ConfirmButton;
import sonar.calculator.mod.client.gui.utils.CalculatorButtons;
import sonar.calculator.mod.common.containers.ContainerCO2Generator;
import sonar.calculator.mod.common.containers.ContainerFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPlug;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPoint;
import sonar.calculator.mod.network.packets.PacketFluxPoint;
import sonar.core.utils.helpers.FontHelper;

public class GuiFluxPoint extends GuiContainer {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/fluxPoint.png");

	public TileEntityFluxPoint entity;

	private GuiTextField id, priority, transfer;

	public GuiFluxPoint(InventoryPlayer inventoryPlayer, TileEntityFluxPoint entity) {
		super(new ContainerFlux(inventoryPlayer, entity));

		this.entity = entity;

		this.xSize = 176;
		this.ySize = 142;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		priority.drawTextBox();
		id.drawTextBox();
		transfer.drawTextBox();
		FontHelper.text(StatCollector.translateToLocal(entity.blockType.getLocalizedName()), 20, 8, 0);
		FontHelper.text("ID:", 89, 8, 0);
		FontHelper.text("Priority:", 10, 28, 0);
		FontHelper.text("Max:", 82, 28, 0);
	
		FontHelper.textCentre((this.entity.networkState==1?"Protected: " :"Open: ") + entity.masterName, xSize, 47, (entity.playerState==0  ||this.entity.networkState==0 ? 0 : Color.RED.getRGB()));
	

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
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		id = new GuiTextField(this.fontRendererObj, 105, 6, 50, 12);
		id.setMaxStringLength(7);
		id.setText(String.valueOf(entity.freq));
		
		priority = new GuiTextField(this.fontRendererObj, 54, 26, 20, 12);
		priority.setMaxStringLength(2);
		priority.setText(String.valueOf(entity.priority));
		
		transfer = new GuiTextField(this.fontRendererObj, 106, 26, 58, 12);
		transfer.setMaxStringLength(8);
		transfer.setText(String.valueOf(entity.maxTransfer));
		this.buttonList.add(new ConfirmButton(guiLeft + 157, guiTop + 6));

	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		id.mouseClicked(i - guiLeft, j - guiTop, k);
		priority.mouseClicked(i - guiLeft, j - guiTop, k);
		transfer.mouseClicked(i - guiLeft, j - guiTop, k);
	}

	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (entity.getWorldObj().isRemote) {
			if (button != null && button instanceof CalculatorButtons.SonarButton) {
				final String text = id.getText();
				if (text.isEmpty() || text == "" || text == null) {
					Calculator.network.sendToServer(new PacketFluxPoint(0, entity.xCoord, entity.yCoord, entity.zCoord, 0));
				} else {
					Calculator.network.sendToServer(new PacketFluxPoint(Integer.valueOf(text), entity.xCoord, entity.yCoord, entity.zCoord, 0));
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
				typeKey(id, c, i);
				final String freq = id.getText();
				if (freq.isEmpty() || freq == "" || freq == null) {
					entity.freq = 0;
				} else {
					entity.freq = Integer.valueOf(freq);
				}
			}
		}
		else if (priority.isFocused()) {
			if (c == 13 || c == 27) {
				priority.setFocused(false);
			} else {
				typeKey(priority, c, i);
				final String order = priority.getText();
				if (order.isEmpty() || order == "" || order == null) {
					Calculator.network.sendToServer(new PacketFluxPoint(0, entity.xCoord, entity.yCoord, entity.zCoord, 1));
				} else {
					Calculator.network.sendToServer(new PacketFluxPoint(Integer.valueOf(order), entity.xCoord, entity.yCoord, entity.zCoord, 1));
				}
				if (order.isEmpty() || order == "" || order == null) {
					entity.priority = 0;
				} else {
					entity.priority = Integer.valueOf(order);
				}
			}
		}else if (transfer.isFocused()) {
			if (c == 13 || c == 27) {
				transfer.setFocused(false);
			} else {
				typeKey(transfer, c, i);
				final String order = transfer.getText();
				if (order.isEmpty() || order == "" || order == null) {
					Calculator.network.sendToServer(new PacketFluxPoint(0, entity.xCoord, entity.yCoord, entity.zCoord, 2));
				} else {
					Calculator.network.sendToServer(new PacketFluxPoint(Integer.valueOf(order), entity.xCoord, entity.yCoord, entity.zCoord, 2));
				}
				if (order.isEmpty() || order == "" || order == null) {
					entity.maxTransfer = 0;
				} else {
					entity.maxTransfer = Integer.valueOf(order);
				}
			}
		}else{		
			super.keyTyped(c, i);
		}
		

	}

	public boolean typeKey(GuiTextField box, char c, int i) {
		switch (c) {
		case '\001':
			return box.textboxKeyTyped(c, i);
		case '\003':
			return box.textboxKeyTyped(c, i);
		case '\026':
			return false;
		case '\030':
			return box.textboxKeyTyped(c, i);
		}
		switch (i) {
		case 14:
			return box.textboxKeyTyped(c, i);
		case 199:
			return box.textboxKeyTyped(c, i);
		case 203:
			return box.textboxKeyTyped(c, i);
		case 205:
			return box.textboxKeyTyped(c, i);
		case 207:
			return box.textboxKeyTyped(c, i);
		case 211:
			return box.textboxKeyTyped(c, i);
		}
		if (Character.isDigit(c)) {
			return box.textboxKeyTyped(c, i);
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
