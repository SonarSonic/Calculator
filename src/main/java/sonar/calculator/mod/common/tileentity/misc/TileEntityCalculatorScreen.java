package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.network.packets.PacketCalculatorScreen;
import sonar.core.common.tileentity.TileEntitySonar;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;

public class TileEntityCalculatorScreen extends TileEntitySonar {

	public int latestMax, latestEnergy;
	public int lastMax, lastEnergy;

	public void update() {

		if (this.worldObj != null && !this.worldObj.isRemote) {
			EnumFacing front = EnumFacing.getFront(this.getBlockMetadata()).getOpposite();
			TileEntity target = SonarHelper.getAdjacentTileEntity(this, front);
			if (target == null) {
				return;
			} else {
				if (target instanceof IEnergyStorage) {
					IEnergyStorage energy = (IEnergyStorage) target;
					int max = energy.getMaxEnergyStored();
					int current = energy.getEnergyStored();

					if (max != this.lastMax) {
						this.sendMax(max);
					}
					if (current != this.lastEnergy) {
						this.sendEnergy(current);
						this.worldObj.markBlockForUpdate(pos);
					}
				} else if (target instanceof IEnergyReceiver) {
					IEnergyReceiver energy = (IEnergyReceiver) target;
					int max = energy.getMaxEnergyStored(front);
					int current = energy.getEnergyStored(front);

					if (max != this.lastMax) {
						this.sendMax(max);
					}
					if (current != this.lastEnergy) {
						this.sendEnergy(current);
						this.worldObj.markBlockForUpdate(pos);
					}
				} else if (target instanceof IEnergyHandler) {
					IEnergyHandler energy = (IEnergyHandler) target;
					int max = energy.getMaxEnergyStored(front);
					int current = energy.getEnergyStored(front);

					if (max != this.lastMax) {
						this.sendMax(max);
					}
					if (current != this.lastEnergy) {
						this.sendEnergy(current);
						this.worldObj.markBlockForUpdate(pos);
					}
				}
			}

		}
	}

	public void sendMax(int max) {
		markDirty();
		this.lastMax = this.latestMax;
		this.latestMax = max;
		if (!this.worldObj.isRemote)
			Calculator.network.sendToAllAround(new PacketCalculatorScreen(pos, 0, max), new TargetPoint(this.worldObj.provider.getDimensionId(), pos.getX(), pos.getY(), pos.getZ(), 64));

	}

	public void sendEnergy(int energy) {
		markDirty();
		this.lastEnergy = this.latestEnergy;
		this.latestEnergy = energy;
		if (!this.worldObj.isRemote)
			Calculator.network.sendToAllAround(new PacketCalculatorScreen(pos, 1, energy), new TargetPoint(this.worldObj.provider.getDimensionId(), pos.getX(), pos.getY(), pos.getZ(), 64));
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.latestMax = nbt.getInteger("latestMax");
			this.latestEnergy = nbt.getInteger("latestEnergy");
			this.lastMax = nbt.getInteger("lastMax");
			this.lastEnergy = nbt.getInteger("lastEnergy");
		}

	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setInteger("latestMax", this.latestMax);
			nbt.setInteger("latestEnergy", this.latestEnergy);
			nbt.setInteger("lastMax", this.lastMax);
			nbt.setInteger("lastEnergy", this.lastEnergy);
		}
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
