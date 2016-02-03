package sonar.calculator.mod.api.nutrition;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.api.machines.ProcessType;

/**used on items with Health Storage*/
public interface IHealthStore {

	/**for adding/remove health from the item*/
	public void transferHealth(int transfer, ItemStack stack, ProcessType process);
	
	/**total stored health points*/
	public int getHealthPoints(ItemStack stack);	

	/**total stored health points*/
	public int getMaxHealthPoints(ItemStack stack);
	
	/**sets maximum health*/
	public void setHealth(ItemStack stack, int health);
}