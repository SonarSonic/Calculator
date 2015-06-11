package sonar.calculator.mod;

import java.io.File;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CalculatorConfig extends Calculator {

	public static int energyStorageType;
	public static int calculatorEnergy;
	public static int craftingEnergy;
	public static int scientificEnergy;
	public static int flawlessEnergy;
	public static int terrainEnergy;
	public static int advancedEnergy;
	public static int moduleEnergy;
	public static int cubeEnergy;
	public static int starchRF;
	public static int redstoneRF;
	public static int glowstoneRF;
	public static int locatorRF;
	public static int conductorRF;
	public static int weatherstationRF;

	public static boolean timeEffect;
	public static boolean beamEffect;
	public static boolean enableWaila;
	public static boolean enableGrenades;

	public static Property atomicblackList;
	public static Property blocksblackList;
	public static Property itemsblackList;

	public static void initConfiguration(FMLPreInitializationEvent event) {

		loadMainConfig();
		loadAtomicBlacklist();
		loadBlocks();
		loadItems();
	}

	public static void loadMainConfig() {
		Configuration config = new Configuration(new File("config/CalculatorMod/CalculatorMod.cfg"));
		config.load();

		energyStorageType = config.getInt("Energy Storage Type RF=1, EU=2", "api", 1, 1, 2, "Calculator");
		calculatorEnergy = config.getInt("(Default: 1000)", "energy storage", 1000, 10, 50000, "Calculator");
		craftingEnergy = config.getInt("(Default: 5000)", "energy storage", 5000, 10, 500000, "Crafting Calculator");
		scientificEnergy = config.getInt("(Default: 2000)", "energy storage", 2000, 10, 50000, "Scientific Calculator");
		flawlessEnergy = config.getInt("(Default: 10000)", "energy storage", 100000, 10000, 500000, "Flawless Calculator");
		terrainEnergy = config.getInt("(Default: 400)", "energy storage", 400, 100, 50000, "Terrain Module");
		advancedEnergy = config.getInt("(Default: 2000)", "energy storage", 2000, 100, 50000, "Advanced Terrain Module");
		moduleEnergy = config.getInt("(Default: 100000)", "energy storage", 100000, 1000, 1000000, "Energy Module");
		cubeEnergy = config.getInt("(Default: 50000)", "energy storage", 50000, 1000, 1000000, "Default Machine Energy");
		starchRF = config.getInt("(Default: 40) = RF per Tick", "energy generation", 40, 1, 128, "Starch Extractor");
		redstoneRF = config.getInt("(Default: 80) = RF per Tick", "energy generation", 80, 4, 192, "Redstone Extractor");
		glowstoneRF = config.getInt("(Default: 160) = RF per Tick", "energy generation", 160, 16, 256, "Glowstone Extractor");
		locatorRF = config.getInt("(Default: 1000) = RF per Tick", "energy generation", 1000, 100, 2500000, "Calculator Locator");
		timeEffect = config.getBoolean("Locator Can Change Time", "settings", true, "Calculator Locator");
		beamEffect = config.getBoolean("Locator has a beam", "settings", true, "Calculator Locator");
		enableWaila = config.getBoolean("enable Waila integration", "api", true, "Waila");
		enableGrenades = config.getBoolean("allow grenades?", "items", true, "Grenades");
		conductorRF = config.getInt("(Default: 5000) = RF per strike", "lightning farms", 5000, 200, 5000000, "Conductor Mast");
		weatherstationRF = config.getInt("(Default: 5000) = RF per strike", "lightning farms", 1000, 200, 5000000, "Weather Station");
		config.save();

	}

	public static void loadAtomicBlacklist() {
		Configuration blacklist = new Configuration(new File("config/CalculatorMod/AtomicMultiplier-BlackList.cfg"));
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
		Configuration blocks = new Configuration(new File("config/CalculatorMod/Blocks-BlackList.cfg"));
		blocks.load();
		String[] blockExamples = new String[2];
		blockExamples[0] = "ExampleBlock";
		blockExamples[1] = "ExampleBlock2";

		blocksblackList = blocks.get("Block Config", "Disabled", blockExamples);
		blocks.save();
	}

	public static void loadItems() {
		Configuration items = new Configuration(new File("config/CalculatorMod/Items-BlackList.cfg"));
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
		if (block != null && item instanceof ItemBlock) {
			if (CalculatorConfig.isBlockEnabled(GameRegistry.findUniqueIdentifierFor(block).name)) {
				return true;

			} else {
				return false;
			}
		} else if (item != null) {
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
