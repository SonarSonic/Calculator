package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherController;
import sonar.core.inventory.containers.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

import javax.annotation.Nonnull;

public class ContainerWeatherController extends ContainerSync {

	public static TransferSlotsManager<TileEntityWeatherController> TRANSFER = new TransferSlotsManager<>(1);
	private TileEntityWeatherController entity;

	public ContainerWeatherController(InventoryPlayer inventory, TileEntityWeatherController entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new Slot(entity, 0, 28, 60));
		addInventory(inventory, 8, 84);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}