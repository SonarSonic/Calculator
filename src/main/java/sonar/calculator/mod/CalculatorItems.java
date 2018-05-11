package sonar.calculator.mod;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.common.util.EnumHelper;
import sonar.calculator.mod.common.item.calculators.*;
import sonar.calculator.mod.common.item.calculators.modules.EnergyModule;
import sonar.calculator.mod.common.item.calculators.modules.GuiModule;
import sonar.calculator.mod.common.item.calculators.modules.JumpModule;
import sonar.calculator.mod.common.item.calculators.modules.WarpModule;
import sonar.calculator.mod.common.item.misc.*;
import sonar.calculator.mod.common.item.modules.*;
import sonar.calculator.mod.common.item.tools.*;
import sonar.core.SonarRegister;
import sonar.core.common.block.properties.IMetaVariant;
import sonar.core.common.item.SonarItem;
import sonar.core.common.item.SonarMetaItem;
import sonar.core.common.item.SonarSeeds;
import sonar.core.common.item.SonarSeedsFood;
import sonar.core.upgrades.MachineUpgrade;

import javax.annotation.Nonnull;

public class CalculatorItems extends Calculator {

	public enum UpgradeTypes {
        SPEED, ENERGY, VOID, TRANSFER
	}

	public static final Item.ToolMaterial ReinforcedStone = EnumHelper.addToolMaterial("ReinforcedStone", 1, 250, 5.0F, 1.5F, 5);
	public static final Item.ToolMaterial RedstoneMaterial = EnumHelper.addToolMaterial("RedstoneMaterial", 2, 800, 7.5F, 2.5F, 18);
	public static final Item.ToolMaterial EnrichedGold = EnumHelper.addToolMaterial("EnrichedGold", 3, 1000, 8.0F, 0.0F, 20);
	public static final Item.ToolMaterial ReinforcedIron = EnumHelper.addToolMaterial("ReinforcedIron", 2, 400, 7.0F, 2.0F, 10);
	public static final Item.ToolMaterial WeakenedDiamond = EnumHelper.addToolMaterial("WeakenedDiamond", 3, 1400, 8.0F, 3.0F, 10);
	public static final Item.ToolMaterial FlawlessDiamond = EnumHelper.addToolMaterial("FlawlessDiamond", 3, 1800, 14.0F, 5.0F, 30);
	public static final Item.ToolMaterial FireDiamond = EnumHelper.addToolMaterial("FireDiamond", 3, 2600, 16.0F, 7.0F, 30);
	public static final Item.ToolMaterial ElectricDiamond = EnumHelper.addToolMaterial("ElectricDiamond", 4, 10000, 18.0F, 10.0F, 30);
	public static final Item.ToolMaterial EndForged = EnumHelper.addToolMaterial("EndForged", 6, -1, 50F, 16.0F, 30);

    public enum ToolTypes implements IMetaVariant {
		ReinforcedStone(0, CalculatorItems.ReinforcedStone), Redstone(1, CalculatorItems.RedstoneMaterial), EnrichedGold(2, CalculatorItems.EnrichedGold), ReinforcedIron(3, CalculatorItems.ReinforcedIron), WeakenedDiamond(4, CalculatorItems.WeakenedDiamond), FlawlessDiamond(5, CalculatorItems.FlawlessDiamond), FireDiamond(6, CalculatorItems.FireDiamond), ElectricDiamond(7, CalculatorItems.ElectricDiamond), EndForged(8, CalculatorItems.EndForged);

		Item.ToolMaterial material;
        int type;

		ToolTypes(int type, Item.ToolMaterial material) {
			this.material = material;
			this.type = type;
		}

		@Nonnull
        @Override
		public String getName() {
			return name();
		}

		@Override
		public int getMeta() {
			return type;
		}

	}

	public static void registerItems() {

		// calculators
		itemInfoCalculator = SonarRegister.addItem(CalculatorConstants.MODID, tab, "InfoCalculator", new InfoCalculator());
		itemCalculator = SonarRegister.addItem(CalculatorConstants.MODID, tab, "Calculator", new SonarUsageModule(GuiModule.calculator, 1000));
		itemCraftingCalculator = SonarRegister.addItem(CalculatorConstants.MODID, tab, "CraftingCalculator", new SonarUsageModule(GuiModule.crafting, 5000));
		itemScientificCalculator = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ScientificCalculator", new SonarUsageModule(GuiModule.scientific, 2000));
		itemFlawlessCalculator = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessCalculator", new FlawlessCalculator());

		// modules
		itemStorageModule = SonarRegister.addItem(CalculatorConstants.MODID, tab, "StorageModule", new SonarModule(GuiModule.storage));
		itemWarpModule = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WarpModule", new SonarUsageModule(new WarpModule(), 10000));
		itemJumpModule = SonarRegister.addItem(CalculatorConstants.MODID, tab, "JumpModule", new SonarUsageModule(new JumpModule(), 10000));
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
		reinforced_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedSword", new CalcSword(ReinforcedStone));
		enrichedgold_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGoldSword", new CalcSword(EnrichedGold));
		reinforcediron_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedIronSword", new CalcSword(ReinforcedIron));
		redstone_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "RedstoneSword", new CalcSword(RedstoneMaterial));
		weakeneddiamond_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WeakenedDiamondSword", new CalcSword(WeakenedDiamond));
		flawlessdiamond_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessDiamondSword", new CalcSword(FlawlessDiamond));
		firediamond_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireDiamondSword", new CalcSword(FireDiamond));
		electric_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ElectricSword", new CalcSword(ElectricDiamond));
		endforged_sword = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EndForgedSword", new CalcSword(EndForged));

