package sonar.calculator.mod.integration.nei.handlers;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.client.gui.calculators.GuiInfoCalculator;
import sonar.calculator.mod.utils.InfoList;
import sonar.core.helpers.FontHelper;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class InfoUsageHandler extends TemplateRecipeHandler {

	public static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

	public class InfoPair

	extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		public String info;

		public InfoPair(ItemStack input, String info) {
			this.info = info;
			input.stackSize = 1;
			this.input = new PositionedStack(input, 1, 5);
		}

		@Override
		public PositionedStack getResult() {
			return input;
		}

	}

	public static ArrayList<InfoPair> ainfo;

	@Override
	public String getRecipeName() {
		return FontHelper.translate("info.type");
	}

	@Override
	public void loadTransferRects() {

		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(7, 2, 72, 12), "All", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(7, 20, 72, 12), "Blocks", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(7, 38, 72, 12), "Calculators", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(7, 56, 72, 12), "Machines", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(98, 2, 72, 12), "Generators", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(98, 20, 72, 12), "Modules", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(98, 38, 72, 12), "Items", new Object[0]));
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(98, 56, 72, 12), "Circuits", new Object[0]));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiInfoCalculator.class;
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<ItemStack, String> recipes = InfoList.info().getInfoList();
		for (Map.Entry<ItemStack, String> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
				InfoPair arecipe = new InfoPair(ingredient, recipe.getValue());
				arecipe.setIngredientPermutation(Arrays.asList(new PositionedStack[] { arecipe.input }), ingredient);
				this.arecipes.add(arecipe);
				this.transferRects.clear();
			}
		}

	}

	@Override
	public String getGuiTexture() {
		return "Calculator:textures/gui/iteminfo.png";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("Machines")) && (getClass() == InfoUsageHandler.class)) {
			Map<ItemStack, String> Machines = InfoList.info().getInfoList();
			for (Map.Entry<ItemStack, String> recipe : Machines.entrySet())
				if (InfoList.info().getType(recipe.getKey()) == "Machines") {
					this.arecipes.add(new InfoPair(recipe.getKey(), recipe.getValue()));
					this.transferRects.clear();
				}
		} else if ((outputId.equals("Calculators")) && (getClass() == InfoUsageHandler.class)) {
			Map<ItemStack, String> recipes2 = InfoList.info().getInfoList();
			for (Map.Entry<ItemStack, String> recipe2 : recipes2.entrySet())
				if (InfoList.info().getType(recipe2.getKey()) == "Calculators") {
					this.arecipes.add(new InfoPair(recipe2.getKey(), recipe2.getValue()));
					this.transferRects.clear();
				}
		} else if ((outputId.equals("Items")) && (getClass() == InfoUsageHandler.class)) {
			Map<ItemStack, String> recipes3 = InfoList.info().getInfoList();
			for (Map.Entry<ItemStack, String> recipe3 : recipes3.entrySet())
				if (InfoList.info().getType(recipe3.getKey()) == "Items") {
					this.arecipes.add(new InfoPair(recipe3.getKey(), recipe3.getValue()));
					this.transferRects.clear();
				}
		} else if ((outputId.equals("Generators")) && (getClass() == InfoUsageHandler.class)) {
			Map<ItemStack, String> recipes4 = InfoList.info().getInfoList();
			for (Map.Entry<ItemStack, String> recipe4 : recipes4.entrySet())
				if (InfoList.info().getType(recipe4.getKey()) == "Generators") {
					this.arecipes.add(new InfoPair(recipe4.getKey(), recipe4.getValue()));
					this.transferRects.clear();
				}
		} else if ((outputId.equals("Circuits")) && (getClass() == InfoUsageHandler.class)) {
			Map<ItemStack, String> recipes5 = InfoList.info().getInfoList();
			for (Map.Entry<ItemStack, String> recipe5 : recipes5.entrySet())
				if (InfoList.info().getType(recipe5.getKey()) == "Circuits") {
					this.arecipes.add(new InfoPair(recipe5.getKey(), recipe5.getValue()));
					this.transferRects.clear();
				}
		} else if ((outputId.equals("Modules")) && (getClass() == InfoUsageHandler.class)) {
			Map<ItemStack, String> recipes5 = InfoList.info().getInfoList();
			for (Map.Entry<ItemStack, String> recipe6 : recipes5.entrySet())
				if (InfoList.info().getType(recipe6.getKey()) == "Modules") {
					this.arecipes.add(new InfoPair(recipe6.getKey(), recipe6.getValue()));
					this.transferRects.clear();
				}
		} else if ((outputId.equals("Blocks")) && (getClass() == InfoUsageHandler.class)) {
			Map<ItemStack, String> recipes5 = InfoList.info().getInfoList();
			for (Map.Entry<ItemStack, String> recipe7 : recipes5.entrySet())
				if (InfoList.info().getType(recipe7.getKey()) == "Blocks") {
					this.arecipes.add(new InfoPair(recipe7.getKey(), recipe7.getValue()));
					this.transferRects.clear();
				}
		}

		else if ((outputId.equals("All")) && (getClass() == InfoUsageHandler.class)) {
			Map<ItemStack, String> recipes5 = InfoList.info().getInfoList();
			for (Map.Entry<ItemStack, String> recipe7 : recipes5.entrySet())
				this.arecipes.add(new InfoPair(recipe7.getKey(), recipe7.getValue()));
			this.transferRects.clear();

		} else {
			super.loadCraftingRecipes(outputId, results);
		}

	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<ItemStack, String> recipes = InfoList.info().getInfoList();

		for (Map.Entry<ItemStack, String> recipe : recipes.entrySet()) {
			if (InfoList.info().getInfo(result) == recipe.getValue())
				this.arecipes.add(new InfoPair(recipe.getKey(), recipe.getValue()));

			this.transferRects.clear();
		}
	}

	@Override
	public void drawExtras(int recipe) {
		if (arecipes.get(recipe).getResult().item != null) {
			ItemStack stack = arecipes.get(recipe).getResult().item;
			String info = InfoList.info().getInfo(stack);
			String[] parts = info.split("-");
			int length = parts.length;

			fontRenderer.drawString(stack.getDisplayName(), 20, 5, 1);

			for (int j = 0; j < length; j++) {
				fontRenderer.drawString(parts[j], 2, 25 + (j * 10), 4210752);
			}
		}
	}

	@Override
	public int recipiesPerPage() {

		return 1;

	}

	@Override
	public String getOverlayIdentifier() {
		return "info";
	}

}
