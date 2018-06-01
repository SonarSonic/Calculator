package sonar.calculator.mod.client.renderers;

import sonar.calculator.mod.client.models.ModelConductorMast;
import sonar.calculator.mod.client.models.ModelScarecrow;
import sonar.calculator.mod.client.models.ModelTransmitter;
import sonar.core.client.SonarTERender;

public class RenderHandlers {

	public static class ConductorMast extends SonarTERender {
		public ConductorMast() {
			super(new ModelConductorMast(), "Calculator:textures/model/conductormask.png");
		}
	}
	
	public static class Transmitter extends SonarTERender {
		public Transmitter() {
			super(new ModelTransmitter(), "Calculator:textures/model/transmitter.png");
		}
	}

	public static class Scarecrow extends SonarTERender {
		public Scarecrow() {
			super(new ModelScarecrow(), "Calculator:textures/model/scarecrow.png");
		}
	}

	
	
	/*
	public static class Teleporter extends SonarTERender {
		private static final ResourceLocation layer1 = new ResourceLocation("Calculator:textures/blocks/teleporter_layer1.png");
		private static final ResourceLocation layer2 = new ResourceLocation("Calculator:textures/blocks/teleporter_layer2.png");
		private static final ResourceLocation layer3 = new ResourceLocation("Calculator:textures/blocks/teleporter_layer3.png");

		public Teleporter() {
			super(new ModelTeleporter(), "Calculator:textures/model/teleporter.png");
		}

		@Override
		public void renderExtras(TileEntity entity, double x, double y, double z, float f) {
			if (entity.getWorld() != null && entity instanceof TileEntityTeleporter) {
				TileEntityTeleporter tile = (TileEntityTeleporter) entity;

				if (tile.destinationName.equals("DESTINATION") || tile.destinationName == null)
					return;

				GL11.glPushMatrix();
				float height = -2.0F;
				Tessellator tessellator = Tessellator.instance;
				for (int i = 0; i < 2; i++) {
					for (double width = 0.4D; width < 1; width += 0.2D) {
						this.bindTexture(width == 0.4 ? layer1 : width == 0.6 ? layer2 : layer3);
						GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
						GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
						GL11.glDisable(GL11.GL_LIGHTING);
						GL11.glDisable(GL11.GL_CULL_FACE);
						GL11.glDisable(GL11.GL_BLEND);
						GL11.glDepthMask(true);
						OpenGlHelper.glBlendFunc(770, 1, 1, 0);
						GL11.glTranslated(0.0, 0.70, 0.0);
						float f2 = (float) entity.getWorld().getTotalWorldTime() + 20;
						float f4 = -f2 * 0.2F - (float) MathHelper.floor_float(-f2 * 0.1F);
						byte b0 = 1;
						double d3 = (double) f2 * 0.025D * (1.0D - (double) (b0 & 1) * 2.5D);
						GL11.glTranslated(0.0, -0.70, 0.0);
						GL11.glEnable(GL11.GL_BLEND);
						OpenGlHelper.glBlendFunc(770, 771, 1, 0);
						GL11.glDepthMask(false);
						tessellator.startDrawingQuads();
						tessellator.setColorRGBA(255, 255, 255, 32);

						double remain = 1 - width;
						double offset = 0.2D - 1 / 4;
						double d18 = height;
						double d20 = 0.0D;
						double d22 = 1.0D;
						double d24 = (double) (-1.0F + f4);
						double d26 = d18 + d24;
						tessellator.addVertexWithUV(x + remain, y + d18, z + remain, d22, d26);
						tessellator.addVertexWithUV(x + remain, y, z + remain, d22, d24);
						tessellator.addVertexWithUV(x + width, y, z + remain, d20, d24);
						tessellator.addVertexWithUV(x + width, y + d18, z + remain, d20, d26);
						tessellator.addVertexWithUV(x + width, y + d18, z + width, d22, d26);
						tessellator.addVertexWithUV(x + width, y, z + width, d22, d24);
						tessellator.addVertexWithUV(x + remain, y, z + width, d20, d24);
						tessellator.addVertexWithUV(x + remain, y + d18, z + width, d20, d26);
						tessellator.addVertexWithUV(x + width, y + d18, z + remain, d22, d26);
						tessellator.addVertexWithUV(x + width, y, z + remain, d22, d24);
						tessellator.addVertexWithUV(x + width, y, z + width, d20, d24);
						tessellator.addVertexWithUV(x + width, y + d18, z + width, d20, d26);
						tessellator.addVertexWithUV(x + remain, y + d18, z + width, d22, d26);
						tessellator.addVertexWithUV(x + remain, y, z + width, d22, d24);
						tessellator.addVertexWithUV(x + remain, y, z + remain, d20, d24);
						tessellator.addVertexWithUV(x + remain, y + d18, z + remain, d20, d26);

						tessellator.draw();
						GL11.glEnable(GL11.GL_LIGHTING);
						GL11.glEnable(GL11.GL_TEXTURE_2D);
						GL11.glDepthMask(true);
					}
				}
				GL11.glPopMatrix();

				GL11.glPushMatrix();

				float f1 = 0.65F;
				float f3;

				int j = RenderHelper.setMetaData(entity);
				f3 = 0.0F;

				if (j == 2) {
					f3 = 180.0F;
				}

				if (j == 4) {
					f3 = 90.0F;
				}

				if (j == 5) {
					f3 = -90.0F;
				}

				GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
				GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
				GL11.glPushMatrix();
				GL11.glScalef(f1, -f1, -f1);
				GL11.glPopMatrix();
				f3 = 0.016666668F * f1;
				GL11.glTranslatef(0.0F, -0.5F, 0.8F * f1);
				GL11.glScalef(f3, -f3, f3);
				GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
				GL11.glDepthMask(false);

				FontHelper.textCentre(tile.destinationName, 0, 0, 0);

				GL11.glDepthMask(true);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glPopMatrix();
			}

		}
	}
	*/
}
