package sonar.calculator.mod.common.block.calculators;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.core.common.block.SonarBlockContainer;
import sonar.core.common.block.SonarMaterials;
import sonar.core.network.FlexibleGuiHandler;
import sonar.core.utils.ISpecialTooltip;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class AtomicCalculator extends SonarBlockContainer implements ITileEntityProvider, ISpecialTooltip {

	public AtomicCalculator() {
		super(SonarMaterials.machine, true);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World world, int i) {
		return new TileEntityCalculator.Atomic();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, World world, List<String> list, @Nullable NBTTagCompound tag) {

	}
}
