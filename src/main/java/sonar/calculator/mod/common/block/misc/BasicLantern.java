package sonar.calculator.mod.common.block.misc;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.block.SonarMaterials;

public class BasicLantern extends SonarBlock {

	public static final PropertyDirection DIR = PropertyDirection.create("dir");

	public BasicLantern() {
		super(SonarMaterials.machine, false, true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(DIR, EnumFacing.NORTH));
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		// setDefaultFacing(world, pos, state);
	}

	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbour) {
		super.onNeighborChange(world, pos, neighbour);
		try {
			// setDefaultFacing((World) world, pos, world.getBlockState(pos));
		} catch (ClassCastException exception) {
			Calculator.logger.error("Lantern: Tried to cast IBlockAccess to World");
		}
	}

	private EnumFacing getDefaultFacing(IBlockAccess world, BlockPos pos, IBlockState state) {
		if (world != null) {
			Iterator<EnumFacing> iterator = EnumFacing.Plane.VERTICAL.iterator();
			boolean vertical = false;
			do {
				if (!iterator.hasNext()) {
					if (!vertical) {
						vertical = true;
						iterator = EnumFacing.Plane.HORIZONTAL.iterator();
					} else {
						return EnumFacing.DOWN;
					}
				}
				EnumFacing facing = iterator.next();
				IBlockState stateOff = world.getBlockState(pos.offset(facing));
				Block block = stateOff.getBlock();
				if (block.isFullBlock(stateOff)) {
					return facing;
				}
			} while (iterator.hasNext() || !vertical);
		}
		return EnumFacing.DOWN;
	}

	@Deprecated
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (customBB != null) {
			return customBB;
		}

		EnumFacing dir = getDefaultFacing(world, pos, state);
		return new AxisAlignedBB(0.3F + (dir.getFrontOffsetX() * 0.32F), 0.0F + getY(dir), 0.3F + (dir.getFrontOffsetZ() * 0.32F), 0.7F + (dir.getFrontOffsetX() * 0.32F), 0.7F + getY(dir), 0.7F + (dir.getFrontOffsetZ() * 0.32F));
	}

	public float getY(EnumFacing meta) {
		if (meta == EnumFacing.DOWN) {
			return 0.0F;
		} else {
			return 0.1F;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		float x1 = pos.getX() + rand.nextFloat();
		float y1 = pos.getY() + 0.5F;
		float z1 = pos.getZ() + rand.nextFloat();
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x1, y1, z1, 0.0D, 0.0D, 0.0D);
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x1, y1, z1, 0.0D, 0.0D, 0.0D);

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
		return this.getDefaultState().withProperty(DIR, EnumFacing.SOUTH);
	}

	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);
		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}
		return this.getDefaultState().withProperty(DIR, enumfacing);
	}

	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(DIR)).getIndex();
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(DIR, getDefaultFacing(world, pos, state));
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { DIR });
	}

}
