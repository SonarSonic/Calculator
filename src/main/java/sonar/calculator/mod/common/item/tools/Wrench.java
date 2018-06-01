package sonar.calculator.mod.common.item.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.core.api.blocks.IWrench;
import sonar.core.api.blocks.IWrenchable;
import sonar.core.common.item.SonarItem;
import sonar.core.utils.IMachineSides;

import javax.annotation.Nonnull;

public class Wrench extends SonarItem implements IWrench {

	@Nonnull
    @Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(pos);
		Block block = world.getBlockState(pos).getBlock();
		if (player.isSneaking()) {
			if (block instanceof IWrenchable && ((IWrenchable) block).canWrench(player, world, pos)){
				world.getBlockState(pos).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos), te, player.getHeldItem(hand));
				((IWrenchable) block).onWrenched(player, world, pos);
			}
		} else {
			if (te instanceof IMachineSides) {
				((IMachineSides) te).getSideConfigs().increaseSide(side);
			}
		}
		return EnumActionResult.SUCCESS;
	}

	/**doesn't call onWrenched*/
	public static void dropBlock(EntityPlayer player, EnumHand hand, World world, BlockPos pos){
		TileEntity te = world.getTileEntity(pos);
		world.getBlockState(pos).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos), te, player.getHeldItem(hand));
	}
}
