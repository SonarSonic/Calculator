package sonar.calculator.mod.common.tileentity.machines;

import cofh.api.energy.IEnergyProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import sonar.core.api.SonarAPI;
import sonar.core.api.utils.ActionType;
import sonar.core.common.tileentity.TileEntitySonar;
import sonar.core.helpers.SonarHelper;

public class TileEntityCreativePowerCube extends TileEntitySonar implements IEnergyProvider {

	public int maxTransfer = Integer.MAX_VALUE;

	public void update() {
		for (EnumFacing face : EnumFacing.VALUES) {
			TileEntity tile = SonarHelper.getAdjacentTileEntity(this, face);
			if (tile != null)
				SonarAPI.getEnergyHelper().receiveEnergy(tile, maxTransfer, face, ActionType.PERFORM);
		}
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return -1;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return -1;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return Math.min(maxTransfer, maxExtract);
	}
}
