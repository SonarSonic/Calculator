package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.CalculatorConfig;
import sonar.core.api.IFlexibleGui;
import sonar.core.api.energy.EnergyMode;
import sonar.core.handlers.energy.EnergyTransferHandler;
import sonar.core.utils.IMachineSides;
import sonar.core.utils.MachineSideConfig;
import sonar.core.utils.MachineSides;

import java.util.ArrayList;

public class TileEntityAdvancedPowerCube extends TileEntityPowerCube implements IFlexibleGui, IMachineSides {

	public MachineSides sides = new MachineSides(MachineSideConfig.INPUT, this, MachineSideConfig.NONE);

	public TileEntityAdvancedPowerCube() {
		syncList.addParts(sides);
		super.storage.setCapacity(CalculatorConfig.ADVANCED_POWER_CUBE_STORAGE);
		super.storage.setMaxTransfer(CalculatorConfig.ADVANCED_POWER_CUBE_TRANSFER_RATE);
		super.CHARGING_RATE = CalculatorConfig.ADVANCED_POWER_CUBE_CHARGING_RATE;
		super.energyMode = EnergyMode.SEND_RECIEVE;
	}

    @Override
	public void update() {
		super.update();
		if (this.isClient()) {
			return;
		}
		this.addEnergy();
		// this.markDirty();
	}

	public void addEnergy() {
		if(this.storage.getEnergyLevel() == 0){
			return;
		}
		ArrayList<EnumFacing> facing = sides.getSidesWithConfig(MachineSideConfig.OUTPUT);
		if (!facing.isEmpty()) {
			EnergyTransferHandler.INSTANCE_SC.transferToAdjacent(this, facing, storage.getMaxExtract());
		}
	}

	@Override
	public EnergyMode getModeForSide(EnumFacing side) {
		if (side != null) {
			return sides.getSideConfig(side).isInput() ? EnergyMode.RECIEVE : sides.getSideConfig(side).isOutput() ? EnergyMode.SEND : EnergyMode.BLOCKED;
		}
		return super.getModeForSide(side);
	}

	@Override
	public MachineSides getSideConfigs() {
		return sides;
	}
}
