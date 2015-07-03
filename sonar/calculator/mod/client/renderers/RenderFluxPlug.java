 package sonar.calculator.mod.client.renderers;

import org.lwjgl.opengl.GL11;

import akka.japi.Effect;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.client.models.ModelFluxPlug;
import sonar.core.utils.helpers.RenderHelper;

public class RenderFluxPlug extends TileEntitySpecialRenderer {
	public ModelFluxPlug model;
	public String on, off;

	public RenderFluxPlug() {
		this.model = new ModelFluxPlug();
		this.on = "Calculator:textures/model/flux_recieve.png";
		this.off =  "Calculator:textures/model/flux_send.png";
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		if (entity!=null && entity.getWorldObj() != null) {
			RenderHelper.beginRender(x + 0.5F, y + 1.5F, z + 0.5F, RenderHelper.setMetaData(entity), on);			
			model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			RenderHelper.finishRender();
		} else {
			RenderHelper.beginRender(x + 0.5F, y + 1.5F, z + 0.5F, RenderHelper.setMetaData(entity), on);
			model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			RenderHelper.finishRender();
		}
	}

}
