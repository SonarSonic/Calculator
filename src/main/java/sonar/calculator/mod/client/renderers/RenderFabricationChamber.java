package sonar.calculator.mod.client.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.client.models.ModelFabricationArm;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFabricationChamber;
import sonar.core.common.block.SonarBlock;

public class RenderFabricationChamber extends TileEntitySpecialRenderer<TileEntityFabricationChamber> {
	private static final ResourceLocation texture = new ResourceLocation("Calculator:textures/model/fabrication_arm_techne.png");
	private ModelFabricationArm model;

	public RenderFabricationChamber() {
		this.model = new ModelFabricationArm();
	}

	public void renderTileEntityAt(TileEntityFabricationChamber tileentity, double x, double y, double z, float partialTicks, int destroyStage) {
		if(tileentity.getWorld()!=null){
			return;
		}
		int i;
		if (tileentity.getWorld() == null) {
			i = 0;
		} else {
			i = tileentity.getWorld().getBlockState(tileentity.getPos()).getValue(SonarBlock.FACING).getIndex();
		}
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(90F, 0.0F, 1.0F, 0.0F);
		// GlStateManager.rotate(tileentity.angle * 10, 0.0F, 1.0F, 0.0F);
		switch (i) {
		case 1:
			GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 2:
			GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 3:
			GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 4:
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
		}
		int progress=tileentity.currentMoveTime.getObject();
		float rotateR=((float)progress*25)/50;
		float rotateL=360-rotateR;
		
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.5, 0.5, 0.5);
		GlStateManager.translate(0.5, 0.375, 0.75);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		GlStateManager.rotate(rotateR, 0, 1, 0);
		this.model.renderTile((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, false, false, progress);
		GlStateManager.rotate(rotateR, 0, -1, 0);
		GlStateManager.translate(-1, 0.0, 0.0);
		GlStateManager.rotate(rotateL, 0, 1, 0);
		this.model.renderTile((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, false, false, progress);
		GlStateManager.rotate(rotateL, 0, -1, 0);
		GlStateManager.popMatrix();
		GlStateManager.translate(0.5, 1.506, 0.5);
		if (tileentity.selected != null) {
			if (!Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(tileentity.selected )) {
				GL11.glRotated(90, 1, 0, 0);
				GL11.glTranslated(0, -0.0, 0);
				GL11.glScaled(0.7, 0.7, 0.7);
				GL11.glTranslated(-0.72, -0.84, 0.74);
				Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.selected , TransformType.GROUND);
			} else {
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glScaled(0.6, 0.6, 0.6);
				GL11.glTranslated(0.84, 1.08, -0.84);
				Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.selected , TransformType.GROUND);
			}
		}		
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}