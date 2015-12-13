package sonar.calculator.mod;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlockWithMetadata;
import sonar.calculator.mod.common.block.CalculatorLeaves;
import sonar.calculator.mod.common.block.CalculatorLogs;
import sonar.calculator.mod.common.block.CalculatorPlanks;
import sonar.calculator.mod.common.block.CalculatorSaplings;
import sonar.calculator.mod.common.block.CalculatorStairs;
import sonar.calculator.mod.common.block.ConnectedBlock;
import sonar.calculator.mod.common.block.ItemMetaBlock;
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
import sonar.calculator.mod.common.block.generators.ExtractorBlock;
import sonar.calculator.mod.common.block.generators.InvisibleBlock;
import sonar.calculator.mod.common.block.machines.AdvancedGreenhouse;
import sonar.calculator.mod.common.block.machines.AdvancedPowerCube;
import sonar.calculator.mod.common.block.machines.AnalysingChamber;
import sonar.calculator.mod.common.block.machines.Assimilator;
import sonar.calculator.mod.common.block.machines.AtomicMultiplier;
import sonar.calculator.mod.common.block.machines.BasicGreenhouse;
import sonar.calculator.mod.common.block.machines.DockingStation;
import sonar.calculator.mod.common.block.machines.EternalFire;
import sonar.calculator.mod.common.block.machines.FlawlessCapacitor;
import sonar.calculator.mod.common.block.machines.FlawlessFurnace;
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
import sonar.calculator.mod.common.block.misc.ReinforcedDirtBlock;
import sonar.calculator.mod.common.block.misc.ReinforcedStoneBlock;
import sonar.calculator.mod.common.block.misc.Scarecrow;
import sonar.calculator.mod.common.block.misc.ScarecrowBlock;
import sonar.calculator.mod.common.block.misc.WeatherController;
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
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessCapacitor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessFurnace;
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
import sonar.core.common.block.SonarBlockTip;
import cpw.mods.fml.common.registry.GameRegistry;

public class CalculatorBlocks extends Calculator {

