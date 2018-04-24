package sonar.calculator.mod.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sonar.calculator.mod.client.models.ModelFabricationArm;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFabricationChamber;
import sonar.core.common.block.SonarBlock;

public class RenderFabricationChamber extends TileEntitySpecialRenderer<TileEntityFabricationChamber> {
	private static final ResourceLocation texture = new ResourceLocation("Calculator:textures/model/fabrication_arm_techne.png");
	private ModelFabricationArm model;

	public RenderFabricationChamber() {
		this.model = new ModelFabricationArm();
	}

    @Override
    public void render(TileEntityFabricationChamber tileentity, double x, double y, double z, float partialTicks, int destroyStage, float f) {
        //public void renderTileEntityAt(TileEntityFabricationChamber tileentity, double x, double y, double z, float partialTicks, int destroyStage) {
        tileentity.getWorld();
        return;

    }
}