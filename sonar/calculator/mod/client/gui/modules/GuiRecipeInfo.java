package sonar.calculator.mod.client.gui.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerInfoCalculator;
import sonar.core.utils.helpers.FontHelper;

public class GuiRecipeInfo
  extends GuiContainer
{
  private ResourceLocation texture = new ResourceLocation("Calculator:textures/gui/clean.png");
  
  public GuiRecipeInfo(EntityPlayer player, InventoryPlayer invPlayer, World world, int x, int y, int z) { 
	  super(new ContainerInfoCalculator(player, invPlayer, world, x, y, z));
    
    this.xSize = 176;
    this.ySize = 166;
  }

  @Override
protected void drawGuiContainerForegroundLayer(int i, int j) { 
	  //FontHelper.textCentre(StatCollector.translateToLocal("item.Calculator.name"), xSize, 5, 0);
	  FontHelper.textCentre(StatCollector.translateToLocal("info.all"), xSize, 12, 0);
	  FontHelper.textCentre(StatCollector.translateToLocal("research.newRecipes"), xSize, 30, 0);		  
	  FontHelper.textCentre(StatCollector.translateToLocal("info.standard"), xSize, 48, 0);	    	  
	  FontHelper.textCentre(StatCollector.translateToLocal("info.unlocked"), xSize, 66, 0);	   
	  }
 

  @Override
protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
  {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    
    Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
    
    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
  }
}
