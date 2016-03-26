package sonar.calculator.mod.integration.minetweaker;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.common.recipes.machines.StoneSeparatorRecipes;
import sonar.core.helpers.RecipeHelper;

/**
 * Created by AEnterprise
 */
@ZenClass("mods.calculator.stoneSeparator")
public class StoneSeparatorHandler {

	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack output1, IItemStack output2) {
		MineTweakerAPI.apply(new AddRecipeAction(input, MineTweakerMC.getItemStack(output1), MineTweakerMC.getItemStack(output2)));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient input) {
		MineTweakerAPI.apply(new RemoveRecipeAction(input));
	}


	private static class AddRecipeAction implements IUndoableAction {
		private Object input;
		private ItemStack output1, output2;

		public AddRecipeAction(Object input, ItemStack output1, ItemStack output2) {
			if (input instanceof IItemStack)
				input = MineTweakerMC.getItemStack((IItemStack) input);
			if (input instanceof IOreDictEntry)
				input = new RecipeHelper.OreStack(((IOreDictEntry) input).getName(), 1);


			if (input instanceof ILiquidStack) {
				MineTweakerAPI.logError("A liquid was passed intro a stone separator recipe, calculators do not use liquids when crafting, aborting!");
				input = output1 = output2 = null;
			}

			this.input = input;
			this.output1 = output1;
			this.output2 = output2;
		}

		@Override
		public void apply() {
			if (input == null || output1 == null ||output2 == null)
				return;
			StoneSeparatorRecipes.instance().addRecipe(input, output1, output2);
		}



		@Override
		public void undo() {
			if (input == null || output1 == null ||output2 == null)
				return;
			StoneSeparatorRecipes.instance().removeRecipe(input);
		}

		@Override
		public String describe() {
			return String.format("Adding stone separator recipe (%s => %s & %s)", input, output1, output2);
		}

		@Override
		public String describeUndo() {
			return String.format("Reverting /%s/", describe());
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}


	}

	private static class RemoveRecipeAction implements IUndoableAction {
		private Object input;
		private ItemStack output1, output2;


		public RemoveRecipeAction(Object input) {
			if (input instanceof IItemStack)
				input = MineTweakerMC.getItemStack((IItemStack) input);
			if (input instanceof IOreDictEntry)
				input = new RecipeHelper.OreStack(((IOreDictEntry) input).getName(), 1);


			if (input instanceof ILiquidStack) {
				MineTweakerAPI.logError("A liquid was passed intro a stone separator recipe, calculators do not use liquids when crafting, aborting!");
				input = output1 = output2 = null;
			}

			this.input = input;

			ItemStack dummyInput = null;

			if (input instanceof ItemStack)
				dummyInput = (ItemStack) input;
			if (input instanceof RecipeHelper.OreStack)
				dummyInput = OreDictionary.getOres(((RecipeHelper.OreStack) input).oreString).get(0);

			ItemStack[] stacks = StoneSeparatorRecipes.instance().getOutput(dummyInput);
			output1 = stacks[0];
			output2 = stacks[1];
		}

		@Override
		public void apply() {
			if (input == null || output1 == null ||output2 == null)
				return;
			StoneSeparatorRecipes.instance().removeRecipe(input);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			if (input == null || output1 == null ||output2 == null)
				return;
			StoneSeparatorRecipes.instance().addRecipe(input, output1, output2);
		}

		@Override
		public String describe() {
			return String.format("Removing stone separator recipe (%s => %s & %s)", input, output1, output2);
		}

		@Override
		public String describeUndo() {
			return String.format("Reverting /%s/", describe());
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
