package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.api.nutrition.IHealthStore;
import sonar.calculator.mod.api.nutrition.IHungerStore;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

public class ContainerAssimilator extends ContainerSync {
	private TileEntityAssimilator entity;
	public static TransferSlotsManager<TileEntityAssimilator> assimilatorTransfer = new TransferSlotsManager() {
		{

			addTransferSlot(new TransferSlots<TileEntityAssimilator>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityAssimilator inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return stack.getItem() instanceof IHungerStore || stack.getItem() instanceof IHealthStore;
				}
			});
			addPlayerInventory();
		}
	};

	public ContainerAssimilator(InventoryPlayer inventory, TileEntityAssimilator entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new Slot(entity, 0, 80, 34));
		addInventory(inventory, 8, 84);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return assimilatorTransfer.transferStackInSlot(this, entity, player, slotID);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUsableByPlayer(player);
	}
}
