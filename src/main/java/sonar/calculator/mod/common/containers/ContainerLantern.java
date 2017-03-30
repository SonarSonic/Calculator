package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.calculator.mod.common.tileentity.misc.TileEntityGasLantern;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.TransferSlotsManager.TransferSlots;
import sonar.core.inventory.TransferSlotsManager.TransferType;

public class ContainerLantern extends ContainerSync {
	private TileEntityGasLantern entity;
	public static TransferSlotsManager<TileEntityGasLantern> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<TileEntityGasLantern>(TransferType.TILE_INV, 1) {
				public boolean canInsert(EntityPlayer player, TileEntityGasLantern inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return TileEntityFurnace.getItemBurnTime(stack) > 0;
				}
			});
			addPlayerInventory();
		}
	};
	public ContainerLantern(InventoryPlayer inventory, TileEntityGasLantern entity) {
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
