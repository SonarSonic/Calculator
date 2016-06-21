package sonar.calculator.mod.common.tileentity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.machines.IGreenhouse;
import sonar.calculator.mod.integration.planting.IHarvester;
import sonar.calculator.mod.integration.planting.IPlanter;
import sonar.core.api.SonarAPI;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.api.utils.ActionType;
import sonar.core.api.utils.BlockCoords;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.InventoryHelper.IInventoryFilter;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.RenderHelper;

public abstract class TileEntityGreenhouse extends TileEntityEnergyInventory implements IGreenhouse {

	public int wasBuilt, isMulti, maxLevel, carbonLevels, plantTicks, planting, houseSize;
	public int plantsHarvested, plantsGrown;
	public int plantTick;
	public int type;
	public final int growthRF = CalculatorConfig.getInteger("Growth Energy");
	public final int plantRF = CalculatorConfig.getInteger("Plant Energy");
	public final int buildRF = CalculatorConfig.getInteger("Build Energy");
	public final int farmlandRF = CalculatorConfig.getInteger("Adding Farmland");
	public final int waterRF = CalculatorConfig.getInteger("Adding Water");
	public EnumFacing forward = EnumFacing.NORTH;
	public EnumFacing horizontal = EnumFacing.EAST;

	public void onLoaded(){
		super.onLoaded();
		forward = EnumFacing.getFront(this.getBlockMetadata()).getOpposite();
		horizontal = RenderHelper.getHorizontal(forward);
	}

	public static class PlantableFilter implements IInventoryFilter {

		@Override
		public boolean allowed(ItemStack stack) {
			return isSeed(stack);
		}
	}

	public abstract ArrayList<BlockCoords> getPlantArea();

	public static boolean isSeed(ItemStack stack) {
		return stack != null && stack.getItem() instanceof IPlantable;
	}

	protected void harvestCrops() {
		if (!(this.storage.getEnergyStored() > this.growthRF)) {
			return;
		}
		for (BlockCoords coord : (ArrayList<BlockCoords>) getPlantArea().clone()) {
			Block block = coord.getBlock();
			if (block != null && block.isReplaceable(worldObj, coord.getBlockPos())) {
				for (IHarvester harvester : Calculator.harvesters.getObjects()) {
					IBlockState state = worldObj.getBlockState(coord.getBlockPos());
					if (harvester.canHarvest(worldObj, coord.getBlockPos(), state)) {
						List<ItemStack> stacks = harvester.getDrops(worldObj, coord.getBlockPos(), state, type);
						if (stacks != null) {
							addHarvestedStacks(stacks, coord);
						}
					}
				}
			}
		}
	}

	protected void addHarvestedStacks(List<ItemStack> array, BlockCoords coord) {
		for (ItemStack stack : array) {
			if (stack != null) {
				TileEntity tile = this.getWorld().getTileEntity(pos.offset(forward));
				StoredItemStack storedstack = new StoredItemStack(stack);
				StoredItemStack harvest = SonarAPI.getItemHelper().addItems(tile, storedstack.copy(), EnumFacing.getFront(0), ActionType.PERFORM, null);
				storedstack.remove(harvest);
				if (storedstack != null && storedstack.stored > 0) {
					EntityItem drop = new EntityItem(worldObj, pos.offset(forward).getX(), pos.offset(forward).getY(), pos.offset(forward).getZ(), storedstack.getFullStack());
					worldObj.spawnEntityInWorld(drop);
				}
				worldObj.setBlockToAir(coord.getBlockPos());
				if (this.type == 3)
					this.plantsHarvested++;
			}
		}

	}

	protected void plantCrops() {
		if (!(this.storage.getEnergyStored() > this.plantRF)) {
			return;
		}
		for (BlockCoords coord : (ArrayList<BlockCoords>) getPlantArea().clone()) {
			Block block = coord.getBlock(worldObj);
			if ((block == null || block.isAir(getWorld(), pos) || block.isReplaceable(worldObj, coord.getBlockPos()))) {
				for (IPlanter planter : Calculator.planters.getObjects()) {
					for (Integer slot : getInvPlants()) {
						ItemStack stack = slots()[slot];
						if (stack != null && planter.canTierPlant(slots()[slot], type)) {
							IBlockState state = planter.getPlant(stack, worldObj, coord.getBlockPos());
							if (state != null) {
								storage.extractEnergy(plantRF, false);
								slots()[slot].stackSize--;
								if (slots()[slot].stackSize == 0) {
									slots()[slot] = null;
								}
								worldObj.setBlockState(coord.getBlockPos(), state, 3);
								return;
							}
						}
					}
				}
			}
		}
	}
	
