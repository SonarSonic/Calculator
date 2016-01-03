package sonar.calculator.mod.client.gui.misc;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerFluxController;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.utils.FluxNetwork;
import sonar.core.SonarCore;
import sonar.core.inventory.SonarButtons;
import sonar.core.inventory.SonarButtons.SonarButton;
import sonar.core.network.PacketMachineButton;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiFluxController extends GuiFlux {

	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/fluxController.png");

	public TileEntityFluxController entity;

	private GuiButton recieveMode, sendMode;

	public GuiFluxController(InventoryPlayer inventoryPlayer, TileEntityFluxController entity) {
		super(new ContainerFluxController(inventoryPlayer, entity), entity, inventoryPlayer.player);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		if (!network()) {
			FontHelper.text(FontHelper.translate(entity.blockType.getLocalizedName()), 6, 8, 0);
			FontHelper.text("R: " + getReceiveString(entity.recieveMode), 10, 27, 0);
			FontHelper.text("S: " + getSendString(entity.sendMode), 10, 37, 0);
			FontHelper.text("T: " + ((entity.transmitterMode == 1) ? FontHelper.translate("network.on") : FontHelper.translate("network.off")), 10, 47, 0);
			FontHelper.text("P: " + getNetworkType(entity.playerProtect), 10, 57, 0);
			FontHelper.text(FontHelper.translate("controller.name") + ": " + entity.playerName, 10, 70, 0);
			FontHelper.text(FontHelper.translate("controller.users") + ": ", 10, 82, 0);
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		if (!network()) {
			this.buttonList.add(new ModeButton(3, this.guiLeft + 119, this.guiTop + 24, 20, 20, "R"));
			this.buttonList.add(new ModeButton(4, this.guiLeft + 143, this.guiTop + 24, 20, 20, "S"));
			this.buttonList.add(new ModeButton(5, this.guiLeft + 119, this.guiTop + 48, 20, 20, "T"));
			this.buttonList.add(new ModeButton(6, this.guiLeft + 143, this.guiTop + 48, 20, 20, "P"));
		}
	}

	
	public String getReceiveString(int receive) {
		switch (receive) {
		case 1:
			return FontHelper.translate("controller.receive.even");
		case 2:
			return FontHelper.translate("controller.receive.surge");
		case 3:
			return FontHelper.translate("controller.receive.hyper");
		case 4:
			return FontHelper.translate("controller.receive.god");
		}
		return FontHelper.translate("controller.default");
	}

	public String getSendString(int send) {
		switch (send) {
		case 1:
			return FontHelper.translate("controller.send.large");
		case 2:
			return FontHelper.translate("controller.send.small");
		}
		return FontHelper.translate("controller.default");
	}

	@SideOnly(Side.CLIENT)
	public class ModeButton extends SonarButtons.SonarButton {

		public ModeButton(int id, int x, int y, int textureX, int textureY, String display) {
			super(id, x, y, textureX, textureY, display);
		}

		public void func_146111_b(int x, int y) {
			if (this.id == 3) {
				drawCreativeTabHoveringText(FontHelper.translate("controller.receive"), x - 20, y - 10);
			}
			if (this.id == 4) {
				drawCreativeTabHoveringText(FontHelper.translate("controller.send"), x - 20, y - 10);
			}
			if (this.id == 5) {
				drawCreativeTabHoveringText(FontHelper.translate("controller.transmitter"), x - 20, y - 10);
			}
			if (this.id == 6) {
				drawCreativeTabHoveringText(FontHelper.translate("network.protection"), x - 20, y - 10);
			}
		}

		@Override
		public void onClicked() {
			SonarCore.network.sendToServer(new PacketMachineButton(this.id, 0, entity.xCoord, entity.yCoord, entity.zCoord));
			buttonList.clear();
			initGui();
		}
	}

	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (!network()) {
			if (button != null && button instanceof SonarButtons.SonarButton) {

				SonarButton sButton = (SonarButton) button;
				sButton.onClicked();

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
		return 199;
	}
	@Override
	public void switchContainer(Container container) {
		if(container instanceof ContainerFluxController){
			((ContainerFluxController)container).switchState(player.inventory, entity);
		}
	}

	@Override
	public int getNetworkID() {
		return entity.networkID;
	}

}
