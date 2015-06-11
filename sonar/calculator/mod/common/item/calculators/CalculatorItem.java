package sonar.calculator.mod.common.item.calculators;

import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.IResearchStore;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipe;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipes;
import sonar.calculator.mod.integration.nei.handlers.CalculatorRecipeHandler.SmeltingPair;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.item.InventoryItem;
import sonar.core.utils.IItemInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CalculatorItem extends SonarCalculator implements IItemInventory, IResearchStore {

	public static class CalculatorInventory extends InventoryItem {
		public CalculatorInventory(ItemStack stack) {
			super(stack, 3);
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
			list.add(StatCollector.translateToLocal("calc.storedstacks") + ": " + storedItems);
		}
		if (stack.hasTagCompound()) {
			int max = stack.stackTagCompound.getInteger("Max");
			int stored = stack.stackTagCompound.getInteger("Stored");
			if (max != 0) {
				list.add(StatCollector.translateToLocal("research.recipe") + ": " + stored + "/" + max);
			}
		}
	}

	public int[] getResearch(ItemStack stack) {
		int[] unblocked = new int[CalculatorRecipes.recipes().getIDList().size() + 1];
		if (stack != null && stack.getItem() == Calculator.itemCalculator) {
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			unblocked = stack.stackTagCompound.getIntArray("Unblocked");
			if (unblocked != null && unblocked.length > 1) {
				return unblocked;
			} else {
				return new int[CalculatorRecipes.recipes().getIDList().size() + 1];
			}
		} else {
			return unblocked;
		}
	}

	public void setResearch(ItemStack stack, int[] unblocked, int stored, int max) {
		if (stack != null && stack.getItem() == Calculator.itemCalculator) {
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			stack.stackTagCompound.setIntArray("Unblocked", unblocked);
			stack.stackTagCompound.setInteger("Max", max);
			stack.stackTagCompound.setInteger("Stored", stored);

		}
	}

}
