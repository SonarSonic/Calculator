package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import sonar.calculator.mod.common.recipes.machines.RestorationChamberRecipes;
import sonar.calculator.mod.integration.nei.handlers.RestorationRecipeHandler.SmeltingPair;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class ReassemblyRecipeHandler
  extends TemplateRecipeHandler
{
  public class SmeltingPair
    extends TemplateRecipeHandler.CachedRecipe
  {
    PositionedStack input;
    PositionedStack result;
    
    public SmeltingPair(Object input, Object result)
    {
      super();
      this.input = new PositionedStack(input, 48, 13);
      this.result = new PositionedStack(result, 102, 13);
    }
    
    @Override
	public List<PositionedStack> getIngredients() {
      return getCycledIngredients(ReassemblyRecipeHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] { this.input }));
    }
    
    @Override
	public PositionedStack getResult() {
      return this.result;
    }
  }
  



  @Override
public void loadTransferRects()
  {
    this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(66, 19, 24, 10), "reassembly", new Object[0]));
    this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(49-5, 63-11, 78, 10), "calculatordischarge", new Object[0]));


  }
  
  @Override
public Class<? extends GuiContainer> getGuiClass()
  {
    return GuiSmeltingBlock.ReassemblyChamber.class;
  }
  
	@Override
	public String getRecipeName() {
		return StatCollector
				.translateToLocal("tile.ReassemblyChamber.name");
	}

  
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("restoration"))	&& (getClass() == ReassemblyRecipeHandler.class)) {
			Map<Object[], Object[]> recipes = RestorationChamberRecipes.instance().getRecipes();
			for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getValue()[0]));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object[], Object[]> recipes = RestorationChamberRecipes.instance().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			int pos = RestorationChamberRecipes.instance().containsStack(result,	recipe.getValue(), false);
			if (pos != -1) {
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getValue()[0]));
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("restoration"))&& (getClass() == ReassemblyRecipeHandler.class)) {
			loadCraftingRecipes("restoration", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object[], Object[]> recipes = RestorationChamberRecipes.instance().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			int pos = RestorationChamberRecipes.instance().containsStack(ingredient, recipe.getKey(), false);
			if (pos != -1) {
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getValue()[0]));
			}
		}
	}

  @Override
public String getGuiTexture() {
    return "Calculator:textures/gui/nei/restorationchamber.png";
  }
  
  @Override
public void drawExtras(int recipe)
  {
    drawProgressBar(71, 13, 176, 10, 24, 16, 48, 0);
  }
  



  @Override
public String getOverlayIdentifier()
  {
    return "reassembly";
  }
}
