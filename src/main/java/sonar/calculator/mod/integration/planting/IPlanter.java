package sonar.calculator.mod.integration.planting;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IPlanter {

	public Block getCropFromStack(ItemStack stack);

	public int getMetaFromStack(ItemStack stack);
}
