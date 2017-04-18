package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.machines.GuiBasicGreenhouse;
import sonar.calculator.mod.common.containers.ContainerBasicGreenhouse;
import sonar.calculator.mod.common.tileentity.TileEntityBuildingGreenhouse;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;
import sonar.core.api.energy.EnergyMode;
import sonar.core.helpers.SonarHelper;
import sonar.core.inventory.SonarInventory;
import sonar.core.utils.IGuiTile;

public class TileEntityBasicGreenhouse extends TileEntityBuildingGreenhouse implements ISidedInventory, IGuiTile {

	public int plants, lanterns, growTicks, growTick;

	public TileEntityBasicGreenhouse() {
		super(56,18,30,14);
		super.storage.setCapacity(350000).setMaxTransfer(800);
		super.inv = new SonarInventory(this, 14);
		super.energyMode = EnergyMode.RECIEVE;
		super.type = 1;
		super.maxLevel = 100000;
		super.plantTick = 60;
		syncList.addPart(inv);
	}

	@Override
	public void update() {
		super.update();
		discharge(4);
	}

	
	public ArrayList<BlockPos> getPlantArea() {
		ArrayList<BlockPos> coords = Lists.newArrayList();
		int fX = forward.getFrontOffsetX();
		int fZ = forward.getFrontOffsetZ();
		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				coords.add(pos.add((2 * fX) + X, 0, (2 * fZ) + Z));

			}
		}
		return coords;
	}

	/** adds gas, depends on day and night **/
	public void gasLevels() {
		boolean day = this.getWorld().isDaytime();
		if (day) {
			int add = (this.plants * 8) - (this.lanterns * 50);
			this.addGas(-add);
		}
		if (!day) {
			int add = (this.plants * 2) + (this.lanterns * 50);
			this.addGas(add);
		}

	}

	/** gets plants inside greenhouse and sets it to this.plants **/
	public int getPlants() {
		this.plants = 0;
		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				if (this.getWorld().getBlockState(pos.add(X, 0, Z)).getBlock() instanceof IGrowable) {
					this.plants++;

				}
			}
		}
		return plants;
	}

	/** gets lanterns inside greenhouse and sets it to this.lanterns **/
	public int getLanterns() {
		this.lanterns = 0;
		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				for (int Y = 0; Y <= 3; Y++) {
					BlockPos pos = this.pos.add((forward.getFrontOffsetX() * 2) + X, Y, (forward.getFrontOffsetZ() * 2) + Z);
					if (this.getWorld().getBlockState(pos).getBlock() == Calculator.gas_lantern_on) {
						this.lanterns++;
					}
				}
			}
		}
		return lanterns;
	}

	/** Checks inventory has resources **/
	public boolean hasRequiredStacks() {
		if (slots()[0] != null && slots()[1] != null && slots()[2] != null && slots()[3] != null) {
			if (slots()[0].stackSize >= requiredLogs && GreenhouseHelper.checkLog(Block.getBlockFromItem(slots()[0].getItem()))) {
				if (slots()[1].stackSize >= requiredStairs && GreenhouseHelper.checkStairs(Block.getBlockFromItem(slots()[1].getItem()))) {
					if (slots()[2].stackSize >= requiredGlass && GreenhouseHelper.checkGlass(Block.getBlockFromItem(slots()[2].getItem()))) {
						if (slots()[3].stackSize >= requiredPlanks && GreenhouseHelper.checkPlanks(Block.getBlockFromItem(slots()[3].getItem()))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/** Hoes the ground **/
	public void addFarmland() {
		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				BlockPos pos = this.pos.add((2 * forward.getFrontOffsetX()) + X, 0, (2 * forward.getFrontOffsetZ()) + Z);
				if (X == 0 && Z == 0) {
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

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.planting = nbt.getInteger("planting");
		this.plants = nbt.getInteger("Plants");
		this.lanterns = nbt.getInteger("lanterns");
		this.levelTicks = nbt.getInteger("Level");
		this.plantTicks = nbt.getInteger("Plant");
		this.checkTicks = nbt.getInteger("Check");
		this.growTicks = nbt.getInteger("Grow");
		this.growTick = nbt.getInteger("GrowTick");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		nbt.setInteger("planting", this.planting);
		nbt.setInteger("Plants", this.plants);
		nbt.setInteger("lanterns", this.lanterns);
		nbt.setInteger("Level", this.levelTicks);
		nbt.setInteger("Check", this.checkTicks);
		nbt.setInteger("Plant", this.plantTicks);
		nbt.setInteger("Grow", this.growTicks);
		nbt.setInteger("GrowTick", this.growTick);
		return nbt;
	}

	public ArrayList<BlockPlace> getStructure() {
		ArrayList<BlockPlace> blocks = Lists.newArrayList();

		int hX = SonarHelper.getHorizontal(forward).getFrontOffsetX();
		int hZ = SonarHelper.getHorizontal(forward).getFrontOffsetZ();

		int hoX = SonarHelper.getHorizontal(forward).getOpposite().getFrontOffsetX();
		int hoZ = SonarHelper.getHorizontal(forward).getOpposite().getFrontOffsetZ();

		int fX = forward.getFrontOffsetX();
		int fZ = forward.getFrontOffsetZ();
		

		
		//end
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		for (int i = 1; i <= 2; i++) {
			blocks.add(new BlockPlace(BlockType.LOG, x, y + i, z, -1));
		}

		for (int i = 0; i <= 2; i++) {
			blocks.add(new BlockPlace(BlockType.LOG, x + (hX * 2), y + i, z + (hZ * 2), -1));
			blocks.add(new BlockPlace(BlockType.LOG, x + (hoX * 2), y + i, z + (hoZ * 2), -1));

		}
		for (int i = 0; i <= 2; i++) {
			blocks.add(new BlockPlace(BlockType.GLASS, x + (hX), y + i, z + (hZ), -1));
			blocks.add(new BlockPlace(BlockType.GLASS, x + (hoX), y + i, z + (hoZ), -1));

		}
		
		//front
		x = pos.getX() + (forward.getFrontOffsetX() * 4);
		y = pos.getY();
		z = pos.getZ() + (forward.getFrontOffsetZ() * 4);
		for (int i = 0; i <= 2; i++) {
			blocks.add(new BlockPlace(BlockType.LOG, x + (hX * 2), y + i, z + (hZ * 2), -1));
			blocks.add(new BlockPlace(BlockType.LOG, x + (hoX * 2), y + i, z + (hoZ * 2), -1));
		}
		for (int i = 0; i <= 2; i++) {
			blocks.add(new BlockPlace(BlockType.PLANKS, x + (hX * 1), y + i, z + (hZ * 1), -1));
			blocks.add(new BlockPlace(BlockType.PLANKS, x + (hoX * 1), y + i, z + (hoZ * 1), -1));
			if (i == 2)
				blocks.add(new BlockPlace(BlockType.PLANKS, x, y + i, z, -1));
		}

		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		//sides
		for (int i = 1; i <= 3; i++) {
			if (i != 2) {
				blocks.add(new BlockPlace(BlockType.PLANKS, x + (hX * 2) + (fX * i), y - 1, z + (hZ * 2) + (fZ * i), -1));
				blocks.add(new BlockPlace(BlockType.PLANKS, x + (hoX * 2) + (fX * i), y - 1, z + (hoZ * 2) + (fZ * i), -1));
				for (int s = 0; s <= 1; s++) {

					blocks.add(new BlockPlace(BlockType.GLASS, x + (hX * 2) + (fX * i), y + s, z + (hZ * 2) + (fZ * i), -1));
					blocks.add(new BlockPlace(BlockType.GLASS, x + (hoX * 2) + (fX * i), y + s, z + (hoZ * 2) + (fZ * i), -1));
				}
			}
			blocks.add(new BlockPlace(BlockType.PLANKS, x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i), -1));
			blocks.add(new BlockPlace(BlockType.PLANKS, x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i), -1));
		}

		for (int Y = 0; Y <= 1; Y++) {
			blocks.add(new BlockPlace(BlockType.LOG, x + (hX * 2) + (fX * 2), y + Y, z + (hZ * 2) + (fZ * 2), -1));
			blocks.add(new BlockPlace(BlockType.LOG, x + (hoX * 2) + (fX * 2), y + Y, z + (hoZ * 2) + (fZ * 2), -1));
		}

		//roof		
		for (int i = -1; i <= 1; i++) {
			blocks.add(new BlockPlace(BlockType.PLANKS, x + (hX * i), y + 3, z + (hZ * i), -1));
			blocks.add(new BlockPlace(BlockType.PLANKS, x + (hX * i) + (fX * 4), y + 3, z + (hZ * i) + (fZ * 4), -1));
		}
		for (int i = -1; i <= 5; i++) {
			for (int s = 2; s <= 4; s++) {
				blocks.add(new BlockPlace(BlockType.STAIRS, x + (hX * intValues(s, BlockType.STAIRS)) + (fX * i), y + s, z + (hZ * intValues(s, BlockType.STAIRS)) + (fZ * i), type("r")));
				blocks.add(new BlockPlace(BlockType.STAIRS, x + (hoX * intValues(s, BlockType.STAIRS)) + (fX * i), y + s, z + (hoZ * intValues(s, BlockType.STAIRS)) + (fZ * i), type("l")));
				blocks.add(new BlockPlace(BlockType.PLANKS, x + (fX * i), y + 4, z + (fZ * i), -1));
			}
		}

		//underroof
		for (int i = -1; i <= 5; i++) {
			if (i != -1 && i != 0 && i != 4 && i != 5) {
				blocks.add(new BlockPlace(BlockType.STAIRS, x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i), type("d")));
				blocks.add(new BlockPlace(BlockType.STAIRS, x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i), type("d2")));
			} else {
				if (i != 0 && i != 4) {
					blocks.add(new BlockPlace(BlockType.STAIRS, x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i), type("d")));
					blocks.add(new BlockPlace(BlockType.STAIRS, x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i), type("d2")));
					blocks.add(new BlockPlace(BlockType.STAIRS, x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i), type("d")));
					blocks.add(new BlockPlace(BlockType.STAIRS, x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i), type("d2")));
				}
			}

		}
		return blocks;
	}
	
	@Override
	public int[] getSlotsForType(BlockType type) {
		switch(type){
		case LOG:
			return new int[]{0};
		case GLASS:
			return new int[]{2};
		case PLANKS:
			return new int[]{3};
		case STAIRS:
			return new int[]{1};
		default:
			return new int[0];
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] { 5, 6, 7, 8, 9, 10, 11, 12, 13 };
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		return stack != null && stack.getItem() instanceof IPlantable;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerBasicGreenhouse(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiBasicGreenhouse(player.inventory, this);
	}
}