	public List<Integer> getInvPlants() {
		List<Integer> plants = new ArrayList();

		if (type == 2) {
			for (int j = 0; j < 9; j++) {
				if (slots()[8 + j] != null) {
					if (isSeed(slots()[8 + j])) {
						plants.add(8 + j);
					}
				}
			}
		}
		if (type == 1) {
			for (int j = 0; j < 9; j++) {
				if (slots()[5 + j] != null) {
					if (isSeed(slots()[5 + j])) {
						plants.add(5 + j);
					}
				}
			}
		}
		if (type == 3) {
			for (int j = 0; j < 9; j++) {
				if (slots()[1 + j] != null) {
					if (isSeed(slots()[1 + j])) {
						plants.add(1 + j);
					}
				}
			}
		}
		return plants;
	}

	/** id = Gas to set. (Carbon=0) (Oxygen=1). set = amount to set it to **/
	public void setGas(int set) {
		if (set <= this.maxLevel) {
			this.carbonLevels = set;
		}
	}

	public enum BlockType {
		LOG, GLASS, PLANKS, STAIRS;
		public boolean checkBlock(Item item) {
			Block block = Block.getBlockFromItem(item);
			if (block == null) {
				return false;
			}
			switch (this) {
			case LOG:
				return checkLog(block);
			case GLASS:
				return checkGlass(block);
			case PLANKS:
				return checkPlanks(block);
			case STAIRS:
				return checkStairs(block);
			}
			return false;
		}
	}

	public void setBlockType(int x, int y, int z, int[] slots, BlockType type, int meta) {
		boolean found = false;
		for (int i = 0; i < slots.length; i++) {
			int slot = slots[i];
			if (slot < slots().length) {
				ItemStack target = slots()[slot];
				if (target != null && type.checkBlock(target.getItem())) {
					found = true;
					Block block = Block.getBlockFromItem(target.getItem());
					slots()[slot].stackSize--;
					if (slots()[slot].stackSize == 1) {
						slots()[slot] = null;
					}
					if (meta == -1) {
						this.worldObj.setBlockState(new BlockPos(x, y, z), block.getStateFromMeta(target.getItemDamage()), 2);
					} else {
						this.worldObj.setBlockState(new BlockPos(x, y, z), block.getStateFromMeta(meta), 3);
					}
					this.storage.modifyEnergyStored(-buildRF);
					found = true;
					break;
				}
			}
		}
		if (!found) {
			setIncomplete();
		}
	}

	/** checks ore dictionary for registered logs **/
	public static boolean checkLog(Block block) {

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
	public static boolean checkGlass(Block block) {

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
	public static boolean checkStairs(Block block) {

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
	public static boolean checkPlanks(Block block) {

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
		if (block instanceof BlockLog) {
			return true;
		}
		return false;
	}

	public int getTier() {
		return type;
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

	@Override
	public int maxGasLevel() {
		return maxLevel;
	}

	public boolean getLog(int x, int y, int z) {
		Block block = this.worldObj.getBlockState(new BlockPos(x, y, z)).getBlock();
		if (block != null && !checkLog(block)) {
			return true;
		}
		return false;
	}

	public boolean getGlass(int x, int y, int z) {
		Block block = this.worldObj.getBlockState(new BlockPos(x, y, z)).getBlock();
		if (block != null && !checkGlass(block)) {
			return true;
		}
		return false;
	}

	public boolean getPlanks(int x, int y, int z) {
		Block block = this.worldObj.getBlockState(new BlockPos(x, y, z)).getBlock();
		if (block != null && !checkPlanks(block)) {
			return true;
		}
		return false;
	}

	public boolean getStairs(int x, int y, int z) {
		Block block = this.worldObj.getBlockState(new BlockPos(x, y, z)).getBlock();
		if (block != null && !checkStairs(block)) {
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
	
	/** types Basic =1 Advanced =2 Flawless = 3 **/
	public int getInvEmpty() {
		if (this.type == 2) {
			for (int j = 0; j < 9; j++) {
				if (slots()[8 + j] == null) {
					return 8 + j;
				}
			}
		}
		if (this.type == 1) {
			for (int j = 0; j < 9; j++) {
				if (slots()[5 + j] == null) {
					return 5 + j;
				}
			}
		}
		if (this.type == 3) {
			for (int j = 0; j < 9; j++) {
				if (slots()[1 + j] == null) {
					return 1 + j;
				}
			}
		}
		return -1;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
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
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			nbt.setInteger("Multi", this.isMulti);
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
