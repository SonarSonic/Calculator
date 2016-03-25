package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.machines.IFlawlessGreenhouse;
import sonar.calculator.mod.client.gui.machines.GuiFlawlessGreenhouse;
import sonar.calculator.mod.common.containers.ContainerFlawlessGreenhouse;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;
import sonar.core.inventory.SonarTileInventory;
import sonar.core.network.sync.SyncEnergyStorage;
import sonar.core.utils.BlockCoords;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.InventoryHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.RenderHelper;

public class TileEntityFlawlessGreenhouse extends TileEntityGreenhouse implements ISidedInventory, IFlawlessGreenhouse, IGuiTile {

	public int plants, levelTicks, checkTicks, houseSize, growTicks, growTick;
	public int plantsGrown;

	public TileEntityFlawlessGreenhouse() {
		super.storage = new SyncEnergyStorage(500000, 64000);
		super.inv = new SonarTileInventory(this, 10);
		super.type = 3;
		super.maxLevel = 100000;
		super.plantTick = 2;

	}

	@Override
	public void update() {
		super.update();
		if (!this.isBeingBuilt()) {
			checkTile();
		}
		if (this.isCompleted()) {
			if (!this.worldObj.isRemote) {
				extraTicks();
			}
			plant();
			grow();
			harvestCrops();

		}
		discharge(0);

		this.markDirty();
	}

	public void grow() {
		if (this.growTicks == 0) {
			this.growTick = GreenhouseHelper.getGrowTicks(this.getOxygen(), 3);
			this.growTicks++;
		}
		if (this.growTick != 0) {
			if (this.growTicks >= 0 && this.growTicks != growTick) {
				growTicks++;
			}
		}
		if (this.growTicks == growTick) {
			for (int i = 0; i <= this.houseSize; i++) {
				if (this.storage.getEnergyStored() >= growthRF) {
					if (growCrop(3, this.houseSize)) {
						if (!this.worldObj.isRemote)
							plantsGrown++;
						this.storage.modifyEnergyStored(-growthRF);
					}
				}
			}
			this.growTicks = 0;
		}

	}

	public List<BlockCoords> getPlantArea() {
		List<BlockCoords> coords = new ArrayList();

		int hX = horizontal.getFrontOffsetX();
		int hZ = horizontal.getFrontOffsetZ();

		int hoX = horizontal.getOpposite().getFrontOffsetX();
		int hoZ = horizontal.getOpposite().getFrontOffsetZ();

		int fX = forward.getFrontOffsetX();
		int fZ = forward.getFrontOffsetZ();

		for (int i = 0; i <= this.houseSize; i++) {
			for (int XZ = 1; XZ <= 2; XZ++) {
				coords.add(new BlockCoords(pos.add((hX * XZ) + (fX * (1 + i)), 0, (hZ * XZ) + (fZ * (1 + i)))));

			}
		}
		return coords;
	}

	public void extraTicks() {
		if (levelTicks == 15) {
			this.getPlants();
		}
		if (this.levelTicks >= 0 && this.levelTicks != 20) {
			levelTicks++;
		}
		if (this.levelTicks == 20) {
			this.levelTicks = 0;
			InventoryHelper.extractItems(this.getWorld().getTileEntity(pos.add((forward.getOpposite().getFrontOffsetX()), 0, (forward.getOpposite().getFrontOffsetZ()))), this, 0, 0, new PlantableFilter());
			gasLevels();

		}
	}

	public FailedCoords isComplete() {
		if (RenderHelper.getHorizontal(forward) != null) {
			int hX = RenderHelper.getHorizontal(forward).getFrontOffsetX();
			int hZ = RenderHelper.getHorizontal(forward).getFrontOffsetZ();

			int hoX = RenderHelper.getHorizontal(forward).getOpposite().getFrontOffsetX();
			int hoZ = RenderHelper.getHorizontal(forward).getOpposite().getFrontOffsetZ();

			int fX = forward.getFrontOffsetX();
			int fZ = forward.getFrontOffsetZ();

			FailedCoords size = checkSize(true, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, pos.getX(), pos.getY(), pos.getZ());

			if (!size.getBoolean()) {
				return size;
			}

			return new FailedCoords(true, BlockCoords.EMPTY, FontHelper.translate("locator.none"));
		}
		return new FailedCoords(false, BlockCoords.EMPTY, "Something went wrong...");
	}

	private void checkTile() {
		if (this.checkTicks >= 0 && this.checkTicks != 50) {
			checkTicks++;
		}

		if (this.checkTicks == 50) {
			this.checkTicks = 0;
			if (this.isComplete().getBoolean()) {
				if (!this.wasBuilt()) {
					this.setGas(0);
					this.setWasBuilt();
				}
				this.setCompleted();
				addFarmland();
			} else {
				this.setIncomplete();
			}
		}

	}

