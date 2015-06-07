package sonar.calculator.mod.integration.nei.handlers;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.item.misc.ItemCircuit;
import sonar.calculator.mod.common.recipes.machines.AnalysingChamberRecipes;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;
import sonar.core.utils.helpers.FontHelper;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class CircuitBoardHandler extends TemplateRecipeHandler {

	public class Circuit

	extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack analysingchamber;
		public boolean energy;
		public boolean stable;
		public boolean recipe;

		public Circuit(ItemStack input, boolean energy, boolean stable, boolean recipe) {
			this.energy = energy;
			this.stable = stable;
			this.recipe = recipe;
			input.stackSize = 1;
			this.input = new PositionedStack(input, 20, 13);
			this.analysingchamber = new PositionedStack(new ItemStack(Calculator.analysingChamber,1), 20, 58-11);
		}

		@Override
		public PositionedStack getResult() {
			return input;
		}
		@Override
		public PositionedStack getOtherStack() {
			return analysingchamber;
		}
	}

	public static ArrayList<Circuit> acircuit;

	@Override
	public String getRecipeName() {
		return "Analysing Chamber";
	}

	@Override
	public void loadTransferRects() {

	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (ingredient.getItem() instanceof ItemCircuit) {
			if (ingredient.hasTagCompound()) {
				
				Circuit arecipe = new Circuit(ingredient, checkEnergy(ingredient),checkItem(ingredient),false);
				arecipe.setIngredientPermutation(
						Arrays.asList(new PositionedStack[] { arecipe.input }),
						ingredient);

				this.arecipes.add(arecipe);
			} else {
				Circuit arecipe = new Circuit(ingredient, false,false,false);
				arecipe.setIngredientPermutation(
						Arrays.asList(new PositionedStack[] { arecipe.input }),
						ingredient);

				this.arecipes.add(arecipe);
			}
		}

	}
	public boolean checkEnergy(ItemStack stack){		
		return TileEntityAnalysingChamber.itemEnergy(stack.stackTagCompound.getInteger("Energy"))>0;
	}
	public boolean checkItem(ItemStack stack){
		if(AnalysingChamberRecipes.instance().getResult(1, stack.stackTagCompound.getInteger("Item1"))!=null){
			return true;
		}
		else if(AnalysingChamberRecipes.instance().getResult(1, stack.stackTagCompound.getInteger("Item2"))!=null){
			return true;
		}
		else if(AnalysingChamberRecipes.instance().getResult(2, stack.stackTagCompound.getInteger("Item3"))!=null){
			return true;
		}
		else if(AnalysingChamberRecipes.instance().getResult(3, stack.stackTagCompound.getInteger("Item4"))!=null){
			return true;
		}
		else if(AnalysingChamberRecipes.instance().getResult(4, stack.stackTagCompound.getInteger("Item5"))!=null){
			return true;
		}
		else if(AnalysingChamberRecipes.instance().getResult(5, stack.stackTagCompound.getInteger("Item6"))!=null){
			return true;
		}
	
		return false;
	}
	public boolean checkStable(ItemStack stack){		
		return stack.stackTagCompound.getInteger("Stable")==1;
	}
	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/analysingchamber_nei.png";
	}

	@Override
	public void drawExtras(int recipe) {
	
		ItemStack stack = arecipes.get(recipe).getResult().item;
		if(stack.hasTagCompound()){
		String energy = "RF Stored: " + this.checkEnergy(stack);
		String item = "Items Stored: " + this.checkItem(stack);
		String stable = "Stable: " + this.checkStable(stack);
		FontHelper.text(energy, 75-5,	18 - 11, 0);
		FontHelper.text(item, 75-5,	30 - 11, 0);		
		FontHelper.text(stable, 75-5,	42 - 11, 0);
		}
		if(!stack.hasTagCompound()){
			String energy = "RF Stored: false";
			String item = "Items Stored: false";
			String stable = "Stable: false";
			FontHelper.text(energy, 75-5,	18 - 11, 0);
			FontHelper.text(energy, 75-5,	18 - 11, 0);
			FontHelper.text(item, 75-5,	30 - 11,0);		
			FontHelper.text(stable, 75-5,	42 - 11, 0);
			}
		FontHelper.text("See Analysing Chamber", 46-5,	59 - 11, 0);
		

	}

	@Override
	public int recipiesPerPage() {

		return 1;

	}

	@Override
	public String getOverlayIdentifier() {
		return "analysingchamber";
	}

}
