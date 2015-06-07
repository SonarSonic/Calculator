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



public class AtomicCalculatorNEIRecipes
{
  private static final AtomicCalculatorNEIRecipes smeltingBase = new AtomicCalculatorNEIRecipes();
  
  private Map smeltingList = new HashMap();
  private Map smeltingList2 = new HashMap();
  private Map smeltingList3 = new HashMap();
  
  private static final String __OBFID = "CL_00000085";
  
  ItemCircuit circuit;
  

  public static AtomicCalculatorNEIRecipes smelting()
  {
    return smeltingBase;
  }
  


  private AtomicCalculatorNEIRecipes()
  {
    addRecipe(new ItemStack(Calculator.itemScientificCalculator, 1), new ItemStack(Items.iron_ingot), new ItemStack(Items.redstone), new ItemStack(Items.iron_ingot));
    addRecipe(new ItemStack(Calculator.flawlessdiamond, 1), new ItemStack(Items.diamond), new ItemStack(Calculator.atomic_binder), new ItemStack(Items.diamond));
    addRecipe(new ItemStack(Calculator.flawlessfirediamond, 1), new ItemStack(Items.diamond), new ItemStack(Items.blaze_rod), new ItemStack(Items.diamond));
    addRecipe(new ItemStack(Calculator.enddiamond, 1), new ItemStack(Blocks.end_stone), new ItemStack(Calculator.electricdiamond), new ItemStack(Blocks.obsidian));
    addRecipe(new ItemStack(Calculator.PearSapling, 1), new ItemStack(Calculator.AmethystSapling), new ItemStack(Blocks.end_stone), new ItemStack(Calculator.tanzaniteSapling));
    addRecipe(new ItemStack(Calculator.itemAdvancedTerrainModule), new ItemStack(Calculator.itemScientificCalculator), new ItemStack(Calculator.atomic_binder), new ItemStack(Calculator.redstone_ingot));
    addRecipe(new ItemStack(Calculator.tanzaniteSapling, 1), new ItemStack(Calculator.tanzaniteLog), new ItemStack(Calculator.atomic_binder), new ItemStack(Calculator.tanzaniteLeaf));
    addRecipe(new ItemStack(Calculator.tanzaniteSapling, 1), new ItemStack(Calculator.large_tanzanite), new ItemStack(Calculator.atomic_binder), new ItemStack(Blocks.sapling));
    addRecipe(new ItemStack(Calculator.itemNutritionModule, 1), new ItemStack(Calculator.healthprocessor), new ItemStack(Calculator.itemEnergyModule), new ItemStack(Calculator.hungerprocessor));
    addRecipe(new ItemStack(Calculator.speedUpgrade, 1), new ItemStack(Calculator.circuitBoard, 1, 4), new ItemStack(Calculator.atomic_binder), new ItemStack(Calculator.enrichedgold,1));
    addRecipe(new ItemStack(Calculator.energyUpgrade, 1), new ItemStack(Calculator.circuitBoard, 1, 5), new ItemStack(Calculator.atomic_binder), new ItemStack(Calculator.enrichedgold,1));
    addRecipe(new ItemStack(Calculator.voidUpgrade, 1), new ItemStack(Calculator.circuitBoard, 1, 6), new ItemStack(Calculator.atomic_binder), new ItemStack(Calculator.enrichedgold,1));
    addRecipe(new ItemStack(Calculator.glowstoneextractor, 1), new ItemStack(Calculator.circuitBoard, 1, 9), new ItemStack(Calculator.redstoneextractor), new ItemStack(Calculator.large_tanzanite));
    addRecipe(new ItemStack(Calculator.redstoneextractor, 1), new ItemStack(Calculator.redstone_ingot), new ItemStack(Calculator.starchextractor), new ItemStack(Calculator.redstone_ingot));
    addRecipe(new ItemStack(Calculator.conductorMast, 1), new ItemStack(Calculator.itemEnergyModule), new ItemStack(Calculator.flawlessfirediamond), new ItemStack(Calculator.itemEnergyModule));
    addRecipe(new ItemStack(Calculator.storageChamber, 1), new ItemStack(Calculator.reinforced_iron_block), new ItemStack(Blocks.chest), new ItemStack(Calculator.reinforced_iron_block));
    addRecipe(new ItemStack(Calculator.processingChamber, 1), new ItemStack(Calculator.reassemblyChamber), new ItemStack(Calculator.flawlessdiamond), new ItemStack(Calculator.restorationChamber));
    addRecipe(new ItemStack(Calculator.itemStorageModule, 1), new ItemStack(Calculator.reinforcediron_ingot), new ItemStack(Blocks.chest), new ItemStack(Calculator.reinforcediron_ingot));
    addRecipe(new ItemStack(Calculator.transmitter, 1), new ItemStack(Calculator.reinforcediron_ingot), new ItemStack(Calculator.electricdiamond), new ItemStack(Calculator.reinforcediron_ingot));
    addRecipe(new ItemStack(Calculator.weatherStation, 4), new ItemStack(Calculator.reinforcediron_ingot), new ItemStack(Calculator.flawlessfirediamond), new ItemStack(Calculator.reinforcediron_ingot));

  }
  


  public void addBlock(Block output, ItemStack input, ItemStack input2, ItemStack input3)
  {
    addItem(Item.getItemFromBlock(output), input, input2, input3);
  }
  
  public void addItem(Item output, ItemStack input, ItemStack input2, ItemStack input3)
  {
    addRecipe(new ItemStack(output, 1, 32767), input, input2, input3);
  }
  

  public void addRecipe(ItemStack output, ItemStack input, ItemStack input2, ItemStack input3)
  {
    this.smeltingList.put(output, input);
    this.smeltingList2.put(output, input2);
    this.smeltingList3.put(output, input3);
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
}
