package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHungerProcessor;
import sonar.core.inventory.containers.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

import javax.annotation.Nonnull;

public class ContainerHungerProcessor extends ContainerSync {

	public static TransferSlotsManager<TileEntityHungerProcessor> transfer = new TransferSlotsManager<>(2);
	private TileEntityHungerProcessor entity;

	public ContainerHungerProcessor(InventoryPlayer inventory, TileEntityHungerProcessor entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new Slot(entity, 0, 55, 34));
		addSlotToContainer(new Slot(entity, 1, 104, 35));
		addInventory(inventory, 8, 84);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return transfer.transferStackInSlot(this, entity, player, slotID);
	}
}
