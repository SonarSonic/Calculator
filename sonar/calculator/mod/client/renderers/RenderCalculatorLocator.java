package sonar.calculator.mod.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.models.ModelCalculatorLocator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;


public class RenderCalculatorLocator
  extends TileEntitySpecialRenderer
{
  private static final ResourceLocation onTexture = new ResourceLocation("Calculator:textures/model/calculatorlocatoron.png");
  private static final ResourceLocation offTexture = new ResourceLocation("Calculator:textures/model/calculatorlocatoroff.png");

  private static final ResourceLocation beam = new ResourceLocation("Calculator:textures/blocks/locator_beam.png");

  private ModelCalculatorLocator model;
  
  public RenderCalculatorLocator() {
    this.model = new ModelCalculatorLocator();
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
    
    if(tileentity.getWorldObj() != null&&tileentity instanceof TileEntityCalculatorLocator){
    	TileEntityCalculatorLocator tile = (TileEntityCalculatorLocator) tileentity;
    	if(tile.active==1){
        Minecraft.getMinecraft().renderEngine.bindTexture(onTexture);
    	}
     	if(tile.active!=1){
        Minecraft.getMinecraft().renderEngine.bindTexture(offTexture);
     	}
    }
    else{
        Minecraft.getMinecraft().renderEngine.bindTexture(onTexture);	
    }
    
    GL11.glPushMatrix();
    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
    int j = 0;
    if (i == 3) {
      j = 270;
    }
    if (i == 2) {
      j = 180;
    }
    if (i == 1) {
      j = 90;
    }
    if (i == 0) {
      j = 360;
    }
    GL11.glRotatef(j, 0.0F, 1.0F, 0.0F);
    this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
    GL11.glPopMatrix();
    GL11.glPopMatrix();
    if(CalculatorConfig.beamEffect && tileentity.getWorldObj()!=null && tileentity instanceof TileEntityCalculatorLocator){
    	TileEntityCalculatorLocator tile = (TileEntityCalculatorLocator) tileentity;    	
    	if(tile.active==1){
    
    int height = tile.beamHeight();
    Tessellator tessellator = Tessellator.instance;
    this.bindTexture(beam);
    GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
    GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
    GL11.glDisable(GL11.GL_LIGHTING);
    GL11.glDisable(GL11.GL_CULL_FACE);
    GL11.glDisable(GL11.GL_BLEND);
    GL11.glDepthMask(true);
    OpenGlHelper.glBlendFunc(770, 1, 1, 0);
    GL11.glTranslated(0.0, 0.70, 0.0);
    float f2 = (float)tileentity.getWorldObj().getTotalWorldTime() + 20;
    float f3 = -f2 * 0.2F - (float)MathHelper.floor_float(-f2 * 0.1F);
    byte b0 = 1;
    double d3 = (double)f2 * 0.025D * (1.0D - (double)(b0 & 1) * 2.5D);
    tessellator.startDrawingQuads();
    tessellator.setColorRGBA(255, 255, 255, 32);
    double d5 = (double)b0 * tile.size/20;
    double d7 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d5;
    double d9 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d5;
    double d11 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * d5;
    double d13 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * d5;
    double d15 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d5;
    double d17 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d5;
    double d19 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d5;
    double d21 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d5;
    double d23 = (double)(height);
    double d25 = 0.0D;
    double d27 = 1.0D;
    double d28 = (double)(-1.0F + f3);
    double d29 = (double)(height) * (0.6D / d5) + d28;
    
    tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d27, d29);
    tessellator.addVertexWithUV(x + d7, y, z + d9, d27, d28);
    tessellator.addVertexWithUV(x + d11, y, z + d13, d25, d28);
    tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d25, d29);
    tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d27, d29);
    tessellator.addVertexWithUV(x + d19, y, z + d21, d27, d28);
    tessellator.addVertexWithUV(x + d15, y, z + d17, d25, d28);
    tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d25, d29);
    tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d27, d29);
    tessellator.addVertexWithUV(x + d11, y, z + d13, d27, d28);
    tessellator.addVertexWithUV(x + d19, y, z + d21, d25, d28);
    tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d25, d29);
    tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d27, d29);
    tessellator.addVertexWithUV(x + d15, y, z + d17, d27, d28);
    tessellator.addVertexWithUV(x + d7, y, z + d9, d25, d28);
    tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d25, d29);
    
    
    tessellator.draw();

    GL11.glTranslated(0.0, -0.70, 0.0);
    GL11.glEnable(GL11.GL_BLEND);
    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    GL11.glDepthMask(false);
    tessellator.startDrawingQuads();
    tessellator.setColorRGBA(255, 255, 255, 32);
    double offset = 0.2D - (tile.size-1)/4;
    double d6 = 0.8D+(tile.size-1)/4;
    double d18 = (double)(height);
    double d20 = 0.0D;
    double d22 = 1.0D;
    double d24 = (double)(-1.0F + f3);
    double d26 = (double)(height) + d24;
    
    tessellator.addVertexWithUV(x + offset, y + d18, z + offset, d22, d26);
    tessellator.addVertexWithUV(x + offset, y, z + offset, d22, d24);
    tessellator.addVertexWithUV(x + d6, y, z + offset, d20, d24);
    tessellator.addVertexWithUV(x + d6, y + d18, z + offset, d20, d26);
    tessellator.addVertexWithUV(x + d6, y + d18, z + offset, d22, d26);
    tessellator.addVertexWithUV(x + d6, y, z + d6, d22, d24);
    tessellator.addVertexWithUV(x + offset, y, z + d6, d20, d24);
    tessellator.addVertexWithUV(x + offset, y + d18, z + d6, d20, d26);
    tessellator.addVertexWithUV(x + d6, y + d18, z + offset, d22, d26);
    tessellator.addVertexWithUV(x + d6, y, z + offset, d22, d24);
    tessellator.addVertexWithUV(x + d6, y, z + d6, d20, d24);
    tessellator.addVertexWithUV(x + d6, y + d18, z + d6, d20, d26);
    tessellator.addVertexWithUV(x + offset, y + d18, z + d6, d22, d26);
    tessellator.addVertexWithUV(x + offset, y, z + d6, d22, d24);
    tessellator.addVertexWithUV(x + offset, y, z + offset, d20, d24);
    tessellator.addVertexWithUV(x + offset, y + d18, z + offset, d20, d26);
    
    
    tessellator.draw();
    GL11.glEnable(GL11.GL_LIGHTING);
    GL11.glEnable(GL11.GL_TEXTURE_2D);
    GL11.glDepthMask(true);
    }
    	
    }
   
  }


}
