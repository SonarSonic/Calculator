package sonar.calculator.mod.client.gui.misc;

import java.util.List;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPlug;
import sonar.calculator.mod.utils.FluxNetwork;
import sonar.core.utils.helpers.FontHelper;

public class GuiFluxPlug extends GuiFlux {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/fluxPlug.png");

	public TileEntityFluxPlug entity;

	public GuiFluxPlug(InventoryPlayer inventoryPlayer, TileEntityFluxPlug entity) {
		super(new ContainerFlux(inventoryPlayer, entity, false), entity, inventoryPlayer.player);
		this.entity = entity;	
	}


	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		if (!this.network()) {
			FontHelper.text(FontHelper.translate("plug.sending"), 10, 40, 0);
			FontHelper.text(FontHelper.translate("plug.receiving"), xSize / 2 + 8, 40, 0);
			FontHelper.text(FontHelper.formatOutput(entity.currentInput), 10, 50, 2);
			FontHelper.text(FontHelper.formatOutput(entity.currentOutput), xSize / 2 + 8, 50, 2);
			FontHelper.text(FontHelper.translate("plug.plugs") + ": " + entity.plugCount, 10, 60, 2);
			FontHelper.text(FontHelper.translate("plug.points") + ": " + entity.pointCount, xSize / 2 + 8, 60, 2);
			
			FontHelper.textCentre("Transfer : " + FontHelper.formatOutput(entity.transfer), xSize, 73, 0);
			FontHelper.textCentre("Buffer: " +FontHelper.formatOutput((int) entity.bufferStorage), xSize, 26, 0);
			
			if(entity.networkName.equals("NETWORK")){
				FontHelper.text(FontHelper.translate("network.notConnected"), 8, 9, 0);	
			}else{
				FontHelper.text(entity.networkName + ": " + getNetworkType(entity.networkState), 8, 9, 0);
			}
		}
	}

	@Override
	public List<FluxNetwork> getNetworks() {
		return entity.networks;
	}

	@Override
	public void setNetworkName(String string) {
		entity.networkName = string;

	}

	@Override
	public String getNetworkName() {
		return entity.networkName;
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
		return 166;
	}


	@Override
	public void switchContainer(Container container) {
		if(container instanceof ContainerFlux){
			((ContainerFlux)container).switchState(player.inventory, entity);
		}
	}
	@Override
	public int getNetworkID() {
		return entity.networkID;
	}
}
