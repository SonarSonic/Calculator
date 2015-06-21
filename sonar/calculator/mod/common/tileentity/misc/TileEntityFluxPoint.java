package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.api.IFluxPlug;
import sonar.calculator.mod.api.IFluxPoint;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.utils.FluxRegistry;

public class TileEntityFluxPoint extends TileEntityFlux implements IFluxPoint {

	public int priority, maxTransfer = 128000;

	@Override
	public int maxTransfer() {
		return maxTransfer;
	}

	@Override
	public int priority() {
		return this.priority;
	}

	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("priority", priority);
		tag.setInteger("maxTransfer", maxTransfer);
	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.priority = tag.getInteger("priority");
		this.maxTransfer = tag.getInteger("maxTransfer");
	}

	@Override
	public void onSync(Object data, int id) {
		super.onSync(data, id);
		switch (id) {
		case SyncType.SPECIAL6:
			this.priority = (Integer) data;
			break;
		case SyncType.SPECIAL7:
			this.maxTransfer = (Integer) data;
			break;
		}
	}

	@Override
	public SyncData getSyncData(int id) {
		switch (id) {
		case SyncType.SPECIAL6:
			return new SyncData(true, priority);
		case SyncType.SPECIAL7:
			return new SyncData(true, maxTransfer);
		}
		return super.getSyncData(id);
	}
}