	/** adds gas, depends on day and night **/
	public void gasLevels() {
		boolean day = this.worldObj.isDaytime();
		int gasAdd = this.getGasAdd();
		if (day) {
			int add = (this.plants * 8) - (gasAdd * 2);
			this.addGas(-add);
		}
		if (!day) {

			int add = (this.plants * 2) + (gasAdd * 2);
			this.addGas(add);
		}

	}

	private int getGasAdd() {
		TileEntity tile = this.worldObj.getTileEntity(pos.add((RenderHelper.getHorizontal(forward).getFrontOffsetX() * 3), 0, (RenderHelper.getHorizontal(forward).getFrontOffsetZ()) * 3));
		if (tile != null && tile instanceof TileEntityCO2Generator) {
			TileEntityCO2Generator generator = (TileEntityCO2Generator) tile;
			return generator.gasAdd;
		}
		return 0;
	}

	/** gets plants inside greenhouse and sets it to this.plants **/
	private void getPlants() {
		int hX = RenderHelper.getHorizontal(forward).getFrontOffsetX();
		int hZ = RenderHelper.getHorizontal(forward).getFrontOffsetZ();

		int hoX = RenderHelper.getHorizontal(forward).getOpposite().getFrontOffsetX();
		int hoZ = RenderHelper.getHorizontal(forward).getOpposite().getFrontOffsetZ();

		int fX = forward.getFrontOffsetX();
		int fZ = forward.getFrontOffsetZ();

		this.plants = 0;

		for (int i = 0; i <= this.houseSize; i++) {
			for (int XZ = 1; XZ <= 2; XZ++) {
				BlockPos pos = this.pos.add((hX * XZ) + (fX * (1 + i)), 0, (hZ * XZ) + (fZ * (1 + i)));
				if (this.worldObj.getBlockState(pos).getBlock() instanceof IGrowable) {
					this.plants++;

				}
			}
		}
	}

	/** id = Gas to add to. (Carbon=0) (Oxygen=1). add = amount to add **/
	public void addGas(int add) {

		if (this.carbonLevels + add < this.maxLevel && this.carbonLevels + add >= 0) {
			this.carbonLevels = this.carbonLevels + add;
		} else {
			if (this.carbonLevels + add > this.maxLevel) {
				setGas(maxLevel);
			}
			if (this.carbonLevels + add < 0) {
				setGas(0);
			}
		}

	}

