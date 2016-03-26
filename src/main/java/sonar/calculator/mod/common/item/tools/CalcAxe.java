package sonar.calculator.mod.common.item.tools;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.CalculatorConfig;
import sonar.core.helpers.FontHelper;

public class CalcAxe extends ItemAxe {
	public CalcAxe(ToolMaterial material) {
		super(material);
	}
}
