package sonar.calculator.mod.network;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import sonar.calculator.mod.BlockRenderRegister;
import sonar.calculator.mod.ItemRenderRegister;
import sonar.calculator.mod.client.renderers.RenderAnalysingChamber;
import sonar.calculator.mod.client.renderers.RenderChamber;
import sonar.calculator.mod.client.renderers.RenderCrank;
import sonar.calculator.mod.client.renderers.RenderFabricationChamber;
import sonar.calculator.mod.client.renderers.RenderHandlers;
import sonar.calculator.mod.client.renderers.RenderMagneticFlux;
import sonar.calculator.mod.client.renderers.RenderReinforcedChest;
import sonar.calculator.mod.client.renderers.RenderWeatherStation;
import sonar.calculator.mod.common.entities.EntityBabyGrenade;
import sonar.calculator.mod.common.entities.EntityGrenade;
import sonar.calculator.mod.common.entities.EntitySmallStone;
import sonar.calculator.mod.common.entities.EntitySoil;
import sonar.calculator.mod.common.tileentity.TileEntityMachine;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFabricationChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityTransmitter;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherStation;
import sonar.calculator.mod.common.tileentity.misc.TileEntityMagneticFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityReinforcedChest;
import sonar.calculator.mod.common.tileentity.misc.TileEntityScarecrow;

