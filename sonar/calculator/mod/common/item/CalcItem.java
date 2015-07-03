package sonar.calculator.mod.common.item;

import java.util.List;

import sonar.calculator.mod.CalculatorConfig;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class CalcItem extends Item {

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list,	boolean par4) {
		super.addInformation(stack, player, list, par4);
		if(!CalculatorConfig.isEnabled(stack)){
			list.add(FontHelper.translate("calc.ban"));
		}
	}
}
