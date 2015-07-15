package sonar.calculator.mod.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


/**not fully implemented yet but will be eventually...hopefully*/
public class BigStack {

	public int maxStacks;
	public int currentStackSize;
	public int maxStackSize;
	public ItemStack stack;

	public BigStack() {

	}

	public BigStack(int max, int current, ItemStack stack) {
		this.maxStacks = max;
		this.stack = stack;
		this.currentStackSize = current;
		this.maxStackSize = stack.getMaxStackSize() * maxStacks;

	}

	public BigStack(int max, ItemStack stack) {
		this.maxStacks = max;
	}

	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger("maxStacks", maxStacks);
		tag.setInteger("currentStackSize", currentStackSize);
		if (stack != null) {
			NBTTagCompound item = new NBTTagCompound();
			stack.writeToNBT(item);
			tag.setTag("stack", item);
		}
	}

	public void readFromNBT(NBTTagCompound tag) {
		this.maxStacks = tag.getInteger("maxStacks");
		this.currentStackSize = tag.getInteger("currentStackSize");
		this.stack = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("stack"));
		if (stack != null) {
			this.maxStackSize = this.maxStackSize * stack.getMaxStackSize();
		}

	}

	public ItemStack removeStack(int derc) {
		if (this.stack == null || this.currentStackSize == 0) {
			return null;
		}
		ItemStack remove = stack.copy();
		int size = Math.min(currentStackSize, Math.min(stack.getMaxStackSize(), derc));
		remove.stackSize = size;
		this.currentStackSize -= size;
		if (this.currentStackSize <= 0) {
			this.stack = null;
			this.maxStackSize = 0;
			this.currentStackSize = 0;
		}
		return remove;
	}

	public ItemStack addStack(ItemStack stack) {
		if (canAddStack(stack) && (this.maxStackSize == 0 || !(this.currentStackSize >= this.maxStackSize))) {
			if (this.stack == null) {
				this.setItemStack(stack);
			}
			int size = Math.min(stack.stackSize, this.maxStackSize - this.currentStackSize);
			stack.stackSize -= size;
			this.currentStackSize += size;
			if (stack.stackSize <= 0) {
				stack = null;
			}
		}
		return stack;
	}

	public boolean canAddStack(ItemStack check) {
		if (check == null) {
			return false;
		}
		if (stack == null) {
			return true;
		}
		if (stack.getItem() == check.getItem() && stack.getItemDamage() == check.getItemDamage()) {
			if (ItemStack.areItemStackTagsEqual(stack, check)) {
				return true;
			}
		}
		return false;
	}

	public ItemStack getOutputStack() {
		if (stack == null || this.currentStackSize == 0) {
			return null;
		}
		int stackSize = 0;
		int current = (int) Math.floor((currentStackSize / stack.getMaxStackSize()));
		if (current == this.maxStacks) {
			stackSize = stack.getMaxStackSize();
		} else {
			stackSize = this.currentStackSize - (current * stack.getMaxStackSize());
		}
		if (stackSize == 0) {
			return null;
		}
		ItemStack outputStack = stack.copy();
		outputStack.stackSize = stackSize;
		if(stackSize==stack.getMaxStackSize()){
			return null;
		}
		return outputStack;
	}

	public void setItemStack(ItemStack stack) {
		this.stack = stack;
		this.maxStackSize = stack.getMaxStackSize() * this.maxStacks;
	}

	public ItemStack getFullStack() {
		if (this.stack == null || this.currentStackSize == 0) {
			return null;
		}

		ItemStack full = stack.copy();
		full.stackSize = this.currentStackSize;
		return full;

	}

	public ItemStack savedStack() {
		return stack;
	}
}
