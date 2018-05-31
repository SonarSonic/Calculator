package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityPowerCube;
import sonar.core.inventory.containers.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerPowerCube extends ContainerSync {

	public static TransferSlotsManager<TileEntityPowerCube> TRANSFER = new TransferSlotsManager<>(2);
	private TileEntityPowerCube entity;

	public ContainerPowerCube(InventoryPlayer inventory, TileEntityPowerCube entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new SlotSonarFiltered(entity, 0, 80, 34));
		addSlotToContainer(new SlotSonarFiltered(entity, 1, 28, 60));
		addInventory(inventory, 8, 84);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
