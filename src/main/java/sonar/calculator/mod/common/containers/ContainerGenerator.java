package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

import javax.annotation.Nonnull;

public class ContainerGenerator extends ContainerSync {

	private TileEntityGenerator entity;
	public static TransferSlotsManager<TileEntityGenerator> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<TileEntityGenerator>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityGenerator inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return TileEntityFurnace.isItemFuel(stack);
				}
			});
			addTransferSlot(new TransferSlots<TileEntityGenerator>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityGenerator inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return inv.getItemValue(stack) > 0;
				}
			});
			addPlayerInventory();
		}
	};

	public ContainerGenerator(InventoryPlayer inventory, TileEntityGenerator entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new Slot(entity, 0, 8, 38));
		addSlotToContainer(new Slot(entity, 1, 8, 14));
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
