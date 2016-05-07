package sonar.calculator.mod;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CalculatorOreDict extends Calculator {

	public static void registerOres() {

		for (int i = 0; i < 14; i++) {
			OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, i));
		}

		for (int i = 0; i < 16; i++) {
			OreDictionary.registerOre("calculatorStableStone", new ItemStack(stablestoneBlock, 1, i));
			OreDictionary.registerOre("calculatorStableStone", new ItemStack(stablestonerimmedBlock, 1, i));
			OreDictionary.registerOre("calculatorStableStone", new ItemStack(stablestonerimmedblackBlock, 1, i));
		}

		// dusts & ingots
		OreDictionary.registerOre("ingotEnrichedGold", enrichedgold_ingot);
		OreDictionary.registerOre("dustEnrichedGold", enrichedgold);
		OreDictionary.registerOre("gemDiamondFake", weakeneddiamond);
		OreDictionary.registerOre("ingotFoolsGold", enrichedgold_ingot);
		//OreDictionary.registerOre("ingotRedstone", redstone_ingot);
		OreDictionary.registerOre("dustFoolsGold", enrichedgold);
		OreDictionary.registerOre("gemAmethyst", small_amethyst);
		OreDictionary.registerOre("gemTanzanite", small_tanzanite);
		OreDictionary.registerOre("slimeball", rotten_pear);
		OreDictionary.registerOre("dustStone", small_stone);
		OreDictionary.registerOre("dustDirt", soil);

		// logs
		OreDictionary.registerOre("logWood", amethystLog);
		OreDictionary.registerOre("logWood", tanzaniteLog);
		OreDictionary.registerOre("logWood", pearLog);
		OreDictionary.registerOre("logWood", diamondLog);

		// planks
		OreDictionary.registerOre("plankWood", amethystPlanks);
		OreDictionary.registerOre("plankWood", tanzanitePlanks);
		OreDictionary.registerOre("plankWood", pearPlanks);
		OreDictionary.registerOre("plankWood", diamondPlanks);

		// stairs
		OreDictionary.registerOre("stairWood", amethystStairs);
		OreDictionary.registerOre("stairWood", tanzaniteStairs);
		OreDictionary.registerOre("stairWood", pearStairs);
		OreDictionary.registerOre("stairWood", diamondStairs);

		// leaves
		OreDictionary.registerOre("calculatorLeaves", amethystLeaf);
		OreDictionary.registerOre("calculatorLeaves", tanzaniteLeaf);
		OreDictionary.registerOre("calculatorLeaves", pearLeaf);
		OreDictionary.registerOre("calculatorLeaves", diamondLeaf);

		// saplings
		OreDictionary.registerOre("treeSapling", tanzaniteSapling);
		OreDictionary.registerOre("treeSapling", diamondSapling);
		OreDictionary.registerOre("treeSapling", AmethystSapling);
		OreDictionary.registerOre("treeSapling", PearSapling);

		// blocks
		OreDictionary.registerOre("calculatorReinforcedBlock", reinforcedstoneBlock);
		OreDictionary.registerOre("calculatorReinforcedBlock", reinforceddirtBlock);
		OreDictionary.registerOre("blockGlass", stableglassBlock);
		OreDictionary.registerOre("blockGlass", clearstableglassBlock);
		OreDictionary.registerOre("reinforcedStone", reinforcedstoneBlock);
		OreDictionary.registerOre("strongStone", reinforcedstoneBlock);
		OreDictionary.registerOre("hardStone", reinforcedstoneBlock);
		OreDictionary.registerOre("reinforcedDirt", reinforceddirtBlock);
		OreDictionary.registerOre("strongDirt", reinforceddirtBlock);
		OreDictionary.registerOre("hardDirt", reinforceddirtBlock);
	}

	/**
	 * 
	 * @param type (Normal=0, Damaged=1, Dirty=2)
	 * @return
	 */
	public static ItemStack[] circuitList(int type) {
		ItemStack[] circuits = new ItemStack[14];
		for (int i = 0; i < circuits.length; i++) {
			switch (type) {
			case 0:
				circuits[i] = new ItemStack(circuitBoard, 1, i);
				break;

			case 1:
				circuits[i] = new ItemStack(circuitDamaged, 1, i);
				break;

			case 2:
				circuits[i] = new ItemStack(circuitDirty, 1, i);
				break;
			}
		}
		return circuits;
	}
}
