package sonar.calculator.mod.common.item.calculators;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.item.calculators.CalculatorItem.CalculatorInventory;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.item.InventoryItem;
import sonar.core.utils.IItemInventory;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ScientificCalc extends SonarCalculator implements IItemInventory {
	
	public ScientificCalc() {
		capacity = CalculatorConfig.scientificEnergy;
		maxReceive = CalculatorConfig.scientificEnergy;
		maxExtract = CalculatorConfig.scientificEnergy;
		maxTransfer = CalculatorConfig.scientificEnergy;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,	EntityPlayer player) {
		return onCalculatorRightClick(itemstack, world, player,CalculatorGui.ScientificCalculator);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		this.itemIcon = iconregister.registerIcon("Calculator:scientificcalculator");
	}

	@Override
	public InventoryItem getInventory(ItemStack stack) {
		return new CalculatorInventory(stack);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean par4) {
		super.addInformation(stack, player, list, par4);
		int storedItems = getInventory(stack).getItemsStored(stack);
		if(storedItems!=0){
			list.add(FontHelper.translate("calc.storedstacks")+": " + storedItems);}
	}
}
