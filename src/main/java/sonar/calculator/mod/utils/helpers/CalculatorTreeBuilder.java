package sonar.calculator.mod.utils.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import sonar.calculator.mod.common.block.CalculatorLeaves;
import sonar.calculator.mod.common.block.CalculatorLogs;
import sonar.calculator.mod.common.block.CalculatorSaplings;

import java.util.Random;

public class CalculatorTreeBuilder extends WorldGenAbstractTree {
	private final int minTreeHeight;
	private final boolean vinesGrow;
	private final int metaWood;
	private final int metaLeaves;
	private CalculatorSaplings sapling;
	private CalculatorLeaves blockLeaves;
	private CalculatorLogs blockLog;
	private static Random rand = new Random();

	public CalculatorTreeBuilder(boolean par1, Block sapling, Block blockLeaves, Block blockLog) {
		this(par1, 4 + rand.nextInt(2), 3, 3, false, (CalculatorSaplings) sapling, (CalculatorLeaves) blockLeaves, (CalculatorLogs) blockLog);
	}

	public CalculatorTreeBuilder(boolean par1, int height, int wood, int leaves, boolean vines, CalculatorSaplings sapling, CalculatorLeaves blockLeaves, CalculatorLogs blockLog) {
		super(par1);
		this.minTreeHeight = height;
		this.metaWood = wood;
		this.metaLeaves = leaves;
		this.vinesGrow = vines;
		this.sapling = sapling;
		this.blockLeaves = blockLeaves;
		this.blockLog = blockLog;
	}

    @Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		int l = rand.nextInt(3) + this.minTreeHeight;
		boolean flag = true;

		if (pos.getY() >= 1 && pos.getY() + l + 1 <= 256) {
			byte b0;
			int k1;
			Block block;

			for (int i1 = pos.getY(); i1 <= pos.getY() + 1 + l; ++i1) {
				b0 = 1;

				if (i1 == pos.getY()) {
					b0 = 0;
				}

				if (i1 >= pos.getY() + 1 + l - 2) {
					b0 = 2;
				}

				for (int j1 = pos.getX() - b0; j1 <= pos.getX() + b0 && flag; ++j1) {
					for (k1 = pos.getZ() - b0; k1 <= pos.getZ() + b0 && flag; ++k1) {
						if (i1 >= 0 && i1 < 256) {
							block = world.getBlockState(new BlockPos(j1, i1, k1)).getBlock();

							if (!isReplaceable(world, new BlockPos(j1, i1, k1))) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}

			if (!flag) {
				return false;
			} else {
				IBlockState state2 = world.getBlockState(pos.offset(EnumFacing.DOWN));
				Block block2 = state2.getBlock();
				boolean isSoil = block2.canSustainPlant(state2, world, pos.offset(EnumFacing.DOWN), EnumFacing.UP, sapling);
				if (isSoil && pos.getY() < 256 - l - 1) {
					block2.onPlantGrow(state2, world, pos.offset(EnumFacing.DOWN), pos);
					b0 = 3;
					byte b1 = 0;
					int l1;
					int i2;
					int j2;
					int i3;

					for (k1 = pos.getY() - b0 + l; k1 <= pos.getY() + l; ++k1) {
						i3 = k1 - (pos.getY() + l);
						l1 = b1 + 1 - i3 / 2;

						for (i2 = pos.getX() - l1; i2 <= pos.getX() + l1; ++i2) {
							j2 = i2 - pos.getX();

							for (int k2 = pos.getZ() - l1; k2 <= pos.getZ() + l1; ++k2) {
								int l2 = k2 - pos.getZ();

								if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || rand.nextInt(2) != 0 && i3 != 0) {
									IBlockState state1 = world.getBlockState(new BlockPos(i2, k1, k2));
									Block block1 = state1.getBlock();
									if (block1.isAir(state1, world, new BlockPos(i2, k1, k2)) || block1.isLeaves(state1, world, new BlockPos(i2, k1, k2))) {
										world.setBlockState(new BlockPos(i2, k1, k2), blockLeaves.getDefaultState());
									}
								}
							}
						}
					}

					for (k1 = 0; k1 < l; ++k1) {
						IBlockState offset = world.getBlockState(pos.add(0, k1, 0));
						block = offset.getBlock();
						if (block.isAir(offset, world, pos.add(0, k1, 0)) || block.isLeaves(offset, world, pos.add(0, k1, 0))) {
							world.setBlockState(pos.add(0, k1, 0), blockLog.getDefaultState());
						}
					}
					if (sapling.type == 3)
						world.setBlockState(pos.offset(EnumFacing.DOWN), Blocks.GRASS.getDefaultState());
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}
}
