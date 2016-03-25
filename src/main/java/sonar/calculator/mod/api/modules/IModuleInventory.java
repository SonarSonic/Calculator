package sonar.calculator.mod.api.modules;

import net.minecraft.item.ItemStack;
import sonar.core.common.item.InventoryItem;

/**for modules which contain an inventory of some kind*/
public interface IModuleInventory extends IModuleClickable{

	public InventoryItem getInventory(ItemStack stack, String tagName, boolean useStackTag);
	
	public boolean isSharedInventory(ItemStack stack);
}
