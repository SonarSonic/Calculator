package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;

public class TileEntityScarecrow extends TileEntity implements ITickable {

	public int growTicks;
	public int range = CalculatorConfig.getInteger("Scarecrow Range");
	public int speed = CalculatorConfig.getInteger("Scarecrow Tick Rate");

	@Override
	public void update() {
		if (this.worldObj.isRemote) {
			return;
		}
		grow();

	}

	public void grow() {
		if (this.growTicks >= 0 && this.growTicks != speed) {
			growTicks++;
		}
		if (this.growTicks == speed) {
			growCrop();
			this.growTicks = 0;

		}

	}

	public boolean growCrop() {
		int X = (0 + (int) (Math.random() * ((range - 0) + range))) - (range - 1);
		int Z = (0 + (int) (Math.random() * ((range - 0) + range))) - (range - 1);
		return GreenhouseHelper.applyBonemeal(worldObj, pos.add(X, 0, Z), false);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.growTicks = nbt.getInteger("Grow");

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Grow", this.growTicks);
		return nbt;
	}
	
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
}
