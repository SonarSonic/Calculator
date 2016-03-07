package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse;
import sonar.calculator.mod.integration.agricraft.AgriCraftAPIWrapper;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;
import sonar.core.network.sync.SyncEnergyStorage;
import sonar.core.utils.BlockCoords;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.InventoryHelper;
import sonar.core.utils.helpers.RenderHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityAdvancedGreenhouse extends TileEntityGreenhouse implements ISidedInventory {

	public int plants, lanterns, levelTicks, checkTicks, growTicks, growTick;

	public int stackStairs = 183;
	public int stackLog = 31;
	public int stackPlanks = 42;
	public int stackGlass = 94;

	public final int requiredBuildEnergy = (stackStairs + stackLog + stackPlanks + stackGlass) * CalculatorConfig.getInteger("Build Energy");

	public TileEntityAdvancedGreenhouse() {

		super.storage = new SyncEnergyStorage(350000, 1600);
		super.slots = new ItemStack[17];
		super.type = 2;
		super.maxLevel = 100000;
		super.plantTick = 10;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
			return;
		}
		if (!this.isBeingBuilt()) {
			checkTile();
		}
		if (this.isCompleted()) {
			this.setCompleted();
			if (!this.worldObj.isRemote) {
				extraTicks();
			}
			plant();
			growTicks();
			harvestCrops();

		} else if (this.isBeingBuilt()) {
			createMultiblock();
		}
		discharge(7);

		this.markDirty();
	}


	public List<BlockCoords> getPlantArea(){
		List<BlockCoords> coords = new ArrayList();
		int fX = getForward().offsetX;
		int fZ = getForward().offsetZ;
		int x = xCoord + (4 * fX);
		int y = yCoord;
		int z = zCoord + (4 * fZ);
		for (int Z = -3; Z <= 3; Z++) {
			for (int X = -3; X <= 3; X++) {
				coords.add(new BlockCoords(x + X, y, z + Z));
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
			this.levelTicks = 0;
			InventoryHelper.extractItems(this.getWorldObj().getTileEntity(xCoord + (getForward().getOpposite().offsetX), yCoord, zCoord + (getForward().getOpposite().offsetZ)), this, 0, 0, new PlantableFilter());
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
			int add = (this.plants / 5 * 8) - (this.lanterns * 50);
			this.addGas(-add);
		}
		if (!day) {

			int add = (this.plants / 5 * 2) + (this.lanterns * 50);
			this.addGas(add);
		}

	}

	/** gets plants inside greenhouse and sets it to this.plants **/
	private void getPlants() {
		this.plants = 0;
		int x = this.xCoord + (getForward().offsetX * 4);
		int z = this.zCoord + (getForward().offsetZ * 4);
		for (int Z = -3; Z <= 3; Z++) {
			for (int X = -3; X <= 3; X++) {
				if (this.worldObj.getBlock(x + X, yCoord, z + Z) instanceof IGrowable) {
					this.plants++;

				}
			}
		}
	}

	/** gets lanterns inside greenhouse and sets it to this.lanterns **/
	private void getLanterns() {
		this.lanterns = 0;
		int x = (this.xCoord + getForward().offsetX * 4);
		int y = this.yCoord;
		int z = (this.zCoord + getForward().offsetZ * 4);
		for (int Z = -3; Z <= 3; Z++) {
			for (int X = -3; X <= 3; X++) {
				for (int Y = 0; Y <= 5; Y++) {
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
		if (slots[0] != null && slots[1] != null && slots[2] != null && slots[3] != null && slots[4] != null && slots[5] != null && slots[6] != null) {
			if (slots[0].stackSize >= stackLog && checkLog(Block.getBlockFromItem(slots[0].getItem()))) {
				if (slots[1].stackSize + slots[2].stackSize + slots[3].stackSize >= stackStairs && checkStairs(Block.getBlockFromItem(slots[1].getItem())) && checkStairs(Block.getBlockFromItem(slots[2].getItem())) && checkStairs(Block.getBlockFromItem(slots[3].getItem()))) {
					if (slots[4].stackSize + slots[5].stackSize >= stackGlass && checkGlass(Block.getBlockFromItem(slots[4].getItem())) && checkGlass(Block.getBlockFromItem(slots[5].getItem()))) {
						if (slots[6].stackSize >= stackPlanks && checkPlanks(Block.getBlockFromItem(slots[6].getItem()))) {
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

		int x = xCoord + (fX * 4);
		int y = yCoord;
		int z = zCoord + (fZ * 4);

		for (int Z = -5; Z <= 5; Z++) {
			for (int X = -5; X <= 5; X++) {
				for (int Y = -1; Y <= 9; Y++) {
					if (!GreenhouseHelper.r(worldObj, x + X, y + Y, z + Z)) {
						if (!(this.worldObj.getTileEntity(x + X, y + Y, z + Z) == this)) {
							return new FailedCoords(false, x + X, y + Y, z + Z, "none");
						}
					}
				}
			}
		}

		return new FailedCoords(true, 0, 0, 0, "none");
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
							this.setCompleted();
						}
					}
				}
			}
		}

	}

	/** Checks Green House Structure **/
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

			return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));
		}
		return new FailedCoords(false, 0, 0, 0, "Something went wrong...");
	}

	/** Hoes the ground **/
	private void addFarmland() {
		ForgeDirection forward = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
		int x = xCoord + 4 * forward.offsetX;
		int y = yCoord;
		int z = zCoord + 4 * forward.offsetZ;

		for (int Z = -3; Z <= 3; Z++) {
			for (int X = -3; X <= 3; X++) {
				AgriCraftAPIWrapper.getInstance().removeWeeds(worldObj, x + X, y, z + Z, false);
				if (X == 3 && Z == 3) {
					if (this.storage.getEnergyStored() >= waterRF) {
						if (GreenhouseHelper.applyWater(worldObj, x + X, y, z + Z)) {
							this.storage.modifyEnergyStored(-waterRF);
						}
					}
				} else if (X == -3 && Z == -3) {
					if (this.storage.getEnergyStored() >= waterRF) {
						if (GreenhouseHelper.applyWater(worldObj, x + X, y, z + Z)) {
							this.storage.modifyEnergyStored(-waterRF);
						}
					}
				} else if (X == 3 && Z == -3) {
					if (this.storage.getEnergyStored() >= waterRF) {
						if (GreenhouseHelper.applyWater(worldObj, x + X, y, z + Z)) {
							this.storage.modifyEnergyStored(-waterRF);
						}
					}
				} else if (X == -3 && Z == 3) {
					if (this.storage.getEnergyStored() >= waterRF) {
						if (GreenhouseHelper.applyWater(worldObj, x + X, y, z + Z)) {
							this.storage.modifyEnergyStored(-waterRF);
						}
					}
				} else {
					if (this.storage.getEnergyStored() >= farmlandRF) {
						if (GreenhouseHelper.applyFarmland(worldObj, x + X, y, z + Z)) {
							this.storage.modifyEnergyStored(-farmlandRF);
						}
					}
				}

			}
		}
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
				this.storage.modifyEnergyStored(-buildRF);
			}
		}

	}

	public void setGlass(int x, int y, int z) {
		if (slots[4] == null) {

			if (slots[5] == null) {
				this.setIncomplete();

			}
		}

		if (slots[4] != null) {
			Block glass1 = Block.getBlockFromItem(slots[4].getItem());
			int damage = slots[4].getItemDamage();
			if (!checkGlass(glass1)) {
				this.setIncomplete();

			} else if (glass1 != null) {

				slots[4].stackSize--;
				if (slots[4].stackSize == 1) {
					slots[4] = null;
				}

				this.worldObj.setBlock(x, y, z, glass1, damage, 2);
				this.storage.modifyEnergyStored(-buildRF);
			}
		} else if (slots[5] != null) {
			Block glass2 = Block.getBlockFromItem(slots[5].getItem());
			int damage = slots[5].getItemDamage();
			if (!checkGlass(glass2)) {
				this.setIncomplete();

			} else if (glass2 != null) {

				slots[5].stackSize--;
				if (slots[5].stackSize == 1) {
					slots[5] = null;
				}
				this.worldObj.setBlock(x, y, z, glass2, damage, 2);
				this.storage.modifyEnergyStored(-buildRF);
			}
		}

	}

	public void setPlanks(int x, int y, int z) {
		if (slots[6] == null) {
			this.setIncomplete();
		}
		if (slots[6] != null) {

			Block planks = Block.getBlockFromItem(slots[6].getItem());
			int damage = slots[6].getItemDamage();
			if (!checkPlanks(planks)) {
				this.setIncomplete();

			} else if (planks != null) {

				slots[6].stackSize--;
				if (slots[6].stackSize == 1) {
					slots[6] = null;
				}
				this.worldObj.setBlock(x, y, z, planks, damage, 2);

				this.storage.modifyEnergyStored(-buildRF);
			}
		}
	}

	public void setStairs(int x, int y, int z, int meta, int flag) {
		if (slots[1] == null) {
			if (slots[2] == null) {
				if (slots[3] == null) {
					this.setIncomplete();
				}
			}
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
				this.storage.modifyEnergyStored(-buildRF);
			}
		} else if (slots[2] != null) {

			Block stairs2 = Block.getBlockFromItem(slots[2].getItem());
			if (!checkStairs(stairs2)) {
				this.setIncomplete();

			} else if (stairs2 != null) {

				slots[2].stackSize--;
				if (slots[2].stackSize == 1) {
					slots[2] = null;
				}
				this.worldObj.setBlock(x, y, z, stairs2, meta, flag);
				this.storage.modifyEnergyStored(-buildRF);
			}
		} else if (slots[3] != null) {

			Block stairs3 = Block.getBlockFromItem(slots[3].getItem());
			if (!checkStairs(stairs3)) {
				this.setIncomplete();

			} else if (stairs3 != null) {

				slots[3].stackSize--;
				if (slots[3].stackSize == 1) {
					slots[3] = null;
				}
				this.worldObj.setBlock(x, y, z, stairs3, meta, flag);
				this.storage.modifyEnergyStored(-buildRF);
			}
		}

	}

	public FailedCoords roof(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		if (getPlanks(w.getBlock(x + (hX * 1), y + 6, z + (hZ * 1)))) {
			if (!check) {
				setPlanks(x + (hX * 1), y + 6, z + (hZ * 1));
			}
			return new FailedCoords(false, x + (hX * 1), y + 6, z + (hZ * 1), FontHelper.translate("greenhouse.planks"));
		}
		if (getPlanks(w.getBlock(x + (hoX * 1), y + 6, z + (hoZ * 1)))) {
			if (!check) {
				setPlanks(x + (hoX * 1), y + 6, z + (hoZ * 1));
			}
			return new FailedCoords(false, x + (hoX * 1), y + 6, z + (hoZ * 1), FontHelper.translate("greenhouse.planks"));

		}

		for (int i = -1; i <= 9; i++) {
			for (int s = 3; s <= 7; s++) {

				if (getStairs(w.getBlock(x + (hX * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fX * i), y + s, z + (hZ * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hX * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fX * i), y + s, z + (hZ * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fZ * i), type("r"), 2);
					}
					return new FailedCoords(false, x + (hX * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fX * i), y + s, z + (hZ * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fZ * i), FontHelper.translate("greenhouse.stairs"));

				}
				if (getStairs(w.getBlock(x + (hoX * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fX * i), y + s, z + (hoZ * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hoX * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fX * i), y + s, z + (hoZ * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fZ * i), type("l"), 2);
					}
					return new FailedCoords(false, x + (hoX * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fX * i), y + s, z + (hoZ * intValues(s, FontHelper.translate("greenhouse.stairs"))) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}
				if (getPlanks(w.getBlock(x + (fX * i), y + 7, zCoord + +(fZ * i)))) {
					if (!check) {
						setPlanks(x + (fX * i), y + 7, zCoord + (fZ * i));
					}
					return new FailedCoords(false, x + (fX * i), y + 7, zCoord + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}

			}
		}

		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));
	}

	public FailedCoords underroof(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = -1; i <= 9; i++) {
			if (i != 0 && i != 4 && i != 8) {

				if (getStairs(w.getBlock(x + (hX * 4) + (fX * i), y + 3, z + (hZ * 4) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hX * 4) + (fX * i), y + 3, z + (hZ * 4) + (fZ * i), type("d"), 2);
					}
					return new FailedCoords(false, x + (hX * 4) + (fX * i), y + 3, z + (hZ * 4) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}
			}

			if (i != 0 && i != 8) {

				if (getStairs(w.getBlock(x + (hX * 3) + (fX * i), y + 4, z + (hZ * 3) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hX * 3) + (fX * i), y + 4, z + (hZ * 3) + (fZ * i), type("d"), 2);
					}
					return new FailedCoords(false, x + (hX * 3) + (fX * i), y + 4, z + (hZ * 3) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}

				if (getStairs(w.getBlock(x + (hX * 2) + (fX * i), y + 5, z + (hZ * 2) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hX * 2) + (fX * i), y + 5, z + (hZ * 2) + (fZ * i), type("d"), 2);
					}
					return new FailedCoords(false, x + (hX * 2) + (fX * i), y + 5, z + (hZ * 2) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}
				if (getStairs(w.getBlock(x + (hX * 1) + (fX * i), y + 6, z + (hZ * 1) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hX * 1) + (fX * i), y + 6, z + (hZ * 1) + (fZ * i), type("d"), 2);
					}
					return new FailedCoords(false, x + (hX * 1) + (fX * i), y + 6, z + (hZ * 1) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}

			}

			if (i != 0 && i != 4 && i != 8) {

				if (getStairs(w.getBlock(x + (hoX * 4) + (fX * i), y + 3, z + (hoZ * 4) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hoX * 4) + (fX * i), y + 3, z + (hoZ * 4) + (fZ * i), type("d2"), 2);
					}
					return new FailedCoords(false, x + (hoX * 4) + (fX * i), y + 3, z + (hoZ * 4) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}

			}

			if (i != 0 && i != 8) {

				if (getStairs(w.getBlock(x + (hoX * 3) + (fX * i), y + 4, z + (hoZ * 3) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hoX * 3) + (fX * i), y + 4, z + (hoZ * 3) + (fZ * i), type("d2"), 2);
					}
					return new FailedCoords(false, x + (hoX * 3) + (fX * i), y + 4, z + (hoZ * 3) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}

				if (getStairs(w.getBlock(x + (hoX * 2) + (fX * i), y + 5, z + (hoZ * 2) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hoX * 2) + (fX * i), y + 5, z + (hoZ * 2) + (fZ * i), type("d2"), 2);
					}
					return new FailedCoords(false, x + (hoX * 2) + (fX * i), y + 5, z + (hoZ * 2) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}

				if (getStairs(w.getBlock(x + (hoX * 1) + (fX * i), y + 6, z + (hoZ * 1) + (fZ * i)))) {
					if (!check) {
						setStairs(x + (hoX * 1) + (fX * i), y + 6, z + (hoZ * 1) + (fZ * i), type("d2"), 2);
					}
					return new FailedCoords(false, x + (hoX * 1) + (fX * i), y + 6, z + (hoZ * 1) + (fZ * i), FontHelper.translate("greenhouse.stairs"));
				}

			}
		}

		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));
	}

	public FailedCoords sides(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = 1; i <= 7; i++) {
			if (i != 4) {
				for (int s = 0; s <= 2; s++) {

					if (s == 0) {
						if (getPlanks(w.getBlock(x + (hX * 4) + (fX * i), y + s, z + (hZ * 4) + (fZ * i)))) {
							if (!check) {
								setPlanks(x + (hX * 4) + (fX * i), y + s, z + (hZ * 4) + (fZ * i));
							}
							return new FailedCoords(false, x + (hX * 4) + (fX * i), y + s, z + (hZ * 4) + (fZ * i), FontHelper.translate("greenhouse.planks"));
						}
						if (getPlanks(w.getBlock(x + (hoX * 4) + (fX * i), y + s, z + (hoZ * 4) + (fZ * i)))) {
							if (!check) {
								setPlanks(x + (hoX * 4) + (fX * i), y + s, z + (hoZ * 4) + (fZ * i));
							}
							return new FailedCoords(false, x + (hoX * 4) + (fX * i), y + s, z + (hoZ * 4) + (fZ * i), FontHelper.translate("greenhouse.planks"));
						}
					} else {
						if (getGlass(w.getBlock(x + (hX * 4) + (fX * i), y + s, z + (hZ * 4) + (fZ * i)))) {
							if (!check) {
								setGlass(x + (hX * 4) + (fX * i), y + s, z + (hZ * 4) + (fZ * i));
							}
							return new FailedCoords(false, x + (hX * 4) + (fX * i), y + s, z + (hZ * 4) + (fZ * i), FontHelper.translate("greenhouse.glass"));
						}
						if (getGlass(w.getBlock(x + (hoX * 4) + (fX * i), y + s, z + (hoZ * 4) + (fZ * i)))) {
							if (!check) {
								setGlass(x + (hoX * 4) + (fX * i), y + s, z + (hoZ * 4) + (fZ * i));
							}
							return new FailedCoords(false, x + (hoX * 4) + (fX * i), y + s, z + (hoZ * 4) + (fZ * i), FontHelper.translate("greenhouse.glass"));
						}

					}

				}
			}
		}

		for (int Y = 0; Y <= 3; Y++) {

			if (getLog(w.getBlock(x + (hX * 4) + (fX * 4), y + Y, z + (hZ * 4) + (fZ * 4)))) {
				if (!check) {
					setLog(x + (hX * 4) + (fX * 4), y + Y, z + (hZ * 4) + (fZ * 4));
				}
				return new FailedCoords(false, x + (hX * 4) + (fX * 4), y + Y, z + (hZ * 4) + (fZ * 4), FontHelper.translate("greenhouse.logs"));
			}

			if (getLog(w.getBlock(x + (hoX * 4) + (fX * 4), y + Y, z + (hoZ * 4) + (fZ * 4)))) {
				if (!check) {
					setLog(x + (hoX * 4) + (fX * 4), y + Y, z + (hoZ * 4) + (fZ * 4));
				}
				return new FailedCoords(false, x + (hoX * 4) + (fX * 4), y + Y, z + (hoZ * 4) + (fZ * 4), FontHelper.translate("greenhouse.logs"));
			}

		}
		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));
	}

	public FailedCoords end(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ, int x, int y, int z) {

		for (int i = 1; i <= 6; i++) {
			if (getLog(w.getBlock(x, y + i, z))) {
				if (!check) {
					setLog(x, y + i, z);
				}
				return new FailedCoords(false, x, y + i, z, FontHelper.translate("greenhouse.logs"));
			}

		}
		for (int i = 0; i <= 3; i++) {

			if (getLog(w.getBlock(x + (hX * 4), y + i, z + (hZ * 4)))) {
				if (!check) {
					setLog(x + (hX * 4), y + i, z + (hZ * 4));
				}
				return new FailedCoords(false, x + (hX * 4), y + i, z + (hZ * 4), FontHelper.translate("greenhouse.logs"));
			}

			if (getLog(w.getBlock(x + (hoX * 4), y + i, z + (hoZ * 4)))) {
				if (!check) {
					setLog(x + (hoX * 4), y + i, z + (hoZ * 4));
				}
				return new FailedCoords(false, x + (hoX * 4), y + i, z + (hoZ * 4), FontHelper.translate("greenhouse.logs"));
			}

		}

		for (int i = 1; i <= 3; i++) {
			if (getPlanks(w.getBlock(x + (hX * i), y - 1, z + (hZ * i)))) {
				if (!check) {
					setPlanks(x + (hX * i), y - 1, z + (hZ * i));
				}
				return new FailedCoords(false, x + (hX * i), y - 1, z + (hZ * i), FontHelper.translate("greenhouse.planks"));

			}

			if (getPlanks(w.getBlock(x + (hoX * i), y - 1, z + (hoZ * i)))) {
				if (!check) {
					setPlanks(x + (hoX * i), y - 1, z + (hoZ * i));
				}
				return new FailedCoords(false, x + (hoX * i), y - 1, z + (hoZ * i), FontHelper.translate("greenhouse.planks"));
			}

		}

		for (int j = 1; j <= 3; j++) {

			if (j != 3) {
				for (int i = 0; i <= 5; i++) {
					if (getGlass(w.getBlock(x + (hX * j), y + i, z + (hZ * j)))) {
						if (!check) {
							setGlass(x + (hX * j), y + i, z + (hZ * j));
						}
						return new FailedCoords(false, x + (hX * j), y + i, z + (hZ * j), FontHelper.translate("greenhouse.glass"));

					}

					if (getGlass(w.getBlock(x + (hoX * j), y + i, z + (hoZ * j)))) {
						if (!check) {
							setGlass(x + (hoX * j), y + i, z + (hoZ * j));
						}
						return new FailedCoords(false, x + (hoX * j), y + i, z + (hoZ * j), FontHelper.translate("greenhouse.glass"));
					}

				}
			}
			if (j == 3) {
				for (int i = 0; i <= 4; i++) {

					if (getGlass(w.getBlock(x + (hX * j), y + i, z + (hZ * j)))) {
						if (!check) {
							setGlass(x + (hX * j), y + i, z + (hZ * j));
						}
						return new FailedCoords(false, x + (hX * j), y + i, z + (hZ * j), FontHelper.translate("greenhouse.glass"));
					}

					if (getGlass(w.getBlock(x + (hoX * j), y + i, z + (hoZ * j)))) {
						if (!check) {
							setGlass(x + (hoX * j), y + i, z + (hoZ * j));
						}
						return new FailedCoords(false, x + (hoX * j), y + i, z + (hoZ * j), FontHelper.translate("greenhouse.glass"));

					}

				}
			}

		}
		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));

	}

	public FailedCoords start(boolean check, World w, int hX, int hZ, int hoX, int hoZ, int fX, int fZ) {
		int x = xCoord + (getForward().offsetX * 8);
		int y = yCoord;
		int z = zCoord + (getForward().offsetZ * 8);
		for (int i = 0; i <= 3; i++) {

			if (getLog(w.getBlock(x + (hX * 4), y + i, z + (hZ * 4)))) {
				if (!check) {
					setLog(x + (hX * 4), y + i, z + (hZ * 4));
				}
				return new FailedCoords(false, x + (hX * 4), y + i, z + (hZ * 4), FontHelper.translate("greenhouse.logs"));

			}

			if (getLog(w.getBlock(x + (hoX * 4), y + i, z + (hoZ * 4)))) {
				if (!check) {
					setLog(x + (hoX * 4), y + i, z + (hoZ * 4));
				}
				return new FailedCoords(false, x + (hoX * 4), y + i, z + (hoZ * 4), FontHelper.translate("greenhouse.logs"));

			}

		}
		for (int i = 0; i <= 5; i++) {

			if (i <= 4) {
				if (getGlass(w.getBlock(x + (hX * 3), y + i, z + (hZ * 3)))) {
					if (!check) {
						setGlass(x + (hX * 3), y + i, z + (hZ * 3));
					}
					return new FailedCoords(false, x + (hX * 3), y + i, z + (hZ * 3), FontHelper.translate("greenhouse.glass"));
				}

				if (getGlass(w.getBlock(x + (hoX * 3), y + i, z + (hoZ * 3)))) {
					if (!check) {
						setGlass(x + (hoX * 3), y + i, z + (hoZ * 3));
					}
					return new FailedCoords(false, x + (hoX * 3), y + i, z + (hoZ * 3), FontHelper.translate("greenhouse.glass"));

				}

			}
			if (getGlass(w.getBlock(x + (hX * 2), y + i, z + (hZ * 2)))) {
				if (!check) {
					setGlass(x + (hX * 2), y + i, z + (hZ * 2));
				}
				return new FailedCoords(false, x + (hX * 2), y + i, z + (hZ * 2), FontHelper.translate("greenhouse.glass"));
			}

			if (getGlass(w.getBlock(x + (hoX * 2), y + i, z + (hoZ * 2)))) {
				if (!check) {
					setGlass(x + (hoX * 2), y + i, z + (hoZ * 2));
				}
				return new FailedCoords(false, x + (hoX * 2), y + i, z + (hoZ * 2), FontHelper.translate("greenhouse.glass"));

			}

		}
		for (int i = 0; i <= 6; i++) {
			if (i > 2) {
				if (getGlass(w.getBlock(x + (hX * 1), y + i, z + (hZ * 1)))) {
					if (!check) {
						setGlass(x + (hX * 1), y + i, z + (hZ * 1));
					}
					return new FailedCoords(false, x + (hX * 1), y + i, z + (hZ * 1), FontHelper.translate("greenhouse.glass"));

				}

				if (getGlass(w.getBlock(x + (hoX * 1), y + i, z + (hoZ * 1)))) {
					if (!check) {
						setGlass(x + (hoX * 1), y + i, z + (hoZ * 1));
					}
					return new FailedCoords(false, x + (hoX * 1), y + i, z + (hoZ * 1), FontHelper.translate("greenhouse.glass"));

				}

				if (getGlass(w.getBlock(x, y + i, z))) {
					if (!check) {
						setGlass(x, y + i, z);
					}
					return new FailedCoords(false, x, y + i, z, FontHelper.translate("greenhouse.glass"));

				}

			}
			if (i <= 2) {
				if (getPlanks(w.getBlock(x + (hX * 1), y + i, z + (hZ * 1)))) {
					if (!check) {
						setPlanks(x + (hX * 1), y + i, z + (hZ * 1));
					}
					return new FailedCoords(false, x + (hX * 1), y + i, z + (hZ * 1), FontHelper.translate("greenhouse.planks"));

				}

				if (getPlanks(w.getBlock(x + (hoX * 1), y + i, z + (hoZ * 1)))) {
					if (!check) {
						setPlanks(x + (hoX * 1), y + i, z + (hoZ * 1));
					}
					return new FailedCoords(false, x + (hoX * 1), y + i, z + (hoZ * 1), FontHelper.translate("greenhouse.planks"));

				}
			}
			if (i == 2) {
				if (getPlanks(w.getBlock(x, y + i, z))) {
					if (!check) {
						setPlanks(x, y + i, z);
					}
					return new FailedCoords(false, x, y + i, z, FontHelper.translate("greenhouse.planks"));

				}
			}
		}

		for (int i = 2; i <= 3; i++) {

			if (getPlanks(w.getBlock(x + (hX * i), y - 1, z + (hZ * i)))) {
				if (!check) {
					setPlanks(x + (hX * i), y - 1, z + (hZ * i));
				}
				return new FailedCoords(false, x + (hX * i), y - 1, z + (hZ * i), FontHelper.translate("greenhouse.planks"));

			}

			if (getPlanks(w.getBlock(x + (hoX * i), y - 1, z + (hoZ * i)))) {
				if (!check) {
					setPlanks(x + (hoX * i), y - 1, z + (hoZ * i));
				}
				return new FailedCoords(false, x + (hoX * i), y - 1, z + (hoZ * i), FontHelper.translate("greenhouse.planks"));

			}

		}
		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));

	}

	public String getRequiredStacks() {

		if (slots[0] == null) {
			return FontHelper.translate("greenhouse.logStack") + " " + FontHelper.translate("greenhouse.isEmpty");
		}
		if (slots[1] == null) {
			return FontHelper.translate("greenhouse.stairsStack") + " 1 " + FontHelper.translate("greenhouse.isEmpty");
		}
		if (slots[2] == null) {
			return FontHelper.translate("greenhouse.stairsStack") + " 2 " + FontHelper.translate("greenhouse.isEmpty");
		}
		if (slots[3] == null) {
			return FontHelper.translate("greenhouse.stairsStack") + " 3 " + FontHelper.translate("greenhouse.isEmpty");
		}
		if (slots[4] == null) {
			return FontHelper.translate("greenhouse.glassStack") + " 1 " + FontHelper.translate("greenhouse.isEmpty");
		}
		if (slots[5] == null) {
			return FontHelper.translate("greenhouse.glassStack") + " 2 " + FontHelper.translate("greenhouse.isEmpty");
		}
		if (slots[6] == null) {
			return FontHelper.translate("greenhouse.plankStack") + " " + FontHelper.translate("greenhouse.isEmpty");
		}

		if (slots[0] != null && slots[1] != null && slots[2] != null && slots[3] != null && slots[4] != null && slots[5] != null && slots[6] != null) {
			if (!checkLog(Block.getBlockFromItem(slots[0].getItem()))) {
				return FontHelper.translate("greenhouse.logStack") + "  " + FontHelper.translate("greenhouse.noLogs");
			}
			if (!checkStairs(Block.getBlockFromItem(slots[1].getItem()))) {
				return FontHelper.translate("greenhouse.stairsStack") + " 1 " + FontHelper.translate("greenhouse.noStairs");
			}
			if (!checkStairs(Block.getBlockFromItem(slots[2].getItem()))) {
				return FontHelper.translate("greenhouse.stairsStack") + " 2 " + FontHelper.translate("greenhouse.noStairs");
			}
			if (!checkStairs(Block.getBlockFromItem(slots[3].getItem()))) {
				return FontHelper.translate("greenhouse.stairsStack") + " 3 " + FontHelper.translate("greenhouse.noStairs");
			}
			if (!checkGlass(Block.getBlockFromItem(slots[4].getItem()))) {
				return FontHelper.translate("greenhouse.glassStack") + " 1 " + FontHelper.translate("greenhouse.noGlass");
			}
			if (!checkGlass(Block.getBlockFromItem(slots[5].getItem()))) {
				return FontHelper.translate("greenhouse.glassStack") + " 2 " + FontHelper.translate("greenhouse.noGlass");
			}
			if (!checkPlanks(Block.getBlockFromItem(slots[6].getItem()))) {
				return FontHelper.translate("greenhouse.plankStack") + " " + FontHelper.translate("greenhouse.noPlanks");
			}
			if (!(slots[0].stackSize >= stackLog)) {
				String logs = FontHelper.translate("greenhouse.requires") + " " + (stackLog - slots[0].stackSize) + " " + FontHelper.translate("greenhouse.moreLogs");
				return logs;
			}
			if (!(slots[1].stackSize + slots[2].stackSize + slots[3].stackSize >= stackStairs)) {
				String stairs = FontHelper.translate("greenhouse.requires") + " " + (stackStairs - slots[1].stackSize - slots[2].stackSize - slots[3].stackSize) + " " + FontHelper.translate("greenhouse.moreStairs");
				return stairs;
			}
			if (!(slots[4].stackSize + slots[5].stackSize >= stackGlass)) {
				String glass = FontHelper.translate("greenhouse.requires") + " " + (stackGlass - slots[4].stackSize - slots[5].stackSize) + " " + FontHelper.translate("greenhouse.moreGlass");
				return glass;
			}
			if (!(slots[6].stackSize >= stackPlanks)) {
				String planks = FontHelper.translate("greenhouse.requires") + " " + (stackPlanks - slots[6].stackSize) + " " + FontHelper.translate("greenhouse.morePlanks");
				return planks;
			}
		}
		return FontHelper.translate("locator.unknown");
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {

		switch (this.isMulti) {
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
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 8, 9, 10, 11, 12, 13, 14, 15, 16 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return item != null && item.getItem() instanceof IPlantable;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return false;
	}

}