package sonar.calculator.mod.common.tileentity;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse.GreenhouseAction;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse.PlantableFilter;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse.State;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;
import sonar.core.api.SonarAPI;
import sonar.core.api.utils.BlockCoords;
import sonar.core.helpers.FontHelper;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.utils.FailedCoords;

public abstract class TileEntityBuildingGreenhouse extends TileEntityGreenhouse implements IByteBufTile {

	public int requiredStairs = 183;
	public int requiredLogs = 30;
	public int requiredPlanks = 42;
	public int requiredGlass = 94;
	public int levelTicks, growTicks, growTick;
	public int requiredBuildEnergy = 0;

	public abstract int[] getSlotsForType(BlockType type);

	public abstract ArrayList<BlockPlace> getStructure();
	
	public abstract void gasLevels();
	
	public abstract int getPlants();
	
	public abstract int getLanterns();

	public TileEntityBuildingGreenhouse(int requiredStairs, int requiredLogs, int requiredPlanks, int requiredGlass) {
		this.requiredStairs=requiredStairs;
		this.requiredLogs=requiredLogs;
		this.requiredPlanks=requiredPlanks;
		this.requiredGlass=requiredGlass;
		this.requiredBuildEnergy = (requiredStairs + requiredLogs + requiredPlanks + requiredGlass) * buildRF;
	}

	@Override
	public void update() {
		super.update();
		if (this.worldObj.isBlockPowered(pos)) {
			return;
		}
		if (!(houseState.getObject() == State.BUILDING)) {
			checkTile();
		}
		switch (houseState.getObject()) {
		case COMPLETED:
			if (!this.worldObj.isRemote) {
				extraTicks();
			}
			plantCrops();
			growTicks();
			harvestCrops();
			break;
		case BUILDING:
			if (checkStructure(GreenhouseAction.BUILD).getBoolean()) {
				addFarmland();
				this.houseState.setObject(State.COMPLETED);
			}
			break;
		case DEMOLISHING:
			checkStructure(GreenhouseAction.DEMOLISH);
			break;
		default:
			break;
		}
		discharge(7);

		this.markDirty();
	}	

