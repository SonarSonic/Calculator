package sonar.calculator.mod.common.tileentity.machines;

import sonar.calculator.mod.CalculatorConfig;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.inventory.SonarTileInventory;
import sonar.core.network.sync.SyncEnergyStorage;

public class TileEntityPowerCube extends TileEntityEnergyInventory {

	public TileEntityPowerCube() {
		super.storage = new SyncEnergyStorage(CalculatorConfig.getInteger("Standard Machine"), 200);
		super.inv = new SonarTileInventory(this, 2);
		super.energyMode = EnergyMode.RECIEVE;
		super.maxTransfer = 1;
	}

	@Override
	public void update() {
		super.update();
		charge(0);
		discharge(1);		
		this.markDirty();
	}

}
