package sonar.calculator.mod;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.common.block.CalculatorCrops;
import sonar.calculator.mod.common.item.calculators.CalculatorItem;
import sonar.calculator.mod.common.item.calculators.CraftingCalc;
import sonar.calculator.mod.common.item.calculators.FlawlessCalc;
import sonar.calculator.mod.common.item.calculators.InfoCalc;
import sonar.calculator.mod.common.item.calculators.ScientificCalc;
import sonar.calculator.mod.common.item.misc.CalculatorScreen;
import sonar.calculator.mod.common.item.misc.EndDiamond;
import sonar.calculator.mod.common.item.misc.Grenade;
import sonar.calculator.mod.common.item.misc.ItemCircuit;
import sonar.calculator.mod.common.item.misc.ItemCircuitDamaged;
import sonar.calculator.mod.common.item.misc.ItemCircuitDirty;
import sonar.calculator.mod.common.item.misc.SmallStone;
import sonar.calculator.mod.common.item.misc.Soil;
import sonar.calculator.mod.common.item.misc.UpgradeCircuit;
import sonar.calculator.mod.common.item.modules.AdvancedTerrainModule;
import sonar.calculator.mod.common.item.modules.EnergyModule;
import sonar.calculator.mod.common.item.modules.HealthModule;
import sonar.calculator.mod.common.item.modules.HungerModule;
import sonar.calculator.mod.common.item.modules.LocatorModule;
import sonar.calculator.mod.common.item.modules.NutritionModule;
import sonar.calculator.mod.common.item.modules.StorageModule;
import sonar.calculator.mod.common.item.modules.TerrainModule;
import sonar.calculator.mod.common.item.modules.WIPSmeltingModule;
import sonar.calculator.mod.common.item.tools.CalcAxe;
import sonar.calculator.mod.common.item.tools.CalcHoe;
import sonar.calculator.mod.common.item.tools.CalcPickaxe;
import sonar.calculator.mod.common.item.tools.CalcShovel;
import sonar.calculator.mod.common.item.tools.CalcSword;
import sonar.calculator.mod.common.item.tools.ObsidianKey;
import sonar.calculator.mod.common.item.tools.Sickle;
import sonar.calculator.mod.common.item.tools.Wrench;
import sonar.core.utils.SonarSeeds;
import sonar.core.utils.SonarSeedsFood;
import cpw.mods.fml.common.registry.GameRegistry;

public class CalculatorItems extends Calculator {

	public static final Item.ToolMaterial ReinforcedStone = EnumHelper.addToolMaterial("ReinforcedStone", 1, 250, 5.0F, 1.5F, 5);
	public static final Item.ToolMaterial RedstoneMaterial = EnumHelper.addToolMaterial("RedstoneMaterial", 2, 800, 7.5F, 2.5F, 18);
	public static final Item.ToolMaterial EnrichedGold = EnumHelper.addToolMaterial("EnrichedGold", 3, 1000, 8.0F, 0.0F, 20);
	public static final Item.ToolMaterial ReinforcedIron = EnumHelper.addToolMaterial("ReinforcedIron", 2, 400, 7.0F, 2.0F, 10);
	public static final Item.ToolMaterial WeakenedDiamond = EnumHelper.addToolMaterial("WeakenedDiamond", 3, 1400, 8.0F, 3.0F, 10);
	public static final Item.ToolMaterial FlawlessDiamond = EnumHelper.addToolMaterial("FlawlessDiamond", 3, 1800, 14.0F, 5.0F, 30);
	public static final Item.ToolMaterial FireDiamond = EnumHelper.addToolMaterial("FireDiamond", 3, 2600, 16.0F, 7.0F, 30);
	public static final Item.ToolMaterial ElectricDiamond = EnumHelper.addToolMaterial("ElectricDiamond", 4, 10000, 18.0F, 10.0F, 30);
	public static final Item.ToolMaterial EndForged = EnumHelper.addToolMaterial("EndForged", 6, -1, 50F, 16.0F, 30);

