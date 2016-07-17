package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.core.common.tileentity.TileEntitySonar;

public class TileEntityWeatherStation extends TileEntitySonar implements ITickable {

	public double angle;
	public boolean loaded;
	public int x, z, ticks;

	public void update() {
		if (!loaded) {
			this.setAngle();
			loaded = true;
		} else {
			if (ticks < 25) {
				ticks++;
			} else if (ticks >= 25) {
				ticks = 0;
				this.setAngle();
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		angle = nbt.getDouble("angle");
		x = nbt.getInteger("xMAST");
		z = nbt.getInteger("zMAST");
		ticks = nbt.getInteger("ticks");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble("angle", angle);
		nbt.setInteger("xMAST", x);
		nbt.setInteger("zMAST", z);
		nbt.setInteger("ticks", ticks);
		return nbt;
	}

	public void setAngle() {
		double distance = 0;
		TileEntityConductorMast mast = null;
		for (int X = -10; X <= 10; X++) {
			for (int Z = -10; Z <= 10; Z++) {
				TileEntity target = this.worldObj.getTileEntity(pos.add(X, 0, Z));
				if (target != null && target instanceof TileEntityConductorMast) {
					TileEntityConductorMast tile = (TileEntityConductorMast) target;
					double mastDist = target.getDistanceSq(pos.getX(), pos.getY(), pos.getZ());
					if (distance == 0 || distance > mastDist || ((mastDist == distance && mast != null) && (mast.lastStations >= tile.lastStations))) {
						distance = mastDist;
						angle = Math.toDegrees(Math.atan2(Z, X)) - 90;
						mast = tile;
					}
				}
			}
		}
		if (mast == null) {
			this.angle = 1000;
		} else {
			x = mast.getPos().getX();
			z = mast.getPos().getZ();
		}
		markBlockForUpdate();

	}

	public boolean maxRender() {
		return true;
	}
}
