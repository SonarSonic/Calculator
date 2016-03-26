package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.machines.GuiBasicGreenhouse;
import sonar.calculator.mod.common.containers.ContainerBasicGreenhouse;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;
import sonar.core.api.BlockCoords;
import sonar.core.api.SonarAPI;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.RenderHelper;
import sonar.core.inventory.SonarTileInventory;
import sonar.core.network.sync.SyncEnergyStorage;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.IGuiTile;

public class TileEntityBasicGreenhouse extends TileEntityGreenhouse implements ISidedInventory, IGuiTile {

	public int plants, lanterns, levelTicks, checkTicks, growTicks, growTick;

	public int stackStairs = 56;
	public int stackLog = 18;
	public int stackPlanks = 30;
	public int stackGlass = 14;

	public int requiredBuildEnergy = (stackStairs + stackLog + stackPlanks + stackGlass) * buildRF;

	public TileEntityBasicGreenhouse() {

		super.storage = new SyncEnergyStorage(350000, 800);
		super.inv = new SonarTileInventory(this, 14);
		super.type = 1;
		super.maxLevel = 100000;
		super.plantTick = 60;

	}

	@Override
	public void update() {
		super.update();
		if (this.worldObj.isBlockIndirectlyGettingPowered(pos) > 0) {
			return;
		}
		if (!this.isBeingBuilt()) {
			checkTile();
		}
		if (this.isCompleted()) {
			if (!this.worldObj.isRemote) {
				extraTicks();
			}
			plant();
			growTicks();
			harvestCrops();
		} else if (this.isBeingBuilt()) {
			createMultiblock();
		}

		discharge(4);

		this.markDirty();
	}

