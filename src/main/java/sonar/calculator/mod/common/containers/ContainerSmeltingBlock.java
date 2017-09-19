package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

public class ContainerSmeltingBlock extends ContainerSync {
	
	private TileEntityAbstractProcess entity;
	public static TransferSlotsManager<TileEntityAbstractProcess> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<TileEntityAbstractProcess>(TransferType.TILE_INV, 1){
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityAbstractProcess inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return inv.isItemValidForSlot(0, stack);
				}
			});
			addTransferSlot(TransferSlotsManager.DISCHARGE_SLOT);
			addTransferSlot(new DisabledSlots<TileEntityAbstractProcess>(TransferType.TILE_INV, 1));
			addPlayerInventory();
		}
	};

	public ContainerSmeltingBlock(InventoryPlayer inventory, TileEntityAbstractProcess entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 53, 24));
		addSlotToContainer(new Slot(entity, 1, 28, 60));
		addSlotToContainer(new Slot(entity, 2, 107, 24));
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
