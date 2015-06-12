package sonar.calculator.mod.common.item.tools;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.StatCollector;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.CalculatorItems;

public class CalcSword extends ItemSword {
	ToolMaterial type;

	public CalcSword(ToolMaterial material) {
		super(material);
		type = material;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,
			Entity entity) {
		if (type == CalculatorItems.FireDiamond) {
			entity.setFire(4);
		}
		super.onLeftClickEntity(stack, player, entity);
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list,	boolean par4) {
		super.addInformation(stack, player, list, par4);
		if (!CalculatorConfig.isEnabled(stack)) {
			list.add(StatCollector.translateToLocal("calc.ban"));
		}
	}
	@Override
    public boolean isItemTool(ItemStack stack)
    {
        return true;
    }
}
