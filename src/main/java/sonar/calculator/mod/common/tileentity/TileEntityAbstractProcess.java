package sonar.calculator.mod.common.tileentity;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.item.misc.CircuitBoard;
import sonar.core.inventory.SonarTileInventory;
import sonar.core.network.sync.SyncEnergyStorage;
import sonar.core.utils.MachineSide;
import sonar.core.utils.helpers.RecipeHelper;

public abstract class TileEntityAbstractProcess extends TileEntityProcess {

	public Random rand = new Random();

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
		super.storage = new SyncEnergyStorage(CalculatorConfig.getInteger("Standard Machine"), 1600);
		super.inv= new SonarTileInventory(this, 1 + inputSize() + outputSize());
	}

	public void update() {
		super.update();
		discharge(inputSize());
	}

	public abstract int inputSize();

	public abstract int outputSize();

	public RecipeHelper recipeHelper() {
		return null;
	}

	public ItemStack[] getOutput(boolean simulate, ItemStack... stacks) {
		return recipeHelper().getOutput(stacks);
	}

	public boolean canProcess() {
		if (slots()[0] == null) {
			return false;
		}

		if (cookTime.getObject() == 0) {
			if (this.storage.getEnergyStored() < this.requiredEnergy()) {
				return false;
			}
		}
		ItemStack[] output = getOutput(true, inputStacks());
		if (output == null || output.length != this.outputSize()) {
			return false;
		}
		for (int o = 0; o < outputSize(); o++) {
			if (output[o] == null) {
				return false;
			} else {
				if (slots()[o + inputSize() + 1] != null) {
					if (!slots()[o + inputSize() + 1].isItemEqual(output[o])) {
						return false;
					} else if (slots()[o + inputSize() + 1].stackSize + output[o].stackSize > slots()[o + inputSize() + 1].getMaxStackSize()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public int getMaxInputSize() {
		int size = 1;
		for (int i = 0; i < inputSize(); i++) {
			if (slots()[i] != null) {
				size = Math.max(size, slots()[i].stackSize);
			} else {
				size = 0;
			}
		}
		return size;
	}

	public int getMaxOutputSize() {
		int size = 1;
		ItemStack[] output = getOutput(true, inputStacks());
		for (int o = 0; o < outputSize(); o++) {
			if (slots()[o + inputSize() + 1] != null && output[o] != null) {
				size = Math.max((this.getInventoryStackLimit() - this.slots()[o + inputSize() + 1].stackSize) / output[o].stackSize, size);
			} else {
				size = 0;
			}
		}
		return size;

	}

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
		for (int i = 0; i < inputSize(); i++) {
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

	public ItemStack[] inputStacks() {
		ItemStack[] input = new ItemStack[this.inputSize()];
		for (int i = 0; i < this.inputSize(); i++) {
			input[i] = slots()[i];
		}
		return input;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot < this.inputSize()) {
			if (recipeHelper() != null) {
				if (recipeHelper().validInput(stack))
					return true;
			} else {
				ItemStack[] inputs = inputStacks();
				inputs[slot] = stack;
				if (getOutput(true, inputs) != null) {
					return true;
				}
			}
		}
		return false;

	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) {
		return this.isItemValidForSlot(slot, stack) && canStack(slots()[slot], stack);
	}

}
