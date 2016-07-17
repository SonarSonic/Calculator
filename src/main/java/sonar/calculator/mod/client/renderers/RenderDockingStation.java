package sonar.calculator.mod.client.renderers;
/*
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;

import org.lwjgl.opengl.GL11;

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
			GlStateManager.translate(-6.4F, -6.5F, -0.245F);
			GlStateManager.scale(0.8, 0.8, 0.01);
			
			RenderHelper.renderItem(station.calcStack, ItemCameraTransforms.TransformType.FIXED);
			net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}

		RenderHelper.finishRender();
	}

}
*/