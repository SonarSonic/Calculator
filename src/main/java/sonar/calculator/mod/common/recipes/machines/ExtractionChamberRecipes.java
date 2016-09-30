package sonar.calculator.mod.common.recipes.machines;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.Calculator;
import sonar.core.recipes.DefinedRecipeHelper;

public class ExtractionChamberRecipes extends DefinedRecipeHelper {

	private static final ExtractionChamberRecipes recipes = new ExtractionChamberRecipes();

	public ExtractionChamberRecipes() {
		super(1, 2, false);
	}

	public static final ExtractionChamberRecipes instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		addRecipe(Blocks.DIRT, new ItemStack(Calculator.soil, 1), new ItemStack(Calculator.circuitDirty, 1, OreDictionary.WILDCARD_VALUE));
		addRecipe("cobblestone", new ItemStack(Calculator.small_stone, 1), new ItemStack(Calculator.circuitDamaged, 1, OreDictionary.WILDCARD_VALUE));
	}

	@Override
	public String getRecipeID() {
		return "ExtractionChamber";
	}
}
