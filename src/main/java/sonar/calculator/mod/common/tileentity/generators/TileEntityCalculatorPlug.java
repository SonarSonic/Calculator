package sonar.calculator.mod.common.tileentity.generators;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.api.IStability;
import sonar.calculator.mod.client.gui.generators.GuiCalculatorPlug;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.utils.ISyncTile;
import sonar.core.utils.helpers.NBTHelper.SyncType;

public class TileEntityCalculatorPlug extends TileEntityInventory implements ISyncTile {

	private String localizedName;
	public int stable;

	public TileEntityCalculatorPlug() {
		super.slots = new ItemStack[1];
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		int flag = stable;
		if (testStable()) {
			fill(0);
		}
		if (flag != this.stable) {
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			this.markDirty();
		}

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
			}
		}

		else if (!stability && slots[slot].getItem() instanceof IStability) {
			stable = 1;
		} else {
			stable = 0;
		}
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.stable = nbt.getInteger("Stable");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setInteger("Stable", this.stable);

		}
	}

	public byte getS() {
		if (stable == 2) {
			return 1;
		}
		return 0;
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		currenttip.add(GuiCalculatorPlug.getString(stable));
		return currenttip;
	}

}
