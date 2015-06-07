package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.calculators.GuiFlawlessCalculator;
import sonar.calculator.mod.integration.nei.FlawlessCalcNEIRecipes;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;



public class FlawlessRecipeHandler
  extends TemplateRecipeHandler
{
  public class SmeltingPair
    extends TemplateRecipeHandler.CachedRecipe
  {
    PositionedStack input;
    PositionedStack input2;
    PositionedStack input3;
    PositionedStack input4;
    PositionedStack output;
    
    public SmeltingPair(ItemStack input, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack output)
    {
      super();
      input.stackSize = 1;
      this.input = new PositionedStack(input, 12, 24);
      this.input2 = new PositionedStack(input2, 44, 24);
      this.input3 = new PositionedStack(input3, 76, 24);
      this.input4 = new PositionedStack(input4, 108, 24);
      this.output = new PositionedStack(output, 140, 24);
    }
    
    @Override
	public List<PositionedStack> getIngredients() {
      return Arrays.asList(new PositionedStack[] { this.input, this.input2, this.input3, this.input4 });
    }
    
    @Override
	public PositionedStack getResult() {
      return this.output;
    }
  }
  


  @Override
public void loadTransferRects()
  {
    this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(127, 28, 10, 8), "flawless", new Object[0]));
  }
  
  @Override
public Class<? extends GuiContainer> getGuiClass()
  {
    return GuiFlawlessCalculator.class;
  }
  
  @Override
public String getRecipeName()
  {
    return StatCollector.translateToLocal("item.FlawlessCalculator.name");
  }
  
  @Override
public void loadCraftingRecipes(String outputId, Object... results) {
    if ((outputId.equals("flawless")) && (getClass() == FlawlessRecipeHandler.class)) {
      Map<ItemStack, ItemStack> recipes = FlawlessCalcNEIRecipes.smelting().getSmeltingList();
      for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
    	if(CalculatorConfig.isEnabled(recipe.getKey())){
        this.arecipes.add(new SmeltingPair(recipe.getValue(), FlawlessCalcNEIRecipes.smelting().getSmeltingResult2(recipe.getKey()), FlawlessCalcNEIRecipes.smelting().getSmeltingResult3(recipe.getKey()), FlawlessCalcNEIRecipes.smelting().getSmeltingResult4(recipe.getKey()), recipe.getKey()));
    	}
    } else {
      super.loadCraftingRecipes(outputId, results);
    }
  }
  
  @Override
public void loadCraftingRecipes(ItemStack result) {
	if(!CalculatorConfig.isEnabled(result)){  
		return;
	}
    Map<ItemStack, ItemStack> recipes = FlawlessCalcNEIRecipes.smelting().getSmeltingList();
    

    for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
      if (NEIServerUtils.areStacksSameType(recipe.getKey(), result)) {
    	        this.arecipes.add(new SmeltingPair(recipe.getValue(), FlawlessCalcNEIRecipes.smelting().getSmeltingResult2(recipe.getKey()), FlawlessCalcNEIRecipes.smelting().getSmeltingResult3(recipe.getKey()), FlawlessCalcNEIRecipes.smelting().getSmeltingResult4(recipe.getKey()), recipe.getKey()));
    	 
      }
    }
    if(result.getItem()==Calculator.itemLocatorModule){
        this.arecipes.add(new SmeltingPair(new ItemStack(Calculator.itemEnergyModule), new ItemStack(Calculator.itemCalculator), new ItemStack(Calculator.itemCalculator), new ItemStack(Calculator.itemEnergyModule), result));

    }
  }
  


  @Override
public void loadUsageRecipes(String inputId, Object... ingredients)
  {
    if ((inputId.equals("flawless")) && (getClass() == FlawlessRecipeHandler.class)) {
      loadCraftingRecipes("flawless", new Object[0]);
    } else {
      super.loadUsageRecipes(inputId, ingredients);
    }
  }
  
  @Override
