package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.core.handlers.inventories.TransferSlotsManager;
import sonar.core.handlers.inventories.containers.ContainerSync;
import sonar.core.handlers.inventories.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerAlgorithmAssimilator extends ContainerSync {

	public static TransferSlotsManager<TileEntityAssimilator> TRANSFER = new TransferSlotsManager<>(27);
	private TileEntityAssimilator entity;

	public ContainerAlgorithmAssimilator(EntityPlayer player, TileEntityAssimilator entity) {
		super(entity);
		this.entity = entity;
		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlotToContainer(new SlotSonarFiltered(entity, k + j * 9, 8 + k * 18, 24 + j * 18));
			}
		}
		addInventory(player.inventory, 8,84);
	}

    @Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
