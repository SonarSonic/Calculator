package sonar.calculator.mod.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;

public class RenderAnalysingChamber extends TileEntitySpecialRenderer<TileEntityAnalysingChamber> {

	@Override
    //public void renderTileEntityAt(TileEntityAnalysingChamber te, double x, double y, double z, float partialTicks, int destroyStage) {
    public void render(TileEntityAnalysingChamber te, double x, double y, double z, float partialTicks, int destroyStage, float f) {
		if (te.getWorld() != null) {
			ItemStack stack = te.inv().getStackInSlot(0);
			if (!stack.isEmpty()) {
				GL11.glPushMatrix();
				GL11.glTranslatef((float) x, (float) y, (float) z);
				GL11.glRotatef(180.0F, 180.0F, 0.0F, 1.0F);
				int j = 0;
				int meta = te.getBlockMetadata();
				switch (meta) {
				case 2:
					j = 180;
					break;
				case 3:
					j = 0;
					break;
				case 4:
					j = 90;
					break;
				case 5:
					j = 270;
					break;
				}
				GL11.glRotatef(j, 0.0F, 1.0F, 0.0F);
				if (!Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(stack)) {
					GL11.glRotated(90, 1, 0, 0);
					GL11.glTranslated(0, -0.0, 0);
					GL11.glScaled(0.5, 0.5, 0.5);
					if (meta == EnumFacing.EAST.getIndex()) {
						GL11.glTranslated(-0.98, -1, 1.05);
					}else if (meta == EnumFacing.WEST.getIndex()) {
						GL11.glTranslated(0.98, 1, 1.05);
					}else if (meta == EnumFacing.NORTH.getIndex()){
						GL11.glTranslated(-0.98, 1, 1.05);
					}else if (meta == EnumFacing.SOUTH.getIndex()){
						GL11.glTranslated(0.98, -1, 1.05);
					}
					Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.GROUND);
				} else {
					GL11.glRotated(180, 1, 0, 0);
					GL11.glRotated(180, 0, 1, 0);
					GL11.glScaled(0.6, 0.6, 0.6);
					if (meta == EnumFacing.EAST.getIndex()) {
						GL11.glTranslated(0.84, 1.08, -0.84);
					}else if (meta == EnumFacing.WEST.getIndex()) {
						GL11.glTranslated(-0.84, 1.08, 0.84);
					}else if (meta == EnumFacing.NORTH.getIndex()) {
						GL11.glTranslated(0.84, 1.08, 0.84);
					}else if (meta == EnumFacing.SOUTH.getIndex()) {
						GL11.glTranslated(-0.84, 1.08, -0.84);
					}
					Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.GROUND);
				}
				GL11.glPopMatrix();
			}
		}
	}
}
