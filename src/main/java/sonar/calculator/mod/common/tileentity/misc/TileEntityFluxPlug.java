package sonar.calculator.mod.common.tileentity.misc;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.api.flux.IFlux;
import sonar.calculator.mod.api.flux.IFluxController;
import sonar.calculator.mod.api.flux.IFluxPlug;
import sonar.calculator.mod.api.flux.IFluxPoint;
import sonar.calculator.mod.client.gui.misc.GuiFluxPlug;
import sonar.calculator.mod.common.containers.ContainerFlux;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.TileEntityFlux.TransferList;
import sonar.calculator.mod.common.tileentity.TileEntityFluxHandler;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.calculator.mod.utils.helpers.FluxHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.SonarHelper;
import sonar.core.network.sync.SyncEnergyStorage;
import sonar.core.utils.IGuiTile;
import cofh.api.energy.IEnergyProvider;

public class TileEntityFluxPlug extends TileEntityFluxHandler implements IFluxPlug, IGuiTile {

	public int pointCount, plugCount, currentInput, currentOutput, transfer;
	public long bufferStorage;
	public SyncEnergyStorage buffer = new SyncEnergyStorage(100000);

	public static final int maxTransfer = 2000000000;

	public static final int DISTRIBUTE = 1, SURGE = 2, HYPER_SURGE = 3, GOD_MODE = 4;

	@Override
	public void update() {
		super.update();
		if (this.worldObj.isRemote) {
			return;
		}
		if (!this.isMaster()) {
			return;
		}
		IFluxController controller = FluxHelper.getController(networkID());
		List<IFluxPoint> points = FluxHelper.getPoints(networkID());
		if (!FluxHelper.checkPlayerName(this, networkID())) {
			return;
		}
		this.beginTransfer(controller, points);
	}

	public int getBuffer(int export, boolean simulate) {
		export -= buffer.extractEnergy(export, simulate);
		return export;
	}

	@Override
	public int pullEnergy(int export, boolean simulate, boolean buffer) {
		if (buffer) {
			export -= this.buffer.extractEnergy(export, simulate);
		}
		for (int i = 0; i < 6; i++) {
			if (this.handlers[i] != null) {
				if (handlers[i] instanceof IEnergyProvider) {
					export -= ((IEnergyProvider) this.handlers[i]).extractEnergy(EnumFacing.VALUES[(i ^ 0x1)], export, simulate);
				}
			}

		}
		return export;
	}

