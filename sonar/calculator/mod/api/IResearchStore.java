package sonar.calculator.mod.api;

import net.minecraft.item.ItemStack;

public interface IResearchStore {

	public int[] getResearch(ItemStack stack);	

	public void setResearch(ItemStack stack, int[] unblocked, int stored, int max);
}
