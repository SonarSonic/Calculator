package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.IResearchStore;
import sonar.calculator.mod.client.gui.calculators.GuiCalculator;
import sonar.calculator.mod.common.containers.ContainerDynamicCalculator;
import sonar.calculator.mod.common.recipes.crafting.RecipeRegistry;
import sonar.core.utils.helpers.FontHelper;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class CalculatorRecipeHandler extends TemplateRecipeHandler {
	public class SmeltingPair extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack input2;
		PositionedStack output;

		public SmeltingPair(Object input, Object input2, Object output) {
			super();
			this.input = new PositionedStack(input, 20, 24);
			this.input2 = new PositionedStack(input2, 74, 24);
			this.output = new PositionedStack(output, 129, 24);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(CalculatorRecipeHandler.this.cycleticks / 16, Arrays.asList(new PositionedStack[] { this.input, this.input2 }));
		}

		@Override
		public PositionedStack getResult() {
			return this.output;
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
		return FontHelper.translate("item.Calculator.name");
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("calculator"))
				&& (getClass() == CalculatorRecipeHandler.class && Minecraft.getMinecraft().thePlayer.getHeldItem() != null && Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof IResearchStore)) {
			IResearchStore calc = (IResearchStore) Minecraft.getMinecraft().thePlayer.getHeldItem().getItem();
			int[] unblocked = calc.getResearch(Minecraft.getMinecraft().thePlayer.getHeldItem());
			Map<Object[], Object[]> recipes = RecipeRegistry.CalculatorRecipes.instance().getRecipes();
			for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getValue()[0]));
			}
		} else if ((outputId.equals("dynacalculator")) && Minecraft.getMinecraft().thePlayer.openContainer instanceof ContainerDynamicCalculator) {

		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		IInventory inv = Minecraft.getMinecraft().thePlayer.inventory;
		int[] unblocked = findResearch(inv);
		Map<Object[], Object[]> recipes = RecipeRegistry.CalculatorRecipes.instance().getRecipes();

		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			if (RecipeRegistry.CalculatorRecipes.instance().containsStack(result, recipe.getValue(), false) != -1)
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getValue()[0]));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		IInventory inv = Minecraft.getMinecraft().thePlayer.inventory;
		int[] unblocked = findResearch(inv);
		Map<Object[], Object[]> recipes = RecipeRegistry.CalculatorRecipes.instance().getRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			if (RecipeRegistry.CalculatorRecipes.instance().containsStack(ingredient, recipe.getKey(), false) != -1)
				this.arecipes.add(new SmeltingPair(recipe.getKey()[0], recipe.getKey()[1], recipe.getValue()[0]));

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
