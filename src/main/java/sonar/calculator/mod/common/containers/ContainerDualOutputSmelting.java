package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.core.api.SonarAPI;
import sonar.core.energy.DischargeValues;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.TransferSlotsManager.DisabledSlots;
import sonar.core.inventory.TransferSlotsManager.TransferSlots;
import sonar.core.inventory.TransferSlotsManager.TransferType;
import sonar.core.inventory.slots.SlotBlockedInventory;

public class ContainerDualOutputSmelting extends ContainerSync {

	public TileEntityAbstractProcess entity;
	public static TransferSlotsManager<TileEntityAbstractProcess> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<TileEntityAbstractProcess>(TransferType.TILE_INV, 1) {
				public boolean canInsert(EntityPlayer player, TileEntityAbstractProcess inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return inv.isItemValidForSlot(0, stack);
				}
			});
			addTransferSlot(TransferSlotsManager.DISCHARGE_SLOT);
			addTransferSlot(new DisabledSlots<TileEntityAbstractProcess>(TransferType.TILE_INV, 2));
			addPlayerInventory();
		}
	};

	public ContainerDualOutputSmelting(InventoryPlayer inventory, TileEntityAbstractProcess entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new Slot(entity, 0, 39, 24));
		addSlotToContainer(new Slot(entity, 1, 28, 60));
		addSlotToContainer(new SlotBlockedInventory(entity, 2, 93, 24));
		addSlotToContainer(new SlotBlockedInventory(entity, 3, 121, 24));
		addInventory(inventory, 8, 84);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return transfer.transferStackInSlot(this, entity, player, slotID);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUseableByPlayer(player);
	}

}
