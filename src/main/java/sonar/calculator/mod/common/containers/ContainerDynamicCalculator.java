package sonar.calculator.mod.common.containers;

import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.core.inventory.InventoryStoredCrafting;
import sonar.core.inventory.InventoryStoredResult;

public class ContainerDynamicCalculator extends Container {

	private static final int INV_START = 10, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	public InventoryStoredCrafting calculatorMatrix;
	public InventoryStoredResult calculatorResult;
	public InventoryStoredCrafting scientficMatrix;
	public InventoryStoredResult scientificResult;
	public InventoryStoredCrafting atomicMatrix;
	public InventoryStoredResult atomicResult;
	public EntityPlayer player;
	public TileEntityCalculator.Dynamic entity;

	public ContainerDynamicCalculator(EntityPlayer player, TileEntityCalculator.Dynamic entity) {
		this.entity = entity;
		this.player = player;

		this.calculatorMatrix = new InventoryStoredCrafting(this, 2, 1, entity);
		this.calculatorResult = new InventoryStoredResult(entity);
		this.scientficMatrix = new InventoryStoredCrafting(this, 2, 1, entity, 3);
		this.scientificResult = new InventoryStoredResult(entity, 3);
		this.atomicMatrix = new InventoryStoredCrafting(this, 3, 1, entity, 6);
		this.atomicResult = new InventoryStoredResult(entity, 6);

		addSlotToContainer(new SlotCrafting(player, this.calculatorMatrix, this.calculatorResult, 0, 134, 9));
		addSlotToContainer(new Slot(this.calculatorMatrix, 0, 25 + 0 * 54, 9));
		addSlotToContainer(new Slot(this.calculatorMatrix, 1, 25 + 1 * 54, 9));

		addSlotToContainer(new SlotCrafting(player, this.scientficMatrix, this.scientificResult, 0, 134, 35));
		addSlotToContainer(new Slot(this.scientficMatrix, 0, 25 + 0 * 54, 35));
		addSlotToContainer(new Slot(this.scientficMatrix, 1, 25 + 1 * 54, 35));

		addSlotToContainer(new SlotCrafting(player, this.atomicMatrix, this.atomicResult, 0, 134, 61));
		addSlotToContainer(new Slot(this.atomicMatrix, 0, 20 + 0 * 32, 61));
		addSlotToContainer(new Slot(this.atomicMatrix, 1, 20 + 1 * 32, 61));
		addSlotToContainer(new Slot(this.atomicMatrix, 2, 20 + 2 * 32, 61));

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
		this.calculatorResult.setInventorySlotContents(0, RecipeRegistry.CalculatorRecipes.instance().getCraftingResult(calculatorMatrix.getStackInSlot(0), calculatorMatrix.getStackInSlot(1)));
		this.scientificResult.setInventorySlotContents(0, RecipeRegistry.ScientificRecipes.instance().getCraftingResult(scientficMatrix.getStackInSlot(0), scientficMatrix.getStackInSlot(1)));
		this.atomicResult.setInventorySlotContents(0, RecipeRegistry.AtomicRecipes.instance().getCraftingResult(atomicMatrix.getStackInSlot(0), atomicMatrix.getStackInSlot(1), atomicMatrix.getStackInSlot(2)));

	}

	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 < INV_START) {
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else {
				int current = this.getCurrentUsage();
				if (par2 >= INV_START && (current == 0 || current == 1)) {
					if (!this.mergeItemStack(itemstack1, 1, 3, false)) {
						return null;
					}
				} else if (par2 >= INV_START && current == 2) {
					if (!this.mergeItemStack(itemstack1, 4, 6, false)) {
						return null;
					}
				} else if (par2 >= INV_START && current == 3) {
					if (!this.mergeItemStack(itemstack1, 7, 10, false)) {
						return null;

					}
				}

				else if (par2 >= INV_START && par2 < HOTBAR_START) {
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END, false)) {
						return null;
					}
				} else if (par2 >= HOTBAR_START && par2 < HOTBAR_END) {
					if (!this.mergeItemStack(itemstack1, INV_START, INV_END, false)) {
						return null;
					}
				}
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

	public int getCurrentUsage() {
		if (((Slot) this.inventorySlots.get(1)).getHasStack() || ((Slot) this.inventorySlots.get(2)).getHasStack()) {
			return 1;
		} else if (((Slot) this.inventorySlots.get(4)).getHasStack() || ((Slot) this.inventorySlots.get(5)).getHasStack()) {
			return 2;
		} else if (((Slot) this.inventorySlots.get(7)).getHasStack() || ((Slot) this.inventorySlots.get(8)).getHasStack() || ((Slot) this.inventorySlots.get(9)).getHasStack()) {
			return 3;
		}
		return 0;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
