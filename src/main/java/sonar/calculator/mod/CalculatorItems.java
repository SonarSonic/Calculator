package sonar.calculator.mod;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import sonar.calculator.mod.common.item.calculators.*;
import sonar.calculator.mod.common.item.calculators.modules.EnergyModule;
import sonar.calculator.mod.common.item.calculators.modules.GuiModule;
import sonar.calculator.mod.common.item.calculators.modules.JumpModule;
import sonar.calculator.mod.common.item.calculators.modules.WarpModule;
import sonar.calculator.mod.common.item.misc.*;
import sonar.calculator.mod.common.item.modules.*;
import sonar.calculator.mod.common.item.tools.*;
import sonar.core.SonarRegister;
import sonar.core.common.item.*;
import sonar.core.upgrades.MachineUpgrade;

public class CalculatorItems extends Calculator {

	public enum UpgradeTypes {
        SPEED, ENERGY, VOID, TRANSFER
	}


	public static void registerItems() {

		// calculators
		itemInfoCalculator = SonarRegister.addItem(CalculatorConstants.MODID, tab, "InfoCalculator", new InfoCalculator());
		itemCalculator = SonarRegister.addItem(CalculatorConstants.MODID, tab, "Calculator", new SonarUsageModule(GuiModule.calculator, CalculatorConfig.CALCULATOR_STORAGE));
		itemCraftingCalculator = SonarRegister.addItem(CalculatorConstants.MODID, tab, "CraftingCalculator", new SonarUsageModule(GuiModule.crafting, CalculatorConfig.CRAFTING_CALCULATOR_STORAGE));
		itemScientificCalculator = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ScientificCalculator", new SonarUsageModule(GuiModule.scientific, CalculatorConfig.SCIENTIFIC_CALCULATOR_STORAGE));
		itemFlawlessCalculator = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessCalculator", new FlawlessCalculator());

		// modules
		itemStorageModule = SonarRegister.addItem(CalculatorConstants.MODID, tab, "StorageModule", new SonarModule(GuiModule.storage));
		itemWarpModule = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WarpModule", new SonarUsageModule(new WarpModule(), CalculatorConfig.WARP_MODULE_STORAGE));
		itemJumpModule = SonarRegister.addItem(CalculatorConstants.MODID, tab, "JumpModule", new SonarUsageModule(new JumpModule(), CalculatorConfig.JUMP_MODULE_STORAGE));
		itemHungerModule = SonarRegister.addItem(CalculatorConstants.MODID, tab, "HungerModule", new HungerModule());
		itemHealthModule = SonarRegister.addItem(CalculatorConstants.MODID, tab, "HealthModule", new HealthModule());
		itemNutritionModule = SonarRegister.addItem(CalculatorConstants.MODID, tab, "NutritionModule", new NutritionModule());
		itemTerrainModule = SonarRegister.addItem(CalculatorConstants.MODID, tab, "TerrainModule", new TerrainModule());
		itemAdvancedTerrainModule = SonarRegister.addItem(CalculatorConstants.MODID, tab, "AdvancedTerrainModule", new AdvancedTerrainModule());
		itemEnergyModule = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnergyModule", new SonarEnergyModule(new EnergyModule()));
		itemLocatorModule = SonarRegister.addItem(CalculatorConstants.MODID, tab, "LocatorModule", new LocatorModule());

		// misc
		soil = SonarRegister.addItem(CalculatorConstants.MODID, tab, "Soil", new Soil());
		small_stone = SonarRegister.addItem(CalculatorConstants.MODID, tab, "SmallStone", new SmallStone());

		// upgrades
		speedUpgrade = SonarRegister.addItem(CalculatorConstants.MODID, tab, "SpeedUpgrade", new MachineUpgrade());
		energyUpgrade = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnergyUpgrade", new MachineUpgrade());
		voidUpgrade = SonarRegister.addItem(CalculatorConstants.MODID, tab, "VoidUpgrade", new MachineUpgrade());
		transferUpgrade = SonarRegister.addItem(CalculatorConstants.MODID, tab, "TransferUpgrade", new MachineUpgrade());

		// calculator parts
		calculator_screen = SonarRegister.addItem(CalculatorConstants.MODID, tab, "CalculatorScreen", new CalculatorScreen());

		calculator_assembly = SonarRegister.addItem(CalculatorConstants.MODID, tab, "CalculatorAssembly", new Item());
		advanced_assembly = SonarRegister.addItem(CalculatorConstants.MODID, tab, "AdvancedAssembly", new Item());
		atomic_module = SonarRegister.addItem(CalculatorConstants.MODID, tab, "AtomicModule", new Item());
		atomic_assembly = SonarRegister.addItem(CalculatorConstants.MODID, tab, "AtomicAssembly", new Item());
		flawless_assembly = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessAssembly", new SonarItem());
		atomic_binder = SonarRegister.addItem(CalculatorConstants.MODID, tab, "AtomicBinder", new Item());

		// tools
		wrench = SonarRegister.addItem(CalculatorConstants.MODID, tab, "Wrench", new Wrench());
		sickle = SonarRegister.addItem(CalculatorConstants.MODID, tab, "Sickle", new Sickle());
		obsidianKey = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ObsidianKey", new ObsidianKey());

		// swords
		reinforced_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedSword", new CalcSword(CalculatorConfig.TOOL_REINFORCED_STONE));
		enrichedgold_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGoldSword", new CalcSword(CalculatorConfig.TOOL_ENRICHED_GOLD));
		reinforcediron_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedIronSword", new CalcSword(CalculatorConfig.TOOL_REINFORCED_IRON));
		redstone_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "RedstoneSword", new CalcSword(CalculatorConfig.TOOL_REDSTONE_INGOT));
		weakeneddiamond_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WeakenedDiamondSword", new CalcSword(CalculatorConfig.TOOL_WEAKENED_DIAMOND));
		flawlessdiamond_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessDiamondSword", new CalcSword(CalculatorConfig.TOOL_FLAWLESS_DIAMOND));
		firediamond_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireDiamondSword", new CalcSword(CalculatorConfig.TOOL_FIRE_DIAMOND));
		electric_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ElectricSword", new CalcSword(CalculatorConfig.TOOL_ELECTRIC_DIAMOND));
		endforged_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EndForgedSword", new CalcSword(CalculatorConfig.TOOL_END_DIAMOND));

