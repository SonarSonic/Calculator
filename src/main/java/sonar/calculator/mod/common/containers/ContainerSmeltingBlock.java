package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.inventory.ContainerSync;

public class ContainerSmeltingBlock extends ContainerSync {
	private TileEntityAbstractProcess entity;

	public ContainerSmeltingBlock(InventoryPlayer inventory, TileEntityAbstractProcess entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 53, 24));
		addSlotToContainer(new Slot(entity, 1, 28, 60));
		addSlotToContainer(new Slot(entity, 2, 107, 24));

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
	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (id == 2) {
				if (!mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if ((id != 1) && (id != 0)) {
				if (entity.isItemValidForSlot(0, itemstack1)) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (CalculatorHelper.canProvideEnergy(itemstack1)) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				}else if ((id >= 3) && (id < 30)) {
					if (!mergeItemStack(itemstack1, 30, 39, false)) {
						return null;
					}
				} else if ((id >= 30) && (id < 39) && (!mergeItemStack(itemstack1, 3, 30, false))) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 3, 39, false)) {
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
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUseableByPlayer(player);
	}

}
