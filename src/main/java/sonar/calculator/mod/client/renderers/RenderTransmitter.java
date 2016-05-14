package sonar.calculator.mod.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.client.models.ModelTransmitter;
import sonar.calculator.mod.common.tileentity.machines.TileEntityTransmitter;

public class RenderTransmitter extends TileEntitySpecialRenderer<TileEntityTransmitter> {
	private static final ResourceLocation texture = new ResourceLocation("Calculator:textures/model/transmitter.png");
	private static final ResourceLocation scan = new ResourceLocation("Calculator:textures/blocks/transmitter_beam.png");
	private ModelTransmitter model;

	public RenderTransmitter() {
		this.model = new ModelTransmitter();
	}

	@Override
	public void renderTileEntityAt(TileEntityTransmitter te, double x, double y, double z, float partialTicks, int destroyStage) {
		int i;

		if (te.getWorld() == null) {
			i = 0;
		} else {
			Block block = te.getBlockType();
			i = te.getBlockMetadata();
			if ((block != null) && (i == 0)) {
				i = te.getBlockMetadata();
			}
		}

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
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
