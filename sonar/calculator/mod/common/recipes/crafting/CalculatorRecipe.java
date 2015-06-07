package sonar.calculator.mod.common.recipes.crafting;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CalculatorRecipe {

	public ItemStack output,input,input2;
	public boolean hidden;
	
	public CalculatorRecipe(ItemStack output, ItemStack input, ItemStack input2,boolean hidden){
		this.output=output;
		this.input=input;
		this.input2=input2;
		this.hidden=hidden;
		
	}
	public CalculatorRecipe(Item output, Item input, Item input2, boolean hidden){
		this.output=new ItemStack(output,1);
		this.input=new ItemStack(input,1);
		this.input2=new ItemStack(input2,1);
		this.hidden=hidden;
	}
}
