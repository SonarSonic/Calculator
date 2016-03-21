package sonar.calculator.mod;

import gnu.trove.map.hash.THashMap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CalculatorConfig extends Calculator {

	private static final List<IntegerConfig> integerConfigs = new ArrayList();
	private static final Map<String, Integer> integers = new THashMap<String, Integer>();

	/*public static int calculatorEnergy; public static int craftingEnergy; public static int scientificEnergy; public static int terrainEnergy; public static int advancedEnergy; public static int moduleEnergy; public static int cubeEnergy; public static int starchRF; public static int redstoneRF; public static int glowstoneRF; public static int conductorRF; public static int weatherstationRF;
	 * public static int growthRF; public static int buildRF; public static int farmlandRF; public static int waterRF; public static int plantRF; */
	public static boolean timeEffect;
	public static boolean beamEffect;
	public static boolean enableWaila;
	public static boolean enableGrenades;
	public static boolean enableToolModels;

	public static Property atomicblackList;
	public static Property blocksblackList;
	public static Property itemsblackList;

	public static void addInteger(String name, String usageType, int min, int defaultValue, int max) {
		integerConfigs.add(new IntegerConfig(name, usageType, min, defaultValue, max));
	}

	public static int getInteger(String name) {
		return integers.get(name);
	}

	public static class IntegerConfig {
		public String name, usageType;
		public int min, defaultValue, max;

		public IntegerConfig(String name, String usageType, int defaultValue, int min, int max) {
			this.name = name;
			this.usageType = usageType;
			this.defaultValue = defaultValue;
			this.min = min;
			this.max = max;
		}
	}

	public static void initConfiguration(FMLPreInitializationEvent event) {
		registerInteger();
		loadMainConfig();
		loadAtomicBlacklist();
		loadBlocks();
		loadItems();
	}

	public static void registerInteger() {
		addInteger("Calculator", "Energy Storage", 1000, 10, 50000);
		addInteger("Crafting Calculator", "Energy Storage", 5000, 10, 50000);
		addInteger("Scientific Calculator", "Energy Storage", 2000, 10, 50000);
		addInteger("Terrain Module", "Energy Storage", 400, 10, 50000);
		addInteger("Advanced Terrain Module", "Energy Storage", 2000, 10, 50000);
		addInteger("Energy Module", "Energy Storage", 100000, 1000, 500000);
		addInteger("Standard Machine", "Energy Storage", 50000, 1000, 1000000);
		addInteger("Starch Extractor", "Generation", 40, 1, 128);
		addInteger("Redstone Extractor", "Generation", 80, 4, 192);
		addInteger("Glowstone Extractor", "Generation", 160, 16, 256);
		addInteger("Conductor Mast", "Generation", 5000, 200, 5000000);
		addInteger("Weather Station", "Generation", 1000, 200, 5000000);
		addInteger("Growth Energy", "Greenhouses", 150, 1, 50000);
		addInteger("Build Energy", "Greenhouses", 100, 1, 50000);
		addInteger("Plant Energy", "Greenhouses", 50, 1, 50000);
		addInteger("Adding Farmland", "Greenhouses", 50, 1, 50000);
		addInteger("Adding Water", "Greenhouses", 1000, 1, 50000);
		addInteger("Scarecrow Tick Rate", "Scarecrow", 500, 1, 10000);
		addInteger("Scarecrow Range", "Scarecrow", 3, 1, 25);
		addInteger("Weather Controller", "Energy Usage", 250000, 1, 1000000);
	}

	public static void loadMainConfig() {
		Configuration config = new Configuration(new File("config/calculator/Calculator-Config.cfg"));
		config.load();
		for (IntegerConfig usageConfig : integerConfigs) {
			int usage = config.getInt(usageConfig.name, usageConfig.usageType, usageConfig.defaultValue, usageConfig.min, usageConfig.max, usageConfig.name);

			integers.put(usageConfig.name, usage);
		}

		timeEffect = config.getBoolean("Locator Can Change Time", "settings", true, "Calculator Locator");
		beamEffect = config.getBoolean("Locator has a beam", "settings", true, "Calculator Locator");
		enableWaila = config.getBoolean("enable Waila integration", "api", true, "Waila");
		enableGrenades = config.getBoolean("allow grenades?", "settings", true, "Grenades");
		enableToolModels = config.getBoolean("Enable Tool Models", "settings", true, "Tool Models");

		config.save();

	}

	public static void loadAtomicBlacklist() {
		Configuration blacklist = new Configuration(new File("config/calculator/AtomicMultiplier-BlackList.cfg"));
		blacklist.load();
		String[] blackDefaults = new String[9];
		blackDefaults[0] = "minecraft:nether_star";
		blackDefaults[1] = "Calculator:AtomicMultiplier";
		blackDefaults[2] = "Calculator:EndBlock";
		blackDefaults[3] = "Calculator:ElectricBlock";
		blackDefaults[4] = "Calculator:FlawlessFireBlock";
		blackDefaults[5] = "Calculator:FlawlessBlock";
		blackDefaults[6] = "Calculator:ElectricDiamond";
		blackDefaults[7] = "Calculator:FlawlessFireDiamond";
		blackDefaults[8] = "Calculator:FlawlessDiamond";

		atomicblackList = blacklist.get("Atomic Multiplier Blacklist", "disabled", blackDefaults);
		blacklist.save();

	}

	public static void loadBlocks() {
		Configuration blocks = new Configuration(new File("config/calculator/Blocks-BlackList.cfg"));
		blocks.load();
		String[] blockExamples = new String[2];
		blockExamples[0] = "ExampleBlock";
		blockExamples[1] = "ExampleBlock2";

		blocksblackList = blocks.get("Block Config", "Disabled", blockExamples);
		blocks.save();
	}

	public static void loadItems() {
		Configuration items = new Configuration(new File("config/calculator/Items-BlackList.cfg"));
		items.load();
		String[] itemExamples = new String[2];
		itemExamples[0] = "ExampleItem";
		itemExamples[1] = "ExampleItem2";

		itemsblackList = items.get("Item Config", "Disabled", itemExamples);
		items.save();
	}

	private static boolean isBlockEnabled(String block) {
		if (block != null) {
			String[] blacklisted = blocksblackList.getStringList();
			for (int i = 0; i < blacklisted.length; i++) {
				if (blacklisted[i] != null && blacklisted[i].equals(block)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private static boolean isItemEnabled(String item) {
		if (item != null) {
			String[] blacklisted = itemsblackList.getStringList();
			for (int i = 0; i < blacklisted.length; i++) {
				if (blacklisted[i] != null && blacklisted[i].equals(item)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param stack the ItemStack you wish to check
	 * @return if the Item/Block is enabled
	 */
	public static boolean isEnabled(ItemStack stack) {
		if (stack == null) {
			return true;
		}
		Block block = Block.getBlockFromItem(stack.getItem());
		Item item = stack.getItem();
		if (block != null && item instanceof ItemBlock && GameRegistry.findUniqueIdentifierFor(block) != null) {
			if (CalculatorConfig.isBlockEnabled(GameRegistry.findUniqueIdentifierFor(block).name)) {
				return true;
			} else {
				return false;
			}
		} else if (item != null && GameRegistry.findUniqueIdentifierFor(item) != null) {
			if (CalculatorConfig.isItemEnabled(GameRegistry.findUniqueIdentifierFor(item).name)) {
				return true;

			} else {
				return false;
			}
		} else {
			return true;
		}
	}
}
