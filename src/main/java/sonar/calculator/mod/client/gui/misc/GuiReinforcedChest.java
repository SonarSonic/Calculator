package sonar.calculator.mod.client.gui.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerReinforcedChest;
import sonar.calculator.mod.common.tileentity.misc.TileEntityReinforcedChest;
import sonar.core.client.gui.GuiSonar;
import sonar.core.helpers.FontHelper;

public class GuiReinforcedChest extends GuiSonar {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/assimilator.png");

	public TileEntityReinforcedChest entity;

	public GuiReinforcedChest(EntityPlayer player, TileEntityReinforcedChest entity) {
		super(new ContainerReinforcedChest(player, entity), entity);
		this.entity = (TileEntityReinforcedChest) entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 8, 0);
		super.drawGuiContainerForegroundLayer(par1, par2);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
