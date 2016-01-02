package sonar.calculator.mod.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.client.models.ModelShovel;

public class ItemShovel implements net.minecraftforge.client.IItemRenderer
{
  public String texture;
  public ModelShovel shovel;
  public ItemShovel(String texture)
  {
	  this.texture=texture;
	  this.shovel=new ModelShovel();
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
    if (type == IItemRenderer.ItemRenderType.EQUIPPED){
    	
        GL11.glScaled(3.0F, 3.0F, 3.0F);
        GL11.glRotated(180, 1, 0, 0);
        GL11.glRotated(45, 0, 1, 0);
        GL11.glRotated(-60, 0, 0, 1);
        GL11.glTranslated(0.35, -1.00, 0.0);
    }
    if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON){
    	
        GL11.glScaled(1.75F, 1.75F, 1.75F);
        GL11.glRotated(190, 1, 0, 0);
        GL11.glRotated(110, 0, 1, 0);
        //GL11.glRotated(-60, 0, 0, 1);
        GL11.glTranslated(0.2, -1.5, 0.3);
    }
    if (type == IItemRenderer.ItemRenderType.ENTITY){
    	GL11.glScaled(0.7F, 0.7F, 0.7F);
        GL11.glRotated(180, 1, 0, 0);
        GL11.glTranslated(0.0, -0.9, 0.0);
        GL11.glRotated(90, 0, 1, 0);
    }
    if (type == IItemRenderer.ItemRenderType.INVENTORY){
        GL11.glScaled(1.4F, 1.4F, 1.4F);
        GL11.glRotated(180, 1, 0, 0);
        GL11.glTranslated(0.0, -1.0, 0.0);
        GL11.glRotated(-45, 0, 1, 0);
        GL11.glRotated(30, 0, 0, 1);
        GL11.glTranslated(0.5, 0.0, 0.0);
    }
    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(texture));
    shovel.render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F);
  }
}
