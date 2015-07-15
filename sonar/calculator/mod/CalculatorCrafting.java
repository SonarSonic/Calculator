package sonar.calculator.mod;

import sonar.calculator.mod.common.recipes.crafting.RecipeRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class CalculatorCrafting extends Calculator {

	public static void addRecipes() {

		// reinforced tools
		addShaped(new ItemStack(reinforced_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', reinforcedstoneBlock, 'S', Items.stick });
		addShaped(new ItemStack(reinforced_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', reinforcedstoneBlock, 'S', Items.stick });
		addShaped(new ItemStack(reinforced_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', reinforcedstoneBlock, 'S', Items.stick });
		addShaped(new ItemStack(reinforced_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', reinforcedstoneBlock, 'S', Items.stick });
		addShaped(new ItemStack(reinforced_sword, 1), new Object[] { " R ", " R ", " S ", 'R', reinforcedstoneBlock, 'S', Items.stick });

		// reinforced iron tools
		addShaped(new ItemStack(reinforcediron_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', reinforcediron_ingot, 'S', Items.stick });
		addShaped(new ItemStack(reinforcediron_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', reinforcediron_ingot, 'S', Items.stick });
		addShaped(new ItemStack(reinforcediron_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', reinforcediron_ingot, 'S', Items.stick });
		addShaped(new ItemStack(reinforcediron_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', reinforcediron_ingot, 'S', Items.stick });
		addShaped(new ItemStack(reinforcediron_sword, 1), new Object[] { " R ", " R ", " S ", 'R', reinforcediron_ingot, 'S', Items.stick });

		// redstone tools
		addShaped(new ItemStack(redstone_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', redstone_ingot, 'S', Items.stick });
		addShaped(new ItemStack(redstone_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', redstone_ingot, 'S', Items.stick });
		addShaped(new ItemStack(redstone_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', redstone_ingot, 'S', Items.stick });
		addShaped(new ItemStack(redstone_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', redstone_ingot, 'S', Items.stick });
		addShaped(new ItemStack(redstone_sword, 1), new Object[] { " R ", " R ", " S ", 'R', redstone_ingot, 'S', Items.stick });

		// enriched gold tools
		addShaped(new ItemStack(enrichedgold_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', enrichedgold_ingot, 'S', Items.stick });
		addShaped(new ItemStack(enrichedgold_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', enrichedgold_ingot, 'S', Items.stick });
		addShaped(new ItemStack(enrichedgold_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', enrichedgold_ingot, 'S', Items.stick });
		addShaped(new ItemStack(enrichedgold_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', enrichedgold_ingot, 'S', Items.stick });
		addShaped(new ItemStack(enrichedgold_sword, 1), new Object[] { " R ", " R ", " S ", 'R', enrichedgold_ingot, 'S', Items.stick });

		// weakened diamond tools
		addShaped(new ItemStack(weakeneddiamond_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', weakeneddiamond, 'S', Items.stick });
		addShaped(new ItemStack(weakeneddiamond_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', weakeneddiamond, 'S', Items.stick });
		addShaped(new ItemStack(weakeneddiamond_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', weakeneddiamond, 'S', Items.stick });
		addShaped(new ItemStack(weakeneddiamond_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', weakeneddiamond, 'S', Items.stick });
		addShaped(new ItemStack(weakeneddiamond_sword, 1), new Object[] { " R ", " R ", " S ", 'R', weakeneddiamond, 'S', Items.stick });

		// flawless diamond tools
		addShaped(new ItemStack(flawlessdiamond_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', flawlessdiamond, 'S', Items.stick });
		addShaped(new ItemStack(flawlessdiamond_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', flawlessdiamond, 'S', Items.stick });
		addShaped(new ItemStack(flawlessdiamond_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', flawlessdiamond, 'S', Items.stick });
		addShaped(new ItemStack(flawlessdiamond_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', flawlessdiamond, 'S', Items.stick });
		addShaped(new ItemStack(flawlessdiamond_sword, 1), new Object[] { " R ", " R ", " S ", 'R', flawlessdiamond, 'S', Items.stick });

		// fire diamond tools
		addShaped(new ItemStack(firediamond_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', flawlessfirediamond, 'S', Items.stick });
		addShaped(new ItemStack(firediamond_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', flawlessfirediamond, 'S', Items.stick });
		addShaped(new ItemStack(firediamond_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', flawlessfirediamond, 'S', Items.stick });
		addShaped(new ItemStack(firediamond_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', flawlessfirediamond, 'S', Items.stick });
		addShaped(new ItemStack(firediamond_sword, 1), new Object[] { " R ", " R ", " S ", 'R', flawlessfirediamond, 'S', Items.stick });

		// electric diamond tools
		addShaped(new ItemStack(electric_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', electricdiamond, 'S', Items.stick });
		addShaped(new ItemStack(electric_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', electricdiamond, 'S', Items.stick });
		addShaped(new ItemStack(electric_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', electricdiamond, 'S', Items.stick });
		addShaped(new ItemStack(electric_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', electricdiamond, 'S', Items.stick });
		addShaped(new ItemStack(electric_sword, 1), new Object[] { " R ", " R ", " S ", 'R', electricdiamond, 'S', Items.stick });

		// end forged tools
		addShaped(new ItemStack(endforged_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', enddiamond, 'S', Items.stick });
		addShaped(new ItemStack(endforged_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', enddiamond, 'S', Items.stick });
		addShaped(new ItemStack(endforged_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', enddiamond, 'S', Items.stick });
		addShaped(new ItemStack(endforged_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', enddiamond, 'S', Items.stick });
		addShaped(new ItemStack(endforged_sword, 1), new Object[] { " R ", " R ", " S ", 'R', enddiamond, 'S', Items.stick });

		// calculator parts
		addShaped(new ItemStack(calculator_screen, 1), new Object[] { "CCC", "CRC", "CCC", 'C', Blocks.cobblestone, 'R', Items.redstone });
		addShaped(new ItemStack(calculator_assembly, 1), new Object[] { "BBB", "BBB", "BBB", 'B', Blocks.stone_button });
		addShaped(new ItemStack(advanced_assembly, 1), new Object[] { "A A", " D ", "A A", 'D', Items.diamond, 'A', calculator_assembly });
		addShaped(new ItemStack(atomic_module, 1), new Object[] { "A A", " D ", "A A", 'D', Items.diamond, 'A', itemScientificCalculator });
		addShaped(new ItemStack(atomic_assembly, 1), new Object[] { "BAB", "AEA", "BAB", 'E', Items.emerald, 'A', atomic_module, 'B', advanced_assembly });
		addShapedOre(new ShapedOreRecipe(new ItemStack(atomic_binder, 8), new Object[] { " A ", "ADA", " A ", 'A', "calculatorReinforcedBlock", 'D', enrichedgold }));

		// calculators
		addShaped(new ItemStack(itemInfoCalculator, 1), new Object[] { "CSC", "BAB", "CCC", 'B', Blocks.stone_button, 'A', Items.redstone, 'S', calculator_screen, 'C', Blocks.cobblestone });
		addShaped(new ItemStack(itemCalculator, 1), new Object[] { "CSC", "BAB", "CCC", 'B', Blocks.stone_button, 'A', calculator_assembly, 'S', calculator_screen, 'C', Blocks.cobblestone });
		addShaped(new ItemStack(itemCraftingCalculator, 1), new Object[] { "CSC", "BAB", "CCC", 'B', Blocks.stone_button, 'A', Blocks.crafting_table, 'S', calculator_screen, 'C', Blocks.cobblestone });
		addShapedOre(new ShapedOreRecipe(new ItemStack(itemScientificCalculator, 1), new Object[] { "CSC", "BAB", "CAC", 'B', "calculatorReinforcedBlock", 'A', calculator_assembly, 'S', calculator_screen, 'C', enrichedgold_ingot }));

		addShapedOre(new ShapedOreRecipe(new ItemStack(atomiccalculatorBlock, 1), new Object[] { "DCD", "WAW", "DWD", 'D', "calculatorReinforcedBlock", 'A', atomic_assembly, 'C', calculator_screen, 'W', Items.diamond }));

		addShapedOre(new ShapedOreRecipe(new ItemStack(dynamiccalculatorBlock, 1), new Object[] { "RSR", "MAM", "RDR", 'D', Items.diamond, 'S', advanced_assembly, 'A', atomiccalculatorBlock, 'M', atomic_module, 'R', "calculatorReinforcedBlock" }));

		addShaped(new ItemStack(itemFlawlessCalculator, 1), new Object[] { "FSF", "DAD", "FEF", 'F', flawlessdiamond, 'D', Items.diamond, 'E', enddiamond, 'A', atomic_assembly, 'S', calculator_screen });

		// modules
		addShaped(new ItemStack(itemHungerModule, 1), new Object[] { "ADA", "BCB", "AEA", 'B', small_amethyst, 'A', shard_amethyst, 'C', Items.golden_apple, 'D', calculator_screen, 'E', redstone_ingot });
		addShaped(new ItemStack(itemHealthModule, 1), new Object[] { "ADA", "BCB", "AEA", 'B', small_tanzanite, 'A', shard_tanzanite, 'C', atomic_binder, 'D', calculator_screen, 'E', flawlessdiamond });

		// machines
		addShaped(new ItemStack(powerCube, 1), new Object[] { "AAA", "ADA", "AAA", 'A', Blocks.cobblestone, 'D', Blocks.furnace });
		addShaped(new ItemStack(advancedPowerCube, 1), new Object[] { "AAA", "ADA", "AAA", 'A', redstone_ingot, 'D', powerCube });
		addShaped(new ItemStack(basicGreenhouse, 1), new Object[] { "BCB", "CAC", "BCB", 'A', reinforced_iron_block, 'B', reinforcedstoneBlock, 'C', enrichedgold_ingot });
		addShaped(new ItemStack(advancedGreenhouse, 1), new Object[] { "BCB", "CAC", "BCB", 'A', basicGreenhouse, 'B', weakeneddiamond, 'C', large_tanzanite });
		addShaped(new ItemStack(flawlessGreenhouse, 1), new Object[] { "BBB", "CAC", "BBB", 'A', atomic_assembly, 'B', flawlessGlass, 'C', advancedGreenhouse });
		addShaped(new ItemStack(hungerprocessor, 1), new Object[] { "ABA", "BCB", "ABA", 'A', large_amethyst, 'B', redstone_ingot, 'C', advanced_assembly });
		addShaped(new ItemStack(healthprocessor, 1), new Object[] { "ABA", "BCB", "ABA", 'A', large_tanzanite, 'B', flawlessdiamond, 'C', advanced_assembly });
		addShaped(new ItemStack(analysingChamber, 1), new Object[] { "ABA", "BCB", "ABA", 'A', reinforced_iron_block, 'B', weakeneddiamond, 'C', advanced_assembly });
		addShaped(new ItemStack(atomicMultiplier, 1), new Object[] { "AEA", "DBD", "ACA", 'A', reinforced_iron_block, 'B', atomic_assembly, 'C', advancedPowerCube, 'D', purifiedobsidianBlock, 'E', redstone_ingot });
		addShaped(new ItemStack(fluxController, 1), new Object[] { "ABA", "A A", "ACA", 'A', reinforcedstoneBlock, 'B', enddiamond, 'C', stablestoneBlock});

		// generators
		addShaped(new ItemStack(crank, 1), new Object[] { "  A", "AAA", "A  ", 'A', Items.stick });
		addShaped(new ItemStack(handcrankedGenerator, 1), new Object[] { "AAA", "CBC", "AAA", 'A', Items.stick, 'B', powerCube, 'C', Blocks.planks });
		addShaped(new ItemStack(calculatorlocator, 1), new Object[] { " B ", "CAC", "DDD", 'A', calculatorplug, 'B', Blocks.beacon, 'C', atomic_assembly, 'D', stablestoneBlock });
		addShaped(new ItemStack(calculatorplug, 1), new Object[] { " B ", "CAC", "DDD", 'A', stablestoneBlock, 'B', itemEnergyModule, 'C', advanced_assembly, 'D', redstone_ingot });

		// smelting
		addShapedOre(new ShapedOreRecipe(new ItemStack(stoneSeperator, 1), new Object[] { "AAA", "BCB", "AAA", 'B', powerCube, 'A', "calculatorReinforcedBlock", 'C', redstone_ingot }));
		addShaped(new ItemStack(algorithmSeperator, 1), new Object[] { "AAA", "BCB", "AAA", 'B', advancedPowerCube, 'A', reinforced_iron_block, 'C', flawlessdiamond });
		addShapedOre(new ShapedOreRecipe(new ItemStack(extractionChamber, 1), new Object[] { "ABA", "BCB", "ABA", 'A', "calculatorReinforcedBlock", 'B', weakeneddiamond, 'C', powerCube }));
		addShaped(new ItemStack(restorationChamber, 1), new Object[] { "ABA", "BCB", "ABA", 'A', redstone_ingot, 'B', weakeneddiamond, 'C', extractionChamber });
		addShaped(new ItemStack(reassemblyChamber, 1), new Object[] { "ABA", "BCB", "ABA", 'A', enrichedgold, 'B', weakeneddiamond, 'C', extractionChamber });
		addShaped(new ItemStack(precisionChamber, 1), new Object[] { "ABA", "BCB", "ABA", 'A', reinforced_iron_block, 'B', weakeneddiamond, 'C', extractionChamber });
		addShaped(new ItemStack(reinforcedFurnace, 1), new Object[] { "AAA", "ABA", "AAA", 'A', reinforcedstoneBlock, 'B', powerCube });

		// item_recipes
		addShapedOre(new ShapedOreRecipe(new ItemStack(grenadecasing, 1), new Object[] { "   ", "R R", "RRR", 'R', "calculatorReinforcedBlock" }));
		addShaped(new ItemStack(ObsidianKey, 1), new Object[] { "  B", "BBB", "A B", 'A', purifiedobsidianBlock, 'B', enrichedgold_ingot });

		// gems
		addShaped(new ItemStack(large_amethyst, 1), new Object[] { "AAA", "AAA", "AAA", 'A', small_amethyst });
		addShaped(new ItemStack(small_amethyst, 1), new Object[] { "AAA", "AAA", "AAA", 'A', shard_amethyst });
		addShaped(new ItemStack(large_tanzanite, 1), new Object[] { "AAA", "AAA", "AAA", 'A', small_tanzanite });
		addShaped(new ItemStack(small_tanzanite, 1), new Object[] { "AAA", "AAA", "AAA", 'A', shard_tanzanite });
		// decoration
		addShaped(new ItemStack(amethyst_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', small_amethyst });
		addShapeless(new ItemStack(small_amethyst, 9), new Object[] { amethyst_block });
		addShaped(new ItemStack(tanzanite_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', small_tanzanite });
		addShapeless(new ItemStack(small_tanzanite, 9), new Object[] { tanzanite_block });
		addShaped(new ItemStack(end_diamond_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', enddiamond });
		addShapeless(new ItemStack(enddiamond, 9), new Object[] { end_diamond_block });
		addShaped(new ItemStack(enriched_gold_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', enrichedgold_ingot });
		addShapeless(new ItemStack(enrichedgold_ingot, 9), new Object[] { enriched_gold_block });
		addShaped(new ItemStack(flawless_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', flawlessdiamond });
		addShapeless(new ItemStack(flawlessdiamond, 9), new Object[] { flawless_block });
		addShaped(new ItemStack(flawless_fire_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', flawlessfirediamond });
		addShapeless(new ItemStack(flawlessfirediamond, 9), new Object[] { flawless_fire_block });
		addShaped(new ItemStack(reinforced_iron_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', reinforcediron_ingot });
		addShapeless(new ItemStack(reinforcediron_ingot, 9), new Object[] { reinforced_iron_block });
		addShaped(new ItemStack(weakened_diamond_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', weakeneddiamond });
		addShapeless(new ItemStack(weakeneddiamond, 9), new Object[] { weakened_diamond_block });
		addShaped(new ItemStack(electric_diamond_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', electricdiamond });
		addShapeless(new ItemStack(electricdiamond, 9), new Object[] { electric_diamond_block });

		// tree blocks
		addShapeless(new ItemStack(amethystPlanks, 4), new Object[] { amethystLog });
		addShapeless(new ItemStack(tanzanitePlanks, 4), new Object[] { tanzaniteLog });
		addShapeless(new ItemStack(pearPlanks, 4), new Object[] { pearLog });
		addShapeless(new ItemStack(diamondPlanks, 4), new Object[] { diamondLog });
		addShaped(new ItemStack(amethystStairs, 4), new Object[] { "A  ", "AA ", "AAA", 'A', amethystPlanks });
		addShaped(new ItemStack(tanzaniteStairs, 4), new Object[] { "A  ", "AA ", "AAA", 'A', tanzanitePlanks });
		addShaped(new ItemStack(pearStairs, 4), new Object[] { "A  ", "AA ", "AAA", 'A', pearPlanks });
		addShaped(new ItemStack(diamondStairs, 4), new Object[] { "A  ", "AA ", "AAA", 'A', diamondPlanks });
		

		addShaped(new ItemStack(amethystFence, 6), new Object[] { "ASA", "ASA", "   ", 'A', amethystPlanks, 'S', Items.stick });
		addShaped(new ItemStack(tanzaniteFence, 6), new Object[] { "ASA", "ASA", "   ", 'A', tanzanitePlanks, 'S', Items.stick });
		addShaped(new ItemStack(pearFence, 6), new Object[] { "ASA", "ASA", "   ", 'A', pearPlanks, 'S', Items.stick });
		addShaped(new ItemStack(diamondFence, 6), new Object[] { "ASA", "ASA", "   ", 'A', diamondPlanks, 'S', Items.stick });

		addShapeless(new ItemStack(stableglassBlock, 1), new Object[] { clearstableglassBlock });
		addShapeless(new ItemStack(clearstableglassBlock, 1), new Object[] { stableglassBlock });
		addShaped(new ItemStack(magneticFlux, 1), new Object[] { " D ", "RFR", "SSS", 'S', stablestoneBlock, 'R', redstone_ingot, 'F', fluxPoint, 'D', flawlessfirediamond });

		
	}

	public static void addShaped(ItemStack result, Object... input) {
		if (CalculatorConfig.isEnabled(result)) {
			GameRegistry.addRecipe(result, input);
		}
	}

	public static void addShapedOre(ShapedOreRecipe recipe) {
		if (CalculatorConfig.isEnabled(recipe.getRecipeOutput())) {
			GameRegistry.addRecipe(recipe);
		}
	}

	public static void addShapeless(ItemStack result, Object... input) {
		if (CalculatorConfig.isEnabled(result)) {
			GameRegistry.addShapelessRecipe(result, input);
		}
	}
}