package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.calculators.GuiAtomicCalculator;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipe;
import sonar.calculator.mod.common.recipes.machines.AtomicCalculatorRecipes;
import sonar.calculator.mod.common.recipes.machines.StoneSeperatorRecipes;
import sonar.calculator.mod.integration.nei.AtomicCalculatorNEIRecipes;
import sonar.calculator.mod.integration.nei.handlers.SSeperatorRecipeHandler.SmeltingPair;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class AtomicRecipeHandler extends TemplateRecipeHandler {
	public class SmeltingPair extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack input2;
		PositionedStack input3;
		PositionedStack output;

		public SmeltingPair(ItemStack input, ItemStack input2,
				ItemStack input3, ItemStack output) {
			super();
			input.stackSize = 1;
			this.input = new PositionedStack(input, 15, 24);
			this.input2 = new PositionedStack(input2, 47, 24);
			this.input3 = new PositionedStack(input3, 79, 24);
			this.output = new PositionedStack(output, 129, 24);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return Arrays.asList(new PositionedStack[] { this.input,
					this.input2, this.input3 });
		}

		@Override
		public PositionedStack getResult() {
			return this.output;
		}
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(
				new Rectangle(100, 25, 22, 14), "atomic", new Object[0]));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiAtomicCalculator.class;
	}

	@Override
	public String getRecipeName() {
		return StatCollector
				.translateToLocal("tile.atomiccalculatorBlock.name");
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("atomic"))
				&& (getClass() == AtomicRecipeHandler.class)) {
			Map<ItemStack, ItemStack> recipes = AtomicCalculatorNEIRecipes.smelting().getSmeltingList();
			for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
				if (CalculatorConfig.isEnabled(recipe.getKey())) {
					this.arecipes.add(new SmeltingPair(recipe.getValue(),AtomicCalculatorNEIRecipes.smelting().getSmeltingResult2(recipe.getKey()),AtomicCalculatorNEIRecipes.smelting().getSmeltingResult3(recipe.getKey()),recipe.getKey()));
				}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		if (!CalculatorConfig.isEnabled(result)) {
			return;
		}
		Map<ItemStack, ItemStack> recipes = AtomicCalculatorNEIRecipes.smelting().getSmeltingList();
		for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType(recipe.getKey(), result)) {
				this.arecipes.add(new SmeltingPair(recipe.getValue(),
						AtomicCalculatorNEIRecipes.smelting().getSmeltingResult2(recipe.getKey()),
						AtomicCalculatorNEIRecipes.smelting().getSmeltingResult3(recipe.getKey()), recipe.getKey()));

			}
		}
		if (result.getItem() == Calculator.itemAdvancedTerrainModule) {
			this.arecipes.add(new SmeltingPair(new ItemStack(
					Calculator.itemScientificCalculator), new ItemStack(
					Calculator.atomic_binder), new ItemStack(
					Calculator.redstone_ingot), result));
		} else if (result.getItem() == Calculator.itemNutritionModule) {
			this.arecipes.add(new SmeltingPair(new ItemStack(
					Calculator.healthprocessor), new ItemStack(
					Calculator.itemEnergyModule), new ItemStack(
					Calculator.hungerprocessor), result));
		} else if (result.getItem() == Calculator.itemStorageModule) {
			this.arecipes.add(new SmeltingPair(new ItemStack(
					Calculator.reinforcediron_ingot), new ItemStack(
					Blocks.chest), new ItemStack(
					Calculator.reinforcediron_ingot), result));
		}

	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<ItemStack, ItemStack> recipes = AtomicCalculatorNEIRecipes.smelting().getSmeltingList();
		Map<ItemStack, ItemStack> recipes2 = AtomicCalculatorNEIRecipes.smelting().getSmeltingList2();
		Map<ItemStack, ItemStack> recipes3 = AtomicCalculatorNEIRecipes.smelting().getSmeltingList3();
		for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue(),ingredient)) {
				if (AtomicCalculatorNEIRecipes.smelting().getSmeltingResult2(recipe.getKey()) != null
						&& AtomicCalculatorNEIRecipes.smelting().getSmeltingResult3(recipe.getKey()) != null) {

					if (CalculatorConfig.isEnabled(recipe.getKey())) {
						SmeltingPair arecipe = new SmeltingPair(recipe.getValue(), AtomicCalculatorNEIRecipes.smelting().getSmeltingResult2(recipe.getKey()),
								AtomicCalculatorNEIRecipes.smelting().getSmeltingResult3(recipe.getKey()),recipe.getKey());
						arecipe.setIngredientPermutation(Arrays.asList(new PositionedStack[] { arecipe.input }),ingredient);
						this.arecipes.add(arecipe);
					}
				}
			}
		}
		for (Map.Entry<ItemStack, ItemStack> recipe2 : recipes2.entrySet()) {
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe2.getValue(),ingredient)) {

				if (AtomicCalculatorNEIRecipes.smelting().getSmeltingResult(recipe2.getKey()) != null&& AtomicCalculatorNEIRecipes.smelting().getSmeltingResult3(recipe2.getKey()) != null) {

					if (CalculatorConfig.isEnabled(recipe2.getKey())) {
						SmeltingPair arecipe = new SmeltingPair(
								AtomicCalculatorNEIRecipes.smelting().getSmeltingResult(recipe2.getKey()),
								recipe2.getValue(), AtomicCalculatorNEIRecipes.smelting().getSmeltingResult3(recipe2.getKey()),recipe2.getKey());
						arecipe.setIngredientPermutation(Arrays.asList(new PositionedStack[] { arecipe.input }),ingredient);
						this.arecipes.add(arecipe);
					}
				}
			}
		}
		for (Map.Entry<ItemStack, ItemStack> recipe3 : recipes3.entrySet()) {
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe3.getValue(),
					ingredient)) {

				if (AtomicCalculatorNEIRecipes.smelting().getSmeltingResult(recipe3.getKey()) != null
						&& AtomicCalculatorNEIRecipes.smelting().getSmeltingResult2(recipe3.getKey()) != null) {

					if (CalculatorConfig.isEnabled(recipe3.getKey())) {
						SmeltingPair arecipe = new SmeltingPair(
								AtomicCalculatorNEIRecipes.smelting().getSmeltingResult(recipe3.getKey()),
								AtomicCalculatorNEIRecipes.smelting().getSmeltingResult2(recipe3.getKey()),recipe3.getValue(), recipe3.getKey());
						arecipe.setIngredientPermutation(Arrays.asList(new PositionedStack[] { arecipe.input }),ingredient);
						this.arecipes.add(arecipe);
					}
				}
			}
		}

	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/atomiccalculator.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "atomic";
	}
}
