package sonar.calculator.mod.api;

import net.minecraft.item.ItemStack;

/**used on items with Hunger Storage*/
public interface IHungerStore {

	/**for adding/remove hunger from the item*/
	public void transferHunger(int transfer, ItemStack stack, ProcessType process);
	
	/**total stored hunger points*/
	public int getHungerPoints(ItemStack stack);	

	/**total stored hunger points*/
	public int getMaxHungerPoints(ItemStack stack);	

	/**sets maximum hunger*/
	public void setHunger(ItemStack stack, int health);
}
