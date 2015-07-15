package sonar.calculator.mod.common.recipes.machines;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;



public class StarchExtractorRecipes
{
  private static final StarchExtractorRecipes discharge = new StarchExtractorRecipes();
  
  private Map powerList = new HashMap();
  
  private static final String __OBFID = "CL_00000085";
  
  

  public static StarchExtractorRecipes discharge()
  {
    return discharge;
  }
  private StarchExtractorRecipes()
  {
    addItem(Items.apple, 2000);
    addItem(Items.potato, 1000);
    addItem(Items.carrot, 1000);
    addItem(Items.wheat, 800);
    addItem(Items.wheat_seeds, 600);
    addItem(Items.melon, 1000);
    addItem(Items.melon_seeds, 800);
    addItem(Items.reeds, 1000);
    addOreDictRecipe("treeSapling", 1000);
    addOreDictRecipe("treeLeaves", 200);
    addOreDictRecipe("calculatorLeaves", 2500);
   
  }
  public void addBlock(Block input, int power)
  {
    addItem(Item.getItemFromBlock(input), power);
  }
  
  public void addItem(Item input, int power)
  {
    addRecipe(new ItemStack(input, 1, 32767), power);
  }
  
  public void addRecipe(ItemStack input, int power)
  {
    this.powerList.put(input, power);
  }
  
  public void addOreDictRecipe(String name,int value){
	  int s = OreDictionary.getOres(name).size();
		for(int i=0; i<s; i++){
		    addRecipe(OreDictionary.getOres(name).get(i),value);
		}  
  }

  public int value(ItemStack stack) {
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
    while (!func_151397_a(stack, (ItemStack)entry.getKey()));
    
    
    return (Integer) entry.getValue();
  }
  
  private boolean func_151397_a(ItemStack stack1, ItemStack stack2) {
    return (stack2.getItem() == stack1.getItem()) && ((stack2.getItemDamage() == 32767) || (stack2.getItemDamage() == stack2.getItemDamage()));
  }
  
 
  
  public Map getPowerList() {
    return this.powerList;
  }
 
}
