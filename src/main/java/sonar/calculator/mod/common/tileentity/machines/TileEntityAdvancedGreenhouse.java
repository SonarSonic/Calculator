package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.machines.GuiAdvancedGreenhouse;
import sonar.calculator.mod.common.containers.ContainerAdvancedGreenhouse;
import sonar.calculator.mod.common.tileentity.TileEntityBuildingGreenhouse;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;
import sonar.core.api.energy.EnergyMode;
import sonar.core.helpers.SonarHelper;
import sonar.core.inventory.SonarInventory;
import sonar.core.utils.IGuiTile;

public class TileEntityAdvancedGreenhouse extends TileEntityBuildingGreenhouse implements ISidedInventory, IGuiTile {

	public int plants, lanterns, checkTicks, growTicks, growTick;

	public int[] logStack = new int[] { 0 };
	public int[] glassStack = new int[] { 4, 5 };
	public int[] plankStack = new int[] { 6 };
	public int[] stairStack = new int[] { 1, 2, 3 };

	public TileEntityAdvancedGreenhouse() {
		super(183, 30, 42, 94);
		super.storage.setCapacity(350000).setMaxTransfer(1600);
		super.inv = new SonarInventory(this, 17);
		super.energyMode = EnergyMode.RECIEVE;
		super.type = 2;
		super.maxLevel = 100000;
		super.plantTick = 10;
		syncList.addPart(inv);
	}

	@Override
	public void update() {
		super.update();
		discharge(7);
	}

	public ArrayList<BlockPos> getPlantArea() {
		ArrayList<BlockPos> coords = Lists.newArrayList();
		for (int Z = -3; Z <= 3; Z++) {
			for (int X = -3; X <= 3; X++) {
				coords.add(this.pos.add((forward.getFrontOffsetX() * 4) + X, 0, (forward.getFrontOffsetZ() * 4) + Z));
			}
		}
		return coords;
	}
	
	/** adds gas, depends on day and night **/
	public void gasLevels() {
		boolean day = this.getWorld().isDaytime();
		if (day) {
			int add = (this.plants / 5 * 8) - (this.lanterns * 50);
			this.addGas(-add);
		}
		if (!day) {
			int add = (this.plants / 5 * 2) + (this.lanterns * 50);
			this.addGas(add);
		}
	}

	/** gets plants inside greenhouse and sets it to this.plants **/
	public int getPlants() {
		this.plants = 0;
		for (int Z = -3; Z <= 3; Z++) {
			for (int X = -3; X <= 3; X++) {
				BlockPos pos = this.pos.add((forward.getFrontOffsetX() * 4) + X, 0, (forward.getFrontOffsetZ() * 4) + Z);
				if (this.getWorld().getBlockState(pos).getBlock() instanceof IGrowable) {
					this.plants++;

				}
			}
		}
		return plants;
	}

	/** gets lanterns inside greenhouse and sets it to this.lanterns **/
	public int getLanterns() {
		this.lanterns = 0;
		for (int Z = -3; Z <= 3; Z++) {
			for (int X = -3; X <= 3; X++) {
				for (int Y = 0; Y <= 5; Y++) {
					BlockPos pos = this.pos.add((forward.getFrontOffsetX() * 4) + X, Y, (forward.getFrontOffsetZ() * 4) + Z);
					if (this.getWorld().getBlockState(pos).getBlock() == Calculator.gas_lantern_on) {
						this.lanterns++;
					}
				}
			}
		}
		return lanterns;
	}

	/** Hoes the ground **/
	public void addFarmland() {
		for (int Z = -3; Z <= 3; Z++) {
			for (int X = -3; X <= 3; X++) {
				BlockPos pos = this.pos.add((4 * forward.getFrontOffsetX()) + X, 0, (4 * forward.getFrontOffsetZ()) + Z);
				if ((X == 3 && Z == 3) || (X == -3 && Z == -3) || (X == 3 && Z == -3) || (X == -3 && Z == 3)) {
					if (this.storage.getEnergyStored() >= waterRF) {
						if (GreenhouseHelper.applyWater(getWorld(), pos)) {
							this.storage.modifyEnergyStored(-waterRF);
						}
					}
				} else {
					if (this.storage.getEnergyStored() >= farmlandRF) {
						if (GreenhouseHelper.applyFarmland(getWorld(), pos)) {
							this.storage.modifyEnergyStored(-farmlandRF);
						}
					}
					IBlockState state = getWorld().getBlockState(pos);
					Block block = state.getBlock();
					if (!block.isAir(state, getWorld(), pos) && GreenhouseHelper.r(getWorld(), pos)) {
						getWorld().setBlockToAir(pos);
					}
				}
			}
		}
	}

