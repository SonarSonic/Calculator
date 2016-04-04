package sonar.calculator.mod.common.recipes.machines;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.core.SonarCore;

public class AnalysingChamberRecipes {
	private static final AnalysingChamberRecipes chanceRecipes = new AnalysingChamberRecipes();

	private Map chance1 = new HashMap();
	private Map chance2 = new HashMap();
	private Map chance3 = new HashMap();
	private Map chance4 = new HashMap();
	private Map chance5 = new HashMap();
	private Map level = new HashMap();

	public static AnalysingChamberRecipes instance() {
		return chanceRecipes;
	}

	private AnalysingChamberRecipes() {
		this.addRecipe(1, 1, new ItemStack(Calculator.itemCalculator, 1));
		this.addRecipe(1, 2, new ItemStack(SonarCore.reinforcedDirtBlock,1));
		this.addRecipe(1, 3, new ItemStack(SonarCore.reinforcedStoneBlock,1));
		this.addRecipe(1, 4, new ItemStack(Calculator.small_stone,1));
		this.addRecipe(1, 5, new ItemStack(Calculator.soil,1));
		this.addRecipe(1, 6, new ItemStack(Calculator.powerCube,1));
		this.addRecipe(1, 7, new ItemStack(Calculator.broccoli,1));
		this.addRecipe(1, 8, new ItemStack(Calculator.reinforcediron_axe, 1));
		this.addRecipe(1, 9, new ItemStack(Calculator.reinforcediron_sword, 1));
		this.addRecipe(1, 10, new ItemStack(Calculator.crankHandle,1));
		this.addRecipe(1, 11, new ItemStack(Calculator.handcrankedGenerator,1));
		this.addRecipe(1, 12, new ItemStack(Calculator.broccoliSeeds,1));
		this.addRecipe(1, 13, new ItemStack(Calculator.grenade,1));
		
		this.addRecipe(1, 14, new ItemStack(Calculator.calculator_assembly,1));
		this.addRecipe(1, 15, new ItemStack(Calculator.calculator_screen,1));
		this.addRecipe(1, 16, new ItemStack(Calculator.enrichedgold_axe, 1));
		this.addRecipe(1, 17, new ItemStack(Calculator.enrichedgold_pickaxe, 1));

		this.addRecipe(2, 1, new ItemStack(Calculator.enrichedGold,1));
		this.addRecipe(2, 2, new ItemStack(Calculator.circuitDamaged, 1));
		this.addRecipe(2, 3, new ItemStack(Calculator.circuitDirty, 1));
		this.addRecipe(2, 4, new ItemStack(Calculator.itemAdvancedTerrainModule, 1));
		this.addRecipe(2, 5, new ItemStack(Calculator.itemScientificCalculator,	1));
		this.addRecipe(2, 6, new ItemStack(Calculator.itemTerrainModule, 1));
		this.addRecipe(2, 7, new ItemStack(Calculator.itemCraftingCalculator, 1));
		this.addRecipe(2, 8, new ItemStack(Calculator.healthProcessor,1));
		this.addRecipe(2, 9, new ItemStack(Calculator.large_amethyst,1));
		this.addRecipe(2, 10, new ItemStack(Calculator.large_tanzanite,1));
		this.addRecipe(2, 11, new ItemStack(Calculator.amethystLog,1));
		this.addRecipe(2, 12, new ItemStack(Calculator.tanzaniteLog,1));
		this.addRecipe(2, 13, new ItemStack(Calculator.flawlessdiamond,1));
		this.addRecipe(2, 14, new ItemStack(Calculator.enriched_coal,1));
		this.addRecipe(2, 15, new ItemStack(Calculator.starchextractor,1));

		this.addRecipe(3, 1, new ItemStack(Calculator.itemEnergyModule,1));
		this.addRecipe(3, 2, new ItemStack(Calculator.itemHealthModule, 1));
		this.addRecipe(3, 3, new ItemStack(Calculator.itemHungerModule, 1));
		this.addRecipe(3, 4, new ItemStack(Calculator.AmethystSapling,1));
		this.addRecipe(3, 5, new ItemStack(Calculator.tanzaniteSapling,1));
		this.addRecipe(3, 6, new ItemStack(Calculator.hungerProcessor,1));
		this.addRecipe(3, 7, new ItemStack(Calculator.healthProcessor,1));
		this.addRecipe(3, 8, new ItemStack(Calculator.large_amethyst,1));
		this.addRecipe(3, 9, new ItemStack(Calculator.large_tanzanite,1));
		this.addRecipe(3, 10, new ItemStack(Calculator.tanzaniteLog,1));
		this.addRecipe(4, 11, new ItemStack(Blocks.obsidian,1));

		this.addRecipe(4, 1, new ItemStack(Calculator.advanced_assembly,1));
		this.addRecipe(4, 2, new ItemStack(Calculator.algorithmSeparator,1));
		this.addRecipe(4, 3, new ItemStack(Calculator.stoneSeparator,1));
		this.addRecipe(4, 4, new ItemStack(Calculator.atomic_module,1));
		this.addRecipe(4, 5, new ItemStack(Items.emerald,1));
		this.addRecipe(4, 6, new ItemStack(Items.diamond,1));
		this.addRecipe(4, 7, new ItemStack(Calculator.flawlessdiamond_sword,1));
		this.addRecipe(4, 8, new ItemStack(Calculator.electric_hoe, 1));
		this.addRecipe(4, 9, new ItemStack(Calculator.endforged_hoe, 1));
		this.addRecipe(4, 10, new ItemStack(Items.ender_pearl,1));
		this.addRecipe(4, 11, new ItemStack(Calculator.fiddledewFruit,1));
		this.addRecipe(4, 12, new ItemStack(Calculator.flawlessGlass,1));
		this.addRecipe(4, 13, new ItemStack(SonarCore.stableStone,1));
		this.addRecipe(4, 14, new ItemStack(Calculator.purifiedObsidian,1));
		this.addRecipe(4, 15, new ItemStack(Calculator.obsidianKey,1));

		this.addRecipe(5, 1, new ItemStack(Calculator.calculatorlocator,1));
		this.addRecipe(5, 2, new ItemStack(Calculator.calculatorplug,1));
		this.addRecipe(5, 3, new ItemStack(Calculator.itemFlawlessCalculator, 1));
		this.addRecipe(5, 4, new ItemStack(Calculator.atomic_assembly,1));
		this.addRecipe(5, 5, new ItemStack(Calculator.atomicCalculator,1));
		this.addRecipe(5, 6, new ItemStack(Items.ender_eye,1));
		this.addRecipe(5, 7, new ItemStack(Items.nether_star,1));
		this.addRecipe(5, 8, new ItemStack(Blocks.beacon,1));
		this.addRecipe(5, 9, new ItemStack(Calculator.endDiamond,1));
		this.addRecipe(5, 10, new ItemStack(Calculator.flawlessGreenhouse,1));

	}

