package sonar.calculator.mod.common.item.calculators;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.api.modules.IModule;
import sonar.core.helpers.FontHelper;
import cofh.api.energy.IEnergyContainerItem;

public class SonarUsageModule extends SonarModule implements IEnergyContainerItem {

	public int storage;

	public SonarUsageModule(IModule module, int storage) {
		super(module);
		this.storage = storage;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(FontHelper.translate("energy.stored") + ": " + getEnergyStored(stack) + " RF");
		super.addInformation(stack, player, list, par4);
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		if (container.getTagCompound() == null) {
			container.setTagCompound(new NBTTagCompound());
		}
		int energy = container.getTagCompound().getInteger("Energy");
		int energyReceived = Math.min(getMaxEnergyStored(container) - energy, Math.min(storage / 10, maxReceive));

		if (!simulate) {
			energy += energyReceived;
			container.getTagCompound().setInteger("Energy", energy);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		if ((container.getTagCompound() == null) || (!container.getTagCompound().hasKey("Energy"))) {
			return 0;
		}
		int energy = container.getTagCompound().getInteger("Energy");
		int energyExtracted = Math.min(energy, Math.min(storage / 10, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
			container.getTagCompound().setInteger("Energy", energy);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		if ((!container.hasTagCompound())) {
			return 0;
		}
		return container.getTagCompound().getInteger("Energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return storage;
	}

}
