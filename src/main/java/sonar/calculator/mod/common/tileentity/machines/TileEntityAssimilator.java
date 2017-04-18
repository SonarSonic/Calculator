package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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
import sonar.core.api.SonarAPI;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.api.utils.ActionType;
import sonar.core.api.utils.BlockCoords;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.inventory.SonarInventory;
import sonar.core.utils.IGuiTile;

public abstract class TileEntityAssimilator extends TileEntityInventory implements IGuiTile {
	public boolean hasTree;
	public Random rand = new Random();
	public int tickRate = 30, tick;

	public abstract boolean harvestBlock(BlockCoords block);

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
				SonarAPI.getItemHelper().transferItems(this, this.getWorld().getTileEntity(pos.offset(dir.getOpposite())), dir, dir.getOpposite(), null);
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
		EnumFacing dir = this.getWorld().getBlockState(pos).getValue(SonarBlock.FACING).getOpposite();
		boolean flag = true;
		for (int log = 0; log < 3; log++) {
			pos.offset(dir).add(0, log, 0);
			if (!(this.getWorld().getBlockState(pos.offset(dir).add(0, log, 0)).getBlock() instanceof CalculatorLogs)) {
				flag = false;
			}
		}
		int leafCount = 0;
		for (int X = -3; X < 3; X++) {
			for (int Z = -3; Z < 3; Z++) {
				for (int leaves = 1; leaves < 8; leaves++) {
					if (!(this.getWorld().getBlockState(pos.offset(dir).add(X, leaves, Z)).getBlock() instanceof CalculatorLeaves)) {
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
		EnumFacing dir = this.getWorld().getBlockState(pos).getValue(SonarBlock.FACING).getOpposite();
		List<BlockCoords> leafList = Lists.newArrayList();
		for (int X = -2; X < 3; X++) {
			for (int Z = -2; Z < 3; Z++) {
				for (int leaves = 1; leaves < 8; leaves++) {
					BlockPos pos = this.pos.offset(dir).add(X, leaves, Z);
					if ((this.getWorld().getBlockState(pos).getBlock() instanceof CalculatorLeaves)) {
						leafList.add(new BlockCoords(pos));
					}

				}
			}
		}
		return leafList;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE)
			tick = nbt.getInteger("tick");
	}

	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE)
			nbt.setInteger("tick", tick);
		return nbt;
	}

	public static class Stone extends TileEntityAssimilator {

		public Stone() {
			super.inv = new SonarInventory(this, 1);
			syncList.addPart(inv);
		}

		public int healthPoints, hungerPoints, speed = 4;;

		public void update() {
			super.update();
			if (this.getWorld().isRemote) {
				return;
			}
			chargeHunger(slots()[0]);
			chargeHealth(slots()[0]);
		}

		public boolean harvestBlock(BlockCoords coords) {
			IBlockState state = coords.getBlockState(this.getWorld());
			if (state.getBlock() == Calculator.tanzaniteLeaves || state.getBlock() == Calculator.amethystLeaves) {
				LeafGrowth growth = state.getValue(CalculatorLeaves.GROWTH);
				if (growth != null && (growth == LeafGrowth.MATURED || growth == LeafGrowth.READY)) {
					if (state.getBlock() == Calculator.tanzaniteLeaves) {
						this.healthPoints++;
						this.getWorld().setBlockState(coords.getBlockPos(), state.withProperty(CalculatorLeaves.GROWTH, LeafGrowth.FRESH));
						return true;
					} else if (state.getBlock() == Calculator.amethystLeaves) {
						this.hungerPoints++;
						this.getWorld().setBlockState(coords.getBlockPos(), state.withProperty(CalculatorLeaves.GROWTH, LeafGrowth.FRESH));
						return true;
					}
				}
			}
			return false;
		}

