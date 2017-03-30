package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.ConductorMastRecipes;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.core.common.item.InventoryItem;
import sonar.core.inventory.ContainerSonar;
import sonar.core.inventory.InventoryStoredCrafting;
import sonar.core.inventory.InventoryStoredResult;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.TransferSlotsManager.DisabledSlots;
import sonar.core.inventory.TransferSlotsManager.TransferType;

public class ContainerCraftingCalculator extends ContainerSonar{
	private final InventoryItem inventory;
	public static TransferSlotsManager<InventoryItem> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<InventoryItem>(TransferType.TILE_INV, 9));
			addTransferSlot(new DisabledSlots<InventoryItem>(TransferType.TILE_INV, 1));
			addPlayerInventory();
		}
	};
	private boolean isRemote;

	public InventoryStoredCrafting craftMatrix;
	public InventoryStoredResult craftResult;
	public EntityPlayer player;

	public ContainerCraftingCalculator(EntityPlayer player, InventoryItem inventoryItem) {
		this.inventory = inventoryItem;
		isRemote = player.getEntityWorld().isRemote;
		craftMatrix = new InventoryStoredCrafting(this, 3, 3, inventory);
		craftResult = new InventoryStoredResult(inventory);
		this.player = player;

		addSlotToContainer(new SlotCrafting(player, this.craftMatrix, this.craftResult, 0, 124, 35));

		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 3; k++) {
				addSlotToContainer(new Slot(this.craftMatrix, k + i * 3, 30 + k * 18, 17 + i * 18));
			}
		}
		addInventoryWithLimiter(player.inventory, 8, 84, Calculator.itemCraftingCalculator);
		onCraftMatrixChanged(null);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inv) {
		this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, player.getEntityWorld()));
	}
	
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return transfer.transferStackInSlot(this, inventory, player, slotID);
	}

	@Override
    public ItemStack slotClick(int slot, int drag, ClickType click, EntityPlayer player){
		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItemMainhand()) {
			return ItemStack.EMPTY;
		}
		return super.slotClick(slot, drag, click, player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
	}


}
