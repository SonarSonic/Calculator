package sonar.calculator.mod.common.tileentity;

import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.core.helpers.SonarHelper;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;

public class TileEntityFluxHandler extends TileEntityFlux {

	protected TileEntity[] handlers = new TileEntity[6];

	public int pullEnergy(int export, boolean simulate) {
		for (int i = 0; i < 6; i++) {
			TileEntity target = handlers[i];
			if (target != null) {
				if (target instanceof IEnergyProvider) {
					export -= ((IEnergyProvider) target).extractEnergy(ForgeDirection.VALID_DIRECTIONS[(i ^ 0x1)], export, simulate);
				} else if (target instanceof IEnergySource) {
					if (simulate) {
						export -= ((IEnergySource) target).getOfferedEnergy() * 4;
					} else {
						int remove = (int) Math.min(((IEnergySource) target).getOfferedEnergy(), export / 4);
						export -= remove * 4;
						((IEnergySource) target).drawEnergy(remove);
					}
				}
			}
		}
		return export;
	}

	public int pushEnergy(int recieve, boolean simulate) {
		for (int i = 0; i < 6; i++) {
			TileEntity target = handlers[i];
			if (target != null) {
				if (target instanceof IEnergyReceiver) {
					recieve -= ((IEnergyReceiver) target).receiveEnergy(ForgeDirection.VALID_DIRECTIONS[(i ^ 0x1)], recieve, simulate);
				} else if (target instanceof IEnergySink) {
					if (simulate) {
						recieve -= ((IEnergySink) target).getDemandedEnergy() * 4;
					} else {
						recieve -= (recieve - (((IEnergySink) target).injectEnergy(ForgeDirection.VALID_DIRECTIONS[(i ^ 0x1)], recieve / 4, 128) * 4));
					}
				}
			}
		}
		return recieve;
	}

	public void updateAdjacentHandlers() {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			TileEntity te = SonarHelper.getAdjacentTileEntity(this, dir);
			if (te != null && !(te instanceof TileEntityFlux)) {
				if (SonarHelper.isEnergyHandlerFromSide(te, dir.getOpposite())) {
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
