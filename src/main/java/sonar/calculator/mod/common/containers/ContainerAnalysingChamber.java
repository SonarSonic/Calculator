package sonar.calculator.mod.common.containers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;
import sonar.core.api.SonarAPI;
import sonar.core.energy.DischargeValues;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.TransferSlotsManager.TransferSlots;
import sonar.core.inventory.TransferSlotsManager.TransferType;
import sonar.core.inventory.slots.SlotBlockedInventory;

public class ContainerAnalysingChamber extends ContainerSync {

	private TileEntityAnalysingChamber entity;
	public static TransferSlotsManager<TileEntityAnalysingChamber> analysingChamberTransfer = new TransferSlotsManager() {
		{

			addTransferSlot(new TransferSlots<TileEntityAnalysingChamber>(TransferType.TILE_INV, 1) {
				public boolean canInsert(EntityPlayer player, TileEntityAnalysingChamber inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return stack.getItem() == Calculator.circuitBoard;
				}
			});
			addTransferSlot(TransferSlotsManager.DISCHARGE_SLOT);
			addTransferSlot(new TransferSlots<TileEntityAnalysingChamber>(TransferType.TILE_INV, 6) {
				public boolean canInsert(EntityPlayer player, TileEntityAnalysingChamber inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return false;
				}
			});
			addPlayerInventory();
		}
	};
	
	public ContainerAnalysingChamber(InventoryPlayer inventory, TileEntityAnalysingChamber entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new Slot(entity, 0, 16, 6));
		addSlotToContainer(new Slot(entity, 1, 28, 60));
		addSlotToContainer(new SlotBlockedInventory(entity, 2, 35, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 3, 53, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 4, 71, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 5, 89, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 6, 107, 34));
		addSlotToContainer(new SlotBlockedInventory(entity, 7, 125, 34));
		addInventory(inventory, 8, 84);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return analysingChamberTransfer.transferStackInSlot(this, entity, player, slotID);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUseableByPlayer(player);
	}
}
