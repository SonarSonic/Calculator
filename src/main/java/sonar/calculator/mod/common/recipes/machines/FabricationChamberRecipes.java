package sonar.calculator.mod.common.recipes.machines;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.Calculator;
import sonar.core.helpers.IRecipeHelper;
import sonar.core.helpers.ItemStackHelper;

public class FabricationChamberRecipes implements IRecipeHelper {

	private static final FabricationChamberRecipes instance = new FabricationChamberRecipes();
	protected LinkedHashMap<ItemStack, CircuitStack[]> recipes = new LinkedHashMap<ItemStack, CircuitStack[]>();
	protected LinkedHashMap<CircuitStack[], ItemStack> recipes_reverse = new LinkedHashMap<CircuitStack[], ItemStack>();

	public FabricationChamberRecipes() {
		this.addRecipes();
	}

	public static FabricationChamberRecipes getInstance() {
		return instance;
	}

	public LinkedHashMap<ItemStack, CircuitStack[]> getRecipes() {
		return recipes;
	}

	public LinkedHashMap<CircuitStack[], ItemStack> getRecipesReversed() {
		return recipes_reverse;
	}

	public void addRecipes() {
		createRecipe(new ItemStack(Calculator.atomic_module, 1), c(0, 1, true), c(1, 1, true), c(2, 1, true), c(3, 1, true), c(4, 1, true), c(5, 1, true), c(6, 1, true), c(7, 1, true), c(8, 1, true), c(9, 1, true), c(10, 1, true), c(11, 1, true), c(12, 1, true), c(13, 1, true));
		createRecipe(new ItemStack(Calculator.atomic_assembly, 1), c(0, 4, false), c(1, 4, false), c(2, 4, false), c(3, 4, false), c(4, 4, false), c(5, 4, false), c(6, 4, false), c(7, 4, false), c(8, 4, false), c(9, 4, false), c(10, 4, false), c(11, 4, false), c(12, 4, false), c(13, 4, false));
		createRecipe(new ItemStack(Calculator.calculator_screen, 1), c(0, 1, false));
		createRecipe(new ItemStack(Calculator.itemCalculator, 1), c(0, 1, false), c(1, 1, false), c(2, 1, false));
		createRecipe(new ItemStack(Calculator.itemScientificCalculator, 1), c(4, 1, false), c(5, 1, false), c(6, 1, false));
		createRecipe(new ItemStack(Calculator.itemWarpModule, 1), c(5, 50, false));
		createRecipe(new ItemStack(Calculator.speedUpgrade, 1), c(6, 1, false));
		createRecipe(new ItemStack(Calculator.energyUpgrade, 1), c(7, 1, false));
		createRecipe(new ItemStack(Calculator.voidUpgrade, 1), c(8, 1, false));
		createRecipe(new ItemStack(Calculator.transferUpgrade, 1), c(9, 1, false));
		createRecipe(new ItemStack(Calculator.calculator_assembly, 1), c(0, 3, false));
	}

	public void createRecipe(ItemStack stack, CircuitStack... circuits) {
		recipes.put(stack, circuits);
		recipes_reverse.put(circuits, stack);
	}

	public CircuitStack[] getRequirements(ItemStack stack) {
		for (Entry<ItemStack, CircuitStack[]> entry : recipes.entrySet()) {
			if (entry.getKey() != null && ItemStackHelper.equalStacksRegular(stack, entry.getKey())) {
				return entry.getValue().clone();
			}
		}
		return null;
	}

	public static boolean canPerformRecipe(CircuitStack[] recipe, ArrayList<CircuitStack> stored) {
		for (CircuitStack stack : recipe) {
			if (!containsCircuit(stored, stack)) {
				return false;
			}
		}
		return true;
	}

	public static boolean containsCircuit(ArrayList<CircuitStack> storedStacks, CircuitStack stack) {
		for (CircuitStack stored : storedStacks) {
			if (stored.stable == stack.stable && stored.meta == stack.meta && stack.required <= stored.required) {
				return true;
			}
		}
		return false;
	}

	public CircuitStack c(int meta, long required, boolean stable) {
		return new CircuitStack(meta, required, stable);
	}

	public static class CircuitStack {
		public int meta;
		public long required;
		public boolean stable;

		public CircuitStack(int meta, long required, boolean stable) {
			this.meta = meta;
			this.required = required;
			this.stable = stable;
		}

		public String toString() {
			return "C " + meta + ": " + required + " S: " + (stable ? "Y" : "N");
		}

		public CircuitStack clone() {
			return new CircuitStack(this.meta, this.required, this.stable);
		}

		public ItemStack buildItemStack() {
			ItemStack stack = new ItemStack(Calculator.circuitBoard, (int) required, meta);
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("Stable", stable ? 1 : 0);
			stack.setTagCompound(tag);
			return stack;
		}
	}

	@Override
	public String getRecipeID() {
		return "FabricationChamber";
	}

}
