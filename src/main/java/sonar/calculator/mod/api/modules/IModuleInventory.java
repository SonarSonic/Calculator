package sonar.calculator.mod.api.modules;

import net.minecraft.item.ItemStack;
import sonar.core.common.item.InventoryItem;

/**
 * for modules which contain an inventories of some kind
 */
public interface IModuleInventory extends IModuleClickable{

    InventoryItem getInventory(ItemStack stack, String tagName, boolean useStackTag);
	
    boolean isSharedInventory(ItemStack stack);
}
