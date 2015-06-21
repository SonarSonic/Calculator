package sonar.calculator.mod.common.tileentity.misc;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.api.IFlux;
import sonar.calculator.mod.api.IFluxPlug;
import sonar.calculator.mod.api.IFluxPoint;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.utils.FluxRegistry;

public class TileEntityFluxPlug extends TileEntityFlux implements IFluxPlug {

	public int pointCount, plugCount, currentInput, currentOutput, transfer;

	public static final int DISTRIBUTE = 1, SURGE = 2, HYPER_SURGE = 3, GOD_MODE = 4;

	@Override
	public int maxTransfer() {
		return 2000000000;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (this.worldObj.isRemote || !this.isMaster()) {
			return;
		}
		TileEntityFluxController controller = this.getController();
		List<IFluxPoint> points = this.getPoints();
		if (controller != null && !checkPlayerName(this) || points == null) {
			return;
		}
		this.beginTransfer(controller, points);
	}

	public void beginTransfer(TileEntityFluxController controller, List<IFluxPoint> points) {
		TransferList transferList = this.getMaxInput();
		this.currentOutput = this.getMaxOutput();
		this.currentInput = transferList.energy;
		if (!(points.size() > 0) || currentOutput == 0 || currentInput == 0) {
			this.transfer = 0;
			return;
		}
		int[] inputList = transferList.inputList;
		int[] currentList = inputList;
		int currentTransfer = 0;

		for (int i = 0; i < points.size(); i++) {
			IFluxPoint point = points.get(i);
			TileEntity target = this.getTile(point);
			if (controller == null) {
				if (target != null && target instanceof TileEntityFlux && ((TileEntityFlux) (target)).dimension() == this.dimension()) {
					TransferList push = transfer(point, currentList, 0, false);
					currentList = push.inputList;
					currentTransfer += push.energy;
				}
			} else {
				if (this.checkPlayerName(target)) {
					TransferList push = transfer(point, currentList, controller.recieveMode, controller.allowDimensions == 1);
					currentList = push.inputList;
					currentTransfer += push.energy;
				}
			}
		}
		this.transfer = currentTransfer;
	}

	public TransferList transfer(IFluxPoint point, int[] inputList, int recieveMode, boolean allowDimensions) {
		int maxOutput = Math.min(point.maxTransfer(), this.maxTransfer());
		if (!(maxOutput > 0)) {
			return new TransferList(inputList, 0);
		}
		int outputted = maxOutput - point.pushEnergy(maxOutput, true);
		int[] currentList = inputList;
		int currentTrans = 0;
		List<IFluxPlug> plugs = this.getPlugs();

		for (int i = 0; i < plugs.size(); i++) {
			IFluxPlug plug = plugs.get(i);
			TileEntity target = this.getTile(plug);
			int plugTransfer = 0;
			int maxTransfer = Math.min(currentList[i], outputted);
			int limitedOutput = Math.min(currentList[i] - point.pushEnergy(currentList[i], true), currentList[i] - plug.pullEnergy(currentList[i], true));
			int fullOutput = Math.min(maxOutput - point.pushEnergy(maxOutput, true), maxOutput - plug.pullEnergy(maxOutput, true));

			if ((allowDimensions || target != null && target instanceof TileEntityFlux && plug.dimension() == point.dimension()) && this.checkPlayerName(target)) {
				switch (recieveMode) {

				case SURGE:
					if (currentList[i] > 0) {
						plugTransfer += push(point, plug, limitedOutput);
					}
					break;
				case HYPER_SURGE:
					int o = 0;
					while (o != 4) {
						plugTransfer += push(point, plug, fullOutput);
						o++;
					}
					break;
				case GOD_MODE:
					int t = 0;
					while (t != 10) {
						plugTransfer += push(point, plug, fullOutput);
						t++;
					}
					break;
				default:
					if (recieveMode == DISTRIBUTE) { // Even Distribution
						int transfer = this.maxTransfer() - plug.pullEnergy(this.maxTransfer(), true);
						int percentage = Math.round((float) (maxOutput - point.pushEnergy(maxOutput, true)) / this.currentOutput);
						int adjustedTransfer = (int) (transfer * percentage);
						maxTransfer = Math.max(10, adjustedTransfer);
					}
					int output = Math.min(maxTransfer - point.pushEnergy(maxTransfer, true), maxTransfer - plug.pullEnergy(maxTransfer, true));
					plugTransfer += push(point, plug, output);

				}
				outputted -= plugTransfer;
				currentList[i] -= plugTransfer;
				currentTrans += plugTransfer;
			}

		}

		return new TransferList(currentList, currentTrans);
	}

