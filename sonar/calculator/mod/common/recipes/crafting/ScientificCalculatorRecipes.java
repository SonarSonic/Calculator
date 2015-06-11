package sonar.calculator.mod.common.recipes.crafting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import cpw.mods.fml.common.registry.GameRegistry;

public class ScientificCalculatorRecipes {
	private static final ScientificCalculatorRecipes smeltingBase = new ScientificCalculatorRecipes();

	private Map<Integer, CalculatorRecipe> standard = new HashMap();
	private Map idList = new HashMap();

	public static ScientificCalculatorRecipes recipes() {
		return smeltingBase;
	}

	private ScientificCalculatorRecipes() {

		addRecipe(Calculator.purified_coal, Items.coal, Calculator.enrichedgold_ingot, false);
		addRecipe(Calculator.redstone_ingot, Items.iron_ingot, Items.redstone, false);
		addRecipe(new ItemStack(Calculator.weakeneddiamond, 4), Items.diamond, Calculator.reinforcediron_ingot, false);
		addRecipe(Calculator.grenade, Calculator.baby_grenade, false);
		addRecipe(Calculator.firecoal, Calculator.enriched_coal, Items.lava_bucket, false);
		addOreDictRecipe(new ItemStack(Calculator.AmethystSapling, 1), new ItemStack(Calculator.large_amethyst, 1), "treeSapling", false);
		addRecipe(Calculator.AmethystSapling, Calculator.amethystLog, Calculator.amethystLeaf, false);
		addRecipe(Calculator.AmethystSapling, Calculator.amethystLog, Calculator.small_amethyst, false);
		addRecipe(Calculator.AmethystSapling, Calculator.amethystLog, Calculator.amethystLog, false);
		addRecipe(new ItemStack(Calculator.itemTerrainModule, 1), Calculator.itemCalculator, Calculator.redstone_ingot, false);
		addRecipe(new ItemStack(Calculator.starchextractor, 1), Calculator.itemEnergyModule, Calculator.small_amethyst, false);
		addRecipe(new ItemStack(Calculator.itemEnergyModule, 1), Calculator.powerCube, Calculator.purified_coal, false);

	}

	public void addOreDictRecipe(ItemStack output, ItemStack input, String name, boolean hidden) {
		int s = OreDictionary.getOres(name).size();
		for (int i = 0; i < s; i++) {
			addRecipe(output, input, OreDictionary.getOres(name).get(i), hidden);
		}
	}

	public void addRecipe(Object output, Object input, boolean hidden) {
		addFinal(newItemStack(output), newItemStack(input), newItemStack(input), hidden);
	}

	public void addRecipe(Object output, Object input, Object input2, boolean hidden) {
		addFinal(newItemStack(output), newItemStack(input), newItemStack(input2), hidden);
	}

	public ItemStack newItemStack(Object obj) {
		if (obj instanceof ItemStack) {
			return ((ItemStack) obj).copy();
		} else if (obj instanceof Item) {
			return new ItemStack((Item) obj, 1);
		} else {
			if (!(obj instanceof Block)) {
				throw new RuntimeException("Invalid Calculator recipe!");
			}

			return new ItemStack((Block) obj, 1);
		}
	}

	public void addFinal(ItemStack output, ItemStack input, ItemStack input2, boolean hidden) {
		if (!idList.containsValue(GameRegistry.findUniqueIdentifierFor(input.getItem()).toString() + ":" + String.valueOf(input.getItemDamage()))) {
			this.idList.put(idList.size() + 1, GameRegistry.findUniqueIdentifierFor(input.getItem()).toString() + ":" + String.valueOf(input.getItemDamage()));
		}
		if (!idList.containsValue(GameRegistry.findUniqueIdentifierFor(input2.getItem()).toString() + ":" + String.valueOf(input.getItemDamage()))) {
			this.idList.put(idList.size() + 1, GameRegistry.findUniqueIdentifierFor(input2.getItem()).toString() + ":" + String.valueOf(input.getItemDamage()));
		}
		if (!idList.containsValue(GameRegistry.findUniqueIdentifierFor(output.getItem()).toString() + ":" + String.valueOf(input.getItemDamage()))) {
			this.idList.put(idList.size() + 1, GameRegistry.findUniqueIdentifierFor(output.getItem()).toString() + ":" + String.valueOf(input.getItemDamage()));
		}
		int size = standard.size();
		this.standard.put(size, new CalculatorRecipe(output, input, input2, hidden));

	}

