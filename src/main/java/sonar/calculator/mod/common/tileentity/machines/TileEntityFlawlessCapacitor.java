package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import sonar.core.common.tileentity.TileEntityEnergy;
import sonar.core.network.sync.SyncEnergyStorage;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.SonarHelper;

/** WIP */
public class TileEntityFlawlessCapacitor extends TileEntityEnergy {

	public int[] output = new int[6];

	public TileEntityFlawlessCapacitor() {
		super.storage = new SyncEnergyStorage(2000000000);
		super.energyMode = EnergyMode.SEND_RECIEVE;
	}

	public void update() {
		handleEnergy();
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.output = nbt.getIntArray("outputs");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setIntArray("outputs", output);

		}
	}

	public void handleEnergy() {
		for (int i = 0; i < output.length; i++) {
			if (output[i] == 1) {
				EnumFacing dir = EnumFacing.getFront(i);
				TileEntity tile = SonarHelper.getAdjacentTileEntity(this, dir);
				if (SonarHelper.isEnergyHandlerFromSide(tile, dir)) {
					int removed = SonarHelper.pushEnergy(tile, dir, SonarHelper.pushEnergy(tile, dir, this.storage.getEnergyStored(), true), false);
					this.storage.extractEnergy(removed, false);
				}
			}
		}

	}

	public void incrementSide(int i) {
		if (!this.worldObj.isRemote) {
			if (i < output.length) {
				if (output[i] == 0) {
					output[i] = 1;
				} else {
					output[i] = 0;
				}
			}
			this.worldObj.markBlockForUpdate(pos);
		}
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		if (output.length == 0 || output == null) {
			output = new int[6];
		}
		if (output[from.ordinal()] == 1) {
			return 0;
		}
		return this.storage.receiveEnergy(maxReceive, simulate);
	}

	public int[] getOutputSides() {
		return output;
	}

}