	public static void registerBlocks() {
		
		// common blocks
		reinforcedstoneBlock = new ReinforcedStoneBlock().setBlockName("reinforcedstoneBlock").setCreativeTab(Calculator).setBlockTextureName(modid + ":" + "reinforcedstone");
		GameRegistry.registerBlock(reinforcedstoneBlock, SonarBlockTip.class, "reinforcedstoneBlock");
		reinforcedstoneBrick = new ReinforcedStoneBlock().setBlockName("reinforcedstoneBrick").setCreativeTab(Calculator).setBlockTextureName(modid + ":" + "reinforcedstonebrick");
		GameRegistry.registerBlock(reinforcedstoneBrick, SonarBlockTip.class, "reinforcedstoneBrick");
		reinforceddirtBlock = new ReinforcedDirtBlock().setBlockName("reinforceddirtBlock").setCreativeTab(Calculator).setBlockTextureName(modid + ":" + "reinforceddirt");
		GameRegistry.registerBlock(reinforceddirtBlock, SonarBlockTip.class, "reinforceddirtBlock");
		reinforceddirtBrick = new ReinforcedDirtBlock().setBlockName("reinforceddirtBrick").setCreativeTab(Calculator).setBlockTextureName(modid + ":" + "reinforceddirtbrick");
		GameRegistry.registerBlock(reinforceddirtBrick, SonarBlockTip.class, "reinforceddirtBrick");
		stablestoneBlock = new ConnectedBlock.Stable().setBlockName("stablestoneBlock").setCreativeTab(Calculator).setHardness(2.0F);
		GameRegistry.registerBlock(stablestoneBlock, ItemMetaBlock.class, "stablestoneBlock");
		stablestonerimmedBlock = new ConnectedBlock.StableRimmed().setBlockName("stablestonerimmedBlock").setCreativeTab(Calculator).setHardness(2.0F);
		GameRegistry.registerBlock(stablestonerimmedBlock, ItemMetaBlock.class, "stablestonerimmedBlock");
		stablestonerimmedblackBlock = new ConnectedBlock.StableBlackRimmed().setBlockName("stablestonerimmedblackBlock").setCreativeTab(Calculator).setHardness(2.0F);
		GameRegistry.registerBlock(stablestonerimmedblackBlock, ItemMetaBlock.class, "stablestonerimmedblackBlock");
		stableglassBlock = new ConnectedBlock.StableGlass("stablestone_glass", 3).setBlockName("StableGlass").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(0.6F);
		GameRegistry.registerBlock(stableglassBlock, SonarBlockTip.class, "StableGlass");
		clearstableglassBlock = new ConnectedBlock.StableGlass("stablestone_clear", 4).setBlockName("ClearStableGlass").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(0.6F);
		GameRegistry.registerBlock(clearstableglassBlock, SonarBlockTip.class, "ClearStableGlass");
		flawlessGlass = new ConnectedBlock(Material.glass, "flawlessglass", 1, false).setBlockName("FlawlessGlass").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(0.6F);
		GameRegistry.registerBlock(flawlessGlass, SonarBlockTip.class, "FlawlessGlass");
		purifiedobsidianBlock = new ConnectedBlock(Material.rock, "purifiedobsidian", 2, false).setBlockName("purifiedobsidianBlock").setCreativeTab(Calculator);
		GameRegistry.registerBlock(purifiedobsidianBlock, SonarBlockTip.class, "purifiedobsidianBlock");

		// calculators
		powerCube = new PowerCube().setBlockName("PCubeIdle").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(powerCube, SonarBlockTip.class, "PCubeIdle");
		GameRegistry.registerTileEntity(TileEntityPowerCube.class, "PCubeIdle");

		advancedPowerCube = new AdvancedPowerCube().setBlockName("AdvancedPCubeIdle").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(advancedPowerCube, SonarBlockTip.class, "AdvancedPCubeIdle");
		GameRegistry.registerTileEntity(TileEntityAdvancedPowerCube.class, "AdvancedPCubeIdle");

		atomiccalculatorBlock = new AtomicCalculatorBlock().setBlockName("atomiccalculatorBlock").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(atomiccalculatorBlock, SonarBlockTip.class, "atomiccalculatorBlock");
		GameRegistry.registerTileEntity(TileEntityCalculator.Atomic.class, "atomiccalculatorBlock");
		dynamiccalculatorBlock = new DynamicCalculatorBlock().setBlockName("dynamiccalculatorBlock").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(dynamiccalculatorBlock, SonarBlockTip.class, "dynamiccalculatorBlock");
		GameRegistry.registerTileEntity(TileEntityCalculator.Dynamic.class, "dynamiccalculatorBlock");

		// smelting
		reinforcedFurnace = new SmeltingBlock(7).setBlockName("ReinforcedFurnace").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(reinforcedFurnace, SonarBlockTip.class, "ReinforcedFurnace");
		GameRegistry.registerTileEntity(TileEntityMachines.ReinforcedFurnace.class, "ReinforcedFurnace");
		flawlessFurnace = new FlawlessFurnace().setBlockName("FlawlessFurnace").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(flawlessFurnace, SonarBlockTip.class, "FlawlessFurnace");
		GameRegistry.registerTileEntity(TileEntityFlawlessFurnace.class, "FlawlessFurnace");
		stoneSeperator = new SmeltingBlock(4).setBlockName("StoneSeperatorIdle").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(stoneSeperator, SonarBlockTip.class, "StoneSeperatorIdle");
		GameRegistry.registerTileEntity(TileEntityMachines.StoneSeperator.class, "StoneSeperatorIdle");
		algorithmSeperator = new SmeltingBlock(5).setBlockName("AlgorithmSeperatorIdle").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(algorithmSeperator, SonarBlockTip.class, "AlgorithmSeperatorIdle");
		GameRegistry.registerTileEntity(TileEntityMachines.AlgorithmSeperator.class, "AlgorithmSeperatorIdle");
		hungerprocessor = new HungerProcessor().setBlockName("HungerProcessor").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(hungerprocessor, SonarBlockTip.class, "HungerProcessor");
		GameRegistry.registerTileEntity(TileEntityHungerProcessor.class, "HungerProcessor");
		healthprocessor = new HealthProcessor().setBlockName("HealthProcessor").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(healthprocessor, SonarBlockTip.class, "HealthProcessor");
		GameRegistry.registerTileEntity(TileEntityHealthProcessor.class, "HealthProcessor");
		dockingStation = new DockingStation().setBlockName("DockingStation").setCreativeTab(Calculator).setHardness(1.5F);
		GameRegistry.registerBlock(dockingStation, SonarBlockTip.class, "DockingStation").setBlockTextureName(modid + ":" + "reinforcedstone");
		GameRegistry.registerTileEntity(TileEntityDockingStation.class, "DockingStation");
		atomicMultiplier = new AtomicMultiplier().setBlockName("AtomicMultiplier").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(6.5F).setBlockTextureName(modid + ":" + "stablestone").setResistance(20.0F);
		GameRegistry.registerBlock(atomicMultiplier, SonarBlockTip.class, "AtomicMultiplier");
		GameRegistry.registerTileEntity(TileEntityAtomicMultiplier.class, "AtomicMultiplier");
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
		analysingChamber = new AnalysingChamber().setBlockName("AnalysingChamber").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(analysingChamber, SonarBlockTip.class, "AnalysingChamber");
		GameRegistry.registerTileEntity(TileEntityAnalysingChamber.class, "AnalysingChamber");
		storageChamber = new StorageChamber().setBlockName("StorageChamber").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(storageChamber, SonarBlockTip.class, "StorageChamber");
		GameRegistry.registerTileEntity(TileEntityStorageChamber.class, "StorageChamber");
		researchChamber = new ResearchChamber().setBlockName("ResearchChamber").setHardness(1.0F).setBlockTextureName(modid + ":" + "stablestone").setResistance(20.0F);
		GameRegistry.registerBlock(researchChamber, SonarBlockTip.class, "ResearchChamber");
		GameRegistry.registerTileEntity(TileEntityResearchChamber.class, "ResearchChamber");
		// manipulationChamber = new ManipulationChamber().setBlockName("ManipulationChamber").setCreativeTab(Calculator).setHardness(1.5F);
		// GameRegistry.registerBlock(manipulationChamber, CalcBlockItem.class, "ManipulationChamber");
		// GameRegistry.registerTileEntity(TileEntityManipulationChamber.class, "ManipulationChamber");

		// machines
		basicGreenhouse = new BasicGreenhouse().setBlockName("BasicGreenhouse").setCreativeTab(Calculator).setHardness(1.0F).setResistance(24.0F);
		GameRegistry.registerBlock(basicGreenhouse, SonarBlockTip.class, "BasicGreenhouse");
		GameRegistry.registerTileEntity(TileEntityBasicGreenhouse.class, "BasicGreenhouse");
		advancedGreenhouse = new AdvancedGreenhouse().setBlockName("AdvancedGreenhouse").setCreativeTab(Calculator).setHardness(1.0F).setResistance(24.0F);
		GameRegistry.registerBlock(advancedGreenhouse, SonarBlockTip.class, "AdvancedGreenhouse");
		GameRegistry.registerTileEntity(TileEntityAdvancedGreenhouse.class, "AdvancedGreenhouse");
		flawlessGreenhouse = new FlawlessGreenhouse().setBlockName("FlawlessGreenhouse").setCreativeTab(Calculator).setHardness(1.0F).setResistance(24.0F);
		GameRegistry.registerBlock(flawlessGreenhouse, SonarBlockTip.class, "FlawlessGreenhouse");
		GameRegistry.registerTileEntity(TileEntityFlawlessGreenhouse.class, "FlawlessGreenhouse");
		carbondioxideGenerator = new CO2Generator().setBlockName("CO2Generator").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(carbondioxideGenerator, SonarBlockTip.class, "CO2Generator");
		GameRegistry.registerTileEntity(TileEntityCO2Generator.class, "CO2Generator");
		
		fluxPlug = new FluxPlug().setBlockName("FluxPlug").setCreativeTab(Calculator).setHardness(0.2F).setResistance(20.0F).setBlockTextureName(modid + ":" + "stablestone");
		GameRegistry.registerBlock(fluxPlug, SonarBlockTip.class, "FluxPlug");
		GameRegistry.registerTileEntity(TileEntityFluxPlug.class, "FluxPlug");
		fluxPoint = new FluxPoint().setBlockName("FluxPoint").setCreativeTab(Calculator).setHardness(0.2F).setResistance(20.0F).setBlockTextureName(modid + ":" + "stablestone");
		GameRegistry.registerBlock(fluxPoint, SonarBlockTip.class, "FluxPoint");
		GameRegistry.registerTileEntity(TileEntityFluxPoint.class, "FluxPoint");
		fluxController = new FluxController().setBlockName("FluxController").setCreativeTab(Calculator).setResistance(20.0F).setBlockTextureName(modid + ":" + "stablestone").setHardness(1.5F).setLightLevel(0.9375F).setLightOpacity(100);
		GameRegistry.registerBlock(fluxController, SonarBlockTip.class, "FluxController");
		GameRegistry.registerTileEntity(TileEntityFluxController.class, "FluxController");
		teleporter = new Teleporter().setBlockName("CalculatorTeleporter").setCreativeTab(Calculator).setHardness(1.0F).setLightLevel(0.625F).setBlockTextureName(modid + ":" + "stablestone");
		GameRegistry.registerBlock(teleporter, SonarBlockTip.class, "CalculatorTeleporter");
		GameRegistry.registerTileEntity(TileEntityTeleporter.class, "CalculatorTeleporter");
		calculatorlocator = new CalculatorLocator().setBlockName("CalculatorLocator").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "calculatorplug").setResistance(20.0F);
		GameRegistry.registerBlock(calculatorlocator, SonarBlockTip.class, "CalculatorLocator");
		GameRegistry.registerTileEntity(TileEntityCalculatorLocator.class, "CalculatorLocator");
		calculatorplug = new CalculatorPlug().setBlockName("CalculatorPlug").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "calculatorplug").setResistance(20.0F);
		GameRegistry.registerBlock(calculatorplug, SonarBlockTip.class, "CalculatorPlug");
		GameRegistry.registerTileEntity(TileEntityCalculatorPlug.class, "CalculatorPlug");

		// energy
		 //flawlessCapacitor = new FlawlessCapacitor().setBlockName("FlawlessCapacitor").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(6.5F).setBlockTextureName(modid + ":" +
		// "electric_diamond_block");
		// GameRegistry.registerBlock(flawlessCapacitor,SonarBlockTip.class, "FlawlessCapacitor");
		// GameRegistry.registerTileEntity(TileEntityFlawlessCapacitor.class, "FlawlessCapacitor");

		// generators
		conductorMast = new ConductorMast().setBlockName("ConductorMast").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(conductorMast, SonarBlockTip.class, "ConductorMast");
		GameRegistry.registerTileEntity(TileEntityConductorMast.class, "ConductorMast");
		conductormastBlock = new InvisibleBlock(0).setBlockName("ConductorMastBlock").setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "stablestone").setResistance(20.0F);
		GameRegistry.registerBlock(conductormastBlock, SonarBlockTip.class, "ConductorMastBlock");
		weatherStation = new WeatherStation().setBlockName("WeatherStation").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "stablestone").setResistance(20.0F);
		GameRegistry.registerBlock(weatherStation, SonarBlockTip.class, "WeatherStation");
		GameRegistry.registerTileEntity(TileEntityWeatherStation.class, "WeatherStation");
		weatherStationBlock = new InvisibleBlock(1).setBlockName("WeatherStationBlock").setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "stablestone").setResistance(20.0F);
		GameRegistry.registerBlock(weatherStationBlock, SonarBlockTip.class, "WeatherStationBlock");
		transmitter = new Transmitter().setBlockName("Transmitter").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "stablestone").setResistance(20.0F);
		GameRegistry.registerBlock(transmitter, SonarBlockTip.class, "Transmitter");
		GameRegistry.registerTileEntity(TileEntityTransmitter.class, "Transmitter");
		transmitterBlock = new InvisibleBlock(2).setBlockName("TransmitterBlock").setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "stablestone").setResistance(20.0F);
		GameRegistry.registerBlock(transmitterBlock, SonarBlockTip.class, "TransmitterBlock");
		starchextractor = new ExtractorBlock(0).setBlockName("starchextractor").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "starchextractor").setResistance(20.0F);
		GameRegistry.registerBlock(starchextractor, SonarBlockTip.class, "starchextractor");
		GameRegistry.registerTileEntity(TileEntityGenerator.StarchExtractor.class, "starchextractor");
		redstoneextractor = new ExtractorBlock(1).setBlockName("redstoneextractor").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "redstoneextractor").setResistance(20.0F);
		GameRegistry.registerBlock(redstoneextractor, SonarBlockTip.class, "redstoneextractor");
		GameRegistry.registerTileEntity(TileEntityGenerator.RedstoneExtractor.class, "redstoneextractor");
		glowstoneextractor = new ExtractorBlock(2).setBlockName("glowstoneextractor").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "glowstoneextractor").setResistance(20.0F);
		GameRegistry.registerBlock(glowstoneextractor, SonarBlockTip.class, "glowstoneextractor");
		GameRegistry.registerTileEntity(TileEntityGenerator.GlowstoneExtractor.class, "glowstoneextractor");
		handcrankedGenerator = new CrankedGenerator().setBlockName("CrankedGenerator").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(1.0F).setBlockTextureName(modid + ":" + "handcrankedGenerator").setResistance(20.0F);
		GameRegistry.registerBlock(handcrankedGenerator, SonarBlockTip.class, "CrankedGenerator");
		GameRegistry.registerTileEntity(TileEntityCrankedGenerator.class, "CrankedGenerator");
		crank = new CrankHandle().setBlockName("Crank").setCreativeTab(Calculator).setHardness(20.0F);
		GameRegistry.registerBlock(crank, SonarBlockTip.class, "Crank");
		GameRegistry.registerTileEntity(TileEntityCrankHandle.class, "Crank");
		magneticFlux = new MagneticFlux().setBlockName("MagneticFlux").setCreativeTab(Calculator).setHardness(1.0F).setBlockTextureName(modid + ":" + "calculatorlocator").setResistance(20.0F);
		GameRegistry.registerBlock(magneticFlux, SonarBlockTip.class, "MagneticFlux");
		GameRegistry.registerTileEntity(TileEntityMagneticFlux.class, "MagneticFlux");
		weatherController = new WeatherController().setBlockName("WeatherController").setCreativeTab(Calculator).setHardness(1.0F);
		GameRegistry.registerBlock(weatherController, SonarBlockTip.class, "WeatherController");
		GameRegistry.registerTileEntity(TileEntityWeatherController.class, "WeatherController");
		rainSensor = new RainSensor().setBlockName("RainSensor").setCreativeTab(Calculator).setHardness(1.0F);
		GameRegistry.registerBlock(rainSensor, SonarBlockTip.class, "RainSensor");
		GameRegistry.registerTileEntity(TileEntityRainSensor.class, "RainSensor");
		

		stoneAssimilator = new Assimilator(0).setBlockName("StoneAssimilator").setCreativeTab(Calculator).setHardness(1.0F).setBlockTextureName(modid + ":" + "reinforcedstone");
		GameRegistry.registerBlock(stoneAssimilator, SonarBlockTip.class, "StoneAssimilator");
		GameRegistry.registerTileEntity(TileEntityAssimilator.Stone.class, "StoneAssimilator");
		
		algorithmAssimilator = new Assimilator(1).setBlockName("AlgorithmAssimilator").setCreativeTab(Calculator).setHardness(1.0F).setBlockTextureName(modid + ":" + "flawless_block");
		GameRegistry.registerBlock(algorithmAssimilator, SonarBlockTip.class, "AlgorithmAssimilator");
		GameRegistry.registerTileEntity(TileEntityAssimilator.Algorithm.class, "AlgorithmAssimilator");
		

		// misc
		gas_lantern_on = new GasLantern(true).setBlockName("LanternOn").setHardness(0.1F).setLightLevel(0.9375F).setLightOpacity(100);
		GameRegistry.registerBlock(gas_lantern_on, SonarBlockTip.class, "LanternOn");
		basic_lantern = new BasicLantern().setBlockName("LanternBasic").setCreativeTab(Calculator).setHardness(0.1F).setLightLevel(0.9375F).setLightOpacity(100);
		GameRegistry.registerBlock(basic_lantern, SonarBlockTip.class, "LanternBasic");
		GameRegistry.registerTileEntity(TileEntityBasicLantern.class, "LanternBasic");
		gas_lantern_off = new GasLantern(false).setBlockName("LanternOff").setCreativeTab(Calculator).setHardness(0.1F);
		GameRegistry.registerBlock(gas_lantern_off, SonarBlockTip.class, "LanternOff");
		GameRegistry.registerTileEntity(TileEntityGasLantern.class, "Lantern");
		scarecrow = new Scarecrow().setBlockName("Scarecrow").setCreativeTab(Calculator).setHardness(0.5F).setResistance(24.0F).setBlockTextureName(modid + ":" + "reinforcedstone");
		GameRegistry.registerBlock(scarecrow, SonarBlockTip.class, "Scarecrow");
		GameRegistry.registerTileEntity(TileEntityScarecrow.class, "Scarecrow");
		scarecrowBlock = new ScarecrowBlock().setBlockName("ScarecrowBlock").setHardness(0.5F).setBlockTextureName(modid + ":" + "reinforcedstone");
		GameRegistry.registerBlock(scarecrowBlock, SonarBlockTip.class, "ScarecrowBlock");

		// amethyst
		amethystLog = new CalculatorLogs(0).setBlockName("AmethystLog").setCreativeTab(Calculator);
		GameRegistry.registerBlock(amethystLog, SonarBlockTip.class, "AmethystLog");
		amethystPlanks = new CalculatorPlanks(0).setBlockName("AmethystPlanks").setCreativeTab(Calculator);
		GameRegistry.registerBlock(amethystPlanks, SonarBlockTip.class, "AmethystPlanks");
		amethystStairs = new CalculatorStairs(amethystPlanks, 0).setBlockName("AmethystStairs").setCreativeTab(Calculator);
		GameRegistry.registerBlock(amethystStairs, SonarBlockTip.class, "AmethystStairs");
		amethystFence = new BlockFence("Calculator:wood/planks_amethyst", Material.wood).setBlockName("AmethystFence").setCreativeTab(Calculator);
		GameRegistry.registerBlock(amethystFence, SonarBlockTip.class, "AmethystFence");
		amethystLeaf = new CalculatorLeaves(0).setBlockName("AmethystLeaf").setCreativeTab(Calculator);
		GameRegistry.registerBlock(amethystLeaf, SonarBlockTip.class, "AmethystLeaf");
		AmethystSapling = new CalculatorSaplings(0).setBlockName("AmethystSapling").setCreativeTab(Calculator);
		GameRegistry.registerBlock(AmethystSapling, SonarBlockTip.class, "AmethystSapling");

		// tanzanite
		tanzaniteLog = new CalculatorLogs(1).setBlockName("TanzaniteLog").setCreativeTab(Calculator);
		GameRegistry.registerBlock(tanzaniteLog, SonarBlockTip.class, "TanzaniteLog");
		tanzanitePlanks = new CalculatorPlanks(1).setBlockName("TanzanitePlanks").setCreativeTab(Calculator);
		GameRegistry.registerBlock(tanzanitePlanks, SonarBlockTip.class, "TanzanitePlanks");
		tanzaniteStairs = new CalculatorStairs(tanzanitePlanks, 0).setBlockName("TanzaniteStairs").setCreativeTab(Calculator);
		GameRegistry.registerBlock(tanzaniteStairs, SonarBlockTip.class, "TanzaniteStairs");
		tanzaniteFence = new BlockFence("Calculator:wood/planks_tanzanite", Material.wood).setBlockName("TanzaniteFence").setCreativeTab(Calculator);
		GameRegistry.registerBlock(tanzaniteFence, SonarBlockTip.class, "TanzaniteFence");
		tanzaniteLeaf = new CalculatorLeaves(1).setBlockName("TanzaniteLeaf").setCreativeTab(Calculator);
		GameRegistry.registerBlock(tanzaniteLeaf, SonarBlockTip.class, "TanzaniteLeaf");
		tanzaniteSapling = new CalculatorSaplings(1).setBlockName("TanzaniteSapling").setCreativeTab(Calculator);
		GameRegistry.registerBlock(tanzaniteSapling, SonarBlockTip.class, "TanzaniteSapling");

		// pear
		pearLog = new CalculatorLogs(2).setBlockName("PearLog").setCreativeTab(Calculator);
		GameRegistry.registerBlock(pearLog, SonarBlockTip.class, "PearLog");
		pearPlanks = new CalculatorPlanks(2).setBlockName("PearPlanks").setCreativeTab(Calculator);
		GameRegistry.registerBlock(pearPlanks, SonarBlockTip.class, "PearPlanks");
		pearStairs = new CalculatorStairs(pearPlanks, 0).setBlockName("PearStairs").setCreativeTab(Calculator);
		GameRegistry.registerBlock(pearStairs, SonarBlockTip.class, "PearStairs");
		pearFence = new BlockFence("Calculator:wood/planks_pear", Material.wood).setBlockName("PearFence").setCreativeTab(Calculator);
		GameRegistry.registerBlock(pearFence, SonarBlockTip.class, "PearFence");
		pearLeaf = new CalculatorLeaves(2).setBlockName("PearLeaf").setCreativeTab(Calculator);
		GameRegistry.registerBlock(pearLeaf, SonarBlockTip.class, "PearLeaf");
		PearSapling = new CalculatorSaplings(2).setBlockName("PearSapling").setCreativeTab(Calculator);
		GameRegistry.registerBlock(PearSapling, SonarBlockTip.class, "PearSapling");

		// diamond
		diamondLog = new CalculatorLogs(3).setBlockName("DiamondLog").setCreativeTab(Calculator);
		GameRegistry.registerBlock(diamondLog, SonarBlockTip.class, "DiamondLog");
		diamondPlanks = new CalculatorPlanks(3).setBlockName("DiamondPlanks").setCreativeTab(Calculator);
		GameRegistry.registerBlock(diamondPlanks, SonarBlockTip.class, "DiamondPlanks");
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

		calculatorScreen = new CalculatorScreen().setBlockName("calculatorScreen").setHardness(1.0F).setResistance(20.0F);
		GameRegistry.registerBlock(calculatorScreen, SonarBlockTip.class, "calculatorScreen");
		GameRegistry.registerTileEntity(TileEntityCalculatorScreen.class, "calculatorScreen");
	}

}
