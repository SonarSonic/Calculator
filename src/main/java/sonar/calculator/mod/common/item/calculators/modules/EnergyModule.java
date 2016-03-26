package sonar.calculator.mod.common.item.calculators.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.modules.IModuleEnergy;

public class EnergyModule implements IModuleEnergy {

	@Override
	public String getClientName() {
		return "Energy Module";
	}

	@Override
	public boolean isLoadable() {
		return true;
	}

	@Override
	public String getName() {
		return "Energy Module";
	}

	public int receiveEnergy(ItemStack container, NBTTagCompound tag, int maxReceive, boolean simulate) {
		int energy = tag.getInteger("Energy");
		int energyReceived = Math.min(getMaxEnergyStored(container, tag) - energy, Math.min(getMaxEnergyStored(container, tag) / 10, maxReceive));

		if (!simulate) {
			energy += energyReceived;
			tag.setInteger("Energy", energy);
		}
		return energyReceived;
	}

	public int extractEnergy(ItemStack container, NBTTagCompound tag, int maxExtract, boolean simulate) {
		if ((!tag.hasKey("Energy"))) {
			return 0;
		}
		int energy = tag.getInteger("Energy");
		int energyExtracted = Math.min(energy, Math.min(getMaxEnergyStored(container, tag) / 10, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
			tag.setInteger("Energy", energy);
		}
		return energyExtracted;
	}

	public int getEnergyStored(ItemStack container, NBTTagCompound tag) {
		return tag.getInteger("Energy");
	}

	public int getMaxEnergyStored(ItemStack container, NBTTagCompound tag) {
		return CalculatorConfig.getInteger("Energy Module");
	}

}
