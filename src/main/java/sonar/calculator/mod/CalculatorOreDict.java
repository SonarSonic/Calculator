package sonar.calculator.mod;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CalculatorOreDict extends Calculator {

	public static void registerOres() {

		for (int i = 0; i < 14; i++) {
			registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, i));
		}

		for (int i = 0; i < 16; i++) {
			registerOre("calculatorStableStone", new ItemStack(stableStone, 1, i));
			registerOre("calculatorStableStone", new ItemStack(stablestonerimmedBlock, 1, i));
			registerOre("calculatorStableStone", new ItemStack(stablestonerimmedblackBlock, 1, i));
		}

		// dusts & ingots
		registerOre("ingotEnrichedGold", enrichedgold_ingot);
		registerOre("dustEnrichedGold", enrichedGold);
		registerOre("gemDiamondFake", weakeneddiamond);
		registerOre("ingotFoolsGold", enrichedgold_ingot);
		registerOre("ingotRedstone", redstone_ingot);
		registerOre("dustFoolsGold", enrichedGold);
		registerOre("gemAmethyst", small_amethyst);
		registerOre("gemTanzanite", small_tanzanite);
		registerOre("slimeball", rotten_pear);
		registerOre("dustStone", small_stone);
		registerOre("dustDirt", soil);

		// logs
		registerOre("logWood", amethystLog);
		registerOre("logWood", tanzaniteLog);
		registerOre("logWood", pearLog);
		registerOre("logWood", diamondLog);

		// planks
		registerOre("plankWood", amethystPlanks);
		registerOre("plankWood", tanzanitePlanks);
		registerOre("plankWood", pearPlanks);
		registerOre("plankWood", diamondPlanks);

		// stairs
		registerOre("stairWood", amethystStairs);
		registerOre("stairWood", tanzaniteStairs);
		registerOre("stairWood", pearStairs);
		registerOre("stairWood", diamondStairs);

		// leaves
		registerOre("calculatorLeaves", amethystLeaf);
		registerOre("calculatorLeaves", tanzaniteLeaf);
		registerOre("calculatorLeaves", pearLeaf);
		registerOre("calculatorLeaves", diamondLeaf);

		// saplings
		registerOre("treeSapling", tanzaniteSapling);
		registerOre("treeSapling", diamondSapling);
		registerOre("treeSapling", AmethystSapling);
		registerOre("treeSapling", PearSapling);

		// blocks
		registerOre("calculatorReinforcedBlock", reinforcedStoneBlock);
		registerOre("calculatorReinforcedBlock", reinforcedDirtBlock);
		registerOre("blockGlass", stableglassBlock);
		registerOre("blockGlass", clearstableglassBlock);
		registerOre("reinforcedStone", reinforcedStoneBlock);
		registerOre("strongStone", reinforcedStoneBlock);
		registerOre("hardStone", reinforcedStoneBlock);
		registerOre("reinforcedDirt", reinforcedDirtBlock);
		registerOre("strongDirt", reinforcedDirtBlock);
		registerOre("hardDirt", reinforcedDirtBlock);
	}

	public static void registerOre(String string, Item item) {
		if (string!=null && item != null)
			OreDictionary.registerOre(string, item);
	}

	public static void registerOre(String string, Block block) {
		if (string!=null && block != null)
			OreDictionary.registerOre(string, block);
	}
	
	public static void registerOre(String string, ItemStack stack) {
		if (string!=null && stack != null && stack.getItem()!=null)
			OreDictionary.registerOre(string, stack);
	}
	
	/** @param type (Normal=0, Damaged=1, Dirty=2)
	 * @return */
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
