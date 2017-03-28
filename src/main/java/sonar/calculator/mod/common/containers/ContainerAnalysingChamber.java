package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;
import sonar.core.api.SonarAPI;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.slots.SlotBlockedInventory;

public class ContainerAnalysingChamber extends ContainerSync {

	private TileEntityAnalysingChamber entity;

	public ContainerAnalysingChamber(InventoryPlayer inventory, TileEntityAnalysingChamber entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 16, 6));
		addSlotToContainer(new Slot(entity, 1, 28, 60));
		addSlotToContainer(new SlotBlockedInventory(entity, 2, 35, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 3, 53, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 4, 71, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 5, 89, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 6, 107, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 7, 125, 34));

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
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int num) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(num);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (num >= 2 && num < 8) {
				if (!mergeItemStack(itemstack1, 8, 43, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if ((num != 1) && (num != 0)) {
				if (itemstack.getItem() == Calculator.circuitBoard) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (SonarAPI.getEnergyHelper().canTransferEnergy(itemstack1)!=null) {
					if (!mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				}
			} else if (!mergeItemStack(itemstack1, 8, 43, false)) {
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

			slot.onTake(p_82846_1_, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUseableByPlayer(player);
	}
}
