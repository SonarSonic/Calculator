package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.machines.GuiDockingStation;
import sonar.calculator.mod.common.containers.ContainerDockingStation;
import sonar.calculator.mod.common.recipes.AtomicCalculatorRecipes;
import sonar.calculator.mod.common.recipes.CalculatorRecipes;
import sonar.calculator.mod.common.recipes.FlawlessCalculatorRecipes;
import sonar.calculator.mod.common.recipes.ScientificRecipes;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.SonarHelper;
import sonar.core.api.inventories.IAdditionalInventory;
import sonar.core.inventory.handling.EnumFilterType;
import sonar.core.recipes.RecipeHelperV2;

public class TileEntityDockingStation extends TileEntityAbstractProcess implements IAdditionalInventory {

	public ItemStack calcStack = ItemStack.EMPTY;

	public TileEntityDockingStation() {
		super(4, 1, CalculatorConfig.DOCKING_STATION_SPEED, CalculatorConfig.DOCKING_STATION_USAGE);
		super.storage.setCapacity(CalculatorConfig.DOCKING_STATION_STORAGE);
		super.storage.setMaxTransfer(CalculatorConfig.DOCKING_STATION_TRANSFER_RATE);
		super.inv.getInsertFilters().put((SLOT,STACK, FACE) -> SonarHelper.intContains(getSlotConfig(FACE), SLOT),  EnumFilterType.EXTERNAL);
	}

	public int[] getSlotConfig(EnumFacing side) {
		int[] outputSlot = new int[] { 5 };
		int[] emptySlot = new int[0];
		int size = getInputStackSize(calcStack);
		EnumFacing dir = EnumFacing.getFront(getBlockMetadata());
		if (size == 0) {
			return emptySlot;
		}
		if (side == EnumFacing.DOWN || side == EnumFacing.UP) {
			return outputSlot;
		}
		if (size != 1) {
			if (side == SonarHelper.getHorizontal(dir)) {
				return new int[] { 0 };
			} else if (side == SonarHelper.getHorizontal(dir).getOpposite()) {
				return new int[] { 1 };
			} else if ((size == 4 || size == 3) && side == dir.getOpposite()) {
				return new int[] { 2 };
			} else if (size == 4 && side == dir) {
				return new int[] { 3 };
			}
		}
		return outputSlot;
	}

	@Override
	public int inputSize() {
		return 4;
	}

	@Override
	public int outputSize() {
		return 1;
	}

	public enum ProcessType {
		NONE(0), CALCULATOR(2), SCIENTIFIC(2), ATOMIC(3), FLAWLESS(4);

		public int inputStacks;

		ProcessType(int inputStacks) {
			this.inputStacks = inputStacks;
		}

		public RecipeHelperV2 getRecipeHelper() {
			switch (this) {
			case ATOMIC:
				return AtomicCalculatorRecipes.instance();
			case CALCULATOR:
				return CalculatorRecipes.instance();
			case FLAWLESS:
				return FlawlessCalculatorRecipes.instance();
			case SCIENTIFIC:
				return ScientificRecipes.instance();
			default:
				return null;
			}
		}

		public Item getItem() {
			switch (this) {
			case ATOMIC:
				return Item.getItemFromBlock(Calculator.atomicCalculator);
			case CALCULATOR:
				return Calculator.itemCalculator;
			case FLAWLESS:
				return Calculator.itemFlawlessCalculator;
			case SCIENTIFIC:
				return Calculator.itemScientificCalculator;
			default:
				return null;
			}
		}

		public static ProcessType getType(Item item) {
			for (ProcessType type : values()) {
				if (type.getItem() == item) {
					return type;
				}
			}
			return NONE;
		}
	}

	public static int getInputStackSize(ItemStack itemstack1) {
		if (!itemstack1.isEmpty()) {
			return ProcessType.getType(itemstack1.getItem()).inputStacks;
		}
		return 0;
	}

    @Override
	public RecipeHelperV2 recipeHelper() {
		if (!calcStack.isEmpty()) {
			return ProcessType.getType(calcStack.getItem()).getRecipeHelper();
		}
		return CalculatorRecipes.instance();
	}

    @Override
	public int getProcessTime() {
		return Math.max(1, super.getProcessTime() / 8);
	}

    @Override
	public int requiredEnergy() {
		return 10;
	}

	@Override
	public ItemStack[] inputStacks() {
		int size = getInputStackSize(calcStack);
		if (size == 0) {
			return null;
		}
		ItemStack[] input = new ItemStack[size];
		for (int i = 0; i < size; i++) {
			input[i] = slots().get(i);
		}
		return input;
	}

    @Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			this.calcStack = new ItemStack(nbt.getCompoundTag("calcStack"));
		}
	}

    @Override
	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			if (calcStack != null) {
				NBTTagCompound stack = new NBTTagCompound();
				calcStack.writeToNBT(stack);
				nbt.setTag("calcStack", stack);
			}
		}
		return nbt;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.slots().set(i, itemstack);

        if (!itemstack.isEmpty() && itemstack.getCount() > getInventoryStackLimit()) {
			itemstack.setCount(getInventoryStackLimit());
		}
	}

	public int convertMeta(int meta) {
		EnumFacing dir = EnumFacing.getFront(meta);
		//SonarHelper.getHorizontal(dir);
		if (meta <= 1) {
			meta = 5;
		} else if ((meta & 5) <= 1) {
			meta = 2;
		}
		return meta;
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerDockingStation(player.inventory, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiDockingStation(player.inventory, this);
	}

    @Override
	public ItemStack[] getAdditionalStacks() {
		if (!calcStack.isEmpty()) {
			return new ItemStack[] { calcStack };
		} else {
			return new ItemStack[0];
		}
	}
}
