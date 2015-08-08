package sonar.calculator.mod.utils;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPortableCrafting extends Slot {
	
	private Container container;
	private IInventory inv;
	
	public SlotPortableCrafting(Container container, IInventory inv, int index, int x, int y) {
		super(inv, index, x, y);
		this.inv=inv;
		this.container = container;
	}
	
    public ItemStack decrStackSize(int size)
    {
        if (inv.getStackInSlot(this.slotNumber) != null)
        {
            ItemStack itemstack;

            if (inv.getStackInSlot(this.slotNumber).stackSize <= size)
            {
                itemstack = inv.getStackInSlot(this.slotNumber);
                inv.setInventorySlotContents(this.slotNumber, null);
                container.onCraftMatrixChanged(null);
                return itemstack;
            }
            else
            {
                itemstack = inv.getStackInSlot(this.slotNumber).splitStack(size);
                
                if (inv.getStackInSlot(this.slotNumber).stackSize == 0)
                {
                    inv.setInventorySlotContents(this.slotNumber, null);
                }

                container.onCraftMatrixChanged(null);
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void putStack(ItemStack stack)
    {
        inv.setInventorySlotContents(this.slotNumber, stack);
        this.container.onCraftMatrixChanged(null);
    }
}
