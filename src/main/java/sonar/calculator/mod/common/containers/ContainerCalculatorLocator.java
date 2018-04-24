package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.calculator.mod.utils.SlotLocatorModule;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

import javax.annotation.Nonnull;

public class ContainerCalculatorLocator extends ContainerSync {
	private TileEntityCalculatorLocator entity;
	public static TransferSlotsManager<TileEntityCalculatorLocator> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(TransferSlotsManager.DISCHARGE_SLOT);
			addTransferSlot(new TransferSlots<TileEntityCalculatorLocator>(TransferType.TILE_INV, 1){
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityCalculatorLocator inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return stack.getItem() == Calculator.itemLocatorModule;
				}
			});
			addPlayerInventory();
		}
	};

	public ContainerCalculatorLocator(InventoryPlayer inventory, TileEntityCalculatorLocator entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new Slot(entity, 0, 28, 60));
		addSlotToContainer(new SlotLocatorModule(entity, 1, 132, 60));
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
