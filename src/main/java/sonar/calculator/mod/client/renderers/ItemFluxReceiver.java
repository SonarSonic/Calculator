package sonar.calculator.mod.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.client.models.ModelFluxModule;

public class ItemFluxReceiver implements net.minecraftforge.client.IItemRenderer
{
  private ModelFluxModule model;
  
  public ItemFluxReceiver()
  {
	  model=new ModelFluxModule();
  }
  

  @Override
public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
  {
    return true;
  }
  
  @Override
public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
  {
    return true;
  }
  
  @Override
public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
  {
	    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("Calculator:textures/model/flux_module.png"));

      GL11.glRotatef(180F, 1F, 0F, 0F);
    
    if (type == IItemRenderer.ItemRenderType.INVENTORY){
    	GL11.glScalef(0.8F, 0.8F, 0.8F);
        GL11.glTranslatef(0.0F, -0.95F, 0.00F);  
        GL11.glRotatef(-45F, 0F, 1F, 0F);
        GL11.glRotatef(00F, 0F, 0F, 1F);
        model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, false);
       }
    if (type == IItemRenderer.ItemRenderType.EQUIPPED){
        GL11.glRotatef(-20F, 1F, 0F, 0F);    
        GL11.glRotatef(-20F, 0F, 1F, 0F);
        GL11.glTranslatef(0.5F, -1.8F, -1F);
       }
    if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON){ 
        GL11.glTranslatef(0.5F, -1.8F, -1F);
        GL11.glScaled(0.8F, 0.8F, 0.8F);  
        GL11.glRotatef(100F, 0F, 1F, 0F);
        GL11.glTranslatef(-0.35F, 0.0F, 0F);
        GL11.glRotatef(-10F, 1F, 0F, 0F);  
       }
    if (type == IItemRenderer.ItemRenderType.ENTITY){
        GL11.glScaled(0.8F, 0.8F, 0.8F);  
        GL11.glTranslatef(0.0F, -1.8F, 0.0F);
      
       }

    if (type != IItemRenderer.ItemRenderType.INVENTORY){
    model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, true);
    }
    
  }
}
