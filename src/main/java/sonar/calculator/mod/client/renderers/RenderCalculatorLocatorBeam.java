package sonar.calculator.mod.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;

public class RenderCalculatorLocatorBeam extends TileEntitySpecialRenderer<TileEntityCalculatorLocator> {

	private static final ResourceLocation beam = new ResourceLocation("Calculator:textures/blocks/locator_beam.png");

	@Override
    //public void renderTileEntityAt(TileEntityCalculatorLocator te, double x, double y, double z, float partialTicks, int destroyStage) {
    public void renderTileEntityAt(TileEntityCalculatorLocator te, double x, double y, double z, float partialTicks, int destroyStage) {
		if (CalculatorConfig.beamEffect && te.getWorld() != null) {
			if (te.active.getObject()) {
				Minecraft.getMinecraft().getTextureManager().bindTexture(beam);
				TileEntityBeaconRenderer.renderBeamSegment(x, y, z, partialTicks, 1.0F, te.getWorld().getTotalWorldTime(), te.beamHeight(), 2, new float[]{256,256,256});
				/*
				GL11.glPushMatrix();
				Minecraft.getMinecraft().getTextureManager().bindTexture(beam);
				GL11.glPushMatrix();				
				GL11.glTranslated(x, y, z);
				int height = te.beamHeight();
				Tessellator tessellator = Tessellator.getInstance();
				VertexBuffer vertex = tessellator.getBuffer();
		        GlStateManager.glTexParameteri(3553, 10242, 10497);
		        GlStateManager.glTexParameteri(3553, 10243, 10497);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glDepthMask(true);
				OpenGlHelper.glBlendFunc(770, 1, 1, 0);
				GL11.glTranslated(0.0, 0.70, 0.0);
				float f2 = (float) te.getWorld().getTotalWorldTime() + 20;
				float f3 = -f2 * 0.2F - (float) Math.floor(-f2 * 0.1F);
				byte b0 = 1;
				double d3 = (double) f2 * 0.025D * (1.0D - (double) (b0 & 1) * 2.5D);
				vertex.begin(7, DefaultVertexFormats.POSITION_TEX);
				double d5 = (double) b0 * te.size.getObject() / 20;
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

				RenderHelper.addVertexWithUV(vertex, x + d7, y + d23, z + d9, d27, d29);
				RenderHelper.addVertexWithUV(vertex, x + d7, y, z + d9, d27, d28);
				RenderHelper.addVertexWithUV(vertex, x + d11, y, z + d13, d25, d28);
				RenderHelper.addVertexWithUV(vertex, x + d11, y + d23, z + d13, d25, d29);
				RenderHelper.addVertexWithUV(vertex, x + d19, y + d23, z + d21, d27, d29);
				RenderHelper.addVertexWithUV(vertex, x + d19, y, z + d21, d27, d28);
				RenderHelper.addVertexWithUV(vertex, x + d15, y, z + d17, d25, d28);
				RenderHelper.addVertexWithUV(vertex, x + d15, y + d23, z + d17, d25, d29);
				RenderHelper.addVertexWithUV(vertex, x + d11, y + d23, z + d13, d27, d29);
				RenderHelper.addVertexWithUV(vertex, x + d11, y, z + d13, d27, d28);
				RenderHelper.addVertexWithUV(vertex, x + d19, y, z + d21, d25, d28);
				RenderHelper.addVertexWithUV(vertex, x + d19, y + d23, z + d21, d25, d29);
				RenderHelper.addVertexWithUV(vertex, x + d15, y + d23, z + d17, d27, d29);
				RenderHelper.addVertexWithUV(vertex, x + d15, y, z + d17, d27, d28);
				RenderHelper.addVertexWithUV(vertex, x + d7, y, z + d9, d25, d28);
				RenderHelper.addVertexWithUV(vertex, x + d7, y + d23, z + d9, d25, d29);

				tessellator.draw();
				
				GL11.glTranslated(0.0, -0.70, 0.0);
				GL11.glEnable(GL11.GL_BLEND);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				GL11.glDepthMask(false);
				vertex.begin(7, DefaultVertexFormats.POSITION_TEX);
				double offset = 0.2D - 1 / 4;
				double d6 = 0.8D + 1 / 4;
				double d18 = (double) (height);
				double d20 = 0.0D;
				double d22 = 1.0D;
				double d24 = (double) (-1.0F + f3);
				double d26 = (double) (height) + d24;

				RenderHelper.addVertexWithUV(vertex, x + offset, y + d18, z + offset, d22, d26);
				RenderHelper.addVertexWithUV(vertex, x + offset, y, z + offset, d22, d24);
				RenderHelper.addVertexWithUV(vertex, x + d6, y, z + offset, d20, d24);
				RenderHelper.addVertexWithUV(vertex, x + d6, y + d18, z + offset, d20, d26);
				RenderHelper.addVertexWithUV(vertex, x + d6, y + d18, z + offset, d22, d26);
				RenderHelper.addVertexWithUV(vertex, x + d6, y, z + d6, d22, d24);
				RenderHelper.addVertexWithUV(vertex, x + offset, y, z + d6, d20, d24);
				RenderHelper.addVertexWithUV(vertex, x + offset, y + d18, z + d6, d20, d26);
				RenderHelper.addVertexWithUV(vertex, x + d6, y + d18, z + offset, d22, d26);
				RenderHelper.addVertexWithUV(vertex, x + d6, y, z + offset, d22, d24);
				RenderHelper.addVertexWithUV(vertex, x + d6, y, z + d6, d20, d24);
				RenderHelper.addVertexWithUV(vertex, x + d6, y + d18, z + d6, d20, d26);
				RenderHelper.addVertexWithUV(vertex, x + offset, y + d18, z + d6, d22, d26);
				RenderHelper.addVertexWithUV(vertex, x + offset, y, z + d6, d22, d24);
				RenderHelper.addVertexWithUV(vertex, x + offset, y, z + offset, d20, d24);
				RenderHelper.addVertexWithUV(vertex, x + offset, y + d18, z + offset, d20, d26);

				tessellator.draw();
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glDepthMask(true);
				GL11.glPopMatrix();
				GL11.glPopMatrix();				
				*/
			}
		}
	}
}
