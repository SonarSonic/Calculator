package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.recipes.ConductorMastRecipes;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.slots.SlotBlockedInventory;

public class ContainerConductorMast extends ContainerSync {
	private TileEntityConductorMast entity;
	public static TransferSlotsManager<TileEntityConductorMast> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<TileEntityConductorMast>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityConductorMast inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return ConductorMastRecipes.instance().isValidInput(stack);
				}
			});
			addTransferSlot(new DisabledSlots<TileEntityConductorMast>(TransferType.TILE_INV, 1));
			addPlayerInventory();
		}
	};

	public ContainerConductorMast(InventoryPlayer inventory, TileEntityConductorMast entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 54, 22));
		addSlotToContainer(new SlotBlockedInventory(entity, 1, 108, 22));
		addInventory(inventory, 8, 84);
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
