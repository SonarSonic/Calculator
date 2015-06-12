package sonar.calculator.mod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import sonar.calculator.mod.common.block.CalcBlockItem;
import sonar.calculator.mod.common.block.CalculatorLeaves;
import sonar.calculator.mod.common.block.CalculatorLogs;
import sonar.calculator.mod.common.block.CalculatorPlanks;
import sonar.calculator.mod.common.block.CalculatorSaplings;
import sonar.calculator.mod.common.block.CalculatorStairs;
import sonar.calculator.mod.common.block.ConnectedBlock;
import sonar.calculator.mod.common.block.SmeltingBlock;
import sonar.calculator.mod.common.block.calculators.AtomicCalculatorBlock;
import sonar.calculator.mod.common.block.calculators.DynamicCalculatorBlock;
import sonar.calculator.mod.common.block.decoration.AmethystBlock;
import sonar.calculator.mod.common.block.decoration.ElectricBlock;
import sonar.calculator.mod.common.block.decoration.EndBlock;
import sonar.calculator.mod.common.block.decoration.EnrichedBlock;
import sonar.calculator.mod.common.block.decoration.FlawlessBlock;
import sonar.calculator.mod.common.block.decoration.FlawlessFireBlock;
import sonar.calculator.mod.common.block.decoration.ReinforcedBlock;
import sonar.calculator.mod.common.block.decoration.TanzaniteBlock;
import sonar.calculator.mod.common.block.decoration.WeakenedBlock;
import sonar.calculator.mod.common.block.generators.CalculatorLocator;
import sonar.calculator.mod.common.block.generators.CalculatorPlug;
import sonar.calculator.mod.common.block.generators.ConductorMast;
import sonar.calculator.mod.common.block.generators.CrankHandle;
import sonar.calculator.mod.common.block.generators.CrankedGenerator;
import sonar.calculator.mod.common.block.generators.GlowstoneExtractor;
import sonar.calculator.mod.common.block.generators.InvisibleBlock;
import sonar.calculator.mod.common.block.generators.RedstoneExtractor;
import sonar.calculator.mod.common.block.generators.StarchExtractor;
import sonar.calculator.mod.common.block.machines.AdvancedGreenhouse;
import sonar.calculator.mod.common.block.machines.AdvancedPowerCube;
import sonar.calculator.mod.common.block.machines.AnalysingChamber;
import sonar.calculator.mod.common.block.machines.AtomicMultiplier;
import sonar.calculator.mod.common.block.machines.BasicGreenhouse;
import sonar.calculator.mod.common.block.machines.FlawlessGreenhouse;
import sonar.calculator.mod.common.block.machines.HealthProcessor;
import sonar.calculator.mod.common.block.machines.HungerProcessor;
import sonar.calculator.mod.common.block.machines.PowerCube;
import sonar.calculator.mod.common.block.machines.ResearchChamber;
import sonar.calculator.mod.common.block.machines.StorageChamber;
import sonar.calculator.mod.common.block.machines.Transmitter;
import sonar.calculator.mod.common.block.machines.WeatherStation;
import sonar.calculator.mod.common.block.misc.BasicLantern;
import sonar.calculator.mod.common.block.misc.CO2Generator;
import sonar.calculator.mod.common.block.misc.GasLantern;
import sonar.calculator.mod.common.block.misc.ReinforcedDirtBlock;
import sonar.calculator.mod.common.block.misc.ReinforcedStoneBlock;
import sonar.calculator.mod.common.block.misc.Scarecrow;
import sonar.calculator.mod.common.block.misc.ScarecrowBlock;
import sonar.calculator.mod.common.block.misc.StablePort;
import sonar.calculator.mod.common.tileentity.TileEntityMachines;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankedGenerator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.calculator.mod.common.tileentity.machines.TileEntityBasicGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHealthProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHungerProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityTransmitter;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherStation;
import sonar.calculator.mod.common.tileentity.misc.TileEntityBasicLantern;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityGasLantern;
import sonar.calculator.mod.common.tileentity.misc.TileEntityScarecrow;
import sonar.calculator.mod.common.tileentity.misc.TileEntityStablePort;
import cpw.mods.fml.common.registry.GameRegistry;

