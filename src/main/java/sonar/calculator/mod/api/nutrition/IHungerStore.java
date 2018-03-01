package sonar.calculator.mod.api.nutrition;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.api.machines.ProcessType;

/**
 * used on items with Hunger Storage
 */
public interface IHungerStore {
    /**
     * for adding/remove hunger from the item
     */
    void transferHunger(int transfer, ItemStack stack, ProcessType process);

    /**
     * total stored hunger points
     */
    int getHungerPoints(ItemStack stack);
	
    /**
     * total stored hunger points
     */
    int getMaxHungerPoints(ItemStack stack);

    /**
     * sets maximum hunger
     */
    void setHunger(ItemStack stack, int health);
}
