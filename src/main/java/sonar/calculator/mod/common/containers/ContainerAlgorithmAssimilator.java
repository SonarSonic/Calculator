package sonar.calculator.mod.common.containers;

import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.core.utils.SlotBlockedInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAlgorithmAssimilator extends Container {
	private TileEntityAssimilator entity;

	public ContainerAlgorithmAssimilator(IInventory playerInv, TileEntityAssimilator entity) {
		this.entity = entity;
		entity.openInventory();
		int i = (3 - 4) * 18;
		int j;
		int k;

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlotToContainer(new SlotBlockedInventory(entity, k + j * 9, 8 + k * 18, 24 + j * 18));
			}
		}

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(playerInv, k + j * 9 + 9, 8 + k * 18, 102 + j * 18 + i));
			}
		}

		for (j = 0; j < 9; ++j) {
			this.addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 160 + i));
		}
	}

	public boolean canInteractWith(EntityPlayer player) {
		return this.entity.isUseableByPlayer(player);
	}

	 public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
	    {
	        ItemStack itemstack = null;
	        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

	        if (slot != null && slot.getHasStack())
	        {
	            ItemStack itemstack1 = slot.getStack();
	            itemstack = itemstack1.copy();

	            if (p_82846_2_ < 3 * 9)
	            {
	                if (!this.mergeItemStack(itemstack1, 3 * 9, this.inventorySlots.size(), true))
	                {
	                    return null;
	                }
	            }
	            else if (!this.mergeItemStack(itemstack1, 0, 3 * 9, false))
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

	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		this.entity.closeInventory();
	}

	public IInventory getLowerChestInventory() {
		return this.entity;
	}
}