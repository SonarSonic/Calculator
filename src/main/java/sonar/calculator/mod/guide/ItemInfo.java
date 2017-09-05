package sonar.calculator.mod.guide;

import net.minecraft.item.ItemStack;

public class ItemInfo implements IItemInfo {

	public Category category;
	public ItemStack stack;
	public String info;
	public ItemStack[] stacks;

	public ItemInfo(Category category, ItemStack stack, String info, ItemStack... stacks) {
		this.category = category;
		this.stack = stack;
		this.info = info;
		this.stacks = stacks;
	}

	@Override
	public Category getMainCategory() {
		return category;
	}

	@Override
	public ItemStack getItem() {
		return stack;
	}

	@Override
	public String getItemInfo() {
		return info;
	}

	@Override
	public ItemStack[] getRelatedItems() {
		return stacks;
	}
}
