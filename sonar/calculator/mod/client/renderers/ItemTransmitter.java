package sonar.calculator.mod.client.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class ItemTransmitter implements net.minecraftforge.client.IItemRenderer
{
  TileEntitySpecialRenderer render;
  private TileEntity entity;
  
  public ItemTransmitter(TileEntitySpecialRenderer render, TileEntity entity)
  {
    this.entity = entity;
    this.render = render;
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
    if (type == IItemRenderer.ItemRenderType.ENTITY){
     GL11.glTranslatef(-0.2F, 0.3F, -0.2F);
     GL11.glScaled(0.5F, 0.5F, 0.5F);
    }
    if (type == IItemRenderer.ItemRenderType.INVENTORY){
        GL11.glTranslatef(0.0F, -0.45F, 0.0F);
        GL11.glScaled(0.7F, 0.7F, 0.7F);
       }

    if (type == IItemRenderer.ItemRenderType.EQUIPPED){
    }
    if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON){
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        GL11.glScaled(1.0F, 1.0F, 1.0F);
    }
    this.render.renderTileEntityAt(this.entity, 0.0D, -0.0D, 0.0D, 0.0F);
  }
}
