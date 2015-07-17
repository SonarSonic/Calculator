package sonar.calculator.mod.common.tileentity.machines;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse.PlantableFilter;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.calculator.mod.integration.planting.IPlanter;
import sonar.calculator.mod.integration.planting.PlanterRegistry;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.InventoryHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.RenderHelper;
import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityFlawlessGreenhouse extends TileEntityGreenhouse implements ISidedInventory {

	public int plants, levelTicks, checkTicks, houseSize, growTicks, growTick;
	public int plantsGrown;

	public int requiredPlantEnergy = 20;
	public int requiredGrowEnergy = 50;

	public TileEntityFlawlessGreenhouse() {
		super.storage = new EnergyStorage(500000, 500000);
		super.slots = new ItemStack[10];
		super.type = 3;
		super.maxLevel = 100000;
		super.plantTick = 2;

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
				if (this.storage.getEnergyStored() >= requiredGrowEnergy) {
					if (growCrop(3, this.houseSize)) {
						if (!this.worldObj.isRemote)
							plantsGrown++;
						this.storage.modifyEnergyStored(-requiredGrowEnergy);
					}
				}
			}
			this.growTicks = 0;
		}

	}

	private void harvestCrops() {
		int hX = RenderHelper.getHorizontal(getForward()).offsetX;
		int hZ = RenderHelper.getHorizontal(getForward()).offsetZ;

		int hoX = RenderHelper.getHorizontal(getForward()).getOpposite().offsetX;
		int hoZ = RenderHelper.getHorizontal(getForward()).getOpposite().offsetZ;

		int fX = getForward().offsetX;
		int fZ = getForward().offsetZ;

		for (int i = 0; i <= this.houseSize; i++) {
			for (int XZ = 1; XZ <= 2; XZ++) {
				Block target = this.worldObj.getBlock(xCoord + (hX * XZ) + (fX * (1 + i)), yCoord, zCoord + (hZ * XZ) + (fZ * (1 + i)));
				if (target instanceof IGrowable) {
					harvest(worldObj, xCoord + (hX * XZ) + (fX * (1 + i)), yCoord, zCoord + (hZ * XZ) + (fZ * (1 + i)), (IGrowable) target);
				}

			}
		}
	}

	@Override
	public boolean plant(ItemStack stack, int slot) {
		int hX = RenderHelper.getHorizontal(getForward()).offsetX;
		int hZ = RenderHelper.getHorizontal(getForward()).offsetZ;

		int hoX = RenderHelper.getHorizontal(getForward()).getOpposite().offsetX;
		int hoZ = RenderHelper.getHorizontal(getForward()).getOpposite().offsetZ;

		int fX = getForward().offsetX;
		int fZ = getForward().offsetZ;

		IPlanter planter = PlanterRegistry.getPlanter(stack);
		Block crop = planter.getCropFromStack(stack);
		int meta = planter.getMetaFromStack(stack);
		if (crop == null) {
			return false;
		}
		for (int i = 0; i <= this.houseSize; i++) {
			for (int XZ = 1; XZ <= 2; XZ++) {
				if (canPlant(this.worldObj, xCoord + (hX * XZ) + (fX * (1 + i)), yCoord, zCoord + (hZ * XZ) + (fZ * (1 + i)), slot, ((IPlantable) stack.getItem()))) {
					this.worldObj.setBlock(xCoord + (hX * XZ) + (fX * (1 + i)), yCoord, zCoord + (hZ * XZ) + (fZ * (1 + i)), Blocks.air, 0, 1 | 2);
					this.worldObj.setBlock(xCoord + (hX * XZ) + (fX * (1 + i)), yCoord, zCoord + (hZ * XZ) + (fZ * (1 + i)), crop, meta, 1 | 2);

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

	public void extraTicks() {
		if (levelTicks == 15) {
			this.getPlants();
		}
		if (this.levelTicks >= 0 && this.levelTicks != 20) {
			levelTicks++;
		}
		if (this.levelTicks == 20) {
			this.levelTicks = 0;
			InventoryHelper.extractItems(this.getWorldObj().getTileEntity(xCoord + (getForward().getOpposite().offsetX), yCoord, zCoord + (getForward().getOpposite().offsetZ)), this, 0, 0,
					new PlantableFilter());
			gasLevels();

		}
	}

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

			FailedCoords size = checkSize(true, this.worldObj, hX, hZ, hoX, hoZ, fX, fZ, xCoord, yCoord, zCoord);

			if (!size.getBoolean()) {
				return size;
			}

			return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));
		}
		return new FailedCoords(false, 0, 0, 0, "Something went wrong...");
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
		TileEntity tile = this.worldObj.getTileEntity(xCoord + (RenderHelper.getHorizontal(getForward()).offsetX * 3), yCoord, zCoord + (RenderHelper.getHorizontal(getForward()).offsetZ) * 3);
		if (tile != null && tile instanceof TileEntityCO2Generator) {
			TileEntityCO2Generator generator = (TileEntityCO2Generator) tile;
			return generator.gasAdd;
		}
		return 0;
	}

	/** gets plants inside greenhouse and sets it to this.plants **/
	private void getPlants() {
		int hX = RenderHelper.getHorizontal(getForward()).offsetX;
		int hZ = RenderHelper.getHorizontal(getForward()).offsetZ;

		int hoX = RenderHelper.getHorizontal(getForward()).getOpposite().offsetX;
		int hoZ = RenderHelper.getHorizontal(getForward()).getOpposite().offsetZ;

		int fX = getForward().offsetX;
		int fZ = getForward().offsetZ;

		this.plants = 0;

		for (int i = 0; i <= this.houseSize; i++) {
			for (int XZ = 1; XZ <= 2; XZ++) {
				if (this.worldObj.getBlock(xCoord + (hX * XZ) + (fX * (1 + i)), yCoord, zCoord + (hZ * XZ) + (fZ * (1 + i))) instanceof IGrowable) {
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
		int hX = RenderHelper.getHorizontal(getForward()).offsetX;
		int hZ = RenderHelper.getHorizontal(getForward()).offsetZ;

		int hoX = RenderHelper.getHorizontal(getForward()).getOpposite().offsetX;
		int hoZ = RenderHelper.getHorizontal(getForward()).getOpposite().offsetZ;

		int fX = getForward().offsetX;
		int fZ = getForward().offsetZ;

		int x = (this.xCoord + getForward().offsetX * 2);
		int y = this.yCoord;
		int z = (this.zCoord + getForward().offsetZ * 2);

		for (int i = 0; i <= this.houseSize; i++) {
			for (int XZ = 0; XZ <= 3; XZ++) {
				if (XZ != 1 && XZ != 2) {
					if (this.storage.getEnergyStored() >= 1000) {
						if (GreenhouseHelper.applyWater(worldObj, xCoord + (hX * XZ) + (fX * (1 + i)), yCoord, zCoord + (hZ * XZ) + (fZ * (1 + i)))) {
							this.storage.modifyEnergyStored(-1000);
						}
					}
				} else {
					if (this.storage.getEnergyStored() >= 50) {
						if (GreenhouseHelper.applyFarmland(worldObj, xCoord + (hX * XZ) + (fX * (1 + i)), yCoord, zCoord + (hZ * XZ) + (fZ * (1 + i)))) {
							this.storage.modifyEnergyStored(-50);
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

	public boolean stableStone(Block block) {
		if (block == Calculator.stablestoneBlock) {
			return false;
		}
		if (block == Calculator.flawlessGreenhouse) {
			return false;
		}
		if (block == Calculator.carbondioxideGenerator) {
			return false;
		}
		return true;
	}

	public boolean flawlessGlass(Block block) {
		if (block == Calculator.flawlessGlass) {
			return false;
		}
		return true;
	}

	public boolean slabQuartz(int x, int y, int z) {
		Block block = this.worldObj.getBlock(x, y, z);
		if (block == Blocks.stone_slab) {
			if (block.getDamageValue(this.worldObj, x, y, z) == 7) {
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

		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));
	}

	public FailedCoords middle(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = 0; i <= 3; i++) {
			if (slabQuartz(x + (hX * i), y + 2, z + (hZ * i))) {
				return new FailedCoords(false, x + (hX * i), y + 2, z + (hZ * i), FontHelper.translate("greenhouse.quartz"));
			}

		}
		for (int i = 0; i <= 1; i++) {
			if (flawlessGlass(w.getBlock(x + (hX * 3), y + i, z + (hZ * 3)))) {
				return new FailedCoords(false, x + (hX * 3), y + i, z + (hZ * 3), FontHelper.translate("greenhouse.glass"));
			}
			if (flawlessGlass(w.getBlock(x, y + i, z))) {
				return new FailedCoords(false, x, y + i, z, FontHelper.translate("greenhouse.glass"));
			}
		}

		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));
	}

	public FailedCoords end(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = 0; i <= 3; i++) {
			if (stableStone(w.getBlock(x + (hX * i), y - 1, z + (hZ * i)))) {
				return new FailedCoords(false, x + (hX * i), y - 1, z + (hZ * i), FontHelper.translate("greenhouse.stable"));
			}
			if (slabQuartz(x + (hX * i), y + 2, z + (hZ * i))) {
				return new FailedCoords(false, x + (hX * i), y + 2, z + (hZ * i), FontHelper.translate("greenhouse.quartz"));
			}

		}
		for (int i = 0; i <= 1; i++) {
			if (stableStone(w.getBlock(x + (hX * 3), y + i, z + (hZ * 3)))) {
				return new FailedCoords(false, x + (hX * 3), y + i, z + (hZ * 3), FontHelper.translate("greenhouse.stable"));
			}
			if (stableStone(w.getBlock(x, y + i, z))) {
				return new FailedCoords(false, x, y + i, z, FontHelper.translate("greenhouse.stable"));
			}
		}
		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return true;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return item != null && item.getItem() instanceof IPlantable;
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		currenttip.add(FontHelper.translate("greenhouse.size") + ": " + this.houseSize);
		return super.getWailaInfo(currenttip);
	}
}