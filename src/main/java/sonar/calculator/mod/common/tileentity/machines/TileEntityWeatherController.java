package sonar.calculator.mod.common.tileentity.machines;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.misc.GuiWeatherController;
import sonar.calculator.mod.common.containers.ContainerWeatherController;
import sonar.core.api.IFlexibleGui;
import sonar.core.api.energy.EnergyMode;
import sonar.core.api.machines.IProcessMachine;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.handlers.inventories.handling.EnumFilterType;
import sonar.core.handlers.inventories.handling.filters.SlotHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.network.utils.IByteBufTile;

public class TileEntityWeatherController extends TileEntityEnergyInventory implements IByteBufTile, IProcessMachine, IFlexibleGui {

	public int type, data, buffer, coolDown;

	public static final int TIME = 0;
	public static final int RAIN = 1;
	public static final int THUNDER = 2;

	public TileEntityWeatherController() {
		super.inv.setSize(1);
		super.inv.getInsertFilters().put(SlotHelper.dischargeSlot(0), EnumFilterType.INTERNAL);
		super.storage.setCapacity(CalculatorConfig.WEATHER_CONTROLLER_STORAGE);
		super.storage.setMaxTransfer(CalculatorConfig.WEATHER_CONTROLLER_TRANSFER_RATE);
		super.energyMode = EnergyMode.RECIEVE;
	}

	@Override
	public void update() {
		super.update();
		startProcess();
		this.discharge(0);
		if (buffer > 0) {
			storage.modifyEnergyStored(-(CalculatorConfig.WEATHER_CONTROLLER_USAGE / 100));
			if (buffer != 100) {
				buffer++;
			} else {
				coolDown = 1;
				buffer = 0;
				if (!this.world.isRemote) {
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
		boolean power = this.world.isBlockPowered(pos);
		if (buffer == 0 && coolDown == 0 && storage.getEnergyStored() >= CalculatorConfig.WEATHER_CONTROLLER_USAGE && this.processType(type, true) && (power || this.world.isBlockIndirectlyGettingPowered(pos) > 0)) {
			buffer = 1;
		}
	}

	public boolean processType(int type, boolean simulate) {
		switch (type) {
		case TIME:
			if (data == 0 && this.world.isDaytime()) {
				return false;
			} else if (data > 0 && !this.world.isDaytime()) {
				return false;
			}
			if (!simulate) {
				long i = world.getWorldTime() + 24000L;
				long newTime = i - i % 24000L;
				long oldTime = world.getWorldTime();
				if (data == 0) { // set to daytime
					world.setWorldTime(newTime);
				} else {// set to night time
					world.setWorldTime(newTime - 12000 > oldTime ? newTime - 12000 : oldTime + 12000);
				}
			}
			return true;
		case RAIN:
			if (data == 0 && !this.world.isRaining()) {
				return false;
			} else if (data > 0 && this.world.isRaining()) {
				return false;
			}
			if (!simulate) {
				this.world.getWorldInfo().setRaining(data != 0);
			}
			return true;
		case THUNDER:
			if (data == 0 && !this.world.isThundering()) {
				return false;
			} else if (data > 0 && this.world.isThundering()) {
				return false;
			}
			if (!simulate) {
				this.world.getWorldInfo().setRaining(data != 0);
				this.world.getWorldInfo().setThundering(data != 0);
			}
			return true;
		}
		return false;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.SAVE, SyncType.DEFAULT_SYNC)) {
			this.type = nbt.getInteger("type");
			this.data = nbt.getInteger("data");
			this.buffer = nbt.getInteger("buffer");
		}
	}

	@Override
	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.SAVE, SyncType.DEFAULT_SYNC)) {
			nbt.setInteger("type", this.type);
			nbt.setInteger("data", data);
			nbt.setInteger("buffer", buffer);
		}
		return nbt;
	}

	@Override
	public void writePacket(ByteBuf buf, int id) {}

	@Override
	public void readPacket(ByteBuf buf, int id) {
		if (id == 0) {
			if (data == 1) {
				data = 0;
			} else {
				data = 1;
			}
		} else {
			setType(id - 1);
		}
	}

	@Override
	public int getCurrentProcessTime() {
		return buffer;
	}

	@Override
	public int getProcessTime() {
		return 100;
	}

	@Override
	public double getEnergyUsage() {
		return CalculatorConfig.WEATHER_CONTROLLER_USAGE / getProcessTime();
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerWeatherController(player.inventory, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiWeatherController(player.inventory, this);
	}

	@Override
	public int getBaseProcessTime() {
		return getProcessTime();
	}
}
