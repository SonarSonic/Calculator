package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import sonar.core.helpers.ValueHelper;

public abstract class AbstractGeneratorHandler extends TemplateRecipeHandler {

	private ArrayList<ValuePair> valueList;
	public static ArrayList<FuelPair> fuelList;

	public abstract ValueHelper valuehelper();

	public abstract String getOverlayIdentifier();

	public abstract String getRecipeName();

	public abstract String getGuiTexture();

	public abstract Class<? extends GuiContainer> getGuiClass();

	@Override
	public void loadTransferRects() {

		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(30 - 5, 41 - 11, 78, 10), "fuel", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(30 - 5, 17 - 11, 78, 10), getOverlayIdentifier(), new Object[0]));

	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals(getOverlayIdentifier())) && (AbstractGeneratorHandler.class.isAssignableFrom(getClass()))) {
			Map<Object, Integer> recipes = valuehelper().getRecipes();
			for (Map.Entry<Object, Integer> recipe : recipes.entrySet())
				this.arecipes.add(new ValuePair(recipe.getKey(), recipe.getValue()));
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		int starch = (Integer) valuehelper().getOutput(ingredient);
		if (starch > 0) {
			arecipes.add(new ValuePair(ingredient, starch));
		}
		int fuel = TileEntityFurnace.getItemBurnTime(ingredient);
		if (fuel > 0) {
			fuelList.add(new FuelPair(ingredient, fuel));
		}

	}

	@Override
	public TemplateRecipeHandler newInstance() {
		if (fuelList == null)
			findFuels();
		return super.newInstance();
	}

	@Override
	public void drawExtras(int i) {
		drawProgressBar(30 - 5, 17 - 11, 0, 166, 138, 10, 150, 0);
		drawProgressBar(30 - 5, 41 - 11, 0, 176, 138, 10, 150, 0);
		drawProgressBar(8 - 5, 63 - 11, 0, 186, 160, 10, 150, 0);

	}

	private static Set<Item> excludedFuels() {
		Set<Item> efuels = new HashSet<Item>();
		efuels.add(Item.getItemFromBlock(Blocks.brown_mushroom));
		efuels.add(Item.getItemFromBlock(Blocks.red_mushroom));
		efuels.add(Item.getItemFromBlock(Blocks.standing_sign));
		efuels.add(Item.getItemFromBlock(Blocks.wall_sign));
		efuels.add(Item.getItemFromBlock(Blocks.wooden_door));
		efuels.add(Item.getItemFromBlock(Blocks.trapped_chest));
		return efuels;
	}

	private static void findFuels() {
		fuelList = new ArrayList<FuelPair>();
		Set<Item> efuels = excludedFuels();
		for (ItemStack item : ItemList.items)
			if (!efuels.contains(item.getItem())) {
				int burnTime = TileEntityFurnace.getItemBurnTime(item);
				if (burnTime > 0)
					fuelList.add(new FuelPair(item.copy(), burnTime));
			}
	}

	@Override
	public int recipiesPerPage() {
		return 1;

	}

	public class ValuePair extends CachedRecipe {

		PositionedStack starch;
		public int value;

		public ValuePair(Object starch, int value) {
			this.value = value;
			this.starch = new PositionedStack(starch, 8 - 5, 14 - 11);
		}

		public int starch() {
			return value;
		}

		@Override
		public PositionedStack getResult() {
			return fuelList.get((cycleticks / 150) % fuelList.size()).stack;
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			return getCycledIngredients(AbstractGeneratorHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] { this.starch }));

		}
	}

	public static class FuelPair {
		public FuelPair(ItemStack ingred, int burnTime) {
			this.stack = new PositionedStack(ingred, 8 - 5, 38 - 11, false);
			this.burnTime = burnTime;
		}

		public PositionedStack stack;
		public int burnTime;
	}
}
