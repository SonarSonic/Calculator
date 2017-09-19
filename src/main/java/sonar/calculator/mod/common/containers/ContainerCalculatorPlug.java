package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

public class ContainerCalculatorPlug extends ContainerSync {
	private TileEntityCalculatorPlug entity;
	public static TransferSlotsManager<TileEntityCalculatorPlug> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<TileEntityCalculatorPlug>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityCalculatorPlug inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return stack.getItem() instanceof IStability;
				}
			});
			addPlayerInventory();
		}
	};

	public ContainerCalculatorPlug(InventoryPlayer inventory, TileEntityCalculatorPlug entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new Slot(entity, 0, 80, 34));
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
