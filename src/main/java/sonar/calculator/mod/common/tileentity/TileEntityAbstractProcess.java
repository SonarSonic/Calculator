package sonar.calculator.mod.common.tileentity;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.item.misc.CircuitBoard;
import sonar.core.inventory.SonarInventory;
import sonar.core.recipes.ISonarRecipe;
import sonar.core.recipes.ISonarRecipeObject;
import sonar.core.recipes.RecipeHelperV2;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.SonarCompat;

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
		if (SonarCompat.isEmpty(slots().get(0)) || cookTime.getObject() == 0 && storage.getEnergyStored() < requiredEnergy()) {
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
				if (!SonarCompat.isEmpty(stackInSlot)) {
					if (!stackInSlot.isItemEqual(outputStack)) {
						return false;
					} else if (SonarCompat.getCount(stackInSlot) + SonarCompat.getCount(outputStack) > stackInSlot.getMaxStackSize()) {
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
			size = SonarCompat.isEmpty(slots().get(i)) ? Math.max(size, SonarCompat.getCount(slots().get(i))) : 0;
		}
		return size;
	}

	public int getMaxOutputSize() {
		int size = 1;
		ISonarRecipe recipe = getRecipe(inputStacks());
		for (int o = 0; o < outputSize(); o++) {
			ISonarRecipeObject outputObject = recipe.outputs().get(o);
			if (SonarCompat.isEmpty(slots().get(o + inputSize() + 1)) && outputObject != null) {
				size = Math.max((getInventoryStackLimit() - SonarCompat.getCount(slots().get(o + inputSize() + 1))) / outputObject.getStackSize(), size);
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
			if (!SonarCompat.isEmpty(stack) && !isOutputVoided(o + inputSize() + 1, stack)) {
				ItemStack stackInSlot = slots().get(o + inputSize() + 1);
				if (SonarCompat.isEmpty(stackInSlot)) {
					ItemStack outputStack = stack.copy();
					if (outputStack.getItem() == Calculator.circuitBoard) {
						CircuitBoard.setData(outputStack);
					}
					slots().set(o + inputSize() + 1, outputStack);
				} else if (stackInSlot.isItemEqual(stack)) {
					stackInSlot = SonarCompat.grow(stackInSlot, outputObject.getStackSize());
				}
			}
		}
		for (int i = 0; i < Math.min(recipe.inputs().size(), inputSize()); i++) {
			ItemStack input = slots().get(i).copy();
			int shrinkSize = recipeHelper() != null ? recipe.inputs().get(i).getStackSize() : 1;
			boolean hasContainer = (SonarCompat.getCount(input) - shrinkSize) <= 0;
			slots().set(i, SonarCompat.shrink(slots().get(i), shrinkSize));
			if (hasContainer && input.getItem().hasContainerItem(input)) {
				ItemStack itemstack2 = input.getItem().getContainerItem(input);

				if (this.isItemValidForSlot(i, itemstack2)) {
					if (SonarCompat.isEmpty(inv.getStackInSlot(i))) {
						inv.setInventorySlotContents(i, itemstack2);
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

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot < this.inputSize()) {
			if (recipeHelper() != null && recipeHelper().isValidInput(stack)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) {
		return isItemValidForSlot(slot, stack) && canStack(slots().get(slot), stack);
	}
}
