package sonar.calculator.mod.api;

import net.minecraft.item.ItemStack;

/**used on items with Stability - mainly circuits*/
public interface IStability {

	/**returns if the item is stable or not*/
	public boolean getStability(ItemStack stack);	
	
	/**sent to the item when it's stability is false used for circuits to reset their tag compound*/
	public void onFalse(ItemStack stack);
}
