package sonar.calculator.mod.common.item.modules;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.item.calculators.SonarCalculator;
import sonar.calculator.mod.common.item.calculators.CalculatorItem.CalculatorInventory;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.item.InventoryContainerItem;
import sonar.core.common.item.InventoryItem;
import sonar.core.common.item.InventoryItem;
import sonar.core.utils.IItemInventory;
import sonar.core.utils.helpers.FontHelper;

public class StorageModule extends InventoryContainerItem implements IItemInventory {

	public static class StorageInventory extends InventoryItem {

		public static final int size = 54;

		public StorageInventory(ItemStack stack) {
			super(stack, size, "Items");
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			player.openGui(Calculator.instance, CalculatorGui.StorageModule, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		return itemstack;
	}

	@Override
	public InventoryItem getInventory(ItemStack stack) {
		return new StorageInventory(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		if (!CalculatorConfig.isEnabled(stack)) {
			list.add(FontHelper.translate("calc.ban"));
		}
		if (stack.hasTagCompound()) {
			int storedItems = getInventory(stack).getItemsStored(stack);
			if (storedItems != 0) {
				list.add(FontHelper.translate("calc.storedstacks") + ": " + storedItems);
			}
		}
	}
}
