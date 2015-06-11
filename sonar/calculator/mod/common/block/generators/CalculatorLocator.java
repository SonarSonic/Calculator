package sonar.calculator.mod.common.block.generators;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.IWrench;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.IDropTile;
import sonar.core.utils.SonarMaterials;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CalculatorLocator extends SonarMachineBlock implements IWrench {

	public CalculatorLocator() {
		super(SonarMaterials.machine, false);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
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
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		this.getCollisionBoundingBoxFromPool(world, x, y, z);
		if (player != null) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance, CalculatorGui.CalculatorLocator, world, x, y, z);
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		TileEntityCalculatorLocator te = (TileEntityCalculatorLocator) world.getTileEntity(x, y, z);
		if (te.active == 1) {
			float x1 = x + random.nextFloat();
			float y1 = y + 0.5F;
			float z1 = z + random.nextFloat();

			world.spawnParticle("smoke", x1, y1, z1, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", x1, y1, z1, 0.0D, 0.0D, 0.0D);
		}
	}

	public static int multiBlockStructure(World world, int x, int y, int z) {
		for(int size = 1; size<12; size++){
			if(checkSize(world,x,y,z,size)){
				
				return size;
			}
		}
		return 0;
	}
	
	public static boolean checkSize(World world, int x, int y, int z, int size){
		for (int X = -size; X <= size; X++) {
			for (int Z = -size; Z <= size; Z++) {
				if (!(X == -size && Z == -size) && (X == -size && Z == size) && (X == size && Z == -size) && (X == size && Z == size)) {
					if (!(world.getBlock(x + X, y - 1, z + Z) == Calculator.stablestoneBlock)) {
						return false;
					}
				}
			}
		}

		for(int XZ = -(size-1); XZ<=(size-1); XZ++){	
			if(!(world.getBlock(x + XZ, y, z+size) == Calculator.stablestoneBlock)){
				return false;
			}
			else if(!(world.getBlock(x + XZ, y, z-size) == Calculator.stablestoneBlock)){
				return false;
			}
			else if(!(world.getBlock(x + size, y, z+XZ) == Calculator.stablestoneBlock)){
				return false;
			}
			else if(!(world.getBlock(x-size, y, z+XZ) == Calculator.stablestoneBlock)){
				return false;
			}
			
		}
		

		for (int X = -(size-1); X <= (size-1); X++) {
			for (int Z = -(size-1); Z <= (size-1); Z++) {
				if (!(X == 0 && Z == 0)) {
					if (!(world.getBlock(x + X, y, z + Z) == Calculator.calculatorplug)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityCalculatorLocator();
	}

	@Override
	public boolean dropStandard(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		if (CalculatorConfig.energyStorageType == 2) {
			list.add(StatCollector.translateToLocal("energy.generate") + ": " + CalculatorConfig.locatorRF / 4 + " EU/t");

		} else {
			list.add(StatCollector.translateToLocal("energy.generate") + ": " + CalculatorConfig.locatorRF + " RF/t");
		}
	}
}
