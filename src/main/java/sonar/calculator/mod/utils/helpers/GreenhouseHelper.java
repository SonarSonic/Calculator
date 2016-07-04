package sonar.calculator.mod.utils.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.Calculator;
import sonar.core.SonarCore;
import sonar.core.common.block.StableStone;

/** helps with using bonemeal on crops, growth speed and replacing blocks */
public class GreenhouseHelper {
	
	/** @param oxygen current Green House oxygen
	 * @param type Greenhouse Type - 1=Basic, 2=Advanced, 3=Flawless
	 * @return if it was grown */
	public static int getGrowTicks(int oxygen, int type) {
		if (type == 1) {
			if (oxygen >= 90000) {
				return 400;
			} else if (oxygen >= 50000) {
				return 300;
			} else if (oxygen >= 30000) {
				return 200;
			} else if (oxygen >= 10000) {
				return 150;
			} else {
				return 80;
			}
		}
		if (type == 2) {
			if (oxygen >= 90000) {
				return 300;
			} else if (oxygen >= 50000) {
				return 200;
			} else if (oxygen >= 30000) {
				return 100;
			} else if (oxygen >= 10000) {
				return 50;
			} else {
				return 15;
			}
		}

		if (type == 3) {
			if (oxygen >= 90000) {
				return 200;
			} else if (oxygen >= 50000) {
				return 100;
			} else if (oxygen >= 30000) {
				return 50;
			} else if (oxygen >= 10000) {
				return 25;
			} else {
				return 15;
			}
		}
		return 1000;
	}

	/** change block to farmland
	 * 
	 * @param x xCoord you wish to change
	 * @param y yCoord you wish to change
	 * @param z zCoord you wish to change
	 * @param world world object
	 * @return if it was changed */
	public static boolean applyFarmland(World world, BlockPos pos) {
		Block target = world.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock();
		if (target == Blocks.DIRT) {
			world.setBlockState(pos.offset(EnumFacing.DOWN), Blocks.FARMLAND.getDefaultState(), 3);
			return true;
		} else if (target == Blocks.GRASS) {
			world.setBlockState(pos.offset(EnumFacing.DOWN), Blocks.FARMLAND.getDefaultState(), 3);
			return true;
		}
		return false;
	}

	/** change block to water
	 * 
	 * @param world world object
	 * @param x xCoord you wish to change
	 * @param y yCoord you wish to change
	 * @param z zCoord you wish to change
	 * @return if it was changed */
	public static boolean applyWater(World world, BlockPos pos) {
		Block target = world.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock();
		if (target == Blocks.DIRT) {
			world.setBlockState(pos.offset(EnumFacing.DOWN), Blocks.WATER.getDefaultState(), 3);
			return true;
		} else if (target == Blocks.GRASS) {
			world.setBlockState(pos.offset(EnumFacing.DOWN), Blocks.WATER.getDefaultState(), 3);
			return true;
		}

		return false;
	}

	/** can block be replaced
	 * 
	 * @param world world object
	 * @param x xCoord you wish to check
	 * @param y yCoord you wish to check
	 * @param z zCoord you wish to check */
	public static boolean r(World world, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		if (block == null) {
			return true;
		} 
		if (block.isReplaceable(world, pos)) {
			return true;
		}else if (world.isAirBlock(pos)) {
			return true;
		} else if (block == Blocks.AIR) {
			return true;
		} else if (block == Blocks.DIRT) {
			return true;
		} else if (block == Blocks.GRASS) {
			return true;
		} else if (block == Blocks.COBBLESTONE) {
			return true;
		} else if (block == Blocks.STONE) {
			return true;
		} else if (block instanceof BlockFlower) {
			return true;
		} else if (block instanceof BlockDoublePlant) {
			return true;
		} else if (block == Blocks.SNOW_LAYER) {
			return true;
		} else if (block == Blocks.NETHERRACK) {
			return true;
		} else if (block == Blocks.TALLGRASS) {
			return true;
		} else if (block == Blocks.VINE) {
			return true;
		} else if (block == Blocks.DEADBUSH) {
			return true;
		}
		return false;
	}

	/** @return if the give block is Stable Stone
	 * @param block block to check */
	public static boolean stableStone(Block block) {
		if (block instanceof StableStone) {
			return true;
		}
		if (block == Calculator.flawlessGreenhouse) {
			return true;
		}
		if (block == Calculator.CO2Generator) {
			return true;
		}
		return false;
	}

	/** @return if the give block is Flawless Glass
	 * @param block block to check */
	public static boolean flawlessGlass(Block block) {
		if (block == Calculator.flawlessGlass) {
			return true;
		}
		return false;
	}

	public static boolean slabQuartz(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == Blocks.STONE_SLAB) {
			if (state.getBlock().getMetaFromState(state) == 7) {
				return true;
			}
		}
		return false;
	}

	/** checks ore dictionary for registered logs **/
	public static boolean checkLog(Block block) {

		for (int i = 0; i < OreDictionary.getOres("logWood").size(); i++) {
			if (OreDictionary.getOres("logWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("treeWood").size(); i++) {
			if (OreDictionary.getOres("treeWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		if (block instanceof BlockLog) {
			return true;
		}
		return false;
	}

	/** checks ore dictionary for registered glass **/
	public static boolean checkGlass(Block block) {

		for (int i = 0; i < OreDictionary.getOres("blockGlass").size(); i++) {
			if (OreDictionary.getOres("blockGlass").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("blockGlassColorless").size(); i++) {
			if (OreDictionary.getOres("blockGlassColorless").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("paneGlassColorless").size(); i++) {
			if (OreDictionary.getOres("paneGlassColorless").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("paneGlass").size(); i++) {
			if (OreDictionary.getOres("paneGlass").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		if (block instanceof BlockGlass) {
			return true;
		}
		if (block instanceof BlockPane) {
			return true;
		}
		return false;
	}

	/** checks ore dictionary for registered stairs **/
	public static boolean checkStairs(Block block) {

		for (int i = 0; i < OreDictionary.getOres("stairWood").size(); i++) {
			if (OreDictionary.getOres("stairWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("stairStone").size(); i++) {
			if (OreDictionary.getOres("stairStone").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("greenhouse.stairs").size(); i++) {
			if (OreDictionary.getOres("stairs").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		if (block instanceof BlockStairs) {
			return true;
		}
		if (block == Blocks.STONE_STAIRS) {
			return true;
		}
		if (block == Blocks.STONE_BRICK_STAIRS) {
			return true;
		}
		if (block == Blocks.SANDSTONE_STAIRS) {
			return true;
		}
		if (block == Blocks.BRICK_STAIRS) {
			return true;
		}
		if (block == Blocks.QUARTZ_STAIRS) {
			return true;
		}
		if (block == Blocks.NETHER_BRICK_STAIRS) {
			return true;
		}
		return false;
	}


	/** checks ore dictionary for registered planks **/
	public static boolean checkPlanks(Block block) {

		for (int i = 0; i < OreDictionary.getOres("plankWood").size(); i++) {
			if (OreDictionary.getOres("plankWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("planksWood").size(); i++) {
			if (OreDictionary.getOres("planksWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		if (block instanceof BlockLog) {
			return true;
		}
		return false;
	}

	public static boolean applyBonemeal(World worldObj, BlockPos add, boolean b) {
		
		return false;
	}
}
