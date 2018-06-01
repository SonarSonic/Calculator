package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import sonar.calculator.mod.common.tileentity.misc.TileEntityReinforcedChest;
import sonar.core.handlers.inventories.containers.ContainerLargeInventory;
import sonar.core.handlers.inventories.slots.SlotChangeableStack;

public class ContainerReinforcedChest extends ContainerLargeInventory {

	public ContainerReinforcedChest(EntityPlayer player, TileEntityReinforcedChest entity) {
		super(entity);
		entity.openInventory(player);
		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlotToContainer(new SlotChangeableStack(entity, k + j * 9, 8 + k * 18, 24 + j * 18));
			}
		}
		addInventory(player.inventory, 8, 84);
	}

}