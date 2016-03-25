package sonar.calculator.mod;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sonar.calculator.mod.common.block.CalculatorCrops;
import sonar.calculator.mod.common.block.CalculatorLeaves;
import sonar.calculator.mod.common.block.CalculatorLogs;
import sonar.calculator.mod.common.block.CalculatorPlanks;
import sonar.calculator.mod.common.block.CalculatorSaplings;
import sonar.calculator.mod.common.block.CalculatorStairs;
import sonar.calculator.mod.common.block.MaterialBlock;
import sonar.calculator.mod.common.block.SmeltingBlock;
import sonar.calculator.mod.common.block.SmeltingBlock.BlockTypes;
import sonar.calculator.mod.common.block.ConnectedBlock;
import sonar.calculator.mod.common.block.calculators.AtomicCalculator;
import sonar.calculator.mod.common.block.calculators.DynamicCalculator;
import sonar.calculator.mod.common.block.generators.CalculatorLocator;
import sonar.calculator.mod.common.block.generators.CalculatorPlug;
import sonar.calculator.mod.common.block.generators.CrankHandle;
import sonar.calculator.mod.common.block.generators.CrankedGenerator;
import sonar.calculator.mod.common.block.generators.ExtractorBlock;
import sonar.calculator.mod.common.block.machines.AdvancedGreenhouse;
import sonar.calculator.mod.common.block.machines.AdvancedPowerCube;
import sonar.calculator.mod.common.block.machines.AtomicMultiplier;
import sonar.calculator.mod.common.block.machines.BasicGreenhouse;
import sonar.calculator.mod.common.block.machines.FlawlessGreenhouse;
import sonar.calculator.mod.common.block.machines.HealthProcessor;
import sonar.calculator.mod.common.block.machines.HungerProcessor;
import sonar.calculator.mod.common.block.machines.ModuleWorkstation;
import sonar.calculator.mod.common.block.machines.PowerCube;
import sonar.calculator.mod.common.block.machines.StorageChamber;
import sonar.calculator.mod.common.block.misc.BasicLantern;
import sonar.calculator.mod.common.block.misc.CO2Generator;
import sonar.calculator.mod.common.block.misc.GasLantern;
import sonar.calculator.mod.common.block.misc.Scarecrow;
import sonar.calculator.mod.common.tileentity.TileEntityMachines;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankedGenerator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.calculator.mod.common.tileentity.machines.TileEntityBasicGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHealthProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHungerProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityModuleWorkstation;
import sonar.calculator.mod.common.tileentity.machines.TileEntityPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityGasLantern;
import sonar.calculator.mod.common.tileentity.misc.TileEntityScarecrow;
import sonar.core.common.block.BlockBase;
import sonar.core.common.block.SonarBlockTip;
import sonar.core.common.block.SonarMetaBlock;

public class CalculatorBlocks extends Calculator {

	public static ArrayList<Block> registeredBlocks = new ArrayList();
	
