package sonar.calculator.mod.client.renderers;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.client.models.ModelChamber;
import sonar.calculator.mod.client.models.ModelProcessing;
import sonar.calculator.mod.client.models.ModelSplit;
import sonar.calculator.mod.common.tileentity.TileEntityMachines;
import sonar.calculator.mod.common.tileentity.TileEntityProcess;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.RenderHelper;

public abstract class RenderChamber extends TileEntitySpecialRenderer {
	public ModelChamber model;
	public String texture;
	public ModelProcessing process = new ModelProcessing();
	public ModelSplit splitter = new ModelSplit();

	public ResourceLocation input = new ResourceLocation("Calculator:textures/blocks/overlays/machine_input.png");
	public ResourceLocation output = new ResourceLocation("Calculator:textures/blocks/overlays/machine_output.png");

	public RenderChamber(boolean white) {
		this.model = new ModelChamber();
		if (white == true) {
			this.texture = "Calculator:textures/model/machine_frame.png";
		} else {
			this.texture = "Calculator:textures/model/machine_frame_black.png";
		}
	}

	public abstract void renderAnimation(TileEntity entity, double x, double y, double z);

	public abstract String getName(TileEntity entity);

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		RenderHelper.beginRender(x + 0.5F, y + 1.5F, z + 0.5F, RenderHelper.setMetaData(entity), texture);
		model.render((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
		this.renderAnimation(entity, x, y, z);
		if (entity != null && entity.getWorldObj() != null && entity instanceof TileEntityProcess) {
			TileEntityProcess inv = (TileEntityProcess) entity;
			ItemStack target = null;
			if (inv.cookTime == 0 && inv.getStackInSlot(2) != null) {
				target = inv.getStackInSlot(2);
			} else {
				target = inv.getStackInSlot(0);
			}

			if (target != null) {
				GL11.glRotated(90, 1, 0, 0);
				GL11.glTranslated(0, -0.2, -1.17);
				RenderHelper.renderItem(entity.getWorldObj(), target);
			}
		}
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		float f1 = 0.6666667F;
		float f3;

		int j = RenderHelper.setMetaData(entity);
		f3 = 0.0F;

		if (j == 2) {
			f3 = 180.0F;
		}

		if (j == 4) {
			f3 = 90.0F;
		}

		if (j == 5) {
			f3 = -90.0F;
		}
		
		GL11.glRotated(-f3, 0, 1, 0);
		f3 = 0.006666668F * f1;
		GL11.glScalef(f3, -f3, f3);
		GL11.glTranslatef(0.0F, 0.0F, -29.0F);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F);		
		GL11.glRotated(25, 1, 0, 0);
		GL11.glDepthMask(false);
		FontHelper.textCentre(FontHelper.translate(this.getName(entity)), 0, 294, 1);
		GL11.glDepthMask(true);		
		GL11.glRotated(-25, 1, 0, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		/*
		Tessellator tes = Tessellator.instance;
		GL11.glTranslated(x, y, z);
		int[] sides = new int[6];
		if (entity != null && entity.getWorldObj() != null && entity instanceof TileEntityProcess) {
			TileEntityProcess inv = (TileEntityProcess) entity;
			sides = inv.sides;
		}
		
		// bottom
		tes.startDrawingQuads();
		this.bindTexture(sides[0] != 0 ? output : input);
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
		this.bindTexture(sides[1] != 0 ? output : input);
		tes.addVertexWithUV(0, 0.9998, 0, 0, 0);
		tes.addVertexWithUV(1, 0.9998, 0, 0, 1);
		tes.addVertexWithUV(1, 0.9998, 1, 1, 1);
		tes.addVertexWithUV(0, 0.9998, 1, 1, 0);

		tes.addVertexWithUV(0, 0.9998, 0, 0, 0);
		tes.addVertexWithUV(0, 0.9998, 1, 1, 0);
		tes.addVertexWithUV(1, 0.9998, 1, 1, 1);
		tes.addVertexWithUV(1, 0.9998, 0, 0, 1);
		tes.draw();

		tes.startDrawingQuads();
		this.bindTexture(sides[2] != 0 ? output : input);
		tes.addVertexWithUV(0, 0, 0.0002, 0, 0);
		tes.addVertexWithUV(0, 1, 0.0002, 0, 1);
		tes.addVertexWithUV(1, 1, 0.0002, 1, 1);
		tes.addVertexWithUV(1, 0, 0.0002, 1, 0);

		tes.addVertexWithUV(0, 1, 0.0002, 0, 1);
		tes.addVertexWithUV(0, 0, 0.0002, 0, 0);
		tes.addVertexWithUV(1, 0, 0.0002, 1, 0);
		tes.addVertexWithUV(1, 1, 0.0002, 1, 1);
		tes.draw();

		tes.startDrawingQuads();
		this.bindTexture(sides[3] != 0 ? output : input);
		tes.addVertexWithUV(0, 0, 0.9998, 0, 0);
		tes.addVertexWithUV(0, 1, 0.9998, 0, 1);
		tes.addVertexWithUV(1, 1, 0.9998, 1, 1);
		tes.addVertexWithUV(1, 0, 0.9998, 1, 0);

		tes.addVertexWithUV(0, 1, 0.9998, 0, 1);
		tes.addVertexWithUV(0, 0, 0.9998, 0, 0);
		tes.addVertexWithUV(1, 0, 0.9998, 1, 0);
		tes.addVertexWithUV(1, 1, 0.9998, 1, 1);
		tes.draw();

		tes.startDrawingQuads();
		this.bindTexture(sides[4] != 0 ? output : input);
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
		this.bindTexture(sides[5] != 0 ? output : input);
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
		*/
	}

	public static class Processing extends RenderChamber {

		public Processing() {
			super(true);
		}

		public void renderAnimation(TileEntity entity, double x, double y, double z) {
			if (entity != null && entity.getWorldObj() != null) {
				float tick = (((TileEntityProcess) entity).getRenderPosition()) / 2F;
				GL11.glTranslated(0, 0, -tick);
				process.render((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
				GL11.glTranslated(0, 0, +tick);
			} else {
				process.render((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
			}
		}

		@Override
		public String getName(TileEntity entity) {
			return "tile.ProcessingChamber.name";
		}
	}

	public static class Removal extends RenderChamber {

		public Removal() {
			super(false);
		}

		public void renderAnimation(TileEntity entity, double x, double y, double z) {
			if (entity != null && entity.getWorldObj() != null) {
				float tick = (((TileEntityProcess) entity).getRenderPosition()) / 2F;
				GL11.glTranslated(0, 0, -tick);
				process.render((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
				GL11.glTranslated(0, 0, +tick);
			} else {
				process.render((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
			}
		}

		@Override
		public String getName(TileEntity entity) {
			if (entity instanceof TileEntityMachines.RestorationChamber) {
				return "tile.RestorationChamber.name";
			}
			return "tile.ReassemblyChamber.name";
		}
	}

	public static class Extraction extends RenderChamber {

		public Extraction() {
			super(false);
		}

		public void renderAnimation(TileEntity entity, double x, double y, double z) {
			if (entity != null && entity.getWorldObj() != null) {
				float tick = (((TileEntityProcess) entity).getRenderPosition()) / 4.5F;
				GL11.glTranslated(0, tick, 0);
				splitter.renderSplitter((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
				GL11.glTranslated(0, -tick, 0);
				splitter.render((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);

			} else {
				splitter.render((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
				splitter.renderSplitter((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
			}
		}

		@Override
		public String getName(TileEntity entity) {
			return "tile.ExtractionChamber.name";
		}
	}

	public static class Precision extends RenderChamber {

		public Precision() {
			super(true);
		}

		public void renderAnimation(TileEntity entity, double x, double y, double z) {
			if (entity != null && entity.getWorldObj() != null) {
				float tick = (((TileEntityProcess) entity).getRenderPosition()) / 4.5F;
				GL11.glTranslated(0, tick, 0);
				splitter.renderSplitter((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
				GL11.glTranslated(0, -tick, 0);
				splitter.render((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);

			} else {
				splitter.render((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
				splitter.renderSplitter((Entity) null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
			}
		}

		@Override
		public String getName(TileEntity entity) {
			return "tile.PrecisionChamber.name";
		}
	}
}
