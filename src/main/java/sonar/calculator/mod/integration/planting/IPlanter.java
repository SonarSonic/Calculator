package sonar.calculator.mod.integration.planting;

import java.util.Random;

import sonar.core.utils.IRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public interface IPlanter extends IRegistryObject {
	
	public boolean canTierPlant(ItemStack stack, int tier);	

    public EnumPlantType getPlantType(ItemStack stack, World world, BlockPos pos);
    
    public IBlockState getPlant(ItemStack stack, World world, BlockPos pos);	
	
}
