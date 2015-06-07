package sonar.calculator.mod.common.recipes.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sonar.calculator.mod.CalculatorConfig;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

public class BaseManager {
	
	public int width;
	public int height;
	public boolean useNBT;
	public List recipes = new ArrayList();
	
	public BaseManager(int width, int height, boolean useNBT){
		this.width=width;
		this.height=height;
		this.useNBT = useNBT;
		
	}
	public AbstractShapedRecipes addRecipe(ItemStack result, Object ... matrix)
	  {
	      String s = "";
	      int i = 0;
	      int j = 0;
	      int k = 0;


	      if (matrix[i] instanceof String[])
	      {
	          String[] astring = ((String[])matrix[i++]);


	          for (int l = 0; l < astring.length; ++l)
	          {
	              String s1 = astring[l];
	              ++k;
	              j = s1.length();
	              s = s + s1;
	          }
	      }
	      else
	      {
	          while (matrix[i] instanceof String)
	          {
	              String s2 = (String)matrix[i++];
	              ++k;
	              j = s2.length();
	              s = s + s2;
	          }
	      }


	      HashMap hashmap;


	      for (hashmap = new HashMap(); i < matrix.length; i += 2)
	      {
	          Character character = (Character)matrix[i];
	          ItemStack itemstack1 = null;


	          if (matrix[i + 1] instanceof Item)
	          {
	              itemstack1 = new ItemStack((Item)matrix[i + 1]);
	          }
	          else if (matrix[i + 1] instanceof Block)
	          {
	              itemstack1 = new ItemStack((Block)matrix[i + 1], 1, 32767);
	          }
	          else if (matrix[i + 1] instanceof ItemStack)
	          {
	              itemstack1 = (ItemStack)matrix[i + 1];
	          }


	          hashmap.put(character, itemstack1);
	      }


	      ItemStack[] aitemstack = new ItemStack[j * k];


	      for (int i1 = 0; i1 < j * k; ++i1)
	      {
	          char c0 = s.charAt(i1);


	          if (hashmap.containsKey(Character.valueOf(c0)))
	          {
	              aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
	          }
	          else
	          {
	              aitemstack[i1] = null;
	          }
	      }


	      AbstractShapedRecipes shapedrecipes = new AbstractShapedRecipes(j, k, aitemstack, result);
	      this.recipes.add(shapedrecipes);
	      return shapedrecipes;
	  }


	  public void addShapelessRecipe(ItemStack result, Object ... matrix)
	  {
	      ArrayList arraylist = new ArrayList();
	      Object[] aobject = matrix;
	      int i = matrix.length;


	      for (int j = 0; j < i; ++j)
	      {
	          Object object1 = aobject[j];


	          if (object1 instanceof ItemStack)
	          {
	              arraylist.add(((ItemStack)object1).copy());
	          }
	          else if (object1 instanceof Item)
	          {
	              arraylist.add(new ItemStack((Item)object1));
	          }
	          else
	          {
	              if (!(object1 instanceof Block))
	              {
	                  throw new RuntimeException("Invalid shapeless recipe!");
	              }


	              arraylist.add(new ItemStack((Block)object1));
	          }
	      }


	      this.recipes.add(new ShapelessRecipes(result, arraylist));
	  }


	  public ItemStack findMatchingRecipe(InventoryCrafting matrix, World world)
	  {
	      int i = 0;
	      ItemStack itemstack = null;
	      ItemStack itemstack1 = null;
	      int j;


	      for (j = 0; j < matrix.getSizeInventory()-1; ++j)
	      {
	          ItemStack itemstack2 = matrix.getStackInSlot(j);

	          if (itemstack2 != null)
	          {
	              if (i == 0)
	              {
	                  itemstack = itemstack2;
	              }


	              if (i == 1)
	              {
	                  itemstack1 = itemstack2;
	              }


	              ++i;
	          }
	      }


	      if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isRepairable())
	      {
	          Item item = itemstack.getItem();
	          int j1 = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
	          int k = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
	          int l = j1 + k + item.getMaxDamage() * 5 / 100;
	          int i1 = item.getMaxDamage() - l;


	          if (i1 < 0)
	          {
	              i1 = 0;
	          }


	          return new ItemStack(itemstack.getItem(), 1, i1);
	      }
	      else
	      {
	          for (j = 0; j < this.recipes.size(); ++j)
	          {
	              IRecipe irecipe = (IRecipe)this.recipes.get(j);

	              if (irecipe.matches(matrix, world))
	              {
	            	  ItemStack result = irecipe.getCraftingResult(matrix);
	            	  if(CalculatorConfig.isEnabled(result)){
		                  return irecipe.getCraftingResult(matrix);
	            	  }else{
	            		  return null;
	            	  }
	              }
	          }


	          return null;
	      }
	  }
	  
}
