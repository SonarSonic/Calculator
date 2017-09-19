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
import sonar.calculator.mod.common.tileentity.machines.TileEntityHealthProcessor;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMaterials;
import sonar.core.common.block.SonarSidedBlock;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.IGuiTile;

public class HealthProcessor extends SonarSidedBlock {

	public HealthProcessor() {
		super(SonarMaterials.machine, true, true);
	}

    @Override
	public boolean hasAnimatedFront() {
		return false;
	}
	
	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
        if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == Calculator.wrench) {
			return false;
		}
		if (player != null && !world.isRemote) {
			player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityHealthProcessor();
	}

	@Override
    public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List<String> list) {
		int health = stack.getTagCompound().getInteger("Food");
		if (health != 0) {
			list.add(FontHelper.translate("points.health") + ": " + health);
		}
	}

    @Override
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list) {
        int health = stack.getTagCompound().getInteger("Food");
        if (health != 0) {
            list.add(FontHelper.translate("points.health") + ": " + health);
        }
    }
}
