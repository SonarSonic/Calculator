package sonar.calculator.mod.common.item.calculators.modules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sonar.calculator.mod.api.modules.IModule;
import sonar.core.api.SonarAPI;
import sonar.core.api.energy.EnergyType;
import sonar.core.api.utils.ActionType;
import sonar.core.helpers.FontHelper;

public abstract class ModuleBase implements IModule {

	@Override
	public boolean isLoadable() {
		return true;
	}

	protected final boolean isCreativeMode(Entity entity) {
        return entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode;
	}

	protected final boolean isEnergyAvailable(ItemStack container, Entity entity, World world, int required) {
		boolean toReturn = isCreativeMode(entity) || required <= getEnergyStored(container, entity);
		if (!toReturn && entity instanceof EntityPlayer) {
			FontHelper.sendMessage(FontHelper.translate("energy.notEnough"), world, (EntityPlayer) entity);
		}
		return toReturn;
	}

	protected final long receiveEnergy(ItemStack container, Entity entity, long maxReceive, boolean simulate) {
		return (int) SonarAPI.getEnergyHelper().receiveEnergy(container, maxReceive, ActionType.getTypeForAction(simulate));
	}

	protected final long extractEnergy(ItemStack container, Entity entity, long maxExtract, boolean simulate) {
		if (!isCreativeMode(entity)) {
			return (int) SonarAPI.getEnergyHelper().extractEnergy(container, maxExtract, ActionType.getTypeForAction(simulate));
		}
		return 0;
	}

	protected final long getEnergyStored(ItemStack container, Entity entity) {
		return SonarAPI.getEnergyHelper().getEnergyStored(container, EnergyType.RF).stored;
	}

	protected final long getMaxEnergyStored(ItemStack container, Entity entity) {
		return SonarAPI.getEnergyHelper().getEnergyStored(container, EnergyType.RF).capacity;
	}
}
