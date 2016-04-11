package sonar.calculator.mod.research;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
import sonar.calculator.mod.Calculator;

public class RecipeResearch extends Research {

	public ArrayList<String> recipes = new ArrayList();

	public RecipeResearch() {
		super("Research", "tile.ResearchChamber.name", Item.getItemFromBlock(Calculator.researchChamber));
	}

	@Override
	public String getHint() {
		return "Discover new recipes for the Calculator!";
	}

	public ArrayList<RecipeReward> getUnlockedRecipes() {
		ArrayList<RecipeReward> unlocked = new ArrayList();
		unlocked.add(new RecipeReward("Calculator", recipes));
		return unlocked;
	}

	@Override
	public byte getProgress() {
		//GET RECIPE SIZING...
		return 0;
	}

	@Override
	public ResearchCategory getResearchType() {
		return ResearchCategory.RECIPES;
	}

	@Override
	public IResearch getInstance() {
		return new RecipeResearch();
	}

}
