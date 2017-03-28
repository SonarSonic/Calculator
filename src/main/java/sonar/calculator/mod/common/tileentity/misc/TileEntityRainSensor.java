package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import sonar.calculator.mod.common.block.misc.RainSensor;

public class TileEntityRainSensor extends TileEntity implements ITickable {

	public void update() {
		if (this.world != null && !this.world.isRemote) {
			IBlockState state = getWorld().getBlockState(pos);	
			boolean meta = state.getValue(RainSensor.bool);
			boolean current = this.world.isRaining();
			if(meta!=current){
				this.world.setBlockState(pos, state.withProperty(RainSensor.bool, current), 2);
				this.world.notifyNeighborsOfStateChange(pos, blockType, true);
			}
		}
	}

}