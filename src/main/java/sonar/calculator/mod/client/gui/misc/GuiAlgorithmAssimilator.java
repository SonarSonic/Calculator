package sonar.calculator.mod.client.gui.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerAlgorithmAssimilator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.core.client.gui.GuiSonar;
import sonar.core.helpers.FontHelper;

public class GuiAlgorithmAssimilator extends GuiSonar {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/assimilator.png");

	public TileEntityAssimilator.Algorithm entity;

	public GuiAlgorithmAssimilator(EntityPlayer player, TileEntityAssimilator entity) {
		super(new ContainerAlgorithmAssimilator(player, entity), entity);
		this.entity = (TileEntityAssimilator.Algorithm) entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 8, 0);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}

}
