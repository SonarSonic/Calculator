package sonar.calculator.mod.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.item.misc.ItemCircuit;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;

public class InfoList {
	private static final InfoList infoBase = new InfoList();

	private Map info = new HashMap();
	private Map infoTypes = new HashMap();

	public static InfoList info() {
		return infoBase;
	}

	private InfoList() {

		addRecipe("Machines", new ItemStack(Calculator.powerCube), "Charges Calculators- -Uses RF or EU");
		addRecipe("Machines", new ItemStack(Calculator.advancedPowerCube), "Charges Calculators quickly- -Uses RF or EU--Can convert EU to RF and back");
		addRecipe("Calculators", new ItemStack(Calculator.itemCalculator), "Adds two items together.");
		addRecipe("Calculators", new ItemStack(Calculator.itemCraftingCalculator), "Portable Crafting Table.");
		addRecipe("Calculators", new ItemStack(Calculator.itemScientificCalculator), "Adds two items together.");
		addRecipe("Calculators", new ItemStack(Calculator.itemFlawlessCalculator), "Has 6 Different Modes-Changed by Shift Clicking-Flawless Crafting-Dynamic Crafting-Normal Crafting-Spawn Grenade-Spawn Ender Pearl-Teleport (See Stable Stone)");
		addRecipe("Machines", new ItemStack(Calculator.stoneSeperator), "A basic seperator-+Used for making Amethyst-+Runs on RF");
		addRecipe("Machines", new ItemStack(Calculator.algorithmSeperator), "A basic seperator-+Used for making Tanzanite-+Runs on RF");
		addRecipe("Items", new ItemStack(Calculator.wrench), "Right Click Changes slot access-for Machines-Shift Click drops the block-and stores its energy");
		addRecipe("Machines", new ItemStack(Calculator.analysingChamber), "Analyses Circuits-+Extracts their items & energy-+'Stable' will appear for Stable- Circuits");
		addRecipe("Calculators", new ItemStack(Calculator.atomiccalculatorBlock), "Adds three items together-Doesn't require RF or EU");
		addRecipe("Items", new ItemStack(Calculator.baby_grenade), "Right Click throws it-Be Careful!");
		addRecipe("Items", new ItemStack(Calculator.grenade), "Right Click throws it-Be Careful!");
		addRecipe("Generators", new ItemStack(Calculator.calculatorlocator), "Power generation system-Multiblock structure required-Also requires an owner-(See Locator Module)-To avoid explosions you need- 100% Stability-See Circuits & Calculator Plug- -For structure see Curse Page");
		addRecipe("Generators", new ItemStack(Calculator.calculatorplug), "Part of the Calculator Locator-It stabilises the locator- -See Circuits for more info.");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard, 1, 1), "Used for Controlled Fuel-Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard, 1, 2), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard, 1, 3), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard, 1, 4), "Used for Speed Upgrades-Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard, 1, 5), "Used for Energy Upgrades-Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard, 1, 6), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard, 1, 7), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard, 1, 8), "Crafting item for CO2 Generator-Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard, 1, 9), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard, 1, 10), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard, 1, 11), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard, 1, 12), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Circuits", new ItemStack(Calculator.circuitBoard, 1, 13), "Has Recipes, Items & Energy-Extract with Analysing Chamber- -'Stable' will appear for Stable-Circuits");
		addRecipe("Generators", new ItemStack(Calculator.crank), "For Hand Cranked Generator-Right click to generate power");
		addRecipe("Calculators", new ItemStack(Calculator.dynamiccalculatorBlock), "Combines all Calcs into one!- -Doesn't require power");
		addRecipe("Machines", new ItemStack(Calculator.extractionChamber), "Extracts Circuits from-Dirt or Stone");
		addRecipe("Generators", new ItemStack(Calculator.handcrankedGenerator), "Generates power by cranking- -Requires Crank Handle on top");
		addRecipe("Machines", new ItemStack(Calculator.healthprocessor), "Converts monster drops into-points- -Add points to Health Stores");
		addRecipe("Machines", new ItemStack(Calculator.hungerprocessor), "Converts food into points- -Add points to Hunger Stores");
		addRecipe("Modules", new ItemStack(Calculator.itemAdvancedTerrainModule), "Instantly switches blocks-It can switch-Stone, Grass, Dirt-Gravel, Sand, Cobblestone");
		addRecipe("Modules", new ItemStack(Calculator.itemTerrainModule), "Instantly switches blocks--It can switch-Stone, Grass, Dirt");
		addRecipe("Modules", new ItemStack(Calculator.itemEnergyModule), "Can store up to " + CalculatorConfig.moduleEnergy + " RF" + "-or " + (CalculatorConfig.moduleEnergy / 4) + " EU");
		addRecipe("Modules", new ItemStack(Calculator.itemHealthModule), "Can store Health Points-To get Health Points-+See Health Processor-+Or right click Tanzanite Trees");
		addRecipe("Modules", new ItemStack(Calculator.itemHungerModule), "Can store Hunger Points-To get Hunger Points-+See Hunger Processor-+Or right click Amethyst Trees");
		addRecipe("Modules", new ItemStack(Calculator.itemLocatorModule), "Activates Calculator Locator--Right click stores username");
		addRecipe("Modules", new ItemStack(Calculator.itemNutritionModule), "Health & Hunger Modules in one!--Maintains both constantly");
		addRecipe("Modules", new ItemStack(Calculator.itemStorageModule), "A portable double size chest");
		addRecipe("Blocks", new ItemStack(Calculator.ObsidianKey), "See Purified Obsidian");
		addRecipe("Machines", new ItemStack(Calculator.processingChamber), "Combines Restoration & -Reassembly Chambers");
		addRecipe("Blocks", new ItemStack(Calculator.purifiedobsidianBlock), "Indestructable/Security block--Only removable with Obsidian Key");
		addRecipe("Machines", new ItemStack(Calculator.reassemblyChamber), "Reassembles Damaged Circuits");
		addRecipe("Machines", new ItemStack(Calculator.restorationChamber), "Restores Dirty Circuits");
		addRecipe("Items", new ItemStack(Calculator.sickle), "Can remove berries/diamonds- from their trees");
		addRecipe("Generators", new ItemStack(Calculator.starchextractor), "Uses Starch to generate RF-Also Requires Fuel--Starch = Leaves,Saplings etc");
		addRecipe("Generators", new ItemStack(Calculator.glowstoneextractor), "Uses Glowstone to generate RF-Also Requires Fuel");
		addRecipe("Generators", new ItemStack(Calculator.redstoneextractor), "Uses Redstone to generate RF-Also Requires Fuel");
		addRecipe("Blocks", new ItemStack(Calculator.stablestoneBlock), "Part of the Calculator Locator-Teleporting(see Flawless Calc)-Right Click to set location-Right click then teleports you-Remove to change location");
		addRecipe("Circuits", new ItemStack(Calculator.energyUpgrade), "Reduces energy consumption-Right click the machine-you want to upgrade--Destroy block to remove");
		addRecipe("Circuits", new ItemStack(Calculator.speedUpgrade), "Increases speed-Right click the machine-you want to upgrade--Destroy block to remove");
		addRecipe("Circuits", new ItemStack(Calculator.voidUpgrade), "For Analysing Chamber-Destroys useless items extracted- from circuits");
		addRecipe("Machines", new ItemStack(Calculator.atomicMultiplier), "Can quadruple almost any item--Requires seven circuits-and " + TileEntityAtomicMultiplier.requiredEnergy + "RF--or " + (TileEntityAtomicMultiplier.requiredEnergy / 4) + "EU");
		addRecipe("Machines", new ItemStack(Calculator.conductorMast), "Conducts and stores Lightning-Can infuse items with electricity--Also it can generate RF/EU-Activated by Redstone Signal-See Transmitter & Weather - Station");
		addRecipe("Items", new ItemStack(Calculator.soil), "Can blind entities when thrown--Can change Dirt to Gravel");
		addRecipe("Items", new ItemStack(Calculator.small_stone), "Can damage entities when thrown--Can change Dirt to Farmland");
		addRecipe("Items", new ItemStack(Calculator.enddiamond), "If you want Ender Pearls-Look no further than Right Click--Makes unbreakable tools");
		addRecipe("Machines", new ItemStack(Calculator.precisionChamber), "Similar to Extraction-But can get the exact Circuit-e.g. Energy Upgrade Circuits");
		addRecipe("Machines", new ItemStack(Calculator.storageChamber), "Vast Storage Unit-For all 14 Circuits-With matching types");
		addRecipe("Machines", new ItemStack(Calculator.basicGreenhouse), "An Environment for Crop Growth-Builds itself automatically-Requires Energy & Chests-See Gas Lanterns- -For structure see Curse Page");
		addRecipe("Machines", new ItemStack(Calculator.advancedGreenhouse), "An Environment for Crop Growth-Builds itself automatically-Requires Energy & Chests-See Gas Lanterns- -For structure see Curse Page");
		addRecipe("Machines", new ItemStack(Calculator.flawlessGreenhouse), "An Environment for Crop Growth-Must be built by hand-Requires Energy & Chests-See Carbon Dioxide Generator- -For structure see Curse Page");
		addRecipe("Machines", new ItemStack(Calculator.carbondioxideGenerator), "Burns fuels to create CO2-Used with Flawless Greenhouse-See Controlled Fuel");
		addRecipe("Items", new ItemStack(Calculator.controlled_Fuel), "Controls its own burning--Only works in-Carbon Dioxide Generator");
		addRecipe("Items", new ItemStack(Calculator.gas_lantern_off), "Used with Advanced/Basic- Greenhouses-Can creates CO2 from fuel-This accelerates plant growth");
		addRecipe("Items", new ItemStack(Calculator.basic_lantern), "The better torch");
		addRecipe("Blocks", new ItemStack(Calculator.scarecrow), "Accelerates crop growth--Requires nothing!");
		addRecipe("Calculators", new ItemStack(Calculator.itemInfoCalculator), "Shows all Calculator Information--Only works with NEI");
		addRecipe("Circuits", new ItemStack(Calculator.atomic_assembly), "Used in advanced things--Have fun crafting!");
		addRecipe("Items", new ItemStack(Calculator.fiddledewFruit), "The best fruit ever-Cures 8 hearts--Only grows in-Flawless Greenhouse");
		addRecipe("Items", new ItemStack(Calculator.prunaeSeeds), "Grows coal dust--Only grows in-Advanced Greenhouse");
		addRecipe("Machines", new ItemStack(Calculator.reinforcedFurnace), "A better furnace--Runs off RF/EU");
		// addRecipe("Machines",new ItemStack(Calculator.researchChamber),
		// "For researching new recipes--Doesn't require energy--Some blocks/items and Stable - Circuits-can be researched");
		addRecipe("Blocks", new ItemStack(Calculator.flawlessGlass), "V. Expensive Glass--Used in Flawless Greenhouses");
		addRecipe("Machines", new ItemStack(Calculator.transmitter), "Can alter the Ionosphere-Increases power and frequency- of Lightning Strikes--See Conductor Mast");
		addRecipe("Machines", new ItemStack(Calculator.weatherStation), "Focuses Lightning Power-Increasing Lightning Energy--See Conductor Mast");
		addRecipe("Items", new ItemStack(Calculator.endforged_sword), "The GOD of Swords");
		addRecipe("Items", new ItemStack(Calculator.endforged_hoe), "Your only desire");
		addRecipe("Items", new ItemStack(Calculator.broccoli), "Eat your greens!");
		addRecipe("Items", new ItemStack(Calculator.CookedBroccoli), "Eat your greens cooked!");
		addRecipe("Items", new ItemStack(Calculator.rotten_pear), "Replacement for Slime Balls");
		addRecipe("Blocks", new ItemStack(Calculator.magneticFlux), "Magnetises items on the ground-Into a chest below");
		addRecipe("Blocks", new ItemStack(Calculator.fluxPlug), "Adds flux to Flux Network");
		addRecipe("Blocks", new ItemStack(Calculator.fluxPoint), "Removes flux from Flux Network");
		addRecipe("Machines", new ItemStack(Calculator.fluxController), "Controls energy flow-in a Flux Network");
		addRecipe("Machines", new ItemStack(Calculator.teleporter), "A simple teleporter -+Can be password protected");
		addRecipe("Blocks", new ItemStack(Calculator.weatherStation), "Has the ability to-control the weather");
		addRecipe("Items", new ItemStack(Calculator.calculator_screen), "Displays the RF stored-in machines when placed-on the side");
		
	}

	public void addRecipe(String type, ItemStack input, String info) {
		this.info.put(input, info);

		this.infoTypes.put(input, type);

	}

	public String getInfo(ItemStack stack) {
		Iterator iterator = this.info.entrySet().iterator();

		Map.Entry entry;

		do {
			if (!iterator.hasNext()) {
				return null;
			}

			entry = (Map.Entry) iterator.next();
		} while (!func_151397_a(stack, (ItemStack) entry.getKey()));

		return (String) entry.getValue();
	}

	public String getType(ItemStack stack) {
		Iterator iterator = this.infoTypes.entrySet().iterator();

		Map.Entry entry;

		do {
			if (!iterator.hasNext()) {
				return null;
			}

			entry = (Map.Entry) iterator.next();
		} while (!func_151397_a(stack, (ItemStack) entry.getKey()));

		return (String) entry.getValue();
	}

	private boolean func_151397_a(ItemStack p_151397_1_, ItemStack p_151397_2_) {
		return (p_151397_2_.getItem() == p_151397_1_.getItem()) && ((p_151397_2_.getItemDamage() == 32767) || (p_151397_2_.getItemDamage() == p_151397_1_.getItemDamage()));
	}

	public Map getInfoList() {
		return this.info;
	}

	public Map getInfoType() {
		return this.infoTypes;
	}

}
