package sonar.calculator.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sonar.calculator.mod.common.entities.CalculatorThrow;
import sonar.calculator.mod.common.entities.EntityBabyGrenade;
import sonar.calculator.mod.common.entities.EntityGrenade;
import sonar.calculator.mod.common.entities.EntitySmallStone;
import sonar.calculator.mod.common.entities.EntitySoil;
import sonar.calculator.mod.common.item.calculators.ModuleItemRegistry;
import sonar.calculator.mod.common.item.calculators.ModuleRegistry;
import sonar.calculator.mod.integration.minetweaker.MineTweakerIntegration;
import sonar.calculator.mod.network.CalculatorCommon;
import sonar.calculator.mod.research.ResearchRegistry;
import sonar.calculator.mod.utils.TeleporterRegistry;

@Mod(modid = Calculator.modid, name = "Calculator", version = Calculator.version, dependencies = "required-after:SonarCore")
public class Calculator {

	@SidedProxy(clientSide = "sonar.calculator.mod.network.CalculatorClient", serverSide = "sonar.calculator.mod.network.CalculatorCommon")
	public static CalculatorCommon calculatorProxy;

	public static final String modid = "calculator";
	public static final String version = "3.2.1";

	public static final int saveDimension = 0;

	public static SimpleNetworkWrapper network;
	public static Logger logger = (Logger) LogManager.getLogger(modid);
	public static ResearchRegistry research = new ResearchRegistry();
	public static ModuleRegistry modules = new ModuleRegistry();
	public static ModuleItemRegistry moduleItems = new ModuleItemRegistry();

	@Instance(modid)
	public static Calculator instance;

	public static CreativeTabs Calculator = new CreativeTabs("Calculator") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(itemCalculator, 1);
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if (!(Loader.instance().isModLoaded("SonarCore") || Loader.instance().isModLoaded("sonarcore"))) {
			logger.fatal("Sonar Core is not loaded");
		} else {
			logger.info("Successfully loaded with Sonar Core");
		}
		network = NetworkRegistry.INSTANCE.newSimpleChannel(modid);
		logger.info("Registered Network");

		CalculatorCommon.registerPackets();
		logger.info("Registered Packets");
		CalculatorConfig.initConfiguration(event);
		logger.info("Loaded Configuration");

		CalculatorBlocks.registerBlocks();
		logger.info("Loaded Blocks");

		CalculatorItems.registerItems();
		logger.info("Loaded Items");

		calculatorProxy.registerRenderThings();
		logger.info("Registered Renderers");

		EntityRegistry.registerModEntity(EntityBabyGrenade.class, "BabyGrenade", 0, instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityGrenade.class, "Grenade", 1, instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntitySmallStone.class, "Stone", 2, instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntitySoil.class, "Soil", 3, instance, 64, 10, true);
		logger.info("Registered Entities");

		CalculatorIntegration.addSonarCore();
		logger.info("Registered with SonarCore");
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {

		Recipes.registerRecipes();
		logger.info("Registered Calculator Recipes");

		CalculatorCrafting.addRecipes();
		logger.info("Added Crafting Recipes");

		CalculatorSmelting.addSmeltingRecipes();
		logger.info("Added Smelting Recipes");

		CalculatorOreDict.registerOres();
		logger.info("Registered OreDict");

		GameRegistry.registerFuelHandler(new CalculatorSmelting());
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CalculatorCommon());
		logger.info("Registered Handlers");

		MinecraftForge.EVENT_BUS.register(new CalculatorEvents());
		logger.info("Registered Events");

