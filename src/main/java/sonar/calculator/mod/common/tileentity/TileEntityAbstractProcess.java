package sonar.calculator.mod.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.item.misc.CircuitBoard;
import sonar.core.helpers.ItemStackHelper;
import sonar.core.inventory.SonarInventory;
import sonar.core.recipes.ISonarRecipe;
import sonar.core.recipes.ISonarRecipeObject;
import sonar.core.recipes.RecipeHelperV2;
import sonar.core.utils.IGuiTile;

public abstract class TileEntityAbstractProcess extends TileEntityProcess implements IGuiTile {

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
		super.input = inputs;
		super.output = outputs;
		super.storage.setCapacity(CalculatorConfig.getInteger("Standard Machine")).setMaxTransfer(32000);
		super.inv = new SonarInventory(this, 1 + inputSize + outputSize);
		syncList.addPart(inv);
	}

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

	public boolean canProcess() {
		if (slots()[0] == null || (cookTime.getObject() == 0 && storage.getEnergyStored() < requiredEnergy())) {
			return false;
		}
		ISonarRecipe recipe = getRecipe(inputStacks());
		if (recipe == null) {
			return false;
		}
		for (int o = 0; o < outputSize(); o++) {
			if (recipe.outputs().get(o) == null) {
				return false;
			} else {
				ItemStack outputStack = RecipeHelperV2.getItemStackFromList(recipe.outputs(), o);
				if (slots()[o + inputSize() + 1] != null) {
					if (!slots()[o + inputSize() + 1].isItemEqual(outputStack)) {
						return false;
					} else if (slots()[o + inputSize() + 1].stackSize + outputStack.stackSize > slots()[o + inputSize() + 1].getMaxStackSize()) {
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
			size = slots()[i] != null ? Math.max(size, slots()[i].stackSize) : 0;
		}
		return size;
	}

	public int getMaxOutputSize() {
		int size = 1;
		ISonarRecipe recipe = getRecipe(inputStacks());
		for (int o = 0; o < outputSize(); o++) {
			ISonarRecipeObject outputObject = recipe.outputs().get(o);
			if (slots()[o + inputSize() + 1] != null && outputObject != null) {
				size = Math.max((getInventoryStackLimit() - slots()[o + inputSize() + 1].stackSize) / outputObject.getStackSize(), size);
			} else {
				size = 0;
			}
		}
		return size;
	}

	public void finishProcess() {
		ISonarRecipe recipe = getRecipe(inputStacks());
		if (recipe == null) {
			return;
		}
		for (int o = 0; o < Math.min(recipe.outputs().size(), outputSize()); o++) {
			ISonarRecipeObject outputObject = recipe.outputs().get(o);

			ItemStack stack = RecipeHelperV2.getItemStackFromList(recipe.outputs(), o);
			if (stack != null && !isOutputVoided(o + inputSize() + 1, stack)) {
				if (slots()[o + inputSize() + 1] == null) {
					ItemStack outputStack = stack.copy();
					if (outputStack.getItem() == Calculator.circuitBoard) {
						CircuitBoard.setData(outputStack);
					}
					slots()[o + inputSize() + 1] = outputStack;
				} else if (slots()[o + inputSize() + 1].isItemEqual(stack)) {
					slots()[o + inputSize() + 1].stackSize += outputObject.getStackSize();
				}
			}
		}
		for (int i = 0; i < Math.min(recipe.inputs().size(), inputSize()); i++) {
			ISonarRecipeObject inputObject = recipe.inputs().get(i);
			if (slots()[i].getItem().hasContainerItem(slots()[i])) {
				slots()[i] = slots()[i].getItem().getContainerItem(slots()[i]);
			} else {
				slots()[i] = ItemStackHelper.reduceStackSize(slots()[i], recipeHelper() != null ? inputObject.getStackSize() : 1);
			}

		}
	}

	public ItemStack[] inputStacks() {
		ItemStack[] input = new ItemStack[inputSize()];
		for (int i = 0; i < inputSize(); i++) {
			input[i] = slots()[i];
		}
		return input;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot < this.inputSize()) {
			if ((recipeHelper() != null && recipeHelper().isValidInput(stack)) || getRecipe(inputStacks()) != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) {
		return isItemValidForSlot(slot, stack) && canStack(slots()[slot], stack);
	}
}
