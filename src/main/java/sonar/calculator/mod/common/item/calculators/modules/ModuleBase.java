package sonar.calculator.mod.common.item.calculators.modules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sonar.calculator.mod.api.modules.IModule;
import sonar.core.helpers.FontHelper;
import cofh.api.energy.IEnergyContainerItem;

public abstract class ModuleBase implements IModule {

	@Override
	public boolean isLoadable() {
		return true;
	}

	protected final boolean isCreativeMode(Entity entity) {
		return (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode);
	}

	protected final boolean isEnergyAvailable(ItemStack container, Entity entity, World world, int required) {
		boolean toReturn = isCreativeMode(entity) || required <= getEnergyStored(container, entity);
		if (!toReturn && entity instanceof EntityPlayer) {
			FontHelper.sendMessage(FontHelper.translate("energy.notEnough"), world, (EntityPlayer) entity);
		}
		return toReturn;
	}

	protected final int receiveEnergy(ItemStack container, Entity entity, int maxReceive, boolean simulate) {
		if (container.getItem() instanceof IEnergyContainerItem) {
			IEnergyContainerItem item = (IEnergyContainerItem) container.getItem();
			return item.receiveEnergy(container, maxReceive, simulate);
		}
		return 0;
	}

	protected final int extractEnergy(ItemStack container, Entity entity, int maxExtract, boolean simulate) {
		if (!isCreativeMode(entity)) {
			if (container.getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) container.getItem();
				return item.extractEnergy(container, maxExtract, simulate);
			}
		}
		return 0;
	}

	protected final int getEnergyStored(ItemStack container, Entity entity) {
		if (container.getItem() instanceof IEnergyContainerItem) {
			IEnergyContainerItem item = (IEnergyContainerItem) container.getItem();
			return item.getEnergyStored(container);
		}
		return 0;
	}

	protected final int getMaxEnergyStored(ItemStack container, Entity entity) {
		if (container.getItem() instanceof IEnergyContainerItem) {
			IEnergyContainerItem item = (IEnergyContainerItem) container.getItem();
			return item.getMaxEnergyStored(container);
		}
		return 0;
	}

}
