package sonar.calculator.mod.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.client.models.ModelCalculatorPlug;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;

public class RenderCalculatorPlug extends TileEntitySpecialRenderer {
	private static final ResourceLocation onTexture = new ResourceLocation("Calculator:textures/model/calculatorplugon.png");
	private static final ResourceLocation offTexture = new ResourceLocation("Calculator:textures/model/calculatorplugoff.png");

	private ModelCalculatorPlug model;

	public RenderCalculatorPlug() {
		this.model = new ModelCalculatorPlug();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
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

		if (tileentity.getWorld() != null && tileentity instanceof TileEntityCalculatorPlug) {
			TileEntityCalculatorPlug tile = (TileEntityCalculatorPlug) tileentity;
			if (tile.stable.getObject() == 2) {
				Minecraft.getMinecraft().renderEngine.bindTexture(onTexture);
			}
			if (tile.stable.getObject() != 2) {
				Minecraft.getMinecraft().renderEngine.bindTexture(offTexture);
			}
		} else {
			Minecraft.getMinecraft().renderEngine.bindTexture(onTexture);
		}

		GL11.glPushMatrix();
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		int j = 0;
		if (i == 3) {
			j = 270;
		}
		if (i == 2) {
			j = 180;
		}
		if (i == 1) {
			j = 90;
		}
		if (i == 0) {
			j = 360;
		}
		GL11.glRotatef(j, 0.0F, 1.0F, 0.0F);
		this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
