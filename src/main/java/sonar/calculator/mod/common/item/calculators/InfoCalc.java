package sonar.calculator.mod.common.item.calculators;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.item.SonarItem;
import sonar.core.utils.helpers.FontHelper;

public class InfoCalc extends SonarItem {

	public InfoCalc() {
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
}
