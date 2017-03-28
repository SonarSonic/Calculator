package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.misc.TileEntityMagneticFlux;
import sonar.core.inventory.slots.SlotList;

public class ContainerMagneticFlux extends Container {

	private static final int INV_START = 7, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	public ContainerMagneticFlux(InventoryPlayer inv, TileEntityMagneticFlux flux) {

		for (int i = 0; i < 7; i++) {
			addSlotToContainer(new SlotList(flux, i, 26 + (18 * i), 61));
		}

		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(inv, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 >= INV_START) {
				if (!this.mergeItemStack(itemstack1.copy(), 0, INV_START, false)) {
					return null;
				}
			} else if (par2 >= INV_START && par2 < HOTBAR_START) {
				if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
					return null;
				}
			} else if (par2 >= HOTBAR_START && par2 < HOTBAR_END + 1) {
				if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
					return null;
				}
			}
			if (itemstack1.getCount() == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return null;
			}

			slot.onTake(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

    public ItemStack slotClick(int slot, int drag, ClickType click, EntityPlayer player){
		Slot targetSlot = slot < 0 ? null : (Slot) this.inventorySlots.get(slot);
		if ((targetSlot instanceof SlotList)) {
			if (click == ClickType.SWAP) {
				targetSlot.putStack(null);
			} else {
				targetSlot.putStack(player.inventory.getItemStack() == null ? null : player.inventory.getItemStack().copy());
			}
			return player.inventory.getItemStack();
		}
		return super.slotClick(slot, drag, click, player);
	}
}