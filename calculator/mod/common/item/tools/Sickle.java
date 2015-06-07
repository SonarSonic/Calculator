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
import sonar.calculator.mod.common.item.CalcItem;
import sonar.core.utils.helpers.FontHelper;

public class Sickle extends CalcItem {

	private static Random rand = new Random();

	public Sickle() {
		this.setMaxDamage(1000);
		this.maxStackSize = 1;
		setMaxDamage(64);

	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
		if (!player.canPlayerEdit(x, y, z, par7, stack)) {
			return false;
		}

		Block block = world.getBlock(x, y, z);
		if (block == Calculator.pearLeaf) {
			if (player.inventory.getFirstEmptyStack() == -1) {

				FontHelper.sendMessage(StatCollector.translateToLocal("inv.full"), world, player);

			} else if (player.inventory.getFirstEmptyStack() != -1) {
				world.setBlock(x, y, z, Calculator.leaves);
				player.inventory.addItemStackToInventory(new ItemStack(Calculator.pear, 0 + (int) (Math.random() * ((1 - 0) + 1))));
				player.inventory.addItemStackToInventory(new ItemStack(Calculator.rotten_pear, 0 + (int) (Math.random() * ((1 - 0) + 1))));
				stack.damageItem(1, player);
			}
		} else if (block == Calculator.diamondLeaf) {
			if (player.inventory.getFirstEmptyStack() == -1) {
				if (!world.isRemote) {
					FontHelper.sendMessage(StatCollector.translateToLocal("inv.full"), world, player);
				}

			} else if (player.inventory.getFirstEmptyStack() != -1) {
				world.setBlock(x, y, z, Calculator.diamondleaves);
				player.inventory.addItemStackToInventory(new ItemStack(Items.diamond, 0 + (int) (Math.random() * ((1 - 0) + 1))));
				player.inventory.addItemStackToInventory(new ItemStack(Calculator.weakeneddiamond, 0 + (int) (Math.random() * ((1 - 0) + 1))));
				stack.damageItem(1, player);
			}
		} else {
			return false;
		}

		return true;
	}

	public static float damageItem(int i, EntityPlayer entityplayer) {
		return 1.0F;
	}
}