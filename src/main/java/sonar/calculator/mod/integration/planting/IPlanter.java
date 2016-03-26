package sonar.calculator.mod.integration.planting;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import sonar.core.api.IRegistryObject;

public interface IPlanter extends IRegistryObject {
	
	public boolean canTierPlant(ItemStack stack, int tier);	

    public EnumPlantType getPlantType(ItemStack stack, World world, BlockPos pos);
    
    public IBlockState getPlant(ItemStack stack, World world, BlockPos pos);	
	
}
