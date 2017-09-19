package sonar.calculator.mod.client.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.client.models.ModelChamber;
import sonar.calculator.mod.client.models.ModelProcessing;
import sonar.calculator.mod.client.models.ModelSplit;
import sonar.calculator.mod.common.tileentity.TileEntityMachine;
import sonar.calculator.mod.common.tileentity.TileEntityProcess;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.RenderHelper;

public abstract class RenderChamber extends TileEntitySpecialRenderer<TileEntity> {
	public ModelChamber model;
	public String texture;
	public ModelProcessing process = new ModelProcessing();
	public ModelSplit splitter = new ModelSplit();

	public ResourceLocation input = new ResourceLocation("Calculator:textures/blocks/overlays/machine_input.png");
	public ResourceLocation output = new ResourceLocation("Calculator:textures/blocks/overlays/machine_output.png");

	public RenderChamber(boolean white) {
		this.model = new ModelChamber();
        if (white) {
			this.texture = "Calculator:textures/model/machine_frame.png";
		} else {
			this.texture = "Calculator:textures/model/machine_frame_black.png";
		}
	}

	public abstract void renderAnimation(TileEntity entity, double x, double y, double z);

	public abstract String getName(TileEntity entity);

	@Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float partialTicks, int destroyStage) {
		RenderHelper.beginRender(x + 0.5F, y + 1.5F, z + 0.5F, RenderHelper.setMetaData(entity), texture);

        if (destroyStage < 0) {
            //GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
        model.render(null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
		this.renderAnimation(entity, x, y, z);
		if (entity != null && entity.getWorld() != null && entity instanceof TileEntityProcess) {
			TileEntityProcess inv = (TileEntityProcess) entity;
            ItemStack target;
			if (inv.cookTime.getObject() == 0 && inv.getStackInSlot(2) != null) {
				target = inv.getStackInSlot(2);
			} else {
				target = inv.getStackInSlot(0);
			}

			if (!target.isEmpty()) {
				if (!Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(target)) {
					GL11.glRotated(90, 1, 0, 0);
					GL11.glTranslated(0, -0.0, -1.17);
					GL11.glScaled(0.5, 0.5, 0.5);
					Minecraft.getMinecraft().getRenderItem().renderItem(target, TransformType.GROUND);
				}else{
					GL11.glRotated(180, 1, 0, 0);
					GL11.glRotated(180, 0, 1, 0);
					GL11.glScaled(0.6, 0.6, 0.6);
					GL11.glTranslated(0, -1-0.05*21, 0);
					Minecraft.getMinecraft().getRenderItem().renderItem(target, TransformType.GROUND);
				}
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
	}

	public static class Processing extends RenderChamber {

		public Processing() {
			super(true);
		}

        @Override
		public void renderAnimation(TileEntity entity, double x, double y, double z) {
			if (entity != null && entity.getWorld() != null) {
                float tick = ((TileEntityProcess) entity).getRenderPosition() / 2F;
				GL11.glTranslated(0, 0, -tick);
                process.render(null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
				GL11.glTranslated(0, 0, +tick);
			} else {
                process.render(null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
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

        @Override
		public void renderAnimation(TileEntity entity, double x, double y, double z) {
			if (entity != null && entity.getWorld() != null) {
                float tick = ((TileEntityProcess) entity).getRenderPosition() / 2F;
				GL11.glTranslated(0, 0, -tick);
                process.render(null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
				GL11.glTranslated(0, 0, +tick);
			} else {
                process.render(null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
			}
		}

		@Override
		public String getName(TileEntity entity) {
			if (entity instanceof TileEntityMachine.RestorationChamber) {
				return "tile.RestorationChamber.name";
			}
			return "tile.ReassemblyChamber.name";
		}
	}

	public static class Extraction extends RenderChamber {

		public Extraction() {
			super(false);
		}

        @Override
		public void renderAnimation(TileEntity entity, double x, double y, double z) {
			if (entity != null && entity.getWorld() != null) {
                float tick = ((TileEntityProcess) entity).getRenderPosition() / 4.5F;
				GL11.glTranslated(0, tick, 0);
                splitter.renderSplitter(null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
				GL11.glTranslated(0, -tick, 0);
                splitter.render(null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
			} else {
                splitter.render(null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
                splitter.renderSplitter(null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
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

        @Override
		public void renderAnimation(TileEntity entity, double x, double y, double z) {
			if (entity != null && entity.getWorld() != null) {
                float tick = ((TileEntityProcess) entity).getRenderPosition() / 4.5F;
				GL11.glTranslated(0, tick, 0);
                splitter.renderSplitter(null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
				GL11.glTranslated(0, -tick, 0);
                splitter.render(null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
			} else {
                splitter.render(null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
                splitter.renderSplitter(null, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
			}
		}

		@Override
		public String getName(TileEntity entity) {
			return "tile.PrecisionChamber.name";
		}
	}
}