	public int push(IFluxPoint point, IFluxPlug plug, int max) {
		return max - point.pushEnergy(max - plug.pullEnergy(max, false), false);
	}

	@Override
	public boolean isMaster() {
		if (FluxRegistry.getMaster(this.freq) == null) {
			return false;
		}
		IFlux flux = FluxRegistry.getMaster(this.freq);
		return flux.freq() == this.freq() && flux.dimension() == this.dimension() && flux.xCoord() == xCoord && flux.yCoord() == yCoord && flux.zCoord() == zCoord;
	}

	@Override
	public void addToFrequency() {
		if (!this.worldObj.isRemote) {
			if (FluxRegistry.getMaster(freq) == null) {
				FluxRegistry.addMaster(this);
			}
		}
		super.addToFrequency();
	}

	@Override
	public void removeFromFrequency() {
		super.removeFromFrequency();
		if (!this.worldObj.isRemote) {
			if (!this.isMaster()) {
				return;
			}
			FluxRegistry.removeMaster(this);
			List<IFlux> plugs = FluxRegistry.getPlugs(freq);
			if (plugs != null && plugs.size() > 0) {
				for (int i = 0; i < plugs.size(); i++) {
					IFlux plug = plugs.get(i);
					TileEntity target = this.getTile(plug);
					if (target != null && target instanceof IFluxPlug) {
						FluxRegistry.addMaster((IFlux) target);
					}

				}
			}

		}
	}

	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("transfer", transfer);
		tag.setInteger("currentInput", currentInput);
		tag.setInteger("currentOutput", currentOutput);
	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.transfer = tag.getInteger("transfer");
		this.currentInput = tag.getInteger("currentInput");
		this.currentOutput = tag.getInteger("currentOutput");
	}

	@Override
	public void onSync(Object data, int id) {
		super.onSync(data, id);
		switch (id) {
		case SyncType.SPECIAL5:
			this.plugCount = (Integer) data;
			break;
		case SyncType.SPECIAL6:
			this.pointCount = (Integer) data;
			break;
		case SyncType.SPECIAL7:
			this.currentInput = (Integer) data;
			break;
		case SyncType.SPECIAL8:
			this.currentOutput = (Integer) data;
			break;
		case SyncType.TRANSFER:
			this.transfer = (Integer) data;
			break;
		}
	}

	@Override
	public SyncData getSyncData(int id) {
		IFluxPlug plug = (IFluxPlug) FluxRegistry.getMaster(this.freq);
		TileEntity target = null;
		if (plug != null) {
			target = this.getTile(plug);
		}

		switch (id) {
		case SyncType.SPECIAL5:
			return new SyncData(true, FluxRegistry.plugCount(this.freq));
		case SyncType.SPECIAL6:
			return new SyncData(true, FluxRegistry.pointCount(this.freq));

		case SyncType.SPECIAL7:
			if (target != null && target instanceof TileEntityFluxPlug) {
				return new SyncData(true, ((TileEntityFluxPlug) target).currentInput);
			}
			break;
		case SyncType.SPECIAL8:
			if (target != null && target instanceof TileEntityFluxPlug) {
				return new SyncData(true, ((TileEntityFluxPlug) target).currentOutput);
			}
			break;
		case SyncType.TRANSFER:
			if (target != null && target instanceof TileEntityFluxPlug) {
				return new SyncData(true, ((TileEntityFluxPlug) target).transfer);
			}
			break;
		}
		return super.getSyncData(id);
	}

}
