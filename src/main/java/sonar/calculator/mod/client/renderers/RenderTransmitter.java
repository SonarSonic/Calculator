package sonar.calculator.mod.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.client.models.ModelTransmitter;

public class RenderTransmitter extends TileEntitySpecialRenderer {
	private static final ResourceLocation texture = new ResourceLocation("Calculator:textures/model/transmitter.png");
	private static final ResourceLocation scan = new ResourceLocation("Calculator:textures/blocks/transmitter_beam.png");
	private ModelTransmitter model;

	public RenderTransmitter() {
		this.model = new ModelTransmitter();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double X, double Y, double Z, float f) {
		int i;

		if (tileentity.getWorldObj() == null) {
			i = 0;
		} else {
			Block block = tileentity.getBlockType();
			i = tileentity.getBlockMetadata();
			if ((block != null) && (i == 0)) {
				i = tileentity.getBlockMetadata();
			}
		}

		GL11.glPushMatrix();
		GL11.glTranslatef((float) X + 0.5F, (float) Y + 1.5F, (float) Z + 0.5F);
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
		this.model.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

		GL11.glPopMatrix();
		GL11.glPopMatrix();

	}

}
