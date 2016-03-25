package sonar.calculator.mod.api.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleEnergy extends IModule {
	public int receiveEnergy(ItemStack container, NBTTagCompound tag, int maxReceive, boolean simulate);

	public int extractEnergy(ItemStack container, NBTTagCompound tag, int maxExtract, boolean simulate);

	public int getEnergyStored(ItemStack container, NBTTagCompound tag);

	public int getMaxEnergyStored(ItemStack container, NBTTagCompound tag);
}
