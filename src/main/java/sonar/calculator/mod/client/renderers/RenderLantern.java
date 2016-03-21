package sonar.calculator.mod.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.client.models.ModelLantern;
import sonar.calculator.mod.common.tileentity.misc.TileEntityBasicLantern;
import sonar.calculator.mod.common.tileentity.misc.TileEntityGasLantern;

public class RenderLantern extends TileEntitySpecialRenderer {
	private static final ResourceLocation on = new ResourceLocation("Calculator:textures/model/lantern_on.png");
	private static final ResourceLocation off = new ResourceLocation("Calculator:textures/model/lantern_off.png");
	private static final ResourceLocation basic = new ResourceLocation("Calculator:textures/model/lantern_basic.png");

	private ModelLantern model;

	public RenderLantern() {
		this.model = new ModelLantern();
	}

	public float getY(double y, int meta) {
		if (meta == 0) {
			return (float) (y + 1.5F);
		} else {
			return (float) (y + 1.5F + 0.1F);
		}
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
		EnumFacing dir = EnumFacing.getOrientation(i).getOpposite();

		GL11.glTranslatef((float) (x + 0.5F + (dir.offsetX * 0.32)), getY(y, i), (float) (z + 0.5 + (dir.offsetZ * 0.32)));

		if (tileentity instanceof TileEntityGasLantern) {
			TileEntityGasLantern lantern = (TileEntityGasLantern) tileentity;
			if (lantern.isBurning()) {
				Minecraft.getMinecraft().renderEngine.bindTexture(on);
			}
			if (!lantern.isBurning()) {
				Minecraft.getMinecraft().renderEngine.bindTexture(off);
			}
		}

		if (tileentity instanceof TileEntityBasicLantern) {
			Minecraft.getMinecraft().renderEngine.bindTexture(basic);
		}
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
		this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
