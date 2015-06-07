package sonar.calculator.mod.common.block.calculators;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IUpgradeCircuits;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarAPI;
import sonar.core.utils.SonarMaterials;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AtomicCalculatorBlock
  extends SonarMachineBlock
{
  @SideOnly(Side.CLIENT)
  private IIcon iconFront;
  @SideOnly(Side.CLIENT)
  private IIcon iconTop;
  
  public AtomicCalculatorBlock()
  {
    super(SonarMaterials.machine, true);
  }
  
  @Override
  @SideOnly(Side.CLIENT)
  public void registerBlockIcons	(IIconRegister iconRegister)
  {
    this.blockIcon = iconRegister.registerIcon("Calculator:atomiccalculatoranimate1");
    this.iconFront = iconRegister.registerIcon("Calculator:atomiccalculatoranimate");
    this.iconTop = iconRegister.registerIcon("Calculator:atomiccalculatoranimate2");
  }
  
  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(int side, int metadata)
  {
    return side == metadata ? this.iconFront : side == 0 ? this.iconTop : side == 1 ? this.iconTop : (metadata == 0) && (side == 3) ? this.iconFront : this.blockIcon;
  }
  

  @Override
  public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
  {
    if (!player.isSneaking()) {
    	player.openGui(Calculator.instance, CalculatorGui.AtomicCalculator, world, x, y, z);
    	return true;    
		}
    return false;
  }

@Override
public boolean dropStandard(World world, int x, int y, int z) {
	return true;
}

@Override
public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
	
}

@Override
public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
	
}

@Override
public TileEntity createNewTileEntity(World world, int i) {
	return new TileEntityCalculator.Atomic();
}
@Override
public void breakBlock(World world, int x, int y, int z,
		Block oldblock, int oldMetadata) {
	TileEntity entity = world.getTileEntity(x, y, z);
	
	if (entity != null && entity instanceof IInventory) {
		IInventory tileentity = (IInventory) world.getTileEntity(x, y, z);
		for (int i = 0; i < tileentity.getSizeInventory(); i++) {			
			ItemStack itemstack = tileentity.getStackInSlot(i);

			if (itemstack != null && !( i==0)) {
				float f = this.rand.nextFloat() * 0.8F + 0.1F;
				float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
				float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

				while (itemstack.stackSize > 0) {
					int j = this.rand.nextInt(21) + 10;

					if (j > itemstack.stackSize) {
						j = itemstack.stackSize;
					}

					itemstack.stackSize -= j;

					EntityItem item = new EntityItem(world, x + f, y + f1,z + f2, new ItemStack(itemstack.getItem(), j,itemstack.getItemDamage()));

					if (itemstack.hasTagCompound()) {
						item.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
					}

					world.spawnEntityInWorld(item);
				}
			}
		}

		world.func_147453_f(x, y, z, oldblock);
	}
	
	super.breakBlock(world, x, y, z, oldblock, oldMetadata);
	world.removeTileEntity(x, y, z);

}
}
