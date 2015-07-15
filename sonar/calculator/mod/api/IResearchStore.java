package sonar.calculator.mod.api;

import java.util.Map;

import net.minecraft.item.ItemStack;

public interface IResearchStore {

	public Map<Integer, Integer> getResearch(ItemStack stack);	

	public void setResearch(ItemStack stack, Map<Integer, Integer> unblocked, int stored, int max);
}
