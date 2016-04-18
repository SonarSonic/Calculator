package sonar.calculator.mod.research.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.common.recipes.RecipeRegistry.RecipeType;
import sonar.calculator.mod.research.IResearch;
import sonar.calculator.mod.research.RecipeReward;
import sonar.calculator.mod.research.Research;
import sonar.calculator.mod.research.ResearchCategory;
import sonar.core.helpers.NBTHelper.SyncType;

public class RecipeResearch extends Research {

	public ArrayList<String> recipes = new ArrayList();

	public RecipeResearch() {
		super(ResearchTypes.RECIPES, "tile.ResearchChamber.name", Item.getItemFromBlock(Calculator.researchChamber));
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

	public void addRecipes(ArrayList<RecipeType> types) {
		for (RecipeType type : types) {
			if (!recipes.contains(type.name())) {
				recipes.add(type.name());
			}
		}
	}

	@Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		NBTTagCompound recipeList = (NBTTagCompound) nbt.getTag("recipeList");
		recipes = new ArrayList();
		for (String recipeID : recipeList.getKeySet()) {
			recipes.add(recipeID);
		}
	}

	@Override
	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		NBTTagCompound recipeList = new NBTTagCompound();
		for (String id : recipes) {
			recipeList.setBoolean(id, true);
		}
		nbt.setTag("recipeList", recipeList);
	}

	@Override
	public byte getProgress() {
		return (byte) ((recipes.size() * 100) / RecipeRegistry.RecipeType.values().length);
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
