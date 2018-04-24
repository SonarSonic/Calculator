package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.ScientificRecipes;
import sonar.calculator.mod.utils.SlotPortableCrafting;
import sonar.calculator.mod.utils.SlotPortableResult;
import sonar.core.api.SonarAPI;
import sonar.core.api.utils.ActionType;
import sonar.core.common.item.InventoryItem;
import sonar.core.inventory.ContainerSonar;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.recipes.RecipeHelperV2;

import javax.annotation.Nonnull;

public class ContainerScientificCalculator extends ContainerSonar implements ICalculatorCrafter {
	private final InventoryItem inventory;
	public static TransferSlotsManager<InventoryItem> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<InventoryItem>(TransferType.TILE_INV, 2));
			addTransferSlot(new DisabledSlots<InventoryItem>(TransferType.TILE_INV, 1));
			addPlayerInventory();
		}
	};
	private EntityPlayer player;

	public ContainerScientificCalculator(EntityPlayer player, InventoryItem inventoryItem) {
		this.inventory = inventoryItem;
		this.player = player;

		addSlotToContainer(new SlotPortableCrafting(this, inventory, 0, 25, 35, Calculator.itemScientificCalculator));
		addSlotToContainer(new SlotPortableCrafting(this, inventory, 1, 79, 35, Calculator.itemScientificCalculator));
		addSlotToContainer(new SlotPortableResult(player, inventory, this, new int[] { 0, 1 }, 2, 134, 35));
		addInventoryWithLimiter(player.inventory, 8, 84, Calculator.itemScientificCalculator);
		onItemCrafted();
	}

	@Override
	public void onItemCrafted() {
		inventory.setInventorySlotContents(2, RecipeHelperV2.getItemStackFromList(ScientificRecipes.instance().getOutputs(player, inventory.getStackInSlot(0), inventory.getStackInSlot(1)), 0));
	}

    @Override
	public void removeEnergy(int remove) {
		if (!player.getEntityWorld().isRemote) {
			if (player.capabilities.isCreativeMode) {
				return;
			}
			SonarAPI.getEnergyHelper().extractEnergy(player.getHeldItemMainhand(), remove, ActionType.PERFORM);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUsableByPlayer(player);
	}

    @Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return transfer.transferStackInSlot(this, inventory, player, slotID);
	}

	@Nonnull
    @Override
	public ItemStack slotClick(int slot, int drag, ClickType click, EntityPlayer player) {
		if (slot >= 0 && getSlot(slot).getStack() == player.getHeldItemMainhand()) {
			return ItemStack.EMPTY;
		}
		return super.slotClick(slot, drag, click, player);
	}
}
