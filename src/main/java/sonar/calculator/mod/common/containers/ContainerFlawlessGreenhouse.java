package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.core.handlers.inventories.TransferSlotsManager;
import sonar.core.handlers.inventories.containers.ContainerSync;
import sonar.core.handlers.inventories.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerFlawlessGreenhouse extends ContainerSync {

	public static TransferSlotsManager<TileEntityFlawlessGreenhouse> TRANSFER = new TransferSlotsManager<TileEntityFlawlessGreenhouse>(10);
	private TileEntityFlawlessGreenhouse entity;

	public ContainerFlawlessGreenhouse(InventoryPlayer inventory, TileEntityFlawlessGreenhouse entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new SlotSonarFiltered(entity, 0, 26, 61));
		for (int j = 0; j < 9; j++) {
			addSlotToContainer(new SlotSonarFiltered(entity, 1 + j, 8 + j * 18, 88));
		}
		addInventory(inventory, 8, 110);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
