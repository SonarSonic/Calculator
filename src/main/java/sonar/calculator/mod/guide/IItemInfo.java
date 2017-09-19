package sonar.calculator.mod.guide;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;

public interface IItemInfo {

    /**
     * the item
     */
    ItemStack getItem();

    /**
     * the info
     */
    String getItemInfo();

    /**
     * the main item category
     **/
    Category getMainCategory();

    /**
     * up to 9 related items
     */
    ItemStack[] getRelatedItems();

    enum Category {
		All(null), 
		Calculators(new ItemStack(Calculator.itemCalculator)), 
		Modules(new ItemStack(Calculator.itemEnergyModule)), 
		Circuits(new ItemStack(Calculator.circuitBoard)), 
		Nutrition(new ItemStack(Calculator.hungerProcessor)), 
		Generation(new ItemStack(Calculator.handcrankedGenerator)), 
		Machines(new ItemStack(Calculator.powerCube)), 
		Tools(new ItemStack(Calculator.wrench)), 
		Items(new ItemStack(Calculator.soil)), 
		Blocks(new ItemStack(Calculator.purifiedObsidian));
		public ItemStack stack;

		Category(ItemStack stack) {
			this.stack = stack;
		}
	}
}