public class CalculatorClient extends CalculatorCommon{
	
	
	@Override
	public void registerRenderThings() {
		BlockRenderRegister.register();
		ItemRenderRegister.register();
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, RenderThrowables.GRENADE);
		RenderingRegistry.registerEntityRenderingHandler(EntityBabyGrenade.class, RenderThrowables.BABY_GRENADE);
		RenderingRegistry.registerEntityRenderingHandler(EntitySoil.class, RenderThrowables.SOIL);
		RenderingRegistry.registerEntityRenderingHandler(EntitySmallStone.class, RenderThrowables.SMALL_STONE);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrankHandle.class, new RenderCrank());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnalysingChamber.class, new RenderAnalysingChamber());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFabricationChamber.class, new RenderFabricationChamber());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReinforcedChest.class, new RenderReinforcedChest());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConductorMast.class, new RenderHandlers.ConductorMast());
		//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalculatorLocator.class, new RenderCalculatorLocatorBeam());
		/*
		TileEntitySpecialRenderer plug = new RenderCalculatorPlug();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalculatorPlug.class, plug);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.calculatorplug), new ItemCalculatorPlug(plug, new TileEntityCalculatorPlug()));

		TileEntitySpecialRenderer locator = new RenderCalculatorLocator();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalculatorLocator.class, locator);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.calculatorlocator), new ItemModelRender(locator, new TileEntityCalculatorLocator()));

		TileEntitySpecialRenderer starch = new RenderHandlers.StarchExtractor();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGenerator.StarchExtractor.class, starch);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.starchextractor), new ItemStarchExtractor(starch, new TileEntityGenerator.StarchExtractor()));

		TileEntitySpecialRenderer crank = new RenderCrank();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrankHandle.class, crank);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.crank), new ItemCrankHandle(crank, new TileEntityCrankHandle()));

		TileEntitySpecialRenderer glowstone = new RenderHandlers.GlowstoneExtractor();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGenerator.GlowstoneExtractor.class, glowstone);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.glowstoneextractor), new ItemStarchExtractor(glowstone, new TileEntityGenerator.GlowstoneExtractor()));

		TileEntitySpecialRenderer redstone = new RenderHandlers.RedstoneExtractor();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGenerator.RedstoneExtractor.class, redstone);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.redstoneextractor), new ItemStarchExtractor(redstone, new TileEntityGenerator.RedstoneExtractor()));
		
		TileEntitySpecialRenderer atomic = new RenderHandlers.AtomicMultiplier();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAtomicMultiplier.class, atomic);
		*/
		//MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.atomicMultiplier), new ItemModelRender(atomic, new TileEntityAtomicMultiplier()));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConductorMast.class, new RenderHandlers.ConductorMast());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWeatherStation.class, new RenderWeatherStation());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityScarecrow.class, new RenderHandlers.Scarecrow());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransmitter.class, new RenderHandlers.Transmitter());
		
		/*
		TileEntitySpecialRenderer lantern = new RenderLantern();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGasLantern.class, lantern);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBasicLantern.class, lantern);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.gas_lantern_off), new ItemLantern(lantern, new TileEntityGasLantern()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.gas_lantern_on), new ItemLantern(lantern, new TileEntityGasLantern()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.basic_lantern), new ItemLantern(lantern, new TileEntityBasicLantern()));

		TileEntitySpecialRenderer scarecrow = new RenderHandlers.Scarecrow();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityScarecrow.class, scarecrow);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.scarecrow), new ItemScarecrow(scarecrow, new TileEntityScarecrow()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.scarecrowBlock), new ItemScarecrow(scarecrow, new TileEntityScarecrow()));

		// TileEntitySpecialRenderer research = new RenderResearchChamber();
		// ClientRegistry.bindTileEntitySpecialRenderer(TileEntityResearchChamber.class,
		// research);
		// MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.researchChamber),
		// new ItemResearchChamber(research, new TileEntityResearchChamber()));

		TileEntitySpecialRenderer weather = new RenderWeatherStation();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWeatherStation.class, weather);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.weatherStation), new ItemWeatherStation(weather, new TileEntityWeatherStation()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.weatherStationBlock), new ItemWeatherStation(weather, new TileEntityWeatherStation()));

		TileEntitySpecialRenderer transmitter = new RenderTransmitter();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransmitter.class, transmitter);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.transmitter), new ItemTransmitter(transmitter, new TileEntityTransmitter()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.transmitterBlock), new ItemTransmitter(transmitter, new TileEntityTransmitter()));

		if (CalculatorConfig.enableToolModels) {
			MinecraftForgeClient.registerItemRenderer(Calculator.endforged_sword, new ItemSword("Calculator:textures/model/end_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.electric_sword, new ItemSword("Calculator:textures/model/electric_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.firediamond_sword, new ItemSword("Calculator:textures/model/firediamond_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.flawlessdiamond_sword, new ItemSword("Calculator:textures/model/flawless_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.redstone_sword, new ItemSword("Calculator:textures/model/redstone_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.weakeneddiamond_sword, new ItemSword("Calculator:textures/model/weakeneddiamond_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.enrichedgold_sword, new ItemSword("Calculator:textures/model/gold_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.reinforcediron_sword, new ItemSword("Calculator:textures/model/iron_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.reinforced_sword, new ItemSword("Calculator:textures/model/stone_sword.png"));

			MinecraftForgeClient.registerItemRenderer(Calculator.endforged_shovel, new ItemShovel("Calculator:textures/model/end_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.electric_shovel, new ItemShovel("Calculator:textures/model/electric_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.firediamond_shovel, new ItemShovel("Calculator:textures/model/firediamond_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.flawlessdiamond_shovel, new ItemShovel("Calculator:textures/model/flawless_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.redstone_shovel, new ItemShovel("Calculator:textures/model/redstone_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.weakeneddiamond_shovel, new ItemShovel("Calculator:textures/model/weakeneddiamond_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.enrichedgold_shovel, new ItemShovel("Calculator:textures/model/gold_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.reinforcediron_shovel, new ItemShovel("Calculator:textures/model/iron_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.reinforced_shovel, new ItemShovel("Calculator:textures/model/stone_sword.png"));

			MinecraftForgeClient.registerItemRenderer(Calculator.endforged_axe, new ItemAxe("Calculator:textures/model/end_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.electric_axe, new ItemAxe("Calculator:textures/model/electric_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.firediamond_axe, new ItemAxe("Calculator:textures/model/firediamond_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.flawlessdiamond_axe, new ItemAxe("Calculator:textures/model/flawless_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.redstone_axe, new ItemAxe("Calculator:textures/model/redstone_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.weakeneddiamond_axe, new ItemAxe("Calculator:textures/model/weakeneddiamond_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.enrichedgold_axe, new ItemAxe("Calculator:textures/model/gold_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.reinforcediron_axe, new ItemAxe("Calculator:textures/model/iron_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.reinforced_axe, new ItemAxe("Calculator:textures/model/stone_sword.png"));

			MinecraftForgeClient.registerItemRenderer(Calculator.endforged_pickaxe, new ItemPickaxe("Calculator:textures/model/end_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.electric_pickaxe, new ItemPickaxe("Calculator:textures/model/electric_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.firediamond_pickaxe, new ItemPickaxe("Calculator:textures/model/firediamond_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.flawlessdiamond_pickaxe, new ItemPickaxe("Calculator:textures/model/flawless_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.redstone_pickaxe, new ItemPickaxe("Calculator:textures/model/redstone_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.weakeneddiamond_pickaxe, new ItemPickaxe("Calculator:textures/model/weakeneddiamond_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.enrichedgold_pickaxe, new ItemPickaxe("Calculator:textures/model/gold_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.reinforcediron_pickaxe, new ItemPickaxe("Calculator:textures/model/iron_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.reinforced_pickaxe, new ItemPickaxe("Calculator:textures/model/stone_sword.png"));

			MinecraftForgeClient.registerItemRenderer(Calculator.endforged_hoe, new ItemHoe("Calculator:textures/model/end_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.electric_hoe, new ItemHoe("Calculator:textures/model/electric_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.firediamond_hoe, new ItemHoe("Calculator:textures/model/firediamond_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.flawlessdiamond_hoe, new ItemHoe("Calculator:textures/model/flawless_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.redstone_hoe, new ItemHoe("Calculator:textures/model/redstone_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.weakeneddiamond_hoe, new ItemHoe("Calculator:textures/model/weakeneddiamond_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.enrichedgold_hoe, new ItemHoe("Calculator:textures/model/gold_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.reinforcediron_hoe, new ItemHoe("Calculator:textures/model/iron_sword.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.reinforced_hoe, new ItemHoe("Calculator:textures/model/stone_sword.png"));

			MinecraftForgeClient.registerItemRenderer(Calculator.wrench, new ItemWrench("Calculator:textures/model/tool.png"));
			MinecraftForgeClient.registerItemRenderer(Calculator.sickle, new ItemSickle("Calculator:textures/model/tool.png"));

		}
		TileEntitySpecialRenderer capacitor = new RenderFlawlessCapacitor();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFlawlessCapacitor.class, capacitor);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.flawlessCapacitor), new ItemModelRender(capacitor, new TileEntityFlawlessCapacitor()));

		TileEntitySpecialRenderer fluxPlug = new RenderFluxPlug();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluxPlug.class, fluxPlug);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.fluxPlug), new ItemModelRender(fluxPlug, new TileEntityFluxPlug()));

		TileEntitySpecialRenderer fluxPoint = new RenderFluxPoint();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluxPoint.class, fluxPoint);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.fluxPoint), new ItemModelRender(fluxPoint, new TileEntityFluxPoint()));

		TileEntitySpecialRenderer fluxController = new RenderHandlers.FluxController();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluxController.class, fluxController);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.fluxController), new ItemModelRender(fluxController, new TileEntityFluxController()));

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalculatorScreen.class, new RenderCalculatorScreen());

		TileEntitySpecialRenderer processing = new RenderChamber.Processing();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachines.ProcessingChamber.class, processing);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.processingChamber), new ItemModelRender(processing, new TileEntityMachines.ProcessingChamber()));
		 */
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachine.ExtractionChamber.class, new RenderChamber.Extraction());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachine.PrecisionChamber.class, new RenderChamber.Precision());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachine.ReassemblyChamber.class, new RenderChamber.Removal());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachine.RestorationChamber.class, new RenderChamber.Removal());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachine.ProcessingChamber.class, new RenderChamber.Processing());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagneticFlux.class, new RenderMagneticFlux());
		
		//MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.extractionChamber), new ItemModelRender(extraction, new TileEntityMachines.ExtractionChamber()));
		/*
		TileEntitySpecialRenderer precision = new RenderChamber.Precision();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachines.PrecisionChamber.class, precision);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.precisionChamber), new ItemModelRender(precision, new TileEntityMachines.PrecisionChamber()));

		TileEntitySpecialRenderer removal = new RenderChamber.Removal();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachines.ReassemblyChamber.class, removal);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.reassemblyChamber), new ItemModelRender(removal, new TileEntityMachines.ReassemblyChamber()));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachines.RestorationChamber.class, removal);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.restorationChamber), new ItemModelRender(removal, new TileEntityMachines.RestorationChamber()));

		TileEntitySpecialRenderer analysing = new RenderAnalysingChamber();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnalysingChamber.class, analysing);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.analysingChamber), new ItemModelRender(analysing, new TileEntityAnalysingChamber()));

		TileEntitySpecialRenderer storage = new RenderStorageChamber();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStorageChamber.class, storage);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.storageChamber), new ItemModelRender(storage, new TileEntityStorageChamber()));

		TileEntitySpecialRenderer magnetic = new RenderMagneticFlux();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagneticFlux.class, magnetic);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.magneticFlux), new ItemModelRender(magnetic, new TileEntityMagneticFlux()));

		TileEntitySpecialRenderer docking = new RenderDockingStation();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDockingStation.class, docking);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.dockingStation), new ItemModelRender(docking, new TileEntityDockingStation()));

		TileEntitySpecialRenderer teleporter = new RenderHandlers.Teleporter();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTeleporter.class, teleporter);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.teleporter), new ItemModelRender(teleporter, new TileEntityTeleporter()));

		TileEntitySpecialRenderer sAssimilator = new RenderHandlers.StoneAssimilator();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAssimilator.Stone.class, sAssimilator);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.stoneAssimilator), new ItemModelRender(sAssimilator, new TileEntityAssimilator.Stone()));

		TileEntitySpecialRenderer aAssimilator = new RenderHandlers.AlgorithmAssimilator();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAssimilator.Algorithm.class, aAssimilator);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Calculator.algorithmAssimilator), new ItemModelRender(aAssimilator, new TileEntityAssimilator.Algorithm()));
	*/
	}
}
