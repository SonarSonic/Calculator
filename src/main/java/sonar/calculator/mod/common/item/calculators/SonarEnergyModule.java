package sonar.calculator.mod.common.item.calculators;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.api.modules.IModuleEnergy;
import sonar.core.api.energy.ISonarEnergyItem;
import sonar.core.api.utils.ActionType;
import sonar.core.handlers.energy.SonarEnergyItemWrapper;
import sonar.core.helpers.FontHelper;

import java.util.List;

@Optional.InterfaceList({@Optional.Interface(iface = "cofh.redstoneflux.api.IEnergyContainerItem", modid = "redstoneflux")})
public class SonarEnergyModule extends SonarModule implements ISonarEnergyItem, IEnergyContainerItem {

	public SonarEnergyModule(IModuleEnergy module) {
		super(module);
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag par4) {
        super.addInformation(stack, world, list, par4);
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
    @Optional.Method(modid = "redstoneflux")
	public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
		return (int) addEnergy(stack, maxReceive, ActionType.getTypeForAction(simulate));
	}

	@Override
    @Optional.Method(modid = "redstoneflux")
	public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
		return (int) removeEnergy(stack, maxExtract, ActionType.getTypeForAction(simulate));
	}

	@Override
    @Optional.Method(modid = "redstoneflux")
	public int getEnergyStored(ItemStack stack) {
		return (int) getEnergyLevel(stack);
	}

	@Override
    @Optional.Method(modid = "redstoneflux")
	public int getMaxEnergyStored(ItemStack stack) {
		return (int) getFullCapacity(stack);
	}

    @Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged || newStack.getItem() != oldStack.getItem() || newStack.getItemDamage() != oldStack.getItemDamage();
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new SonarEnergyItemWrapper(this, stack);
	}
}