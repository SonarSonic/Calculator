package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.slots.SlotBlockedInventory;

public class ContainerAtomicMultiplier extends ContainerSync {

	private TileEntityAtomicMultiplier entity;
	public static TransferSlotsManager<TileEntityAtomicMultiplier> transfer = new TransferSlotsManager() {
		{

			addTransferSlot(new TransferSlots<TileEntityAtomicMultiplier>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityAtomicMultiplier inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return slot.isItemValid(stack);
				}
			});
			addTransferSlot(new TransferSlots<TileEntityAtomicMultiplier>(TransferType.TILE_INV, 7) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityAtomicMultiplier inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return stack.getItem() == Calculator.circuitBoard;
				}
			});
			addTransferSlot(new DisabledSlots<TileEntityAtomicMultiplier>(TransferType.TILE_INV, 1));
			addTransferSlot(TransferSlotsManager.DISCHARGE_SLOT);
			addPlayerInventory();
		}
	};

	public ContainerAtomicMultiplier(InventoryPlayer inventory, TileEntityAtomicMultiplier entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 54, 16) {
            @Override
			public boolean isItemValid(ItemStack stack) {
				if (!TileEntityAtomicMultiplier.isAllowed(stack)) {
					return false;
				} else if (stack.getMaxStackSize() < 4) {
					return false;
				}
				return true;
			}
		});
		addSlotToContainer(new Slot(entity, 1, 26, 41));
		addSlotToContainer(new Slot(entity, 2, 44, 41));
		addSlotToContainer(new Slot(entity, 3, 62, 41));
		addSlotToContainer(new Slot(entity, 4, 80, 41));
		addSlotToContainer(new Slot(entity, 5, 98, 41));
		addSlotToContainer(new Slot(entity, 6, 116, 41));
		addSlotToContainer(new Slot(entity, 7, 134, 41));
		addSlotToContainer(new SlotBlockedInventory(entity, 8, 108, 16));
		addSlotToContainer(new Slot(entity, 9, 20, 62));
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
