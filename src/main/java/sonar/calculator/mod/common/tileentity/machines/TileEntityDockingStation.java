package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.machines.GuiDockingStation;
import sonar.calculator.mod.common.containers.ContainerDockingStation;
import sonar.calculator.mod.common.item.misc.CircuitBoard;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.RecipeHelper;
import sonar.core.helpers.RenderHelper;
import sonar.core.inventory.IAdditionalInventory;
import sonar.core.utils.IGuiTile;

public class TileEntityDockingStation extends TileEntityAbstractProcess implements IGuiTile, IAdditionalInventory {

	public TileEntityDockingStation() {
		super(4, 1, 200, 10);
	}

	public ItemStack calcStack;

	@Override
	public int inputSize() {
		return 4;
	}

	@Override
	public int outputSize() {
		return 1;
	}

	public ItemStack[] getOutput(boolean simulate, ItemStack... stacks) {
		return recipeHelper().getOutput(stacks);
	}

	public RecipeHelper recipeHelper() {
		if (calcStack != null) {
			if (calcStack.getItem() == Calculator.itemCalculator) {
				return RecipeRegistry.CalculatorRecipes.instance();
			}
			if (calcStack.getItem() == Calculator.itemScientificCalculator) {
				return RecipeRegistry.ScientificRecipes.instance();
			}
			if (calcStack.getItem() == Item.getItemFromBlock(Calculator.atomicCalculator)) {
				return RecipeRegistry.AtomicRecipes.instance();
			}
			if (calcStack.getItem() == Calculator.itemFlawlessCalculator) {
				return RecipeRegistry.FlawlessRecipes.instance();
			}
		}
		return RecipeRegistry.CalculatorRecipes.instance();
	}

	public int getProcessTime() {
		return Math.max(1, super.getProcessTime() / 8);
	}

	public int requiredEnergy() {
		return 10;
	}

	@Override
	public ItemStack[] inputStacks() {
		int size = this.isCalculator(calcStack);
		if (size == 0) {
			return null;
		}
		ItemStack[] input = new ItemStack[size];
		for (int i = 0; i < size; i++) {
			input[i] = slots()[i];
		}
		return input;
	}

	@Override
	public void finishProcess() {
		ItemStack[] output = getOutput(false, inputStacks());
		for (int o = 0; o < outputSize(); o++) {
			if (output[o] != null) {
				if (this.slots()[o + inputSize() + 1] == null) {
					ItemStack outputStack = output[o].copy();
					if (output[o].getItem() == Calculator.circuitBoard) {
						CircuitBoard.setData(outputStack);
					}
					this.slots()[o + inputSize() + 1] = outputStack;
				} else if (this.slots()[o + inputSize() + 1].isItemEqual(output[o])) {
					this.slots()[o + inputSize() + 1].stackSize += output[o].stackSize;

				}
			}
		}
		for (int i = 0; i < this.isCalculator(calcStack); i++) {
			if (recipeHelper() != null) {
				this.slots()[i].stackSize -= recipeHelper().getInputSize(i, output);
			} else {
				this.slots()[i].stackSize -= 1;
			}
			if (this.slots()[i].stackSize <= 0) {
				this.slots()[i] = null;
			}
		}
	}

	public static int isCalculator(ItemStack itemstack1) {
		if (itemstack1 != null) {
			if (itemstack1.getItem() == Calculator.itemCalculator) {
				return 2;
			}
			if (itemstack1.getItem() == Calculator.itemScientificCalculator) {
				return 2;
			}
			if (itemstack1.getItem() == Item.getItemFromBlock(Calculator.atomicCalculator)) {
				return 3;
			}
			if (itemstack1.getItem() == Calculator.itemFlawlessCalculator) {
				return 4;
			}
		}
		return 0;
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			this.calcStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("calcStack"));
		}
	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			if (calcStack != null) {
				NBTTagCompound stack = new NBTTagCompound();
				calcStack.writeToNBT(stack);
				nbt.setTag("calcStack", stack);
			}
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.slots()[i] = itemstack;

		if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit())) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		int[] outputSlot = new int[] { 5 };
		int[] emptySlot = new int[0];
		int size = this.isCalculator(calcStack);
		EnumFacing dir = EnumFacing.getFront(getBlockMetadata());
		if (dir == null || size == 0) {
			return emptySlot;
		}
		if (side == EnumFacing.DOWN || side == EnumFacing.UP) {
			return outputSlot;
		}
		if (size != 1) {
			if (side == RenderHelper.getHorizontal(dir)) {
				return new int[] { 0 };
			} else if (side == RenderHelper.getHorizontal(dir).getOpposite()) {
				return new int[] { 1 };
			} else if ((size == 4 || size == 3) && side == RenderHelper.getHorizontal(dir).getOpposite()) {
				return new int[] { 2 };
			} else if (size == 4 && side == RenderHelper.getHorizontal(dir).getOpposite()) {
				return new int[] { 3 };
			}
		}
		return outputSlot;
	}

	public int convertMeta(int meta) {
		EnumFacing dir = EnumFacing.getFront(meta);
		RenderHelper.getHorizontal(dir);
		if (meta <= 1) {
			meta = 5;
		} else if ((meta & 5) <= 1) {
			meta = 2;
		}
		return meta;
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerDockingStation(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiDockingStation(player.inventory, this);
	}

	public ItemStack[] getAdditionalStacks(){
		if(calcStack!=null){
			return new ItemStack[]{calcStack};
		}else{
			return new ItemStack[0];
		}
	}
}
