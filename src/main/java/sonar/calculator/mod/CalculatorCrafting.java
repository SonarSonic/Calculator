package sonar.calculator.mod;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CalculatorCrafting extends Calculator {

	public static void addRecipes() {

		// reinforced tools
		addShapedOre(new ItemStack(reinforced_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', reinforcedStoneBlock, 'S', "stickWood" });
		addShapedOre(new ItemStack(reinforced_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', reinforcedStoneBlock, 'S', "stickWood" });
		addShapedOre(new ItemStack(reinforced_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', reinforcedStoneBlock, 'S', "stickWood" });
		addShapedOre(new ItemStack(reinforced_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', reinforcedStoneBlock, 'S', "stickWood" });
		addShapedOre(new ItemStack(reinforced_sword, 1), new Object[] { " R ", " R ", " S ", 'R', reinforcedStoneBlock, 'S', "stickWood" });

		// reinforced iron tools
		addShapedOre(new ItemStack(reinforcediron_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', reinforcediron_ingot, 'S', "stickWood" });
		addShapedOre(new ItemStack(reinforcediron_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', reinforcediron_ingot, 'S', "stickWood" });
		addShapedOre(new ItemStack(reinforcediron_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', reinforcediron_ingot, 'S', "stickWood" });
		addShapedOre(new ItemStack(reinforcediron_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', reinforcediron_ingot, 'S', "stickWood" });
		addShapedOre(new ItemStack(reinforcediron_sword, 1), new Object[] { " R ", " R ", " S ", 'R', reinforcediron_ingot, 'S', "stickWood" });

		// redstone tools
		addShapedOre(new ItemStack(redstone_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', redstone_ingot, 'S', "stickWood" });
		addShapedOre(new ItemStack(redstone_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', redstone_ingot, 'S', "stickWood" });
		addShapedOre(new ItemStack(redstone_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', redstone_ingot, 'S', "stickWood" });
		addShapedOre(new ItemStack(redstone_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', redstone_ingot, 'S', "stickWood" });
		addShapedOre(new ItemStack(redstone_sword, 1), new Object[] { " R ", " R ", " S ", 'R', redstone_ingot, 'S', "stickWood" });

		// enriched gold tools
		addShapedOre(new ItemStack(enrichedgold_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', enrichedgold_ingot, 'S', "stickWood" });
		addShapedOre(new ItemStack(enrichedgold_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', enrichedgold_ingot, 'S', "stickWood" });
		addShapedOre(new ItemStack(enrichedgold_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', enrichedgold_ingot, 'S', "stickWood" });
		addShapedOre(new ItemStack(enrichedgold_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', enrichedgold_ingot, 'S', "stickWood" });
		addShapedOre(new ItemStack(enrichedgold_sword, 1), new Object[] { " R ", " R ", " S ", 'R', enrichedgold_ingot, 'S', "stickWood" });

		// weakened diamond tools
		addShapedOre(new ItemStack(weakeneddiamond_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', weakeneddiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(weakeneddiamond_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', weakeneddiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(weakeneddiamond_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', weakeneddiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(weakeneddiamond_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', weakeneddiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(weakeneddiamond_sword, 1), new Object[] { " R ", " R ", " S ", 'R', weakeneddiamond, 'S', "stickWood" });

		// flawless diamond tools
		addShapedOre(new ItemStack(flawlessdiamond_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', flawlessdiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(flawlessdiamond_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', flawlessdiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(flawlessdiamond_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', flawlessdiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(flawlessdiamond_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', flawlessdiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(flawlessdiamond_sword, 1), new Object[] { " R ", " R ", " S ", 'R', flawlessdiamond, 'S', "stickWood" });

		// fire diamond tools
		addShapedOre(new ItemStack(firediamond_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', firediamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(firediamond_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', firediamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(firediamond_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', firediamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(firediamond_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', firediamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(firediamond_sword, 1), new Object[] { " R ", " R ", " S ", 'R', firediamond, 'S', "stickWood" });

		// electric diamond tools
		addShapedOre(new ItemStack(electric_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', electricDiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(electric_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', electricDiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(electric_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', electricDiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(electric_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', electricDiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(electric_sword, 1), new Object[] { " R ", " R ", " S ", 'R', electricDiamond, 'S', "stickWood" });

		// end forged tools
		addShapedOre(new ItemStack(endforged_axe, 1), new Object[] { "RR ", "RS ", " S ", 'R', endDiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(endforged_pickaxe, 1), new Object[] { "RRR", " S ", " S ", 'R', endDiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(endforged_shovel, 1), new Object[] { " R ", " S ", " S ", 'R', endDiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(endforged_hoe, 1), new Object[] { "RR ", " S ", " S ", 'R', endDiamond, 'S', "stickWood" });
		addShapedOre(new ItemStack(endforged_sword, 1), new Object[] { " R ", " R ", " S ", 'R', endDiamond, 'S', "stickWood" });

		// calculator parts
		addShapedOre(new ItemStack(calculator_screen, 1), new Object[] { "CCC", "CRC", "CCC", 'C', "cobblestone", 'R', "dustRedstone" });
		addShaped(new ItemStack(calculator_assembly, 1), new Object[] { "BBB", "BBB", "BBB", 'B', Blocks.stone_button });
		addShapedOre(new ItemStack(advanced_assembly, 1), new Object[] { "A A", " D ", "A A", 'D', "gemDiamond", 'A', calculator_assembly });
		addShapedOre(new ItemStack(atomic_module, 1), new Object[] { "A A", " D ", "A A", 'D', "gemDiamond", 'A', itemScientificCalculator });
		addShapedOre(new ItemStack(atomic_assembly, 1), new Object[] { "BAB", "AEA", "BAB", 'E', "gemEmerald", 'A', atomic_module, 'B', advanced_assembly });
		addShapedOre(new ItemStack(atomic_binder, 8), new Object[] { " A ", "ADA", " A ", 'A', "calculatorReinforcedBlock", 'D', enrichedGold });

		// calculators
		addShapedOre(new ItemStack(itemInfoCalculator, 1), new Object[] { "CSC", "BAB", "CCC", 'B', Blocks.stone_button, 'A', "dustRedstone", 'S', calculator_screen, 'C', "cobblestone" });
		addShapedOre(new ItemStack(itemCalculator, 1), new Object[] { "CSC", "BAB", "CCC", 'B', Blocks.stone_button, 'A', calculator_assembly, 'S', calculator_screen, 'C', "cobblestone" });
		addShapedOre(new ItemStack(itemCraftingCalculator, 1), new Object[] { "CSC", "BAB", "CCC", 'B', Blocks.stone_button, 'A', Blocks.crafting_table, 'S', calculator_screen, 'C', "cobblestone" });
		addShapedOre(new ItemStack(itemScientificCalculator, 1), new Object[] { "CSC", "BAB", "CAC", 'B', "calculatorReinforcedBlock", 'A', calculator_assembly, 'S', calculator_screen, 'C', enrichedgold_ingot });

		addShapedOre(new ItemStack(atomicCalculator, 1), new Object[] { "DCD", "WAW", "DWD", 'D', "calculatorReinforcedBlock", 'A', atomic_assembly, 'C', calculator_screen, 'W', "gemDiamond" });

		addShapedOre(new ItemStack(dynamicCalculator, 1), new Object[] { "RSR", "MAM", "RDR", 'D', "gemDiamond", 'S', advanced_assembly, 'A', atomicCalculator, 'M', atomic_module, 'R', "calculatorReinforcedBlock" });

		addShapedOre(new ItemStack(itemFlawlessCalculator, 1), new Object[] { "FSF", "DAD", "FEF", 'F', flawlessdiamond, 'D', "gemDiamond", 'E', endDiamond, 'A', atomic_assembly, 'S', calculator_screen });

		// modules
		addShaped(new ItemStack(itemHungerModule, 1), new Object[] { "ADA", "BCB", "AEA", 'B', small_amethyst, 'A', shard_amethyst, 'C', Items.golden_apple, 'D', calculator_screen, 'E', redstone_ingot });
		addShaped(new ItemStack(itemHealthModule, 1), new Object[] { "ADA", "BCB", "AEA", 'B', small_tanzanite, 'A', shard_tanzanite, 'C', atomic_binder, 'D', calculator_screen, 'E', flawlessdiamond });

		// machines
		addShapedOre(new ItemStack(powerCube, 1), new Object[] { "AAA", "ADA", "AAA", 'A', "cobblestone", 'D', Blocks.furnace });
		addShaped(new ItemStack(advancedPowerCube, 1), new Object[] { "AAA", "ADA", "AAA", 'A', redstone_ingot, 'D', powerCube });
		addShaped(new ItemStack(basicGreenhouse, 1), new Object[] { "BCB", "CAC", "BCB", 'A', reinforced_iron_block, 'B', reinforcedStoneBlock, 'C', enrichedgold_ingot });
		addShaped(new ItemStack(advancedGreenhouse, 1), new Object[] { "BCB", "CAC", "BCB", 'A', basicGreenhouse, 'B', weakeneddiamond, 'C', large_tanzanite });
		addShaped(new ItemStack(flawlessGreenhouse, 1), new Object[] { "BBB", "CAC", "BBB", 'A', atomic_assembly, 'B', flawlessGlass, 'C', advancedGreenhouse });
		addShaped(new ItemStack(hungerProcessor, 1), new Object[] { "ABA", "BCB", "ABA", 'A', large_amethyst, 'B', redstone_ingot, 'C', advanced_assembly });
		addShaped(new ItemStack(healthProcessor, 1), new Object[] { "ABA", "BCB", "ABA", 'A', large_tanzanite, 'B', flawlessdiamond, 'C', advanced_assembly });
		addShaped(new ItemStack(analysingChamber, 1), new Object[] { "ABA", "BCB", "ABA", 'A', reinforced_iron_block, 'B', weakeneddiamond, 'C', advanced_assembly });
		addShaped(new ItemStack(atomicMultiplier, 1), new Object[] { "EEE", "DBD", "ACA", 'A', fluxPlug, 'B', atomic_assembly, 'C', fluxController, 'D', teleporter, 'E', calculatorplug });
		addShapedOre(new ItemStack(fluxController, 1), new Object[] { "ABA", "A A", "ACA", 'A', reinforcedStoneBlock, 'B', endDiamond, 'C', "calculatorStableStone" });

		// generators
		addShapedOre(new ItemStack(crank, 1), new Object[] { "  A", "AAA", "A  ", 'A', "stickWood" });
		addShapedOre(new ItemStack(handcrankedGenerator, 1), new Object[] { "AAA", "CBC", "AAA", 'A', "stickWood", 'B', powerCube, 'C', "plankWood" });
		addShapedOre(new ItemStack(calculatorlocator, 1), new Object[] { " B ", "CAC", "DDD", 'A', calculatorplug, 'B', Blocks.beacon, 'C', atomic_assembly, 'D', "calculatorStableStone" });
		addShapedOre(new ItemStack(calculatorplug, 1), new Object[] { " B ", "CAC", "DDD", 'A', "calculatorStableStone", 'B', itemEnergyModule, 'C', advanced_assembly, 'D', redstone_ingot });

		// smelting
		addShapedOre(new ItemStack(stoneSeparator, 1), new Object[] { "AAA", "BCB", "AAA", 'B', powerCube, 'A', "calculatorReinforcedBlock", 'C', reinforcediron_ingot });
		addShaped(new ItemStack(algorithmSeparator, 1), new Object[] { "AAA", "BCB", "AAA", 'B', advancedPowerCube, 'A', reinforced_iron_block, 'C', flawlessdiamond });
		addShapedOre(new ItemStack(extractionChamber, 1), new Object[] { "ABA", "BCB", "ABA", 'A', "calculatorReinforcedBlock", 'B', weakeneddiamond, 'C', powerCube });
		addShaped(new ItemStack(restorationChamber, 1), new Object[] { "ABA", "BCB", "ABA", 'A', redstone_ingot, 'B', weakeneddiamond, 'C', extractionChamber });
		addShaped(new ItemStack(reassemblyChamber, 1), new Object[] { "ABA", "BCB", "ABA", 'A', enrichedGold, 'B', weakeneddiamond, 'C', extractionChamber });
		addShaped(new ItemStack(precisionChamber, 1), new Object[] { "ABA", "BCB", "ABA", 'A', reinforced_iron_block, 'B', weakeneddiamond, 'C', extractionChamber });
		addShaped(new ItemStack(reinforcedFurnace, 1), new Object[] { "AAA", "ABA", "AAA", 'A', reinforcedStoneBlock, 'B', powerCube });

		// item_recipes
		addShapedOre(new ItemStack(grenadecasing, 1), new Object[] { "   ", "R R", "RRR", 'R', "calculatorReinforcedBlock" });
		addShaped(new ItemStack(ObsidianKey, 1), new Object[] { "  B", "BBB", "A B", 'A', purifiedobsidianBlock, 'B', enrichedgold_ingot });

		// gems
		addShaped(new ItemStack(large_amethyst, 1), new Object[] { "AAA", "AAA", "AAA", 'A', small_amethyst });
		addShaped(new ItemStack(small_amethyst, 1), new Object[] { "AAA", "AAA", "AAA", 'A', shard_amethyst });
		addShaped(new ItemStack(large_tanzanite, 1), new Object[] { "AAA", "AAA", "AAA", 'A', small_tanzanite });
		addShaped(new ItemStack(small_tanzanite, 1), new Object[] { "AAA", "AAA", "AAA", 'A', shard_tanzanite });
		// decoration
		addShaped(new ItemStack(amethyst_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', large_amethyst });
		addShapeless(new ItemStack(large_amethyst, 9), new Object[] { amethyst_block });
		addShaped(new ItemStack(tanzanite_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', large_tanzanite });
		addShapeless(new ItemStack(large_tanzanite, 9), new Object[] { tanzanite_block });
		addShaped(new ItemStack(end_diamond_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', endDiamond });
		addShapeless(new ItemStack(endDiamond, 9), new Object[] { end_diamond_block });
		addShaped(new ItemStack(enriched_gold_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', enrichedgold_ingot });
		addShapeless(new ItemStack(enrichedgold_ingot, 9), new Object[] { enriched_gold_block });
		addShaped(new ItemStack(flawless_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', flawlessdiamond });
		addShapeless(new ItemStack(flawlessdiamond, 9), new Object[] { flawless_block });
		addShaped(new ItemStack(flawless_fire_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', firediamond });
		addShapeless(new ItemStack(firediamond, 9), new Object[] { flawless_fire_block });
		addShaped(new ItemStack(reinforced_iron_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', reinforcediron_ingot });
		addShapeless(new ItemStack(reinforcediron_ingot, 9), new Object[] { reinforced_iron_block });
		addShaped(new ItemStack(weakened_diamond_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', weakeneddiamond });
		addShapeless(new ItemStack(weakeneddiamond, 9), new Object[] { weakened_diamond_block });
		addShaped(new ItemStack(electric_diamond_block, 1), new Object[] { "AAA", "AAA", "AAA", 'A', electricDiamond });
		addShapeless(new ItemStack(electricDiamond, 9), new Object[] { electric_diamond_block });

		// tree blocks
		addShapeless(new ItemStack(amethystPlanks, 4), new Object[] { amethystLog });
		addShapeless(new ItemStack(tanzanitePlanks, 4), new Object[] { tanzaniteLog });
		addShapeless(new ItemStack(pearPlanks, 4), new Object[] { pearLog });
		addShapeless(new ItemStack(diamondPlanks, 4), new Object[] { diamondLog });
		addShaped(new ItemStack(amethystStairs, 4), new Object[] { "A  ", "AA ", "AAA", 'A', amethystPlanks });
		addShaped(new ItemStack(tanzaniteStairs, 4), new Object[] { "A  ", "AA ", "AAA", 'A', tanzanitePlanks });
		addShaped(new ItemStack(pearStairs, 4), new Object[] { "A  ", "AA ", "AAA", 'A', pearPlanks });
		addShaped(new ItemStack(diamondStairs, 4), new Object[] { "A  ", "AA ", "AAA", 'A', diamondPlanks });

		addShapedOre(new ItemStack(amethystFence, 6), new Object[] { "ASA", "ASA", "   ", 'A', amethystPlanks, 'S', "stickWood" });
		addShapedOre(new ItemStack(tanzaniteFence, 6), new Object[] { "ASA", "ASA", "   ", 'A', tanzanitePlanks, 'S', "stickWood" });
		addShapedOre(new ItemStack(pearFence, 6), new Object[] { "ASA", "ASA", "   ", 'A', pearPlanks, 'S', "stickWood" });
		addShapedOre(new ItemStack(diamondFence, 6), new Object[] { "ASA", "ASA", "   ", 'A', diamondPlanks, 'S', "stickWood" });

		addShapeless(new ItemStack(stableglassBlock, 1), new Object[] { clearstableglassBlock });
		addShapeless(new ItemStack(clearstableglassBlock, 1), new Object[] { stableglassBlock });
		addShapedOre(new ItemStack(magneticFlux, 1), new Object[] { " D ", "RFR", "SSS", 'S', "calculatorStableStone", 'R', redstone_ingot, 'F', fluxPoint, 'D', firediamond });
		addShaped(new ItemStack(dockingStation, 1), new Object[] { " R ", "APA", "RRR", 'R', reinforcedStoneBlock, 'P', powerCube, 'A', calculator_assembly });
		addShapedOre(new ItemStack(teleporter, 2), new Object[] { "SRS", "RFR", "SDS", 'R', reinforcedStoneBlock, 'S', "calculatorStableStone", 'D', endDiamond, 'F', fluxPlug });
		addShaped(new ItemStack(weatherController, 1), new Object[] { "   ", "DSD", "RRR", 'S', Items.nether_star, 'D', electricDiamond, 'R', rainSensor });
		addShaped(new ItemStack(stoneAssimilator, 1), new Object[] { " S ", "IPI", " I ", 'P', stoneSeparator, 'I', reinforcedStoneBlock, 'S', sickle });
		addShaped(new ItemStack(algorithmAssimilator, 1), new Object[] { " S ", "IPI", " I ", 'P', algorithmSeparator, 'I', reinforced_iron_block, 'S', sickle });

		for (int i = 0; i < 16; i++) {
			int meta = ~i & 15;
			if (meta != 0) {
				addShaped(new ItemStack(stableStone, 8, meta), new Object[] { "SSS", "SDS", "SSS", 'D', new ItemStack(Items.dye, 1, i), 'S', new ItemStack(stableStone, 1, 0) });
			}
		}

		for (int i = 0; i < 16; i++) {
			addShapeless(new ItemStack(stablestonerimmedBlock, 1, i), new Object[] { new ItemStack(stableStone, 1, i) });
			addShapeless(new ItemStack(stablestonerimmedblackBlock, 1, i), new Object[] { new ItemStack(stablestonerimmedBlock, 1, i) });
			addShapeless(new ItemStack(stableStone, 1, i), new Object[] { new ItemStack(stablestonerimmedblackBlock, 1, i) });
		}

		addShaped(new ItemStack(reinforcedStoneStairs, 4), new Object[] { "A  ", "AA ", "AAA", 'A', reinforcedStoneBlock });
		addShaped(new ItemStack(reinforcedStoneBrickStairs, 4), new Object[] { "A  ", "AA ", "AAA", 'A', reinforcedStoneBrick });
		addShaped(new ItemStack(reinforcedDirtStairs, 4), new Object[] { "A  ", "AA ", "AAA", 'A', reinforcedDirtBlock });
		addShaped(new ItemStack(reinforcedDirtBrickStairs, 4), new Object[] { "A  ", "AA ", "AAA", 'A', reinforcedDirtBrick });

		addShapedOre(new ItemStack(reinforcedStoneFence, 6), new Object[] { "ASA", "ASA", "   ", 'A', reinforcedStoneBlock, 'S', "stickWood" });
		addShapedOre(new ItemStack(reinforcedStoneBrickFence, 6), new Object[] { "ASA", "ASA", "   ", 'A', reinforcedStoneBrick, 'S', "stickWood" });
		addShapedOre(new ItemStack(reinforcedDirtFence, 6), new Object[] { "ASA", "ASA", "   ", 'A', reinforcedDirtBlock, 'S', "stickWood" });
		addShapedOre(new ItemStack(reinforcedDirtBrickFence, 6), new Object[] { "ASA", "ASA", "   ", 'A', reinforcedDirtBrick, 'S', "stickWood" });
	}

	public static void addShaped(ItemStack result, Object... input) {
		if (result!=null && CalculatorConfig.isEnabled(result)) {
			//GameRegistry.addRecipe(result, input);
		}
	}

	public static void addShapedOre(ItemStack result, Object... input) {
		if (result!=null && CalculatorConfig.isEnabled(result)) {
			//ShapedOreRecipe oreRecipe = new ShapedOreRecipe(result, input);
			//GameRegistry.addRecipe(oreRecipe);
		}
	}

	public static void addShapeless(ItemStack result, Object... input) {
		if (result!=null && CalculatorConfig.isEnabled(result)) {
			//GameRegistry.addShapelessRecipe(result, input);
		}
	}
}