package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.core.inventory.containers.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerConductorMast extends ContainerSync {

	public static TransferSlotsManager<TileEntityConductorMast> TRANSFER = new TransferSlotsManager<>(2);
	private TileEntityConductorMast entity;

	public ContainerConductorMast(InventoryPlayer inventory, TileEntityConductorMast entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new SlotSonarFiltered(entity, 0, 54, 22));
		addSlotToContainer(new SlotSonarFiltered(entity, 1, 108, 22));
		addInventory(inventory, 8, 84);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