		// pickaxes
		reinforced_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedPickaxe", new CalcPickaxe(CalculatorConfig.TOOL_REINFORCED_STONE));
		enrichedgold_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGoldPickaxe", new CalcPickaxe(CalculatorConfig.TOOL_ENRICHED_GOLD));
		reinforcediron_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedIronPickaxe", new CalcPickaxe(CalculatorConfig.TOOL_REINFORCED_IRON));
		redstone_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "RedstonePickaxe", new CalcPickaxe(CalculatorConfig.TOOL_REDSTONE_INGOT));
		weakeneddiamond_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WeakenedDiamondPickaxe", new CalcPickaxe(CalculatorConfig.TOOL_WEAKENED_DIAMOND));
		flawlessdiamond_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessDiamondPickaxe", new CalcPickaxe(CalculatorConfig.TOOL_FLAWLESS_DIAMOND));
		firediamond_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireDiamondPickaxe", new CalcPickaxe(CalculatorConfig.TOOL_FIRE_DIAMOND));
		electric_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ElectricPickaxe", new CalcPickaxe(CalculatorConfig.TOOL_ELECTRIC_DIAMOND));
		endforged_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EndForgedPickaxe", new CalcPickaxe(CalculatorConfig.TOOL_END_DIAMOND));

		// axes
		reinforced_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedAxe", new CalcAxe(CalculatorConfig.TOOL_REINFORCED_STONE));
		enrichedgold_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGoldAxe", new CalcAxe(CalculatorConfig.TOOL_ENRICHED_GOLD));
		reinforcediron_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedIronAxe", new CalcAxe(CalculatorConfig.TOOL_REINFORCED_IRON));
		redstone_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "RedstoneAxe", new CalcAxe(CalculatorConfig.TOOL_REDSTONE_INGOT));
		weakeneddiamond_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WeakenedDiamondAxe", new CalcAxe(CalculatorConfig.TOOL_WEAKENED_DIAMOND));
		flawlessdiamond_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessDiamondAxe", new CalcAxe(CalculatorConfig.TOOL_FLAWLESS_DIAMOND));
		firediamond_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireDiamondAxe", new CalcAxe(CalculatorConfig.TOOL_FIRE_DIAMOND));
		electric_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ElectricAxe", new CalcAxe(CalculatorConfig.TOOL_ELECTRIC_DIAMOND));
		endforged_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EndForgedAxe", new CalcAxe(CalculatorConfig.TOOL_END_DIAMOND));

		// shovels
		reinforced_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedShovel", new CalcShovel(CalculatorConfig.TOOL_REINFORCED_STONE));
		enrichedgold_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGoldShovel", new CalcShovel(CalculatorConfig.TOOL_ENRICHED_GOLD));
		reinforcediron_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedIronShovel", new CalcShovel(CalculatorConfig.TOOL_REINFORCED_IRON));
		redstone_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "RedstoneShovel", new CalcShovel(CalculatorConfig.TOOL_REDSTONE_INGOT));
		weakeneddiamond_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WeakenedDiamondShovel", new CalcShovel(CalculatorConfig.TOOL_WEAKENED_DIAMOND));
		flawlessdiamond_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessDiamondShovel", new CalcShovel(CalculatorConfig.TOOL_FLAWLESS_DIAMOND));
		firediamond_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireDiamondShovel", new CalcShovel(CalculatorConfig.TOOL_FIRE_DIAMOND));
		electric_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ElectricShovel", new CalcShovel(CalculatorConfig.TOOL_ELECTRIC_DIAMOND));
		endforged_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EndForgedShovel", new CalcShovel(CalculatorConfig.TOOL_END_DIAMOND));

		// hoes
		reinforced_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedHoe", new CalcHoe(CalculatorConfig.TOOL_REINFORCED_STONE));
		enrichedgold_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGoldHoe", new CalcHoe(CalculatorConfig.TOOL_ENRICHED_GOLD));
		reinforcediron_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedIronHoe", new CalcHoe(CalculatorConfig.TOOL_REINFORCED_IRON));
		redstone_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "RedstoneHoe", new CalcHoe(CalculatorConfig.TOOL_REDSTONE_INGOT));
		weakeneddiamond_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WeakenedDiamondHoe", new CalcHoe(CalculatorConfig.TOOL_WEAKENED_DIAMOND));
		flawlessdiamond_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessDiamondHoe", new CalcHoe(CalculatorConfig.TOOL_FLAWLESS_DIAMOND));
		firediamond_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireDiamondHoe", new CalcHoe(CalculatorConfig.TOOL_FIRE_DIAMOND));
		electric_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ElectricHoe", new CalcHoe(CalculatorConfig.TOOL_ELECTRIC_DIAMOND));
		endforged_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EndForgedHoe", new CalcHoe(CalculatorConfig.TOOL_END_DIAMOND));

		// materials
		enrichedGold = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGold", new Item());
		enrichedgold_ingot = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGoldIngot", new Item());
		reinforcediron_ingot = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedIronIngot", new Item());
		redstone_ingot = SonarRegister.addItem(CalculatorConstants.MODID, tab, "RedstoneIngot", new Item());
		weakeneddiamond = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WeakenedDiamond", new Item());
		flawlessdiamond = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessDiamond", new Item());
		firediamond = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireDiamond", new SonarItemSimpleFuel(160000));
		electricDiamond = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ElectricDiamond", new Item());
		endDiamond = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EndDiamond", new EndDiamond());

		// gems
		large_amethyst = SonarRegister.addItem(CalculatorConstants.MODID, tab, "LargeAmethyst", new Item());
		small_amethyst = SonarRegister.addItem(CalculatorConstants.MODID, tab, "SmallAmethyst", new Item());
		shard_amethyst = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ShardAmethyst", new Item());
		large_tanzanite = SonarRegister.addItem(CalculatorConstants.MODID, tab, "LargeTanzanite", new Item());
		small_tanzanite = SonarRegister.addItem(CalculatorConstants.MODID, tab, "SmallTanzanite", new Item());
		shard_tanzanite = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ShardTanzanite", new Item());

		// seeds
		broccoliSeeds = SonarRegister.addItem(CalculatorConstants.MODID, tab, "BroccoliSeeds", new SonarSeeds(cropBroccoliPlant, Blocks.FARMLAND, 0));
		prunaeSeeds = SonarRegister.addItem(CalculatorConstants.MODID, tab, "PrunaeSeeds", new SonarSeeds(cropPrunaePlant, Blocks.FARMLAND, 2));
		fiddledewFruit = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FiddledewFruit", new SonarSeedsFood(16, 0.6F, cropFiddledewPlant, Blocks.FARMLAND, 3));

		// food
		broccoli = SonarRegister.addItem(CalculatorConstants.MODID, tab, "Broccoli", new ItemFood(1, 0.2F, false));
		pear = SonarRegister.addItem(CalculatorConstants.MODID, tab, "Pear", new ItemFood(12, 2.0F, false));
		rotten_pear = SonarRegister.addItem(CalculatorConstants.MODID, tab, "RottenPear", new ItemFood(1, 0.1F, false));
		cookedBroccoli = SonarRegister.addItem(CalculatorConstants.MODID, tab, "CookedBroccoli", new ItemFood(9, 0.6F, false));

		// fuels
		coal_dust = SonarRegister.addItem(CalculatorConstants.MODID, tab, "CoalDust", new SonarItemSimpleFuel(1000));
		enriched_coal = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedCoal", new SonarItemSimpleFuel(5000));
		purified_coal = SonarRegister.addItem(CalculatorConstants.MODID, tab, "PurifiedCoal", new SonarItemSimpleFuel(10000));
		firecoal = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireCoal", new SonarItemSimpleFuel(25000));
		controlled_Fuel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ControlledFuel", new SonarItemSimpleFuel(80000));

		// grenades
		grenadecasing = SonarRegister.addItem(CalculatorConstants.MODID, tab, "GrenadeCasing", new Item());
		baby_grenade = SonarRegister.addItem(CalculatorConstants.MODID, tab, "BabyGrenade", new Grenade(0));
		grenade = SonarRegister.addItem(CalculatorConstants.MODID, tab, "Grenade", new Grenade(1));
		circuitBoard = SonarRegister.addItem(CalculatorConstants.MODID, tab, "CircuitBoard", new CircuitBoard().setHasSubtypes(true).setMaxStackSize(1));
		circuitDamaged = SonarRegister.addItem(CalculatorConstants.MODID, tab, "CircuitDamaged", new SonarMetaItem(14).setHasSubtypes(true).setMaxStackSize(1));
		circuitDirty = SonarRegister.addItem(CalculatorConstants.MODID, tab, "CircuitDirty", new SonarMetaItem(14).setHasSubtypes(true).setMaxStackSize(1));

		//ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(circuitBoard, 0, 1, 1, 2)); ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(circuitDirty, 0, 1, 1, 5)); ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(circuitDamaged, 0, 1, 1, 5)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(itemCalculator, 0, 1, 1, 4)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(itemCraftingCalculator, 0, 1, 1, 4)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(itemInfoCalculator, 0, 1, 1, 4)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(Item.getItemFromBlock(reinforcedStoneBlock), 0, 5, 20, 12)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(wrench, 0, 1, 1, 3)); ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(itemInfoCalculator, 0, 1, 1, 10)); 
	}
}
