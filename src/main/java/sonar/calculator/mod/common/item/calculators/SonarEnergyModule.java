package sonar.calculator.mod.common.item.calculators;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.api.modules.IModuleEnergy;
import sonar.core.utils.helpers.FontHelper;
import cofh.api.energy.IEnergyContainerItem;

public class SonarEnergyModule extends SonarModule implements IEnergyContainerItem {

	public SonarEnergyModule(IModuleEnergy module) {
		super(module);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		list.add(FontHelper.translate("energy.stored") + ": " + getEnergyStored(stack) + " RF");
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		return ((IModuleEnergy) module).receiveEnergy(container, getTagCompound(container), maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		return ((IModuleEnergy) module).extractEnergy(container, getTagCompound(container), maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		return ((IModuleEnergy) module).getEnergyStored(container, getTagCompound(container));
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return ((IModuleEnergy) module).getMaxEnergyStored(container, getTagCompound(container));
	}

}