package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.api.blocks.IStableBlock;
import sonar.calculator.mod.api.blocks.IStableGlass;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.helpers.SonarHelper;
import sonar.core.utils.FailedCoords;

public class TileEntityCalculator extends TileEntityInventory implements ISidedInventory {

	public static class Dynamic extends TileEntityCalculator {
		public Dynamic() {
			super.slots = new ItemStack[10];
		}

		public FailedCoords checkStructure() {
			ForgeDirection forward = SonarHelper.getForward(this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord));

			int x = xCoord + (forward.offsetX * 3);
			int y = yCoord;
			int z = zCoord + (forward.offsetZ * 3);

			FailedCoords bottom = this.outsideLayer(x, y - 3, z);
			if (!bottom.getBoolean()) {
				return bottom;
			}
			FailedCoords top = this.outsideLayer(x, y + 3, z);
			if (!top.getBoolean()) {
				return top;
			}
			FailedCoords middle = this.insideLayers(x, y, z);
			if (!middle.getBoolean()) {
				return middle;
			}
			return new FailedCoords(true, 0, 0, 0, null);

		}

		public FailedCoords outsideLayer(int x, int y, int z) {
			for (int X = -3; X <= 3; X++) {
				for (int Z = -3; Z <= 3; Z++) {
					if (X == 3 || Z == 3 || X == -3 || Z == -3) {
						if (!(this.worldObj.getBlock(x + X, y, z + Z) instanceof IStableBlock)) {
							return new FailedCoords(false, x + X, y, z + Z, "stable");
						}
					} else if (!(this.worldObj.getBlock(x + X, y, z + Z) instanceof IStableGlass)) {
						return new FailedCoords(false, x + X, y, z + Z, "glass");
					}

				}
			}
			return new FailedCoords(true, 0, 0, 0, null);
		}

		public FailedCoords insideLayers(int x, int y, int z) {
			for (int Y = -2; Y <= 2; Y++) {

				for (int X = -3; X <= 3; X++) {
					for (int Z = -3; Z <= 3; Z++) {
						if (X == 3 || Z == 3 || X == -3 || Z == -3) {
							if (!(xCoord == x + X) || !(yCoord == y + Y) || !(zCoord == z + Z)) {
								if (X == 3 && Z == 3 || X == -3 && Z == -3 || X == -3 && Z == 3 || X == 3 && Z == -3) {
									if (!(this.worldObj.getBlock(x + X, y + Y, z + Z) instanceof IStableBlock)) {
										return new FailedCoords(false, x + X, y + Y, z + Z, "stable");
									}
								} else if (!(this.worldObj.getBlock(x + X, y + Y, z + Z) instanceof IStableGlass)) {

									return new FailedCoords(false, x + X, y + Y, z + Z, "glass");
								}
							}
						} else if (!(this.worldObj.getBlock(x + X, y + Y, z + Z) == Blocks.air)) {
							return new FailedCoords(false, x + X, y + Y, z + Z, "air");
						}

					}
				}
			}
			return new FailedCoords(true, 0, 0, 0, null);
		}
	}

	public static class Atomic extends TileEntityCalculator {
		public Atomic() {
			super.slots = new ItemStack[4];
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return false;
	}
}
