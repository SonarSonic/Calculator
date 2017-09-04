package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerModuleSelector extends Container {
	public EntityPlayer player;
	public ItemStack calc;

	public ContainerModuleSelector(EntityPlayer player, ItemStack calc) {
		this.player=player;
		this.calc=calc;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		return ItemStack.EMPTY;
	}
}