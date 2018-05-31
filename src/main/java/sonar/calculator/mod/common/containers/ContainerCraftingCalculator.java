package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import sonar.calculator.mod.Calculator;
import sonar.core.common.item.InventoryItem;
import sonar.core.inventory.containers.ContainerSonar;
import sonar.core.inventory.slots.InventoryStoredCrafting;
import sonar.core.inventory.slots.InventoryStoredResult;
import sonar.core.inventory.TransferSlotsManager;

import javax.annotation.Nonnull;

public class ContainerCraftingCalculator extends ContainerSonar{
	private final InventoryItem inventory;
	public static TransferSlotsManager<InventoryItem> transfer = new TransferSlotsManager(10);

	public InventoryStoredCrafting craftMatrix;
	public InventoryStoredResult craftResult;
	public EntityPlayer player;

	public ContainerCraftingCalculator(EntityPlayer player, InventoryItem inventoryItem) {
		this.inventory = inventoryItem;
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
        this.craftResult.setInventorySlotContents(0, CraftingManager.findMatchingResult(this.craftMatrix, player.getEntityWorld()));//Was getInstance()findMatchingRecipe
	}
	
    @Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return transfer.transferStackInSlot(this, inventory, player, slotID);
	}

	@Nonnull
    @Override
    public ItemStack slotClick(int slot, int drag, ClickType click, EntityPlayer player){
		if (slot >= 0 && getSlot(slot).getStack() == player.getHeldItemMainhand()) {
			return ItemStack.EMPTY;
		}
		return super.slotClick(slot, drag, click, player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUsableByPlayer(player);
	}
}