	public void beginTransfer(IFluxController controller, List<IFluxPoint> points) {
		TransferList transferList = FluxHelper.getMaxInput(networkID());
		this.currentOutput = FluxHelper.getMaxOutput(networkID());
		this.currentInput = transferList.energy;
		this.bufferStorage = FluxHelper.getBuffer(networkID());
		int[] inputList = transferList.inputList;
		int[] currentList = inputList;
		int currentTransfer = 0;

		if (controller != null && controller.getTransmitterMode() == 1) {
			TransferList push = sendEnergy(controller, currentList, controller.getRecieveMode(), true);
			currentList = push.inputList;
			currentTransfer += push.energy;
		}
		if (points != null && points.size() > 0 && currentOutput != 0 && (currentInput != 0 || bufferStorage != 0)) {
			for (int i = 0; i < points.size(); i++) {
				TileEntity target = FluxHelper.getTile(points.get(i));
				if (controller == null) {
					if (target != null && target instanceof TileEntityFlux && ((TileEntityFlux) (target)).getCoords().getDimension() == coords.getDimension()) {
						TransferList push = transferEnergy((IFluxPoint) target, currentList, 0, false);
						currentList = push.inputList;
						currentTransfer += push.energy;
					}
				} else {
					if (FluxHelper.checkPlayerName(target, networkID())) {
						TransferList push = transferEnergy((IFluxPoint) target, currentList, controller.getRecieveMode(), true);
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
		List<IFluxPlug> plugs = FluxHelper.getPlugs(networkID());

		for (int i = 0; i < plugs.size(); i++) {
			TileEntity target = FluxHelper.getTile(plugs.get(i));
			if (target instanceof IFluxPlug) {
				int plugTransfer = 0;
				int maxTransfer = Math.min(currentList[i], outputted);

				int output = Math.min(maxTransfer - point.pushEnergy(maxTransfer, true), maxTransfer - ((IFluxPlug) target).pullEnergy(maxTransfer, true, true));
				plugTransfer += push(point, ((IFluxPlug) target), output, false);

				outputted -= plugTransfer;
				currentList[i] -= plugTransfer;
				currentTrans += plugTransfer;
			}

		}

		return new TransferList(currentList, currentTrans);
	}

	/** sends energy from all available handlers to the given point
	 * 
	 * @param point recieving transfer
	 * @param inputList list of maximum rf/t for plugs
	 * @param recieveMode DISTRIBUTE = 1, SURGE = 2, HYPER_SURGE = 3, GOD_MODE = 4;
	 * @param allowDimensions is a controller present
	 * @return */
	public TransferList transferEnergy(IFluxPoint point, int[] inputList, int recieveMode, boolean allowDimensions) {
		int maxOutput = Math.min(point.maxTransfer(), this.maxTransfer);
		if (!(maxOutput > 0)) {
			return new TransferList(inputList, 0);
		}
		int outputted = maxOutput - point.pushEnergy(maxOutput, true);
		int[] currentList = inputList;
		int currentTrans = 0;
		List<IFluxPlug> plugs = FluxHelper.getPlugs(networkID());
		for (int i = 0; i < plugs.size(); i++) {
			int maxTransfer = Math.min(currentList[i], outputted);
			TileEntity target = FluxHelper.getTile(plugs.get(i));
			int plugTransfer = 0;
			if (target != null && (allowDimensions || target instanceof TileEntityFlux && ((IFluxPlug) target).getCoords().getDimension() == point.getCoords().getDimension()) && FluxHelper.checkPlayerName(target, networkID())) {
				switch (recieveMode) {
				case SURGE:
					if (currentList[i] > 0) {
						int limitedOutput = Math.min(currentList[i] - point.pushEnergy(currentList[i], true), currentList[i] - ((IFluxPlug) target).pullEnergy(currentList[i], true, true));
						plugTransfer += push(point, ((IFluxPlug) target), limitedOutput, false);
					}
					break;
				case HYPER_SURGE:
					int o = 0;
					int hyperSurge = Math.min(maxOutput - point.pushEnergy(maxOutput, true), maxOutput - ((IFluxPlug) target).pullEnergy(maxOutput, true, true));
					while (o != 4) {
						plugTransfer += push(point, ((IFluxPlug) target), hyperSurge, false);
						o++;
					}
					break;
				case GOD_MODE:
					int t = 0;
					int godMode = Math.min(maxOutput - point.pushEnergy(maxOutput, true), maxOutput - ((IFluxPlug) target).pullEnergy(maxOutput, true, true));
					while (t != 10) {
						plugTransfer += push(point, ((IFluxPlug) target), godMode, false);
						t++;
					}
					break;
				default:
					if (recieveMode == DISTRIBUTE) { // Even Distribution
						int transfer = this.maxTransfer - ((IFluxPlug) target).pullEnergy(this.maxTransfer, true, true);
						int percentage = Math.round((float) (maxOutput - point.pushEnergy(maxOutput, true)) / this.currentOutput);
						int adjustedTransfer = (int) (transfer * percentage);
						maxTransfer = Math.max(10, adjustedTransfer);
					}
					int output = Math.min(maxTransfer - point.pushEnergy(maxTransfer, true), maxTransfer - ((IFluxPlug) target).pullEnergy(maxTransfer, true, true));
					plugTransfer += push(point, ((IFluxPlug) target), output, false);

				}
				outputted -= plugTransfer;
				currentList[i] -= plugTransfer;
				currentTrans += plugTransfer;
			}

		}

		return new TransferList(currentList, currentTrans);
	}

	public int beginReceive(IFluxController controller, List<IFluxPoint> points, int receive, boolean simulate) {
		int currentTransfer = 0;
		if (points != null && points.size() > 0 && receive != 0) {
			for (int i = 0; i < points.size(); i++) {
				TileEntity target = FluxHelper.getTile(points.get(i));
				if (controller == null) {
					if (target != null && target instanceof TileEntityFlux && ((TileEntityFlux) (target)).getCoords().getDimension() == coords.getDimension()) {
						currentTransfer += receiveEnergy((IFluxPoint) target, receive, 0, false, simulate);
					}
				} else {
					if (FluxHelper.checkPlayerName(target, networkID())) {
						currentTransfer += receiveEnergy((IFluxPoint) target, receive, controller.getRecieveMode(), true, simulate);
					}
				}
			}
		}
		return currentTransfer;
	}

	/** receiving energy from nearby tiles
	 * 
	 * @param point recieving transfer
	 * @param inputList list of maximum rf/t for plugs
	 * @param recieveMode DISTRIBUTE = 1, SURGE = 2, HYPER_SURGE = 3, GOD_MODE = 4;
	 * @param allowDimensions is a controller present
	 * @return */
	public int receiveEnergy(IFluxPoint point, int input, int recieveMode, boolean allowDimensions, boolean simulate) {
		int maxOutput = Math.min(point.maxTransfer(), this.maxTransfer);
		if (!(maxOutput > 0)) {
			return 0;
		}
		int outputted = maxOutput - point.pushEnergy(maxOutput, true);
		int maxTransfer = Math.min(input, outputted);
		int plugTransfer = 0;
		if (allowDimensions || coords.getDimension() == point.getCoords().getDimension() && FluxHelper.checkPlayerName(this, networkID())) {
			switch (recieveMode) {
			case SURGE:
				if (input > 0) {
					int limitedOutput = Math.min(input - point.pushEnergy(input, true), input - pullEnergy(input, true));
					plugTransfer += push(point, this, limitedOutput, simulate);
				}
				break;
			case HYPER_SURGE:
				int o = 0;
				int hyperSurge = Math.min(maxOutput - point.pushEnergy(maxOutput, true), maxOutput - pullEnergy(maxOutput, true));
				while (o != 4) {
					plugTransfer += push(point, this, hyperSurge, simulate);
					o++;
				}
				break;
			case GOD_MODE:
				int t = 0;
				int godMode = Math.min(maxOutput - point.pushEnergy(maxOutput, true), maxOutput - pullEnergy(maxOutput, true));
				while (t != 10) {
					plugTransfer += push(point, this, godMode, simulate);
					t++;
				}
				break;
			default:
				if (recieveMode == DISTRIBUTE) { // Even Distribution
					int transfer = this.maxTransfer - pullEnergy(this.maxTransfer, true);
					int percentage = Math.round((float) (maxOutput - point.pushEnergy(maxOutput, true)) / this.currentOutput);
					int adjustedTransfer = (int) (transfer * percentage);
					maxTransfer = Math.max(10, adjustedTransfer);
				}
				int output = Math.min(maxTransfer - point.pushEnergy(maxTransfer, true), input);
				plugTransfer += push(point, this, output, simulate);

			}

			outputted -= plugTransfer;

		}
		return plugTransfer;
	}

	public int receiveEnergy(EnumFacing dir, int maxTransfer, boolean simulate) {
		int export = maxTransfer;
		TileEntity handler = SonarHelper.getAdjacentTileEntity(this, dir);
		if (handler != null) {
			if (handler instanceof IEnergyProvider) {
				export -= ((IEnergyProvider) handler).extractEnergy(dir.getOpposite(), maxTransfer, true);
			}
		}
		if (maxTransfer == export) {
			IFluxPlug plug = (IFluxPlug) FluxRegistry.getMaster(this.networkID());
			TileEntity target = null;
			if (plug != null) {
				target = FluxHelper.getTile(plug);
			}
			if (target != null) {
				if (!networkName.equals("NETWORK")) {
					return buffer.receiveEnergy(Math.min(maxTransfer, ((TileEntityFluxPlug) target).currentOutput), simulate);
				}
			}
		}

		return 0;
	}

	/** Empties any excess in the buffer */
	public int pushDrainage(TileEntityFluxPlug plug, TileEntityFluxPlug drain, int max, boolean simulate) {
		return max - plug.buffer.receiveEnergy(max - drain.buffer.extractEnergy(max, simulate), simulate);
	}

	public int push(IFluxPoint point, IFluxPlug plug, int max, boolean simulate) {
		return max - point.pushEnergy(max - plug.pullEnergy(max, simulate, true), simulate);
	}

	@Override
	public boolean isMaster() {
		if (FluxRegistry.getMaster(this.networkID()) == null) {
			return false;
		}
		IFlux flux = FluxRegistry.getMaster(this.networkID());
		return flux.networkID() == networkID() && flux.getCoords().getDimension() == coords.getDimension() && flux.getCoords().getX() == pos.getX() && flux.getCoords().getY() == pos.getY() && flux.getCoords().getZ() == pos.getZ();
	}

	@Override
	public void addToFrequency() {
		if (!this.worldObj.isRemote) {
			if (FluxRegistry.getMaster(networkID()) == null) {
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
			List<IFlux> plugs = FluxRegistry.getPlugs(networkID());
			boolean masterSet = false;
			if (plugs != null && plugs.size() > 0) {
				for (int i = 0; i < plugs.size(); i++) {
					IFlux plug = plugs.get(i);
					TileEntity target = FluxHelper.getTile(plug);
					if (target != null && target instanceof TileEntityFluxPlug) {
						TileEntityFluxPlug master = (TileEntityFluxPlug) target;
						if (!masterSet) {
							FluxRegistry.addMaster(master);
							masterSet = true;
						}
						pushDrainage(master, this, maxTransfer, false);
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
			this.buffer.readFromNBT(nbt);
		}
		if (type == SyncType.SYNC) {
			this.plugCount = nbt.getInteger("plugCount");
			this.pointCount = nbt.getInteger("pointCount");
			this.currentInput = nbt.getInteger("currentInput");
			this.currentOutput = nbt.getInteger("currentOutput");
			this.bufferStorage = nbt.getLong("bufferStorage");
			this.transfer = nbt.getInteger("transfer");
		}
		if (type == SyncType.DROP) {
			this.buffer.setEnergyStored(nbt.getInteger("energy"));
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE) {
			nbt.setInteger("transfer", transfer);
			nbt.setInteger("currentInput", currentInput);
			nbt.setInteger("currentOutput", currentOutput);
			buffer.writeToNBT(nbt);
		}
		if (type == SyncType.SYNC) {
			IFluxPlug plug = (IFluxPlug) FluxRegistry.getMaster(this.networkID());
			TileEntity target = null;
			if (plug != null) {
				target = FluxHelper.getTile(plug);
			}
			nbt.setInteger("plugCount", FluxRegistry.plugCount(this.networkID()));
			nbt.setInteger("pointCount", FluxRegistry.pointCount(this.networkID()));
			if (target != null && target instanceof TileEntityFluxPlug) {
				nbt.setInteger("currentInput", ((TileEntityFluxPlug) target).currentInput);
				nbt.setInteger("currentOutput", ((TileEntityFluxPlug) target).currentOutput);
				nbt.setInteger("transfer", ((TileEntityFluxPlug) target).transfer);
				nbt.setLong("bufferStorage", ((TileEntityFluxPlug) target).bufferStorage);
			}
		}
		if (type == SyncType.DROP) {
			nbt.setInteger("energy", this.buffer.getEnergyStored());

		}
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerFlux(player.inventory, this, false);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiFluxPlug(player.inventory, this);
	}

}
