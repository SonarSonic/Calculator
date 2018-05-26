package sonar.calculator.mod.common.item.calculators.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.modules.IModuleEnergy;
import sonar.core.api.utils.ActionType;

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

    @Override
	public long receiveEnergy(ItemStack container, NBTTagCompound tag, long maxReceive, ActionType action) {
		long energy = tag.getLong("Energy");
		long energyReceived = Math.min(getMaxEnergyStored(container, tag) - energy, Math.min(getMaxEnergyStored(container, tag) / 10, maxReceive));

		if (!action.shouldSimulate()) {
			energy += energyReceived;
			tag.setLong("Energy", energy);
		}
		return energyReceived;
	}

    @Override
	public long extractEnergy(ItemStack container, NBTTagCompound tag, long maxExtract, ActionType action) {
        if (!tag.hasKey("Energy")) {
			return 0;
		}
		long energy = tag.getLong("Energy");
		long energyExtracted = Math.min(energy, Math.min(getMaxEnergyStored(container, tag) / 10, maxExtract));

		if (!action.shouldSimulate()) {
			energy -= energyExtracted;
			tag.setLong("Energy", energy);
		}
		return energyExtracted;
	}

    @Override
	public long getEnergyStored(ItemStack container, NBTTagCompound tag) {
		return tag.getLong("Energy");
	}

    @Override
	public long getMaxEnergyStored(ItemStack container, NBTTagCompound tag) {
		return CalculatorConfig.ENERGY_MODULE_STORAGE;
	}
}
