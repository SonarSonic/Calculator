package sonar.calculator.mod.integration.nei;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.item.misc.ItemCircuit;



public class FlawlessCalcNEIRecipes
{
  private static final FlawlessCalcNEIRecipes smeltingBase = new FlawlessCalcNEIRecipes();
  
  private Map smeltingList = new HashMap();
  private Map smeltingList2 = new HashMap();
  private Map smeltingList3 = new HashMap();
  private Map smeltingList4 = new HashMap();
  
  private static final String __OBFID = "CL_00000085";
  
  ItemCircuit circuit;
  

  public static FlawlessCalcNEIRecipes smelting()
  {
    return smeltingBase;
  }
  

  private FlawlessCalcNEIRecipes()
  {
    addRecipe(new ItemStack(Calculator.diamondSapling), new ItemStack(Calculator.PearSapling), new ItemStack(Calculator.enddiamond), new ItemStack(Calculator.enddiamond), new ItemStack(Blocks.end_stone));
    addRecipe(new ItemStack(Items.diamond), new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_ingot));
    addRecipe(new ItemStack(Items.emerald), new ItemStack(Items.diamond), new ItemStack(Items.diamond), new ItemStack(Items.diamond), new ItemStack(Items.diamond));
    addRecipe(new ItemStack(Items.ender_pearl), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot));
    addRecipe(new ItemStack(Blocks.obsidian), new ItemStack(Blocks.log), new ItemStack(Blocks.log), new ItemStack(Blocks.log), new ItemStack(Blocks.log));
    addRecipe(new ItemStack(Calculator.purifiedobsidianBlock), new ItemStack(Blocks.obsidian), new ItemStack(Blocks.obsidian), new ItemStack(Blocks.obsidian), new ItemStack(Blocks.obsidian));
    addRecipe(new ItemStack(Calculator.stablestoneBlock), new ItemStack(Calculator.reinforcedstoneBlock), new ItemStack(Calculator.reinforcedstoneBlock), new ItemStack(Calculator.reinforcedstoneBlock), new ItemStack(Calculator.reinforcedstoneBlock));
    addRecipe(new ItemStack(Calculator.fiddledewFruit), new ItemStack(Calculator.broccoli), new ItemStack(Calculator.broccoli), new ItemStack(Calculator.broccoli), new ItemStack(Calculator.broccoli));

    addRecipe(new ItemStack(Calculator.itemLocatorModule), new ItemStack(Calculator.itemEnergyModule), new ItemStack(Calculator.itemCalculator), new ItemStack(Calculator.itemCalculator), new ItemStack(Calculator.itemEnergyModule));
    addRecipe(new ItemStack(Calculator.flawlessGlass, 4), new ItemStack(Calculator.flawlessdiamond), new ItemStack(Blocks.glass), new ItemStack(Blocks.glass), new ItemStack(Calculator.flawlessdiamond));
    addRecipe(new ItemStack(Calculator.controlled_Fuel, 1), new ItemStack(Calculator.circuitBoard), new ItemStack(Calculator.enriched_coal), new ItemStack(Calculator.enriched_coal), new ItemStack(Calculator.circuitBoard));
    addRecipe(new ItemStack(Calculator.carbondioxideGenerator, 1), new ItemStack(Calculator.gas_lantern_off), new ItemStack(Calculator.circuitBoard, 1, 8),new ItemStack(Calculator.circuitBoard, 1, 8), new ItemStack(Calculator.gas_lantern_off));


    addRecipe(new ItemStack(Items.blaze_rod), new ItemStack(Items.blaze_powder), new ItemStack(Items.blaze_powder), new ItemStack(Items.blaze_powder), new ItemStack(Items.blaze_powder));
    addRecipe(new ItemStack(Items.ghast_tear), new ItemStack(Items.blaze_rod), new ItemStack(Items.blaze_rod), new ItemStack(Items.blaze_rod), new ItemStack(Items.blaze_rod));

  }  


  public void addBlock(Block output, ItemStack input, ItemStack input2, ItemStack input3, ItemStack input4)
  {
    addItem(Item.getItemFromBlock(output), input, input2, input3, input4);
  }
  
  public void addItem(Item output, ItemStack input, ItemStack input2, ItemStack input3, ItemStack input4)
  {
    addRecipe(new ItemStack(output, 1, 32767), input, input2, input3, input4);
  }
  

  public void addRecipe(ItemStack output, ItemStack input, ItemStack input2, ItemStack input3, ItemStack input4)
  {
    this.smeltingList.put(output, input);
    this.smeltingList2.put(output, input2);
    this.smeltingList3.put(output, input3);
    this.smeltingList4.put(output, input4);
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
    while (!func_151397_a(stack, (ItemStack)entry.getKey()));
    
    return (ItemStack)entry.getValue();
  }
  
  public ItemStack getSmeltingResult2(ItemStack stack) {
    Iterator iterator = this.smeltingList2.entrySet().iterator();
    
    Map.Entry entry;
    do
    {
      if (!iterator.hasNext())
      {
        return null;
      }
      
      entry = (Map.Entry)iterator.next();
    }
    while (!func_151397_a(stack, (ItemStack)entry.getKey()));
    
    return (ItemStack)entry.getValue();
  }
  
  public ItemStack getSmeltingResult3(ItemStack stack) {
    Iterator iterator = this.smeltingList3.entrySet().iterator();
    
    Map.Entry entry;
    do
    {
      if (!iterator.hasNext())
      {
        return null;
      }
      
      entry = (Map.Entry)iterator.next();
    }
    while (!func_151397_a(stack, (ItemStack)entry.getKey()));
    
    return (ItemStack)entry.getValue();
  }
  
  public ItemStack getSmeltingResult4(ItemStack stack) {
    Iterator iterator = this.smeltingList4.entrySet().iterator();
    
    Map.Entry entry;
    do
    {
      if (!iterator.hasNext())
      {
        return null;
      }
      
      entry = (Map.Entry)iterator.next();
    }
    while (!func_151397_a(stack, (ItemStack)entry.getKey()));
    
    return (ItemStack)entry.getValue();
  }
  public ItemStack getSmeltingInput(ItemStack stack)
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
    while (!func_151397_a(stack, (ItemStack)entry.getValue()));
    
    return (ItemStack)entry.getKey();
  }
  public ItemStack getSmeltingInput2(ItemStack stack)
  {
    Iterator iterator = this.smeltingList2.entrySet().iterator();
    
    Map.Entry entry;
    do
    {
      if (!iterator.hasNext())
      {
        return null;
      }
      
      entry = (Map.Entry)iterator.next();
    }
    while (!func_151397_a(stack, (ItemStack)entry.getValue()));
    
    return (ItemStack)entry.getKey();
  }
  public ItemStack getSmeltingInput3(ItemStack stack)
  {
    Iterator iterator = this.smeltingList3.entrySet().iterator();
    
    Map.Entry entry;
    do
    {
      if (!iterator.hasNext())
      {
        return null;
      }
      
      entry = (Map.Entry)iterator.next();
    }
    while (!func_151397_a(stack, (ItemStack)entry.getValue()));
    
    return (ItemStack)entry.getKey();
  }
  public ItemStack getSmeltingInput4(ItemStack stack)
  {
    Iterator iterator = this.smeltingList4.entrySet().iterator();
    
    Map.Entry entry;
    do
    {
      if (!iterator.hasNext())
      {
        return null;
      }
      
      entry = (Map.Entry)iterator.next();
    }
    while (!func_151397_a(stack, (ItemStack)entry.getValue()));
    
    return (ItemStack)entry.getKey();
  }
  private boolean func_151397_a(ItemStack p_151397_1_, ItemStack p_151397_2_) {
    return (p_151397_2_.getItem() == p_151397_1_.getItem()) && ((p_151397_2_.getItemDamage() == 32767) || (p_151397_2_.getItemDamage() == p_151397_1_.getItemDamage()));
  }
  
  public Map getSmeltingList()
  {
    return this.smeltingList;
  }
  
  public Map getSmeltingList2() {
    return this.smeltingList2;
  }
  
  public Map getSmeltingList3() {
    return this.smeltingList3;
  }
  
  public Map getSmeltingList4() {
    return this.smeltingList4;
  }
}
