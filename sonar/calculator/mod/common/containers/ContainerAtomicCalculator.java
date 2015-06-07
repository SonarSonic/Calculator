package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.recipes.crafting.AtomicCalculatorCraftingManager;
import sonar.calculator.mod.common.recipes.machines.AtomicCalculatorRecipes;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.calculator.mod.integration.nei.AtomicCalculatorNEIRecipes;
import sonar.core.client.gui.InventoryStoredCrafting;
import sonar.core.client.gui.InventoryStoredResult;

public class ContainerAtomicCalculator extends Container {
	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	public TileEntityCalculator.Atomic entity;
	
	public ContainerAtomicCalculator(EntityPlayer player,TileEntityCalculator.Atomic entity) {
		this.entity = entity;
		this.craftMatrix = new InventoryStoredCrafting(this, 3, 1, entity);
		this.craftResult = new InventoryStoredResult(entity);

		addSlotToContainer(new SlotCrafting(player, this.craftMatrix,	this.craftResult, 0, 134, 35));
		for (int i = 0; i < 1; i++) {
			for (int k = 0; k < 3; k++) {
				addSlotToContainer(new Slot(this.craftMatrix, k + i * 2,
						20 + k * 32, 35 + i * 18));
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(player.inventory, k + i * 9 + 9,
						8 + k * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}

		onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory iiventory) {
		this.craftResult.setInventorySlotContents(0,AtomicCalculatorCraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.entity.getWorldObj()));

	}

	 @Override
	public boolean canInteractWith(EntityPlayer player) {
		    return true;
		  }

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotID == 0) {
				if (!mergeItemStack(itemstack1, 4, 40, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (slotID != 1 && slotID != 2 && slotID != 3) {
				if (AtomicCalculatorNEIRecipes.smelting().getSmeltingInput(itemstack1) != null) {
					if (!mergeItemStack(itemstack1, 1, 2, true)) {
						return null;
					}
				} else if (AtomicCalculatorNEIRecipes.smelting().getSmeltingInput2(
						itemstack1) != null) {
					if (!mergeItemStack(itemstack1, 2, 3, true)) {
						return null;
					}
				} else if (AtomicCalculatorNEIRecipes.smelting().getSmeltingInput3(
						itemstack1) != null) {
					if (!mergeItemStack(itemstack1, 3, 4, true)) {
						return null;
					}
				}
			} else if ((slotID >= 4) && (slotID < 38)) {
				if (!mergeItemStack(itemstack1, 38, 40, false)) {
					return null;
				}
			} else if ((slotID >= 38) && (slotID < 40)) {
				if (!mergeItemStack(itemstack1, 4, 38, false)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 4, 40, false)) {
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

			slot.onPickupFromSlot(entityplayer, itemstack);
		}

		return itemstack;
	}
}
