package sonar.calculator.mod.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.client.models.ModelCrankHandle;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;

public class RenderCrank extends TileEntitySpecialRenderer<TileEntityCrankHandle> {
	private static final ResourceLocation texture = new ResourceLocation("Calculator:textures/model/crank.png");
	private ModelCrankHandle model;

	public RenderCrank() {
		this.model = new ModelCrankHandle();
	}

	public void renderTileEntityAt(TileEntityCrankHandle tileentity, double x, double y, double z, float partialTicks, int destroyStage) {
		int i;
		if (tileentity.getWorld() == null || tileentity==null) {
			return;
		} else {
			i = tileentity.getBlockMetadata();
		}
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(90F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(tileentity.angle * 10, 0.0F, 1.0F, 0.0F);
		switch (i) {
		case 1:
			GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 2:
			GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 3:
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 4:
			GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
		}
		this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}