	public boolean recipeFull(InventoryCrafting matrix) {
		int i = 0;
		for (int j = 0; j < matrix.getSizeInventory(); ++j) {
			ItemStack itemstack2 = matrix.getStackInSlot(j);

			if (itemstack2 != null) {
				++i;
			}
		}
		return i == 2;

	}

	public ItemStack findMatchingRecipe(InventoryCrafting matrix) {

		if (!recipeFull(matrix)) {
			return null;
		}
		ItemStack result = null;
		CalculatorRecipe recipe = null;
		result = findMatch(matrix.getStackInSlot(0), matrix.getStackInSlot(1));
		recipe = findRecipe(matrix.getStackInSlot(0), matrix.getStackInSlot(1));
		if (result == null) {
			result = findMatch(matrix.getStackInSlot(1), matrix.getStackInSlot(0));
			recipe = findRecipe(matrix.getStackInSlot(1), matrix.getStackInSlot(0));
		}

		if (result != null && recipe != null) {
			if (!recipe.hidden) {
				if (CalculatorConfig.isEnabled(result)) {
					return result;
				} else {
					return null;
				}
			}

		}
		return null;
	}

	public CalculatorRecipe getRecipe(int size) {
		Iterator iterator = this.standard.entrySet().iterator();

		Map.Entry entry;
		do {
			if (!iterator.hasNext()) {
				return null;
			}

			entry = (Map.Entry) iterator.next();
		} while (!(size == (Integer) entry.getKey()));

		return (CalculatorRecipe) entry.getValue();

	}

	public CalculatorRecipe getRecipe(ItemStack stack) {
		Iterator iterator = this.standard.entrySet().iterator();

		Map.Entry entry;
		do {
			if (!iterator.hasNext()) {
				return null;
			}

			entry = (Map.Entry) iterator.next();
		} while (!(this.matchingStack(stack, ((CalculatorRecipe) entry.getValue()).output)));

		return (CalculatorRecipe) entry.getValue();
	}

	public ItemStack getRegisteredStack(int stack) {
		String string = getStack(stack);
		if (string != null) {
			String[] parts = string.split(":");
			return new ItemStack(GameRegistry.findItem(parts[0], parts[1]), 1, Integer.valueOf(parts[2]));

		} else {
			return null;
		}
	}

	public String getStack(int stack) {
		Iterator iterator = this.idList.entrySet().iterator();

		Map.Entry entry;
		do {
			if (!iterator.hasNext()) {
				return null;
			}

			entry = (Map.Entry) iterator.next();
		} while (!(stack == (Integer) entry.getKey()));

		return (String) entry.getValue();
	}

	public int getID(ItemStack stack) {
		if (stack != null) {
			return getID(GameRegistry.findUniqueIdentifierFor(stack.getItem()).toString() + ":" + String.valueOf(stack.getItemDamage()));
		} else {
			return 0;
		}

	}

	public int getID(String stack) {
		Iterator iterator = this.idList.entrySet().iterator();

		Map.Entry entry;
		do {
			if (!iterator.hasNext()) {
				return 0;
			}

			entry = (Map.Entry) iterator.next();
		} while (!stack.equals((String) entry.getValue()));

		return (Integer) entry.getKey();

	}

	public ItemStack findMatch(ItemStack input, ItemStack input2) {
		Iterator iterator = this.standard.entrySet().iterator();

		Map.Entry entry;
		do {
			if (!iterator.hasNext()) {
				return null;
			}

			entry = (Map.Entry) iterator.next();
		} while (!(matchingStack(input, ((CalculatorRecipe) entry.getValue()).input) && matchingStack(input2, ((CalculatorRecipe) entry.getValue()).input2)));

		return ((CalculatorRecipe) entry.getValue()).output.copy();

	}

	public CalculatorRecipe findRecipe(ItemStack input, ItemStack input2) {
		Iterator iterator = this.standard.entrySet().iterator();

		Map.Entry entry;
		do {
			if (!iterator.hasNext()) {
				return null;
			}

			entry = (Map.Entry) iterator.next();
		} while (!(matchingStack(input, ((CalculatorRecipe) entry.getValue()).input) && matchingStack(input2, ((CalculatorRecipe) entry.getValue()).input2)));

		return ((CalculatorRecipe) entry.getValue());

	}

	private boolean matchingStack(ItemStack stack1, ItemStack stack2) {
		return (stack2.getItem() == stack1.getItem()) && ((stack2.getItemDamage() == 32767) || (stack2.getItemDamage() == stack1.getItemDamage()));
	}

	public Map getStandardList() {
		return this.standard;
	}

	public Map getIDList() {
		return this.idList;
	}

}
