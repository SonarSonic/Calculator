package sonar.calculator.mod;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
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
import sonar.calculator.mod.common.block.misc.CalculatorScreen;
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
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculatorScreen;
import sonar.calculator.mod.common.tileentity.misc.TileEntityGasLantern;
import sonar.calculator.mod.common.tileentity.misc.TileEntityMagneticFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityRainSensor;
import sonar.calculator.mod.common.tileentity.misc.TileEntityReinforcedChest;
import sonar.calculator.mod.common.tileentity.misc.TileEntityScarecrow;
import sonar.core.SonarRegister;
import sonar.core.common.block.ConnectedBlock;
import sonar.core.common.block.SonarBlockTip;
import sonar.core.common.block.SonarStairs;
import sonar.core.registries.SonarRegistryBlock;
import sonar.core.registries.SonarRegistryMetablock;

public class CalculatorBlocks extends Calculator {

	public static void registerBlocks() {

		flawlessGlass = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "FlawlessGlass", new ConnectedBlock.Glass(Material.GLASS, 3)).setLightLevel(0.625F).setHardness(0.6F);
		purifiedObsidian = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "PurifiedObsidian", new PurifiedObsidian(Material.ROCK, 4)).setBlockUnbreakable().setResistance(6000000.0F);

		/* stablestonerimmedBlock = new ConnectedBlock.StableRimmed().setUnlocalizedName("stablestonerimmedBlock").setCreativeTab(Calculator).setHardness(2.0F); GameRegistry.SonarRegister.addBlock(CalculatorConstants.MODID, tab, stablestonerimmedBlock, ItemMetaBlock.class, "stablestonerimmedBlock"); stablestonerimmedblackBlock = new ConnectedBlock.StableBlackRimmed().setUnlocalizedName("stablestonerimmedblackBlock").setCreativeTab(Calculator).setHardness(2.0F); GameRegistry.SonarRegister.addBlock(CalculatorConstants.MODID, tab, stablestonerimmedblackBlock, ItemMetaBlock.class, "stablestonerimmedblackBlock"); stableglassBlock = new ConnectedBlock.StableGlass("stablestone_glass", 3).setBlockName("StableGlass").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(0.6F); GameRegistry.SonarRegister.addBlock(CalculatorConstants.MODID, tab, stableglassBlock, SonarBlockTip.class, "StableGlass"); clearstableglassBlock = new ConnectedBlock.StableGlass("stablestone_clear", 4).setBlockName("ClearStableGlass").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(0.6F); GameRegistry.SonarRegister.addBlock(CalculatorConstants.MODID, tab, clearstableglassBlock, SonarBlockTip.class, "ClearStableGlass"); flawlessGlass = new ConnectedBlock(Material.glass, "flawlessglass", 1, false).setBlockName("FlawlessGlass").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(0.6F); GameRegistry.SonarRegister.addBlock(CalculatorConstants.MODID, tab, flawlessGlass, SonarBlockTip.class, "FlawlessGlass"); */
		// calculators
		powerCube = SonarRegister.addBlock(CalculatorConstants.MODID, tab, new SonarRegistryBlock(new PowerCube(), "PowerCube", TileEntityPowerCube.class).setProperties());
		advancedPowerCube = SonarRegister.addBlock(CalculatorConstants.MODID, tab, new SonarRegistryBlock(new AdvancedPowerCube(), "AdvancedPowerCube", TileEntityAdvancedPowerCube.class).setProperties());
		creativePowerCube = SonarRegister.addBlock(CalculatorConstants.MODID, tab, new SonarRegistryBlock(new CreativePowerCube(), "CreativePowerCube", TileEntityCreativePowerCube.class).setProperties());
		atomicCalculator = SonarRegister.addBlock(CalculatorConstants.MODID, tab, new SonarRegistryBlock(new AtomicCalculator(), "AtomicCalculator", TileEntityCalculator.Atomic.class).setProperties());
		dynamicCalculator = SonarRegister.addBlock(CalculatorConstants.MODID, tab, new SonarRegistryBlock(new DynamicCalculator(), "DynamicCalculator", TileEntityCalculator.Dynamic.class).setProperties());
		moduleWorkstation = SonarRegister.addBlock(CalculatorConstants.MODID, tab, new SonarRegistryBlock(new ModuleWorkstation(), "ModuleWorkstation", TileEntityModuleWorkstation.class).setProperties());

		// smelting

		reinforcedFurnace = SonarRegister.addBlock(CalculatorConstants.MODID, tab, new SonarRegistryBlock(new SmeltingBlock(BlockTypes.FURNACE), "ReinforcedFurnace", TileEntityMachine.ReinforcedFurnace.class).setProperties());
		reinforcedChest = SonarRegister.addBlock(CalculatorConstants.MODID, tab, new SonarRegistryBlock(new ReinforcedChest(), "ReinforcedChest", TileEntityReinforcedChest.class).setProperties());
		
		
		// flawlessFurnace = new FlawlessFurnace().setUnlocalizedName("FlawlessFurnace").setCreativeTab(Calculator).setHardness(1.0F).setResistance(20.0F);
		// GameRegistry.SonarRegister.addBlock(CalculatorConstants.MODID, tab, flawlessFurnace, SonarBlockTip.class, "FlawlessFurnace");
		// GameRegistry.registerTileEntity(TileEntityFlawlessFurnace.class, "FlawlessFurnace");

		stoneSeparator = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "StoneSeparator", new SmeltingBlock(BlockTypes.STONE).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.StoneSeparator.class, "StoneSeparator");
		algorithmSeparator = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "AlgorithmSeparator", new SmeltingBlock(BlockTypes.ALGORITHM).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.AlgorithmSeparator.class, "AlgorithmSeparator");
		hungerProcessor = SonarRegister.addBlock(CalculatorConstants.MODID, tab, new SonarRegistryBlock(new HungerProcessor(), "HungerProcessor", TileEntityHungerProcessor.class).setProperties());
		healthProcessor = SonarRegister.addBlock(CalculatorConstants.MODID, tab, new SonarRegistryBlock(new HealthProcessor(), "HealthProcessor", TileEntityHealthProcessor.class).setProperties());

		amethystPiping = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "AmethystPiping", new Piping.Amethyst().setHardness(1.0F).setResistance(20.0F));
		// GameRegistry.registerTileEntity(TileEntityHungerProcessor.class, "AmethystPiping");

		tanzanitePiping = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "TanzanitePiping", new Piping.Tanzanite().setHardness(1.0F).setResistance(20.0F));

		// dockingStation = new DockingStation().setUnlocalizedName("DockingStation").setCreativeTab(Calculator).setHardness(1.5F); GameRegistry.SonarRegister.addBlock(CalculatorConstants.MODID, tab, dockingStation, SonarBlockTip.class, "DockingStation").setBlockTextureName(CalculatorConstants.MODID + ":" + "reinforcedstone"); GameRegistry.registerTileEntity(TileEntityDockingStation.class, "DockingStation"); */

		dockingStation = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "DockingStation", new DockingStation().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityDockingStation.class, "DockingStation");

		atomicMultiplier = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "AtomicMultiplier", new AtomicMultiplier().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAtomicMultiplier.class, "AtomicMultiplier");

		extractionChamber = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "ExtractionChamber", new SmeltingBlock.ChamberBlock(BlockTypes.EXTRACTION).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.ExtractionChamber.class, "ExtractionChamber");
		restorationChamber = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "RestorationChamber", new SmeltingBlock.ChamberBlock(BlockTypes.RESTORATION).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.RestorationChamber.class, "RestorationChamber");
		reassemblyChamber = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "ReassemblyChamber", new SmeltingBlock.ChamberBlock(BlockTypes.REASSEMBLY).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.ReassemblyChamber.class, "ReassemblyChamber");
		precisionChamber = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "PrecisionChamber", new SmeltingBlock.ChamberBlock(BlockTypes.PRECISION).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.PrecisionChamber.class, "PrecisionChamber");
		processingChamber = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "ProcessingChamber", new SmeltingBlock.ChamberBlock(BlockTypes.PROCESSING).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMachine.ProcessingChamber.class, "ProcessingChamber");
		analysingChamber = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "AnalysingChamber", new AnalysingChamber().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAnalysingChamber.class, "AnalysingChamber");
		storageChamber = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "StorageChamber", new StorageChamber().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityStorageChamber.class, "StorageChamber");
		fabricationChamber = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "FabricationChamber", new FabricationChamber().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityFabricationChamber.class, "FabricationChamber");
		// researchChamber = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "ResearchChamber", new ResearchChamber().setHardness(1.0F).setResistance(20.0F));
		// GameRegistry.registerTileEntity(TileEntityResearchChamber.class, "ResearchChamber");

		// machines
		basicGreenhouse = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "BasicGreenhouse", new Greenhouse.Basic().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityBasicGreenhouse.class, "BasicGreenhouse");
		advancedGreenhouse = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "AdvancedGreenhouse", new Greenhouse.Advanced().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAdvancedGreenhouse.class, "AdvancedGreenhouse");
		flawlessGreenhouse = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "FlawlessGreenhouse", new FlawlessGreenhouse().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityFlawlessGreenhouse.class, "FlawlessGreenhouse");
		CO2Generator = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "CO2Generator", new CO2Generator().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCO2Generator.class, "CO2Generator");
		/* teleporter = new Teleporter().setUnlocalizedName("CalculatorTeleporter").setCreativeTab(Calculator).setHardness(1.0F).setLightLevel(0.625F).setBlockTextureName(CalculatorConstants.MODID + ":" + "stablestone"); GameRegistry.SonarRegister.addBlock(CalculatorConstants.MODID, tab, teleporter, SonarBlockTip.class, "CalculatorTeleporter"); GameRegistry.registerTileEntity(TileEntityTeleporter.class, "CalculatorTeleporter"); */
		calculatorlocator = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "CalculatorLocator", new CalculatorLocator().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCalculatorLocator.class, "CalculatorLocator");
		calculatorplug = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "CalculatorPlug", new CalculatorPlug().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCalculatorPlug.class, "CalculatorPlug");
		// energy //flawlessCapacitor = new FlawlessCapacitor().setUnlocalizedName("FlawlessCapacitor").setCreativeTab(Calculator).setLightLevel(0.625F).setHardness(6.5F).setBlockTextureName(CalculatorConstants.MODID + ":" + // "electric_diamond_block"); // GameRegistry.SonarRegister.addBlock(CalculatorConstants.MODID, tab, flawlessCapacitor,SonarBlockTip.class, "FlawlessCapacitor"); // GameRegistry.registerTileEntity(TileEntityFlawlessCapacitor.class, "FlawlessCapacitor");

		// generators
		conductorMast = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "ConductorMast", new ConductorMast().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityConductorMast.class, "ConductorMast");
		conductormastBlock = SonarRegister.addBlock(CalculatorConstants.MODID, "ConductorMastBlock", new InvisibleBlock(0).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));

		weatherStation = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "WeatherStation", new WeatherStation().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityWeatherStation.class, "WeatherStation");
		weatherStationBlock = SonarRegister.addBlock(CalculatorConstants.MODID, "WeatherStationBlock", new InvisibleBlock(1).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));

		transmitter = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "Transmitter", new Transmitter().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityTransmitter.class, "Transmitter");
		transmitterBlock = SonarRegister.addBlock(CalculatorConstants.MODID, "TransmitterBlock", new InvisibleBlock(2).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));

		starchextractor = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "StarchExtractor", new ExtractorBlock(0).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityGenerator.StarchExtractor.class, "StarchExtractor");
		redstoneextractor = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "RedstoneExtractor", new ExtractorBlock(1).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityGenerator.RedstoneExtractor.class, "RedstoneExtractor");
		glowstoneextractor = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "GlowstoneExtractor", new ExtractorBlock(2).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityGenerator.GlowstoneExtractor.class, "GlowstoneExtractor");
		handcrankedGenerator = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "HandCrankedGenerator", new CrankedGenerator().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCrankedGenerator.class, "HandCrankedGenerator");

		crankHandle = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "CrankHandle", new CrankHandle().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityCrankHandle.class, "CrankHandle");
		/* crank = new CrankHandle().setUnlocalizedName("Crank").setCreativeTab(Calculator).setHardness(20.0F); GameRegistry.SonarRegister.addBlock(CalculatorConstants.MODID, tab, crank, SonarBlockTip.class, "Crank"); GameRegistry.registerTileEntity(TileEntityCrankHandle.class, "Crank"); */
		magneticFlux = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "MagneticFlux", new MagneticFlux().setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityMagneticFlux.class, "MagneticFlux");
		weatherController = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "WeatherController", new WeatherController().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityWeatherController.class, "WeatherController");
		rainSensor = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "RainSensor", new RainSensor().setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityRainSensor.class, "RainSensor");

		stoneAssimilator = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "StoneAssimilator", new Assimilator(0).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAssimilator.Stone.class, "StoneAssimilator");
		algorithmAssimilator = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "AlgorithmAssimilator", new Assimilator(1).setLightLevel(0.625F).setHardness(1.0F).setResistance(20.0F));
		GameRegistry.registerTileEntity(TileEntityAssimilator.Algorithm.class, "AlgorithmAssimilator");

		// misc

		gas_lantern_off = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "GasLanternOff", new GasLantern(false).setHardness(0.1F).setLightOpacity(100));
		gas_lantern_on = new GasLantern(true).setHardness(0.1F).setLightLevel(0.9375F).setUnlocalizedName("GasLanternOn").setRegistryName(CalculatorConstants.MODID, "GasLanternOn");
		ForgeRegistries.BLOCKS.register(gas_lantern_on);
		ForgeRegistries.ITEMS.register(new SonarBlockTip(gas_lantern_on).setRegistryName(CalculatorConstants.MODID, "GasLanternOn"));

		basic_lantern = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "Lantern", new BasicLantern().setHardness(0.1F).setLightLevel(0.9375F).setLightOpacity(100));
		GameRegistry.registerTileEntity(TileEntityGasLantern.class, "Lantern");

		scarecrow = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "Scarecrow", new Scarecrow().setHardness(0.5F).setResistance(24.0F));
		GameRegistry.registerTileEntity(TileEntityScarecrow.class, "Scarecrow");
		scarecrowBlock = SonarRegister.addBlock(CalculatorConstants.MODID, "ScarecrowBlock", new ScarecrowBlock().setHardness(0.5F).setResistance(24.0F));

		// amethyst
		amethystLog = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "AmethystLog", new CalculatorLogs());
		amethystPlanks = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "AmethystPlanks", new CalculatorPlanks());
		amethystStairs = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "AmethystStairs", new SonarStairs(amethystPlanks));
		amethystFence = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "AmethystFence", new BlockFence(Material.WOOD, MapColor.PURPLE));
		amethystLeaves = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "AmethystLeaves", new CalculatorLeaves(0));
		amethystSapling = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "AmethystSapling", new CalculatorSaplings(0));

		tanzaniteLog = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "TanzaniteLog", new CalculatorLogs());
		tanzanitePlanks = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "TanzanitePlanks", new CalculatorPlanks());
		tanzaniteStairs = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "TanzaniteStairs", new SonarStairs(tanzanitePlanks));
		tanzaniteFence = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "TanzaniteFence", new BlockFence(Material.WOOD, MapColor.BLUE));
		tanzaniteLeaves = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "TanzaniteLeaves", new CalculatorLeaves(1));
		tanzaniteSapling = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "TanzaniteSapling", new CalculatorSaplings(1));

		pearLog = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "PearLog", new CalculatorLogs());
		pearPlanks = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "PearPlanks", new CalculatorPlanks());
		pearStairs = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "PearStairs", new SonarStairs(pearPlanks));
		pearFence = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "PearFence", new BlockFence(Material.WOOD, MapColor.BROWN));
		pearLeaves = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "PearLeaves", new CalculatorLeaves(2));
		pearSapling = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "PearSapling", new CalculatorSaplings(2));

		diamondLog = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "DiamondLog", new CalculatorLogs());
		diamondPlanks = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "DiamondPlanks", new CalculatorPlanks());
		diamondStairs = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "DiamondStairs", new SonarStairs(diamondPlanks));
		diamondFence = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "DiamondFence", new BlockFence(Material.WOOD, MapColor.CYAN));
		diamondLeaves = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "DiamondLeaves", new CalculatorLeaves(3));
		diamondSapling = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "DiamondSapling", new CalculatorSaplings(3));
		calculatorScreen = SonarRegister.addBlock(CalculatorConstants.MODID, new SonarRegistryBlock(new CalculatorScreen(), "CalculatorScreenBlock", TileEntityCalculatorScreen.class).setProperties());
		material_block = SonarRegister.addBlock(CalculatorConstants.MODID, tab, new SonarRegistryMetablock(new MaterialBlock(), "Material"));
		cropBroccoliPlant = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "CropBroccoli", new CalculatorCrops(0, 0));
		cropPrunaePlant = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "CropPrunae", new CalculatorCrops(1, 2));
		cropFiddledewPlant = SonarRegister.addBlock(CalculatorConstants.MODID, tab, "CropFiddledew", new CalculatorCrops(2, 3));
	}
}
