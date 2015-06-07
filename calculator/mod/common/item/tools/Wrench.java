package sonar.calculator.mod.common.item.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.api.IWrench;
import sonar.calculator.mod.common.item.CalcItem;
import sonar.core.utils.ISonarSides;
import sonar.core.utils.helpers.SonarHelper;

public class Wrench extends CalcItem {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int side, float par8, float par9, float par10) {
		if (!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		}
			TileEntity te = world.getTileEntity(x, y, z);
			Block block = world.getBlock(x, y, z);
			if (!player.isSneaking()) {
				if (te != null && te instanceof ISonarSides)
					if (((ISonarSides) te).canBeConfigured()) {
						((ISonarSides) te).increaseSide(side, player.dimension);
					}
			} else {
				if (block instanceof IWrench) {
					SonarHelper.dropTile(te,player, block, world, x, y, z);
				}

			}
		
		return true;
	}


}
