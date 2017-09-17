package sonar.calculator.mod.common.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.Calculator;
import sonar.core.SonarCore;
import sonar.core.recipes.DefinedRecipeHelper;
import sonar.core.recipes.RecipeItemStack;

public class ExtractionChamberRecipes extends DefinedRecipeHelper<CalculatorRecipe> {

	private static final ExtractionChamberRecipes recipes = new ExtractionChamberRecipes();

	public ExtractionChamberRecipes() {
		super(1, 2, false);
	}

    public static ExtractionChamberRecipes instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		addRecipe(Blocks.DIRT, new ItemStack(Calculator.soil, 1), new ExtractionChamberOutput(new ItemStack(Calculator.circuitDirty, 1, OreDictionary.WILDCARD_VALUE)));
		addRecipe("cobblestone", new ItemStack(Calculator.small_stone, 1), new ExtractionChamberOutput(new ItemStack(Calculator.circuitDamaged, 1, OreDictionary.WILDCARD_VALUE)));
	}

	@Override
	public String getRecipeID() {
		return "ExtractionChamber";
	}

	public static class ExtractionChamberOutput extends RecipeItemStack {

		public ExtractionChamberOutput(ItemStack stack) {
			super(stack, true);
		}

		@Override
		public ItemStack getOutputStack() {
			ItemStack circuit = stack.copy();
			if (SonarCore.rand.nextInt(8 + 1) == 8) {
				circuit.setItemDamage(SonarCore.rand.nextInt(13 + 1));
			} else {
				circuit = ItemStack.EMPTY;
			}
			return circuit;
		}
	}
}
