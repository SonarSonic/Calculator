package sonar.calculator.mod.common.block.generators;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;

import javax.annotation.Nonnull;

public class CrankHandle extends SonarMachineBlock {

	public CrankHandle() {
		super(SonarMaterials.machine, true, true);
        setBlockBounds((float) (0.0625 * 3), 0.0F, (float) (0.0625 * 3), (float) (1 - 0.0625 * 3), 0.625F, (float) (1 - 0.0625 * 3));
	}

    @Nonnull
    @Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

    @Override
	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		TileEntity target = world.getTileEntity(pos);
		if (target instanceof TileEntityCrankHandle) {
			TileEntityCrankHandle crank = (TileEntityCrankHandle) target;
            int rand1 = (int) (Math.random() * 100.0D);
			if (!crank.cranked) {
				crank.cranked = true;
				if (rand1 < 1) {
					spawnAsEntity(world, pos, new ItemStack(Items.STICK, 2));
					world.setBlockToAir(pos);
				}
				return true;
			}
		}
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(World world, @Nonnull BlockPos pos) {
		super.canPlaceBlockAt(world, pos);
        if (pos.getY() >= 0 && pos.getY() < 256) {
			Block block = world.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock();
            return block == Calculator.handcrankedGenerator;
		}

		return false;
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor){
		Block block = world.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock();
		/*
		if (block != Calculator.handcrankedGenerator) {
			world.destroyBlock(pos, true);
			world.markBlockForUpdate(pos);
		}
		*/
	}

	@Override
	protected void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState down = worldIn.getBlockState(pos.offset(EnumFacing.DOWN));
			if (down.getBlock() == Calculator.handcrankedGenerator) {
				worldIn.setBlockState(pos, state.withProperty(FACING, down.getValue(FACING)), 3);
			} else {
				super.setDefaultFacing(worldIn, pos, state);
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World var1, int var2) {
		return new TileEntityCrankHandle();
	}

	@Override
	public boolean dropStandard(IBlockAccess world, BlockPos pos) {
		return true;
	}
}
