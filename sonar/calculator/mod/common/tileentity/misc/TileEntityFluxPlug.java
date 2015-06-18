package sonar.calculator.mod.common.tileentity.misc;

import ic2.api.energy.tile.IEnergySource;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import scala.actors.threadpool.Arrays;
import sonar.calculator.mod.api.IFlux;
import sonar.calculator.mod.api.IFluxPlug;
import sonar.calculator.mod.api.IFluxPoint;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.utils.FluxRegistry;
import cofh.api.energy.IEnergyProvider;

public class TileEntityFluxPlug extends TileEntityFlux implements IFluxPlug {

	public int pointCount, plugCount, currentInput, currentOutput, transfer;

	public void updateEntity() {
		super.updateEntity();

		if (this.worldObj.isRemote) {
			return;
		}
		if (!this.isMaster()) {

			return;
		}
		List<IFluxPoint> points = this.getPoints();
		TransferList transferList = this.getMaxInput();
		this.currentOutput = this.getMaxOutput();
		this.currentInput = transferList.energy;
		if (!(points.size() > 0) || currentOutput == 0 || currentInput == 0) {
			return;
		}
		int[] inputList = transferList.inputList;
		int[] currentList = inputList;
		int currentTransfer = 0;

		for (int i = 0; i < points.size(); i++) {
			TransferList push = pushToPoint(points.get(i), currentList, points.size());
			currentList = push.inputList;
			currentTransfer += push.energy;

		}
		this.transfer = currentTransfer;

	}

	public TransferList pushToPoint(IFluxPoint point, int[] inputList, int pointSize) {
		int maxOutput = Math.min(point.maxTransfer(), this.maxTransfer());
		int outputted = maxOutput - point.pushEnergy(maxOutput, true);
		int[] currentList = inputList;
		int currentTrans = 0;
		List<IFluxPlug> plugs = this.getPlugs();
		for (int i = 0; i < plugs.size(); i++) {
			if (outputted > 0) {
				int maxTransfer = Math.min(currentList[i], outputted);
				if (maxTransfer > 0) {
					int output = Math.min(maxTransfer - point.pushEnergy(maxTransfer, true), maxTransfer - plugs.get(i).pullEnergy(maxTransfer, true));
					int transfer = output - point.pushEnergy(output - plugs.get(i).pullEnergy(output, false), false);
					outputted -= transfer;
					currentList[i] -= transfer;
					currentTrans += transfer;

				}
			}
		}

		return new TransferList(currentList, currentTrans);
	}

	@Override
	public int maxTransfer() {
		return 1000000000;
	}

	@Override
	public boolean isMaster() {
		if (FluxRegistry.getMaster(this.freq) == null) {
			return false;
		}
		IFlux flux = FluxRegistry.getMaster(this.freq);
		return flux.freq() == this.freq() && flux.dimension() == this.dimension() && flux.xCoord() == xCoord && flux.yCoord() == yCoord && flux.zCoord() == zCoord;
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
	public void onSync(int data, int id) {
		super.onSync(data, id);
		switch (id) {
		case SyncType.SPECIAL5:
			this.plugCount = data;
			break;
		case SyncType.SPECIAL6:
			this.pointCount = data;
			break;
		case SyncType.SPECIAL7:
			this.currentInput = data;
			break;
		case SyncType.SPECIAL8:
			this.currentOutput = data;
			break;
		case SyncType.SPECIAL9:
			this.transfer = data;
			break;
		}
	}

	@Override
	public SyncData getSyncData(int id) {
		IFluxPlug plug = (IFluxPlug) FluxRegistry.getMaster(this.freq);
		TileEntity target = null;
		if (plug != null) {
			target = this.worldObj.getTileEntity(plug.xCoord(), plug.yCoord(), plug.zCoord());
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
		case SyncType.SPECIAL8:
			if (target != null && target instanceof TileEntityFluxPlug) {
				return new SyncData(true, ((TileEntityFluxPlug) target).currentOutput);
			}
		case SyncType.SPECIAL9:
			if (target != null && target instanceof TileEntityFluxPlug) {
				return new SyncData(true, ((TileEntityFluxPlug) target).transfer);
			}
		}
		return super.getSyncData(id);
	}

	@Override
	public void addToFrequency() {
		if (!this.worldObj.isRemote) {
			FluxRegistry.addFlux(this);
			if (FluxRegistry.getMaster(freq) == null) {
				FluxRegistry.addMaster(this);
			}
		}
	}

	@Override
	public void removeFromFrequency() {
		if (!this.worldObj.isRemote) {
			super.removeFromFrequency();
			if (this.isMaster()) {
				FluxRegistry.removeMaster(this);
				List<IFlux> plugs = FluxRegistry.getPlugs(freq);
				if (plugs != null && plugs.size() > 0) {
					for (int i = 0; i < plugs.size(); i++) {
						TileEntity target = this.worldObj.getTileEntity(plugs.get(i).xCoord(), plugs.get(i).yCoord(), plugs.get(i).zCoord());
						if (target != null && target instanceof IFluxPlug) {
							FluxRegistry.addMaster((IFlux) target);
						}

					}
				}
			}
		}
	}
}
