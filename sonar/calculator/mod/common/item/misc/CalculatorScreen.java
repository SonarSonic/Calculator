package sonar.calculator.mod.common.item.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.item.CalcItem;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculatorScreen;

public class CalculatorScreen extends CalcItem {

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (side == 0) {
			return false;
		} else if (!world.getBlock(x, y, z).getMaterial().isSolid() ||world.getBlock(x, y, z)==Calculator.calculatorScreen ||!world.getBlock(x, y, z).hasTileEntity(world.getBlockMetadata(x, y, z))) {
			return false;
		} else {			
			switch (side) {
			case 1:
				++y;
				break;
			case 2:
				--z;
				break;
			case 3:
				++z;
				break;
			case 4:
				--x;
				break;
			case 5:
				++x;
				break;
			}

			if (!player.canPlayerEdit(x, y, z, side, stack)) {

				return false;
			} else if (world.isRemote) {
				return true;
			} else {
				if (side != 1) {
					world.setBlock(x, y, z, Calculator.calculatorScreen, side, 3);
					--stack.stackSize;
					//TileEntityCalculatorScreen screen = (TileEntityCalculatorScreen) world.getTileEntity(x, y, z);
					//if (screen != null) {
					//	screen.direction = side;
					//}
				}

				return true;
			}
		}
	}
}
