package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;
import sonar.core.inventory.containers.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerAnalysingChamber extends ContainerSync {

	public static TransferSlotsManager<TileEntityAnalysingChamber> TRANSFER = new TransferSlotsManager<>(8);
	private TileEntityAnalysingChamber entity;
	
	public ContainerAnalysingChamber(InventoryPlayer inventory, TileEntityAnalysingChamber entity) {
		super(entity);
		this.entity = entity;

		addSlotToContainer(new SlotSonarFiltered(entity, 0, 16, 6));
		addSlotToContainer(new SlotSonarFiltered(entity, 1, 28, 60));
		addSlotToContainer(new SlotSonarFiltered(entity, 2, 35, 34));
		addSlotToContainer(new SlotSonarFiltered(entity, 3, 53, 34));
		addSlotToContainer(new SlotSonarFiltered(entity, 4, 71, 34));
		addSlotToContainer(new SlotSonarFiltered(entity, 5, 89, 34));
		addSlotToContainer(new SlotSonarFiltered(entity, 6, 107, 34));
		addSlotToContainer(new SlotSonarFiltered(entity, 7, 125, 34));
		addInventory(inventory, 8, 84);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
