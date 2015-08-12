package sonar.calculator.mod.client.renderers;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.models.ModelAtomicMultiplier;
import sonar.calculator.mod.client.models.ModelConductorMast;
import sonar.calculator.mod.client.models.ModelFluxController;
import sonar.calculator.mod.client.models.ModelFluxPlug;
import sonar.calculator.mod.client.models.ModelFluxPoint;
import sonar.calculator.mod.client.models.ModelGenerator;
import sonar.calculator.mod.client.models.ModelScarecrow;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.core.renderers.SonarTERender;

public class RenderHandlers {

	public static class AtomicMultiplier extends SonarTERender {
		public AtomicMultiplier() {
			super(new ModelAtomicMultiplier(), "Calculator:textures/model/atomicmultiplier.png");
		}

	}

	public static class ConductorMast extends SonarTERender {
		public ConductorMast() {
			super(new ModelConductorMast(), "Calculator:textures/model/conductormask.png");
		}

	}

	public static class Scarecrow extends SonarTERender {
		public Scarecrow() {
			super(new ModelScarecrow(), "Calculator:textures/model/scarecrow.png");
		}
	}

	public static class StarchExtractor extends SonarTERender {
		public StarchExtractor() {
			super(new ModelGenerator(), "Calculator:textures/model/starchextractor.png");
		}
	}

	public static class RedstoneExtractor extends SonarTERender {
		public RedstoneExtractor() {
			super(new ModelGenerator(), "Calculator:textures/model/redstoneextractor.png");
		}
	}

	public static class GlowstoneExtractor extends SonarTERender {
		public GlowstoneExtractor() {
			super(new ModelGenerator(), "Calculator:textures/model/glowstoneextractor.png");
		}
	}


	public static class FluxController extends SonarTERender {

		private static final ResourceLocation beam = new ResourceLocation("Calculator:textures/blocks/controller_beam.png");
		
		public FluxController() {
			super(new ModelFluxController(), "Calculator:textures/model/fluxmaster.png");
		}

		@Override
		public void renderExtras(TileEntity entity, double x, double y, double z, float f) {
			GL11.glPushMatrix();
			GL11.glTranslated(0.0, -0.6, 0.0);
			if (CalculatorConfig.beamEffect && entity.getWorldObj() != null && entity instanceof TileEntityFluxController) {
				TileEntityFluxController tile = (TileEntityFluxController) entity;
					float height = 0.8F;
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
					double d5 = (double) b0 * 1 / 20;
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
			
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glDepthMask(true);
				

			}
			GL11.glPopMatrix();
		}
	}
}
