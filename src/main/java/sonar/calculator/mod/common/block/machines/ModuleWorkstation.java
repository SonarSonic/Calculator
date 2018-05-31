package sonar.calculator.mod.common.block.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityModuleWorkstation;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.network.FlexibleGuiHandler;

import javax.annotation.Nonnull;

public class ModuleWorkstation extends SonarMachineBlock {

	public ModuleWorkstation() {
		super(SonarMaterials.machine, true, true);
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null && !world.isRemote) {
			FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World world, int i) {
		return new TileEntityModuleWorkstation();
	}
}
