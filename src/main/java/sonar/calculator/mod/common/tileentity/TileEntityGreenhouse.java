package sonar.calculator.mod.common.tileentity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.machines.IGreenhouse;
import sonar.core.SonarCore;
import sonar.core.api.SonarAPI;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.api.machines.IPausable;
import sonar.core.api.utils.ActionType;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.InventoryHelper.IInventoryFilter;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.SonarHelper;
import sonar.core.integration.planting.IFertiliser;
import sonar.core.integration.planting.IHarvester;
import sonar.core.integration.planting.IPlanter;
import sonar.core.network.sync.SyncEnum;
import sonar.core.network.sync.SyncTagType;
import sonar.core.network.sync.SyncTagType.INT;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.SonarCompat;

public abstract class TileEntityGreenhouse extends TileEntityEnergyInventory implements IGreenhouse, IPausable, IByteBufTile {

	public enum State {
		INCOMPLETE, BUILDING, COMPLETED, DEMOLISHING
	}

	public SyncEnum<State> houseState = new SyncEnum(State.values(), 0);
	public SyncTagType.INT carbon = (INT) new SyncTagType.INT(1).addSyncType(SyncType.DROP);
	public SyncTagType.BOOLEAN wasBuilt = new SyncTagType.BOOLEAN(2);
	public SyncTagType.BOOLEAN paused = new SyncTagType.BOOLEAN(3);

	public int maxLevel, plantTicks, planting, houseSize, checkTicks;
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

	public TileEntityGreenhouse() {
		syncList.addParts(houseState, carbon, wasBuilt, paused);
	}

	@Override
	public void update() {
		super.update();
		forward = EnumFacing.getFront(this.getBlockMetadata()).getOpposite();
		horizontal = SonarHelper.getHorizontal(forward);
	}

	public void checkTile() {
		if (checkTicks >= 0 && this.checkTicks != 50) {
			checkTicks++;
		}

		if (checkTicks == 50) {
			checkTicks = 0;
			if (checkStructure(GreenhouseAction.CHECK).getBoolean()) {
				if (!wasBuilt.getObject()) {
					setGas(0);
					wasBuilt.setObject(true);
				}
				houseState.setObject(State.COMPLETED);
				addFarmland();
			} else {
				houseState.setObject(State.INCOMPLETE);
			}
		}
	}

	public static class PlantableFilter implements IInventoryFilter {

		@Override
		public boolean allowed(ItemStack stack) {
			return isSeed(stack);
		}
	}

	public enum GreenhouseAction {
		CAN_BUILD, CHECK, BUILD, DEMOLISH
	}

	public abstract FailedCoords checkStructure(GreenhouseAction action);

	public abstract ArrayList<BlockPos> getPlantArea();

	public abstract void addFarmland();

	public static boolean isSeed(ItemStack stack) {
		return !SonarCompat.isEmpty(stack) && stack.getItem() instanceof IPlantable;
	}

	protected void growCrops(int repeat) {
		if (isClient()) {
			return;
		}
		ArrayList<BlockPos> plantArea = (ArrayList<BlockPos>) getPlantArea().clone();
		for (int i = 0; i < repeat; i++) {
			if (this.storage.getEnergyStored() > this.growthRF) {
				int rand = SonarCore.randInt(0, plantArea.size() - 1);
				BlockPos pos = plantArea.get(rand);
				IBlockState state = getWorld().getBlockState(pos);
				Block block = state.getBlock();
				if (block != null) {
					for (IFertiliser fertiliser : SonarCore.fertilisers.getObjects()) {
						if (fertiliser.canFertilise(getWorld(), pos, state) && fertiliser.canGrow(getWorld(), pos, state, false)) {
							fertiliser.grow(getWorld(), SonarCore.rand, pos, state);
						}
					}
				}
				this.storage.modifyEnergyStored(-growthRF);
			}
		}
	}

	protected void harvestCrops() {
		if (isClient() || this.storage.getEnergyStored() < this.plantRF) {
			return;
		}
		for (BlockPos pos : (ArrayList<BlockPos>) getPlantArea().clone()) {
			IBlockState state = getWorld().getBlockState(pos);
			Block block = state.getBlock();
			if (block != null) {
				for (IHarvester harvester : SonarCore.harvesters.getObjects()) {
					if (harvester.canHarvest(getWorld(), pos, state) && harvester.isReady(getWorld(), pos, state)) {
						List<ItemStack> stacks = harvester.getDrops(getWorld(), pos, state, type);
						if (stacks != null) {
							addHarvestedStacks(stacks, pos, false, true);
							harvester.harvest(getWorld(), pos, state, false);
						}
						this.storage.modifyEnergyStored(-growthRF);
					}
				}
			}
		}
	}

	protected void addHarvestedStacks(List<ItemStack> array, BlockPos pos, boolean keepBlock, boolean isCrops) {
		boolean keptBlock = !keepBlock;
		for (ItemStack stack : array) {
			if (!SonarCompat.isEmpty(stack)) {
				if (!keptBlock && stack.getItem() instanceof IPlantable) {
					keptBlock = true;
					continue;
				}
				StoredItemStack storedstack = new StoredItemStack(stack.copy());
				for (TileEntity tile : Arrays.asList(this, this.getWorld().getTileEntity(this.pos.offset(forward.getOpposite())))) {
					StoredItemStack returned = SonarAPI.getItemHelper().addItems(tile, storedstack, isCrops ? EnumFacing.getFront(0) : EnumFacing.UP, ActionType.PERFORM, null);
					if (returned != null)
						storedstack.stored -= returned.getStackSize();
					if (!isCrops) {
						break;
					}
				}
				if (storedstack != null && storedstack.stored > 0) {
					EntityItem drop = new EntityItem(getWorld(), this.pos.offset(forward.getOpposite()).getX(), this.pos.offset(forward.getOpposite()).getY(), this.pos.offset(forward.getOpposite()).getZ(), storedstack.getFullStack());
					getWorld().spawnEntityInWorld(drop);
				}

				if (isCrops && this.type == 3)
					this.plantsHarvested++;
			}
		}
	}

