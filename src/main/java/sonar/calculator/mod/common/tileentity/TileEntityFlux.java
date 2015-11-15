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
import sonar.calculator.mod.api.IFlux;
import sonar.calculator.mod.client.gui.misc.GuiFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.utils.FluxNetwork;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.calculator.mod.utils.helpers.FluxHelper;
import sonar.core.common.tileentity.TileEntitySonar;
import sonar.core.utils.ISyncTile;
import sonar.core.utils.SonarAPI;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.FontHelper;
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

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE) {
			this.networkID = nbt.getInteger("networkID");
			this.dimension = nbt.getInteger("DIMENSION");
			this.playerName = nbt.getString("playerName");
			this.networkName = nbt.getString("networkName");
		} else if (type == SyncType.SYNC) {
			this.masterName = nbt.getString("masterName");
			this.networkID = nbt.getInteger("networkID");
			this.networkState = nbt.getInteger("networkState");
			this.playerState = nbt.getInteger("playerState");
			this.networkName = nbt.getString("networkName");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE) {
			nbt.setInteger("networkID", networkID);
			nbt.setInteger("DIMENSION", dimension);
			if(playerName==null || playerName.isEmpty()){
				playerName=" ";
			}
			nbt.setString("playerName", playerName);
			nbt.setString("networkName", networkName);
		} else if (type == SyncType.SYNC) {
			nbt.setString("masterName", getMasterName());
			nbt.setInteger("networkID", networkID);
			nbt.setInteger("networkState", networkState);
			nbt.setInteger("playerState", playerState);
			nbt.setString("networkName", networkName);
		}

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
	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		if(networkName.equals("NETWORK")){
			currenttip.add(FontHelper.translate("network.notConnected"));	
		}else{
			currenttip.add(networkName + ": " + GuiFlux.getNetworkType(networkState));
		}
		return currenttip;
	}
}
