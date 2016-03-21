package sonar.calculator.mod.client.gui.misc;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerMagneticFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityMagneticFlux;
import sonar.core.SonarCore;
import sonar.core.inventory.GuiSonar;
import sonar.core.network.PacketByteBufServer;
import sonar.core.utils.helpers.FontHelper;

public class GuiMagneticFlux extends GuiSonar {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/magnetic_flux.png");

	public TileEntityMagneticFlux entity;

	public GuiMagneticFlux(InventoryPlayer inventoryPlayer, TileEntityMagneticFlux entity) {
		super(new ContainerMagneticFlux(inventoryPlayer, entity), entity);
		this.entity = entity;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButton(0, guiLeft + (xSize / 2 - (80 / 2)), guiTop + 18, 80, 20, entity.whitelisted ? "Whitelist" : "Blacklist"));
		this.buttonList.add(new GuiButton(1, guiLeft + (xSize / 2 - (80 / 2)), guiTop + 18*2, 80, 20, entity.exact ? "Exact = No" : "Exact = Yes"));
	}

	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button == null) {
			return;
		}
		if (button instanceof GuiButton) {
			SonarCore.network.sendToServer(new PacketByteBufServer(entity, entity.getPos(), button.id));		
			this.reset();
		}
		
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 6, 0);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
