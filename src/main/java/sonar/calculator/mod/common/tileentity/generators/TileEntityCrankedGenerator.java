package sonar.calculator.mod.common.tileentity.generators;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.core.common.tileentity.TileEntityEnergy;
import sonar.core.network.sync.SyncEnergyStorage;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.IEnergyReceiver;

public class TileEntityCrankedGenerator extends TileEntityEnergy {

	protected TileEntity[] handlers = new TileEntity[6];
	public boolean cranked;
	public int ticks;
	public int ticksforpower = 2;

	public TileEntityCrankedGenerator() {
		super.storage = new SyncEnergyStorage(1000, 200);
		super.maxTransfer = 32;
	}

	@Override
	public void update() {

		super.update();
		if (cranked()) {
			TileEntityCrankHandle crank = (TileEntityCrankHandle) this.worldObj.getTileEntity(pos.offset(EnumFacing.UP));
			if (crank.angle > 0) {
				if (ticks == 0) {
					this.storage.modifyEnergyStored(2);
				}
				ticks++;
				if (ticks == ticksforpower) {
					ticks = 0;
				}
			}
		}
		int maxTransfer = Math.min(this.maxTransfer, this.storage.getEnergyStored());
		this.storage.extractEnergy(maxTransfer - this.pushEnergy(maxTransfer, false), false);
	}

	public boolean cranked() {
		Block crank = this.worldObj.getBlockState(pos.offset(EnumFacing.UP)).getBlock();
		if (crank != null && crank == Calculator.crankHandle) {
			return true;
		}
		return false;
	}

	public int pushEnergy(int recieve, boolean simulate) {
		for (int i = 0; i < 6; i++) {
			if (this.handlers[i] != null) {
				if (handlers[i] instanceof IEnergyReceiver) {
					recieve -= ((IEnergyReceiver) this.handlers[i]).receiveEnergy(EnumFacing.VALUES[(i ^ 0x1)], recieve, simulate);
				}
			}
		}
		return recieve;
	}

	public void updateAdjacentHandlers() {
		for (int i = 0; i < 6; i++) {
			TileEntity te = SonarHelper.getAdjacentTileEntity(this, EnumFacing.getFront(i));
			if (!(te instanceof TileEntityFlux)) {
				if (SonarHelper.isEnergyHandlerFromSide(te, EnumFacing.VALUES[(i ^ 0x1)])) {
					this.handlers[i] = te;
				} else
					this.handlers[i] = null;
			}
		}
	}

	public void onLoaded() {
		super.onLoaded();
		this.updateAdjacentHandlers();
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.cranked = nbt.getBoolean("cranked");
			this.ticks = nbt.getInteger("ticks");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setBoolean("cranked", cranked());
			nbt.setInteger("ticks", ticks);
		}
	}

	public List<String> getWailaInfo(List<String> tooltip) {
		tooltip.add(FontHelper.translate("crank.cranked") + ": " + (this.cranked ? FontHelper.translate("locator.true") : FontHelper.translate("locator.false")));
		return tooltip;
	}
}