	public void growTicks() {
		if (this.growTicks == 0) {
			this.growTick = GreenhouseHelper.getGrowTicks(this.getOxygen(), 1);
			this.growTicks++;
			return;
		}
		if (growTick != 0 && this.growTicks >= growTick) {
			if (this.storage.getEnergyStored() >= growthRF) {
				this.growTicks = 0;
				growCrops(1);
			}
		} else {
			growTicks++;
		}
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
			SonarAPI.getItemHelper().transferItems(this.getWorld().getTileEntity(pos.offset(forward.getOpposite())), this, EnumFacing.getFront(0), EnumFacing.getFront(0), new PlantableFilter());
			gasLevels();
		}
	}
	
	public enum BlockType {
		LOG, GLASS, PLANKS, STAIRS, NONE;
		public boolean checkBlock(Item item) {
			Block block = Block.getBlockFromItem(item);
			if (block == null) {
				return false;
			}
			switch (this) {
			case LOG:
				return GreenhouseHelper.checkLog(block);
			case GLASS:
				return GreenhouseHelper.checkGlass(block);
			case PLANKS:
				return GreenhouseHelper.checkPlanks(block);
			case STAIRS:
				return GreenhouseHelper.checkStairs(block);
			default:
				break;
			}
			return false;
		}

		public static BlockType getTypeForItem(Item item) {
			for (BlockType type : BlockType.values()) {
				if (type.checkBlock(item)) {
					return type;
				}
			}
			return NONE;
		}
	}

	public static class BlockPlace {
		public BlockType type;
		public BlockPos pos;
		public int meta;

		public BlockPlace(BlockType block, BlockCoords coords, int meta) {
			this(block, coords.getBlockPos(), meta);
		}

		public BlockPlace(BlockType block, int x, int y, int z, int meta) {
			this(block, new BlockPos(x, y, z), meta);
		}

		public BlockPlace(BlockType block, BlockPos pos, int meta) {
			this.type = block;
			this.pos = pos;
			this.meta = meta;
		}
	}

	public void setBlockType(BlockPos pos, int[] slots, BlockType type, int meta) {
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
						this.worldObj.setBlockState(pos, block.getStateFromMeta(target.getItemDamage()), 2);
					} else {
						this.worldObj.setBlockState(pos, block.getStateFromMeta(meta), 3);
					}
					this.storage.modifyEnergyStored(-buildRF);
					found = true;
					break;
				}
			}
		}
		if (!found) {
			houseState.setObject(State.INCOMPLETE);
		}
	}

	/** Checks inventory has resources **/
	public boolean hasRequiredStacks() {
		int logs = 0, stairs = 0, planks = 0, glass = 0;
		for (int i = 0; i < 7; i++) {
			ItemStack stack = slots()[i];
			if (stack == null) {
				return false;
			} else if (GreenhouseHelper.checkLog(Block.getBlockFromItem(stack.getItem()))) {
				logs += stack.stackSize;
			} else if (GreenhouseHelper.checkStairs(Block.getBlockFromItem(stack.getItem()))) {
				stairs += stack.stackSize;
			} else if (GreenhouseHelper.checkPlanks(Block.getBlockFromItem(stack.getItem()))) {
				planks += stack.stackSize;
			} else if (GreenhouseHelper.checkGlass(Block.getBlockFromItem(stack.getItem()))) {
				glass += stack.stackSize;
			}
		}
		return logs >= requiredLogs && stairs >= requiredStairs && planks >= requiredPlanks && glass >= requiredGlass;
	}

	public ArrayList<String> getRequiredStacks() {
		int logs = 0, stairs = 0, planks = 0, glass = 0;
		for (int i = 0; i < 7; i++) {
			ItemStack stack = slots()[i];
			if (stack == null) {
				continue;
			} else if (GreenhouseHelper.checkLog(Block.getBlockFromItem(stack.getItem()))) {
				logs += stack.stackSize;
			} else if (GreenhouseHelper.checkStairs(Block.getBlockFromItem(stack.getItem()))) {
				stairs += stack.stackSize;
			} else if (GreenhouseHelper.checkPlanks(Block.getBlockFromItem(stack.getItem()))) {
				planks += stack.stackSize;
			} else if (GreenhouseHelper.checkGlass(Block.getBlockFromItem(stack.getItem()))) {
				glass += stack.stackSize;
			}
		}
		ArrayList<String> list = new ArrayList<String>();
		list.add("greenhouse.requires");
		if (logs < requiredLogs) {
			list.add(requiredLogs - logs + " " + FontHelper.translate("greenhouse.logs"));
		}
		if (stairs < requiredStairs) {
			list.add(requiredStairs - stairs + " " + FontHelper.translate("greenhouse.stairs"));
		}
		if (planks < requiredPlanks) {
			list.add(requiredPlanks - planks + " " + FontHelper.translate("greenhouse.planks"));
		}
		if (glass < requiredGlass) {
			list.add(requiredGlass - glass + " " + FontHelper.translate("greenhouse.glass"));
		}
		if (list.size() == 1) {
			return new ArrayList();
		}
		return list;
	}
	
	public FailedCoords createBlock() {
		FailedCoords coords = checkStructure(GreenhouseAction.CAN_BUILD);
		if (!(houseState.getObject() == State.BUILDING)) {
			if (coords.getBoolean()) {
				if (this.storage.getEnergyStored() >= this.requiredBuildEnergy) {
					if (this.hasRequiredStacks()) {
						this.houseState.setObject(State.BUILDING);
					}
				}
			}
		}
		return coords;
	}

	/** Checks Green House Structure **/
	public FailedCoords checkStructure(GreenhouseAction action) {
		FailedCoords current = null;
		ArrayList<BlockPlace> blocks = getStructure();
		for (BlockPlace block : blocks) {
			if (block.pos.equals(this.getPos())) {
				continue;
			}
			current = checkBlock(action, block);
			if (current != null) {
				return current;
			}
		}
		if (action == GreenhouseAction.DEMOLISH) {
			ArrayList<BlockCoords> coords = new ArrayList();
			for (int Z = -5; Z <= 5; Z++) {
				for (int X = -5; X <= 5; X++) {
					BlockPos pos = this.pos.add((forward.getFrontOffsetX() * 4) + X, -1, (forward.getFrontOffsetZ() * 4) + Z);
					Block target = worldObj.getBlockState(pos).getBlock();
					if (target == Blocks.DIRT || target == Blocks.FARMLAND || target == Blocks.WATER || target.isReplaceable(worldObj, pos)) {
						worldObj.setBlockState(pos, Blocks.GRASS.getDefaultState(), 2);
					}
				}
			}
			if (this.houseState.getObject() == State.DEMOLISHING) {
				this.houseState.setObject(State.INCOMPLETE);
				this.wasBuilt.setObject(false);
			}
		}
		return new FailedCoords(true, 0, 0, 0, FontHelper.translate("locator.none"));
	}

	public FailedCoords checkBlock(GreenhouseAction action, BlockPlace place) {
		IBlockState state = worldObj.getBlockState(place.pos);
		boolean checkBlock = false;
		switch (place.type) {
		case LOG:
			checkBlock = checkLog(place.pos);
			break;
		case STAIRS:
			checkBlock = checkStairs(place.pos);
			break;
		case PLANKS:
			checkBlock = checkPlanks(place.pos);
			break;
		case GLASS:
			checkBlock = checkGlass(place.pos);
			break;
		default:
			break;
		}

		switch (action) {
		case BUILD:
			if (!checkBlock) {
				setBlockType(place.pos, getSlotsForType(place.type), place.type, place.meta);
				return new FailedCoords(false, place.pos, place.type.toString());
			}
			break;
		case CHECK:
			if (!checkBlock) {
				return new FailedCoords(false, place.pos, place.type.toString());
			}
			break;
		case CAN_BUILD:
			if (checkBlock) {
				break;
			} else if (!GreenhouseHelper.r(worldObj, place.pos) && !state.getBlock().isAir(state, worldObj, place.pos) && !state.getBlock().isReplaceable(worldObj, place.pos)) {
				return new FailedCoords(false, place.pos, "Can't Replace");
			}
			break;
		case DEMOLISH:
			if (checkBlock) {
				List<ItemStack> stacks = state.getBlock().getDrops(worldObj, place.pos, state, 0);
				if (stacks != null) {
					if (stacks.isEmpty() && place.type == BlockType.GLASS) {
						stacks.add(new ItemStack(Item.getItemFromBlock(state.getBlock()), 1, state.getBlock().getMetaFromState(state)));
					}
					addHarvestedStacks(stacks, place.pos, false);
					return new FailedCoords(false, place.pos, place.type.toString());
				}
			}

			break;
		}
		return null;
	}

	/** gets metadata for stairs **/
	public int intValues(int par, BlockType block) {
		if (type == 2) {
			if (block == BlockType.STAIRS) {
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
			if (block == BlockType.STAIRS) {
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

	public boolean checkLog(BlockPos pos) {
		Block block = this.worldObj.getBlockState(pos).getBlock();
		if (block == null || !GreenhouseHelper.checkLog(block)) {
			return false;
		}
		return true;
	}

	public boolean checkGlass(BlockPos pos) {
		Block block = this.worldObj.getBlockState(pos).getBlock();
		if (block == null || !GreenhouseHelper.checkGlass(block)) {
			return false;
		}
		return true;
	}

	public boolean checkPlanks(BlockPos pos) {
		Block block = this.worldObj.getBlockState(pos).getBlock();
		if (block == null || !GreenhouseHelper.checkPlanks(block)) {
			return false;
		}
		return true;
	}

	public boolean checkStairs(BlockPos pos) {
		Block block = this.worldObj.getBlockState(pos).getBlock();
		if (block == null || !GreenhouseHelper.checkStairs(block)) {
			return false;
		}
		return true;
	}

	@Override
	public void writePacket(ByteBuf buf, int id) {

	}

	@Override
	public void readPacket(ByteBuf buf, int id) {
		switch (id) {
		case 0:
			createBlock();
			break;
		case 1:
			this.houseState.setObject(State.BUILDING);
			break;
		case 2:
			this.houseState.setObject(State.DEMOLISHING);
			break;
		}
	}
}
