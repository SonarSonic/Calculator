package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;

public class TileEntityScarecrow extends TileEntity {

	public int growTicks;
	public int size = 3;
	public int speed = 500;

	@Override
	public void updateEntity() {
		super.updateEntity();	
		grow();

	}

	public void grow() {
		if (!this.worldObj.isRemote) {
			if (this.growTicks >= 0 && this.growTicks != speed) {
				growTicks++;
			}
			if (this.growTicks == speed) {
				growCrop();
				this.growTicks = 0;

			}
		}
	}

	public boolean growCrop() {
		int X = (0 + (int) (Math.random() * ((size - 0) + size))) - (size - 1);
		int Z = (0 + (int) (Math.random() * ((size - 0) + size))) - (size - 1);
		return GreenhouseHelper.applyBonemeal(worldObj, xCoord + X, yCoord, zCoord + Z, false);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.growTicks = nbt.getInteger("Grow");

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Grow", this.growTicks);
	}
}
