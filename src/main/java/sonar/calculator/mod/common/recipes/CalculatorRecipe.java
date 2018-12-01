package sonar.calculator.mod.common.recipes;

import net.minecraft.entity.player.EntityPlayer;
import sonar.core.recipes.DefaultSonarRecipe;
import sonar.core.recipes.ISonarRecipeObject;

import java.util.List;

public class CalculatorRecipe extends DefaultSonarRecipe {

	public ResearchRecipeType recipeType;

	public CalculatorRecipe(List<ISonarRecipeObject> inputs, List<ISonarRecipeObject> outputs, ResearchRecipeType recipeType, boolean shapeless) {
		super(inputs, outputs, shapeless);
		this.recipeType = recipeType;
	}

	public ResearchRecipeType getRecipeType() {
		return recipeType;
	}

	@Override
	public boolean canUseRecipe(EntityPlayer player) {
		// PlayerResearchRegistry.getPlayerResearch(name)
		return true;
	}
}