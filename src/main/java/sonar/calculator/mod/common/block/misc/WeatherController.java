package sonar.calculator.mod.common.block.misc;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherController;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.IGuiTile;

public class WeatherController extends SonarMachineBlock {

	public WeatherController() {
		super(Material.wood, false, true);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (player != null && !world.isRemote) {
			player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block block) {
		super.onNeighborBlockChange(world, pos, state, block);
		if (!world.isRemote) {
			TileEntity target = world.getTileEntity(pos);
			if (target != null && target instanceof TileEntityWeatherController) {
				TileEntityWeatherController controller = (TileEntityWeatherController) target;
				controller.startProcess();
			}
		}
	}

	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
	}

	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWeatherController();
	}
@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);
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

	public boolean canProvidePower() {
		return true;
	}

}