package sonar.calculator.mod.client.renderers;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculatorScreen;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCalculatorScreen extends TileEntitySpecialRenderer {
	private static final ResourceLocation tex = new ResourceLocation("Calculator:textures/model/calculatorScreen.png");
	private final ModelSign modelSign = new ModelSign();

	public void renderTileEntityAt(TileEntityCalculatorScreen screen, double x, double y, double z, float var) {
		GL11.glPushMatrix();

		float f1 = 0.6666667F;
		float f3;

		int j = screen.getBlockMetadata();
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
		this.modelSign.signStick.showModel = false;
		this.bindTexture(tex);
		GL11.glPushMatrix();
		GL11.glScalef(f1, -f1, -f1);
		this.modelSign.renderSign();
		GL11.glPopMatrix();
		FontRenderer fontrenderer = this.func_147498_b();
		f3 = 0.016666668F * f1;
		GL11.glTranslatef(0.0F, 0.5F * f1, 0.07F * f1);
		GL11.glScalef(f3, -f3, f3);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
		GL11.glDepthMask(false);
		byte b0 = 0;
		if (screen.getWorldObj() != null) {
			String energy = "C: " + FontHelper.formatStorage(screen.latestEnergy);
			String max = "M: " +FontHelper.formatStorage(screen.latestMax);
			fontrenderer.drawString(energy, -fontrenderer.getStringWidth(energy) / 2, -8, b0);
			fontrenderer.drawString(max, -fontrenderer.getStringWidth(max) / 2, 4, b0);

		}
		GL11.glDepthMask(true);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float var) {
		this.renderTileEntityAt((TileEntityCalculatorScreen) tile, x, y, z, var);
	}
}