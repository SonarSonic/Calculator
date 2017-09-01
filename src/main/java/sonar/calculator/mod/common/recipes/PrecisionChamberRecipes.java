package sonar.calculator.mod.common.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.Calculator;
import sonar.core.SonarCore;
import sonar.core.recipes.DefinedRecipeHelper;
import sonar.core.recipes.RecipeItemStack;

public class PrecisionChamberRecipes extends DefinedRecipeHelper {

	private static final PrecisionChamberRecipes recipes = new PrecisionChamberRecipes();

	public PrecisionChamberRecipes() {
		super(1, 2, false);
	}

	public static final PrecisionChamberRecipes instance() {
		return recipes;
	}

	@Override
	public void addRecipes() {
		addRecipe("cobblestone", new ItemStack(Calculator.small_stone, 2), new PrecisionChamberOutput(new ItemStack(Calculator.circuitDamaged, 1, OreDictionary.WILDCARD_VALUE)));
		addRecipe(Blocks.DIRT, new ItemStack(Calculator.soil, 2), new PrecisionChamberOutput(new ItemStack(Calculator.circuitDirty, 1, OreDictionary.WILDCARD_VALUE)));
		addRecipe("stone", new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 0));
		addRecipe("sand", new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 1));
		addRecipe(Blocks.STONEBRICK, new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 2));
		addRecipe(SonarCore.reinforcedStoneBlock, new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 3));
		addRecipe(Blocks.SOUL_SAND, new ItemStack(Calculator.soil, 2), new ItemStack(Calculator.circuitDirty, 1, 4));
		addRecipe(Blocks.OBSIDIAN, new ItemStack(Calculator.soil, 2), new ItemStack(Calculator.circuitDirty, 1, 5));
		addRecipe(Blocks.GRAVEL, new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 6));
		addRecipe("treeLeaves", new ItemStack(Calculator.soil, 2), new ItemStack(Calculator.circuitDirty, 1, 7));
		addRecipe("gemLapis", new ItemStack(Calculator.soil, 2), new ItemStack(Calculator.circuitDirty, 1, 8));
		addRecipe(Blocks.NETHER_BRICK, new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 9));
		addRecipe(SonarCore.reinforcedDirtBlock, new ItemStack(Calculator.soil, 2), new ItemStack(Calculator.circuitDirty, 1, 10));
		addRecipe("sandstone", new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 11));
		addRecipe(Blocks.CLAY, new ItemStack(Calculator.small_stone, 2), new ItemStack(Calculator.circuitDamaged, 1, 12));
		addRecipe("dustRedstone", new ItemStack(Calculator.soil, 2), new ItemStack(Calculator.circuitDirty, 1, 13));

	}

	@Override
	public String getRecipeID() {
		return "PrecisionChamber";
	}

	public static class PrecisionChamberOutput extends RecipeItemStack {

		public PrecisionChamberOutput(ItemStack stack) {
			super(stack, true);
		}

		@Override
		public ItemStack getOutputStack() {
			ItemStack circuit = stack.copy();
			circuit.setItemDamage(SonarCore.rand.nextInt(13 + 1));
			return circuit;
		}
	}
}
