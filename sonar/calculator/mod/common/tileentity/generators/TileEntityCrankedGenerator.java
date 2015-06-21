package sonar.calculator.mod.common.tileentity.generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.core.common.tileentity.TileEntitySender;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.EnergyStorage;

public class TileEntityCrankedGenerator extends TileEntitySender {

	public int maxTransfer = 16;
	public boolean cranked;
	public int ticks;
	public int ticksforpower = 2;
	public String direction;

	public TileEntityCrankedGenerator() {
		super.storage = new EnergyStorage(1000, 1000);
		super.maxTransfer = 32;
	}

	@Override
	public void updateEntity() {

		super.updateEntity();
		if (canAddEnergy()) {
			addEnergy();
		}
		if (cranked()) {
			TileEntityCrankHandle crank = (TileEntityCrankHandle) this.worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
			if (crank.angle > 0) {
				if (ticks == 0) {
					this.storage.modifyEnergyStored(1);
				}
				ticks++;
				if (ticks == ticksforpower) {
					ticks = 0;
				}
			}
		}
	}

	public boolean cranked() {
		Block crank = this.worldObj.getBlock(xCoord, yCoord + 1, zCoord);
		if (crank != null && crank == Calculator.crank) {
			return true;
		}
		return false;
	}

	private void addEnergy() {

		TileEntity down = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		TileEntity north = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
		TileEntity south = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
		TileEntity east = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
		TileEntity west = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
		if (direction == "down") {
			if (SonarHelper.isEnergyHandlerFromSide(down, ForgeDirection.DOWN)) {
				this.storage.modifyEnergyStored(-SonarHelper.pushEnergy(down, ForgeDirection.UP, this.storage.extractEnergy(maxTransfer, true), false));

			}
		} else if (direction == "west") {
			if (SonarHelper.isEnergyHandlerFromSide(west, ForgeDirection.WEST)) {
				this.storage.modifyEnergyStored(-SonarHelper.pushEnergy(west, ForgeDirection.EAST, this.storage.extractEnergy(maxTransfer, true), false));
			}
		} else if (direction == "east") {
			if (SonarHelper.isEnergyHandlerFromSide(east, ForgeDirection.EAST)) {
				this.storage.modifyEnergyStored(-SonarHelper.pushEnergy(east, ForgeDirection.WEST, this.storage.extractEnergy(maxTransfer, true), false));
			}
		} else if (direction == "north") {
			if (SonarHelper.isEnergyHandlerFromSide(north, ForgeDirection.NORTH)) {
				this.storage.modifyEnergyStored(-SonarHelper.pushEnergy(north, ForgeDirection.SOUTH, this.storage.extractEnergy(maxTransfer, true), false));
			}
		} else if (direction == "south") {
			if (SonarHelper.isEnergyHandlerFromSide(south, ForgeDirection.SOUTH)) {
				this.storage.modifyEnergyStored(-SonarHelper.pushEnergy(south, ForgeDirection.NORTH, this.storage.extractEnergy(maxTransfer, true), false));
			}
		}

	}

	private boolean canAddEnergy() {
		if (storage.getEnergyStored() == 0) {
			return false;
		}
		if (direction == "none") {
			return false;
		}
		return true;
	}

	public void updateHandlers() {
		String d = getHandlers().toString();
		if (d == null) {
			direction = "none";
		} else
			direction = d;
	}

	public String getHandlers() {
		TileEntity down = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		TileEntity north = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
		TileEntity south = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
		TileEntity east = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
		TileEntity west = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);

		if (SonarHelper.isEnergyHandlerFromSide(down, ForgeDirection.DOWN)) {
			return "down";
		}

		else if (SonarHelper.isEnergyHandlerFromSide(west, ForgeDirection.WEST)) {
			return "west";
		}

		else if (SonarHelper.isEnergyHandlerFromSide(east, ForgeDirection.EAST)) {
			return "east";
		}

		else if (SonarHelper.isEnergyHandlerFromSide(north, ForgeDirection.NORTH)) {
			return "north";
		}

		else if (SonarHelper.isEnergyHandlerFromSide(south, ForgeDirection.SOUTH)) {
			return "south";
		}
		return "none";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		super.readFromNBT(nbt);
		direction = nbt.getString("facing");

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);
		if (direction == null) {
			nbt.setString("facing", "none");
		} else
			nbt.setString("facing", direction);
	}

	@Override
	public void onSync(Object data, int id) {
		super.onSync(data, id);
		switch (id) {
		case SyncType.ACTIVE:
			this.cranked = (Boolean)data;
			break;
		}
	}

	@Override
	public SyncData getSyncData(int id) {
		switch (id) {
		case SyncType.ACTIVE:
			return new SyncData(true, cranked);
		}
		return super.getSyncData(id);
	}
}