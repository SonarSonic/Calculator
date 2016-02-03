package sonar.calculator.mod.api.items;

import java.util.Map;

import net.minecraft.item.ItemStack;

/**not currently implemented anywhere, don't bother using it*/
public interface IResearchStore {

	public Map<Integer, Integer> getResearch(ItemStack stack);	

	public void setResearch(ItemStack stack, Map<Integer, Integer> unblocked, int stored, int max);
}
