package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse.PlantableFilter;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.helpers.InventoryHelper;
import sonar.core.utils.helpers.RenderHelper;
import cofh.api.energy.EnergyStorage;

public class TileEntityBasicGreenhouse extends TileEntityGreenhouse {

	public int plants, lanterns, levelTicks, checkTicks, growTicks, growTick;

	public int stackStairs = 56;
	public int stackLog = 18;
	public int stackPlanks = 30;
	public int stackGlass = 14;

	public int requiredPlantEnergy = 20;
	public int requiredGrowEnergy = 50;
	public int requiredBuildEnergy = (stackStairs + stackLog + stackPlanks + stackGlass) * 100;

	public TileEntityBasicGreenhouse() {

		super.storage = new EnergyStorage(350000, 350000);
		super.slots = new ItemStack[14];
		super.type = 1;
		super.maxLevel = 100000;
		super.plantTick = 60;

	}

	@Override
	public void updateEntity() {

		super.updateEntity();
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
		} else if (this.isBeingBuilt()) {
			createMultiblock();
		}

		discharge(4);

		this.markDirty();
	}

	private void harvestCrops() {
		int fX = getForward().offsetX;
		int fZ = getForward().offsetZ;
		int x = xCoord + (2 * fX);
		int y = yCoord;
		int z = zCoord + (2 * fZ);
		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				Block target = this.worldObj.getBlock(x + X, y, z + Z);
				if (target instanceof IGrowable) {
					harvest(worldObj, x + X, y, z + Z, (IGrowable) target);
				}

			}
		}
	}

	@Override
	public boolean plant(IPlantable block, int slot) {

		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				if (canPlant(this.worldObj, xCoord + (getForward().offsetX * 2) + X, yCoord, zCoord + (getForward().offsetZ * 2) + Z, slot, block)) {
					this.worldObj.setBlock(xCoord + (getForward().offsetX * 2) + X, yCoord, zCoord + (getForward().offsetZ * 2) + Z, Blocks.air, 0, 1 | 2);
					Block target = block.getPlant(null, 0, 0, 0);
					int meta = block.getPlantMetadata(null, 0, 0, 0);
					this.worldObj.setBlock(xCoord + (getForward().offsetX * 2) + X, yCoord, zCoord + (getForward().offsetZ * 2) + Z, target, meta, 1 | 2);

					this.slots[slot].stackSize--;
					if (this.slots[slot].stackSize <= 0) {
						this.slots[slot] = null;
					}
					return true;
				}
			}

		}
		this.planting = 0;
		return false;
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
			InventoryHelper.extractItems(this.getWorldObj().getTileEntity(xCoord + (getForward().getOpposite().offsetX), yCoord, zCoord + (getForward().getOpposite().offsetZ)), this, 0, 0, new PlantableFilter());
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

	public void grow() {
		if (this.growTicks == 0) {
			this.growTick = GreenhouseHelper.getGrowTicks(this.getOxygen(), 1);
			this.growTicks++;
		} else if (this.growTick != 0) {
			if (this.growTicks >= 0 && this.growTicks != growTick) {
				growTicks++;
			}
		} else if (this.growTicks == growTick) {
			if (this.storage.getEnergyStored() >= requiredGrowEnergy) {
				if (growCrop(1, 0)) {
					this.storage.modifyEnergyStored(-requiredGrowEnergy);
				}
				this.growTicks = 0;
			}
		}

	}

	/** adds gas, depends on day and night **/
	public void gasLevels() {
		boolean day = this.worldObj.isDaytime();
		if (day) {
			int add = (this.plants * 8) - (this.lanterns * 20);
			this.addGas(-add);
		}
		if (!day) {

			int add = (this.plants * 2) + (this.lanterns * 20);
			this.addGas(add);
		}

	}

	/** gets plants inside greenhouse and sets it to this.plants **/
	private void getPlants() {
		this.plants = 0;

		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				if (this.worldObj.getBlock(xCoord + X, yCoord, zCoord + Z) instanceof IGrowable) {
					this.plants++;

				}
			}
		}
	}

	/** gets lanterns inside greenhouse and sets it to this.lanterns **/
	private void getLanterns() {
		this.lanterns = 0;
		int x = (this.xCoord + getForward().offsetX * 2);
		int y = this.yCoord;
		int z = (this.zCoord + getForward().offsetZ * 2);
		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				for (int Y = 0; Y <= 3; Y++) {
					if (this.worldObj.getBlock(x + X, y + Y, z + Z) == Calculator.gas_lantern_on) {

						this.lanterns++;

					}
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

	/** Checks inventory has resources **/
	public boolean hasRequiredStacks() {
		if (slots[0] != null && slots[1] != null && slots[2] != null && slots[3] != null) {
			if (slots[0].stackSize >= stackLog && checkLog(Block.getBlockFromItem(slots[0].getItem()))) {
				if (slots[1].stackSize >= stackStairs && checkStairs(Block.getBlockFromItem(slots[1].getItem()))) {
					if (slots[2].stackSize >= stackGlass && checkGlass(Block.getBlockFromItem(slots[2].getItem()))) {
						if (slots[3].stackSize >= stackPlanks && checkPlanks(Block.getBlockFromItem(slots[3].getItem()))) {
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
		int hX = RenderHelper.getHorizontal(getForward()).offsetX;
		int hZ = RenderHelper.getHorizontal(getForward()).offsetZ;

		int hoX = RenderHelper.getHorizontal(getForward()).getOpposite().offsetX;
		int hoZ = RenderHelper.getHorizontal(getForward()).getOpposite().offsetZ;

		int fX = getForward().offsetX;
		int fZ = getForward().offsetZ;

		int x = xCoord + (fX * 2);
		int y = yCoord;
		int z = zCoord + (fZ * 2);

		for (int Z = -3; Z <= 3; Z++) {
			for (int X = -3; X <= 3; X++) {

				for (int Y = -1; Y <= 4; Y++) {

					if (!GreenhouseHelper.r(worldObj, x + X, y + Y, z + Z)) {
						if (!(this.worldObj.getTileEntity(x + X, y + Y, z + Z) == this)) {

							return new FailedCoords(false, x + X, y + Y, z + Z, StatCollector.translateToLocal("locator.none"));
						}

					}

				}
			}
		}

		return new FailedCoords(true, 0, 0, 0, StatCollector.translateToLocal("locator.none"));
	}

	/** Creates Green House Structure **/
	private void createMultiblock() {
		int hX = RenderHelper.getHorizontal(getForward()).offsetX;
		int hZ = RenderHelper.getHorizontal(getForward()).offsetZ;

		int hoX = RenderHelper.getHorizontal(getForward()).getOpposite().offsetX;
		int hoZ = RenderHelper.getHorizontal(getForward()).getOpposite().offsetZ;

		int fX = getForward().offsetX;
		int fZ = getForward().offsetZ;

		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		if (end(false, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, xCoord, yCoord, zCoord).getBoolean()) {
			if (start(false, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ).getBoolean()) {
				if (sides(false, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, xCoord, yCoord, zCoord).getBoolean()) {
					if (roof(false, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, xCoord, yCoord, zCoord).getBoolean()) {
						if (underroof(false, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, xCoord, yCoord, zCoord).getBoolean()) {
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
		if (RenderHelper.getHorizontal(getForward()) != null) {
			int hX = RenderHelper.getHorizontal(getForward()).offsetX;
			int hZ = RenderHelper.getHorizontal(getForward()).offsetZ;

			int hoX = RenderHelper.getHorizontal(getForward()).getOpposite().offsetX;
			int hoZ = RenderHelper.getHorizontal(getForward()).getOpposite().offsetZ;

			int fX = getForward().offsetX;
			int fZ = getForward().offsetZ;

			int x = xCoord;
			int y = yCoord;
			int z = zCoord;

			FailedCoords end = end(true, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, xCoord, yCoord, zCoord);
			FailedCoords start = start(true, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ);
			FailedCoords sides = sides(true, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, xCoord, yCoord, zCoord);
			FailedCoords roof = roof(true, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, xCoord, yCoord, zCoord);
			FailedCoords underroof = underroof(true, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, xCoord, yCoord, zCoord);

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

			return new FailedCoords(true, 0, 0, 0, StatCollector.translateToLocal("locator.none"));
		}
		return new FailedCoords(false, 0, 0, 0, "Something went wrong...");
	}

	/** Hoes the ground **/
	private void addFarmland() {
		ForgeDirection forward = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
		int x = xCoord + (2 * forward.offsetX);
		int y = yCoord;
		int z = zCoord + (2 * forward.offsetZ);

		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				if (X == 0 && Z == 0) {
					if (this.storage.getEnergyStored() >= 1000) {
						if (GreenhouseHelper.applyWater(worldObj, x + X, y, z + Z)) {
							this.storage.modifyEnergyStored(-1000);
						}
					}
				} else {
					if (this.storage.getEnergyStored() >= 50) {
						if (GreenhouseHelper.applyFarmland(worldObj, x + X, y, z + Z)) {
							this.storage.modifyEnergyStored(-50);
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

		if (slots[0] == null) {
			this.setIncomplete();
		}

		if (slots[0] != null) {

			Block log = Block.getBlockFromItem(slots[0].getItem());
			int damage = slots[0].getItemDamage();
			if (!checkLog(log)) {
				this.setIncomplete();

			} else if (log != null) {

				slots[0].stackSize--;
				if (slots[0].stackSize == 1) {
					slots[0] = null;
				}
				this.worldObj.setBlock(x, y, z, log, damage, 2);
				this.storage.modifyEnergyStored(-100);
			}
		}

	}

	public void setGlass(int x, int y, int z) {

		if (slots[2] == null) {
			this.setIncomplete();
		}

		if (slots[2] != null) {

			Block glass = Block.getBlockFromItem(slots[2].getItem());
			int damage = slots[2].getItemDamage();
			if (!checkGlass(glass)) {
				this.setIncomplete();

			} else if (glass != null) {

				slots[2].stackSize--;
				if (slots[2].stackSize == 1) {
					slots[2] = null;
				}
				this.worldObj.setBlock(x, y, z, glass, damage, 2);
				this.storage.modifyEnergyStored(-100);
			}
		}

	}

	public void setPlanks(int x, int y, int z) {

		if (slots[3] == null) {
			this.setIncomplete();
		}

		if (slots[3] != null) {

			Block planks = Block.getBlockFromItem(slots[3].getItem());
			int damage = slots[3].getItemDamage();
			if (!checkPlanks(planks)) {
				this.setIncomplete();

			} else if (planks != null) {

				slots[3].stackSize--;
				if (slots[3].stackSize == 1) {
					slots[3] = null;
				}
				this.worldObj.setBlock(x, y, z, planks, damage, 2);
				this.storage.modifyEnergyStored(-100);
			}
		}

	}

	public void setStairs(int x, int y, int z, int meta, int flag) {

		if (slots[1] == null) {
			this.setIncomplete();

		}

		if (slots[1] != null) {
			Block stairs = Block.getBlockFromItem(slots[1].getItem());
			if (!checkStairs(stairs)) {
				this.setIncomplete();

			} else if (stairs != null) {

				slots[1].stackSize--;
				if (slots[1].stackSize == 1) {
					slots[1] = null;
				}
				this.worldObj.setBlock(x, y, z, stairs, meta, flag);
				this.storage.modifyEnergyStored(-100);
			}
		}

	}

	public FailedCoords roof(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = -1; i <= 1; i++) {
			if (getPlanks(w.getBlock(x + (hX * i), y + 3, z + (hZ * i)))) {
				if (!check) {
					setPlanks(x + (hX * i), y + 3, z + (hZ * i));
				}
				return new FailedCoords(false, x + (hX * i), y + 3, z + (hZ * i), StatCollector.translateToLocal("greenhouse.planks"));
			}
			if (getPlanks(w.getBlock(x + (hX * i) + (fX * 4), y + 3, z + (hZ * i) + (fZ * 4)))) {
				if (!check) {
					setPlanks(x + (hX * i) + (fX * 4), y + 3, z + (hZ * i) + (fZ * 4));
				}
				return new FailedCoords(false, x + (hX * i) + (fX * 4), y + 3, z + (hZ * i) + (fZ * 4), StatCollector.translateToLocal("greenhouse.planks"));
			}
		}

		for (int i = -1; i <= 5; i++) {
			for (int s = 2; s <= 4; s++) {

				if (getStairs(w.getBlock(x + (hX * intValues(s, StatCollector.translateToLocal("greenhouse.stairs"))) + (fX * i), y + s, z + (hZ * intValues(s, StatCollector.translateToLocal("greenhouse.stairs"))) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hX * intValues(s, StatCollector.translateToLocal("greenhouse.stairs"))) + (fX * i), y + s, z + (hZ * intValues(s, StatCollector.translateToLocal("greenhouse.stairs"))) + (fZ * i), type("r"), 2);
					}
					return new FailedCoords(false, x + (hX * intValues(s, StatCollector.translateToLocal("greenhouse.stairs"))) + (fX * i), y + s, z + (hZ * intValues(s, StatCollector.translateToLocal("greenhouse.stairs"))) + (fZ * i), StatCollector.translateToLocal("greenhouse.stairs"));

				}
				if (getStairs(w.getBlock(x + (hoX * intValues(s, StatCollector.translateToLocal("greenhouse.stairs"))) + (fX * i), y + s, z + (hoZ * intValues(s, StatCollector.translateToLocal("greenhouse.stairs"))) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hoX * intValues(s, StatCollector.translateToLocal("greenhouse.stairs"))) + (fX * i), y + s, z + (hoZ * intValues(s, StatCollector.translateToLocal("greenhouse.stairs"))) + (fZ * i), type("l"), 2);
					}
					return new FailedCoords(false, x + (hoX * intValues(s, StatCollector.translateToLocal("greenhouse.stairs"))) + (fX * i), y + s, z + (hoZ * intValues(s, StatCollector.translateToLocal("greenhouse.stairs"))) + (fZ * i), StatCollector.translateToLocal("greenhouse.stairs"));
				}
				if (getPlanks(w.getBlock(x + (fX * i), y + 4, zCoord + +(fZ * i)))) {
					if (!check) {
						setPlanks(x + (fX * i), y + 4, zCoord + (fZ * i));
					}
					return new FailedCoords(false, x + (fX * i), y + 4, zCoord + (fZ * i), StatCollector.translateToLocal("greenhouse.stairs"));
				}

			}
		}

		return new FailedCoords(true, 0, 0, 0, StatCollector.translateToLocal("locator.none"));
	}

	public FailedCoords underroof(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = -1; i <= 5; i++) {
			if (i != -1 && i != 0 && i != 4 && i != 5) {
				if (getStairs(w.getBlock(x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i), type("d"), 2);
					}
					return new FailedCoords(false, x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i), StatCollector.translateToLocal("greenhouse.stairs"));
				}
				if (getStairs(w.getBlock(x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i), type("d2"), 2);
					}
					return new FailedCoords(false, x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i), StatCollector.translateToLocal("greenhouse.stairs"));
				}
			} else {
				if (i != 0 && i != 4) {
					if (getStairs(w.getBlock(x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i)))) {
						if (!check) {
							setStairs(x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i), type("d"), 2);
						}
						return new FailedCoords(false, x + (hX) + (fX * i), y + 3, z + (hZ) + (fZ * i), StatCollector.translateToLocal("greenhouse.stairs"));
					}
					if (getStairs(w.getBlock(x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i)))) {
						if (!check) {
							setStairs(x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i), type("d2"), 2);
						}
						return new FailedCoords(false, x + (hoX) + (fX * i), y + 3, z + (hoZ) + (fZ * i), StatCollector.translateToLocal("greenhouse.stairs"));
					}
					if (i != 0 && i != 4) {
						if (getStairs(w.getBlock(x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i)))) {
							if (!check) {
								setStairs(x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i), type("d"), 2);
							}
							return new FailedCoords(false, x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i), StatCollector.translateToLocal("greenhouse.stairs"));
						}
						if (getStairs(w.getBlock(x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i)))) {
							if (!check) {
								setStairs(x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i), type("d2"), 2);
							}
							return new FailedCoords(false, x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i), StatCollector.translateToLocal("greenhouse.stairs"));
						}
					}
				}
			}
		}

		return new FailedCoords(true, 0, 0, 0, StatCollector.translateToLocal("locator.none"));
	}

	public FailedCoords sides(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = 1; i <= 3; i++) {
			if (i != 2) {
				if (getPlanks(w.getBlock(x + (hX * 2) + (fX * i), y - 1, z + (hZ * 2) + (fZ * i)))) {
					if (!check) {
						setPlanks(x + (hX * 2) + (fX * i), y - 1, z + (hZ * 2) + (fZ * i));
					}
					return new FailedCoords(false, x + (hX * 2) + (fX * i), y - 1, z + (hZ * 2) + (fZ * i), StatCollector.translateToLocal("greenhouse.planks"));
				}

				if (getPlanks(w.getBlock(x + (hoX * 2) + (fX * i), y - 1, z + (hoZ * 2) + (fZ * i)))) {
					if (!check) {
						setPlanks(x + (hoX * 2) + (fX * i), y - 1, z + (hoZ * 2) + (fZ * i));
					}
					return new FailedCoords(false, x + (hoX * 2) + (fX * i), y - 1, z + (hoZ * 2) + (fZ * i), StatCollector.translateToLocal("greenhouse.planks"));
				}

				for (int s = 0; s <= 1; s++) {
					if (getGlass(w.getBlock(x + (hX * 2) + (fX * i), y + s, z + (hZ * 2) + (fZ * i)))) {
						if (!check) {
							setGlass(x + (hX * 2) + (fX * i), y + s, z + (hZ * 2) + (fZ * i));
						}
						return new FailedCoords(false, x + (hX * 2) + (fX * i), y + s, z + (hZ * 2) + (fZ * i), StatCollector.translateToLocal("greenhouse.glass"));
					}
					if (getGlass(w.getBlock(x + (hoX * 2) + (fX * i), y + s, z + (hoZ * 2) + (fZ * i)))) {
						if (!check) {
							setGlass(x + (hoX * 2) + (fX * i), y + s, z + (hoZ * 2) + (fZ * i));
						}
						return new FailedCoords(false, x + (hoX * 2) + (fX * i), y + s, z + (hoZ * 2) + (fZ * i), StatCollector.translateToLocal("greenhouse.glass"));
					}

				}
			}

			if (getPlanks(w.getBlock(x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i)))) {
				if (!check) {
					setPlanks(x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i));
				}
				return new FailedCoords(false, x + (hX * 2) + (fX * i), y + 2, z + (hZ * 2) + (fZ * i), StatCollector.translateToLocal("greenhouse.planks"));
			}
			if (getPlanks(w.getBlock(x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i)))) {
				if (!check) {
					setPlanks(x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i));
				}
				return new FailedCoords(false, x + (hoX * 2) + (fX * i), y + 2, z + (hoZ * 2) + (fZ * i), StatCollector.translateToLocal("greenhouse.planks"));
			}
		}
		for (int Y = 0; Y <= 1; Y++) {

			if (getLog(w.getBlock(x + (hX * 2) + (fX * 2), y + Y, z + (hZ * 2) + (fZ * 2)))) {
				if (!check) {
					setLog(x + (hX * 2) + (fX * 2), y + Y, z + (hZ * 2) + (fZ * 2));
				}
				return new FailedCoords(false, x + (hX * 2) + (fX * 2), y + Y, z + (hZ * 2) + (fZ * 2), StatCollector.translateToLocal("greenhouse.logs"));
			}

			if (getLog(w.getBlock(x + (hoX * 2) + (fX * 2), y + Y, z + (hoZ * 2) + (fZ * 2)))) {
				if (!check) {
					setLog(x + (hoX * 2) + (fX * 2), y + Y, z + (hoZ * 2) + (fZ * 2));
				}
				return new FailedCoords(false, x + (hoX * 2) + (fX * 2), y + Y, z + (hoZ * 2) + (fZ * 2), StatCollector.translateToLocal("greenhouse.logs"));
			}

		}
		return new FailedCoords(true, 0, 0, 0, StatCollector.translateToLocal("locator.none"));
	}

	public FailedCoords end(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = 1; i <= 2; i++) {
			if (getLog(w.getBlock(x, y + i, z))) {
				if (!check) {
					setLog(x, y + i, z);
				}
				return new FailedCoords(false, x, y + i, z, StatCollector.translateToLocal("greenhouse.logs"));
			}

		}

		for (int i = 0; i <= 2; i++) {

			if (getLog(w.getBlock(x + (hX * 2), y + i, z + (hZ * 2)))) {
				if (!check) {
					setLog(x + (hX * 2), y + i, z + (hZ * 2));
				}
				return new FailedCoords(false, x + (hX * 2), y + i, z + (hZ * 2), StatCollector.translateToLocal("greenhouse.logs"));
			}

			if (getLog(w.getBlock(x + (hoX * 2), y + i, z + (hoZ * 2)))) {
				if (!check) {
					setLog(x + (hoX * 2), y + i, z + (hoZ * 2));
				}
				return new FailedCoords(false, x + (hoX * 2), y + i, z + (hoZ * 2), StatCollector.translateToLocal("greenhouse.logs"));
			}

		}

		for (int i = 0; i <= 2; i++) {
			if (getGlass(w.getBlock(x + (hX), y + i, z + (hZ)))) {
				if (!check) {
					setGlass(x + (hX), y + i, z + (hZ));
				}
				return new FailedCoords(false, x + (hX), y + i, z + (hZ), StatCollector.translateToLocal("greenhouse.glass"));

			}

			if (getGlass(w.getBlock(x + (hoX), y + i, z + (hoZ)))) {
				if (!check) {
					setGlass(x + (hoX), y + i, z + (hoZ));
				}
				return new FailedCoords(false, x + (hoX), y + i, z + (hoZ), StatCollector.translateToLocal("greenhouse.glass"));
			}

		}

		return new FailedCoords(true, 0, 0, 0, StatCollector.translateToLocal("locator.none"));

	}

	public FailedCoords start(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ) {
		int x = xCoord + (getForward().offsetX * 4);
		int y = yCoord;
		int z = zCoord + (getForward().offsetZ * 4);
		for (int i = 0; i <= 2; i++) {

			if (getLog(w.getBlock(x + (hX * 2), y + i, z + (hZ * 2)))) {
				if (!check) {
					setLog(x + (hX * 2), y + i, z + (hZ * 2));
				}
				return new FailedCoords(false, x + (hX * 2), y + i, z + (hZ * 2), StatCollector.translateToLocal("greenhouse.logs"));

			}

			if (getLog(w.getBlock(x + (hoX * 2), y + i, z + (hoZ * 2)))) {
				if (!check) {
					setLog(x + (hoX * 2), y + i, z + (hoZ * 2));
				}
				return new FailedCoords(false, x + (hoX * 2), y + i, z + (hoZ * 2), StatCollector.translateToLocal("greenhouse.logs"));

			}

		}
		for (int i = 0; i <= 2; i++) {

			if (getPlanks(w.getBlock(x + (hX * 1), y + i, z + (hZ * 1)))) {
				if (!check) {
					setPlanks(x + (hX * 1), y + i, z + (hZ * 1));
				}
				return new FailedCoords(false, x + (hX * 1), y + i, z + (hZ * 1), StatCollector.translateToLocal("greenhouse.planks"));

			}

			if (getPlanks(w.getBlock(x + (hoX * 1), y + i, z + (hoZ * 1)))) {
				if (!check) {
					setPlanks(x + (hoX * 1), y + i, z + (hoZ * 1));
				}
				return new FailedCoords(false, x + (hoX * 1), y + i, z + (hoZ * 1), StatCollector.translateToLocal("greenhouse.planks"));

			}

			if (i == 2) {
				if (getPlanks(w.getBlock(x, y + i, z))) {
					if (!check) {
						setPlanks(x, y + i, z);
					}
					return new FailedCoords(false, x, y + i, z, StatCollector.translateToLocal("greenhouse.planks"));

				}
			}
		}

		return new FailedCoords(true, 0, 0, 0, StatCollector.translateToLocal("locator.none"));

	}

	public String getRequiredStacks() {

		if (slots[0] == null) {
			return StatCollector.translateToLocal("greenhouse.logStack") + " " + StatCollector.translateToLocal("greenhouse.isEmpty");
		}
		if (slots[1] == null) {
			return StatCollector.translateToLocal("greenhouse.stairsStack") + " " + StatCollector.translateToLocal("greenhouse.isEmpty");
		}
		if (slots[2] == null) {
			return StatCollector.translateToLocal("greenhouse.glassStack") + " " + StatCollector.translateToLocal("greenhouse.isEmpty");
		}
		if (slots[3] == null) {
			return StatCollector.translateToLocal("greenhouse.plankStack") + " " + StatCollector.translateToLocal("greenhouse.isEmpty");
		}

		if (slots[0] != null && slots[1] != null && slots[2] != null && slots[3] != null) {
			if (!checkLog(Block.getBlockFromItem(slots[0].getItem()))) {
				return StatCollector.translateToLocal("greenhouse.logStack") + " " + StatCollector.translateToLocal("greenhouse.noLogs");
			}
			if (!checkStairs(Block.getBlockFromItem(slots[1].getItem()))) {
				return StatCollector.translateToLocal("greenhouse.stairsStack") + " " + StatCollector.translateToLocal("greenhouse.noStairs");
			}
			if (!checkGlass(Block.getBlockFromItem(slots[2].getItem()))) {
				return StatCollector.translateToLocal("greenhouse.glassStack") + " " + StatCollector.translateToLocal("greenhouse.noGlass");
			}
			if (!checkPlanks(Block.getBlockFromItem(slots[3].getItem()))) {
				return StatCollector.translateToLocal("greenhouse.plankStack") + " " + StatCollector.translateToLocal("greenhouse.noPlanks");
			}
			if (!(slots[0].stackSize >= stackLog)) {
				String logs = StatCollector.translateToLocal("greenhouse.requires") + " " + (stackLog - slots[0].stackSize) + " " + StatCollector.translateToLocal("greenhouse.moreLogs");
				return logs;
			}
			if (!(slots[1].stackSize >= stackStairs)) {
				String stairs = StatCollector.translateToLocal("greenhouse.requires") + " " + (stackStairs - slots[1].stackSize) + " " + StatCollector.translateToLocal("greenhouse.moreStairs");
				return stairs;
			}
			if (!(slots[2].stackSize >= stackGlass)) {
				String stairs = StatCollector.translateToLocal("greenhouse.requires") + " " + (stackGlass - slots[2].stackSize) + " " + StatCollector.translateToLocal("greenhouse.moreGlass");
				return stairs;
			}
			if (!(slots[3].stackSize >= stackPlanks)) {
				String stairs = StatCollector.translateToLocal("greenhouse.requires") + " " + (stackPlanks - slots[3].stackSize) + " " + StatCollector.translateToLocal("greenhouse.morePlanks");
				return stairs;
			}
		}
		return StatCollector.translateToLocal("locator.unknown");
	}

	/*
	 * isMulti Types -1 = Being Built 0 = Not Complete 1 = Complete (Checks haven't been run) 2 = Complete
	 */

}