package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedPowerCube;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.api.utils.BlockInteractionType;
import sonar.core.common.block.SonarMaterials;
import sonar.core.common.block.SonarSidedBlock;
import sonar.core.utils.IGuiTile;

public class AdvancedPowerCube extends SonarSidedBlock {

	public AdvancedPowerCube() {
		super(SonarMaterials.machine, true, true);
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null) {
			if (interact.type == BlockInteractionType.SHIFT_RIGHT) {
				if (world.getTileEntity(pos) instanceof TileEntityAdvancedPowerCube) {
					TileEntityAdvancedPowerCube cube = (TileEntityAdvancedPowerCube) world.getTileEntity(pos);
					cube.getSideConfigs().increaseSide(interact.getDir());
					world.markBlockRangeForRenderUpdate(pos, pos);
				}
			} else {
				player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityAdvancedPowerCube();
	}

	@Override
    public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List<String> list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);
	}

    @Override
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list) {
        CalculatorHelper.addEnergytoToolTip(stack, world, list);
    }

    @Override
	public boolean hasAnimatedFront() {
		return false;
	}
}
