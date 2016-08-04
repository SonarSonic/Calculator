package sonar.calculator.mod.common.item.calculators;

import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.api.modules.IModuleEnergy;
import sonar.core.api.energy.ISonarEnergyItem;
import sonar.core.api.utils.ActionType;
import sonar.core.helpers.FontHelper;

public class SonarEnergyModule extends SonarModule implements ISonarEnergyItem, IEnergyContainerItem {

	public SonarEnergyModule(IModuleEnergy module) {
		super(module);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		list.add(FontHelper.translate("energy.stored") + ": " + getEnergyLevel(stack) + " RF");
	}

	@Override
	public long addEnergy(ItemStack stack, long maxReceive, ActionType action) {
		return ((IModuleEnergy) module).receiveEnergy(stack, getTagCompound(stack), maxReceive, action);
	}

	@Override
	public long removeEnergy(ItemStack stack, long maxExtract, ActionType action) {
		return ((IModuleEnergy) module).extractEnergy(stack, getTagCompound(stack), maxExtract, action);
	}

	@Override
	public long getEnergyLevel(ItemStack stack) {
		return ((IModuleEnergy) module).getEnergyStored(stack, getTagCompound(stack));
	}

	@Override
	public long getFullCapacity(ItemStack stack) {
		return ((IModuleEnergy) module).getMaxEnergyStored(stack, getTagCompound(stack));
	}

	@Override
	public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
		return (int) addEnergy(stack, maxReceive, ActionType.getTypeForAction(simulate));
	}

	@Override
	public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
		return (int) removeEnergy(stack, maxExtract, ActionType.getTypeForAction(simulate));
	}

	@Override
	public int getEnergyStored(ItemStack stack) {
		return (int) getEnergyLevel(stack);
	}

	@Override
	public int getMaxEnergyStored(ItemStack stack) {
		return (int) getFullCapacity(stack);
	}

	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged || newStack.getItem() != oldStack.getItem() || newStack.getItemDamage() != oldStack.getItemDamage();
	}
}