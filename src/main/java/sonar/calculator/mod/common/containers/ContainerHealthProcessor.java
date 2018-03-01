package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.HealthProcessorRecipes;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHealthProcessor;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

public class ContainerHealthProcessor extends ContainerSync {
	private TileEntityHealthProcessor entity;
	public static TransferSlotsManager<TileEntityHealthProcessor> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<TileEntityHealthProcessor>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityHealthProcessor inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return HealthProcessorRecipes.instance().getValue(player, stack) > 0;
				}
			});
			addTransferSlot(new TransferSlots<TileEntityHealthProcessor>(TransferType.TILE_INV, 1) {
                @Override
				public boolean canInsert(EntityPlayer player, TileEntityHealthProcessor inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return stack.getItem() == Calculator.itemHealthModule || stack.getItem() == Calculator.itemNutritionModule;
				}
			});
			addPlayerInventory();
		}
	};
	
	public ContainerHealthProcessor(InventoryPlayer inventory, TileEntityHealthProcessor entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new Slot(entity, 0, 55, 34));
		addSlotToContainer(new Slot(entity, 1, 104, 35));
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
