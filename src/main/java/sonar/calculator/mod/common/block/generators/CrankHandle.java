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
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;
import sonar.core.common.block.SonarBlockContainer;
import sonar.core.common.block.SonarMaterials;
import sonar.core.common.block.properties.SonarProperties;

import javax.annotation.Nonnull;

public class CrankHandle extends SonarBlockContainer {

	public CrankHandle() {
		super(SonarMaterials.machine, true);
		this.hasSpecialRenderer = true;
        this.setBlockBounds((float) (0.0625 * 3), 0.0F, (float) (0.0625 * 3), (float) (1 - 0.0625 * 3), 0.625F, (float) (1 - 0.0625 * 3));
	}

    @Nonnull
    @Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
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
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		IBlockState down = world.getBlockState(pos.offset(EnumFacing.DOWN));
		if (down.getBlock() == Calculator.handcrankedGenerator) {
			world.setBlockState(pos, state.withProperty(SonarProperties.FACING, down.getValue(SonarProperties.FACING)), 3);
		}

	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World var1, int var2) {
		return new TileEntityCrankHandle();
	}
}
