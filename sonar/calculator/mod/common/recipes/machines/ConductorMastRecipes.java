package sonar.calculator.mod.common.recipes.machines;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;



public class ConductorMastRecipes
{
  private static final ConductorMastRecipes smeltingBase = new ConductorMastRecipes();
  
  private Map smeltingList = new HashMap();
  private Map powerList = new HashMap();
 


  public static ConductorMastRecipes smelting()
  {
    return smeltingBase;
  }
  private ConductorMastRecipes()
  {
    addItem(Calculator.flawlessfirediamond, new ItemStack(Calculator.electricdiamond), 10000);
    addItem(Calculator.itemCalculator, new ItemStack(Calculator.itemScientificCalculator), 2000);
    addBlock(Calculator.flawless_fire_block, new ItemStack(Calculator.electric_diamond_block), 90000);
    
  }
  public void addBlock(Block input, ItemStack output, int power)
  {
    addItem(Item.getItemFromBlock(input), output, power);
  }
  
  public void addItem(Item input, ItemStack output, int power)
  {
    addRecipe(new ItemStack(input, 1, 32767), output, power);
  }
  
  public void addRecipe(ItemStack input, ItemStack output, int power)
  {
    this.smeltingList.put(input, output);
    this.powerList.put(input, power);
  }
  



  public ItemStack getSmeltingResult(ItemStack stack)
  {
    Iterator iterator = this.smeltingList.entrySet().iterator();
    
    Map.Entry entry;
    do
    {
      if (!iterator.hasNext())
      {
        return null;
      }
      
      entry = (Map.Entry)iterator.next();
    }
    while (!equalStack(stack, (ItemStack)entry.getKey()));
    
	if(CalculatorConfig.isEnabled((ItemStack) entry.getValue())){
		return (ItemStack) entry.getValue();
	}else{
	return null;
	}
  }
  
  public int getPowerUsage(ItemStack stack) {
    Iterator iterator = this.powerList.entrySet().iterator();
    
    Map.Entry entry;
    do
    {
      if (!iterator.hasNext())
      {
        return 0;
      }
      
      entry = (Map.Entry)iterator.next();
    }
    while (!equalStack(stack, (ItemStack)entry.getKey()));
    
    
    return (Integer) entry.getValue();
  }
  
  private boolean equalStack(ItemStack stack1, ItemStack stack2) {
    return (stack2.getItem() == stack1.getItem()) && ((stack2.getItemDamage() == 32767) || (stack2.getItemDamage() == stack2.getItemDamage()));
  }
  
  public Map getSmeltingList()
  {
    return this.smeltingList;
  }
  
  public Map getPowerList() {
    return this.powerList;
  }
}
