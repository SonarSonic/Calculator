package sonar.calculator.mod.common.item.tools;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.core.common.item.SonarItem;
import sonar.core.utils.helpers.FontHelper;

public class Sickle extends SonarItem {

	public Sickle() {
		this.maxStackSize = 1;

	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
		if (!player.canPlayerEdit(x, y, z, par7, stack)) {
			return false;
		}

		Block block = world.getBlock(x, y, z);
		if (block == Calculator.pearLeaf) {
			if (player.inventory.getFirstEmptyStack() == -1) {

				FontHelper.sendMessage(FontHelper.translate("inv.full"), world, player);

			} else if (player.inventory.getFirstEmptyStack() != -1) {
				world.setBlock(x, y, z, Calculator.leaves);
				player.inventory.addItemStackToInventory(new ItemStack(Calculator.pear, 0 + (int) (Math.random() * ((1 - 0) + 1))));
				player.inventory.addItemStackToInventory(new ItemStack(Calculator.rotten_pear, 0 + (int) (Math.random() * ((1 - 0) + 1))));
			}
		} else if (block == Calculator.diamondLeaf) {
			if (player.inventory.getFirstEmptyStack() == -1) {
				if (!world.isRemote) {
					FontHelper.sendMessage(FontHelper.translate("inv.full"), world, player);
				}

			} else if (player.inventory.getFirstEmptyStack() != -1) {
				world.setBlock(x, y, z, Calculator.diamondleaves);
				player.inventory.addItemStackToInventory(new ItemStack(Items.diamond, 0 + (int) (Math.random() * ((1 - 0) + 1))));
				player.inventory.addItemStackToInventory(new ItemStack(Calculator.weakeneddiamond, 0 + (int) (Math.random() * ((1 - 0) + 1))));
			}
		} else {
			return false;
		}

		return true;
	}

}