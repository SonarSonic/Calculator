package sonar.calculator.mod.common.tileentity;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.api.FluxNetwork;
import sonar.calculator.mod.api.IFlux;
import sonar.calculator.mod.api.ISyncTile;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.calculator.mod.utils.helpers.FluxHelper;
import sonar.core.common.tileentity.TileEntitySonar;
import sonar.core.utils.SonarAPI;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class TileEntityFlux extends TileEntitySonar implements IEnergyHandler, ISyncTile, IEnergySink, IFlux {

	public int networkState, playerState;
	private int dimension;
	public int networkID;
	public String playerName, masterName, networkName = "NETWORK";

	/** client only list */
	public List<FluxNetwork> networks;

	public String getMasterName() {
		TileEntityFluxController controller = FluxHelper.getController(networkID);
		if (controller != null) {
			this.networkState = controller.playerProtect;
			if (controller.validPlayer(this.playerName)) {
				this.playerState = 0;
			} else {
				this.playerState = 1;
			}
			return controller.playerName;
		}

		this.playerState = 0;
		this.networkState = 1;
		return " ";

	}

	public void setName(String name) {
		if (!name.isEmpty() && !name.equals("NETWORK")) {
			this.removeFromFrequency();
			int network = FluxRegistry.getNetwork(name, playerName);
			if (network == 0) {
				this.networkID = FluxRegistry.createNetwork(name);
			} else {
				this.networkID = network;
			}

			this.addToFrequency();
			networkName = name;
		}
	}
	public void rename(String name) {
		if (!name.isEmpty() && !name.equals("NETWORK")) {
			FluxRegistry.renameNetwork(playerName, networkName, name);
			networkName = name;
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
	public void onSync(Object data, int id) {
		switch (id) {
		case SyncType.SPECIAL1:
			this.networkName = (String) data;
			break;
		case SyncType.SPECIAL2:
			this.networkID = (Integer) data;
			break;
		case SyncType.PLAYER:
			this.masterName = (String) data;
			break;
		case SyncType.STATE:
			this.networkState = (Integer) data;
			break;
		case SyncType.PLAYERSTATE:
			this.playerState = (Integer) data;
			break;
		}
	}

	@Override
	public SyncData getSyncData(int id) {

		switch (id) {
		case SyncType.SPECIAL1:
			return new SyncData(true, networkName);
		case SyncType.SPECIAL2:
			return new SyncData(true, networkID);
		case SyncType.PLAYER:
			return new SyncData(true, this.getMasterName());
		case SyncType.STATE:
			return new SyncData(true, this.networkState);
		case SyncType.PLAYERSTATE:
			return new SyncData(true, this.playerState);
		}
		return new SyncData(false, 0);
	}

	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("networkID", networkID);
		tag.setInteger("DIMENSION", dimension);
		tag.setString("playerName", playerName);
		tag.setString("networkName", networkName);

	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.networkID = tag.getInteger("networkID");
		this.dimension = tag.getInteger("DIMENSION");
		this.playerName = tag.getString("playerName");
		this.networkName = tag.getString("networkName");
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
		if (!this.worldObj.isRemote) {
			if (SonarAPI.ic2Loaded()) {
				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			}
			int network = FluxRegistry.getNetwork(networkName, playerName);
			if (network == 0) {
				this.networkID = FluxRegistry.createNetwork(networkName);
			} else {
				this.networkID = network;
			}
			this.addToFrequency();
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
	public int networkID() {
		return this.networkID;
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

	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public String masterName() {
		return this.playerName;
	}

}
