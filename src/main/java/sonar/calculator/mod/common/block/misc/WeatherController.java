package sonar.calculator.mod.common.block.misc;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

public class WeatherController extends SonarMachineBlock {
	private IIcon[] icons = new IIcon[2];

	public WeatherController() {
		super(Material.wood);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (player != null) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance, CalculatorGui.WeatherController, world, x, y, z);
			}
			return true;
		}
		return false;
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
		if (!world.isRemote) {
			TileEntity target = world.getTileEntity(x, y, z);
			if (target != null && target instanceof TileEntityWeatherController) {
				TileEntityWeatherController controller = (TileEntityWeatherController) target;
				controller.startProcess();
			}
		}
	}

	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
	}

	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return 0;
	}

	public boolean canProvidePower() {
		return false;
	}

	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWeatherController();
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? this.icons[0] : this.icons[1];
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		this.icons[0] = register.registerIcon(Calculator.modid + ":weather_controller");
		this.icons[1] = register.registerIcon(Calculator.modid + ":rain_sensor_bottom");
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

}