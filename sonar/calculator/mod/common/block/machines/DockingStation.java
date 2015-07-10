package sonar.calculator.mod.common.block.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IWrench;
import sonar.calculator.mod.common.tileentity.machines.TileEntityDockingStation;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarMaterials;

public class DockingStation
  extends SonarMachineBlock implements IWrench{

  private static boolean keepInventory;
  private Random rand = new Random();
  
  public DockingStation() { 
	  super(SonarMaterials.machine);
    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.95F, 1.0F);
  }
  
  @Override
public int getRenderType()
  {
    return -1;
  }
  
  @Override
public boolean isOpaqueCube() {
    return false;
  }
  
  @Override
public boolean renderAsNormalBlock () {
    return false;
  }
  
  @Override
public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if(!player.isSneaking()){
			if(!insertCalculator(player, world, x, y, z)){
				if (!world.isRemote) {
					player.openGui(Calculator.instance, CalculatorGui.DockingStation, world, x, y, z);
				}
			}
			}
			
	  }
    return true;
  }
  public boolean insertCalculator(EntityPlayer player, World world, int x, int y, int z){
	  if(player.getHeldItem()!=null && TileEntityDockingStation.isCalculator(player.getHeldItem())>0){
			if(world.getTileEntity(x, y, z)!=null && world.getTileEntity(x, y, z) instanceof TileEntityDockingStation){
				TileEntityDockingStation station = (TileEntityDockingStation) world.getTileEntity(x, y, z);
				if(station.getStackInSlot(0)==null){
					station.setInventorySlotContents(0, player.getHeldItem().copy());
					player.getHeldItem().stackSize--;
					return true;
				}
			}
			
		}
	  return false;
  }

  @Override
public TileEntity createNewTileEntity(World var1, int var2)
  {
    return new TileEntityDockingStation();
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
