package sonar.calculator.mod.api;

import java.lang.reflect.Method;

public class CalculatorAPI {

	private CalculatorAPI() {

	}

	public static final String VERSION = "1.7.10 - 1.0";

	/**
	 * 
	 * @param objects four parameters, see below
	 * @param output (ItemStack)
	 * @param input1 (ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input2 (ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param hidden Does the recipe require research? (Boolean)
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
	 * @param input1 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input2 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param output (Item, Block, ItemStack)
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
	 * @param input1 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input2 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input3 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param output (Item, Block, ItemStack)
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
	 * @param input1 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input2 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input3 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input4 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param output (Item, Block, ItemStack)
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
