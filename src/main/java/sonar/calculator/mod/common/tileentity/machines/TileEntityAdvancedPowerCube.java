package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.client.gui.machines.GuiPowerCube;
import sonar.core.api.SonarAPI;
import sonar.core.helpers.SonarHelper;
import sonar.core.network.sync.SyncEnergyStorage;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.IMachineSides;
import sonar.core.utils.MachineSideConfig;
import sonar.core.utils.MachineSides;

public class TileEntityAdvancedPowerCube extends TileEntityPowerCube implements IGuiTile, IMachineSides {

	public MachineSides sides = new MachineSides(MachineSideConfig.INPUT, this, MachineSideConfig.NONE);

	public TileEntityAdvancedPowerCube() {
		super.storage = new SyncEnergyStorage(100000, 64000);
		super.maxTransfer = 100000;
		super.energyMode = EnergyMode.SEND_RECIEVE;
	}

	public void update() {
		super.update();
		if (this.isClient()) {
			return;
		}
		this.addEnergy();
		this.markDirty();
	}

	public void addEnergy() {
		ArrayList<EnumFacing> facing = sides.getSidesWithConfig(MachineSideConfig.OUTPUT);
		if (facing.isEmpty()) {
			return;
		}
		int transfer = maxTransfer / facing.size();
		for (EnumFacing dir : facing) {
			TileEntity entity = SonarHelper.getAdjacentTileEntity(this, dir);
			SonarAPI.getEnergyHelper().transferEnergy(this, entity, dir.getOpposite(), dir, transfer);
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		sides.readFromNBT(nbt);
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		sides.writeToNBT(nbt);
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		if (sides.getSideConfig(from).isInput()) {
			return storage.receiveEnergy(maxReceive, simulate);
		}
		return 0;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		if (sides.getSideConfig(from).isOutput()) {
			return storage.extractEnergy(maxExtract, simulate);
		}
		return 0;
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiPowerCube(player.inventory, this);
	}

	@Override
	public MachineSides getSideConfigs() {
		return sides;
	}
}
