package sonar.calculator.mod.common.item.calculators;

import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.api.items.IResearchStore;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.item.InventoryItem;
import sonar.core.inventory.IItemInventory;
import sonar.core.utils.helpers.FontHelper;

public class CalculatorItem extends SonarCalculator implements IItemInventory {

	public CalculatorItem(){
		this.maxStackSize=1;
	}
	
	public static class CalculatorInventory extends InventoryItem {
		public static final int size = 3;

		public CalculatorInventory(ItemStack stack) {
			super(stack, size, "Items");
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		return onCalculatorRightClick(itemstack, world, player, CalculatorGui.Calculator);

	}
	
	@Override
	public InventoryItem getInventory(ItemStack stack) {
		return new CalculatorInventory(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		int storedItems = getInventory(stack).getItemsStored(stack);
		if (storedItems != 0) {
			list.add(FontHelper.translate("calc.storedstacks") + ": " + storedItems);
		}
		if (stack.hasTagCompound()) {
			int max = stack.getTagCompound().getInteger("Max");
			int stored = stack.getTagCompound().getInteger("Stored");
			if (max != 0) {
				list.add(FontHelper.translate("research.recipe") + ": " + stored + "/" + max);
			}
		}
	}

}
