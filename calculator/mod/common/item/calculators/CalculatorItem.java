package sonar.calculator.mod.common.item.calculators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.containers.ContainerCalculator;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipe;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipes;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.ResearchPlayer;
import sonar.core.common.item.InventoryItem;
import sonar.core.utils.IItemInventory;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CalculatorItem extends SonarCalculator implements IItemInventory {

	public static class CalculatorInventory extends InventoryItem {
		public CalculatorInventory(ItemStack stack) {
			super(stack, 3);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		if (player.isSneaking()) {
			player.openGui(Calculator.instance, CalculatorGui.RecipeInfo, world, (int) player.posX, (int) player.posY, (int) player.posZ);
			return itemstack;
		} else {
			return onCalculatorRightClick(itemstack, world, player, CalculatorGui.Calculator);
		}
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
	}

}
