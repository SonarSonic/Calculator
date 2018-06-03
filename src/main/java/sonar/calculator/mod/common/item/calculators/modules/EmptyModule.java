package sonar.calculator.mod.common.item.calculators.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.api.modules.IModule;

public class EmptyModule implements IModule {

	public static final EmptyModule EMPTY = new EmptyModule();
	
	@Override
	public boolean isLoadable() {
		return true;
	}

	@Override
	public String getName() {
		return "Empty";
	}

	@Override
	public String getClientName(NBTTagCompound tag) {
		return "Empty Module";
	}

	@Override
	public ItemStack getItemStack(NBTTagCompound tag) {
		return ItemStack.EMPTY;
	}
}
