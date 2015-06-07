package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerInfoCalculator extends Container {

	public ContainerInfoCalculator(EntityPlayer player,
			InventoryPlayer invPlayer, World world, int x, int y, int z) {

		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(invPlayer, k + i * 9 + 9,
						8 + k * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int slotID) {
		
		Slot slot = (Slot) this.inventorySlots.get(slotID);
		ItemStack itemstack = slot.getStack();
		return itemstack;
	}
	public ItemStack slotClick(int slot, int button, int par, EntityPlayer player){
		return null;
	}
}