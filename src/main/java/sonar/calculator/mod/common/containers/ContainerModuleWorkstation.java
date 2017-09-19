package sonar.calculator.mod.common.containers;

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
                    @Override
					public boolean isItemValid(ItemStack stack) {
						return !stack.isEmpty() && Calculator.moduleItems.getSecondaryObject(stack.getItem()) != null;
					}

                    @Override
					public int getSlotStackLimit() {
						return 1;
					}
				});
			}
		}

		addSlotToContainer(new Slot(entity, 16, 8, 8) {
            @Override
			public boolean isItemValid(ItemStack stack) {
				return !stack.isEmpty() && stack.getItem() instanceof IFlawlessCalculator;
			}
		});
		addInventory(inventory, 8, 84);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUsableByPlayer(player);
	}

    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(slotID);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotID < INV_START) {
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)) {
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else {

				if (slotID >= INV_START) {
					if (itemstack1 != null && itemstack1.getItem() instanceof IFlawlessCalculator) {
						if (!this.mergeItemStack(itemstack1, 16, 17, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.mergeItemStack(itemstack1, 0, INV_START - 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (slotID >= INV_START && slotID < HOTBAR_START) {
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (slotID >= HOTBAR_START && slotID < HOTBAR_END + 1) {
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
}
