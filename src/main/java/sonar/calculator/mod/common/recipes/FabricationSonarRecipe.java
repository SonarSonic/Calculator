package sonar.calculator.mod.common.recipes;

import java.util.ArrayList;

import sonar.core.recipes.DefaultSonarRecipe;
import sonar.core.recipes.ISonarRecipeObject;
import sonar.core.recipes.RecipeObjectType;

public class FabricationSonarRecipe extends DefaultSonarRecipe {

	public FabricationSonarRecipe(ArrayList<ISonarRecipeObject> inputs, ArrayList<ISonarRecipeObject> outputs, boolean shapeless) {
		super(inputs, outputs, shapeless);
	}
	
	public boolean matchingInputs(Object[] inputs) {
		return FabricationChamberRecipes.matchingCircuitIngredients(RecipeObjectType.INPUT, recipeInputs, shapeless, inputs);
	}

	public boolean matchingOutputs(Object[] outputs) {
		return FabricationChamberRecipes.matchingCircuitIngredients(RecipeObjectType.OUTPUT, recipeOutputs, shapeless, outputs);
	}

}
