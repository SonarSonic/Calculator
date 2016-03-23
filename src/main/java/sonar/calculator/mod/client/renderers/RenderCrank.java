package sonar.calculator.mod.client.renderers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.animation.FastTESR;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;

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
