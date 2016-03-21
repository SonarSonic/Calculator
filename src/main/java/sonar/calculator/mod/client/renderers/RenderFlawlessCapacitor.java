package sonar.calculator.mod.client.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.client.models.ModelFlawlessCapacitor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessCapacitor;
import sonar.core.utils.helpers.RenderHelper;

public class RenderFlawlessCapacitor extends TileEntitySpecialRenderer {
	public ModelFlawlessCapacitor model;
	public String texture;

	public RenderFlawlessCapacitor() {
		this.model = new ModelFlawlessCapacitor();
		this.texture = "Calculator:textures/model/flawless_capacitor.png";
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		RenderHelper.beginRender(x + 0.5F, y + 1.5F, z + 0.5F, 0, texture);
		if (entity.getWorld() != null && entity instanceof TileEntityFlawlessCapacitor) {
			TileEntityFlawlessCapacitor capacitor = (TileEntityFlawlessCapacitor) entity;
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, capacitor.getOutputSides());
		} else {
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		RenderHelper.finishRender();
	}

}
