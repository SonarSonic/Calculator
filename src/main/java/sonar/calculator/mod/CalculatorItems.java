package sonar.calculator.mod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.common.item.calculators.FlawlessCalculator;
import sonar.calculator.mod.common.item.calculators.InfoCalculator;
import sonar.calculator.mod.common.item.calculators.SonarEnergyModule;
import sonar.calculator.mod.common.item.calculators.SonarModule;
import sonar.calculator.mod.common.item.calculators.SonarUsageModule;
import sonar.calculator.mod.common.item.calculators.modules.EnergyModule;
import sonar.calculator.mod.common.item.calculators.modules.GuiModule;
import sonar.calculator.mod.common.item.calculators.modules.JumpModule;
import sonar.calculator.mod.common.item.calculators.modules.WarpModule;
import sonar.calculator.mod.common.item.misc.CalculatorScreen;
import sonar.calculator.mod.common.item.misc.CircuitBoard;
import sonar.calculator.mod.common.item.misc.EndDiamond;
import sonar.calculator.mod.common.item.misc.Grenade;
import sonar.calculator.mod.common.item.misc.SmallStone;
import sonar.calculator.mod.common.item.misc.Soil;
import sonar.calculator.mod.common.item.modules.AdvancedTerrainModule;
import sonar.calculator.mod.common.item.modules.HealthModule;
import sonar.calculator.mod.common.item.modules.HungerModule;
import sonar.calculator.mod.common.item.modules.LocatorModule;
import sonar.calculator.mod.common.item.modules.NutritionModule;
import sonar.calculator.mod.common.item.modules.TerrainModule;
import sonar.calculator.mod.common.item.tools.CalcAxe;
import sonar.calculator.mod.common.item.tools.CalcHoe;
import sonar.calculator.mod.common.item.tools.CalcPickaxe;
import sonar.calculator.mod.common.item.tools.CalcShovel;
import sonar.calculator.mod.common.item.tools.CalcSword;
import sonar.calculator.mod.common.item.tools.ObsidianKey;
import sonar.calculator.mod.common.item.tools.Sickle;
import sonar.calculator.mod.common.item.tools.Wrench;
import sonar.core.common.block.properties.IMetaVariant;
import sonar.core.common.item.SonarItem;
import sonar.core.common.item.SonarMetaItem;
import sonar.core.common.item.SonarSeeds;
import sonar.core.common.item.SonarSeedsFood;
import sonar.core.upgrades.MachineUpgrade;

public class CalculatorItems extends Calculator {

	public static ArrayList<Item> registeredItems = new ArrayList();

	public enum UpgradeTypes {
		SPEED, ENERGY, VOID, TRANSFER;
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

	public static enum ToolTypes implements IMetaVariant {
		ReinforcedStone(0, CalculatorItems.ReinforcedStone), Redstone(1, CalculatorItems.RedstoneMaterial), EnrichedGold(2, CalculatorItems.EnrichedGold), ReinforcedIron(3, CalculatorItems.ReinforcedIron), WeakenedDiamond(4, CalculatorItems.WeakenedDiamond), FlawlessDiamond(5, CalculatorItems.FlawlessDiamond), FireDiamond(6, CalculatorItems.FireDiamond), ElectricDiamond(7, CalculatorItems.ElectricDiamond), EndForged(8, CalculatorItems.EndForged);

		Item.ToolMaterial material;
		int type = 0;

		ToolTypes(int type, Item.ToolMaterial material) {
			this.material = material;
			this.type = type;
		}

		@Override
		public String getName() {
			return name();
		}

		@Override
		public int getMeta() {
			return type;
		}

	}

	public static Item registerItem(String name, Item item) {
		item.setCreativeTab(Calculator);
		GameRegistry.registerItem(item.setUnlocalizedName(name), name);
		registeredItems.add(item);
		return item;
	}

