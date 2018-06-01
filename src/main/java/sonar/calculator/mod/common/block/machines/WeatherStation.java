package sonar.calculator.mod.common.block.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherStation;
import sonar.core.common.block.SonarBlockContainer;
import sonar.core.common.block.SonarMaterials;
import sonar.core.network.FlexibleGuiHandler;

import javax.annotation.Nonnull;

public class WeatherStation extends SonarBlockContainer {

	public WeatherStation() {
		super(SonarMaterials.machine, false);
		this.hasSpecialRenderer = true;
	}

    @Nonnull
    @Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote && player != null) {
			FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World var1, int var2) {
		return new TileEntityWeatherStation();
	}

	@Override
	public boolean canPlaceBlockAt(World world, @Nonnull BlockPos pos) {
		for (int X = -1; X <= 1; X++) {
			for (int Z = -1; Z <= 1; Z++) {
				if (!world.getBlockState(pos.add(X, 1, Z)).getBlock().isReplaceable(world, pos.add(X, 1, Z))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		setBlocks(world, pos, state);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		super.breakBlock(world, pos, state);
		this.removeBlocks(world, pos, state);
	}

	private void setBlocks(World world, BlockPos pos, IBlockState state) {
		world.setBlockState(pos.offset(EnumFacing.UP), Calculator.weatherStationBlock.getDefaultState());
	}

	private void removeBlocks(World world, BlockPos pos, IBlockState state) {
		world.setBlockToAir(pos.offset(EnumFacing.UP));
	}
}
