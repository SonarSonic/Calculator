package sonar.calculator.mod.common.item.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.api.blocks.IWrenchable;
import sonar.core.common.item.SonarItem;
import sonar.core.helpers.SonarHelper;
import cofh.api.block.IDismantleable;
import cofh.api.tileentity.IReconfigurableSides;

public class Wrench extends SonarItem {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		if (!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		}
		TileEntity te = world.getTileEntity(x, y, z);
		Block block = world.getBlock(x, y, z);
		if (!player.isSneaking()) {
			if (block instanceof IWrenchable)
				((IWrenchable) block).onWrench(world, x, y, z, side);
			else if (te != null && te instanceof IReconfigurableSides)
				((IReconfigurableSides) te).incrSide(side);

		} else {
			if (block instanceof IDismantleable && ((IDismantleable) block).canDismantle(player, world, x, y, z))
				((IDismantleable) block).dismantleBlock(player, block, world, x, y, z,false);

		}

		return true;
	}

}
