package sonar.calculator.mod.client.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import sonar.core.common.block.properties.IItemRenderer;

public class ItemScarecrow implements net.minecraftforge.client.IItemRenderer {
	TileEntitySpecialRenderer render;
	private TileEntity entity;

	public ItemScarecrow(TileEntitySpecialRenderer render, TileEntity entity) {
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
		if (type == IItemRenderer.ItemRenderType.INVENTORY) {
			GL11.glScaled(0.5F, 0.5F, 0.5F);
			GL11.glTranslated(0.0F, -0.9F, 0.0F);
		}
		if (type == IItemRenderer.ItemRenderType.EQUIPPED) {
			GL11.glTranslated(-0.1F, 0F, -0.1F);
			GL11.glTranslated(0.4F, 0.4F, 0.4F);
			GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
			GL11.glScaled(0.6, 0.6, 0.6);
		}
		if (type == IItemRenderer.ItemRenderType.ENTITY) {
			GL11.glTranslated(0.5F, 0F, 0.5F);
			// GL11.glTranslated(-0.7F, 0F, -0.7F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);

		}
		if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslated(0.3F, 0.7F, 0.3F);
			GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScaled(0.5, 0.5, 0.5);
		}

		this.render.renderTileEntityAt(this.entity, 0.0D, -0.0D, 0.0D, 0.0F);
	}
}
