package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.core.client.gui.ContainerCraftInventory;
import sonar.core.client.gui.InventoryStoredCrafting;
import sonar.core.client.gui.InventoryStoredResult;
import sonar.core.common.item.InventoryItem;
import sonar.core.utils.SlotLimiter;

public class ContainerCraftingCalculator extends ContainerCraftInventory {

	public InventoryCrafting craftMatrix;
	public IInventory craftResult;	
	
	public ContainerCraftingCalculator(EntityPlayer player,InventoryPlayer inv, InventoryItem calc) {
		super(player,inv,calc);

		this.craftMatrix = new InventoryStoredCrafting(this, 3, 3, inventory);
    	this.craftResult = new InventoryStoredResult(inventory);
    	
		addSlotToContainer(new SlotCrafting(player, this.craftMatrix,
				this.craftResult, 0, 124, 35));
		
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 3; k++) {
				addSlotToContainer(new Slot(this.craftMatrix, k + i * 3,
						30 + k * 18, 17 + i * 18));
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new SlotLimiter(inv, k + i * 9 + 9,
						8 + k * 18, 84 + i * 18, Calculator.itemCraftingCalculator));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new SlotLimiter(inv, i, 8 + i * 18, 142, Calculator.itemCraftingCalculator));
		}
		onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory iiventory) {
		this.craftResult.setInventorySlotContents(0,CraftingManager.getInstance().findMatchingRecipe(
						this.craftMatrix, this.worldObj));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(id);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (id == 0)
            {
                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (id >= 10 && id < 37)
            {
                if (!this.mergeItemStack(itemstack1, 37, 46, false))
                {
                    return null;
                }
            }
            else if (id >= 37 && id < 46)
            {
                if (!this.mergeItemStack(itemstack1, 10, 37, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 10, 46, false))
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
	public boolean func_94530_a(ItemStack stack, Slot slot)
    {
        return slot.inventory != this.craftResult && super.func_94530_a(stack, slot);
    }
}
