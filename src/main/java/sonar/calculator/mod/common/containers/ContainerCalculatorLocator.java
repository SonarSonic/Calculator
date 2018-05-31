package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.core.inventory.containers.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerCalculatorLocator extends ContainerSync {

	public static TransferSlotsManager<TileEntityCalculatorLocator> TRANSFER = new TransferSlotsManager<>(2);
	private TileEntityCalculatorLocator entity;

	public ContainerCalculatorLocator(InventoryPlayer inventory, TileEntityCalculatorLocator entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new SlotSonarFiltered(entity, 0, 28, 60));
		addSlotToContainer(new SlotSonarFiltered(entity, 1, 132, 60));
		addInventory(inventory, 8, 84);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
