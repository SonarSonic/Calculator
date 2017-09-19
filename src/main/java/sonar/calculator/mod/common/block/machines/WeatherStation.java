package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherStation;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.IGuiTile;

public class WeatherStation extends SonarMachineBlock {

	public WeatherStation() {
		super(SonarMaterials.machine, false, true);
	}

    @Override
	public boolean hasSpecialRenderer() {
		return true;
	}
	
    @Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
	
	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (!world.isRemote && player != null) {
			player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityWeatherStation();
	}

	@Override
    public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List<String> list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);
	}

    @Override
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list) {
        CalculatorHelper.addEnergytoToolTip(stack, world, list);
    }

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
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