public void loadUsageRecipes(ItemStack ingredient) {
    Map<ItemStack, ItemStack> recipes = FlawlessCalcNEIRecipes.smelting().getSmeltingList();
    Map<ItemStack, ItemStack> recipes2 = FlawlessCalcNEIRecipes.smelting().getSmeltingList2();
    Map<ItemStack, ItemStack> recipes3 = FlawlessCalcNEIRecipes.smelting().getSmeltingList3();
    Map<ItemStack, ItemStack> recipes4 = FlawlessCalcNEIRecipes.smelting().getSmeltingList4();
    for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
      if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue(), ingredient)) {

      	if(CalculatorConfig.isEnabled(recipe.getKey())){
        SmeltingPair arecipe = new SmeltingPair(recipe.getValue(), FlawlessCalcNEIRecipes.smelting().getSmeltingResult2(recipe.getKey()), FlawlessCalcNEIRecipes.smelting().getSmeltingResult3(recipe.getKey()), FlawlessCalcNEIRecipes.smelting().getSmeltingResult4(recipe.getKey()), recipe.getKey());
        arecipe.setIngredientPermutation(Arrays.asList(new PositionedStack[] { arecipe.input }), ingredient);
        this.arecipes.add(arecipe);
      	}
      }
    }
    for (Map.Entry<ItemStack, ItemStack> recipe2 : recipes2.entrySet()) {
      if (NEIServerUtils.areStacksSameTypeCrafting(recipe2.getValue(), ingredient)) {

      	if(CalculatorConfig.isEnabled(recipe2.getKey())){
        SmeltingPair arecipe = new SmeltingPair(FlawlessCalcNEIRecipes.smelting().getSmeltingResult(recipe2.getKey()), recipe2.getValue(), FlawlessCalcNEIRecipes.smelting().getSmeltingResult3(recipe2.getKey()), FlawlessCalcNEIRecipes.smelting().getSmeltingResult4(recipe2.getKey()), recipe2.getKey());
        arecipe.setIngredientPermutation(Arrays.asList(new PositionedStack[] { arecipe.input }), ingredient);
        this.arecipes.add(arecipe);
      	}
      }
    }
    for (Map.Entry<ItemStack, ItemStack> recipe3 : recipes3.entrySet()) {
      if (NEIServerUtils.areStacksSameTypeCrafting(recipe3.getValue(), ingredient)) {

      	if(CalculatorConfig.isEnabled(recipe3.getKey())){
        SmeltingPair arecipe = new SmeltingPair(FlawlessCalcNEIRecipes.smelting().getSmeltingResult(recipe3.getKey()), FlawlessCalcNEIRecipes.smelting().getSmeltingResult2(recipe3.getKey()), recipe3.getValue(), FlawlessCalcNEIRecipes.smelting().getSmeltingResult4(recipe3.getKey()), recipe3.getKey());
        arecipe.setIngredientPermutation(Arrays.asList(new PositionedStack[] { arecipe.input }), ingredient);
        this.arecipes.add(arecipe);
      	}
      }
    }
    for (Map.Entry<ItemStack, ItemStack> recipe4 : recipes4.entrySet()) {
      if (NEIServerUtils.areStacksSameTypeCrafting(recipe4.getValue(), ingredient)) {

      	if(CalculatorConfig.isEnabled(recipe4.getKey())){
        SmeltingPair arecipe = new SmeltingPair(FlawlessCalcNEIRecipes.smelting().getSmeltingResult(recipe4.getKey()), FlawlessCalcNEIRecipes.smelting().getSmeltingResult2(recipe4.getKey()), FlawlessCalcNEIRecipes.smelting().getSmeltingResult3(recipe4.getKey()), recipe4.getValue(), recipe4.getKey());
        arecipe.setIngredientPermutation(Arrays.asList(new PositionedStack[] { arecipe.input }), ingredient);
        this.arecipes.add(arecipe);
      	}
      }
    }
  }
  
  @Override
public String getGuiTexture()
  {
    return "Calculator:textures/gui/flawlesscalculator.png";
  }
  


  @Override
public String getOverlayIdentifier()
  {
    return "flawless";
  }
}
