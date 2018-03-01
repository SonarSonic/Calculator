package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import sonar.calculator.mod.common.block.misc.RainSensor;

public class TileEntityRainSensor extends TileEntity implements ITickable {

    @Override
	public void update() {
		if (this.getWorld() != null && !this.getWorld().isRemote) {
			IBlockState state = getWorld().getBlockState(pos);	
			boolean meta = state.getValue(RainSensor.bool);
			boolean current = this.getWorld().isRaining();
			if(meta!=current){
				this.getWorld().setBlockState(pos, state.withProperty(RainSensor.bool, current), 2);
				this.getWorld().notifyNeighborsOfStateChange(pos, blockType);
			}
		}
	}
}