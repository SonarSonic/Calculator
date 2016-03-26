package sonar.calculator.mod.common.item.calculators;

import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.core.common.item.InventoryContainerItem;
import sonar.core.energy.SonarElectricManager;
import sonar.core.helpers.FontHelper;
import sonar.core.integration.SonarLoader;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Optional.InterfaceList(value = { @Optional.Interface(iface = "ic2.api.item.ISpecialElectricItem", modid = "IC2", striprefs = true), @Optional.Interface(iface = "ic2.api.item.IElectricItem", modid = "IC2", striprefs = true) })
public class SonarCalculator extends InventoryContainerItem implements IEnergyContainerItem, ISpecialElectricItem {

	protected int capacity = CalculatorConfig.getInteger("Calculator");
	protected int maxReceive = 200;
	protected int maxExtract = 200;
	protected int maxTransfer = 200;

	public SonarCalculator() {
		super();
		setCreativeTab(Calculator.Calculator);
		setMaxStackSize(1);
	}

	public ItemStack onCalculatorRightClick(ItemStack itemstack, World world, EntityPlayer player, int ID) {
		if (!world.isRemote) {
			if (player.capabilities.isCreativeMode) {
				player.openGui(Calculator.instance, ID, world, 0, -1000, 0);
				return itemstack;
			} else if (getEnergyStored(itemstack) > 1) {
				player.openGui(Calculator.instance, ID, world, 0, -1000, 0);
				return itemstack;

			} else if ((getEnergyStored(itemstack) < 1)) {
				FontHelper.sendMessage(FontHelper.translate("energy.notEnough"), world, player);
				return itemstack;
			}
		}
		return itemstack;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		if (!CalculatorConfig.isEnabled(stack)) {
			list.add(FontHelper.translate("calc.ban"));
		}
		super.addInformation(stack, player, list, par4);
		list.add(FontHelper.translate("energy.stored") + ": " + getEnergyStored(stack) + " RF");

	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		if (container.getTagCompound() == null) {
			container.stackTagCompound = new NBTTagCompound();
		}
		int energy = container.getTagCompound().getInteger("Energy");
		int energyReceived = Math.min(this.capacity - energy, Math.min(this.maxReceive, maxReceive));

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
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

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
		return capacity;
	}

	@Method(modid = "IC2")
	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return true;
	}

	@Method(modid = "IC2")
	@Override
	public Item getChargedItem(ItemStack itemStack) {
		return this;
	}

	@Method(modid = "IC2")
	@Override
	public Item getEmptyItem(ItemStack itemStack) {
		return null;
	}

	@Method(modid = "IC2")
	@Override
	public double getMaxCharge(ItemStack itemStack) {
		return this.capacity / 4;
	}

	@Method(modid = "IC2")
	@Override
	public int getTier(ItemStack itemStack) {
		return 4;
	}

	@Method(modid = "IC2")
	@Override
	public double getTransferLimit(ItemStack itemStack) {
		return this.maxTransfer / 4;
	}

	@Method(modid = "IC2")
	@Override
	public IElectricItemManager getManager(ItemStack itemStack) {
		if (SonarLoader.ic2Loaded()) {
			return new SonarElectricManager();
		} else
			return null;
	}

}
