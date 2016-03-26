package sonar.calculator.mod.client.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.client.models.ModelFluxPoint;
import sonar.core.helpers.RenderHelper;

public class RenderFluxPoint extends TileEntitySpecialRenderer {
	public ModelFluxPoint model;
	public String texture;

	public RenderFluxPoint() {
		this.model = new ModelFluxPoint();
		this.texture = "Calculator:textures/model/flux_point.png";
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		if (entity != null && entity.getWorldObj() != null) {
			RenderHelper.beginRender(x + 0.5F, y + 1.5F, z + 0.5F, RenderHelper.setMetaData(entity), texture);
			model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			RenderHelper.finishRender();
		} else {
			RenderHelper.beginRender(x + 0.5F, y + 1.5F, z + 0.5F, RenderHelper.setMetaData(entity), texture);
			model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			RenderHelper.finishRender();
		}
	}

}
