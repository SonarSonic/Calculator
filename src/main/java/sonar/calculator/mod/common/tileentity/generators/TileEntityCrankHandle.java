package sonar.calculator.mod.common.tileentity.generators;

import net.minecraft.nbt.NBTTagCompound;
import sonar.core.common.tileentity.TileEntitySonar;

public class TileEntityCrankHandle extends TileEntitySonar {
	public int angle;
	public boolean cranked;

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (this.cranked) {
			
			if (this.angle == 36) {
				this.angle = 0;
				this.cranked = false;
			}
			active();
		}
	}

	public void active() {

		if (this.cranked) {
			this.angle += 2;
		}
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

}
