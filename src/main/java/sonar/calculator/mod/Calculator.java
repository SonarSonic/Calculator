package sonar.calculator.mod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sonar.calculator.mod.common.entities.CalculatorThrow;
import sonar.calculator.mod.common.entities.EntityBabyGrenade;
import sonar.calculator.mod.common.entities.EntityGrenade;
import sonar.calculator.mod.common.entities.EntitySmallStone;
import sonar.calculator.mod.common.entities.EntitySoil;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.common.recipes.machines.AlgorithmSeparatorRecipes;
import sonar.calculator.mod.common.recipes.machines.ExtractionChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.GlowstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.machines.HealthProcessorRecipes;
import sonar.calculator.mod.common.recipes.machines.PrecisionChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.ProcessingChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.ReassemblyChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.RedstoneExtractorRecipes;
import sonar.calculator.mod.common.recipes.machines.StarchExtractorRecipes;
import sonar.calculator.mod.common.recipes.machines.StoneSeparatorRecipes;
import sonar.calculator.mod.integration.ae2.StorageChamberHandler;
import sonar.calculator.mod.integration.minetweaker.MinetweakerIntegration;
import sonar.calculator.mod.network.CalculatorCommon;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.calculator.mod.utils.TeleporterRegistry;
import sonar.core.energy.DischargeValues;

@Mod(modid = Calculator.modid, name = "Calculator", version = Calculator.version)
public class Calculator {

	@SidedProxy(clientSide = "sonar.calculator.mod.network.CalculatorClient", serverSide = "sonar.calculator.mod.network.CalculatorCommon")
	public static CalculatorCommon calculatorProxy;

	public static final String modid = "Calculator";
	public static final String version = "1.9.5";

	public static SimpleNetworkWrapper network;
	public static Logger logger = (Logger) LogManager.getLogger(modid);

	@Instance(modid)
	public static Calculator instance;

	public static CreativeTabs Calculator = new CreativeTabs("Calculator") {
		@Override
		public Item getTabIconItem() {
			return itemCalculator;
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if (!Loader.isModLoaded("SonarCore")) {
			logger.fatal("Sonar Core is not loaded");
		} else {
			logger.info("Successfully loaded with Sonar Core");
		}
		/*
		ForgeChunkManager.setForcedChunkLoadingCallback(this, new ChunkHandler());

		*/
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
		

	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		
		RecipeRegistry.registerRecipes();
		logger.info("Registered Calculator Recipes");

		CalculatorCrafting.addRecipes();
		logger.info("Added Crafting Recipes");

		CalculatorSmelting.addSmeltingRecipes();
		logger.info("Added Smelting Recipes");

		DischargeValues.addValue(enriched_coal, 3000);
		DischargeValues.addValue(firecoal, 10000);
		DischargeValues.addValue(purified_coal, 10000);
		DischargeValues.addValue(coal_dust, 250);
		logger.info("Added Discharge Values");

		CalculatorOreDict.registerOres();
		logger.info("Registered OreDict");
	
		/*
		PlanterRegistry.registerPlanters();
		logger.info("Registered Planters");

		 */
		GameRegistry.registerFuelHandler(new CalculatorSmelting());
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CalculatorCommon());
		logger.info("Registered Handlers");

		//MinecraftForge.EVENT_BUS.register(new CalculatorEvents());
		//FMLCommonHandler.instance().bus().register(new CalculatorEvents());
		//logger.info("Registered Events");
	}

