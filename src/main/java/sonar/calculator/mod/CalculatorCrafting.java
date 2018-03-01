package sonar.calculator.mod;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.common.block.MaterialBlock;
import sonar.calculator.mod.common.block.MaterialBlock.Variants;
import sonar.core.SonarCrafting;

public class CalculatorCrafting extends Calculator {
	public static void addRecipes() {
        ResourceLocation group = new ResourceLocation(name);
		// reinforced tools
        SonarCrafting.addShapedOre(modid, new ItemStack(reinforced_axe, 1), "RR ", "RS ", " S ", 'R', "calculatorReinforcedStone", 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(reinforced_pickaxe, 1), "RRR", " S ", " S ", 'R', "calculatorReinforcedStone", 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(reinforced_shovel, 1), " R ", " S ", " S ", 'R', "calculatorReinforcedStone", 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(reinforced_hoe, 1), "RR ", " S ", " S ", 'R', "calculatorReinforcedStone", 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(reinforced_sword, 1), " R ", " R ", " S ", 'R', "calculatorReinforcedStone", 'S', "stickWood");

		// reinforced iron tools
        SonarCrafting.addShapedOre(modid, new ItemStack(reinforcediron_axe, 1), "RR ", "RS ", " S ", 'R', reinforcediron_ingot, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(reinforcediron_pickaxe, 1), "RRR", " S ", " S ", 'R', reinforcediron_ingot, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(reinforcediron_shovel, 1), " R ", " S ", " S ", 'R', reinforcediron_ingot, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(reinforcediron_hoe, 1), "RR ", " S ", " S ", 'R', reinforcediron_ingot, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(reinforcediron_sword, 1), " R ", " R ", " S ", 'R', reinforcediron_ingot, 'S', "stickWood");

		// redstone tools
        SonarCrafting.addShapedOre(modid, new ItemStack(redstone_axe, 1), "RR ", "RS ", " S ", 'R', redstone_ingot, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(redstone_pickaxe, 1), "RRR", " S ", " S ", 'R', redstone_ingot, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(redstone_shovel, 1), " R ", " S ", " S ", 'R', redstone_ingot, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(redstone_hoe, 1), "RR ", " S ", " S ", 'R', redstone_ingot, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(redstone_sword, 1), " R ", " R ", " S ", 'R', redstone_ingot, 'S', "stickWood");

		// enriched gold tools
        SonarCrafting.addShapedOre(modid, new ItemStack(enrichedgold_axe, 1), "RR ", "RS ", " S ", 'R', enrichedgold_ingot, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(enrichedgold_pickaxe, 1), "RRR", " S ", " S ", 'R', enrichedgold_ingot, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(enrichedgold_shovel, 1), " R ", " S ", " S ", 'R', enrichedgold_ingot, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(enrichedgold_hoe, 1), "RR ", " S ", " S ", 'R', enrichedgold_ingot, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(enrichedgold_sword, 1), " R ", " R ", " S ", 'R', enrichedgold_ingot, 'S', "stickWood");

		// weakened diamond tools
        SonarCrafting.addShapedOre(modid, new ItemStack(weakeneddiamond_axe, 1), "RR ", "RS ", " S ", 'R', weakeneddiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(weakeneddiamond_pickaxe, 1), "RRR", " S ", " S ", 'R', weakeneddiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(weakeneddiamond_shovel, 1), " R ", " S ", " S ", 'R', weakeneddiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(weakeneddiamond_hoe, 1), "RR ", " S ", " S ", 'R', weakeneddiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(weakeneddiamond_sword, 1), " R ", " R ", " S ", 'R', weakeneddiamond, 'S', "stickWood");

		// flawless diamond tools
        SonarCrafting.addShapedOre(modid, new ItemStack(flawlessdiamond_axe, 1), "RR ", "RS ", " S ", 'R', flawlessdiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(flawlessdiamond_pickaxe, 1), "RRR", " S ", " S ", 'R', flawlessdiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(flawlessdiamond_shovel, 1), " R ", " S ", " S ", 'R', flawlessdiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(flawlessdiamond_hoe, 1), "RR ", " S ", " S ", 'R', flawlessdiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(flawlessdiamond_sword, 1), " R ", " R ", " S ", 'R', flawlessdiamond, 'S', "stickWood");

		// fire diamond tools
        SonarCrafting.addShapedOre(modid, new ItemStack(firediamond_axe, 1), "RR ", "RS ", " S ", 'R', firediamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(firediamond_pickaxe, 1), "RRR", " S ", " S ", 'R', firediamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(firediamond_shovel, 1), " R ", " S ", " S ", 'R', firediamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(firediamond_hoe, 1), "RR ", " S ", " S ", 'R', firediamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(firediamond_sword, 1), " R ", " R ", " S ", 'R', firediamond, 'S', "stickWood");

		// electric diamond tools
        SonarCrafting.addShapedOre(modid, new ItemStack(electric_axe, 1), "RR ", "RS ", " S ", 'R', electricDiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(electric_pickaxe, 1), "RRR", " S ", " S ", 'R', electricDiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(electric_shovel, 1), " R ", " S ", " S ", 'R', electricDiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(electric_hoe, 1), "RR ", " S ", " S ", 'R', electricDiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(electric_sword, 1), " R ", " R ", " S ", 'R', electricDiamond, 'S', "stickWood");

		// end forged tools
        SonarCrafting.addShapedOre(modid, new ItemStack(endforged_axe, 1), "RR ", "RS ", " S ", 'R', endDiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(endforged_pickaxe, 1), "RRR", " S ", " S ", 'R', endDiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(endforged_shovel, 1), " R ", " S ", " S ", 'R', endDiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(endforged_hoe, 1), "RR ", " S ", " S ", 'R', endDiamond, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(endforged_sword, 1), " R ", " R ", " S ", 'R', endDiamond, 'S', "stickWood");

		// calculator parts
        SonarCrafting.addShapedOre(modid, new ItemStack(calculator_screen, 1), "CCC", "CRC", "CCC", 'C', "cobblestone", 'R', "dustRedstone");
        // addShaped(new ItemStack(calculator_assembly, 1), new Object[] { "BBB", "BBB", "BBB", 'B', Blocks.stone_button });
        // addShapedOre(new ItemStack(advanced_assembly, 1), new Object[] { "A A", " D ", "A A", 'D', "gemDiamond", 'A', calculator_assembly });
        // addShapedOre(new ItemStack(atomic_module, 1), new Object[] { "A A", " D ", "A A", 'D', "gemDiamond", 'A', itemScientificCalculator });
        SonarCrafting.addShapedOre(modid, new ItemStack(atomic_assembly, 1), "BAB", "AEA", "BAB", 'E', "gemEmerald", 'A', atomic_module, 'B', advanced_assembly);
        SonarCrafting.addShapedOre(modid, new ItemStack(atomic_binder, 8), " A ", "ADA", " A ", 'A', "calculatorReinforcedStone", 'D', enrichedGold);

		// calculators
        SonarCrafting.addShapedOre(modid, new ItemStack(itemInfoCalculator, 1), "CSC", "BAB", "CCC", 'B', Blocks.STONE_BUTTON, 'A', "dustRedstone", 'S', calculator_screen, 'C', "cobblestone");
        SonarCrafting.addShapedOre(modid, new ItemStack(itemCalculator, 1), "CSC", "BAB", "CCC", 'B', Blocks.STONE_BUTTON, 'A', calculator_assembly, 'S', calculator_screen, 'C', "cobblestone");
        SonarCrafting.addShapedOre(modid, new ItemStack(itemCraftingCalculator, 1), "CSC", "BAB", "CCC", 'B', Blocks.STONE_BUTTON, 'A', Blocks.CRAFTING_TABLE, 'S', calculator_screen, 'C', "cobblestone");
        SonarCrafting.addShapedOre(modid, new ItemStack(itemScientificCalculator, 1), "CSC", "BAB", "CAC", 'B', "calculatorReinforcedStone", 'A', calculator_assembly, 'S', calculator_screen, 'C', enrichedgold_ingot);

        SonarCrafting.addShapedOre(modid, new ItemStack(atomicCalculator, 1), "DCD", "WAW", "DWD", 'D', "calculatorReinforcedStone", 'A', atomic_assembly, 'C', calculator_screen, 'W', "gemDiamond");

        SonarCrafting.addShapedOre(modid, new ItemStack(dynamicCalculator, 1), "RSR", "MAM", "RDR", 'D', "gemDiamond", 'S', advanced_assembly, 'A', atomicCalculator, 'M', atomic_module, 'R', "calculatorReinforcedStone");

        SonarCrafting.addShapedOre(modid, new ItemStack(itemFlawlessCalculator, 1), "FSF", "DAD", "FEF", 'F', flawlessdiamond, 'D', "gemDiamond", 'E', endDiamond, 'A', flawless_assembly, 'S', calculator_screen);

		// modules
        SonarCrafting.addShapedOre(modid, new ItemStack(itemHungerModule, 1), "ADA", "BCB", "AEA", 'B', "gemAmethyst", 'A', shard_amethyst, 'C', Items.GOLDEN_APPLE, 'D', calculator_screen, 'E', redstone_ingot);
        SonarCrafting.addShapedOre(modid, new ItemStack(itemHealthModule, 1), "ADA", "BCB", "AEA", 'B', "gemTanzanite", 'A', shard_tanzanite, 'C', atomic_binder, 'D', calculator_screen, 'E', flawlessdiamond);

		// machines
        SonarCrafting.addShapedOre(modid, new ItemStack(powerCube, 1), "AAA", "ADA", "AAA", 'A', "cobblestone", 'D', Blocks.FURNACE);
        SonarCrafting.addShaped(modid, group, new ItemStack(advancedPowerCube, 1), "AAA", "ADA", "AAA", 'A', redstone_ingot, 'D', powerCube);
        SonarCrafting.addShapedOre(modid, new ItemStack(basicGreenhouse, 1), "BCB", "CAC", "BCB", 'A', new ItemStack(material_block, 1, Variants.REINFORCED_IRON.getMeta()), 'B', "calculatorReinforcedStone", 'C', enrichedgold_ingot);
        SonarCrafting.addShaped(modid, group, new ItemStack(advancedGreenhouse, 1), "BCB", "CAC", "BCB", 'A', basicGreenhouse, 'B', weakeneddiamond, 'C', large_tanzanite);
        SonarCrafting.addShaped(modid, group, new ItemStack(flawlessGreenhouse, 1), "BBB", "CAC", "BBB", 'A', atomic_assembly, 'B', flawlessGlass, 'C', advancedGreenhouse);
        SonarCrafting.addShaped(modid, group, new ItemStack(hungerProcessor, 1), "ABA", "BCB", "ABA", 'A', large_amethyst, 'B', redstone_ingot, 'C', advanced_assembly);
        SonarCrafting.addShaped(modid, group, new ItemStack(healthProcessor, 1), "ABA", "BCB", "ABA", 'A', large_tanzanite, 'B', flawlessdiamond, 'C', advanced_assembly);

        SonarCrafting.addShaped(modid, group, new ItemStack(analysingChamber, 1), "ABA", "BCB", "ABA", 'A', new ItemStack(material_block, 1, Variants.REINFORCED_IRON.getMeta()), 'B', weakeneddiamond, 'C', advanced_assembly);
        SonarCrafting.addShaped(modid, group, new ItemStack(fabricationChamber, 1), "C C", "ABA", 'A', new ItemStack(material_block, 1, Variants.REINFORCED_IRON.getMeta()), 'B', storageChamber, 'C', reinforcediron_ingot);
        SonarCrafting.addShapedOre(modid, new ItemStack(atomicMultiplier, 1), "EAE", "DBD", "FCF", 'A', fabricationChamber, 'B', atomic_assembly, 'C', endDiamond, 'D', atomic_module, 'E', calculatorplug, 'F', "sonarStableStone");

		// generators
        SonarCrafting.addShapedOre(modid, new ItemStack(crankHandle, 1), "  A", "AAA", "A  ", 'A', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(handcrankedGenerator, 1), "AAA", "CBC", "AAA", 'A', "stickWood", 'B', powerCube, 'C', "plankWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(calculatorlocator, 1), " B ", "CAC", "DDD", 'A', calculatorplug, 'B', Blocks.BEACON, 'C', atomic_assembly, 'D', "sonarStableStone");
        SonarCrafting.addShapedOre(modid, new ItemStack(calculatorplug, 1), " B ", "CAC", "DDD", 'A', "sonarStableStone", 'B', itemEnergyModule, 'C', advanced_assembly, 'D', redstone_ingot);

		// smelting
        SonarCrafting.addShapedOre(modid, new ItemStack(stoneSeparator, 1), "AAA", "BCB", "AAA", 'B', powerCube, 'A', "calculatorReinforcedStone", 'C', reinforcediron_ingot);
        SonarCrafting.addShapedOre(modid, new ItemStack(extractionChamber, 1), "ABA", "BCB", "ABA", 'A', "calculatorReinforcedStone", 'B', weakeneddiamond, 'C', powerCube);
        SonarCrafting.addShaped(modid, group, new ItemStack(restorationChamber, 1), "ABA", "BCB", "ABA", 'A', redstone_ingot, 'B', weakeneddiamond, 'C', extractionChamber);
        SonarCrafting.addShaped(modid, group, new ItemStack(reassemblyChamber, 1), "ABA", "BCB", "ABA", 'A', enrichedGold, 'B', weakeneddiamond, 'C', extractionChamber);
        SonarCrafting.addShaped(modid, group, new ItemStack(precisionChamber, 1), "ABA", "BCB", "ABA", 'A', new ItemStack(material_block, 1, Variants.REINFORCED_IRON.getMeta()), 'B', weakeneddiamond, 'C', extractionChamber);
        SonarCrafting.addShapedOre(modid, new ItemStack(reinforcedFurnace, 1), "AAA", "ABA", "AAA", 'A', "calculatorReinforcedStone", 'B', powerCube);
        SonarCrafting.addShapedOre(modid, new ItemStack(reinforcedChest, 1), "ABA", "BCB", "ABA", 'A', new ItemStack(material_block, 1, Variants.REINFORCED_IRON.getMeta()), 'B', "calculatorReinforcedStone", 'C', Blocks.CHEST);

		// item_recipes
        SonarCrafting.addShapedOre(modid, new ItemStack(grenadecasing, 1), "R R", "RRR", 'R', "calculatorReinforcedStone");
        SonarCrafting.addShaped(modid, group, new ItemStack(obsidianKey, 1), "  B", "BBB", "A B", 'A', purifiedObsidian, 'B', enrichedgold_ingot);

		// gems
        SonarCrafting.addShapedOre(modid, new ItemStack(large_amethyst, 1), "AAA", "AAA", "AAA", 'A', "gemAmethyst");
        SonarCrafting.addShaped(modid, group, new ItemStack(small_amethyst, 1), "AAA", "AAA", "AAA", 'A', shard_amethyst);
        SonarCrafting.addShapedOre(modid, new ItemStack(large_tanzanite, 1), "AAA", "AAA", "AAA", 'A', "gemTanzanite");
        SonarCrafting.addShaped(modid, group, new ItemStack(small_tanzanite, 1), "AAA", "AAA", "AAA", 'A', shard_tanzanite);
		// decoration
		for (Variants variant : MaterialBlock.Variants.values()) {
            SonarCrafting.addShaped(modid, group, new ItemStack(material_block, 1, variant.getMeta()), "AAA", "AAA", "AAA", 'A', variant.getBaseItem());
            SonarCrafting.addShapeless(modid, group, new ItemStack(variant.getBaseItem(), 9),new Object[] { new ItemStack(material_block, 1, variant.getMeta())});
		}
		/* addShaped(new ItemStack(material_block, 1, Variants.AMETHYST.getMeta()), new Object[] { "AAA", "AAA", "AAA", 'A', large_amethyst }); addShapeless(new ItemStack(large_amethyst, 9), new Object[] { new ItemStack(material_block, 1, Variants.AMETHYST.getMeta()) }); addShaped(new ItemStack(material_block, 1, Variants.TANZANITE.getMeta()), new Object[] { "AAA", "AAA", "AAA", 'A', large_tanzanite }); addShapeless(new ItemStack(large_tanzanite, 9), new Object[] { new ItemStack(material_block, 1, Variants.TANZANITE.getMeta()) }); addShaped(new ItemStack(material_block, 1, Variants.END_DIAMOND.getMeta()), new Object[] { "AAA", "AAA", "AAA", 'A', endDiamond }); addShapeless(new ItemStack(endDiamond, 9), new Object[] { new ItemStack(material_block, 1, Variants.END_DIAMOND.getMeta()) }); addShaped(new ItemStack(material_block, 1, Variants.ENRICHED_GOLD.getMeta()), new Object[] { "AAA", "AAA", "AAA", 'A', enrichedgold_ingot }); addShapeless(new ItemStack(enrichedgold_ingot, 9), new Object[] { new ItemStack(material_block, 1, Variants.ENRICHED_GOLD.getMeta()) }); addShaped(new ItemStack(material_block, 1, Variants.FLAWLESS_DIAMOND.getMeta()), new Object[] { "AAA", "AAA", "AAA", 'A', flawlessdiamond }); addShapeless(new ItemStack(flawlessdiamond, 9), new Object[] { new ItemStack(material_block, 1, Variants.FLAWLESS_DIAMOND.getMeta()) }); addShaped(new ItemStack(material_block, 1, Variants.FIRE_DIAMOND.getMeta()), new Object[] { "AAA", "AAA", "AAA", 'A', firediamond }); addShapeless(new ItemStack(firediamond, 9), new Object[] { new ItemStack(material_block, 1, Variants.FIRE_DIAMOND.getMeta()) }); addShaped(new ItemStack(material_block, 1, Variants.REINFORCED_IRON.getMeta()), new Object[] { "AAA", "AAA", "AAA", 'A', reinforcediron_ingot }); addShapeless(new ItemStack(reinforcediron_ingot, 9), new Object[] { new ItemStack(material_block, 1, Variants.REINFORCED_IRON.getMeta()) }); addShaped(new ItemStack(material_block, 1, Variants.WEAKENED_DIAMOND.getMeta()), new Object[] {
		 * "AAA", "AAA", "AAA", 'A', weakeneddiamond }); addShapeless(new ItemStack(weakeneddiamond, 9), new Object[] { new ItemStack(material_block, 1, Variants.WEAKENED_DIAMOND.getMeta()) }); addShaped(new ItemStack(material_block, 1, Variants.ELECTRIC_DIAMOND.getMeta()), new Object[] { "AAA", "AAA", "AAA", 'A', electricDiamond }); addShapeless(new ItemStack(electricDiamond, 9), new Object[] { new ItemStack(material_block, 1, Variants.ELECTRIC_DIAMOND.getMeta()) }); */
		// tree blocks
        SonarCrafting.addShapeless(modid, group, new ItemStack(amethystPlanks, 4), SonarCrafting.fromBlock(amethystLog));
        SonarCrafting.addShapeless(modid, group, new ItemStack(tanzanitePlanks, 4), SonarCrafting.fromBlock(tanzaniteLog));
        SonarCrafting.addShapeless(modid, group, new ItemStack(pearPlanks, 4), SonarCrafting.fromBlock(pearLog));
        SonarCrafting.addShapeless(modid, group, new ItemStack(diamondPlanks, 4), SonarCrafting.fromBlock(diamondLog));
        SonarCrafting.addShaped(modid, group, new ItemStack(amethystStairs, 4), "A  ", "AA ", "AAA", 'A', amethystPlanks);
        SonarCrafting.addShaped(modid, group, new ItemStack(tanzaniteStairs, 4), "A  ", "AA ", "AAA", 'A', tanzanitePlanks);
        SonarCrafting.addShaped(modid, group, new ItemStack(pearStairs, 4), "A  ", "AA ", "AAA", 'A', pearPlanks);
        SonarCrafting.addShaped(modid, group, new ItemStack(diamondStairs, 4), "A  ", "AA ", "AAA", 'A', diamondPlanks);

        SonarCrafting.addShapedOre(modid, new ItemStack(amethystFence, 6), "ASA", "ASA", 'A', amethystPlanks, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(tanzaniteFence, 6), "ASA", "ASA", 'A', tanzanitePlanks, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(pearFence, 6), "ASA", "ASA", 'A', pearPlanks, 'S', "stickWood");
        SonarCrafting.addShapedOre(modid, new ItemStack(diamondFence, 6), "ASA", "ASA", 'A', diamondPlanks, 'S', "stickWood");
        //TODO maybe add gates

		// addShapedOre(new ItemStack(magneticFlux, 1), new Object[] { " D ", "RFR", "SSS", 'S', "sonarStableStone", 'R', redstone_ingot, 'F', fluxPoint, 'D', firediamond });
        SonarCrafting.addShapedOre(modid, new ItemStack(dockingStation, 1), " R ", "APA", "RRR", 'R', "calculatorReinforcedStone", 'P', powerCube, 'A', calculator_assembly);
		// addShapedOre(new ItemStack(teleporter, 2), new Object[] { "SRS", "RFR", "SDS", 'R', reinforcedStoneBlock, 'S', "sonarStableStone", 'D', endDiamond, 'F', fluxPlug });
        SonarCrafting.addShaped(modid, group, new ItemStack(weatherController, 1), "DSD", "RRR", 'S', Items.NETHER_STAR, 'D', electricDiamond, 'R', rainSensor);
        SonarCrafting.addShapedOre(modid, new ItemStack(stoneAssimilator, 1), " S ", "IPI", " I ", 'P', stoneSeparator, 'I', "calculatorReinforcedStone", 'S', sickle);
        SonarCrafting.addShaped(modid, group, new ItemStack(algorithmAssimilator, 1), " S ", "IPI", " I ", 'P', algorithmSeparator, 'I', new ItemStack(material_block, 1, Variants.REINFORCED_IRON.getMeta()), 'S', sickle);

        SonarCrafting.addShapedOre(modid, new ItemStack(calculator_assembly, 8), "CBC", "BCB", "CBC", 'C', "cobblestone", 'B', Blocks.STONE_BUTTON);
        SonarCrafting.addShapedOre(modid, new ItemStack(advanced_assembly, 4), "GCG", "CIC", "GCG", 'C', calculator_assembly, 'G', enrichedgold_ingot, 'I', reinforcediron_ingot);
        SonarCrafting.addShapedOre(modid, new ItemStack(atomic_module, 4), "GCG", "CIC", "GCG", 'C', calculator_assembly, 'G', "gemTanzanite", 'I', "gemDiamond");
        SonarCrafting.addShapedOre(modid, new ItemStack(algorithmSeparator, 1), "BCB", "ADA", "BCB", 'A', stoneSeparator, 'B', new ItemStack(material_block, 1, Variants.REINFORCED_IRON.getMeta()), 'C', powerCube, 'D', "gemDiamond");
        SonarCrafting.addShapedOre(modid, new ItemStack(flawless_assembly, 1), "GCG", "CAC", "GCG", 'C', calculator_assembly, 'G', advanced_assembly, 'A', atomic_assembly);
        SonarCrafting.addShapedOre(modid, new ItemStack(moduleWorkstation, 1), "GCG", "CAC", "GCG", 'C', atomic_binder, 'G', "calculatorReinforcedStone", 'A', atomic_module);
	}
}