package sonar.calculator.mod.integration.minetweaker;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.core.utils.helpers.RecipeHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by AEnterprise
 */

@ZenClass("mods.calculator.flawless")
public class FlawlessHandler {

	@ZenMethod
	public static void addRecipe(IIngredient input1, IIngredient input2, IIngredient input3, IIngredient input4, IItemStack output) {
		MineTweakerAPI.apply(new AddRecipeAction(input1, input2, input3, input4, MineTweakerMC.getItemStack(output)));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient input1, IIngredient input2, IIngredient input3, IIngredient input4) {
		MineTweakerAPI.apply(new RemoveRecipeAction(input1, input2, input3, input4));
	}


	private static class AddRecipeAction implements IUndoableAction {
		private Object input1, input2, input3, input4;
		private ItemStack output;

		public AddRecipeAction(Object input1, Object input2, Object input3, Object input4, ItemStack output) {
			if (input1 instanceof IItemStack)
				input1 = MineTweakerMC.getItemStack((IItemStack) input1);
			if (input1 instanceof IOreDictEntry)
				input1 = new RecipeHelper.OreStack(((IOreDictEntry) input1).getName(), 1);

			if (input2 instanceof IItemStack)
				input2 = MineTweakerMC.getItemStack((IItemStack) input2);
			if (input2 instanceof IOreDictEntry)
				input2 = new RecipeHelper.OreStack(((IOreDictEntry) input2).getName(), 1);

			if (input3 instanceof IItemStack)
				input3 = MineTweakerMC.getItemStack((IItemStack) input3);
			if (input3 instanceof IOreDictEntry)
				input3 = new RecipeHelper.OreStack(((IOreDictEntry) input3).getName(), 1);

			if (input4 instanceof IItemStack)
				input4 = MineTweakerMC.getItemStack((IItemStack) input4);
			if (input4 instanceof IOreDictEntry)
				input4 = new RecipeHelper.OreStack(((IOreDictEntry) input4).getName(), 1);

			if (input1 instanceof ILiquidStack || input2 instanceof ILiquidStack || input3 instanceof ILiquidStack || input4 instanceof ILiquidStack) {
				MineTweakerAPI.logError("A liquid was passed intro a calculator recipe, calculators do not use liquids when crafting, aborting!");
				input1 = input2 = input3 = input4 = output = null;
			}

			this.input1 = input1;
			this.input2 = input2;
			this.input3 = input3;
			this.input4 = input4;
			this.output = output;
		}

		@Override
		public void apply() {
			if (input1 == null || input2 == null || input3 ==  null || input4 == null ||output == null)
				return;
			RecipeRegistry.FlawlessRecipes.instance().addRecipe(input1, input2, input3, input4, output);
		}



		@Override
		public void undo() {
			if (input1 == null || input2 == null || input3 ==  null || input4 == null ||output == null)
				return;
			RecipeRegistry.FlawlessRecipes.instance().removeRecipe(input1, input2, input3, input4);
		}

		@Override
		public String describe() {
			return String.format("Adding calculator recipe (%s + %s ÷ %s + %s = %s)", input1, input2, input3, input4, output);
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
		private Object input1, input2, input3, input4;
		private ItemStack output;


		public RemoveRecipeAction(Object input1, Object input2, Object input3, Object input4) {
			if (input1 instanceof IItemStack)
				input1 = MineTweakerMC.getItemStack((IItemStack) input1);
			if (input1 instanceof IOreDictEntry)
				input1 = new RecipeHelper.OreStack(((IOreDictEntry) input1).getName(), 1);

			if (input2 instanceof IItemStack)
				input2 = MineTweakerMC.getItemStack((IItemStack) input2);
			if (input2 instanceof IOreDictEntry)
				input2 = new RecipeHelper.OreStack(((IOreDictEntry) input2).getName(), 1);

			if (input3 instanceof IItemStack)
				input3 = MineTweakerMC.getItemStack((IItemStack) input3);
			if (input3 instanceof IOreDictEntry)
				input3 = new RecipeHelper.OreStack(((IOreDictEntry) input3).getName(), 1);

			if (input4 instanceof IItemStack)
				input4 = MineTweakerMC.getItemStack((IItemStack) input4);
			if (input4 instanceof IOreDictEntry)
				input4 = new RecipeHelper.OreStack(((IOreDictEntry) input4).getName(), 1);

			if (input1 instanceof ILiquidStack || input2 instanceof ILiquidStack || input3 instanceof ILiquidStack || input4 instanceof ILiquidStack) {
				MineTweakerAPI.logError("A liquid was passed intro a calculator recipe, calculators do not use liquids when crafting, aborting!");
				input1 = input2 = output = null;
			}

			this.input1 = input1;
			this.input2 = input2;
			this.input3 = input3;
			this.input4 = input4;

			ItemStack dummyInput1 = null;
			ItemStack dummyInput2 = null;
			ItemStack dummyInput3 = null;
			ItemStack dummyInput4 = null;

			if (input1 instanceof ItemStack)
				dummyInput1 = (ItemStack) input1;
			if (input1 instanceof RecipeHelper.OreStack)
				dummyInput1 = OreDictionary.getOres(((RecipeHelper.OreStack) input1).oreString).get(0);

			if (input2 instanceof ItemStack)
				dummyInput2 = (ItemStack) input2;
			if (input2 instanceof RecipeHelper.OreStack)
				dummyInput2 = OreDictionary.getOres(((RecipeHelper.OreStack) input2).oreString).get(0);

			if (input3 instanceof ItemStack)
				dummyInput3 = (ItemStack) input3;
			if (input3 instanceof RecipeHelper.OreStack)
				dummyInput3 = OreDictionary.getOres(((RecipeHelper.OreStack) input3).oreString).get(0);

			if (input4 instanceof ItemStack)
				dummyInput4 = (ItemStack) input4;
			if (input4 instanceof RecipeHelper.OreStack)
				dummyInput4 = OreDictionary.getOres(((RecipeHelper.OreStack) input4).oreString).get(0);

			output = RecipeRegistry.CalculatorRecipes.instance().getCraftingResult(dummyInput1, dummyInput2, dummyInput3, dummyInput4);
		}

		@Override
		public void apply() {
			if (input1 == null || input2 == null || input3 ==  null || input4 == null ||output == null)
				return;
			RecipeRegistry.CalculatorRecipes.instance().removeRecipe(input1, input2, input3, input4);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			if (input1 == null || input2 == null || input3 ==  null || input4 == null ||output == null)
				return;
			RecipeRegistry.ScientificRecipes.instance().addRecipe(input1, input2, input3, input4, output);
		}

		@Override
		public String describe() {
			return String.format("Removing calculator recipe (%s + %s ÷ %s + %s = %s)", input1, input2, input3, input4, output);
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
