package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.IResearchStore;
import sonar.calculator.mod.client.gui.calculators.GuiCalculator;
import sonar.calculator.mod.common.containers.ContainerDynamicCalculator;
import sonar.calculator.mod.common.item.calculators.CalculatorItem;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipe;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipes;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class CalculatorRecipeHandler extends TemplateRecipeHandler {
	public class SmeltingPair extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack input2;
		PositionedStack output;

		public SmeltingPair(ItemStack input, ItemStack input2, ItemStack output) {
			super();
			input.stackSize = 1;
			this.input = new PositionedStack(input, 20, 24);
			this.input2 = new PositionedStack(input2, 74, 24);
			this.output = new PositionedStack(output, 129, 24);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(CalculatorRecipeHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] { this.input }));
		}

		@Override
		public PositionedStack getResult() {
			return this.output;
		}

		@Override
		public PositionedStack getOtherStack() {
			return this.input2;
		}
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(100, 25, 22, 14), "calculator", new Object[0]));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiCalculator.class;
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("item.Calculator.name");
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("calculator"))
				&& (getClass() == CalculatorRecipeHandler.class && Minecraft.getMinecraft().thePlayer.getHeldItem() != null && Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof IResearchStore)) {
			IResearchStore calc = (IResearchStore) Minecraft.getMinecraft().thePlayer.getHeldItem().getItem();
			int[] unblocked = calc.getResearch(Minecraft.getMinecraft().thePlayer.getHeldItem());
			Map<Integer, CalculatorRecipe> recipes = CalculatorRecipes.recipes().getStandardList();
			for (Map.Entry<Integer, CalculatorRecipe> recipe : recipes.entrySet()) {
				if (!recipe.getValue().hidden) {
					if (CalculatorConfig.isEnabled(((CalculatorRecipe) recipe.getValue()).output)) {
						this.arecipes.add(new SmeltingPair(((CalculatorRecipe) recipe.getValue()).input, ((CalculatorRecipe) recipe.getValue()).input2, ((CalculatorRecipe) recipe.getValue()).output));
					}
				} else if (unblocked != null && unblocked.length >= 1) {
					if (recipe.getValue().hidden && unblocked[CalculatorRecipes.recipes().getID(recipe.getValue().input)] != 0
							&& unblocked[CalculatorRecipes.recipes().getID(recipe.getValue().input2)] != 0 || unblocked[CalculatorRecipes.recipes().getID(recipe.getValue().output)] != 0) {
						if (CalculatorConfig.isEnabled(((CalculatorRecipe) recipe.getValue()).output)) {
							this.arecipes.add(new SmeltingPair(((CalculatorRecipe) recipe.getValue()).input, ((CalculatorRecipe) recipe.getValue()).input2,
									((CalculatorRecipe) recipe.getValue()).output));
						}
					}
				}

			}
		}
		else if ((outputId.equals("dynacalculator")) && Minecraft.getMinecraft().thePlayer.openContainer instanceof ContainerDynamicCalculator) {

			int[] unblocked = ((ContainerDynamicCalculator) Minecraft.getMinecraft().thePlayer.openContainer).entity.unblocked;
			Map<Integer, CalculatorRecipe> recipes = CalculatorRecipes.recipes().getStandardList();
			for (Map.Entry<Integer, CalculatorRecipe> recipe : recipes.entrySet()) {
				if (!recipe.getValue().hidden) {
					if (CalculatorConfig.isEnabled(((CalculatorRecipe) recipe.getValue()).output)) {
						this.arecipes.add(new SmeltingPair(((CalculatorRecipe) recipe.getValue()).input, ((CalculatorRecipe) recipe.getValue()).input2, ((CalculatorRecipe) recipe.getValue()).output));
					}
				} else if (unblocked != null && unblocked.length >= 1) {
					if (recipe.getValue().hidden && unblocked[CalculatorRecipes.recipes().getID(recipe.getValue().input)] != 0
							&& unblocked[CalculatorRecipes.recipes().getID(recipe.getValue().input2)] != 0 || unblocked[CalculatorRecipes.recipes().getID(recipe.getValue().output)] != 0) {
						if (CalculatorConfig.isEnabled(((CalculatorRecipe) recipe.getValue()).output)) {
							this.arecipes.add(new SmeltingPair(((CalculatorRecipe) recipe.getValue()).input, ((CalculatorRecipe) recipe.getValue()).input2,
									((CalculatorRecipe) recipe.getValue()).output));
						}
					}
				}
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		IInventory inv = Minecraft.getMinecraft().thePlayer.inventory;
		int[] unblocked = findResearch(inv);
		Map<Integer, CalculatorRecipe> recipes = CalculatorRecipes.recipes().getStandardList();

		for (Map.Entry<Integer, CalculatorRecipe> recipe : recipes.entrySet()) {

			if (NEIServerUtils.areStacksSameTypeCrafting(((CalculatorRecipe) recipe.getValue()).output, result)) {
				if (unblocked != null && unblocked.length >= 1) {
					if (recipe.getValue().hidden && unblocked[CalculatorRecipes.recipes().getID(recipe.getValue().input)] != 0
							&& unblocked[CalculatorRecipes.recipes().getID(recipe.getValue().input2)] != 0 || unblocked[CalculatorRecipes.recipes().getID(recipe.getValue().output)] != 0) {

						if (CalculatorConfig.isEnabled(((CalculatorRecipe) recipe.getValue()).output)) {
							this.arecipes.add(new SmeltingPair(((CalculatorRecipe) recipe.getValue()).input, ((CalculatorRecipe) recipe.getValue()).input2,
									((CalculatorRecipe) recipe.getValue()).output));
						}
					} else if (!recipe.getValue().hidden) {
						if (CalculatorConfig.isEnabled(((CalculatorRecipe) recipe.getValue()).output)) {
							this.arecipes.add(new SmeltingPair(((CalculatorRecipe) recipe.getValue()).input, ((CalculatorRecipe) recipe.getValue()).input2,
									((CalculatorRecipe) recipe.getValue()).output));
						}
					}
				}

			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("calculator")) && (getClass() == CalculatorRecipeHandler.class)) {
			loadCraftingRecipes("calculator", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Integer, CalculatorRecipe> recipes = CalculatorRecipes.recipes().getStandardList();
		IInventory inv = Minecraft.getMinecraft().thePlayer.inventory;
		int[] unblocked = findResearch(inv);

		for (Map.Entry<Integer, CalculatorRecipe> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameTypeCrafting(((CalculatorRecipe) recipe.getValue()).input, ingredient)
					|| NEIServerUtils.areStacksSameTypeCrafting(((CalculatorRecipe) recipe.getValue()).input2, ingredient)) {
				if (unblocked != null && unblocked.length >= 1) {
					if (recipe.getValue().hidden && unblocked[CalculatorRecipes.recipes().getID(recipe.getValue().input)] != 0
							&& unblocked[CalculatorRecipes.recipes().getID(recipe.getValue().input2)] != 0 || unblocked[CalculatorRecipes.recipes().getID(recipe.getValue().output)] != 0) {

						if (CalculatorConfig.isEnabled(((CalculatorRecipe) recipe.getValue()).output)) {
							this.arecipes.add(new SmeltingPair(((CalculatorRecipe) recipe.getValue()).input, ((CalculatorRecipe) recipe.getValue()).input2,
									((CalculatorRecipe) recipe.getValue()).output));
						}
					}
				} else if (!recipe.getValue().hidden) {
					if (CalculatorConfig.isEnabled(((CalculatorRecipe) recipe.getValue()).output)) {
						this.arecipes.add(new SmeltingPair(((CalculatorRecipe) recipe.getValue()).input, ((CalculatorRecipe) recipe.getValue()).input2, ((CalculatorRecipe) recipe.getValue()).output));
					}

				}
			}
		}
	}

	public static int[] findResearch(IInventory inv) {
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack target = inv.getStackInSlot(i);
			if (target != null && target.getItem() instanceof IResearchStore) {
				return ((IResearchStore) target.getItem()).getResearch(target);
			}
		}
		return null;
	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/calculator.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "calculator";
	}
}
