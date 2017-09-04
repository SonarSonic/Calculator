package sonar.calculator.mod.common.item.tools;

import net.minecraft.item.ItemPickaxe;

import java.util.Random;

public class CalcPickaxe extends ItemPickaxe {
	private Random rand = new Random();

	public CalcPickaxe(ToolMaterial arg0) {
		super(arg0);
	}
}
