package sonar.calculator.mod.common.block.generators;

import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.ISpecialTooltip;

public class ConductorMast extends SonarMachineBlock implements ISpecialTooltip {

	private Random rand = new Random();

	public ConductorMast() {
		super(SonarMaterials.machine, false, true);
	}
	
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (!world.isRemote) {
			player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 4.0F, 1.0F);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		world.setBlockToAir(pos.offset(EnumFacing.UP, 1));
		world.setBlockToAir(pos.offset(EnumFacing.UP, 2));
		world.setBlockToAir(pos.offset(EnumFacing.UP, 3));
		super.breakBlock(world, pos, state);
		TileEntityConductorMast.setWeatherStationAngles(true, world, pos);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		if (world.getBlockState(pos.offset(EnumFacing.UP, 1)).getBlock() != Blocks.air) {
			return false;
		}
		if (world.getBlockState(pos.offset(EnumFacing.UP, 2)).getBlock() != Blocks.air) {
			return false;
		}
		if (world.getBlockState(pos.offset(EnumFacing.UP, 3)).getBlock() != Blocks.air) {
			return false;
		}
		return true;

	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		setBlocks(world, pos);
	}

	private void setBlocks(World world, BlockPos pos) {
		world.setBlockState(pos.offset(EnumFacing.UP, 1), Calculator.conductormastBlock.getDefaultState());
		world.setBlockState(pos.offset(EnumFacing.UP, 2), Calculator.conductormastBlock.getDefaultState());
		world.setBlockState(pos.offset(EnumFacing.UP, 3), Calculator.conductormastBlock.getDefaultState());

	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityConductorMast();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {

	}	

	public boolean isFullCube() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int getRenderType() {
		return 3;
	}
}
