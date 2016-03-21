package sonar.calculator.mod.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;

public class TileEntityFluxHandler extends TileEntityFlux {

	protected TileEntity[] handlers = new TileEntity[6];

	public int pullEnergy(int export, boolean simulate) {
		for (int i = 0; i < 6; i++) {
			if (this.handlers[i] != null) {
				if (handlers[i] instanceof IEnergyProvider) {
					export -= ((IEnergyProvider) this.handlers[i]).extractEnergy(EnumFacing.VALUES[(i ^ 0x1)], export, simulate);
	
				}
			}
		}
		return export;
	}

	public int pushEnergy(int recieve, boolean simulate) {
		for (int i = 0; i < 6; i++) {
			if (this.handlers[i] != null) {
				if (handlers[i] instanceof IEnergyReceiver) {
					recieve -= ((IEnergyReceiver) this.handlers[i]).receiveEnergy(EnumFacing.VALUES[(i ^ 0x1)], recieve, simulate);
				}
			}
		}
		return recieve;
	}

	public void updateAdjacentHandlers() {
		for (int i = 0; i < 6; i++) {
			TileEntity te = SonarHelper.getAdjacentTileEntity(this, EnumFacing.getFront(i));
			if (!(te instanceof TileEntityFlux)) {
				if (SonarHelper.isEnergyHandlerFromSide(te, EnumFacing.VALUES[(i ^ 0x1)])) {
					this.handlers[i] = te;
				} else
					this.handlers[i] = null;
			}
		}
	}

	public void onLoaded() {
		super.onLoaded();
		this.updateAdjacentHandlers();
	}

}
