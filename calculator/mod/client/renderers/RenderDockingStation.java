package sonar.calculator.mod.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.models.ModelCalculator;
import sonar.calculator.mod.client.models.ModelDockingStation;
import sonar.calculator.mod.common.tileentity.machines.TileEntityDockingStation;


public class RenderDockingStation
  extends TileEntitySpecialRenderer
{
  private static final ResourceLocation texture = new ResourceLocation("Calculator:textures/model/dockingstation.png");
  private ModelDockingStation model;
  private ModelCalculator calc;
  
  public RenderDockingStation() {
    this.model = new ModelDockingStation();
    this.calc = new ModelCalculator();
  }
  

  @Override
public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
  {
    int i;
    
    if (tileentity.getWorldObj() == null)
    {
      i = 0;
    } else {
      Block block = tileentity.getBlockType();
      i = tileentity.getBlockMetadata();
      if ((block != null) && (i == 0))
      {
    	   i = tileentity.getBlockMetadata();
      }
    }
   
    GL11.glPushMatrix();
    GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
    Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    GL11.glPushMatrix();
    GL11.glRotatef(180.0F, 180.0F, 0.0F, 1.0F);
    int j = 0;
    if (i == 3) {
        j = 0;
      }
      if (i == 2) {
        j = 180;
      }
      if (i == 4) {
        j = 90;
      }
      if (i == 5) {
        j = 270;
      }
    GL11.glRotatef(j, 0.0F, 1.0F, 0.0F);
    
    this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
    
    if(tileentity.getWorldObj()!=null && tileentity instanceof TileEntityDockingStation){
    TileEntityDockingStation station = (TileEntityDockingStation) tileentity;
    if(station.type!=0){
    Tessellator tessellator = Tessellator.instance;
    switch(station.type){
    case 1:    	ItemStack bcalc = new ItemStack(Calculator.itemCalculator);    
				Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().renderEngine.getResourceLocation(bcalc.getItemSpriteNumber()));
				IIcon bIcon = bcalc.getIconIndex();
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glTranslatef(0.5F, 1.05F, -0.35F);
				GL11.glRotatef(45F, -45.0F, 0.0F, 0.0F);
				GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
				ItemRenderer.renderItemIn2D(tessellator, bIcon.getMaxU(), bIcon.getMinV(), bIcon.getMinU(), bIcon.getMaxV(), bIcon.getIconWidth(), bIcon.getIconHeight(), 0.0625F); 
				break;
				
    case 2:    	ItemStack sCalc = new ItemStack(Calculator.itemScientificCalculator);    
				Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().renderEngine.getResourceLocation(sCalc.getItemSpriteNumber()));
				IIcon sIcon = sCalc.getIconIndex();
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glTranslatef(0.5F, 1.05F, -0.35F);
				GL11.glRotatef(45F, -45.0F, 0.0F, 0.0F);
				GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
				ItemRenderer.renderItemIn2D(tessellator, sIcon.getMaxU(), sIcon.getMinV(), sIcon.getMinU(), sIcon.getMaxV(), sIcon.getIconWidth(), sIcon.getIconHeight(), 0.0625F); 
				break;
				
    case 3:    	ItemStack aCalc = new ItemStack(Calculator.atomiccalculatorBlock);    
				Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().renderEngine.getResourceLocation(aCalc.getItemSpriteNumber()));
				IIcon aIcon = aCalc.getIconIndex();
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glTranslatef(0.5F, 1.05F, -0.35F);
				GL11.glRotatef(45F, -45.0F, 0.0F, 0.0F);
				GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
				ItemRenderer.renderItemIn2D(tessellator, aIcon.getMaxU(), aIcon.getMinV(), aIcon.getMinU(), aIcon.getMaxV(), aIcon.getIconWidth(), aIcon.getIconHeight(), 0.0625F); 
				break;
				
    case 4:    	ItemStack fCalc = new ItemStack(Calculator.itemFlawlessCalculator);    
				Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().renderEngine.getResourceLocation(fCalc.getItemSpriteNumber()));
				IIcon fIcon = fCalc.getIconIndex();
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glTranslatef(0.5F, 1.05F, -0.35F);
				GL11.glRotatef(45F, -45.0F, 0.0F, 0.0F);
				GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
				ItemRenderer.renderItemIn2D(tessellator, fIcon.getMaxU(), fIcon.getMinV(), fIcon.getMinU(), fIcon.getMaxV(), fIcon.getIconWidth(), fIcon.getIconHeight(), 0.0625F); 
				break;	
	
    }
}
    }
    GL11.glPopMatrix();
    GL11.glPopMatrix();
    
  }


}
