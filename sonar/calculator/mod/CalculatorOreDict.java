package sonar.calculator.mod;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CalculatorOreDict extends Calculator {

	public static void registerOres(){		
		//circuits
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 0));
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 1));
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 2));
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 3));
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 4));
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 5));
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 6));
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 7));
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 8));
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 9));
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 10));
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 11));
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 12));
		OreDictionary.registerOre("calculatorCircuit", new ItemStack(circuitBoard, 1, 13));

		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 0));
		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 1));
		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 2));
		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 3));
		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 4));
		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 5));
		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 6));
		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 7));
		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 8));
		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 9));
		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 10));
		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 11));
		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 12));
		OreDictionary.registerOre("calculatorDirty", new ItemStack(circuitBoard, 1, 13));
		
		//dusts & ingots
		OreDictionary.registerOre("ingotEnrichedGold", enrichedgold_ingot);
		OreDictionary.registerOre("dustEnrichedGold", enrichedgold);
		OreDictionary.registerOre("gemDiamondFake", weakeneddiamond);
		OreDictionary.registerOre("ingotFoolsGold", enrichedgold_ingot);
		OreDictionary.registerOre("ingotRedstone", redstone_ingot);
		OreDictionary.registerOre("dustFoolsGold", enrichedgold);
		OreDictionary.registerOre("gemAmethyst", small_amethyst);
		OreDictionary.registerOre("gemTanzanite", small_tanzanite);
		OreDictionary.registerOre("slimeball", rotten_pear);
		OreDictionary.registerOre("dustStone", small_stone);
		OreDictionary.registerOre("dustDirt", soil);
		
		//logs
		OreDictionary.registerOre("logWood", amethystLog);
		OreDictionary.registerOre("logWood", tanzaniteLog);
		OreDictionary.registerOre("logWood", pearLog);
		OreDictionary.registerOre("logWood", diamondLog);
		
		//planks
		OreDictionary.registerOre("plankWood", amethystPlanks);
		OreDictionary.registerOre("plankWood", tanzanitePlanks);
		OreDictionary.registerOre("plankWood", pearPlanks);
		OreDictionary.registerOre("plankWood", diamondPlanks);
		
		//stairs
		OreDictionary.registerOre("stairWood", amethystStairs);
		OreDictionary.registerOre("stairWood", tanzaniteStairs);
		OreDictionary.registerOre("stairWood", pearStairs);
		OreDictionary.registerOre("stairWood", diamondStairs);
		
		//leaves
		OreDictionary.registerOre("treeLeaves", leaves);	
		OreDictionary.registerOre("calculatorLeaves", amethystLeaf);	
		OreDictionary.registerOre("calculatorLeaves", tanzaniteLeaf);
		OreDictionary.registerOre("calculatorLeaves", pearLeaf);
		OreDictionary.registerOre("calculatorLeaves", diamondLeaf);	
		OreDictionary.registerOre("treeLeaves", diamondleaves);	
		
		//saplings
		OreDictionary.registerOre("treeSapling", tanzaniteSapling);
		OreDictionary.registerOre("treeSapling", diamondSapling);
		OreDictionary.registerOre("treeSapling", AmethystSapling);
		OreDictionary.registerOre("treeSapling", PearSapling);	
		
		//blocks
		OreDictionary.registerOre("calculatorReinforcedBlock", reinforcedstoneBlock);	
		OreDictionary.registerOre("calculatorReinforcedBlock", reinforceddirtBlock);	
		OreDictionary.registerOre("blockGlass", flawlessGlass);	
		OreDictionary.registerOre("reinforcedStone",reinforcedstoneBlock);
		OreDictionary.registerOre("strongStone",reinforcedstoneBlock);
		OreDictionary.registerOre("hardStone",reinforcedstoneBlock);
		OreDictionary.registerOre("reinforcedDirt",reinforceddirtBlock);
		OreDictionary.registerOre("strongDirt",reinforceddirtBlock);
		OreDictionary.registerOre("hardDirt",reinforceddirtBlock);
	}
	/**
	 * 
	 * @param type (Normal=0, Damaged=1, Dirty=2)
	 * @return
	 */
	public static ItemStack[] circuitList(int type){
		ItemStack[] circuits = new ItemStack[14];
		for(int i=0;i<circuits.length;i++){
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