public class CalculatorBlocks extends Calculator {

	public static void registerBlocks(){
		
	//common blocks	
	reinforcedstoneBlock = new ReinforcedStoneBlock().setBlockName("reinforcedstoneBlock").setCreativeTab(Calculator).setBlockTextureName(modid + ":" + "reinforcedstone");
	GameRegistry.registerBlock(reinforcedstoneBlock, CalcBlockItem.class, "reinforcedstoneBlock");	
	reinforcedstoneBrick = new ReinforcedStoneBlock().setBlockName("reinforcedstoneBrick").setCreativeTab(Calculator).setBlockTextureName(modid + ":" + "reinforcedstonebrick");
	GameRegistry.registerBlock(reinforcedstoneBrick, CalcBlockItem.class, "reinforcedstoneBrick");
	reinforceddirtBlock = new ReinforcedDirtBlock().setBlockName("reinforceddirtBlock").setCreativeTab(Calculator).setBlockTextureName(modid + ":" + "reinforceddirt");
	GameRegistry.registerBlock(reinforceddirtBlock, CalcBlockItem.class, "reinforceddirtBlock");
	reinforceddirtBrick = new ReinforcedDirtBlock().setBlockName("reinforceddirtBrick").setCreativeTab(Calculator).setBlockTextureName(modid + ":" + "reinforceddirtbrick");
	GameRegistry.registerBlock(reinforceddirtBrick, CalcBlockItem.class, "reinforceddirtBrick");
	stablestoneBlock = new ConnectedBlock.Stable().setBlockName("stablestoneBlock").setCreativeTab(Calculator).setHardness(5.0F);
	GameRegistry.registerBlock(stablestoneBlock, CalcBlockItem.class, "stablestoneBlock");
	stablePort = new StablePort().setBlockName("stablePort").setHardness(5.0F).setBlockTextureName(modid + ":" + "stable_port");
	GameRegistry.registerBlock(stablePort, "stablePort");
	GameRegistry.registerTileEntity(TileEntityStablePort.class, "stablePort");
	purifiedobsidianBlock = new ConnectedBlock(Material.rock, "purifiedobsidian",2).setBlockName("purifiedobsidianBlock").setCreativeTab(Calculator);
	GameRegistry.registerBlock(purifiedobsidianBlock, CalcBlockItem.class, "purifiedobsidianBlock");
	flawlessGlass = new ConnectedBlock(Material.glass,"flawlessglass",1).setBlockName("FlawlessGlass").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(5.0F);;
	GameRegistry.registerBlock(flawlessGlass, CalcBlockItem.class, "FlawlessGlass");
	
	//calculators
	powerCube = new PowerCube().setBlockName("PCubeIdle").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(powerCube, CalcBlockItem.class, "PCubeIdle");
	GameRegistry.registerTileEntity(TileEntityPowerCube.class, "PCubeIdle");
	
	advancedPowerCube = new AdvancedPowerCube().setBlockName("AdvancedPCubeIdle").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(advancedPowerCube, CalcBlockItem.class, "AdvancedPCubeIdle");
	GameRegistry.registerTileEntity(TileEntityAdvancedPowerCube.class, "AdvancedPCubeIdle");
	//dockingStation = new DockingStation().setBlockName("DockingStation").setCreativeTab(Calculator).setHardness(1.5F);
	//GameRegistry.registerBlock(dockingStation, CalcBlockItem.class, "DockingStation").setBlockTextureName(modid + ":" + "reinforcedstone");
	//GameRegistry.registerTileEntity(TileEntityDockingStation.class, "DockingStation");
	 
	atomiccalculatorBlock = new AtomicCalculatorBlock().setBlockName("atomiccalculatorBlock").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(atomiccalculatorBlock, CalcBlockItem.class, "atomiccalculatorBlock");
	GameRegistry.registerTileEntity(TileEntityCalculator.Atomic.class, "atomiccalculatorBlock");
	dynamiccalculatorBlock = new DynamicCalculatorBlock().setBlockName("dynamiccalculatorBlock").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(dynamiccalculatorBlock, CalcBlockItem.class, "dynamiccalculatorBlock");
	GameRegistry.registerTileEntity(TileEntityCalculator.Dynamic.class, "dynamiccalculatorBlock");
	
	//smelting
	reinforcedFurnace = new SmeltingBlock(7).setBlockName("ReinforcedFurnace").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(reinforcedFurnace,CalcBlockItem.class, "ReinforcedFurnace");
	GameRegistry.registerTileEntity(TileEntityMachines.ReinforcedFurnace.class, "ReinforcedFurnace");
	stoneSeperator = new SmeltingBlock(4).setBlockName("StoneSeperatorIdle").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(stoneSeperator,CalcBlockItem.class, "StoneSeperatorIdle");
	GameRegistry.registerTileEntity(TileEntityMachines.StoneSeperator.class, "StoneSeperatorIdle");		
	algorithmSeperator = new SmeltingBlock(5).setBlockName("AlgorithmSeperatorIdle").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(algorithmSeperator,CalcBlockItem.class, "AlgorithmSeperatorIdle");
	GameRegistry.registerTileEntity(TileEntityMachines.AlgorithmSeperator.class, "AlgorithmSeperatorIdle");
	extractionChamber = new SmeltingBlock(0).setBlockName("ExtractionChamber").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(extractionChamber,CalcBlockItem.class, "ExtractionChamber");
	GameRegistry.registerTileEntity(TileEntityMachines.ExtractionChamber.class, "ExtractionChamber");
	restorationChamber = new SmeltingBlock(1).setBlockName("RestorationChamber").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(restorationChamber,CalcBlockItem.class, "RestorationChamber");
	GameRegistry.registerTileEntity(TileEntityMachines.RestorationChamber.class, "RestorationChamber");
	reassemblyChamber = new SmeltingBlock(2).setBlockName("ReassemblyChamber").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(reassemblyChamber,CalcBlockItem.class, "ReassemblyChamber");
	GameRegistry.registerTileEntity(TileEntityMachines.ReassemblyChamber.class, "ReassemblyChamber");
	processingChamber = new SmeltingBlock(3).setBlockName("ProcessingChamber").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(processingChamber,CalcBlockItem.class, "ProcessingChamber");
	GameRegistry.registerTileEntity(TileEntityMachines.ProcessingChamber.class, "ProcessingChamber");
	precisionChamber = new SmeltingBlock(6).setBlockName("PrecisionChamber").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(precisionChamber,CalcBlockItem.class, "PrecisionChamber");
	GameRegistry.registerTileEntity(TileEntityMachines.PrecisionChamber.class, "PrecisionChamber");
	storageChamber = new StorageChamber().setBlockName("StorageChamber").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);
	GameRegistry.registerBlock(storageChamber, CalcBlockItem.class, "StorageChamber");
	GameRegistry.registerTileEntity(TileEntityStorageChamber.class, "StorageChamber");
	researchChamber = new ResearchChamber().setBlockName("ResearchChamber").setCreativeTab(Calculator).setHardness(1.5F).setBlockTextureName(modid + ":" + "stablestone");
	GameRegistry.registerBlock(researchChamber, CalcBlockItem.class, "ResearchChamber");
	GameRegistry.registerTileEntity(TileEntityResearchChamber.class, "ResearchChamber");
	//manipulationChamber = new ManipulationChamber().setBlockName("ManipulationChamber").setCreativeTab(Calculator).setHardness(1.5F);
	//GameRegistry.registerBlock(manipulationChamber, CalcBlockItem.class, "ManipulationChamber");
	//GameRegistry.registerTileEntity(TileEntityManipulationChamber.class, "ManipulationChamber");
	