	protected void plantCrops() {
		if (this.storage.getEnergyStored() < this.plantRF) {
			return;
		}
		for (BlockPos pos : (ArrayList<BlockPos>) getPlantArea().clone()) {
			IBlockState oldState = getWorld().getBlockState(pos);
			Block block = oldState.getBlock();
			if (!block.isAir(oldState, getWorld(), pos) && !block.isReplaceable(getWorld(), pos)) {
				// can't plant here!
				continue;
			}
			ItemStack seeds = getAvailableSeedStack();
			if (!SonarCompat.isEmpty(seeds)) {
				IPlanter planter = getPlanter(seeds);

				if (planter != null) {
					IBlockState state = planter.getPlant(seeds, getWorld(), pos);
					plantCrop(pos, state, seeds);
				}
			}

		}
	}

	private ItemStack getAvailableSeedStack() {
		for (ItemStack stack : getCropStacks()) {
			if (!SonarCompat.isEmpty(stack) && SonarCompat.getCount(stack) > 0) {
				return stack;
			}
		}
		return SonarCompat.getEmpty();
	}

	private IPlanter getPlanter(ItemStack stack) {
		if (SonarCompat.isEmpty(stack))
			return null;
		for (IPlanter planter : SonarCore.planters.getObjects()) {
			if (planter.canTierPlant(stack, type)) {
				return planter;
			}
		}
		return null;
	}

	public void plantCrop(BlockPos pos, IBlockState state, ItemStack stack) {
		if (state == null) {
			return;
		}

		this.storage.modifyEnergyStored(-plantRF);
		int size = SonarCompat.getCount(stack) - 1;
		stack = SonarCompat.setCount(stack, size);
		getWorld().setBlockState(pos, state, 3);
	}
	
	private int getSlotOffset() {
		switch (type) {
		case 2:
			return 8;
		case 1:
			return 5;
		default:
			return 1;
		}
	}

	public List<ItemStack> getCropStacks() {
		List<ItemStack> stacks = new ArrayList<>();
		List<ItemStack> seedSlots = slots();
		int offset = getSlotOffset();
		for (int j = 0; j < 9; j++) {
			ItemStack stack = seedSlots.get(j + offset);
			if (!SonarCompat.isEmpty(stack) && isSeed(stack)) {
				stacks.add(stack);
			}
		}
		return stacks;
	}

	/** id = Gas to set. (Carbon=0) (Oxygen=1). set = amount to set it to **/
	public void setGas(int set) {
		if (set <= this.maxLevel) {
			this.carbon.setObject(set);
		}
	}

	@Override
	public int getTier() {
		return type;
	}

	@Override
	public int getOxygen() {
		return maxLevel - carbon.getObject();
	}

	@Override
	public int getCarbon() {
		return carbon.getObject();
	}

	@Override
	public int maxGasLevel() {
		return maxLevel;
	}

	/** id = Gas to add to. (Carbon=0) (Oxygen=1). add = amount to add **/
	public void addGas(int add) {
		int carbonLevels = carbon.getObject();

		if (carbonLevels + add < this.maxLevel && carbonLevels + add >= 0) {
			carbon.setObject(carbonLevels + add);
		} else {
			if (carbonLevels + add > this.maxLevel) {
				setGas(maxLevel);
			}
			if (carbonLevels + add < 0) {
				setGas(0);
			}
		}
	}

	public int type(String string) {
		int meta = this.getBlockMetadata();
		if (string.equals("r")) {
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
		if (string.equals("l")) {
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
		if (string.equals("d")) {
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
		if (string.equals("d2")) {
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

	@Override
	public State getState() {
		return houseState.getObject();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
		switch (houseState.getObject()) {
		case BUILDING:
			currenttip.add(FontHelper.translate("locator.state") + ": " + FontHelper.translate("greenhouse.building"));
			break;
		case INCOMPLETE:
			currenttip.add(FontHelper.translate("locator.state") + ": " + FontHelper.translate("greenhouse.incomplete"));
			break;
		case COMPLETED:
			currenttip.add(FontHelper.translate("locator.state") + ": " + FontHelper.translate("greenhouse.complete"));
			break;
		case DEMOLISHING:
			currenttip.add(FontHelper.translate("locator.state") + ": " + "Demolishing");
			break;
		default:
			break;
		}

		DecimalFormat dec = new DecimalFormat("##.##");
		int oxygen = getOxygen();
		int carbon = getCarbon();
		if (carbon != 0) {
			String carbonString = FontHelper.translate("greenhouse.carbon") + ": " + dec.format(carbon * 100 / 100000) + '%';
			currenttip.add(carbonString);
		}
		if (oxygen != 0) {
			String oxygenString = FontHelper.translate("greenhouse.oxygen") + ": " + dec.format(oxygen * 100 / 100000) + '%';
			currenttip.add(oxygenString);
		}
		currenttip.add(String.valueOf(storage.getEnergyStored()));
		return currenttip;
	}

	@Override
	public void writePacket(ByteBuf buf, int id) {
		if (id == 3) {
			onPause();
		}
	}

	@Override
	public void readPacket(ByteBuf buf, int id) {
		if (id == 3) {
			onPause();
		}
	}

	@Override
	public void onPause() {
		paused.invert();
	}

	@Override
	public boolean isActive() {
		return !paused.getObject();
	}

	@Override
	public boolean isPaused() {
		return paused.getObject();
	}
}