	@EventHandler
	public void postLoad(FMLPostInitializationEvent evt) {
		
		BlockDispenser.dispenseBehaviorRegistry.putObject(baby_grenade, new CalculatorThrow(0));
		BlockDispenser.dispenseBehaviorRegistry.putObject(grenade, new CalculatorThrow(1));
		BlockDispenser.dispenseBehaviorRegistry.putObject(small_stone, new CalculatorThrow(2));
		BlockDispenser.dispenseBehaviorRegistry.putObject(soil, new CalculatorThrow(3));
		logger.info(RecipeRegistry.getUnblockedSize() + " Standard Calculator Recipes were loaded");
		logger.info(RecipeRegistry.getBlockedSize() + " Hidden Calculator Recipes were loaded");
		logger.info(RecipeRegistry.getScientificSize() + " Scientific Recipes were loaded");
		logger.info(RecipeRegistry.getAtomicSize() + " Atomic Recipes were loaded");
		logger.info(RecipeRegistry.getFlawlessSize() + " Flawless Recipes were loaded");
		logger.info(RecipeRegistry.ConductorMastItemRecipes.instance().getRecipes().size() + " Conductor Mast Recipes Recipes were loaded");
		logger.info(AlgorithmSeparatorRecipes.instance().getRecipes().size() + " Algorithm Seperator Recipes were loaded");
		logger.info(ExtractionChamberRecipes.instance().getRecipes().size() + " Extraction Chamber Recipes were loaded");
		logger.info(GlowstoneExtractorRecipes.instance().getRecipes().size() + " Glowstone Extractor Recipes were loaded");
		logger.info(HealthProcessorRecipes.instance().getRecipes().size() + " Health Processor Recipes were loaded");
		logger.info(PrecisionChamberRecipes.instance().getRecipes().size() + " Precision Chamber Recipes were loaded");
		logger.info(ProcessingChamberRecipes.instance().getRecipes().size() + " Processing Chamber Recipes were loaded");
		logger.info(ReassemblyChamberRecipes.instance().getRecipes().size() + " Reassembly Chamber Recipes were loaded");
		logger.info(RedstoneExtractorRecipes.instance().getRecipes().size() + " Redstone Extractor Recipes were loaded");
		logger.info(StarchExtractorRecipes.instance().getRecipes().size() + " Starch Extractor Recipes were loaded");
		logger.info(StoneSeparatorRecipes.instance().getRecipes().size() + " Stone Separator Recipes were loaded");
		if (Loader.isModLoaded("MineTweaker3")) {
			MinetweakerIntegration.integrate();
		}

		if (Loader.isModLoaded("appliedenergistics2")) {
			StorageChamberHandler.init();
			logger.info("Registered AE2 Handler for Storage Chamber");
		}
		 
	}

	@EventHandler
	public void loadFluxNetwork(FMLServerStoppingEvent event) {
		FluxRegistry.removeAll();
		TeleporterRegistry.removeAll();
		RecipeRegistry.clearRecipes();
	}

	public static Item itemCalculator;
	public static Item itemInfoCalculator;
	public static Item itemCraftingCalculator;
	public static Item itemScientificCalculator;
	public static Item itemFlawlessCalculator;
	public static Item itemHungerModule;
	public static Item itemHealthModule;
	public static Item itemNutritionModule;
	public static Item itemTerrainModule;
	public static Item itemAdvancedTerrainModule;
	public static Item itemEnergyModule;
	public static Item itemLocatorModule;
	public static Item itemStorageModule;
	public static Item itemSmeltingModule;
	public static Item circuitBoard;
	public static Item circuitDamaged;
	public static Item circuitDirty;
	public static Item wrench;

	public static Block atomicCalculator;
	public static Block dynamicCalculator;
	public static Block powerCube;
	public static Block advancedPowerCube;
	public static Block dockingStation;
	public static Block reinforcedFurnace;
	public static Block extractionChamber;
	public static Block restorationChamber;
	public static Block reassemblyChamber;
	public static Block processingChamber;
	public static Block precisionChamber;
	public static Block analysingChamber;
	public static Block storageChamber;
	public static Block manipulationChamber;
	public static Block hungerProcessor;
	public static Block healthProcessor;
	public static Block basicGreenhouse;
	public static Block advancedGreenhouse;
	public static Block flawlessGreenhouse;
	public static Block CO2Generator;
	public static Block fluxPlug, fluxPoint, fluxController;
	public static Block scarecrow;
	public static Block scarecrowBlock;
	public static Block gas_lantern_on;
	public static Block gas_lantern_off;
	public static Block basic_lantern;
	public static Block stoneSeparator;
	public static Block algorithmSeparator;
	public static Block starchextractor;
	public static Block redstoneextractor;
	public static Block glowstoneextractor;
	public static Block calculatorplug;
	public static Block calculatorlocator;
	public static Block conductorMast;
	public static Block conductormastBlock;
	public static Block weatherStation;
	public static Block weatherStationBlock;
	public static Block transmitter;
	public static Block transmitterBlock;
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

