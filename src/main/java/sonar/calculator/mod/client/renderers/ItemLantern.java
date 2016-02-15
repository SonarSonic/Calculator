package sonar.calculator.mod.client.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class ItemLantern implements net.minecraftforge.client.IItemRenderer {
	TileEntitySpecialRenderer render;
	private TileEntity entity;

	public ItemLantern(TileEntitySpecialRenderer render, TileEntity entity) {
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
			GL11.glScaled(1.5F, 1.5F, 1.5F);
		}
		if (type == IItemRenderer.ItemRenderType.EQUIPPED) {
			GL11.glScaled(2F, 2F, 2F);
			GL11.glTranslated(-0.2F, -0.41F, -0.2F);
		}
		if (type == IItemRenderer.ItemRenderType.ENTITY) {
			GL11.glScaled(2F, 2F, 2F);
			GL11.glTranslated(-0.5F, 0F, -0.5F);

		}

		this.render.renderTileEntityAt(this.entity, 0.0D, -0.0D, 0.0D, 0.0F);
	}
}
