package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.inventory.ContainerLargeInventory;
import sonar.core.inventory.slots.SlotLarge;

import javax.annotation.Nonnull;

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
		addInventory(player.inventory, 8, 101);
	}

    @Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

    @Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			if (slot instanceof SlotLarge) {
				StoredItemStack stored = entity.getTileInv().getLargeStack(slotID);
				itemstack1 = stored == null ? ItemStack.EMPTY : stored.copy().setStackSize(Math.min(stored.stored, stored.getItemStack().getMaxStackSize())).getFullStack();
			}
			itemstack = itemstack1.copy();
			if (slotID < 14) {
				if (!this.mergeItemStack(itemstack1, 14, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
				StoredItemStack stored = entity.getTileInv().getLargeStack(slotID);
				stored.stored -= itemstack.getCount() - itemstack1.getCount();
				if (stored.stored == 0) {
					entity.getTileInv().setLargeStack(slotID, null);
				}
				entity.getTileInv().setLargeStack(slotID, stored.copy());
				return ItemStack.EMPTY;
			} else if (!this.mergeSpecial(itemstack1, 0, 14, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

    @Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		//this.entity.closeInventory(player);
	}
}