package sonar.calculator.mod.common.item.calculators;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.item.CalcItem;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class InfoCalc extends CalcItem {

	public InfoCalc() {
		setCreativeTab(Calculator.Calculator);
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		list.add(FontHelper.translate("info.nei"));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		player.openGui(Calculator.instance, CalculatorGui.InfoCalculator, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		return itemstack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		this.itemIcon = iconregister.registerIcon("Calculator:infocalculator");
	}

}
