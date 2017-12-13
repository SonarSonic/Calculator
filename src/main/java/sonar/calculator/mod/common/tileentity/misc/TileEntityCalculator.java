package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import sonar.calculator.mod.client.gui.calculators.GuiAtomicCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiDynamicCalculator;
import sonar.calculator.mod.common.containers.ContainerAtomicCalculator;
import sonar.calculator.mod.common.containers.ContainerDynamicCalculator;
import sonar.core.api.blocks.IStableBlock;
import sonar.core.api.blocks.IStableGlass;
import sonar.core.api.utils.BlockCoords;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.inventory.IDropInventory;
import sonar.core.inventory.SonarInventory;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.IGuiTile;

public abstract class TileEntityCalculator extends TileEntityInventory implements ISidedInventory, IGuiTile {

	public static class Dynamic extends TileEntityCalculator implements IDropInventory {

		public int[] dropSlots = new int[] { 0, 1, 3, 4, 6, 7, 8 };

		public Dynamic() {
			super.inv = new SonarInventory(this, 10);
			syncList.addPart(inv);
		}

		public FailedCoords checkStructure() {
			EnumFacing forward = this.world.getBlockState(pos).getValue(SonarBlock.FACING).getOpposite();
			BlockPos centre = pos.add(forward.getFrontOffsetX() * 3, 0, forward.getFrontOffsetZ() * 3);

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
						if (!(this.world.getBlockState(current).getBlock() instanceof IStableBlock)) {
							return new FailedCoords(false, new BlockCoords(current, world.provider.getDimension()), "stable");
						}
					} else if (!(this.world.getBlockState(current).getBlock() instanceof IStableGlass)) {
						return new FailedCoords(false, new BlockCoords(current, world.provider.getDimension()), "glass");
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
									if (!(this.world.getBlockState(current).getBlock() instanceof IStableBlock)) {
										return new FailedCoords(false, new BlockCoords(current, world.provider.getDimension()), "stable");
									}
								} else if (!(this.world.getBlockState(current).getBlock() instanceof IStableGlass)) {

									return new FailedCoords(false, new BlockCoords(current, world.provider.getDimension()), "glass");
								}
							}
						} else if (!(this.world.getBlockState(current).getBlock() == Blocks.AIR)) {
							return new FailedCoords(false, new BlockCoords(current, world.provider.getDimension()), "air");
						}
					}
				}
			}
			return new FailedCoords(true, BlockCoords.EMPTY, null);
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerDynamicCalculator(player, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiDynamicCalculator(player, this);
		}

		@Override
		public int[] dropSlots() {
			return dropSlots;
		}

		@Override
		public boolean canDrop() {
			return true;
		}
	}

	public static class Atomic extends TileEntityCalculator implements IDropInventory {

		public int[] dropSlots = new int[] { 0, 1, 2};
		
		public Atomic() {
			super.inv = new SonarInventory(this, 4);
			syncList.addPart(inv);
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerAtomicCalculator(player, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiAtomicCalculator(player, this);
		}

		@Override
		public int[] dropSlots() {
			return dropSlots;
		}

		@Override
		public boolean canDrop() {
			return true;
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
