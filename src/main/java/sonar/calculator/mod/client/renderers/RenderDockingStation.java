package sonar.calculator.mod.client.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import sonar.calculator.mod.client.models.ModelDockingStation;
import sonar.calculator.mod.common.tileentity.machines.TileEntityDockingStation;
import sonar.core.helpers.RenderHelper;

public class RenderDockingStation extends TileEntitySpecialRenderer<TileEntityDockingStation> {
	private static final String texture = "Calculator:textures/model/dockingstation.png";
	private ModelDockingStation model;

	public RenderDockingStation() {
		this.model = new ModelDockingStation();
	}

	@Override
	public void renderTileEntityAt(TileEntityDockingStation te, double x, double y, double z, float partialTicks, int destroyStage) {

		RenderHelper.beginRender(x + 0.5F, y + 1.5F, z + 0.5F, RenderHelper.setMetaData(te), texture);
		model.render((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
		TileEntityDockingStation station = (TileEntityDockingStation) te;
		if (station.calcStack != null) {
			GlStateManager.pushAttrib();
			GlStateManager.pushMatrix();
			net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.scale(0.8, 0.8, 0.8);
			//GlStateManager.translate(6.4F, 6.5F, 0.245F);
			
			if (!Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(station.calcStack)) {
				GL11.glRotated(90+45, 1, 0, 0);
				GL11.glTranslated(0, -0.86, -0.58);
				GL11.glScaled(1.75, 1.75, 1.75);
				Minecraft.getMinecraft().getRenderItem().renderItem(station.calcStack, TransformType.GROUND);
			}else{
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glScaled(1, 1, 1);
				GL11.glRotated(45, 1, 0, 0);
				GL11.glTranslated(0, -1+0.05*4, 0.5);
				Minecraft.getMinecraft().getRenderItem().renderItem(station.calcStack, TransformType.GROUND);
			}
			
			//Minecraft.getMinecraft().getRenderItem().renderItem(station.calcStack, TransformType.GROUND);
			//RenderHelper.renderItem(station.calcStack, ItemCameraTransforms.TransformType.FIXED);
			net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}

		RenderHelper.finishRender();
	}

}
