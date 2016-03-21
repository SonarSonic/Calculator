package sonar.calculator.mod;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sonar.calculator.mod.common.block.CalculatorLeaves;
import sonar.calculator.mod.common.block.CalculatorLogs;
import sonar.calculator.mod.common.block.CalculatorPlanks;
import sonar.calculator.mod.common.block.CalculatorSaplings;
import sonar.calculator.mod.common.block.CalculatorStairs;
import sonar.calculator.mod.common.block.ConnectedBlock;
import sonar.calculator.mod.common.block.ItemMetaBlock;
import sonar.calculator.mod.common.block.SmeltingBlock;
import sonar.calculator.mod.common.block.SmeltingBlock.BlockTypes;
import sonar.calculator.mod.common.block.StableStone;
import sonar.calculator.mod.common.block.calculators.AtomicCalculator;
import sonar.calculator.mod.common.block.calculators.DynamicCalculator;
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
import sonar.calculator.mod.common.block.generators.ExtractorBlock;
import sonar.calculator.mod.common.block.generators.InvisibleBlock;
import sonar.calculator.mod.common.block.machines.AdvancedGreenhouse;
import sonar.calculator.mod.common.block.machines.AdvancedPowerCube;
import sonar.calculator.mod.common.block.machines.AnalysingChamber;
import sonar.calculator.mod.common.block.machines.Assimilator;
import sonar.calculator.mod.common.block.machines.AtomicMultiplier;
import sonar.calculator.mod.common.block.machines.BasicGreenhouse;
import sonar.calculator.mod.common.block.machines.DockingStation;
import sonar.calculator.mod.common.block.machines.FlawlessGreenhouse;
import sonar.calculator.mod.common.block.machines.HealthProcessor;
import sonar.calculator.mod.common.block.machines.HungerProcessor;
import sonar.calculator.mod.common.block.machines.PowerCube;
import sonar.calculator.mod.common.block.machines.ResearchChamber;
import sonar.calculator.mod.common.block.machines.StorageChamber;
import sonar.calculator.mod.common.block.machines.Teleporter;
import sonar.calculator.mod.common.block.machines.Transmitter;
import sonar.calculator.mod.common.block.machines.WeatherStation;
import sonar.calculator.mod.common.block.misc.BasicLantern;
import sonar.calculator.mod.common.block.misc.CO2Generator;
import sonar.calculator.mod.common.block.misc.CalculatorScreen;
import sonar.calculator.mod.common.block.misc.FluxController;
import sonar.calculator.mod.common.block.misc.FluxPlug;
import sonar.calculator.mod.common.block.misc.FluxPoint;
import sonar.calculator.mod.common.block.misc.GasLantern;
import sonar.calculator.mod.common.block.misc.MagneticFlux;
import sonar.calculator.mod.common.block.misc.RainSensor;
import sonar.calculator.mod.common.block.misc.Scarecrow;
import sonar.calculator.mod.common.block.misc.ScarecrowBlock;
import sonar.calculator.mod.common.block.misc.WeatherController;
import sonar.calculator.mod.common.item.misc.Soil;
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
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.calculator.mod.common.tileentity.machines.TileEntityBasicGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityDockingStation;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHealthProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHungerProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityTransmitter;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherController;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherStation;
import sonar.calculator.mod.common.tileentity.misc.TileEntityBasicLantern;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculatorScreen;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPlug;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPoint;
import sonar.calculator.mod.common.tileentity.misc.TileEntityGasLantern;
import sonar.calculator.mod.common.tileentity.misc.TileEntityMagneticFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityRainSensor;
import sonar.calculator.mod.common.tileentity.misc.TileEntityScarecrow;
import sonar.calculator.mod.common.tileentity.misc.TileEntityTeleporter;
import sonar.core.common.block.BlockBase;
import sonar.core.common.block.SonarBlockTip;

public class CalculatorBlocks extends Calculator {

	public static ArrayList<Block> registeredBlocks = new ArrayList();
	
