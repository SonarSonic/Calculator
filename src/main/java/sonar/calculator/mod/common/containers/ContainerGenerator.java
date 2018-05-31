package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.core.inventory.containers.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerGenerator extends ContainerSync {

	public static TransferSlotsManager<TileEntityGenerator> TRANSFER = new TransferSlotsManager<>(2);
	private TileEntityGenerator entity;

	public ContainerGenerator(InventoryPlayer inventory, TileEntityGenerator entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new SlotSonarFiltered(entity, 0, 8, 38));
		addSlotToContainer(new SlotSonarFiltered(entity, 1, 8, 14));
		addInventory(inventory, 8, 84);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
