package sonar.calculator.mod.common.tileentity.machines;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.api.ISyncTile;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.common.tileentity.TileEntityInventoryReceiver;
import sonar.core.utils.ChargingUtils;
import sonar.core.utils.EnergyCharge;
import sonar.core.utils.IDropTile;
import sonar.core.utils.SonarAPI;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.Optional.Method;

public class TileEntityWeatherController extends TileEntityInventoryReceiver implements IEnergyHandler, IDropTile, IEnergySink, ISyncTile {

	public int type, data, buffer, coolDown;

	public static final int TIME = 0;
	public static final int RAIN = 1;
	public static final int THUNDER = 2;

	public int requiredPower = 10000;

	public TileEntityWeatherController() {
		super.slots = new ItemStack[1];
		super.storage = new EnergyStorage(100000, 10000);
	}

	public void updateEntity() {
		super.updateEntity();
		this.discharge(0);
		if (buffer > 0) {
			storage.modifyEnergyStored(-(requiredPower / 100));
			if (buffer != 100) {
				buffer++;
			} else {
					coolDown = 1;
					buffer = 0;
					if (!this.worldObj.isRemote) {
						processType(type, false);
					}
			}

		}
		if (coolDown > 0) {
			if (coolDown != 30) {
				coolDown++;
			} else {
				coolDown = 0;
			}
		}
	}

	public void startProcess() {
		int power = this.worldObj.getBlockPowerInput(xCoord, yCoord, zCoord);
		if (buffer == 0 && coolDown == 0 && storage.getEnergyStored() >= requiredPower && this.processType(type, true)
				&& (power != 0 || this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))) {
			buffer = 1;
		}
	}

	public boolean processType(int type, boolean simulate) {
		switch (type) {
		case TIME:
			if (data == 0 && this.worldObj.isDaytime()) {
				return false;
			} else if (data > 0 && !this.worldObj.isDaytime()) {
				return false;
			}
			if (!simulate) {
				this.worldObj.setWorldTime(data == 0 ? 1000 : 13000);
			}
			return true;
		case RAIN:
			if (data == 0 && !this.worldObj.isRaining()) {
				return false;
			} else if (data > 0 && this.worldObj.isRaining()) {
				return false;
			}
			if (!simulate) {
				this.worldObj.getWorldInfo().setRaining(data == 0 ? false : true);
			}
			return true;
		case THUNDER:
			if (data == 0 && !this.worldObj.isThundering()) {
				return false;
			} else if (data > 0 && this.worldObj.isThundering()) {
				return false;
			}
			if (!simulate) {
				this.worldObj.getWorldInfo().setRaining(data == 0 ? false : true);
				this.worldObj.getWorldInfo().setThundering(data == 0 ? false : true);
			}
			return true;
		}
		return false;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.type = nbt.getInteger("type");
		this.data = nbt.getInteger("data");
		this.buffer = nbt.getInteger("buffer");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("type", type);
		nbt.setInteger("data", data);
		nbt.setInteger("buffer", buffer);
	}

	@Override
	public void onSync(Object data, int id) {
		super.onSync(data, id);
		switch (id) {
		case SyncType.STATE:
			this.type = (Integer) data;
			break;
		case SyncType.ACTIVE:
			this.data = (Integer) data;
			break;
		case SyncType.SPECIAL1:
			this.buffer = (Integer) data;
			break;
		case SyncType.SPECIAL2:
			this.coolDown = (Integer) data;
			break;
		}
	}

	@Override
	public SyncData getSyncData(int id) {
		switch (id) {
		case SyncType.STATE:
			return new SyncData(true, type);
		case SyncType.ACTIVE:
			return new SyncData(true, data);
		case SyncType.SPECIAL1:
			return new SyncData(true, buffer);
		case SyncType.SPECIAL2:
			return new SyncData(true, coolDown);
		}
		return super.getSyncData(id);
	}
}
