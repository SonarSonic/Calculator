package sonar.calculator.mod.guide;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.calculator.mod.guide.IItemInfo.Category;
import sonar.core.helpers.ItemStackHelper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class InfoRegistry {

	private static final InfoRegistry instance = new InfoRegistry();

    public LinkedHashMap<Category, ArrayList<IItemInfo>> info = new LinkedHashMap<>();

	public InfoRegistry getInstance() {
		return new InfoRegistry();
	}

	public InfoRegistry() {
		addInfo(Category.Calculators, new ItemStack(Calculator.itemCalculator), "info.Calculator.name", Calculator.powerCube, Calculator.researchChamber, Calculator.itemScientificCalculator);
		addInfo(Category.Calculators, new ItemStack(Calculator.itemCraftingCalculator), "info.CraftingCalculator.name", Calculator.powerCube);
		addInfo(Category.Calculators, new ItemStack(Calculator.itemScientificCalculator), "info.ScientificCalculator.name", Calculator.powerCube, Calculator.atomicCalculator);
		addInfo(Category.Calculators, new ItemStack(Calculator.itemFlawlessCalculator), "info.FlawlessCalculator.name", Calculator.powerCube);
		addInfo(Category.Calculators, new ItemStack(Calculator.atomicCalculator), "info.AtomicCalculator.name");
		addInfo(Category.Calculators, new ItemStack(Calculator.dynamicCalculator), "info.DynamicCalculator.name");
		addInfo(Category.Calculators, new ItemStack(Calculator.itemInfoCalculator), "info.InfoCalculator.name");

		addInfo(Category.Modules, new ItemStack(Calculator.itemTerrainModule), "info.BasicTerrainModule.name", Calculator.itemAdvancedTerrainModule);
		addInfo(Category.Modules, new ItemStack(Calculator.itemAdvancedTerrainModule), "info.AdvancedTerrainModule.name", Calculator.itemTerrainModule);
        addInfo(Category.Modules, new ItemStack(Calculator.itemEnergyModule), "Can store up to " + CalculatorConfig.getInteger("Energy Module") + " RF" + "or " + CalculatorConfig.getInteger("Energy Module") / 4 + " EU", Calculator.powerCube, Calculator.itemFlawlessCalculator);
		addInfo(Category.Modules, new ItemStack(Calculator.itemStorageModule), "info.StorageModule.name", Calculator.itemFlawlessCalculator);
		addInfo(Category.Modules, new ItemStack(Calculator.itemLocatorModule), "info.LocatorModule.name", Calculator.calculatorlocator);
		addInfo(Category.Modules, new ItemStack(Calculator.itemJumpModule), "info.JumpModule.name", Calculator.itemFlawlessCalculator);
		addInfo(Category.Modules, new ItemStack(Calculator.itemWarpModule), "info.WarpModule.name", Calculator.itemFlawlessCalculator);
		addInfo(Category.Modules, new ItemStack(Calculator.moduleWorkstation), "info.ModuleWorkstation.name", Calculator.itemFlawlessCalculator);

		/* addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1, 1), "Used for Controlled Fuel-Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits"); addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1, 2), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits"); addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1, 3), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits"); addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1, 4), "Used for Speed Upgrades-Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits"); addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1, 5), "Used for Energy Upgrades-Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits"); addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1, 6), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits"); addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1, 7), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits"); addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1, 8), "Crafting item for CO2 Generator-Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits"); addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1, 9), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits"); addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1, 10), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits"); addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1, 11), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits"); addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1, 12), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits"); addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1, 13), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits"); */
		// addInfo(Category.Blocks, new ItemStack(Calculator.stableStone), "Part of the Calculator Locator-Teleporting(see Flawless Calc)-Right Click to set location-Right click then teleports you-Remove to change location");

		addInfo(Category.Circuits, new ItemStack(Calculator.circuitBoard, 1), "info.CircuitBoard.name", Calculator.circuitDamaged, Calculator.circuitDirty, Calculator.analysingChamber, Calculator.storageChamber, Calculator.fabricationChamber, Calculator.calculatorlocator);
		addInfo(Category.Circuits, new ItemStack(Calculator.circuitDamaged, 1), "info.DirtyCircuit.name",Calculator.precisionChamber, Calculator.extractionChamber,Calculator.reassemblyChamber,Calculator.processingChamber, Calculator.storageChamber, Calculator.circuitBoard);
		addInfo(Category.Circuits, new ItemStack(Calculator.circuitDirty, 1), "info.DamagedCircuit.name", Calculator.precisionChamber, Calculator.extractionChamber,Calculator.restorationChamber,Calculator.processingChamber, Calculator.storageChamber, Calculator.circuitBoard);
		addInfo(Category.Circuits, new ItemStack(Calculator.atomic_assembly), "info.AtomicAssembly.name", Calculator.atomicCalculator, Calculator.atomicMultiplier);
		addInfo(Category.Circuits, new ItemStack(Calculator.energyUpgrade), "info.EnergyUpgrade.name",Calculator.speedUpgrade,Calculator.voidUpgrade,Calculator.transferUpgrade);
		addInfo(Category.Circuits, new ItemStack(Calculator.speedUpgrade), "info.SpeedUpgrade.name",Calculator.energyUpgrade,Calculator.voidUpgrade,Calculator.transferUpgrade);
		addInfo(Category.Circuits, new ItemStack(Calculator.voidUpgrade), "info.VoidUpgrade.name",Calculator.energyUpgrade,Calculator.speedUpgrade,Calculator.transferUpgrade);
		addInfo(Category.Circuits, new ItemStack(Calculator.transferUpgrade), "info.TransferUpgrade.name",Calculator.energyUpgrade,Calculator.speedUpgrade,Calculator.voidUpgrade);

		addInfo(Category.Nutrition, new ItemStack(Calculator.scarecrow), "info.Scarecrow.name", Calculator.basicGreenhouse, Calculator.advancedGreenhouse, Calculator.flawlessGreenhouse);
		addInfo(Category.Nutrition, new ItemStack(Calculator.amethystSapling), "info.AmethystSapling.name", Calculator.hungerProcessor);
		addInfo(Category.Nutrition, new ItemStack(Calculator.tanzaniteSapling), "info.TanzaniteSapling.name", Calculator.healthProcessor);
		addInfo(Category.Nutrition, new ItemStack(Calculator.pearSapling), "info.PearSapling.name", Calculator.rotten_pear);
		addInfo(Category.Nutrition, new ItemStack(Calculator.diamondSapling), "info.DiamondSapling.name");
		addInfo(Category.Nutrition, new ItemStack(Calculator.fiddledewFruit), "info.FiddledewFruit.name", Calculator.flawlessGreenhouse);
		addInfo(Category.Nutrition, new ItemStack(Calculator.prunaeSeeds), "info.PrunaeSeeds.name", Calculator.advancedGreenhouse);
		addInfo(Category.Nutrition, new ItemStack(Calculator.broccoli), "info.Broccoli.name", Calculator.cookedBroccoli);
		addInfo(Category.Nutrition, new ItemStack(Calculator.cookedBroccoli), "info.CookedBroccoli.name",Calculator.broccoli);
		addInfo(Category.Nutrition, new ItemStack(Calculator.rotten_pear), "info.RottenPear.name",Calculator.pearSapling);
		addInfo(Category.Nutrition, new ItemStack(Calculator.amethystPiping), "info.AmethystPiping.name", Calculator.amethystSapling, Calculator.hungerProcessor);
		addInfo(Category.Nutrition, new ItemStack(Calculator.tanzanitePiping), "info.TanzanitePiping.name",Calculator.tanzaniteSapling, Calculator.healthProcessor);
		addInfo(Category.Nutrition, new ItemStack(Calculator.hungerProcessor), "info.HungerProcessor.name", Calculator.amethystPiping);
		addInfo(Category.Nutrition, new ItemStack(Calculator.healthProcessor), "info.HealthProcessor.name", Calculator.tanzanitePiping);
		addInfo(Category.Nutrition, new ItemStack(Calculator.itemHungerModule), "info.HungerModule.name",Calculator.amethystSapling, Calculator.hungerProcessor, Calculator.itemNutritionModule);
		addInfo(Category.Nutrition, new ItemStack(Calculator.itemHealthModule), "info.HealthModule.name", Calculator.tanzaniteSapling, Calculator.healthProcessor, Calculator.itemNutritionModule);
		addInfo(Category.Nutrition, new ItemStack(Calculator.itemNutritionModule), "info.NutritionModule.name", Calculator.itemHealthModule, Calculator.itemHungerModule, Calculator.amethystSapling,Calculator.tanzaniteSapling, Calculator.hungerProcessor, Calculator.healthProcessor);
		addInfo(Category.Nutrition, new ItemStack(Calculator.basicGreenhouse), "info.BasicGreenhouse.name",Calculator.advancedGreenhouse);
		addInfo(Category.Nutrition, new ItemStack(Calculator.advancedGreenhouse), "info.AdvancedGreenhouse.name", Calculator.flawlessGreenhouse, Calculator.prunaeSeeds);
		addInfo(Category.Nutrition, new ItemStack(Calculator.flawlessGreenhouse), "info.FlawlessGreenhouse.name", Calculator.flawlessGlass, Calculator.fiddledewFruit);
		addInfo(Category.Nutrition, new ItemStack(Calculator.CO2Generator), "info.CarbonDioxideGenerator.name");

		addInfo(Category.Generation, new ItemStack(Calculator.handcrankedGenerator), "info.HandCrankedGenerator.name",Calculator.crankHandle);
		addInfo(Category.Generation, new ItemStack(Calculator.crankHandle), "info.CrankHandle.name",Calculator.handcrankedGenerator);
		addInfo(Category.Generation, new ItemStack(Calculator.starchextractor), "info.StarchExtractor.name",Calculator.redstoneextractor,Calculator.glowstoneextractor);
		addInfo(Category.Generation, new ItemStack(Calculator.redstoneextractor), "info.RedstoneExtractor.name",Calculator.starchextractor,Calculator.glowstoneextractor);
		addInfo(Category.Generation, new ItemStack(Calculator.glowstoneextractor), "info.GlowstoneExtractor.name",Calculator.starchextractor,Calculator.redstoneextractor);
		addInfo(Category.Generation, new ItemStack(Calculator.conductorMast), "info.ConductorMast.name", Calculator.transmitter, Calculator.weatherStation);
		addInfo(Category.Generation, new ItemStack(Calculator.weatherStation), "info.WeatherStation.name", Calculator.conductorMast, Calculator.transmitter);
		addInfo(Category.Generation, new ItemStack(Calculator.transmitter), "info.Transmitter.name",Calculator.conductorMast, Calculator.weatherStation);
		addInfo(Category.Generation, new ItemStack(Calculator.calculatorlocator), "info.CalculatorLocator.name",Calculator.calculatorplug, Calculator.itemLocatorModule);
		addInfo(Category.Generation, new ItemStack(Calculator.calculatorplug), "info.CalculatorPlug.name",Calculator.circuitBoard,Calculator.analysingChamber, Calculator.calculatorlocator);

		addInfo(Category.Machines, new ItemStack(Calculator.powerCube), "info.PowerCube.name", Calculator.advancedPowerCube);
		addInfo(Category.Machines, new ItemStack(Calculator.advancedPowerCube), "info.AdvancedPowerCube.name", Calculator.powerCube);
		addInfo(Category.Machines, new ItemStack(Calculator.reinforcedFurnace), "info.ReinforcedFurnace.name");
		addInfo(Category.Machines, new ItemStack(Calculator.stoneSeparator), "info.StoneSeparator.name", Calculator.amethystSapling);
		addInfo(Category.Machines, new ItemStack(Calculator.algorithmSeparator), "info.AlgorithmSeparator.name", Calculator.tanzaniteSapling);
		addInfo(Category.Machines, new ItemStack(Calculator.researchChamber), "info.FabricationChamber.name");
		addInfo(Category.Machines, new ItemStack(Calculator.extractionChamber), "info.ExtractionChamber.name", Calculator.precisionChamber);
		addInfo(Category.Machines, new ItemStack(Calculator.reassemblyChamber), "info.ReassemblyChamber.name", Calculator.restorationChamber, Calculator.processingChamber);
		addInfo(Category.Machines, new ItemStack(Calculator.restorationChamber), "info.RestorationChamber.name", Calculator.reassemblyChamber, Calculator.processingChamber);
		addInfo(Category.Machines, new ItemStack(Calculator.precisionChamber), "info.PrecisionChamber.name", Calculator.restorationChamber);
		addInfo(Category.Machines, new ItemStack(Calculator.processingChamber), "info.ProcessingChamber.name", Calculator.restorationChamber, Calculator.reassemblyChamber);
		addInfo(Category.Machines, new ItemStack(Calculator.analysingChamber), "info.AnalysingChamber.name", Calculator.circuitBoard);
		addInfo(Category.Machines, new ItemStack(Calculator.storageChamber), "info.StorageChamber.name");
		addInfo(Category.Machines, new ItemStack(Calculator.fabricationChamber), "info.FabricationChamber.name");
		addInfo(Category.Machines, new ItemStack(Calculator.rainSensor), "info.RainSensor.name");
		addInfo(Category.Machines, new ItemStack(Calculator.weatherStation), "Has the ability to-control the weather");
		addInfo(Category.Machines, new ItemStack(Calculator.magneticFlux), "info.MagneticFlux.name");
		addInfo(Category.Machines, new ItemStack(Calculator.teleporter), "A simple teleporter -+Can be password protected");
		addInfo(Category.Machines, new ItemStack(Calculator.stoneAssimilator), "Harvests Calculator Trees-+Must be placed next to the Tree-+For Tanzanite and Amethyst-+Doesn't Require Energy");
		addInfo(Category.Machines, new ItemStack(Calculator.algorithmAssimilator), "Harvests Calculator Trees-+Must be placed next to the Tree-+For Pear and Diamond-+Doesn't Require Energy");
        addInfo(Category.Machines, new ItemStack(Calculator.atomicMultiplier), "Can quadruple almost any item--Requires seven circuits-and " + TileEntityAtomicMultiplier.requiredEnergy + "RF--or " + TileEntityAtomicMultiplier.requiredEnergy / 4 + "EU");

		addInfo(Category.Tools, new ItemStack(Calculator.wrench), "info.Wrench.name");
		addInfo(Category.Tools, new ItemStack(Calculator.sickle), "Can remove berries/diamonds- from their trees");
		addInfo(Category.Tools, new ItemStack(Calculator.obsidianKey), "See Purified Obsidian");
		addInfo(Category.Tools, new ItemStack(Calculator.endforged_sword), "The GOD of Swords", Calculator.endDiamond);
		addInfo(Category.Tools, new ItemStack(Calculator.endforged_hoe), "Your only desire", Calculator.endDiamond);

		addInfo(Category.Items, new ItemStack(Calculator.baby_grenade), "info.BabyGrenade.name");
		addInfo(Category.Items, new ItemStack(Calculator.grenade), "info.Grenade.name");
		addInfo(Category.Items, new ItemStack(Calculator.soil), "Can blind entities when thrown--Can change Dirt to Gravel");
		addInfo(Category.Items, new ItemStack(Calculator.small_stone), "Can damage entities when thrown--Can change Dirt to Farmland");
		addInfo(Category.Items, new ItemStack(Calculator.endDiamond), "If you want Ender Pearls-Look no further than Right Click--Makes unbreakable tools",Calculator.endforged_sword);
		addInfo(Category.Items, new ItemStack(Calculator.controlled_Fuel), "Controls its own burning--Only works in-Carbon Dioxide Generator", Calculator.CO2Generator);
		addInfo(Category.Items, new ItemStack(Calculator.gas_lantern_off), "info.GasLantern.name",Calculator.basic_lantern);
		addInfo(Category.Items, new ItemStack(Calculator.basic_lantern), "info.BasicLantern.name", Calculator.gas_lantern_off);
		addInfo(Category.Items, new ItemStack(Calculator.calculator_screen), "Displays the RF stored-in machines when placed-on the side");

		addInfo(Category.Blocks, new ItemStack(Calculator.purifiedObsidian), "Indestructable/Security block--Only removable with Obsidian Key", Calculator.obsidianKey);
		addInfo(Category.Blocks, new ItemStack(Calculator.flawlessGlass), "V. Expensive Glass--Used in Flawless Greenhouses", Calculator.flawlessGreenhouse);
		addInfo(Category.Blocks, new ItemStack(Calculator.reinforcedChest), "info.ReinforcedChest.name");
		// addInfo(Category.Blocks, new ItemStack(Calculator.fluxPlug), "Adds flux to Flux Network");
		// addInfo(Category.Blocks, new ItemStack(Calculator.fluxPoint), "Removes flux from Flux Network");
		// addInfo(Category.Machines, new ItemStack(Calculator.fluxController), "Controls energy flow-in a Flux Network");
	}

	public void addInfo(Category category, ItemStack stack, String info, Object... objs) {
		if (stack == null || stack.getItem() == null) {
			return;
		}
		try {
            this.info.putIfAbsent(category, new ArrayList<>());
			ItemStack[] stacks = new ItemStack[objs.length];
			int pos = 0;
			for (Object obj : objs) {
				stacks[pos] = ItemStackHelper.createStack(obj);
				pos++;
			}
			this.info.get(category).add(new ItemInfo(category, stack, info, stacks));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<IItemInfo> getInfo(Category category) {
        ArrayList<IItemInfo> info = new ArrayList<>();
		for (Entry<Category, ArrayList<IItemInfo>> entry : instance.info.entrySet()) {
			if (category == Category.All || entry.getKey() == category) {
				info.addAll(entry.getValue());
			}
		}
		return info;
	}
}
