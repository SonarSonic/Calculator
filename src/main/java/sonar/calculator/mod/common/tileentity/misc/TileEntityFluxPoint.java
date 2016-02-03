package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.api.flux.IFluxPoint;
import sonar.calculator.mod.common.tileentity.TileEntityFluxHandler;
import sonar.core.utils.helpers.NBTHelper.SyncType;

public class TileEntityFluxPoint extends TileEntityFluxHandler implements IFluxPoint {

	public int priority, maxTransfer = 128000;

	@Override
	public int maxTransfer() {
		return maxTransfer;
	}

	@Override
	public int priority() {
		return this.priority;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.priority = nbt.getInteger("priority");
			this.maxTransfer = nbt.getInteger("maxTransfer");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setInteger("priority", priority);
			nbt.setInteger("maxTransfer", maxTransfer);

		}
	}

}
