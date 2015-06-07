package sonar.calculator.mod.common.block.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.IWrench;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherStation;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarMaterials;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class WeatherStation extends SonarMachineBlock implements IWrench {

	private Random rand = new Random();

	public WeatherStation() {
		super(SonarMaterials.machine);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance,
						CalculatorGui.WeatherStation, world, x, y, z);
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityWeatherStation();
	}

	@Override
	public boolean dropStandard(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player,
			List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		for (int X = -1; X <= 1; X++) {
			for (int Z = -1; Z <= 1; Z++) {
				if (!world.getBlock(x + X, y + 1, z + Z).isReplaceable(world, X, y, Z)) {
					return false;
				}
			}
		}
		return true;

	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
		setBlocks(world, x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldblock,
			int oldMetadata) {
		super.breakBlock(world, x, y, z, oldblock, oldMetadata);
		this.removeBlocks(world, x, y, z);
	}
	private void setBlocks(World world, int x, int y, int z) {
		world.setBlock(x, y + 1, z,	Calculator.weatherStationBlock);
		/*
		world.setBlock(x, y + 1, z+1,	Calculator.weatherStationBlock);
		world.setBlock(x, y + 1, z-1,	Calculator.weatherStationBlock);
		world.setBlock(x+1, y + 1, z,	Calculator.weatherStationBlock);
		world.setBlock(x-1, y + 1, z,	Calculator.weatherStationBlock);
		world.setBlock(x+1, y + 1, z+1,	Calculator.weatherStationBlock);
		world.setBlock(x+1, y + 1, z-1,	Calculator.weatherStationBlock);
		world.setBlock(x-1, y + 1, z-1,	Calculator.weatherStationBlock);
		world.setBlock(x-1, y + 1, z+1,	Calculator.weatherStationBlock);
		*/
	}

	private void removeBlocks(World world, int x, int y, int z) {
		world.setBlockToAir(x, y + 1, z);
		/*
		world.setBlockToAir(x, y + 1, z+1);
		world.setBlockToAir(x, y + 1, z-1);
		world.setBlockToAir(x+1, y + 1, z);
		world.setBlockToAir(x-1, y + 1, z);
		world.setBlockToAir(x+1, y + 1, z+1);
		world.setBlockToAir(x+1, y + 1, z-1);
		world.setBlockToAir(x-1, y + 1, z-1);
		world.setBlockToAir(x-1, y + 1, z+1);
		*/
	}
}

