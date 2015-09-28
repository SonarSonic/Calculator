package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPoint;
import sonar.core.inventory.ContainerSync;
import sonar.core.utils.SlotAllowed;

public class ContainerFluxController extends ContainerSync {

	public int state;

	public ContainerFluxController(InventoryPlayer player, TileEntityFluxController entity) {
		super(entity);
		addSlots(player,entity);


	}

	public void addSlots(InventoryPlayer player, TileEntity entity) {

		if(state==1){
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(player, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
		}else{
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(player, k + i * 9 + 9, 8 + k * 18, 84 + 33 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player, i, 8 + i * 18, 142 + 33));
		}
		for (int j = 0; j < 9; j++) {
			addSlotToContainer(new SlotAllowed((IInventory) entity, j, 8 + j * 18, 93, Calculator.itemLocatorModule));
		}
		}
		
	}

	public void switchState(InventoryPlayer player, TileEntity entity) {
		if (state == 0) {
			state = 1;
		} else {
			state = 0;
		}
		this.inventoryItemStacks.clear();
		this.inventorySlots.clear();
		this.addSlots(player, entity);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (state == 0) {

				if (id < 36 && itemstack.getItem() == Calculator.itemLocatorModule) {
					if (!this.mergeItemStack(itemstack1, 36, 45, false)) {
						return null;
					}

				} else if (id < 27) {
					if (!this.mergeItemStack(itemstack1, 27, 36, false)) {
						return null;
					}
				} else if (id >= 27 && id < 36) {
					if (!this.mergeItemStack(itemstack1, 0, 27, false)) {
						return null;
					}
				} else if (!this.mergeItemStack(itemstack1, 0, 36, false)) {
					return null;
				}

				if (itemstack1.stackSize == 0) {
					slot.putStack((ItemStack) null);
				} else {
					slot.onSlotChanged();

				}
			} else {
				if (id < 27) {
					if (!this.mergeItemStack(itemstack1, 27, 36, false)) {
						return null;
					}
				} else if (id >= 27 && id < 36) {
					if (!this.mergeItemStack(itemstack1, 0, 27, false)) {
						return null;
					}
				} else if (!this.mergeItemStack(itemstack1, 0, 36, false)) {
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
		}
		return itemstack;
	}

}