package sonar.calculator.mod.common.block.misc;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityScarecrow;
import sonar.core.common.block.SonarBlockContainer;
import sonar.core.helpers.FontHelper;

import javax.annotation.Nonnull;

public class Scarecrow extends SonarBlockContainer {

	public Scarecrow() {
		super(Material.CLOTH, true);
		this.hasSpecialRenderer = true;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.6F, 1.0F);
	}

    @Nonnull
    @Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	private void setBlocks(World world, BlockPos pos) {
		world.setBlockState(pos.offset(EnumFacing.UP, 1), Calculator.scarecrowBlock.getDefaultState(), 3);
		world.setBlockState(pos.offset(EnumFacing.UP, 2), Calculator.scarecrowBlock.getDefaultState(), 3);
	}

	@Override
	public boolean canPlaceBlockAt(World world, @Nonnull BlockPos pos) {
		return world.isAirBlock(pos.offset(EnumFacing.UP, 1)) && world.isAirBlock(pos.offset(EnumFacing.UP, 2));
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		setBlocks(world, pos);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		super.breakBlock(world, pos, state);
		world.setBlockToAir(pos.offset(EnumFacing.UP, 1));
		world.setBlockToAir(pos.offset(EnumFacing.UP, 2));
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World var1, int var2) {
		return new TileEntityScarecrow();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote){
			FontHelper.sendMessage("Wow, that's an impressive Scarecrow", world, player);
			return true;
		}
		return true;
	}
}