	public static Block registerBlock(String name, Block block) {
		block.setCreativeTab(Calculator);
		GameRegistry.registerBlock(block.setUnlocalizedName(name), SonarBlockTip.class, name);
		registeredBlocks.add(block);
		return block;
	}	
	public static void registerBlocks() {
		
		// common blocks
		reinforcedStoneBlock = registerBlock("ReinforcedStoneBlock",new BlockBase(Material.rock, 2.0f, 10.0f));
		reinforcedStoneStairs = registerBlock("ReinforcedStoneStairs",new CalculatorStairs(reinforcedStoneBlock));
		reinforcedStoneBrick = registerBlock("ReinforcedStoneBrick",new BlockBase(Material.rock, 2.0f, 10.0f));
		reinforcedStoneBrickStairs = registerBlock("ReinforcedStoneBrickStairs",new CalculatorStairs(reinforcedStoneBrick));
		reinforcedDirtBlock = registerBlock("ReinforcedDirtBlock",new BlockBase(Material.ground, 1.0f, 4.0f));
		reinforcedDirtStairs = registerBlock("ReinforcedDirtStairs",new CalculatorStairs(reinforcedDirtBlock));
		reinforcedDirtBrick = registerBlock("ReinforcedDirtBrick",new BlockBase(Material.ground, 1.0f, 4.0f));
		reinforcedDirtBrickStairs = registerBlock("ReinforcedDirtBrickStairs",new CalculatorStairs(reinforcedDirtBrick));
		/*	
		reinforcedStoneBrickFence = new BlockFence("Calculator:reinforcedstonebrick", Material.rock).setBlockName("ReinforcedStoneBrickFence").setCreativeTab(Calculator);
		GameRegistry.registerBlock(reinforcedStoneBrickFence, SonarBlockTip.class, "ReinforcedStoneBrickFence");	
		*/
		/*	
		reinforcedDirtFence = new BlockFence("Calculator:reinforceddirt", Material.ground).setBlockName("ReinforcedDirtFence").setCreativeTab(Calculator);
		GameRegistry.registerBlock(reinforcedDirtFence, SonarBlockTip.class, "ReinforcedDirtFence");
		*/
		/*
		reinforcedDirtBrickFence = new BlockFence("Calculator:reinforceddirtbrick", Material.ground).setBlockName("ReinforcedDirtBrickFence").setCreativeTab(Calculator);
		GameRegistry.registerBlock(reinforcedDirtBrickFence, SonarBlockTip.class, "ReinforcedDirtBrickFence");
		*/
		
		stableStone = new StableStone(Material.rock).setUnlocalizedName("StableStone").setCreativeTab(Calculator).setHardness(2.0F);
		GameRegistry.registerBlock(stableStone, SonarBlockTip.class, "StableStone");
		/*
		stablestonerimmedBlock = new ConnectedBlock.StableRimmed().setUnlocalizedName("stablestonerimmedBlock").setCreativeTab(Calculator).setHardness(2.0F);
		GameRegistry.registerBlock(stablestonerimmedBlock, ItemMetaBlock.class, "stablestonerimmedBlock");
		stablestonerimmedblackBlock = new ConnectedBlock.StableBlackRimmed().setUnlocalizedName("stablestonerimmedblackBlock").setCreativeTab(Calculator).setHardness(2.0F);
		GameRegistry.registerBlock(stablestonerimmedblackBlock, ItemMetaBlock.class, "stablestonerimmedblackBlock");
		stableglassBlock = new ConnectedBlock.StableGlass("stablestone_glass", 3).setBlockName("StableGlass").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(0.6F);
		GameRegistry.registerBlock(stableglassBlock, SonarBlockTip.class, "StableGlass");
		clearstableglassBlock = new ConnectedBlock.StableGlass("stablestone_clear", 4).setBlockName("ClearStableGlass").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(0.6F);
		GameRegistry.registerBlock(clearstableglassBlock, SonarBlockTip.class, "ClearStableGlass");
		flawlessGlass = new ConnectedBlock(Material.glass, "flawlessglass", 1, false).setBlockName("FlawlessGlass").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(0.6F);
		GameRegistry.registerBlock(flawlessGlass, SonarBlockTip.class, "FlawlessGlass");
		purifiedobsidianBlock = new ConnectedBlock.PurifiedObsidian().setUnlocalizedName("purifiedobsidianBlock").setCreativeTab(Calculator);
		GameRegistry.registerBlock(purifiedobsidianBlock, SonarBlockTip.class, "purifiedobsidianBlock");
	*/
		// calculators
		powerCube = registerBlock("PowerCube",new PowerCube().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityPowerCube.class, "PowerCube");	
		advancedPowerCube = registerBlock("AdvancedPowerCube",new AdvancedPowerCube().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAdvancedPowerCube.class, "AdvancedPowerCube");
		atomicCalculator = registerBlock("AtomicCalculator",new AtomicCalculator().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCalculator.Atomic.class, "AtomicCalculator");
		dynamicCalculator = registerBlock("DynamicCalculator",new DynamicCalculator().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCalculator.Dynamic.class, "DynamicCalculator");	

		// smelting
		reinforcedFurnace = registerBlock("ReinforcedFurnace",new SmeltingBlock(BlockTypes.FURNACE).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachines.ReinforcedFurnace.class, "ReinforcedFurnace");
		//flawlessFurnace = new FlawlessFurnace().setUnlocalizedName("FlawlessFurnace").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		//GameRegistry.registerBlock(flawlessFurnace, SonarBlockTip.class, "FlawlessFurnace");
		//GameRegistry.registerTileEntity(TileEntityFlawlessFurnace.class, "FlawlessFurnace");

		stoneSeparator = registerBlock("StoneSeparator",new SmeltingBlock(BlockTypes.STONE).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachines.StoneSeperator.class, "StoneSeparator");
		algorithmSeparator = registerBlock("AlgorithmSeparator",new SmeltingBlock(BlockTypes.ALGORITHM).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachines.AlgorithmSeperator.class, "AlgorithmSeparator");
		hungerProcessor = registerBlock("HungerProcessor",new HungerProcessor().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityHungerProcessor.class, "HungerProcessor");
		healthProcessor = registerBlock("HealthProcessor",new HealthProcessor().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityHealthProcessor.class, "HealthProcessor");
		/*
		dockingStation = new DockingStation().setUnlocalizedName("DockingStation").setCreativeTab(Calculator).setHardness(1.5F);
		GameRegistry.registerBlock(dockingStation, SonarBlockTip.class, "DockingStation").setBlockTextureName(modid + ":" + "reinforcedstone");
		GameRegistry.registerTileEntity(TileEntityDockingStation.class, "DockingStation");
		*/
		atomicMultiplier = registerBlock("AtomicMultiplier",new AtomicMultiplier().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAtomicMultiplier.class, "AtomicMultiplier");		
		/*
		extractionChamber = new SmeltingBlock(0).setBlockName("ExtractionChamber").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(extractionChamber, SonarBlockTip.class, "ExtractionChamber");
		GameRegistry.registerTileEntity(TileEntityMachines.ExtractionChamber.class, "ExtractionChamber");
		restorationChamber = new SmeltingBlock(1).setBlockName("RestorationChamber").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(restorationChamber, SonarBlockTip.class, "RestorationChamber");
		GameRegistry.registerTileEntity(TileEntityMachines.RestorationChamber.class, "RestorationChamber");
		reassemblyChamber = new SmeltingBlock(2).setBlockName("ReassemblyChamber").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(reassemblyChamber, SonarBlockTip.class, "ReassemblyChamber");
		GameRegistry.registerTileEntity(TileEntityMachines.ReassemblyChamber.class, "ReassemblyChamber");
		precisionChamber = new SmeltingBlock(6).setBlockName("PrecisionChamber").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(precisionChamber, SonarBlockTip.class, "PrecisionChamber");
		GameRegistry.registerTileEntity(TileEntityMachines.PrecisionChamber.class, "PrecisionChamber");
		processingChamber = new SmeltingBlock(3).setBlockName("ProcessingChamber").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(processingChamber, SonarBlockTip.class, "ProcessingChamber");
		GameRegistry.registerTileEntity(TileEntityMachines.ProcessingChamber.class, "ProcessingChamber");
		analysingChamber = new AnalysingChamber().setUnlocalizedName("AnalysingChamber").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(analysingChamber, SonarBlockTip.class, "AnalysingChamber");
		GameRegistry.registerTileEntity(TileEntityAnalysingChamber.class, "AnalysingChamber");
		*/

		storageChamber = registerBlock("StorageChamber",new StorageChamber().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityStorageChamber.class, "StorageChamber");
		/*
		researchChamber = new ResearchChamber().setUnlocalizedName("ResearchChamber").setHardness(1.0F).setBlockTextureName(modid + ":" + "stablestone").setResistance(20.0F);
		GameRegistry.registerBlock(researchChamber, SonarBlockTip.class, "ResearchChamber");
		GameRegistry.registerTileEntity(TileEntityResearchChamber.class, "ResearchChamber");
		// manipulationChamber = new ManipulationChamber().setUnlocalizedName("ManipulationChamber").setCreativeTab(Calculator).setHardness(1.5F);
		// GameRegistry.registerBlock(manipulationChamber, CalcBlockItem.class, "ManipulationChamber");
		// GameRegistry.registerTileEntity(TileEntityManipulationChamber.class, "ManipulationChamber");
	*/
		// machines

		basicGreenhouse = registerBlock("BasicGreenhouse",new BasicGreenhouse().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityBasicGreenhouse.class, "BasicGreenhouse");
		advancedGreenhouse = registerBlock("AdvancedGreenhouse",new AdvancedGreenhouse().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAdvancedGreenhouse.class, "AdvancedGreenhouse");
		flawlessGreenhouse = registerBlock("FlawlessGreenhouse",new FlawlessGreenhouse().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityFlawlessGreenhouse.class, "FlawlessGreenhouse");
		CO2Generator = registerBlock("CO2Generator",new CO2Generator().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCO2Generator.class, "CO2Generator");
		/*
		fluxPlug = new FluxPlug().setUnlocalizedName("FluxPlug").setCreativeTab(Calculator).setHardness(0.2F).setResistance(20.0F).setBlockTextureName(modid + ":" + "stablestone");
		GameRegistry.registerBlock(fluxPlug, SonarBlockTip.class, "FluxPlug");
		GameRegistry.registerTileEntity(TileEntityFluxPlug.class, "FluxPlug");
		fluxPoint = new FluxPoint().setUnlocalizedName("FluxPoint").setCreativeTab(Calculator).setHardness(0.2F).setResistance(20.0F).setBlockTextureName(modid + ":" + "stablestone");
		GameRegistry.registerBlock(fluxPoint, SonarBlockTip.class, "FluxPoint");
		GameRegistry.registerTileEntity(TileEntityFluxPoint.class, "FluxPoint");
		fluxController = new FluxController().setUnlocalizedName("FluxController").setCreativeTab(Calculator).setResistance(20.0F).setBlockTextureName(modid + ":" + "stablestone").setHardness(1.5F).setLightLevel(0.9375F).setLightOpacity(100);
		GameRegistry.registerBlock(fluxController, SonarBlockTip.class, "FluxController");
		GameRegistry.registerTileEntity(TileEntityFluxController.class, "FluxController");
		teleporter = new Teleporter().setUnlocalizedName("CalculatorTeleporter").setCreativeTab(Calculator).setHardness(1.0F).setLightLevel(0.625F).setBlockTextureName(modid + ":" + "stablestone");
		GameRegistry.registerBlock(teleporter, SonarBlockTip.class, "CalculatorTeleporter");
		GameRegistry.registerTileEntity(TileEntityTeleporter.class, "CalculatorTeleporter");
		*/
		calculatorlocator = registerBlock("CalculatorLocator",new CalculatorLocator().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCalculatorLocator.class, "CalculatorLocator");		
		calculatorplug = registerBlock("CalculatorPlug",new CalculatorPlug().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCalculatorPlug.class, "CalculatorPlug");
	/*
		// energy
		 //flawlessCapacitor = new FlawlessCapacitor().setUnlocalizedName("FlawlessCapacitor").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(6.5F).setBlockTextureName(modid + ":" +
		// "electric_diamond_block");
		// GameRegistry.registerBlock(flawlessCapacitor,SonarBlockTip.class, "FlawlessCapacitor");
		// GameRegistry.registerTileEntity(TileEntityFlawlessCapacitor.class, "FlawlessCapacitor");

		// generators
		conductorMast = new ConductorMast().setUnlocalizedName("ConductorMast").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(conductorMast, SonarBlockTip.class, "ConductorMast");
		GameRegistry.registerTileEntity(TileEntityConductorMast.class, "ConductorMast");
		conductormastBlock = new InvisibleBlock(0).setBlockName("ConductorMastBlock").setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "stablestone").setResistance(20.0F);
		GameRegistry.registerBlock(conductormastBlock, SonarBlockTip.class, "ConductorMastBlock");
		weatherStation = new WeatherStation().setUnlocalizedName("WeatherStation").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "stablestone").setResistance(20.0F);
		GameRegistry.registerBlock(weatherStation, SonarBlockTip.class, "WeatherStation");
		GameRegistry.registerTileEntity(TileEntityWeatherStation.class, "WeatherStation");
		weatherStationBlock = new InvisibleBlock(1).setBlockName("WeatherStationBlock").setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "stablestone").setResistance(20.0F);
		GameRegistry.registerBlock(weatherStationBlock, SonarBlockTip.class, "WeatherStationBlock");
		transmitter = new Transmitter().setUnlocalizedName("Transmitter").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "stablestone").setResistance(20.0F);
		GameRegistry.registerBlock(transmitter, SonarBlockTip.class, "Transmitter");
		GameRegistry.registerTileEntity(TileEntityTransmitter.class, "Transmitter");
		transmitterBlock = new InvisibleBlock(2).setBlockName("TransmitterBlock").setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "stablestone").setResistance(20.0F);
		GameRegistry.registerBlock(transmitterBlock, SonarBlockTip.class, "TransmitterBlock");
		*/

		starchextractor = registerBlock("StarchExtractor",new ExtractorBlock(0).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityGenerator.StarchExtractor.class, "StarchExtractor");
		redstoneextractor = registerBlock("RedstoneExtractor",new ExtractorBlock(1).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityGenerator.RedstoneExtractor.class, "RedstoneExtractor");
		glowstoneextractor = registerBlock("GlowstoneExtractor",new ExtractorBlock(2).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityGenerator.GlowstoneExtractor.class, "GlowstoneExtractor");
		handcrankedGenerator = registerBlock("HandCrankedGenerator",new CrankedGenerator().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCrankedGenerator.class, "HandCrankedGenerator");
		
		/*
		handcrankedGenerator = new CrankedGenerator().setUnlocalizedName("CrankedGenerator").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "handcrankedGenerator").setResistance(20.0F);
		GameRegistry.registerBlock(handcrankedGenerator, SonarBlockTip.class, "CrankedGenerator");
		GameRegistry.registerTileEntity(TileEntityCrankedGenerator.class, "CrankedGenerator");
		crank = new CrankHandle().setUnlocalizedName("Crank").setCreativeTab(Calculator).setHardness(20.0F);
		GameRegistry.registerBlock(crank, SonarBlockTip.class, "Crank");
		GameRegistry.registerTileEntity(TileEntityCrankHandle.class, "Crank");
		magneticFlux = new MagneticFlux().setUnlocalizedName("MagneticFlux").setCreativeTab(Calculator).setHardness(1.0F).setBlockTextureName(modid + ":" + "calculatorlocator").setResistance(20.0F);
		GameRegistry.registerBlock(magneticFlux, SonarBlockTip.class, "MagneticFlux");
		GameRegistry.registerTileEntity(TileEntityMagneticFlux.class, "MagneticFlux");
		weatherController = new WeatherController().setUnlocalizedName("WeatherController").setCreativeTab(Calculator).setHardness(1.0F);
		GameRegistry.registerBlock(weatherController, SonarBlockTip.class, "WeatherController");
		GameRegistry.registerTileEntity(TileEntityWeatherController.class, "WeatherController");
		rainSensor = new RainSensor().setUnlocalizedName("RainSensor").setCreativeTab(Calculator).setHardness(1.0F);
		GameRegistry.registerBlock(rainSensor, SonarBlockTip.class, "RainSensor");
		GameRegistry.registerTileEntity(TileEntityRainSensor.class, "RainSensor");
		

		stoneAssimilator = new Assimilator(0).setBlockName("StoneAssimilator").setCreativeTab(Calculator).setHardness(1.0F).setBlockTextureName(modid + ":" + "reinforcedstone");
		GameRegistry.registerBlock(stoneAssimilator, SonarBlockTip.class, "StoneAssimilator");
		GameRegistry.registerTileEntity(TileEntityAssimilator.Stone.class, "StoneAssimilator");
		
		algorithmAssimilator = new Assimilator(1).setBlockName("AlgorithmAssimilator").setCreativeTab(Calculator).setHardness(1.0F).setBlockTextureName(modid + ":" + "flawless_block");
		GameRegistry.registerBlock(algorithmAssimilator, SonarBlockTip.class, "AlgorithmAssimilator");
		GameRegistry.registerTileEntity(TileEntityAssimilator.Algorithm.class, "AlgorithmAssimilator");
		
		*/
		// misc

		gas_lantern_on = registerBlock("GasLanternOn", new GasLantern(true).setHardness(0.1F).setLightLevel(0.9375F).setLightOpacity(100));
		gas_lantern_off = registerBlock("GasLanternOff", new GasLantern(true).setHardness(0.1F));
		basic_lantern = registerBlock("Lantern", new BasicLantern().setHardness(0.1F).setLightLevel(0.9375F).setLightOpacity(100));
		GameRegistry.registerTileEntity(TileEntityGasLantern.class, "Lantern");

		scarecrow = registerBlock("Scarecrow", new Scarecrow().setHardness(0.5F).setResistance(24.0F));
		GameRegistry.registerTileEntity(TileEntityScarecrow.class, "Scarecrow");
		/*
		scarecrowBlock = new ScarecrowBlock().setUnlocalizedName("ScarecrowBlock").setHardness(0.5F).setBlockTextureName(modid + ":" + "reinforcedstone");
		GameRegistry.registerBlock(scarecrowBlock, SonarBlockTip.class, "ScarecrowBlock");
	*/
		// amethyst
		amethystLog = registerBlock("AmethystLog",new CalculatorLogs());
		amethystPlanks = registerBlock("AmethystPlanks",new CalculatorPlanks());
		/*
		amethystStairs = new CalculatorStairs(amethystPlanks, 0).setBlockName("AmethystStairs").setCreativeTab(Calculator);
		GameRegistry.registerBlock(amethystStairs, SonarBlockTip.class, "AmethystStairs");
		amethystFence = new BlockFence("Calculator:wood/planks_amethyst", Material.wood).setBlockName("AmethystFence").setCreativeTab(Calculator);
		GameRegistry.registerBlock(amethystFence, SonarBlockTip.class, "AmethystFence");
		amethystLeaf = new CalculatorLeaves(0).setBlockName("AmethystLeaf").setCreativeTab(Calculator);
		GameRegistry.registerBlock(amethystLeaf, SonarBlockTip.class, "AmethystLeaf");
		AmethystSapling = new CalculatorSaplings(0).setBlockName("AmethystSapling").setCreativeTab(Calculator);
		GameRegistry.registerBlock(AmethystSapling, SonarBlockTip.class, "AmethystSapling");
	
		// tanzanite
		 * */

		tanzaniteLog = registerBlock("TanzaniteLog",new CalculatorLogs());
		tanzanitePlanks = registerBlock("TanzanitePlanks",new CalculatorPlanks());
		/*
		tanzaniteStairs = new CalculatorStairs(tanzanitePlanks, 0).setBlockName("TanzaniteStairs").setCreativeTab(Calculator);
		GameRegistry.registerBlock(tanzaniteStairs, SonarBlockTip.class, "TanzaniteStairs");
		tanzaniteFence = new BlockFence("Calculator:wood/planks_tanzanite", Material.wood).setBlockName("TanzaniteFence").setCreativeTab(Calculator);
		GameRegistry.registerBlock(tanzaniteFence, SonarBlockTip.class, "TanzaniteFence");
		tanzaniteLeaf = new CalculatorLeaves(1).setBlockName("TanzaniteLeaf").setCreativeTab(Calculator);
		GameRegistry.registerBlock(tanzaniteLeaf, SonarBlockTip.class, "TanzaniteLeaf");
		tanzaniteSapling = new CalculatorSaplings(1).setBlockName("TanzaniteSapling").setCreativeTab(Calculator);
		GameRegistry.registerBlock(tanzaniteSapling, SonarBlockTip.class, "TanzaniteSapling");

		// pear
		 * */
		pearLog = registerBlock("PearLog",new CalculatorLogs());
		pearPlanks = registerBlock("PearPlanks",new CalculatorPlanks());
		/*
		pearStairs = new CalculatorStairs(pearPlanks, 0).setBlockName("PearStairs").setCreativeTab(Calculator);
		GameRegistry.registerBlock(pearStairs, SonarBlockTip.class, "PearStairs");
		pearFence = new BlockFence("Calculator:wood/planks_pear", Material.wood).setBlockName("PearFence").setCreativeTab(Calculator);
		GameRegistry.registerBlock(pearFence, SonarBlockTip.class, "PearFence");
		pearLeaf = new CalculatorLeaves(2).setBlockName("PearLeaf").setCreativeTab(Calculator);
		GameRegistry.registerBlock(pearLeaf, SonarBlockTip.class, "PearLeaf");
		PearSapling = new CalculatorSaplings(2).setBlockName("PearSapling").setCreativeTab(Calculator);
		GameRegistry.registerBlock(PearSapling, SonarBlockTip.class, "PearSapling");

		// diamond
		 */
		diamondLog = registerBlock("DiamondLog",new CalculatorLogs());
		diamondPlanks = registerBlock("DiamondPlanks",new CalculatorPlanks());
		/*
		diamondStairs = new CalculatorStairs(diamondPlanks, 0).setBlockName("DiamondStairs").setCreativeTab(Calculator);
		GameRegistry.registerBlock(diamondStairs, SonarBlockTip.class, "DiamondStairs");
		diamondFence = new BlockFence("Calculator:wood/planks_diamond", Material.wood).setBlockName("DiamondFence").setCreativeTab(Calculator);
		GameRegistry.registerBlock(diamondFence, SonarBlockTip.class, "DiamondFence");
		diamondLeaf = new CalculatorLeaves(3).setBlockName("DiamondLeaf").setCreativeTab(Calculator);
		GameRegistry.registerBlock(diamondLeaf, SonarBlockTip.class, "DiamondLeaf");
		diamondSapling = new CalculatorSaplings(3).setBlockName("DiamondSapling").setCreativeTab(Calculator);
		GameRegistry.registerBlock(diamondSapling, SonarBlockTip.class, "DiamondSapling");

		// decoration blocks
		amethyst_block = new AmethystBlock().setHardness(1.0F).setCreativeTab(Calculator).setResistance(20.0F);
		GameRegistry.registerBlock(amethyst_block, SonarBlockTip.class, "AmethystBlock");
		tanzanite_block = new TanzaniteBlock().setHardness(1.0F).setCreativeTab(Calculator).setResistance(20.0F);
		GameRegistry.registerBlock(tanzanite_block, SonarBlockTip.class, "TanzaniteBlock");
		enriched_gold_block = new EnrichedBlock().setHardness(1.0F).setCreativeTab(Calculator).setResistance(20.0F);
		GameRegistry.registerBlock(enriched_gold_block, SonarBlockTip.class, "EnrichedBlock");
		reinforced_iron_block = new ReinforcedBlock().setHardness(1.0F).setCreativeTab(Calculator).setResistance(20.0F);
		GameRegistry.registerBlock(reinforced_iron_block, SonarBlockTip.class, "ReinforcedBlock");
		weakened_diamond_block = new WeakenedBlock().setHardness(1.0F).setCreativeTab(Calculator).setResistance(20.0F);
		GameRegistry.registerBlock(weakened_diamond_block, SonarBlockTip.class, "WeakenedBlock");
		flawless_block = new FlawlessBlock().setHardness(1.0F).setCreativeTab(Calculator).setResistance(20.0F);
		GameRegistry.registerBlock(flawless_block, SonarBlockTip.class, "FlawlessBlock");
		flawless_fire_block = new FlawlessFireBlock().setHardness(1.0F).setCreativeTab(Calculator).setResistance(20.0F);
		GameRegistry.registerBlock(flawless_fire_block, SonarBlockTip.class, "FlawlessFireBlock");
		electric_diamond_block = new ElectricBlock().setHardness(1.0F).setCreativeTab(Calculator).setResistance(20.0F);
		GameRegistry.registerBlock(electric_diamond_block, SonarBlockTip.class, "ElectricBlock");
		end_diamond_block = new EndBlock().setCreativeTab(Calculator).setResistance(20.0F);
		GameRegistry.registerBlock(end_diamond_block, SonarBlockTip.class, "EndBlock");

		calculatorScreen = new CalculatorScreen().setUnlocalizedName("calculatorScreen").setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(calculatorScreen, SonarBlockTip.class, "calculatorScreen");
		GameRegistry.registerTileEntity(TileEntityCalculatorScreen.class, "calculatorScreen");
		*/
	}

}
