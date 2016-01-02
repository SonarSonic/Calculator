package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IHealthStore;
import sonar.calculator.mod.api.IHungerStore;
import sonar.calculator.mod.api.ProcessType;
import sonar.calculator.mod.common.block.CalculatorLeaves;
import sonar.calculator.mod.common.block.CalculatorLogs;
import sonar.calculator.mod.integration.planting.TreeHarvestRecipes;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.utils.BlockCoords;
import sonar.core.utils.helpers.InventoryHelper;
import sonar.core.utils.helpers.NBTHelper.SyncType;
import sonar.core.utils.helpers.SonarHelper;

public abstract class TileEntityAssimilator extends TileEntityInventory {
	public boolean hasTree;
	public Random rand = new Random();
	public int tickRate = 30, tick;

	public abstract boolean harvestBlock(BlockCoords block);

	public void updateEntity() {
		if (this.worldObj.isRemote) {
			return;
		}
		if (this.tick != tickRate) {
			tick++;
		} else {
		//	int blockmeta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		//	InventoryHelper.extractItems(this.getWorldObj().getTileEntity(xCoord + (SonarHelper.getForward(blockmeta).getOpposite().offsetX), yCoord, zCoord + (SonarHelper.getForward(blockmeta).getOpposite().offsetZ)), this, 0, 0, null);
			tick=0;
			this.hasTree = hasTree();
			if (hasTree) {
				List<BlockCoords> leaves = getLeaves();
				if (leaves != null && leaves.size() != 0) {
					for (BlockCoords coords : leaves) {
						Block block = coords.getBlock(worldObj);
						int meta = worldObj.getBlockMetadata(coords.getX(), coords.getY(), coords.getZ());
						if (meta > 2) {
							if (harvestBlock(coords)) {
								return;
							}
							/** remove energy maybe **/
						}
					}
				}
			}
		}
	}

	public boolean hasTree() {
		ForgeDirection dir = SonarHelper.getForward(this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
		boolean flag = true;
		for (int log = 0; log < 3; log++) {
			if (!(this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord + log, this.zCoord + dir.offsetZ) instanceof CalculatorLogs)) {
				flag = false;
			}
		}
		int leafCount = 0;
		for (int X = -3; X < 3; X++) {
			for (int Z = -3; Z < 3; Z++) {
				for (int leaves = 1; leaves < 8; leaves++) {
					if (!(this.worldObj.getBlock(this.xCoord + dir.offsetX + X, this.yCoord + leaves, this.zCoord + dir.offsetZ + Z) instanceof CalculatorLeaves)) {
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
		ForgeDirection dir = SonarHelper.getForward(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
		List<BlockCoords> leafList = new ArrayList();
		for (int X = -2; X < 3; X++) {
			for (int Z = -2; Z < 3; Z++) {
				for (int leaves = 1; leaves < 8; leaves++) {
					if ((this.worldObj.getBlock(this.xCoord + dir.offsetX + X, this.yCoord + leaves, this.zCoord + dir.offsetZ + Z) instanceof CalculatorLeaves)) {
						leafList.add(new BlockCoords(this.xCoord + dir.offsetX + X, this.yCoord + leaves, this.zCoord + dir.offsetZ + Z));
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

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE)
			nbt.setInteger("tick", tick);
	}
	public static class Stone extends TileEntityAssimilator {

		public Stone() {
			super.slots = new ItemStack[1];
		}

		public int healthPoints, hungerPoints, speed = 4;;

		public void updateEntity() {
			super.updateEntity();
			if (this.worldObj.isRemote) {
				return;
			}
			chargeHunger(slots[0]);
			chargeHealth(slots[0]);
		}

		public boolean harvestBlock(BlockCoords block) {
			if (block.getBlock(worldObj) == Calculator.tanzaniteLeaf) {
				this.healthPoints++;
				this.worldObj.setBlockMetadataWithNotify(block.getX(), block.getY(), block.getZ(), 0, 2);
				return true;
			} else if (block.getBlock(worldObj) == Calculator.amethystLeaf) {
				this.hungerPoints++;
				this.worldObj.setBlockMetadataWithNotify(block.getX(), block.getY(), block.getZ(), 0, 2);
				return true;
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

		public void writeData(NBTTagCompound nbt, SyncType type) {
			super.writeData(nbt, type);
			nbt.setInteger("health", healthPoints);
			nbt.setInteger("hunger", hungerPoints);
			if (type != SyncType.DROP)
				nbt.setBoolean("hasTree", hasTree);
		}
	}

	public static class Algorithm extends TileEntityAssimilator implements ISidedInventory {

		public Algorithm() {
			super.slots = new ItemStack[27];
		}

		public boolean harvestBlock(BlockCoords block) {
			int meta = worldObj.getBlockMetadata(block.getX(), block.getY(), block.getZ());
			if (meta > 2) {
				int randInt = 3 + rand.nextInt(3);
				ItemStack[] stacks = TreeHarvestRecipes.harvestLeaves(worldObj, block.getX(), block.getY(), block.getZ(), randInt);
				if (stacks != null) {
					for (int i = 0; i < stacks.length; i++) {
						InventoryHelper.addItems(this, SonarHelper.restoreItemStack(stacks[i], 1), 0, null);
					}

					return true;
				}
			}

			return false;

		}

		@Override
		public int[] getAccessibleSlotsFromSide(int side) {
			return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26 };
		}

		@Override
		public boolean canInsertItem(int slot, ItemStack item, int side) {
			return true;
		}

		@Override
		public boolean canExtractItem(int slot, ItemStack item, int side) {
			return true;
		}

	}

}
