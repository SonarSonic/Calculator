package sonar.calculator.mod;

import gnu.trove.map.hash.THashMap;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculatorConfig extends Calculator {

	public static boolean beamEffect = false;
	public static boolean enableWaila;
	public static boolean enableGrenades;
	public static boolean enableToolModels;

	public static Property atomicblackList;
	public static Property blocksblackList;
	public static Property itemsblackList;

	//// CONSTANTS \\\\
	public static final int BASIC_DEFAULT_TRANSFER_RATE = 400;
	public static final int SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE = 3200;
	public static final int ATOMIC_TIER_DEFAULT_TRANSFER_RATE = 12800;
	public static final int FLAWLESS_TIER_DEFAULT_TRANSFER_RATE = 64000;

	//// CALCULATOR ENERGY STORAGE \\\\
	public static int CALCULATOR_STORAGE, CRAFTING_CALCULATOR_STORAGE, SCIENTIFIC_CALCULATOR_STORAGE;

	//// MODULE ENERGY STORAGE \\\\
	public static int TERRAIN_MODULE_STORAGE, ADVANCED_TERRAIN_MODULE_STORAGE, ENERGY_MODULE_STORAGE;

	//// POWER CUBE \\\\
	public static int POWER_CUBE_STORAGE, POWER_CUBE_TRANSFER_RATE, POWER_CUBE_CHARGING_RATE;

	//// ADVANCED POWER CUBE \\\\
	public static int ADVANCED_POWER_CUBE_STORAGE, ADVANCED_POWER_CUBE_TRANSFER_RATE, ADVANCED_POWER_CUBE_CHARGING_RATE;

	//// CREATIVE POWER CUBE \\\\
	public static int CREATIVE_POWER_CUBE_TRANSFER_RATE;

	//// HAND CRANKED GENERATOR \\\\
	public static int HAND_CRANK_STORAGE, HAND_CRANK_TRANSFER_RATE, HAND_CRANK_PER_TICK;

	//// STARCH EXTRACTOR \\\\
	public static int STARCH_EXTRACTOR_STORAGE, STARCH_EXTRACTOR_TRANSFER_RATE, STARCH_EXTRACTOR_GENERATOR_CAPACITY, STARCH_EXTRACTOR_GENERATOR_REQUIRED, STARCH_EXTRACTOR_PER_TICK;

	//// REDSTONE EXTRACTOR \\\\
	public static int REDSTONE_EXTRACTOR_STORAGE, REDSTONE_EXTRACTOR_TRANSFER_RATE, REDSTONE_EXTRACTOR_GENERATOR_CAPACITY, REDSTONE_EXTRACTOR_GENERATOR_REQUIRED, REDSTONE_EXTRACTOR_PER_TICK;

	//// GLOWSTONE EXTRACTOR \\\\
	public static int GLOWSTONE_EXTRACTOR_STORAGE, GLOWSTONE_EXTRACTOR_TRANSFER_RATE, GLOWSTONE_EXTRACTOR_GENERATOR_CAPACITY, GLOWSTONE_EXTRACTOR_GENERATOR_REQUIRED, GLOWSTONE_EXTRACTOR_PER_TICK;

	//// CONDUCTOR_MAST \\\\
	public static int CONDUCTOR_MAST_STORAGE, CONDUCTOR_MAST_TRANSFER_RATE, CONDUCTOR_MAST_CHARGING_RATE, CONDUCTOR_MAST_PER_TICK, CONDUCTOR_MAST_SPEED, WEATHER_STATION_PER_TICK;

	//// CALCULATOR LOCATOR \\\\
	public static int CALCULATOR_LOCATOR_STORAGE, CALCULATOR_LOCATOR_TRANSFER_RATE, CALCULATOR_LOCATOR_CHARGING_RATE;
	public static float CALCULATOR_LOCATOR_MULTIPLIER;
	public static boolean CALCULATOR_LOCATOR_CAN_CHANGE_TIME;

	//// FLAWLESS GREENHOUSE \\\\
	public static int FLAWLESS_GREENHOUSE_STORAGE, FLAWLESS_GREENHOUSE_TRANSFER_RATE;

	//// ADVANCED GREENHOUSE \\\\
	public static int ADVANCED_GREENHOUSE_STORAGE, ADVANCED_GREENHOUSE_TRANSFER_RATE;

	//// BASIC GREENHOUSE \\\\
	public static int BASIC_GREENHOUSE_STORAGE, BASIC_GREENHOUSE_TRANSFER_RATE;

	//// CO2 GENERATOR \\\\
	public static int C02_GENERATOR_STORAGE, C02_GENERATOR_TRANSFER_RATE;

	//// GREENHOUSES \\\\
	public static int GROWTH_ENERGY_USAGE, BUILD_ENERGY_USAGE, PLANTING_ENERGY_USAGE, FARMLAND_GENERATION_USAGE, WATER_GENERATION_USAGE;

	//// SCARECROW \\\\
	public static int SCARECROW_TICK_RATE, SCARECROW_RANGE;

	//// WEATHER CONTROLLER \\\\
	public static int WEATHER_CONTROLLER_STORAGE, WEATHER_CONTROLLER_TRANSFER_RATE, WEATHER_CONTROLLER_USAGE;

	//// ATOMIC MULTIPLIER \\\\
	public static int ATOMIC_MULTIPLIER_STORAGE, ATOMIC_MULTIPLIER_TRANSFER_RATE, ATOMIC_MULTIPLIER_USAGE, ATOMIC_MULTIPLIER_SPEED;
	//// ANALYSING CHAMBER \\\\
	public static int ANALYSING_CHAMBER_STORAGE, ANALYSING_CHAMBER_TRANSFER_RATE;
	//// DOCKING STATION \\\\
	public static int DOCKING_STATION_STORAGE, DOCKING_STATION_TRANSFER_RATE, DOCKING_STATION_USAGE, DOCKING_STATION_SPEED;
	//// REINFORCED FURNACE \\\\
	public static int REINFORCED_FURNACE_STORAGE, REINFORCED_FURNACE_TRANSFER_RATE, REINFORCED_FURNACE_USAGE, REINFORCED_FURNACE_SPEED;
	//// STONE SEPERATOR \\\\
	public static int STONE_SEPERATOR_STORAGE, STONE_SEPERATOR_TRANSFER_RATE, STONE_SEPERATOR_USAGE, STONE_SEPERATOR_SPEED;
	//// ALGORITHM SEPERATOR \\\\
	public static int ALGORITHM_SEPERATOR_STORAGE, ALGORITHM_SEPERATOR_TRANSFER_RATE, ALGORITHM_SEPERATOR_USAGE,ALGORITHM_SEPERATOR_SPEED;
	//// EXTRACTION CHAMBER \\\\
	public static int EXTRACTION_CHAMBER_STORAGE, EXTRACTION_CHAMBER_TRANSFER_RATE, EXTRACTION_CHAMBER_USAGE, EXTRACTION_CHAMBER_SPEED;
	//// REASSEMBLY CHAMBER \\\\
	public static int REASSEMBLY_CHAMBER_STORAGE, REASSEMBLY_CHAMBER_TRANSFER_RATE, REASSEMBLY_CHAMBER_USAGE, REASSEMBLY_CHAMBER_SPEED;
	//// RESTORATION CHAMBER \\\\
	public static int RESTORATION_CHAMBER_STORAGE, RESTORATION_CHAMBER_TRANSFER_RATE, RESTORATION_CHAMBER_USAGE, RESTORATION_CHAMBER_SPEED;
	//// PRECISION CHAMBER \\\\
	public static int PRECISION_CHAMBER_STORAGE, PRECISION_CHAMBER_TRANSFER_RATE, PRECISION_CHAMBER_USAGE,PRECISION_CHAMBER_SPEED;
	//// PROCESSING CHAMBER \\\\
	public static int PROCESSING_CHAMBER_STORAGE, PROCESSING_CHAMBER_TRANSFER_RATE, PROCESSING_CHAMBER_USAGE, PROCESSING_CHAMBER_SPEED;

	public static void initConfiguration(FMLPreInitializationEvent event) {
		loadMainConfig();
		loadAtomicBlacklist();
		loadBlocks();
		loadItems();
	}

	public static void loadMainConfig() {
		Configuration config = new Configuration(new File("config/calculator/Calculator-Config.cfg"));
		config.load();

		//// CALCULATOR ENERGY STORAGE \\\\
		CALCULATOR_STORAGE = INT(config,"Calculator", "Energy Storage", 1000, 1, Integer.MAX_VALUE);
		CRAFTING_CALCULATOR_STORAGE = INT(config,"Crafting Calculator", "Energy Storage", 5000, 1, Integer.MAX_VALUE);
		SCIENTIFIC_CALCULATOR_STORAGE = INT(config,"Scientific Calculator", "Energy Storage", 2000, 1, Integer.MAX_VALUE);

		//// MODULE ENERGY STORAGE \\\\
		TERRAIN_MODULE_STORAGE = INT(config,"Terrain Module", "Energy Storage", 400, 10, Integer.MAX_VALUE);
		ADVANCED_TERRAIN_MODULE_STORAGE = INT(config,"Advanced Terrain Module", "Energy Storage", 2000, 1, Integer.MAX_VALUE);
		ENERGY_MODULE_STORAGE = INT(config,"Energy Module", "Energy Storage", 100000, 1000, Integer.MAX_VALUE);

		//// POWER CUBE \\\\
		POWER_CUBE_STORAGE = INT(config,"Power Cube", "Energy Storage", 50000, 1, Integer.MAX_VALUE);
		POWER_CUBE_TRANSFER_RATE = INT(config,"Power Cube", "Transfer Rate", BASIC_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		POWER_CUBE_CHARGING_RATE = INT(config,"Power Cube", "Charging Rate", 4, 1, Integer.MAX_VALUE);

		//// ADVANCED POWER CUBE \\\\
		ADVANCED_POWER_CUBE_STORAGE = INT(config,"Advanced Power Cube", "Energy Storage", 100000, 1, Integer.MAX_VALUE);
		ADVANCED_POWER_CUBE_TRANSFER_RATE = INT(config,"Advanced Power Cube", "Transfer Rate", 64000, 1, Integer.MAX_VALUE);
		ADVANCED_POWER_CUBE_CHARGING_RATE = INT(config,"Advanced Power Cube", "Charging Rate", 100000, 1, Integer.MAX_VALUE);

		//// CREATIVE POWER CUBE \\\\
		CREATIVE_POWER_CUBE_TRANSFER_RATE = INT(config,"Creative Power Cube", "Transfer Rate", Integer.MAX_VALUE, 1, Integer.MAX_VALUE);

		//// HAND CRANKED GENERATOR \\\\
		HAND_CRANK_STORAGE = INT(config,"Hand Cranked Generator", "Energy Storage", 1000, 1, Integer.MAX_VALUE);
		HAND_CRANK_TRANSFER_RATE = INT(config,"Hand Cranked Generator", "Transfer Rate", BASIC_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		HAND_CRANK_PER_TICK = INT(config,"Hand Cranked Generator", "Generation", 8, 1, Integer.MAX_VALUE);

		//// STARCH EXTRACTOR \\\\
		STARCH_EXTRACTOR_STORAGE = INT(config,"Starch Extractor", "Energy Storage", 1000000, 1, Integer.MAX_VALUE);
		STARCH_EXTRACTOR_TRANSFER_RATE = INT(config,"Starch Extractor", "Transfer Rate", SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		STARCH_EXTRACTOR_GENERATOR_CAPACITY = INT(config,"Starch Extractor", "Generator Capacity", 5000, 1, Integer.MAX_VALUE);
		STARCH_EXTRACTOR_GENERATOR_REQUIRED = INT(config,"Starch Extractor", "Generator Requirement", 400, 1, Integer.MAX_VALUE);
		STARCH_EXTRACTOR_PER_TICK = INT(config,"Starch Extractor", "Generation", 40, 1, Integer.MAX_VALUE);

		//// REDSTONE EXTRACTOR \\\\
		REDSTONE_EXTRACTOR_STORAGE = INT(config,"Redstone Extractor", "Energy Storage", 1000000, 1, Integer.MAX_VALUE);
		REDSTONE_EXTRACTOR_TRANSFER_RATE = INT(config,"Redstone Extractor", "Transfer Rate", SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		REDSTONE_EXTRACTOR_GENERATOR_CAPACITY = INT(config,"Redstone Extractor", "Generator Capacity", 5000, 1, Integer.MAX_VALUE);
		REDSTONE_EXTRACTOR_GENERATOR_REQUIRED = INT(config,"Redstone Extractor", "Generator Requirement", 400, 1, Integer.MAX_VALUE);
		REDSTONE_EXTRACTOR_PER_TICK = INT(config,"Redstone Extractor", "Generation", 80, 1, Integer.MAX_VALUE);

		//// GLOWSTONE EXTRACTOR \\\\
		GLOWSTONE_EXTRACTOR_STORAGE = INT(config,"Glowstone Extractor", "Energy Storage", 1000000, 1, Integer.MAX_VALUE);
		GLOWSTONE_EXTRACTOR_TRANSFER_RATE = INT(config,"Glowstone Extractor", "Transfer Rate", SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		GLOWSTONE_EXTRACTOR_GENERATOR_CAPACITY = INT(config,"Glowstone Extractor", "Generator Capacity", 5000, 1, Integer.MAX_VALUE);
		GLOWSTONE_EXTRACTOR_GENERATOR_REQUIRED = INT(config,"Glowstone Extractor", "Generator Requirement", 400, 1, Integer.MAX_VALUE);
		GLOWSTONE_EXTRACTOR_PER_TICK = INT(config,"Glowstone Extractor", "Generation", 160, 1, Integer.MAX_VALUE);

		//// CONDUCTOR_MAST \\\\
		CONDUCTOR_MAST_STORAGE  = INT(config,"Conductor Mast", "Energy Storage", 50000000, 1, Integer.MAX_VALUE);
		CONDUCTOR_MAST_TRANSFER_RATE  = INT(config,"Conductor Mast", "Transfer Rate", Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
		CONDUCTOR_MAST_CHARGING_RATE  = INT(config,"Conductor Mast", "Charging Rate", 100000, 1, Integer.MAX_VALUE);
		CONDUCTOR_MAST_PER_TICK = INT(config,"Conductor Mast", "Generation", 5000, 1, Integer.MAX_VALUE);
		CONDUCTOR_MAST_SPEED = INT(config,"Conductor Mast", "Base Speed", 50, 1, Integer.MAX_VALUE);
		WEATHER_STATION_PER_TICK = INT(config,"Conductor Mast", "Weather Station Generation", 1000, 1, Integer.MAX_VALUE);

		//// CALCULATOR LOCATOR \\\\
		CALCULATOR_LOCATOR_STORAGE  = INT(config,"Calculator Locator", "Energy Storage", 50000000, 1, Integer.MAX_VALUE);
		CALCULATOR_LOCATOR_TRANSFER_RATE  = INT(config,"Calculator Locator", "Transfer Rate", Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
		CALCULATOR_LOCATOR_CHARGING_RATE  = INT(config,"Calculator Locator", "Charging Rate", 5000000, 1, Integer.MAX_VALUE);
		CALCULATOR_LOCATOR_MULTIPLIER = config.getFloat("Calculator Locator", "Generation", 2F, 0.1F, 64F, "MULTIPLIER");
		CALCULATOR_LOCATOR_CAN_CHANGE_TIME = config.getBoolean("Locator Can Change Time", "Calculator Locator", true, "");

		//// FLAWLESS GREENHOUSE \\\\
		FLAWLESS_GREENHOUSE_STORAGE  = INT(config,"Flawless Greenhouse", "Energy Storage", 500000, 1, Integer.MAX_VALUE);
		FLAWLESS_GREENHOUSE_TRANSFER_RATE  = INT(config,"Flawless Greenhouse", "Transfer Rate", FLAWLESS_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);

		//// ADVANCED GREENHOUSE \\\\
		ADVANCED_GREENHOUSE_STORAGE  = INT(config,"Advanced Greenhouse", "Energy Storage", 350000, 1, Integer.MAX_VALUE);
		ADVANCED_GREENHOUSE_TRANSFER_RATE  = INT(config,"Advanced Greenhouse", "Transfer Rate", SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);

		//// BASIC GREENHOUSE \\\\
		BASIC_GREENHOUSE_STORAGE  = INT(config,"Basic Greenhouse", "Energy Storage", 350000, 1, Integer.MAX_VALUE);
		BASIC_GREENHOUSE_TRANSFER_RATE  = INT(config,"Basic Greenhouse", "Transfer Rate", BASIC_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);

		//// CO2 GENERATOR \\\\
		C02_GENERATOR_STORAGE  = INT(config,"Carbon Dioxide Generator", "Energy Storage", 1000000, 1, Integer.MAX_VALUE);
		C02_GENERATOR_TRANSFER_RATE  = INT(config,"Carbon Dioxide Generator", "Transfer Rate", FLAWLESS_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);

		//// DOCKING STATION \\\\
		DOCKING_STATION_STORAGE  = INT(config,"Docking Station", "Energy Storage", 50000, 1, Integer.MAX_VALUE);
		DOCKING_STATION_TRANSFER_RATE  = INT(config,"Docking Station", "Transfer Rate", SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		DOCKING_STATION_USAGE = INT(config,"Docking Station", "Energy Usage", 10, 1, Integer.MAX_VALUE);
		DOCKING_STATION_SPEED = INT(config,"Docking Station", "Base Speed", 200, 1, Integer.MAX_VALUE);

		//// GREENHOUSES \\\\
		GROWTH_ENERGY_USAGE = INT(config,"Greenhouses","Growth Energy", 150, 1, Integer.MAX_VALUE);
		BUILD_ENERGY_USAGE = INT(config,"Greenhouses","Build Energy", 100, 1, Integer.MAX_VALUE);
		PLANTING_ENERGY_USAGE = INT(config,"Greenhouses","Plant Energy", 50, 1, Integer.MAX_VALUE);
		FARMLAND_GENERATION_USAGE = INT(config,"Greenhouses","Adding Farmland",50, 1, Integer.MAX_VALUE);
		WATER_GENERATION_USAGE = INT(config,"Greenhouses","Adding Water", 1000, 1, Integer.MAX_VALUE);

		//// SCARECROW \\\\
		SCARECROW_TICK_RATE = INT(config,"Scarecrow","Scarecrow Tick Rate",500, 1, Integer.MAX_VALUE);
		SCARECROW_RANGE = INT(config,"Scarecrow","Scarecrow Range", 3, 1, 32);

		//// WEATHER CONTROLLER \\\\
		WEATHER_CONTROLLER_STORAGE  = INT(config,"Weather Controller", "Energy Storage", 1000000, 1, Integer.MAX_VALUE);
		WEATHER_CONTROLLER_TRANSFER_RATE  = INT(config,"Weather Controller", "Transfer Rate", FLAWLESS_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		WEATHER_CONTROLLER_USAGE = INT(config,"Weather Controller", "Energy Usage", 250000, 1, Integer.MAX_VALUE);

		//// ATOMIC_MULTIPLIER \\\\
		ATOMIC_MULTIPLIER_STORAGE  = INT(config,"Atomic Multiplier", "Energy Storage", 1500000000, 1, Integer.MAX_VALUE);
		ATOMIC_MULTIPLIER_TRANSFER_RATE  = INT(config,"Atomic Multiplier", "Transfer Rate", Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
		ATOMIC_MULTIPLIER_USAGE  = INT(config,"Atomic Multiplier", "Energy Usage", 1500000000, 1, Integer.MAX_VALUE);
		ATOMIC_MULTIPLIER_SPEED = INT(config,"Atomic Multiplier", "Base Speed", 1000, 1, Integer.MAX_VALUE);

		//// ANALYSING CHAMBER \\\\
		ANALYSING_CHAMBER_STORAGE  = INT(config,"Analysing Chamber", "Energy Storage", 100000, 1, Integer.MAX_VALUE);
		ANALYSING_CHAMBER_TRANSFER_RATE  = INT(config,"Analysing Chamber", "Transfer Rate", ATOMIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);

		//// REINFORCED FURNACE \\\\
		REINFORCED_FURNACE_STORAGE  = INT(config,"Reinforced Furnace", "Energy Storage", 50000, 1, Integer.MAX_VALUE);
		REINFORCED_FURNACE_TRANSFER_RATE  = INT(config,"Reinforced Furnace", "Transfer Rate", SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		REINFORCED_FURNACE_USAGE = INT(config,"Reinforced Furnace", "Energy Usage", 500, 1, Integer.MAX_VALUE);
		REINFORCED_FURNACE_SPEED = INT(config,"Reinforced Furnace", "Base Speed", 200, 1, Integer.MAX_VALUE);

		//// STONE SEPERATOR \\\\
		STONE_SEPERATOR_STORAGE  = INT(config,"Stone Seperator", "Energy Storage", 50000, 1, Integer.MAX_VALUE);
		STONE_SEPERATOR_TRANSFER_RATE  = INT(config,"Stone Seperator", "Transfer Rate", SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		STONE_SEPERATOR_USAGE = INT(config,"Stone Seperator", "Energy Usage", 500, 1, Integer.MAX_VALUE);
		STONE_SEPERATOR_SPEED = INT(config,"Stone Seperator", "Base Speed", 200, 1, Integer.MAX_VALUE);

		//// ALGORITHM SEPERATOR \\\\
		ALGORITHM_SEPERATOR_STORAGE  = INT(config,"Algorithm Seperator", "Energy Storage", 50000, 1, Integer.MAX_VALUE);
		ALGORITHM_SEPERATOR_TRANSFER_RATE  = INT(config,"Algorithm Seperator", "Transfer Rate", SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		ALGORITHM_SEPERATOR_USAGE = INT(config,"Algorithm Seperator", "Energy Usage", 5000, 1, Integer.MAX_VALUE);
		ALGORITHM_SEPERATOR_SPEED = INT(config,"Algorithm Seperator", "Base Speed", 200, 1, Integer.MAX_VALUE);

		//// EXTRACTION CHAMBER \\\\
		EXTRACTION_CHAMBER_STORAGE  = INT(config,"Extraction Chamber", "Energy Storage", 50000, 1, Integer.MAX_VALUE);
		EXTRACTION_CHAMBER_TRANSFER_RATE  = INT(config,"Extraction Chamber", "Transfer Rate", SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		EXTRACTION_CHAMBER_USAGE = INT(config,"Extraction Chamber", "Energy Usage", 5000, 1, Integer.MAX_VALUE);
		EXTRACTION_CHAMBER_SPEED = INT(config,"Extraction Chamber", "Base Speed", 1000, 1, Integer.MAX_VALUE);

		//// REASSEMBLY CHAMBER \\\\
		REASSEMBLY_CHAMBER_STORAGE  = INT(config,"Reassembly Chamber", "Energy Storage", 50000, 1, Integer.MAX_VALUE);
		REASSEMBLY_CHAMBER_TRANSFER_RATE  = INT(config,"Reassembly Chamber", "Transfer Rate", SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		REASSEMBLY_CHAMBER_USAGE = INT(config,"Reassembly Chamber", "Energy Usage", 1000, 1, Integer.MAX_VALUE);
		REASSEMBLY_CHAMBER_SPEED = INT(config,"Reassembly Chamber", "Base Speed", 1000, 1, Integer.MAX_VALUE);

		//// RESTORATION CHAMBER \\\\
		RESTORATION_CHAMBER_STORAGE  = INT(config,"Restoration Chamber", "Energy Storage", 50000, 1, Integer.MAX_VALUE);
		RESTORATION_CHAMBER_TRANSFER_RATE  = INT(config,"Restoration Chamber", "Transfer Rate", SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		RESTORATION_CHAMBER_USAGE = INT(config,"Restoration Chamber", "Energy Usage", 1000, 1, Integer.MAX_VALUE);
		RESTORATION_CHAMBER_SPEED = INT(config,"Restoration Chamber", "Base Speed", 1000, 1, Integer.MAX_VALUE);

		//// PRECISION CHAMBER \\\\
		PRECISION_CHAMBER_STORAGE  = INT(config,"Precision Chamber", "Energy Storage", 50000, 1, Integer.MAX_VALUE);
		PRECISION_CHAMBER_TRANSFER_RATE  = INT(config,"Precision Chamber", "Transfer Rate", SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		PRECISION_CHAMBER_USAGE = INT(config,"Precision Chamber", "Energy Usage", 5000, 1, Integer.MAX_VALUE);
		PRECISION_CHAMBER_SPEED = INT(config,"Precision Chamber", "Base Speed", 500, 1, Integer.MAX_VALUE);

		//// PROCESSING CHAMBER \\\\
		PROCESSING_CHAMBER_STORAGE  = INT(config,"Processing Chamber", "Energy Storage", 50000, 1, Integer.MAX_VALUE);
		PROCESSING_CHAMBER_TRANSFER_RATE  = INT(config,"Processing Chamber", "Transfer Rate", SCIENTIFIC_TIER_DEFAULT_TRANSFER_RATE, 1, Integer.MAX_VALUE);
		PROCESSING_CHAMBER_USAGE = INT(config,"Processing Chamber", "Energy Usage", 1000, 1, Integer.MAX_VALUE);
		PROCESSING_CHAMBER_SPEED = INT(config,"Processing Chamber", "Base Speed", 500, 1, Integer.MAX_VALUE);


		//// GENERAL SETTINGS \\\\
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
            for (String aBlacklisted : blacklisted) {
                if (aBlacklisted != null && aBlacklisted.equals(block)) {
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
            for (String aBlacklisted : blacklisted) {
                if (aBlacklisted != null && aBlacklisted.equals(item)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

    /**
     * @param stack the ItemStack you wish to check
     * @return if the Item/Block is enabled
     */
	public static boolean isEnabled(ItemStack stack) {
		/* if (stack == null) { return true; } Block block = Block.getBlockFromItem(stack.getItem()); Item item = stack.getItem(); if (block != null && item instanceof ItemBlock && GameRegistry.findUniqueIdentifierFor(block) != null) { if (CalculatorConfig.isBlockEnabled(GameRegistry.findUniqueIdentifierFor(block).name)) { return true; } else { return false; } } else if (item != null && GameRegistry.findUniqueIdentifierFor(item) != null) { if (CalculatorConfig.isItemEnabled(GameRegistry.findUniqueIdentifierFor(item).name)) { return true;
		 * 
		 * } else { return false; } } else { return true; } */
		return true;
	}
	public static int INT(Configuration config, String category, String name, int defaultValue, int minValue, int maxValue){
		Property prop = config.get(category, name, defaultValue);
		prop.setLanguageKey(name);
		prop.setComment("");
		prop.setMinValue(minValue);
		prop.setMaxValue(maxValue);
		return prop.getInt(defaultValue) < minValue ? minValue : (prop.getInt(defaultValue) > maxValue ? maxValue : prop.getInt(defaultValue));
	}
}
