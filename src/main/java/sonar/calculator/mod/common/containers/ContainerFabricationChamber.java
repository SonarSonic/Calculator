package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFabricationChamber;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.slots.SlotBlockedInventory;

public class ContainerFabricationChamber extends ContainerSync {
	private TileEntityFabricationChamber entity;
	public static TransferSlotsManager<TileEntityFabricationChamber> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new DisabledSlots<TileEntityFabricationChamber>(TransferType.TILE_INV, 1));
			addPlayerInventory();
		}
	};

	public ContainerFabricationChamber(InventoryPlayer inventory, TileEntityFabricationChamber entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new SlotBlockedInventory(entity, 0, 115+18, 89));
		addInventory(inventory, 8, 118);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return transfer.transferStackInSlot(this, entity, player, slotID);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUsableByPlayer(player);
	}
}
