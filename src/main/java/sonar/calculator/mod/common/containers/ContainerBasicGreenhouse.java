package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityBasicGreenhouse;
import sonar.core.handlers.inventories.TransferSlotsManager;
import sonar.core.handlers.inventories.containers.ContainerSync;
import sonar.core.handlers.inventories.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerBasicGreenhouse extends ContainerSync {

	public static TransferSlotsManager<TileEntityBasicGreenhouse> TRANSFER = new TransferSlotsManager<TileEntityBasicGreenhouse>(14);
	private TileEntityBasicGreenhouse entity;

	public ContainerBasicGreenhouse(InventoryPlayer inventory, TileEntityBasicGreenhouse entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new SlotSonarFiltered(entity, 0, 26, 22));
		addSlotToContainer(new SlotSonarFiltered(entity, 1, 44, 22));
		addSlotToContainer(new SlotSonarFiltered(entity, 2, 26, 40));
		addSlotToContainer(new SlotSonarFiltered(entity, 3, 44, 40));
		addSlotToContainer(new SlotSonarFiltered(entity, 4, 80, 61));
		for (int j = 0; j < 9; j++) {
			addSlotToContainer(new SlotSonarFiltered(entity, 5 + j, 8 + j * 18, 88));
		}
		addInventory(inventory, 8, 110);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
