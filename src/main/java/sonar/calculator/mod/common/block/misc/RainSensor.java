package sonar.calculator.mod.common.block.misc;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityRainSensor;

public class RainSensor extends BlockContainer {

	public static final PropertyBool bool = PropertyBool.create("active");

	public RainSensor() {
		super(Material.wood);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(bool, true));
	}

	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
	}

	public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
		return state.getValue(bool) ? 15 : 0;
	}

	public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
		return state.getValue(bool) ? 15 : 0;
	}

	/* @SideOnly(Side.CLIENT) public IIcon getIcon(int side, int meta) { return side == 1 ? meta == 15 ? this.icons[1] : this.icons[0] : this.icons[2]; }
	 * 
	 * @SideOnly(Side.CLIENT) public void registerBlockIcons(IIconRegister register) { this.icons[0] = register.registerIcon(Calculator.modid + ":rain_sensor_top"); this.icons[1] = register.registerIcon(Calculator.modid + ":rain_sensor_top_on"); this.icons[2] = register.registerIcon(Calculator.modid + ":rain_sensor_bottom"); } */

	public int getMetaFromState(IBlockState state) {
		return state.getValue(bool) ? 1 : 0;
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(bool, meta==1?true:false);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRainSensor();
	}

	public boolean isFullCube() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int getRenderType() {
		return 3;
	}

	public boolean canProvidePower() {
		return true;
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { bool });
	}
}