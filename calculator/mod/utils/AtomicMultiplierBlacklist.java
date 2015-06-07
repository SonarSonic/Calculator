package sonar.calculator.mod.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.item.misc.ItemCircuit;


/**Uses the config BlackList file to create a Map which can be easily accessed*/
public class AtomicMultiplierBlacklist
{
  private static final AtomicMultiplierBlacklist blacklist = new AtomicMultiplierBlacklist();
  
  private Map bannedList = new HashMap();
  
  
  public static AtomicMultiplierBlacklist blacklist()
  {
    return blacklist;
  }
  

  public AtomicMultiplierBlacklist()
  {
	  String[] blacklisted = CalculatorConfig.atomicblackList.getStringList();
	  for(int i =0; i<blacklisted.length; i++){
		  String[] parts = blacklisted[i].split(":");
		  Item itemBan = GameRegistry.findItem(parts[0], parts[1]);
		  if(itemBan!=null){
			  this.addBan(itemBan);
		  }else{
			  Block blockBan = GameRegistry.findBlock(parts[0], parts[1]); 
			  if(blockBan!=null){
				  this.addBan(blockBan);
			  }
		  }
	  }
  }
  

  public void addBan(Block input)
  {
	  addBan(Item.getItemFromBlock(input));
  }
  public void addBan(Item input)
  {
    this.bannedList.put(input, false);
  }
  
  public boolean isAllowed(Item item)
  {
    Iterator iterator = this.bannedList.entrySet().iterator();
    
    Map.Entry entry;
    do
    {
      if (!iterator.hasNext())
      {
        return true;
      }
      
      entry = (Map.Entry)iterator.next();
    }
    while (!(item == (Item)entry.getKey()));
    
    return (Boolean) entry.getValue();
  }
  
  
  private boolean func_151397_a(ItemStack p_151397_1_, ItemStack p_151397_2_) {
    return (p_151397_2_.getItem() == p_151397_1_.getItem()) && ((p_151397_2_.getItemDamage() == 32767) || (p_151397_2_.getItemDamage() == p_151397_1_.getItemDamage()));
  }
  
  public Map getSmeltingList()
  {
    return this.bannedList;
  }
  
}
