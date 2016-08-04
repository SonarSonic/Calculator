package sonar.calculator.mod.integration.minetweaker;

import com.google.common.collect.Lists;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.core.integration.minetweaker.SonarAddRecipe;
import sonar.core.integration.minetweaker.SonarRemoveRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.calculator.atomic")
public class AtomicHandler {

	@ZenMethod
	public static void addRecipe(IIngredient input1, IIngredient input2, IIngredient input3, IItemStack output) {
		MineTweakerAPI.apply(new SonarAddRecipe(RecipeRegistry.AtomicRecipes.instance(), Lists.newArrayList(input1, input2, input3), Lists.newArrayList(MineTweakerMC.getItemStack(output))));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient input1, IIngredient input2, IIngredient input3) {
		MineTweakerAPI.apply(new SonarRemoveRecipe(RecipeRegistry.AtomicRecipes.instance(), Lists.newArrayList(input1, input2, input3), true));
	}
}
