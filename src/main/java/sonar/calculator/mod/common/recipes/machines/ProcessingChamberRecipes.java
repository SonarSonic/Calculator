package sonar.calculator.mod.common.recipes.machines;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.utils.helpers.RecipeHelper;

public class ProcessingChamberRecipes extends RecipeHelper {

	private static final ProcessingChamberRecipes recipes = new ProcessingChamberRecipes();

	public ProcessingChamberRecipes() {
		super(1, 1, false);
	}

	public static final RecipeHelper instance() {
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
		addCircuit(Calculator.circuitDamaged, Calculator.circuitBoard, 14);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 0);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 1);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 2);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 3);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 4);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 5);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 6);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 7);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 8);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 9);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 10);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 11);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 12);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 13);
		addCircuit(Calculator.circuitDirty, Calculator.circuitBoard, 14);
	}

	public void addCircuit(Item input, Item output, int par) {
		addRecipe(new ItemStack(input, 1, par), new ItemStack(output, 1, par));
	}

	@Override
	public String getRecipeID() {
		return "ProcessingChamber";
	}
}
