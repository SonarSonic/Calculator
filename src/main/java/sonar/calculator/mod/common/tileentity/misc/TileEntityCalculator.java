package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.api.blocks.IStableBlock;
import sonar.calculator.mod.api.blocks.IStableGlass;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.inventory.SonarTileInventory;
import sonar.core.utils.BlockCoords;
import sonar.core.utils.FailedCoords;

public class TileEntityCalculator extends TileEntityInventory implements ISidedInventory {

	public static class Dynamic extends TileEntityCalculator {
		public Dynamic() {
			super.inv = new SonarTileInventory(this, 10);
		}

		public FailedCoords checkStructure() {
			EnumFacing forward = this.worldObj.getBlockState(pos).getValue(SonarBlock.FACING).getOpposite();
			BlockPos centre = pos.add((forward.getFrontOffsetX() * 3), 0, (forward.getFrontOffsetZ() * 3));

			FailedCoords bottom = this.outsideLayer(centre.offset(EnumFacing.DOWN, 3));
			if (!bottom.getBoolean()) {
				return bottom;
			}
			FailedCoords top = this.outsideLayer(centre.offset(EnumFacing.UP, 3));
			if (!top.getBoolean()) {
				return top;
			}
			FailedCoords middle = this.insideLayers(centre);
			if (!middle.getBoolean()) {
				return middle;
			}
			return new FailedCoords(true, BlockCoords.EMPTY, null);

		}

		public FailedCoords outsideLayer(BlockPos pos) {
			for (int X = -3; X <= 3; X++) {
				for (int Z = -3; Z <= 3; Z++) {
					BlockPos current = pos.add(X, 0, Z);
					if (X == 3 || Z == 3 || X == -3 || Z == -3) {
						if (!(this.worldObj.getBlockState(current).getBlock() instanceof IStableBlock)) {
							return new FailedCoords(false, new BlockCoords(current, worldObj.provider.getDimensionId()), "stable");
						}
					} else if (!(this.worldObj.getBlockState(current).getBlock() instanceof IStableGlass)) {
						return new FailedCoords(false, new BlockCoords(current, worldObj.provider.getDimensionId()), "glass");
					}

				}
			}
			return new FailedCoords(true, BlockCoords.EMPTY, null);
		}

		public FailedCoords insideLayers(BlockPos pos) {
			for (int Y = -2; Y <= 2; Y++) {

				for (int X = -3; X <= 3; X++) {
					for (int Z = -3; Z <= 3; Z++) {
						BlockPos current = pos.add(X, Y, Z);
						if (X == 3 || Z == 3 || X == -3 || Z == -3) {
							if (!this.pos.equals(current)) {
								if (X == 3 && Z == 3 || X == -3 && Z == -3 || X == -3 && Z == 3 || X == 3 && Z == -3) {
									if (!(this.worldObj.getBlockState(current).getBlock() instanceof IStableBlock)) {
										return new FailedCoords(false, new BlockCoords(current, worldObj.provider.getDimensionId()), "stable");
									}
								} else if (!(this.worldObj.getBlockState(current).getBlock() instanceof IStableGlass)) {

									return new FailedCoords(false, new BlockCoords(current, worldObj.provider.getDimensionId()), "glass");
								}
							}
						} else if (!(this.worldObj.getBlockState(current).getBlock() == Blocks.air)) {
							return new FailedCoords(false, new BlockCoords(current, worldObj.provider.getDimensionId()), "air");
						}

					}
				}
			}
			return new FailedCoords(true, BlockCoords.EMPTY, null);
		}
	}

	public static class Atomic extends TileEntityCalculator {
		public Atomic() {
			super.inv = new SonarTileInventory(this,4);
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}
}
