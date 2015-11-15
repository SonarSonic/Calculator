package sonar.calculator.mod.utils.helpers;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.common.block.CalculatorLeaves;
import sonar.calculator.mod.common.block.CalculatorLogs;
import sonar.calculator.mod.common.block.CalculatorSaplings;

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

	public boolean generate(World world, Random rand, int x, int y, int z) {
		int l = rand.nextInt(3) + this.minTreeHeight;
		boolean flag = true;

		if (y >= 1 && y + l + 1 <= 256) {
			byte b0;
			int k1;
			Block block;

			for (int i1 = y; i1 <= y + 1 + l; ++i1) {
				b0 = 1;

				if (i1 == y) {
					b0 = 0;
				}

				if (i1 >= y + 1 + l - 2) {
					b0 = 2;
				}

				for (int j1 = x - b0; j1 <= x + b0 && flag; ++j1) {
					for (k1 = z - b0; k1 <= z + b0 && flag; ++k1) {
						if (i1 >= 0 && i1 < 256) {
							block = world.getBlock(j1, i1, k1);

							if (!this.isReplaceable(world, j1, i1, k1)) {
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
				Block block2 = world.getBlock(x, y - 1, z);

				boolean isSoil = block2.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, sapling);
				if (isSoil && y < 256 - l - 1) {
					block2.onPlantGrow(world, x, y - 1, z, x, y, z);
					b0 = 3;
					byte b1 = 0;
					int l1;
					int i2;
					int j2;
					int i3;

					for (k1 = y - b0 + l; k1 <= y + l; ++k1) {
						i3 = k1 - (y + l);
						l1 = b1 + 1 - i3 / 2;

						for (i2 = x - l1; i2 <= x + l1; ++i2) {
							j2 = i2 - x;

							for (int k2 = z - l1; k2 <= z + l1; ++k2) {
								int l2 = k2 - z;

								if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || rand.nextInt(2) != 0 && i3 != 0) {
									Block block1 = world.getBlock(i2, k1, k2);

									if (block1.isAir(world, i2, k1, k2) || block1.isLeaves(world, i2, k1, k2)) {
										world.setBlock(i2, k1, k2, blockLeaves);
									}
								}
							}
						}
					}

					for (k1 = 0; k1 < l; ++k1) {
						block = world.getBlock(x, y + k1, z);

						if (block.isAir(world, x, y + k1, z) || block.isLeaves(world, x, y + k1, z)) {
							world.setBlock(x, y + k1, z, blockLog);

						}
					}

					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	private void growVines(World world, int x, int y, int z, int flag) {
		setBlockAndNotifyAdequately(world, x, y, z, Blocks.vine, flag);
		int i1 = 4;
		for (;;) {
			y--;
			if ((world.getBlock(x, y, z).isAir(world, x, y, z)) || (i1 <= 0)) {
				return;
			}
			setBlockAndNotifyAdequately(world, x, y, z, Blocks.vine, flag);
			i1--;
		}
	}
}
