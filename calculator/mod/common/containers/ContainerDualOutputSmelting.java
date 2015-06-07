package sonar.calculator.mod.common.containers;

import ic2.api.item.IElectricItem;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.ISyncTile;
import sonar.calculator.mod.api.SyncType;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.calculator.mod.network.packets.PacketTileSync;
import sonar.core.utils.DischargeValues;
import sonar.core.utils.SlotBlockedInventory;
import sonar.core.utils.SonarAPI;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerDualOutputSmelting extends ContainerSync {
	
	public TileEntityAbstractProcess entity;
	public int lastCookTime;
	public int lastFurnaceSpeed;

	public ContainerDualOutputSmelting(InventoryPlayer inventory, TileEntityAbstractProcess entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 39, 24));
		addSlotToContainer(new Slot(entity, 1, 28, 60));
		addSlotToContainer(new SlotBlockedInventory(entity, 2, 93, 24));
		addSlotToContainer(new SlotBlockedInventory(entity, 3, 121, 24));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
		}
	}
	/*
	@Override
	public void addCraftingToCrafters(ICrafting icrafting) {
		super.addCraftingToCrafters(icrafting);

		icrafting.sendProgressBarUpdate(this, 0, this.entity.cookTime);
		icrafting.sendProgressBarUpdate(this, 1, this.entity.currentSpeed);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); i++) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastCookTime != this.entity.cookTime) {
				icrafting.sendProgressBarUpdate(this, 0, this.entity.cookTime);
			}
			if (this.lastFurnaceSpeed != this.entity.currentSpeed) {
				icrafting.sendProgressBarUpdate(this, 1, this.entity.currentSpeed);
			}
			
		}
		for(int i = 0; i <syncData.length; i++){
			if (syncData[i] != entity.getSyncData(i) && crafters != null) {
				for (Object o : crafters)
					if (o != null && o instanceof EntityPlayerMP)
						Calculator.network.sendTo(new PacketTileSync(entity.xCoord, entity.yCoord, entity.zCoord, i, entity), (EntityPlayerMP) o);
			}
			syncData[i] = entity.storage.getEnergyStored();
				
		}
		
		this.lastCookTime = this.entity.cookTime;
		this.lastFurnaceSpeed = this.entity.currentSpeed;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int value) {
		super.updateProgressBar(slot, value);
		if (slot == 0)
			this.entity.cookTime = value;
		if (slot == 1)
			this.entity.currentSpeed = value;

	}
*/
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (id == 3 || id == 2) {
				if (!mergeItemStack(itemstack1, 4, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if ((id != 1) && (id != 0)) {
				if (entity.isItemValidForSlot(0, itemstack1)) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (DischargeValues.discharge().value(itemstack1) > 0) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if (itemstack1.getItem() instanceof IEnergyContainerItem) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				}else if (SonarAPI.ic2Loaded() && itemstack1.getItem() instanceof IElectricItem) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				}else if (SonarAPI.ic2Loaded() && itemstack1.getItem() instanceof ISpecialElectricItem) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if ((id >= 4) && (id < 30)) {
					if (!mergeItemStack(itemstack1, 30, 39, false)) {
						return null;
					}
				} else if ((id >= 30) && (id < 39)
						&& (!mergeItemStack(itemstack1, 4, 30, false))) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if (!mergeItemStack(itemstack1, 4, 39, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	
	  @Override
	public boolean canInteractWith(EntityPlayer player) {
		    return entity.isUseableByPlayer(player);
		  }

}
