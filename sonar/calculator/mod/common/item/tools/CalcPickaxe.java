package sonar.calculator.mod.common.item.tools;

import java.util.List;
import java.util.Random;

import sonar.calculator.mod.CalculatorConfig;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class CalcPickaxe extends ItemPickaxe
{
	private Random rand = new Random();
  public CalcPickaxe(ToolMaterial arg0) {
    super(arg0);
  }
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list,	boolean par4) {
		super.addInformation(stack, player, list, par4);
		if(!CalculatorConfig.isEnabled(stack)){
			list.add(StatCollector.translateToLocal("calc.ban"));
		}
	}
}
