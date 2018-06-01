package sonar.calculator.mod.common.block.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.common.tileentity.machines.TileEntityCreativePowerCube;
import sonar.core.common.block.SonarBlockContainer;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;

import javax.annotation.Nonnull;

public class CreativePowerCube extends SonarBlockContainer {

	public CreativePowerCube() {
		super(SonarMaterials.machine, true);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player != null && !world.isRemote) {
			FontHelper.sendMessage("Transfers: " + FontHelper.formatOutput(Integer.MAX_VALUE), world, player);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World world, int i) {
		return new TileEntityCreativePowerCube();
	}
}
