package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.api.ISyncTile;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.common.block.misc.GasLantern;
import sonar.core.common.tileentity.TileEntityInventory;

public class TileEntityGasLantern extends TileEntityInventory implements ISyncTile {

	public int burnTime;
	public int maxBurnTime;

	public TileEntityGasLantern() {
		super.slots = new ItemStack[1];
	}

	@Override
	public void updateEntity() {
		boolean flag1 = this.burnTime > 0;
		boolean flag2 = false;
		if (this.burnTime > 0) {
			this.burnTime++;
		}
		if (!this.worldObj.isRemote) {
			if (this.maxBurnTime == 0) {
				if (this.slots[0] != null) {
					if (TileEntityFurnace.isItemFuel(slots[0])) {
						burn();
					}

				}
			}
			if (this.maxBurnTime != 0 && this.burnTime == 0) {
				this.burnTime++;
				flag2 = true;
			}

			if (this.burnTime == maxBurnTime) {
				this.maxBurnTime = 0;
				this.burnTime = 0;
				flag2 = true;
			}

		}

		if (flag1 != this.burnTime > 0) {
			flag1 = true;

			GasLantern.updateLatternBlockState(this.isBurning(), worldObj, xCoord, yCoord, zCoord);
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

		}

		if (flag2) {
			this.markDirty();
		}

	}

	private void burn() {
		this.maxBurnTime = TileEntityFurnace.getItemBurnTime(this.slots[0]) * 10;
		this.slots[0].stackSize--;

		if (this.slots[0].stackSize <= 0) {
			this.slots[0] = null;
		}

	}

	public boolean isBurning() {
		if (this.maxBurnTime == 0) {
			return false;
		}
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.burnTime = nbt.getInteger("burnTime");
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("burnTime", this.burnTime);
		nbt.setInteger("maxBurnTime", this.maxBurnTime);

	}

	@Override
	public void onSync(int data, int id) {
		switch (id) {
		case SyncType.BURN:
			this.burnTime = data;
			break;
		case SyncType.SPECIAL1:
			this.maxBurnTime = data;
			break;
		}
	}

	@Override
	public SyncData getSyncData(int id) {
		switch (id) {
		case SyncType.BURN:
			return new SyncData(true, burnTime);
		case SyncType.SPECIAL1:
			return new SyncData(true, maxBurnTime);
		}
		return new SyncData(false, 0);
	}
}
