package sonar.calculator.mod;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sonar.calculator.mod.common.block.CalculatorCrops;
import sonar.calculator.mod.common.block.CalculatorLeaves;
import sonar.calculator.mod.common.block.CalculatorLogs;
import sonar.calculator.mod.common.block.CalculatorPlanks;
import sonar.calculator.mod.common.block.CalculatorSaplings;
import sonar.calculator.mod.common.block.MaterialBlock;
import sonar.calculator.mod.common.block.SmeltingBlock;
import sonar.calculator.mod.common.block.SmeltingBlock.BlockTypes;
import sonar.calculator.mod.common.block.calculators.AtomicCalculator;
import sonar.calculator.mod.common.block.calculators.DynamicCalculator;
import sonar.calculator.mod.common.block.generators.CalculatorLocator;
import sonar.calculator.mod.common.block.generators.CalculatorPlug;
import sonar.calculator.mod.common.block.generators.ConductorMast;
import sonar.calculator.mod.common.block.generators.CrankHandle;
import sonar.calculator.mod.common.block.generators.CrankedGenerator;
import sonar.calculator.mod.common.block.generators.ExtractorBlock;
import sonar.calculator.mod.common.block.generators.InvisibleBlock;
import sonar.calculator.mod.common.block.machines.AdvancedPowerCube;
import sonar.calculator.mod.common.block.machines.AnalysingChamber;
import sonar.calculator.mod.common.block.machines.Assimilator;
import sonar.calculator.mod.common.block.machines.AtomicMultiplier;
import sonar.calculator.mod.common.block.machines.CreativePowerCube;
import sonar.calculator.mod.common.block.machines.DockingStation;
import sonar.calculator.mod.common.block.machines.FabricationChamber;
import sonar.calculator.mod.common.block.machines.FlawlessGreenhouse;
import sonar.calculator.mod.common.block.machines.Greenhouse;
import sonar.calculator.mod.common.block.machines.HealthProcessor;
import sonar.calculator.mod.common.block.machines.HungerProcessor;
import sonar.calculator.mod.common.block.machines.ModuleWorkstation;
import sonar.calculator.mod.common.block.machines.PowerCube;
import sonar.calculator.mod.common.block.machines.Transmitter;
import sonar.calculator.mod.common.block.machines.WeatherStation;
import sonar.calculator.mod.common.block.misc.BasicLantern;
import sonar.calculator.mod.common.block.misc.CO2Generator;
import sonar.calculator.mod.common.block.misc.GasLantern;
import sonar.calculator.mod.common.block.misc.MagneticFlux;
import sonar.calculator.mod.common.block.misc.Piping;
import sonar.calculator.mod.common.block.misc.PurifiedObsidian;
import sonar.calculator.mod.common.block.misc.RainSensor;
import sonar.calculator.mod.common.block.misc.ReinforcedChest;
import sonar.calculator.mod.common.block.misc.Scarecrow;
import sonar.calculator.mod.common.block.misc.ScarecrowBlock;
import sonar.calculator.mod.common.block.misc.StorageChamber;
import sonar.calculator.mod.common.block.misc.WeatherController;
import sonar.calculator.mod.common.tileentity.TileEntityMachine;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankedGenerator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.calculator.mod.common.tileentity.machines.TileEntityBasicGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityCreativePowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityDockingStation;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFabricationChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHealthProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHungerProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityModuleWorkstation;
import sonar.calculator.mod.common.tileentity.machines.TileEntityPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityTransmitter;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherController;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherStation;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityGasLantern;
import sonar.calculator.mod.common.tileentity.misc.TileEntityMagneticFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityRainSensor;
import sonar.calculator.mod.common.tileentity.misc.TileEntityReinforcedChest;
import sonar.calculator.mod.common.tileentity.misc.TileEntityScarecrow;
import sonar.core.common.block.ConnectedBlock;
import sonar.core.common.block.SonarBlockTip;
import sonar.core.common.block.SonarMetaBlock;
import sonar.core.common.block.SonarStairs;

public class CalculatorBlocks extends Calculator {

	public static ArrayList<Block> registeredBlocks = new ArrayList();

