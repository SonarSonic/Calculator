package sonar.calculator.mod.client.gui.misc;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerModuleWorkstation;
import sonar.calculator.mod.common.tileentity.machines.TileEntityModuleWorkstation;
import sonar.core.client.gui.GuiSonar;
import sonar.core.helpers.FontHelper;

public class GuiModuleWorkstation extends GuiSonar {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/module_workstation.png");

	public TileEntityModuleWorkstation entity;

	public GuiModuleWorkstation(InventoryPlayer inv, TileEntityModuleWorkstation entity) {
		super(new ContainerModuleWorkstation(inv, entity), entity);
		this.entity = (TileEntityModuleWorkstation) entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize+10, 11, 0);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}

}
