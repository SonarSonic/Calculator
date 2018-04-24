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
		itemInfoCalculator = SonarRegister.addItem(modid, tab, "InfoCalculator", new InfoCalculator());
		itemCalculator = SonarRegister.addItem(modid, tab, "Calculator", new SonarUsageModule(GuiModule.calculator, 1000));
		itemCraftingCalculator = SonarRegister.addItem(modid, tab, "CraftingCalculator", new SonarUsageModule(GuiModule.crafting, 5000));
		itemScientificCalculator = SonarRegister.addItem(modid, tab, "ScientificCalculator", new SonarUsageModule(GuiModule.scientific, 2000));
		itemFlawlessCalculator = SonarRegister.addItem(modid, tab, "FlawlessCalculator", new FlawlessCalculator());

		// modules
		itemStorageModule = SonarRegister.addItem(modid, tab, "StorageModule", new SonarModule(GuiModule.storage));
		itemWarpModule = SonarRegister.addItem(modid, tab, "WarpModule", new SonarUsageModule(new WarpModule(), 10000));
		itemJumpModule = SonarRegister.addItem(modid, tab, "JumpModule", new SonarUsageModule(new JumpModule(), 10000));
		itemHungerModule = SonarRegister.addItem(modid, tab, "HungerModule", new HungerModule());
		itemHealthModule = SonarRegister.addItem(modid, tab, "HealthModule", new HealthModule());
		itemNutritionModule = SonarRegister.addItem(modid, tab, "NutritionModule", new NutritionModule());
		itemTerrainModule = SonarRegister.addItem(modid, tab, "TerrainModule", new TerrainModule());
		itemAdvancedTerrainModule = SonarRegister.addItem(modid, tab, "AdvancedTerrainModule", new AdvancedTerrainModule());
		itemEnergyModule = SonarRegister.addItem(modid, tab, "EnergyModule", new SonarEnergyModule(new EnergyModule()));
		itemLocatorModule = SonarRegister.addItem(modid, tab, "LocatorModule", new LocatorModule());

		// misc
		soil = SonarRegister.addItem(modid, tab, "Soil", new Soil());
		small_stone = SonarRegister.addItem(modid, tab, "SmallStone", new SmallStone());

		// upgrades
		speedUpgrade = SonarRegister.addItem(modid, tab, "SpeedUpgrade", new MachineUpgrade());
		energyUpgrade = SonarRegister.addItem(modid, tab, "EnergyUpgrade", new MachineUpgrade());
		voidUpgrade = SonarRegister.addItem(modid, tab, "VoidUpgrade", new MachineUpgrade());
		transferUpgrade = SonarRegister.addItem(modid, tab, "TransferUpgrade", new MachineUpgrade());

		// calculator parts
		calculator_screen = SonarRegister.addItem(modid, tab, "CalculatorScreen", new CalculatorScreen());

		calculator_assembly = SonarRegister.addItem(modid, tab, "CalculatorAssembly", new Item());
		advanced_assembly = SonarRegister.addItem(modid, tab, "AdvancedAssembly", new Item());
		atomic_module = SonarRegister.addItem(modid, tab, "AtomicModule", new Item());
		atomic_assembly = SonarRegister.addItem(modid, tab, "AtomicAssembly", new Item());
		flawless_assembly = SonarRegister.addItem(modid, tab, "FlawlessAssembly", new SonarItem());
		atomic_binder = SonarRegister.addItem(modid, tab, "AtomicBinder", new Item());

		// tools
		wrench = SonarRegister.addItem(modid, tab, "Wrench", new Wrench());
		sickle = SonarRegister.addItem(modid, tab, "Sickle", new Sickle());
		obsidianKey = SonarRegister.addItem(modid, tab, "ObsidianKey", new ObsidianKey());

		// swords
		reinforced_sword = SonarRegister.addItem(modid, tab, "ReinforcedSword", new CalcSword(ReinforcedStone));
		enrichedgold_sword = SonarRegister.addItem(modid, tab, "EnrichedGoldSword", new CalcSword(EnrichedGold));
		reinforcediron_sword = SonarRegister.addItem(modid, tab, "ReinforcedIronSword", new CalcSword(ReinforcedIron));
		redstone_sword = SonarRegister.addItem(modid, tab, "RedstoneSword", new CalcSword(RedstoneMaterial));
		weakeneddiamond_sword = SonarRegister.addItem(modid, tab, "WeakenedDiamondSword", new CalcSword(WeakenedDiamond));
		flawlessdiamond_sword = SonarRegister.addItem(modid, tab, "FlawlessDiamondSword", new CalcSword(FlawlessDiamond));
		firediamond_sword = SonarRegister.addItem(modid, tab, "FireDiamondSword", new CalcSword(FireDiamond));
		electric_sword = SonarRegister.addItem(modid, tab, "ElectricSword", new CalcSword(ElectricDiamond));
		endforged_sword = SonarRegister.addItem(modid, tab, "EndForgedSword", new CalcSword(EndForged));

		// pickaxes
		reinforced_pickaxe = SonarRegister.addItem(modid, tab, "ReinforcedPickaxe", new CalcPickaxe(ReinforcedStone));
		enrichedgold_pickaxe = SonarRegister.addItem(modid, tab, "EnrichedGoldPickaxe", new CalcPickaxe(EnrichedGold));
		reinforcediron_pickaxe = SonarRegister.addItem(modid, tab, "ReinforcedIronPickaxe", new CalcPickaxe(ReinforcedIron));
		redstone_pickaxe = SonarRegister.addItem(modid, tab, "RedstonePickaxe", new CalcPickaxe(RedstoneMaterial));
		weakeneddiamond_pickaxe = SonarRegister.addItem(modid, tab, "WeakenedDiamondPickaxe", new CalcPickaxe(WeakenedDiamond));
		flawlessdiamond_pickaxe = SonarRegister.addItem(modid, tab, "FlawlessDiamondPickaxe", new CalcPickaxe(FlawlessDiamond));
		firediamond_pickaxe = SonarRegister.addItem(modid, tab, "FireDiamondPickaxe", new CalcPickaxe(FireDiamond));
		electric_pickaxe = SonarRegister.addItem(modid, tab, "ElectricPickaxe", new CalcPickaxe(ElectricDiamond));
		endforged_pickaxe = SonarRegister.addItem(modid, tab, "EndForgedPickaxe", new CalcPickaxe(EndForged));

		// axes
		reinforced_axe = SonarRegister.addItem(modid, tab, "ReinforcedAxe", new CalcAxe(ReinforcedStone));
		enrichedgold_axe = SonarRegister.addItem(modid, tab, "EnrichedGoldAxe", new CalcAxe(EnrichedGold));
		reinforcediron_axe = SonarRegister.addItem(modid, tab, "ReinforcedIronAxe", new CalcAxe(ReinforcedIron));
		redstone_axe = SonarRegister.addItem(modid, tab, "RedstoneAxe", new CalcAxe(RedstoneMaterial));
		weakeneddiamond_axe = SonarRegister.addItem(modid, tab, "WeakenedDiamondAxe", new CalcAxe(WeakenedDiamond));
		flawlessdiamond_axe = SonarRegister.addItem(modid, tab, "FlawlessDiamondAxe", new CalcAxe(FlawlessDiamond));
		firediamond_axe = SonarRegister.addItem(modid, tab, "FireDiamondAxe", new CalcAxe(FireDiamond));
		electric_axe = SonarRegister.addItem(modid, tab, "ElectricAxe", new CalcAxe(ElectricDiamond));
		endforged_axe = SonarRegister.addItem(modid, tab, "EndForgedAxe", new CalcAxe(EndForged));

		// shovels
		reinforced_shovel = SonarRegister.addItem(modid, tab, "ReinforcedShovel", new CalcShovel(ReinforcedStone));
		enrichedgold_shovel = SonarRegister.addItem(modid, tab, "EnrichedGoldShovel", new CalcShovel(EnrichedGold));
		reinforcediron_shovel = SonarRegister.addItem(modid, tab, "ReinforcedIronShovel", new CalcShovel(ReinforcedIron));
		redstone_shovel = SonarRegister.addItem(modid, tab, "RedstoneShovel", new CalcShovel(RedstoneMaterial));
		weakeneddiamond_shovel = SonarRegister.addItem(modid, tab, "WeakenedDiamondShovel", new CalcShovel(WeakenedDiamond));
		flawlessdiamond_shovel = SonarRegister.addItem(modid, tab, "FlawlessDiamondShovel", new CalcShovel(FlawlessDiamond));
		firediamond_shovel = SonarRegister.addItem(modid, tab, "FireDiamondShovel", new CalcShovel(FireDiamond));
		electric_shovel = SonarRegister.addItem(modid, tab, "ElectricShovel", new CalcShovel(ElectricDiamond));
		endforged_shovel = SonarRegister.addItem(modid, tab, "EndForgedShovel", new CalcShovel(EndForged));

		// hoes
		reinforced_hoe = SonarRegister.addItem(modid, tab, "ReinforcedHoe", new CalcHoe(ReinforcedStone));
		enrichedgold_hoe = SonarRegister.addItem(modid, tab, "EnrichedGoldHoe", new CalcHoe(EnrichedGold));
		reinforcediron_hoe = SonarRegister.addItem(modid, tab, "ReinforcedIronHoe", new CalcHoe(ReinforcedIron));
		redstone_hoe = SonarRegister.addItem(modid, tab, "RedstoneHoe", new CalcHoe(RedstoneMaterial));
		weakeneddiamond_hoe = SonarRegister.addItem(modid, tab, "WeakenedDiamondHoe", new CalcHoe(WeakenedDiamond));
		flawlessdiamond_hoe = SonarRegister.addItem(modid, tab, "FlawlessDiamondHoe", new CalcHoe(FlawlessDiamond));
		firediamond_hoe = SonarRegister.addItem(modid, tab, "FireDiamondHoe", new CalcHoe(FireDiamond));
		electric_hoe = SonarRegister.addItem(modid, tab, "ElectricHoe", new CalcHoe(ElectricDiamond));
		endforged_hoe = SonarRegister.addItem(modid, tab, "EndForgedHoe", new CalcHoe(EndForged));

		// materials
		enrichedGold = SonarRegister.addItem(modid, tab, "EnrichedGold", new Item());
		enrichedgold_ingot = SonarRegister.addItem(modid, tab, "EnrichedGoldIngot", new Item());
		reinforcediron_ingot = SonarRegister.addItem(modid, tab, "ReinforcedIronIngot", new Item());
		redstone_ingot = SonarRegister.addItem(modid, tab, "RedstoneIngot", new Item());
		weakeneddiamond = SonarRegister.addItem(modid, tab, "WeakenedDiamond", new Item());
		flawlessdiamond = SonarRegister.addItem(modid, tab, "FlawlessDiamond", new Item());
		firediamond = SonarRegister.addItem(modid, tab, "FireDiamond", new Item());
		electricDiamond = SonarRegister.addItem(modid, tab, "ElectricDiamond", new Item());
		endDiamond = SonarRegister.addItem(modid, tab, "EndDiamond", new EndDiamond());

		// gems
		large_amethyst = SonarRegister.addItem(modid, tab, "LargeAmethyst", new Item());
		small_amethyst = SonarRegister.addItem(modid, tab, "SmallAmethyst", new Item());
		shard_amethyst = SonarRegister.addItem(modid, tab, "ShardAmethyst", new Item());
		large_tanzanite = SonarRegister.addItem(modid, tab, "LargeTanzanite", new Item());
		small_tanzanite = SonarRegister.addItem(modid, tab, "SmallTanzanite", new Item());
		shard_tanzanite = SonarRegister.addItem(modid, tab, "ShardTanzanite", new Item());

		// seeds
		broccoliSeeds = SonarRegister.addItem(modid, tab, "BroccoliSeeds", new SonarSeeds(cropBroccoliPlant, Blocks.FARMLAND, 0));
		prunaeSeeds = SonarRegister.addItem(modid, tab, "PrunaeSeeds", new SonarSeeds(cropPrunaePlant, Blocks.FARMLAND, 2));
		fiddledewFruit = SonarRegister.addItem(modid, tab, "FiddledewFruit", new SonarSeedsFood(16, 0.6F, cropFiddledewPlant, Blocks.FARMLAND, 3));

		// food
		broccoli = SonarRegister.addItem(modid, tab, "Broccoli", new ItemFood(1, 0.2F, false));
		pear = SonarRegister.addItem(modid, tab, "Pear", new ItemFood(12, 2.0F, false));
		rotten_pear = SonarRegister.addItem(modid, tab, "RottenPear", new ItemFood(1, 0.1F, false));
		cookedBroccoli = SonarRegister.addItem(modid, tab, "CookedBroccoli", new ItemFood(9, 0.6F, false));

		// fuels
		coal_dust = SonarRegister.addItem(modid, tab, "CoalDust", new Item());
		enriched_coal = SonarRegister.addItem(modid, tab, "EnrichedCoal", new Item());
		purified_coal = SonarRegister.addItem(modid, tab, "PurifiedCoal", new Item());
		firecoal = SonarRegister.addItem(modid, tab, "FireCoal", new Item());
		controlled_Fuel = SonarRegister.addItem(modid, tab, "ControlledFuel", new Item());

		// grenades
		grenadecasing = SonarRegister.addItem(modid, tab, "GrenadeCasing", new Item());
		baby_grenade = SonarRegister.addItem(modid, tab, "BabyGrenade", new Grenade(0));
		grenade = SonarRegister.addItem(modid, tab, "Grenade", new Grenade(1));
		circuitBoard = SonarRegister.addItem(modid, tab, "CircuitBoard", new CircuitBoard().setHasSubtypes(true).setMaxStackSize(1));
		circuitDamaged = SonarRegister.addItem(modid, tab, "CircuitDamaged", new SonarMetaItem(14).setHasSubtypes(true).setMaxStackSize(1));
		circuitDirty = SonarRegister.addItem(modid, tab, "CircuitDirty", new SonarMetaItem(14).setHasSubtypes(true).setMaxStackSize(1));

		//ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(circuitBoard, 0, 1, 1, 2)); ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(circuitDirty, 0, 1, 1, 5)); ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(circuitDamaged, 0, 1, 1, 5)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(itemCalculator, 0, 1, 1, 4)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(itemCraftingCalculator, 0, 1, 1, 4)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(itemInfoCalculator, 0, 1, 1, 4)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(Item.getItemFromBlock(reinforcedStoneBlock), 0, 5, 20, 12)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(wrench, 0, 1, 1, 3)); ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(itemInfoCalculator, 0, 1, 1, 10)); 
	}
}
