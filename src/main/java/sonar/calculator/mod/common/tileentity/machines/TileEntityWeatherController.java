package sonar.calculator.mod.common.tileentity.machines;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.misc.GuiWeatherController;
import sonar.calculator.mod.common.containers.ContainerWeatherController;
import sonar.core.api.energy.EnergyMode;
import sonar.core.api.machines.IProcessMachine;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.inventory.SonarInventory;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.utils.IGuiTile;

public class TileEntityWeatherController extends TileEntityEnergyInventory implements IByteBufTile,IProcessMachine, IGuiTile {

	public int type, data, buffer, coolDown;

	public static final int TIME = 0;
	public static final int RAIN = 1;
	public static final int THUNDER = 2;

	public int requiredPower = CalculatorConfig.getInteger("Weather Controller");

	public TileEntityWeatherController() {
		super.inv = new SonarInventory(this, 1);
		super.storage.setCapacity(1000000).setMaxTransfer(64000);
		super.energyMode = EnergyMode.RECIEVE;
		syncList.addPart(inv);
	}

	public void update() {
		super.update();
		this.discharge(0);
		if (buffer > 0) {
			storage.modifyEnergyStored(-(requiredPower / 100));
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
		if (buffer == 0 && coolDown == 0 && storage.getEnergyStored() >= requiredPower && this.processType(type, true) && (power || this.world.isBlockIndirectlyGettingPowered(pos)>0)) {
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
				this.world.setWorldTime(data == 0 ? 1000 : 13000);
			}
			return true;
		case RAIN:
			if (data == 0 && !this.world.isRaining()) {
				return false;
			} else if (data > 0 && this.world.isRaining()) {
				return false;
			}
			if (!simulate) {
				this.world.getWorldInfo().setRaining(data == 0 ? false : true);
			}
			return true;
		case THUNDER:
			if (data == 0 && !this.world.isThundering()) {
				return false;
			} else if (data > 0 && this.world.isThundering()) {
				return false;
			}
			if (!simulate) {
				this.world.getWorldInfo().setRaining(data == 0 ? false : true);
				this.world.getWorldInfo().setThundering(data == 0 ? false : true);
			}
			return true;
		}
		return false;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if(type.isType(SyncType.SAVE,SyncType.DEFAULT_SYNC)){
			this.type = nbt.getInteger("type");
			this.data = nbt.getInteger("data");
			this.buffer = nbt.getInteger("buffer");
		}
	}

	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if(type.isType(SyncType.SAVE,SyncType.DEFAULT_SYNC)){
			nbt.setInteger("type", this.type);
			nbt.setInteger("data", data);
			nbt.setInteger("buffer", buffer);
		}
		return nbt;
	}

	@Override
	public void writePacket(ByteBuf buf, int id) {
	}

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
		return requiredPower/getProcessTime();
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerWeatherController(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiWeatherController(player.inventory, this);
	}

	@Override
	public int getBaseProcessTime() {
		return getProcessTime();
	}
}
