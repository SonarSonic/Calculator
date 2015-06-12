package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.crafting.FlawlessCalculatorCraftingManager;
import sonar.calculator.mod.integration.nei.FlawlessCalcNEIRecipes;
import sonar.core.client.gui.ContainerCraftInventory;
import sonar.core.client.gui.InventoryStoredCrafting;
import sonar.core.client.gui.InventoryStoredResult;
import sonar.core.common.item.InventoryItem;
import sonar.core.utils.SlotLimiter;

public class ContainerFlawlessCalculator extends ContainerCraftInventory {
	public final int width = 4;

	public InventoryCrafting craftMatrix;
	public IInventory craftResult;	
	
	public ContainerFlawlessCalculator(EntityPlayer player,
			InventoryPlayer inv, InventoryItem calc) {
		super(player, inv, calc);
		this.craftMatrix = new InventoryStoredCrafting(this, width, 1,inventory);
		this.craftResult = new InventoryStoredResult(inventory);

		addSlotToContainer(new SlotCrafting(player, this.craftMatrix,this.craftResult, 0, 145, 35));

		for (int k = 0; k < width; k++) {
			addSlotToContainer(new Slot(this.craftMatrix, k,17 + k * 32, 35));
		}
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new SlotLimiter(inv, k + i * 9 + 9, 8 + k * 18, 84 + i * 18, Calculator.itemFlawlessCalculator));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new SlotLimiter(inv, i, 8 + i * 18, 142, Calculator.itemFlawlessCalculator));
		}
		onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory iiventory) {
		this.craftResult.setInventorySlotContents(0,FlawlessCalculatorCraftingManager.getInstance().findMatchingRecipe(this.craftMatrix,this.player.worldObj));
	}

    public ItemStack transferStackInSlot(EntityPlayer player, int id)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(id);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (id == 0)
            {
                if (!this.mergeItemStack(itemstack1, 5, 39, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (id >= 10 && id < 37)
            {
                if (!this.mergeItemStack(itemstack1, 32, 41, false))
                {
                    return null;
                }
            }
            else if (id >= 32 && id < 41)
            {
                if (!this.mergeItemStack(itemstack1, 5, 32, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 5, 41, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
