package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.machines.GuiPowerCube;
import sonar.calculator.mod.common.containers.ContainerPowerCube;
import sonar.core.api.IFlexibleGui;
import sonar.core.api.energy.EnergyMode;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.handlers.inventories.handling.EnumFilterType;
import sonar.core.handlers.inventories.handling.filters.SlotHelper;

public class TileEntityPowerCube extends TileEntityEnergyInventory implements IFlexibleGui {

	public TileEntityPowerCube() {
		super.storage.setCapacity(CalculatorConfig.POWER_CUBE_STORAGE);
		super.storage.setMaxTransfer(CalculatorConfig.POWER_CUBE_TRANSFER_RATE);
		super.CHARGING_RATE = CalculatorConfig.POWER_CUBE_CHARGING_RATE;
		super.energyMode = EnergyMode.RECIEVE;
		super.inv.setSize(2);
		super.inv.getInsertFilters().put(SlotHelper.chargeSlot(0), EnumFilterType.INTERNAL);
		super.inv.getInsertFilters().put(SlotHelper.dischargeSlot(1), EnumFilterType.INTERNAL);
	}

	@Override
	public void update() {
		super.update();
		if (!world.isRemote) {
			charge(0);
			discharge(1);
		}
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerPowerCube(player.inventory, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiPowerCube(player.inventory, this);
	}
}