	//machines
	analysingChamber = new AnalysingChamber().setBlockName("AnalysingChamber").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(analysingChamber,CalcBlockItem.class, "AnalysingChamber");
	GameRegistry.registerTileEntity(TileEntityAnalysingChamber.class, "AnalysingChamber");
	basicGreenhouse = new BasicGreenhouse().setBlockName("BasicGreenhouse").setCreativeTab(Calculator).setHardness(1.5F).setResistance(24.0F);
	GameRegistry.registerBlock(basicGreenhouse,CalcBlockItem.class, "BasicGreenhouse");
	GameRegistry.registerTileEntity(TileEntityBasicGreenhouse.class, "BasicGreenhouse");
	advancedGreenhouse = new AdvancedGreenhouse().setBlockName("AdvancedGreenhouse").setCreativeTab(Calculator).setHardness(1.5F).setResistance(24.0F);
	GameRegistry.registerBlock(advancedGreenhouse,CalcBlockItem.class, "AdvancedGreenhouse");
	GameRegistry.registerTileEntity(TileEntityAdvancedGreenhouse.class, "AdvancedGreenhouse");
	flawlessGreenhouse = new FlawlessGreenhouse().setBlockName("FlawlessGreenhouse").setCreativeTab(Calculator).setHardness(1.5F).setResistance(24.0F);
	GameRegistry.registerBlock(flawlessGreenhouse,CalcBlockItem.class, "FlawlessGreenhouse");
	GameRegistry.registerTileEntity(TileEntityFlawlessGreenhouse.class, "FlawlessGreenhouse");
	hungerprocessor = new HungerProcessor().setBlockName("HungerProcessor").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(hungerprocessor,CalcBlockItem.class, "HungerProcessor");
	GameRegistry.registerTileEntity(TileEntityHungerProcessor.class, "HungerProcessor");
	healthprocessor = new HealthProcessor().setBlockName("HealthProcessor").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(healthprocessor,CalcBlockItem.class, "HealthProcessor");
	GameRegistry.registerTileEntity(TileEntityHealthProcessor.class, "HealthProcessor");
	carbondioxideGenerator= new CO2Generator().setBlockName("CO2Generator").setCreativeTab(Calculator).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(carbondioxideGenerator,CalcBlockItem.class, "CO2Generator");
	GameRegistry.registerTileEntity(TileEntityCO2Generator.class, "CO2Generator");
	atomicMultiplier = new AtomicMultiplier().setBlockName("AtomicMultiplier").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(6.5F).setBlockTextureName(modid + ":" + "stablestone").setResistance(5.0F);;
	GameRegistry.registerBlock(atomicMultiplier,CalcBlockItem.class, "AtomicMultiplier");
	GameRegistry.registerTileEntity(TileEntityAtomicMultiplier.class, "AtomicMultiplier");
	
