package sonar.calculator.mod.common.tileentity.machines;

import java.util.List;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.machines.IProcessMachine;
import sonar.calculator.mod.utils.AtomicMultiplierBlacklist;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.energy.DischargeValues;
import sonar.core.inventory.SonarTileInventory;
import sonar.core.network.sync.SyncEnergyStorage;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;

public class TileEntityAtomicMultiplier extends TileEntityEnergyInventory implements ISidedInventory, IProcessMachine {

	public int cookTime, active;
	public int furnaceSpeed = 1000;
	public static int requiredEnergy = 1500000000;

	private static final int[] input = new int[] { 0 };
	private static final int[] circuits = new int[] { 1, 2, 3, 4, 5, 6, 7 };
	private static final int[] output = new int[] { 8 };

	public TileEntityAtomicMultiplier() {
		super.storage = new SyncEnergyStorage(1500000000, 1500000000);
		super.inv = new SonarTileInventory(this, 10);
	}

	@Override
	public void update() {
		super.update();
		discharge(9);
		if (this.cookTime > 0) {
			this.active = 1;
			this.cookTime++;
			int energy = requiredEnergy / furnaceSpeed;
			this.storage.modifyEnergyStored(-energy);
		}
		if (this.canCook()) {
			if (!this.worldObj.isRemote) {
				if (cookTime == 0) {
					this.cookTime++;
				}
			}
			if (this.cookTime == furnaceSpeed) {

				this.cookTime = 0;
				this.cookItem();
				this.active = 0;

				int energy = requiredEnergy / furnaceSpeed;
				this.storage.modifyEnergyStored(-energy);
				this.worldObj.markBlockForUpdate(pos);
			}

		} else {
			if (this.cookTime != 0 || this.active != 0) {
				this.cookTime = 0;
				this.active = 0;
				this.worldObj.markBlockForUpdate(pos);
			}
		}

		this.markDirty();

	}

	public boolean canCook() {
		if (this.storage.getEnergyStored() == 0) {
			return false;
		}
		for (int i = 0; i < 8; i++) {
			if (slots()[i] == null) {
				return false;
			}
		}
		if (!isAllowed(slots()[0])) {
			return false;
		}

		if (slots()[8] != null) {
			if (slots()[8].stackSize + 4 > 64) {
				return false;
			}
			if (!slots()[0].isItemEqual(slots()[8])) {
				return false;
			}
		}

		if (cookTime == 0) {
			if (this.storage.getEnergyStored() < requiredEnergy) {
				return false;
			}
		}
		if (!(slots()[0].getMaxStackSize() >= 4)) {
			return false;
		}

		for (int i = 1; i < 8; i++) {
			if (slots()[i].getItem() != Calculator.circuitBoard) {
				return false;
			}
		}

		if (cookTime >= furnaceSpeed) {
			return true;
		}
		return true;

	}

	public static boolean isAllowed(ItemStack stack) {
		return AtomicMultiplierBlacklist.blacklist().isAllowed(stack.getItem());
	}

	private void cookItem() {
		ItemStack itemstack = new ItemStack(slots()[0].getItem(), 4, slots()[0].getItemDamage());
		if (this.slots()[8] == null) {
			this.slots()[8] = itemstack;
		} else if (this.slots()[8].isItemEqual(itemstack)) {
			this.slots()[8].stackSize = this.slots()[8].stackSize + 4;
		}

		for (int i = 0; i < 8; i++) {
			this.slots()[i].stackSize--;
			if (this.slots()[i].stackSize <= 0) {
				this.slots()[i] = null;
			}
		}

	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.cookTime = nbt.getShort("cookTime");
			this.active = nbt.getShort("active");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setShort("cookTime", (short) this.cookTime);
			nbt.setShort("active", (short) this.active);
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (0 < slot && slot < 8) {
			if (stack.getItem() == Calculator.circuitBoard) {
				return true;
			}
		} else if (slot == 0) {
			if (stack.getMaxStackSize() >= 4) {
				return true;
			}
		} else if (slot == 9 && DischargeValues.getValueOf(stack) > 0) {
			return true;
		}
		return false;

	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return EnumFacing.DOWN == side ? output : EnumFacing.UP == side ? input : EnumFacing.VALUES[2] == side ? circuits : EnumFacing.VALUES[3] == side ? circuits : EnumFacing.VALUES[4] == side ? circuits : EnumFacing.VALUES[5] == side ? circuits : input;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing direction) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing direction) {
		if (slot == 8) {
			return true;
		}
		return false;
	}

	public boolean receiveClientEvent(int action, int param) {
		if (action == 1) {
			this.worldObj.markBlockForUpdate(pos);
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		super.getWailaInfo(currenttip);
		if (cookTime > 0) {
			String active = FontHelper.translate("locator.state") + ":" + FontHelper.translate("locator.active");
			currenttip.add(active);
		} else {
			String idle = FontHelper.translate("locator.state") + ":" + FontHelper.translate("locator.idle");
			currenttip.add(idle);
		}
		return currenttip;
	}

	@Override
	public int getCurrentProcessTime() {
		return cookTime;
	}

	@Override
	public int getProcessTime() {
		return furnaceSpeed;
	}

	@Override
	public double getEnergyUsage() {
		return requiredEnergy / getProcessTime();
	}

}
