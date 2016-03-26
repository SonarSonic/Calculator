package sonar.calculator.mod.client.renderers;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.client.models.ModelStorageChamber;
import sonar.core.common.tileentity.TileEntitySidedInventory;
import sonar.core.helpers.RenderHelper;

public class RenderStorageChamber extends TileEntitySpecialRenderer {
	public ModelStorageChamber model;
	public String texture;

	public ResourceLocation inputS = new ResourceLocation("Calculator:textures/blocks/overlays/machine_input.png");
	public ResourceLocation outputS = new ResourceLocation("Calculator:textures/blocks/overlays/machine_output.png");
	public ResourceLocation inputT = new ResourceLocation("Calculator:textures/blocks/overlays/machine_input_storage_top.png");
	public ResourceLocation outputT = new ResourceLocation("Calculator:textures/blocks/overlays/machine_output_storage_top.png");

	public RenderStorageChamber() {
		this.model = new ModelStorageChamber();
		this.texture = "Calculator:textures/model/storage_chamber.png";
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		RenderHelper.beginRender(x + 0.5F, y + 1.5F, z + 0.5F, RenderHelper.setMetaData(entity), texture);
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		RenderHelper.finishRender();
		int[] sides = new int[6];
		if (entity != null && entity.getWorldObj() != null && entity instanceof TileEntitySidedInventory) {
			TileEntitySidedInventory inv = (TileEntitySidedInventory) entity;
			sides = inv.sides;
		}
		if (entity == null || entity.getWorldObj() == null) {
			Tessellator tes = Tessellator.instance;
			GL11.glTranslated(x, y, z);
			tes.startDrawingQuads();
			this.bindTexture(sides[0] != 0 ? outputT : inputT);
			tes.addVertexWithUV(0, 0.0002, 0, 0, 0);
			tes.addVertexWithUV(1, 0.0002, 0, 0, 1);
			tes.addVertexWithUV(1, 0.0002, 1, 1, 1);
			tes.addVertexWithUV(0, 0.0002, 1, 1, 0);

			tes.addVertexWithUV(0, 0.0002, 0, 0, 0);
			tes.addVertexWithUV(0, 0.0002, 1, 1, 0);
			tes.addVertexWithUV(1, 0.0002, 1, 1, 1);
			tes.addVertexWithUV(1, 0.0002, 0, 0, 1);
			tes.draw();

			// top
			tes.startDrawingQuads();
			this.bindTexture(sides[1] != 0 ? outputT : inputT);
			tes.addVertexWithUV(0, 0.9998, 0, 0, 0);
			tes.addVertexWithUV(1, 0.9998, 0, 0, 1);
			tes.addVertexWithUV(1, 0.9998, 1, 1, 1);
			tes.addVertexWithUV(0, 0.9998, 1, 1, 0);

			tes.addVertexWithUV(0, 0.9998, 0, 0, 0);
			tes.addVertexWithUV(0, 0.9998, 1, 1, 0);
			tes.addVertexWithUV(1, 0.9998, 1, 1, 1);
			tes.addVertexWithUV(1, 0.9998, 0, 0, 1);
			tes.draw();

			// top
			tes.startDrawingQuads();
			this.bindTexture(sides[1] != 0 ? outputT : inputT);
			tes.addVertexWithUV(0, 0.8750, 0, 0, 0);
			tes.addVertexWithUV(1, 0.8750, 0, 0, 1);
			tes.addVertexWithUV(1, 0.8750, 1, 1, 1);
			tes.addVertexWithUV(0, 0.8750, 1, 1, 0);

			tes.addVertexWithUV(0, 0.8750, 0, 0, 0);
			tes.addVertexWithUV(0, 0.8750, 1, 1, 0);
			tes.addVertexWithUV(1, 0.8750, 1, 1, 1);
			tes.addVertexWithUV(1, 0.8750, 0, 0, 1);
			tes.draw();

			// top
			tes.startDrawingQuads();
			this.bindTexture(inputT);
			tes.addVertexWithUV(0, 0.5, 0, 0, 0);
			tes.addVertexWithUV(1, 0.5, 0, 0, 1);
			tes.addVertexWithUV(1, 0.5, 1, 1, 1);
			tes.addVertexWithUV(0, 0.5, 1, 1, 0);

			tes.addVertexWithUV(0, 0.5, 0, 0, 0);
			tes.addVertexWithUV(0, 0.5, 1, 1, 0);
			tes.addVertexWithUV(1, 0.5, 1, 1, 1);
			tes.addVertexWithUV(1, 0.5, 0, 0, 1);
			tes.draw();

			tes.startDrawingQuads();
			this.bindTexture(sides[2] != 0 ? outputT : inputT);
			tes.addVertexWithUV(0, 0, 0.0002, 0, 0);
			tes.addVertexWithUV(0, 1, 0.0002, 0, 1);
			tes.addVertexWithUV(1, 1, 0.0002, 1, 1);
			tes.addVertexWithUV(1, 0, 0.0002, 1, 0);

			tes.addVertexWithUV(0, 1, 0.0002, 0, 1);
			tes.addVertexWithUV(0, 0, 0.0002, 0, 0);
			tes.addVertexWithUV(1, 0, 0.0002, 1, 0);
			tes.addVertexWithUV(1, 1, 0.0002, 1, 1);
			tes.draw();
			/*
			 * tes.startDrawingQuads(); this.bindTexture(sides[3] != 0 ? outputT
			 * : inputT); tes.addVertexWithUV(0, 0, 0.9998, 0, 0);
			 * tes.addVertexWithUV(0, 1, 0.9998, 0, 1); tes.addVertexWithUV(1,
			 * 1, 0.9998, 1, 1); tes.addVertexWithUV(1, 0, 0.9998, 1, 0);
			 * 
			 * tes.addVertexWithUV(0, 1, 0.9998, 0, 1); tes.addVertexWithUV(0,
			 * 0, 0.9998, 0, 0); tes.addVertexWithUV(1, 0, 0.9998, 1, 0);
			 * tes.addVertexWithUV(1, 1, 0.9998, 1, 1); tes.draw();
			 */
			tes.startDrawingQuads();
			this.bindTexture(sides[4] != 0 ? outputT : inputT);
			tes.addVertexWithUV(0.0002, 0, 0, 1, 1);
			tes.addVertexWithUV(0.0002, 0, 1, 1, 0);
			tes.addVertexWithUV(0.0002, 1, 1, 0, 0);
			tes.addVertexWithUV(0.0002, 1, 0, 0, 1);

			tes.addVertexWithUV(0.0002, 0, 1, 1, 0);
			tes.addVertexWithUV(0.0002, 0, 0, 1, 1);
			tes.addVertexWithUV(0.0002, 1, 0, 0, 1);
			tes.addVertexWithUV(0.0002, 1, 1, 0, 0);
			tes.draw();

			tes.startDrawingQuads();
			this.bindTexture(sides[5] != 0 ? outputT : inputT);
			tes.addVertexWithUV(0.9998, 0, 0, 1, 1);
			tes.addVertexWithUV(0.9998, 0, 1, 1, 0);
			tes.addVertexWithUV(0.9998, 1, 1, 0, 0);
			tes.addVertexWithUV(0.9998, 1, 0, 0, 1);

			tes.addVertexWithUV(0.9998, 0, 1, 1, 0);
			tes.addVertexWithUV(0.9998, 0, 0, 1, 1);
			tes.addVertexWithUV(0.9998, 1, 0, 0, 1);
			tes.addVertexWithUV(0.9998, 1, 1, 0, 0);
			tes.draw();
			GL11.glTranslated(-x, -y, -z);
		}
	}
}