	public static void registerItems() {

		// calculators
		itemInfoCalculator = new InfoCalc().setUnlocalizedName("InfoCalculator");
		GameRegistry.registerItem(itemInfoCalculator, "InfoCalculator");
		itemCalculator = new CalculatorItem().setUnlocalizedName("Calculator");
		GameRegistry.registerItem(itemCalculator, "Calculator");
		itemCraftingCalculator = new CraftingCalc().setUnlocalizedName("CraftingCalculator");
		GameRegistry.registerItem(itemCraftingCalculator, "CraftingCalculator");
		itemScientificCalculator = new ScientificCalc().setUnlocalizedName("ScientificCalculator");
		GameRegistry.registerItem(itemScientificCalculator, "ScientificCalculator");
		itemFlawlessCalculator = new FlawlessCalc().setUnlocalizedName("FlawlessCalculator");
		GameRegistry.registerItem(itemFlawlessCalculator, "FlawlessCalculator");

		// modules
		itemStorageModule = new StorageModule().setUnlocalizedName("StorageModule").setCreativeTab(Calculator).setTextureName(modid + ":" + "storage_module").setMaxStackSize(1);
		GameRegistry.registerItem(itemStorageModule, "StorageModule");
		// itemSmeltingModule = new WIPSmeltingModule().setUnlocalizedName("SmeltingModule").setCreativeTab(Calculator);
		// GameRegistry.registerItem(itemSmeltingModule, "SmeltingModule");
		itemHungerModule = new HungerModule().setUnlocalizedName("HungerModule").setTextureName(modid + ":" + "hungermodule");
		GameRegistry.registerItem(itemHungerModule, "HungerModule");
		itemHealthModule = new HealthModule().setUnlocalizedName("HealthModule").setTextureName(modid + ":" + "healthmodule");
		GameRegistry.registerItem(itemHealthModule, "HealthModule");
		itemNutritionModule = new NutritionModule().setUnlocalizedName("NutritionModule").setTextureName(modid + ":" + "nutritionmodule");
		GameRegistry.registerItem(itemNutritionModule, "NutritionModule");
		itemTerrainModule = new TerrainModule().setUnlocalizedName("TerrainModule").setTextureName(modid + ":" + "terrainmodule").setCreativeTab(Calculator).setMaxStackSize(1);
		GameRegistry.registerItem(itemTerrainModule, "TerrainModule");
		itemAdvancedTerrainModule = new AdvancedTerrainModule().setUnlocalizedName("AdvancedTerrainModule").setTextureName(modid + ":" + "advancedterrainmodule").setCreativeTab(Calculator).setMaxStackSize(1);
		GameRegistry.registerItem(itemAdvancedTerrainModule, "AdvancedTerrainModule");
		itemEnergyModule = new EnergyModule().setUnlocalizedName("EnergyModule").setTextureName(modid + ":" + "energy_module");
		GameRegistry.registerItem(itemEnergyModule, "EnergyModule");
		itemLocatorModule = new LocatorModule().setUnlocalizedName("LocatorModule").setCreativeTab(Calculator);
		GameRegistry.registerItem(itemLocatorModule, "LocatorModule");

		// misc
		soil = new Soil();
		GameRegistry.registerItem(soil, "Soil");
		small_stone = new SmallStone();
		GameRegistry.registerItem(small_stone, "SmallStone");

		// upgrades
		speedUpgrade = new UpgradeCircuit(0).setUnlocalizedName("SpeedUpgrade").setCreativeTab(Calculator).setTextureName(modid + ":" + "module_speed");
		GameRegistry.registerItem(speedUpgrade, "SpeedUpgrade");
		energyUpgrade = new UpgradeCircuit(1).setUnlocalizedName("EnergyUpgrade").setCreativeTab(Calculator).setTextureName(modid + ":" + "module_energy");
		GameRegistry.registerItem(energyUpgrade, "EnergyUpgrade");
		voidUpgrade = new UpgradeCircuit(2).setUnlocalizedName("VoidUpgrade").setCreativeTab(Calculator).setTextureName(modid + ":" + "module_void");
		GameRegistry.registerItem(voidUpgrade, "VoidUpgrade");

		// calculator parts
		calculator_screen = new CalculatorScreen().setUnlocalizedName("CalculatorScreen").setCreativeTab(Calculator).setTextureName(modid + ":" + "calculator_screen");
		GameRegistry.registerItem(calculator_screen, "CalculatorScreen");
		calculator_assembly = new Item().setUnlocalizedName("CalculatorAssembly").setCreativeTab(Calculator).setTextureName(modid + ":" + "calculator_assembly");
		GameRegistry.registerItem(calculator_assembly, "CalculatorAssembly");
		advanced_assembly = new Item().setUnlocalizedName("AdvancedAssembly").setCreativeTab(Calculator).setTextureName(modid + ":" + "advanced_assembly");
		GameRegistry.registerItem(advanced_assembly, "AdvancedAssembly");
		atomic_module = new Item().setUnlocalizedName("AtomicModule").setCreativeTab(Calculator).setTextureName(modid + ":" + "atomic_module");
		GameRegistry.registerItem(atomic_module, "AtomicModule");
		atomic_assembly = new Item().setUnlocalizedName("AtomicAssembly").setCreativeTab(Calculator).setTextureName(modid + ":" + "atomic_assembly");
		GameRegistry.registerItem(atomic_assembly, "AtomicAssembly");
		atomic_binder = new Item().setUnlocalizedName("AtomicBinder").setCreativeTab(Calculator).setTextureName(modid + ":" + "atomicbinder");
		GameRegistry.registerItem(atomic_binder, "AtomicBinder");

		// tools
		wrench = new Wrench().setUnlocalizedName("Wrench").setCreativeTab(Calculator).setMaxStackSize(1).setTextureName(modid + ":" + "wrench");
		GameRegistry.registerItem(wrench, "Wrench");
		sickle = new Sickle().setUnlocalizedName("Sickle").setCreativeTab(Calculator).setTextureName(modid + ":" + "sickle");
		GameRegistry.registerItem(sickle, "Sickle");
		ObsidianKey = new ObsidianKey().setUnlocalizedName("ObsidianKey").setCreativeTab(Calculator).setTextureName(modid + ":" + "codedkey");
		GameRegistry.registerItem(ObsidianKey, "ObsidianKey");

		// swords
		reinforced_sword = new CalcSword(ReinforcedStone).setUnlocalizedName("ReinforcedSword").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/reinforcedstone_sword");
		GameRegistry.registerItem(reinforced_sword, "ReinforcedSword");
		enrichedgold_sword = new CalcSword(EnrichedGold).setUnlocalizedName("EnrichedGoldSword").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/enrichedgold_sword");
		GameRegistry.registerItem(enrichedgold_sword, "EnrichedGoldSword");
		reinforcediron_sword = new CalcSword(ReinforcedIron).setUnlocalizedName("ReinforcedIronSword").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/reinforcediron_sword");
		GameRegistry.registerItem(reinforcediron_sword, "ReinforcedIronSword");
		redstone_sword = new CalcSword(RedstoneMaterial).setUnlocalizedName("RedstoneSword").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/redstone_sword");
		GameRegistry.registerItem(redstone_sword, "RedstoneSword");
		weakeneddiamond_sword = new CalcSword(WeakenedDiamond).setUnlocalizedName("WeakenedDiamondSword").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/weakeneddiamond_sword");
		GameRegistry.registerItem(weakeneddiamond_sword, "WeakenedDiamondSword");
		flawlessdiamond_sword = new CalcSword(FlawlessDiamond).setUnlocalizedName("FlawlessDiamondSword").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/flawlessdiamond_sword");
		GameRegistry.registerItem(flawlessdiamond_sword, "FlawlessDiamondSword");
		firediamond_sword = new CalcSword(FireDiamond).setUnlocalizedName("FireDiamondSword").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/firediamond_sword");
		GameRegistry.registerItem(firediamond_sword, "FireDiamondSword");
		electric_sword = new CalcSword(ElectricDiamond).setUnlocalizedName("ElectricSword").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/electric_sword");
		GameRegistry.registerItem(electric_sword, "ElectricSword");
		endforged_sword = new CalcSword(EndForged).setUnlocalizedName("EndForgedSword").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/endforged_sword");
		GameRegistry.registerItem(endforged_sword, "EndForgedSword");

		// pickaxe
		reinforced_pickaxe = new CalcPickaxe(ReinforcedStone).setUnlocalizedName("ReinforcedPickaxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/reinforcedstone_pickaxe");
		GameRegistry.registerItem(reinforced_pickaxe, "ReinforcedPickaxe");
		enrichedgold_pickaxe = new CalcPickaxe(EnrichedGold).setUnlocalizedName("EnrichedGoldPickaxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/enrichedgold_pickaxe");
		GameRegistry.registerItem(enrichedgold_pickaxe, "EnrichedGoldPickaxe");
		reinforcediron_pickaxe = new CalcPickaxe(ReinforcedIron).setUnlocalizedName("ReinforcedIronPickaxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/reinforcediron_pickaxe");
		GameRegistry.registerItem(reinforcediron_pickaxe, "ReinforcedIronPickaxe");
		redstone_pickaxe = new CalcPickaxe(RedstoneMaterial).setUnlocalizedName("RedstonePickaxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/redstone_pickaxe");
		GameRegistry.registerItem(redstone_pickaxe, "RedstonePickaxe");
		weakeneddiamond_pickaxe = new CalcPickaxe(WeakenedDiamond).setUnlocalizedName("WeakenedDiamondPickaxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/weakeneddiamond_pickaxe");
		GameRegistry.registerItem(weakeneddiamond_pickaxe, "WeakenedDiamondPickaxe");
		flawlessdiamond_pickaxe = new CalcPickaxe(FlawlessDiamond).setUnlocalizedName("FlawlessDiamondPickaxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/flawlessdiamond_pickaxe");
		GameRegistry.registerItem(flawlessdiamond_pickaxe, "FlawlessDiamondPickaxe");
		firediamond_pickaxe = new CalcPickaxe(FireDiamond).setUnlocalizedName("FireDiamondPickaxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/firediamond_pickaxe");
		GameRegistry.registerItem(firediamond_pickaxe, "FireDiamondPickaxe");
		electric_pickaxe = new CalcPickaxe(ElectricDiamond).setUnlocalizedName("ElectricPickaxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/electric_pickaxe");
		GameRegistry.registerItem(electric_pickaxe, "ElectricPickaxe");
		endforged_pickaxe = new CalcPickaxe(EndForged).setUnlocalizedName("EndForgedPickaxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/endforged_pickaxe");
		GameRegistry.registerItem(endforged_pickaxe, "EndForgedPickaxe");

		// axe
		reinforced_axe = new CalcAxe(ReinforcedStone).setUnlocalizedName("ReinforcedAxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/reinforcedstone_axe");
		GameRegistry.registerItem(reinforced_axe, "ReinforcedAxe");
		enrichedgold_axe = new CalcAxe(EnrichedGold).setUnlocalizedName("EnrichedGoldAxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/enrichedgold_axe");
		GameRegistry.registerItem(enrichedgold_axe, "EnrichedGoldAxe");
		reinforcediron_axe = new CalcAxe(ReinforcedIron).setUnlocalizedName("ReinforcedIronAxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/reinforcediron_axe");
		GameRegistry.registerItem(reinforcediron_axe, "ReinforcedIronAxe");
		redstone_axe = new CalcAxe(RedstoneMaterial).setUnlocalizedName("RedstoneAxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/redstone_axe");
		GameRegistry.registerItem(redstone_axe, "RedstonedAxe");
		weakeneddiamond_axe = new CalcAxe(WeakenedDiamond).setUnlocalizedName("WeakenedDiamondAxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/weakeneddiamond_axe");
		GameRegistry.registerItem(weakeneddiamond_axe, "WeakenedDiamondAxe");
		flawlessdiamond_axe = new CalcAxe(FlawlessDiamond).setUnlocalizedName("FlawlessDiamondAxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/flawlessdiamond_axe");
		GameRegistry.registerItem(flawlessdiamond_axe, "FlawlessDiamondAxe");
		firediamond_axe = new CalcAxe(FireDiamond).setUnlocalizedName("FireDiamondAxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/firediamond_axe");
		GameRegistry.registerItem(firediamond_axe, "FireDiamondAxe");
		electric_axe = new CalcAxe(ElectricDiamond).setUnlocalizedName("ElectricAxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/electric_axe");
		GameRegistry.registerItem(electric_axe, "ElectricAxe");
		endforged_axe = new CalcAxe(EndForged).setUnlocalizedName("EndForgedAxe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/endforged_axe");
		GameRegistry.registerItem(endforged_axe, "EndForgedAxe");

		// shovels
		reinforced_shovel = new CalcShovel(ReinforcedStone).setUnlocalizedName("ReinforcedShovel").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/reinforcedstone_shovel");
		GameRegistry.registerItem(reinforced_shovel, "ReinforcedShovel");
		enrichedgold_shovel = new CalcShovel(EnrichedGold).setUnlocalizedName("EnrichedGoldShovel").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/enrichedgold_shovel");
		GameRegistry.registerItem(enrichedgold_shovel, "EnrichedGoldShovel");
		reinforcediron_shovel = new CalcShovel(ReinforcedIron).setUnlocalizedName("ReinforcedIronShovel").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/reinforcediron_shovel");
		GameRegistry.registerItem(reinforcediron_shovel, "ReinforcedIronShovel");
		redstone_shovel = new CalcShovel(RedstoneMaterial).setUnlocalizedName("RedstoneShovel").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/redstone_shovel");
		GameRegistry.registerItem(redstone_shovel, "RedstoneShovel");
		weakeneddiamond_shovel = new CalcShovel(WeakenedDiamond).setUnlocalizedName("WeakenedDiamondShovel").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/weakeneddiamond_shovel");
		GameRegistry.registerItem(weakeneddiamond_shovel, "WeakenedDiamondShovel");
		flawlessdiamond_shovel = new CalcShovel(FlawlessDiamond).setUnlocalizedName("FlawlessDiamondShovel").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/flawlessdiamond_shovel");
		GameRegistry.registerItem(flawlessdiamond_shovel, "FlawlessDiamondShovel");
		firediamond_shovel = new CalcShovel(FireDiamond).setUnlocalizedName("FireDiamondShovel").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/firediamond_shovel");
		GameRegistry.registerItem(firediamond_shovel, "FireDiamondShovel");
		electric_shovel = new CalcShovel(ElectricDiamond).setUnlocalizedName("ElectricShovel").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/electric_shovel");
		GameRegistry.registerItem(electric_shovel, "ElectricShovel");
		endforged_shovel = new CalcShovel(EndForged).setUnlocalizedName("EndForgedShovel").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/endforged_shovel");
		GameRegistry.registerItem(endforged_shovel, "EndForgedShovel");

		// hoes
		reinforced_hoe = new CalcHoe(ReinforcedStone).setUnlocalizedName("ReinforcedHoe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/reinforcedstone_hoe");
		GameRegistry.registerItem(reinforced_hoe, "ReinforcedHoe");
		enrichedgold_hoe = new CalcHoe(EnrichedGold).setUnlocalizedName("EnrichedGoldHoe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/enrichedgold_hoe");
		GameRegistry.registerItem(enrichedgold_hoe, "EnrichedGoldHoe");
		reinforcediron_hoe = new CalcHoe(ReinforcedIron).setUnlocalizedName("ReinforcedIronHoe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/reinforcediron_hoe");
		GameRegistry.registerItem(reinforcediron_hoe, "ReinforcedIronHoe");
		redstone_hoe = new CalcHoe(RedstoneMaterial).setUnlocalizedName("RedstoneHoe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/redstone_hoe");
		GameRegistry.registerItem(redstone_hoe, "RedstoneHoe");
		weakeneddiamond_hoe = new CalcHoe(WeakenedDiamond).setUnlocalizedName("WeakenedDiamondHoe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/weakeneddiamond_hoe");
		GameRegistry.registerItem(weakeneddiamond_hoe, "WeakenedDiamondHoe");
		flawlessdiamond_hoe = new CalcHoe(FlawlessDiamond).setUnlocalizedName("FlawlessDiamondHoe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/flawlessdiamond_hoe");
		GameRegistry.registerItem(flawlessdiamond_hoe, "FlawlessDiamondHoe");
		firediamond_hoe = new CalcHoe(FireDiamond).setUnlocalizedName("FireDiamondHoe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/firediamond_hoe");
		GameRegistry.registerItem(firediamond_hoe, "FireDiamondHoe");
		electric_hoe = new CalcHoe(ElectricDiamond).setUnlocalizedName("ElectricHoe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/electric_hoe");
		GameRegistry.registerItem(electric_hoe, "ElectricHoe");
		endforged_hoe = new CalcHoe(EndForged).setUnlocalizedName("EndForgedHoe").setCreativeTab(Calculator).setTextureName(modid + ":" + "tools/endforged_hoe");
		GameRegistry.registerItem(endforged_hoe, "EndForgedHoe");

		// tool materials
		enrichedgold = new Item().setUnlocalizedName("EnrichedGold").setCreativeTab(Calculator).setTextureName(modid + ":" + "enrichedgold");
		GameRegistry.registerItem(enrichedgold, "EnrichedGold");
		enrichedgold_ingot = new Item().setUnlocalizedName("EnrichedGoldIngot").setCreativeTab(Calculator).setTextureName(modid + ":" + "enrichedgold_ingot");
		GameRegistry.registerItem(enrichedgold_ingot, "EnrichedGoldIngot");
		reinforcediron_ingot = new Item().setUnlocalizedName("ReinforcedIronIngot").setCreativeTab(Calculator).setTextureName(modid + ":" + "reinforcediron_ingot");
		GameRegistry.registerItem(reinforcediron_ingot, "ReinforcedIronIngot");
		redstone_ingot = new Item().setUnlocalizedName("RedstoneIngot").setCreativeTab(Calculator).setTextureName(modid + ":" + "redstone_ingot");
		GameRegistry.registerItem(redstone_ingot, "RedstoneIngot");
		weakeneddiamond = new Item().setUnlocalizedName("WeakenedDiamond").setCreativeTab(Calculator).setTextureName(modid + ":" + "weakeneddiamond");
		GameRegistry.registerItem(weakeneddiamond, "WeakenedDiamond");
		flawlessdiamond = new Item().setUnlocalizedName("FlawlessDiamond").setCreativeTab(Calculator).setTextureName(modid + ":" + "flawlessdiamond");
		GameRegistry.registerItem(flawlessdiamond, "FlawlessDiamond");
		flawlessfirediamond = new Item().setUnlocalizedName("FlawlessFireDiamond").setCreativeTab(Calculator).setTextureName(modid + ":" + "firediamond");
		GameRegistry.registerItem(flawlessfirediamond, "FlawlessFireDiamond");
		electricdiamond = new Item().setUnlocalizedName("ElectricDiamond").setCreativeTab(Calculator).setTextureName(modid + ":" + "electricdiamondanimate");
		GameRegistry.registerItem(electricdiamond, "ElectricDiamond");
		enddiamond = new EndDiamond().setUnlocalizedName("EndDiamond").setCreativeTab(Calculator).setTextureName(modid + ":" + "enddiamond").setMaxStackSize(16);
		GameRegistry.registerItem(enddiamond, "EndDiamond");

		// gems
		large_amethyst = new Item().setUnlocalizedName("LargeAmethyst").setTextureName(modid + ":" + "large_amethyst").setCreativeTab(Calculator);
		GameRegistry.registerItem(large_amethyst, "Large Amethyst");
		small_amethyst = new Item().setUnlocalizedName("SmallAmethyst").setTextureName(modid + ":" + "small_amethyst").setCreativeTab(Calculator);
		GameRegistry.registerItem(small_amethyst, "Small Amethyst");
		shard_amethyst = new Item().setUnlocalizedName("ShardAmethyst").setTextureName(modid + ":" + "shard_amethyst").setCreativeTab(Calculator);
		GameRegistry.registerItem(shard_amethyst, "Shard Amethyst");
		large_tanzanite = new Item().setUnlocalizedName("LargeTanzanite").setTextureName(modid + ":" + "large_tanzanite").setCreativeTab(Calculator);
		GameRegistry.registerItem(large_tanzanite, "LargeTanzanite");
		small_tanzanite = new Item().setUnlocalizedName("SmallTanzanite").setTextureName(modid + ":" + "small_tanzanite").setCreativeTab(Calculator);
		GameRegistry.registerItem(small_tanzanite, "SmallTanzanite");
		shard_tanzanite = new Item().setUnlocalizedName("ShardTanzanite").setTextureName(modid + ":" + "shard_tanzanite").setCreativeTab(Calculator);
		GameRegistry.registerItem(shard_tanzanite, "ShardTanzanite");

		// crops
		cropBroccoliPlant = new CalculatorCrops(0, 0).setBlockName("BroccoliPlant");
		GameRegistry.registerBlock(cropBroccoliPlant, "BroccoliPlant");
		cropPrunaePlant = new CalculatorCrops(1, 2).setBlockName("PrunaePlant");
		GameRegistry.registerBlock(cropPrunaePlant, "PrunaePlant");
		cropFiddledewPlant = new CalculatorCrops(2, 3).setBlockName("FiddledewPlant");
		GameRegistry.registerBlock(cropFiddledewPlant, "FiddledewPlant");

		// seeds
		broccoliSeeds = new SonarSeeds(cropBroccoliPlant, Blocks.farmland, 0).setUnlocalizedName("BroccoliSeeds").setCreativeTab(Calculator).setTextureName(modid + ":" + "broccoli_seeds");
		GameRegistry.registerItem(broccoliSeeds, "BroccoliSeeds");
		prunaeSeeds = new SonarSeeds(cropPrunaePlant, Blocks.farmland, 2).setUnlocalizedName("PrunaeSeeds").setCreativeTab(Calculator).setTextureName(modid + ":" + "prunae_seeds");
		GameRegistry.registerItem(prunaeSeeds, "PrunaeSeeds");
		fiddledewFruit = new SonarSeedsFood(16, 0.6F, cropFiddledewPlant, Blocks.farmland, 3).setUnlocalizedName("FiddledewFruit").setCreativeTab(Calculator).setTextureName(modid + ":" + "fiddledew_fruit");
		GameRegistry.registerItem(fiddledewFruit, "FiddledewFruit");

		// harvested
		broccoli = new ItemFood(1, 0.2F, false).setUnlocalizedName("Broccoli").setCreativeTab(Calculator).setTextureName(modid + ":" + "broccoli");
		GameRegistry.registerItem(broccoli, "Broccoli");
		pear = new ItemFood(12, 2.0F, false).setUnlocalizedName("Pear").setCreativeTab(Calculator).setTextureName(modid + ":" + "pear");
		GameRegistry.registerItem(pear, "Pear");
		rotten_pear = new ItemFood(1, 0.1F, false).setUnlocalizedName("RottenPear").setCreativeTab(Calculator).setTextureName(modid + ":" + "rotten_pear");
		GameRegistry.registerItem(rotten_pear, "RottenPear");

		// special harvested
		CookedBroccoli = new ItemFood(9, 0.6F, false).setUnlocalizedName("CookedBroccoli").setCreativeTab(Calculator).setTextureName(modid + ":" + "cookedbroccoli");
		GameRegistry.registerItem(CookedBroccoli, "CookedBroccoli");
		coal_dust = new Item().setUnlocalizedName("CoalDust").setCreativeTab(Calculator).setTextureName(modid + ":" + "coal_dust");
		GameRegistry.registerItem(coal_dust, "CoalDust");

		// fuels
		enriched_coal = new Item().setUnlocalizedName("EnrichedCoal").setCreativeTab(Calculator).setTextureName(modid + ":" + "enriched_coal");
		GameRegistry.registerItem(enriched_coal, "EnrichedCoal");
		purified_coal = new Item().setUnlocalizedName("PurifiedCoal").setCreativeTab(Calculator).setTextureName(modid + ":" + "purified_coal");
		GameRegistry.registerItem(purified_coal, "PurifiedCoal");
		firecoal = new Item().setUnlocalizedName("FireCoal").setCreativeTab(Calculator).setTextureName(modid + ":" + "firecoal");
		GameRegistry.registerItem(firecoal, "FireCoal");
		controlled_Fuel = new Item().setUnlocalizedName("ControlledFuel").setCreativeTab(Calculator).setTextureName(modid + ":" + "controlled_fuel");
		GameRegistry.registerItem(controlled_Fuel, "ControlledFuel");

		// grenades
		grenadecasing = new Item().setUnlocalizedName("GrenadeCasing").setCreativeTab(Calculator).setTextureName(modid + ":" + "grenadecasing");
		GameRegistry.registerItem(grenadecasing, "GrenadeCasing");
		baby_grenade = new Grenade(0).setUnlocalizedName("BabyGrenade").setCreativeTab(Calculator).setTextureName(modid + ":" + "baby_grenade");
		GameRegistry.registerItem(baby_grenade, "BabyGrenade");
		grenade = new Grenade(1).setUnlocalizedName("Grenade").setCreativeTab(Calculator).setTextureName(modid + ":" + "grenade");
		GameRegistry.registerItem(grenade, "Grenade");
		circuitBoard = new ItemCircuit().setUnlocalizedName("CircuitBoard").setCreativeTab(Calculator).setMaxStackSize(1);
		GameRegistry.registerItem(circuitBoard, "CircuitBoard");
		circuitDamaged = new ItemCircuitDamaged().setUnlocalizedName("CircuitDamaged").setCreativeTab(Calculator).setMaxStackSize(1);
		GameRegistry.registerItem(circuitDamaged, "CircuitDamaged");
		circuitDirty = new ItemCircuitDirty().setUnlocalizedName("CircuitDirty").setCreativeTab(Calculator).setMaxStackSize(1);
		GameRegistry.registerItem(circuitDirty, "CircuitDirty");

		// chest generation items
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(circuitBoard, 0, 1, 1, 2));
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(circuitDirty, 0, 1, 1, 5));
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(circuitDamaged, 0, 1, 1, 5));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(itemCalculator, 0, 1, 1, 4));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(itemCraftingCalculator, 0, 1, 1, 4));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(itemInfoCalculator, 0, 1, 1, 4));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(Item.getItemFromBlock(reinforcedstoneBlock), 0, 5, 20, 12));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(wrench, 0, 1, 1, 3));
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(itemInfoCalculator, 0, 1, 1, 10));

	}
}
