package sonar.calculator.mod.common.tileentity.misc;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.api.IFlux;
import sonar.calculator.mod.api.IFluxPlug;
import sonar.calculator.mod.api.IFluxPoint;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.TileEntityFluxHandler;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.calculator.mod.utils.helpers.FluxHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;

public class TileEntityFluxPlug extends TileEntityFluxHandler implements IFluxPlug {

	public int pointCount, plugCount, currentInput, currentOutput, transfer;

	public static final int maxTransfer = 2000000000;

	public static final int DISTRIBUTE = 1, SURGE = 2, HYPER_SURGE = 3, GOD_MODE = 4;

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (this.worldObj.isRemote || !this.isMaster()) {
			return;
		}
		TileEntityFluxController controller = FluxHelper.getController(this.networkID);
		List<IFluxPoint> points = FluxHelper.getPoints(networkID);
		if (!FluxHelper.checkPlayerName(this, networkID)) {
			return;
		}
		this.beginTransfer(controller, points);
	}

	public void beginTransfer(TileEntityFluxController controller, List<IFluxPoint> points) {
		TransferList transferList = FluxHelper.getMaxInput(networkID);
		this.currentOutput = FluxHelper.getMaxOutput(networkID);
		this.currentInput = transferList.energy;
		int[] inputList = transferList.inputList;
		int[] currentList = inputList;
		int currentTransfer = 0;

		if (controller != null && controller.transmitterMode == 1) {
			TransferList push = sendEnergy(controller, currentList, controller.recieveMode, true);
			currentList = push.inputList;
			currentTransfer += push.energy;
		}

		if (points != null && points.size() > 0 && currentOutput != 0 && currentInput != 0) {
			for (int i = 0; i < points.size(); i++) {
				TileEntity target = FluxHelper.getTile(points.get(i));
				if (controller == null) {
					if (target != null && target instanceof TileEntityFlux && ((TileEntityFlux) (target)).dimension() == this.dimension()) {
						TransferList push = transferEnergy((IFluxPoint) target, currentList, 0, false);
						currentList = push.inputList;
						currentTransfer += push.energy;
					}
				} else {
					if (FluxHelper.checkPlayerName(target, networkID)) {
						TransferList push = transferEnergy((IFluxPoint) target, currentList, controller.recieveMode, true);
						currentList = push.inputList;
						currentTransfer += push.energy;
					}
				}
			}
		}

		this.transfer = currentTransfer;
	}

	/** used when sending energy to players */
	public TransferList sendEnergy(IFluxPoint point, int[] inputList, int recieveMode, boolean allowDimensions) {
		int maxOutput = Math.min(point.maxTransfer(), this.maxTransfer);
		if (!(maxOutput > 0)) {
			return new TransferList(inputList, 0);
		}
		int outputted = maxOutput - point.pushEnergy(maxOutput, true);
		int[] currentList = inputList;
		int currentTrans = 0;
		List<IFluxPlug> plugs = FluxHelper.getPlugs(networkID);

		for (int i = 0; i < plugs.size(); i++) {
			TileEntity target = FluxHelper.getTile(plugs.get(i));
			if (target instanceof IFluxPlug) {
				int plugTransfer = 0;
				int maxTransfer = Math.min(currentList[i], outputted);

				int output = Math.min(maxTransfer - point.pushEnergy(maxTransfer, true), maxTransfer - ((IFluxPlug) target).pullEnergy(maxTransfer, true));
				plugTransfer += push(point, ((IFluxPlug) target), output);

				outputted -= plugTransfer;
				currentList[i] -= plugTransfer;
				currentTrans += plugTransfer;
			}

		}

		return new TransferList(currentList, currentTrans);
	}

	/**
	 * sends energy from all available handlers to the given point
	 * 
	 * @param point recieving transfer
	 * @param inputList list of maximum rf/t for plugs
	 * @param recieveMode DISTRIBUTE = 1, SURGE = 2, HYPER_SURGE = 3, GOD_MODE = 4;
	 * @param allowDimensions is a controller present
	 * @return
	 */
	public TransferList transferEnergy(IFluxPoint point, int[] inputList, int recieveMode, boolean allowDimensions) {
		int maxOutput = Math.min(point.maxTransfer(), this.maxTransfer);
		if (!(maxOutput > 0)) {
			return new TransferList(inputList, 0);
		}
		int outputted = maxOutput - point.pushEnergy(maxOutput, true);
		int[] currentList = inputList;
		int currentTrans = 0;
		List<IFluxPlug> plugs = FluxHelper.getPlugs(networkID);

		for (int i = 0; i < plugs.size(); i++) {
			int maxTransfer = Math.min(currentList[i], outputted);
			TileEntity target = FluxHelper.getTile(plugs.get(i));
			int plugTransfer = 0;
			if (target != null && (allowDimensions || target instanceof TileEntityFlux && ((IFluxPlug) target).dimension() == point.dimension()) && FluxHelper.checkPlayerName(target, networkID)) {
				switch (recieveMode) {
				case SURGE:
					if (currentList[i] > 0) {
						int limitedOutput = Math.min(currentList[i] - point.pushEnergy(currentList[i], true), currentList[i] - ((IFluxPlug) target).pullEnergy(currentList[i], true));
						plugTransfer += push(point, ((IFluxPlug) target), limitedOutput);
					}
					break;
				case HYPER_SURGE:
					int o = 0;
					int hyperSurge = Math.min(maxOutput - point.pushEnergy(maxOutput, true), maxOutput - ((IFluxPlug) target).pullEnergy(maxOutput, true));
					while (o != 4) {
						plugTransfer += push(point, ((IFluxPlug) target), hyperSurge);
						o++;
					}
					break;
				case GOD_MODE:
					int t = 0;
					int godMode = Math.min(maxOutput - point.pushEnergy(maxOutput, true), maxOutput - ((IFluxPlug) target).pullEnergy(maxOutput, true));
					while (t != 10) {
						plugTransfer += push(point, ((IFluxPlug) target), godMode);
						t++;
					}
					break;
				default:
					if (recieveMode == DISTRIBUTE) { // Even Distribution
						int transfer = this.maxTransfer - ((IFluxPlug) target).pullEnergy(this.maxTransfer, true);
						int percentage = Math.round((float) (maxOutput - point.pushEnergy(maxOutput, true)) / this.currentOutput);
						int adjustedTransfer = (int) (transfer * percentage);
						maxTransfer = Math.max(10, adjustedTransfer);
					}
					int output = Math.min(maxTransfer - point.pushEnergy(maxTransfer, true), maxTransfer - ((IFluxPlug) target).pullEnergy(maxTransfer, true));
					plugTransfer += push(point, ((IFluxPlug) target), output);

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
		if (FluxRegistry.getMaster(this.networkID) == null) {
			return false;
		}
		IFlux flux = FluxRegistry.getMaster(this.networkID);
		return flux.networkID() == networkID && flux.dimension() == this.dimension() && flux.xCoord() == xCoord && flux.yCoord() == yCoord && flux.zCoord() == zCoord;
	}

	@Override
	public void addToFrequency() {
		if (!this.worldObj.isRemote) {
			if (FluxRegistry.getMaster(networkID) == null) {
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
			List<IFlux> plugs = FluxRegistry.getPlugs(networkID);
			if (plugs != null && plugs.size() > 0) {
				for (int i = 0; i < plugs.size(); i++) {
					IFlux plug = plugs.get(i);
					TileEntity target = FluxHelper.getTile(plug);
					if (target != null && target instanceof IFluxPlug) {
						FluxRegistry.addMaster((IFlux) target);
					}

				}
			}

		}
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE) {

			this.transfer = nbt.getInteger("transfer");
			this.currentInput = nbt.getInteger("currentInput");
			this.currentOutput = nbt.getInteger("currentOutput");
		}
		if (type == SyncType.SYNC) {
			this.plugCount = nbt.getInteger("plugCount");
			this.pointCount = nbt.getInteger("pointCount");
			this.currentInput = nbt.getInteger("currentInput");
			this.currentOutput = nbt.getInteger("currentOutput");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE) {
			nbt.setInteger("transfer", transfer);
			nbt.setInteger("currentInput", currentInput);
			nbt.setInteger("currentOutput", currentOutput);
		}
		if (type == SyncType.SYNC) {
			IFluxPlug plug = (IFluxPlug) FluxRegistry.getMaster(this.networkID);
			TileEntity target = null;
			if (plug != null) {
				target = FluxHelper.getTile(plug);
			}
			nbt.setInteger("plugCount", FluxRegistry.plugCount(this.networkID));
			nbt.setInteger("pointCount", FluxRegistry.pointCount(this.networkID));
			if (target != null && target instanceof TileEntityFluxPlug) {
				nbt.setInteger("currentInput", ((TileEntityFluxPlug) target).currentInput);
				nbt.setInteger("currentOutput", ((TileEntityFluxPlug) target).currentOutput);
				nbt.setInteger("transfer", ((TileEntityFluxPlug) target).transfer);
			}
		}
	}

}
