package sonar.calculator.mod.client.gui.calculators;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.containers.ContainerInfoCalculator;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiInfoCalculator
  extends GuiContainer
{
  private ResourceLocation texture = new ResourceLocation("Calculator:textures/gui/infocalc.png");
  
  public GuiInfoCalculator(EntityPlayer player, InventoryPlayer invPlayer, World world, int x, int y, int z) { 
	  super(new ContainerInfoCalculator(player, invPlayer, world, x, y, z));
    
    this.xSize = 176;
    this.ySize = 166;
  }

  @Override
protected void drawGuiContainerForegroundLayer(int i, int j) { 
	  FontHelper.textCentre(FontHelper.translate("item.InfoCalculator.name"), xSize, 5, 0);
	  FontHelper.textOffsetCentre(FontHelper.translate("info.all"), 43, 17, 0);
	  FontHelper.textOffsetCentre(FontHelper.translate("info.blocks"), 43, 35, 0);
	  FontHelper.textOffsetCentre(FontHelper.translate("info.calculators"), 43, 53, 0); 
	  FontHelper.textOffsetCentre(FontHelper.translate("info.machines"), 43, 71, 0); 
	  FontHelper.textOffsetCentre(FontHelper.translate("info.generators"), 134, 17, 0);
	  FontHelper.textOffsetCentre(FontHelper.translate("info.modules"), 134, 35, 0);
	  FontHelper.textOffsetCentre(FontHelper.translate("info.items"), 134, 53, 0); 
	  FontHelper.textOffsetCentre(FontHelper.translate("info.circuits"), 134, 71, 0); 
	  
	  }
 

  @Override
protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
  {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    
    Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
    
    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
  }
}
