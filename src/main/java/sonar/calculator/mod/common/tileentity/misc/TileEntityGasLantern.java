package sonar.calculator.mod.common.tileentity.misc;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import sonar.calculator.mod.common.block.misc.GasLantern;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.network.utils.ISyncTile;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityGasLantern extends TileEntityInventory implements ISyncTile {

	public int burnTime;
	public int maxBurnTime;

	public TileEntityGasLantern() {
		super.slots = new ItemStack[1];
	}

	@Override
	public void updateEntity() {
		if (this.worldObj.isRemote) {
			return;
		}
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

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.burnTime = nbt.getInteger("burnTime");
			this.maxBurnTime = nbt.getInteger("maxBurnTime");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setInteger("burnTime", this.burnTime);
			nbt.setInteger("maxBurnTime", this.maxBurnTime);

		}
	}
	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		if (burnTime > 0 && maxBurnTime != 0) {
			String burn = FontHelper.translate("co2.burnt") + ": " + burnTime * 100 / maxBurnTime;
			currenttip.add(burn);
		} else {
			String burn = FontHelper.translate("co2.burning");
			currenttip.add(burn);
		}
		return currenttip;
	}
}
