package sonar.calculator.mod.common.tileentity;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.api.flux.IFlux;
import sonar.calculator.mod.api.flux.IFluxController;
import sonar.calculator.mod.client.gui.misc.GuiFlux;
import sonar.calculator.mod.utils.FluxNetwork;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.calculator.mod.utils.helpers.FluxHelper;
import sonar.core.common.tileentity.TileEntityEnergy;
import sonar.core.network.sync.ISyncPart;
import sonar.core.network.sync.SyncTagType;
import sonar.core.network.sync.SyncTagType.INT;
import sonar.core.network.sync.SyncTagType.STRING;
import sonar.core.network.utils.ISyncTile;
import sonar.core.utils.BlockCoords;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.IEnergyHandler;

import com.google.common.collect.Lists;

public abstract class TileEntityFlux extends TileEntityEnergy implements IEnergyHandler, ISyncTile, IFlux {

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

	public boolean hasEnergyHandler(EnumFacing from) {
		TileEntity handler = SonarHelper.getAdjacentTileEntity(this, from);
		if (handler instanceof TileEntityFlux) {
			return false;
		}
		return handler instanceof IEnergyHandler ? SonarHelper.isAdjacentEnergyHandlerFromSide(this, from) : SonarHelper.isEnergyHandler(handler);
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	public int receiveEnergy(EnumFacing dir, int maxTransfer, boolean simulate) {
		return 0;
	}

	public int extractEnergy(EnumFacing dir, int paramInt, boolean paramBoolean) {
		return 0;
	}

	public int getEnergyStored(EnumFacing dir) {
		return 0;
	}

	public int getMaxEnergyStored(EnumFacing dir) {
		return 0;
	}

	public void onChunkUnload() {
		this.removeFromFrequency();
	}

	public void onLoaded() {
		if (!this.worldObj.isRemote) {
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
			this.removeFromFrequency();
		}
	}

	@Override
	public BlockCoords getCoords(){
		return new BlockCoords(this);
	}

	@Override
	public int networkID() {
		return this.networkID.getObject();
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
