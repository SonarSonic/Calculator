package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityDockingStation;
import sonar.core.api.SonarAPI;
import sonar.core.energy.DischargeValues;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.slots.SlotBlockedInventory;
import sonar.core.utils.SonarCompat;

public class ContainerDockingStation extends ContainerSync {

	public TileEntityDockingStation entity;

	private int INV_START, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	public ContainerDockingStation(InventoryPlayer inventory, TileEntityDockingStation entity) {
		super(entity);
		this.entity = entity;
        INV_START = TileEntityDockingStation.getInputStackSize(entity.calcStack) + 2;
		addSlots(entity);
		addInventory(inventory, 8, 84);
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
			} else if (entity.calcStack.getItem() == Item.getItemFromBlock(Calculator.atomicCalculator)) {
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

    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		ItemStack itemstack = SonarCompat.getEmpty();
        Slot slot = this.inventorySlots.get(id);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
            int start = TileEntityDockingStation.getInputStackSize(entity.calcStack) + 2;
			if (id < start) {
				if (!this.mergeItemStack(itemstack1, start, HOTBAR_END + 1, true)) {
					return SonarCompat.getEmpty();
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else {

                if (id >= TileEntityDockingStation.getInputStackSize(entity.calcStack)) {
					if (DischargeValues.getValueOf(itemstack1) > 0 || SonarAPI.getEnergyHelper().canTransferEnergy(itemstack1) != null) {
						if (!mergeItemStack(itemstack1, start - 2, start - 1, false)) {
							return SonarCompat.getEmpty();
						}
                    } else if (!this.mergeItemStack(itemstack1, 0, TileEntityDockingStation.getInputStackSize(entity.calcStack), false)) {
						return SonarCompat.getEmpty();
					}
				} else if (id >= start && id < HOTBAR_START) {
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
						return SonarCompat.getEmpty();
					}
				} else if (id >= HOTBAR_START && id < HOTBAR_END + 1) {
					if (!this.mergeItemStack(itemstack1, start, INV_END + 1, false)) {
						return SonarCompat.getEmpty();
					}
				}
			}

			if (SonarCompat.getCount(itemstack1) == 0) {
				slot.putStack(SonarCompat.getEmpty());
			} else {
				slot.onSlotChanged();
			}

			if (SonarCompat.getCount(itemstack1) == SonarCompat.getCount(itemstack)) {
				return SonarCompat.getEmpty();
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
