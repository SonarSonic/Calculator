package sonar.calculator.mod.common.containers;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import sonar.calculator.mod.common.tileentity.misc.TileEntityReinforcedChest;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.containers.ContainerSync;
import sonar.core.inventory.handling.ItemTransferHelper;
import sonar.core.inventory.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerReinforcedChest extends ContainerSync {
	public static TransferSlotsManager<TileEntityReinforcedChest> TRANSFER = new TransferSlotsManager<>(27);
	public TileEntityReinforcedChest entity;

	public ContainerReinforcedChest(EntityPlayer player, TileEntityReinforcedChest entity) {
		super(entity);
		this.entity = entity;
		this.entity.openInventory(player);
		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlotToContainer(new SlotChangeableStack(entity, k + j * 9, 8 + k * 18, 24 + j * 18, entity.isClient()));
			}
		}
		addInventory(player.inventory, 8, 84);
	}

	public boolean addToInventory(ItemStack stack){
		ItemStack inserted = ItemHandlerHelper.insertItemStacked(entity.inv, stack.copy(), false);
		if(inserted.getCount() != stack.getCount()){
			stack.shrink(stack.getCount() - inserted.getCount());
			detectAndSendChanges();
			return true;
		}
		return false;
	}

	public boolean removeToPlayer(EntityPlayer player, ItemStack stack, int targetSlot){
		int before = entity.inv.getStackInSlot(targetSlot).getCount();
		ItemTransferHelper.doTransferFromSlot(entity.inv, Lists.newArrayList(ItemTransferHelper.getMainInventoryHandler(player)), targetSlot);
		if(before != entity.inv.getStackInSlot(targetSlot).getCount()) {
			detectAndSendChanges();
			return true;
		}
		return false;
	}

	public boolean slotHasStack(Slot slot){
		return slot instanceof SlotSonarFiltered ? entity.inv.slots.get(slot.getSlotIndex()).getActualStored() > 0 : slot.getHasStack();
	}

	public ItemStack getRemovalStack(Slot slot){
		return slot instanceof SlotSonarFiltered ? entity.inv.slots.get(slot.getSlotIndex()).getLargeStack().getFullStack() : slot.getStack();
	}

	@Nonnull
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index){
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slotHasStack(slot)){
			ItemStack itemstack1 = getRemovalStack(slot);
			itemstack = itemstack1.copy();

			if (index < 27){
				this.removeToPlayer(player, itemstack1, index);

				return ItemStack.EMPTY;
			}
			else if (!this.addToInventory(itemstack1)){
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()){
				slot.putStack(ItemStack.EMPTY);
			}
			else{
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}

	public void onContainerClosed(EntityPlayer player){
		super.onContainerClosed(player);
		entity.closeInventory(player);
	}

	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player){
		Slot slot = slotId >= 0 && slotId < inventorySlots.size() ? inventorySlots.get(slotId) : null;
		if(slot instanceof SlotChangeableStack){
			if(clickTypeIn == ClickType.PICKUP || clickTypeIn == ClickType.PICKUP_ALL){
				if(slotHasStack(slot)) {
					//return super.slotClick(slotId, dragType, ClickType.QUICK_MOVE, player);
					return ItemStack.EMPTY;
				}
			}
		}
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}

}