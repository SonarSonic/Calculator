package sonar.calculator.mod.common.item.tools;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.core.common.item.SonarItem;

public class ObsidianKey extends SonarItem
{
  private static Random rand = new Random();
  
  public ObsidianKey() { setMaxDamage(1000);
    this.maxStackSize = 1;
  }
  
  @Override
public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
  {
    if (!player.canPlayerEdit(x, y, z, par7, stack))
    {
      return false;
    }
    Block block = world.getBlock(x, y, z);
    
    if (block == Calculator.purifiedobsidianBlock)
    {
    	block.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		world.setBlockToAir(x, y, z);
		stack.damageItem(1, player);
    }
    else if (block == Blocks.obsidian)
    {
    	block.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		world.setBlockToAir(x, y, z);
		stack.damageItem(1, player);

    }
    else
    {
      return false;
    }
    


    return true;
  }
  



  public static float damageItem(int i, EntityPlayer entityplayer)
  {
    return 1.0F;
  }
}
