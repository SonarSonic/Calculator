package sonar.calculator.mod.utils;

import sonar.calculator.mod.api.ICalculatorCrafter;
import sonar.core.common.item.InventoryItem;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPortableCrafting extends SlotPortable {

	private ICalculatorCrafter container;

	public SlotPortableCrafting(ICalculatorCrafter container, InventoryItem inv, int index, int x, int y, boolean isRemote) {
		super(inv, index, x, y, isRemote);
		this.container = container;
	}

	public ItemStack decrStackSize(int size) {
		if (invItem.getStackInSlot(this.slotNumber) != null) {
			ItemStack itemstack;

			if (invItem.getStackInSlot(this.slotNumber).stackSize <= size) {
				itemstack = invItem.getStackInSlot(this.slotNumber);
				invItem.setInventorySlotContents(this.slotNumber, null, isRemote);
				container.onItemCrafted();
				return itemstack;
			} else {
				itemstack = invItem.getStackInSlot(this.slotNumber).splitStack(size);

				if (invItem.getStackInSlot(this.slotNumber).stackSize == 0) {
					invItem.setInventorySlotContents(this.slotNumber, null, isRemote);
				}

				container.onItemCrafted();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public void putStack(ItemStack stack) {
		super.putStack(stack);
		container.onItemCrafted();

	}

}
