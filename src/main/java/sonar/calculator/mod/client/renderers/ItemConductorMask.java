package sonar.calculator.mod.client.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import sonar.core.common.block.properties.IItemRenderer;

public class ItemConductorMask implements net.minecraftforge.client.IItemRenderer {
	TileEntitySpecialRenderer render;
	private TileEntity entity;

	public ItemConductorMask(TileEntitySpecialRenderer render, TileEntity entity) {
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
		if (type == IItemRenderer.ItemRenderType.ENTITY) {
			GL11.glTranslatef(-0.2F, 0.3F, -0.2F);
			GL11.glScaled(0.5F, 0.5F, 0.5F);
		}
		if (type == IItemRenderer.ItemRenderType.INVENTORY) {
			GL11.glTranslatef(0.0F, -0.55F, 0.0F);
			GL11.glScaled(0.4F, 0.4F, 0.4F);
		}

		if (type == IItemRenderer.ItemRenderType.EQUIPPED) {
			GL11.glTranslatef(0.0F, 0.0F, 0.0F);
		}
		if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glScaled(0.6F, 0.6F, 0.6F);
		}
		this.render.renderTileEntityAt(this.entity, 0.0D, -0.0D, 0.0D, 0.0F);
	}
}
