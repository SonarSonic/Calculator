package sonar.calculator.mod.integration.minetweaker;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.core.helpers.RecipeHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.calculator.conductorMast")
public class ConductorMastHandler {

	@ZenMethod
	public static void addRecipe(IIngredient input, int powercost, IItemStack output) {
		MineTweakerAPI.apply(new AddRecipeAction(input, powercost, MineTweakerMC.getItemStack(output)));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient input) {
		MineTweakerAPI.apply(new RemoveRecipeAction(input));
	}


	private static class AddRecipeAction implements IUndoableAction {
		private Object input;
		private ItemStack output;
		private int powercost;

		public AddRecipeAction(Object input, int powercost, ItemStack output) {
			if (input instanceof IItemStack)
				input = MineTweakerMC.getItemStack((IItemStack) input);
			if (input instanceof IOreDictEntry)
				input = new RecipeHelper.OreStack(((IOreDictEntry) input).getName(), 1);

			if (input instanceof ILiquidStack) {
				MineTweakerAPI.logError("A liquid was passed intro a conductor mast recipe, calculators do not use liquids when crafting, aborting!");
				input = output = null;
			}

			this.input = input;
			this.output = output;
			this.powercost = powercost;
		}

		@Override
		public void apply() {
			if (input == null || output == null)
				return;
			RecipeRegistry.ConductorMastItemRecipes.instance().addRecipe(input, output);
			RecipeRegistry.ConductorMastPowerRecipes.instance().addRecipe(input, powercost);
		}



		@Override
		public void undo() {
			if (input == null || output == null)
				return;
			RecipeRegistry.ConductorMastItemRecipes.instance().removeRecipe(input);
			RecipeRegistry.ConductorMastItemRecipes.instance().removeRecipe(input);
		}

		@Override
		public String describe() {
			return String.format("Adding conductor mast recipe (%s => %s  *powercost: %d RF)", input, output, powercost);
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
		private int powercost;
		private ItemStack output;


		public RemoveRecipeAction(Object input) {
			if (input instanceof IItemStack)
				input = MineTweakerMC.getItemStack((IItemStack) input);
			if (input instanceof IOreDictEntry)
				input = new RecipeHelper.OreStack(((IOreDictEntry) input).getName(), 1);

			if (input instanceof ILiquidStack) {
				MineTweakerAPI.logError("A liquid was passed intro a calculator recipe, calculators do not use liquids when crafting, aborting!");
				input = output = null;
			}

			this.input = input;

			ItemStack dummyInput = null;

			if (input instanceof ItemStack)
				dummyInput = (ItemStack) input;
			if (input instanceof RecipeHelper.OreStack)
				dummyInput = ((RecipeHelper.OreStack) input).getStacks().get(0);


			output = RecipeRegistry.ConductorMastItemRecipes.instance().getCraftingResult(dummyInput);
			powercost = RecipeRegistry.ConductorMastPowerRecipes.instance().getPowercost(dummyInput);
		}

		@Override
		public void apply() {
			if (input == null || output == null)
				return;
			RecipeRegistry.ConductorMastItemRecipes.instance().removeRecipe(input);
			RecipeRegistry.ConductorMastPowerRecipes.instance().removeRecipe(input);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			if (input == null || output == null)
				return;
			RecipeRegistry.ConductorMastItemRecipes.instance().addRecipe(input, output);
			RecipeRegistry.ConductorMastPowerRecipes.instance().addRecipe(input, powercost);
		}

		@Override
		public String describe() {
			return String.format("Removing conductor mast recipe (%s => %s  *powercost: %d RF)", input, output, powercost);
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