	public static Block addRegisteredBlock(Block block) {
		registeredBlocks.add(block);
		return block;
	}

	public static Block registerBlock(String name, Block block) {
		block.setCreativeTab(Calculator);
		block.setUnlocalizedName(name);
		block.setRegistryName(modid, name);
		GameRegistry.register(block);
		GameRegistry.register(new SonarBlockTip(block).setRegistryName(modid, name));
		return addRegisteredBlock(block);
	}
	
	public static Block registerBlockWithoutTab(String name, Block block) {
		block.setUnlocalizedName(name);
		block.setRegistryName(modid, name);
		GameRegistry.register(block);
		GameRegistry.register(new SonarBlockTip(block).setRegistryName(modid, name));
		return addRegisteredBlock(block);
	}

	public static Block registerMetaBlock(String name, Block block) {
		block.setCreativeTab(Calculator);
		block.setUnlocalizedName(name);
		block.setRegistryName(modid, name);
		GameRegistry.register(block);
		GameRegistry.register(new SonarMetaBlock(block).setRegistryName(modid, name));
		return addRegisteredBlock(block);
	}

	public static void registerBlocks() {

		flawlessGlass = registerBlock("FlawlessGlass", new ConnectedBlock.Glass(Material.GLASS, 3)).setLightLevel(0.625F).setHardness(0.6F);
		purifiedObsidian = registerBlock("PurifiedObsidian", new PurifiedObsidian(Material.ROCK, 4)).setBlockUnbreakable().setResistance(6000000.0F);
		
		/* stablestonerimmedBlock = new ConnectedBlock.StableRimmed().setUnlocalizedName("stablestonerimmedBlock").setCreativeTab(Calculator).setHardness(2.0F); GameRegistry.registerBlock(stablestonerimmedBlock, ItemMetaBlock.class, "stablestonerimmedBlock"); stablestonerimmedblackBlock = new ConnectedBlock.StableBlackRimmed().setUnlocalizedName("stablestonerimmedblackBlock").setCreativeTab(Calculator).setHardness(2.0F); GameRegistry.registerBlock(stablestonerimmedblackBlock, ItemMetaBlock.class, "stablestonerimmedblackBlock"); stableglassBlock = new ConnectedBlock.StableGlass("stablestone_glass", 3).setBlockName("StableGlass").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(0.6F); GameRegistry.registerBlock(stableglassBlock, SonarBlockTip.class, "StableGlass"); clearstableglassBlock = new ConnectedBlock.StableGlass("stablestone_clear", 4).setBlockName("ClearStableGlass").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(0.6F); GameRegistry.registerBlock(clearstableglassBlock, SonarBlockTip.class, "ClearStableGlass"); flawlessGlass = new ConnectedBlock(Material.glass, "flawlessglass", 1, false).setBlockName("FlawlessGlass").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(0.6F); GameRegistry.registerBlock(flawlessGlass, SonarBlockTip.class, "FlawlessGlass"); */
		// calculators
		powerCube = registerBlock("PowerCube", new PowerCube().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityPowerCube.class, "PowerCube");
		advancedPowerCube = registerBlock("AdvancedPowerCube", new AdvancedPowerCube().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAdvancedPowerCube.class, "AdvancedPowerCube");
		creativePowerCube = registerBlock("CreativePowerCube", new CreativePowerCube().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCreativePowerCube.class, "CreativePowerCube");
		atomicCalculator = registerBlock("AtomicCalculator", new AtomicCalculator().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCalculator.Atomic.class, "AtomicCalculator");
		dynamicCalculator = registerBlock("DynamicCalculator", new DynamicCalculator().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCalculator.Dynamic.class, "DynamicCalculator");
		moduleWorkstation = registerBlock("ModuleWorkstation", new ModuleWorkstation().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityModuleWorkstation.class, "ModuleWorkstation");

		// smelting
		reinforcedFurnace = registerBlock("ReinforcedFurnace", new SmeltingBlock(BlockTypes.FURNACE).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.ReinforcedFurnace.class, "ReinforcedFurnace");
		reinforcedChest = registerBlock("ReinforcedChest", new ReinforcedChest().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityReinforcedChest.class, "ReinforcedChest");
		// flawlessFurnace = new FlawlessFurnace().setUnlocalizedName("FlawlessFurnace").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		// GameRegistry.registerBlock(flawlessFurnace, SonarBlockTip.class, "FlawlessFurnace");
		// GameRegistry.registerTileEntity(TileEntityFlawlessFurnace.class, "FlawlessFurnace");

		stoneSeparator = registerBlock("StoneSeparator", new SmeltingBlock(BlockTypes.STONE).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.StoneSeperator.class, "StoneSeparator");
		algorithmSeparator = registerBlock("AlgorithmSeparator", new SmeltingBlock(BlockTypes.ALGORITHM).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.AlgorithmSeperator.class, "AlgorithmSeparator");
		hungerProcessor = registerBlock("HungerProcessor", new HungerProcessor().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityHungerProcessor.class, "HungerProcessor");
		healthProcessor = registerBlock("HealthProcessor", new HealthProcessor().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityHealthProcessor.class, "HealthProcessor");

		amethystPiping = registerBlock("AmethystPiping", new Piping.Amethyst().setHardness(1.0F).setResistance(20.0F));
		// GameRegistry.registerTileEntity(TileEntityHungerProcessor.class, "AmethystPiping");

		tanzanitePiping = registerBlock("TanzanitePiping", new Piping.Tanzanite().setHardness(1.0F).setResistance(20.0F));

		// dockingStation = new DockingStation().setUnlocalizedName("DockingStation").setCreativeTab(Calculator).setHardness(1.5F); GameRegistry.registerBlock(dockingStation, SonarBlockTip.class, "DockingStation").setBlockTextureName(modid + ":" + "reinforcedstone"); GameRegistry.registerTileEntity(TileEntityDockingStation.class, "DockingStation"); */

		dockingStation = registerBlock("DockingStation", new DockingStation().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityDockingStation.class, "DockingStation");
		
		atomicMultiplier = registerBlock("AtomicMultiplier", new AtomicMultiplier().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAtomicMultiplier.class, "AtomicMultiplier");

		extractionChamber = registerBlock("ExtractionChamber", new SmeltingBlock.ChamberBlock(BlockTypes.EXTRACTION).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.ExtractionChamber.class, "ExtractionChamber");
		restorationChamber = registerBlock("RestorationChamber", new SmeltingBlock.ChamberBlock(BlockTypes.RESTORATION).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.RestorationChamber.class, "RestorationChamber");
		reassemblyChamber = registerBlock("ReassemblyChamber", new SmeltingBlock.ChamberBlock(BlockTypes.REASSEMBLY).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.ReassemblyChamber.class, "ReassemblyChamber");
		precisionChamber = registerBlock("PrecisionChamber", new SmeltingBlock.ChamberBlock(BlockTypes.PRECISION).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.PrecisionChamber.class, "PrecisionChamber");
		processingChamber = registerBlock("ProcessingChamber", new SmeltingBlock.ChamberBlock(BlockTypes.PROCESSING).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.ProcessingChamber.class, "ProcessingChamber");
		analysingChamber = registerBlock("AnalysingChamber", new AnalysingChamber().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAnalysingChamber.class, "AnalysingChamber");
		storageChamber = registerBlock("StorageChamber", new StorageChamber().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityStorageChamber.class, "StorageChamber");
		fabricationChamber = registerBlock("FabricationChamber", new FabricationChamber().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityFabricationChamber.class, "FabricationChamber");
		//researchChamber = registerBlock("ResearchChamber", new ResearchChamber().setHardness(1.0F).setResistance(20.0F));
		//GameRegistry.registerTileEntity(TileEntityResearchChamber.class, "ResearchChamber");

		// machines
		basicGreenhouse = registerBlock("BasicGreenhouse", new Greenhouse.Basic().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityBasicGreenhouse.class, "BasicGreenhouse");
		advancedGreenhouse = registerBlock("AdvancedGreenhouse", new Greenhouse.Advanced().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAdvancedGreenhouse.class, "AdvancedGreenhouse");
		flawlessGreenhouse = registerBlock("FlawlessGreenhouse", new FlawlessGreenhouse().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityFlawlessGreenhouse.class, "FlawlessGreenhouse");
		CO2Generator = registerBlock("CO2Generator", new CO2Generator().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCO2Generator.class, "CO2Generator");
		/* teleporter = new Teleporter().setUnlocalizedName("CalculatorTeleporter").setCreativeTab(Calculator).setHardness(1.0F).setLightLevel(0.625F).setBlockTextureName(modid + ":" + "stablestone"); GameRegistry.registerBlock(teleporter, SonarBlockTip.class, "CalculatorTeleporter"); GameRegistry.registerTileEntity(TileEntityTeleporter.class, "CalculatorTeleporter"); */
		calculatorlocator = registerBlock("CalculatorLocator", new CalculatorLocator().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCalculatorLocator.class, "CalculatorLocator");
		calculatorplug = registerBlock("CalculatorPlug", new CalculatorPlug().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCalculatorPlug.class, "CalculatorPlug");
		// energy //flawlessCapacitor = new FlawlessCapacitor().setUnlocalizedName("FlawlessCapacitor").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(6.5F).setBlockTextureName(modid + ":" + // "electric_diamond_block"); // GameRegistry.registerBlock(flawlessCapacitor,SonarBlockTip.class, "FlawlessCapacitor"); // GameRegistry.registerTileEntity(TileEntityFlawlessCapacitor.class, "FlawlessCapacitor");

		// generators
		conductorMast = registerBlock("ConductorMast", new ConductorMast().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityConductorMast.class, "ConductorMast");
		conductormastBlock = registerBlockWithoutTab("ConductorMastBlock", new InvisibleBlock(0).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));	

