package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityRainSensor extends TileEntity implements ITickable {

	public void update() {
		if (this.worldObj != null && !this.worldObj.isRemote) {
			int meta = this.getBlockMetadata();
			int newMeta = this.getMeta();
			if (newMeta != meta) {
				this.worldObj.setBlockState(pos, this.getBlockType().getStateFromMeta(newMeta), 3);
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