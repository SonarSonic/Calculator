package sonar.calculator.mod.api.nutrition;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.api.machines.ProcessType;

/**
 * used on items with Health Storage
 */
public interface IHealthStore {
    /**
     * for adding/remove health from the item
     */
    void transferHealth(int transfer, ItemStack stack, ProcessType process);

    /**
     * total stored health points
     */
    int getHealthPoints(ItemStack stack);
	
    /**
     * total stored health points
     */
    int getMaxHealthPoints(ItemStack stack);

    /**
     * sets maximum health
     */
    void setHealth(ItemStack stack, int health);
}