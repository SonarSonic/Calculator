package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.core.inventory.ContainerSonar;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.slots.SlotBlockedInventory;

public class ContainerAlgorithmAssimilator extends ContainerSonar {
	private TileEntityAssimilator entity;
	public static TransferSlotsManager<TileEntityAssimilator> transfer = new TransferSlotsManager() {
		{

			addTransferSlot(new TransferSlots<TileEntityAssimilator>(TransferType.TILE_INV, 3 * 9) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityAssimilator inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return true;
				}
			});
			addPlayerInventory();
		}
	};

	public ContainerAlgorithmAssimilator(EntityPlayer player, TileEntityAssimilator entity) {
		this.entity = entity;
		entity.openInventory(player);
		int i = (3 - 4) * 18;
		int j;
		int k;

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlotToContainer(new SlotBlockedInventory(entity, k + j * 9, 8 + k * 18, 24 + j * 18));
			}
		}
		addInventory(player.inventory, 8,84);
	}

    @Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.entity.isUseableByPlayer(player);
	}

    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return transfer.transferStackInSlot(this, entity, player, slotID);
	}

    @Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		this.entity.closeInventory(player);
	}

	public IInventory getLowerChestInventory() {
		return this.entity;
	}
}