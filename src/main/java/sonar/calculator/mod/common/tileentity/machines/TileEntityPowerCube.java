package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.CalculatorConfig;
import sonar.core.common.tileentity.TileEntityInventoryReceiver;
import cofh.api.energy.EnergyStorage;

public class TileEntityPowerCube extends TileEntityInventoryReceiver {

	public TileEntityPowerCube() {
		super.storage = new EnergyStorage(CalculatorConfig.getInteger("Standard Machine"));
		super.slots = new ItemStack[2];
		super.maxTransfer = 1;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		charge(0);
		discharge(1);
		
		this.markDirty();
	}

}
