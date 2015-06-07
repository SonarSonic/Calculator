package sonar.calculator.mod.common.tileentity.generators;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IStability;
import sonar.calculator.mod.api.ISyncTile;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.core.common.tileentity.TileEntityInventory;

public class TileEntityCalculatorPlug extends TileEntityInventory implements ISyncTile {

	private String localizedName;
	public byte stable;

	public TileEntityCalculatorPlug() {
		super.slots = new ItemStack[1];
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (testStable()) {
			fill(0);
		}

		this.markDirty();

	}

	public boolean testStable() {
		if (this.slots[0] == null) {
			this.stable = 0;
			return false;
		}
		if (this.slots[0].getItem() instanceof IStability) {
			return true;
		}
		return false;
	}

	public void fill(int slot) {
		IStability item = (IStability) slots[slot].getItem();
		boolean stability = item.getStability(slots[slot]);
		if (stability) {
			if (this.stable != 2) {
				this.stable = 2;
				this.worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
			}
		}

		else if (!stability && slots[slot].getItem() instanceof IStability) {
			stable = 1;
		} else {
			stable = 0;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		super.readFromNBT(nbt);
		this.stable = nbt.getByte("Stable");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);
		nbt.setByte("Stable", this.stable);

		NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.slots.length; i++) {
			if (this.slots[i] != null) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte) i);
				this.slots[i].writeToNBT(compound);
				list.appendTag(compound);
			}
		}

		nbt.setTag("Items", list);

	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	public byte getS() {
		if (stable == 2) {
			return 1;
		}
		return 0;
	}

	@Override
	public void onSync(int data, int id) {
		switch (id) {
		case SyncType.SPECIAL1:
			this.stable = (byte) data;
			break;
		}
	}

	@Override
	public SyncData getSyncData(int id) {
		switch (id) {
		case SyncType.SPECIAL1:
			return new SyncData(true, stable);
		}
		return new SyncData(false, 0);
	}
}
