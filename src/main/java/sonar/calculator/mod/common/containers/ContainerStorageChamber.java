package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.core.handlers.inventories.TransferSlotsManager;
import sonar.core.handlers.inventories.containers.ContainerSync;
import sonar.core.handlers.inventories.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerStorageChamber extends ContainerSync {
	public static TransferSlotsManager<TileEntityStorageChamber> TRANSFER = new TransferSlotsManager<>(14);
	private TileEntityStorageChamber entity;

	public ContainerStorageChamber(EntityPlayer player, TileEntityStorageChamber entity) {
		super(entity);
		this.entity = entity;
		
		addSlotToContainer(new SlotSonarFiltered(entity, 0, 26, 6 + 17));
		addSlotToContainer(new SlotSonarFiltered(entity, 1, 62, 6 + 17));
		addSlotToContainer(new SlotSonarFiltered(entity, 2, 98, 6 + 17));
		addSlotToContainer(new SlotSonarFiltered(entity, 3, 134, 6 + 17));
		addSlotToContainer(new SlotSonarFiltered(entity, 4, 8, 32 + 17));
		addSlotToContainer(new SlotSonarFiltered(entity, 5, 44, 32 + 17));
		addSlotToContainer(new SlotSonarFiltered(entity, 6, 80, 32 + 17));
		addSlotToContainer(new SlotSonarFiltered(entity, 7, 116, 32 + 17));
		addSlotToContainer(new SlotSonarFiltered(entity, 8, 152, 32 + 17));
		addSlotToContainer(new SlotSonarFiltered(entity, 9, 8, 58 + 17));
		addSlotToContainer(new SlotSonarFiltered(entity, 10, 44, 58 + 17));
		addSlotToContainer(new SlotSonarFiltered(entity, 11, 80, 58 + 17));
		addSlotToContainer(new SlotSonarFiltered(entity, 12, 116, 58 + 17));
		addSlotToContainer(new SlotSonarFiltered(entity, 13, 152, 58 + 17));
		addInventory(player.inventory, 8, 101);
	}


	@Nonnull
	@Override
	public void detectAndSendChanges(){
		for (int i = 14; i < this.inventorySlots.size(); ++i)
		{
			ItemStack itemstack = ((Slot)this.inventorySlots.get(i)).getStack();
			ItemStack itemstack1 = this.inventoryItemStacks.get(i);

			if (!ItemStack.areItemStacksEqual(itemstack1, itemstack))
			{
				boolean clientStackChanged = !ItemStack.areItemStacksEqualUsingNBTShareTag(itemstack1, itemstack);
				itemstack1 = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
				this.inventoryItemStacks.set(i, itemstack1);

				if (clientStackChanged)
					for (int j = 0; j < this.listeners.size(); ++j)
					{
						((IContainerListener)this.listeners.get(j)).sendSlotContents(this, i, itemstack1);
					}
			}
		}
	}

	@Nonnull
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}