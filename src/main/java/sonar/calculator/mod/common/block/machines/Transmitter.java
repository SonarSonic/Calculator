package sonar.calculator.mod.common.block.machines;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityTransmitter;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.ISpecialTooltip;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class Transmitter extends SonarBlock implements ITileEntityProvider, ISpecialTooltip {

	public Transmitter() {
		super(SonarMaterials.machine, false);
		this.hasSpecialRenderer = true;
		this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
	}

	@Nonnull
    @Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World var1, int var2) {
		return new TileEntityTransmitter();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, World world, List<String> list, NBTTagCompound tag) {
		CalculatorHelper.addEnergytoToolTip(stack, world, list);
	}

	@Override
	public boolean canPlaceBlockAt(World world, @Nonnull BlockPos pos) {
		return world.getBlockState(pos.offset(EnumFacing.UP)).getBlock().isReplaceable(world, pos.offset(EnumFacing.UP));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
		float x1 = pos.getX() + random.nextFloat();
		float y1 = pos.getY() + 0.5F;
		float z1 = pos.getZ() + random.nextFloat();

		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x1, y1, z1, 0.0D, 0.0D, 0.0D);
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x1, y1, z1, 0.0D, 0.0D, 0.0D);
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x1, y1 + 1.0F, z1, 0.0D, 0.0D, 0.0D);
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x1, y1 + 1.0F, z1, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		setBlocks(world, pos, state);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		super.breakBlock(world, pos, state);
		this.removeBlocks(world, pos, state);
	}

	private void setBlocks(World world, BlockPos pos, IBlockState state) {
		world.setBlockState(pos.offset(EnumFacing.UP), Calculator.transmitterBlock.getDefaultState());
	}

	private void removeBlocks(World world, BlockPos pos, IBlockState state) {
		world.setBlockToAir(pos.offset(EnumFacing.UP));
	}
}
