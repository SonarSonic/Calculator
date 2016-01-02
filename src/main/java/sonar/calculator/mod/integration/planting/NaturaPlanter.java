package sonar.calculator.mod.integration.planting;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;

public class NaturaPlanter implements IPlanter {

	@Override
	public Block getCropFromStack(ItemStack stack) {
		if(stack.getItem() instanceof IPlantable){
			return ((IPlantable)stack.getItem()).getPlant(null, 0, 0, 0);
		}
		return null;
	}

	@Override
	public int getMetaFromStack(ItemStack stack) {
		if(stack.getItem() instanceof IPlantable){
			return stack.getItemDamage()*4;
		}
		return 0;
	}

}
