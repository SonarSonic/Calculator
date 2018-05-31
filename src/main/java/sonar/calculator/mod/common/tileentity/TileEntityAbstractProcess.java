package sonar.calculator.mod.common.tileentity;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.item.misc.CircuitBoard;
import sonar.core.api.IFlexibleGui;
import sonar.core.inventory.handling.EnumFilterType;
import sonar.core.inventory.handling.filters.SlotHelper;
import sonar.core.recipes.ISonarRecipe;
import sonar.core.recipes.ISonarRecipeObject;
import sonar.core.recipes.RecipeHelperV2;

public abstract class TileEntityAbstractProcess extends TileEntityProcess implements IFlexibleGui {

	public final int inputSize, outputSize, baseProcess, baseEnergy;

	public TileEntityAbstractProcess(int inputSize, int outputSize, int baseProcess, int baseEnergy) {
		this.inputSize = inputSize;
		this.outputSize = outputSize;
		this.baseProcess = baseProcess;
		this.baseEnergy = baseEnergy;

		int[] inputs = new int[inputSize];
		int[] outputs = new int[outputSize];
		for (int i = 0; i < inputSize; i++) {
			inputs[i] = i;
		}
		for (int o = inputSize; o < inputSize + outputSize; o++) {
			outputs[o - inputSize] = o + 1;
		}
		super.sides.input = inputs;
		super.sides.output = outputs;
		super.storage.setCapacity(50000).setMaxTransfer(32000);
		super.inv.setSize(1 + inputSize + outputSize);
		super.inv.getInsertFilters().put((SLOT,STACK,FACE)-> (!(isInputSlot(SLOT)) || recipeHelper() == null) ? null : recipeHelper().isValidInput(STACK), EnumFilterType.EXTERNAL_INTERNAL);
		super.inv.getInsertFilters().put((SLOT,STACK,FACE) -> isInputSlot(SLOT) ? (recipeHelper() == null || recipeHelper().isValidInput(STACK)) : null , EnumFilterType.INTERNAL);
		super.inv.getInsertFilters().put(SlotHelper.dischargeSlot(inputSize), EnumFilterType.INTERNAL);
		super.inv.getInsertFilters().put((SLOT,STACK,FACE)-> !isOutputSlot(SLOT), EnumFilterType.EXTERNAL_INTERNAL);
		super.inv.getExtractFilters().put((SLOT,COUNT,FACE)-> isOutputSlot(SLOT), EnumFilterType.EXTERNAL);
	}

	@Override
	public void update() {
		super.update();
		discharge(inputSize);
	}

	public int inputSize() {
		return inputSize;
	}

	public int outputSize() {
		return outputSize;
	}

	public boolean isInputSlot(int slot){
		return slot < inputSize();
	}

	public boolean isOutputSlot(int slot){
		return slot > inputSize() + 1;
	}

	@Override
	public int getBaseProcessTime() {
		return baseProcess;
	}

	@Override
	public int getBaseEnergyUsage() {
		return baseEnergy;
	}

	public RecipeHelperV2 recipeHelper() {
		return null;
	}

	public ISonarRecipe getRecipe(ItemStack[] inputs) {
		return recipeHelper().getRecipeFromInputs(null, inputs);
	}

	@Override
	public boolean canProcess() {
		if (slots().get(0).isEmpty() || cookTime.getObject() == 0 && storage.getEnergyStored() < requiredEnergy()) {
			return false;
		}
		ISonarRecipe recipe = getRecipe(inputStacks());
		if (recipe == null) {
			return false;
		}
		for (int o = 0; o < outputSize(); o++) {
			if (recipe.outputs().get(o).isNull()) {
				return false;
			} else {
				ItemStack outputStack = RecipeHelperV2.getItemStackFromList(recipe.outputs(), o);
				ItemStack stackInSlot = slots().get(o + inputSize() + 1);
				if (!stackInSlot.isEmpty()) {
					if (!stackInSlot.isItemEqual(outputStack)) {
						return false;
					} else if (stackInSlot.getCount() + outputStack.getCount() > stackInSlot.getMaxStackSize()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public boolean isOutputVoided(int slot, ItemStack outputStack) {
		return false;
	}

	public int getMaxInputSize() {
		int size = 1;
		for (int i = 0; i < inputSize(); i++) {
			size = slots().get(i).isEmpty() ? Math.max(size, slots().get(i).getCount()) : 0;
		}
		return size;
	}

	public int getMaxOutputSize() {
		int size = 1;
		ISonarRecipe recipe = getRecipe(inputStacks());
		for (int o = 0; o < outputSize(); o++) {
			ISonarRecipeObject outputObject = recipe.outputs().get(o);
			if (slots().get(o + inputSize() + 1).isEmpty() && outputObject != null) {
				size = Math.max((getInventoryStackLimit() - slots().get(o + inputSize() + 1).getCount()) / outputObject.getStackSize(), size);
			} else {
				size = 0;
			}
		}
		return size;
	}

	@Override
	public void finishProcess() {
		ISonarRecipe recipe = getRecipe(inputStacks());
		if (recipe == null) {
			return;
		}
		for (int o = 0; o < Math.min(recipe.outputs().size(), outputSize()); o++) {
			ISonarRecipeObject outputObject = recipe.outputs().get(o);

			ItemStack stack = RecipeHelperV2.getItemStackFromList(recipe.outputs(), o);
			if (!stack.isEmpty() && !isOutputVoided(o + inputSize() + 1, stack)) {
				ItemStack stackInSlot = slots().get(o + inputSize() + 1);
				if (stackInSlot.isEmpty()) {
					ItemStack outputStack = stack.copy();
					if (outputStack.getItem() == Calculator.circuitBoard) {
						CircuitBoard.setData(outputStack);
					}
					slots().set(o + inputSize() + 1, outputStack);
				} else if (stackInSlot.isItemEqual(stack)) {
					stackInSlot.grow(outputObject.getStackSize());
				}
			}
		}
		for (int i = 0; i < Math.min(recipe.inputs().size(), inputSize()); i++) {
			ItemStack input = slots().get(i).copy();
			int shrinkSize = recipeHelper() != null ? recipe.inputs().get(i).getStackSize() : 1;
			boolean hasContainer = (input.getCount() - shrinkSize) <= 0;
			slots().get(i).shrink(shrinkSize);

			if (hasContainer && input.getItem().hasContainerItem(input)) {
				ItemStack itemstack2 = input.getItem().getContainerItem(input);

				if (this.isItemValidForSlot(i, itemstack2)) {
					if (inv.getStackInSlot(i).isEmpty()) {
						inv.setStackInSlot(i, itemstack2);
					} else {
						InventoryHelper.spawnItemStack(this.getWorld(), pos.getX(), pos.getY(), pos.getZ(), itemstack2);
					}
				}
			}
		}
	}

	public ItemStack[] inputStacks() {
		ItemStack[] input = new ItemStack[inputSize()];
		for (int i = 0; i < inputSize(); i++) {
			input[i] = slots().get(i);
		}
		return input;
	}
}
