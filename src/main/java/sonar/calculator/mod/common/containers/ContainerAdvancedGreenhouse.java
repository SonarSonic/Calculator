package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedGreenhouse;
import sonar.core.handlers.inventories.TransferSlotsManager;
import sonar.core.handlers.inventories.containers.ContainerSync;
import sonar.core.handlers.inventories.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerAdvancedGreenhouse extends ContainerSync {

	public static TransferSlotsManager<TileEntityAdvancedGreenhouse> TRANSFER = new TransferSlotsManager<>(17);
	private TileEntityAdvancedGreenhouse entity;

	public ContainerAdvancedGreenhouse(InventoryPlayer inventory, TileEntityAdvancedGreenhouse entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new SlotSonarFiltered(entity, 0, 35, 11));
		addSlotToContainer(new SlotSonarFiltered(entity, 1, 17, 29));
		addSlotToContainer(new SlotSonarFiltered(entity, 2, 35, 29));
		addSlotToContainer(new SlotSonarFiltered(entity, 3, 53, 29));
		addSlotToContainer(new SlotSonarFiltered(entity, 4, 17, 47));
		addSlotToContainer(new SlotSonarFiltered(entity, 5, 35, 47));
		addSlotToContainer(new SlotSonarFiltered(entity, 6, 53, 47));
		addSlotToContainer(new SlotSonarFiltered(entity, 7, 80, 61));
		for (int j = 0; j < 9; j++) {
			addSlotToContainer(new SlotSonarFiltered(entity, 8 + j, 8 + j * 18, 88));
		}
		addInventory(inventory, 8, 110);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
