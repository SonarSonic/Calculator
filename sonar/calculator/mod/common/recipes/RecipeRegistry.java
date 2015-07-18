package sonar.calculator.mod.common.recipes;

import gnu.trove.map.hash.THashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.Calculator;
import sonar.core.utils.helpers.RecipeHelper;

public class RecipeRegistry {

	private static final Map<Integer, Object[]> blocked = new THashMap<Integer, Object[]>();
	private static final List<Object[]> unblocked = new ArrayList();
	private static final List<Object[]> scientific = new ArrayList();
	private static final List<Object[]> atomic = new ArrayList();
	private static final List<Object[]> flawless = new ArrayList();

	public static void clearRecipes() {
		blocked.clear();
		unblocked.clear();
		scientific.clear();
		atomic.clear();
		flawless.clear();
	}

	public static void registerRecipes() {
		clearRecipes();
		addStandardRecipes();
		addScientificRecipes();
		addAtomicRecipes();
		addFlawlessRecipes();
	}

	private static void addStandardRecipes() {

		registerCalculatorRecipe(Calculator.baby_grenade, Calculator.grenadecasing, Blocks.tnt, false);
		registerCalculatorRecipe(Calculator.reinforcedstoneBlock, "cobblestone", "plankWood", false);

		registerCalculatorRecipe(Calculator.wrench, Calculator.reinforced_sword, Calculator.reinforced_pickaxe, false);
		registerCalculatorRecipe(Calculator.reinforceddirtBlock, "dirt", "plankWood", false);
		registerCalculatorRecipe(new ItemStack(Calculator.enrichedgold, 4), "ingotGold", "dustRedstone", false);
		registerCalculatorRecipe(Calculator.reinforcediron_ingot, "ingotIron", Calculator.reinforcedstoneBlock, false);
		registerCalculatorRecipe(Calculator.enriched_coal, Items.coal, "dustRedstone", false);
		registerCalculatorRecipe(Calculator.broccoliSeeds, Items.wheat_seeds, Items.pumpkin_seeds, false);
		registerCalculatorRecipe(Calculator.sickle, Calculator.reinforced_shovel, Calculator.reinforced_axe, false);
		registerCalculatorRecipe(Calculator.scarecrow, Blocks.pumpkin, Blocks.hay_block, false);
		registerCalculatorRecipe(Calculator.basic_lantern, Blocks.torch, Calculator.reinforcediron_ingot, false);
		registerCalculatorRecipe(Calculator.gas_lantern_off, Calculator.basic_lantern, Calculator.basic_lantern, false);
		registerCalculatorRecipe(Calculator.prunaeSeeds, Calculator.enriched_coal, Items.wheat_seeds, false);
		registerCalculatorRecipe(Calculator.enriched_coal, Calculator.coal_dust, Calculator.coal_dust, false);
		registerCalculatorRecipe(Calculator.researchChamber, Calculator.reinforced_iron_block, Calculator.powerCube, false);

		// calculator recipes
		registerCalculatorRecipe(new ItemStack(Calculator.reinforcedstoneBlock, 8), "cobblestone", Items.clay_ball, true);
		registerCalculatorRecipe(new ItemStack(Calculator.reinforceddirtBlock, 2, 2), "stone", new ItemStack(Blocks.dirt, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Calculator.reinforceddirtBlock, 1, 2), "cobblestone", new ItemStack(Blocks.dirt, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Calculator.reinforceddirtBlock, 4), new ItemStack(Blocks.grass, 1), Blocks.log, true);
		registerCalculatorRecipe(new ItemStack(Calculator.reinforceddirtBlock, 4), new ItemStack(Blocks.grass, 1), Blocks.log2, true);
		registerCalculatorRecipe(new ItemStack(Calculator.reinforceddirtBlock, 1), new ItemStack(Blocks.grass, 1), new ItemStack(Blocks.planks, 1), true);
		registerCalculatorRecipe(Calculator.powerCube, Blocks.furnace, "stone", true);
		registerCalculatorRecipe(Calculator.powerCube, Blocks.furnace, "cobblestone", true);
		registerCalculatorRecipe(Calculator.powerCube, Blocks.furnace, Calculator.reinforcedstoneBlock, true);
		registerCalculatorRecipe(Calculator.stoneSeperator, Blocks.furnace, Blocks.furnace, true);

		registerCalculatorRecipe(new ItemStack(Calculator.reinforceddirtBlock, 1), "dirt", "plankWood", true);
		registerCalculatorRecipe(new ItemStack(Calculator.reinforceddirtBlock, 4), "dirt", "logWood", true);
		registerCalculatorRecipe(new ItemStack(Calculator.reinforcedstoneBlock, 1), "cobblestone", "plankWood", true);
		registerCalculatorRecipe(new ItemStack(Calculator.reinforcedstoneBlock, 4), "cobblestone", "logWood", true);
		registerCalculatorRecipe(new ItemStack(Calculator.reinforcedstoneBlock, 6), "stone", "logWood", true);
		registerCalculatorRecipe(new ItemStack(Calculator.reinforcedstoneBlock, 2), "stone", "plankWood", true);

		// standard blocks
		registerCalculatorRecipe(new ItemStack(Blocks.dirt, 1), Calculator.soil, true);
		registerCalculatorRecipe(new ItemStack(Blocks.dirt, 1), Blocks.gravel, "sand", true);
		registerCalculatorRecipe(new ItemStack(Blocks.dirt, 1), "sand", "sand", true);
		registerCalculatorRecipe(new ItemStack(Blocks.dirt, 1, 2), new ItemStack(Blocks.grass, 1), "dirt", true);
		registerCalculatorRecipe(new ItemStack(Blocks.dirt, 1, 2), new ItemStack(Blocks.grass, 1), new ItemStack(Blocks.red_mushroom_block, 1, 5), true);
		registerCalculatorRecipe(new ItemStack(Blocks.dirt, 1, 2), new ItemStack(Blocks.grass, 1), new ItemStack(Blocks.brown_mushroom_block, 1, 5), true);
		registerCalculatorRecipe(new ItemStack(Blocks.mossy_cobblestone, 4), "stone", "treeSapling", true);
		registerCalculatorRecipe(new ItemStack(Blocks.mossy_cobblestone, 2), "stone", "treeLeaves", true);
		registerCalculatorRecipe(new ItemStack(Blocks.mossy_cobblestone, 2), "cobblestone", "treeSapling", true);
		registerCalculatorRecipe(new ItemStack(Blocks.mossy_cobblestone, 1), "cobblestone", "treeLeaves", true);
		registerCalculatorRecipe(new ItemStack(Blocks.mossy_cobblestone, 2), Blocks.vine, "cobblestone", true);
		registerCalculatorRecipe(new ItemStack(Blocks.mossy_cobblestone, 4), Blocks.vine, "stone", true);
		registerCalculatorRecipe(Blocks.stone, Calculator.small_stone, true);
		registerCalculatorRecipe(Blocks.stone, Blocks.gravel, Blocks.gravel, true);
		registerCalculatorRecipe(Blocks.stone, "cobblestone", "cobblestone", true);
		registerCalculatorRecipe(new ItemStack(Blocks.stonebrick, 1, 2), "cobblestone", "stone", true);
		registerCalculatorRecipe(Blocks.sand, Blocks.gravel, new ItemStack(Blocks.grass, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sand, 1), "cobblestone", Blocks.gravel, true);
		registerCalculatorRecipe(new ItemStack(Blocks.sand, 2), "stone", Blocks.gravel, true);
		registerCalculatorRecipe(Blocks.sand, Blocks.gravel, "dirt", true);
		registerCalculatorRecipe(new ItemStack(Blocks.sandstone, 1, 2), "stone", Blocks.sandstone, true);
		registerCalculatorRecipe(new ItemStack(Blocks.sandstone, 1, 1), "cobblestone", Blocks.sandstone, true);
		registerCalculatorRecipe(new ItemStack(Blocks.grass, 2), Items.wheat_seeds, "dirt", true);
		registerCalculatorRecipe(new ItemStack(Blocks.grass, 2), Items.pumpkin_seeds, "dirt", true);
		registerCalculatorRecipe(new ItemStack(Blocks.grass, 4), Calculator.broccoliSeeds, "dirt", true);
		registerCalculatorRecipe(new ItemStack(Blocks.gravel, 3), "stone", new ItemStack(Blocks.grass, 1), true);
		registerCalculatorRecipe(Blocks.gravel, Blocks.sand, new ItemStack(Blocks.grass, 1), true);
		registerCalculatorRecipe(Blocks.gravel, "cobblestone", "dirt", true);
		registerCalculatorRecipe(Blocks.gravel, "stone", "dirt", true);
		registerCalculatorRecipe(Blocks.gravel, Blocks.sand, "dirt", true);
		registerCalculatorRecipe(new ItemStack(Blocks.gravel, 2), "stone", Blocks.sand, true);
		registerCalculatorRecipe(new ItemStack(Blocks.gravel, 1), "cobblestone", Blocks.sand, true);
		registerCalculatorRecipe(Blocks.gravel, new ItemStack(Blocks.grass, 1), "cobblestone", true);
		registerCalculatorRecipe(new ItemStack(Blocks.gravel, 2), "dirt", "dirt", true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 4, 0), Blocks.vine, new ItemStack(Blocks.leaves, 1, 0), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 4, 1), Blocks.vine, new ItemStack(Blocks.leaves, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 4, 2), Blocks.vine, new ItemStack(Blocks.leaves, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 4, 3), Blocks.vine, new ItemStack(Blocks.leaves, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 4, 0), Blocks.vine, new ItemStack(Blocks.leaves2, 1, 0), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 4, 1), Blocks.vine, new ItemStack(Blocks.leaves2, 1, 1), true);
		registerCalculatorRecipe(Items.clay_ball, Calculator.soil, Calculator.small_stone, true);

		// special blocks
		registerCalculatorRecipe(new ItemStack(Blocks.stained_glass, 1, 8), "stone", Blocks.glass, true);
		registerCalculatorRecipe(new ItemStack(Blocks.stained_glass, 1, 7), "cobblestone", Blocks.glass, true);
		registerCalculatorRecipe(new ItemStack(Blocks.stained_glass_pane, 1, 8), "stone", Blocks.glass_pane, true);
		registerCalculatorRecipe(new ItemStack(Blocks.stained_glass_pane, 1, 7), "cobblestone", Blocks.glass_pane, true);
		registerCalculatorRecipe(Blocks.cactus, "sand", "treeSapling", true);
		registerCalculatorRecipe(Blocks.cactus, "sand", Items.wheat_seeds, true);
		registerCalculatorRecipe(Blocks.cactus, "sand", "treeLeaves", true);
		registerCalculatorRecipe(new ItemStack(Blocks.netherrack, 64), Blocks.obsidian, "stone", true);
		registerCalculatorRecipe(new ItemStack(Blocks.netherrack, 32), Blocks.obsidian, "cobblestone", true);
		registerCalculatorRecipe(new ItemStack(Blocks.netherrack, 32), Blocks.soul_sand, "stone", true);
		registerCalculatorRecipe(new ItemStack(Blocks.netherrack, 16), Blocks.soul_sand, "cobblestone", true);
		registerCalculatorRecipe(new ItemStack(Blocks.soul_sand, 64), Blocks.netherrack, Items.ghast_tear, true);
		registerCalculatorRecipe(new ItemStack(Blocks.soul_sand, 32), "sand", Items.ghast_tear, true);
		registerCalculatorRecipe(new ItemStack(Blocks.obsidian, 32), "gemEmerald", "stone", true);
		registerCalculatorRecipe(Blocks.mycelium, new ItemStack(Blocks.dirt, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Blocks.mycelium, 2), new ItemStack(Blocks.grass, 1), new ItemStack(Blocks.dirt, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Blocks.mycelium, 2), new ItemStack(Blocks.grass, 1), new ItemStack(Blocks.grass, 1), true);

		// standard items
		registerCalculatorRecipe(Items.sugar, Items.water_bucket, "treeSapling", true);
		registerCalculatorRecipe(Items.egg, Items.feather, true);
		registerCalculatorRecipe(new ItemStack(Items.bone, 16), Items.skull, "ingotIron", true);
		registerCalculatorRecipe(new ItemStack(Items.rotten_flesh, 16), new ItemStack(Items.skull, 1, 2), "ingotIron", true);
		registerCalculatorRecipe(new ItemStack(Items.gunpowder, 16), new ItemStack(Items.skull, 1, 4), "ingotIron", true);
		registerCalculatorRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), Items.blaze_powder, true);
		registerCalculatorRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Items.dye, 1, 14), true);
		registerCalculatorRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Blocks.stained_hardened_clay, 1, 1), true);
		registerCalculatorRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Blocks.wool, 1, 1), true);
		registerCalculatorRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Blocks.stained_glass, 1, 1), true);
		registerCalculatorRecipe(Items.lava_bucket, Items.water_bucket.setContainerItem(null), new ItemStack(Blocks.red_flower, 1, 5), true);

		// specials
		registerCalculatorRecipe(Blocks.sticky_piston, Blocks.piston, Calculator.rotten_pear, true);
		registerCalculatorRecipe(new ItemStack(Blocks.rail, 4), Blocks.ladder, "stone", true);

		registerCalculatorRecipe(new ItemStack(Blocks.rail, 4), Blocks.ladder, "cobblestone", true);
		registerCalculatorRecipe(new ItemStack(Blocks.furnace, 1), Blocks.crafting_table, "stone", true);
		registerCalculatorRecipe(new ItemStack(Blocks.furnace, 1), Blocks.crafting_table, "cobblestone", true);
		registerCalculatorRecipe(new ItemStack(Blocks.crafting_table, 2), Blocks.furnace, new ItemStack(Blocks.planks, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.anvil, 1), Blocks.iron_block, Blocks.iron_block, true);
		registerCalculatorRecipe(new ItemStack(Items.minecart, 1), "ingotIron", "ingotIron", true);
		registerCalculatorRecipe(new ItemStack(Blocks.end_portal_frame, 3), Blocks.enchanting_table, Items.ender_eye, true);
		registerCalculatorRecipe(Items.brewing_stand, Items.blaze_powder, Blocks.furnace, true);

		registerCalculatorRecipe(Blocks.fence, "stickWood", "stickWood", true);

		registerCalculatorRecipe(Items.bed, "treeLeaves", new ItemStack(Blocks.planks, 1), true);

		// log recipes
		for (int i = 0; i < 6; i++) {
			if (i != 3 && i != 4 && i != 5) {
				registerCalculatorRecipe(new ItemStack(Blocks.log, 2, i), new ItemStack(Blocks.log, 1, i + 1), new ItemStack(Blocks.log, 1, i + 1), true);
			}
			if (i == 3) {
				registerCalculatorRecipe(new ItemStack(Blocks.log, 2, i), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log2, 1, 0), true);
			} else if (i == 4) {
				registerCalculatorRecipe(new ItemStack(Blocks.log2, 2, 0), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log2, 1, 1), true);
			} else if (i == 5) {
				registerCalculatorRecipe(new ItemStack(Blocks.log2, 2, 1), new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.log, 1, 0), true);
			}
		}
		registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 0), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 1), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 1), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 1), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 0), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 0), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 2), new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.log, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 2), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 2), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log2, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 3), new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.log, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 3), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 2, 3), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log, 1, 0), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 2, 0), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log2, 1, 0), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 2, 0), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 0), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 2, 1), new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.log, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 2, 1), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.log, 1, 0), true);

		// plank recipes
		for (int i = 0; i < 6; i++) {
			if (i != 6) {
				registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, i), new ItemStack(Blocks.planks, 1, i + 1), new ItemStack(Blocks.planks, 1, i + 1), true);
			} else {
				registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, i), new ItemStack(Blocks.planks, 1, 0), new ItemStack(Blocks.planks, 1, 0), true);
			}
		}
		for (int i = 0; i < 6; i++) {
			if (i != 6) {
				registerCalculatorRecipe(new ItemStack(Blocks.wooden_slab, 2, i), new ItemStack(Blocks.wooden_slab, 1, i + 1), new ItemStack(Blocks.wooden_slab, 1, i + 1), true);
			} else {
				registerCalculatorRecipe(new ItemStack(Blocks.wooden_slab, 2, i), new ItemStack(Blocks.wooden_slab, 1, 0), new ItemStack(Blocks.wooden_slab, 1, 0), true);
			}
		}
		for (int i = 0; i < 7; i++) {
			if (i != 7) {
				registerCalculatorRecipe(new ItemStack(Blocks.stone_slab, 2, i), new ItemStack(Blocks.stone_slab, 1, i + 1), new ItemStack(Blocks.stone_slab, 1, i + 1), true);
			} else {
				registerCalculatorRecipe(new ItemStack(Blocks.stone_slab, 2, i), new ItemStack(Blocks.stone_slab, 1, 0), new ItemStack(Blocks.stone_slab, 1, 0), true);
			}
		}
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 0), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 0), new ItemStack(Blocks.planks, 1, 5), new ItemStack(Blocks.planks, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 1), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 1), new ItemStack(Blocks.planks, 1, 5), new ItemStack(Blocks.planks, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 3), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 0), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 1), new ItemStack(Blocks.planks, 1, 5), new ItemStack(Blocks.planks, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 2), new ItemStack(Blocks.planks, 1, 0), new ItemStack(Blocks.planks, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 2), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 2), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 5), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 3), new ItemStack(Blocks.planks, 1, 0), new ItemStack(Blocks.planks, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 3), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 4), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 4), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 4), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 0), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 5), new ItemStack(Blocks.planks, 1, 1), new ItemStack(Blocks.planks, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 5), new ItemStack(Blocks.planks, 1, 5), new ItemStack(Blocks.planks, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.planks, 2, 5), new ItemStack(Blocks.planks, 1), true);

		// sapling recipes
		for (int i = 0; i < 6; i++) {
			if (i != 6) {
				registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, i), new ItemStack(Blocks.sapling, 1, i + 1), new ItemStack(Blocks.sapling, 1, i + 1), true);
			} else {
				registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, i), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.sapling, 1, 0), true);
			}
		}
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 0), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.sapling, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 0), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.sapling, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 1), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.sapling, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.sapling, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 3), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.sapling, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.sapling, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 2), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.sapling, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 2), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.sapling, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 2), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.sapling, 1, 5), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 3), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.sapling, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 3), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.sapling, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 4), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.sapling, 1, 4), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 4), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.sapling, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 5), new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.sapling, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 2, 5), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.sapling, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.leaves, 1, 0), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.leaves, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.leaves, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.leaves, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.leaves, 1, 4), true);
		registerCalculatorRecipe(new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.leaves, 1, 5), true);

		// leave recipes
		for (int i = 0; i < 6; i++) {
			if (i != 3 && i != 4 && i != 5) {
				registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, i), new ItemStack(Blocks.leaves, 1, i + 1), new ItemStack(Blocks.leaves, 1, i + 1), true);
			}
			if (i == 3) {
				registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, i), new ItemStack(Blocks.leaves2, 1), new ItemStack(Blocks.leaves2, 1), true);
			} else if (i == 4) {
				registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves2, 1, 1), true);
			} else if (i == 5) {
				registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2, 1), new ItemStack(Blocks.leaves, 1), new ItemStack(Blocks.leaves, 1), true);
			}
		}

		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 0), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 0), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 1), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 1), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 1), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 2), new ItemStack(Blocks.leaves, 1), new ItemStack(Blocks.leaves, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 2), new ItemStack(Blocks.leaves2, 1), new ItemStack(Blocks.leaves, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 2), new ItemStack(Blocks.leaves2, 1), new ItemStack(Blocks.leaves2, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 3), new ItemStack(Blocks.leaves, 1), new ItemStack(Blocks.leaves, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 3), new ItemStack(Blocks.leaves2), new ItemStack(Blocks.leaves, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves, 2, 3), new ItemStack(Blocks.leaves2), new ItemStack(Blocks.leaves, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves2, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2), new ItemStack(Blocks.leaves, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2, 1), new ItemStack(Blocks.leaves, 1, 1), new ItemStack(Blocks.leaves, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2, 1), new ItemStack(Blocks.leaves2, 1, 1), new ItemStack(Blocks.leaves, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.leaves2, 2, 1), new ItemStack(Blocks.leaves, 1), true);

		// special log recipes
		registerCalculatorRecipe(new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.sapling, 1, 0), "dirt", true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.sapling, 1, 1), "dirt", true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.sapling, 1, 2), "dirt", true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.sapling, 1, 3), "dirt", true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.sapling, 1, 4), "dirt", true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.sapling, 1, 5), "dirt", true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.sapling, 1), new ItemStack(Blocks.grass, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.grass, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.grass, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.grass, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.grass, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.grass, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.planks, 1), true);

		registerCalculatorRecipe(new ItemStack(Blocks.log, 4, 0), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.leaves, 1, 0), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 4, 1), new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.leaves, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 4, 2), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.leaves, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 4, 3), new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.leaves, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 4, 0), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.leaves2, 1, 0), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 4, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.leaves2, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 4, 0), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.planks, 1, 0), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 4, 1), new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.planks, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 4, 2), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.planks, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 4, 3), new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.planks, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 4, 0), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.planks, 1, 4), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 4, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.planks, 1, 5), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 8, 0), new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.log, 1, 0), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 8, 1), new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.log, 1, 1), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 8, 2), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.log, 1, 2), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 8, 3), new ItemStack(Blocks.sapling, 1, 3), new ItemStack(Blocks.log, 1, 3), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 8, 0), new ItemStack(Blocks.sapling, 1, 4), new ItemStack(Blocks.log2, 1, 0), true);
		registerCalculatorRecipe(new ItemStack(Blocks.log2, 8, 1), new ItemStack(Blocks.sapling, 1, 5), new ItemStack(Blocks.log2, 1, 1), true);

		// new
		registerCalculatorRecipe(new ItemStack(Calculator.itemCalculator, 1), "ingotIron", "dustRedstone", true);
		registerCalculatorRecipe(Blocks.grass, "dirt", new ItemStack(Blocks.tallgrass, 1, 1), true);
		registerCalculatorRecipe(Blocks.gravel, "dirt", Calculator.small_stone, true);
		registerCalculatorRecipe(Blocks.grass, "dirt", Calculator.soil, true);
		registerCalculatorRecipe(Blocks.dirt, Blocks.sand, Calculator.soil, true);
		registerCalculatorRecipe(new ItemStack(Calculator.enrichedgold_ingot, 2), Calculator.reinforcediron_ingot, new ItemStack(Items.dye, 1, 11), true);
		registerCalculatorRecipe(new ItemStack(Calculator.weakeneddiamond, 6), "gemEmerald", new ItemStack(Items.dye, 1, 4), true);
		registerCalculatorRecipe(Items.ghast_tear, Items.blaze_rod, Items.ender_eye, true);
		registerCalculatorRecipe(new ItemStack(Items.blaze_powder, 16), Items.blaze_rod, Items.ghast_tear, true);
		registerCalculatorRecipe(new ItemStack(Blocks.end_stone, 16), Items.ender_eye, "stone", true);
		registerCalculatorRecipe(new ItemStack(Blocks.obsidian, 16), Items.magma_cream, "stone", true);
		registerCalculatorRecipe(new ItemStack(Blocks.netherrack, 32), Items.blaze_rod, "stone", true);
		registerCalculatorRecipe(Blocks.end_stone, Blocks.obsidian, Blocks.soul_sand, true);
		registerCalculatorRecipe(new ItemStack(Calculator.calculator_assembly, 8), "ingotIron", Blocks.stone_button, true);
		registerCalculatorRecipe(new ItemStack(Calculator.calculator_screen, 8), Calculator.redstone_ingot, "cobblestone", true);
		registerCalculatorRecipe(new ItemStack(Items.iron_ingot, 8), Items.ender_pearl, Items.ender_pearl, true);
		registerCalculatorRecipe(new ItemStack(Items.gold_ingot, 8), "gemEmerald", "gemEmerald", true);
		registerCalculatorRecipe(new ItemStack(Blocks.log, 8, 0), Blocks.obsidian, Blocks.obsidian, true);
		registerCalculatorRecipe(new ItemStack(Items.blaze_rod, 8), Items.ghast_tear, Items.ghast_tear, true);
		registerCalculatorRecipe(Blocks.vine, new ItemStack(Blocks.tallgrass, 1, 1), new ItemStack(Blocks.tallgrass, 1, 1), true);
		registerCalculatorRecipe(Items.melon_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, true);
		registerCalculatorRecipe(new ItemStack(Items.pumpkin_seeds, 2), Items.melon_seeds, Items.melon_seeds, true);
		registerCalculatorRecipe(Items.pumpkin_seeds, "cropCarrot", "cropPotato", true);

		registerCalculatorRecipe(new ItemStack(Blocks.piston, 1), new ItemStack(Calculator.reinforcediron_ingot, 1), "logWood", true);
		registerCalculatorRecipe(new ItemStack(Blocks.noteblock, 8), new ItemStack(Calculator.redstone_ingot, 1), "logWood", true);
		registerCalculatorRecipe(new ItemStack(Blocks.noteblock, 2), new ItemStack(Calculator.redstone_ingot, 1), "plankWood", true);
		registerCalculatorRecipe(new ItemStack(Items.reeds, 1), new ItemStack(Blocks.grass, 1), "treeSapling", true);

		registerCalculatorRecipe(new ItemStack(Items.fire_charge, 4), Items.snowball, Items.blaze_powder, true);
		registerCalculatorRecipe(new ItemStack(Items.leather, 2), Items.rotten_flesh, "slimeball", true);
		registerCalculatorRecipe(Items.slime_ball, Items.clay_ball, Items.rotten_flesh, true);
		registerCalculatorRecipe(Items.slime_ball, Calculator.pear, Items.clay_ball, true);

		registerCalculatorRecipe(new ItemStack(Items.slime_ball, 2), Calculator.rotten_pear, Calculator.rotten_pear, true);
		registerCalculatorRecipe(new ItemStack(Calculator.rotten_pear, 2), "slimeball", "slimeball", true);
		registerCalculatorRecipe(new ItemStack(Items.netherbrick, 4), Items.brick, Items.brick, true);
		registerCalculatorRecipe(new ItemStack(Items.brick, 1), Items.netherbrick, Items.netherbrick, true);
		registerCalculatorRecipe(new ItemStack(Items.nether_wart, 1), Items.reeds, Blocks.obsidian, true);

		registerCalculatorRecipe(new ItemStack(Items.wheat, 8), new ItemStack(Blocks.tallgrass, 1, 1), "treeSapling", true);
		registerCalculatorRecipe(new ItemStack(Items.wheat_seeds, 1), new ItemStack(Blocks.vine), "treeSapling", true);

		registerCalculatorRecipe(new ItemStack(Blocks.ender_chest, 1), Blocks.end_stone, Items.ender_eye, true);
		registerCalculatorRecipe(new ItemStack(Blocks.ender_chest, 16), Blocks.chest, Items.nether_star, true);

		for (int i = 0; i < 9; i++) {
			registerCalculatorRecipe(new ItemStack(Blocks.vine, 16), new ItemStack(Blocks.red_flower, 1, i), "treeSapling", true);
		}

		for (int i = 0; i < 9; i++) {
			if (i != 8) {
				registerCalculatorRecipe(new ItemStack(Blocks.red_flower, 2, i), new ItemStack(Blocks.red_flower, 1, i + 1), new ItemStack(Blocks.red_flower, 1, i + 1), true);
			} else {
				registerCalculatorRecipe(new ItemStack(Blocks.red_flower, 2, i), new ItemStack(Blocks.red_flower, 1, 0), new ItemStack(Blocks.red_flower, 1, 0), true);
			}
		}

		for (int i = 0; i < 16; i++) {
			if (i != 15) {
				registerCalculatorRecipe(new ItemStack(Blocks.wool, 2, i), new ItemStack(Blocks.wool, 1, i + 1), new ItemStack(Blocks.wool, 1, i + 1), true);
			} else {
				registerCalculatorRecipe(new ItemStack(Blocks.wool, 2, i), new ItemStack(Blocks.wool, 1, 0), new ItemStack(Blocks.wool, 1, 0), true);
			}
		}
		for (int i = 0; i < 16; i++) {
			if (i != 15) {
				registerCalculatorRecipe(new ItemStack(Blocks.stained_glass, 2, i), new ItemStack(Blocks.stained_glass, 1, i + 1), new ItemStack(Blocks.stained_glass, 1, i + 1), true);
			} else {
				registerCalculatorRecipe(new ItemStack(Blocks.stained_glass, 2, i), new ItemStack(Blocks.stained_glass, 1, 0), new ItemStack(Blocks.stained_glass, 1, 0), true);
			}
		}
		for (int i = 0; i < 16; i++) {
			if (i != 15) {
				registerCalculatorRecipe(new ItemStack(Blocks.stained_glass_pane, 2, i), new ItemStack(Blocks.stained_glass_pane, 1, i + 1), new ItemStack(Blocks.stained_glass_pane, 1, i + 1), true);
			} else {
				registerCalculatorRecipe(new ItemStack(Blocks.stained_glass_pane, 2, i), new ItemStack(Blocks.stained_glass_pane, 1, 0), new ItemStack(Blocks.stained_glass_pane, 1, 0), true);
			}
		}
		for (int i = 0; i < 16; i++) {
			if (i != 15) {
				registerCalculatorRecipe(new ItemStack(Blocks.stained_hardened_clay, 2, i), new ItemStack(Blocks.stained_hardened_clay, 1, i + 1),
						new ItemStack(Blocks.stained_hardened_clay, 1, i + 1), true);
			} else {
				registerCalculatorRecipe(new ItemStack(Blocks.stained_hardened_clay, 2, i), new ItemStack(Blocks.stained_hardened_clay, 1, 0), new ItemStack(Blocks.stained_hardened_clay, 1, 0), true);
			}
		}
		for (int i = 0; i < 16; i++) {
			if (i != 15) {
				registerCalculatorRecipe(new ItemStack(Blocks.carpet, 2, i), new ItemStack(Blocks.carpet, 1, i + 1), new ItemStack(Blocks.carpet, 1, i + 1), true);
			} else {
				registerCalculatorRecipe(new ItemStack(Blocks.carpet, 2, i), new ItemStack(Blocks.carpet, 1, 0), new ItemStack(Blocks.carpet, 1, 0), true);
			}
		}
		for (int i = 0; i < 16; i++) {
			if (i != 15) {

				registerCalculatorRecipe(new ItemStack(Items.dye, 2, i), new ItemStack(Items.dye, 1, i + 1), new ItemStack(Items.dye, 1, i + 1), true);
			} else {
				registerCalculatorRecipe(new ItemStack(Items.dye, 2, i), new ItemStack(Items.dye, 1, 0), new ItemStack(Items.dye, 1, 0), true);
			}
		}
		for (int i = 0; i < 6; i++) {
			if (i != 5) {
				if (i == 1) {
					registerCalculatorRecipe(new ItemStack(Blocks.double_plant, 2, i), new ItemStack(Blocks.tallgrass, 1, 1), new ItemStack(Blocks.tallgrass, 1, 1), true);
				} else if (i == 2) {
					registerCalculatorRecipe(new ItemStack(Blocks.tallgrass, 2, 1), new ItemStack(Blocks.tallgrass, 1, 2), new ItemStack(Blocks.tallgrass, 1, 2), true);
				} else if (i == 3) {
					registerCalculatorRecipe(new ItemStack(Blocks.tallgrass, 2, 2), new ItemStack(Blocks.double_plant, 1, i + 1), new ItemStack(Blocks.double_plant, 1, i + 1), true);
				} else {
					registerCalculatorRecipe(new ItemStack(Blocks.double_plant, 2, i), new ItemStack(Blocks.double_plant, 1, i + 1), new ItemStack(Blocks.double_plant, 1, i + 1), true);

				}
			} else {
				registerCalculatorRecipe(new ItemStack(Blocks.double_plant, 2, i), new ItemStack(Blocks.double_plant, 1, 0), new ItemStack(Blocks.double_plant, 1, 0), true);
			}
		}

		registerCalculatorRecipe(new ItemStack(Calculator.reinforcedstoneBrick, 2), Calculator.reinforcedstoneBlock, Calculator.reinforcedstoneBlock, false);
		registerCalculatorRecipe(new ItemStack(Calculator.reinforceddirtBrick, 2), Calculator.reinforceddirtBlock, Calculator.reinforceddirtBlock, false);
		registerCalculatorRecipe(Calculator.rainSensor, Blocks.daylight_detector, Items.bucket, false);

	}

	private static void addScientificRecipes() {
		registerScientificRecipe(Items.coal, Calculator.enrichedgold_ingot, Calculator.purified_coal);
		registerScientificRecipe(Items.iron_ingot, "dustRedstone", Calculator.redstone_ingot);
		registerScientificRecipe("gemDiamond", Calculator.reinforcediron_ingot, new ItemStack(Calculator.weakeneddiamond, 4));
		registerScientificRecipe(Calculator.baby_grenade, Calculator.baby_grenade, Calculator.grenade);
		registerScientificRecipe(Calculator.enriched_coal, Items.lava_bucket, Calculator.firecoal);
		registerScientificRecipe(Calculator.large_amethyst, "treeSapling", Calculator.AmethystSapling);
		registerScientificRecipe(Calculator.amethystLog, Calculator.amethystLeaf, Calculator.AmethystSapling);
		registerScientificRecipe(Calculator.amethystLog, Calculator.small_amethyst, Calculator.AmethystSapling);
		registerScientificRecipe(Calculator.amethystLog, Calculator.amethystLog, Calculator.AmethystSapling);
		registerScientificRecipe(Calculator.itemCalculator, Calculator.redstone_ingot, Calculator.itemTerrainModule);
		registerScientificRecipe(Calculator.itemEnergyModule, Calculator.small_amethyst, Calculator.starchextractor);
		registerScientificRecipe(Calculator.powerCube, Calculator.purified_coal, Calculator.itemEnergyModule);
		registerScientificRecipe(Calculator.reinforced_iron_block, Items.ender_eye, Calculator.fluxPlug);
		registerScientificRecipe("ingotRedstone", Items.ender_pearl, Calculator.fluxPoint);

	}

	private static void addAtomicRecipes() {
		registerAtomicRecipe("ingotIron", "dustRedstone", "ingotIron", Calculator.itemScientificCalculator);
		registerAtomicRecipe("gemDiamond", Calculator.atomic_binder, "gemDiamond", Calculator.flawlessdiamond);
		registerAtomicRecipe("gemDiamond", Items.blaze_rod, "gemDiamond", Calculator.flawlessfirediamond);
		registerAtomicRecipe(Blocks.end_stone, Calculator.electricdiamond, Blocks.obsidian, Calculator.enddiamond);
		registerAtomicRecipe(Calculator.AmethystSapling, Blocks.end_stone, Calculator.tanzaniteSapling, Calculator.PearSapling);
		registerAtomicRecipe(Calculator.itemScientificCalculator, Calculator.atomic_binder, "ingotRedstone", Calculator.itemAdvancedTerrainModule);
		registerAtomicRecipe(Calculator.tanzaniteLog, Calculator.atomic_binder, Calculator.tanzaniteLeaf, Calculator.tanzaniteSapling);
		registerAtomicRecipe(Calculator.large_tanzanite, Calculator.atomic_binder, "treeSapling", Calculator.tanzaniteSapling);
		registerAtomicRecipe(Calculator.healthprocessor, Calculator.itemEnergyModule, Calculator.hungerprocessor, Calculator.itemNutritionModule);
		registerAtomicRecipe(new ItemStack(Calculator.circuitBoard, 1, 4), Calculator.atomic_binder, "dustEnrichedGold", new ItemStack(Calculator.speedUpgrade, 4));
		registerAtomicRecipe(new ItemStack(Calculator.circuitBoard, 1, 5), Calculator.atomic_binder, "dustEnrichedGold", new ItemStack(Calculator.energyUpgrade, 4));
		registerAtomicRecipe(new ItemStack(Calculator.circuitBoard, 1, 6), Calculator.atomic_binder, "dustEnrichedGold", Calculator.voidUpgrade);
		registerAtomicRecipe(new ItemStack(Calculator.circuitBoard, 1, 9), Calculator.redstoneextractor, Calculator.large_tanzanite, Calculator.glowstoneextractor);
		registerAtomicRecipe("ingotRedstone", Calculator.starchextractor, "ingotRedstone", Calculator.redstoneextractor);
		registerAtomicRecipe(Calculator.itemEnergyModule, Calculator.flawlessfirediamond, Calculator.itemEnergyModule, Calculator.conductorMast);
		registerAtomicRecipe(Calculator.reinforced_iron_block, Blocks.chest, Calculator.reinforced_iron_block, Calculator.storageChamber);
		registerAtomicRecipe(Calculator.reassemblyChamber, Calculator.flawlessdiamond, Calculator.restorationChamber, Calculator.processingChamber);
		registerAtomicRecipe(Calculator.reinforcediron_ingot, Blocks.chest, Calculator.reinforcediron_ingot, Calculator.itemStorageModule);
		registerAtomicRecipe(Calculator.reinforcediron_ingot, Calculator.electricdiamond, Calculator.reinforcediron_ingot, Calculator.transmitter);
		registerAtomicRecipe(Calculator.reinforcediron_ingot, Calculator.flawlessfirediamond, Calculator.reinforcediron_ingot, new ItemStack(Calculator.weatherStation, 4));

	}

	private static void addFlawlessRecipes() {
		registerFlawlessRecipe(Calculator.PearSapling, Calculator.enddiamond, Calculator.enddiamond, Blocks.end_stone, Calculator.diamondSapling);
		registerFlawlessRecipe("ingotGold", "ingotGold", "ingotGold", "ingotGold", Items.diamond);
		registerFlawlessRecipe("gemDiamond", "gemDiamond", "gemDiamond", "gemDiamond", Items.emerald);
		registerFlawlessRecipe("ingotIron", "ingotIron", "ingotIron", "ingotIron", Items.ender_pearl);
		registerFlawlessRecipe("logWood", "logWood", "logWood", "logWood", Blocks.obsidian);
		registerFlawlessRecipe(Blocks.obsidian, Blocks.obsidian, Blocks.obsidian, Blocks.obsidian, Calculator.purifiedobsidianBlock);
		registerFlawlessRecipe(Calculator.reinforcedstoneBlock, Calculator.reinforcedstoneBlock, Calculator.reinforcedstoneBlock, Calculator.reinforcedstoneBlock, new ItemStack(
				Calculator.stablestoneBlock, 4));
		registerFlawlessRecipe(Calculator.broccoli, Calculator.broccoli, Calculator.broccoli, Calculator.broccoli, Calculator.fiddledewFruit);

		registerFlawlessRecipe(Calculator.itemEnergyModule, Calculator.itemCalculator, Calculator.itemCalculator, Calculator.itemEnergyModule, Calculator.itemLocatorModule);
		registerFlawlessRecipe(Calculator.flawlessdiamond, "blockGlass", "blockGlass", Calculator.flawlessdiamond, new ItemStack(Calculator.flawlessGlass, 4));
		registerFlawlessRecipe(Calculator.circuitBoard, Calculator.enriched_coal, Calculator.enriched_coal, Calculator.circuitBoard, new ItemStack(Calculator.controlled_Fuel, 4));
		registerFlawlessRecipe(Calculator.gas_lantern_off, new ItemStack(Calculator.circuitBoard, 1, 8), new ItemStack(Calculator.circuitBoard, 1, 8), Calculator.gas_lantern_off,
				Calculator.carbondioxideGenerator);

		registerFlawlessRecipe(Items.blaze_powder, Items.blaze_powder, Items.blaze_powder, Items.blaze_powder, Items.blaze_rod);
		registerFlawlessRecipe(Items.blaze_rod, Items.blaze_rod, Items.blaze_rod, Items.blaze_rod, Items.ghast_tear);
		registerFlawlessRecipe(Blocks.glass,Blocks.glass,Blocks.glass,Blocks.glass, new ItemStack(Calculator.stableglassBlock, 4));
	}

	/**
	 * 
	 * @param objects four parameters, see below
	 * @param output (ItemStack)
	 * @param input1 (ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input2 (ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param hidden Does the recipe require research? (Boolean)
	 */
	public static void registerCalculatorRecipe(Object... objects) {
		if (objects.length != 4) {
			Calculator.logger.warn("Calculator Recipes - Invalid Recipe Size!");
			return;
		}
		rearrangeCalculatorRecipe(objects);
	}

	/**
	 * @param objects three parameters, see below
	 * @param input1 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input2 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param output (Item, Block, ItemStack)
	 */
	public static void registerScientificRecipe(Object... objects) {
		if (objects.length != 3) {
			Calculator.logger.warn("Scientific Recipes - Invalid Recipe Size!");
			return;
		}
		scientific.add(objects);

	}

	/**
	 * @param objects four parameters, see below
	 * @param input1 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input2 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input3 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param output (Item, Block, ItemStack)
	 */
	public static void registerAtomicRecipe(Object... objects) {
		if (objects.length != 4) {
			Calculator.logger.warn("Atomic Recipes - Invalid Recipe Size!");
			return;
		}
		atomic.add(objects);

	}

	/**
	 * @param objects five parameters, see below
	 * @param input1 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input2 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input3 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param input4 (Item, Block, ItemStack, ItemStack[], OreDict String, OreStack)
	 * @param output (Item, Block, ItemStack)
	 */
	public static void registerFlawlessRecipe(Object... objects) {
		if (objects.length != 5) {
			FMLLog.warning("Flawless Recipes - Invalid Recipe Size!");
			return;
		}
		flawless.add(objects);

	}

	/**
	 * old versions put the output first this fixes that
	 * 
	 * @param objects
	 */
	private static void rearrangeCalculatorRecipe(Object... objects) {
		if (objects[3] == null || ((Boolean) objects[3])) {
			Object[] recipe = new Object[3];
			recipe[0] = objects[1];
			recipe[1] = objects[2];
			recipe[2] = objects[0];
			blocked.put(blocked.size(), recipe);
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
		public int currentRecipe =0;

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
			for (int i = 0; i < RecipeRegistry.blocked.size(); i++) {
				currentRecipe = i;
				this.addRecipe(RecipeRegistry.blocked.get(i));
			}

		}
		
		public void writeToNBT(NBTTagCompound nbt, Map<Integer, Integer> unblocked, String tag) {
			NBTTagList list = new NBTTagList();

			for (Entry<Integer, Integer> recipes : unblocked.entrySet()) {
				if (recipes.getValue() != 0) {
					NBTTagCompound compound = new NBTTagCompound();
					compound.setInteger("ID", recipes.getKey());
					compound.setInteger("TYPE", recipes.getValue());
					list.appendTag(compound);
				}
			}
			nbt.setTag(tag, list);
		}

		public Map<Integer, Integer> readFromNBT(NBTTagCompound nbt, String tag) {
			NBTTagList list = nbt.getTagList(tag, 10);
			Map<Integer, Integer> recipes = new THashMap<Integer, Integer>();
			for (int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound compound = list.getCompoundTagAt(i);
				recipes.put(compound.getInteger("ID"), compound.getInteger("TYPE"));
			}
			return recipes;
		}

		public void unblockStack(Map<Integer, Integer> unblocked, ItemStack stack) {
			if(unblocked==null){
				unblocked= new THashMap<Integer, Integer>();
			}
			List<Integer> inputs = getInputIDs(stack);
			List<Integer> outputs = getOutputIDs(stack);
			if (inputs != null) {
				for (int i = 0; i < inputs.size(); i++) {
					if (unblocked.get(i) ==null || unblocked.get(i) instanceof Integer && unblocked.get(i) == 0) {
						unblocked.put(i, 1);
					} else if (unblocked.get(i) == 1) {
						unblocked.replace(i, 2);
					}
				}
			}
			if (outputs != null) {
				for (int i = 0; i < outputs.size(); i++) {
					int current = unblocked.get(i);
					if (current != 2) {
						unblocked.replace(i, 2);
					}
				}
			}
		}

		public List<Integer> getInputIDs(ItemStack input) {
			if (input == null) {
				return null;
			}
			Iterator iterator = this.recipeList.entrySet().iterator();
			int pos = 0;
			List<Integer> positions = new ArrayList();

			Map.Entry entry;
			do {
				if (!iterator.hasNext()) {
					return positions;
				}

				entry = (Map.Entry) iterator.next();
				pos++;
				if (!(containsStack(input, (Object[]) entry.getKey(), false) == -1)) {
					positions.add(pos);
				}

			} while (iterator.hasNext());

			return positions;
		}

		public List<Integer> getOutputIDs(ItemStack output) {
			if (output == null) {
				return null;
			}
			Iterator iterator = this.recipeList.entrySet().iterator();
			int pos = 0;
			List<Integer> positions = new ArrayList();

			Map.Entry entry;
			do {
				if (!iterator.hasNext()) {
					return positions;
				}

				entry = (Map.Entry) iterator.next();
				pos++;
				if (!(containsStack(output, (Object[]) entry.getKey(), false) == -1)) {
					positions.add(pos);
				}

			} while (iterator.hasNext());

			return positions;
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
