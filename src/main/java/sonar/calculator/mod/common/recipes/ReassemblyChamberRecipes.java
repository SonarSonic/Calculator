package sonar.calculator.mod.common.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.recipes.DefinedRecipeHelper;

public class ReassemblyChamberRecipes extends DefinedRecipeHelper<CalculatorRecipe> {

	private static final ReassemblyChamberRecipes recipes = new ReassemblyChamberRecipes();

	public ReassemblyChamberRecipes() {
		super(1, 1, false);
	}

    public static ReassemblyChamberRecipes instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		for(int i=0;i<14;i++){
			addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, i);
		}
	}

	public void addCircuit(Item input, Item output, int par) {
		addRecipe(new ItemStack(input, 1, par), new ItemStack(output, 1, par));
	}

	@Override
	public String getRecipeID() {
		return "ReassemblyChamber";
	}
}
