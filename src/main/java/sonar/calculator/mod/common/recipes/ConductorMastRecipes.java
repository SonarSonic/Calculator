package sonar.calculator.mod.common.recipes;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.block.MaterialBlock.Variants;
import sonar.core.recipes.ValueHelperV2;

public class ConductorMastRecipes extends ValueHelperV2 {
	private static final ConductorMastRecipes INSTANCE = new ConductorMastRecipes();

	public static ConductorMastRecipes instance() {
		return INSTANCE;
	}

	public ConductorMastRecipes() {
		super(1, 1, true);
	}

	@Override
	public void addRecipes() {
		addRecipe(Calculator.firediamond, new ItemStack(Calculator.electricDiamond), 10000);
		addRecipe(Calculator.itemCalculator, new ItemStack(Calculator.itemScientificCalculator), 2000);
		addRecipe(new ItemStack(Calculator.material_block, 1, Variants.FIRE_DIAMOND.getMeta()), new ItemStack(Calculator.material_block, 1, Variants.ELECTRIC_DIAMOND.getMeta()), 90000);
	}

	@Override
	public String getRecipeID() {
		return "ConductorMastItem";
	}
}