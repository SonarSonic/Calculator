package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

public class ContainerResearchChamber extends ContainerSync {
	private TileEntityResearchChamber entity;
	public static TransferSlotsManager<TileEntityResearchChamber> transfer = new TransferSlotsManager() {
		{
			addTransferSlot(new TransferSlots<TileEntityResearchChamber>(TransferType.TILE_INV, 1));
			addPlayerInventory();
		}
	};

	public ContainerResearchChamber(EntityPlayer player, TileEntityResearchChamber entity) {
		super(entity);
		this.entity = entity;
		entity.openInventory(player);
		addSlotToContainer(new Slot(entity, 0, 80, 34));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}
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
