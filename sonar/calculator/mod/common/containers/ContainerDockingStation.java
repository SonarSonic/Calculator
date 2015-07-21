package sonar.calculator.mod.common.containers;

import ic2.api.item.IElectricItem;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.item.modules.StorageModule;
import sonar.calculator.mod.common.tileentity.machines.TileEntityDockingStation;
import sonar.calculator.mod.utils.SlotSize;
import sonar.core.utils.DischargeValues;
import sonar.core.utils.SlotBlockedInventory;
import sonar.core.utils.SonarAPI;
import cofh.api.energy.IEnergyContainerItem;

public class ContainerDockingStation extends ContainerSync {

	private TileEntityDockingStation entity;

	private int INV_START, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	public ContainerDockingStation(InventoryPlayer inventory, TileEntityDockingStation entity) {
		super(entity);
		this.entity = entity;
		INV_START=entity.isCalculator(entity.calcStack) + 2;
		addSlots(entity);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
	}

	public void addSlots(TileEntityDockingStation entity) {
		if (entity.calcStack != null) {
			if (entity.calcStack.getItem() == Calculator.itemFlawlessCalculator) {
				addSlotToContainer(new Slot(entity, 0, 16, 33));
				addSlotToContainer(new Slot(entity, 1, 48, 33));
				addSlotToContainer(new Slot(entity, 2, 80, 33));
				addSlotToContainer(new Slot(entity, 3, 112, 33));
				addSlotToContainer(new Slot(entity, 4, 28, 60));
				addSlotToContainer(new SlotBlockedInventory(entity, 5, 144, 33));
			} else if (entity.calcStack.getItem() == Item.getItemFromBlock(Calculator.atomiccalculatorBlock)) {
				addSlotToContainer(new Slot(entity, 0, 21, 30));
				addSlotToContainer(new Slot(entity, 1, 53, 30));
				addSlotToContainer(new Slot(entity, 2, 85, 30));
				addSlotToContainer(new Slot(entity, 4, 28, 60));
				addSlotToContainer(new SlotBlockedInventory(entity, 5, 135, 30));
			} else {

				addSlotToContainer(new Slot(entity, 0, 26, 30));
				addSlotToContainer(new Slot(entity, 1, 80, 30));
				addSlotToContainer(new Slot(entity, 4, 28, 60));
				addSlotToContainer(new SlotBlockedInventory(entity, 5, 135, 30));
			}

		}
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			int start = entity.isCalculator(entity.calcStack) + 2;
			if (id < start) {
				if (!this.mergeItemStack(itemstack1, start, HOTBAR_END + 1, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else {

				if (id >= entity.isCalculator(entity.calcStack)) {
					if (itemstack1.getItem() instanceof IEnergyContainerItem) {
						if (!mergeItemStack(itemstack1, start - 2, start - 1, false)) {
							return null;
						}
					} else if (SonarAPI.ic2Loaded() && itemstack1.getItem() instanceof IElectricItem) {
						if (!mergeItemStack(itemstack1, start - 2, start - 1, false)) {
							return null;
						}
					} else if (!this.mergeItemStack(itemstack1, 0, entity.isCalculator(entity.calcStack), false)) {
						return null;
					}

				} else if (id >= start && id < HOTBAR_START) {
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
						return null;
					}
				} else if (id >= HOTBAR_START && id < HOTBAR_END + 1) {
					if (!this.mergeItemStack(itemstack1, start, INV_END + 1, false)) {
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

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.calcStack != null;
	}
}
