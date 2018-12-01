package sonar.calculator.mod.common.recipes;

import sonar.core.recipes.DefaultSonarRecipe;
import sonar.core.recipes.ISonarRecipeObject;
import sonar.core.recipes.RecipeObjectType;

import java.util.List;

public class FabricationSonarRecipe extends DefaultSonarRecipe {

	public FabricationSonarRecipe(List<ISonarRecipeObject> inputs, List<ISonarRecipeObject> outputs, boolean shapeless) {
		super(inputs, outputs, shapeless);
	}
	
    @Override
	public boolean matchingInputs(Object[] inputs) {
		return FabricationChamberRecipes.matchingCircuitIngredients(RecipeObjectType.INPUT, recipeInputs, shapeless, inputs);
	}

    @Override
	public boolean matchingOutputs(Object[] outputs) {
		return FabricationChamberRecipes.matchingCircuitIngredients(RecipeObjectType.OUTPUT, recipeOutputs, shapeless, outputs);
	}
}
