package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.utils.SlotPortable;
import sonar.core.common.item.InventoryItem;
import sonar.core.inventory.ContainerSonar;
import sonar.core.inventory.slots.SlotLimiter;

public class ContainerStorageModule extends ContainerSonar {
	private final InventoryItem inventory;
	private boolean isRemote;

	private static final int INV_START = 54, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	public ContainerStorageModule(EntityPlayer player, InventoryItem inventoryItem) {
		this.inventory = inventoryItem;
		isRemote = player.getEntityWorld().isRemote;
		int i = 36;
		int j;
		int k;

		for (j = 0; j < 6; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlotToContainer(new SlotPortable(inventory, k + j * 9, 8 + k * 18, 18 + j * 18, isRemote, Calculator.itemStorageModule));
			}
		}
		addInventoryWithLimiter(player.inventory, 8, 140, Calculator.itemStorageModule);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return inventory.isUseableByPlayer(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(id);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (id < INV_START) {
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else {

				if (id >= INV_START) {
					if (!this.mergeItemStack(itemstack1, 0, INV_START, false)) {
						return ItemStack.EMPTY;
					}
				} else if (id >= INV_START && id < HOTBAR_START) {
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (id >= HOTBAR_START && id < HOTBAR_END + 1) {
					if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
						return ItemStack.EMPTY;
					}
				}
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	public ItemStack slotClick(int slot, int drag, ClickType click, EntityPlayer player) {
		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItemMainhand()) {
			return ItemStack.EMPTY;
		}
		return super.slotClick(slot, drag, click, player);
	}
}
