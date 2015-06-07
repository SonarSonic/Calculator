package sonar.calculator.mod.integration.nei.handlers;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.client.gui.calculators.GuiInfoCalculator;
import sonar.calculator.mod.client.gui.modules.GuiRecipeInfo;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipe;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipes;
import sonar.calculator.mod.integration.nei.handlers.CalculatorRecipeHandler.SmeltingPair;
import sonar.calculator.mod.utils.InfoList;
import sonar.calculator.mod.utils.helpers.ResearchPlayer;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
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
		ResearchPlayer player =ResearchPlayer.get(Minecraft.getMinecraft().thePlayer);
		int[] unblocked = player.unblocked(Minecraft.getMinecraft().thePlayer);
		int[] newRecipes = player.newRecipes(Minecraft.getMinecraft().thePlayer);
		if ((outputId.equals("calculator"))	&& (getClass() == RecipeInfoHandler.class)) {
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
		}else if ((outputId.equals("New-Recipes"))&& (getClass() == RecipeInfoHandler.class)) {
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
		}else if ((outputId.equals("Standard-Recipes"))&& (getClass() == RecipeInfoHandler.class)) {
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
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "RecipeInfo";
	}
	
}
