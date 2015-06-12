package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.recipes.crafting.AtomicCalculatorCraftingManager;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipes;
import sonar.calculator.mod.common.recipes.crafting.ScientificCalculatorRecipes;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.core.client.gui.InventoryStoredCrafting;
import sonar.core.client.gui.InventoryStoredResult;

public class ContainerDynamicCalculator extends Container {

	public InventoryCrafting calculatorMatrix;
	public IInventory calculatorResult;
	public InventoryCrafting scientficMatrix;
	public IInventory scientificResult;
	public InventoryCrafting atomicMatrix;
	public IInventory atomicResult;
	public EntityPlayer player;
	public TileEntityCalculator.Dynamic entity;

	public ContainerDynamicCalculator(EntityPlayer player, TileEntityCalculator.Dynamic entity) {
		this.entity = entity;
		this.player = player;

		this.calculatorMatrix = new InventoryStoredCrafting(this, 2, 1, entity);
		this.calculatorResult = new InventoryStoredResult(entity);
		this.scientficMatrix = new InventoryStoredCrafting(this, 2, 1, entity, 3, 2);
		this.scientificResult = new InventoryStoredResult(entity, 3);
		this.atomicMatrix = new InventoryStoredCrafting(this, 3, 1, entity, 6, 3);
		this.atomicResult = new InventoryStoredResult(entity, 6);

		addSlotToContainer(new SlotCrafting(player, this.calculatorMatrix, this.calculatorResult, 0, 134, 9));
		addSlotToContainer(new SlotCrafting(player, this.scientficMatrix, this.scientificResult, 3, 134, 35));
		addSlotToContainer(new SlotCrafting(player, this.atomicMatrix, this.atomicResult, 6, 134, 61));

		for (int k = 0; k < 2; k++) {
			addSlotToContainer(new Slot(this.calculatorMatrix, k, 25 + k * 54, 9));
		}
		for (int k = 0; k < 2; k++) {
			addSlotToContainer(new Slot(this.scientficMatrix, k, 25 + k * 54, 35));
		}

		for (int k = 0; k < 3; k++) {
			addSlotToContainer(new Slot(this.atomicMatrix, k, 20 + k * 32, 61));
		}

		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(player.inventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}

		onCraftMatrixChanged(this.calculatorResult);
	}

	@Override
	public void onCraftMatrixChanged(IInventory iiventory) {

		this.calculatorResult.setInventorySlotContents(0, CalculatorRecipes.recipes().findMatchingRecipe(calculatorMatrix, entity.getUnblocked()));
		this.scientificResult.setInventorySlotContents(3, ScientificCalculatorRecipes.recipes().findMatchingRecipe(scientficMatrix));
		this.atomicResult.setInventorySlotContents(6, AtomicCalculatorCraftingManager.getInstance().findMatchingRecipe(this.atomicMatrix, this.entity.getWorldObj()));

	}

	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (id == 0 || id == 3 || id == 6) {
				if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (id >= 10 && id < 37) {
				if (!this.mergeItemStack(itemstack1, 37, 46, false)) {
					return null;
				}
			} else if (id >= 37 && id < 46) {
				if (!this.mergeItemStack(itemstack1, 10, 37, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
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

	public int currentUsage() {

		Slot slot = (Slot) this.inventorySlots.get(3);
		Slot slot2 = (Slot) this.inventorySlots.get(4);
		Slot slot3 = (Slot) this.inventorySlots.get(5);
		Slot slot4 = (Slot) this.inventorySlots.get(6);
		Slot slot5 = (Slot) this.inventorySlots.get(7);
		Slot slot6 = (Slot) this.inventorySlots.get(8);
		Slot slot7 = (Slot) this.inventorySlots.get(9);

		if (slot.getHasStack()) {
			return 1;
		} else if (slot2.getHasStack()) {
			return 1;
		} else if (slot3.getHasStack()) {
			return 2;
		} else if (slot4.getHasStack()) {
			return 2;
		} else if (slot5.getHasStack()) {
			return 3;
		} else if (slot6.getHasStack()) {
			return 3;
		} else if (slot7.getHasStack()) {
			return 3;
		}
		return 0;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
