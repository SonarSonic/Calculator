package sonar.calculator.mod.common.item.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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

public class Wrench extends SonarItem implements IWrench {

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (!player.canPlayerEdit(pos, side, stack)) {
			return EnumActionResult.PASS;
		}
		TileEntity te = world.getTileEntity(pos);
		Block block = world.getBlockState(pos).getBlock();
		if (player.isSneaking()) {
			if (block instanceof IWrenchable && ((IWrenchable) block).canWrench(player, world, pos))
				((IWrenchable) block).wrenchBlock(player, world, pos, true);
			// DISMANTLE needs to be added again.
		} else {
			if (te != null && te instanceof IMachineSides) {
				((IMachineSides) te).getSideConfigs().increaseSide(side);
			}
		}
		return EnumActionResult.SUCCESS;
	}
}
