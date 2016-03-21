package sonar.calculator.mod.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.client.models.ModelCrankHandle;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;

public class RenderCrank extends TileEntitySpecialRenderer {
	private static final ResourceLocation texture = new ResourceLocation("Calculator:textures/model/crank.png");
	private ModelCrankHandle model;

	public RenderCrank() {
		this.model = new ModelCrankHandle();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick) {
		renderAModelAt((TileEntityCrankHandle) tileEntity, x, y, z, partialTick);
	}

	public void renderAModelAt(TileEntityCrankHandle tileentity, double x, double y, double z, float f) {
		int i;
		if (tileentity.getWorld() == null) {
			i = 0;
		} else {
			Block block = tileentity.getBlockType();
			i = tileentity.getBlockMetadata();
			if ((block != null) && (i == 0)) {
				i = tileentity.getBlockMetadata();
			}
		}
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		GL11.glPushMatrix();
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotated(90, 0.0D, 1.0D, 0.0D);
		GL11.glRotated(tileentity.angle * 10, 0.0D, 1.0D, 0.0D);
		switch (i) {
		case 1:
			GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 2:
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 3:
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 4:
			GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
		}
		this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
