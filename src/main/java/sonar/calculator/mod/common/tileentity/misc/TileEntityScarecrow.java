package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;
import sonar.core.SonarCore;

import javax.annotation.Nonnull;

public class TileEntityScarecrow extends TileEntity implements ITickable {

	public int growTicks;

	@Override
	public void update() {
		if (this.world.isRemote) {
			return;
		}
		grow();
	}

	public void grow() {
		if (this.growTicks >= 0 && this.growTicks != CalculatorConfig.SCARECROW_TICK_RATE) {
			growTicks++;
		}
		if (this.growTicks == CalculatorConfig.SCARECROW_TICK_RATE) {
			growCrop();
			this.growTicks = 0;
		}
	}

	public boolean growCrop() {
		int X = SonarCore.randInt(0, CalculatorConfig.SCARECROW_RANGE*2) - CalculatorConfig.SCARECROW_RANGE;
		int Z = SonarCore.randInt(0, CalculatorConfig.SCARECROW_RANGE*2) - CalculatorConfig.SCARECROW_RANGE;
		return GreenhouseHelper.applyBonemeal(world, pos.add(X, 0, Z), false);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.growTicks = nbt.getInteger("Grow");
	}

	@Nonnull
    @Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Grow", this.growTicks);
		return nbt;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Nonnull
    @Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

    @Override
	public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
}
