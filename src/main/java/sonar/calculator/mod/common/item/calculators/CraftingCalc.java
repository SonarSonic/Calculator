package sonar.calculator.mod.common.item.calculators;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.item.InventoryItem;
import sonar.core.inventory.IItemInventory;
import sonar.core.utils.helpers.FontHelper;

public class CraftingCalc extends SonarCalculator implements IItemInventory {
	
	public static int energyUsage = CalculatorConfig.getInteger("Crafting Calculator");

	public CraftingCalc(){
		this.maxStackSize=1;	
		capacity = energyUsage;
		maxReceive = energyUsage;
		maxExtract = energyUsage;
		maxTransfer = energyUsage;
	}
	
	public static class CraftingInventory extends InventoryItem {
		public static final int size = 10;
		public CraftingInventory(ItemStack stack) {
			super(stack, size, "Items");
		}
	}

	@Override
	public InventoryItem getInventory(ItemStack stack) {
		return new CraftingInventory(stack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		return onCalculatorRightClick(itemstack, world, player, CalculatorGui.CraftingCalculator);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		int storedItems = getInventory(stack).getItemsStored(stack);
		if (storedItems != 0) {
			list.add(FontHelper.translate("calc.storedstacks") + ": " + storedItems);
		}
	}
}
