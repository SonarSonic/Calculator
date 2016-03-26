package sonar.calculator.mod.common.item.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sonar.core.common.item.SonarItem;
import sonar.core.helpers.SonarHelper;
import sonar.core.utils.IWrench;
import sonar.core.utils.IWrenchable;


public class Wrench extends SonarItem implements IWrench {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitx, float hity, float hitz) {
		if (!player.canPlayerEdit(pos, side, stack)) {
			return false;
		}
		TileEntity te = world.getTileEntity(pos);
		Block block = world.getBlockState(pos).getBlock();
		if (!player.isSneaking()) {
			if (block instanceof IWrenchable)
				((IWrenchable) block).onWrench(world, pos, side);
			else if (te != null && te instanceof IReconfigurableSides)
				((IReconfigurableSides) te).incrSide(side);

		} else {
			if (block instanceof IDismantleable && ((IDismantleable) block).canDismantle(player, world, x, y, z))
				SonarHelper.dropTile(player, block, world, x, y, z);

		}

		return true;
	}

}
