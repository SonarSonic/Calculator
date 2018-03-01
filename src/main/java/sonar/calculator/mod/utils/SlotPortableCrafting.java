package sonar.calculator.mod.utils;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.containers.ICalculatorCrafter;
import sonar.core.utils.SonarCompat;

public class SlotPortableCrafting extends SlotPortable {

	private ICalculatorCrafter container;

	public SlotPortableCrafting(ICalculatorCrafter container, IInventory inv, int index, int x, int y, Item type) {
		super(inv, index, x, y, type);
		this.container = container;
	}

    @Override
	public ItemStack decrStackSize(int size) {
		if (!SonarCompat.isEmpty(invItem.getStackInSlot(this.slotNumber))) {
			ItemStack itemstack;
			if (SonarCompat.getCount(invItem.getStackInSlot(this.slotNumber)) <= size) {
				itemstack = invItem.getStackInSlot(this.slotNumber);
				invItem.setInventorySlotContents(this.slotNumber, SonarCompat.getEmpty());
				container.onItemCrafted();
				return itemstack;
			} else {
				itemstack = invItem.getStackInSlot(this.slotNumber).splitStack(size);
				container.onItemCrafted();
				return itemstack;
			}
		} else {
			return SonarCompat.getEmpty();
		}
	}

	@Override
	public void putStack(ItemStack stack) {
		super.putStack(stack);
		container.onItemCrafted();
	}
}
