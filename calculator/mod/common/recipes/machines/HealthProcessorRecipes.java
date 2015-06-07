package sonar.calculator.mod.common.recipes.machines;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;



public class HealthProcessorRecipes
{
  private static final HealthProcessorRecipes health = new HealthProcessorRecipes();
  
  private Map healthList = new HashMap();
  
  private static final String __OBFID = "CL_00000085";
  
  

  public static HealthProcessorRecipes instance()
  {
    return health;
  }
  private HealthProcessorRecipes()
  {
	addRecipe(Items.blaze_rod, 100);
	addRecipe(Items.bone, 2);
	addRecipe(Items.blaze_powder, 50);
	addRecipe(Items.ender_pearl, 200);
	addRecipe(Items.feather, 2);
	addRecipe(Items.egg, 2);
	addRecipe(new ItemStack(Items.dye,1), 6);
	addRecipe(Items.leather, 4);
	addRecipe(Items.ghast_tear, 250);
	addRecipe(Items.string, 2);
	addRecipe(Items.gunpowder, 4);
	addRecipe(Items.nether_star, 5000);
	addRecipe(Items.rotten_flesh, 2);
	addRecipe(Items.spider_eye, 4);
	addRecipe(Items.slime_ball, 20);
	addRecipe(Items.magma_cream, 50);
	addRecipe(Items.fermented_spider_eye, 8);
	addRecipe(Blocks.wool, 6);
	addRecipe(Items.golden_apple, 250);
    
  }
  public void addRecipe(Block input, int power)
  {
	  addRecipe(Item.getItemFromBlock(input),  power);
  }
  
  public void addRecipe(Item input,int power)
  {
    addRecipe(new ItemStack(input, 1, 32767), power);
  }
  
  public void addRecipe(ItemStack input,int power)
  {
    this.healthList.put(input, power);
  }
 
  
  public int getHealthValue(ItemStack stack) {
    Iterator iterator = this.healthList.entrySet().iterator();
    
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
  public Map getHealthList()
  {
    return this.healthList;
  }
}
