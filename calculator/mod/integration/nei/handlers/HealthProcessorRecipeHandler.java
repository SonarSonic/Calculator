package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.client.gui.machines.GuiHealthProcessor;
import sonar.calculator.mod.common.recipes.machines.HealthProcessorRecipes;
import sonar.core.utils.helpers.FontHelper;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class HealthProcessorRecipeHandler
  extends TemplateRecipeHandler
{
  public class ChancePair
    extends TemplateRecipeHandler.CachedRecipe
  {
    PositionedStack result;
    public int value;
    
    public ChancePair(ItemStack result, int chance)
    {
      super();
      result.stackSize = 1;
      this.result = new PositionedStack(result, 80-5, 34-11);
      this.value=chance;
    }
        
    @Override
	public PositionedStack getResult() {
      return this.result;
    }
  }
  



  @Override
public void loadTransferRects()
  {
	this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(84-11, 32-5, 16, 8), "healthvalue", new Object[0]));

  }
  
  @Override
public Class<? extends GuiContainer> getGuiClass()
  {
    return GuiHealthProcessor.class;
  }
  
	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("tile.HealthProcessor.name");
	}

  
  @Override
public void loadCraftingRecipes(ItemStack result) {
	  if(HealthProcessorRecipes.instance().getHealthValue(result)!=0){  
		  this.arecipes.add(new ChancePair(result,HealthProcessorRecipes.instance().getHealthValue(result)));
	  }
  }
  
  @Override
public void loadCraftingRecipes(String outputId, Object... results) {
    if ((outputId.equals("healthvalue")) && (getClass() == HealthProcessorRecipeHandler.class)) {
      Map<ItemStack, Integer> recipes = HealthProcessorRecipes.instance().getHealthList();
      for (Map.Entry<ItemStack, Integer> recipe : recipes.entrySet())
        this.arecipes.add(new ChancePair(recipe.getKey(), recipe.getValue()));
      	this.transferRects.clear();
    } else {
      super.loadCraftingRecipes(outputId, results);
    }
  }
  @Override
public void loadUsageRecipes(ItemStack ingredient)
  {
	  if(HealthProcessorRecipes.instance().getHealthValue(ingredient)!=0){  
		  this.arecipes.add(new ChancePair(ingredient,HealthProcessorRecipes.instance().getHealthValue(ingredient)));
	  }
  }
  @Override
public String getGuiTexture() {
    return "Calculator:textures/gui/guicalculatorplug.png";
  }
  
  @Override
public void drawExtras(int recipe)
  {
	  ChancePair pair = (ChancePair) this.arecipes.get(recipe);
	  int value = pair.value;
	  FontHelper.textCentre("Health Points = " + value, 176, 8, 0);
	  
  }
  



  @Override
public String getOverlayIdentifier()
  {
    return "circuitextraction";
  }



}
