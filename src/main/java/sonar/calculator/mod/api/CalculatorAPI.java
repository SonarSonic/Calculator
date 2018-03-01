package sonar.calculator.mod.api;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gnu.trove.map.hash.THashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CalculatorAPI {

	private CalculatorAPI() {

	}

    private static final List<ResourceLocation> multiplierBlacklist = new ArrayList<>();
    private static final List<String> multiplierModBlacklist = new ArrayList<>();
    private static final Map<String, List<Object[]>> recipes = new THashMap<>();
	public static final String VERSION = "1.9.4 - 1.0";
	public static final String MODID = "calculator";
	public static final String NAME = "calculatorapi";

	public static List<Object[]> getRecipes(String recipeID) {
        return recipes.get(recipeID);
	}
	
	/**
	 * @param recipeID the id of the machine
	 * @param obj inputs followed by outputs in any of the following formats 
	 */
	public static void registerRecipe(String recipeID, Object... obj) {
        recipes.computeIfAbsent(recipeID, k -> new ArrayList<>());
		recipes.get(recipeID).add(obj);
	}

	public static List<ResourceLocation> getItemBlackList() {
		return multiplierBlacklist;
	}

	public static List<String> getModBlackList() {
		return multiplierModBlacklist;
	}

    /**
     * adds a Item/Block to Atomic Multiplier blacklist
     */
	public static void addItemStackToBlackList(ItemStack stack) {
		multiplierBlacklist.add(stack.getItem().getRegistryName());
	}

    /**
     * adds a mod to Atomic Multiplier blacklist
     */
	public static void addModToBlackList(String string) {
		multiplierModBlacklist.add(string);
	}

	/**
	 * @param objects four parameters, see below
	 */
	public static void registerCalculatorRecipe(Object... objects) {
		try {
			Class recipeClass = Class.forName("sonar.calculator.mod.common.recipes.RecipeRegistry");
			Method method = recipeClass.getMethod("registerCalculatorRecipe", Object[].class);
			method.invoke(null, objects);
		} catch (Exception exception) {
			System.err.println("Calculator API: Invalid Calculator Recipe  " + exception.getMessage());
		}
	}

	/**
	 * @param objects three parameters, see below
	 */
	public static void registerScientificRecipe(Object... objects) {
		try {
			Class recipeClass = Class.forName("sonar.calculator.mod.common.recipes.RecipeRegistry");
			Method method = recipeClass.getMethod("registerScientificRecipe", Object[].class);
			method.invoke(null, objects);
		} catch (Exception exception) {
			System.err.println("Calculator API: Invalid Scientific Recipe  " + exception.getMessage());
		}
	}

	/**
	 * @param objects four parameters, see below
	 */
	public static void registerAtomicRecipe(Object... objects) {
		try {
			Class recipeClass = Class.forName("sonar.calculator.mod.common.recipes.RecipeRegistry");
			Method method = recipeClass.getMethod("registerAtomicRecipe", Object[].class);
			method.invoke(null, objects);
		} catch (Exception exception) {
			System.err.println("Calculator API: Invalid Atomic Recipe  " + exception.getMessage());
		}
	}

	/**
	 * @param objects five parameters, see below
	 */
	public static void registerFlawlessRecipe(Object... objects) {
		try {
			Class recipeClass = Class.forName("sonar.calculator.mod.common.recipes.RecipeRegistry");
			Method method = recipeClass.getMethod("registerFlawlessRecipe", Object[].class);
			method.invoke(null, objects);
		} catch (Exception exception) {
			System.err.println("Calculator API: Invalid Atomic Recipe  " + exception.getMessage());
		}
	}
}
