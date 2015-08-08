package sonar.calculator.mod.common.item.calculators;

import gnu.trove.map.hash.THashMap;

import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IResearchStore;
import sonar.calculator.mod.common.recipes.RecipeRegistry;
import sonar.calculator.mod.common.recipes.RecipeRegistry.CalculatorRecipes;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.item.InventoryItem;
import sonar.core.utils.IItemInventory;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CalculatorItem extends SonarCalculator implements IItemInventory, IResearchStore {

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
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		this.itemIcon = iconregister.registerIcon("Calculator:calculator");
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

	public Map<Integer, Integer> getResearch(ItemStack stack) {
		Map<Integer, Integer> unblocked = new THashMap<Integer, Integer>();
		if (stack != null && stack.getItem() instanceof IResearchStore) {
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			unblocked = CalculatorRecipes.instance().readFromNBT(stack.getTagCompound(), "unblocked");
		}
		return unblocked;
	}

	public void setResearch(ItemStack stack, Map<Integer, Integer> unblocked, int stored, int max) {
		if (stack != null && stack.getItem() == Calculator.itemCalculator) {
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			CalculatorRecipes.instance().writeToNBT(stack.getTagCompound(), unblocked, "unblocked");
			stack.getTagCompound().setInteger("Max", max);
			stack.getTagCompound().setInteger("Stored", stored);

		}
	}

}
