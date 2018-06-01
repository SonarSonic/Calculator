package sonar.calculator.mod.common.block.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMaterials;
import sonar.core.common.block.SonarSidedBlock;
import sonar.core.network.FlexibleGuiHandler;
import sonar.core.upgrades.MachineUpgrade;
import sonar.core.utils.ISpecialTooltip;

import javax.annotation.Nonnull;
import java.util.List;

public class AnalysingChamber extends SonarSidedBlock implements ISpecialTooltip {

	public AnalysingChamber() {
		super(SonarMaterials.machine, true);
		this.hasSpecialRenderer = true;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player != null) {
            player.getHeldItemMainhand();
            if (player.getHeldItemMainhand().getItem() instanceof MachineUpgrade) {
				return false;
			} else {
                player.getHeldItemMainhand();
                if (player.getHeldItemMainhand().getItem() == Calculator.wrench) {
                    return false;
                } else {
                    if (!world.isRemote) {
						FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
                    }
                    return true;
                }
            }
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World world, int i) {
		return new TileEntityAnalysingChamber();
	}


	@Override
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list, NBTTagCompound tag) {
        CalculatorHelper.addEnergytoToolTip(stack, world, list);
        list.add("Doesn't require power to operate");
	}

    @Override
	public boolean hasAnimatedFront() {
		return false;
	}

    @Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

    @Nonnull
    @Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
}
