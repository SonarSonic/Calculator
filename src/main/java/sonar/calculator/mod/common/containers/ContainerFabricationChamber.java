package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFabricationChamber;
import sonar.core.inventory.containers.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;
import sonar.core.inventory.handling.SlotSonarFiltered;

import javax.annotation.Nonnull;

public class ContainerFabricationChamber extends ContainerSync {

	public static TransferSlotsManager<TileEntityFabricationChamber> TRANSFER = new TransferSlotsManager<>(1);
	private TileEntityFabricationChamber entity;

	public ContainerFabricationChamber(InventoryPlayer inventory, TileEntityFabricationChamber entity) {
		super(entity);
		this.entity = entity;
		addSlotToContainer(new SlotSonarFiltered(entity, 0, 115+18, 89));
		addInventory(inventory, 8, 118);
	}
	
	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return TRANSFER.transferStackInSlot(this, entity, player, slotID);
	}
}
