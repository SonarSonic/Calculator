package sonar.calculator.mod.utils.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.core.SonarCore;

/** helps with using bonemeal on crops, growth speed and replacing blocks */
public class GreenhouseHelper {
	/** @param crop you wish to grow
	 * @return if it was grown */
	public static boolean applyBonemeal(World world, BlockPos pos, boolean magic) {
		//Block block = world.getBlock(x, y, z);
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if (block instanceof IGrowable) {
			IGrowable igrowable = (IGrowable) block;

			if (igrowable.canGrow(world, pos, state, world.isRemote)) {
				if (!world.isRemote) {
					if (igrowable.canUseBonemeal(world, world.rand, pos, state)) {
						igrowable.grow(world, world.rand, pos, state);
					}

				}

				/*
				if (magic && !world.isRemote) {
					if (GameRegistry.findUniqueIdentifierFor(block).modId.matches("magicalcrops")) {
						int random = (int) (Math.random() * ((4)));
						if (random == 3) {
							world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) + 1, 2);
						}
					}
				}
				*/
				return true;
			}
		}

		return false;
	}

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
		} else if (world.isAirBlock(pos)) {
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
		if (block.isReplaceable(world, pos)) {
			return true;
		}

		return false;
	}

	/** @return if the give block is Stable Stone
	 * @param block block to check */
	public static boolean stableStone(Block block) {
		if (block == SonarCore.stableStone) {
			return false;
		}
		if (block == SonarCore.stablestonerimmedBlock) {
			return false;
		}
		if (block == SonarCore.stablestonerimmedblackBlock) {
			return false;
		}
		if (block == Calculator.flawlessGreenhouse) {
			return false;
		}
		if (block == Calculator.CO2Generator) {
			return false;
		}
		return true;
	}

	/** @return if the give block is Flawless Glass
	 * @param block block to check */
	public static boolean flawlessGlass(Block block) {
		if (block == Calculator.flawlessGlass) {
			return false;
		}
		return true;
	}

	public static boolean slabQuartz(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == Blocks.STONE_SLAB) {
			if (state.getBlock().getMetaFromState(state) == 7) {
				return false;
			}
		}
		return true;
	}

}