	public void addRecipe(int chanceLevel, int number, ItemStack output) {
		switch (chanceLevel) {
		case 1:
			this.chance1.put(number, output);
			break;
		case 2:
			this.chance2.put(number, output);
			break;
		case 3:
			this.chance3.put(number, output);
			break;
		case 4:
			this.chance4.put(number, output);
			break;
		case 5:
			this.chance5.put(number, output);
			break;
		}
		level.put(output, chanceLevel);
	}

	public ItemStack getResult(int level, int number) {
		Iterator iterator = null;
		switch (level) {
		case 1:
			iterator = this.chance1.entrySet().iterator();
			break;
		case 2:
			iterator = this.chance2.entrySet().iterator();
			break;
		case 3:
			iterator = this.chance3.entrySet().iterator();
			break;
		case 4:
			iterator = this.chance4.entrySet().iterator();
			break;
		case 5:
			iterator = this.chance5.entrySet().iterator();
			break;

		}

		Map.Entry entry;
		do {
			if (!iterator.hasNext()) {
				return null;
			}

			entry = (Map.Entry) iterator.next();
		} while (!(number == (Integer) entry.getKey()));

		if(CalculatorConfig.isEnabled((ItemStack) entry.getValue())){
			return (ItemStack) entry.getValue();
		}else{
		return null;
		}
	}

	public int getChance(ItemStack stack) {
		Iterator iterator = this.level.entrySet().iterator();

		Map.Entry entry;
		do {
			if (!iterator.hasNext()) {
				return 0;
			}

			entry = (Map.Entry) iterator.next();
		} while (!func_151397_a(stack, (ItemStack) entry.getKey()));

		return (Integer) entry.getValue();
	}

	private boolean func_151397_a(ItemStack stack1, ItemStack stack2) {
		return (stack2.getItem() == stack1.getItem())
				&& ((stack2.getItemDamage() == 32767) || (stack2
						.getItemDamage() == stack1.getItemDamage()));
	}

	public Map getChanceList() {
		return this.level;
	}

}
