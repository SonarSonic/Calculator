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
		this(par1, 2 + rand.nextInt(2) * 2, 0, 0, false, (CalculatorSaplings) sapling, (CalculatorLeaves) blockLeaves, (CalculatorLogs) blockLog);

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
	public boolean generate(World world, Random random, int wood, int leaves, int vines) {
		int l = random.nextInt(1) + this.minTreeHeight;
		boolean flag = true;
		if ((leaves >= 1) && (leaves + l + 1 <= 256)) {
			for (int i1 = leaves; i1 <= leaves + 1 + l; i1++) {
				byte b0 = 1;
				if (i1 == leaves) {
					b0 = 0;
				}
				if (i1 >= leaves + 1 + l - 2) {
					b0 = 2;
				}
				for (int j1 = wood - b0; (j1 <= wood + b0) && (flag); j1++) {
					for (int k1 = vines - b0; (k1 <= vines + b0) && (flag); k1++) {
						if ((i1 >= 0) && (i1 < 256)) {
							Block block = world.getBlock(j1, i1, k1);
							if (!isReplaceable(world, j1, i1, k1)) {
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
			}
			Block block2 = world.getBlock(wood, leaves - 1, vines);

			boolean isSoil = block2.canSustainPlant(world, wood, leaves - 1, vines, ForgeDirection.UP, this.sapling);
			if ((isSoil) && (leaves < 256 - l - 1)) {
				block2.onPlantGrow(world, wood, leaves - 1, vines, wood, leaves, vines);
				byte b0 = 3;
				byte b1 = 0;
				for (int k1 = leaves - b0 + l; k1 <= leaves + l; k1++) {
					int i3 = k1 - (leaves + l);
					int l1 = b1 + 1 - i3 / 2;
					for (int i2 = wood - l1; i2 <= wood + l1; i2++) {
						int j2 = i2 - wood;
						for (int k2 = vines - l1; k2 <= vines + l1; k2++) {
							int l2 = k2 - vines;
							if ((Math.abs(j2) != l1) || (Math.abs(l2) != l1) || ((random.nextInt(2) != 0) && (i3 != 0))) {
								Block block1 = world.getBlock(i2, k1, k2);
								if ((block1.isAir(world, i2, k1, k2)) || (block1.isLeaves(world, i2, k1, k2))) {
									setBlockAndNotifyAdequately(world, i2, k1, k2, this.blockLeaves, this.metaLeaves);
								}
							}
						}
					}
				}
				for (b1 = 0; b1 < l; b1 = (byte) (b1 + 1)) {
					Block block = world.getBlock(wood, leaves + b1, vines);
					if ((block.isAir(world, wood, leaves + b1, vines)) || (block.isLeaves(world, wood, leaves + b1, vines))) {
						setBlockAndNotifyAdequately(world, wood, leaves + b1, vines, this.blockLog, this.metaWood);
						if ((this.vinesGrow) && (b1 > 0)) {
							if ((random.nextInt(3) > 0) && (world.isAirBlock(wood - 1, leaves + b1, vines))) {
								setBlockAndNotifyAdequately(world, wood - 1, leaves + b1, vines, Blocks.vine, 8);
							}
							if ((random.nextInt(3) > 0) && (world.isAirBlock(wood + 1, leaves + b1, vines))) {
								setBlockAndNotifyAdequately(world, wood + 1, leaves + b1, vines, Blocks.vine, 2);
							}
							if ((random.nextInt(3) > 0) && (world.isAirBlock(wood, leaves + b1, vines - 1))) {
								setBlockAndNotifyAdequately(world, wood, leaves + b1, vines - 1, Blocks.vine, 1);
							}
							if ((random.nextInt(3) > 0) && (world.isAirBlock(wood, leaves + b1, vines + 1))) {
								setBlockAndNotifyAdequately(world, wood, leaves + b1, vines + 1, Blocks.vine, 4);
							}
						}
					}
				}
				if (this.vinesGrow) {
					for (b1 = (byte) (leaves - 3 + l); b1 <= leaves + l; b1 = (byte) (b1 + 1)) {
						int i3 = b1 - (leaves + l);
						int l1 = 2 - i3 / 2;
						for (int i2 = wood - l1; i2 <= wood + l1; i2++) {
							for (int j2 = vines - l1; j2 <= vines + l1; j2++) {
								if (world.getBlock(i2, b1, j2).isLeaves(world, i2, b1, j2)) {
									if ((random.nextInt(4) == 0) && (world.getBlock(i2 - 1, b1, j2).isAir(world, i2 - 1, b1, j2))) {
										growVines(world, i2 - 1, b1, j2, 8);
									}
									if ((random.nextInt(4) == 0) && (world.getBlock(i2 + 1, b1, j2).isAir(world, i2 + 1, b1, j2))) {
										growVines(world, i2 + 1, b1, j2, 2);
									}
									if ((random.nextInt(4) == 0) && (world.getBlock(i2, b1, j2 - 1).isAir(world, i2, b1, j2 - 1))) {
										growVines(world, i2, b1, j2 - 1, 1);
									}
									if ((random.nextInt(4) == 0) && (world.getBlock(i2, b1, j2 + 1).isAir(world, i2, b1, j2 + 1))) {
										growVines(world, i2, b1, j2 + 1, 4);
									}
								}
							}
						}
					}
					if ((random.nextInt(5) == 0) && (l > 5)) {
						for (b1 = 0; b1 < 2; b1 = (byte) (b1 + 1)) {
							for (int i3 = 0; i3 < 4; i3++) {
								if (random.nextInt(4 - b1) == 0) {
									int l1 = random.nextInt(3);
									setBlockAndNotifyAdequately(world, wood + net.minecraft.util.Direction.offsetX[net.minecraft.util.Direction.rotateOpposite[i3]], leaves + l - 5 + b1, vines + net.minecraft.util.Direction.offsetZ[net.minecraft.util.Direction.rotateOpposite[i3]], Blocks.cocoa,
											l1 << 2 | i3);
								}
							}
						}
					}
				}
				return true;
			}
			return false;
		}
		return false;
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
