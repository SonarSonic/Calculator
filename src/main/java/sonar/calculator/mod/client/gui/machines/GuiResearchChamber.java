package sonar.calculator.mod.client.gui.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.containers.ContainerResearchChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.calculator.mod.research.ClientResearch;
import sonar.calculator.mod.research.types.ResearchTypes;
import sonar.core.client.gui.GuiSonarTile;
import sonar.core.helpers.FontHelper;

public class GuiResearchChamber extends GuiSonarTile {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/researchchamber.png");

	public TileEntityResearchChamber entity;

	public GuiResearchChamber(EntityPlayer player, TileEntityResearchChamber entity) {
		super(new ContainerResearchChamber(player, entity), entity);
		this.entity = entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		FontHelper.textCentre(FontHelper.translate(entity.getName()), xSize, 6, 0);
		if (entity.ticks.getObject() != 0 && entity.ticks.getObject() != -1) {
            FontHelper.textCentre(String.valueOf(this.entity.ticks.getObject() * 100 / TileEntityResearchChamber.researchSpeed) + " %", xSize, 55, 0);
		}
        FontHelper.textCentre(ClientResearch.getSpecificResearch(ResearchTypes.RECIPES).getProgress() + " %", xSize, 14, 0);
	}

	@Override
	public ResourceLocation getBackground() {
		return bground;
	}
}
