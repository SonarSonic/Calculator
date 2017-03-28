package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.core.api.SonarAPI;
import sonar.core.energy.DischargeValues;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.slots.SlotBlockedInventory;

public class ContainerAtomicMultiplier extends ContainerSync {

	private TileEntityAtomicMultiplier entity;

	public ContainerAtomicMultiplier(InventoryPlayer inventory, TileEntityAtomicMultiplier entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 54, 16) {
			public boolean isItemValid(ItemStack stack) {
				if (!TileEntityAtomicMultiplier.isAllowed(stack)) {
					return false;
				} else if (stack.getMaxStackSize() < 4) {
					return false;
				}
				return true;
			}
		});
		addSlotToContainer(new Slot(entity, 1, 26, 41));
		addSlotToContainer(new Slot(entity, 2, 44, 41));
		addSlotToContainer(new Slot(entity, 3, 62, 41));
		addSlotToContainer(new Slot(entity, 4, 80, 41));
		addSlotToContainer(new Slot(entity, 5, 98, 41));
		addSlotToContainer(new Slot(entity, 6, 116, 41));
		addSlotToContainer(new Slot(entity, 7, 134, 41));
		addSlotToContainer(new SlotBlockedInventory(entity, 8, 108, 16));
		addSlotToContainer(new Slot(entity, 9, 20, 62));

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

			if (slotID <= 9) {
				if (!mergeItemStack(itemstack1, 10, 46, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (slotID > 9) {
				if (itemstack1.getItem() == Calculator.circuitBoard) {
					if (!mergeItemStack(itemstack1, 1, 8, false)) {
						return null;
					}
				} else if (DischargeValues.getValueOf(itemstack1) > 0) {
					if (!mergeItemStack(itemstack1, 9, 10, false)) {
						return null;
					}
				} else if (SonarAPI.getEnergyHelper().canTransferEnergy(itemstack1)!=null) {
					if (!mergeItemStack(itemstack1, 9, 10, false)) {
						return null;
					}
				}
			} else if (itemstack1.getMaxStackSize() >= 4) {
				if (!mergeItemStack(itemstack1, 0, 1, false)) {
					return null;
				}
			} else if ((slotID >= 10) && (slotID < 37)) {
				if (!mergeItemStack(itemstack1, 37, 46, false)) {
					return null;
				}
			} else if ((slotID >= 37) && (slotID < 46) && (!mergeItemStack(itemstack1, 10, 37, false))) {
				return null;

			} else if (!mergeItemStack(itemstack1, 10, 46, false)) {
				return null;
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return null;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUseableByPlayer(player);
	}
}
