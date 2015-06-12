package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.crafting.ScientificCalculatorRecipes;
import sonar.core.client.gui.ContainerCraftInventory;
import sonar.core.client.gui.InventoryStoredCrafting;
import sonar.core.client.gui.InventoryStoredResult;
import sonar.core.common.item.InventoryItem;
import sonar.core.utils.SlotLimiter;

public class ContainerScientificCalculator extends ContainerCraftInventory {

	public InventoryCrafting craftMatrix;
	public IInventory craftResult;

	public ContainerScientificCalculator(EntityPlayer player, InventoryPlayer inv, InventoryItem calc) {
		super(player, inv, calc);

		this.craftMatrix = new InventoryStoredCrafting(this, 2, 1, inventory);
		this.craftResult = new InventoryStoredResult(inventory);

		addSlotToContainer(new SlotCrafting(player, this.craftMatrix, this.craftResult, 0, 134, 35));

		for (int k = 0; k < 2; k++) {
			addSlotToContainer(new Slot(this.craftMatrix, k, 25 + k * 54, 35));
		}
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new SlotLimiter(inv, k + i * 9 + 9, 8 + k * 18, 84 + i * 18, Calculator.itemScientificCalculator));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new SlotLimiter(inv, i, 8 + i * 18, 142, Calculator.itemScientificCalculator));
		}
		onCraftMatrixChanged(inventory);
	}

	@Override
	public void onCraftMatrixChanged(IInventory iiventory) {
		craftResult.setInventorySlotContents(0, ScientificCalculatorRecipes.recipes().findMatchingRecipe(craftMatrix));

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (id == 0) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (id >= 3 && id < 30) {
				if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
					return null;
				}
			} else if (id >= 30 && id < 39) {
				if (!this.mergeItemStack(itemstack1, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}
		return itemstack;
	}

	@Override
	public boolean func_94530_a(ItemStack stack, Slot slot) {
		return slot.inventory != this.craftResult && super.func_94530_a(stack, slot);
	}
}
