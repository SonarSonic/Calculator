package sonar.calculator.mod.common.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gnu.trove.map.hash.THashMap;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorItems;
import sonar.calculator.mod.common.block.MaterialBlock.Variants;
import sonar.core.SonarCore;
import sonar.core.helpers.RecipeHelper;

public class RecipeRegistry {

	private static final Map<String, ArrayList<Object[]>> blocked = new THashMap<String, ArrayList<Object[]>>();
	private static final List<Object[]> unblocked = new ArrayList();
	private static final List<Object[]> scientific = new ArrayList();
	private static final List<Object[]> atomic = new ArrayList();
	private static final List<Object[]> flawless = new ArrayList();
	private static final Map<String, List<Object[]>> machines = new THashMap<String, List<Object[]>>();

	public static void clearRecipes() {
		blocked.clear();
		unblocked.clear();
		scientific.clear();
		atomic.clear();
		flawless.clear();
		machines.clear();
	}

	public static void registerRecipes() {
		addStandardRecipes();
		addScientificRecipes();
		addAtomicRecipes();
		addFlawlessRecipes();
	}

	/** @param objects four parameters, see below
	 * @param output (ItemStack)
	 * @param input1 (ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input2 (ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param hidden Does the recipeOutput require research? (Boolean) */
	public static void registerCalculatorRecipe(Object... objects) {
		if (objects.length < 3) {
			Calculator.logger.warn("Calculator Recipes - Invalid Recipe Size!");
			return;
		}
		rearrangeCalculatorRecipe(objects);
	}

	/** @param objects three parameters, see below
	 * @param input1 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input2 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param output (Item, Block, ItemStack) */
	public static void registerScientificRecipe(Object... objects) {
		if (objects.length != 3) {
			Calculator.logger.warn("Scientific Recipes - Invalid Recipe Size!");
			return;
		}
		scientific.add(objects);
	}

	/** @param objects four parameters, see below
	 * @param input1 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input2 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input3 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param output (Item, Block, ItemStack) */
	public static void registerAtomicRecipe(Object... objects) {
		if (objects.length != 4) {
			Calculator.logger.warn("Atomic Recipes - Invalid Recipe Size!");
			return;
		}
		atomic.add(objects);
	}

	/** @param objects five parameters, see below
	 * @param input1 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input2 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input3 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input4 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param output (Item, Block, ItemStack) */
	public static void registerFlawlessRecipe(Object... objects) {
		if (objects.length != 5) {
			FMLLog.warning("Flawless Recipes - Invalid Recipe Size!");
			return;
		}
		flawless.add(objects);
	}

	public enum RecipeType {
		REINFORCED_STONE(SonarCore.reinforcedStoneBlock, SonarCore.reinforcedDirtBlock, "reinforcedStone"),
		/**/
		CROP(Items.WHEAT, Items.POTATO, Items.CARROT, "cropWheat", "cropPotato", "cropCarrot"),
		/**/
		SLIMEBALL(Items.SLIME_BALL, "slimeball"),
		/**/
		IRON(Blocks.IRON_ORE, Items.IRON_INGOT, "oreIron", "blockIron", "ingotIron"),
		/**/
		EMERALD("oreEmerald", "blockEmerald", "gemEmerald"),
		/**/
		FLOWER(Blocks.RED_FLOWER, Blocks.YELLOW_FLOWER),
		/**/
		CACTUS(Blocks.CACTUS),
		/**/
		NETHER(Blocks.OBSIDIAN, Blocks.NETHERRACK, Blocks.SOUL_SAND),
		/**/
		END(Blocks.END_STONE, Items.ENDER_PEARL),
		/**/
		SAND(Blocks.SAND, "sand"),
		/**/
		GRASS(Blocks.GRASS, "grass"),
		/**/
		SANDSTONE(Blocks.SANDSTONE, Blocks.SANDSTONE_STAIRS),
		/**/
		DIRT(Blocks.DIRT, "dirt"),
		/**/
		STONE(Blocks.STONE, "stone"),
		/**/
		GRAVEL(Blocks.GRAVEL, "gravel"),
		/**/
		COBBLESTONE(Blocks.COBBLESTONE, "cobblestone"),
		/**/
		WOOD(Blocks.LOG, Blocks.LOG2, "logWood"),
		/**/
		PLANKS(Blocks.PLANKS, "plankWood", "stairWood"),
		/**/
		LEAVES(Blocks.LEAVES, Blocks.LEAVES2, "calculatorLeaves"),
		/**/
		SAPLING(Blocks.SAPLING, "treeSapling"),
		/**/
		GLASS(Blocks.GLASS, "blockGlass"),
		/**/
		GLASS_PANE(Blocks.GLASS_PANE, "paneGlass"),
		/**/
		FURNACE(Blocks.FURNACE),
		/**/
		DYE(Items.DYE, "dye"),
		/**/
		CARPET(Blocks.CARPET, "carpet"),
		/**/
		CLAY_BLOCK(Items.CLAY_BALL, Blocks.CLAY, Blocks.HARDENED_CLAY, "clay"), FOLIAGE(Blocks.DOUBLE_PLANT, Blocks.DEADBUSH, Blocks.VINE, Blocks.WATERLILY),
		/**/
		WOOL(Blocks.WOOL, "wool");

		public Object[] items;

		RecipeType(Object... items) {
			this.items = items;
		}

		public static ArrayList<RecipeType> getUnlocked(ItemStack stack) {
			ArrayList<RecipeType> unlocked = new ArrayList<RecipeType>();
			int[] ids = OreDictionary.getOreIDs(stack);
			for (RecipeType type : RecipeType.values()) {
				for (Object object : type.items) {
					if (stack.getItem() == object || Block.getBlockFromItem(stack.getItem()) == object) {
						unlocked.add(type);
						break;
					} else if (object instanceof String) {
						int oreID = OreDictionary.getOreID((String) object);
						for (int id : ids) {
							if (id == oreID) {
								unlocked.add(type);
								break;
							}
						}
					}
				}
			}
			return unlocked;
		}
	}

