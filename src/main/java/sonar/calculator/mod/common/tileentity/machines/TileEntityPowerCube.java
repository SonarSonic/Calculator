package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.CalculatorConfig;
import sonar.core.common.tileentity.TileEntityInventoryReceiver;
import sonar.core.network.sync.SyncEnergyStorage;

public class TileEntityPowerCube extends TileEntityInventoryReceiver {

	public TileEntityPowerCube() {
		super.storage = new SyncEnergyStorage(CalculatorConfig.getInteger("Standard Machine"), 200);
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
