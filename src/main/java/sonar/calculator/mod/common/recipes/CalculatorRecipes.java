package sonar.calculator.mod.common.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.SonarCore;
import sonar.core.recipes.DefinedRecipeHelper;
import sonar.core.recipes.ISonarRecipeObject;

import java.util.ArrayList;
import java.util.List;

public class CalculatorRecipes extends DefinedRecipeHelper<CalculatorRecipe> {

	private static final CalculatorRecipes recipes = new CalculatorRecipes();

    public static CalculatorRecipes instance() {
		return recipes;
	}

	public CalculatorRecipes() {
		super(2, 1, true);
		validInputs.add(ResearchRecipeType.class);
	}

	@Override
	public void addRecipes() {
		addRecipe(Calculator.baby_grenade, Calculator.grenadecasing, Blocks.TNT);
		addRecipe(SonarCore.reinforcedStoneBlock, "cobblestone", "plankWood");
		addRecipe(new ItemStack(SonarCore.reinforcedStoneBlock, 4), "cobblestone", "logWood");

		addRecipe(Calculator.wrench, Calculator.reinforced_sword, Calculator.reinforced_pickaxe);
		addRecipe(SonarCore.reinforcedDirtBlock, Blocks.DIRT, "plankWood");
		addRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 4), Blocks.DIRT, "logWood");
		addRecipe(new ItemStack(Calculator.enrichedGold, 4), "ingotGold", "dustRedstone");
		addRecipe(Calculator.reinforcediron_ingot, "ingotIron", SonarCore.reinforcedStoneBlock);
		addRecipe(Calculator.enriched_coal, Items.COAL, "dustRedstone");
		addRecipe(Calculator.broccoliSeeds, Items.WHEAT_SEEDS, Items.PUMPKIN_SEEDS);
		addRecipe(Calculator.sickle, Calculator.reinforced_shovel, Calculator.reinforced_axe);
		addRecipe(Calculator.scarecrow, Blocks.PUMPKIN, Blocks.HAY_BLOCK);
		addRecipe(Calculator.basic_lantern, Blocks.TORCH, Calculator.reinforcediron_ingot);
		addRecipe(Calculator.gas_lantern_off, Calculator.basic_lantern, Calculator.basic_lantern);
		addRecipe(Calculator.prunaeSeeds, Calculator.enriched_coal, Items.WHEAT_SEEDS);
		addRecipe(Calculator.enriched_coal, Calculator.coal_dust, Calculator.coal_dust);
		addRecipe(new ItemStack(SonarCore.reinforcedStoneBrick, 2), SonarCore.reinforcedStoneBlock, SonarCore.reinforcedStoneBlock);
		addRecipe(new ItemStack(SonarCore.stableGlass, 2), "blockGlass", "blockGlass");
		addRecipe(new ItemStack(SonarCore.stableStone[0], 2), SonarCore.reinforcedStoneBrick, SonarCore.reinforcedStoneBrick);
		addRecipe(new ItemStack(SonarCore.reinforcedDirtBrick, 2), SonarCore.reinforcedDirtBlock, SonarCore.reinforcedDirtBlock);
		addRecipe(Calculator.rainSensor, Blocks.DAYLIGHT_DETECTOR, Items.BUCKET);

		// addRecipe(Calculator.researchChamber, Calculator.reinforced_iron_block, Calculator.powerCube);

		// reinforced materials
		ResearchRecipeType type = ResearchRecipeType.REINFORCED_STONE;
		addRecipe(new ItemStack(SonarCore.reinforcedStoneBlock, 8), "cobblestone", Items.CLAY_BALL, type);
		addRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 2, 2), "stone", new ItemStack(Blocks.DIRT, 1, 2), type);
		addRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 1, 2), "cobblestone", new ItemStack(Blocks.DIRT, 1, 2), type);
		addRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 4), new ItemStack(Blocks.GRASS, 1), Blocks.LOG, type);
		addRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 4), new ItemStack(Blocks.GRASS, 1), Blocks.LOG2, type);
		addRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 1), new ItemStack(Blocks.GRASS, 1), new ItemStack(Blocks.PLANKS, 1), type);
		addRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 1), Blocks.DIRT, "plankWood", type);
		addRecipe(new ItemStack(SonarCore.reinforcedDirtBlock, 4), Blocks.DIRT, "logWood", type);
		addRecipe(new ItemStack(SonarCore.reinforcedStoneBlock, 1), "cobblestone", "plankWood", type);
		addRecipe(new ItemStack(SonarCore.reinforcedStoneBlock, 4), "cobblestone", "logWood", type);
		addRecipe(new ItemStack(SonarCore.reinforcedStoneBlock, 6), "stone", "logWood", type);
		addRecipe(new ItemStack(SonarCore.reinforcedStoneBlock, 2), "stone", "plankWood", type);

		// furnace
		type = ResearchRecipeType.FURNACE;
		addRecipe(Calculator.powerCube, Blocks.FURNACE, "stone", type);
		addRecipe(Calculator.powerCube, Blocks.FURNACE, "cobblestone", type);
		addRecipe(Calculator.powerCube, Blocks.FURNACE, SonarCore.reinforcedStoneBlock, type);
		addRecipe(Calculator.stoneSeparator, Blocks.FURNACE, Blocks.FURNACE, type);
		addRecipe(new ItemStack(Blocks.FURNACE, 1), Blocks.CRAFTING_TABLE, "stone", type);
		addRecipe(new ItemStack(Blocks.FURNACE, 1), Blocks.CRAFTING_TABLE, "cobblestone", type);
		addRecipe(Items.BREWING_STAND, Items.BLAZE_POWDER, Blocks.FURNACE, type);
		addRecipe(new ItemStack(Blocks.CRAFTING_TABLE, 2), Blocks.FURNACE, new ItemStack(Blocks.PLANKS, 1), type);

		// cobblestone
		type = ResearchRecipeType.COBBLESTONE;
		addRecipe(new ItemStack(Blocks.MOSSY_COBBLESTONE, 4), "stone", "treeSapling", type);
		addRecipe(new ItemStack(Blocks.MOSSY_COBBLESTONE, 2), "stone", "treeLeaves", type);
		addRecipe(new ItemStack(Blocks.MOSSY_COBBLESTONE, 2), "cobblestone", "treeSapling", type);
		addRecipe(new ItemStack(Blocks.MOSSY_COBBLESTONE, 1), "cobblestone", "treeLeaves", type);
		addRecipe(new ItemStack(Blocks.MOSSY_COBBLESTONE, 2), Blocks.VINE, "cobblestone", type);
		addRecipe(new ItemStack(Blocks.MOSSY_COBBLESTONE, 4), Blocks.VINE, "stone", type);

		// stone
		type = ResearchRecipeType.STONE;
		addRecipe(new ItemStack(Blocks.STONEBRICK, 1, 2), "cobblestone", "stone", type);
		addRecipe(Blocks.STONE, "cobblestone", "cobblestone", type);
		addRecipe(Blocks.STONE, Calculator.small_stone, type);
		addRecipe(Blocks.STONE, Blocks.GRAVEL, Blocks.GRAVEL, type);

		// dirt
		type = ResearchRecipeType.DIRT;
		addRecipe(new ItemStack(Blocks.DIRT, 1), Calculator.soil, type);
		addRecipe(new ItemStack(Blocks.DIRT, 1), Blocks.GRAVEL, "sand", type);
        addRecipe(new ItemStack(Blocks.DIRT, 1), "sand", "sand", type);
		addRecipe(new ItemStack(Blocks.DIRT, 1, 2), new ItemStack(Blocks.GRASS, 1), Blocks.DIRT, type); // addRecipe(new ItemStack(Blocks.DIRT, 1, 2), new ItemStack(Blocks.GRASS, 1), new ItemStack(Blocks.red_mushroom_block, 1, 5), type); // addRecipe(new ItemStack(Blocks.DIRT, 1, 2), new ItemStack(Blocks.GRASS, 1), new ItemStack(Blocks.brown_mushroom_block, 1, 5), type); addRecipe(Blocks.mycelium, new ItemStack(Blocks.DIRT, 1, 2), type); addRecipe(new ItemStack(Blocks.mycelium, 2), new ItemStack(Blocks.GRASS, 1), new ItemStack(Blocks.DIRT, 1, 2), type); addRecipe(new ItemStack(Blocks.mycelium, 2), new ItemStack(Blocks.GRASS, 1), new ItemStack(Blocks.GRASS, 1), type); addRecipe(Blocks.DIRT, Blocks.SAND, Calculator.soil, type);

		// sand
		type = ResearchRecipeType.SAND;
		addRecipe(Blocks.SAND, Blocks.GRAVEL, new ItemStack(Blocks.GRASS, 1), type);
		addRecipe(new ItemStack(Blocks.SAND, 1), "cobblestone", Blocks.GRAVEL, type);
		addRecipe(new ItemStack(Blocks.SAND, 2), "stone", Blocks.GRAVEL, type);
		addRecipe(Blocks.SAND, Blocks.GRAVEL, Blocks.DIRT, type);

		// gravel
		type = ResearchRecipeType.GRAVEL;
		addRecipe(Blocks.GRAVEL, Blocks.SAND, new ItemStack(Blocks.GRASS, 1), type);
		addRecipe(Blocks.GRAVEL, new ItemStack(Blocks.GRASS, 1), "cobblestone", type);
		addRecipe(Blocks.GRAVEL, "cobblestone", Blocks.DIRT, type);
		addRecipe(Blocks.GRAVEL, "stone", Blocks.DIRT, type);
		addRecipe(Blocks.GRAVEL, Blocks.SAND, Blocks.DIRT, type);
		addRecipe(new ItemStack(Blocks.GRAVEL, 2), "stone", Blocks.SAND, type);
		addRecipe(new ItemStack(Blocks.GRAVEL, 1), "cobblestone", Blocks.SAND, type);
		addRecipe(new ItemStack(Blocks.GRAVEL, 2), Blocks.DIRT, Blocks.DIRT, type);
		addRecipe(new ItemStack(Blocks.GRAVEL, 3), "stone", new ItemStack(Blocks.GRASS, 1), type);
		addRecipe(Blocks.GRAVEL, Blocks.DIRT, Calculator.small_stone, type);

		// sandstone
		type = ResearchRecipeType.SANDSTONE;
		addRecipe(new ItemStack(Blocks.SANDSTONE, 1, 2), "stone", Blocks.SANDSTONE, type);
		addRecipe(new ItemStack(Blocks.SANDSTONE, 1, 1), "cobblestone", Blocks.SANDSTONE, type);

		// grass
		type = ResearchRecipeType.GRASS;
		addRecipe(new ItemStack(Blocks.GRASS, 2), Items.WHEAT_SEEDS, Blocks.DIRT, type);
		addRecipe(new ItemStack(Blocks.GRASS, 2), Items.PUMPKIN_SEEDS, Blocks.DIRT, type);
		addRecipe(new ItemStack(Blocks.GRASS, 4), Calculator.broccoliSeeds, Blocks.DIRT, type);
		addRecipe(Blocks.GRASS, Blocks.DIRT, Calculator.soil, type);
		addRecipe(Blocks.GRASS, Blocks.DIRT, new ItemStack(Blocks.TALLGRASS, 1, 1), type);

		// glass
		type = ResearchRecipeType.GLASS;
		addRecipe(new ItemStack(Blocks.STAINED_GLASS, 1, 8), "stone", Blocks.GLASS, type);
		addRecipe(new ItemStack(Blocks.STAINED_GLASS, 1, 7), "cobblestone", Blocks.GLASS, type);

		// glass_pane
		type = ResearchRecipeType.GLASS_PANE;
		addRecipe(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 8), "stone", Blocks.GLASS_PANE, type);
		addRecipe(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 7), "cobblestone", Blocks.GLASS_PANE, type);

		// cactus
		type = ResearchRecipeType.CACTUS;
		addRecipe(Blocks.CACTUS, "sand", "treeSapling", type);
		addRecipe(Blocks.CACTUS, "sand", Items.WHEAT_SEEDS, type);
		addRecipe(Blocks.CACTUS, "sand", "treeLeaves", type);

		// nether
		type = ResearchRecipeType.NETHER;
		addRecipe(new ItemStack(Blocks.NETHERRACK, 64), Blocks.OBSIDIAN, "stone", type);
		addRecipe(new ItemStack(Blocks.NETHERRACK, 32), Blocks.OBSIDIAN, "cobblestone", type);
		addRecipe(new ItemStack(Blocks.NETHERRACK, 32), Blocks.SOUL_SAND, "stone", type);
		addRecipe(new ItemStack(Blocks.NETHERRACK, 16), Blocks.SOUL_SAND, "cobblestone", type);
		addRecipe(new ItemStack(Blocks.SOUL_SAND, 64), Blocks.NETHERRACK, Items.GHAST_TEAR, type);
		addRecipe(new ItemStack(Blocks.SOUL_SAND, 32), "sand", Items.GHAST_TEAR, type);
		addRecipe(new ItemStack(Blocks.OBSIDIAN, 16), Items.MAGMA_CREAM, "stone", type);
		addRecipe(new ItemStack(Blocks.NETHERRACK, 32), Items.BLAZE_ROD, "stone", type);
		addRecipe(new ItemStack(Items.NETHER_WART, 1), Items.REEDS, Blocks.OBSIDIAN, type);
		addRecipe(Items.GHAST_TEAR, Items.BLAZE_ROD, Items.ENDER_EYE, type);
		addRecipe(new ItemStack(Items.BLAZE_POWDER, 16), Items.BLAZE_ROD, Items.GHAST_TEAR, type);
		addRecipe(new ItemStack(Items.NETHERBRICK, 4), Items.BRICK, Items.BRICK, type);
		addRecipe(new ItemStack(Items.BRICK, 1), Items.NETHERBRICK, Items.NETHERBRICK, type);
		addRecipe(new ItemStack(Blocks.LOG, 8, 0), Blocks.OBSIDIAN, Blocks.OBSIDIAN, type);
		addRecipe(new ItemStack(Items.BLAZE_ROD, 8), Items.GHAST_TEAR, Items.GHAST_TEAR, type);
		addRecipe(Items.LAVA_BUCKET, Items.WATER_BUCKET, Items.BLAZE_POWDER, type);
		addRecipe(new ItemStack(Items.FIRE_CHARGE, 4), Items.SNOWBALL, Items.BLAZE_POWDER, type);

		// buckets
		addRecipe(Items.LAVA_BUCKET, Items.WATER_BUCKET, new ItemStack(Items.DYE, 1, 14), ResearchRecipeType.DYE);
		addRecipe(Items.LAVA_BUCKET, Items.WATER_BUCKET, new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 1), ResearchRecipeType.CLAY_BLOCK);
		addRecipe(Items.LAVA_BUCKET, Items.WATER_BUCKET, new ItemStack(Blocks.WOOL, 1, 1), ResearchRecipeType.WOOL);
		addRecipe(Items.LAVA_BUCKET, Items.WATER_BUCKET, new ItemStack(Blocks.STAINED_GLASS, 1, 1), ResearchRecipeType.GLASS);
		addRecipe(Items.LAVA_BUCKET, Items.WATER_BUCKET, new ItemStack(Blocks.RED_FLOWER, 1, 5), ResearchRecipeType.FLOWER);

		// irons
		type = ResearchRecipeType.IRON;
		addRecipe(new ItemStack(Blocks.ANVIL, 1), Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, type);
		addRecipe(new ItemStack(Items.MINECART, 1), "ingotIron", "ingotIron", type);
		addRecipe(new ItemStack(Blocks.RAIL, 4), Blocks.LADDER, "stone", type);
		addRecipe(new ItemStack(Blocks.RAIL, 4), Blocks.LADDER, "cobblestone", type);
		addRecipe(new ItemStack(Items.IRON_INGOT, 8), Items.ENDER_PEARL, Items.ENDER_PEARL, type);
		addRecipe(new ItemStack(Calculator.itemCalculator, 1), "ingotIron", "dustRedstone", type);
		addRecipe(new ItemStack(Items.BONE, 16), Items.SKULL, "ingotIron", type);
		addRecipe(new ItemStack(Items.ROTTEN_FLESH, 16), new ItemStack(Items.SKULL, 1, 2), "ingotIron", type);
		addRecipe(new ItemStack(Items.GUNPOWDER, 16), new ItemStack(Items.SKULL, 1, 4), "ingotIron", type);

		// emerald
		type = ResearchRecipeType.EMERALD;
		addRecipe(new ItemStack(Items.GOLD_INGOT, 8), "gemEmerald", "gemEmerald", type);
		addRecipe(new ItemStack(Calculator.weakeneddiamond, 6), "gemEmerald", new ItemStack(Items.DYE, 1, 4), type);
		addRecipe(new ItemStack(Blocks.OBSIDIAN, 32), "gemEmerald", "stone", type);

		// misc
		addRecipe(new ItemStack(Calculator.enrichedgold_ingot, 2), Calculator.reinforcediron_ingot, new ItemStack(Items.DYE, 1, 11), ResearchRecipeType.DYE);
		addRecipe(new ItemStack(Calculator.calculator_assembly, 8), "ingotIron", Blocks.STONE_BUTTON, ResearchRecipeType.STONE);
		addRecipe(new ItemStack(Calculator.calculator_screen, 8), Calculator.redstone_ingot, "cobblestone", ResearchRecipeType.COBBLESTONE);
		addRecipe(Blocks.VINE, new ItemStack(Blocks.TALLGRASS, 1, 1), new ItemStack(Blocks.TALLGRASS, 1, 1), ResearchRecipeType.FOLIAGE);
		addRecipe(new ItemStack(Blocks.PISTON, 1), new ItemStack(Calculator.reinforcediron_ingot, 1), "logWood", ResearchRecipeType.WOOD);
		addRecipe(new ItemStack(Blocks.NOTEBLOCK, 8), new ItemStack(Calculator.redstone_ingot, 1), "logWood", ResearchRecipeType.WOOD);
		addRecipe(new ItemStack(Blocks.NOTEBLOCK, 2), new ItemStack(Calculator.redstone_ingot, 1), "plankWood", ResearchRecipeType.PLANKS);
		addRecipe(Items.CLAY_BALL, Calculator.soil, Calculator.small_stone, ResearchRecipeType.CLAY_BLOCK);

		// slime ball
		type = ResearchRecipeType.SLIMEBALL;
		addRecipe(new ItemStack(Items.LEATHER, 2), Items.ROTTEN_FLESH, "slimeball", type);
		addRecipe(Items.SLIME_BALL, Items.CLAY_BALL, Items.ROTTEN_FLESH, type);
		addRecipe(Items.SLIME_BALL, Calculator.pear, Items.CLAY_BALL, type);
		addRecipe(new ItemStack(Items.SLIME_BALL, 2), Calculator.rotten_pear, Calculator.rotten_pear, type);
		addRecipe(new ItemStack(Calculator.rotten_pear, 2), "slimeball", "slimeball", type);
		addRecipe(Blocks.STICKY_PISTON, Blocks.PISTON, Calculator.rotten_pear, type);

		// crops
		type = ResearchRecipeType.CROP;
		addRecipe(Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, type);
		addRecipe(new ItemStack(Items.PUMPKIN_SEEDS, 2), Items.MELON_SEEDS, Items.MELON_SEEDS, type);
		addRecipe(new ItemStack(Items.WHEAT, 8), new ItemStack(Blocks.TALLGRASS, 1, 1), "treeSapling", type);
		addRecipe(new ItemStack(Items.WHEAT_SEEDS, 1), new ItemStack(Blocks.VINE), "treeSapling", type);
		addRecipe(Items.PUMPKIN_SEEDS, "cropCarrot", "cropPotato", type);

		// end
		type = ResearchRecipeType.END;
		addRecipe(new ItemStack(Blocks.ENDER_CHEST, 1), Blocks.END_STONE, Items.ENDER_EYE, type);
		addRecipe(new ItemStack(Blocks.ENDER_CHEST, 16), Blocks.CHEST, Items.NETHER_STAR, type);
		addRecipe(new ItemStack(Blocks.END_STONE, 16), Items.ENDER_EYE, "stone", type);
		addRecipe(Blocks.END_STONE, Blocks.OBSIDIAN, Blocks.SOUL_SAND, type);
		addRecipe(new ItemStack(Blocks.END_PORTAL_FRAME, 3), Blocks.ENCHANTING_TABLE, Items.ENDER_EYE, type);

		// addRecipe(Blocks.fence, "stickWood", "stickWood", true);

		type = ResearchRecipeType.WOOD;

		for (int i = 0; i < 6; i++) {
			if (i != 3 && i != 4 && i != 5) {
				addRecipe(new ItemStack(Blocks.LOG, 2, i), new ItemStack(Blocks.LOG, 1, i + 1), new ItemStack(Blocks.LOG, 1, i + 1), type);
			}
			if (i == 3) {
				addRecipe(new ItemStack(Blocks.LOG, 2, i), new ItemStack(Blocks.LOG2, 1, 0), new ItemStack(Blocks.LOG2, 1, 0), type);
			} else if (i == 4) {
				addRecipe(new ItemStack(Blocks.LOG2, 2, 0), new ItemStack(Blocks.LOG2, 1, 1), new ItemStack(Blocks.LOG2, 1, 1), type);
			} else if (i == 5) {
				addRecipe(new ItemStack(Blocks.LOG2, 2, 1), new ItemStack(Blocks.LOG, 1, 0), new ItemStack(Blocks.LOG, 1, 0), type);
			}
		}
		addRecipe(new ItemStack(Blocks.LOG, 2, 0), new ItemStack(Blocks.LOG, 1, 2), new ItemStack(Blocks.LOG, 1, 3), type);
		addRecipe(new ItemStack(Blocks.LOG, 2, 1), new ItemStack(Blocks.LOG, 1, 2), new ItemStack(Blocks.LOG, 1, 3), type);
		addRecipe(new ItemStack(Blocks.LOG, 2, 1), new ItemStack(Blocks.LOG2, 1, 1), new ItemStack(Blocks.LOG, 1, 3), type);
		addRecipe(new ItemStack(Blocks.LOG, 2, 1), new ItemStack(Blocks.LOG2, 1, 1), new ItemStack(Blocks.LOG, 1, 2), type);
		addRecipe(new ItemStack(Blocks.LOG, 2, 0), new ItemStack(Blocks.LOG, 1, 2), new ItemStack(Blocks.LOG, 1, 1), type);
		addRecipe(new ItemStack(Blocks.LOG, 2, 0), new ItemStack(Blocks.LOG2, 1, 1), new ItemStack(Blocks.LOG, 1, 1), type);
		addRecipe(new ItemStack(Blocks.LOG, 2, 2), new ItemStack(Blocks.LOG, 1, 0), new ItemStack(Blocks.LOG, 1, 3), type);
		addRecipe(new ItemStack(Blocks.LOG, 2, 2), new ItemStack(Blocks.LOG2, 1, 0), new ItemStack(Blocks.LOG, 1, 3), type);
		addRecipe(new ItemStack(Blocks.LOG, 2, 2), new ItemStack(Blocks.LOG2, 1, 0), new ItemStack(Blocks.LOG2, 1, 1), type);
		addRecipe(new ItemStack(Blocks.LOG, 2, 3), new ItemStack(Blocks.LOG, 1, 0), new ItemStack(Blocks.LOG, 1, 1), type);
		addRecipe(new ItemStack(Blocks.LOG, 2, 3), new ItemStack(Blocks.LOG2, 1, 0), new ItemStack(Blocks.LOG, 1, 1), type);
		addRecipe(new ItemStack(Blocks.LOG, 2, 3), new ItemStack(Blocks.LOG2, 1, 0), new ItemStack(Blocks.LOG, 1, 0), type);
		addRecipe(new ItemStack(Blocks.LOG2, 2, 0), new ItemStack(Blocks.LOG, 1, 2), new ItemStack(Blocks.LOG2, 1, 0), type);
		addRecipe(new ItemStack(Blocks.LOG2, 2, 0), new ItemStack(Blocks.LOG, 1, 2), new ItemStack(Blocks.LOG, 1, 0), type);
		addRecipe(new ItemStack(Blocks.LOG2, 2, 1), new ItemStack(Blocks.LOG, 1, 1), new ItemStack(Blocks.LOG, 1, 3), type);
		addRecipe(new ItemStack(Blocks.LOG2, 2, 1), new ItemStack(Blocks.LOG2, 1, 1), new ItemStack(Blocks.LOG, 1, 0), type);

		// special log recipes

		for (int i = 0; i < 4; i++) {
			addRecipe(new ItemStack(Blocks.LOG, 1, i), new ItemStack(Blocks.SAPLING, 1, i), Blocks.DIRT, type);
			addRecipe(new ItemStack(Blocks.LOG, 1, i), new ItemStack(Blocks.SAPLING, 1, i), new ItemStack(Blocks.GRASS, 1), type);
			addRecipe(new ItemStack(Blocks.LOG, 4, i), new ItemStack(Blocks.SAPLING, 1, i), new ItemStack(Blocks.PLANKS, 1, i), type);
			addRecipe(new ItemStack(Blocks.LOG, 8, i), new ItemStack(Blocks.SAPLING, 1, i), new ItemStack(Blocks.LOG, 1, i), type);
			addRecipe(new ItemStack(Blocks.LOG, 4, i), new ItemStack(Blocks.SAPLING, 1, i), new ItemStack(Blocks.LEAVES, 1, i), type);
		}

		for (int i = 0; i < 2; i++) {
			addRecipe(new ItemStack(Blocks.LOG2, 1, i), new ItemStack(Blocks.SAPLING, 1, i + 4), Blocks.DIRT, type);
			addRecipe(new ItemStack(Blocks.LOG2, 1, i), new ItemStack(Blocks.SAPLING, 1, i + 4), new ItemStack(Blocks.GRASS, 1), type);
			addRecipe(new ItemStack(Blocks.LOG2, 4, i), new ItemStack(Blocks.SAPLING, 1, i + 4), new ItemStack(Blocks.LEAVES2, 1, i), type);
			addRecipe(new ItemStack(Blocks.LOG2, 4, i), new ItemStack(Blocks.SAPLING, 1, i + 4), new ItemStack(Blocks.PLANKS, 1, i + 4), type);
			addRecipe(new ItemStack(Blocks.LOG2, 4, i), new ItemStack(Blocks.SAPLING, 1, i + 4), new ItemStack(Blocks.PLANKS, 1, i + 4), type);
			addRecipe(new ItemStack(Blocks.LOG2, 8, i), new ItemStack(Blocks.SAPLING, 1, i + 4), new ItemStack(Blocks.LOG2, 1, i), type);
		}
		type = ResearchRecipeType.PLANKS;

		for (int i = 0; i < 6; i++) {
			if (i != 5) {
				addRecipe(new ItemStack(Blocks.PLANKS, 2, i), new ItemStack(Blocks.PLANKS, 1, i + 1), new ItemStack(Blocks.PLANKS, 1, i + 1), type);
			} else {
				addRecipe(new ItemStack(Blocks.PLANKS, 2, i), new ItemStack(Blocks.PLANKS, 1, 0), new ItemStack(Blocks.PLANKS, 1, 0), type);
			}
		}
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 0), new ItemStack(Blocks.PLANKS, 1, 2), new ItemStack(Blocks.PLANKS, 1, 1), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 0), new ItemStack(Blocks.PLANKS, 1, 5), new ItemStack(Blocks.PLANKS, 1, 1), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 1), new ItemStack(Blocks.PLANKS, 1, 2), new ItemStack(Blocks.PLANKS, 1, 3), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 1), new ItemStack(Blocks.PLANKS, 1, 5), new ItemStack(Blocks.PLANKS, 1, 3), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 3), new ItemStack(Blocks.PLANKS, 1, 4), new ItemStack(Blocks.PLANKS, 1, 0), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 1), new ItemStack(Blocks.PLANKS, 1, 5), new ItemStack(Blocks.PLANKS, 1, 2), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 2), new ItemStack(Blocks.PLANKS, 1, 0), new ItemStack(Blocks.PLANKS, 1, 3), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 2), new ItemStack(Blocks.PLANKS, 1, 4), new ItemStack(Blocks.PLANKS, 1, 3), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 2), new ItemStack(Blocks.PLANKS, 1, 4), new ItemStack(Blocks.PLANKS, 1, 5), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 3), new ItemStack(Blocks.PLANKS, 1, 0), new ItemStack(Blocks.PLANKS, 1, 1), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 3), new ItemStack(Blocks.PLANKS, 1, 4), new ItemStack(Blocks.PLANKS, 1, 1), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 4), new ItemStack(Blocks.PLANKS, 1, 2), new ItemStack(Blocks.PLANKS, 1, 4), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 4), new ItemStack(Blocks.PLANKS, 1, 2), new ItemStack(Blocks.PLANKS, 1, 0), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 5), new ItemStack(Blocks.PLANKS, 1, 1), new ItemStack(Blocks.PLANKS, 1, 3), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 5), new ItemStack(Blocks.PLANKS, 1, 5), new ItemStack(Blocks.PLANKS, 1), type);
		addRecipe(new ItemStack(Blocks.PLANKS, 2, 5), new ItemStack(Blocks.PLANKS, 1), type); /* for (int i = 0; i < 6; i++) { if (i != 5) { addRecipe(new ItemStack(Blocks.wooden_slab, 2, i), new ItemStack(Blocks.wooden_slab, 1, i + 1), new ItemStack(Blocks.wooden_slab, 1, i + 1), true); } else { addRecipe(new ItemStack(Blocks.wooden_slab, 2, i), new ItemStack(Blocks.wooden_slab, 1, 0), new ItemStack(Blocks.wooden_slab, 1, 0), true); } }
																								 * 
																								 * for (int i = 0; i < 7; i++) { if (i != 6) { addRecipe(new ItemStack(Blocks.STONE_slab, 2, i), new ItemStack(Blocks.STONE_slab, 1, i + 1), new ItemStack(Blocks.STONE_slab, 1, i + 1), true); } else { addRecipe(new ItemStack(Blocks.STONE_slab, 2, i), new ItemStack(Blocks.STONE_slab, 1, 0), new ItemStack(Blocks.STONE_slab, 1, 0), true); } } */
		// saplings
		type = ResearchRecipeType.SAPLING;
		for (int i = 0; i < 6; i++) {
			if (i != 5) {
				addRecipe(new ItemStack(Blocks.SAPLING, 2, i), new ItemStack(Blocks.SAPLING, 1, i + 1), new ItemStack(Blocks.SAPLING, 1, i + 1), type);
			} else {
				addRecipe(new ItemStack(Blocks.SAPLING, 2, i), new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(Blocks.SAPLING, 1, 0), type);
			}
		}
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 0), new ItemStack(Blocks.SAPLING, 1, 2), new ItemStack(Blocks.SAPLING, 1, 1), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 0), new ItemStack(Blocks.SAPLING, 1, 5), new ItemStack(Blocks.SAPLING, 1, 1), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 1), new ItemStack(Blocks.SAPLING, 1, 2), new ItemStack(Blocks.SAPLING, 1, 3), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 1), new ItemStack(Blocks.SAPLING, 1, 5), new ItemStack(Blocks.SAPLING, 1, 3), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 3), new ItemStack(Blocks.SAPLING, 1, 4), new ItemStack(Blocks.SAPLING, 1), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 1), new ItemStack(Blocks.SAPLING, 1, 5), new ItemStack(Blocks.SAPLING, 1, 2), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 2), new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(Blocks.SAPLING, 1, 3), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 2), new ItemStack(Blocks.SAPLING, 1, 4), new ItemStack(Blocks.SAPLING, 1, 3), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 2), new ItemStack(Blocks.SAPLING, 1, 4), new ItemStack(Blocks.SAPLING, 1, 5), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 3), new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(Blocks.SAPLING, 1, 1), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 3), new ItemStack(Blocks.SAPLING, 1, 4), new ItemStack(Blocks.SAPLING, 1, 1), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 4), new ItemStack(Blocks.SAPLING, 1, 2), new ItemStack(Blocks.SAPLING, 1, 4), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 4), new ItemStack(Blocks.SAPLING, 1, 2), new ItemStack(Blocks.SAPLING, 1), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 5), new ItemStack(Blocks.SAPLING, 1, 1), new ItemStack(Blocks.SAPLING, 1, 3), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 2, 5), new ItemStack(Blocks.SAPLING, 1, 5), new ItemStack(Blocks.SAPLING, 1), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(Blocks.LOG, 1, 0), new ItemStack(Blocks.LEAVES, 1, 0), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 1, 1), new ItemStack(Blocks.LOG, 1, 1), new ItemStack(Blocks.LEAVES, 1, 1), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 1, 2), new ItemStack(Blocks.LOG, 1, 2), new ItemStack(Blocks.LEAVES, 1, 2), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 1, 3), new ItemStack(Blocks.LOG, 1, 3), new ItemStack(Blocks.LEAVES, 1, 3), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 1, 4), new ItemStack(Blocks.LOG2, 1, 0), new ItemStack(Blocks.LEAVES2, 1, 0), type);
		addRecipe(new ItemStack(Blocks.SAPLING, 1, 5), new ItemStack(Blocks.LOG2, 1, 1), new ItemStack(Blocks.LEAVES2, 1, 1), type);

		// leaves
		type = ResearchRecipeType.LEAVES;
		for (int i = 0; i < 6; i++) {
			if (i != 3 && i != 4 && i != 5) {
				addRecipe(new ItemStack(Blocks.LEAVES, 2, i), new ItemStack(Blocks.LEAVES, 1, i + 1), new ItemStack(Blocks.LEAVES, 1, i + 1), type);
			}
			if (i == 3) {
				addRecipe(new ItemStack(Blocks.LEAVES, 2, i), new ItemStack(Blocks.LEAVES2, 1), new ItemStack(Blocks.LEAVES2, 1), type);
			} else if (i == 4) {
				addRecipe(new ItemStack(Blocks.LEAVES2, 2), new ItemStack(Blocks.LEAVES2, 1, 1), new ItemStack(Blocks.LEAVES2, 1, 1), type);
			} else if (i == 5) {
				addRecipe(new ItemStack(Blocks.LEAVES2, 2, 1), new ItemStack(Blocks.LEAVES, 1), new ItemStack(Blocks.LEAVES, 1), type);
			}
		}

		addRecipe(new ItemStack(Blocks.LEAVES, 2, 0), new ItemStack(Blocks.LEAVES, 1, 2), new ItemStack(Blocks.LEAVES, 1, 1), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 2, 0), new ItemStack(Blocks.LEAVES2, 1, 1), new ItemStack(Blocks.LEAVES, 1, 1), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 2, 1), new ItemStack(Blocks.LEAVES, 1, 2), new ItemStack(Blocks.LEAVES, 1, 3), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 2, 1), new ItemStack(Blocks.LEAVES2, 1, 1), new ItemStack(Blocks.LEAVES, 1, 3), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 2, 1), new ItemStack(Blocks.LEAVES2, 1, 1), new ItemStack(Blocks.LEAVES, 1, 2), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 2, 2), new ItemStack(Blocks.LEAVES, 1), new ItemStack(Blocks.LEAVES, 1, 3), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 2, 2), new ItemStack(Blocks.LEAVES2, 1), new ItemStack(Blocks.LEAVES, 1, 3), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 2, 2), new ItemStack(Blocks.LEAVES2, 1), new ItemStack(Blocks.LEAVES2, 1, 1), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 2, 3), new ItemStack(Blocks.LEAVES, 1), new ItemStack(Blocks.LEAVES, 1, 1), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 2, 3), new ItemStack(Blocks.LEAVES2), new ItemStack(Blocks.LEAVES, 1, 1), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 2, 3), new ItemStack(Blocks.LEAVES2), new ItemStack(Blocks.LEAVES, 1), type);
		addRecipe(new ItemStack(Blocks.LEAVES2, 2), new ItemStack(Blocks.LEAVES, 1, 2), new ItemStack(Blocks.LEAVES2, 1), type);
		addRecipe(new ItemStack(Blocks.LEAVES2, 2), new ItemStack(Blocks.LEAVES, 1, 2), new ItemStack(Blocks.LEAVES, 1), type);
		addRecipe(new ItemStack(Blocks.LEAVES2, 2), new ItemStack(Blocks.LEAVES, 1, 2), type);
		addRecipe(new ItemStack(Blocks.LEAVES2, 2, 1), new ItemStack(Blocks.LEAVES, 1, 1), new ItemStack(Blocks.LEAVES, 1, 3), type);
		addRecipe(new ItemStack(Blocks.LEAVES2, 2, 1), new ItemStack(Blocks.LEAVES2, 1, 1), new ItemStack(Blocks.LEAVES, 1), type);
		addRecipe(new ItemStack(Blocks.LEAVES2, 2, 1), new ItemStack(Blocks.LEAVES, 1), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 4, 0), Blocks.VINE, new ItemStack(Blocks.LEAVES, 1, 0), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 4, 1), Blocks.VINE, new ItemStack(Blocks.LEAVES, 1, 1), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 4, 2), Blocks.VINE, new ItemStack(Blocks.LEAVES, 1, 2), type);
		addRecipe(new ItemStack(Blocks.LEAVES, 4, 3), Blocks.VINE, new ItemStack(Blocks.LEAVES, 1, 3), type);
		addRecipe(new ItemStack(Blocks.LEAVES2, 4, 0), Blocks.VINE, new ItemStack(Blocks.LEAVES2, 1, 0), type);
		addRecipe(new ItemStack(Blocks.LEAVES2, 4, 1), Blocks.VINE, new ItemStack(Blocks.LEAVES2, 1, 1), type);
		addRecipe(Items.BED, "treeLeaves", new ItemStack(Blocks.PLANKS, 1), type);

		for (int i = 0; i < 9; i++) {
			switch (i) {
			case 8:
				addRecipe(new ItemStack(Blocks.RED_FLOWER, 2, i), new ItemStack(Blocks.RED_FLOWER, 1, 0), new ItemStack(Blocks.RED_FLOWER, 1, 0), ResearchRecipeType.FLOWER);
				break;
			default:
				addRecipe(new ItemStack(Blocks.RED_FLOWER, 2, i), new ItemStack(Blocks.RED_FLOWER, 1, i + 1), new ItemStack(Blocks.RED_FLOWER, 1, i + 1), ResearchRecipeType.FLOWER);
				break;
			}
		}
		for (int i = 0; i < 16; i++) {
			switch (i) {
			case 15:
				addRecipe(new ItemStack(Items.DYE, 2, i), new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.DYE, 1, 0), ResearchRecipeType.DYE);
				addRecipe(new ItemStack(Blocks.CARPET, 2, i), new ItemStack(Blocks.CARPET, 1, 0), new ItemStack(Blocks.CARPET, 1, 0), ResearchRecipeType.CARPET);
				addRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 2, i), new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 0), new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 0), ResearchRecipeType.CLAY_BLOCK);
				addRecipe(new ItemStack(Blocks.STAINED_GLASS_PANE, 2, i), new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 0), new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 0), ResearchRecipeType.GLASS_PANE);
				addRecipe(new ItemStack(Blocks.STAINED_GLASS, 2, i), new ItemStack(Blocks.STAINED_GLASS, 1, 0), new ItemStack(Blocks.STAINED_GLASS, 1, 0), ResearchRecipeType.GLASS);
				addRecipe(new ItemStack(Blocks.WOOL, 2, i), new ItemStack(Blocks.WOOL, 1, 0), new ItemStack(Blocks.WOOL, 1, 0), ResearchRecipeType.WOOL);
				break;
			default:
				addRecipe(new ItemStack(Items.DYE, 2, i), new ItemStack(Items.DYE, 1, i + 1), new ItemStack(Items.DYE, 1, i + 1), ResearchRecipeType.DYE);
				addRecipe(new ItemStack(Blocks.CARPET, 2, i), new ItemStack(Blocks.CARPET, 1, i + 1), new ItemStack(Blocks.CARPET, 1, i + 1), ResearchRecipeType.CARPET);
				addRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 2, i), new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, i + 1), new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, i + 1), ResearchRecipeType.CLAY_BLOCK);
				addRecipe(new ItemStack(Blocks.STAINED_GLASS_PANE, 2, i), new ItemStack(Blocks.STAINED_GLASS_PANE, 1, i + 1), new ItemStack(Blocks.STAINED_GLASS_PANE, 1, i + 1), ResearchRecipeType.GLASS_PANE);
				addRecipe(new ItemStack(Blocks.STAINED_GLASS, 2, i), new ItemStack(Blocks.STAINED_GLASS, 1, i + 1), new ItemStack(Blocks.STAINED_GLASS, 1, i + 1), ResearchRecipeType.GLASS);
				addRecipe(new ItemStack(Blocks.WOOL, 2, i), new ItemStack(Blocks.WOOL, 1, i + 1), new ItemStack(Blocks.WOOL, 1, i + 1), ResearchRecipeType.WOOL);

				break;
			}
		} // foliage
		type = ResearchRecipeType.FOLIAGE;
		addRecipe(new ItemStack(Items.REEDS, 1), new ItemStack(Blocks.GRASS, 1), "treeSapling", type);
		for (int i = 0; i < 9; i++) {
			addRecipe(new ItemStack(Blocks.VINE, 16), new ItemStack(Blocks.RED_FLOWER, 1, i), "treeSapling", type);
		}
		for (int i = 0; i < 6; i++) {
			switch (i) {
			default:
				addRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 2, i), new ItemStack(Blocks.DOUBLE_PLANT, 1, i + 1), new ItemStack(Blocks.DOUBLE_PLANT, 1, i + 1), type);
				break;
			case 1:
				addRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 2, i), new ItemStack(Blocks.TALLGRASS, 1, 1), new ItemStack(Blocks.TALLGRASS, 1, 1), type);
				break;
			case 2:
				addRecipe(new ItemStack(Blocks.TALLGRASS, 2, 1), new ItemStack(Blocks.TALLGRASS, 1, 2), new ItemStack(Blocks.TALLGRASS, 1, 2), type);
				break;
			case 3:
				addRecipe(new ItemStack(Blocks.TALLGRASS, 2, 2), new ItemStack(Blocks.DOUBLE_PLANT, 1, i + 1), new ItemStack(Blocks.DOUBLE_PLANT, 1, i + 1), type);
				break;
			case 5:
				addRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 2, i), new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), type);
				break;
			}
		}
	}

    @Override
	public boolean reverseRecipes() {
		return true;
	}

	public boolean enableResearchRecipes() {
		return false;//change as required...
	}

	@Override
	public String getRecipeID() {
		return "Calculator";
	}

    @Override
	public CalculatorRecipe buildRecipe(ArrayList<ISonarRecipeObject> recipeInputs, ArrayList<ISonarRecipeObject> recipeOutputs, List additionals, boolean shapeless) {
		ResearchRecipeType type = additionals.isEmpty() ? ResearchRecipeType.NONE : (ResearchRecipeType) additionals.get(0);
		if (!enableResearchRecipes() && type != ResearchRecipeType.NONE) {
			return null;
		}
		return new CalculatorRecipe(recipeInputs, recipeOutputs, type, shapeless);
	}
}