package sonar.calculator.mod.client.gui.generators;

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
import sonar.calculator.mod.common.containers.ContainerConductorMast;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.calculator.mod.network.packets.PacketConductorMast;
import sonar.core.utils.helpers.FontHelper;



public class GuiConductorMast
  extends GuiContainer
{
  public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/conductorMast.png");
  
  public TileEntityConductorMast entity;
  

  public GuiConductorMast(InventoryPlayer inventoryPlayer, TileEntityConductorMast entity2)
  {
    super(new ContainerConductorMast(inventoryPlayer, entity2));   

    this.entity = entity2;    

    this.xSize = 176;
    this.ySize = 206;
    
  }
  public void initGui()
  {
      super.initGui();
      this.buttonList.add(new GuiButton(0, this.guiLeft + 20, this.guiTop + 60, 50, 20, StatCollector.translateToLocal("lightning.day")));
      this.buttonList.add(new GuiButton(1, this.guiLeft +108, this.guiTop + 60, 50, 20, StatCollector.translateToLocal("lightning.night")));
  }


  protected void actionPerformed(GuiButton button)
  {
	  if(button!=null){
  		if(entity.getWorldObj().isRemote){
  			Calculator.network.sendToServer(new PacketConductorMast(button.id,entity.xCoord,entity.yCoord,entity.zCoord));
  			}
	  }
  }


  @Override
public void drawGuiContainerForegroundLayer(int par1, int par2)
  {
	FontHelper.textCentre(StatCollector.translateToLocal(entity.getInventoryName()), xSize, 6, 0);
    FontHelper.textCentre(StatCollector.translateToLocal("energy.required") + " = 10000 LE", xSize, 86, 0);

    String wait = StatCollector.translateToLocal("conductor.wait")+ ": ";
    if(!(this.entity.lightningSpeed<600) &&this.entity.lightingTicks<500){
    switch(this.entity.random){
    case 0: 
        String power = wait + StatCollector.translateToLocal("conductor.msg");
        this.fontRendererObj.drawString(power, this.xSize / 2 - this.fontRendererObj.getStringWidth(power) / 2, 46, 4210752);
        break;
    case 1: 
        String power1 = wait + StatCollector.translateToLocal("conductor.msg1");
        this.fontRendererObj.drawString(power1, this.xSize / 2 - this.fontRendererObj.getStringWidth(power1) / 2, 46, 4210752);
        break;
    case 2: 
        String power2 = wait + StatCollector.translateToLocal("conductor.msg2");
        this.fontRendererObj.drawString(power2, this.xSize / 2 - this.fontRendererObj.getStringWidth(power2) / 2, 46, 4210752);
        break;
    case 3: 
        String power3 = wait + StatCollector.translateToLocal("conductor.msg3");
        this.fontRendererObj.drawString(power3, this.xSize / 2 - this.fontRendererObj.getStringWidth(power3) / 2, 46, 4210752);
        break;
    case 4: 
        String power4 = wait + StatCollector.translateToLocal("conductor.msg4");
        this.fontRendererObj.drawString(power4, this.xSize / 2 - this.fontRendererObj.getStringWidth(power4) / 2, 46, 4210752);
        break;
    case 5: 
        String power5 = wait + StatCollector.translateToLocal("conductor.msg5");
        this.fontRendererObj.drawString(power5, this.xSize / 2 - this.fontRendererObj.getStringWidth(power5) / 2, 46, 4210752);
        break;
    case 6: 
        String power6 = wait + StatCollector.translateToLocal("conductor.msg6");
        this.fontRendererObj.drawString(power6, this.xSize / 2 - this.fontRendererObj.getStringWidth(power6) / 2, 46, 4210752);
        break;
    case 7: 
        String power7 = wait + StatCollector.translateToLocal("conductor.msg7");
        this.fontRendererObj.drawString(power7, this.xSize / 2 - this.fontRendererObj.getStringWidth(power7) / 2, 46, 4210752);
        break;
    case 8: 
        String power8 = wait + StatCollector.translateToLocal("conductor.msg8");
        this.fontRendererObj.drawString(power8, this.xSize / 2 - this.fontRendererObj.getStringWidth(power8) / 2, 46, 4210752);
        break;
    }
     
    }else{
    String power =  wait + (this.entity.lightningSpeed/20-this.entity.lightingTicks/20)+" " +StatCollector.translateToLocal("lightning.seconds");
    this.fontRendererObj.drawString(power, this.xSize / 2 - this.fontRendererObj.getStringWidth(power) / 2, 46, 4210752);
    }
    
    FontHelper.textOffsetCentre(this.entity.storage.getEnergyStored() + " LE", 90, 106, 2);
    
  }
  


  @Override
protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
  {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    

    Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    
    int c = this.entity.cookTime * 18 / this.entity.furnaceSpeed;
    drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 26, 176, 0, c, 9);
    

    int l = this.entity.storage.getEnergyStored() * 145 / this.entity.storage.getMaxEnergyStored();
    drawTexturedModalRect(this.guiLeft + 22, this.guiTop +99, 0, 206, l, 20);
  
  }
}
