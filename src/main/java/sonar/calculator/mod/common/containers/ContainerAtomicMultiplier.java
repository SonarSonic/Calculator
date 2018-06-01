package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.core.handlers.inventories.TransferSlotsManager;
import sonar.core.handlers.inventories.containers.ContainerSync;
import sonar.core.handlers.inventories.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerAtomicMultiplier extends ContainerSync {

	public static TransferSlotsManager<TileEntityAtomicMultiplier> TRANSFER = new TransferSlotsManager<TileEntityAtomicMultiplier>(10);
	private TileEntityAtomicMultiplier entity;

	public ContainerAtomicMultiplier(InventoryPlayer inventory, TileEntityAtomicMultiplier entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new SlotSonarFiltered(entity, 0, 54, 16));
		addSlotToContainer(new SlotSonarFiltered(entity, 1, 26, 41));
		addSlotToContainer(new SlotSonarFiltered(entity, 2, 44, 41));
		addSlotToContainer(new SlotSonarFiltered(entity, 3, 62, 41));
		addSlotToContainer(new SlotSonarFiltered(entity, 4, 80, 41));
		addSlotToContainer(new SlotSonarFiltered(entity, 5, 98, 41));
		addSlotToContainer(new SlotSonarFiltered(entity, 6, 116, 41));
		addSlotToContainer(new SlotSonarFiltered(entity, 7, 134, 41));
		addSlotToContainer(new SlotSonarFiltered(entity, 8, 108, 16));
		addSlotToContainer(new SlotSonarFiltered(entity, 9, 20, 62));
		addInventory(inventory, 8, 84);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
