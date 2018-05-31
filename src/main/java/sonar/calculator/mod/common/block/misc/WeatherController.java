package sonar.calculator.mod.common.block.misc;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherController;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.network.FlexibleGuiHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class WeatherController extends SonarMachineBlock {

	public WeatherController() {
		super(Material.WOOD, false, true);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return RainSensor.sensor;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return RainSensor.sensor;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null && !world.isRemote) {
			FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
		}
		return true;
	}

	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		TileEntity target = worldIn.getTileEntity(pos);
		if (target instanceof TileEntityWeatherController) {
			TileEntityWeatherController controller = (TileEntityWeatherController) target;
			controller.startProcess();
		}
	}

    @Override
	public TileEntity createNewTileEntity(@Nonnull World world, int meta) {
		return new TileEntityWeatherController();
	}

    @Override
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list, NBTTagCompound tag) {
        CalculatorHelper.addEnergytoToolTip(stack, world, list);
    }

    @Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

    @Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}