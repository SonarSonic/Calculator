package sonar.calculator.mod.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import sonar.calculator.mod.common.recipes.machines.AlgorithmSeperatorRecipes;
import sonar.calculator.mod.common.recipes.machines.ExtractionChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.PrecisionChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.ProcessingChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.ReassemblyChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.RestorationChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.StoneSeperatorRecipes;
import sonar.calculator.mod.utils.helpers.RecipeHelper;

public class TileEntityMachines {

	/** single process machines */
	public static class ReassemblyChamber extends TileEntityAbstractProcess {
		@Override
		public int getFurnaceSpeed() {
			return super.getFurnaceSpeed() * 2;
		}

		@Override
		public int getRequiredEnergy() {
			return super.getRequiredEnergy() * 5;
		}

		@Override
		public RecipeHelper recipeHelper() {
			return ReassemblyChamberRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 1;
		}

	}

	public static class RestorationChamber extends TileEntityAbstractProcess {
		@Override
		public int getFurnaceSpeed() {
			return super.getFurnaceSpeed() * 2;
		}

		@Override
		public int getRequiredEnergy() {
			return super.getRequiredEnergy() * 5;
		}

		@Override
		public RecipeHelper recipeHelper() {
			return RestorationChamberRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 1;
		}
	}

	public static class ProcessingChamber extends TileEntityAbstractProcess {

		@Override
		public int getFurnaceSpeed() {
			return super.getFurnaceSpeed() / 2;
		}

		@Override
		public RecipeHelper recipeHelper() {
			return ProcessingChamberRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 1;
		}
	}

	public static class ReinforcedFurnace extends TileEntityAbstractProcess {
		@Override
		public int getFurnaceSpeed() {
			return super.getFurnaceSpeed() / 5;
		}

		@Override
		public int getRequiredEnergy() {
			return super.getRequiredEnergy() / 5;
		}

		@Override
		public ItemStack[] getOutput(ItemStack... stacks) {
			return new ItemStack[] { FurnaceRecipes.smelting().getSmeltingResult(stacks[0]) };
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 1;
		}
	}

	/** dual process machines */
	public static class AlgorithmSeperator extends TileEntityAbstractProcess {

		@Override
		public RecipeHelper recipeHelper() {
			return AlgorithmSeperatorRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 2;
		}
	}

	public static class ExtractionChamber extends TileEntityAbstractProcess {

		@Override
		public RecipeHelper recipeHelper() {
			return ExtractionChamberRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 2;
		}
	}

	public static class PrecisionChamber extends TileEntityAbstractProcess {

		@Override
		public RecipeHelper recipeHelper() {
			return PrecisionChamberRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 2;
		}
	}

	public static class StoneSeperator extends TileEntityAbstractProcess {

		@Override
		public RecipeHelper recipeHelper() {
			return StoneSeperatorRecipes.instance();
		}

		@Override
		public int inputSize() {
			return 1;
		}

		@Override
		public int outputSize() {
			return 2;
		}

	}
}
