package sonar.calculator.mod.common.containers;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IFlawlessCalculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityModuleWorkstation;
import sonar.core.inventory.ContainerSync;

public class ContainerModuleWorkstation extends ContainerSync {
	private final InventoryPlayer inventory;
	private final TileEntityModuleWorkstation entity;

	private static final int INV_START = 17, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	public ContainerModuleWorkstation(InventoryPlayer inventory, TileEntityModuleWorkstation entity) {
		super(entity);
		this.inventory = inventory;
		this.entity = entity;

		for (int j = 0; j < 2; ++j) {
			for (int k = 0; k < 8; ++k) {
				this.addSlotToContainer(new Slot(entity, k + j * 8, 10 + k * 20, 40 + j * 22) {
					public boolean isItemValid(@Nullable ItemStack stack) {
						return stack != null && Calculator.moduleItems.getSecondaryObject(stack.getItem()) != null;
					}

					public int getSlotStackLimit() {
						return 1;
					}
				});
			}
		}

		addSlotToContainer(new Slot(entity, 16, 8, 8) {
			public boolean isItemValid(@Nullable ItemStack stack) {
				return stack != null && stack.getItem() instanceof IFlawlessCalculator;
			}
		});

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotID < INV_START) {
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else {

				if (slotID >= INV_START) {
					if (itemstack1 != null && itemstack1.getItem() instanceof IFlawlessCalculator) {
						if (!this.mergeItemStack(itemstack1, 16, 17, false)) {
							return null;
						}
					} else if (!this.mergeItemStack(itemstack1, 0, INV_START - 1, false)) {
						return null;
					}
				} else if (slotID >= INV_START && slotID < HOTBAR_START) {
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
						return null;
					}
				} else if (slotID >= HOTBAR_START && slotID < HOTBAR_END + 1) {
					if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
						return null;
					}
				}
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
