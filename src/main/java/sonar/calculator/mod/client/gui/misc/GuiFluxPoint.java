package sonar.calculator.mod.client.gui.misc;

import java.util.List;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.containers.ContainerFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPoint;
import sonar.calculator.mod.network.packets.PacketFluxPoint;
import sonar.calculator.mod.utils.FluxNetwork;
import sonar.core.utils.helpers.FontHelper;

public class GuiFluxPoint extends GuiFlux {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/fluxPoint.png");

	public TileEntityFluxPoint entity;

	private GuiTextField priority, transfer;

	public GuiFluxPoint(InventoryPlayer inventoryPlayer, TileEntityFluxPoint entity) {
		super(new ContainerFlux(inventoryPlayer, entity, false), entity, inventoryPlayer.player);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		if (!network()) {
			priority.drawTextBox();
			transfer.drawTextBox();
			FontHelper.text(FontHelper.translate(entity.blockType.getLocalizedName()), 20, 8, 0);
			FontHelper.text(FontHelper.translate("point.priority") + ":", 6, 28, 0);
			FontHelper.text(FontHelper.translate("point.max") +":", 84, 28, 0);
			if(entity.networkName.equals("NETWORK")){
				FontHelper.textCentre(FontHelper.translate("network.notConnected"), xSize, 47, 0);	
			}else{
				FontHelper.textCentre(entity.networkName + ": " + getNetworkType(entity.networkState.getObject()), xSize, 47, 0);
			}
		}

	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		if (!network()) {
			priority = new GuiTextField(this.fontRendererObj, 50, 26, 30, 12);
			priority.setMaxStringLength(3);
			priority.setText(String.valueOf(entity.priority));

			transfer = new GuiTextField(this.fontRendererObj, 110, 26, 58, 12);
			transfer.setMaxStringLength(8);
			transfer.setText(String.valueOf(entity.maxTransfer));
		}
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		if (!network()) {
			priority.mouseClicked(i - guiLeft, j - guiTop, k);
			transfer.mouseClicked(i - guiLeft, j - guiTop, k);
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		if (!network() && priority.isFocused()) {
			if (c == 13 || c == 27) {
				priority.setFocused(false);
			} else {
				FontHelper.addDigitsToString(priority, c, i);
				final String order = priority.getText();
				if (order.isEmpty() || order == "" || order == null) {
					Calculator.network.sendToServer(new PacketFluxPoint(String.valueOf(0), entity.xCoord, entity.yCoord, entity.zCoord, 1));
				} else {
					Calculator.network.sendToServer(new PacketFluxPoint(order, entity.xCoord, entity.yCoord, entity.zCoord, 1));
				}
				if (order.isEmpty() || order == "" || order == null) {
					entity.priority = 0;
				} else {
					entity.priority = Integer.valueOf(order);
				}
			}
		} else 	if (!network() && transfer.isFocused()) {
			if (c == 13 || c == 27) {
				transfer.setFocused(false);
			} else {
				FontHelper.addDigitsToString(transfer, c, i);
				final String order = transfer.getText();
				
				if (order.isEmpty() || order == "" || order == null) {
					Calculator.network.sendToServer(new PacketFluxPoint(String.valueOf(0), entity.xCoord, entity.yCoord, entity.zCoord, 2));
				} else {
					Calculator.network.sendToServer(new PacketFluxPoint(order, entity.xCoord, entity.yCoord, entity.zCoord, 2));
				}
				
				if (order.isEmpty() || order == "" || order == null) {
					entity.maxTransfer = 0;
				} else {
					entity.maxTransfer = Integer.valueOf(order);
				}
			}
		} else {
			super.keyTyped(c, i);
		}

	}

	@Override
	public List<FluxNetwork> getNetworks() {
		return entity.networks;
	}

	@Override
	public void setNetworkName(String string) {
		entity.networkName.setObject(string);

	}

	@Override
	public String getNetworkName() {
		return entity.networkName.getObject();
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}

	@Override
	public int xSize() {
		return 176;
	}

	@Override
	public int ySize() {
		return 142;
	}

	@Override
	public void switchContainer(Container container) {
		if(container instanceof ContainerFlux){
			((ContainerFlux)container).switchState(player.inventory, entity);
		}
	}
	@Override
	public int getNetworkID() {
		return entity.networkID();
	}
}
