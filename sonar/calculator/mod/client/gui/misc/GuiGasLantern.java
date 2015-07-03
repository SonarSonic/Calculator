package sonar.calculator.mod.client.gui.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerLantern;
import sonar.calculator.mod.common.tileentity.misc.TileEntityGasLantern;
import sonar.core.utils.helpers.FontHelper;



public class GuiGasLantern
  extends GuiContainer
{
  public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/guicalculatorplug.png");
  
  public TileEntityGasLantern entity;
  

  public GuiGasLantern(InventoryPlayer inventoryPlayer, TileEntityGasLantern entity)
  {
    super(new ContainerLantern(inventoryPlayer, entity));
    

    this.entity = entity;
    

    this.xSize = 176;
    this.ySize = 166;
  }
  



  @Override
public void drawGuiContainerForegroundLayer(int par1, int par2)
  {
	  FontHelper.textCentre(FontHelper.translate(entity.getInventoryName()), xSize, 6, 0);
    if(entity.burnTime>0 && this.entity.maxBurnTime!=0){
    String burn = FontHelper.translate("co2.burnt")+": " + this.entity.burnTime*100/this.entity.maxBurnTime;
    FontHelper.textCentre(burn, xSize, 60, 0);
    }
    else{
    	String burn = FontHelper.translate("co2.burning");
    	 FontHelper.textCentre(burn, xSize, 60, 0);
    }
    }
  
  



  @Override
protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
  {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    

    Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
  }
}
