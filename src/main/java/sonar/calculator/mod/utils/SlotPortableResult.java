package sonar.calculator.mod.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import sonar.calculator.mod.common.containers.ICalculatorCrafter;

import javax.annotation.Nonnull;

public class SlotPortableResult extends SlotPortable {
	private EntityPlayer thePlayer;
	private int amountCrafted;
	private int[] craftSlots;
	private ICalculatorCrafter container;

	public SlotPortableResult(EntityPlayer player, IInventory inv, ICalculatorCrafter container, int[] craftSlots, int index, int x, int y) {
		super(inv, index, x, y, null);
		this.thePlayer = player;
		this.craftSlots = craftSlots;
		this.container = container;
	}

    @Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	@Nonnull
    @Override
	public ItemStack decrStackSize(int size) {
		if (this.getHasStack()) {
			this.amountCrafted += Math.min(size, this.getStack().getCount());
		}
		return super.decrStackSize(size);
	}

	@Override
	protected void onCrafting(ItemStack stack, int size) {
		this.amountCrafted += size;
	}

	@Nonnull
    @Override
	public ItemStack onTake(EntityPlayer player, @Nonnull ItemStack stack) {
		this.container.removeEnergy(amountCrafted);
		amountCrafted = 0;
        for (int craftSlot : this.craftSlots) {
            ItemStack itemstack1 = this.invItem.getStackInSlot(craftSlot);

			if (!itemstack1.isEmpty()) {
                decrIngredientSize(craftSlot, 1);
				if (itemstack1.getItem().hasContainerItem(itemstack1)) {
					ItemStack itemstack2 = itemstack1.getItem().getContainerItem(itemstack1);

					if (itemstack2.isItemStackDamageable() && itemstack2.getItemDamage() > itemstack2.getMaxDamage()) {
						MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(thePlayer, itemstack2, player.getActiveHand()));
						continue;
					}

					if (!this.thePlayer.inventory.addItemStackToInventory(itemstack2)) {
                        this.invItem.getStackInSlot(craftSlot);
                        this.thePlayer.dropItem(itemstack2, false);
                    }
				}
			}
		}
		return stack;
	}

	public ItemStack decrIngredientSize(int slot, int size) {
		ItemStack itemstack = invItem.getStackInSlot(slot);
		if(itemstack.isEmpty()){
			return ItemStack.EMPTY;
		}

        if (invItem.getStackInSlot(slot).getCount() <= size) {
            invItem.setInventorySlotContents(slot, ItemStack.EMPTY);
            container.onItemCrafted();
            return itemstack;
        } else {
            itemstack = invItem.getStackInSlot(slot).splitStack(size);
            container.onItemCrafted();
            return itemstack;
        }
    }
}