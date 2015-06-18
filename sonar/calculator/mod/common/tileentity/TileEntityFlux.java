package sonar.calculator.mod.common.tileentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.api.IFlux;
import sonar.calculator.mod.api.IFluxPlug;
import sonar.calculator.mod.api.IFluxPoint;
import sonar.calculator.mod.api.ISyncTile;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.core.common.tileentity.TileEntitySonar;
import sonar.core.utils.SonarAPI;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.Optional.Method;

public abstract class TileEntityFlux extends TileEntitySonar implements IEnergyHandler, ISyncTile, IEnergySink, IFlux {

	public int freq, x, y, z, handlerCount;
	private int dimension;
	public String playerName;

	TileEntity[] handlers = new TileEntity[6];

	public abstract int maxTransfer();

	public IFluxPoint getPoint(IFlux flux) {
		MinecraftServer server = MinecraftServer.getServer();
		World world = server.worldServerForDimension(flux.dimension());
		if (world != null) {
			TileEntity target = world.getTileEntity(flux.xCoord(), flux.yCoord(), flux.zCoord());
			if (target != null && target instanceof IFluxPoint) {
				return (IFluxPoint) target;
			}
		}
		return null;
	}

	public IFluxPlug getPlug(IFlux flux) {
		MinecraftServer server = MinecraftServer.getServer();
		World world = server.worldServerForDimension(flux.dimension());
		if (world != null) {
			TileEntity target = world.getTileEntity(flux.xCoord(), flux.yCoord(), flux.zCoord());
			if (target != null && target instanceof IFluxPlug) {
				return (IFluxPlug) target;
			}
		}
		return null;
	}

	public List<IFluxPoint> getPoints() {
		List<IFlux> fluxList = FluxRegistry.getPoints(freq);
		List<IFluxPoint> points = new ArrayList();
		for (int i = 0; i < fluxList.size(); i++) {
			IFluxPoint point = this.getPoint(fluxList.get(i));
			if (point != null && point.freq() == this.freq) {
				points.add(point);
			} else {
				FluxRegistry.removeFlux(fluxList.get(i));
			}
		}
		Collections.sort(points, new Comparator<IFluxPoint>() {
			public int compare(IFluxPoint o1, IFluxPoint o2) {
				return o2.priority() - o1.priority();
			}
		});
		return points;
	}

	public List<IFluxPlug> getPlugs() {
		List<IFlux> fluxList = FluxRegistry.getPlugs(freq);
		List<IFluxPlug> plugs = new ArrayList();
		for (int i = 0; i < fluxList.size(); i++) {
			IFluxPlug plug = this.getPlug(fluxList.get(i));
			if (plug != null && plug.freq() == this.freq) {
				plugs.add(plug);
			} else {
				FluxRegistry.removeFlux(fluxList.get(i));
			}
		}
		return plugs;
	}

	public int getMaxOutput() {
		List<IFlux> points = FluxRegistry.getPoints(freq);
		int output = 0;
		if (points.size() != 0) {
			for (int i = 0; i < points.size(); i++) {
				if (points.get(i) != null) {
					TileEntity target = this.getWorldObj().getTileEntity(points.get(i).xCoord(), points.get(i).yCoord(), points.get(i).zCoord());
					if (target != null && target instanceof IFluxPoint) {
						IFluxPoint point = (IFluxPoint) target;
						int transfer = Math.min(this.maxTransfer(), point.maxTransfer());
						output += transfer - point.pushEnergy(transfer, true);
					}

				}
			}
		}
		return output;
	}

	public TransferList getMaxInput() {
		List<IFluxPlug> plugs = this.getPlugs();
		int input = 0;
		int[] inputList = new int[plugs.size()];
		for (int i = 0; i < plugs.size(); i++) {
			int transfer = plugs.get(i).pullEnergy(this.maxTransfer(), true);
			input += this.maxTransfer() - transfer;
			inputList[i] = this.maxTransfer() - transfer;

		}
		return new TransferList(inputList, input);
	}

