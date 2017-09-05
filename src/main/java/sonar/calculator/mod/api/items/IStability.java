package sonar.calculator.mod.api.items;

import net.minecraft.item.ItemStack;

/**
 * used on items with Stability - mainly circuits
 */
public interface IStability {

    /**
     * returns if the item is stable or not
     */
    boolean getStability(ItemStack stack);
	
    /**
     * sent to the item when it's stability is false used for circuits to reset their tag compound
     */
    void onFalse(ItemStack stack);
}
