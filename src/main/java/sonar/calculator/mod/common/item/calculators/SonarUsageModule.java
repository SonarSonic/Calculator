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
import sonar.calculator.mod.api.modules.IModule;
import sonar.core.api.energy.ISonarEnergyItem;
import sonar.core.api.utils.ActionType;
import sonar.core.handlers.energy.SonarEnergyItemWrapper;
import sonar.core.helpers.FontHelper;

import java.util.List;

@Optional.InterfaceList({@Optional.Interface(iface = "cofh.redstoneflux.api.IEnergyContainerItem", modid = "redstoneflux")})
public class SonarUsageModule extends SonarModule implements ISonarEnergyItem, IEnergyContainerItem {

	public int storage;

	public SonarUsageModule(IModule module, int storage) {
		super(module);
		this.storage = storage;
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag par4) {
		list.add(FontHelper.translate("energy.stored") + ": " + getEnergyLevel(stack) + " RF");
        super.addInformation(stack, world, list, par4);
	}

	@Override
	public long addEnergy(ItemStack stack, long maxReceive, ActionType action) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		long energy = stack.getTagCompound().getLong("Energy");
		long energyReceived = Math.min(getFullCapacity(stack) - energy, Math.min(storage / 10, maxReceive));

		if (!action.shouldSimulate()) {
			energy += energyReceived;
			stack.getTagCompound().setLong("Energy", energy);
		}
		return energyReceived;
	}

	@Override
	public long removeEnergy(ItemStack stack, long maxExtract, ActionType action) {
        if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("Energy")) {
			return 0;
		}
		long energy = stack.getTagCompound().getLong("Energy");
		long energyExtracted = Math.min(energy, Math.min(storage / 10, maxExtract));

		if (!action.shouldSimulate()) {
			energy -= energyExtracted;
			stack.getTagCompound().setLong("Energy", energy);
		}
		return energyExtracted;
	}

	@Override
	public long getEnergyLevel(ItemStack stack) {
        if (!stack.hasTagCompound()) {
			return 0;
		}
		return stack.getTagCompound().getLong("Energy");
	}

	@Override
	public long getFullCapacity(ItemStack stack) {
		return storage;
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
