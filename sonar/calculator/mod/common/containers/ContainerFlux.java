package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPoint;

public class ContainerFlux extends ContainerSync {

	public ContainerFlux(InventoryPlayer player, TileEntityFlux entity) {
		super(entity);
		if (entity instanceof TileEntityFluxPoint) {
			for (int i = 0; i < 3; i++) {
				for (int k = 0; k < 9; k++) {
					addSlotToContainer(new Slot(player, k + i * 9 + 9, 8 + k * 18, 60 + i * 18));
				}
			}

			for (int i = 0; i < 9; i++) {
				addSlotToContainer(new Slot(player, i, 8 + i * 18, 118));
			}
		} else {
			for (int i = 0; i < 3; i++) {
				for (int k = 0; k < 9; k++) {
					addSlotToContainer(new Slot(player, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
				}
			}

			for (int i = 0; i < 9; i++) {
				addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
			}
		}

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

		return itemstack;
	}

}