	private static void addStandardRecipes() {
		registerCalculatorRecipe(Calculator.baby_grenade, Calculator.grenadecasing, Blocks.TNT);
		registerCalculatorRecipe(SonarCore.reinforcedStoneBlock, "cobblestone", "plankWood");
		registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedStoneBlock, 4), "cobblestone", "logWood");

		registerCalculatorRecipe(Calculator.wrench, Calculator.reinforced_sword, Calculator.reinforced_pickaxe);
		registerCalculatorRecipe(SonarCore.reinforcedDirtBlock, Blocks.DIRT, "plankWood");
		registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 4), Blocks.DIRT, "logWood");
		registerCalculatorRecipe(new ItemStack(Calculator.enrichedGold, 4), "ingotGold", "dustRedstone");
		registerCalculatorRecipe(Calculator.reinforcediron_ingot, "ingotIron", SonarCore.reinforcedStoneBlock);
		registerCalculatorRecipe(Calculator.enriched_coal, Items.COAL, "dustRedstone");
		registerCalculatorRecipe(Calculator.broccoliSeeds, Items.WHEAT_SEEDS, Items.PUMPKIN_SEEDS);
		registerCalculatorRecipe(Calculator.sickle, Calculator.reinforced_shovel, Calculator.reinforced_axe);
		registerCalculatorRecipe(Calculator.scarecrow, Blocks.PUMPKIN, Blocks.HAY_BLOCK);
		registerCalculatorRecipe(Calculator.basic_lantern, Blocks.TORCH, Calculator.reinforcediron_ingot);
		registerCalculatorRecipe(Calculator.gas_lantern_off, Calculator.basic_lantern, Calculator.basic_lantern);
		registerCalculatorRecipe(Calculator.prunaeSeeds, Calculator.enriched_coal, Items.WHEAT_SEEDS);
		registerCalculatorRecipe(Calculator.enriched_coal, Calculator.coal_dust, Calculator.coal_dust);
		registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedStoneBrick, 2), SonarCore.reinforcedStoneBlock, SonarCore.reinforcedStoneBlock);
		registerCalculatorRecipe(new ItemStack(SonarCore.stableGlass, 2), "blockGlass", "blockGlass");
		registerCalculatorRecipe(new ItemStack(SonarCore.stableStone[0], 2), SonarCore.reinforcedStoneBrick, SonarCore.reinforcedStoneBrick);
		registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedDirtBrick, 2), SonarCore.reinforcedDirtBlock, SonarCore.reinforcedDirtBlock);
		registerCalculatorRecipe(Calculator.rainSensor, Blocks.DAYLIGHT_DETECTOR, Items.BUCKET);

		// registerCalculatorRecipe(Calculator.researchChamber, Calculator.reinforced_iron_block, Calculator.powerCube);

		// reinforced materials
		/*RecipeType type = RecipeType.REINFORCED_STONE; registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedStoneBlock, 8), "cobblestone", Items.clay_ball, type); registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 2, 2), "stone", new ItemStack(Blocks.dirt, 1, 2), type); registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 1, 2), "cobblestone", new ItemStack(Blocks.dirt, 1, 2), type); registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 4), new ItemStack(Blocks.grass, 1), Blocks.log, type); registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 4), new ItemStack(Blocks.grass, 1), Blocks.log2, type); registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 1), new ItemStack(Blocks.grass, 1), new ItemStack(Blocks.planks, 1), type); registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 1), Blocks.dirt, "plankWood", type); registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 4), Blocks.dirt, "logWood", type); registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedStoneBlock, 1), "cobblestone", "plankWood", type); registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedStoneBlock, 4), "cobblestone", "logWood", type); registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedStoneBlock, 6), "stone", "logWood", type); registerCalculatorRecipe(new ItemStack(SonarCore.reinforcedStoneBlock, 2), "stone", "plankWood", type);
		 * 
		 * // furnace type = RecipeType.FURNACE; registerCalculatorRecipe(Calculator.powerCube, Blocks.furnace, "stone", type); registerCalculatorRecipe(Calculator.powerCube, Blocks.furnace, "cobblestone", type); registerCalculatorRecipe(Calculator.powerCube, Blocks.furnace, SonarCore.reinforcedStoneBlock, type); registerCalculatorRecipe(Calculator.stoneSeparator, Blocks.furnace, Blocks.furnace, type); registerCalculatorRecipe(new ItemStack(Blocks.furnace, 1), Blocks.crafting_table, "stone", type); registerCalculatorRecipe(new ItemStack(Blocks.furnace, 1), Blocks.crafting_table, "cobblestone", type); registerCalculatorRecipe(Items.brewing_stand, Items.blaze_powder, Blocks.furnace, type); registerCalculatorRecipe(new ItemStack(Blocks.crafting_table, 2), Blocks.furnace, new ItemStack(Blocks.planks, 1), type);
		 * 
		 * // cobblestone type = RecipeType.COBBLESTONE; registerCalculatorRecipe(new ItemStack(Blocks.mossy_cobblestone, 4), "stone", "treeSapling", type); registerCalculatorRecipe(new ItemStack(Blocks.mossy_cobblestone, 2), "stone", "treeLeaves", type); registerCalculatorRecipe(new ItemStack(Blocks.mossy_cobblestone, 2), "cobblestone", "treeSapling", type); registerCalculatorRecipe(new ItemStack(Blocks.mossy_cobblestone, 1), "cobblestone", "treeLeaves", type); registerCalculatorRecipe(new ItemStack(Blocks.mossy_cobblestone, 2), Blocks.vine, "cobblestone", type); registerCalculatorRecipe(new ItemStack(Blocks.mossy_cobblestone, 4), Blocks.vine, "stone", type);
		 * 
		 * // stone type = RecipeType.STONE; registerCalculatorRecipe(new ItemStack(Blocks.stonebrick, 1, 2), "cobblestone", "stone", type); registerCalculatorRecipe(Blocks.stone, "cobblestone", "cobblestone", type); registerCalculatorRecipe(Blocks.stone, Calculator.small_stone, type); registerCalculatorRecipe(Blocks.stone, Blocks.gravel, Blocks.gravel, type);
		 * 
		 * // dirt type = RecipeType.DIRT; registerCalculatorRecipe(new ItemStack(Blocks.dirt, 1), Calculator.soil, type); registerCalculatorRecipe(new ItemStack(Blocks.dirt, 1), Blocks.gravel, "sand", type); registerCalculatorRecipe(new ItemStack(Blocks.dirt, 1), "sand", "sand", RecipeType.DIRT); registerCalculatorRecipe(new ItemStack(Blocks.dirt, 1, 2), new ItemStack(Blocks.grass, 1), Blocks.dirt, type); // registerCalculatorRecipe(new ItemStack(Blocks.dirt, 1, 2), new ItemStack(Blocks.grass, 1), new ItemStack(Blocks.red_mushroom_block, 1, 5), type); // registerCalculatorRecipe(new ItemStack(Blocks.dirt, 1, 2), new ItemStack(Blocks.grass, 1), new ItemStack(Blocks.brown_mushroom_block, 1, 5), type); registerCalculatorRecipe(Blocks.mycelium, new ItemStack(Blocks.dirt, 1, 2), type); registerCalculatorRecipe(new ItemStack(Blocks.mycelium, 2), new ItemStack(Blocks.grass, 1), new ItemStack(Blocks.dirt, 1, 2), type); registerCalculatorRecipe(new ItemStack(Blocks.mycelium, 2), new ItemStack(Blocks.grass, 1), new ItemStack(Blocks.grass, 1), type); registerCalculatorRecipe(Blocks.dirt, Blocks.sand, Calculator.soil, type);
		 * 
		 * // sand type = RecipeType.SAND; registerCalculatorRecipe(Blocks.sand, Blocks.gravel, new ItemStack(Blocks.grass, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.sand, 1), "cobblestone", Blocks.gravel, type); registerCalculatorRecipe(new ItemStack(Blocks.sand, 2), "stone", Blocks.gravel, type); registerCalculatorRecipe(Blocks.sand, Blocks.gravel, Blocks.dirt, type);
		 * 
		 * // gravel type = RecipeType.GRAVEL; registerCalculatorRecipe(Blocks.gravel, Blocks.sand, new ItemStack(Blocks.grass, 1), type); registerCalculatorRecipe(Blocks.gravel, new ItemStack(Blocks.grass, 1), "cobblestone", type); registerCalculatorRecipe(Blocks.gravel, "cobblestone", Blocks.dirt, type); registerCalculatorRecipe(Blocks.gravel, "stone", Blocks.dirt, type); registerCalculatorRecipe(Blocks.gravel, Blocks.sand, Blocks.dirt, type); registerCalculatorRecipe(new ItemStack(Blocks.gravel, 2), "stone", Blocks.sand, type); registerCalculatorRecipe(new ItemStack(Blocks.gravel, 1), "cobblestone", Blocks.sand, type); registerCalculatorRecipe(new ItemStack(Blocks.gravel, 2), Blocks.dirt, Blocks.dirt, type); registerCalculatorRecipe(new ItemStack(Blocks.gravel, 3), "stone", new ItemStack(Blocks.grass, 1), type); registerCalculatorRecipe(Blocks.gravel, Blocks.dirt, Calculator.small_stone, type);
		 * 
		 * // sandstone type = RecipeType.SANDSTONE; registerCalculatorRecipe(new ItemStack(Blocks.sandstone, 1, 2), "stone", Blocks.sandstone, type); registerCalculatorRecipe(new ItemStack(Blocks.sandstone, 1, 1), "cobblestone", Blocks.sandstone, type);
		 * 
		 * // grass type = RecipeType.GRASS; registerCalculatorRecipe(new ItemStack(Blocks.grass, 2), Items.wheat_seeds, Blocks.dirt, type); registerCalculatorRecipe(new ItemStack(Blocks.grass, 2), Items.pumpkin_seeds, Blocks.dirt, type); registerCalculatorRecipe(new ItemStack(Blocks.grass, 4), Calculator.broccoliSeeds, Blocks.dirt, type); registerCalculatorRecipe(Blocks.grass, Blocks.dirt, Calculator.soil, type); registerCalculatorRecipe(Blocks.grass, Blocks.dirt, new ItemStack(Blocks.tallgrass, 1, 1), type);
		 * 
		 * // glass type = RecipeType.GLASS; registerCalculatorRecipe(new ItemStack(Blocks.stained_glass, 1, 8), "stone", Blocks.glass, type); registerCalculatorRecipe(new ItemStack(Blocks.stained_glass, 1, 7), "cobblestone", Blocks.glass, type);
		 * 
		 * // glass_pane type = RecipeType.GLASS_PANE; registerCalculatorRecipe(new ItemStack(Blocks.stained_glass_pane, 1, 8), "stone", Blocks.glass_pane, type); registerCalculatorRecipe(new ItemStack(Blocks.stained_glass_pane, 1, 7), "cobblestone", Blocks.glass_pane, type);
		 * 
		 * // cactus type = RecipeType.CACTUS; registerCalculatorRecipe(Blocks.cactus, "sand", "treeSapling", type); registerCalculatorRecipe(Blocks.cactus, "sand", Items.wheat_seeds, type); registerCalculatorRecipe(Blocks.cactus, "sand", "treeLeaves", type);
		 * 
		 * // nether type = RecipeType.NETHER; registerCalculatorRecipe(new ItemStack(Blocks.netherrack, 64), Blocks.obsidian, "stone", type); registerCalculatorRecipe(new ItemStack(Blocks.netherrack, 32), Blocks.obsidian, "cobblestone", type); registerCalculatorRecipe(new ItemStack(Blocks.netherrack, 32), Blocks.soul_sand, "stone", type); registerCalculatorRecipe(new ItemStack(Blocks.netherrack, 16), Blocks.soul_sand, "cobblestone", type); registerCalculatorRecipe(new ItemStack(Blocks.soul_sand, 64), Blocks.netherrack, Items.ghast_tear, type); registerCalculatorRecipe(new ItemStack(Blocks.soul_sand, 32), "sand", Items.ghast_tear, type); registerCalculatorRecipe(new ItemStack(Blocks.obsidian, 16), Items.magma_cream, "stone", type); registerCalculatorRecipe(new ItemStack(Blocks.netherrack, 32), Items.blaze_rod, "stone", type); registerCalculatorRecipe(new ItemStack(Items.nether_wart, 1), Items.reeds, Blocks.obsidian, type); registerCalculatorRecipe(Items.ghast_tear, Items.blaze_rod, Items.ender_eye, type); registerCalculatorRecipe(new ItemStack(Items.blaze_powder, 16), Items.blaze_rod, Items.ghast_tear, type); registerCalculatorRecipe(new ItemStack(Items.netherbrick, 4), Items.brick, Items.brick, type); registerCalculatorRecipe(new ItemStack(Items.brick, 1), Items.netherbrick, Items.netherbrick, type); registerCalculatorRecipe(new ItemStack(Blocks.log, 8, 0), Blocks.obsidian, Blocks.obsidian, type); registerCalculatorRecipe(new ItemStack(Items.blaze_rod, 8), Items.ghast_tear, Items.ghast_tear, type); registerCalculatorRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), Items.blaze_powder, type); registerCalculatorRecipe(new ItemStack(Items.fire_charge, 4), Items.snowball, Items.blaze_powder, type);
		 * 
		 * // buckets registerCalculatorRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Items.dye, 1, 14), RecipeType.DYE); registerCalculatorRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Blocks.stained_hardened_clay, 1, 1), RecipeType.CLAY_BLOCK); registerCalculatorRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Blocks.wool, 1, 1), RecipeType.WOOL); registerCalculatorRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Blocks.stained_glass, 1, 1), RecipeType.GLASS); registerCalculatorRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Blocks.red_flower, 1, 5), RecipeType.FLOWER);
		 * 
		 * // irons type = RecipeType.IRON; registerCalculatorRecipe(new ItemStack(Blocks.anvil, 1), Blocks.iron_block, Blocks.iron_block, type); registerCalculatorRecipe(new ItemStack(Items.minecart, 1), "ingotIron", "ingotIron", type); registerCalculatorRecipe(new ItemStack(Blocks.rail, 4), Blocks.ladder, "stone", type); registerCalculatorRecipe(new ItemStack(Blocks.rail, 4), Blocks.ladder, "cobblestone", type); registerCalculatorRecipe(new ItemStack(Items.iron_ingot, 8), Items.ender_pearl, Items.ender_pearl, type); registerCalculatorRecipe(new ItemStack(Calculator.itemCalculator, 1), "ingotIron", "dustRedstone", type); registerCalculatorRecipe(new ItemStack(Items.bone, 16), Items.skull, "ingotIron", type); registerCalculatorRecipe(new ItemStack(Items.rotten_flesh, 16), new ItemStack(Items.skull, 1, 2), "ingotIron", type); registerCalculatorRecipe(new ItemStack(Items.gunpowder, 16), new ItemStack(Items.skull, 1, 4), "ingotIron", type);
		 * 
		 * // emerald type = RecipeType.EMERALD; registerCalculatorRecipe(new ItemStack(Items.gold_ingot, 8), "gemEmerald", "gemEmerald", type); registerCalculatorRecipe(new ItemStack(Calculator.weakeneddiamond, 6), "gemEmerald", new ItemStack(Items.dye, 1, 4), type); registerCalculatorRecipe(new ItemStack(Blocks.obsidian, 32), "gemEmerald", "stone", type);
		 * 
		 * // misc registerCalculatorRecipe(new ItemStack(Calculator.enrichedgold_ingot, 2), Calculator.reinforcediron_ingot, new ItemStack(Items.dye, 1, 11), RecipeType.DYE); registerCalculatorRecipe(new ItemStack(Calculator.calculator_assembly, 8), "ingotIron", Blocks.stone_button, RecipeType.STONE); registerCalculatorRecipe(new ItemStack(Calculator.calculator_screen, 8), Calculator.redstone_ingot, "cobblestone", RecipeType.COBBLESTONE); registerCalculatorRecipe(Blocks.vine, new ItemStack(Blocks.tallgrass, 1, 1), new ItemStack(Blocks.tallgrass, 1, 1), RecipeType.FOLIAGE); registerCalculatorRecipe(new ItemStack(Blocks.piston, 1), new ItemStack(Calculator.reinforcediron_ingot, 1), "logWood", RecipeType.WOOD); registerCalculatorRecipe(new ItemStack(Blocks.noteblock, 8), new ItemStack(Calculator.redstone_ingot, 1), "logWood", RecipeType.WOOD); registerCalculatorRecipe(new ItemStack(Blocks.noteblock, 2), new ItemStack(Calculator.redstone_ingot, 1), "plankWood", RecipeType.PLANKS); registerCalculatorRecipe(Items.clay_ball, Calculator.soil, Calculator.small_stone, RecipeType.CLAY_BLOCK);
		 * 
		 * // slime ball type = RecipeType.SLIMEBALL; registerCalculatorRecipe(new ItemStack(Items.leather, 2), Items.rotten_flesh, "slimeball", type); registerCalculatorRecipe(Items.slime_ball, Items.clay_ball, Items.rotten_flesh, type); registerCalculatorRecipe(Items.slime_ball, Calculator.pear, Items.clay_ball, type); registerCalculatorRecipe(new ItemStack(Items.slime_ball, 2), Calculator.rotten_pear, Calculator.rotten_pear, type); registerCalculatorRecipe(new ItemStack(Calculator.rotten_pear, 2), "slimeball", "slimeball", type); registerCalculatorRecipe(Blocks.sticky_piston, Blocks.piston, Calculator.rotten_pear, type);
		 * 
		 * // crops type = RecipeType.CROP; registerCalculatorRecipe(Items.melon_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, type); registerCalculatorRecipe(new ItemStack(Items.pumpkin_seeds, 2), Items.melon_seeds, Items.melon_seeds, type); registerCalculatorRecipe(new ItemStack(Items.wheat, 8), new ItemStack(Blocks.tallgrass, 1, 1), "treeSapling", type); registerCalculatorRecipe(new ItemStack(Items.wheat_seeds, 1), new ItemStack(Blocks.vine), "treeSapling", type); registerCalculatorRecipe(Items.pumpkin_seeds, "cropCarrot", "cropPotato", type);
		 * 
		 * // end type = RecipeType.END; registerCalculatorRecipe(new ItemStack(Blocks.ender_chest, 1), Blocks.end_stone, Items.ender_eye, type); registerCalculatorRecipe(new ItemStack(Blocks.ender_chest, 16), Blocks.chest, Items.nether_star, type); registerCalculatorRecipe(new ItemStack(Blocks.end_stone, 16), Items.ender_eye, "stone", type); registerCalculatorRecipe(Blocks.end_stone, Blocks.obsidian, Blocks.soul_sand, type); registerCalculatorRecipe(new ItemStack(Blocks.end_portal_frame, 3), Blocks.enchanting_table, Items.ender_eye, type);
		 * 
		 * // registerCalculatorRecipe(Blocks.fence, "stickWood", "stickWood", true);
		 * 
		 * type = RecipeType.WOOD;
		 * 
		 * for (int i = 0; i < 6; i++) { if (i != 3 && i != 4 && i != 5) { registerCalculatorRecipe(new ItemStack(Blocks.log, 2, i), new ItemStack(Blocks.log, 1, i + 1), new ItemStack(Blocks.log, 1, i + 1), type); } if (i == 3) { registerCalculatorRecipe(new ItemStack(Blocks.log, 2, i), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log2, 1, 0), type); } else if (i == 4) { registerCalculatorRecipe(new ItemStack(Blocks.log2, 2, 0), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log2, 1, 1), type); } else if (i == 5) { registerCalculatorRecipe(new ItemStack(Blocks.log2, 2, 1), new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.log, 1, 0), type); } } registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 0), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 1), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 1), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 1), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log, 1, 2), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 0), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 0), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 2), new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.log, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 2), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 2), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log2, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 3), new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.log, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 3), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 3), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log, 1, 0), type); registerCalculatorRecipe(new ItemStack(Blocks.log2, 2, 0), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log2, 1, 0), type); registerCalculatorRecipe(new ItemStack(Blocks.log2, 2, 0), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 0), type); registerCalculatorRecipe(new ItemStack(Blocks.log2, 2, 1), new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.log, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.log2, 2, 1), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log, 1, 0), type);
		 * 
		 * // special log recipes
		 * 
		 * for (int i = 0; i < 4; i++) { registerCalculatorRecipe(new ItemStack(Blocks.log, 1, i), new ItemStack(Blocks.sapling, 1, i), Blocks.dirt, type); registerCalculatorRecipe(new ItemStack(Blocks.log, 1, i), new ItemStack(Blocks.sapling, 1, i), new ItemStack(Blocks.grass, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 4, i), new ItemStack(Blocks.sapling, 1, i), new ItemStack(Blocks.planks, 1, i), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 8, i), new ItemStack(Blocks.sapling, 1, i), new ItemStack(Blocks.log, 1, i), type); registerCalculatorRecipe(new ItemStack(Blocks.log, 4, i), new ItemStack(Blocks.sapling, 1, i), new ItemStack(Blocks.leaves, 1, i), type); }
		 * 
		 * for (int i = 0; i < 2; i++) { registerCalculatorRecipe(new ItemStack(Blocks.log2, 1, i), new ItemStack(Blocks.sapling, 1, i + 4), Blocks.dirt, type); registerCalculatorRecipe(new ItemStack(Blocks.log2, 1, i), new ItemStack(Blocks.sapling, 1, i + 4), new ItemStack(Blocks.grass, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.log2, 4, i), new ItemStack(Blocks.sapling, 1, i + 4), new ItemStack(Blocks.leaves2, 1, i), type); registerCalculatorRecipe(new ItemStack(Blocks.log2, 4, i), new ItemStack(Blocks.sapling, 1, i + 4), new ItemStack(Blocks.planks, 1, i + 4), type); registerCalculatorRecipe(new ItemStack(Blocks.log2, 4, i), new ItemStack(Blocks.sapling, 1, i + 4), new ItemStack(Blocks.planks, 1, i + 4), type); registerCalculatorRecipe(new ItemStack(Blocks.log2, 8, i), new ItemStack(Blocks.sapling, 1, i + 4), new ItemStack(Blocks.log2, 1, i), type);
		 * 
		 * } type = RecipeType.PLANKS;
		 * 
		 * for (int i = 0; i < 6; i++) { if (i != 5) { registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, i), new ItemStack(Blocks.planks, 1, i + 1), new ItemStack(Blocks.planks, 1, i + 1), type); } else { registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, i), new ItemStack(Blocks.planks, 1, 0), new ItemStack(Blocks.planks, 1, 0), type); } } registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 0), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 0), new ItemStack(Blocks.planks, 1, 5), new ItemStack(Blocks.planks, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 1), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 1), new ItemStack(Blocks.planks, 1, 5), new ItemStack(Blocks.planks, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 3), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 0), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 1), new ItemStack(Blocks.planks, 1, 5), new ItemStack(Blocks.planks, 1, 2), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 2), new ItemStack(Blocks.planks, 1, 0), new ItemStack(Blocks.planks, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 2), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 2), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 5), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 3), new ItemStack(Blocks.planks, 1, 0), new ItemStack(Blocks.planks, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 3), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 4), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 4), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 4), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 0), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 5), new ItemStack(Blocks.planks, 1, 1), new ItemStack(Blocks.planks, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 5), new ItemStack(Blocks.planks, 1, 5), new ItemStack(Blocks.planks, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 5), new ItemStack(Blocks.planks, 1), type); /* for (int i = 0; i < 6; i++) { if (i != 5) { registerCalculatorRecipe(new ItemStack(Blocks.wooden_slab, 2, i), new ItemStack(Blocks.wooden_slab, 1, i + 1), new ItemStack(Blocks.wooden_slab, 1, i + 1), true); } else { registerCalculatorRecipe(new ItemStack(Blocks.wooden_slab, 2, i), new ItemStack(Blocks.wooden_slab, 1, 0), new ItemStack(Blocks.wooden_slab, 1, 0), true); } }
		 * 
		 * for (int i = 0; i < 7; i++) { if (i != 6) { registerCalculatorRecipe(new ItemStack(Blocks.stone_slab, 2, i), new ItemStack(Blocks.stone_slab, 1, i + 1), new ItemStack(Blocks.stone_slab, 1, i + 1), true); } else { registerCalculatorRecipe(new ItemStack(Blocks.stone_slab, 2, i), new ItemStack(Blocks.stone_slab, 1, 0), new ItemStack(Blocks.stone_slab, 1, 0), true); } } */
		// saplings
		/*type = RecipeType.SAPLING; for (int i = 0; i < 6; i++) { if (i != 5) { registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, i), new ItemStack(Blocks.sapling, 1, i + 1), new ItemStack(Blocks.sapling, 1, i + 1), type); } else { registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, i), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.sapling, 1, 0), type); } } registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 0), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.sapling, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 0), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.sapling, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 1), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.sapling, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.sapling, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 3), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.sapling, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.sapling, 1, 2), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 2), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.sapling, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 2), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.sapling, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 2), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.sapling, 1, 5), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 3), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.sapling, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 3), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.sapling, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 4), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.sapling, 1, 4), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 4), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.sapling, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 5), new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.sapling, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 5), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.sapling, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.leaves, 1, 0), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.leaves, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.leaves, 1, 2), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.leaves, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.leaves2, 1, 0), type); registerCalculatorRecipe(new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.leaves2, 1, 1), type);
		 * 
		 * // leaves type = RecipeType.LEAVES; for (int i = 0; i < 6; i++) { if (i != 3 && i != 4 && i != 5) { registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, i), new ItemStack(Blocks.leaves, 1, i + 1), new ItemStack(Blocks.leaves, 1, i + 1), type); } if (i == 3) { registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, i), new ItemStack(Blocks.leaves2, 1), new ItemStack(Blocks.leaves2, 1), type); } else if (i == 4) { registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves2, 1, 1), type); } else if (i == 5) { registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2, 1), new ItemStack(Blocks.leaves, 1), new ItemStack(Blocks.leaves, 1), type); } }
		 * 
		 * registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 0), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 0), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 1), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 1), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 1), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves, 1, 2), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 2), new ItemStack(Blocks.leaves, 1), new ItemStack(Blocks.leaves, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 2), new ItemStack(Blocks.leaves2, 1), new ItemStack(Blocks.leaves, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 2), new ItemStack(Blocks.leaves2, 1), new ItemStack(Blocks.leaves2, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 3), new ItemStack(Blocks.leaves, 1), new ItemStack(Blocks.leaves, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 3), new ItemStack(Blocks.leaves2), new ItemStack(Blocks.leaves, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 3), new ItemStack(Blocks.leaves2), new ItemStack(Blocks.leaves, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves2, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2), new ItemStack(Blocks.leaves, 1, 2), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2, 1), new ItemStack(Blocks.leaves, 1, 1), new ItemStack(Blocks.leaves, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2, 1), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2, 1), new ItemStack(Blocks.leaves, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 4, 0), Blocks.vine, new ItemStack(Blocks.leaves, 1, 0), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 4, 1), Blocks.vine, new ItemStack(Blocks.leaves, 1, 1), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 4, 2), Blocks.vine, new ItemStack(Blocks.leaves, 1, 2), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves, 4, 3), Blocks.vine, new ItemStack(Blocks.leaves, 1, 3), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 4, 0), Blocks.vine, new ItemStack(Blocks.leaves2, 1, 0), type); registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 4, 1), Blocks.vine, new ItemStack(Blocks.leaves2, 1, 1), type); registerCalculatorRecipe(Items.bed, "treeLeaves", new ItemStack(Blocks.planks, 1), type);
		 * 
		 * //
		 * 
		 * for (int i = 0; i < 9; i++) { switch (i) { case 8: registerCalculatorRecipe(new ItemStack(Blocks.red_flower, 2, i), new ItemStack(Blocks.red_flower, 1, 0), new ItemStack(Blocks.red_flower, 1, 0), RecipeType.FLOWER); break; default: registerCalculatorRecipe(new ItemStack(Blocks.red_flower, 2, i), new ItemStack(Blocks.red_flower, 1, i + 1), new ItemStack(Blocks.red_flower, 1, i + 1), RecipeType.FLOWER); break; } } for (int i = 0; i < 16; i++) { switch (i) { case 15: registerCalculatorRecipe(new ItemStack(Items.dye, 2, i), new ItemStack(Items.dye, 1, 0), new ItemStack(Items.dye, 1, 0), RecipeType.DYE); registerCalculatorRecipe(new ItemStack(Blocks.carpet, 2, i), new ItemStack(Blocks.carpet, 1, 0), new ItemStack(Blocks.carpet, 1, 0), RecipeType.CARPET); registerCalculatorRecipe(new ItemStack(Blocks.stained_hardened_clay, 2, i), new ItemStack(Blocks.stained_hardened_clay, 1, 0), new ItemStack(Blocks.stained_hardened_clay, 1, 0), RecipeType.CLAY_BLOCK); registerCalculatorRecipe(new ItemStack(Blocks.stained_glass_pane, 2, i), new ItemStack(Blocks.stained_glass_pane, 1, 0), new ItemStack(Blocks.stained_glass_pane, 1, 0), RecipeType.GLASS_PANE); registerCalculatorRecipe(new ItemStack(Blocks.stained_glass, 2, i), new ItemStack(Blocks.stained_glass, 1, 0), new ItemStack(Blocks.stained_glass, 1, 0), RecipeType.GLASS); registerCalculatorRecipe(new ItemStack(Blocks.wool, 2, i), new ItemStack(Blocks.wool, 1, 0), new ItemStack(Blocks.wool, 1, 0), RecipeType.WOOL); break; default: registerCalculatorRecipe(new ItemStack(Items.dye, 2, i), new ItemStack(Items.dye, 1, i + 1), new ItemStack(Items.dye, 1, i + 1), RecipeType.DYE); registerCalculatorRecipe(new ItemStack(Blocks.carpet, 2, i), new ItemStack(Blocks.carpet, 1, i + 1), new ItemStack(Blocks.carpet, 1, i + 1), RecipeType.CARPET); registerCalculatorRecipe(new ItemStack(Blocks.stained_hardened_clay, 2, i), new ItemStack(Blocks.stained_hardened_clay, 1, i + 1), new ItemStack(Blocks.stained_hardened_clay, 1, i + 1), RecipeType.CLAY_BLOCK); registerCalculatorRecipe(new ItemStack(Blocks.stained_glass_pane, 2, i), new ItemStack(Blocks.stained_glass_pane, 1, i + 1), new ItemStack(Blocks.stained_glass_pane, 1, i + 1), RecipeType.GLASS_PANE); registerCalculatorRecipe(new ItemStack(Blocks.stained_glass, 2, i), new ItemStack(Blocks.stained_glass, 1, i + 1), new ItemStack(Blocks.stained_glass, 1, i + 1), RecipeType.GLASS); registerCalculatorRecipe(new ItemStack(Blocks.wool, 2, i), new ItemStack(Blocks.wool, 1, i + 1), new ItemStack(Blocks.wool, 1, i + 1), RecipeType.WOOL);
		 * 
		 * break; } } // foliage type = RecipeType.FOLIAGE; registerCalculatorRecipe(new ItemStack(Items.reeds, 1), new ItemStack(Blocks.grass, 1), "treeSapling", type); for (int i = 0; i < 9; i++) { registerCalculatorRecipe(new ItemStack(Blocks.vine, 16), new ItemStack(Blocks.red_flower, 1, i), "treeSapling", type); } for (int i = 0; i < 6; i++) { switch (i) { default: registerCalculatorRecipe(new ItemStack(Blocks.double_plant, 2, i), new ItemStack(Blocks.double_plant, 1, i + 1), new ItemStack(Blocks.double_plant, 1, i + 1), type); break; case 1: registerCalculatorRecipe(new ItemStack(Blocks.double_plant, 2, i), new ItemStack(Blocks.tallgrass, 1, 1), new ItemStack(Blocks.tallgrass, 1, 1), type); break; case 2: registerCalculatorRecipe(new ItemStack(Blocks.tallgrass, 2, 1), new ItemStack(Blocks.tallgrass, 1, 2), new ItemStack(Blocks.tallgrass, 1, 2), type); break; case 3: registerCalculatorRecipe(new ItemStack(Blocks.tallgrass, 2, 2), new ItemStack(Blocks.double_plant, 1, i + 1), new ItemStack(Blocks.double_plant, 1, i + 1), type); break; case 5: registerCalculatorRecipe(new ItemStack(Blocks.double_plant, 2, i), new ItemStack(Blocks.double_plant, 1, 0), new ItemStack(Blocks.double_plant, 1, 0), type); break; } } */
	}

	private static void addScientificRecipes() {
		registerScientificRecipe(Calculator.enriched_coal, Calculator.enrichedgold_ingot, Calculator.purified_coal);
		registerScientificRecipe("ingotIron", "dustRedstone", Calculator.redstone_ingot);
		registerScientificRecipe("gemDiamond", Calculator.reinforcediron_ingot, new ItemStack(Calculator.weakeneddiamond, 4));
		registerScientificRecipe(Calculator.baby_grenade, Calculator.baby_grenade, Calculator.grenade);
		registerScientificRecipe(Calculator.enriched_coal, Items.LAVA_BUCKET, Calculator.firecoal);
		registerScientificRecipe(Calculator.large_amethyst, "treeSapling", Calculator.amethystSapling);
		registerScientificRecipe(Calculator.amethystLog, Calculator.amethystLeaves, Calculator.amethystSapling);
		registerScientificRecipe(Calculator.amethystLog, Calculator.small_amethyst, Calculator.amethystSapling);
		registerScientificRecipe(Calculator.amethystLog, Calculator.amethystLog, Calculator.amethystSapling);
		registerScientificRecipe(Calculator.itemCalculator, Calculator.redstone_ingot, Calculator.itemTerrainModule);
		registerScientificRecipe(Calculator.itemEnergyModule, Calculator.small_amethyst, Calculator.starchextractor);
		registerScientificRecipe(Calculator.powerCube, Calculator.purified_coal, Calculator.itemEnergyModule);
		// registerScientificRecipe(new ItemStack(Calculator.material_block, 1, CalculatorItems.ToolTypes.ReinforcedIron.getMeta()), Items.ender_eye, Calculator.fluxPlug);
		//registerScientificRecipe(Calculator.redstone_ingot, Items.ender_pearl, Calculator.fluxPoint);

	}

	private static void addAtomicRecipes() {
		registerAtomicRecipe("ingotIron", "dustRedstone", "ingotIron", Calculator.itemScientificCalculator);
		registerAtomicRecipe("gemDiamond", Calculator.atomic_binder, "gemDiamond", Calculator.flawlessdiamond);
		registerAtomicRecipe("gemDiamond", Items.BLAZE_ROD, "gemDiamond", Calculator.firediamond);
		registerAtomicRecipe(Items.BLAZE_ROD, Calculator.flawlessdiamond, Items.BLAZE_ROD, Calculator.firediamond);
		registerAtomicRecipe(Blocks.END_STONE, Calculator.electricDiamond, Blocks.OBSIDIAN, Calculator.endDiamond);
		registerAtomicRecipe(Calculator.amethystSapling, Blocks.END_STONE, Calculator.tanzaniteSapling, Calculator.pearSapling);
		registerAtomicRecipe(Calculator.itemScientificCalculator, Calculator.atomic_binder, Calculator.redstone_ingot, Calculator.itemAdvancedTerrainModule);
		registerAtomicRecipe(Calculator.tanzaniteLog, Calculator.atomic_binder, Calculator.tanzaniteLeaves, Calculator.tanzaniteSapling);
		registerAtomicRecipe(Calculator.large_tanzanite, Calculator.atomic_binder, "treeSapling", Calculator.tanzaniteSapling);
		registerAtomicRecipe(Calculator.healthProcessor, Calculator.itemEnergyModule, Calculator.hungerProcessor, Calculator.itemNutritionModule);
		registerAtomicRecipe(new ItemStack(Calculator.circuitBoard, 1, 4), Calculator.atomic_binder, "dustEnrichedGold", new ItemStack(Calculator.speedUpgrade, 4));
		registerAtomicRecipe(new ItemStack(Calculator.circuitBoard, 1, 5), Calculator.atomic_binder, "dustEnrichedGold", new ItemStack(Calculator.energyUpgrade, 4));
		registerAtomicRecipe(new ItemStack(Calculator.circuitBoard, 1, 6), Calculator.atomic_binder, "dustEnrichedGold", Calculator.voidUpgrade);
		registerAtomicRecipe(new ItemStack(Calculator.circuitBoard, 1, 7), Calculator.atomic_binder, "dustEnrichedGold", Calculator.transferUpgrade);
		registerAtomicRecipe(new ItemStack(Calculator.circuitBoard, 1, 9), Calculator.redstoneextractor, Calculator.large_tanzanite, Calculator.glowstoneextractor);
		registerAtomicRecipe(Calculator.redstone_ingot, Calculator.starchextractor, Calculator.redstone_ingot, Calculator.redstoneextractor);
		registerAtomicRecipe(Calculator.itemEnergyModule, Calculator.firediamond, Calculator.itemEnergyModule, Calculator.conductorMast);
		registerAtomicRecipe(new ItemStack(Calculator.material_block, 1, Variants.REINFORCED_IRON.getMeta()), Calculator.reinforcedChest, new ItemStack(Calculator.material_block, 1, Variants.REINFORCED_IRON.getMeta()), Calculator.storageChamber);
		registerAtomicRecipe(Calculator.reassemblyChamber, Calculator.flawlessdiamond, Calculator.restorationChamber, Calculator.processingChamber);
		registerAtomicRecipe(Calculator.reinforcediron_ingot, Blocks.CHEST, Calculator.reinforcediron_ingot, Calculator.itemStorageModule);
		registerAtomicRecipe(Calculator.reinforcediron_ingot, Calculator.electricDiamond, Calculator.reinforcediron_ingot, Calculator.transmitter);
		registerAtomicRecipe(Calculator.reinforcediron_ingot, Calculator.firediamond, Calculator.reinforcediron_ingot, new ItemStack(Calculator.weatherStation, 4));
	}

	private static void addFlawlessRecipes() {
		registerFlawlessRecipe(Calculator.pearSapling, Calculator.endDiamond, Calculator.endDiamond, Blocks.END_STONE, Calculator.diamondSapling);
		registerFlawlessRecipe("ingotGold", "ingotGold", "ingotGold", "ingotGold", Items.DIAMOND);
		registerFlawlessRecipe("gemDiamond", "gemDiamond", "gemDiamond", "gemDiamond", Items.EMERALD);
		registerFlawlessRecipe("ingotIron", "ingotIron", "ingotIron", "ingotIron", Items.ENDER_PEARL);
		registerFlawlessRecipe("logWood", "logWood", "logWood", "logWood", Blocks.OBSIDIAN);
		registerFlawlessRecipe(Blocks.OBSIDIAN, Blocks.OBSIDIAN, Blocks.OBSIDIAN, Blocks.OBSIDIAN, Calculator.purifiedObsidian);
		registerFlawlessRecipe(Calculator.broccoli, Calculator.broccoli, Calculator.broccoli, Calculator.broccoli, Calculator.fiddledewFruit);

		registerFlawlessRecipe(Calculator.itemEnergyModule, Calculator.itemCalculator, Calculator.itemCalculator, Calculator.itemEnergyModule, Calculator.itemLocatorModule);
		registerFlawlessRecipe(Calculator.flawlessdiamond, "blockGlass", "blockGlass", Calculator.flawlessdiamond, new ItemStack(Calculator.flawlessGlass, 4));
		registerFlawlessRecipe(Calculator.circuitBoard, Calculator.enriched_coal, Calculator.enriched_coal, Calculator.circuitBoard, new ItemStack(Calculator.controlled_Fuel, 4));
		registerFlawlessRecipe(Calculator.gas_lantern_off, new ItemStack(Calculator.circuitBoard, 1, 8), new ItemStack(Calculator.circuitBoard, 1, 8), Calculator.gas_lantern_off, Calculator.CO2Generator);

		registerFlawlessRecipe(Items.BLAZE_POWDER, Items.BLAZE_POWDER, Items.BLAZE_POWDER, Items.BLAZE_POWDER, Items.BLAZE_ROD);
		registerFlawlessRecipe(Items.BLAZE_ROD, Items.BLAZE_ROD, Items.BLAZE_ROD, Items.BLAZE_ROD, Items.GHAST_TEAR);

	}

	/** old versions put the output first this fixes that
	 * 
	 * @param objects */
	private static void rearrangeCalculatorRecipe(Object... objects) {
		if (objects.length == 4 && objects[3] != null && objects[3] instanceof RecipeType) {
			String recipeID = ((String) objects[3]);
			Object[] recipe = new Object[3];
			recipe[0] = objects[1];
			recipe[1] = objects[2];
			recipe[2] = objects[0];
			blocked.putIfAbsent(recipeID, new ArrayList());
			blocked.get(recipeID).add(recipe);
		} else {
			Object[] recipe = new Object[3];
			recipe[0] = objects[1];
			recipe[1] = objects[2];
			recipe[2] = objects[0];
			unblocked.add(recipe);
		}
	}

	public static class CalculatorRecipes extends RecipeHelper {

		private static final CalculatorRecipes recipes = new CalculatorRecipes();
		public int currentRecipe = 0;

		public static final CalculatorRecipes instance() {
			return recipes;
		}

		public CalculatorRecipes() {
			super(2, 1, true);
		}

		@Override
		public void addRecipes() {
			for (int i = 0; i < RecipeRegistry.unblocked.size(); i++) {
				this.addRecipe(RecipeRegistry.unblocked.get(i));
			}
		}

		@Override
		public String getRecipeID() {
			return "Calculator";
		}
	}

	public static class ScientificRecipes extends RecipeHelper {

		private static final ScientificRecipes recipes = new ScientificRecipes();

		public static final RecipeHelper instance() {
			return recipes;
		}

		public ScientificRecipes() {
			super(2, 1, true);
		}

		@Override
		public void addRecipes() {
			for (int i = 0; i < RecipeRegistry.scientific.size(); i++) {
				this.addRecipe(RecipeRegistry.scientific.get(i));
			}

		}

		@Override
		public String getRecipeID() {
			return "Scientific";
		}
	}

	public static class AtomicRecipes extends RecipeHelper {

		private static final AtomicRecipes recipes = new AtomicRecipes();

		public static final RecipeHelper instance() {
			return recipes;
		}

		public AtomicRecipes() {
			super(3, 1, true);
		}

		@Override
		public void addRecipes() {
			for (int i = 0; i < RecipeRegistry.atomic.size(); i++) {
				this.addRecipe(RecipeRegistry.atomic.get(i));
			}

		}

		@Override
		public String getRecipeID() {
			return "Atomic";
		}
	}

	public static class FlawlessRecipes extends RecipeHelper {

		private static final FlawlessRecipes recipes = new FlawlessRecipes();

		public static final RecipeHelper instance() {
			return recipes;
		}

		public FlawlessRecipes() {
			super(4, 1, true);
		}

		@Override
		public void addRecipes() {
			for (int i = 0; i < RecipeRegistry.flawless.size(); i++) {
				this.addRecipe(RecipeRegistry.flawless.get(i));
			}

		}

		@Override
		public String getRecipeID() {
			return "Flawless";
		}
	}

	public static class ConductorMastItemRecipes extends RecipeHelper {
		private static final ConductorMastItemRecipes INSTANCE = new ConductorMastItemRecipes();

		public static ConductorMastItemRecipes instance() {
			return INSTANCE;
		}

		public ConductorMastItemRecipes() {
			super(1, 1, true);
		}

		@Override
		public void addRecipes() {
			addRecipe(Calculator.firediamond, new ItemStack(Calculator.electricDiamond));
			addRecipe(Calculator.itemCalculator, new ItemStack(Calculator.itemScientificCalculator));
			addRecipe(new ItemStack(Calculator.material_block, 1, Variants.FIRE_DIAMOND.getMeta()), new ItemStack(Calculator.material_block, 1, Variants.ELECTRIC_DIAMOND.getMeta()));
		}

		public Map<ItemStack, ItemStack> getRecipeStacks() {
			Map<ItemStack, ItemStack> map = new HashMap<ItemStack, ItemStack>();
			for (Map.Entry<Object[], Object[]> entry : recipeList.entrySet()) {
				map.put((ItemStack) entry.getKey()[0], (ItemStack) entry.getValue()[0]);
			}
			return map;
		}

		@Override
		public String getRecipeID() {
			return "ConductorMastItem";
		}
	}

	public static class ConductorMastPowerRecipes extends RecipeHelper {
		private static final ConductorMastPowerRecipes INSTANCE = new ConductorMastPowerRecipes();

		public static ConductorMastPowerRecipes instance() {
			return INSTANCE;
		}

		ConductorMastPowerRecipes() {
			super(1, 1, true);
		}

		@Override
		public void addRecipes() {
			addRecipe(new ItemStack(Calculator.electricDiamond), 10000);
			addRecipe(new ItemStack(Calculator.itemScientificCalculator), 2000);
			addRecipe(new ItemStack(Calculator.material_block, 1, CalculatorItems.ToolTypes.ElectricDiamond.getMeta()), 90000);
		}

		/** custom method because the result is an int, not an itemstack */
		public int getPowercost(ItemStack input) {
			Object[] output = getOutputRaw(input);
			if (output == null)
				return 0;
			return (Integer) output[0];
		}

		@Override
		public String getRecipeID() {
			return "ConductorMastPower";
		}
	}

	public static int getBlockedSize() {
		return blocked.size();
	}

	public static int getUnblockedSize() {
		return unblocked.size();
	}

	public static int getScientificSize() {
		return scientific.size();
	}

	public static int getAtomicSize() {
		return atomic.size();
	}

	public static int getFlawlessSize() {
		return flawless.size();
	}

}
