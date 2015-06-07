package sonar.calculator.mod.common.block.generators;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarMaterials;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class CrankHandle
  extends SonarMachineBlock
{
  private Random rand = new Random();
  @SideOnly(Side.CLIENT)
  private IIcon icon;
  
  public CrankHandle() {
    super(SonarMaterials.machine);
    setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.9F, 0.7F);
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
    TileEntityCrankHandle crank = (TileEntityCrankHandle)world.getTileEntity(x, y, z);
    int rand1 = 0 + (int)(Math.random() * 100.0D);
    
    if (!crank.cranked) {
      crank.cranked = true;
      if (rand1 < 1) {
        dropBlockAsItem(world, x, y, z, new ItemStack(Items.stick, 2));
        world.setBlockToAir(x, y, z);
      }
      
      return true;
    }
    
    return true;
  }

  @Override
@SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister register)
  {
    this.icon = register.registerIcon("Calculator:crank");
  }
  
  @Override
public boolean canPlaceBlockAt(World world, int x, int y, int z) {
    super.canPlaceBlockAt(world, x, y, z);
    if ((y >= 0) && (y < 256))
    {
      Block block = world.getBlock(x, y - 1, z);
      if (block == Calculator.handcrankedGenerator) {
        return true;
      }
    }
    
    return false;
  }
  
  @Override
public void onNeighborBlockChange(World world, int x, int y, int z, Block id) {
    super.canPlaceBlockAt(world, x, y, z);
    Block block = world.getBlock(x, y - 1, z);
    if (block != Calculator.handcrankedGenerator) {
      world.func_147480_a(x, y, z, true);
      world.markBlockForUpdate(x, y, z);
    }
  }
  
  @Override
@SideOnly(Side.CLIENT)
  public IIcon getIcon(int p_149691_1_, int p_149691_2_)
  {
    return this.icon;
  }
  
  @Override
public TileEntity createNewTileEntity(World var1, int var2)
  {
    return new TileEntityCrankHandle();
  }

@Override
public boolean dropStandard(World world, int x, int y, int z) {
	return true;
}

@Override
public void addSpecialToolTip(ItemStack stack, EntityPlayer player,
		List list) {
	
}

@Override
public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
	
}
}
