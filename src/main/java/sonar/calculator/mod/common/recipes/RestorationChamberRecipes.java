package sonar.calculator.mod.common.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.recipes.DefinedRecipeHelper;

public class RestorationChamberRecipes extends DefinedRecipeHelper<CalculatorRecipe> {

	private static final RestorationChamberRecipes recipes = new RestorationChamberRecipes();

	public RestorationChamberRecipes() {
		super(1, 1, false);
	}

    public static RestorationChamberRecipes instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		for (int i = 0; i < 14; i++) {
			addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, i);
		}
	}

	public void addCircuit(Item input, Item output, int par) {
		addRecipe(new ItemStack(input, 1, par), new ItemStack(output, 1, par));
	}

	@Override
	public String getRecipeID() {
		return "RestorationChamber";
	}
}
