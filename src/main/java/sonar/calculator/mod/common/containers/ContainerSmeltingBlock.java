package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.core.inventory.containers.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerSmeltingBlock extends ContainerSync {

	public static TransferSlotsManager<TileEntityAbstractProcess> TRANSFER = new TransferSlotsManager<>(3);
	private TileEntityAbstractProcess entity;

	public ContainerSmeltingBlock(InventoryPlayer inventory, TileEntityAbstractProcess entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new SlotSonarFiltered(entity, 0, 53, 24));
		addSlotToContainer(new SlotSonarFiltered(entity, 1, 28, 60));
		addSlotToContainer(new SlotSonarFiltered(entity, 2, 107, 24));
		addInventory(inventory, 8, 84);
	}
	
	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
