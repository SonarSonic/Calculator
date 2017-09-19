package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankedGenerator;
import sonar.core.inventory.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

public class ContainerCrankedGenerator extends ContainerSync {

	private TileEntityCrankedGenerator entity;

	public ContainerCrankedGenerator(InventoryPlayer inventory, TileEntityCrankedGenerator entity) {
		super(entity);
		this.entity = entity;
		addInventory(inventory, 8, 84);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        return TransferSlotsManager.DEFAULT.transferStackInSlot(this, null, player, slotID);
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}
}
