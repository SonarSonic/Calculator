package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.core.inventory.containers.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerAssimilator extends ContainerSync {

	public static TransferSlotsManager<TileEntityAssimilator> TRANSFER = new TransferSlotsManager<>(1);
	private TileEntityAssimilator entity;

	public ContainerAssimilator(InventoryPlayer inventory, TileEntityAssimilator entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new SlotSonarFiltered(entity, 0, 80, 34));
		addInventory(inventory, 8, 84);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
