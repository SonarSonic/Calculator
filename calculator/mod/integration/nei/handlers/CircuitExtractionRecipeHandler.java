package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.machines.GuiAnalysingChamber;
import sonar.calculator.mod.common.recipes.machines.AnalysingChamberRecipes;
import sonar.core.utils.helpers.FontHelper;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class CircuitExtractionRecipeHandler
  extends TemplateRecipeHandler
{
  public class ChancePair
    extends TemplateRecipeHandler.CachedRecipe
  {
    PositionedStack result;
    public int chance;
    
    public ChancePair(ItemStack result, int chance)
    {
      super();
      result.stackSize = 1;
      this.result = new PositionedStack(result, 80-5, 34-11);
      this.chance=chance;
    }
        
    @Override
	public PositionedStack getResult() {
      return this.result;
    }
  }
  



  @Override
public void loadTransferRects()
  {
	this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(26-11, 20-5, 116, 8), "circuitextraction", new Object[0]));

  }
  
  @Override
public Class<? extends GuiContainer> getGuiClass()
  {
    return GuiAnalysingChamber.class;
  }
  
  @Override
public String getRecipeName()
  {
    return "Analysing Chamber";
  }
  
  @Override
public void loadCraftingRecipes(ItemStack result) {
	  if(!CalculatorConfig.isEnabled(result)){
		  return;
	  }
	  if(AnalysingChamberRecipes.instance().getChance(result)!=0){  
		  this.arecipes.add(new ChancePair(result,AnalysingChamberRecipes.instance().getChance(result)));
    	  
	  }
  }
  
  @Override
public void loadCraftingRecipes(String outputId, Object... results) {
    if ((outputId.equals("circuitextraction")) && (getClass() == CircuitExtractionRecipeHandler.class)) {
      Map<ItemStack, Integer> recipes = AnalysingChamberRecipes.instance().getChanceList();
      for (Map.Entry<ItemStack, Integer> recipe : recipes.entrySet())
    	  if(CalculatorConfig.isEnabled(recipe.getKey())){
        this.arecipes.add(new ChancePair(recipe.getKey(), recipe.getValue()));
    	  }
      	this.transferRects.clear();
    } else {
      super.loadCraftingRecipes(outputId, results);
    }
  }
  @Override
public void loadUsageRecipes(ItemStack ingredient)
  {
	 if(ingredient.getItem()== Calculator.circuitBoard){
		Map<ItemStack, Integer> recipes = AnalysingChamberRecipes.instance().getChanceList();
	 for (Map.Entry<ItemStack, Integer> recipe : recipes.entrySet())
   	  if(CalculatorConfig.isEnabled(recipe.getKey())){
		 this.arecipes.add(new ChancePair(recipe.getKey(), recipe.getValue()));
	 		this.transferRects.clear();
   	  }
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
	  int chance = pair.chance;
	  switch (chance){
	  case 1: FontHelper.textCentre(StatCollector.translateToLocal("info.extractChance") + " = 6 %", 176, 8, 0); break;
	  case 2: FontHelper.textCentre(StatCollector.translateToLocal("info.extractChance") + " = 0.2 %", 176, 8, 0); break;
	  case 3: FontHelper.textCentre(StatCollector.translateToLocal("info.extractChance") + " = 0.1 %", 176, 8, 0); break;
	  case 4: FontHelper.textCentre(StatCollector.translateToLocal("info.extractChance") + " = 0.02 %", 176, 8, 0); break;
	  case 5: FontHelper.textCentre(StatCollector.translateToLocal("info.extractChance") + " = 0.01 %", 176, 8, 0); break;
	  
	  }
  }
  



  @Override
public String getOverlayIdentifier()
  {
    return "circuitextraction";
  }



}
