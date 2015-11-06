package sonar.calculator.mod.integration.minetweaker;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.integration.nei.handlers.CalculatorRecipeHandler;
import sonar.core.utils.helpers.RecipeHelper;
import sonar.core.utils.helpers.SonarHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Map;

/**
 * Created by AEnterprise
 */
@ZenClass("mods.calculator.basic")
public class CalculatorHandler {

	@ZenMethod
	public static void addRecipe(IItemStack input1, IItemStack input2, IItemStack output) {
		MineTweakerAPI.apply(new AddRecipeAction(input1, input2, MineTweakerMC.getItemStack(output)));
	}

	@ZenMethod
	public static void addRecipe(IOreDictEntry input1, IItemStack input2, IItemStack output) {
		MineTweakerAPI.apply(new AddRecipeAction(input1, input2, MineTweakerMC.getItemStack(output)));
	}

	@ZenMethod
	public static void addRecipe(IItemStack input1, IOreDictEntry input2, IItemStack output) {
		MineTweakerAPI.apply(new AddRecipeAction(input1, input2, MineTweakerMC.getItemStack(output)));
	}

	@ZenMethod
	public static void addRecipe(IOreDictEntry input1, IOreDictEntry input2, IItemStack output) {
		MineTweakerAPI.apply(new AddRecipeAction(input1, input2, MineTweakerMC.getItemStack(output)));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack input1, IItemStack input2) {
		MineTweakerAPI.apply(new RemoveRecipeAction(input1, input2));
	}

	@ZenMethod
	public static void removeRecipe(IOreDictEntry input1, IItemStack input2) {
		MineTweakerAPI.apply(new RemoveRecipeAction(input1, input2));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack input1, IOreDictEntry input2) {
		MineTweakerAPI.apply(new RemoveRecipeAction(input1, input2));
	}

	@ZenMethod
	public static void removeRecipe(IOreDictEntry input1, IOreDictEntry input2) {
		MineTweakerAPI.apply(new RemoveRecipeAction(input1, input2));
	}


	private static class AddRecipeAction implements IUndoableAction{
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

			this.input1 = input1;
			this.input2 = input2;
			this.output = output;
		}

		@Override
		public void apply() {
			RecipeRegistry.CalculatorRecipes.instance().addRecipe(input1, input2, output);
		}



		@Override
		public void undo() {
			//TODO: logging if no recipe was removed
			RecipeRegistry.CalculatorRecipes.instance().removeRecipe(input1, input2);
		}

		@Override
		public String describe() {
			return "Adding calculator recipe";
		}

		@Override
		public String describeUndo() {
			return "Removing calculator recipe";
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

			output = RecipeRegistry.CalculatorRecipes.instance().getCraftingResult(dummyInput1, dummyInput2);
		}

		@Override
		public void apply() {
			RecipeRegistry.CalculatorRecipes.instance().removeRecipe(input1, input2);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			RecipeRegistry.CalculatorRecipes.instance().addRecipe(input1, input2, output);
		}

		@Override
		public String describe() {
			return null;
		}

		@Override
		public String describeUndo() {
			return null;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
