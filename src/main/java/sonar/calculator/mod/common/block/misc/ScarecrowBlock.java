package sonar.calculator.mod.common.block.misc;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarBlock;

public class ScarecrowBlock extends SonarBlock {

	public ScarecrowBlock() {
		super(Material.CLOTH, false, false);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		world.setBlockToAir(pos);
		for (int i = 1; i < 3; i++) {
			BlockPos offset = pos.offset(EnumFacing.DOWN, i);
			IBlockState offsetState = world.getBlockState(offset);
			Block block = world.getBlockState(offset).getBlock();
			if (block == Calculator.scarecrow) {
				block.dropBlockAsItem(world, offset, offsetState, 0);
				world.setBlockToAir(offset);
			}
		}
	}

    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player){
    	return new ItemStack(Calculator.scarecrow, 1);
    }
    
	@Override
	public int quantityDropped(Random rand) {
		return 0;
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public boolean dropStandard(IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState();
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();

	}

	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}

}
