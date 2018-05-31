package sonar.calculator.mod.common.containers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.common.tileentity.TileEntityLargeInventory;
import sonar.core.inventory.handling.SlotSonarFiltered;

public class SlotChangeableStack extends SlotSonarFiltered {

	public TileEntityLargeInventory invTile;
	public boolean defStack;
	public boolean fullStack;

	public SlotChangeableStack(TileEntityLargeInventory invTile, int index, int xPosition, int yPosition, boolean fullStack) {
		super(invTile, index, xPosition, yPosition);
		this.invTile = invTile;
		this.fullStack = defStack = fullStack;
	}

	@Override
	public ItemStack getStack() {
		if(fullStack) {
			StoredItemStack slot = invTile.inv.slots.get(this.getSlotIndex()).getLargeStack();
			return ItemHandlerHelper.copyStackWithSize(slot.item, (int) slot.stored);
		}else{
			return invTile.inv.slots.get(this.getSlotIndex()).getAccessStack();
		}
	}
}