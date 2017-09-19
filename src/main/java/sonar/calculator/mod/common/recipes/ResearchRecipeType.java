package sonar.calculator.mod.common.recipes;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sonar.core.SonarCore;

public enum ResearchRecipeType {
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
	WOOL(Blocks.WOOL, "wool"),

	NONE();

	public Object[] items;

	ResearchRecipeType(Object... items) {
		this.items = items;
	}

	public static ArrayList<ResearchRecipeType> getUnlocked(ItemStack stack) {
        ArrayList<ResearchRecipeType> unlocked = new ArrayList<>();
		int[] ids = OreDictionary.getOreIDs(stack);
		for (ResearchRecipeType type : ResearchRecipeType.values()) {
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