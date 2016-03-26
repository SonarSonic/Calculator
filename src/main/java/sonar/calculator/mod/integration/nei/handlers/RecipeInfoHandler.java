package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;

import net.minecraft.client.gui.inventory.GuiContainer;
import sonar.calculator.mod.client.gui.modules.GuiRecipeInfo;

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
			for (Map.Entry<Integer, CalculatorRecipe> recipeOutput : recipes.entrySet()) {
				if (unblocked != null && unblocked.length >= 1) {
					if (recipeOutput.getValue().hidden
							&& unblocked[CalculatorRecipes.recipes().getID(
									recipeOutput.getValue().input)] != 0
							&& unblocked[CalculatorRecipes.recipes().getID(
									recipeOutput.getValue().input2)] != 0
							|| unblocked[CalculatorRecipes.recipes().getID(
									recipeOutput.getValue().output)] != 0) {
						this.arecipes.add(new SmeltingPair(
								((CalculatorRecipe) recipeOutput.getValue()).input,
								((CalculatorRecipe) recipeOutput.getValue()).input2,
								((CalculatorRecipe) recipeOutput.getValue()).output));
					}
				 else if (!recipeOutput.getValue().hidden) {
					this.arecipes.add(new SmeltingPair(
							((CalculatorRecipe) recipeOutput.getValue()).input,
							((CalculatorRecipe) recipeOutput.getValue()).input2,
							((CalculatorRecipe) recipeOutput.getValue()).output));
				}
				}
			}
		}
		/*
		
		else if ((outputId.equals("New-Recipes"))&& (getClass() == RecipeInfoHandler.class)) {
			Map<Integer, CalculatorRecipe> recipes = CalculatorRecipes.recipes().getStandardList();
			for (Map.Entry<Integer, CalculatorRecipe> recipeOutput : recipes.entrySet()) {
				if (newRecipes != null && newRecipes.length >= 1) {
					if (recipeOutput.getValue().hidden
							&& newRecipes[CalculatorRecipes.recipes().getID(
									recipeOutput.getValue().input)] != 0
							|| newRecipes[CalculatorRecipes.recipes().getID(
									recipeOutput.getValue().input2)] != 0
							|| newRecipes[CalculatorRecipes.recipes().getID(
									recipeOutput.getValue().output)] != 0) {
						if(unblocked[CalculatorRecipes.recipes().getID(
											recipeOutput.getValue().input)] != 0
									&& unblocked[CalculatorRecipes.recipes().getID(
											recipeOutput.getValue().input2)] != 0
									|| unblocked[CalculatorRecipes.recipes().getID(
											recipeOutput.getValue().output)] != 0){
						this.arecipes.add(new SmeltingPair(
								((CalculatorRecipe) recipeOutput.getValue()).input,
								((CalculatorRecipe) recipeOutput.getValue()).input2,
								((CalculatorRecipe) recipeOutput.getValue()).output));
						this.transferRects.clear();
					}
					}
				}
			}		
		}
		
		else if ((outputId.equals("Standard-Recipes"))&& (getClass() == RecipeInfoHandler.class)) {
			Map<Integer, CalculatorRecipe> recipes = CalculatorRecipes.recipes().getStandardList();
			for (Map.Entry<Integer, CalculatorRecipe> recipeOutput : recipes.entrySet()) {
					if (!recipeOutput.getValue().hidden) {
						this.arecipes.add(new SmeltingPair(
								((CalculatorRecipe) recipeOutput.getValue()).input,
								((CalculatorRecipe) recipeOutput.getValue()).input2,
								((CalculatorRecipe) recipeOutput.getValue()).output));
						this.transferRects.clear();
					}
				
			}		
		}else if ((outputId.equals("Unlocked-Recipes"))&& (getClass() == RecipeInfoHandler.class)) {
			Map<Integer, CalculatorRecipe> recipes = CalculatorRecipes.recipes().getStandardList();
			for (Map.Entry<Integer, CalculatorRecipe> recipeOutput : recipes.entrySet()) {
				if (unblocked != null && unblocked.length >= 1) {
					if (recipeOutput.getValue().hidden
							&& (unblocked[CalculatorRecipes.recipes().getID(
									recipeOutput.getValue().input)] != 0
							&& unblocked[CalculatorRecipes.recipes().getID(
									recipeOutput.getValue().input2)] != 0
							|| unblocked[CalculatorRecipes.recipes().getID(
									recipeOutput.getValue().output)] != 0)) {
						this.arecipes.add(new SmeltingPair(
								((CalculatorRecipe) recipeOutput.getValue()).input,
								((CalculatorRecipe) recipeOutput.getValue()).input2,
								((CalculatorRecipe) recipeOutput.getValue()).output));
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