	public List<BlockCoords> getPlantArea() {
		List<BlockCoords> coords = new ArrayList();
		int fX = forward.getFrontOffsetX();
		int fZ = forward.getFrontOffsetZ();
		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				coords.add(new BlockCoords(pos.add((2 * fX) + X, 0, (2 * fZ) + Z)));

			}
		}
		return coords;
	}

	public FailedCoords createBlock() {
		FailedCoords coords = canMakeMultiblock();
		if (!this.isBeingBuilt()) {
			if (coords.getBoolean()) {
				if (this.storage.getEnergyStored() >= this.requiredBuildEnergy) {
					if (this.hasRequiredStacks()) {
						this.setBeingBuilt();
					}
				}
			}

		}

		return coords;
	}

	public void extraTicks() {
		if (levelTicks == 15) {
			this.getPlants();
			this.getLanterns();
		}
		if (this.levelTicks >= 0 && this.levelTicks != 20) {
			levelTicks++;
		}
		if (this.levelTicks == 20) {
			SonarAPI.getItemHelper().transferItems(this.getWorld().getTileEntity(xCoord + (getForward().getOpposite().offsetX), yCoord, zCoord + (getForward().getOpposite().offsetZ)), this, ForgeDirection.getOrientation(0), ForgeDirection.getOrientation(0), new PlantableFilter());

			this.levelTicks = 0;
			gasLevels();
		}
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

	public void growTicks() {
		if (this.growTicks == 0) {
			this.growTick = GreenhouseHelper.getGrowTicks(this.getOxygen(), 1);
			this.growTicks++;
			return;
		}
		if (growTick != 0 && this.growTicks >= growTick) {
			if (this.storage.getEnergyStored() >= growthRF) {
				if (growCrop(1, 0)) {
					this.storage.modifyEnergyStored(-growthRF);
				}
				this.growTicks = 0;
			}
		} else {
			growTicks++;
		}
	}

	/** adds gas, depends on day and night **/
	public void gasLevels() {
		boolean day = this.worldObj.isDaytime();
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
	private void getPlants() {
		this.plants = 0;
		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				if (this.worldObj.getBlockState(pos.add(X, 0, Z)).getBlock() instanceof IGrowable) {
					this.plants++;

				}
			}
		}
	}

	/** gets lanterns inside greenhouse and sets it to this.lanterns **/
	private void getLanterns() {
		this.lanterns = 0;
		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				for (int Y = 0; Y <= 3; Y++) {
					BlockPos pos = this.pos.add((forward.getFrontOffsetX() * 2) + X, Y, (forward.getFrontOffsetZ() * 2) + Z);
					if (this.worldObj.getBlockState(pos).getBlock() == Calculator.gas_lantern_on) {
						this.lanterns++;
					}
				}
			}
		}
	}

	/** add = amount to add **/
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

	/** Checks inventory has resources **/
	public boolean hasRequiredStacks() {
		if (slots()[0] != null && slots()[1] != null && slots()[2] != null && slots()[3] != null) {
			if (slots()[0].stackSize >= stackLog && checkLog(Block.getBlockFromItem(slots()[0].getItem()))) {
				if (slots()[1].stackSize >= stackStairs && checkStairs(Block.getBlockFromItem(slots()[1].getItem()))) {
					if (slots()[2].stackSize >= stackGlass && checkGlass(Block.getBlockFromItem(slots()[2].getItem()))) {
						if (slots()[3].stackSize >= stackPlanks && checkPlanks(Block.getBlockFromItem(slots()[3].getItem()))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/** Checks the space is free **/
	public FailedCoords canMakeMultiblock() {
		int hX = RenderHelper.getHorizontal(forward).getFrontOffsetX();
		int hZ = RenderHelper.getHorizontal(forward).getFrontOffsetZ();

		int hoX = RenderHelper.getHorizontal(forward).getOpposite().getFrontOffsetX();
		int hoZ = RenderHelper.getHorizontal(forward).getOpposite().getFrontOffsetZ();

		for (int Z = -3; Z <= 3; Z++) {
			for (int X = -3; X <= 3; X++) {

				for (int Y = -1; Y <= 4; Y++) {

					BlockPos pos = this.pos.add((forward.getFrontOffsetX() * 2) + X, Y, (forward.getFrontOffsetZ() * 2) + Z);
					if (!GreenhouseHelper.r(worldObj, pos)) {
						if (!(this.worldObj.getTileEntity(pos) == this)) {

							return new FailedCoords(false, new BlockCoords(pos), FontHelper.translate("locator.none"));
						}

					}

				}
			}
		}

		return new FailedCoords(true, BlockCoords.EMPTY, FontHelper.translate("locator.none"));
	}

	/** Creates Green House Structure **/
	private void createMultiblock() {
		int hX = RenderHelper.getHorizontal(forward).getFrontOffsetX();
		int hZ = RenderHelper.getHorizontal(forward).getFrontOffsetZ();

		int hoX = RenderHelper.getHorizontal(forward).getOpposite().getFrontOffsetX();
		int hoZ = RenderHelper.getHorizontal(forward).getOpposite().getFrontOffsetZ();

		int fX = forward.getFrontOffsetX();
		int fZ = forward.getFrontOffsetZ();

		if (end(false, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, pos.getX(), pos.getY(), pos.getZ()).getBoolean()) {
			if (start(false, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ).getBoolean()) {
				if (sides(false, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, pos.getX(), pos.getY(), pos.getZ()).getBoolean()) {
					if (roof(false, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, pos.getX(), pos.getY(), pos.getZ()).getBoolean()) {
						if (underroof(false, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, pos.getX(), pos.getY(), pos.getZ()).getBoolean()) {
							addFarmland();
							this.isMulti = 1;
						}
					}
				}
			}
		}

	}

	/** Checjs Green House Structure **/
	public FailedCoords isComplete() {
		if (RenderHelper.getHorizontal(forward) != null) {
			int hX = RenderHelper.getHorizontal(forward).getFrontOffsetX();
			int hZ = RenderHelper.getHorizontal(forward).getFrontOffsetZ();

			int hoX = RenderHelper.getHorizontal(forward).getOpposite().getFrontOffsetX();
			int hoZ = RenderHelper.getHorizontal(forward).getOpposite().getFrontOffsetZ();

			int fX = forward.getFrontOffsetX();
			int fZ = forward.getFrontOffsetZ();

			FailedCoords end = end(true, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, pos.getX(), pos.getY(), pos.getZ());
			FailedCoords start = start(true, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ);
			FailedCoords sides = sides(true, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, pos.getX(), pos.getY(), pos.getZ());
			FailedCoords roof = roof(true, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, pos.getX(), pos.getY(), pos.getZ());
			FailedCoords underroof = underroof(true, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, pos.getX(), pos.getY(), pos.getZ());

			if (!end.getBoolean()) {
				return end;
			} else if (!start.getBoolean()) {
				return start;
			} else if (!sides.getBoolean()) {
				return sides;
			} else if (!roof.getBoolean()) {
				return roof;
			} else if (!underroof.getBoolean()) {
				return underroof;
			}

			return new FailedCoords(true, BlockCoords.EMPTY, FontHelper.translate("locator.none"));
		}
		return new FailedCoords(false, BlockCoords.EMPTY, "Something went wrong...");
	}

	/** Hoes the ground **/
	private void addFarmland() {
		EnumFacing forward = EnumFacing.getFront(this.getBlockMetadata()).getOpposite();

		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				BlockPos pos = this.pos.add((2 * forward.getFrontOffsetX()) + X, 0, (2 * forward.getFrontOffsetZ()) + Z);
				if (X == 0 && Z == 0) {
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

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.isMulti = nbt.getInteger("Multi");
		this.planting = nbt.getInteger("planting");
		this.plants = nbt.getInteger("Plants");
		this.lanterns = nbt.getInteger("lanterns");
		this.carbonLevels = nbt.getInteger("Carbon");
		this.levelTicks = nbt.getInteger("Level");
		this.plantTicks = nbt.getInteger("Plant");
		this.checkTicks = nbt.getInteger("Check");
		this.growTicks = nbt.getInteger("Grow");
		this.growTick = nbt.getInteger("GrowTick");
		this.wasBuilt = nbt.getInteger("wasBuilt");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);

		NBTTagList list = new NBTTagList();
		nbt.setInteger("Multi", this.isMulti);
		nbt.setInteger("planting", this.planting);
		nbt.setInteger("Plants", this.plants);
		nbt.setInteger("lanterns", this.lanterns);
		nbt.setInteger("Carbon", this.carbonLevels);
		nbt.setInteger("Level", this.levelTicks);
		nbt.setInteger("Check", this.checkTicks);
		nbt.setInteger("Plant", this.plantTicks);
		nbt.setInteger("Grow", this.growTicks);
		nbt.setInteger("GrowTick", this.growTick);
		nbt.setInteger("wasBuilt", this.wasBuilt);
	}

	public void setLog(int x, int y, int z) {
		this.setBlockType(x, y, z, new int[] { 0 }, BlockType.LOG);

	}

	public void setGlass(int x, int y, int z) {
		this.setBlockType(x, y, z, new int[] { 4, 5 }, BlockType.GLASS);
	}

	public void setPlanks(int x, int y, int z) {
		this.setBlockType(x, y, z, new int[] { 3 }, BlockType.PLANKS);
	}

	public void setStairs(int x, int y, int z, int meta, int flag) {
		this.setBlockType(x, y, z, new int[] { 1}, BlockType.STAIRS);
	}
	
	public FailedCoords roof(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = -1; i <= 1; i++) {
			if (getPlanks(x + (hX * i), y + 3, z + (hZ * i))) {
				if (!check) {
					setPlanks(x + (hX * i), y + 3, z + (hZ * i));
				}
				return new FailedCoords(false, x + (hX * i), y + 3, z + (hZ * i), FontHelper.translate("greenhouse.planks"));
			}
			if (getPlanks(x + (hX * i) + (fX * 4), y + 3, z + (hZ * i) + (fZ * 4))) {
				if (!check) {
					setPlanks(x + (hX * i) + (fX * 4), y + 3, z + (hZ * i) + (fZ * 4));
				}
				return new FailedCoords(false, x + (hX * i) + (fX * 4), y + 3, z + (hZ * i) + (fZ * 4), FontHelper.translate("greenhouse.planks"));
			}
		}

		for (int i = -1; i <= 5; i++) {
			for (int s = 2; s <= 4; s++) {

				if (getStairs(x + (hX * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fX * i), y + s, z + (hZ * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fZ * i))) {
					if (!check) {
						setStairs(x + (hX * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fX * i), y + s, z + (hZ * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fZ * i), type("r"), 2);
					}
					return new FailedCoords(false, x + (hX * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fX * i), y + s, z + (hZ * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fZ * i), FontHelper.translate("greenhouse.stairs"));

				}
				if (getStairs(x + (hoX * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fX * i), y + s, z + (hoZ * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fZ * i))) {
					if (!check) {
						setStairs(x + (hoX * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fX * i), y + s, z + (hoZ * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fZ * i), type("l"), 2);
					}
					return new FailedCoords(false, x + (hoX * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fX * i), y + s, z + (hoZ * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}
				if (getPlanks(x + (fX * i), y + 4, z +(fZ * i))) {
					if (!check) {
						setPlanks(x + (fX * i), y + 4, z + (fZ * i));
					}
					return new FailedCoords(false, x + (fX * i), y + 4, z + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}

			}
		}

		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));
	}

	public FailedCoords underroof(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = -1; i <= 5; i++) {
			if (i != -1 && i != 0 && i != 4 && i != 5) {
				if (getStairs(x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i))) {
					if (!check) {
						setStairs(x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i), type("d"), 2);
					}
					return new FailedCoords(false, x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}
				if (getStairs(x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i))) {
					if (!check) {
						setStairs(x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i), type("d2"), 2);
					}
					return new FailedCoords(false, x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}
			} else {
				if (i != 0 && i != 4) {
					if (getStairs(x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i))) {
						if (!check) {
							setStairs(x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i), type("d"), 2);
						}
						return new FailedCoords(false, x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
					}
					if (getStairs(x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i))) {
						if (!check) {
							setStairs(x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i), type("d2"), 2);
						}
						return new FailedCoords(false, x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
					}
					if (i != 0 && i != 4) {
						if (getStairs(x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i))) {
							if (!check) {
								setStairs(x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i), type("d"), 2);
							}
							return new FailedCoords(false, x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
						}
						if (getStairs(x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i))) {
							if (!check) {
								setStairs(x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i), type("d2"), 2);
							}
							return new FailedCoords(false, x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
						}
					}
				}
			}
		}

		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));
	}

	public FailedCoords sides(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = 1; i <= 3; i++) {
			if (i != 2) {
				if (getPlanks(x + (hX * 2) + (fX * i), y - 1, z + (hZ * 2) + (fZ * i))) {
					if (!check) {
						setPlanks(x + (hX * 2) + (fX * i), y - 1, z + (hZ * 2) + (fZ * i));
					}
					return new FailedCoords(false, x + (hX * 2) + (fX * i), y - 1, z + (hZ * 2) + (fZ * i), FontHelper.translate("greenhouse.planks"));
				}

				if (getPlanks(x + (hoX * 2) + (fX * i), y - 1, z + (hoZ * 2) + (fZ * i))) {
					if (!check) {
						setPlanks(x + (hoX * 2) + (fX * i), y - 1, z + (hoZ * 2) + (fZ * i));
					}
					return new FailedCoords(false, x + (hoX * 2) + (fX * i), y - 1, z + (hoZ * 2) + (fZ * i), FontHelper.translate("greenhouse.planks"));
				}

				for (int s = 0; s <= 1; s++) {
					if (getGlass(x + (hX * 2) + (fX * i), y + s, z + (hZ * 2) + (fZ * i))) {
						if (!check) {
							setGlass(x + (hX * 2) + (fX * i), y + s, z + (hZ * 2) + (fZ * i));
						}
						return new FailedCoords(false, x + (hX * 2) + (fX * i), y + s, z + (hZ * 2) + (fZ * i), FontHelper.translate("greenhouse.glass"));
					}
					if (getGlass(x + (hoX * 2) + (fX * i), y + s, z + (hoZ * 2) + (fZ * i))) {
						if (!check) {
							setGlass(x + (hoX * 2) + (fX * i), y + s, z + (hoZ * 2) + (fZ * i));
						}
						return new FailedCoords(false, x + (hoX * 2) + (fX * i), y + s, z + (hoZ * 2) + (fZ * i), FontHelper.translate("greenhouse.glass"));
					}

				}
			}

			if (getPlanks(x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i))) {
				if (!check) {
					setPlanks(x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i));
				}
				return new FailedCoords(false, x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i), FontHelper.translate("greenhouse.planks"));
			}
			if (getPlanks(x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i))) {
				if (!check) {
					setPlanks(x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i));
				}
				return new FailedCoords(false, x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i), FontHelper.translate("greenhouse.planks"));
			}
		}
		for (int Y = 0; Y <= 1; Y++) {

			if (getLog(x + (hX * 2) + (fX * 2), y + Y, z + (hZ * 2) + (fZ * 2))) {
				if (!check) {
					setLog(x + (hX * 2) + (fX * 2), y + Y, z + (hZ * 2) + (fZ * 2));
				}
				return new FailedCoords(false, x + (hX * 2) + (fX * 2), y + Y, z + (hZ * 2) + (fZ * 2), FontHelper.translate("greenhouse.logs"));
			}

			if (getLog(x + (hoX * 2) + (fX * 2), y + Y, z + (hoZ * 2) + (fZ * 2))) {
				if (!check) {
					setLog(x + (hoX * 2) + (fX * 2), y + Y, z + (hoZ * 2) + (fZ * 2));
				}
				return new FailedCoords(false, x + (hoX * 2) + (fX * 2), y + Y, z + (hoZ * 2) + (fZ * 2), FontHelper.translate("greenhouse.logs"));
			}

		}
		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));
	}

	public FailedCoords end(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = 1; i <= 2; i++) {
			if (getLog(x, y + i, z)) {
				if (!check) {
					setLog(x, y + i, z);
				}
				return new FailedCoords(false, x, y + i, z, FontHelper.translate("greenhouse.logs"));
			}

		}

		for (int i = 0; i <= 2; i++) {

			if (getLog(x + (hX * 2), y + i, z + (hZ * 2))) {
				if (!check) {
					setLog(x + (hX * 2), y + i, z + (hZ * 2));
				}
				return new FailedCoords(false, x + (hX * 2), y + i, z + (hZ * 2), FontHelper.translate("greenhouse.logs"));
			}

			if (getLog(x + (hoX * 2), y + i, z + (hoZ * 2))) {
				if (!check) {
					setLog(x + (hoX * 2), y + i, z + (hoZ * 2));
				}
				return new FailedCoords(false, x + (hoX * 2), y + i, z + (hoZ * 2), FontHelper.translate("greenhouse.logs"));
			}

		}

		for (int i = 0; i <= 2; i++) {
			if (getGlass(x + (hX), y + i, z + (hZ))) {
				if (!check) {
					setGlass(x + (hX), y + i, z + (hZ));
				}
				return new FailedCoords(false, x + (hX), y + i, z + (hZ), FontHelper.translate("greenhouse.glass"));

			}

			if (getGlass(x + (hoX), y + i, z + (hoZ))) {
				if (!check) {
					setGlass(x + (hoX), y + i, z + (hoZ));
				}
				return new FailedCoords(false, x + (hoX), y + i, z + (hoZ), FontHelper.translate("greenhouse.glass"));
			}

		}

		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));

	}

	public FailedCoords start(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ) {
		int x = pos.getX() + (forward.getFrontOffsetX() * 4);
		int y = pos.getY();
		int z = pos.getZ() + (forward.getFrontOffsetZ() * 4);
		for (int i = 0; i <= 2; i++) {

			if (getLog(x + (hX * 2), y + i, z + (hZ * 2))) {
				if (!check) {
					setLog(x + (hX * 2), y + i, z + (hZ * 2));
				}
				return new FailedCoords(false, x + (hX * 2), y + i, z + (hZ * 2), FontHelper.translate("greenhouse.logs"));

			}

			if (getLog(x + (hoX * 2), y + i, z + (hoZ * 2))) {
				if (!check) {
					setLog(x + (hoX * 2), y + i, z + (hoZ * 2));
				}
				return new FailedCoords(false, x + (hoX * 2), y + i, z + (hoZ * 2), FontHelper.translate("greenhouse.logs"));

			}

		}
		for (int i = 0; i <= 2; i++) {

			if (getPlanks(x + (hX * 1), y + i, z + (hZ * 1))) {
				if (!check) {
					setPlanks(x + (hX * 1), y + i, z + (hZ * 1));
				}
				return new FailedCoords(false, x + (hX * 1), y + i, z + (hZ * 1), FontHelper.translate("greenhouse.planks"));

			}

			if (getPlanks(x + (hoX * 1), y + i, z + (hoZ * 1))) {
				if (!check) {
					setPlanks(x + (hoX * 1), y + i, z + (hoZ * 1));
				}
				return new FailedCoords(false, x + (hoX * 1), y + i, z + (hoZ * 1), FontHelper.translate("greenhouse.planks"));

			}

			if (i == 2) {
				if (getPlanks(x, y + i, z)) {
					if (!check) {
						setPlanks(x, y + i, z);
					}
					return new FailedCoords(false, x, y + i, z, FontHelper.translate("greenhouse.planks"));

				}
			}
		}

		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));

	}


	public String getRequiredStacks() {

		if (slots()[0] == null) {
			return FontHelper.translate("greenhouse.logStack") + " " + FontHelper.translate("greenhouse.isEmpty");
		}
		if (slots()[1] == null) {
			return FontHelper.translate("greenhouse.stairsStack") + " " + FontHelper.translate("greenhouse.isEmpty");
		}
		if (slots()[2] == null) {
			return FontHelper.translate("greenhouse.glassStack") + " " + FontHelper.translate("greenhouse.isEmpty");
		}
		if (slots()[3] == null) {
			return FontHelper.translate("greenhouse.plankStack") + " " + FontHelper.translate("greenhouse.isEmpty");
		}

		if (slots()[0] != null && slots()[1] != null && slots()[2] != null && slots()[3] != null) {
			if (!checkLog(Block.getBlockFromItem(slots()[0].getItem()))) {
				return FontHelper.translate("greenhouse.logStack") + " " + FontHelper.translate("greenhouse.noLogs");
			}
			if (!checkStairs(Block.getBlockFromItem(slots()[1].getItem()))) {
				return FontHelper.translate("greenhouse.stairsStack") + " " + FontHelper.translate("greenhouse.noStairs");
			}
			if (!checkGlass(Block.getBlockFromItem(slots()[2].getItem()))) {
				return FontHelper.translate("greenhouse.glassStack") + " " + FontHelper.translate("greenhouse.noGlass");
			}
			if (!checkPlanks(Block.getBlockFromItem(slots()[3].getItem()))) {
				return FontHelper.translate("greenhouse.plankStack") + " " + FontHelper.translate("greenhouse.noPlanks");
			}
			if (!(slots()[0].stackSize >= stackLog)) {
				String logs = FontHelper.translate("greenhouse.requires") + " " + (stackLog - slots()[0].stackSize) + " " + FontHelper.translate("greenhouse.moreLogs");
				return logs;
			}
			if (!(slots()[1].stackSize >= stackStairs)) {
				String stairs = FontHelper.translate("greenhouse.requires") + " " + (stackStairs - slots()[1].stackSize) + " " + FontHelper.translate("greenhouse.moreStairs");
				return stairs;
			}
			if (!(slots()[2].stackSize >= stackGlass)) {
				String stairs = FontHelper.translate("greenhouse.requires") + " " + (stackGlass - slots()[2].stackSize) + " " + FontHelper.translate("greenhouse.moreGlass");
				return stairs;
			}
			if (!(slots()[3].stackSize >= stackPlanks)) {
				String stairs = FontHelper.translate("greenhouse.requires") + " " + (stackPlanks - slots()[3].stackSize) + " " + FontHelper.translate("greenhouse.morePlanks");
				return stairs;
			}
		}
		return FontHelper.translate("locator.unknown");
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		switch (isMulti) {
		case -1:
			currenttip.add(FontHelper.translate("locator.state") + ": " + FontHelper.translate("greenhouse.building"));
			break;
		case 0:
			currenttip.add(FontHelper.translate("locator.state") + ": " + FontHelper.translate("greenhouse.incomplete"));
			break;
		case 1:
			currenttip.add(FontHelper.translate("locator.state") + ": " + FontHelper.translate("greenhouse.complete"));
			break;
		case 2:
			currenttip.add(FontHelper.translate("locator.state") + ": " + FontHelper.translate("greenhouse.complete"));
			break;
		}
		return super.getWailaInfo(currenttip);
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