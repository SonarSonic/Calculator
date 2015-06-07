package sonar.calculator.mod.common.tileentity;

import net.minecraft.item.ItemStack;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.item.misc.ItemCircuit;
import sonar.calculator.mod.common.recipes.machines.AlgorithmSeperatorRecipes;
import sonar.calculator.mod.common.recipes.machines.ExtractionChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.PrecisionChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.StoneSeperatorRecipes;
import sonar.calculator.mod.utils.helpers.RecipeHelper;
import cofh.api.energy.EnergyStorage;

public abstract class TileEntityAbstractProcess extends TileEntityProcess {

	public TileEntityAbstractProcess() {
		int[] inputs = new int[inputSize()];
		int[] outputs = new int[outputSize()];
		for (int i = 0; i < inputSize(); i++) {
			inputs[i] = i;
		}
		for (int o = inputSize(); o < inputSize() + outputSize(); o++) {
			outputs[o - inputSize()] = o + 1;
		}
		super.input = inputs;
		super.output = outputs;
		super.storage = new EnergyStorage(CalculatorConfig.cubeEnergy, CalculatorConfig.cubeEnergy);
		super.slots = new ItemStack[1 + inputSize() + outputSize()];
	}

	public void updateEntity() {
		super.updateEntity();
		discharge(inputSize());
	}

	public abstract int inputSize();

	public abstract int outputSize();

	public RecipeHelper recipeHelper() {
		return null;
	}

	public ItemStack[] getOutput(ItemStack... stacks) {
		return recipeHelper().getOutput(stacks);
	}

	public boolean canProcess() {

		for (int i = 0; i < inputSize(); i++) {
			if (slots[i] == null) {
				return false;
			}
		}
		if (cookTime == 0) {
			if (this.storage.getEnergyStored() < currentEnergy) {
				return false;
			}
		}
		ItemStack[] output = getOutput(this.slots[0]);
		for (int o = 0; o < outputSize(); o++) {
			if (output[o] == null) {
				return false;
			}
			if (slots[o + inputSize() + 1] != null) {
				if (!slots[o + inputSize() + 1].isItemEqual(output[o])) {
					return false;
				} else if (slots[o + inputSize() + 1].stackSize + output[o].stackSize > slots[o + inputSize() + 1].getMaxStackSize()) {
					return false;
				}
			}
		}
		return true;
	}

	public void finishProcess() {
		ItemStack[] output = getOutput(this.slots[0]);
		for (int o = 0; o < outputSize(); o++) {
			if (output[o] == null) {
				return;
			}
			if (this.slots[o + inputSize() + 1] == null) {
				this.slots[o + inputSize() + 1] = output[o].copy();
			} else if (this.slots[o + inputSize() + 1].isItemEqual(output[o])) {
				this.slots[o + inputSize() + 1].stackSize += output[o].stackSize;

			}
			if (this.slots[o + outputSize()] != null && this.slots[o + outputSize()].getItem() instanceof ItemCircuit) {
				ItemCircuit.setData(this.slots[o + outputSize()]);
			}

		}
		for (int i = 0; i < inputSize(); i++) {
			if (output[i] == null) {
				return;
			}
			if (recipeHelper() != null)
				this.slots[i].stackSize -= recipeHelper().getInputSize(i, output);
			else {
				this.slots[i].stackSize -= 1;
			}
			if (this.slots[i].stackSize <= 0) {
				this.slots[i] = null;
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 0) {
			if (recipeHelper() != null) {
				if (recipeHelper().validInput(stack))
					return true;
			} else {
				if (getOutput(stack) != null) {
					return true;
				}
			}
		}
		return false;

	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(slot, stack) && canStack(slots[slot], stack);
	}

}
