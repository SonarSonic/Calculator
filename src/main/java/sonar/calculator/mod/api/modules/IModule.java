package sonar.calculator.mod.api.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.core.api.IRegistryObject;

import javax.annotation.Nonnull;

/**
 * all modules must implement this
 */
public interface IModule extends IRegistryObject {
	
    String getClientName(@Nonnull NBTTagCompound tag);

    ItemStack getItemStack(NBTTagCompound tag);
}
