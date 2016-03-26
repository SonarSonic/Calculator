package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.client.gui.machines.GuiAdvancedPowerCube;
import sonar.core.helpers.SonarHelper;
import sonar.core.network.sync.SyncEnergyStorage;
import sonar.core.utils.IGuiTile;

public class TileEntityAdvancedPowerCube extends TileEntityPowerCube implements IGuiTile {

	public int energySide;

	public TileEntityAdvancedPowerCube() {
		super.storage = new SyncEnergyStorage(100000, 64000);
		super.maxTransfer = 100000;
		super.energyMode = EnergyMode.SEND_RECIEVE;
	}

	public void update() {
		super.update();
		this.addEnergy();
		this.markDirty();

	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		if (!(from == EnumFacing.getFront(energySide))) {
			return this.storage.receiveEnergy(maxReceive, simulate);
		}

		return 0;
	}

	public void addEnergy() {
		EnumFacing dir = EnumFacing.getFront(energySide);
		TileEntity entity = SonarHelper.getAdjacentTileEntity(this, dir);
		if (SonarHelper.isEnergyHandlerFromSide(entity, dir.getOpposite())) {

			if (entity != null)
				this.storage.modifyEnergyStored(-SonarHelper.pushEnergy(entity, dir.getOpposite(), this.storage.extractEnergy(maxTransfer, true), false));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.energySide = nbt.getShort("Sides");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("Sides", (short) this.energySide);
	}

	public int side(int par) {
		return this.energySide;
	}

	public void incrementEnergy(int par) {
		if (par == this.energySide) {
			energySide = -1;
		} else {
			energySide = par;
		}
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		if (from == EnumFacing.getFront(energySide)) {
			return this.storage.extractEnergy(maxExtract, simulate);
		}
		return 0;
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiAdvancedPowerCube(player.inventory, this);
	}
}
