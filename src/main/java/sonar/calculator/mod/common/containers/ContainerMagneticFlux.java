package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.misc.TileEntityMagneticFlux;
import sonar.core.inventory.containers.ContainerSonar;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.slots.SlotList;

import javax.annotation.Nonnull;

public class ContainerMagneticFlux extends ContainerSonar {

	public static TransferSlotsManager<TileEntityMagneticFlux> TRANSFER = new TransferSlotsManager<>(7);
	public TileEntityMagneticFlux flux;

	public ContainerMagneticFlux(InventoryPlayer inv, TileEntityMagneticFlux flux) {
		this.flux = flux;
		for (int i = 0; i < 7; i++) {
            addSlotToContainer(new SlotList(flux, i, 26 + 18 * i, 61));
		}
		this.addInventory(inv, 8, 84);
	}

	@Override
	public boolean canInteractWith(@Nonnull EntityPlayer player) {
		return true;
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, flux, player, slotID);
	}

    @Nonnull
    @Override
	public ItemStack slotClick(int slot, int drag, ClickType click, EntityPlayer player) {
        Slot targetSlot = slot < 0 ? null : this.inventorySlots.get(slot);
        if (targetSlot instanceof SlotList) {
			if (click == ClickType.SWAP) {
				targetSlot.putStack(ItemStack.EMPTY);
			} else {;
                targetSlot.putStack(player.inventory.getItemStack().isEmpty() ? ItemStack.EMPTY : player.inventory.getItemStack().copy());
			}
			return player.inventory.getItemStack();
		}
		return super.slotClick(slot, drag, click, player);
	}
}