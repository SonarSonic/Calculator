package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import sonar.calculator.mod.common.block.misc.RainSensor;

public class TileEntityRainSensor extends TileEntity implements ITickable {

	public void update() {
		if (this.worldObj != null && !this.worldObj.isRemote) {
			IBlockState state = getWorld().getBlockState(pos);	
			boolean meta = state.getValue(RainSensor.bool);
			boolean current = this.worldObj.isRaining();
			if(meta!=current){
				this.worldObj.setBlockState(pos, state.withProperty(RainSensor.bool, current), 3);
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