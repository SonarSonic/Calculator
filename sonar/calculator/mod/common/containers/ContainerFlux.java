package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;

public class ContainerFlux extends ContainerSync {
	
	
	public ContainerFlux(InventoryPlayer player, TileEntityFlux entity) {
		super(entity);
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(player, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int slotID) {

		Slot slot = (Slot) this.inventorySlots.get(slotID);
		ItemStack itemstack = slot.getStack();
		return itemstack;
	}

}