		// pickaxes
		reinforced_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedPickaxe", new CalcPickaxe(ReinforcedStone));
		enrichedgold_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGoldPickaxe", new CalcPickaxe(EnrichedGold));
		reinforcediron_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedIronPickaxe", new CalcPickaxe(ReinforcedIron));
		redstone_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "RedstonePickaxe", new CalcPickaxe(RedstoneMaterial));
		weakeneddiamond_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WeakenedDiamondPickaxe", new CalcPickaxe(WeakenedDiamond));
		flawlessdiamond_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessDiamondPickaxe", new CalcPickaxe(FlawlessDiamond));
		firediamond_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireDiamondPickaxe", new CalcPickaxe(FireDiamond));
		electric_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ElectricPickaxe", new CalcPickaxe(ElectricDiamond));
		endforged_pickaxe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EndForgedPickaxe", new CalcPickaxe(EndForged));

		// axes
		reinforced_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedAxe", new CalcAxe(ReinforcedStone));
		enrichedgold_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGoldAxe", new CalcAxe(EnrichedGold));
		reinforcediron_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedIronAxe", new CalcAxe(ReinforcedIron));
		redstone_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "RedstoneAxe", new CalcAxe(RedstoneMaterial));
		weakeneddiamond_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WeakenedDiamondAxe", new CalcAxe(WeakenedDiamond));
		flawlessdiamond_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessDiamondAxe", new CalcAxe(FlawlessDiamond));
		firediamond_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireDiamondAxe", new CalcAxe(FireDiamond));
		electric_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ElectricAxe", new CalcAxe(ElectricDiamond));
		endforged_axe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EndForgedAxe", new CalcAxe(EndForged));

		// shovels
		reinforced_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedShovel", new CalcShovel(ReinforcedStone));
		enrichedgold_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGoldShovel", new CalcShovel(EnrichedGold));
		reinforcediron_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedIronShovel", new CalcShovel(ReinforcedIron));
		redstone_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "RedstoneShovel", new CalcShovel(RedstoneMaterial));
		weakeneddiamond_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WeakenedDiamondShovel", new CalcShovel(WeakenedDiamond));
		flawlessdiamond_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessDiamondShovel", new CalcShovel(FlawlessDiamond));
		firediamond_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireDiamondShovel", new CalcShovel(FireDiamond));
		electric_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ElectricShovel", new CalcShovel(ElectricDiamond));
		endforged_shovel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EndForgedShovel", new CalcShovel(EndForged));

		// hoes
		reinforced_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedHoe", new CalcHoe(ReinforcedStone));
		enrichedgold_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGoldHoe", new CalcHoe(EnrichedGold));
		reinforcediron_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedIronHoe", new CalcHoe(ReinforcedIron));
		redstone_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "RedstoneHoe", new CalcHoe(RedstoneMaterial));
		weakeneddiamond_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WeakenedDiamondHoe", new CalcHoe(WeakenedDiamond));
		flawlessdiamond_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessDiamondHoe", new CalcHoe(FlawlessDiamond));
		firediamond_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireDiamondHoe", new CalcHoe(FireDiamond));
		electric_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ElectricHoe", new CalcHoe(ElectricDiamond));
		endforged_hoe = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EndForgedHoe", new CalcHoe(EndForged));

		// materials
		enrichedGold = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGold", new Item());
		enrichedgold_ingot = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedGoldIngot", new Item());
		reinforcediron_ingot = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ReinforcedIronIngot", new Item());
		redstone_ingot = SonarRegister.addItem(CalculatorConstants.MODID, tab, "RedstoneIngot", new Item());
		weakeneddiamond = SonarRegister.addItem(CalculatorConstants.MODID, tab, "WeakenedDiamond", new Item());
		flawlessdiamond = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FlawlessDiamond", new Item());
		firediamond = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireDiamond", new Item());
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
		coal_dust = SonarRegister.addItem(CalculatorConstants.MODID, tab, "CoalDust", new Item());
		enriched_coal = SonarRegister.addItem(CalculatorConstants.MODID, tab, "EnrichedCoal", new Item());
		purified_coal = SonarRegister.addItem(CalculatorConstants.MODID, tab, "PurifiedCoal", new Item());
		firecoal = SonarRegister.addItem(CalculatorConstants.MODID, tab, "FireCoal", new Item());
		controlled_Fuel = SonarRegister.addItem(CalculatorConstants.MODID, tab, "ControlledFuel", new Item());

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
