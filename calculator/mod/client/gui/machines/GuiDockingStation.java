package sonar.calculator.mod.client.gui.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.containers.ContainerDockingStation;
import sonar.calculator.mod.common.tileentity.machines.TileEntityDockingStation;
import sonar.core.utils.helpers.FontHelper;

public class GuiDockingStation extends GuiContainer
{
  public static final ResourceLocation emptyTex = new ResourceLocation("Calculator:textures/gui/dockingstation/empty.png");
  public static final ResourceLocation calculatorTex = new ResourceLocation("Calculator:textures/gui/dockingstation/calculator.png");
  public static final ResourceLocation scientificTex = new ResourceLocation("Calculator:textures/gui/dockingstation/scientific.png");
  public static final ResourceLocation atomicTex = new ResourceLocation("Calculator:textures/gui/dockingstation/atomic.png");
  public static final ResourceLocation flawlessTex = new ResourceLocation("Calculator:textures/gui/dockingstation/flawless.png");
  
  public TileEntityDockingStation entity;
  

  public GuiDockingStation(InventoryPlayer inventoryPlayer, TileEntityDockingStation entity)
  {
    super(new ContainerDockingStation(inventoryPlayer, entity));
    

    this.entity = entity;
    

    this.xSize = 176;
    this.ySize = 166;
  }
  




  @Override
public void drawGuiContainerForegroundLayer(int par1, int par2)
  {
	String name = null;
	switch(entity.type){
	case 0: name = "Docking Station";break;
	case 1: name = StatCollector.translateToLocal("item.Calculator.name");break;
	case 2: name = StatCollector.translateToLocal("item.ScientificCalculator.name");break;
	case 3: name = StatCollector.translateToLocal("item.AtomicCalculator.name");break;
	case 4: name = StatCollector.translateToLocal("item.FlawlessCalculator.name");break;
	}
	FontHelper.textCentre(name, xSize, 6, 0);
	
	
	
	String power= null;
    switch(CalculatorConfig.energyStorageType){
	case 1: power = this.entity.storage.getEnergyStored() + " RF";	break;
	case 2: power = (this.entity.storage.getEnergyStored()/4) + " EU";	break;
	}
    FontHelper.textCentre(power, xSize, 64, 2);
    
  }
  

  @Override
protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
  {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    
    switch(entity.type){
    case 0:  Minecraft.getMinecraft().getTextureManager().bindTexture(emptyTex); break;
    case 1:  Minecraft.getMinecraft().getTextureManager().bindTexture(calculatorTex); break;
    case 2:  Minecraft.getMinecraft().getTextureManager().bindTexture(scientificTex); break;
    case 3:  Minecraft.getMinecraft().getTextureManager().bindTexture(atomicTex); break;
    case 4:  Minecraft.getMinecraft().getTextureManager().bindTexture(flawlessTex); break;
    }
    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    
    int k = this.entity.storage.getEnergyStored() * 78 / this.entity.storage.getMaxEnergyStored();
    int j = 78 - k;
    drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 63, 176, 0, k, 10);
  }
}
