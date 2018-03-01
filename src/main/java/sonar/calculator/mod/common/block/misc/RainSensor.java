package sonar.calculator.mod.common.block.misc;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.common.tileentity.misc.TileEntityRainSensor;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;

public class RainSensor extends SonarMachineBlock {

	public static final PropertyBool bool = PropertyBool.create("active");
	protected static final AxisAlignedBB sensor = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625*6, 1.0D);

	public RainSensor() {
		super(Material.WOOD, false, true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(bool, true));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return RainSensor.sensor;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return RainSensor.sensor;
	}

	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state.getValue(bool) ? 15 : 0;
	}

	@Override
	public int getStrongPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state.getValue(bool) ? 15 : 0;
	}

    @Override
	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState().withProperty(bool, false);
	}

    @Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(bool) ? 1 : 0;
	}

    @Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(bool, meta == 1);
	}

    @Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, bool);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRainSensor();
	}

    @Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

    @Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

    @Override
	public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public boolean dropStandard(IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		return false;
	}
}