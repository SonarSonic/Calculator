package sonar.calculator.mod.common.tileentity;

import java.util.List;

import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import sonar.calculator.mod.utils.FluxNetwork;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFluxHandler extends TileEntityFlux {

	protected TileEntity[] handlers = new TileEntity[6];

	public int pullEnergy(int export, boolean simulate) {
		for (int i = 0; i < 6; i++) {
			if (this.handlers[i] != null) {
				if (handlers[i] instanceof IEnergyProvider) {
					export -= ((IEnergyProvider) this.handlers[i]).extractEnergy(ForgeDirection.VALID_DIRECTIONS[(i ^ 0x1)], export, simulate);
				} else if (handlers[i] instanceof IEnergySource) {
					if (simulate) {
						export -= ((IEnergySource) this.handlers[i]).getOfferedEnergy() * 4;
					} else {
						int remove = (int) Math.min(((IEnergySource) this.handlers[i]).getOfferedEnergy(), export / 4);
						export -= remove * 4;
						((IEnergySource) this.handlers[i]).drawEnergy(remove);
					}
				}
			}
		}
		return export;
	}

	public int pushEnergy(int recieve, boolean simulate) {
		for (int i = 0; i < 6; i++) {
			if (this.handlers[i] != null) {
				if (handlers[i] instanceof IEnergyReceiver) {
					recieve -= ((IEnergyReceiver) this.handlers[i]).receiveEnergy(ForgeDirection.VALID_DIRECTIONS[(i ^ 0x1)], recieve, simulate);
				} else if (handlers[i] instanceof IEnergySink) {
					if (simulate) {
						recieve -= ((IEnergySink) this.handlers[i]).getDemandedEnergy() * 4;
					} else {
						recieve -= (recieve - (((IEnergySink) this.handlers[i]).injectEnergy(ForgeDirection.VALID_DIRECTIONS[(i ^ 0x1)], recieve / 4, 128) * 4));
					}
				}
			}
		}
		return recieve;
	}

	public void updateAdjacentHandlers() {
		for (int i = 0; i < 6; i++) {
			TileEntity te = SonarHelper.getAdjacentTileEntity(this, ForgeDirection.getOrientation(i));
			if (!(te instanceof TileEntityFlux)) {
				if (SonarHelper.isEnergyHandlerFromSide(te, ForgeDirection.VALID_DIRECTIONS[(i ^ 0x1)])) {
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
