package sonar.calculator.mod.common.tileentity.machines;
/*
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import sonar.core.api.SonarAPI;
import sonar.core.api.energy.EnergyMode;
import sonar.core.common.tileentity.TileEntityEnergy;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.SonarHelper;

 W.I.P
public class TileEntityFlawlessCapacitor extends TileEntityEnergy {

	public int[] output = new int[6];

	public TileEntityFlawlessCapacitor() {
		super.storage.setCapacity(2000000000).setMaxTransfer(128000);
		super.energyMode = EnergyMode.SEND_RECIEVE;
	}

	public void update() {
		handleEnergy();
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			this.output = nbt.getIntArray("outputs");
		}
	}

	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			nbt.setIntArray("outputs", output);
		}
		return nbt;
	}

	public void handleEnergy() {
		for (int i = 0; i < output.length; i++) {
			if (output[i] == 1) {
				EnumFacing dir = EnumFacing.getFront(i);
				TileEntity tile = SonarHelper.getAdjacentTileEntity(this, dir);
				TileEntity entity = SonarHelper.getAdjacentTileEntity(this, dir);
				SonarAPI.getEnergyHelper().transferEnergy(this, entity, dir.getOpposite(), dir, Integer.MAX_VALUE);
			}
		}

	}

	public void incrementSide(int i) {
		if (!this.getWorld().isRemote) {
			if (i < output.length) {
				if (output[i] == 0) {
					output[i] = 1;
				} else {
					output[i] = 0;
				}
			}
			markBlockForUpdate();
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
*/