		public void chargeHunger(ItemStack stack) {
			if (!(stack == null) && this.hungerPoints != 0) {
				if (stack.getItem() instanceof IHungerStore) {
					IHungerStore module = (IHungerStore) stack.getItem();
					int hunger = module.getHungerPoints(stack);
					int max = module.getMaxHungerPoints(stack);
					if (!(hunger >= max) || max == -1) {
						if (hungerPoints >= speed) {
							if (max == -1 || max >= hunger + speed) {
								module.transferHunger(speed, stack, ProcessType.ADD);
								hungerPoints = hungerPoints - speed;
							} else if (max != -1) {
								module.transferHunger(max - hunger, stack, ProcessType.ADD);
								hungerPoints = hungerPoints - (max - hunger);
							}
						} else if (hungerPoints <= speed) {
							if (max == -1 | max >= hunger + speed) {
								module.transferHunger(speed, stack, ProcessType.ADD);
								hungerPoints = 0;
							} else if (max != -1) {
								module.transferHunger(max - hunger, stack, ProcessType.ADD);
								hungerPoints = hungerPoints - max - hunger;
							}
						}
					}
				}
			}
		}

		public void chargeHealth(ItemStack stack) {
			if (!(stack == null) && this.healthPoints != 0) {
				if (stack.getItem() instanceof IHealthStore) {
					IHealthStore module = (IHealthStore) stack.getItem();
					int health = module.getHealthPoints(stack);
					int max = module.getMaxHealthPoints(stack);
					if (!(health >= max) || max == -1) {
						if (healthPoints >= speed) {
							if (max == -1 || max >= health + speed) {
								module.transferHealth(speed, stack, ProcessType.ADD);
								healthPoints = healthPoints - speed;
							} else if (max != -1) {
								module.transferHealth(max - health, stack, ProcessType.ADD);
								healthPoints = healthPoints - (max - health);
							}
						} else if (healthPoints <= speed) {
							if (max == -1 | max >= health + speed) {
								module.transferHealth(speed, stack, ProcessType.ADD);
								healthPoints = 0;
							} else if (max != -1) {
								module.transferHealth(max - health, stack, ProcessType.ADD);
								healthPoints = healthPoints - max - health;
							}
						}
					}
				}
			}

		}

		public void readData(NBTTagCompound nbt, SyncType type) {
			super.readData(nbt, type);
			healthPoints = nbt.getInteger("health");
			hungerPoints = nbt.getInteger("hunger");
			if (type != SyncType.DROP)
				hasTree = nbt.getBoolean("hasTree");
		}

		public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
			super.writeData(nbt, type);
			nbt.setInteger("health", healthPoints);
			nbt.setInteger("hunger", hungerPoints);
			if (type != SyncType.DROP)
				nbt.setBoolean("hasTree", hasTree);
			return nbt;
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerAssimilator(player.inventory, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiStoneAssimilator(player.inventory, this);
		}
	}

	public static class Algorithm extends TileEntityAssimilator implements ISidedInventory {

		public Algorithm() {
			super.inv = new SonarInventory(this, 27);
			syncList.addPart(inv);
		}

		public boolean harvestBlock(BlockCoords block) {
			IBlockState state = block.getBlockState(getWorld());
			if (state.getValue(CalculatorLeaves.GROWTH).getMeta() > 2) {
				ArrayList<ItemStack> stacks = TreeHarvestRecipes.harvestLeaves(getWorld(), block.getBlockPos(), rand.nextBoolean());
				EnumFacing forward = EnumFacing.getFront(getBlockMetadata());
				if (stacks != null && !stacks.isEmpty()) {
					for (ItemStack s : stacks) {
						if (s != null) {
							ItemStack stack = s.copy();
							StoredItemStack storedstack = new StoredItemStack(stack);
							StoredItemStack harvest = SonarAPI.getItemHelper().addItems(this, storedstack.copy(), EnumFacing.DOWN, ActionType.PERFORM, null);
							storedstack.remove(harvest);
							if (storedstack != null && storedstack.stored > 0) {
								BlockPos pos = this.pos.offset(forward.getOpposite());
								EntityItem drop = new EntityItem(getWorld(), pos.getX(), pos.getY(), pos.getZ(), storedstack.getFullStack());
								getWorld().spawnEntityInWorld(drop);
							}
						}
					}
				}
				return true;
			}
			return false;

		}

		@Override
		public int[] getSlotsForFace(EnumFacing side) {
			return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26 };
		}

		@Override
		public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
			return true;
		}

		@Override
		public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
			return true;
		}

		@Override
		public Object getGuiContainer(EntityPlayer player) {
			return new ContainerAlgorithmAssimilator(player, this);
		}

		@Override
		public Object getGuiScreen(EntityPlayer player) {
			return new GuiAlgorithmAssimilator(player, this);
		}

	}

}
