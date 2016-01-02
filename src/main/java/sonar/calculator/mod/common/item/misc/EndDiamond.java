package sonar.calculator.mod.common.item.misc;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sonar.core.common.item.SonarItem;

public class EndDiamond extends SonarItem
{
  
  @Override
public ItemStack onItemRightClick(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityHuman)
  {
    paramWorld.playSoundAtEntity(paramEntityHuman, "random.bow", 0.5F, 0.4F);
    if (!paramWorld.isRemote) {
      paramWorld.spawnEntityInWorld(new EntityEnderPearl(paramWorld, paramEntityHuman));
    }
    return paramItemStack;
  }
}
