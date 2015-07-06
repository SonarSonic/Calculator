package sonar.calculator.mod.common.tileentity.machines;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.BigStack;
import sonar.calculator.mod.common.item.misc.ItemCircuit.CircuitType;
import sonar.calculator.mod.network.packets.PacketSonarSides;
import sonar.core.common.tileentity.TileEntitySidedInventory;
import sonar.core.common.tileentity.TileEntitySonar;

public class TileEntityStorageChamberDEAD2 extends TileEntitySonar implements ISidedInventory {

	public BigStack[] slots;
	public ItemStack stack;

	public TileEntityStorageChamberDEAD2() {
		slots = new BigStack[14];
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagCompound stackTag = (NBTTagCompound) nbt.getTag("Stack");
		if (stackTag != null) {
			stack = ItemStack.loadItemStackFromNBT(stackTag);
		}
		NBTTagList list = nbt.getTagList("Items", 10);
		this.slots = new BigStack[this.getSizeInventory()];
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound compound = list.getCompoundTagAt(i);
			byte b = compound.getByte("Slot");
			if (b >= 0 && b < this.slots.length) {
				this.slots[b] = new BigStack();
				this.slots[b].readFromNBT(compound);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (this.stack != null) {
			NBTTagCompound stackTag = new NBTTagCompound();
			stack.writeToNBT(stackTag);
			nbt.setTag("Stack", stackTag);
		}
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
	public int getSizeInventory() {
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		if (slots[var1] == null) {
			return null;
		}
		return this.slots[var1].getFullStack();
	}

	@Override
	public ItemStack decrStackSize(int slot, int var2) {
		if (this.slots[slot] != null) {

			if (this.slots[slot].maxStackSize <= var2) {
				ItemStack itemstack = this.slots[slot].getFullStack();
				this.slots[slot] = null;
				return itemstack;
			}
			ItemStack itemstack = this.slots[slot].getFullStack().splitStack(var2);

			if (this.slots[slot].getFullStack().stackSize == 0) {
				this.slots[slot] = null;
			}

			return itemstack;
		}

		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.slots[i] != null) {
			ItemStack itemstack = this.slots[i].getFullStack();
			this.slots[i] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		
		//needs fix

		if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit())) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public String getInventoryName() {
		return this.blockType.getLocalizedName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		return true;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return true;
	}

}
