package sonar.calculator.mod.common.tileentity.misc;


import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.SyncData;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.core.common.tileentity.TileEntityInventoryReceiver;
import sonar.core.utils.ChargingUtils;
import sonar.core.utils.EnergyCharge;
import sonar.core.utils.helpers.RenderHelper;
import cofh.api.energy.EnergyStorage;

public class TileEntityCO2Generator extends TileEntityInventoryReceiver implements ISidedInventory{

	public int burnTime;
	public int maxBurnTime;
	public int maxBurn = 10000;
	public int energyAmount = 100000;
	public int gasAdd;
	public boolean controlled;
	public boolean control;
	private static final int[] input = new int[] { 0 };

	public TileEntityCO2Generator() {
		super.storage = new EnergyStorage(1000000, 1000000);
		super.slots = new ItemStack[2];
	}

	@Override
	public void updateEntity() {

		super.updateEntity();
		if(RenderHelper.getHorizontal(getForward())!=null){
		boolean flag1 = this.burnTime > 0;
		boolean flag2 = false;
		ForgeDirection hoz = RenderHelper.getHorizontal(getForward()).getOpposite();
		TileEntity tile = this.worldObj.getTileEntity(xCoord+ (hoz.offsetX * 3), yCoord, zCoord+ (hoz.offsetZ*3));
		
		if (this.maxBurnTime == 0 && !this.worldObj.isRemote && this.slots[0] != null) {
			if (TileEntityFurnace.isItemFuel(slots[0])&& this.storage.getEnergyStored() >= energyAmount) {			
						if (tile != null&& tile instanceof TileEntityFlawlessGreenhouse) {
							burn();
							this.storage.modifyEnergyStored(-energyAmount);
						}
					}
				
				
			}
		if(!this.controlled){
			if (this.maxBurnTime != 0 && this.burnTime >= 0	&& this.burnTime < this.maxBurnTime) {				
				flag2 = true;
				burnTime++;
				}
			}
		
		if(this.controlled){
			if (tile != null && tile instanceof TileEntityFlawlessGreenhouse) {
				TileEntityFlawlessGreenhouse greenhouse = (TileEntityFlawlessGreenhouse) tile;
				int carbon = greenhouse.getCarbon();
				if(control){
					if (!(carbon == greenhouse.maxLevel)) {
						if (this.maxBurnTime != 0 && this.burnTime >= 0	&& this.burnTime < this.maxBurnTime) {
								flag2 = true;
								burnTime++;
								this.gasAdd = 800;
							}						
					}else{
						this.control=false;
					}
					
				}
				if(!control){
					if(carbon<=92000){
						this.control=true;				
					}else{
						this.gasAdd=0;
						}
					}				
				}
			}
			if (this.burnTime == this.maxBurnTime) {
				this.maxBurnTime = 0;
				this.burnTime = 0;
				this.gasAdd = 0;
				flag2 = true;
				}			
			if (flag2) {
				this.markDirty();
			}
		}
			discharge(1);
			
	}

	public void burn() {
		this.maxBurnTime = maxBurn;
		this.gasAdd = TileEntityFurnace.getItemBurnTime(this.slots[0]) / 100;
		this.controlled = slots[0].getItem() == Calculator.controlled_Fuel;
		this.slots[0].stackSize--;

		if (this.slots[0].stackSize <= 0) {
			this.slots[0] = null;
		}

	}

	public boolean isBurning() {
		
		if (this.maxBurnTime == 0) {
			return false;}
		
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.burnTime = nbt.getInteger("burnTime");
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.controlled = nbt.getBoolean("controlled");
		this.control = nbt.getBoolean("control");
		this.gasAdd = nbt.getInteger("gasAdd");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("burnTime", this.burnTime);
		nbt.setInteger("maxBurnTime", this.maxBurnTime);
		nbt.setBoolean("controlled", this.controlled);
		nbt.setBoolean("control", this.control);
		nbt.setInteger("gasAdd", this.gasAdd);

	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return input;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 0 && TileEntityFurnace.isItemFuel(stack)) {
			return true;
		}
		return false;

	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int par) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return false;
	}

	public ForgeDirection getForward() {

		return ForgeDirection.getOrientation(this.getBlockMetadata())
				.getOpposite();

	}
	@Override
	public void onSync(int data, int id) {
		super.onSync(data, id);
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
		return super.getSyncData(id);
	}

}
