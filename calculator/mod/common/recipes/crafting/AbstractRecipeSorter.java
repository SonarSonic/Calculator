package sonar.calculator.mod.common.recipes.crafting;

import java.util.Comparator;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

public class AbstractRecipeSorter
  implements Comparator
{
  final BaseManager manager;
  
  public AbstractRecipeSorter(BaseManager manager)
  {
    this.manager = manager;
  }
  
  public int compareRecipes(IRecipe irecipe1, IRecipe irecipe2)
  {
    return irecipe2.getRecipeSize() > irecipe1.getRecipeSize() ? 1 : irecipe2.getRecipeSize() < irecipe1.getRecipeSize() ? -1 : ((irecipe2 instanceof ShapelessRecipes)) && ((irecipe1 instanceof ShapedRecipes)) ? -1 : ((irecipe1 instanceof ShapelessRecipes)) && ((irecipe2 instanceof ShapedRecipes)) ? 1 : 0;
  }
  

  @Override
public int compare(Object o1, Object o2)
  {
    return compareRecipes((IRecipe)o1, (IRecipe)o2);
  }
}
