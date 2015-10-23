package sonar.calculator.mod.common.tileentity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockWood;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.common.block.CalculatorCrops;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;
import sonar.core.common.tileentity.TileEntityInventoryReceiver;
import sonar.core.utils.SonarSeeds;
import sonar.core.utils.SonarSeedsFood;
import sonar.core.utils.helpers.FontHelper;
import sonar.core.utils.helpers.InventoryHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.RenderHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityGreenhouse extends TileEntityInventoryReceiver {

	public int wasBuilt, isMulti, maxLevel, carbonLevels, plantTicks, planting, houseSize;
	public int plantsHarvested, plantsGrown;
	public int plantTick;
	public int requiredPlantEnergy = 20;
	public int type;

	public static class PlantableFilter implements InventoryHelper.IInventoryFilter {

		@Override
		public boolean matches(ItemStack stack) {
			if (stack != null && stack.getItem() instanceof IPlantable) {
				return true;
			}
			return false;
		}
	}

	public void plant() {
		if (this.plantTicks >= 0 && this.plantTicks != this.plantTick) {
			this.plantTicks++;
		}
		if (this.plantTicks == this.plantTick) {
			if (this.storage.getEnergyStored() >= requiredPlantEnergy) {
				if (plantAction()) {
					this.planting = 0;
					this.storage.extractEnergy(requiredPlantEnergy, false);
				}
				this.plantTicks = 0;
			}
		}
	}

	public boolean plantAction() {
		int inv = getInvPlants();
		if (inv != -1 && this.planting == 0) {
			this.planting = 1;
			return plant(slots[inv], inv);

		}
		return false;
	}

	/** checks plant slots for plants */
	public int getInvPlants() {
		if (type == 2) {
			for (int j = 0; j < 9; j++) {
				if (slots[8 + j] != null) {
					if (slots[8 + j].getItem() instanceof IPlantable) {
						return 8 + j;
					}
				}
			}
		}
		if (type == 1) {
			for (int j = 0; j < 9; j++) {
				if (slots[5 + j] != null) {
					if (slots[5 + j].getItem() instanceof IPlantable) {
						return 5 + j;
					}
				}
			}
		}
		if (type == 3) {
			for (int j = 0; j < 9; j++) {
				if (slots[1 + j] != null) {
					if (slots[1 + j].getItem() instanceof IPlantable) {
						return 1 + j;
					}
				}
			}
		}
		return -1;
	}

	/** id = Gas to set. (Carbon=0) (Oxygen=1). set = amount to set it to **/
	public void setGas(int set) {
		if (set <= this.maxLevel) {
			this.carbonLevels = set;
		}
	}

	public boolean growCrop(int house, int size) {
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		int X = 0;
		int Z = 0;
		int XX = 0;
		int ZZ = 0;
		if (house == 1) {
			int fX = getForward().offsetX;
			int fZ = getForward().offsetZ;
			x = xCoord + (2 * fX);
			y = yCoord;
			z = zCoord + (2 * fZ);

			X = (0 + (int) (Math.random() * ((3 - 0) + 3))) - 2;
			Z = (0 + (int) (Math.random() * ((3 - 0) + 3))) - 2;
		}
		if (house == 2) {
			int fX = getForward().offsetX;
			int fZ = getForward().offsetZ;
			x = xCoord + (4 * fX);
			y = yCoord;
			z = zCoord + (4 * fZ);

			X = (0 + (int) (Math.random() * ((6 - 0) + 6))) - 3;
			Z = (0 + (int) (Math.random() * ((6 - 0) + 6))) - 3;
		}
		if (house == 3) {

			int fX = getForward().offsetX;
			int fZ = getForward().offsetZ;

			int hX = RenderHelper.getHorizontal(getForward()).offsetX;
			int hZ = RenderHelper.getHorizontal(getForward()).offsetZ;

			X = fX * (0 + (int) (Math.random() * ((size - 0) + size)));
			Z = fZ * (0 + (int) (Math.random() * ((size - 0) + size)));
			XX = hX * ((1 + (int) (Math.random() * ((2 - 1) + 2))));
			ZZ = hZ * ((1 + (int) (Math.random() * ((2 + 1) + 2))));
			return GreenhouseHelper.applyBonemeal(worldObj, x + X + XX, y, z + Z + ZZ, true);
		}
		return GreenhouseHelper.applyBonemeal(worldObj, x + X + XX, y, z + Z + ZZ, false);
	}

	public boolean plant(ItemStack block, int slot) {
		return false;

	}

	public void harvest(World world, int x, int y, int z, IGrowable target) {
		ArrayList<ItemStack> array = new ArrayList();
		
		if (!target.func_149851_a(world, x, y, z, world.isRemote)) {
			array = world.getBlock(x, y, z).getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		}
		if (!world.isRemote) {
			if (array != null) {

				if (this.type == 3)
					this.plantsHarvested++;

				for (ItemStack stack : array) {
					if (stack != null) {
						ItemStack add = InventoryHelper.addItems(this, stack, 0, new PlantableFilter());

						TileEntity tile = this.getWorldObj().getTileEntity(xCoord + (getForward().getOpposite().offsetX), yCoord, zCoord + (getForward().getOpposite().offsetZ));
						if (add == null) {
							world.setBlockToAir(x, y, z);
						} else if (tile != null) {
							ItemStack harvest = InventoryHelper.addItems(tile, stack, 0, null);
							if (harvest == null) {
								world.setBlockToAir(x, y, z);
							} else {
								EntityItem drop = new EntityItem(world, xCoord + (getForward().getOpposite().offsetX), yCoord, zCoord + (getForward().getOpposite().offsetZ), harvest);
								world.spawnEntityInWorld(drop);
								world.setBlockToAir(x, y, z);
							}
						} else {
							EntityItem drop = new EntityItem(world, xCoord + (getForward().getOpposite().offsetX), yCoord, zCoord + (getForward().getOpposite().offsetZ), add);
							world.spawnEntityInWorld(drop);
							world.setBlockToAir(x, y, z);
						}
					}
				}
			}
		}
	}

	protected boolean checkTierUsage(Item seeds) {
		if (seeds instanceof SonarSeeds) {
			SonarSeeds seed = (SonarSeeds) seeds;
			return seed.canTierUse(this.type);
		}
		if (seeds instanceof SonarSeedsFood) {
			SonarSeedsFood seed = (SonarSeedsFood) seeds;
			return seed.canTierUse(this.type);
		}

		return true;
	}

	/**
	 * checks if crop can be planted at coords
	 * 
	 * @param slot2
	 */
	protected boolean canPlant(World worldObj, int x, int y, int z, int slot, IPlantable plantable) {
		Block target = plantable.getPlant(null, 0, 0, 0);
		Block ground = worldObj.getBlock(x, y - 1, z);
		if (target != null) {
			IPlantable crop = (IPlantable) slots[slot].getItem();
			EnumPlantType type = crop.getPlantType(this.worldObj, 0, 0, 0);
			if (type != null) {
				if (checkTierUsage(this.slots[slot].getItem())) {
					if (target instanceof CalculatorCrops) {

						CalculatorCrops crops = (CalculatorCrops) target;
						if (crops.canPlaceCropsAt(worldObj, x, y, z)) {
							if (ground.canSustainPlant(worldObj, x, y - 1, z, ForgeDirection.UP, plantable)) {
								return true;
							}

						}

					} else {
						if (type == EnumPlantType.Crop) {
							if (target.canPlaceBlockAt(worldObj, x, y, z)) {
								if (ground.canSustainPlant(worldObj, x, y - 1, z, ForgeDirection.UP, plantable)) {
									return true;
								}

							}
						}
						if (type == EnumPlantType.Nether) {

							if (target.canPlaceBlockAt(worldObj, x, y, z)) {
								return true;

							}
						}

					}
				}
			}
		}
		return false;
	}

	/** checks ore dictionary for registered logs **/
	public boolean checkLog(Block block) {

		for (int i = 0; i < OreDictionary.getOres("logWood").size(); i++) {
			if (OreDictionary.getOres("logWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("treeWood").size(); i++) {
			if (OreDictionary.getOres("treeWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		if (block instanceof BlockLog) {
			return true;
		}
		return false;
	}

	/** checks ore dictionary for registered glass **/
	public boolean checkGlass(Block block) {

		for (int i = 0; i < OreDictionary.getOres("blockGlass").size(); i++) {
			if (OreDictionary.getOres("blockGlass").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("blockGlassColorless").size(); i++) {
			if (OreDictionary.getOres("blockGlassColorless").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("paneGlassColorless").size(); i++) {
			if (OreDictionary.getOres("paneGlassColorless").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("paneGlass").size(); i++) {
			if (OreDictionary.getOres("paneGlass").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		if (block instanceof BlockGlass) {
			return true;
		}
		if (block instanceof BlockPane) {
			return true;
		}
		return false;
	}

	/** checks ore dictionary for registered stairs **/
	public boolean checkStairs(Block block) {

		for (int i = 0; i < OreDictionary.getOres("stairWood").size(); i++) {
			if (OreDictionary.getOres("stairWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("stairStone").size(); i++) {
			if (OreDictionary.getOres("stairStone").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("greenhouse.stairs").size(); i++) {
			if (OreDictionary.getOres("stairs").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		if (block == Blocks.stone_stairs) {
			return true;
		}
		if (block == Blocks.stone_brick_stairs) {
			return true;
		}
		if (block == Blocks.sandstone_stairs) {
			return true;
		}
		if (block == Blocks.brick_stairs) {
			return true;
		}
		if (block == Blocks.quartz_stairs) {
			return true;
		}
		if (block == Blocks.nether_brick_stairs) {
			return true;
		}
		if (block instanceof BlockStairs) {
			return true;
		}
		return false;
	}

	/** gets metadata for stairs **/
	public int intValues(int par, String block) {
		if (type == 2) {
			if (block == FontHelper.translate("greenhouse.stairs")) {
				switch (par) {
				case 3:
					return 5;
				case 4:
					return 4;
				case 5:
					return 3;
				case 6:
					return 2;
				case 7:
					return 1;
				}
			}
		}
		if (type == 1) {
			if (block == FontHelper.translate("greenhouse.stairs")) {
				switch (par) {
				case 2:
					return 3;
				case 3:
					return 2;
				case 4:
					return 1;
				}
			}
		}
		return 0;

	}

	/** checks ore dictionary for registered planks **/
	public boolean checkPlanks(Block block) {

		for (int i = 0; i < OreDictionary.getOres("plankWood").size(); i++) {
			if (OreDictionary.getOres("plankWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("planksWood").size(); i++) {
			if (OreDictionary.getOres("planksWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		if (block instanceof BlockWood) {
			return true;
		}
		return false;
	}

	public boolean isCompleted() {
		if (isMulti == 2) {
			return true;
		}
		return false;
	}

	public boolean isBeingBuilt() {
		if (isMulti == -1) {
			return true;
		}
		return false;
	}

	public boolean isIncomplete() {
		if (isMulti == 0) {
			return true;
		}
		return false;
	}

	public boolean wasBuilt() {
		if (wasBuilt == 1) {
			return true;
		}
		return false;
	}

	public void setCompleted() {
		this.isMulti = 2;
	}

	public void setBeingBuilt() {
		this.isMulti = -1;

	}

	public void setIncomplete() {
		this.isMulti = 0;
	}

	public void setWasBuilt() {
		if (!(this.wasBuilt == 1)) {
			this.wasBuilt = 1;
		}
	}

	public int getOxygen() {
		return maxLevel - carbonLevels;
	}

	public int getCarbon() {
		return carbonLevels;
	}

	public boolean getLog(Block block) {
		if (!checkLog(block)) {
			return true;
		}
		return false;
	}

	public boolean getGlass(Block block) {
		if (!checkGlass(block)) {
			return true;
		}
		return false;
	}

	public boolean getPlanks(Block block) {
		if (!checkPlanks(block)) {
			return true;
		}
		return false;
	}

	public boolean getStairs(Block block) {
		if (!checkStairs(block)) {
			return true;
		}
		return false;
	}

	public int type(String string) {
		int meta = this.getBlockMetadata();
		if (string == "r") {
			if (meta == 3) {
				return 1;
			}
			if (meta == 4) {
				return 3;
			}
			if (meta == 5) {
				return 2;
			}
			if (meta == 2) {
				return 0;
			}
		}
		if (string == "l") {
			if (meta == 3) {
				return 0;
			}
			if (meta == 4) {
				return 2;
			}
			if (meta == 5) {
				return 3;
			}
			if (meta == 2) {
				return 1;
			}
		}
		if (string == "d") {
			if (meta == 3) {
				return 4;
			}
			if (meta == 4) {
				return 6;
			}
			if (meta == 5) {
				return 7;
			}
			if (meta == 2) {
				return 5;
			}
		}
		if (string == "d2") {
			if (meta == 3) {
				return 5;
			}
			if (meta == 4) {
				return 7;
			}
			if (meta == 5) {
				return 6;
			}
			if (meta == 2) {
				return 4;
			}
		}
		return 0;

	}

	public ForgeDirection getForward() {

		return ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();

	}

	/**
	 * types Basic =1 Advanced =2 Flawless = 3
	 **/
	public int getInvEmpty() {
		if (this.type == 2) {
			for (int j = 0; j < 9; j++) {
				if (slots[8 + j] == null) {
					return 8 + j;
				}
			}
		}
		if (this.type == 1) {
			for (int j = 0; j < 9; j++) {
				if (slots[5 + j] == null) {
					return 5 + j;
				}
			}
		}
		if (this.type == 3) {
			for (int j = 0; j < 9; j++) {
				if (slots[1 + j] == null) {
					return 1 + j;
				}
			}
		}
		return -1;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SYNC || type == SyncType.SAVE) {
			this.isMulti = nbt.getInteger("Multi");
			this.wasBuilt = nbt.getInteger("wasBuilt");
			this.carbonLevels = nbt.getInteger("Carbon");
		}

		if (type == SyncType.DROP) {
			this.carbonLevels = nbt.getInteger("Carbon");
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SYNC || type == SyncType.SAVE) {
			nbt.setInteger("wasBuilt", this.wasBuilt);
			nbt.setInteger("Carbon", this.carbonLevels);
		}
		if (type == SyncType.DROP) {
			nbt.setInteger("Carbon", this.getCarbon());
			nbt.setInteger("Oxygen", this.getOxygen());
		}
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
		DecimalFormat dec = new DecimalFormat("##.##");
		int oxygen = getOxygen();
		int carbon = getCarbon();
		if (carbon != 0) {
			String carbonString = FontHelper.translate("greenhouse.carbon") + ": " + dec.format(carbon * 100 / 100000) + "%";
			currenttip.add(carbonString);
		}
		if (oxygen != 0) {
			String oxygenString = FontHelper.translate("greenhouse.oxygen") + ": " + dec.format(oxygen * 100 / 100000) + "%";
			currenttip.add(oxygenString);
		}
		return currenttip;
	}
}