	// calculator parts
	public static Item calculator_screen;
	public static Item calculator_assembly;
	public static Item advanced_assembly;
	public static Item atomic_module;
	public static Item atomic_binder;
	public static Item atomic_assembly;

	public static Item calculator_sword;
	
	// tools
	public static Item reinforced_axe;
	public static Item reinforced_pickaxe;
	public static Item reinforced_shovel;
	public static Item reinforced_hoe;
	public static Item reinforced_sword;

	public static Item redstone_axe;
	public static Item redstone_pickaxe;
	public static Item redstone_shovel;
	public static Item redstone_hoe;
	public static Item redstone_sword;

	public static Item enrichedgold_axe;
	public static Item enrichedgold_pickaxe;
	public static Item enrichedgold_shovel;
	public static Item enrichedgold_hoe;
	public static Item enrichedgold_sword;

	public static Item reinforcediron_axe;
	public static Item reinforcediron_pickaxe;
	public static Item reinforcediron_shovel;
	public static Item reinforcediron_hoe;
	public static Item reinforcediron_sword;

	public static Item weakeneddiamond_axe;
	public static Item weakeneddiamond_pickaxe;
	public static Item weakeneddiamond_shovel;
	public static Item weakeneddiamond_hoe;
	public static Item weakeneddiamond_sword;

	public static Item flawlessdiamond_axe;
	public static Item flawlessdiamond_pickaxe;
	public static Item flawlessdiamond_shovel;
	public static Item flawlessdiamond_hoe;
	public static Item flawlessdiamond_sword;

	public static Item firediamond_axe;
	public static Item firediamond_pickaxe;
	public static Item firediamond_shovel;
	public static Item firediamond_hoe;
	public static Item firediamond_sword;

	public static Item electric_axe;
	public static Item electric_pickaxe;
	public static Item electric_shovel;
	public static Item electric_hoe;
	public static Item electric_sword;

	public static Item endforged_axe;
	public static Item endforged_pickaxe;
	public static Item endforged_shovel;
	public static Item endforged_hoe;
	public static Item endforged_sword;

	public static Item energyUpgrade;
	public static Item speedUpgrade;
	public static Item voidUpgrade;

	// fuels
	public static Item enriched_coal, purified_coal, firecoal, controlled_Fuel;

	// diamonds
	public static Item weakeneddiamond, flawlessdiamond, firediamond, electricDiamond, endDiamond;

	// ingots
	public static Item redstone_ingot, reinforcediron_ingot, enrichedgold_ingot, enrichedGold;

	// gems
	public static Item large_amethyst, small_amethyst, shard_amethyst;
	public static Item large_tanzanite, small_tanzanite, shard_tanzanite;

	// grenades
	public static Item baby_grenade, grenade, grenadecasing;

	// crops
	public static Item broccoliSeeds, broccoli, cookedBroccoli;
	public static Item prunaeSeeds, coal_dust;
	public static Item fiddledewFruit;
	public static Block cropBroccoliPlant, cropPrunaePlant, cropFiddledewPlant;

	// food
	public static Item pear, rotten_pear;
	public static Item soil, small_stone;

	// common blocks
	public static Block reinforcedStoneBlock, reinforcedStoneBrick, reinforcedDirtBlock, reinforcedDirtBrick, purifiedObsidian, stableStone, stablestonerimmedBlock, stablestonerimmedblackBlock, stableGlass, clearStableGlass, flawlessGlass;
	public static Block reinforcedStoneStairs, reinforcedStoneBrickStairs, reinforcedDirtStairs, reinforcedDirtBrickStairs;
	public static Block reinforcedStoneFence, reinforcedStoneBrickFence, reinforcedDirtFence, reinforcedDirtBrickFence;

	// trees
	public static Block amethystLeaves, tanzaniteLeaves, pearLeaves, diamondLeaves;
	public static Block amethystLog, tanzaniteLog, pearLog, diamondLog;
	public static Block AmethystSapling, tanzaniteSapling, PearSapling, diamondSapling;
	public static Block amethystPlanks, tanzanitePlanks, pearPlanks, diamondPlanks;
	public static Block amethystStairs, tanzaniteStairs, pearStairs, diamondStairs;
	public static Block amethystFence, tanzaniteFence, pearFence, diamondFence;

	// decorative blocks
	public static Block material_block;
	// tools
	public static Item sickle, ObsidianKey;
}