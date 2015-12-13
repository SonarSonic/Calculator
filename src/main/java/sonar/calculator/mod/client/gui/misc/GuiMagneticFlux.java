package sonar.calculator.mod.client.gui.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.containers.ContainerMagneticFlux;
import sonar.calculator.mod.common.containers.ContainerPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityPowerCube;
import sonar.calculator.mod.common.tileentity.misc.TileEntityMagneticFlux;
import sonar.core.inventory.GuiSonar;
import sonar.core.network.PacketMachineButton;
import sonar.core.network.SonarPackets;
import sonar.core.utils.helpers.FontHelper;

public class GuiMagneticFlux extends GuiSonar {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/magnetic_flux.png");

	public TileEntityMagneticFlux entity;

	public GuiMagneticFlux(InventoryPlayer inventoryPlayer, TileEntityMagneticFlux entity) {
		super(new ContainerMagneticFlux(inventoryPlayer, entity), entity);

		this.entity = entity;

		this.xSize = 176;
		this.ySize = 166;
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
			entity.buttonPress(button.id, 0);
			SonarPackets.network.sendToServer(new PacketMachineButton(button.id, 0, entity.xCoord, entity.yCoord, entity.zCoord));			
			this.reset();
		}
		
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		FontHelper.textCentre(FontHelper.translate(entity.getInventoryName()), xSize, 6, 0);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
