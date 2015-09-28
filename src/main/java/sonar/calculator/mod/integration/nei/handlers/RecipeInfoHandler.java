package sonar.calculator.mod.integration.nei.handlers;


import java.awt.Rectangle;

import net.minecraft.client.gui.inventory.GuiContainer;
import sonar.calculator.mod.client.gui.modules.GuiRecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class RecipeInfoHandler extends CalculatorRecipeHandler {

	
	@Override
	public void loadTransferRects() {
		
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(
				new Rectangle(50, 2, 72, 12), "calculator", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(
				new Rectangle(50, 20, 72, 12), "New-Recipes", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(
				new Rectangle(50, 38, 72, 12), "Standard-Recipes", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(
				new Rectangle(50, 56, 72, 12), "Unlocked-Recipes", new Object[0]));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiRecipeInfo.class;
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		/*
		if ((outputId.equals("calculator"))	&& (getClass() == RecipeInfoHandler.class) && Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() == Calculator.itemCalculator) {

			int[] unblocked = CalculatorItem.getResearch(Minecraft.getMinecraft().thePlayer.getHeldItem());
			Map<Integer, CalculatorRecipe> recipes = CalculatorRecipes.recipes().getStandardList();
			for (Map.Entry<Integer, CalculatorRecipe> recipe : recipes.entrySet()) {
				if (unblocked != null && unblocked.length >= 1) {
					if (recipe.getValue().hidden
							&& unblocked[CalculatorRecipes.recipes().getID(
									recipe.getValue().input)] != 0
							&& unblocked[CalculatorRecipes.recipes().getID(
									recipe.getValue().input2)] != 0
							|| unblocked[CalculatorRecipes.recipes().getID(
									recipe.getValue().output)] != 0) {
						this.arecipes.add(new SmeltingPair(
								((CalculatorRecipe) recipe.getValue()).input,
								((CalculatorRecipe) recipe.getValue()).input2,
								((CalculatorRecipe) recipe.getValue()).output));
					}
				 else if (!recipe.getValue().hidden) {
					this.arecipes.add(new SmeltingPair(
							((CalculatorRecipe) recipe.getValue()).input,
							((CalculatorRecipe) recipe.getValue()).input2,
							((CalculatorRecipe) recipe.getValue()).output));
				}
				}
			}
		}
		/*
		
		else if ((outputId.equals("New-Recipes"))&& (getClass() == RecipeInfoHandler.class)) {
			Map<Integer, CalculatorRecipe> recipes = CalculatorRecipes.recipes().getStandardList();
			for (Map.Entry<Integer, CalculatorRecipe> recipe : recipes.entrySet()) {
				if (newRecipes != null && newRecipes.length >= 1) {
					if (recipe.getValue().hidden
							&& newRecipes[CalculatorRecipes.recipes().getID(
									recipe.getValue().input)] != 0
							|| newRecipes[CalculatorRecipes.recipes().getID(
									recipe.getValue().input2)] != 0
							|| newRecipes[CalculatorRecipes.recipes().getID(
									recipe.getValue().output)] != 0) {
						if(unblocked[CalculatorRecipes.recipes().getID(
											recipe.getValue().input)] != 0
									&& unblocked[CalculatorRecipes.recipes().getID(
											recipe.getValue().input2)] != 0
									|| unblocked[CalculatorRecipes.recipes().getID(
											recipe.getValue().output)] != 0){
						this.arecipes.add(new SmeltingPair(
								((CalculatorRecipe) recipe.getValue()).input,
								((CalculatorRecipe) recipe.getValue()).input2,
								((CalculatorRecipe) recipe.getValue()).output));
						this.transferRects.clear();
					}
					}
				}
			}		
		}
		
		else if ((outputId.equals("Standard-Recipes"))&& (getClass() == RecipeInfoHandler.class)) {
			Map<Integer, CalculatorRecipe> recipes = CalculatorRecipes.recipes().getStandardList();
			for (Map.Entry<Integer, CalculatorRecipe> recipe : recipes.entrySet()) {
					if (!recipe.getValue().hidden) {
						this.arecipes.add(new SmeltingPair(
								((CalculatorRecipe) recipe.getValue()).input,
								((CalculatorRecipe) recipe.getValue()).input2,
								((CalculatorRecipe) recipe.getValue()).output));
						this.transferRects.clear();
					}
				
			}		
		}else if ((outputId.equals("Unlocked-Recipes"))&& (getClass() == RecipeInfoHandler.class)) {
			Map<Integer, CalculatorRecipe> recipes = CalculatorRecipes.recipes().getStandardList();
			for (Map.Entry<Integer, CalculatorRecipe> recipe : recipes.entrySet()) {
				if (unblocked != null && unblocked.length >= 1) {
					if (recipe.getValue().hidden
							&& (unblocked[CalculatorRecipes.recipes().getID(
									recipe.getValue().input)] != 0
							&& unblocked[CalculatorRecipes.recipes().getID(
									recipe.getValue().input2)] != 0
							|| unblocked[CalculatorRecipes.recipes().getID(
									recipe.getValue().output)] != 0)) {
						this.arecipes.add(new SmeltingPair(
								((CalculatorRecipe) recipe.getValue()).input,
								((CalculatorRecipe) recipe.getValue()).input2,
								((CalculatorRecipe) recipe.getValue()).output));
						this.transferRects.clear();
					}
				}				
			}		
		
	}
		*/
	}
	
	
	@Override
	public String getOverlayIdentifier() {
		return "RecipeInfo";
	}
	
}
