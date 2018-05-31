package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankedGenerator;
import sonar.core.inventory.containers.ContainerSync;
import sonar.core.inventory.TransferSlotsManager;

import javax.annotation.Nonnull;

public class ContainerCrankedGenerator extends ContainerSync {

	public ContainerCrankedGenerator(InventoryPlayer inventory, TileEntityCrankedGenerator entity) {
		super(entity);
		addInventory(inventory, 8, 84);
	}

	@Nonnull
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        return TransferSlotsManager.DEFAULT.transferStackInSlot(this, null, player, slotID);
	}
}
