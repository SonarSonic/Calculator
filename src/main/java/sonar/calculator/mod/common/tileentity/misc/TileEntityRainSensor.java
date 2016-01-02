package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.tileentity.TileEntity;

public class TileEntityRainSensor extends TileEntity {

	public void updateEntity() {
		if (this.worldObj != null && !this.worldObj.isRemote) {
			int meta = this.blockMetadata;
			int newMeta = this.getMeta();
			if (newMeta != meta) {
				this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, newMeta, 3);
			}
		}
	}

	public int getMeta() {
		if (this.worldObj.isRaining()) {
			return 15;
		} else {
			return 1;
		}
	}

}