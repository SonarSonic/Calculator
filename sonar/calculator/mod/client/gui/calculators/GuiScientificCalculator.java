package sonar.calculator.mod.client.gui.calculators;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sonar.calculator.mod.common.containers.ContainerScientificCalculator;
import sonar.calculator.mod.common.item.calculators.CalculatorItem;
import sonar.calculator.mod.common.item.calculators.CalculatorItem.CalculatorInventory;
import sonar.core.common.item.InventoryItem;
import sonar.core.utils.helpers.FontHelper;

@SideOnly(Side.CLIENT)
public class GuiScientificCalculator extends GuiContainer
{
  private ResourceLocation texture = new ResourceLocation("Calculator:textures/gui/scientificcalculator.png");

  public GuiScientificCalculator(EntityPlayer player, InventoryPlayer inv, CalculatorInventory calculatorInventory) { 
	  super(new ContainerScientificCalculator(player, inv, calculatorInventory));
    
    this.xSize = 176;
    this.ySize = 166;
  }
  
  
  @Override
protected void drawGuiContainerForegroundLayer(int i, int j) { 
	  FontHelper.textCentre(FontHelper.translate("item.ScientificCalculator.name"), xSize, 8, 0); }
  

  @Override
protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
  {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    
    Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
    
    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
  }
}
