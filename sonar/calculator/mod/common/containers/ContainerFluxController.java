package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.core.utils.SlotAllowed;

public class ContainerFluxController extends ContainerSync {

	public ContainerFluxController(InventoryPlayer player, TileEntityFluxController entity) {
		super(entity);

		for (int j = 0; j < 9; j++) {
			addSlotToContainer(new SlotAllowed(entity, j, 8 + j * 18, 93, Calculator.itemLocatorModule));
		}

		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(player, k + i * 9 + 9, 8 + k * 18, 84 + 33 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player, i, 8 + i * 18, 142 + 33));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (id >= 9 && itemstack.getItem() == Calculator.itemLocatorModule) {
				if (!this.mergeItemStack(itemstack1, 0, 9, false)) {
					return null;
				}

			} else if (id < 36) {
				if (!this.mergeItemStack(itemstack1, 36, 45, false)) {
					return null;
				}
			} else if (id >= 36 && id < 45) {
				if (!this.mergeItemStack(itemstack1, 9, 36, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 9, 45, false)) {
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

}