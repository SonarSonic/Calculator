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
import sonar.calculator.mod.api.flux.IFlux;
import sonar.calculator.mod.api.flux.IFluxController;
import sonar.calculator.mod.client.gui.misc.GuiFlux;
import sonar.calculator.mod.utils.FluxNetwork;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.calculator.mod.utils.helpers.FluxHelper;
import sonar.core.common.tileentity.TileEntitySonar;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.SonarHelper;
import sonar.core.integration.SonarLoader;
import sonar.core.network.sync.ISyncPart;
import sonar.core.network.sync.SyncTagType;
import sonar.core.network.sync.SyncTagType.INT;
import sonar.core.network.sync.SyncTagType.STRING;
import sonar.core.network.utils.ISyncTile;
import cofh.api.energy.IEnergyHandler;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class TileEntityFlux extends TileEntitySonar implements IEnergyHandler, ISyncTile, IEnergySink, IFlux {

	public SyncTagType.INT networkState = (INT) new SyncTagType.INT("networkState").removeSyncType(SyncType.SAVE);
	public SyncTagType.INT playerState = (INT) new SyncTagType.INT("playerState").removeSyncType(SyncType.SAVE);	
	public SyncTagType.INT dimension = (INT) new SyncTagType.INT("DIMENSION").removeSyncType(SyncType.SYNC);
	public SyncTagType.INT networkID = new SyncTagType.INT("networkID");
	public SyncTagType.STRING playerName = (STRING) new SyncTagType.STRING("playerName").removeSyncType(SyncType.SYNC);
	public SyncTagType.STRING networkName = (STRING) new SyncTagType.STRING("networkName").setDefault("NETWORK");
	public String masterName = "";
	/** client only list */
	public List<FluxNetwork> networks;

	public String getMasterName() {
		IFluxController controller = FluxHelper.getController(networkID.getObject());
		if (controller != null) {
			this.networkState.setObject(controller.getProtectionMode());
			if (controller.validPlayer(this.playerName.getObject())) {
				this.playerState.setObject(0);
			} else {
				this.playerState.setObject(1);
			}
			return controller.toString();
		}

		this.playerState.setObject(0);
		this.networkState.setObject(1);
		return " ";

	}

	public void setName(String name) {
		if (!name.isEmpty() && !name.equals("NETWORK")) {
			this.removeFromFrequency();
			int network = FluxRegistry.getNetwork(name, playerName.getObject());
			if (network == 0) {
				this.networkID.setObject(FluxRegistry.createNetwork(name));
			} else {
				this.networkID.setObject(network);
			}

			this.addToFrequency();
			networkName.setObject(name);
		}
	}

	public void rename(String name) {
		if (!name.isEmpty() && !name.equals("NETWORK")) {
			FluxRegistry.renameNetwork(playerName.getObject(), networkName.getObject(), name);
			networkName.setObject(name);
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

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SYNC) {
			this.masterName = nbt.getString("masterName");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SYNC) {
			nbt.setString("masterName", getMasterName());
		}

	}

	public void addSyncParts(List<ISyncPart> parts) {
		super.addSyncParts(parts);
		parts.addAll(Lists.newArrayList(dimension, networkID, playerName, networkName, networkState, playerState));
	}

	public boolean hasEnergyHandler(ForgeDirection from) {
		TileEntity handler = SonarHelper.getAdjacentTileEntity(this, from);
		if (handler instanceof TileEntityFlux) {
			return false;
		}
		return SonarHelper.isAdjacentEnergyHandlerFromSide(this, from);
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
			if (SonarLoader.ic2Loaded()) {
				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			}
			int network = FluxRegistry.getNetwork(networkName.getObject(), playerName.getObject());

			if (network == 0) {
				this.networkID.setObject(FluxRegistry.createNetwork(networkName.getObject()));
			} else {
				this.networkID.setObject(network);
			}
			this.addToFrequency();
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (!this.worldObj.isRemote) {
			if (SonarLoader.ic2Loaded()) {
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
		return this.networkID.getObject();
	}

	@Override
	public int dimension() {
		return this.dimension.getObject();
	}

	public String getPlayer() {
		return this.playerName.getObject();
	}

	public void setPlayer(EntityPlayer player) {
		if (player == null) {
			return;
		}
		this.playerName.setObject(player.getGameProfile().getName());
		this.dimension.setObject(player.dimension);
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
		return this.playerName.getObject();
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		if (networkName.equals("NETWORK")) {
			currenttip.add(FontHelper.translate("network.notConnected"));
		} else {
			currenttip.add(networkName.getObject() + ": " + GuiFlux.getNetworkType(networkState.getObject()));
		}
		return currenttip;
	}
}
