package sonar.calculator.mod.common.block.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.IWrench;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessCapacitor;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarMaterials;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FlawlessCapacitor extends SonarMachineBlock implements IWrench {

	
	public FlawlessCapacitor() {
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
	

	public IIcon getIcon(int side, int metadata) {
		return blockIcon;
		
	}
	@Override
	public boolean operateBlock(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if(!player.isSneaking()){
			if (!world.isRemote) {
				player.openGui(Calculator.instance,
						CalculatorGui.AtomicEnergyCube, world, x, y, z);
			}
			}else{
				TileEntity te =world.getTileEntity(x, y, z);
				if(te!=null && te instanceof TileEntityFlawlessCapacitor){
					TileEntityFlawlessCapacitor cube = (TileEntityFlawlessCapacitor) te;
					cube.incrementSide(side);
					FontHelper.sendMessage("Current Stored: " +cube.storage.getEnergyStored(), world, player);
				}
				
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityFlawlessCapacitor();
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
}
