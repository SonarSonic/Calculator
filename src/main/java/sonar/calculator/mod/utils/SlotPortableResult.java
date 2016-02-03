package sonar.calculator.mod.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import sonar.calculator.mod.common.containers.ICalculatorCrafter;
import sonar.core.common.item.InventoryItem;

public class SlotPortableResult extends SlotPortable {
	private EntityPlayer thePlayer;
	private int amountCrafted;
	private int[] craftSlots;
	private ICalculatorCrafter container;

	public SlotPortableResult(EntityPlayer player, InventoryItem inv, ICalculatorCrafter container, int[] craftSlots, int index, int x, int y, boolean isRemote) {
		super(inv, index, x, y, isRemote);
		this.thePlayer = player;
		this.craftSlots = craftSlots;
		this.container = container;
	}

	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int size) {
		if (this.getHasStack()) {
			this.amountCrafted += Math.min(size, this.getStack().stackSize);
		}

		return super.decrStackSize(size);
	}

	@Override
	protected void onCrafting(ItemStack stack, int size) {
		this.amountCrafted += size;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		this.container.removeEnergy();
		for (int i = 0; i < this.craftSlots.length; ++i) {
			ItemStack itemstack1 = this.invItem.getStackInSlot(craftSlots[i]);

			if (itemstack1 != null) {
				this.decrIngredientSize(craftSlots[i], 1);
				if (itemstack1.getItem().hasContainerItem(itemstack1)) {
					ItemStack itemstack2 = itemstack1.getItem().getContainerItem(itemstack1);

					if (itemstack2 != null && itemstack2.isItemStackDamageable() && itemstack2.getItemDamage() > itemstack2.getMaxDamage()) {
						MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(thePlayer, itemstack2));
						continue;
					}

					if (!itemstack1.getItem().doesContainerItemLeaveCraftingGrid(itemstack1) || !this.thePlayer.inventory.addItemStackToInventory(itemstack2)) {
						if (this.invItem.getStackInSlot(craftSlots[i]) == null) {
							this.invItem.setInventorySlotContents(craftSlots[i], itemstack2, isRemote);
						} else {
							this.thePlayer.dropPlayerItemWithRandomChoice(itemstack2, false);
						}
					}
				}
			}
		}
	}

	public ItemStack decrIngredientSize(int slot, int size) {
		if (invItem.getStackInSlot(slot) != null) {
			ItemStack itemstack;

			if (invItem.getStackInSlot(slot).stackSize <= size) {
				itemstack = invItem.getStackInSlot(slot);
				invItem.setInventorySlotContents(slot, null, isRemote);
				container.onItemCrafted();
				return itemstack;
			} else {
				itemstack = invItem.getStackInSlot(slot).splitStack(size);

				if (invItem.getStackInSlot(slot).stackSize == 0) {
					invItem.setInventorySlotContents(slot, null, isRemote);
				}

				container.onItemCrafted();
				return itemstack;
			}
		} else {
			return null;
		}
	}

}