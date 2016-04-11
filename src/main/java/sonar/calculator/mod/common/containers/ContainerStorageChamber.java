package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.inventory.ContainerLargeInventory;
import sonar.core.inventory.slots.SlotLarge;

public class ContainerStorageChamber extends ContainerLargeInventory {
	private TileEntityStorageChamber entity;

	public ContainerStorageChamber(EntityPlayer player, TileEntityStorageChamber entity) {
		super(entity);
		this.entity = entity;
		
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 0, 26, 6 + 17));
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 1, 62, 6 + 17));
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 2, 98, 6 + 17));
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 3, 134, 6 + 17));
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 4, 8, 32 + 17));
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 5, 44, 32 + 17));
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 6, 80, 32 + 17));
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 7, 116, 32 + 17));
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 8, 152, 32 + 17));
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 9, 8, 58 + 17));
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 10, 44, 58 + 17));
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 11, 80, 58 + 17));
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 12, 116, 58 + 17));
		addSlotToContainer(new SlotLarge(entity.getTileInv(), 13, 152, 58 + 17));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 17));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142 + 17));
		}
	}

	public boolean canInteractWith(EntityPlayer player) {
		return this.entity.isUseableByPlayer(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			if (slot instanceof SlotLarge) {
				StoredItemStack stored = entity.getTileInv().buildItemStack(entity.getTileInv().slots[slotID]);
				itemstack1 = stored.copy().setStackSize(Math.min(stored.stored, stored.getItemStack().getMaxStackSize())).getFullStack();
			}
			itemstack = itemstack1.copy();
			if (slotID < 14) {
				if (!this.mergeItemStack(itemstack1, 14, this.inventorySlots.size(), true)) {
					return null;
				}
				StoredItemStack stored = entity.getTileInv().buildItemStack(entity.getTileInv().slots[slotID]);
				stored.stored -= itemstack.stackSize - itemstack1.stackSize;
				if (stored.stored == 0) {
					entity.getTileInv().slots[slotID] = null;
				}
				entity.getTileInv().slots[slotID] = entity.getTileInv().buildArrayList(stored);
				return null;
			} else if (!this.mergeSpecial(itemstack1, 0, 14, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		this.entity.closeInventory(player);
	}
}