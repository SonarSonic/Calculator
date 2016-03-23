package sonar.calculator.mod.common.item.calculators;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.core.common.item.InventoryContainerItem;
import sonar.core.utils.helpers.FontHelper;
import cofh.api.energy.IEnergyContainerItem;

public class SonarCalculator extends InventoryContainerItem implements IEnergyContainerItem{

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
			System.out.print("click");
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
			container.setTagCompound(new NBTTagCompound());
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

}
