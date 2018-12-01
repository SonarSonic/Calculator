package sonar.calculator.mod.common.item.calculators.modules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sonar.calculator.mod.api.modules.IModule;
import sonar.calculator.mod.common.item.calculators.ModuleItemRegistry;
import sonar.core.api.energy.EnergyType;
import sonar.core.api.utils.ActionType;
import sonar.core.handlers.energy.EnergyTransferHandler;
import sonar.core.helpers.FontHelper;

public abstract class ModuleBase implements IModule {

	@Override
	public boolean isLoadable() {
		return true;
	}

	@Override
	public String getClientName(NBTTagCompound tag) {
		return getItemStack(tag).getDisplayName();
	}

	@Override
	public ItemStack getItemStack(NBTTagCompound tag){
		Item item = ModuleItemRegistry.instance().getValue(getName());
		if (item != null) {
			ItemStack moduleStack = new ItemStack(item, 1);
			moduleStack.setTagCompound(tag);
			return moduleStack;
		}
		return ItemStack.EMPTY;
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
		return (int) EnergyTransferHandler.INSTANCE_SC.chargeItem(container, maxReceive, EnergyType.FE, ActionType.getTypeForAction(simulate));
	}

	protected final long extractEnergy(ItemStack container, Entity entity, long maxExtract, boolean simulate) {
		if (!isCreativeMode(entity)) {
			return EnergyTransferHandler.INSTANCE_SC.dischargeItem(container, maxExtract, EnergyType.FE, ActionType.getTypeForAction(simulate));
		}
		return 0;
	}

	protected final long getEnergyStored(ItemStack container, Entity entity) {
		return EnergyTransferHandler.INSTANCE_SC.getEnergyStored(container, EnergyType.FE);
	}

	protected final long getMaxEnergyStored(ItemStack container, Entity entity) {
		return EnergyTransferHandler.INSTANCE_SC.getEnergyCapacity(container, EnergyType.FE);
	}
}
