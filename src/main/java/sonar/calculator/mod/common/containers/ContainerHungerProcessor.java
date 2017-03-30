package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.HealthProcessorRecipes;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHealthProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHungerProcessor;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.TransferSlotsManager.TransferSlots;
import sonar.core.inventory.TransferSlotsManager.TransferType;

public class ContainerHungerProcessor extends ContainerSync {
	private TileEntityHungerProcessor entity;
	public static TransferSlotsManager<TileEntityHungerProcessor> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<TileEntityHungerProcessor>(TransferType.TILE_INV, 1) {
				public boolean canInsert(EntityPlayer player, TileEntityHungerProcessor inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return stack.getItem() instanceof ItemFood;
				}
			});
			addTransferSlot(new TransferSlots<TileEntityHungerProcessor>(TransferType.TILE_INV, 1) {
				public boolean canInsert(EntityPlayer player, TileEntityHungerProcessor inv, Slot slot, int pos, int slotID, ItemStack stack) {
					return stack.getItem() == Calculator.itemHungerModule || stack.getItem() == Calculator.itemNutritionModule;
				}
			});
			addPlayerInventory();
		}
	};

	public ContainerHungerProcessor(InventoryPlayer inventory, TileEntityHungerProcessor entity) {
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
		return entity.isUseableByPlayer(player);
	}
}
