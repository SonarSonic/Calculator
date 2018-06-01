package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.core.handlers.inventories.containers.ContainerLargeInventory;
import sonar.core.handlers.inventories.slots.SlotChangeableStack;

public class ContainerStorageChamber extends ContainerLargeInventory {

	public ContainerStorageChamber(EntityPlayer player, TileEntityStorageChamber entity) {
		super(entity);
		addSlotToContainer(new SlotChangeableStack(entity, 0, 26, 6 + 17));
		addSlotToContainer(new SlotChangeableStack(entity, 1, 62, 6 + 17));
		addSlotToContainer(new SlotChangeableStack(entity, 2, 98, 6 + 17));
		addSlotToContainer(new SlotChangeableStack(entity, 3, 134, 6 + 17));
		addSlotToContainer(new SlotChangeableStack(entity, 4, 8, 32 + 17));
		addSlotToContainer(new SlotChangeableStack(entity, 5, 44, 32 + 17));
		addSlotToContainer(new SlotChangeableStack(entity, 6, 80, 32 + 17));
		addSlotToContainer(new SlotChangeableStack(entity, 7, 116, 32 + 17));
		addSlotToContainer(new SlotChangeableStack(entity, 8, 152, 32 + 17));
		addSlotToContainer(new SlotChangeableStack(entity, 9, 8, 58 + 17));
		addSlotToContainer(new SlotChangeableStack(entity, 10, 44, 58 + 17));
		addSlotToContainer(new SlotChangeableStack(entity, 11, 80, 58 + 17));
		addSlotToContainer(new SlotChangeableStack(entity, 12, 116, 58 + 17));
		addSlotToContainer(new SlotChangeableStack(entity, 13, 152, 58 + 17));
		addInventory(player.inventory, 8, 101);
	}
}