	public void setName(int newName) {
		int oldFreq = freq;
		this.removeFromFrequency();
		this.freq = newName;
		this.addToFrequency();
	}

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
		handlerCount = 0;
		for (int i = 0; i < 6; i++) {
			TileEntity te = SonarHelper.getAdjacentTileEntity(this, ForgeDirection.getOrientation(i));
			if (!(te instanceof TileEntityFlux)) {
				if (SonarHelper.isEnergyHandlerFromSide(te, ForgeDirection.VALID_DIRECTIONS[(i ^ 0x1)])) {
					this.handlers[i] = te;
					handlerCount++;
				} else
					this.handlers[i] = null;
			}
		}
	}

	public void addToFrequency() {
		if (!this.worldObj.isRemote) {
			FluxRegistry.addFlux(this);
		}
	}

	public void removeFromFrequency() {
		if (!this.worldObj.isRemote) {
			FluxRegistry.removeFlux(this);
		}
	}

	// save methods
	@Override
	public void onSync(int data, int id) {
		switch (id) {
		case SyncType.SPECIAL1:
			this.freq = data;
			break;
		case SyncType.SPECIAL2:
			this.x = data;
			break;
		case SyncType.SPECIAL3:
			this.y = data;
			break;
		case SyncType.SPECIAL4:
			this.z = data;
			break;
		}
	}

	@Override
	public SyncData getSyncData(int id) {

		switch (id) {
		case SyncType.SPECIAL1:
			return new SyncData(true, freq);
		case SyncType.SPECIAL2:
			return new SyncData(true, x);
		case SyncType.SPECIAL3:
			return new SyncData(true, y);
		case SyncType.SPECIAL4:
			return new SyncData(true, z);
		}
		return new SyncData(false, 0);
	}

	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("freq", freq);
		tag.setInteger("XX", x);
		tag.setInteger("YY", y);
		tag.setInteger("ZZ", z);
		tag.setInteger("DIMENSION", dimension);
		tag.setString("playerName", playerName);
	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.freq = tag.getInteger("freq");
		this.x = tag.getInteger("XX");
		this.y = tag.getInteger("YY");
		this.z = tag.getInteger("ZZ");
		this.dimension = tag.getInteger("DIMENSION");
		this.playerName = tag.getString("playerName");
	}

	public boolean hasEnergyHandler(ForgeDirection from) {
		TileEntity handler = SonarHelper.getAdjacentTileEntity(this, from);
		if (handler instanceof TileEntityFlux) {
			return false;
		}
		return handler instanceof IEnergyHandler ? SonarHelper.isAdjacentEnergyHandlerFromSide(this, from) : SonarHelper.isEnergyHandler(handler);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	public int receiveEnergy(ForgeDirection dir, int maxTransfer, boolean simulate) {
		return 0;
	}

	public int extractEnergy(ForgeDirection dir, int paramInt, boolean paramBoolean) {
		return 0;
	}

	public int getEnergyStored(ForgeDirection dir) {
		return 0;
	}

	public int getMaxEnergyStored(ForgeDirection dir) {
		return 0;
	}

	public void onChunkUnload() {
		this.removeFromFrequency();
	}

	public void onLoaded() {
		if (!this.worldObj.isRemote && SonarAPI.ic2Loaded()) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			this.addToFrequency();
			this.updateAdjacentHandlers();
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (!this.worldObj.isRemote) {
			if (SonarAPI.ic2Loaded()) {
				MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			}
			this.removeFromFrequency();
		}
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
		return true;
	}

	@Override
	public double getDemandedEnergy() {
		return 0;
	}

	@Override
	public int getSinkTier() {
		return 4;
	}

	@Override
	public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
		return 0;
	}

	@Override
	public int xCoord() {
		return xCoord;
	}

	@Override
	public int yCoord() {
		return yCoord;
	}

	@Override
	public int zCoord() {
		return zCoord;
	}

	@Override
	public int freq() {
		return this.freq;
	}

	public int handlerCount() {
		return this.handlerCount;
	}

	@Override
	public int dimension() {
		return this.dimension;
	}

	public String getPlayer() {
		return this.playerName;
	}

	public void setPlayer(EntityPlayer player) {
		if (player == null) {
			return;
		}
		this.playerName = player.getGameProfile().getName();
		this.dimension = player.dimension;
	}

	public static class TransferList {
		public int[] inputList;
		public int energy;

		public TransferList(int[] inputList, int input) {
			this.inputList = inputList;
			this.energy = input;
		}
	}

}
