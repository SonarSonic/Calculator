package sonar.calculator.mod.common.tileentity.generators;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCrankHandle extends TileEntity {
	public int angle;
	public boolean cranked;

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (this.cranked) {
			this.worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
			if (this.angle == 36) {
				this.angle = 0;
				this.cranked = false;
			}
			active();
		}
		this.markDirty();
	}

	public void active() {

		if (this.cranked) {
			this.angle += 1;
		}
		this.markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		angle = nbt.getInteger("angle");
		cranked = nbt.getBoolean("cranked");

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("angle", this.angle);
		nbt.setBoolean("cranked", this.cranked);
	}

	@Override
	public Packet getDescriptionPacket() {
		super.getDescriptionPacket();
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}
}