	public static Block registerBlock(String name, Block block) {
		block.setCreativeTab(Calculator);
		GameRegistry.registerBlock(block.setUnlocalizedName(name), SonarBlockTip.class, name);
		registeredBlocks.add(block);
		return block;
	}	
	public static Block registerMetaBlock(String name, Block block) {
		block.setCreativeTab(Calculator);
		GameRegistry.registerBlock(block.setUnlocalizedName(name), SonarMetaBlock.class, name);
		registeredBlocks.add(block);
		return block;
	}	
	public static void registerBlocks() {
		
		// common blocks
		reinforcedStoneBlock = registerBlock("ReinforcedStoneBlock",new BlockBase(Material.rock, 2.0f, 10.0f));
		reinforcedStoneStairs = registerBlock("ReinforcedStoneStairs",new CalculatorStairs(reinforcedStoneBlock));
		reinforcedStoneFence = registerBlock("ReinforcedStoneFence",new BlockFence(Material.rock));
		
		reinforcedStoneBrick = registerBlock("ReinforcedStoneBrick",new BlockBase(Material.rock, 2.0f, 10.0f));
		reinforcedStoneBrickStairs = registerBlock("ReinforcedStoneBrickStairs",new CalculatorStairs(reinforcedStoneBrick));
		reinforcedStoneBrickFence = registerBlock("ReinforcedStoneBrickFence",new BlockFence(Material.rock));
		
		reinforcedDirtBlock = registerBlock("ReinforcedDirtBlock",new BlockBase(Material.ground, 1.0f, 4.0f));
		reinforcedDirtStairs = registerBlock("ReinforcedDirtStairs",new CalculatorStairs(reinforcedDirtBlock));
		reinforcedDirtFence = registerBlock("ReinforcedDirtFence",new BlockFence(Material.ground));
		
		
		reinforcedDirtBrick = registerBlock("ReinforcedDirtBrick",new BlockBase(Material.ground, 1.0f, 4.0f));
		reinforcedDirtBrickStairs = registerBlock("ReinforcedDirtBrickStairs",new CalculatorStairs(reinforcedDirtBrick));
		reinforcedDirtBrickFence = registerBlock("ReinforcedDirtBrickFence",new BlockFence(Material.ground));
		
		stableStone = registerBlock("StableStone", new ConnectedBlock(Material.rock, 0));		
		stableGlass = registerBlock("StableGlass", new ConnectedBlock.Glass(Material.glass, 1)).setLightLevel(0.625F).setHardness(0.6F);
		clearStableGlass = registerBlock("ClearStableGlass", new ConnectedBlock.Glass(Material.glass, 2)).setLightLevel(0.625F).setHardness(0.6F);		
		flawlessGlass = registerBlock("FlawlessGlass", new ConnectedBlock.Glass(Material.glass, 3)).setLightLevel(0.625F).setHardness(0.6F);
		purifiedObsidian = registerBlock("PurifiedObsidian", new ConnectedBlock(Material.rock, 4)).setBlockUnbreakable();
		
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
		moduleWorkstation = registerBlock("ModuleWorkstation",new ModuleWorkstation().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityModuleWorkstation.class, "ModuleWorkstation");

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
		
		crankHandle = registerBlock("CrankHandle",new CrankHandle().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCrankHandle.class, "CrankHandle");
		/*
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
		amethystStairs = registerBlock("AmethystStairs",new CalculatorStairs(amethystPlanks));
		amethystFence = registerBlock("AmethystFence",new BlockFence(Material.wood));
		amethystLeaves = registerBlock("AmethystLeaves",new CalculatorLeaves(0));
		AmethystSapling = registerBlock("AmethystSapling",new CalculatorSaplings(0));
		
		tanzaniteLog = registerBlock("TanzaniteLog",new CalculatorLogs());
		tanzanitePlanks = registerBlock("TanzanitePlanks",new CalculatorPlanks());
		tanzaniteStairs = registerBlock("TanzaniteStairs",new CalculatorStairs(tanzanitePlanks));
		tanzaniteFence = registerBlock("TanzaniteFence",new BlockFence(Material.wood));
		tanzaniteLeaves = registerBlock("TanzaniteLeaves",new CalculatorLeaves(1));
		tanzaniteSapling = registerBlock("TanzaniteSapling",new CalculatorSaplings(1));
		
		pearLog = registerBlock("PearLog",new CalculatorLogs());
		pearPlanks = registerBlock("PearPlanks",new CalculatorPlanks());
		pearStairs = registerBlock("PearStairs",new CalculatorStairs(pearPlanks));
		pearFence = registerBlock("PearFence",new BlockFence(Material.wood));
		pearLeaves = registerBlock("PearLeaves",new CalculatorLeaves(2));
		PearSapling = registerBlock("PearSapling",new CalculatorSaplings(2));
		
		diamondLog = registerBlock("DiamondLog",new CalculatorLogs());
		diamondPlanks = registerBlock("DiamondPlanks",new CalculatorPlanks());
		diamondStairs = registerBlock("DiamondStairs",new CalculatorStairs(diamondPlanks));
		diamondFence = registerBlock("DiamondFence",new BlockFence(Material.wood));
		diamondLeaves = registerBlock("DiamondLeaves",new CalculatorLeaves(3));
		diamondSapling = registerBlock("DiamondSapling",new CalculatorSaplings(3));
		/*
		calculatorScreen = new CalculatorScreen().setUnlocalizedName("calculatorScreen").setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(calculatorScreen, SonarBlockTip.class, "calculatorScreen");
		GameRegistry.registerTileEntity(TileEntityCalculatorScreen.class, "calculatorScreen");
		*/
		material_block = registerMetaBlock("Material", new MaterialBlock());

		cropBroccoliPlant = registerBlock("CropBroccoli",new CalculatorCrops(0, 0));
		cropPrunaePlant = registerBlock("CropPrunae",new CalculatorCrops(1, 2));
		cropFiddledewPlant = registerBlock("CropFiddledew",new CalculatorCrops(2, 3));
	}

}
