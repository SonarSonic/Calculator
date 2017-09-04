package sonar.calculator.mod.api.modules;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * implemented on modules which update every player tick
 */
public interface IModuleUpdate extends IModule {

    void onUpdate(ItemStack stack, NBTTagCompound tag, World world, Entity entity);
}
