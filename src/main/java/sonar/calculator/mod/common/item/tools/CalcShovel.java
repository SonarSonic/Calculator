package sonar.calculator.mod.common.item.tools;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.CalculatorConfig;
import sonar.core.helpers.FontHelper;

import java.util.List;

public class CalcShovel extends ItemSpade {
	public CalcShovel(ToolMaterial material) {
		super(material);
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag par4) {
        super.addInformation(stack, world, list, par4);
		if (!CalculatorConfig.isEnabled(stack)) {
			list.add(FontHelper.translate("calc.ban"));
		}
	}
}
