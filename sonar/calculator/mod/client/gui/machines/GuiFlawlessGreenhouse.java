package sonar.calculator.mod.client.gui.machines;

import java.text.DecimalFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerFlawlessGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.core.utils.helpers.FontHelper;

public class GuiFlawlessGreenhouse extends GuiContainer {
	DecimalFormat dec = new DecimalFormat("##.##");
	public static final ResourceLocation bground = new ResourceLocation(
			"Calculator:textures/gui/flawlessgreenhouse.png");

	public TileEntityFlawlessGreenhouse entity;

	public GuiFlawlessGreenhouse(InventoryPlayer inventoryPlayer,
			TileEntityFlawlessGreenhouse entity2) {
		super(new ContainerFlawlessGreenhouse(inventoryPlayer, entity2));

		this.entity = entity2;

		this.xSize = 176;
		this.ySize = 192;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {

		if(entity.wasBuilt()){
			double car = (double)this.entity.carbonLevels*100 / this.entity.maxLevel;
			String carbon = dec.format(car) + "%";
			FontHelper.textOffsetCentre(carbon, 61, 79, 0);
			double oxy = (double)this.entity.getOxygen()*100  / this.entity.maxLevel;		
			String oxygen = dec.format(oxy) + "%";
		    FontHelper.textOffsetCentre(oxygen, 97, 79, 0);
			}	
		String size = FontHelper.translate("greenhouse.size")+": " +entity.houseSize;
		FontHelper.textOffsetCentre(size, 144, 10, 0);
	    
	    String grown = FontHelper.translate("greenhouse.grown")+": ";
	    FontHelper.textOffsetCentre(grown, 144, 30, 0);
	   
	    String grown2 = ""+entity.plantsGrown;
	    FontHelper.textOffsetCentre(grown2, 144, 40, 0);
	   
	    String harvest = FontHelper.translate("greenhouse.harvested")+": ";
	    FontHelper.textOffsetCentre(harvest, 144, 60, 0);
	   
	    String harvest2 = ""+entity.plantsHarvested;
	    FontHelper.textOffsetCentre(harvest2, 144, 70, 0);
	  
	
	
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);

		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize,
				this.ySize);

		int e = this.entity.storage.getEnergyStored() * 46	/ this.entity.storage.getMaxEnergyStored();
		this.drawTexturedModalRect(this.guiLeft + 27, this.guiTop + 46 + 11 - e, 176, 46 - e, 14, 46);
		
		if(entity.wasBuilt()){
		int c = this.entity.carbonLevels * 66 / this.entity.maxLevel;
		this.drawTexturedModalRect(this.guiLeft + 47, this.guiTop + 11 + 66
				- c, 190, 66 - c, 28, 66);
		
		int o = this.entity.getOxygen() * 66 / this.entity.maxLevel;
		this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 11 + 66
				- o, 218, 66 - o, 28, 66);
		}
		
	}
}
