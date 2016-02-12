package sonar.calculator.mod.client.renderers;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.models.ModelCalculatorLocator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.core.utils.helpers.RenderHelper;

public class RenderCalculatorLocator extends TileEntitySpecialRenderer {
	private static final String onTexture = "Calculator:textures/model/calculatorlocatoron.png";
	private static final String offTexture = "Calculator:textures/model/calculatorlocatoroff.png";
	private static final ResourceLocation beam = new ResourceLocation("Calculator:textures/blocks/locator_beam.png");

	private ModelCalculatorLocator model;

	public RenderCalculatorLocator() {
		this.model = new ModelCalculatorLocator();
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {

		if (entity.getWorldObj() != null && entity instanceof TileEntityCalculatorLocator) {
			TileEntityCalculatorLocator tile = (TileEntityCalculatorLocator) entity;
			if (tile.active.getBoolean()) {
				RenderHelper.beginRender(x + 0.5F, y + 1.5F, z + 0.5F, 0, onTexture);	
			}else{
				RenderHelper.beginRender(x + 0.5F, y + 1.5F, z + 0.5F, 0, offTexture);	
			}
			
		}else{
			RenderHelper.beginRender(x + 0.5F, y + 1.5F, z + 0.5F, 0, onTexture);	
		}
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		RenderHelper.finishRender();

		if (CalculatorConfig.beamEffect && entity.getWorldObj() != null && entity instanceof TileEntityCalculatorLocator) {
			TileEntityCalculatorLocator tile = (TileEntityCalculatorLocator) entity;
			if (tile.active.getBoolean()) {
				int height = tile.beamHeight();
				Tessellator tessellator = Tessellator.instance;
				this.bindTexture(beam);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glDepthMask(true);
				OpenGlHelper.glBlendFunc(770, 1, 1, 0);
				GL11.glTranslated(0.0, 0.70, 0.0);
				float f2 = (float) entity.getWorldObj().getTotalWorldTime() + 20;
				float f3 = -f2 * 0.2F - (float) MathHelper.floor_float(-f2 * 0.1F);
				byte b0 = 1;
				double d3 = (double) f2 * 0.025D * (1.0D - (double) (b0 & 1) * 2.5D);
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA(255, 255, 255, 32);
				double d5 = (double) b0 * tile.size.getInt() / 20;
				double d7 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d5;
				double d9 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d5;
				double d11 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * d5;
				double d13 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * d5;
				double d15 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d5;
				double d17 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d5;
				double d19 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d5;
				double d21 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d5;
				double d23 = (double) (height);
				double d25 = 0.0D;
				double d27 = 1.0D;
				double d28 = (double) (-1.0F + f3);
				double d29 = (double) (height) * (0.6D / d5) + d28;

				tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d27, d29);
				tessellator.addVertexWithUV(x + d7, y, z + d9, d27, d28);
				tessellator.addVertexWithUV(x + d11, y, z + d13, d25, d28);
				tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d25, d29);
				tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d27, d29);
				tessellator.addVertexWithUV(x + d19, y, z + d21, d27, d28);
				tessellator.addVertexWithUV(x + d15, y, z + d17, d25, d28);
				tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d25, d29);
				tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d27, d29);
				tessellator.addVertexWithUV(x + d11, y, z + d13, d27, d28);
				tessellator.addVertexWithUV(x + d19, y, z + d21, d25, d28);
				tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d25, d29);
				tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d27, d29);
				tessellator.addVertexWithUV(x + d15, y, z + d17, d27, d28);
				tessellator.addVertexWithUV(x + d7, y, z + d9, d25, d28);
				tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d25, d29);

				tessellator.draw();

				GL11.glTranslated(0.0, -0.70, 0.0);
				GL11.glEnable(GL11.GL_BLEND);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				GL11.glDepthMask(false);
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA(255, 255, 255, 32);
				double offset = 0.2D - 1 / 4;
				double d6 = 0.8D + 1 / 4;
				double d18 = (double) (height);
				double d20 = 0.0D;
				double d22 = 1.0D;
				double d24 = (double) (-1.0F + f3);
				double d26 = (double) (height) + d24;

				tessellator.addVertexWithUV(x + offset, y + d18, z + offset, d22, d26);
				tessellator.addVertexWithUV(x + offset, y, z + offset, d22, d24);
				tessellator.addVertexWithUV(x + d6, y, z + offset, d20, d24);
				tessellator.addVertexWithUV(x + d6, y + d18, z + offset, d20, d26);
				tessellator.addVertexWithUV(x + d6, y + d18, z + offset, d22, d26);
				tessellator.addVertexWithUV(x + d6, y, z + d6, d22, d24);
				tessellator.addVertexWithUV(x + offset, y, z + d6, d20, d24);
				tessellator.addVertexWithUV(x + offset, y + d18, z + d6, d20, d26);
				tessellator.addVertexWithUV(x + d6, y + d18, z + offset, d22, d26);
				tessellator.addVertexWithUV(x + d6, y, z + offset, d22, d24);
				tessellator.addVertexWithUV(x + d6, y, z + d6, d20, d24);
				tessellator.addVertexWithUV(x + d6, y + d18, z + d6, d20, d26);
				tessellator.addVertexWithUV(x + offset, y + d18, z + d6, d22, d26);
				tessellator.addVertexWithUV(x + offset, y, z + d6, d22, d24);
				tessellator.addVertexWithUV(x + offset, y, z + offset, d20, d24);
				tessellator.addVertexWithUV(x + offset, y + d18, z + offset, d20, d26);

				tessellator.draw();
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glDepthMask(true);
			}

		}

	}

}
