package sonar.calculator.mod.common.block.generators;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;

public class CrankHandle extends SonarMachineBlock {

	public CrankHandle() {
		super(SonarMaterials.machine, true, true);
		setBlockBounds((float) (0.0625 * 3), 0.0F, (float) (0.0625 * 3), (float) (1 - (0.0625 * 3)), 0.625F, (float) (1 - (0.0625 * 3)));
	}

	public int getRenderType() {
		return 2;
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		TileEntity target = world.getTileEntity(pos);
		if (target instanceof TileEntityCrankHandle) {
			TileEntityCrankHandle crank = (TileEntityCrankHandle) target;
			int rand1 = 0 + (int) (Math.random() * 100.0D);
			if (!crank.cranked) {
				crank.cranked = true;
				if (rand1 < 1) {
					spawnAsEntity(world, pos, new ItemStack(Items.stick, 2));
					world.setBlockToAir(pos);
				}
				return true;
			}
		}
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		super.canPlaceBlockAt(world, pos);
		if ((pos.getY() >= 0) && (pos.getY() < 256)) {
			Block block = world.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock();
			if (block == Calculator.handcrankedGenerator) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbour) {
		super.onNeighborBlockChange(world, pos, state, neighbour);
		Block block = world.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock();
		if (block != Calculator.handcrankedGenerator) {
			world.destroyBlock(pos, true);
			world.markBlockForUpdate(pos);
		}
	}

	@Override
	protected void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState down = worldIn.getBlockState(pos.offset(EnumFacing.DOWN));
			if (down.getBlock() == Calculator.handcrankedGenerator) {
				System.out.print(down.getValue(FACING));
				worldIn.setBlockState(pos, state.withProperty(FACING, down.getValue(FACING)), 3);
			} else {
				super.setDefaultFacing(worldIn, pos, state);
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityCrankHandle();
	}

	@Override
	public boolean dropStandard(IBlockAccess world, BlockPos pos) {
		return true;
	}

}
