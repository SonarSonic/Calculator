package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.slots.SlotBlockedInventory;

import javax.annotation.Nonnull;

public class ContainerDualOutputSmelting extends ContainerSync {

	public TileEntityAbstractProcess entity;
	public static TransferSlotsManager<TileEntityAbstractProcess> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<TileEntityAbstractProcess>(TransferType.TILE_INV, 1) {
                @Override
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
	
	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return transfer.transferStackInSlot(this, entity, player, slotID);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUsableByPlayer(player);
	}
}