	public static void registerItems() {

		// calculators
		itemInfoCalculator = registerItem("InfoCalculator", new InfoCalculator());
		itemCalculator = registerItem("Calculator", new SonarUsageModule(GuiModule.calculator, 1000));
		itemCraftingCalculator = registerItem("CraftingCalculator", new SonarUsageModule(GuiModule.crafting, 5000));
		itemScientificCalculator = registerItem("ScientificCalculator", new SonarUsageModule(GuiModule.scientific, 2000));
		itemFlawlessCalculator = registerItem("FlawlessCalculator", new FlawlessCalculator());

		// modules
		itemStorageModule = registerItem("StorageModule", new SonarModule(GuiModule.storage));
		itemWarpModule = registerItem("WarpModule", new SonarUsageModule(new WarpModule(), 10000).setNew());
		itemJumpModule = registerItem("JumpModule", new SonarUsageModule(new JumpModule(), 10000).setNew());
		itemHungerModule = registerItem("HungerModule", new HungerModule());
		itemHealthModule = registerItem("HealthModule", new HealthModule());
		itemNutritionModule = registerItem("NutritionModule", new NutritionModule());
		itemTerrainModule = registerItem("TerrainModule", new TerrainModule());
		itemAdvancedTerrainModule = registerItem("AdvancedTerrainModule", new AdvancedTerrainModule());
		itemEnergyModule = registerItem("EnergyModule", new SonarEnergyModule(new EnergyModule()));
		itemLocatorModule = registerItem("LocatorModule", new LocatorModule());

		// misc
		soil = registerItem("Soil", new Soil());
		small_stone = registerItem("SmallStone", new SmallStone());

		// upgrades
		speedUpgrade = registerItem("SpeedUpgrade", new MachineUpgrade());
		energyUpgrade = registerItem("EnergyUpgrade", new MachineUpgrade());
		voidUpgrade = registerItem("VoidUpgrade", new MachineUpgrade());
		transferUpgrade = registerItem("TransferUpgrade", new MachineUpgrade().setNew());

		// calculator parts
		calculator_screen = registerItem("CalculatorScreen", new CalculatorScreen());

		calculator_assembly = registerItem("CalculatorAssembly", new Item());
		advanced_assembly = registerItem("AdvancedAssembly", new Item());
		atomic_module = registerItem("AtomicModule", new Item());
		atomic_assembly = registerItem("AtomicAssembly", new Item());
		flawless_assembly = registerItem("FlawlessAssembly", new SonarItem().setNew());
		atomic_binder = registerItem("AtomicBinder", new Item());

		// tools
		wrench = registerItem("Wrench", new Wrench());
		sickle = registerItem("Sickle", new Sickle());
		obsidianKey = registerItem("ObsidianKey", new ObsidianKey());

		// swords
		reinforced_sword = registerItem("ReinforcedSword", new CalcSword(ReinforcedStone));
		enrichedgold_sword = registerItem("EnrichedGoldSword", new CalcSword(EnrichedGold));
		reinforcediron_sword = registerItem("ReinforcedIronSword", new CalcSword(ReinforcedIron));
		redstone_sword = registerItem("RedstoneSword", new CalcSword(RedstoneMaterial));
		weakeneddiamond_sword = registerItem("WeakenedDiamondSword", new CalcSword(WeakenedDiamond));
		flawlessdiamond_sword = registerItem("FlawlessDiamondSword", new CalcSword(FlawlessDiamond));
		firediamond_sword = registerItem("FireDiamondSword", new CalcSword(FireDiamond));
		electric_sword = registerItem("ElectricSword", new CalcSword(ElectricDiamond));
		endforged_sword = registerItem("EndForgedSword", new CalcSword(EndForged));

		// pickaxes
		reinforced_pickaxe = registerItem("ReinforcedPickaxe", new CalcPickaxe(ReinforcedStone));
		enrichedgold_pickaxe = registerItem("EnrichedGoldPickaxe", new CalcPickaxe(EnrichedGold));
		reinforcediron_pickaxe = registerItem("ReinforcedIronPickaxe", new CalcPickaxe(ReinforcedIron));
		redstone_pickaxe = registerItem("RedstonePickaxe", new CalcPickaxe(RedstoneMaterial));
		weakeneddiamond_pickaxe = registerItem("WeakenedDiamondPickaxe", new CalcPickaxe(WeakenedDiamond));
		flawlessdiamond_pickaxe = registerItem("FlawlessDiamondPickaxe", new CalcPickaxe(FlawlessDiamond));
		firediamond_pickaxe = registerItem("FireDiamondPickaxe", new CalcPickaxe(FireDiamond));
		electric_pickaxe = registerItem("ElectricPickaxe", new CalcPickaxe(ElectricDiamond));
		endforged_pickaxe = registerItem("EndForgedPickaxe", new CalcPickaxe(EndForged));

		// axes
		reinforced_axe = registerItem("ReinforcedAxe", new CalcAxe(ReinforcedStone));
		enrichedgold_axe = registerItem("EnrichedGoldAxe", new CalcAxe(EnrichedGold));
		reinforcediron_axe = registerItem("ReinforcedIronAxe", new CalcAxe(ReinforcedIron));
		redstone_axe = registerItem("RedstoneAxe", new CalcAxe(RedstoneMaterial));
		weakeneddiamond_axe = registerItem("WeakenedDiamondAxe", new CalcAxe(WeakenedDiamond));
		flawlessdiamond_axe = registerItem("FlawlessDiamondAxe", new CalcAxe(FlawlessDiamond));
		firediamond_axe = registerItem("FireDiamondAxe", new CalcAxe(FireDiamond));
		electric_axe = registerItem("ElectricAxe", new CalcAxe(ElectricDiamond));
		endforged_axe = registerItem("EndForgedAxe", new CalcAxe(EndForged));

		// shovels
		reinforced_shovel = registerItem("ReinforcedShovel", new CalcShovel(ReinforcedStone));
		enrichedgold_shovel = registerItem("EnrichedGoldShovel", new CalcShovel(EnrichedGold));
		reinforcediron_shovel = registerItem("ReinforcedIronShovel", new CalcShovel(ReinforcedIron));
		redstone_shovel = registerItem("RedstoneShovel", new CalcShovel(RedstoneMaterial));
		weakeneddiamond_shovel = registerItem("WeakenedDiamondShovel", new CalcShovel(WeakenedDiamond));
		flawlessdiamond_shovel = registerItem("FlawlessDiamondShovel", new CalcShovel(FlawlessDiamond));
		firediamond_shovel = registerItem("FireDiamondShovel", new CalcShovel(FireDiamond));
		electric_shovel = registerItem("ElectricShovel", new CalcShovel(ElectricDiamond));
		endforged_shovel = registerItem("EndForgedShovel", new CalcShovel(EndForged));

		// hoes
		reinforced_hoe = registerItem("ReinforcedHoe", new CalcHoe(ReinforcedStone));
		enrichedgold_hoe = registerItem("EnrichedGoldHoe", new CalcHoe(EnrichedGold));
		reinforcediron_hoe = registerItem("ReinforcedIronHoe", new CalcHoe(ReinforcedIron));
		redstone_hoe = registerItem("RedstoneHoe", new CalcHoe(RedstoneMaterial));
		weakeneddiamond_hoe = registerItem("WeakenedDiamondHoe", new CalcHoe(WeakenedDiamond));
		flawlessdiamond_hoe = registerItem("FlawlessDiamondHoe", new CalcHoe(FlawlessDiamond));
		firediamond_hoe = registerItem("FireDiamondHoe", new CalcHoe(FireDiamond));
		electric_hoe = registerItem("ElectricHoe", new CalcHoe(ElectricDiamond));
		endforged_hoe = registerItem("EndForgedHoe", new CalcHoe(EndForged));

		// materials
		enrichedGold = registerItem("EnrichedGold", new Item());
		enrichedgold_ingot = registerItem("EnrichedGoldIngot", new Item());
		reinforcediron_ingot = registerItem("ReinforcedIronIngot", new Item());
		redstone_ingot = registerItem("RedstoneIngot", new Item());
		weakeneddiamond = registerItem("WeakenedDiamond", new Item());
		flawlessdiamond = registerItem("FlawlessDiamond", new Item());
		firediamond = registerItem("FireDiamond", new Item());
		electricDiamond = registerItem("ElectricDiamond", new Item());
		endDiamond = registerItem("EndDiamond", new EndDiamond());

		// gems
		large_amethyst = registerItem("LargeAmethyst", new Item());
		small_amethyst = registerItem("SmallAmethyst", new Item());
		shard_amethyst = registerItem("ShardAmethyst", new Item());
		large_tanzanite = registerItem("LargeTanzanite", new Item());
		small_tanzanite = registerItem("SmallTanzanite", new Item());
		shard_tanzanite = registerItem("ShardTanzanite", new Item());

		// seeds
		broccoliSeeds = registerItem("BroccoliSeeds", new SonarSeeds(cropBroccoliPlant, Blocks.farmland, 0));
		prunaeSeeds = registerItem("PrunaeSeeds", new SonarSeeds(cropPrunaePlant, Blocks.farmland, 2));
		fiddledewFruit = registerItem("FiddledewFruit", new SonarSeedsFood(16, 0.6F, cropFiddledewPlant, Blocks.farmland, 3));

		// food
		broccoli = registerItem("Broccoli", new ItemFood(1, 0.2F, false));
		pear = registerItem("Pear", new ItemFood(12, 2.0F, false));
		rotten_pear = registerItem("RottenPear", new ItemFood(1, 0.1F, false));
		cookedBroccoli = registerItem("CookedBroccoli", new ItemFood(9, 0.6F, false));

		// fuels
		coal_dust = registerItem("CoalDust", new Item());
		enriched_coal = registerItem("EnrichedCoal", new Item());
		purified_coal = registerItem("PurifiedCoal", new Item());
		firecoal = registerItem("FireCoal", new Item());
		controlled_Fuel = registerItem("ControlledFuel", new Item());

		// grenades
		grenadecasing = registerItem("GrenadeCasing", new Item());
		baby_grenade = registerItem("BabyGrenade", new Grenade(0));
		grenade = registerItem("Grenade", new Grenade(1));
		circuitBoard = registerItem("CircuitBoard", new CircuitBoard().setHasSubtypes(true).setMaxStackSize(1));
		circuitDamaged = registerItem("CircuitDamaged", new SonarMetaItem(14).setHasSubtypes(true).setMaxStackSize(1));
		circuitDirty = registerItem("CircuitDirty", new SonarMetaItem(14).setHasSubtypes(true).setMaxStackSize(1));

		// circuitDamaged = new ItemCircuitDamaged().setHasSubtypes(true).setUnlocalizedName("CircuitDamaged").setCreativeTab(Calculator).setMaxStackSize(1);
		// GameRegistry.registerItem(circuitDamaged, "CircuitDamaged");
		// circuitDirty = new ItemCircuitDirty().setHasSubtypes(true).setUnlocalizedName("CircuitDirty").setCreativeTab(Calculator).setMaxStackSize(1);
		// GameRegistry.registerItem(circuitDirty, "CircuitDirty");
		/* // chest generation items ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(circuitBoard, 0, 1, 1, 2)); ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(circuitDirty, 0, 1, 1, 5)); ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(circuitDamaged, 0, 1, 1, 5)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(itemCalculator, 0, 1, 1, 4)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(itemCraftingCalculator, 0, 1, 1, 4)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(itemInfoCalculator, 0, 1, 1, 4)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(Item.getItemFromBlock(reinforcedStoneBlock), 0, 5, 20, 12)); ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(wrench, 0, 1, 1, 3)); ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(itemInfoCalculator, 0, 1, 1, 10)); */
	}
}
