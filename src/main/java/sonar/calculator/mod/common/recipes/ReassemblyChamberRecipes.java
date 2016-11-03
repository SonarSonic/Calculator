package sonar.calculator.mod.common.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.recipes.DefinedRecipeHelper;

public class ReassemblyChamberRecipes extends DefinedRecipeHelper {

	private static final ReassemblyChamberRecipes recipes = new ReassemblyChamberRecipes();

	public ReassemblyChamberRecipes() {
		super(1, 1, false);
	}

	public static final ReassemblyChamberRecipes instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 0);
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 1);
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 2);
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 3);
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 4);
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 5);
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 6);
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 7);
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 8);
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 9);
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 10);
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 11);
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 12);
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 13);
	}

	public void addCircuit(Item input, Item output, int par) {
		addRecipe(new ItemStack(input, 1, par), new ItemStack(output, 1, par));
	}

	@Override
	public String getRecipeID() {
		return "ReassemblyChamber";
	}
}
