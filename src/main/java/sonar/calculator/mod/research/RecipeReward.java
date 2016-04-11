package sonar.calculator.mod.research;

import java.util.ArrayList;

public class RecipeReward {
	public String id;
	public ArrayList<String> recipes;

	public RecipeReward(String id, ArrayList<String> recipes) {
		this.id = id;
		this.recipes = recipes;
	}
}
