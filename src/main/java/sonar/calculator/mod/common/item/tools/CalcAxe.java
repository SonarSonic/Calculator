package sonar.calculator.mod.common.item.tools;

import net.minecraft.item.ItemAxe;

public class CalcAxe extends ItemAxe {
	public CalcAxe(ToolMaterial material) {
		super(material, material.getAttackDamage(), -3.5F);
	}
}
