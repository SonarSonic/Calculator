package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherController;
import sonar.core.energy.DischargeValues;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.TransferSlotsManager.TransferSlots;
import sonar.core.inventory.TransferSlotsManager.TransferType;

public class ContainerWeatherController extends ContainerSync {
	private TileEntityWeatherController entity;
	public static TransferSlotsManager<TileEntityWeatherController> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(TransferSlotsManager.DISCHARGE_SLOT);
			addPlayerInventory();
		}
	};

	public ContainerWeatherController(InventoryPlayer inventory, TileEntityWeatherController entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new Slot(entity, 0, 28, 60));
		addInventory(inventory, 8, 84);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return transfer.transferStackInSlot(this, entity, player, slotID);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return entity.isUseableByPlayer(player);
	}
}
