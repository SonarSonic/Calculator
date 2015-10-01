package sonar.calculator.mod.common.item.misc;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.entities.EntitySmallStone;
import sonar.core.common.item.SonarItem;

public class SmallStone extends SonarItem
{
  public SmallStone()
  {
    setUnlocalizedName ("SmallStone").setCreativeTab(Calculator.Calculator).setTextureName("Calculator:small_stone");
  }
  @Override
public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
  {
    if (!player.capabilities.isCreativeMode) {
      itemstack.stackSize -= 1;
    }
    world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
    

   if (!world.isRemote) {
	  world.spawnEntityInWorld(new EntitySmallStone(world, player));    
	 }
    return itemstack;    
	
}
  @Override
public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
  {
	 if(player.isSneaking()){
    if (!player.canPlayerEdit(x, y, z, par7, stack))
    {
      return false;
    }
    Block block = world.getBlock(x, y, z);
    
    if (block == Blocks.dirt)
    {
    	world.setBlock(x, y, z, Blocks.gravel);
      stack.stackSize -= 1;
    }
    if (block == Blocks.grass) {
    	world.setBlock(x, y, z, Blocks.gravel);
      stack.stackSize -= 1;
    }
    else {
      return false;
    }
	 }
    return true;
  }
}
