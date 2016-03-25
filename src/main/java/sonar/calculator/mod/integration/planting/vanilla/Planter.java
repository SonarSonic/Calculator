package sonar.calculator.mod.integration.planting.vanilla;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import sonar.calculator.mod.integration.planting.IPlanter;

public class Planter implements IPlanter {

	@Override
	public boolean isLoadable() {
		return true;
	}

	@Override
	public String getName() {
		return "Vanilla Planter";
	}

	@Override
	public boolean canTierPlant(ItemStack stack, int tier) {
		if(stack.getItem() instanceof IPlantable){
			return true;
		}
		return false;
	}

	@Override
	public EnumPlantType getPlantType(ItemStack stack, World world, BlockPos pos) {
		IPlantable plant = (IPlantable) stack.getItem();
		return plant.getPlantType(world, pos);
	}

	@Override
	public IBlockState getPlant(ItemStack stack, World world, BlockPos pos) {
		IPlantable plant = (IPlantable) stack.getItem();
		return plant.getPlant(world, pos);
	}

}
