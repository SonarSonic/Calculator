package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.api.IHealthStore;
import sonar.calculator.mod.api.IHungerStore;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.core.inventory.ContainerSync;

public class ContainerAssimilator extends ContainerSync {
	private TileEntityAssimilator entity;

	public ContainerAssimilator(InventoryPlayer inventory, TileEntityAssimilator entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 80, 34));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if ((slotID != 0)) {
				if (itemstack1.getItem() instanceof IHungerStore || itemstack1.getItem() instanceof IHealthStore) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {

						return null;
					}

				} else if ((slotID >= 1) && (slotID < 28)) {
					if (!mergeItemStack(itemstack1, 28, 37, false)) {
						return null;
					}
				} else if ((slotID >= 28) && (slotID < 37) && (!mergeItemStack(itemstack1, 1, 28, false))) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 1, 37, false)) {
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
	public ItemStack slotClick(int par, int par1, int par2, EntityPlayer player) {
		return super.slotClick(par, par1, par2, player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUseableByPlayer(player);
	}
}
