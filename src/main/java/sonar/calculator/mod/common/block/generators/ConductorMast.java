package sonar.calculator.mod.common.block.generators;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.network.FlexibleGuiHandler;
import sonar.core.utils.ISpecialTooltip;

import javax.annotation.Nonnull;
import java.util.List;

public class ConductorMast extends SonarBlock implements ISpecialTooltip, ITileEntityProvider {

	public ConductorMast() {
		super(SonarMaterials.machine, false);
		this.hasSpecialRenderer = true;
	}

    @Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
		}
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		world.setBlockToAir(pos.offset(EnumFacing.UP, 1));
		world.setBlockToAir(pos.offset(EnumFacing.UP, 2));
		world.setBlockToAir(pos.offset(EnumFacing.UP, 3));
		super.breakBlock(world, pos, state);
		TileEntityConductorMast.setWeatherStationAngles(true, world, pos);
	}

	@Override
	public boolean canPlaceBlockAt(World world, @Nonnull BlockPos pos) {
        return world.getBlockState(pos.offset(EnumFacing.UP, 1)).getBlock() == Blocks.AIR &&
                world.getBlockState(pos.offset(EnumFacing.UP, 2)).getBlock() == Blocks.AIR &&
                world.getBlockState(pos.offset(EnumFacing.UP, 3)).getBlock() == Blocks.AIR;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		setBlocks(world, pos);
	}

	private void setBlocks(World world, BlockPos pos) {
		world.setBlockState(pos.offset(EnumFacing.UP, 1), Calculator.conductormastBlock.getDefaultState());
		world.setBlockState(pos.offset(EnumFacing.UP, 2), Calculator.conductormastBlock.getDefaultState());
		world.setBlockState(pos.offset(EnumFacing.UP, 3), Calculator.conductormastBlock.getDefaultState());
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World var1, int var2) {
		return new TileEntityConductorMast();
	}

    @Override
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list, NBTTagCompound tag) {
        CalculatorHelper.addEnergytoToolTip(stack, world, list);
		list.add(FontHelper.translate("energy.generate") + ": " + CalculatorConfig.CONDUCTOR_MAST_PER_TICK + " RF per strike");
    }

    @Nonnull
    @Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
}
