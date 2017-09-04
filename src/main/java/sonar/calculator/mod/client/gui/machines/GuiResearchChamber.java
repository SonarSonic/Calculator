package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sonar.calculator.mod.common.containers.ContainerResearchChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.calculator.mod.research.ClientResearch;
import sonar.calculator.mod.research.types.ResearchTypes;
import sonar.core.helpers.FontHelper;

public class GuiResearchChamber extends GuiContainer {
	public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/researchchamber.png");

	public TileEntityResearchChamber entity;

	public GuiResearchChamber(EntityPlayer player, TileEntityResearchChamber entity) {
		super(new ContainerResearchChamber(player, entity));
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
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}
