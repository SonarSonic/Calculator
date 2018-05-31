package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.core.inventory.containers.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerCO2Generator extends ContainerSync {

	public static TransferSlotsManager<TileEntityCO2Generator> TRANSFER = new TransferSlotsManager<>(2);
	private TileEntityCO2Generator entity;

	public ContainerCO2Generator(InventoryPlayer inventory, TileEntityCO2Generator entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new SlotSonarFiltered(entity, 0, 80, 22));
		addSlotToContainer(new SlotSonarFiltered(entity, 1, 28, 60));
		addInventory(inventory, 8, 84);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
