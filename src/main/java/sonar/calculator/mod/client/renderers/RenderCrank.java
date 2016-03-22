package sonar.calculator.mod.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.animation.FastTESR;
import sonar.calculator.mod.client.models.ModelCrankHandle;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;
import sonar.core.utils.helpers.FontHelper;

public class RenderCrank extends FastTESR<TileEntityCrankHandle> {
	
	@Override
	public void renderTileEntityFast(TileEntityCrankHandle te, double x, double y, double z, float partialTicks, int destroyStage, WorldRenderer worldRenderer) {
		World world = te.getWorld();
		IBlockState state = world.getBlockState(te.getPos());
		BlockPos pos = te.getPos();
		IBakedModel ibakedmodel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(state);
		Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(world, ibakedmodel, state, pos, worldRenderer);
	}
}
