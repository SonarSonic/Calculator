package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.client.gui.ContainerCraftInventory;
import sonar.core.common.item.InventoryItem;
import sonar.core.utils.SlotLimiter;

public class ContainerStorageModule extends ContainerCraftInventory
{
	public ContainerStorageModule(EntityPlayer player,InventoryPlayer inv, InventoryItem calc) {
		super(player,inv,calc);
        int i = 36;
        int j;
        int k;

        for (j = 0; j < 6; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new SlotLimiter(inventory, k + j * 9, 8 + k * 18, 18 + j * 18, Calculator.itemStorageModule));
            }
        }

        for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new SlotLimiter(inv, k + j * 9 + 9, 8 + k * 18, 104 + j * 18 + i, Calculator.itemStorageModule));
            }
        }

        for (j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new SlotLimiter(inv, j, 8 + j * 18, 162 + i, Calculator.itemStorageModule));
        }
    }

    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotID)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotID < 54)
            {
                if (!this.mergeItemStack(itemstack1, 54, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 54, false))
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
        }

        return itemstack;
    }

}