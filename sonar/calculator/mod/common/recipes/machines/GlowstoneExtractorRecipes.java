package sonar.calculator.mod.common.recipes.machines;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;



public class GlowstoneExtractorRecipes
{
  private static final GlowstoneExtractorRecipes discharge = new GlowstoneExtractorRecipes();
  
  private Map powerList = new HashMap();
  
  private static final String __OBFID = "CL_00000085";
  
  

  public static GlowstoneExtractorRecipes discharge()
  {
    return discharge;
  }
  private GlowstoneExtractorRecipes()
  {
    addItem(Items.glowstone_dust, 1000);
    addBlock(Blocks.glowstone, 4000);
    addOreDictRecipe("ingotGlowstone", 3000);

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