		research.register();
		modules.register();
		moduleItems.register();

	}

	@EventHandler
	public void postLoad(FMLPostInitializationEvent evt) {
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(baby_grenade, new CalculatorThrow(0));
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(grenade, new CalculatorThrow(1));
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(small_stone, new CalculatorThrow(2));
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(soil, new CalculatorThrow(3));
		Recipes.printRecipeInfo();

		if (Loader.isModLoaded("MineTweaker3") || Loader.isModLoaded("MineTweaker3".toLowerCase())) {
			MineTweakerIntegration.init();
		}

	}

	@EventHandler
	public void onServerStopping(FMLServerStoppingEvent event) {
		TeleporterRegistry.removeAll();
	}

	public static Item itemCalculator, itemInfoCalculator, itemCraftingCalculator, itemScientificCalculator,
			itemFlawlessCalculator;
	public static Item itemHungerModule, itemHealthModule, itemNutritionModule;
	public static Item itemTerrainModule, itemAdvancedTerrainModule;
	public static Item itemEnergyModule;
	public static Item itemLocatorModule;
	public static Item itemStorageModule;
	public static Item itemWarpModule, itemJumpModule;
	// public static Item itemSmeltingModule;
	public static Item circuitBoard, circuitDamaged, circuitDirty;
	public static Item wrench;

	public static Block atomicCalculator, dynamicCalculator;
	public static Block powerCube, advancedPowerCube;
	public static Block dockingStation;
	public static Block reinforcedFurnace, reinforcedChest, stoneSeparator, algorithmSeparator;

	public static Block extractionChamber, restorationChamber, reassemblyChamber, processingChamber, precisionChamber;
	public static Block analysingChamber, storageChamber, fabricationChamber;

	// public static Block manipulationChamber;
	public static Block hungerProcessor, healthProcessor, amethystPiping, tanzanitePiping;
	public static Block basicGreenhouse, advancedGreenhouse, flawlessGreenhouse, CO2Generator;
	public static Block scarecrow, scarecrowBlock;
	public static Block gas_lantern_on, gas_lantern_off, basic_lantern;
	public static Block starchextractor, redstoneextractor, glowstoneextractor;
	public static Block calculatorplug, calculatorlocator;
	public static Block conductorMast, conductormastBlock;
	public static Block weatherStation, weatherStationBlock;
	public static Block transmitter, transmitterBlock;
	public static Block atomicMultiplier;
	public static Block flawlessCapacitor;
	public static Block handcrankedGenerator;
	public static Block weatherController, rainSensor;
	public static Block crankHandle;
	public static Block researchChamber;
	public static Block calculatorScreen;
	public static Block magneticFlux;
	public static Block teleporter;
	public static Block stoneAssimilator, algorithmAssimilator;
	public static Block flawlessFurnace;
	public static Block eternalFire;
	public static Block moduleWorkstation;
	public static Block creativePowerCube;

	// calculator parts
	public static Item calculator_screen;
	public static Item calculator_assembly;
	public static Item advanced_assembly;
	public static Item atomic_module;
	public static Item atomic_binder;
	public static Item atomic_assembly;
	public static Item flawless_assembly;

	// tools
	public static Item reinforced_axe, reinforced_pickaxe, reinforced_shovel, reinforced_hoe, reinforced_sword;
	public static Item redstone_axe, redstone_pickaxe, redstone_shovel, redstone_hoe, redstone_sword;
	public static Item enrichedgold_axe, enrichedgold_pickaxe, enrichedgold_shovel, enrichedgold_hoe,
			enrichedgold_sword;
	public static Item reinforcediron_axe, reinforcediron_pickaxe, reinforcediron_shovel, reinforcediron_hoe,
			reinforcediron_sword;
	public static Item weakeneddiamond_axe, weakeneddiamond_pickaxe, weakeneddiamond_shovel, weakeneddiamond_hoe,
			weakeneddiamond_sword;
	public static Item flawlessdiamond_axe, flawlessdiamond_pickaxe, flawlessdiamond_shovel, flawlessdiamond_hoe,
			flawlessdiamond_sword;
	public static Item firediamond_axe, firediamond_pickaxe, firediamond_shovel, firediamond_hoe, firediamond_sword;
	public static Item electric_axe, electric_pickaxe, electric_shovel, electric_hoe, electric_sword;
	public static Item endforged_axe, endforged_pickaxe, endforged_shovel, endforged_hoe, endforged_sword;

	// upgrades
	public static Item energyUpgrade, speedUpgrade, voidUpgrade, transferUpgrade;

	// materials
	public static Item enriched_coal, purified_coal, firecoal, controlled_Fuel;
	public static Item redstone_ingot, reinforcediron_ingot, enrichedgold_ingot, enrichedGold;
	public static Item weakeneddiamond, flawlessdiamond, firediamond, electricDiamond, endDiamond;
	public static Item large_amethyst, small_amethyst, shard_amethyst;
	public static Item large_tanzanite, small_tanzanite, shard_tanzanite;

	// crops
	public static Item broccoliSeeds, broccoli, cookedBroccoli;
	public static Item prunaeSeeds, coal_dust;
	public static Item fiddledewFruit;
	public static Block cropBroccoliPlant, cropPrunaePlant, cropFiddledewPlant;

	// misc
	public static Item baby_grenade, grenade, grenadecasing;
	public static Item pear, rotten_pear;
	public static Item soil, small_stone;

	public static Block flawlessGlass, purifiedObsidian;

	// trees
	public static Block amethystLeaves, tanzaniteLeaves, pearLeaves, diamondLeaves;
	public static Block amethystLog, tanzaniteLog, pearLog, diamondLog;
	public static Block amethystSapling, tanzaniteSapling, pearSapling, diamondSapling;
	public static Block amethystPlanks, tanzanitePlanks, pearPlanks, diamondPlanks;
	public static Block amethystStairs, tanzaniteStairs, pearStairs, diamondStairs;
	public static Block amethystFence, tanzaniteFence, pearFence, diamondFence;

	// decorative blocks
	public static Block material_block;
	// tools
	public static Item sickle, obsidianKey;

}