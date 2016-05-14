package sonar.calculator.mod.integration.minetweaker;
/*
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.core.helpers.RecipeHelper;

* Created by AEnterprise

@ZenClass("mods.calculator.scientific")
public class ScientificHandler {

	@ZenMethod
	public static void addRecipe(IIngredient input1, IIngredient input2, IItemStack output) {
		MineTweakerAPI.apply(new AddRecipeAction(input1, input2, MineTweakerMC.getItemStack(output)));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient input1, IIngredient input2) {
		MineTweakerAPI.apply(new RemoveRecipeAction(input1, input2));
	}


	private static class AddRecipeAction implements IUndoableAction {
		private Object input1, input2;
		private ItemStack output;

		public AddRecipeAction(Object input1, Object input2, ItemStack output) {
			if (input1 instanceof IItemStack)
				input1 = MineTweakerMC.getItemStack((IItemStack) input1);
			if (input1 instanceof IOreDictEntry)
				input1 = new RecipeHelper.OreStack(((IOreDictEntry) input1).getName(), 1);

			if (input2 instanceof IItemStack)
				input2 = MineTweakerMC.getItemStack((IItemStack) input2);
			if (input2 instanceof IOreDictEntry)
				input2 = new RecipeHelper.OreStack(((IOreDictEntry) input2).getName(), 1);

			if (input1 instanceof ILiquidStack || input2 instanceof ILiquidStack) {
				MineTweakerAPI.logError("A liquid was passed intro a scientific recipe, calculators do not use liquids when crafting, aborting!");
				input1 = input2 = output = null;
			}

			this.input1 = input1;
			this.input2 = input2;
			this.output = output;
		}

		@Override
		public void apply() {
			if (input1 == null || input2 == null ||output == null)
				return;
			RecipeRegistry.ScientificRecipes.instance().addRecipe(input1, input2, output);
		}



		@Override
		public void undo() {
			if (input1 == null || input2 == null ||output == null)
				return;
			RecipeRegistry.ScientificRecipes.instance().removeRecipe(input1, input2);
		}

		@Override
		public String describe() {
			return String.format("Adding scientific recipe (%s ÷ %s = %s)", input1, input2, output);
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
		private Object input1, input2;
		private ItemStack output;


		public RemoveRecipeAction(Object input1, Object input2) {
			if (input1 instanceof IItemStack)
				input1 = MineTweakerMC.getItemStack((IItemStack) input1);
			if (input1 instanceof IOreDictEntry)
				input1 = new RecipeHelper.OreStack(((IOreDictEntry) input1).getName(), 1);

			if (input2 instanceof IItemStack)
				input2 = MineTweakerMC.getItemStack((IItemStack) input2);
			if (input2 instanceof IOreDictEntry)
				input2 = new RecipeHelper.OreStack(((IOreDictEntry) input2).getName(), 1);

			if (input1 instanceof ILiquidStack || input2 instanceof ILiquidStack) {
				MineTweakerAPI.logError("A liquid was passed intro a scientific recipe, calculators do not use liquids when crafting, aborting!");
				input1 = input2 = output = null;
			}

			this.input1 = input1;
			this.input2 = input2;

			ItemStack dummyInput1 = null;
			ItemStack dummyInput2 = null;

			if (input1 instanceof ItemStack)
				dummyInput1 = (ItemStack) input1;
			if (input1 instanceof RecipeHelper.OreStack)
				dummyInput1 = OreDictionary.getOres(((RecipeHelper.OreStack) input1).oreString).get(0);

			if (input2 instanceof ItemStack)
				dummyInput2 = (ItemStack) input2;
			if (input2 instanceof RecipeHelper.OreStack)
				dummyInput2 = OreDictionary.getOres(((RecipeHelper.OreStack) input2).oreString).get(0);

			output = RecipeRegistry.ScientificRecipes.instance().getCraftingResult(dummyInput1, dummyInput2);
		}

		@Override
		public void apply() {
			if (input1 == null || input2 == null ||output == null)
				return;
			RecipeRegistry.ScientificRecipes.instance().removeRecipe(input1, input2);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			if (input1 == null || input2 == null ||output == null)
				return;
			RecipeRegistry.ScientificRecipes.instance().addRecipe(input1, input2, output);
		}

		@Override
		public String describe() {
			return String.format("Removing scientific recipe (%s ÷ %s = %s)", input1, input2, output);
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
*/