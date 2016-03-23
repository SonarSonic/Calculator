package sonar.calculator.mod.client.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import sonar.core.common.block.properties.IItemRenderer;

public class ItemConduit implements net.minecraftforge.client.IItemRenderer {
	TileEntitySpecialRenderer render;
	private TileEntity entity;

	public ItemConduit(TileEntitySpecialRenderer render, TileEntity entity) {
		this.entity = entity;
		this.render = render;
	}

	@Override
	public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {

		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		if (type == IItemRenderer.ItemRenderType.INVENTORY) {
			GL11.glScaled(2.5F, 2.5F, 2.5F);
			GL11.glTranslated(-1.0F, -1.25F, -0.05F);
		}
		if (type == IItemRenderer.ItemRenderType.EQUIPPED) {
			GL11.glScaled(2F, 2F, 2F);
			GL11.glTranslated(-1.0F, -1.25F, -0.25F);
		}
		if (type == IItemRenderer.ItemRenderType.ENTITY) {
			GL11.glScaled(2.5F, 2.5F, 2.5F);
			GL11.glTranslated(-0.5F, -1.0F, -0.5F);

		}
		if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glScaled(10F, 10F, 10F);
			GL11.glTranslated(-1.0F, -0.75F, -0.6F);
		}

		this.render.renderTileEntityAt(this.entity, 0.0D, -0.0D, 0.0D, 0.0F);
	}
}
