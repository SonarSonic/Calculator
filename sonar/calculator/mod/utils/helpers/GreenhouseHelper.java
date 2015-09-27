package sonar.calculator.mod.utils.helpers;

import com.InfinityRaider.AgriCraft.api.v1.APIv1;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import cpw.mods.fml.common.registry.GameRegistry;

/** helps with using bonemeal on crops, growth speed and replacing blocks */
public class GreenhouseHelper {
	/**
	 * @param crop you wish to grow
	 * @return if it was grown
	 */
	public static boolean applyBonemeal(World world, int x, int y, int z, boolean magic) {
		Block block = world.getBlock(x, y, z);

		if (block instanceof IGrowable || Calculator.getAgricraftAPI() !=null && ((APIv1) Calculator.getAgricraftAPI()).canGrow(world, x, y, z)) {
			IGrowable igrowable = (IGrowable) block;

			if (igrowable.func_149851_a(world, x, y, z, world.isRemote)) {
				if (!world.isRemote) {
					if (igrowable.func_149852_a(world, world.rand, x, y, z)) {
						igrowable.func_149853_b(world, world.rand, x, y, z);
					}

				}

				if (magic && !world.isRemote) {
					if (GameRegistry.findUniqueIdentifierFor(block).modId.matches("magicalcrops")) {
						int random = (int) (Math.random() * ((4)));
						if (random == 3) {
							world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) + 1, 2);
						}
					}
				}

				return true;
			}else if (Calculator.getAgricraftAPI() !=null){
				return ((APIv1) Calculator.getAgricraftAPI()).applyFertilizer(world, x, y, z, new ItemStack(Items.dye, 1, 15));
			}
		}

		return false;
	}

	/**
	 * @param oxygen current Green House oxygen
	 * @param type Greenhouse Type - 1=Basic, 2=Advanced, 3=Flawless
	 * @return if it was grown
	 */
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

	/**
	 * change block to farmland
	 * 
	 * @param x xCoord you wish to change
	 * @param y yCoord you wish to change
	 * @param z zCoord you wish to change
	 * @param world world object
	 * @return if it was changed
	 */
	public static boolean applyFarmland(World world, int x, int y, int z) {
		if (world.getBlock(x, y - 1, z) == Blocks.dirt && world.getBlock(x, y, z) == Blocks.air) {
			world.setBlock(x, y - 1, z, Blocks.farmland);
			return true;
		} else if (world.getBlock(x, y - 1, z) == Blocks.grass && world.getBlock(x, y, z) == Blocks.air) {
			world.setBlock(x, y - 1, z, Blocks.farmland);
			return true;
		}
		return false;
	}

	/**
	 * change block to water
	 * 
	 * @param world world object
	 * @param x xCoord you wish to change
	 * @param y yCoord you wish to change
	 * @param z zCoord you wish to change
	 * @return if it was changed
	 */
	public static boolean applyWater(World world, int x, int y, int z) {
		if (world.getBlock(x, y - 1, z) == Blocks.dirt) {
			world.setBlock(x, y - 1, z, Blocks.water);
			return true;
		} else if (world.getBlock(x, y - 1, z) == Blocks.grass) {
			world.setBlock(x, y - 1, z, Blocks.water);
			return true;
		}

		return false;
	}

	/**
	 * can block be replaced
	 * 
	 * @param world world object
	 * @param x xCoord you wish to check
	 * @param y yCoord you wish to check
	 * @param z zCoord you wish to check
	 */
	public static boolean r(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		if (block == null) {
			return true;
		} else if (block == Blocks.air) {
			return true;
		} else if (block == Blocks.dirt) {
			return true;
		} else if (block == Blocks.grass) {
			return true;
		} else if (block == Blocks.cobblestone) {
			return true;
		} else if (block == Blocks.stone) {
			return true;
		} else if (block instanceof BlockFlower) {
			return true;
		} else if (block instanceof BlockDoublePlant) {
			return true;
		} else if (block == Blocks.snow_layer) {
			return true;
		} else if (block == Blocks.netherrack) {
			return true;
		} else if (block == Blocks.tallgrass) {
			return true;
		} else if (block == Blocks.vine) {
			return true;
		} else if (block == Blocks.deadbush) {
			return true;
		}
		if (block.isReplaceable(world, x, y, z)) {
			return true;
		}

		return false;
	}

	/**
	 * @return if the give block is Stable Stone
	 * @param block block to check
	 */
	public static boolean stableStone(Block block) {
		if (block == Calculator.stablestoneBlock) {
			return false;
		}
		if (block == Calculator.flawlessGreenhouse) {
			return false;
		}
		if (block == Calculator.carbondioxideGenerator) {
			return false;
		}
		return true;
	}

	/**
	 * @return if the give block is Flawless Glass
	 * @param block block to check
	 */
	public static boolean flawlessGlass(Block block) {
		if (block == Calculator.flawlessGlass) {
			return false;
		}
		return true;
	}

	public static boolean slabQuartz(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		if (block == Blocks.stone_slab) {
			if (block.getDamageValue(world, x, y, z) == 7) {
				return false;
			}
		}
		return true;
	}

}