		weatherStation = registerBlock("WeatherStation", new WeatherStation().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityWeatherStation.class, "WeatherStation");
		weatherStationBlock = registerBlockWithoutTab("WeatherStationBlock", new InvisibleBlock(1).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		
		transmitter = registerBlock("Transmitter", new Transmitter().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityTransmitter.class, "Transmitter");
		transmitterBlock = registerBlockWithoutTab("TransmitterBlock", new InvisibleBlock(2).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		 
		starchextractor = registerBlock("StarchExtractor", new ExtractorBlock(0).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityGenerator.StarchExtractor.class, "StarchExtractor");
		redstoneextractor = registerBlock("RedstoneExtractor", new ExtractorBlock(1).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityGenerator.RedstoneExtractor.class, "RedstoneExtractor");
		glowstoneextractor = registerBlock("GlowstoneExtractor", new ExtractorBlock(2).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityGenerator.GlowstoneExtractor.class, "GlowstoneExtractor");
		handcrankedGenerator = registerBlock("HandCrankedGenerator", new CrankedGenerator().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCrankedGenerator.class, "HandCrankedGenerator");

		crankHandle = registerBlock("CrankHandle", new CrankHandle().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCrankHandle.class, "CrankHandle");
		/* crank = new CrankHandle().setUnlocalizedName("Crank").setCreativeTab(Calculator).setHardness(20.0F); GameRegistry.registerBlock(crank, SonarBlockTip.class, "Crank"); GameRegistry.registerTileEntity(TileEntityCrankHandle.class, "Crank"); */
		magneticFlux = registerBlock("MagneticFlux", new MagneticFlux().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMagneticFlux.class, "MagneticFlux");
		weatherController = registerBlock("WeatherController", new WeatherController().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityWeatherController.class, "WeatherController");
		rainSensor = registerBlock("RainSensor", new RainSensor().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityRainSensor.class, "RainSensor");

		stoneAssimilator = registerBlock("StoneAssimilator", new Assimilator(0).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAssimilator.Stone.class, "StoneAssimilator");
		algorithmAssimilator = registerBlock("AlgorithmAssimilator", new Assimilator(1).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAssimilator.Algorithm.class, "AlgorithmAssimilator");

		// misc

		gas_lantern_off = registerBlock("GasLanternOff", new GasLantern(false).setHardness(0.1F).setLightOpacity(100));
		gas_lantern_on = new GasLantern(true).setHardness(0.1F).setLightLevel(0.9375F).setUnlocalizedName("GasLanternOn").setRegistryName(modid, "GasLanternOn");
		GameRegistry.register(gas_lantern_on);
		GameRegistry.register(new SonarBlockTip(gas_lantern_on).setRegistryName(modid, "GasLanternOn"));
		
		basic_lantern = registerBlock("Lantern", new BasicLantern().setHardness(0.1F).setLightLevel(0.9375F).setLightOpacity(100));
		GameRegistry.registerTileEntity(TileEntityGasLantern.class, "Lantern");
		
		scarecrow = registerBlock("Scarecrow", new Scarecrow().setHardness(0.5F).setResistance(24.0F));
		GameRegistry.registerTileEntity(TileEntityScarecrow.class, "Scarecrow");
		scarecrowBlock = registerBlockWithoutTab("ScarecrowBlock", new ScarecrowBlock().setHardness(0.5F).setResistance(24.0F));
		 
		// amethyst
		amethystLog = registerBlock("AmethystLog", new CalculatorLogs());
		amethystPlanks = registerBlock("AmethystPlanks", new CalculatorPlanks());
		amethystStairs = registerBlock("AmethystStairs", new SonarStairs(amethystPlanks));
		amethystFence = registerBlock("AmethystFence", new BlockFence(Material.WOOD, MapColor.PURPLE));
		amethystLeaves = registerBlock("AmethystLeaves", new CalculatorLeaves(0));
		amethystSapling = registerBlock("AmethystSapling", new CalculatorSaplings(0));

		tanzaniteLog = registerBlock("TanzaniteLog", new CalculatorLogs());
		tanzanitePlanks = registerBlock("TanzanitePlanks", new CalculatorPlanks());
		tanzaniteStairs = registerBlock("TanzaniteStairs", new SonarStairs(tanzanitePlanks));
		tanzaniteFence = registerBlock("TanzaniteFence", new BlockFence(Material.WOOD, MapColor.BLUE));
		tanzaniteLeaves = registerBlock("TanzaniteLeaves", new CalculatorLeaves(1));
		tanzaniteSapling = registerBlock("TanzaniteSapling", new CalculatorSaplings(1));

		pearLog = registerBlock("PearLog", new CalculatorLogs());
		pearPlanks = registerBlock("PearPlanks", new CalculatorPlanks());
		pearStairs = registerBlock("PearStairs", new SonarStairs(pearPlanks));
		pearFence = registerBlock("PearFence", new BlockFence(Material.WOOD, MapColor.BROWN));
		pearLeaves = registerBlock("PearLeaves", new CalculatorLeaves(2));
		pearSapling = registerBlock("PearSapling", new CalculatorSaplings(2));

		diamondLog = registerBlock("DiamondLog", new CalculatorLogs());
		diamondPlanks = registerBlock("DiamondPlanks", new CalculatorPlanks());
		diamondStairs = registerBlock("DiamondStairs", new SonarStairs(diamondPlanks));
		diamondFence = registerBlock("DiamondFence", new BlockFence(Material.WOOD, MapColor.CYAN));
		diamondLeaves = registerBlock("DiamondLeaves", new CalculatorLeaves(3));
		diamondSapling = registerBlock("DiamondSapling", new CalculatorSaplings(3));
		/* calculatorScreen = new CalculatorScreen().setUnlocalizedName("calculatorScreen").setHardness(1.0F).setResistance(20.0F); GameRegistry.registerBlock(calculatorScreen, SonarBlockTip.class, "calculatorScreen"); GameRegistry.registerTileEntity(TileEntityCalculatorScreen.class, "calculatorScreen"); */
		material_block = registerMetaBlock("Material", new MaterialBlock());

		cropBroccoliPlant = registerBlock("CropBroccoli", new CalculatorCrops(0, 0));
		cropPrunaePlant = registerBlock("CropPrunae", new CalculatorCrops(1, 2));
		cropFiddledewPlant = registerBlock("CropFiddledew", new CalculatorCrops(2, 3));
	}
}
