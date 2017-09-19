package sonar.calculator.mod.api.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.core.api.utils.ActionType;

public interface IModuleEnergy extends IModule {
    long receiveEnergy(ItemStack container, NBTTagCompound tag, long maxReceive, ActionType action);

    long extractEnergy(ItemStack container, NBTTagCompound tag, long maxExtract, ActionType action);

    long getEnergyStored(ItemStack container, NBTTagCompound tag);

    long getMaxEnergyStored(ItemStack container, NBTTagCompound tag);
}
