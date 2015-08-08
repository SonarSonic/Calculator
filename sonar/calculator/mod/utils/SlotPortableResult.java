package sonar.calculator.mod.utils;

import sonar.calculator.mod.api.ICalculatorCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import cpw.mods.fml.common.FMLCommonHandler;

public class SlotPortableResult extends Slot {
	private EntityPlayer thePlayer;
	private int amountCrafted;
	private int[] craftSlots;
	private ICalculatorCrafter container;

	public SlotPortableResult(EntityPlayer player, IInventory inv, ICalculatorCrafter container, int[] craftSlots, int index, int x, int y) {
		super(inv, index, x, y);
		this.thePlayer = player;
		this.craftSlots=craftSlots;
		this.container=container;
	}

	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	public ItemStack decrStackSize(int size) {
		if (this.getHasStack()) {
			this.amountCrafted += Math.min(size, this.getStack().stackSize);
		}

		return super.decrStackSize(size);
	}

	protected void onCrafting(ItemStack stack, int size) {
		this.amountCrafted += size;
	}

	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		this.container.removeEnergy();
		for (int i = 0; i < this.craftSlots.length; ++i) {
			ItemStack itemstack1 = this.inventory.getStackInSlot(craftSlots[i]);

			if (itemstack1 != null) {
				this.decrIngredientSize(craftSlots[i], 1);
				if (itemstack1.getItem().hasContainerItem(itemstack1)) {
					ItemStack itemstack2 = itemstack1.getItem().getContainerItem(itemstack1);

					if (itemstack2 != null && itemstack2.isItemStackDamageable() && itemstack2.getItemDamage() > itemstack2.getMaxDamage()) {
						MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(thePlayer, itemstack2));
						continue;
					}

					if (!itemstack1.getItem().doesContainerItemLeaveCraftingGrid(itemstack1) || !this.thePlayer.inventory.addItemStackToInventory(itemstack2)) {
						if (this.inventory.getStackInSlot(craftSlots[i]) == null) {
							this.inventory.setInventorySlotContents(craftSlots[i], itemstack2);
						} else {
							this.thePlayer.dropPlayerItemWithRandomChoice(itemstack2, false);
						}
					}
				}
			}
		}
	}
	
    public ItemStack decrIngredientSize(int slot, int size)
    {
        if (inventory.getStackInSlot(slot) != null)
        {
            ItemStack itemstack;

            if (inventory.getStackInSlot(slot).stackSize <= size)
            {
                itemstack = inventory.getStackInSlot(slot);
                inventory.setInventorySlotContents(slot, null);
                container.onCraftMatrixChanged(null);
                return itemstack;
            }
            else
            {
                itemstack = inventory.getStackInSlot(slot).splitStack(size);
                
                if (inventory.getStackInSlot(slot).stackSize == 0)
                {
                	inventory.setInventorySlotContents(slot, null);
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
}