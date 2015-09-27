package sonar.calculator.mod.integration.planting;

import sonar.calculator.mod.Calculator;

import com.InfinityRaider.AgriCraft.api.API;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class AgricraftPlanter implements IPlanter {

	@Override
	public Block getCropFromStack(ItemStack stack) {
		return null;
	}

	@Override
	public int getMetaFromStack(ItemStack stack) {
		return 0;
	}

}
