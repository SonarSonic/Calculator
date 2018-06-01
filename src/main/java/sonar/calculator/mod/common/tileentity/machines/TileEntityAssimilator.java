package sonar.calculator.mod.common.tileentity.machines;

import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.machines.ProcessType;
import sonar.calculator.mod.api.nutrition.IHealthStore;
import sonar.calculator.mod.api.nutrition.IHungerStore;
import sonar.calculator.mod.client.gui.misc.GuiAlgorithmAssimilator;
import sonar.calculator.mod.client.gui.misc.GuiStoneAssimilator;
import sonar.calculator.mod.common.block.CalculatorLeaves;
import sonar.calculator.mod.common.block.CalculatorLeaves.LeafGrowth;
import sonar.calculator.mod.common.block.CalculatorLogs;
import sonar.calculator.mod.common.containers.ContainerAlgorithmAssimilator;
import sonar.calculator.mod.common.containers.ContainerAssimilator;
import sonar.calculator.mod.common.recipes.TreeHarvestRecipes;
import sonar.core.api.IFlexibleGui;
import sonar.core.api.utils.BlockCoords;
import sonar.core.common.block.properties.SonarProperties;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.handlers.inventories.handling.EnumFilterType;
import sonar.core.handlers.inventories.handling.ItemTransferHelper;
import sonar.core.handlers.inventories.handling.filters.SlotHelper;
import sonar.core.helpers.NBTHelper.SyncType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class TileEntityAssimilator extends TileEntityInventory implements IFlexibleGui {
	public boolean hasTree;
	public Random rand = new Random();
	public int tickRate = 30, tick;

	public abstract boolean harvestBlock(BlockCoords block);

    @Override
	public void update() {
		super.update();
		if (isClient()) {
			return;
		}
		if (this.tick != tickRate) {
			tick++;
		} else {
			if (this instanceof Algorithm) {
				EnumFacing dir = EnumFacing.getFront(this.getBlockMetadata());
				IItemHandler handler = ItemTransferHelper.getItemHandlerOffset(world, pos, dir);
				ItemTransferHelper.doSimpleTransfer(Lists.newArrayList(handler), Lists.newArrayList(inv().getItemHandler(dir)), IS -> true, 32);
			}
			tick = 0;
			this.hasTree = hasTree();
			if (hasTree) {
				List<BlockCoords> leaves = getLeaves();
				if (leaves != null && leaves.size() != 0) {
					for (BlockCoords coords : leaves) {
						if (harvestBlock(coords)) {
							return;
						}
					}
				}
			}
		}
	}

	public boolean hasTree() {
		EnumFacing dir = this.getWorld().getBlockState(pos).getValue(SonarProperties.FACING).getOpposite();
		boolean flag = true;
		for (int log = 0; log < 3; log++) {
			pos.offset(dir).add(0, log, 0);
			if (!(this.world.getBlockState(pos.offset(dir).add(0, log, 0)).getBlock() instanceof CalculatorLogs)) {
				flag = false;
			}
		}
		int leafCount = 0;
		for (int X = -3; X < 3; X++) {
			for (int Z = -3; Z < 3; Z++) {
				for (int leaves = 1; leaves < 8; leaves++) {
					if (!(this.world.getBlockState(pos.offset(dir).add(X, leaves, Z)).getBlock() instanceof CalculatorLeaves)) {
						leafCount++;
					}
				}
			}
		}
		if (leafCount < 10) {
			flag = false;
		}
		return flag;
	}

	public List<BlockCoords> getLeaves() {
		EnumFacing dir = this.getWorld().getBlockState(pos).getValue(SonarProperties.FACING).getOpposite();
        List<BlockCoords> leafList = new ArrayList<>();
		for (int X = -2; X < 3; X++) {
			for (int Z = -2; Z < 3; Z++) {
				for (int leaves = 1; leaves < 8; leaves++) {
					BlockPos pos = this.pos.offset(dir).add(X, leaves, Z);
                    if (this.world.getBlockState(pos).getBlock() instanceof CalculatorLeaves) {
						leafList.add(new BlockCoords(pos));
					}
				}
			}
		}
		return leafList;
	}

    @Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE)
			tick = nbt.getInteger("tick");
	}

    @Override
	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE)
			nbt.setInteger("tick", tick);
		return nbt;
	}

	public static class Stone extends TileEntityAssimilator {

		public Stone() {
			super.inv.setSize(1);
			super.inv.getInsertFilters().put(SlotHelper.filterSlot(0, s -> s.getItem() instanceof IHungerStore || s.getItem() instanceof IHealthStore), EnumFilterType.INTERNAL);
		}

        public int healthPoints, hungerPoints, speed = 4;

        @Override
		public void update() {
			super.update();
			if (this.world.isRemote) {
				return;
			}
			chargeHunger(slots().get(0));
			chargeHealth(slots().get(0));
		}

        @Override
		public boolean harvestBlock(BlockCoords coords) {
			IBlockState state = coords.getBlockState(this.world);
			if (state.getBlock() == Calculator.tanzaniteLeaves || state.getBlock() == Calculator.amethystLeaves) {
				LeafGrowth growth = state.getValue(CalculatorLeaves.GROWTH);
				if (growth == LeafGrowth.MATURED || growth == LeafGrowth.READY) {
					if (state.getBlock() == Calculator.tanzaniteLeaves) {
						this.healthPoints++;
						this.world.setBlockState(coords.getBlockPos(), state.withProperty(CalculatorLeaves.GROWTH, LeafGrowth.FRESH));
						return true;
					} else if (state.getBlock() == Calculator.amethystLeaves) {
						this.hungerPoints++;
						this.world.setBlockState(coords.getBlockPos(), state.withProperty(CalculatorLeaves.GROWTH, LeafGrowth.FRESH));
						return true;
					}
				}
			}
			return false;
		}

		public void chargeHunger(ItemStack stack) {
            if (!stack.isEmpty() && this.hungerPoints != 0) {
				if (stack.getItem() instanceof IHungerStore) {
					IHungerStore module = (IHungerStore) stack.getItem();
					int hunger = module.getHungerPoints(stack);
					int max = module.getMaxHungerPoints(stack);
					if (!(hunger >= max) || max == -1) {
						if (hungerPoints >= speed) {
							if (max == -1 || max >= hunger + speed) {
								module.transferHunger(speed, stack, ProcessType.ADD);
								hungerPoints = hungerPoints - speed;
							} else {
								module.transferHunger(max - hunger, stack, ProcessType.ADD);
								hungerPoints = hungerPoints - (max - hunger);
							}
						} else if (hungerPoints <= speed) {
							if (max == -1 | max >= hunger + speed) {
								module.transferHunger(speed, stack, ProcessType.ADD);
								hungerPoints = 0;
							} else {
								module.transferHunger(max - hunger, stack, ProcessType.ADD);
								hungerPoints = hungerPoints - max - hunger;
							}
						}
					}
				}
			}
		}

		public void chargeHealth(ItemStack stack) {
            if (!stack.isEmpty() && this.healthPoints != 0) {
				if (stack.getItem() instanceof IHealthStore) {
					IHealthStore module = (IHealthStore) stack.getItem();
					int health = module.getHealthPoints(stack);
					int max = module.getMaxHealthPoints(stack);
					if (!(health >= max) || max == -1) {
						if (healthPoints >= speed) {
							if (max == -1 || max >= health + speed) {
								module.transferHealth(speed, stack, ProcessType.ADD);
								healthPoints = healthPoints - speed;
							} else {
								module.transferHealth(max - health, stack, ProcessType.ADD);
								healthPoints = healthPoints - (max - health);
							}
						} else if (healthPoints <= speed) {
							if (max == -1 | max >= health + speed) {
								module.transferHealth(speed, stack, ProcessType.ADD);
								healthPoints = 0;
							} else {
								module.transferHealth(max - health, stack, ProcessType.ADD);
								healthPoints = healthPoints - max - health;
							}
						}
					}
				}
			}
		}

        @Override
		public void readData(NBTTagCompound nbt, SyncType type) {
			super.readData(nbt, type);
			healthPoints = nbt.getInteger("health");
			hungerPoints = nbt.getInteger("hunger");
			if (type != SyncType.DROP)
				hasTree = nbt.getBoolean("hasTree");
		}

        @Override
		public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
			super.writeData(nbt, type);
			nbt.setInteger("health", healthPoints);
			nbt.setInteger("hunger", hungerPoints);
			if (type != SyncType.DROP)
				nbt.setBoolean("hasTree", hasTree);
			return nbt;
		}

		@Override
		public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new ContainerAssimilator(player.inventory, this);
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiStoneAssimilator(player.inventory, this);
		}
	}

	public static class Algorithm extends TileEntityAssimilator {

		public Algorithm() {
			super.inv.setSize(27);
			super.inv.default_external_insert_result = false;
			super.inv.default_external_extract_result = true;
		}

        @Override
		public boolean harvestBlock(BlockCoords block) {
			IBlockState state = block.getBlockState(world);
			if (state.getValue(CalculatorLeaves.GROWTH).getMeta() > 2) {
				ArrayList<ItemStack> stacks = TreeHarvestRecipes.harvestLeaves(world, block.getBlockPos(), rand.nextBoolean());
				for(ItemStack stack : stacks){
					if(!stack.isEmpty()){
						stack = ItemHandlerHelper.insertItem(inv(), stack, false);
						if (!stack.isEmpty()) {
							InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
						}
					}
				}
				return true;
			}
			return false;
		}

		@Override
		public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new ContainerAlgorithmAssimilator(player, this);
		}

		@Override
		public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
			return new GuiAlgorithmAssimilator(player, this);
		}
	}
}
