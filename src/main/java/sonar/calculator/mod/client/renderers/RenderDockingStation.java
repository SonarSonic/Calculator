package sonar.calculator.mod.client.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.client.models.ModelDockingStation;
import sonar.calculator.mod.common.tileentity.machines.TileEntityDockingStation;
import sonar.core.utils.helpers.RenderHelper;

public class RenderDockingStation extends TileEntitySpecialRenderer {
	private static final String texture = "Calculator:textures/model/dockingstation.png";
	private ModelDockingStation model;

	public RenderDockingStation() {
		this.model = new ModelDockingStation();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {

		RenderHelper.beginRender(x + 0.5F, y + 1.5F, z + 0.5F, RenderHelper.setMetaData(tileentity), texture);
		model.render((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);

		if (tileentity.getWorldObj() != null && tileentity instanceof TileEntityDockingStation) {
			TileEntityDockingStation station = (TileEntityDockingStation) tileentity;
			if (station.calcStack != null) {
				GL11.glTranslated(0, 0.86, -0.20);
				if (station.calcStack.getItem() instanceof ItemBlock) {
					GL11.glRotated(45, 1, 0, 0);
					GL11.glTranslated(0, -0.05, 0.12);
					GL11.glScaled(1, 0.2, 1);
				} else {
					GL11.glRotated(90 + 45, 1, 0, 0);
				}
				RenderHelper.renderItem(tileentity.getWorldObj(), station.calcStack);
			}

		}
		RenderHelper.finishRender();
	}

}
