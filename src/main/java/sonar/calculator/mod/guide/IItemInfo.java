package sonar.calculator.mod.guide;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;

public interface IItemInfo {

	/** the item */
	public ItemStack getItem();

	/** the info */
	public String getItemInfo();

	/** the main item category **/
	public Category getMainCategory();

	/** up to 9 related items */
	public ItemStack[] getRelatedItems();

	public static enum Category {
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
