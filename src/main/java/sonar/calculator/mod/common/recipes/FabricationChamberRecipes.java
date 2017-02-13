package sonar.calculator.mod.common.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.Calculator;
import sonar.core.recipes.DefaultSonarRecipe;
import sonar.core.recipes.ISonarRecipe;
import sonar.core.recipes.ISonarRecipeObject;
import sonar.core.recipes.RecipeHelperV2;
import sonar.core.recipes.RecipeObjectType;

public class FabricationChamberRecipes extends RecipeHelperV2<FabricationSonarRecipe> {

	private static FabricationChamberRecipes instance = new FabricationChamberRecipes();

	public FabricationChamberRecipes() {
		super();
		addRecipes();
	}

	public static FabricationChamberRecipes instance() {
		return instance;
	}

	@Override
	public String getRecipeID() {
		return "FabricationChamber";
	}

	@Override
	public void addRecipes() {
		addRecipe(new ItemStack(Calculator.atomic_module, 1), c(0, 1, true), c(1, 1, true), c(2, 1, true), c(3, 1, true), c(4, 1, true), c(5, 1, true), c(6, 1, true), c(7, 1, true), c(8, 1, true), c(9, 1, true), c(10, 1, true), c(11, 1, true), c(12, 1, true), c(13, 1, true));
		addRecipe(new ItemStack(Calculator.atomic_assembly, 1), c(0, 4, false), c(1, 4, false), c(2, 4, false), c(3, 4, false), c(4, 4, false), c(5, 4, false), c(6, 4, false), c(7, 4, false), c(8, 4, false), c(9, 4, false), c(10, 4, false), c(11, 4, false), c(12, 4, false), c(13, 4, false));
		addRecipe(new ItemStack(Calculator.calculator_screen, 1), c(0, 1, false));
		addRecipe(new ItemStack(Calculator.itemCalculator, 1), c(0, 1, false), c(1, 1, false), c(2, 1, false));
		addRecipe(new ItemStack(Calculator.itemScientificCalculator, 1), c(4, 1, false), c(5, 1, false), c(6, 1, false));
		addRecipe(new ItemStack(Calculator.itemWarpModule, 1), c(5, 50, false));
		addRecipe(new ItemStack(Calculator.itemJumpModule, 1), c(6, 50, false));
		addRecipe(new ItemStack(Calculator.speedUpgrade, 1), c(6, 1, false));
		addRecipe(new ItemStack(Calculator.energyUpgrade, 1), c(7, 1, false));
		addRecipe(new ItemStack(Calculator.voidUpgrade, 1), c(8, 1, false));
		addRecipe(new ItemStack(Calculator.transferUpgrade, 1), c(9, 1, false));
		addRecipe(new ItemStack(Calculator.calculator_assembly, 1), c(0, 3, false));
	}

	private boolean isValidInputType(Object input) {
		return input != null && input instanceof ItemStack;
	}

	public void addRecipe(Object... objs) {
		ArrayList outputs = new ArrayList();
		ArrayList inputs = new ArrayList();
		for (int i = 0; i < objs.length; i++) {
			Object obj = objs[i];
			if (i < 1) {
				outputs.add(obj);
			} else {
				inputs.add(obj);
			}
		}
		addRecipe(buildDefaultRecipe(inputs, outputs, new ArrayList(), true));
	}

	public ItemStack c(int meta, long required, boolean stable) {
		ItemStack stack = new ItemStack(Calculator.circuitBoard, (int) required, meta);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("Stable", stable ? 1 : 0);
		tag.setBoolean("Analysed", true);
		stack.setTagCompound(tag);
		return stack;
	}
	

	public FabricationSonarRecipe buildRecipe(ArrayList<ISonarRecipeObject> recipeInputs, ArrayList<ISonarRecipeObject> recipeOutputs, List additionals, boolean shapeless) {
		return new FabricationSonarRecipe(recipeInputs, recipeOutputs, shapeless);
	}

	public static boolean matchingCircuitIngredients(RecipeObjectType type, ArrayList<ISonarRecipeObject> ingredients, boolean shapeless, Object[] objs) {
		ArrayList<ISonarRecipeObject> matches = (ArrayList<ISonarRecipeObject>) ingredients.clone();
		if (ingredients.size() > objs.length) {
			return false;
		}
		ArrayList<ISonarRecipeObject> remaining = (ArrayList<ISonarRecipeObject>) ingredients.clone();
		int iPos = 0;
		i: for (ISonarRecipeObject ingredient : ingredients) {
			int pos = -1;
			for (Object obj : objs) {
				pos++;
				if (obj != null) {
					if (obj instanceof List) {
						List list = (List) obj;
						for (Object listObj : list) {
							if (matchingIngredient(listObj, pos, type, matches, ingredients, shapeless)) {
								remaining.remove(iPos);
								continue i;
							}
						}
					}
					if (matchingIngredient(obj, pos, type, matches, ingredients, shapeless)) {
						remaining.remove(iPos);
						continue i;
					}
				}
			}
			iPos++;
		}
		return remaining.isEmpty();
	}
}
