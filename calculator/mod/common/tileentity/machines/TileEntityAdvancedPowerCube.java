package sonar.calculator.mod.common.tileentity.machines;

import ic2.api.energy.tile.IEnergySource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Method;

@Optional.InterfaceList(value = { @Optional.Interface(iface = "ic2.api.energy.tile.IEnergySource", modid = "IC2", striprefs = true), @Optional.Interface(iface = "ic2.api.energy.tile.IEnergyEmitter", modid = "IC2", striprefs = true),
		@Optional.Interface(iface = "ic2.api.energy.tile.IEnergyTile", modid = "IC2", striprefs = true) })
public class TileEntityAdvancedPowerCube extends TileEntityPowerCube implements IEnergySource {

	public int energySide;

	public TileEntityAdvancedPowerCube() {
		super.storage = new EnergyStorage(100000, 100000);
		super.maxTransfer = 100000;
	}

	public void updateEntity() {
		super.updateEntity();
		this.addEnergy();
		this.markDirty();
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {

		if (!(from == ForgeDirection.getOrientation(energySide))) {
			return this.storage.receiveEnergy(maxReceive, simulate);
		}

		return 0;
	}

	public void addEnergy() {
		ForgeDirection dir = ForgeDirection.getOrientation(energySide);
		TileEntity entity = SonarHelper.getAdjacentTileEntity(this, dir);
		if (SonarHelper.isEnergyHandlerFromSide(entity, dir)) {
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

	@Method(modid = "IC2")
	@Override
	public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
		if (direction == ForgeDirection.getOrientation(energySide)) {
			return true;
		}
		return false;
	}

	@Method(modid = "IC2")
	@Override
	public double getOfferedEnergy() {
		return this.storage.extractEnergy(maxTransfer, true) / 4;
	}

	@Method(modid = "IC2")
	@Override
	public void drawEnergy(double amount) {
		this.storage.extractEnergy((int) (amount * 4), false);

	}

	@Method(modid = "IC2")
	@Override
	public int getSourceTier() {
		return 4;
	}

	@Method(modid = "IC2")
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
		if (!(direction == ForgeDirection.getOrientation(energySide))) {
			return true;
		}
		return false;
	}

	@Method(modid = "IC2")
	@Override
	public double getDemandedEnergy() {
		return this.storage.receiveEnergy(this.storage.getMaxReceive(), true) / 4;
	}

	@Method(modid = "IC2")
	@Override
	public int getSinkTier() {
		return 4;
	}

	@Method(modid = "IC2")
	@Override
	public double injectEnergy(ForgeDirection directionFrom, double amountEU, double voltage) {

		if (!(directionFrom == ForgeDirection.getOrientation(energySide))) {
			int addRF = this.storage.receiveEnergy((int) amountEU * 4, true);
			this.storage.receiveEnergy((int) addRF, false);
			return amountEU - (addRF / 4);
		} else {
			return 0;
		}
	}
}