	/** Hoes the ground **/
	public void addFarmland() {
		int hX = RenderHelper.getHorizontal(forward).getFrontOffsetX();
		int hZ = RenderHelper.getHorizontal(forward).getFrontOffsetZ();

		int hoX = RenderHelper.getHorizontal(forward).getOpposite().getFrontOffsetX();
		int hoZ = RenderHelper.getHorizontal(forward).getOpposite().getFrontOffsetZ();

		int fX = forward.getFrontOffsetX();
		int fZ = forward.getFrontOffsetZ();

		for (int i = 0; i <= this.houseSize; i++) {
			for (int XZ = 0; XZ <= 3; XZ++) {
				BlockPos pos = this.pos.add((fX * 2) + (hX * XZ) + (fX * (1 + i)), 0, (fZ * 2) + (hZ * XZ) + (fZ * (1 + i)));
				if (XZ != 1 && XZ != 2) {
					if (this.storage.getEnergyStored() >= waterRF) {
						if (GreenhouseHelper.applyWater(worldObj, pos)) {
							this.storage.modifyEnergyStored(-waterRF);
						}
					}
				} else {
					if (this.storage.getEnergyStored() >= farmlandRF) {
						if (GreenhouseHelper.applyFarmland(worldObj, pos)) {
							this.storage.modifyEnergyStored(-farmlandRF);
						}
					}
				}
			}
		}
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.plantsHarvested = nbt.getInteger("plantsHarvested");
			this.plantsGrown = nbt.getInteger("plantsGrown");
			this.houseSize = nbt.getInteger("houseSize");

			if (type == SyncType.SAVE) {
				this.planting = nbt.getInteger("planting");
				this.plants = nbt.getInteger("Plants");
				this.levelTicks = nbt.getInteger("Level");
				this.plantTicks = nbt.getInteger("Plant");
				this.checkTicks = nbt.getInteger("Check");
				this.growTick = nbt.getInteger("growTick");
				this.growTicks = nbt.getInteger("growTicks");
			}
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setInteger("plantsHarvested", this.plantsHarvested);
			nbt.setInteger("plantsGrown", this.plantsGrown);
			nbt.setInteger("houseSize", this.houseSize);

			if (type == SyncType.SAVE) {
				nbt.setInteger("planting", this.planting);
				nbt.setInteger("Plants", this.plants);
				nbt.setInteger("Level", this.levelTicks);
				nbt.setInteger("Check", this.checkTicks);
				nbt.setInteger("Plant", this.plantTicks);
				nbt.setInteger("growTicks", this.growTicks);
				nbt.setInteger("growTick", this.growTick);
			}

		}
	}

	public boolean stableStone(int x, int y, int z) {
		Block block = this.worldObj.getBlockState(new BlockPos(x, y, z)).getBlock();
		if (block != null && block == Calculator.stableStone) {
			return false;
		}
		if (block != null && block == Calculator.stablestonerimmedBlock) {
			return false;
		}
		if (block != null && block == Calculator.stablestonerimmedblackBlock) {
			return false;
		}
		if (block != null && block == Calculator.flawlessGreenhouse) {
			return false;
		}
		if (block != null && block == Calculator.CO2Generator) {
			return false;
		}
		return true;
	}

	public boolean flawlessGlass(int x, int y, int z) {
		Block block = this.worldObj.getBlockState(new BlockPos(x, y, z)).getBlock();
		if (block != null && block == Calculator.flawlessGlass) {
			return false;
		}
		return true;
	}

	public boolean slabQuartz(int x, int y, int z) {
		IBlockState state = this.worldObj.getBlockState(new BlockPos(x, y, z));
		if (state.getBlock() == Blocks.stone_slab) {
			if (state.getBlock().getMetaFromState(state) == 7) {
				return false;
			}
		}
		return true;
	}

	public FailedCoords checkSize(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {
		this.houseSize = 0;
		FailedCoords start = end(check, w, hX, hZ, hoX, hoZ, fX, fZ, x, y, z);
		if (start.getBoolean()) {
			for (int i = 1; i <= 65; i++) {

				if (i == 65) {
					FailedCoords end = end(check, w, hX, hZ, hoX, hoZ, fX, fZ, x + (fX * i), y, z + (fZ * i));
					return end;

				} else {
					FailedCoords middle = middle(check, w, hX, hZ, hoX, hoZ, fX, fZ, x + (fX * i), y, z + (fZ * i));
					if (!middle.getBoolean()) {
						if (this.houseSize > 0) {
							FailedCoords end = end(check, w, hX, hZ, hoX, hoZ, fX, fZ, x + (fX * i), y, z + (fZ * i));
							return end;
						} else {
							return middle;
						}
					}

					if (middle.getBoolean()) {
						this.houseSize++;
					}
				}

			}
		} else {
			return start;
		}

		return new FailedCoords(true, BlockCoords.EMPTY, FontHelper.translate("locator.none"));
	}

	public FailedCoords middle(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = 0; i <= 3; i++) {
			if (slabQuartz(x + (hX * i), y + 2, z + (hZ * i))) {
				return new FailedCoords(false, x + (hX * i), y + 2, z + (hZ * i), FontHelper.translate("greenhouse.quartz"));
			}

		}
		for (int i = 0; i <= 1; i++) {
			if (flawlessGlass(x + (hX * 3), y + i, z + (hZ * 3))) {
				return new FailedCoords(false, x + (hX * 3), y + i, z + (hZ * 3), FontHelper.translate("greenhouse.glass"));
			}
			if (flawlessGlass(x, y + i, z)) {
				return new FailedCoords(false, x, y + i, z, FontHelper.translate("greenhouse.glass"));
			}
		}

		return new FailedCoords(true, BlockCoords.EMPTY, FontHelper.translate("locator.none"));
	}

	public FailedCoords end(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = 0; i <= 3; i++) {
			if (stableStone(x + (hX * i), y - 1, z + (hZ * i))) {
				return new FailedCoords(false, x + (hX * i), y - 1, z + (hZ * i), FontHelper.translate("greenhouse.stable"));
			}
			if (slabQuartz(x + (hX * i), y + 2, z + (hZ * i))) {
				return new FailedCoords(false, x + (hX * i), y + 2, z + (hZ * i), FontHelper.translate("greenhouse.quartz"));
			}

		}
		for (int i = 0; i <= 1; i++) {
			if (stableStone(x + (hX * 3), y + i, z + (hZ * 3))) {
				return new FailedCoords(false, x + (hX * 3), y + i, z + (hZ * 3), FontHelper.translate("greenhouse.stable"));
			}
			if (stableStone(x, y + i, z)) {
				return new FailedCoords(false, x, y + i, z, FontHelper.translate("greenhouse.stable"));
			}
		}
		return new FailedCoords(true, BlockCoords.EMPTY, FontHelper.translate("locator.none"));
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		return true;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return stack != null && stack.getItem() instanceof IPlantable;
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		currenttip.add(FontHelper.translate("greenhouse.size") + ": " + this.houseSize);
		return super.getWailaInfo(currenttip);
	}

	@Override
	public int getPlantsHarvested() {
		return this.plantsHarvested;
	}

	@Override
	public int getPlantsGrown() {
		return this.plantsGrown;
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerFlawlessGreenhouse(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiFlawlessGreenhouse(player.inventory, this);
	}
}