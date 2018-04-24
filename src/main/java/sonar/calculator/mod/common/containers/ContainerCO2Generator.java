package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

import javax.annotation.Nonnull;

public class ContainerCO2Generator extends ContainerSync {

	private TileEntityCO2Generator entity;
	public static TransferSlotsManager<TileEntityCO2Generator> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<TileEntityCO2Generator>(TransferType.TILE_INV, 1){
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityCO2Generator inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return TileEntityFurnace.getItemBurnTime(stack) > 0;
				}
			});
			addTransferSlot(TransferSlotsManager.DISCHARGE_SLOT);
			addPlayerInventory();
		}
	};

	public ContainerCO2Generator(InventoryPlayer inventory, TileEntityCO2Generator entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new Slot(entity, 0, 80, 22));
		addSlotToContainer(new Slot(entity, 1, 28, 60));
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
