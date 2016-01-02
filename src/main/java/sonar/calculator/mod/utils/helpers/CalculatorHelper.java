package sonar.calculator.mod.utils.helpers;

import ic2.api.item.IElectricItem;

import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.core.energy.DischargeValues;
import sonar.core.integration.SonarAPI;
import sonar.core.utils.helpers.FontHelper;
import cofh.api.energy.IEnergyContainerItem;

public class CalculatorHelper {

	/** formatted Gas Levels */
	static DecimalFormat dec = new DecimalFormat("##.##");

	/**
	 * Adds stored energy to the Tool Tip
	 * @param stack Item that will feature list
	 * @param player Player who is hovering over ToolTip
	 * @param list Tool Tip
	 */
	public static void addEnergytoToolTip(ItemStack stack, EntityPlayer player, List list) {
		int energy = stack.getTagCompound().getInteger("energy");
		if (energy != 0) {
			list.add(FontHelper.translate("energy.stored") + ": " + FontHelper.formatStorage(energy));

		}
	}
	
	public static boolean canProvideEnergy(ItemStack stack){
		if (DischargeValues.discharge().value(stack) > 0) {
			return true;
		} else if (stack.getItem() instanceof IEnergyContainerItem) {
			return true;
		} else if (SonarAPI.ic2Loaded() && stack.getItem() instanceof IElectricItem) {
			return true;
		}
		return false;
	}

	/**
	 * Adds stored Item Level for Generators to the Tool Tip
	 * @param stack Item that will feature list
	 * @param player Player who is hovering over ToolTip
	 * @param list Tool Tip
	 */
	public static void addItemLevelToolTip(ItemStack stack, EntityPlayer player, List list) {
		if (stack.hasTagCompound()) {
			int standardLevel = stack.getTagCompound().getInteger("ItemLevel");
			int level = standardLevel * 100 / 5000;
			if (standardLevel != 0 && stack.getItem() == Item.getItemFromBlock(Calculator.starchextractor)) {
				String points = FontHelper.translate("generator.starch") + ": " + level + " %";
				list.add(points);
			} else if (standardLevel != 0 && stack.getItem() == Item.getItemFromBlock(Calculator.redstoneextractor)) {
				String points = FontHelper.translate("generator.redstone") + ": " + level + " %";
				list.add(points);
			} else if (standardLevel != 0 && stack.getItem() == Item.getItemFromBlock(Calculator.glowstoneextractor)) {
				String points = FontHelper.translate("generator.glowstone") + ": " + level + " %";
				list.add(points);
			}
		}
	}

	/**
	 * Adds stored Gas Level for Green Houses to the Tool Tip
	 * @param stack Item that will feature list
	 * @param player Player who is hovering over ToolTip
	 * @param list Tool Tip
	 */
	public static void addGasToolTip(ItemStack stack, EntityPlayer player, List list) {
		if (stack.hasTagCompound()) {

			int carbon = stack.getTagCompound().getInteger("Carbon");
			int oxygen = stack.getTagCompound().getInteger("Oxygen");

			if (carbon != 0) {
				String carbonString = FontHelper.translate("greenhouse.carbon") + ": " + dec.format(carbon * 100 / 100000) + "%";
				list.add(carbonString);
			}
			if (oxygen != 0) {
				String oxygenString = FontHelper.translate("greenhouse.oxygen") + ": " + dec.format(oxygen * 100 / 100000) + "%";
				list.add(oxygenString);
			}
		}
	}
}
