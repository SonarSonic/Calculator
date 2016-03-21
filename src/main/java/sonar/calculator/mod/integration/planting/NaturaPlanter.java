package sonar.calculator.mod.integration.planting;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.IPlantable;

public class NaturaPlanter implements IPlanter {

	@Override
	public Block getCropFromStack(ItemStack stack) {
		if (stack.getItem() instanceof IPlantable) {
			return ((IPlantable) stack.getItem()).getPlant(null, new BlockPos(0, 0, 0)).getBlock();
		}
		return null;
	}

	@Override
	public int getMetaFromStack(ItemStack stack) {
		if (stack.getItem() instanceof IPlantable) {
			return stack.getItemDamage() * 4;
		}
		return 0;
	}

}
