package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHealthProcessor;
import sonar.core.handlers.inventories.TransferSlotsManager;
import sonar.core.handlers.inventories.containers.ContainerSync;

import javax.annotation.Nonnull;

public class ContainerHealthProcessor extends ContainerSync {

	public static TransferSlotsManager<TileEntityHealthProcessor> TRANSFER = new TransferSlotsManager<>(2);
	private TileEntityHealthProcessor entity;
	
	public ContainerHealthProcessor(InventoryPlayer inventory, TileEntityHealthProcessor entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new Slot(entity, 0, 55, 34));
		addSlotToContainer(new Slot(entity, 1, 104, 35));
		addInventory(inventory, 8, 84);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
