package sonar.calculator.mod.client.gui.generators;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerCalculatorPlug;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.core.utils.helpers.FontHelper;



public class GuiCalculatorPlug
  extends GuiContainer
{
  public static final ResourceLocation bground = new ResourceLocation("Calculator:textures/gui/guicalculatorplug.png");
  
  public TileEntityCalculatorPlug entity;
  

  public GuiCalculatorPlug(InventoryPlayer inventoryPlayer, TileEntityCalculatorPlug entity)
  {
    super(new ContainerCalculatorPlug(inventoryPlayer, entity));
    

    this.entity = entity;
    

    this.xSize = 176;
    this.ySize = 166;
  }
  



  @Override
public void drawGuiContainerForegroundLayer(int par1, int par2)
  {

	  FontHelper.textCentre(StatCollector.translateToLocal(entity.getInventoryName()), xSize, 6, 0);
    
    String stable = getString();
    FontHelper.textCentre(stable, xSize, 60, 0);
  }
  
  private String getString()
  {
    if (this.entity.stable == 0){
      return StatCollector.translateToLocal("circuit.stable") + ": " + StatCollector.translateToLocal("circuit.noStability");}
    if (this.entity.stable == 1)
      return StatCollector.translateToLocal("circuit.stable") + ": " + StatCollector.translateToLocal("locator.false");
    if (this.entity.stable == 2){
        return StatCollector.translateToLocal("circuit.stable") + ": " + StatCollector.translateToLocal("locator.true");}
    
    return StatCollector.translateToLocal("circuit.stable") + ": " + StatCollector.translateToLocal("locator.unknown");
  }
  



  @Override
protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
  {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    

    Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
  }
}
