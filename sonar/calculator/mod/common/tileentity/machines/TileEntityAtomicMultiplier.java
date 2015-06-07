package sonar.calculator.mod.common.tileentity.machines;


import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IPausable;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.utils.AtomicMultiplierBlacklist;
import sonar.core.common.tileentity.TileEntityInventoryReceiver;
import sonar.core.utils.DischargeValues;
import cofh.api.energy.EnergyStorage;

public class TileEntityAtomicMultiplier extends TileEntityInventoryReceiver implements ISidedInventory {

	public int cookTime, active;
	public int furnaceSpeed = 1000;
	public static int requiredEnergy = 900000000;
	
	private static final int[] input = new int[] { 0 };
	private static final int[] circuits = new int[] { 1, 2, 3, 4, 5, 6, 7 };
	private static final int[] output = new int[] { 8 };

	public TileEntityAtomicMultiplier() {
		super.storage = new EnergyStorage(900000000, 900000000);
		super.slots = new ItemStack[10];
	}

	@Override
	public void updateEntity() {		
		super.updateEntity();
		discharge(9);	
		if(this.cookTime>0){
			this.active=1;
			this.cookTime++;
			int energy = requiredEnergy / furnaceSpeed;
			this.storage.modifyEnergyStored(-energy);
		}		
		if (this.canCook()) {
			if (!this.worldObj.isRemote) {
			if(cookTime==0){
			this.cookTime++;
			}
			}
			if (this.cookTime == furnaceSpeed) {

			this.cookTime = 0;
			this.cookItem();
			this.active=0;
			
			int energy = requiredEnergy / furnaceSpeed;
			this.storage.modifyEnergyStored(-energy);
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}	

		} else {
			if(this.cookTime != 0 || this.active != 0 ){
				this.cookTime = 0;
				this.active=0;
				this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		
		
		
		this.markDirty();

	}


	public boolean canCook() {
		if (this.storage.getEnergyStored() == 0) {
			return false;
		}
		for (int i = 0; i < 8; i++) {
			if (slots[i] == null) {
				return false;
			}
		}
		if (!isAllowed(slots[0])) {
			return false;
		}

		if (slots[8] != null) {
			if (slots[8].stackSize + 4 > 64) {
				return false;
			}
			if (!slots[0].isItemEqual(slots[8])) {
				return false;
			}
		}

		if (cookTime == 0) {
			if (this.storage.getEnergyStored() < requiredEnergy) {
				return false;
			}
		}
		if (!(slots[0].getMaxStackSize() >= 4)) {
			return false;
		}

		for (int i = 1; i < 8; i++) {
			if (slots[i].getItem() != Calculator.circuitBoard) {
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
		ItemStack itemstack = new ItemStack(slots[0].getItem(), 4,	slots[0].getItemDamage());
		if (this.slots[8] == null) {
			this.slots[8] = itemstack;
		} else if (this.slots[8].isItemEqual(itemstack)) {
			this.slots[8].stackSize = this.slots[8].stackSize + 4;
		}

		for (int i = 0; i < 8; i++) {
			this.slots[i].stackSize--;
			if (this.slots[i].stackSize <= 0) {
				this.slots[i] = null;
			}
		}

	}


	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.cookTime = nbt.getShort("cookTime");
		this.active = nbt.getShort("active");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("cookTime", (short) this.cookTime);
		nbt.setShort("active", (short) this.active);
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
		} else if (slot == 9 && DischargeValues.discharge().value(stack) > 0) {
			return true;
		}
		return false;

	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int par) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int slot) {
		return slot == 0 ? output : slot == 1 ? input : slot == 2 ? circuits
				: slot == 3 ? circuits : slot == 4 ? circuits
						: slot == 5 ? circuits : input;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack p_102008_2_, int side) {
		if (slot == 8) {
			return true;
		}
		return false;
	}
	public boolean receiveClientEvent(int action, int param){
		if(action==1){
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);}
		return true;
	}
	@Override
	public void onSync(int data, int id) {
		super.onSync(data, id);
		switch (id) {
		case SyncType.COOK:
			this.cookTime = data;
			break;
		case SyncType.ACTIVE:
			this.active = data;
			break;
		}
	}

	@Override
	public SyncData getSyncData(int id) {
		switch (id) {
		case SyncType.COOK:
			return new SyncData(true, cookTime);
		case SyncType.ACTIVE:
			return new SyncData(true, active);
		}
		return super.getSyncData(id);
	}
}
