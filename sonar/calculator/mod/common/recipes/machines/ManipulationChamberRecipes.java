package sonar.calculator.mod.common.recipes.machines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.item.misc.ItemCircuit;




public class ManipulationChamberRecipes
{
  private static final ManipulationChamberRecipes smeltingBase = new ManipulationChamberRecipes();
  
  private Map smeltingList = new HashMap();
  public Map referenceList = new HashMap();
  
  private static final String __OBFID = "CL_00000085";
  
  ItemCircuit circuit;
  

  public static ManipulationChamberRecipes smelting()
  {
    return smeltingBase;
  }
  


  private ManipulationChamberRecipes()
  {
	  ArrayList<ItemStack> atomicAssembly = new ArrayList();
	  atomicAssembly.add(circuit(0, 1));
	  atomicAssembly.add(circuit(1, 1));
	  atomicAssembly.add(circuit(2, 1));
	  atomicAssembly.add(circuit(3, 1));
	  atomicAssembly.add(circuit(4, 1));
	  atomicAssembly.add(circuit(5, 1));
	  atomicAssembly.add(circuit(6, 1));
	  atomicAssembly.add(circuit(7, 1));
	  atomicAssembly.add(circuit(8, 1));
	  atomicAssembly.add(circuit(9, 1));
	  atomicAssembly.add(circuit(10, 1));
	  atomicAssembly.add(circuit(11, 1));
	  atomicAssembly.add(circuit(12, 1));
	  atomicAssembly.add(circuit(13, 1));	  
	  addRecipe(atomicAssembly, new ItemStack(Calculator.atomic_assembly, 1), 0);

	  ArrayList<ItemStack> atomicModule = new ArrayList();
	  atomicModule.add(circuit(8, 1));
	  atomicModule.add(circuit(8, 1));
	  atomicModule.add(circuit(8, 1));
	  atomicModule.add(circuit(8, 1));
	  atomicModule.add(circuit(8, 1));
	  atomicModule.add(circuit(8, 1));
	  atomicModule.add(circuit(8, 1));
	  atomicModule.add(circuit(8, 1));
	  atomicModule.add(circuit(8, 1));
	  atomicModule.add(circuit(8, 1));
	  atomicModule.add(circuit(8, 1));
	  atomicModule.add(circuit(8, 1));
	  atomicModule.add(circuit(8, 1));
	  atomicModule.add(circuit(8, 1));
	  addRecipe(atomicModule, new ItemStack(Calculator.atomic_module, 1), 1);
	  
	  ArrayList<ItemStack> atomicbinder = new ArrayList();
	  atomicbinder.add(circuit(3, 0));
	  atomicbinder.add(circuit(3, 0));
	  addRecipe(atomicbinder, new ItemStack(Calculator.atomic_binder, 1), 2);

	  ArrayList<ItemStack> calculatorassembly = new ArrayList();
	  calculatorassembly.add(circuit(3, 0));
	  calculatorassembly.add(circuit(3, 0));
	  addRecipe(calculatorassembly, new ItemStack(Calculator.calculator_assembly, 1), 3);

	  ArrayList<ItemStack> calculator_screen = new ArrayList();
	  calculator_screen.add(circuit(1, 0));
	  addRecipe(calculator_screen, new ItemStack(Calculator.calculator_screen, 1), 4);
  }

  public void addRecipe(ArrayList<ItemStack> atomicAssembly, ItemStack output, int ID)
  {
    this.smeltingList.put(ID, output);
    this.referenceList.put(atomicAssembly, ID);
  }
  public int getRecipeID(ArrayList<ItemStack> p_151395_1_)
  {
    Iterator iterator = this.smeltingList.entrySet().iterator();
    
    Map.Entry entry;
    do
    {
      if (!iterator.hasNext())
      {
        return -1;
      }
      
      entry = (Map.Entry)iterator.next();
    }
    while (!arraysMatch(p_151395_1_, (ArrayList<ItemStack>)entry.getKey()));
    
    return (Integer)entry.getValue();
  }
  public boolean arraysMatch(ArrayList<ItemStack> check, ArrayList<ItemStack> input){
	  ArrayList<ItemStack> requiredStacks =input;
	  for(int c =0; c<check.size();c++){
		  ItemStack target = check.get(c);
			if (target != null) {
				boolean flag = false;
				Iterator iterator = input.iterator();

				while (iterator.hasNext()) {
					if (iterator.next() instanceof ItemStack) {
						ItemStack required = (ItemStack) iterator.next();

						if (target.areItemStacksEqual(target, required) 
								&& target.areItemStackTagsEqual(target, required)) {
								flag = true;
								requiredStacks.remove(required);
								break;
							
						}
					}
				}
				if (!flag) {
					return false;
				}
			}}
	return requiredStacks.isEmpty();
  }
  
  public ItemStack getSmeltingResult(int p_151395_1_)
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
    while (!(p_151395_1_ == (Integer)entry.getKey()));
    
    return (ItemStack)entry.getValue();
  }
    
  public Map getSmeltingList()
  {
    return this.smeltingList;
  }
  
	/**
	 * @param i
	 *            = damage value
	 * @param t
	 *            = type (1=Stable, 2=Damage, 3=Dirty else normal
	 */
	public ItemStack circuit(int i, int t) {
		ItemStack circuit = new ItemStack(circuitType(t), i);
		if (t == 1) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("Stable", 1);
			circuit.setTagCompound(tag);
		}
		return circuit;

	}

	public Item circuitType(int t) {
		switch (t) {
		case 2:
			return Calculator.circuitDamaged;
		case 3:
			return Calculator.circuitDirty;
		}
		return Calculator.circuitBoard;

	}
  
}