	public ArrayList<BlockPlace> getStructure() {
		ArrayList<BlockPlace> blocks = Lists.newArrayList();

		int hX = SonarHelper.getHorizontal(forward).getFrontOffsetX();
		int hZ = SonarHelper.getHorizontal(forward).getFrontOffsetZ();

		int hoX = SonarHelper.getHorizontal(forward).getOpposite().getFrontOffsetX();
		int hoZ = SonarHelper.getHorizontal(forward).getOpposite().getFrontOffsetZ();

		int fX = forward.getFrontOffsetX();
		int fZ = forward.getFrontOffsetZ();

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		//end
		for (int i = 1; i <= 6; i++) {
			blocks.add(new BlockPlace(BlockType.LOG, x, y + i, z, -1));
		}

		for (int i = 0; i <= 3; i++) {
			blocks.add(new BlockPlace(BlockType.LOG, x + (hX * 4), y + i, z + (hZ * 4), -1));
			blocks.add(new BlockPlace(BlockType.LOG, x + (hoX * 4), y + i, z + (hoZ * 4), -1));
		}

		for (int i = 1; i <= 3; i++) {
			blocks.add(new BlockPlace(BlockType.PLANKS, x + (hX * i), y - 1, z + (hZ * i), -1));
			blocks.add(new BlockPlace(BlockType.PLANKS, x + (hoX * i), y - 1, z + (hoZ * i), -1));
		}

		for (int j = 1; j <= 3; j++) {
			if (j != 3) {
				for (int i = 0; i <= 5; i++) {
					blocks.add(new BlockPlace(BlockType.GLASS, x + (hX * j), y + i, z + (hZ * j), -1));
					blocks.add(new BlockPlace(BlockType.GLASS, x + (hoX * j), y + i, z + (hoZ * j), -1));
				}
			} else if (j == 3) {
				for (int i = 0; i <= 4; i++) {
					blocks.add(new BlockPlace(BlockType.GLASS, x + (hX * j), y + i, z + (hZ * j), -1));
					blocks.add(new BlockPlace(BlockType.GLASS, x + (hoX * j), y + i, z + (hoZ * j), -1));
				}
			}
		}

		x = pos.getX() + (forward.getFrontOffsetX() * 8);
		y = pos.getY();
		z = pos.getZ() + (forward.getFrontOffsetZ() * 8);
		for (int i = 0; i <= 3; i++) {
			blocks.add(new BlockPlace(BlockType.LOG, x + (hX * 4), y + i, z + (hZ * 4), -1));
			blocks.add(new BlockPlace(BlockType.LOG, x + (hoX * 4), y + i, z + (hoZ * 4), -1));
		}

		for (int i = 0; i <= 5; i++) {
			if (i <= 4) {
				blocks.add(new BlockPlace(BlockType.GLASS, x + (hX * 3), y + i, z + (hZ * 3), -1));
				blocks.add(new BlockPlace(BlockType.GLASS, x + (hoX * 3), y + i, z + (hoZ * 3), -1));
			}
			blocks.add(new BlockPlace(BlockType.GLASS, x + (hX * 2), y + i, z + (hZ * 2), -1));
			blocks.add(new BlockPlace(BlockType.GLASS, x + (hoX * 2), y + i, z + (hoZ * 2), -1));
		}

		for (int i = 0; i <= 6; i++) {
			if (i > 2) {
				blocks.add(new BlockPlace(BlockType.GLASS, x + (hX * 1), y + i, z + (hZ * 1), -1));
				blocks.add(new BlockPlace(BlockType.GLASS, x + (hoX * 1), y + i, z + (hoZ * 1), -1));
				blocks.add(new BlockPlace(BlockType.GLASS, x, y + i, z, -1));
			}
			if (i <= 2) {
				blocks.add(new BlockPlace(BlockType.PLANKS, x + (hX * 1), y + i, z + (hZ * 1), -1));
				blocks.add(new BlockPlace(BlockType.PLANKS, x + (hoX * 1), y + i, z + (hoZ * 1), -1));
			}
			if (i == 2) {
				blocks.add(new BlockPlace(BlockType.PLANKS, x, y + i, z, -1));
			}
		}

		for (int i = 2; i <= 3; i++) {
			blocks.add(new BlockPlace(BlockType.PLANKS, x + (hX * i), y - 1, z + (hZ * i), -1));
			blocks.add(new BlockPlace(BlockType.PLANKS, x + (hoX * i), y - 1, z + (hoZ * i), -1));

		}

		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		//sides
		for (int i = 1; i <= 7; i++) {
			if (i != 4) {
				for (int s = 0; s <= 2; s++) {
					if (s == 0) {
						blocks.add(new BlockPlace(BlockType.PLANKS, x + (hX * 4) + (fX * i), y + s, z + (hZ * 4) + (fZ * i), -1));
						blocks.add(new BlockPlace(BlockType.PLANKS, x + (hoX * 4) + (fX * i), y + s, z + (hoZ * 4) + (fZ * i), -1));
					} else {
						blocks.add(new BlockPlace(BlockType.GLASS, x + (hX * 4) + (fX * i), y + s, z + (hZ * 4) + (fZ * i), -1));
						blocks.add(new BlockPlace(BlockType.GLASS, x + (hoX * 4) + (fX * i), y + s, z + (hoZ * 4) + (fZ * i), -1));
					}
				}
			}
		}

		for (int Y = 0; Y <= 3; Y++) {
			blocks.add(new BlockPlace(BlockType.LOG, x + (hX * 4) + (fX * 4), y + Y, z + (hZ * 4) + (fZ * 4), -1));
			blocks.add(new BlockPlace(BlockType.LOG, x + (hoX * 4) + (fX * 4), y + Y, z + (hoZ * 4) + (fZ * 4), -1));
		}

		//roof		
		blocks.add(new BlockPlace(BlockType.PLANKS, x + (hX * 1), y + 6, z + (hZ * 1), -1));
		blocks.add(new BlockPlace(BlockType.PLANKS, x + (hoX * 1), y + 6, z + (hoZ * 1), -1));

		for (int i = -1; i <= 9; i++) {
			for (int s = 3; s <= 7; s++) {
				blocks.add(new BlockPlace(BlockType.STAIRS, x + (hX * intValues(s, BlockType.STAIRS)) + (fX * i), y + s, z + (hZ * intValues(s, BlockType.STAIRS)) + (fZ * i), type("r")));
				blocks.add(new BlockPlace(BlockType.STAIRS, x + (hoX * intValues(s, BlockType.STAIRS)) + (fX * i), y + s, z + (hoZ * intValues(s, BlockType.STAIRS)) + (fZ * i), type("l")));
				blocks.add(new BlockPlace(BlockType.PLANKS, x + (fX * i), y + 7, z + (fZ * i), -1));

			}
		}

		//under-roof
		for (int i = -1; i <= 9; i++) {
			if (i != 0) {
				if (i != 0 && i != 4 && i != 8) {
					blocks.add(new BlockPlace(BlockType.STAIRS, x + (hX * 4) + (fX * i), y + 3, z + (hZ * 4) + (fZ * i), type("d")));
					blocks.add(new BlockPlace(BlockType.STAIRS, x + (hoX * 4) + (fX * i), y + 3, z + (hoZ * 4) + (fZ * i), type("d2")));
				}
				if (i != 0 && i != 8) {
					blocks.add(new BlockPlace(BlockType.STAIRS, x + (hX * 3) + (fX * i), y + 4, z + (hZ * 3) + (fZ * i), type("d")));
					blocks.add(new BlockPlace(BlockType.STAIRS, x + (hX * 2) + (fX * i), y + 5, z + (hZ * 2) + (fZ * i), type("d")));
					blocks.add(new BlockPlace(BlockType.STAIRS, x + (hX * 1) + (fX * i), y + 6, z + (hZ * 1) + (fZ * i), type("d")));

					blocks.add(new BlockPlace(BlockType.STAIRS, x + (hoX * 3) + (fX * i), y + 4, z + (hoZ * 3) + (fZ * i), type("d2")));
					blocks.add(new BlockPlace(BlockType.STAIRS, x + (hoX * 2) + (fX * i), y + 5, z + (hoZ * 2) + (fZ * i), type("d2")));
					blocks.add(new BlockPlace(BlockType.STAIRS, x + (hoX * 1) + (fX * i), y + 6, z + (hoZ * 1) + (fZ * i), type("d2")));
				}
			}
		}
		return blocks;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if (side == EnumFacing.UP) {
			return new int[] { 0, 1, 2, 3, 4, 5, 6 };
		}
		return new int[] { 8, 9, 10, 11, 12, 13, 14, 15, 16 };
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing side) {
		if (side == EnumFacing.UP && stack.getItem() != null) {
			BlockType type = BlockType.getTypeForItem(stack.getItem());
			if (type != BlockType.NONE) {
				for (int slot : getSlotsForType(type)) {
					if (slot == index) {
						return true;
					}
				}
			}
		}
		return stack != null && stack.getItem() instanceof IPlantable;
	}

	public int[] getSlotsForType(BlockType type) {
		switch (type) {
		case LOG:
			return logStack;
		case STAIRS:
			return stairStack;
		case PLANKS:
			return plankStack;
		case GLASS:
			return glassStack;
		default:
			break;
		}
		return new int[0];
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerAdvancedGreenhouse(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiAdvancedGreenhouse(player.inventory, this);
	}

}