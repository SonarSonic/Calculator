package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.misc.TileEntityReinforcedChest;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.inventory.ContainerLargeInventory;
import sonar.core.inventory.slots.SlotLarge;

public class ContainerReinforcedChest extends ContainerLargeInventory {
	public TileEntityReinforcedChest entity;

	public ContainerReinforcedChest(EntityPlayer player, TileEntityReinforcedChest entity) {
		super(entity);
		this.entity = entity;
		entity.openInventory(player);
		int i = -18;
		int j;
		int k;

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlotToContainer(new SlotLarge(entity.getTileInv(), k + j * 9, 8 + k * 18, 24 + j * 18));
			}
		}
		addInventory(player.inventory, 8, 102);
	}

    @Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.entity.isUseableByPlayer(player);
	}

    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			if (slot instanceof SlotLarge) {
				StoredItemStack stored = entity.getTileInv().getLargeStack(slotID);
				itemstack1 = stored == null ? null : stored.copy().setStackSize(Math.min(stored.stored, stored.getItemStack().getMaxStackSize())).getFullStack();
			}
			itemstack = itemstack1.copy();
			if (slotID < 27) {
				if (!this.mergeItemStack(itemstack1, 3 * 9, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
				StoredItemStack stored = entity.getTileInv().getLargeStack(slotID);
				stored.stored -= itemstack.getCount() - itemstack1.getCount();
				if (stored.stored == 0) {
					entity.getTileInv().setLargeStack(slotID, null);
				}
				entity.getTileInv().setLargeStack(slotID, stored);
				return ItemStack.EMPTY;
			} else if (!this.mergeSpecial(itemstack1, 0, 3 * 9, false)) {
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
		this.entity.closeInventory(player);
	}
}