	//energy
	//flawlessCapacitor = new FlawlessCapacitor().setBlockName("FlawlessCapacitor").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(6.5F).setBlockTextureName(modid + ":" + "electric_diamond_block");
	//GameRegistry.registerBlock(flawlessCapacitor,CalcBlockItem.class, "FlawlessCapacitor");
	//GameRegistry.registerTileEntity(TileEntityFlawlessCapacitor.class, "FlawlessCapacitor");
	
	//generators
	conductorMast = new ConductorMast().setBlockName("ConductorMast").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.5F).setResistance(5.0F);;
	GameRegistry.registerBlock(conductorMast,CalcBlockItem.class, "ConductorMast");
	GameRegistry.registerTileEntity(TileEntityConductorMast.class, "ConductorMast");
	conductormastBlock = new InvisibleBlock(0).setBlockName("ConductorMastBlock").setLightLevel(0.625F).setHardness(1.5F).setBlockTextureName(modid + ":" + "stablestone").setResistance(5.0F);;
	GameRegistry.registerBlock(conductormastBlock, CalcBlockItem.class, "ConductorMastBlock");
	weatherStation = new WeatherStation().setBlockName("WeatherStation").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.5F).setBlockTextureName(modid + ":" + "stablestone").setResistance(5.0F);;
	GameRegistry.registerBlock(weatherStation,CalcBlockItem.class, "WeatherStation");
	GameRegistry.registerTileEntity(TileEntityWeatherStation.class, "WeatherStation");
	weatherStationBlock = new InvisibleBlock(1).setBlockName("WeatherStationBlock").setLightLevel(0.625F).setHardness(1.5F).setBlockTextureName(modid + ":" + "stablestone").setResistance(5.0F);;
	GameRegistry.registerBlock(weatherStationBlock, CalcBlockItem.class, "WeatherStationBlock");
	transmitter = new Transmitter().setBlockName("Transmitter").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.5F).setBlockTextureName(modid + ":" + "stablestone").setResistance(5.0F);;
	GameRegistry.registerBlock(transmitter,CalcBlockItem.class, "Transmitter");
	GameRegistry.registerTileEntity(TileEntityTransmitter.class, "Transmitter");
	transmitterBlock = new InvisibleBlock(2).setBlockName("TransmitterBlock").setLightLevel(0.625F).setHardness(1.5F).setBlockTextureName(modid + ":" + "stablestone").setResistance(5.0F);;
	GameRegistry.registerBlock(transmitterBlock, CalcBlockItem.class, "TransmitterBlock");
	calculatorlocator = new CalculatorLocator().setBlockName("CalculatorLocator").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(6.5F).setBlockTextureName(modid + ":" + "calculatorplug").setResistance(5.0F);;
	GameRegistry.registerBlock(calculatorlocator,CalcBlockItem.class, "CalculatorLocator");
	GameRegistry.registerTileEntity(TileEntityCalculatorLocator.class, "CalculatorLocator");
	calculatorplug = new CalculatorPlug().setBlockName("CalculatorPlug").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(6.5F).setBlockTextureName(modid + ":" + "calculatorplug").setResistance(5.0F);;
	GameRegistry.registerBlock(calculatorplug, CalcBlockItem.class, "CalculatorPlug");
	GameRegistry.registerTileEntity(TileEntityCalculatorPlug.class, "CalculatorPlug");
	starchextractor = new StarchExtractor().setBlockName("starchextractor").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.5F).setBlockTextureName(modid + ":" + "starchextractor").setResistance(5.0F);;
	GameRegistry.registerBlock(starchextractor,CalcBlockItem.class, "starchextractor");
	GameRegistry.registerTileEntity(TileEntityGenerator.StarchExtractor.class, "starchextractor");
	redstoneextractor = new RedstoneExtractor().setBlockName("redstoneextractor").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.5F).setBlockTextureName(modid + ":" + "redstoneextractor").setResistance(5.0F);;
	GameRegistry.registerBlock(redstoneextractor,CalcBlockItem.class, "redstoneextractor");
	GameRegistry.registerTileEntity(TileEntityGenerator.RedstoneExtractor.class, "redstoneextractor");
	glowstoneextractor = new GlowstoneExtractor().setBlockName("glowstoneextractor").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.5F).setBlockTextureName(modid + ":" + "glowstoneextractor").setResistance(5.0F);;
	GameRegistry.registerBlock(glowstoneextractor,CalcBlockItem.class, "glowstoneextractor");
	GameRegistry.registerTileEntity(TileEntityGenerator.GlowstoneExtractor.class, "glowstoneextractor");
	handcrankedGenerator = new CrankedGenerator().setBlockName("CrankedGenerator").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.5F).setBlockTextureName(modid + ":" + "handcrankedGenerator").setResistance(2.0F);;
	GameRegistry.registerBlock(handcrankedGenerator,CalcBlockItem.class, "CrankedGenerator");
	GameRegistry.registerTileEntity(TileEntityCrankedGenerator.class, "CrankedGenerator");
	crank = new CrankHandle().setBlockName("Crank").setCreativeTab(Calculator).setHardness(6.5F);
	GameRegistry.registerBlock(crank, CalcBlockItem.class, "Crank");
	GameRegistry.registerTileEntity(TileEntityCrankHandle.class, "Crank");
	
	//misc
	gas_lantern_on = new GasLantern(true).setBlockName("LanternOn").setHardness(0.1F).setLightLevel(0.9375F).setLightOpacity(100);
	GameRegistry.registerBlock(gas_lantern_on, CalcBlockItem.class, "LanternOn");
	basic_lantern = new BasicLantern().setBlockName("LanternBasic").setCreativeTab(Calculator).setHardness(0.1F).setLightLevel(0.9375F).setLightOpacity(100);
	GameRegistry.registerBlock(basic_lantern, CalcBlockItem.class, "LanternBasic");
	GameRegistry.registerTileEntity(TileEntityBasicLantern.class, "LanternBasic");
	gas_lantern_off= new GasLantern(false).setBlockName("LanternOff").setCreativeTab(Calculator).setHardness(0.1F);
	GameRegistry.registerBlock(gas_lantern_off, CalcBlockItem.class, "LanternOff");
	GameRegistry.registerTileEntity(TileEntityGasLantern.class, "Lantern");
	scarecrow = new Scarecrow().setBlockName("Scarecrow").setCreativeTab(Calculator).setHardness(0.5F).setResistance(24.0F).setBlockTextureName(modid + ":" + "reinforcedstone");
	GameRegistry.registerBlock(scarecrow, CalcBlockItem.class, "Scarecrow");
	GameRegistry.registerTileEntity(TileEntityScarecrow.class, "Scarecrow");
	scarecrowBlock = new ScarecrowBlock().setBlockName("ScarecrowBlock").setHardness(0.5F).setBlockTextureName(modid + ":" + "reinforcedstone");
	GameRegistry.registerBlock(scarecrowBlock, CalcBlockItem.class, "ScarecrowBlock");

	//amethyst
	amethystLog = new CalculatorLogs(0).setBlockName("AmethystLog").setCreativeTab(Calculator);
	GameRegistry.registerBlock(amethystLog, CalcBlockItem.class, "AmethystLog");
	amethystPlanks = new CalculatorPlanks(0).setBlockName("AmethystPlanks").setCreativeTab(Calculator);
	GameRegistry.registerBlock(amethystPlanks, CalcBlockItem.class, "AmethystPlanks");
	amethystStairs = new CalculatorStairs(amethystPlanks, 0).setBlockName("AmethystStairs").setCreativeTab(Calculator);
	GameRegistry.registerBlock(amethystStairs, CalcBlockItem.class, "AmethystStairs");
	amethystLeaf = new CalculatorLeaves(0).setBlockName("AmethystLeaf").setCreativeTab(Calculator);
	GameRegistry.registerBlock(amethystLeaf, CalcBlockItem.class, "AmethystLeaf");
	AmethystSapling = new CalculatorSaplings(0).setBlockName("AmethystSapling").setCreativeTab(Calculator);
	GameRegistry.registerBlock(AmethystSapling, CalcBlockItem.class, "AmethystSapling");
	
	//tanzanite
	tanzaniteLog = new CalculatorLogs(1).setBlockName("TanzaniteLog").setCreativeTab(Calculator);
	GameRegistry.registerBlock(tanzaniteLog, CalcBlockItem.class, "TanzaniteLog");
	tanzanitePlanks = new CalculatorPlanks(1).setBlockName("TanzanitePlanks").setCreativeTab(Calculator);
	GameRegistry.registerBlock(tanzanitePlanks, CalcBlockItem.class, "TanzanitePlanks");
	tanzaniteStairs = new CalculatorStairs(tanzanitePlanks, 0).setBlockName("TanzaniteStairs").setCreativeTab(Calculator);
	GameRegistry.registerBlock(tanzaniteStairs, CalcBlockItem.class, "TanzaniteStairs");
	tanzaniteLeaf = new CalculatorLeaves(1).setBlockName("TanzaniteLeaf").setCreativeTab(Calculator);
	GameRegistry.registerBlock(tanzaniteLeaf, CalcBlockItem.class, "TanzaniteLeaf");
	tanzaniteSapling = new CalculatorSaplings(1).setBlockName("TanzaniteSapling").setCreativeTab(Calculator);
	GameRegistry.registerBlock(tanzaniteSapling, CalcBlockItem.class, "TanzaniteSapling");
	
	//pear
	pearLog = new CalculatorLogs(2).setBlockName("PearLog").setCreativeTab(Calculator);		
	GameRegistry.registerBlock(pearLog, CalcBlockItem.class, "PearLog");
	pearPlanks = new CalculatorPlanks(2).setBlockName("PearPlanks").setCreativeTab(Calculator);		
	GameRegistry.registerBlock(pearPlanks, CalcBlockItem.class, "PearPlanks");
	pearStairs = new CalculatorStairs(pearPlanks, 0).setBlockName("PearStairs").setCreativeTab(Calculator);		
	GameRegistry.registerBlock(pearStairs, CalcBlockItem.class, "PearStairs");
	pearLeaf = new CalculatorLeaves(2).setBlockName("PearLeaf").setCreativeTab(Calculator);
	GameRegistry.registerBlock(pearLeaf, CalcBlockItem.class, "PearLeaf");
	leaves = new CalculatorLeaves(4).setBlockName("Leaves");
	GameRegistry.registerBlock(leaves, CalcBlockItem.class, "Leaves");
	PearSapling = new CalculatorSaplings(2).setBlockName("PearSapling").setCreativeTab(Calculator);
	GameRegistry.registerBlock(PearSapling, CalcBlockItem.class, "PearSapling");
	
	//diamond
	diamondLog = new CalculatorLogs(3).setBlockName("DiamondLog").setCreativeTab(Calculator);		
	GameRegistry.registerBlock(diamondLog, CalcBlockItem.class, "DiamondLog");
	diamondPlanks = new CalculatorPlanks(3).setBlockName("DiamondPlanks").setCreativeTab(Calculator);		
	GameRegistry.registerBlock(diamondPlanks, CalcBlockItem.class, "DiamondPlanks");
	diamondStairs = new CalculatorStairs(diamondPlanks, 0).setBlockName("DiamondStairs").setCreativeTab(Calculator);		
	GameRegistry.registerBlock(diamondStairs, CalcBlockItem.class, "DiamondStairs");
	diamondLeaf = new CalculatorLeaves(3).setBlockName("DiamondLeaf").setCreativeTab(Calculator);
	GameRegistry.registerBlock(diamondLeaf, CalcBlockItem.class, "DiamondLeaf");
	diamondleaves = new CalculatorLeaves(5).setBlockName("DiamondLeaves");
	GameRegistry.registerBlock(diamondleaves, CalcBlockItem.class, "DiamondLeaves");
	diamondSapling = new CalculatorSaplings(3).setBlockName("DiamondSapling").setCreativeTab(Calculator);
	GameRegistry.registerBlock(diamondSapling, CalcBlockItem.class, "DiamondSapling");
	
	//decoration blocks
	amethyst_block = new AmethystBlock();
	GameRegistry.registerBlock(amethyst_block, CalcBlockItem.class, "AmethystBlock").setResistance(20.0F);
	tanzanite_block = new TanzaniteBlock();
	GameRegistry.registerBlock(tanzanite_block, CalcBlockItem.class, "TanzaniteBlock").setResistance(20.0F);
	enriched_gold_block = new EnrichedBlock();
	GameRegistry.registerBlock(enriched_gold_block, CalcBlockItem.class, "EnrichedBlock").setResistance(20.0F);
	reinforced_iron_block = new ReinforcedBlock();
	GameRegistry.registerBlock(reinforced_iron_block, CalcBlockItem.class, "ReinforcedBlock").setResistance(20.0F);
	weakened_diamond_block = new WeakenedBlock();
	GameRegistry.registerBlock(weakened_diamond_block, CalcBlockItem.class, "WeakenedBlock").setResistance(20.0F);
	flawless_block = new FlawlessBlock();
	GameRegistry.registerBlock(flawless_block, CalcBlockItem.class, "FlawlessBlock").setResistance(20.0F);
	flawless_fire_block = new FlawlessFireBlock();
	GameRegistry.registerBlock(flawless_fire_block, CalcBlockItem.class, "FlawlessFireBlock").setResistance(20.0F);
	electric_diamond_block = new ElectricBlock();
	GameRegistry.registerBlock(electric_diamond_block, CalcBlockItem.class, "ElectricBlock").setResistance(20.0F);
	end_diamond_block = new EndBlock();
	GameRegistry.registerBlock(end_diamond_block, CalcBlockItem.class, "EndBlock").setResistance(20.0F);
	

	
	}
	
	
}
